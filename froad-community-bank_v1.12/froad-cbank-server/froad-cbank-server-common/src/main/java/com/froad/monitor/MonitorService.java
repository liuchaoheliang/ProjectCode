package com.froad.monitor;

import com.froad.enums.MonitorPointEnum;

/**
 * 业务监控
 * @ClassName: MonitorService 
 * @Description: TODO 
 * @author FQ 
 * @date 2015年3月18日 上午10:11:02 
 *
 */
public interface MonitorService {
	
	/**
	 * 
	 * @Description:TODO
	 * @param businame 【必填】 业务名称唯一标示符，从“接入申请系统”获得
	 * @param attrId 【必填】 业务监控项唯一标示符，从“接入申请系统”获得
	 * @param value 【必填】 要上报的监控项的值
	 * @param type  (int|float|string)默认为数值型（int|float）  value的值类型;如果是数值型则不必传入此参数
	 * @param unionAttrId “汇聚上报时”必传，特殊用途的attr_id，从“接入申请系统”获得
	 * void
	 * 
	 * @author: FQ
	 * @date:2015年3月18日 上午10:29:57
	 */
	public void send(String businame,String attrId,String value,String type,String unionAttrId);
	
	/**
	 * 发送监控数据
	 * 
	 * @param point
	 * @author: FQ
	 * @date:2015年3月18日 上午10:29:57
	 */
	public void send(MonitorPointEnum point);
	
	/**
	 * 发送监控数据
	 * 
	 * @param point
	 * @param value 覆盖枚举的中value
	 * @author: FQ
	 * @date:2015年3月18日 上午10:29:57
	 */
	public void send(MonitorPointEnum point, String value);
	
	
}
