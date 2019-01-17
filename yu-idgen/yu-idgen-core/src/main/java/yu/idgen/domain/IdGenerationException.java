package yu.idgen.domain;

/**
 *
 */
public class IdGenerationException extends RuntimeException {

	public IdGenerationException() {
		super();
	}

	public IdGenerationException(String msg) {
		super(msg);
	}

	public IdGenerationException(String msg, Throwable cause) {
		super(msg, cause);
	}

	public IdGenerationException(Throwable cause) {
		super(cause);
	}
	
}
