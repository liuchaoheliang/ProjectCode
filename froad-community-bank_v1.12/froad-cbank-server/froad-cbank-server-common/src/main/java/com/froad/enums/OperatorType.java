package com.froad.enums;


/**
 * 数据状态
 */
public enum OperatorType {

	PLATFORM("1","平台用户"),
	BANK("2","银行用户");
	
	private String code;
	
	private String description;
	
	private OperatorType(String code,String description){
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
    public static OperatorType getType(String code){
    	for(OperatorType type : OperatorType.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
}
