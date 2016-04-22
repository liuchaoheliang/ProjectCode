package com.froad.AppException;

public class PriceError extends Exception {
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public PriceError(String message) {
        super(message);
    }

    public PriceError(String message, Throwable cause) {
        super(message, cause);
    }

    public PriceError(Throwable cause) {
        super(cause);
    }
}
