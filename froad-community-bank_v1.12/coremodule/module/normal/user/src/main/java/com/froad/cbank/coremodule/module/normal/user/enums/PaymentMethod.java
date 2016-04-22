
package com.froad.cbank.coremodule.module.normal.user.enums;


/**
 * 支付方式
 */
public enum PaymentMethod {

    /**
     * 现金
     */
    cash("1", "现金支付"),

    /**
     * 方付通积分支付
     */
    froadPoints("2", "联盟积分支付"),

    /**
     * 银行积分支付 
     */
    bankPoints("3", "银行积分支付"),

    /**
     * 方付通积分+现金支付
     */
    froadPointsAndCash("4", "联盟积分+现金支付"),

    /**
     * 银行积分+现金支付
     */
    bankPointsAndCash("5", "银行积分+现金支付"),
    
    /**
     * 赠送积分
     */
    creditPoints("6", "赠送积分"),
    
    /**
     * 无效值
     */
    invalid("", "无效值");

    private String code;

    private String describe;

    private PaymentMethod(String code, String describe) {
        this.code = code;
        this.describe = describe;
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

    @Override
    public String toString() {
        return this.code;
    }
    
    public static PaymentMethod getPaymentMethod(String code){
    	for(PaymentMethod p : PaymentMethod.values()){
    		if(p.getCode().equals(code)){
    			return p;
    		}
    	}
    	return null;
    }

}
