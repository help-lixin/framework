package help.lixin.framework.config.exception;

public class UnknownClassException extends RuntimeException {
	private static final long serialVersionUID = 5958690743462019863L;

	/**
	 * Creates a new UnknownClassException.
	 */
	public UnknownClassException() {
		super();
	}

	/**
	 * Constructs a new UnknownClassException.
	 *
	 * @param message
	 *            the reason for the exception
	 */
	public UnknownClassException(String message) {
		super(message);
	}

	/**
	 * Constructs a new UnknownClassException.
	 *
	 * @param cause
	 *            the underlying Throwable that caused this exception to be
	 *            thrown.
	 */
	public UnknownClassException(Throwable cause) {
		super(cause);
	}

	/**
	 * Constructs a new UnknownClassException.
	 *
	 * @param message
	 *            the reason for the exception
	 * @param cause
	 *            the underlying Throwable that caused this exception to be
	 *            thrown.
	 */
	public UnknownClassException(String message, Throwable cause) {
		super(message, cause);
	}

}
