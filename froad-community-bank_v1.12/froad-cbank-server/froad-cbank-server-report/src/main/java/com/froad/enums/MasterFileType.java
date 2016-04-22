package com.froad.enums;

/**
 * Batch cycle status
 *
 */
public enum MasterFileType {

    source('S',"源文件"),
    report('R',"报表文件"),
    ;
    
    private char code;
    
    private String description;
    
    private MasterFileType(char code,String description){
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
