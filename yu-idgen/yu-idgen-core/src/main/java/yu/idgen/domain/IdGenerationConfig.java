package yu.idgen.domain;

/**
 * Created by zsp on 2019/1/16.
 */
public class IdGenerationConfig {

    public final static int DEFAULT_TIMESTAMP_BITS = 41;
    public final static int DEFAULT_SERVER_NODE_SEQUENCE_BITS = 10;
    public final static int DEFAULT_INCREMENT_BITS = 12;
    public final static long DEFAULT_TIMESTAMP_OFFSET = 0L;
    public final static float DEFAULT_EMPTY_NODE_FETCH_FACTOR = 2.0f;

    /**
     * 时间戳位数
     */
    private int timestampBits = DEFAULT_TIMESTAMP_BITS;
    /**
     * 服务节点序列号位数
     */
    private int serverNodeSequenceBits = DEFAULT_SERVER_NODE_SEQUENCE_BITS;
    /**
     * 自增序列号位数
     */
    private int incrementBits = DEFAULT_INCREMENT_BITS;
    /**
     * 时间戳的偏移量
     */
    private long timestampOffset = DEFAULT_TIMESTAMP_OFFSET;
    /**
     * 调节获取空节点记录个数的因子
     */
    private float emptyNodeFetchFactor = DEFAULT_EMPTY_NODE_FETCH_FACTOR;

    public int getTimestampBits() {
        return timestampBits;
    }

    public void setTimestampBits(int timestampBits) {
        this.timestampBits = timestampBits;
    }

    public int getServerNodeSequenceBits() {
        return serverNodeSequenceBits;
    }

    public void setServerNodeSequenceBits(int serverNodeSequenceBits) {
        this.serverNodeSequenceBits = serverNodeSequenceBits;
    }

    public int getIncrementBits() {
        return incrementBits;
    }

    public void setIncrementBits(int incrementBits) {
        this.incrementBits = incrementBits;
    }

    public long getTimestampOffset() {
        return timestampOffset;
    }

    public void setTimestampOffset(long timestampOffset) {
        this.timestampOffset = timestampOffset;
    }

    public float getEmptyNodeFetchFactor() {
        return emptyNodeFetchFactor;
    }

    public void setEmptyNodeFetchFactor(float emptyNodeFetchFactor) {
        this.emptyNodeFetchFactor = emptyNodeFetchFactor;
    }
}
