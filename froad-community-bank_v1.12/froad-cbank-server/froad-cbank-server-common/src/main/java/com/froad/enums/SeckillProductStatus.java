package com.froad.enums;

/**
 * 秒杀商品状态
 * 
 * @author wangzhangxu
 * @date 2015年5月3日 下午6:55:42
 * @version v1.0
 */
public enum SeckillProductStatus {
	
    ing("1","正在秒杀"),
    soon("2","即将秒杀"),
    soldout("3","秒杀售罄"),
    expired("4","秒杀过期"),
    ing_and_soon("5","正在秒杀和即将秒杀");
    
    
    private String code;
    
    private String describe;
    
    private SeckillProductStatus(String code,String describe){
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
    public static SeckillProductStatus getType(String code){
    	for(SeckillProductStatus type : SeckillProductStatus.values()){
    		if(type.getCode().equals(code)){
    			return type;
    		}
    	}
    	return null;
	}
    
}
