
package com.froad.enums;

import com.froad.util.Checker;

/**
 * 支付方式
 */
public enum PaymentMethod {

    /**
     * 现金
     */
    cash("1", "现金支付", "现金"),

    /**
     * 方付通积分支付
     */
    froadPoints("2", "联盟积分支付", "联盟积分"),

    /**
     * 银行积分支付 
     */
    bankPoints("3", "银行积分支付", "银行积分"),

    /**
     * 方付通积分+现金支付
     */
    froadPointsAndCash("4", "联盟积分+现金支付", "现金+联盟积分"),

    /**
     * 银行积分+现金支付
     */
    bankPointsAndCash("5", "银行积分+现金支付", "现金+银行积分"),
    
    /**
     * 赠送积分
     */
    creditPoints("6", "赠送积分", "赠送积分"),
    
    /**
     * 无效值
     */
    invalid("", "无效值", "无效值");

    private String code;

    private String describe;
    
    private String bossDescribe;

    private PaymentMethod(String code, String describe, String bossDescribe) {
        this.code = code;
        this.describe = describe;
        this.bossDescribe = bossDescribe;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getBossDescribe() {
		return bossDescribe;
	}

	public void setBossDescribe(String bossDescribe) {
		this.bossDescribe = bossDescribe;
	}

	@Override
    public String toString() {
        return this.code;
    }

    public static PaymentMethod getByCode(String code){
    	if(Checker.isNotEmpty(code)){
    		for(PaymentMethod p : values()){
    			if(p.getCode().equals(code)){
    				return p;
    			}
    		}
    	}
    	return null;
    }
    
}
