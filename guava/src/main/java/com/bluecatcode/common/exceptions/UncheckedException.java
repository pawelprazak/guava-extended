package com.bluecatcode.common.exceptions;

/**
 * Base UncheckedException exception with a clear name
 * <p>
 * Intended for use with custom exceptions for clear class hierarchy
 *
 * @see RuntimeException
 * @since 1.0.4
 */
public class UncheckedException extends RuntimeException {

    static final long serialVersionUID = -0L;

    /**
     * @see RuntimeException#RuntimeException()
     */
    public UncheckedException() {
        /* Empty */
    }

    /**
     * @see RuntimeException#RuntimeException(String)
     */
    public UncheckedException(String message) {
        super(message);
    }

    /**
     * @see RuntimeException#RuntimeException(String, Throwable)
     */
    public UncheckedException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * @see RuntimeException#RuntimeException(Throwable)
     */
    public UncheckedException(Throwable cause) {
        super(cause);
    }

}
