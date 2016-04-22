package com.froad.enums;

/**
 * 营销平台活动状态
 * @author yefeifei
 *
 */
public enum ActiveStatus {
    
	pending("0","待提交"),
    audit("1","审核中"),
    auditFail("2","审核不通过"),
    launch("3","启用"),
    forbidden("4","禁用");
	
    private String code;
    
    private String describe;
    
    private ActiveStatus(String code,String describe){
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
    public static ActiveStatus getType(String code){
        for(ActiveStatus type : ActiveStatus.values()){
            if(type.getCode().equals(code)){
                return type;
            }
        }
        return null;
    }

}
