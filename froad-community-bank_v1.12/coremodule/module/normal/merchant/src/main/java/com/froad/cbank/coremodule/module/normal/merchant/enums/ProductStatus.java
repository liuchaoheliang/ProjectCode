package com.froad.cbank.coremodule.module.normal.merchant.enums;

/**
 * 商品状态
 * @author wangyan
 *
 */
public enum ProductStatus {
    
    noShelf("0","未上架"),
    onShelf("1","已上架"),
    offShelf("2","已下架"),
    isDeleted("3","已删除");
    
    private String code;
    
    private String describe;
    
    private ProductStatus(String code,String describe){
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


}
