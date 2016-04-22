package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.ReportSalePaymethod;

public interface ReportSalePaymethodMapper {

	public Boolean addSalePayMethod(ReportSalePaymethod salePaymethod);
	
	public Boolean addByBatch(@Param("items")List<ReportSalePaymethod> salePaymethods);
}
