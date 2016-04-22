/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:PlatTypePo.java
 * Package Name:com.froad.test.po
 * Date:2015年10月16日下午2:28:51
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.enums;


/**
 * ClassName:PlatTypePo
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月16日 下午2:28:51
 * @author   vania
 * @version  
 * @see 	 
 */
public enum PlatTypeEnum {

	  /**
	   * boss
	   */
	  boss(1),
	  /**
	   * 银行端
	   */
	  bank(2),
	  /**
	   * 商户pc
	   */
	  merchant_pc(3),
	  /**
	   * 商户h5
	   */
	  merchant_h5(4),
	  /**
	   * 个人pc
	   */
	  personal_pc(5),
	  /**
	   * 个人h5
	   */
	  personal_h5(6),
	  /**
	   * 自动任务
	   */
	  auto_task(7);

	  private final int value;

	  private PlatTypeEnum(int value) {
	    this.value = value;
	  }

	  /**
	   * Get the integer value of this enum value, as defined in the Thrift IDL.
	   */
	  public int getValue() {
	    return value;
	  }

	  /**
	   * Find a the enum type by its integer value, as defined in the Thrift IDL.
	   * @return null if the value is not found.
	   */
	  public static PlatTypeEnum findByValue(int value) { 
	    switch (value) {
	      case 1:
	        return boss;
	      case 2:
	        return bank;
	      case 3:
	        return merchant_pc;
	      case 4:
	        return merchant_h5;
	      case 5:
	        return personal_pc;
	      case 6:
	        return personal_h5;
	      case 7:
	        return auto_task;
	      default:
	        return null;
	    }
	  }
	
}
