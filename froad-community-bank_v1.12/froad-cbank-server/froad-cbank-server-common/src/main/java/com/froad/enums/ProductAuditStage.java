package com.froad.enums;

public enum ProductAuditStage {
    
    firstAudit("0","初审"),
    secondAudit("1","复审");
    
    private String code;
    
    private String describe;
    
    private ProductAuditStage(String code,String describe){
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
