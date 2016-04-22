/**
 * 
 */
package com.froad.enums;


/**
 * 订单来源
 */
public enum ReportOrderType {
	FACE_TO_FACE("0", "面对面订单"), 
	GROUP("1", "团购订单"), 
	undefined("","未知")
	;
	
	
	private String code;
	
	private String describe;
	
	private ReportOrderType(String code,String describe){
		this.code=code;
		this.describe=describe;
	}
	
	public static  ReportOrderType getReportOrderType(String code){
		for(ReportOrderType t:ReportOrderType.values()){
			if(t.getCode().equals(code)){
				return t;
			}
		}
		return undefined;
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
}
