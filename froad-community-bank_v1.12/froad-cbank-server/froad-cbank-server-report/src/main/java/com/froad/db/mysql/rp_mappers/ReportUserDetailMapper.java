package com.froad.db.mysql.rp_mappers;

import java.sql.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ReportBankOrg;
import com.froad.po.ReportUserDetail;

public interface ReportUserDetailMapper {
	
	Boolean addByBatch(@Param("details")List<ReportUserDetail> details);
	
	Long getTotalNewUsers(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("bankOrg")ReportBankOrg bankOrg, @Param("flag")Boolean flag);
	
	List<ReportUserDetail> selectUserSummaryList(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("bankOrg")ReportBankOrg bankOrg, @Param("flag")Boolean flag);
	
	List<ReportUserDetail> selectUserSummaryListByPage(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("bankOrg")ReportBankOrg bankJgm, Page page, @Param("flag")Boolean flag);
}
