package com.froad.enums;

public enum ProcessNodeType {
	node_start("0","开始节点"),
	node_end("1","结束节点"),
    node_execute("2","任务节点")
    ;
    
    
    private String code;
    
    private String describe;
    
    private ProcessNodeType(String code,String describe){
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
    public static ProcessNodeType getType(String code){
    	for(ProcessNodeType auditState : ProcessNodeType.values()){
    		if(auditState.getCode().equals(code)){
    			return auditState;
    		}
    	}
    	return null;
	}
}
