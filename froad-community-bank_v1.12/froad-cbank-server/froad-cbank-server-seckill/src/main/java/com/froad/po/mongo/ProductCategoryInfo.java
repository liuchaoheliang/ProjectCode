package com.froad.po.mongo;

import java.io.Serializable;

import com.alibaba.fastjson.annotation.JSONField;

/**
 *  商品分类信息
  * @ClassName: ProductCategoryInfo
  * @Description: TODO
  * @author share 2015年3月21日
  * @modify share 2015年3月21日
 */
public class ProductCategoryInfo implements Serializable{
    
	/**
     * 
     */
    private static final long serialVersionUID = -5847823229141403769L;
    
    private long productCategoryId;

    @JSONField(name = "product_category_id")
    public long getProductCategoryId() {
        return productCategoryId;
    }

    @JSONField(name = "product_category_id")
    public void setProductCategoryId(long productCategoryId) {
        this.productCategoryId = productCategoryId;
    }

	
}
