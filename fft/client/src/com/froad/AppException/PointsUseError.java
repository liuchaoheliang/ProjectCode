package com.froad.AppException;

public class PointsUseError  extends Exception  {
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public PointsUseError(String message) {
        super(message);
    }

    public PointsUseError(String message, Throwable cause) {
        super(message, cause);
    }

    public PointsUseError(Throwable cause) {
        super(cause);
    }
}
