package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportBossOrgSale;

public interface ReportBossOrgSaleMapper {
    
    /**
     * 批量添加
     */
    int addByBatch(@Param("orgSaleList")List<ReportBossOrgSale> orgSaleList);
    
    /**
	 * 删除当期历史数据数据
	 */
	public int deleteHistoryData(@Param("reportDate") Integer reportDate);

	/**
	 * 根据ClientId获取当前的报表数据
	 */
	public List<ReportBossOrgSale> findByClientId(@Param("clientId") String clientId, 
			@Param("reportDate") Integer reportDate, 
			@Param("orderType") String orderType);
	
}