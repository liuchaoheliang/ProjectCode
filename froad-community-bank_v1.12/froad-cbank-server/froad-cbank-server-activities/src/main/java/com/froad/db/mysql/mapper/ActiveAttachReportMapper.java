package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ActiveAttachReport;

public interface ActiveAttachReportMapper {
	/**
	 * @Title: getActiveAttachReport
	 * @Description: 获取活动交易附表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activeId,clientId
	 * @return
	*/	
	public List<ActiveAttachReport> getActiveAttachReport(@Param("activeId")String activeId,@Param("clientId") String clientId);
}
