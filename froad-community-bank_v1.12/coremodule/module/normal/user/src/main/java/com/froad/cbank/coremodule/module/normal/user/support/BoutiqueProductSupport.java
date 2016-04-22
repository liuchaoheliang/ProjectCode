/**
 * Project Name:coremodule-user
 * File Name:BoutiqueProductSupport.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.support
 * Date:2015年11月27日下午3:01:53
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.BoutiqueFiledSort;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.enums.TerminalType;
import com.froad.cbank.coremodule.module.normal.user.pojo.AdPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.BoutiqueProductDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.BoutiqueProductPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.BoutiqueProductReqPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.FindActivePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductCategoryPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductImagePojo;
import com.froad.thrift.service.ActiveSearchService;
import com.froad.thrift.service.AdvertisingService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.AdvertisingVo;
import com.froad.thrift.vo.BoutiqueGoodsInfoVo;
import com.froad.thrift.vo.BoutiqueProductPageVoRes;
import com.froad.thrift.vo.BoutiqueProductVo;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.FindAllAdvertisingParamVo;
import com.froad.thrift.vo.FindAllAdvertisingResultVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.QueryBoutiqueGoodsFilterVo;
import com.froad.thrift.vo.active.FindActiveRuleByProductVo;
import com.froad.thrift.vo.active.FindActiveRuleListResVo;
import com.froad.thrift.vo.active.FindActiveRuleResVo;
import com.froad.thrift.vo.active.FindActiveVo;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoReq;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoRes;

/**
 * ClassName:BoutiqueProductSupport
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月27日 下午3:01:53
 * @author   wm
 * @version  
 * @see 	 
 */
@Service
public class BoutiqueProductSupport extends BaseSupport {

	@Resource
	private ProductService.Iface productService; 
	
	@Resource
	private ProductCategoryService.Iface productCategoryService;
	
	@Resource
	private AdvertisingService.Iface advertisingService;
	
	@Resource
	private ActiveSearchService.Iface activeSearchService;
	
	@Resource
	private VipSupport vipSupport;
	
	@Resource
	private OrderService.Iface orderService;
	
	
	/**
	 * 
	 * getfroadMallIndex:精品商城首页直出.
	 *
	 * @author wm
	 * 2015年12月1日 下午1:39:26
	 * @param reqPojo
	 * @return
	 * @throws TException 
	 *
	 */
	public HashMap<String, Object> getfroadMallIndex(String clientId, TerminalType terminalType) throws TException{
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		//精品商城首页头部广告
		resMap.put("froadMallHead", adBoutiqueList(clientId, "froadMallHead", terminalType.getCode(), null, null, null));
		//精品商城底部头部广告
		resMap.put("froadMallFoot", adBoutiqueList(clientId, "froadMallFoot", terminalType.getCode(), null, null, null));
		
		//获取精品商城分类
		List<ProductCategoryVo> categoryList = productCategoryService.queryBoutiqueGoodsCategorys(clientId, 0);
		if(null != categoryList){
			resMap.put("categoryList", categoryList);
			
			if(categoryList.size() >=3){
				//这里默认取精品商城前三个分类广告位
				for(int i=0;i<3;i++){
					long categoryId = categoryList.get(i).getId();
					resMap.put("froadMallMid_"+i, adBoutiqueList(clientId, "froadMallMid_"+i, terminalType.getCode(), categoryId+"", null, null));
				}
			}else{
				for(int i=0;i<categoryList.size();i++){
					long categoryId = categoryList.get(i).getId();
					resMap.put("froadMallMid_"+i, adBoutiqueList(clientId, "froadMallMid_"+i, terminalType.getCode(), categoryId+"", null, null));
				}
			}
		}
		return resMap;
	}
	
	/**
	 * 
	 * adBoutiqueList:获取精品商城首页广告位.
	 *
	 * @author wm
	 * 2015年12月1日 下午2:05:16
	 * @param clientId
	 * @param positionPage
	 * @param terminalType
	 * @param paramOne
	 * @param paramTwo
	 * @param paramThree
	 * @return
	 *
	 */
	public List<AdPojo> adBoutiqueList(String clientId, String positionPage, String terminalType,String paramOne, String paramTwo, String paramThree){
		
		FindAllAdvertisingParamVo paramVo = new FindAllAdvertisingParamVo();
		paramVo.setClientId(clientId);
		paramVo.setPositionPage(positionPage);
		paramVo.setTerminalType(terminalType);
		paramVo.setParamOneValue(paramOne);
		paramVo.setParamTwoValue(paramTwo);
		paramVo.setParamThreeValue(paramThree);
		List<AdPojo> adList = new ArrayList<AdPojo>();
		try {
//			LogCvt.info("调用接口传入参数:"+JSON.toJSONString(adPositionVo)  );
			AdPojo adPojo =null ;
			
			FindAllAdvertisingResultVo resultVo = advertisingService.pageOptFindAdvertisings(paramVo);
			if(ResultCode.success.getCode().equals(resultVo.getResultVo().getResultCode())){
				for(AdvertisingVo adVo : resultVo.getAdvertisingVoList()){
					adPojo=new AdPojo();
					BeanUtils.copyProperties(adPojo, adVo);
					adList.add(adPojo);
				}
			}
		} catch (TException e) {
			LogCvt.error("广告列表查询出错", e);
		}
		return adList;
	}
	
	
	/**
	 * 
	 * getBoutiqueProductList:精品商城商品列表.
	 * @author wm
	 * 2015年11月30日 下午2:47:47
	 * @param reqPojo
	 * @return
	 *
	 */
	public HashMap<String, Object> getBoutiqueProductList(BoutiqueProductReqPojo reqPojo){
		LogCvt.info("查询精品商场商品分类getBoutiqueProductList---------请求参数---》: " + JSON.toJSONString(reqPojo));
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(reqPojo.getPageNumber());
		pageVo.setPageSize(reqPojo.getPageSize());
		pageVo.setLastPageNumber(reqPojo.getLastPageNumber());
		pageVo.setLastRecordTime(reqPojo.getLastRecordTime());
		pageVo.setFirstRecordTime(reqPojo.getFirstRecordTime());
		
		//条件过滤
		QueryBoutiqueGoodsFilterVo qbFilter = new QueryBoutiqueGoodsFilterVo();
		qbFilter.setClientId(reqPojo.getClientId());
		qbFilter.setProductName(reqPojo.getProductName());
		qbFilter.setProductCategoryId(reqPojo.getProductCategoryId());
		//'1'推荐;'2'热销;'3'新品;'4' 秒杀商品
		qbFilter.setGoodFlag(reqPojo.getGoodFlag());
		
		//排序规则
		FiledSort paramFiledSort = new FiledSort();
//		paramFiledSort.setSortPrior(sortPrior);
		//推荐优先" 我的VIP页面里的为您推荐用到
		if(BoutiqueFiledSort.recommend.getCode().equals(reqPojo.getSortCode())){
			paramFiledSort.setSortName(BoutiqueFiledSort.recommend.getMsg());
		}
		//综合排序
		if(BoutiqueFiledSort.hensive.getCode().equals(reqPojo.getSortCode())){
			paramFiledSort.setSortName(BoutiqueFiledSort.hensive.getMsg());
		}
		//销量排序
		if(BoutiqueFiledSort.sellCount.getCode().equals(reqPojo.getSortCode())){
			paramFiledSort.setSortName(BoutiqueFiledSort.sellCount.getMsg());
		}
		//价格排序
		if(BoutiqueFiledSort.price.getCode().equals(reqPojo.getSortCode())){
			paramFiledSort.setSortName(BoutiqueFiledSort.price.getMsg());
		}
		//-1负数代表降序，1整数代表升序
		paramFiledSort.setSortBy(reqPojo.getSortBy());

		BoutiqueProductPageVoRes pageVoRes = null;
		PagePojo page=new PagePojo();
		List<BoutiqueProductPojo> plist = null;
		BoutiqueProductPojo  boutiqueProductPojo = null;
		List<ProductCategoryVo> categoryList = null;
		try {
			//获取精品商城分类
			categoryList = productCategoryService.queryBoutiqueGoodsCategorys(reqPojo.getClientId(), 0);
			LogCvt.info("查询精品商场商品分类categoryList---------server端返回---》: " + JSON.toJSONString(categoryList));
			
			pageVoRes = productService.queryBoutiqueGoods(qbFilter, paramFiledSort, pageVo);
			LogCvt.info("查询精品商场商品分类pageVoRes---------server端返回---=============== =》: " + JSON.toJSONString(pageVoRes));
			
			if(pageVoRes.isSetPage()){
				BeanUtils.copyProperties(page,pageVoRes.getPage());
			}
			if(null != pageVoRes.getProductVos() && pageVoRes.getProductVos().size() != 0){
				 plist=new ArrayList<BoutiqueProductPojo>();
				 for(BoutiqueProductVo boutiqueVo : pageVoRes.getProductVos()){
					 boutiqueProductPojo = new BoutiqueProductPojo();
					 //商品基本信息
					 BeanUtils.copyProperties(boutiqueProductPojo, boutiqueVo);
					 ProductImagePojo productImage = new ProductImagePojo();
					 //商品图片信息
					 BeanUtils.copyProperties(productImage, boutiqueVo.getImage());
					 boutiqueProductPojo.setImage(productImage);
					 plist.add(boutiqueProductPojo);
				 }
			}
			
		} catch (TException e) {
			LogCvt.error("获取精品商城商品列表出错", e);
		}
		resMap.put("page", page);
		resMap.put("productList",plist);
		resMap.put("categoryList",categoryList);
		return resMap;
	}
	
	/**
	 * 
	 * getProductDetail:精品商城商品详情.
	 * @author wm
	 * 2015年12月4日 下午5:35:01
	 * @param clientId
	 * @param memberCode
	 * @param productId
	 * @param type
	 * @param areaId
	 * @return
	 *
	 */
	public HashMap<String, Object> getProductDetail(String clientId, String productId, Long memberCode){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		BoutiqueGoodsInfoVo boutiqueGoodsInfo=null;
		try {
			boutiqueGoodsInfo = productService.queryBoutiqueGoodsDetail(productId);
			LogCvt.info("查询精品商场商品详情boutiqueGoodsInfo---------server端返回---=============== =》: " + JSON.toJSONString(boutiqueGoodsInfo));
			BoutiqueProductDetailPojo boutiqueProductDetail = null;
				
			if(boutiqueGoodsInfo != null){
				boutiqueProductDetail = new BoutiqueProductDetailPojo();
				BeanUtils.copyProperties(boutiqueProductDetail,boutiqueGoodsInfo);
				
				List<String> imageUrls = boutiqueGoodsInfo.getImageUrls();
				boutiqueProductDetail.setImageUrls(imageUrls);
				 
				// 处理满减活动 (商品详情--返回集合型满减活动列表)			
				ArrayList<String> ids = new ArrayList<String>();
				ids.add(productId);
				FindActiveRuleByProductVo findActiveRuleByProductVo = new FindActiveRuleByProductVo();
				findActiveRuleByProductVo.setClientId(clientId);
				findActiveRuleByProductVo.setProductIdList(ids);
				if(memberCode != null ){
					findActiveRuleByProductVo.setMemberCode(memberCode.toString());
				}
				FindActiveRuleListResVo activeRuleListResVo = activeSearchService.findActiveRuleByProductIds(findActiveRuleByProductVo);
				if (!ArrayUtil.empty(activeRuleListResVo.getFindActiveRuleResList())) {
					
					for(FindActiveRuleResVo activeRuleResVo : activeRuleListResVo.getFindActiveRuleResList() ){
						//详情，返回当前商品绑定的所有活动标签
						List<FindActivePojo> alist = new ArrayList<FindActivePojo>();
						FindActivePojo activePojo =  null ;					
						for(FindActiveVo findActiveVo :  activeRuleResVo.getFindActiveList()){
							activePojo = new FindActivePojo();
							BeanUtils.copyProperties(activePojo,findActiveVo);
							alist.add(activePojo);
						}
						boutiqueProductDetail.setActivePojo(alist);				
					}
				}
				
				
				if(memberCode != null) {
					//根据用户ID + 商品ID 获取 商品是否已收藏 状态
					boolean isCollected = productService.isExitsStoreProductInfo(clientId, memberCode, productId);
					boutiqueProductDetail.setIsCollected(isCollected);
					
					//查询用户可购买商品数量的总数
					boolean isVip = vipSupport.isOrNotVip(memberCode,clientId);
					GetMemberBuyLimitVoReq getMemberBuyLimitVoReq = new GetMemberBuyLimitVoReq();
					getMemberBuyLimitVoReq.setClientId(clientId);
					getMemberBuyLimitVoReq.setMemberCode(memberCode);
					getMemberBuyLimitVoReq.setProductId(productId);
					getMemberBuyLimitVoReq.setIsVip(isVip);
					//设置商户ID
					getMemberBuyLimitVoReq.setMerchantId(boutiqueGoodsInfo.getMerchantId());
					
					GetMemberBuyLimitVoRes res=orderService.getMemberBuyLimit(getMemberBuyLimitVoReq);
					boutiqueProductDetail.setNum(res.getQuantity());
					boutiqueProductDetail.setVipNum(res.getVipQuantity());
					boutiqueProductDetail.setTotalNum(res.getTotalQuantity());
				}
			}
			resMap.put("boutiqueProductDetail", boutiqueProductDetail);
		} catch (TException e) {
			LogCvt.error("获取商品详情出错", e);
		}
		return resMap;
	}
	
	
	/**
	 * 
	 * queryBoutiqueGoodsCategorys:H5精品商城商品分类查询
	 *
	 * @author wm
	 * 2015年11月30日 下午1:44:21
	 * @param clientId
	 * @param parentId
	 * @return
	 *
	 */
	public  HashMap<String, Object> getBoutiqueGoodsCategorys(String clientId, Long parentId){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		List<ProductCategoryVo> respList = null;
		ProductCategoryPojo proCategoryPojo = null;
		List<ProductCategoryPojo> categoryList = new ArrayList<ProductCategoryPojo>();
		if(parentId==null){
			parentId=(long) 0;
		}
		try {
			respList = productCategoryService.queryBoutiqueGoodsCategorys(clientId, parentId);
			LogCvt.info("H5精品商城商品分类查询接收server端返回 ---->:  "+JSON.toJSONString(respList));
			if(!ArrayUtil.empty(respList)){
				for (ProductCategoryVo tmp :respList) {
					proCategoryPojo = new ProductCategoryPojo();
					BeanUtils.copyProperties(proCategoryPojo, tmp);
					categoryList.add(proCategoryPojo);
				}
			}
			resMap.put("categoryList", categoryList);
		} catch (TException e) {
			LogCvt.info("H5精品商城商品分类查询异常" + e.getMessage(), e);
			resMap.put("code", ResultCode.failed.getCode());
			resMap.put("message", "H5精品商城商品分类查询异常!");
		}
		return resMap;
	}
	
	
	/**
	 * 
	 * getRecommendProductCategorys:H5 精品商城广告位查询 
	 *
	 * @author wm
	 * 2015年11月30日 下午1:57:14
	 * @param clientId
	 * @param isMall
	 * @return
	 *
	 */
	public  HashMap<String, Object> getRecommendProductCategorys(String clientId, boolean isMall){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		ProductCategoryPojo proCategoryPojo = null;
		List<ProductCategoryPojo> categoryList = new ArrayList<ProductCategoryPojo>();
		try {
			List<ProductCategoryVo> respList = productCategoryService.queryRecommendProductCategorys(clientId, isMall);
			LogCvt.info("H5 精品商城广告位查询 server端返回 ---->:  "+JSON.toJSONString(respList));
			if(!ArrayUtil.empty(respList)){
				for (ProductCategoryVo tmp :respList) {
					proCategoryPojo = new ProductCategoryPojo();
					BeanUtils.copyProperties(proCategoryPojo, tmp);
					categoryList.add(proCategoryPojo);
				}
			}
			resMap.put("categoryList", categoryList);
		} catch (TException e) {
			LogCvt.info("类目推荐的商品分类查询异常" + e.getMessage(), e);
			resMap.put("code", ResultCode.failed.getCode());
			resMap.put("message", "类目推荐的商品分类查询异常!");
		}
		return resMap;
	}
	
}
