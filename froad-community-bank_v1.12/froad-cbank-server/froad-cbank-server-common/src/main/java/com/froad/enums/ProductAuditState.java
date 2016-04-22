package com.froad.enums;

public enum ProductAuditState {
    
    noAudit("0","待审核"),
    passAudit("1","审核通过"),
    failAudit("2","审核未通过"),
    noCommit("3","未提交"),
    waitSynchAudit("4","审核通过待同步")
    ;
    
    
    private String code;
    
    private String describe;
    
    private ProductAuditState(String code,String describe){
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
    public static ProductAuditState getType(String code){
    	for(ProductAuditState auditState : ProductAuditState.values()){
    		if(auditState.getCode().equals(code)){
    			return auditState;
    		}
    	}
    	return null;
	}

}
