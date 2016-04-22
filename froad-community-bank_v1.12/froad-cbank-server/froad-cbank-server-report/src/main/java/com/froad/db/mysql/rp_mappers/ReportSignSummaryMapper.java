package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportBankUser;
import com.froad.po.ReportSignSummary;

public interface ReportSignSummaryMapper {
	
	Boolean addByBatch(@Param("summarys")List<ReportSignSummary> summarys);
	
	
	List<ReportSignSummary> findByDate(@Param("startDate")Date startDate, @Param("endDate")Date endDate);
	
	Date findSummaryMaxDate();
	
	List<ReportSignSummary> getTotalMechantsBySignUser(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	/**
	 * 查询签约人结余商户排行
	 * @Title: selectContractRank 
	 * @Description: 
	 * @author: froad-huangyihao 2015年6月4日
	 * @modify: froad-huangyihao 2015年6月4日
	 * @param begDate
	 * @param endDate
	 * @return
	 * @throws
	 */
	List<ReportSignSummary> selectContractRank(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	
	/**
	 * 根据机构号查询该机构下所有的商户总数
	 * @Title: selectTotalMerchantsByOrg 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月3日
	 * @modify: froad-huangyihao 2015年6月3日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @return
	 * @throws
	 */
	Integer selectTotalMerchantsByOrg(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	
	List<ReportSignSummary> getTotalMerchantsByOrg(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
}
