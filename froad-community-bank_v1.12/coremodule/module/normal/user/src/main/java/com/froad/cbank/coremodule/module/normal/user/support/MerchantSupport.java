package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.CategoryInfoPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.FindActivePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.MerchantCategoryPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletCommentPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletPhotoPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletSimplePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductOutletPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductPojo;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.service.ActiveSearchService;
import com.froad.thrift.service.MerchantCategoryService;
import com.froad.thrift.service.MerchantOutletFavoriteService;
import com.froad.thrift.service.MerchantOutletPhotoService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.LocationVo;
import com.froad.thrift.vo.MerchantCategoryPageVoRes;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.MerchantOutletPhotoVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.OutletCommentPageVoRes;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.OutletDetailSimpleInfoPageVoRes;
import com.froad.thrift.vo.OutletDetailSimpleInfoVo;
import com.froad.thrift.vo.OutletDetailVo;
import com.froad.thrift.vo.OutletMongoInfoPageVoRes;
import com.froad.thrift.vo.OutletMongoInfoVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductBriefPageVoRes;
import com.froad.thrift.vo.ProductBriefVoRes;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.TypeInfoVo;
import com.froad.thrift.vo.active.FindActiveRuleByProductVo;
import com.froad.thrift.vo.active.FindActiveRuleListResVo;
import com.froad.thrift.vo.active.FindActiveRuleResVo;
import com.froad.thrift.vo.active.FindActiveVo;

/**
 * @author 陆万全 luwanquan@f-road.com.cn
 * @date 创建时间：2015年4月1日 上午10:07:15
 * @version 1.0
 * @desc 商户支持类
 */
@Service
public class MerchantSupport extends BaseSupport {
	
	@Resource
	private ProductService.Iface productService; 

	@Resource
	private MerchantService.Iface merchantService;
	
	@Resource
	private OutletService.Iface outletService;
	
	@Resource
	private MerchantCategoryService.Iface merchantCategoryService;
	
	@Resource
	private MerchantOutletPhotoService.Iface merchantOutletPhotoService;
	
	@Resource
	private MerchantOutletFavoriteService.Iface merchantOutletFavoriteService;
	
	@Resource
	private OutletCommentService.Iface outletCommentService;
	
	
	@Resource
	private OrgService.Iface orgService;
	
	@Resource
	private ActiveSearchService.Iface activeSearchService;
	
	/**
	 * @desc 获取商户分类列表（分页/全部）
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月3日 下午5:00:24
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getMerchantCategory(String clientId, Long parentId, Integer pageNumber, Integer pageSize, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		MerchantCategoryVo req = new MerchantCategoryVo();
		List<MerchantCategoryVo> respList = null;	//返回全部结果
		MerchantCategoryPageVoRes respPage = null;	//返回分页结果
		List<MerchantCategoryPojo> categoryList = new ArrayList<MerchantCategoryPojo>();
		MerchantCategoryPojo categoryPojo = null;
		PagePojo pagePojo = new PagePojo();
		//封装分页对象
		PageVo page = null;
		if(pageNumber != null && pageSize != null) {
			page = new PageVo();
			page.setPageNumber(pageNumber);
			page.setPageSize(pageSize);
			page.setLastPageNumber(lastPageNumber);
			page.setFirstRecordTime(firstRecordTime);
			page.setLastRecordTime(lastRecordTime);
		}
		//封装请求对象
		req.setClientId(clientId);
		if(parentId != null) {
			req.setParentId(parentId);
		}
		try {
			if(page == null) {
				//查询全部商户分类
				LogCvt.info(">>进入全部商户分类查询");
				respList = merchantCategoryService.getMerchantCategory(req);
				if(!ArrayUtil.empty(respList)) {
					for(MerchantCategoryVo tmp : respList) {
						categoryPojo = new MerchantCategoryPojo();
						BeanUtils.copyProperties(categoryPojo, tmp);
						categoryList.add(categoryPojo);
					}
				}
				resResult.put("categoryList", categoryList);
			} else {
				//分页查询商户分类
				LogCvt.info(">>进入分页商户分类查询");
				respPage = merchantCategoryService.getMerchantCategoryByPage(page, req);
				if(respPage.getPage() != null) {
					BeanUtils.copyProperties(pagePojo, respPage.getPage());
				}
				if(!ArrayUtil.empty(respPage.getMerchantCategoryVoList())) {
					for(MerchantCategoryVo tmp : respPage.getMerchantCategoryVoList()) {
						categoryPojo = new MerchantCategoryPojo();
						BeanUtils.copyProperties(categoryPojo, tmp);
						categoryList.add(categoryPojo);
					}
				}
				resResult.put("page", pagePojo);
				resResult.put("categoryList", categoryList);
			}
		} catch (TException e) {
			LogCvt.info("商户分类列表查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "商户分类列表查询异常");
		} 
		return resResult;
	}
	
	/**
	 * @desc 根据商户ID获取门店列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年5月9日 上午9:35:43
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getOutletListByMerchantId(String clientId, String merchantId, Integer pageNumber, Integer pageSize, Integer lastPageNumber,Double longitude, Double latitude, Long firstRecordTime, Long lastRecordTime) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		OutletDetailVo req = new OutletDetailVo();
		//OutletDetailPageVoRes resp = null;
		PagePojo pagePojo = new PagePojo();
		List<OutletSimplePojo> outletList = new ArrayList<OutletSimplePojo>();
		OutletSimplePojo pojo = null;
		//封装分页对象
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);
		//封装请求对象
		req.setClientId(clientId);
		req.setMerchantId(merchantId);
		req.setIsEnable(true);
		
		if(latitude  == null || longitude == null){
			latitude = 0.0;
			longitude = 0.0;
		}
		try {
			/*//含经纬度查询
			if(latitude!=null && longitude!=null ){*/
				OutletMongoInfoPageVoRes voRes =outletService.getOutletMongoInfoVoByPage(page, longitude, latitude, merchantId);
				if(voRes.getPage() != null) {
					BeanUtils.copyProperties(pagePojo, voRes.getPage());
				}
				if(!ArrayUtil.empty(voRes.getUtletMongoInfoVoList())) {
					for(OutletMongoInfoVo tmp : voRes.getUtletMongoInfoVoList() ) {
						pojo = new OutletSimplePojo();
						BeanUtils.copyProperties(pojo, tmp);
						outletList.add(pojo);
					}
				}		
			/*}*/
			/*else{  //  非经纬度查询
				resp = outletService.getOutletDetailByPage(page, req);
				if(resp.getPage() != null) {
					BeanUtils.copyProperties(pagePojo, resp.getPage());
				}
				if(!ArrayUtil.empty(resp.getOutletDetailVoList())) {
					for(OutletDetailVo tmp : resp.getOutletDetailVoList()) {
						pojo = new OutletSimplePojo();
						BeanUtils.copyProperties(pojo, tmp);
						
						//商户类型集合
						if( !ArrayUtil.empty(tmp.getTypeInfo()) ) {
							StringBuilder type=new StringBuilder();
							for(TypeInfoVo temp : tmp.getTypeInfo()) {
								type.append(temp.getType()+",");
							}
							pojo.setTypeInfo(type.toString());
						}
						
						//封装门店分类集合
						if(tmp.getCategoryInfoSize() > 0) {
							List<CategoryInfoPojo> categoryList = new ArrayList<CategoryInfoPojo>();
							CategoryInfoPojo categoryInfo = null;
							for(CategoryInfoVo categoryTemp : tmp.getCategoryInfo()) {
								categoryInfo = new CategoryInfoPojo();
								BeanUtils.copyProperties(categoryInfo, categoryTemp);
								categoryList.add(categoryInfo);
							}
							pojo.setCategoryList(categoryList);
					}
					outletList.add(pojo);
				}
			}
				
			}*/
		} catch (TException e) {
			LogCvt.info("根据商户ID获取门店列表异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "根据商户ID获取门店列表异常");
		}
		resResult.put("page", pagePojo);
		resResult.put("outletList", outletList);
		return resResult;
	}
	
	/**
	 * @desc 获取门店详情 
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月8日 下午4:02:05
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getOutletDetail(String clientId, Long memberCode, String outletId) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		OutletVo req = new OutletVo();
		//OutletDetailVo resp = new OutletDetailVo();
		List<OutletVo> respList = null;
		OutletVo resp = null;
		OutletPojo outletPojo = null;
		int outletCount = 0;
		//商户类型
		String typeInfo="";
		//封装请求对象
		req.setClientId(clientId);
		req.setOutletId(outletId);
		
		//门店详情获取
		try {
			//因OutletDetailVo缺少营业时间字段，故取OutletVo
			//outletService.getOutletDetail(outletId);
			respList = outletService.getOutlet(req);
			if(respList != null && respList.size() > 0) {
				outletPojo = new OutletPojo();
				resp = respList.get(0);
				BeanUtils.copyProperties(outletPojo, resp);
				//商户类型集合
				if( !ArrayUtil.empty(resp.getTypeInfo()) ) {
					StringBuilder type=new StringBuilder();
					for(TypeInfoVo temp : resp.getTypeInfo()) {
						type.append(temp.getType()+",");
					}
					typeInfo=type.toString();
					// (1, ---代表直接优惠商户) ,如果该商户类型不包含直接优惠，则不需要优惠详情
					if (!typeInfo.contains("1,")) {
						outletPojo.setDiscount("");
						outletPojo.setPreferDetails("");
						outletPojo.setDiscountCode("");
						outletPojo.setDiscountRate("");
					} else {
						// 如果是直接优惠的商户类型，组合成新的折扣信息展示
						String code = outletPojo.getDiscountCode();
						String rate = outletPojo.getDiscountRate();
						if(!StringUtil.empty(code) && !StringUtil.empty(rate) ){
							String info = "凭" + code + "消费，可享受" + rate + "折，欢迎到本店消费。";
							outletPojo.setDiscount(info);
							//outletPojo.setPreferDetails(info);
						}
					}
					outletPojo.setDiscountRate("");
					outletPojo.setTypeInfo(typeInfo);
				}
				outletPojo.setOutletId(resp.getOutletId());
				//根据商户ID统计门店数量	
				outletCount = merchantService.getMerchantDetail(resp.getMerchantId()).getOutletInfoSize();
				LogCvt.info(">>从SERVER端返回商户详情（门店数量）结果：outletCount=" + outletCount);
				if(memberCode != null) {
					//根据门店ID与用户ID获取 门店是否已收藏状态
					boolean isCollected = merchantOutletFavoriteService.isExitsStoreOutletInfo(clientId, memberCode, outletId);
					LogCvt.info(">>从SERVER端返回'" + memberCode + "'用户是否已收藏门店'" + outletId + "'结果：" + isCollected);
					outletPojo.setIsCollected(isCollected);
					//根据用户ID + 门店ID + 当前时间 获取 门店当日是否已评价状态
					boolean isComment = outletCommentService.isExistComment(StringUtil.toString(memberCode), outletId, new Date().getTime());
					LogCvt.info(">>从SERVER端返回'" + memberCode + "'用户当天是否已评价门店'" + outletId + "'结果：" + isComment);
					outletPojo.setIsComment(isComment);
				}
			}
			resResult.put("outletCount", outletCount);
			resResult.put("outlet", outletPojo);	
			
		} catch (TException e) {
			LogCvt.info("门店详情查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店详情查询异常");
		}
		
		//获取门店相册		
		MerchantOutletPhotoVo req1 = new MerchantOutletPhotoVo();
		List<MerchantOutletPhotoVo> resp1 = null;
		List<OutletPhotoPojo> photoList = new ArrayList<OutletPhotoPojo>();
		OutletPhotoPojo photoPojo = null;
		//封装请求对象
		req1.setOutletId(outletId);
		//除logo以外的图片
		req1.setIsDefault(false);
		try {
			resp1 = merchantOutletPhotoService.getMerchantOutletPhoto(req1);
			if(!ArrayUtil.empty(resp1)) {
				for(MerchantOutletPhotoVo tmp : resp1) {
					photoPojo = new OutletPhotoPojo();
					BeanUtils.copyProperties(photoPojo, tmp);
					photoList.add(photoPojo);
				}
			}
			resResult.put("outletPhotoList", photoList);
		} catch (TException e) {
			LogCvt.info("门店相册列表查询异常" + e.getMessage(), e);
		}
		
		//获取门店评论
		OutletCommentVo req3 = new OutletCommentVo();
		OutletCommentPageVoRes resp3 = null;
		List<OutletCommentPojo> outletCommentList = new ArrayList<OutletCommentPojo>();
		OutletCommentPojo pojo = null;
		PagePojo pagePojo = new PagePojo();
		
		//封装page分页对象
		PageVo page = new PageVo();
		page.setPageNumber(1);
		page.setPageSize(2);
		
		//封装开始/结束时间
//		CreateTimeFilterVo timeFilter =new CreateTimeFilterVo();
//		timeFilter.setBegTime(DateUtils.addDays(new Date(), 0).getTime());
//		timeFilter.setEndTime(new Date().getTime());

		//封装请求对象
		req3.setClientId(clientId);
		req3.setOutletId(outletId);
//		req3.setCreateTimeFilter(timeFilter);
		try {
			resp3 = outletCommentService.getOutletCommentByPage(page, req3);
			if(resp3.getPage() != null) {
				BeanUtils.copyProperties(pagePojo, resp3.getPage());
			}
			if(!ArrayUtil.empty(resp3.getOutletCommentVoList())) {
				for(OutletCommentVo tmp : resp3.getOutletCommentVoList()) {
					pojo = new OutletCommentPojo();
					BeanUtils.copyProperties(pojo, tmp);
					//隐藏用户名
					String memberName = pojo.getMemberName().substring(0, 1)+"****"+ pojo.getMemberName().substring(pojo.getMemberName().length()-1);
					pojo.setMemberName(memberName);
					outletCommentList.add(pojo);
				}
			}
			resResult.put("outletCommentList", outletCommentList);
			resResult.put("commentPage", pagePojo);
		} catch (TException e) {
			LogCvt.info("门店评论分页列表查询异常" + e.getMessage(), e);
		}
		
		//商户的商品列表
		
		ProductBriefPageVoRes pageVoRes=null;
		PagePojo productPage=null;
		List<ProductPojo> plist = new ArrayList<ProductPojo>();
		ProductPojo  productPojo=null;
		
		try {
			//如果门店不存在，即商户也不存在，则不需要查询商户的团购商品列表
			//如果商户类型不包含团购商户类型（0, ---代表团购商户类型），则也不需要查询团购商品列表
			if(outletPojo!= null && !StringUtil.empty(outletPojo.getMerchantId()) && typeInfo.contains("0,")   ){
				//分页page对象
				PageVo pageVo = new PageVo();
				pageVo.setPageNumber(1);
				pageVo.setPageSize(3);
				
				ProductFilterVoReq productFilterVoReq=new ProductFilterVoReq();
				productFilterVoReq.setClientId(clientId);
				productFilterVoReq.setMerchantId(outletPojo.getMerchantId());

				//条件过滤
				Map<String,Object> filter=new HashMap<String, Object>();
				filter.put("type" , "1" ); //团购商品
				filter.put("orderField" , "{'3':'sellCount-desc','2':'storeCount-desc','1':'startTime-asc'}" ); //团购商品
				
				//处理掉json中的转义符号
				productFilterVoReq.setFilter( JSON.toJSONString(filter).replace("\\", "") );

				pageVoRes = productService.queryProducts(productFilterVoReq, pageVo);
			
				if(pageVoRes.isSetPage()){
					productPage=new PagePojo();
					BeanUtils.copyProperties(productPage,pageVoRes.getPage());
				}
				
				if( pageVoRes.getProductBriefVoList() !=null && pageVoRes.getProductBriefVoList().size() != 0     ){
					 plist=new ArrayList<ProductPojo>();
					 List<String> ids = new ArrayList<String>();
					 HashMap<String, Integer> map = new HashMap<String, Integer>();
					 for( int i=0 ; i < pageVoRes.getProductBriefVoList().size() ; i++ ){
						 ProductBriefVoRes temp = pageVoRes.getProductBriefVoList().get(i);
						 ids.add(temp.getProductId());
						 map.put(temp.getProductId(), i);
						 productPojo=new ProductPojo();
						 BeanUtils.copyProperties(productPojo,temp);
							//特殊处理 ， 秒杀商品为1时，才为正常状态下的秒杀商品
						 if("1".equals(temp.getIsSeckill())){ 						 
							 if(temp.getServerTime() > temp.getSecStartTime() && temp.getServerTime() < temp.getSecEndTime()){
								//秒杀中
								 productPojo.setIsSeckill("1");
							 }
							 if(temp.getServerTime() < temp.getSecStartTime()){
								 //即将秒杀
								 productPojo.setIsSeckill("2");
							 }
							 if(temp.getServerTime() > temp.getSecEndTime()){
								 //秒杀过期
								 productPojo.setIsSeckill("0");
							 }
						 }else{
							 productPojo.setIsSeckill("0");
						 }
						 plist.add(productPojo);
					 }
					 
					// 处理满减活动 
					FindActiveRuleByProductVo findActiveRuleByProductVo = new FindActiveRuleByProductVo();
					findActiveRuleByProductVo.setClientId(clientId);
					findActiveRuleByProductVo.setProductIdList(ids);
					 
					FindActiveRuleListResVo activeRuleListResVo = activeSearchService.findActiveRuleByProductIds(findActiveRuleByProductVo);
					if (!ArrayUtil.empty(activeRuleListResVo.getFindActiveRuleResList())) {
						for(FindActiveRuleResVo activeRuleResVo : activeRuleListResVo.getFindActiveRuleResList()){
							String pid = activeRuleResVo.getId();
							//现目前一个商品暂定只关联一个活动
							 if(!ArrayUtil.empty(activeRuleResVo.getFindActiveList())){
								 FindActivePojo activePojo =  null ;
								 List<FindActivePojo> list = new ArrayList<FindActivePojo>();
								for(FindActiveVo findActiveVo:activeRuleResVo.getFindActiveList()){
									activePojo = new FindActivePojo();
									BeanUtils.copyProperties(activePojo,findActiveVo);
									list.add(activePojo);
								} 
								int index = map.get(pid);
								plist.get(index).setActivePojo(list);
							 }				
						}
				    }
				  // 处理满减活动结束
					 
				}
				
			}
			
		} catch (TException e) {
			LogCvt.error("获取商品列表出错", e);
		}
		resResult.put("productPage", productPage);
		resResult.put("productList",plist);
	
		return resResult;
	}
	
	/**
	 * @desc 根据定位信息获取‘按距离升序门店列表’（若无定位信息，则获取‘按收藏数降序门店列表’）
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年5月9日 上午9:56:37
	 * @param 
	 * @return
	 */
	public HashMap<String, Object> getOutletList(String clientId, Long areaId, Long parentAreaId, Double longitude, Double latitude, Long categoryId, String keyword, Integer pageNumber, Integer pageSize,Integer pageCount,Integer totalCount, Integer lastPageNumber, Long firstRecordTime, Long lastRecordTime, Integer sortBy, Double distance) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		OutletDetailVo req = new OutletDetailVo();
		OutletDetailSimpleInfoPageVoRes resp = null;
		PagePojo pagePojo = new PagePojo();
		List<OutletSimplePojo> outletList = new ArrayList<OutletSimplePojo>();
		OutletSimplePojo pojo = null;
		//封装分页对象
		PageVo page = new PageVo();
		page.setPageNumber(pageNumber);
		page.setPageSize(pageSize);
		page.setPageCount(pageCount);
		page.setTotalCount(totalCount);
		page.setLastPageNumber(lastPageNumber);
		page.setFirstRecordTime(firstRecordTime);
		page.setLastRecordTime(lastRecordTime);
		//封装请求对象
		if(areaId != null) {
			req.setAreaId(areaId);
		}
		if(parentAreaId != null) {
			req.setParentAreaId(parentAreaId);
		}
		//封装Location位置对象
		LocationVo locationVo = null;
		if(longitude != null && latitude != null) {
			locationVo = new LocationVo();
			locationVo.setLongitude(longitude);
			locationVo.setLatitude(latitude);
			req.setLocation(locationVo);
		}
		//封装商户分类对象
		List<CategoryInfoVo> categoryInfoList = null;
		if(categoryId != null) {
			categoryInfoList = new ArrayList<CategoryInfoVo>();
			CategoryInfoVo category = new CategoryInfoVo();
			category.setCategoryId(categoryId);
			categoryInfoList.add(category);
			req.setCategoryInfo(categoryInfoList);
		}
		req.setClientId(clientId);
		req.setOutletName(keyword);
		try {
				LogCvt.info(">>进入按定位信息查询门店列表");
				resp = outletService.getNearbyOutlet(page, req, distance,sortBy);
			if(resp.getPage() != null) {
				BeanUtils.copyProperties(pagePojo, resp.getPage());
			}
			if(!ArrayUtil.empty(resp.getOutletDetailSimpleInfoVoList())) {
				for(OutletDetailSimpleInfoVo tmp : resp.getOutletDetailSimpleInfoVoList()) {
					pojo = new OutletSimplePojo();
					BeanUtils.copyProperties(pojo, tmp);
					
					//商户类型集合
					if( !ArrayUtil.empty(tmp.getTypeInfo()) ) {
						StringBuilder type=new StringBuilder();
						for(TypeInfoVo temp : tmp.getTypeInfo()) {
							type.append(temp.getType()+",");
						}
						pojo.setTypeInfo(type.toString());
					}
					
					//封装门店分类集合
					if(tmp.getCategoryInfoSize() > 0) {
						List<CategoryInfoPojo> categoryList = new ArrayList<CategoryInfoPojo>();
						CategoryInfoPojo categoryInfo = null;
						for(CategoryInfoVo categoryTemp : tmp.getCategoryInfo()) {
							categoryInfo = new CategoryInfoPojo();
							BeanUtils.copyProperties(categoryInfo, categoryTemp);
							categoryList.add(categoryInfo);
						}
						pojo.setCategoryList(categoryList);
					}
					outletList.add(pojo);
				}
			}
			resResult.put("page", pagePojo);
			resResult.put("outletList", outletList);
		} catch (TException e) {
			LogCvt.info("门店列表查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店列表查询异常");
		}
		return resResult;
	}
	
	/**
	 * @desc 获取门店相册列表
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年4月3日 下午5:16:04
	 * @param 
	 * @return 
	 */
	public HashMap<String, Object> getOutletPhotoList(String clientId, String outletId) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		MerchantOutletPhotoVo req = new MerchantOutletPhotoVo();
		List<MerchantOutletPhotoVo> resp = null;
		List<OutletPhotoPojo> photoList = new ArrayList<OutletPhotoPojo>();
		OutletPhotoPojo photoPojo = null;
		//封装请求对象
		req.setOutletId(outletId);
		req.setIsDefault(false);
		try {
			resp = merchantOutletPhotoService.getMerchantOutletPhoto(req);
			if(!ArrayUtil.empty(resp)) {
				for(MerchantOutletPhotoVo tmp : resp) {
					photoPojo = new OutletPhotoPojo();
					BeanUtils.copyProperties(photoPojo, tmp);
					photoList.add(photoPojo);
				}
			}
			resResult.put("outletPhotoList", photoList);
		} catch (TException e) {
			LogCvt.info("门店相册列表查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "门店相册列表查询异常");
		}
		return resResult;
	}
	
	
	
	/**
	  * 方法描述：根据区域ID查询对应区域下门店集合
	  * @param: areaId　区域ID
	  * @return:  门店集合
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月16日 下午2:39:07
	  */
	public HashMap<String, Object> getOutlet(Long areaId, String clientId){
				HashMap<String, Object> resResult = new HashMap<String, Object>();
				OutletVo outletVo=new OutletVo();
				outletVo.setIsEnable(true);
				outletVo.setOutletStatus(true);
				outletVo.setAreaId(areaId);
				outletVo.setClientId(clientId);
				List<OrgVo>  list=null;
				List<ProductOutletPojo> mlist=null;
				try {
					list=orgService.getOrgByAreaId(clientId, areaId);				
					
					//返回集合存在数据，转换pojo
					if( list != null && list.size() != 0 ){
						mlist=new ArrayList<ProductOutletPojo>();
						ProductOutletPojo outletPojo=null;  
						//属性copy
						for ( OrgVo temp : list){
								outletPojo=new ProductOutletPojo();
								outletPojo.setOutletName(temp.getOrgName());
								outletPojo.setOutletId(temp.getOutletId());
								outletPojo.setAreaId(String.valueOf (temp.getAreaId() ) );
								outletPojo.setOrgCode(temp.getOrgCode());
								BeanUtils.copyProperties(outletPojo, temp);
								mlist.add(outletPojo);
						}
						
					}
					resResult.put("resResult", mlist);
				} catch (TException e) {
					LogCvt.info("根据区域ID获取门店列表查询异常" + e.getMessage(), e);
				}
				return resResult;
	}
	
	

	/**
	 * getMerchantCategoryList:(商户分类分级查询列表)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月2日 下午4:36:16
	 * @param clientId
	 * @param parentId
	 * @param pageNumber
	 * @param pageSize
	 * @param lastPageNumber
	 * @param firstRecordTime
	 * @param lastRecordTime
	 * @return
	 * 
	 */
	public HashMap<String, Object> getMerchantCategoryList(String clientId, Long parentId,Long areaId) {
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		List<MerchantCategoryVo> respList = null;	//返回全部结果		
		MerchantCategoryPojo categoryPojo = null;
		List<MerchantCategoryPojo> categoryList = new ArrayList<MerchantCategoryPojo>();

		//封装请求对象
		MerchantCategoryVo req = new MerchantCategoryVo();
		req.setClientId(clientId);
		if(parentId != null) {
			req.setParentId(parentId);
		}else {
			req.setParentId(0L);
		}
		if(areaId != null) {
			req.setAreaId(areaId);
		}
		try {
			//查询全部商户分类
			LogCvt.info(">>进入全部商户分类查询");
			respList = merchantCategoryService.getMerchantCategoryByH5(req);
			if(!ArrayUtil.empty(respList)) {
				for(MerchantCategoryVo tmp : respList) {
					categoryPojo = new MerchantCategoryPojo();
					BeanUtils.copyProperties(categoryPojo, tmp);
					categoryList.add(categoryPojo);
				}
			}
			resResult.put("categoryList", categoryList);
			
		} catch (TException e) {
			LogCvt.info("商户分类分级查询列表查询异常" + e.getMessage(), e);
			resResult.put("code", "9999");
			resResult.put("message", "商户分类分级查询列表查询异常");
		} 
		return resResult;
	}
	
	
	/**
	 * getSimpleOutletDetail:获取简单的门店信息
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2016年1月4日 下午2:23:10
	 * @return
	 * 
	 */
	public HashMap<String, Object> getSimpleOutletDetail(String clientId ,String outletId){
		HashMap<String, Object> resResult = new HashMap<String, Object>();
		
		OutletVo req = new OutletVo();
		//封装请求对象
		req.setClientId(clientId);
		req.setOutletId(outletId);
		OutletVo resp = null;
		List<OutletVo> respList = null;
		 String merchantId = "";
		try {
			respList = outletService.getOutlet(req);
			if(respList != null && respList.size() > 0) {
				resp = respList.get(0);
				if(resp != null){
					merchantId = resp.getMerchantId();
					resResult.put("outletId", resp.getOutletId());
					resResult.put("outletName", resp.getOutletName());
					//因需求临时调整，折扣比例暂定不使用
					//resResult.put("discountRate", resp.getDiscountRate());
					resResult.put("discountRate", "");
					resResult.put("preferStatus", resp.isPreferStatus());
					//商户类型
					String typeInfo="";
					if( !ArrayUtil.empty(resp.getTypeInfo()) ) {
						StringBuilder type=new StringBuilder();
						for(TypeInfoVo temp : resp.getTypeInfo()) {
							type.append(temp.getType()+",");
						}
						typeInfo=type.toString();
					}
					resResult.put("typeInfo", typeInfo);
				}
			}
			//获取商户信息
			if(StringUtil.isNotBlank(merchantId)){
				MerchantVo merchant = merchantService.getMerchantByMerchantId(merchantId);
				resResult.put("merchantId", merchant.getMerchantId());
				resResult.put("merchantName", merchant.getMerchantName());
			}
		} catch (TException e) {
			LogCvt.info("门店简单详情查询异常", e);
		}
		return resResult;
	}

}
