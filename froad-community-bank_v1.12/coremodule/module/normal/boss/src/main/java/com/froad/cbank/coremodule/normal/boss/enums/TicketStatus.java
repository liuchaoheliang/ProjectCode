package com.froad.cbank.coremodule.normal.boss.enums;

/**
 * 券状态
 * @author lyj
 *
 */
public enum TicketStatus {

    init("0","待发送"),
    sent("1","已发送"),
    consumed("2","已消费"),
    expired("3","已过期"),
    refunded("4","已退款"),
    refunding("5","退款中"),
    refund_failed("6","退款失败"),
    expired_refunded("7","已过期退款"),
    init_refunded("8","未发码退款中"),
    expired_refunding("9","已过期退款中"),
    ;
    
    private String code;
    
    private String describe;
    
    private TicketStatus(String code,String describe){
        this.code=code;
        this.describe=describe;
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
    
    public static String getDescriptionByCode(String code){
		for(TicketStatus e : TicketStatus.values()){
			if(e.getCode().equals(code)){
				return e.getDescribe();
			}
		}
		return "";
	}
    
    @Override
    public String toString() {
        return this.code;
    }
    
}
