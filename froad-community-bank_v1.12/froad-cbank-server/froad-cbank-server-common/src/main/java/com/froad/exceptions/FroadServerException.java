package com.froad.exceptions;

/**
 * 异常
 * @ClassName: FroadServerException 
 * @Description: TODO 
 * @author FQ 
 * @date 2015年3月13日 下午1:37:59 
 *
 */
public class FroadServerException extends RuntimeException {

	private static final long serialVersionUID = -2946266495682282677L;

	  public FroadServerException(String message) {
	    super(message);
	  }

	  public FroadServerException(Throwable e) {
	    super(e);
	  }

	  public FroadServerException(String message, Throwable cause) {
	    super(message, cause);
	  }
}
