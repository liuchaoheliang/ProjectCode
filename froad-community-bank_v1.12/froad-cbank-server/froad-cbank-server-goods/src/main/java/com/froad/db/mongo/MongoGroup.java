package com.froad.db.mongo;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * mongo group 产生的对象
 * @author wangyan
 *
 */
public class MongoGroup {

	private String id;
	private Integer count;
	
	@JSONField(name="_id")
    public String getId() {
        return id;
    }
	
	@JSONField(name="_id")
    public void setId(String id) {
        this.id = id;
    }
	
	@JSONField(name="count")
	public Integer getCount() {
		return count;
	}

	@JSONField(name="count")
	public void setCount(Integer count) {
		this.count = count;
	}
    
}
