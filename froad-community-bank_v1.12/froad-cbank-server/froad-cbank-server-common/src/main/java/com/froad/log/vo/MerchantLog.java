package com.froad.log.vo;
/**
 * 类描述：商户日志实体类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-11-3上午11:05:28 
 */
public class MerchantLog  extends Head{
	private MerchantDetailLog data;

	public MerchantDetailLog getData() {
		return data;
	}

	public void setData(MerchantDetailLog data) {
		this.data = data;
	}

	
}
