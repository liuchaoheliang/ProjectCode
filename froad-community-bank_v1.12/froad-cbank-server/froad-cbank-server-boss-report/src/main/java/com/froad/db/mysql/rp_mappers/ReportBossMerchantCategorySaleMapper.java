package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportBossMerchantCategorySale;

public interface ReportBossMerchantCategorySaleMapper {
	
	/**
     * 批量添加
     */
	public int addByBatch(@Param("mcsList")List<ReportBossMerchantCategorySale> orgSaleList);
    
    /**
	 * 删除当期历史数据数据
	 */
	public int deleteHistoryData(@Param("reportDate") Integer reportDate);
	
	/**
	 * 查询商户分类销量报表
	 */
	public List<ReportBossMerchantCategorySale> findByClientId(@Param("clientId")String clientId, 
			@Param("reportDate") Integer reportDate);

}