package com.froad.CB.AppException;

public class StateIsNull extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public StateIsNull(String message) {
        super(message);
    }

    public StateIsNull(String message, Throwable cause) {
        super(message, cause);
    }

    public StateIsNull(Throwable cause) {
        super(cause);
    }
}
