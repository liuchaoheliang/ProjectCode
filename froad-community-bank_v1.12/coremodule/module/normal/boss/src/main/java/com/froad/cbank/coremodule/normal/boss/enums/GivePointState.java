package com.froad.cbank.coremodule.normal.boss.enums;


/**
 * 积分赠送状态
 */
public enum GivePointState {

	GIVE_FAIL("0","赠送失败"),
	GIVE_SUCCESS("1","赠送成功"),
	NO_GIVE("2","未赠送"),
	REFUND_FAIL("3","退分失败"),
	REFUND_SUCCESS("4","退分成功");
	
	private String code;
	
	private String description;
	
	private GivePointState(String code,String description){
		this.code=code;
		this.description=description;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
    public String toString() {
        return this.code;
    }
	
	/**
     * 通过code取得类型
     * @param code
     * @return
     */
    public static GivePointState getType(String code){
    	for(GivePointState type : GivePointState.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
    
    public static String getDescriptionByCode(String code){
		for(GivePointState e : GivePointState.values()){
			if(e.getCode().equals(code)){
				return e.getDescription();
			}
		}
		return "";
	}
}
