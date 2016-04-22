package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportBossOrgMerchant;

public interface ReportBossOrgMerchantMapper {
	
	/**
     * 批量添加
     */
    int addByBatch(@Param("orgMerchantList")List<ReportBossOrgMerchant> orgMerchantList);
    
    /**
	 * 删除当期历史数据数据
	 */
	public int deleteHistoryData(@Param("reportDate") Integer reportDate);

	 /**
	  * 查询
	  */
	public List<ReportBossOrgMerchant> findByClientId(@Param("clientId") String clientId, @Param("reportDate") Integer reportDate);
	
}