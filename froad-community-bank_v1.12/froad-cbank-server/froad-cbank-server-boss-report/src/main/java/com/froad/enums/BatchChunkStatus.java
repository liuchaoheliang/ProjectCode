package com.froad.enums;

/**
 * Batch job status
 *
 */
public enum BatchChunkStatus {

    init('0',"初始化"),
    running('1',"进行中"),
    success('2',"完成"),
    failed('3',"失败"),
    ;
    
    private char code;
    
    private String description;
    
    private BatchChunkStatus(char code,String description){
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
