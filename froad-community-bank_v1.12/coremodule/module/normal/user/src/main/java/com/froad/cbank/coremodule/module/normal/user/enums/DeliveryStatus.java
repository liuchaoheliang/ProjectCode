package com.froad.cbank.coremodule.module.normal.user.enums;

/**
 * 物流状态
 * @author yfy
 *
 */
public enum DeliveryStatus {

	in_transit("0","在途"),
	pick_up("1","揽件"),
	exception("2","疑难"),
	sign_in("3","签收"),
	sign_out("4","退签"),
	delivery("5","派件"),
	send_back("5","退回");
	
	private String code;
	
	private String describe;

	private DeliveryStatus(String code,String describe){
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
	public static DeliveryStatus getDeliveryStatus(String code){
		for(DeliveryStatus ds : DeliveryStatus.values()){
			if(ds.code.equals(code)){
				return ds;
			}
		}
		return null;
	}
	
	@Override
    public String toString() {
        return this.code;
    }
	
}
