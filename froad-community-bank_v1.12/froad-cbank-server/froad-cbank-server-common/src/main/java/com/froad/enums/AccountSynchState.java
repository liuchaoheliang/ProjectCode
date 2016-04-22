package com.froad.enums;

public enum AccountSynchState {
	init("0","初始化"),
    synchSucc("1","同步成功"),
    synchFail("2","同步失败")
    ;
    
    
    private String code;
    
    private String describe;
    
    private AccountSynchState(String code,String describe){
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
    
    @Override
    public String toString() {
        return this.code;
    }
    
    /**
     * 通过code取得类型
     * @param code
     * @return
     */
    public static AccountSynchState getType(String code){
    	for(AccountSynchState auditState : AccountSynchState.values()){
    		if(auditState.getCode().equals(code)){
    			return auditState;
    		}
    	}
    	return null;
	}
}
