package com.froad.enums;

public enum AccountAuditState {
	init("0","初始化"),
	ingAudit("1","审核中"),
    passAudit("2","审核通过"),
    failAudit("3","审核不通过")
    ;
    
    
    private String code;
    
    private String describe;
    
    private AccountAuditState(String code,String describe){
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
    public static AccountAuditState getType(String code){
    	for(AccountAuditState auditState : AccountAuditState.values()){
    		if(auditState.getCode().equals(code)){
    			return auditState;
    		}
    	}
    	return null;
	}
}
