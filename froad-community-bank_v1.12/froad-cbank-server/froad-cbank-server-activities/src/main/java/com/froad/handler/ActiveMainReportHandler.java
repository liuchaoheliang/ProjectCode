package com.froad.handler;


public interface ActiveMainReportHandler {
	/**
	 * @Title: getActiveMainReportURL
	 * @Description: 生成活动报表主表URL
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activeId，clientId， type
	 * @return
	*/	
	public String getActiveMainReportURL(String activeId, String clientId, String type, String activeType);
}
