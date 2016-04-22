package com.froad.enums;

/**
 * 秒杀标识
 * @author wangzhangxu
 *
 */
public enum SeckillFlagEnum {
    
    notSeckill("0","非秒杀"),
    IsSeckill1("1","秒杀已上架"),
    IsSeckill2("2","秒杀未上架");
    
    private String code;
    
    private String describe;
    
    private SeckillFlagEnum(String code,String describe){
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
    public static SeckillFlagEnum getType(String code){
    	for(SeckillFlagEnum type : SeckillFlagEnum.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}

}
