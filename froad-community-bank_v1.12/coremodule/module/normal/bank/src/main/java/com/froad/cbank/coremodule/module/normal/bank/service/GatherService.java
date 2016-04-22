package com.froad.cbank.coremodule.module.normal.bank.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.PayGatherVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.TransGatherVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.TypeVo;
import com.froad.thrift.service.ProductCategoryService;

/**
 * 汇总（支付汇总、商户交易汇总）
 * 
 * @author yfy
 * @date 2015-03-25 下午 14:35:31
 */
@Service
public class GatherService {
	
	@Resource
	ProductCategoryService.Iface productCategoryService;

	/**
	 * 支付汇总
	 * 
	 * @param productType
	 * @param merchantName
	 * @param pratenOrgCode
	 * @param orgCode
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public List<PayGatherVo> findByConditions(String productType,String merchantName,
			String pratenOrgCode,String orgCode,String startDate,String endDate){
		
		List<PayGatherVo> payGatherList = new ArrayList<PayGatherVo>();
		
		return payGatherList;
	}
	
	/**
	 * 商户交易汇总
	 * 
	 * @param merchantId
	 * @param productType
	 * @param state
	 * @param auditReason
	 * @return
	 */
	public QueryResult<TransGatherVo> findPageByConditions(String productType,String merchantName,String pratenOrgCode,
			String orgCode,String startDate,String endDate,int pageNumber,int pageSize){
		
		QueryResult<TransGatherVo> queryVo = new QueryResult<TransGatherVo>();
		return queryVo;
	}
	
	public List<TypeVo> getProductType(){
		List<TypeVo> productTypeList = new ArrayList<TypeVo>();
//		productCategoryService.findCategorysByPage(productCategoryVo, pageVo);
		
		return productTypeList;
	}
	
	
}
