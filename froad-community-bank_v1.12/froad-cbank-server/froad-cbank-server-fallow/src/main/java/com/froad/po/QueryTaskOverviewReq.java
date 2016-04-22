package com.froad.po;
/**
 * 
 * ClassName: 审核概要信息请求参数
 * Function: TODO ADD FUNCTION
 * date: 2015年10月22日 上午10:46:18
 *
 * @author wm
 * @version
 */
public class QueryTaskOverviewReq {
	
	private Origin origin;
	public String instanceId;

	public Origin getOrigin() {
		return origin;
	}

	public void setOrigin(Origin origin) {
		this.origin = origin;
	}

	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	} 

}
