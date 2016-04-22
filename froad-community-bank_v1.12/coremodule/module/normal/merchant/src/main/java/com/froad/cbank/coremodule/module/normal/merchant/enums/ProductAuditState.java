package com.froad.cbank.coremodule.module.normal.merchant.enums;

public enum ProductAuditState {
    
    noAudit("0","待审核"),
    passAudit("1","审核通过"),
    failAudit("2","审核未通过"),
    noCommit("3","未提交")
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

}
