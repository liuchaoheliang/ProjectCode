package com.froad.enums;

/**
 * 执行类型:0-用户,1-岗位,2-部门,3-用户组,4-机构级别
 * ClassName: ProcessNodeRunnerFlag
 * Function: TODO ADD FUNCTION
 * date: 2015年10月29日 下午3:46:00
 *
 * @author vania
 * @version
 */
public enum ProcessNodeRunnerFlag {
	user("0","用户"),
	post("1","岗位"),
	depart("2","部门"),
	usergroup("2","用户组"),
	orglevel("4","机构级别")
    ;
    
    
    private String code;
    
    private String describe;
    
    private ProcessNodeRunnerFlag(String code,String describe){
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
    public static ProcessNodeRunnerFlag getType(String code){
    	for(ProcessNodeRunnerFlag auditState : ProcessNodeRunnerFlag.values()){
    		if(auditState.getCode().equals(code)){
    			return auditState;
    		}
    	}
    	return null;
	}
}
