package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportBankUser;
import com.froad.po.ReportSignTypeSummary;

public interface ReportSignTypeSummaryMapper {
	
	Boolean addByBatch(@Param("typeSummarys")List<ReportSignTypeSummary> typeSummarys);
	
	
	List<ReportSignTypeSummary> selectMerchantTypeCount(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
	
	Date findTypeSummaryMaxDate();
	
	/**
	 * 根据类型查询机构下的商户总数
	 * @Title: selectMerchantCount
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月3日
	 * @modify: froad-huangyihao 2015年6月3日
	 * @param begDate
	 * @param endDate
	 * @param user
	 * @param type
	 * @return
	 * @throws
	 */
	List<ReportSignTypeSummary> selectMerchantCount(@Param("begDate")Date begDate, @Param("endDate")Date endDate, @Param("user")ReportBankUser user, @Param("flag")Boolean flag);
}
