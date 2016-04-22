package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ReportMerchantProduct;

public interface ReportMerchantProductMapper {
		
	public Boolean addReportMerchantProduct(ReportMerchantProduct reportMerchantProduct);
	
	public Boolean addByBatch(@Param("reportMerchantProducts")List<ReportMerchantProduct> reportMerchantProducts);
	
	public List<ReportMerchantProduct> findReportMerchantProductByPage(@Param("startDate") Date startDate, @Param("endDate") Date endDate, Page<ReportMerchantProduct> page);
	
	/**
	 * find merchant product count by date
	 * 
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public Integer findRecordCountByDate(@Param("startDate") Date startDate, @Param("endDate") Date endDate);
}
