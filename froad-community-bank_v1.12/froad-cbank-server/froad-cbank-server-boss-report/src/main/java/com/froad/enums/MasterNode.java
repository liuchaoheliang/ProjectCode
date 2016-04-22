package com.froad.enums;

/**
 * Batch cycle status
 *
 */
public enum MasterNode {

    master('M',"主节点"),
    slave('S',"从节点"),
    ;
    
    private char code;
    
    private String description;
    
    private MasterNode(char code,String description){
        this.code=code;
        this.description=description;
    }

    public char getCode() {
        return code;
    }

    public void setCode(char code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
    @Override
    public String toString() {
        return String.valueOf(this.code);
    }
    
}
