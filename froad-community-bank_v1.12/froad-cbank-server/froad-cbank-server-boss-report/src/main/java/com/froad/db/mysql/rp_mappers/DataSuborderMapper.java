package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.DataSuborder;
import com.froad.po.ReportBossMerchantCategorySale;
import com.froad.po.ReportBossOrgSale;

public interface DataSuborderMapper {

	/**
	 * 批量添加
	 */
	public int addByBatch(@Param("orders") List<DataSuborder> orders);

	/**
	 * 删除当期历史数据数据
	 */
	public int deleteHistoryData(@Param("reportDate") Integer reportDate);

	/**
	 * 按机构统计销售数据
	 */
	public ReportBossOrgSale sumOrgSaleDataByOrg(@Param("reportDate") Integer reportDate, 
			@Param("clientId") String clientId, @Param("orgCode2") String orgCode2,
			@Param("orderType") String orderType,
			@Param("begin") Date begin, @Param("end") Date end);
	
	/**
	 * 按机构统计销售数据
	 */
	public ReportBossMerchantCategorySale sumOrgSaleDataByMerchantCategory(@Param("reportDate") Integer reportDate, 
			@Param("clientId") String clientId, 
			@Param("merchantCategoryIds") String merchantCategoryIds, @Param("end") Date end);
	
}