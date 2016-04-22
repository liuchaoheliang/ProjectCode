package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.CommonParam;
import com.froad.po.ProductSaleDetail;
import com.froad.po.ProductSaleTrend;
import com.froad.po.TypePercentInfo;

/**
 * 社区银行商品统计
 * @ClassName: ReportProductService 
 * @Description:  
 * @author longyunbo
 * @date 2015年6月5日 下午10:21:15
 */
public interface ReportProductLogic {
	
	/**
	 * 商品销售走势
	* @Title: getProductSaleTrend 
	* @Description: 
	* @author longyunbo
	* @param @return
	* @return List<ProductSaleTrend>
	* @throws
	 */
	public List<ProductSaleTrend> getProductSaleTrend(CommonParam param) throws FroadBusinessException, Exception;
	
	
	/**
	 * 商品业务类型占比
	* @Title: getProductTypePercent 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<TypePercentInfo>
	* @throws
	 */
	public List<TypePercentInfo> getProductTypePercent(CommonParam param) throws Exception;
	
	
	/**
	 * 商品类目占比
	* @Title: getProductCategoryPercent 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<TypePercentInfo>
	* @throws
	 */
	public List<TypePercentInfo> getProductCategoryPercent(CommonParam param) throws Exception;
	
	
	/**
	 * 商品销售详情列表
	* @Title: getProductSaleDetail 
	* @Description: 
	* @author longyunbo
	* @param @param param
	* @param @return
	* @return List<ProductSaleDetail>
	* @throws
	 */
	public List<ProductSaleDetail> getProductSaleDetail(CommonParam param) throws FroadBusinessException, Exception;
	
	
	/**
	 * 商品销售详情列表(分页)
	* @Title: getProductSaleDetailListByPage 
	* @Description: 
	* @author longyunbo
	* @param @param page
	* @param @param param
	* @param @return
	* @return Page<ProductSaleDetail>
	* @throws
	 */
	public Page<ProductSaleDetail> getProductSaleDetailListByPage(Page<ProductSaleDetail> page,CommonParam param) throws FroadBusinessException, Exception;
		
}
