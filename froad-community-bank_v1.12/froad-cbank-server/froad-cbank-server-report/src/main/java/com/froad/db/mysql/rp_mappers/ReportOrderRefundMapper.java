package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ReportOrderRefund;

public interface ReportOrderRefundMapper {
	
	public Boolean addRefund(ReportOrderRefund refund);
	
	public Boolean addRefundByBatch(@Param("refunds")List<ReportOrderRefund> refunds);
	
	public List<ReportOrderRefund> findByMerchantId(
			@Param("clientId") String clientId,
			@Param("merchantId") String merchantId,
			@Param("createDate") Integer createDate,
			@Param("orderType") String orderType
			);
	
	
	public List<ReportOrderRefund> findByMemberCode(ReportOrderRefund query);
	
	public List<ReportOrderRefund> findByMemberCodeByPage(@Param("createDate") Integer createDate, Page<ReportOrderRefund> page);
}
