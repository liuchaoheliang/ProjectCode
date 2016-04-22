package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ActiveMainReport;

public interface ActiveMainReportMapper {
	/**
	 * @Title: getActiveMainReport
	 * @Description: 生成活动交易主表
	 * @author: yefeifei 2015年11月20日
	 * @modify: yefeifei 2015年11月20日
	 * @param activeId，clientId，type
	 * @return
	*/
	public List<ActiveMainReport> getActiveMainReport(@Param("activeId")String activeId,@Param("clientId") String clientId, @Param("Type") String type);
}
