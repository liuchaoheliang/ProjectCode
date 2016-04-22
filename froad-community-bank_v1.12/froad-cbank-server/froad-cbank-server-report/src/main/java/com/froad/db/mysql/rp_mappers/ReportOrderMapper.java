package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ReportOrder;

public interface ReportOrderMapper {
	
	public Boolean addOrder(ReportOrder order);
	
	public Boolean addOrderByBatch(@Param("orders")List<ReportOrder> orders);
	
	public List<ReportOrder> findByCondition(ReportOrder orderQuery);
	
	public List<ReportOrder> findByConditionByPage(@Param("createDate") Integer createDate, Page<ReportOrder> page);
}
