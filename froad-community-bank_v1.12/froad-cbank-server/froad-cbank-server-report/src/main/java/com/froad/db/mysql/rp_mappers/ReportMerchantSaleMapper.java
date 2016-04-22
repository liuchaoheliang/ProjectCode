package com.froad.db.mysql.rp_mappers;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BusinessSaleDetail;
import com.froad.po.MerchantSaleDetail;
import com.froad.po.PayTypeCount;
import com.froad.po.ReportBankUser;
import com.froad.po.ReportMerchantSale;
import com.froad.po.SaleCountDetail;
import com.froad.po.SaleTypeCount;

public interface ReportMerchantSaleMapper {
	Boolean addByBatch(@Param("reportMerchantSales") List<ReportMerchantSale> reportMerchantSales);

    /**
     * @param merchantSale
     * @return <pre>
     * @Description: 商户商品销售
     * @version V1.0 修改人：Arron 日期：2015年6月2日 下午5:03:19
     * </pre>
     */
    public long add(ReportMerchantSale merchantSale);

    public List<ReportMerchantSale> findSaleTrend(@Param("user")ReportBankUser user, @Param("orgCode")String orgCode);

	public List<SaleTypeCount> findSaleTypeCount(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, @Param("orgCode") String orgCode);

	public List<PayTypeCount> findPayTypeCount(@Param("begDate") Date begDate,
			@Param("endDate") Date endDate, @Param("user") ReportBankUser user,
			@Param("orgCode") String orgCode);

	public List<SaleCountDetail> findSaleCountDetail(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, @Param("orgCode") String orgCode);
	
	public List<SaleCountDetail> findSaleCountDetailListByPage(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, Page<SaleCountDetail> page,
			@Param("orgCode") String orgCode);
	
	public List<MerchantSaleDetail> findMerchantSaleDetail(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, @Param("orgCode") String orgCode);
	  
	public List<BusinessSaleDetail> findBusinessSaleDetail(
			@Param("begDate") Date begDate, @Param("endDate") Date endDate,
			@Param("user") ReportBankUser user, @Param("orgCode") String orgCode);
	
}
