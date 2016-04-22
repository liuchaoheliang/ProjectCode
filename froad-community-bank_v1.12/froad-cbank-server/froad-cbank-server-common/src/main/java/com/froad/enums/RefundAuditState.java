package com.froad.enums;
/**
 * 退款审核状态
 */
public enum RefundAuditState {
    
    noAudit("0","待审核"),
    passAudit("1","审核通过"),
    failAudit("2","审核未通过");
    
    private String code;
    
    private String describe;
    
    private RefundAuditState(String code,String describe){
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
