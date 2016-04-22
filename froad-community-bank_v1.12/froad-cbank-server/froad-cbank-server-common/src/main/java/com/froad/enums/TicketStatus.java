package com.froad.enums;

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
    init_refunded("8","未发码退款"),
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

	public static TicketStatus getTicketStatus(String code) {
		if (code == null)
			return null;
		TicketStatus[] es = TicketStatus.values();
		for (TicketStatus ticketStatus : es) {
			if (ticketStatus.getCode().equals(code))
				return ticketStatus;
		}
		return null;
	}
	
    public static String getDescription(String code){
    	TicketStatus status = getTicketStatus(code);
		return status == null ? null : status.getDescribe();
//    	String msg = null;
//    	if (code.equals(init.getCode().toString())){
//    		msg = init.getDescribe();
//    	} else if (code.equals(sent.getCode().toString())) {
//    		msg = sent.getDescribe();
//    	} else if (code.equals(consumed.getCode().toString())) {
//    		msg = consumed.getDescribe();
//    	} else if (code.equals(expired.getCode().toString())) {
//    		msg = expired.getDescribe();
//    	} else if (code.equals(refunded.getCode().toString())) {
//    		msg = refunded.getDescribe();
//    	} else if (code.equals(refunding.getCode().toString())) {
//    		msg = refunding.getDescribe();
//    	}
    	
//    	return msg;
    }
    
    @Override
    public String toString() {
        return this.code;
    }
    
}
