package com.froad.CB.AppException;

public class StateNotExists extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public StateNotExists(String message) {
        super(message);
    }

    public StateNotExists(String message, Throwable cause) {
        super(message, cause);
    }

    public StateNotExists(Throwable cause) {
        super(cause);
    }
}
