package com.froad.db.mysql.mappers;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ProductCategory;
import com.froad.po.ReportMerchantProduct;

public interface ProductMapper {
	
	/**
	 * find product count by date
	 * 
	 * @param date
	 * @return
	 */
	public Integer findRecordCountByDate(@Param("date")Date date);
	
	public List<ReportMerchantProduct> makeReportMerchantProductDataByPage(@Param("date")Date date, Page<ReportMerchantProduct> page);
	
	public List<String> getProducts(Map<String,Object> param);
	
	public List<ProductCategory> findProductCategory();
	
	public List<ReportMerchantProduct> findProductCountByPage(@Param("endDate") Date endDate, Page<ReportMerchantProduct> page);
}
