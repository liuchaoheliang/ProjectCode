package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.BusinessSaleDetail;
import com.froad.po.CommonParam;
import com.froad.po.MerchantSaleDetail;
import com.froad.po.SaleCountDetail;
import com.froad.po.SaleTrend;
import com.froad.po.TypePercentInfo;

/**
 * 社区银行业务销售统计
 * @ClassName: ReportMerchantProductLogic 
 * @Description:  
 * @author longyunbo
 * @date 2015年6月5日 下午10:22:20
 */
public interface ReportMerchantProductLogic {
	/**
	 * 销售走势图
	* @Title: getSaleTrend 
	* @Description: 
	* @author longyunbo
	* @param @return
	* @return List<SaleTrend>
	* @throws
	 */
	public List<SaleTrend> getSaleTrend(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 业务销售类型占比
	* @Title: getSaleTypePercent 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<TypePercentInfo>
	* @throws
	 */
	public List<TypePercentInfo> getSaleTypePercent(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 支付方式占比
	* @Title: getPayTypePercent 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<TypePercentInfo>
	* @throws
	 */
	public List<TypePercentInfo> getPayTypePercent(CommonParam param) throws FroadBusinessException, Exception;
	
	/**
	 * 业务销售统计详情列表
	* @Title: getSaleCountDetail 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<SaleCountDetail>
	* @throws
	 */
	public List<SaleCountDetail> getSaleCountDetail(CommonParam param) throws FroadBusinessException, Exception;
	
	
	/**
	 * 业务销售统计详情列表(分页)
	* @Title: getSaleCountDetailListByPage 
	* @Description: 
	* @author longyunbo
	* @param @param page
	* @param @param param
	* @param @return
	* @return Page<SaleCountDetail>
	* @throws
	 */
	public Page<SaleCountDetail> getSaleCountDetailListByPage(Page<SaleCountDetail> page, CommonParam param) throws FroadBusinessException, Exception;
	
	
	
	/**
	 * 商户业务销售详情
	* @Title: getMerchantSaleDetail 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<MerchantSaleDetail>
	* @throws
	 */
	public List<MerchantSaleDetail> getMerchantSaleDetail(CommonParam param) throws FroadBusinessException, Exception;
	
	
	/**
	 * 业务类型销售统计详情列表
	* @Title: getBusinessSaleDetail 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<BusinessSaleDetail>
	* @throws
	 */
	public List<BusinessSaleDetail> getBusinessSaleDetail(CommonParam param) throws FroadBusinessException, Exception;
	
}
