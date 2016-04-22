package com.froad.common.enums;

public enum ProductTempAuditState {

	noAudit("0","审核中"),
    passAudit("1","审核通过"),
    failAudit("2","审核不通过"),
    noCommit("3","未提交"),
    passAuditUpdateing("4","审核通过(更新中)"),
    passAuditFail("5","审核通过(更新审核未通过)"),
    passAuditNoCommit("6","审核通过(更新未提交)")
    ;
    
    private String code;
    
    private String describe;
    
    private ProductTempAuditState(String code,String describe){
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
    public static ProductTempAuditState getType(String code){
    	for(ProductTempAuditState auditState : ProductTempAuditState.values()){
    		if(auditState.getCode().equals(code)){
    			return auditState;
    		}
    	}
    	return null;
	}
    
}
