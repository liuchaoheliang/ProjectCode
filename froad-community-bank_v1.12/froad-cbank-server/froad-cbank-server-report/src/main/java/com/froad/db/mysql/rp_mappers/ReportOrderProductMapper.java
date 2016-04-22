package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ReportOrderProduct;

public interface ReportOrderProductMapper {
	
	public Boolean addProduct(ReportOrderProduct product);
	
	public Boolean addProductByBatch(@Param("products")List<ReportOrderProduct> products);
	
	public List<ReportOrderProduct> findByCondition(ReportOrderProduct productQuery);
	
	public List<ReportOrderProduct> findByConditionByPage(@Param("createDate") Integer createDate, Page<ReportOrderProduct> page);
}
