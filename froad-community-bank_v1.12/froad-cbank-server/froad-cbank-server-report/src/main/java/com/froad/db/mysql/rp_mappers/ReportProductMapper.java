package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ProductCategoryCount;
import com.froad.po.ProductSaleDetail;
import com.froad.po.ProductTypeCount;
import com.froad.po.ReportBankUser;
import com.froad.po.ReportMerchantSale;

public interface ReportProductMapper {
    

    public List<ReportMerchantSale> findSaleTrend(@Param("user")ReportBankUser user, @Param("orgCode")String orgCode);

	public List<ProductTypeCount> findProductTypeCount(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, @Param("orgCode") String orgCode);

	public List<ProductCategoryCount> findProductCategoryCount(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, @Param("orgCode") String orgCode);

	public List<ProductSaleDetail> findProductSaleDetail(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, @Param("orgCode") String orgCode);

	public List<ProductSaleDetail> findInvalidProductList(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, @Param("orgCode") String orgCode);	
	
	public List<ProductSaleDetail> findProductSaleDetailListByPage(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, Page<ProductSaleDetail> page,
			@Param("orgCode") String orgCode);
	
	public List<ProductSaleDetail> findInvalidProductListByPage(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, Page<ProductSaleDetail> page,
			@Param("orgCode") String orgCode);
}
