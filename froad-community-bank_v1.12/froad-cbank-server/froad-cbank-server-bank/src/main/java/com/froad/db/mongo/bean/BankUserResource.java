package com.froad.db.mongo.bean;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;
import com.froad.util.JSonUtil;

/**
 * 
 * @ClassName: BankUserResource 
 * @Description: 用户资源mongo类
 * @author: huangyihao
 * @date: 2015年3月25日
 */
public class BankUserResource{
	

	private String id;		//clientId+roleId
	private List<ResourcesInfo> resources;	// 资源集合
	
	
	@JSONField(name="_id")
	public String getId() {
		return id;
	}
	
	@JSONField(name="_id")
	public void setId(String id) {
		this.id = id;
	}
	
	@JSONField(name="resources")
	public List<ResourcesInfo> getResources() {
		return resources;
	}
	
	@JSONField(name="resources")
	public void setResources(List<ResourcesInfo> resources) {
		this.resources = resources;
	}

	@Override
	public String toString() {
		return JSonUtil.toJSonString(this);
	}
	
	
}
