package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportMerchantOutlet;
import com.froad.po.ReportMerchantOutletCount;

public interface ReportMerchantOutletMapper {
	
	/**
	 * 添加报表商户门店信息
	 * 
	 * @param reportMerchantOutlet
	 * @return
	 */
	public Boolean addReportMerchantOutlet(ReportMerchantOutlet reportMerchantOutlet);
	
	/**
	 * 批量添加报表商户门店信息
	 * 
	 * @param reportMerchantOutlets
	 * @return
	 */
	public Boolean addByBatch(@Param("reportMerchantOutlets")List<ReportMerchantOutlet> reportMerchantOutlets);
	
	/**
	 * 查找报表商户门店信息
	 * 
	 * @param reportOutletQuery
	 * @return
	 */
	public List<ReportMerchantOutlet> findByCondition(ReportMerchantOutlet reportOutletQuery);
	
	
	public List<ReportMerchantOutletCount> findMerchantOutletCount(
			@Param("clientId") String clientId,
			@Param("forgCode") String forgCode,
			@Param("sorgCode") String sorgCode,
			@Param("orgCode") String orgCode,
			@Param("startTime") Date startTime,
			@Param("endTime") Date endTime);
}
