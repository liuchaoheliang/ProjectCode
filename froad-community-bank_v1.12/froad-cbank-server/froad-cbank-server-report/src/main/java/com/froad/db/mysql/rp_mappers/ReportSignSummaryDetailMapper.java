package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportBankUser;
import com.froad.po.ReportSignSummaryDetail;

public interface ReportSignSummaryDetailMapper {
	
	Boolean addByBatch(@Param("details")List<ReportSignSummaryDetail> details);
	
	
	List<ReportSignSummaryDetail> selectMerchantIncrCount(@Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	/**
	 * 查询签约人新增商户排行
	 * @Title: selectContractAddRank 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月4日
	 * @modify: froad-huangyihao 2015年6月4日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @return
	 * @throws
	 */
	List<ReportSignSummaryDetail> selectContractAddRank(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
}
