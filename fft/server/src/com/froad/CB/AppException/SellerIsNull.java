package com.froad.CB.AppException;

public class SellerIsNull  extends Exception{

	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = -4273651821562226821L;

    public SellerIsNull(String message) {
        super(message);
    }

    public SellerIsNull(String message, Throwable cause) {
        super(message, cause);
    }

    public SellerIsNull(Throwable cause) {
        super(cause);
    }
}
