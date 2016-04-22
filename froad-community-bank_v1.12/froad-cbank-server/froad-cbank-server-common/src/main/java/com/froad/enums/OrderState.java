package com.froad.enums;


/**
 * 数据状态
 */
public enum OrderState {

	NORMAL("1","正常"),
	RETURNED("2","库存已退"),
	SYSTEM_CLOSED("3","系统关闭");
	
	private String code;
	
	private String description;
	
	private OrderState(String code,String description){
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
    public static OrderState getType(String code){
    	for(OrderState type : OrderState.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
}
