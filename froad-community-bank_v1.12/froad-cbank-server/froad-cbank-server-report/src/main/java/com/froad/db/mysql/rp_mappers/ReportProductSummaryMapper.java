package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportProductSummary;

public interface ReportProductSummaryMapper {
	
	public Boolean addSummary(ReportProductSummary summary);
	
	public Boolean addSummaryByBatch(@Param("items")List<ReportProductSummary> summarys);
	
	public List<ReportProductSummary> findByDate(@Param("clientId") String clientId, @Param("queryDate") Integer queryDate);
}
