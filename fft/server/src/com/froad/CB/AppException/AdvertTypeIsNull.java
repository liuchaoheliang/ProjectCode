package com.froad.CB.AppException;

public class AdvertTypeIsNull extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public AdvertTypeIsNull(String message) {
        super(message);
    }

    public AdvertTypeIsNull(String message, Throwable cause) {
        super(message, cause);
    }

    public AdvertTypeIsNull(Throwable cause) {
        super(cause);
    }
}