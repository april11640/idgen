package yu.idgen.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yu.idgen.domain.IdGenerationConfig;
import yu.idgen.domain.IdGenerationException;
import yu.idgen.domain.ServerNodeSequence;
import yu.idgen.service.api.IdService;
import yu.idgen.service.api.ServerNodeSequenceService;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by zsp on 2018/12/4.
 */
@Service
public class SnowflakeIdService implements IdService, InitializingBean {

    private final Logger logger = LoggerFactory.getLogger(SnowflakeIdService.class);

    private final ReentrantLock lock = new ReentrantLock();

    private volatile long nodeSequence = -1;
    private volatile long currentMillis = 0;
    private volatile long incr;
    private String ip;
    private int incrementCapacity;
    private int nodeSequenceShift;
    private int timestampShift;
    private long timestampOffset;

    @Value("${server.port}")
    private Integer serverPort;

    @Autowired
    private ServerNodeSequenceService serverNodeSequenceService;

    @Autowired(required = false)
    private IdGenerationConfig idGenerationConfig;

    @Override
    public void afterPropertiesSet() throws Exception {
        if(serverPort == null) {
            throw new IdGenerationException("未配置: server.port");
        }
        ip = getHostAddress();
        ServerNodeSequence serverNodeSequence
                = serverNodeSequenceService.getServerNodeSequenceByIpAndPort(ip, serverPort);
        if(serverNodeSequence != null) {
            nodeSequence = serverNodeSequence.getId();
            if(logger.isInfoEnabled()) {
                logger.info("服务节点" + ip + ":" + serverPort +"的序列号为：" + nodeSequence);
            }
        }
        int incrementBits;
        if(idGenerationConfig != null) {
            nodeSequenceShift = incrementBits = idGenerationConfig.getIncrementBits();
            timestampShift = nodeSequenceShift + idGenerationConfig.getServerNodeSequenceBits();
            timestampOffset = idGenerationConfig.getTimestampOffset();
        } else {
            nodeSequenceShift = incrementBits = IdGenerationConfig.DEFAULT_INCREMENT_BITS;
            timestampShift = nodeSequenceShift + IdGenerationConfig.DEFAULT_SERVER_NODE_SEQUENCE_BITS;
            timestampOffset = IdGenerationConfig.DEFAULT_TIMESTAMP_OFFSET;
        }
        incrementCapacity = 1 << incrementBits;
    }

    @Override
    public Long generateId() {
        if(nodeSequence == -1) {
            initSequence();
        }
        try{
            //上锁防止单个节点每毫秒的自增数被耗尽而超用
            lock.lock();
            long millis = System.currentTimeMillis();
            if (currentMillis < millis) {
                //更新当前毫秒，并且自增数归零
                currentMillis = millis;
                incr = 0;
            }
            if (incr >= incrementCapacity) {
                incr = 0;
                //如果当前自增数已达最大值，则归零；判断最新毫秒数，是否已流逝到下一毫秒，否则线程自旋等待时间的流逝
                long m = currentMillis;
                long latestMillis = System.currentTimeMillis();
                while (latestMillis <= m) {
                    Thread.yield();
                    latestMillis = System.currentTimeMillis();
                }
                millis = latestMillis;
                currentMillis = millis;
            }
            //时间戳 + 机器序号 + 自增序列
            return ((millis - timestampOffset) << timestampShift) | (nodeSequence << nodeSequenceShift) | incr++;
        } finally {
            lock.unlock();
        }
    }

    @Override
    public List<Long> generateId(int size) {
        if(size < 1) {
            throw new IllegalArgumentException("The size cannot be small than 1.");
        }
        List<Long> list = new ArrayList<>();
        for(int i = 0, len = size; i < len; i++) {
            list.add(generateId());
        }
        return list;
    }

    private void initSequence() {
        try {
            lock.lock();
            if(nodeSequence == -1) {
                ServerNodeSequence serverNodeSequence
                        = serverNodeSequenceService.getOrUpdateEmptyServerNodeSequenceByIpAndPort(ip, serverPort);
                int sequence = serverNodeSequence.getId();
                if(logger.isInfoEnabled()) {
                    logger.info("初始化服务节点" + serverNodeSequence.getNode() + "的序列号为：" + sequence);
                }
                nodeSequence = sequence;
            }
        } finally {
            lock.unlock();
        }
    }

    private String getHostAddress() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
           throw new IdGenerationException("获取本机IP地址异常", e);
        }
    }

}
