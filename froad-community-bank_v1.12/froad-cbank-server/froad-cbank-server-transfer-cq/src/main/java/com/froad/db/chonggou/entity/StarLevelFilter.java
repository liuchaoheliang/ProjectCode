package com.froad.db.chonggou.entity;

public enum StarLevelFilter {
    
    sl("00","全部"),
    slle5("05","5星以下"),
    slle4("04","4星以下"),
    slle3("03","3星以下"),
    slle2("02","2星以下"),
    slle1("01","1星以下"),
    sl5("5","5星"),
    sl4("4","4星"),
    sl3("3","3星"),
    sl2("2","2星"),
    sl1("1","1星");
    
    private String code;
    
    private String describe;
    
    private StarLevelFilter(String code,String describe){
        this.code=code;
        this.describe=describe;
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
