package com.froad.fft.support;

import com.froad.fft.persistent.entity.ProductGroup;
import com.froad.fft.persistent.entity.ProductPresell;


	/**
	 * 类描述：团购属性适配
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年3月29日 下午4:34:32 
	 */
public class GroupAdapter {

	public static GroupCommonBean adapt(ProductGroup group){
		if(group==null){
			return null;
		}
		GroupCommonBean common=new GroupCommonBean();
		common.setPerNumber(group.getPerNumber());
		common.setPerMinNumber(group.getPerminNumber());
		common.setStartTime(group.getStartTime());
		common.setEndTime(group.getEndTime());
		return common;
	}
	
	public static GroupCommonBean adapt(ProductPresell presell){
		if(presell==null){
			return null;
		}
		GroupCommonBean common=new GroupCommonBean();
		common.setPerNumber(presell.getPerNumber());
		common.setPerMinNumber(presell.getPerminNumber());
		common.setStartTime(presell.getStartTime());
		common.setEndTime(presell.getEndTime());
		return common;
	}
}
