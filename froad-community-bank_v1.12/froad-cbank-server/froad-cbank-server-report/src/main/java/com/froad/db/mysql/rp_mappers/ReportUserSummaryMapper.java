package com.froad.db.mysql.rp_mappers;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportBankOrg;
import com.froad.po.ReportUserSummary;

public interface ReportUserSummaryMapper {
	
	Boolean addByBatch(@Param("summarys")List<ReportUserSummary> summarys);
	
	List<ReportUserSummary> selectMerchantTrend(@Param("bankOrg")ReportBankOrg bankOrg, @Param("flag")Boolean flag);
	
	List<ReportUserSummary> findTotalUserByCardBin(@Param("date")Date date, @Param("bankOrg")ReportBankOrg bankOrg, @Param("flag")Boolean flag);
	
}
