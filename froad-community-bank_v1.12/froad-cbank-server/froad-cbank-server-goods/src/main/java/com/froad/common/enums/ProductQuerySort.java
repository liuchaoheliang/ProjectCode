package com.froad.common.enums;

public enum ProductQuerySort {

	/**
	 * 综合排序 精品商城个人H5用到
	 * 推荐优先  精品商城个人H5用到 我的VIP页面里的为您推荐用到
	 */
	sellCount("sellCount","销量排序"),
	distance("distance","距离排序"),
	price("price","价格排序"),
	disCount("disCount","折扣排序"),
	recommend("recommend","推荐优先"),
	hensive("hensive","综合排序");
	
	private String code;
    
    private String describe;
	
    private ProductQuerySort(String code,String describe){
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
    public static ProductQuerySort getType(String code){
    	for(ProductQuerySort sort : ProductQuerySort.values()){
    		if(sort.getCode().equals(code)){
    			return sort;
    		}
    	}
    	return null;
	}

}
