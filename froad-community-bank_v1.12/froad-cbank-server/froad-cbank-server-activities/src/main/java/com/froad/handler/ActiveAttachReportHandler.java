package com.froad.handler;


public interface ActiveAttachReportHandler {
	/**
	 * @Title: generateActiveAttachReport
	 * @Description: 生成活动报表附表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activeId，clientId， mainReportDir
	 * @return
	*/
	public String generateActiveAttachReport(String activeId, String clientId, String mainReportDir, String activeType);
}
