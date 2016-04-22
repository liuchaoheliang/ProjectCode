package com.froad.db.mysql.rp_mappers;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportBankOrg;
import com.froad.po.ReportUserTrans;

public interface ReportUserTransMapper {

	
	Boolean addByBatch(@Param("trans")List<ReportUserTrans> trans);

	
	List<ReportUserTrans> selectConsumeTradeType(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("bankOrg")ReportBankOrg bankOrg, @Param("flag")Boolean flag);
	
	
	List<ReportUserTrans> selectUserTradeDetailList(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("bankOrg")ReportBankOrg bankOrg, @Param("flag")Boolean flag);
	
	
	List<ReportUserTrans> selectUserTradeInfoList(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("bankOrg")ReportBankOrg bankOrg, @Param("flag")Boolean flag);
	
	
}
