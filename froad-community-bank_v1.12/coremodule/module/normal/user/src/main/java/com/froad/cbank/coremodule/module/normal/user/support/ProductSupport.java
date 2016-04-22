package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
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
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.enums.DeliveryType;
import com.froad.cbank.coremodule.module.normal.user.pojo.AreaPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.FindActivePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.GroupProductPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.OutletProductPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductBuyLimitPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductCategoryPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductListPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductOutletPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductPojo;
import com.froad.thrift.service.ActiveSearchService;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.OrderService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.ProductCategoryService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.ShoppingCartService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.FiledSort;
import com.froad.thrift.vo.GroupProductOutletVo;
import com.froad.thrift.vo.GroupProductPageVoRes;
import com.froad.thrift.vo.GroupProductVo;
import com.froad.thrift.vo.MerchantProductPageVoRes;
import com.froad.thrift.vo.OutletProductVoReq;
import com.froad.thrift.vo.OutletProductVoRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductActivitiesVo;
import com.froad.thrift.vo.ProductAreaVo;
import com.froad.thrift.vo.ProductBriefPageVoRes;
import com.froad.thrift.vo.ProductBriefVoRes;
import com.froad.thrift.vo.ProductCategoryPageVo;
import com.froad.thrift.vo.ProductCategoryVo;
import com.froad.thrift.vo.ProductFilterVoReq;
import com.froad.thrift.vo.ProductImageVo;
import com.froad.thrift.vo.ProductInfoVo;
import com.froad.thrift.vo.ProductOperateVoReq;
import com.froad.thrift.vo.ProductOutletPageVo;
import com.froad.thrift.vo.ProductOutletVo;
import com.froad.thrift.vo.ProductPageVoRes;
import com.froad.thrift.vo.ProductVo;
import com.froad.thrift.vo.QueryProductFilterVo;
import com.froad.thrift.vo.active.FindActiveRuleByProductVo;
import com.froad.thrift.vo.active.FindActiveRuleListResVo;
import com.froad.thrift.vo.active.FindActiveRuleResVo;
import com.froad.thrift.vo.active.FindActiveVo;
import com.froad.thrift.vo.active.MinatoSingleLocationVo;
import com.froad.thrift.vo.active.MinatoSingleParamVo;
import com.froad.thrift.vo.active.MinatoSingleProductListVo;
import com.froad.thrift.vo.active.MinatoSingleProductVo;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoReq;
import com.froad.thrift.vo.order.GetMemberBuyLimitVoRes;


	/**
	 * 类描述：商品x相关接口支持类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年3月25日 下午2:13:54 
	 */
@Service
public class ProductSupport extends BaseSupport {

	@Resource
	private ShoppingCartService.Iface shoppingCartService;
	
	@Resource
	private ProductService.Iface productService; 
	
	@Resource
	private ProductCategoryService.Iface productCategoryService;
	
	@Resource
	private AreaService.Iface areaService;
	
	@Resource
	private OrderService.Iface orderService;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private OutletService.Iface outletService;
	
	@Resource
	private VipSupport vipSupport;
	
	@Resource
	private CartSupport cartSupport;
	
	@Resource
	private ActiveSearchService.Iface activeSearchService;
	
	/**
	  * 方法描述：获取商品列表
	  * @param merchantName:商户名称,
				type:0-不限1-团购2-预售
				categoryId:商品分类id，
				isSellHot:(true ,sfalse)
				presellNum:预售期(1正在预售,2下期预售,3往期预售),
				orderField:排序字段
				权重:字段-排序方式（权重越高排序越优先,排序方式为desc或asc）
	  * @return 
	  * @version 1.0
	  * @author 刘超 liuchao@f-road.com.cn
	  * @time 2015年3月25日 下午2:43:32
	  */
	public HashMap<String, Object> getProductList(String clientId,String merchantId,String filter,PagePojo pagePojo){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		ProductFilterVoReq productFilterVoReq=new ProductFilterVoReq();
		productFilterVoReq.setClientId(clientId);
		productFilterVoReq.setMerchantId(merchantId);
		//处理掉json中的转义符号
		productFilterVoReq.setFilter(filter.replace("\\", ""));
		
		ProductBriefPageVoRes pageVoRes=null;
		PagePojo page=new PagePojo();
		List<ProductPojo> plist = null;
		ProductPojo  productPojo=null;
		
		try {

			pageVoRes = productService.queryProducts(productFilterVoReq, pageVo);
		
			if(pageVoRes.isSetPage()){
				BeanUtils.copyProperties(page,pageVoRes.getPage());
			}
			 
			List<String> ids = new ArrayList<String>();
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			if( pageVoRes.getProductBriefVoList() !=null && pageVoRes.getProductBriefVoList().size() != 0     ){
				 plist=new ArrayList<ProductPojo>();
				 for( int i=0 ; i<pageVoRes.getProductBriefVoList().size() ; i++  ){
					 ProductBriefVoRes temp = pageVoRes.getProductBriefVoList().get(i);
					 productPojo=new ProductPojo();
					 BeanUtils.copyProperties(productPojo,temp);
					 ids.add(temp.getProductId());
					 map.put(temp.getProductId(), i);
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
				// 处理满减活动 \	
				 
				FindActiveRuleByProductVo findActiveRuleByProductVo = new FindActiveRuleByProductVo();
				findActiveRuleByProductVo.setClientId(clientId);
				findActiveRuleByProductVo.setProductIdList(ids);
				try{
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
				}catch(Exception e){
					 LogCvt.error("商品相关活动查询接口出错", e); 
				}
			}
			
		} catch (TException e) {
			LogCvt.error("获取商品列表出错", e);
		}
		resMap.put("page", page);
		resMap.put("productList",plist);
		return resMap;
	}
	
	/**
	  * 方法描述：获取商品详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月25日 下午2:43:32
	  */
	public HashMap<String, Object> getProductDetail(String clientId, Long memberCode, String productId, String type,Long areaId){
		LogCvt.info("获取商品详情，clientId："+clientId+",productId:"+productId);
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		//分页page对象
		ProductOperateVoReq productFilterVoReq = new ProductOperateVoReq();
		productFilterVoReq.setClientId(clientId);
		productFilterVoReq.setProductId(productId);
		productFilterVoReq.setType(type);
		if(areaId != null ){
			productFilterVoReq.setCityId(areaId);
		}
//		productFilterVoReq.setFilter(filter);
		
		ProductInfoVo productInfoVo=null;
		try {
			productInfoVo = productService.queryProductDetail(productFilterVoReq);
			ProductDetailPojo detailPojo=null;
			ProductDetailPojo.MerchantOutlet outlet =null;
			ProductDetailPojo.ImageInfo  imageInfo=null; 
			ProductDetailPojo.ProductActivitie activitie=null;
				
			//封装商品详情Po
			if(productInfoVo.getProduct() != null){
				detailPojo=new ProductDetailPojo();
				ProductVo temp= productInfoVo.getProduct();
				BeanUtils.copyProperties(detailPojo,temp);
				//特殊处理 ， 秒杀商品为1时，才为正常状态下的秒杀商品
				 if("1".equals(temp.getIsSeckill())){ 						 
					 if(temp.getServerTime() > temp.getSecStartTime() && temp.getServerTime() < temp.getSecEndTime()){
						//秒杀中
						 detailPojo.setIsSeckill("1");
					 }
					 if(temp.getServerTime() < temp.getSecStartTime()){
						 //即将秒杀
						 detailPojo.setIsSeckill("2");
					 }
					 if(temp.getServerTime() > temp.getSecEndTime()){
						 //秒杀过期
						 detailPojo.setIsSeckill("0");
					 }
				 }else{
					 detailPojo.setIsSeckill("0");
				 }
				 
				//商品限购相关转换实体
				ProductBuyLimitPojo buyLimit=new ProductBuyLimitPojo();
				if(productInfoVo.getProduct().isIsLimit() && productInfoVo.getBuyLimit() != null ){
					BeanUtils.copyProperties(buyLimit, productInfoVo.getBuyLimit() );
				}
				detailPojo.setBuyLimit(buyLimit);	
				
				//封装图片集合
				if(productInfoVo.getImage() !=null &&  productInfoVo.getImage().size() != 0 ){
					List<ProductDetailPojo.ImageInfo> imageList=new ArrayList<ProductDetailPojo.ImageInfo>();
					for(ProductImageVo image:productInfoVo.getImage() ){
						imageInfo=new ProductDetailPojo().new ImageInfo(); 
							BeanUtils.copyProperties(imageInfo, image);
							imageList.add(imageInfo);
					}
					detailPojo.setImageInfoList(imageList);
				}
					
				//封装门店集合
				if(productInfoVo.getOutlet() !=null &&  productInfoVo.getOutlet().size() != 0 ){
					//门店集合是该商品在该地区下(areaId)的提货网点集合
					List<ProductDetailPojo.MerchantOutlet>  outletList = new ArrayList<ProductDetailPojo.MerchantOutlet>();
//					for(ProductOutletVo temp:productInfoVo.getOutlet() ){
							outlet =  new ProductDetailPojo().new MerchantOutlet();
							BeanUtils.copyProperties(outlet, productInfoVo.getOutlet().get(0));
							outletList.add(outlet);
//					}
					detailPojo.setOutletNum(productInfoVo.getOutlet().size());
					detailPojo.setMerchantOutletList(outletList);
				}else if(DeliveryType.home_or_take == DeliveryType.getType(detailPojo.getDeliveryOption())){
					//如果该商品配送方式是送货上门+网点自提，网点集合为空，修改配送方式为只有送货上门
					detailPojo.setDeliveryOption(DeliveryType.home.getCode());
				}
				
				//封装商品活动
				if( !ArrayUtil.empty(productInfoVo.getActivities()) ){
					List<ProductDetailPojo.ProductActivitie>  activities = new ArrayList<ProductDetailPojo.ProductActivitie>();
					for(ProductActivitiesVo activitieVo:productInfoVo.getActivities() ){
						activitie=new ProductDetailPojo().new ProductActivitie();
						BeanUtils.copyProperties(activitie, activitieVo);
						activities.add(activitie);
					}
					detailPojo.setProductActivities(activities);
				}
				
				// 处理满减活动 (商品详情--返回集合型满减活动列表)			
				ArrayList<String> ids = new ArrayList<String>();
				ids.add(productId);
				FindActiveRuleByProductVo findActiveRuleByProductVo = new FindActiveRuleByProductVo();
				findActiveRuleByProductVo.setClientId(clientId);
				findActiveRuleByProductVo.setProductIdList(ids);
				if(memberCode != null ){
					findActiveRuleByProductVo.setMemberCode(memberCode.toString());
				}
				try{
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
							detailPojo.setActivePojo(alist);				
						}
					}
				}catch(Exception e){
					 LogCvt.error("商品相关活动查询接口出错", e); 
				}

				if(memberCode != null) {
					//根据用户ID + 商品ID 获取 商品是否已收藏 状态
					boolean isCollected = productService.isExitsStoreProductInfo(clientId, memberCode, productId);
					detailPojo.setIsCollected(isCollected);
					
					//查询用户可购买商品数量的总数
					boolean isVip = vipSupport.isOrNotVip(memberCode,clientId);
					GetMemberBuyLimitVoReq getMemberBuyLimitVoReq = new GetMemberBuyLimitVoReq();
					
					
					getMemberBuyLimitVoReq.setClientId(clientId);
					getMemberBuyLimitVoReq.setMemberCode(memberCode);
					getMemberBuyLimitVoReq.setProductId(productId);
					getMemberBuyLimitVoReq.setIsVip(isVip);
					//设置商户ID
					getMemberBuyLimitVoReq.setMerchantId(productInfoVo.getProduct().getMerchantId());
					
					GetMemberBuyLimitVoRes res=orderService.getMemberBuyLimit(getMemberBuyLimitVoReq);
					detailPojo.setNum(res.getQuantity());
					detailPojo.setVipNum(res.getVipQuantity());
					detailPojo.setTotalNum(res.getTotalQuantity());
				}
			}
			resMap.put("resResult", detailPojo);
		} catch (TException e) {
			LogCvt.error("获取商品详情出错", e);
		}
		return resMap;
	}
	
	
	
	
	
	
	/**
	  * 方法描述：获取商品分类
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月26日 下午2:04:01
	  */
	public HashMap<String, Object> queryProductCategorys(int pageNumber,int pageSize,String clientId,Long parentId,PagePojo pagePojo){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pageNumber);
		pageVo.setPageSize(pageSize);
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		//查询条件
		ProductCategoryVo productCategoryVo=new ProductCategoryVo();
		productCategoryVo.setClientId(clientId);
		if(parentId != null){
			productCategoryVo.setParentId(parentId);
		}
		
		ProductCategoryPageVo productCategoryPageVo=null;
		try {
			LogCvt.info("调用接口传入参数:"+JSON.toJSONString(productCategoryVo)  );
			productCategoryPageVo=productCategoryService.queryProductCategorys(productCategoryVo, pageVo);
			
			//转换分页实体
			PagePojo page=new PagePojo();
			BeanUtils.copyProperties(page, productCategoryPageVo.getPage());
			
			//转换分类数据实体
			ProductCategoryPojo categoryPojo=null;
			List<ProductCategoryPojo> clist =new ArrayList<ProductCategoryPojo>();
			
			if(productCategoryPageVo.getCategoryVoList()  != null  &&  productCategoryPageVo.getCategoryVoList().size() !=0  ){
				for(ProductCategoryVo temp:productCategoryPageVo.getCategoryVoList()) {
					categoryPojo=new ProductCategoryPojo();
					BeanUtils.copyProperties(categoryPojo, temp);
					clist.add(categoryPojo);
				}
			}
			resMap.put("page", page );
			resMap.put("categoryList", clist );
		} catch (TException e) {
			LogCvt.error("获取商品分类出错", e);
		}
		return resMap;
	
	}
	
	
	/**
	  * 方法描述：获取预售商品对应区域下的网点信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:04:46
	  */
	public HashMap<String, Object> getProductOutlet(Long cityId , Long areaId,String productId){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		ProductAreaVo productAreaVo =new ProductAreaVo();
		productAreaVo.setAreaId(areaId);
		productAreaVo.setProductId(productId);
		productAreaVo.setCityId(cityId);
		
		List<ProductAreaVo> list=null;
		ProductOutletPojo productOutletPojo =null;
		List<ProductOutletPojo> alist=null;
		try {
			list=productService.getProductAreaOutlets(productAreaVo);
			if(list != null && list.size() !=0 ){
				alist=new ArrayList<ProductOutletPojo>();
				for(ProductAreaVo  temp : list){
					productOutletPojo=new ProductOutletPojo();
						BeanUtils.copyProperties(productOutletPojo, temp);
						alist.add(productOutletPojo);
				}
			}
		resMap.put("resResult",alist);
		} catch (TException e) {
			LogCvt.error("查询预售商品对应区域下网点列表出错", e);
		}
		return resMap;
	}
	
	
	
	/**
	  * 方法描述：根据二维码扫码结果查询面对面商品详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:04:46
	  */
	public HashMap<String, Object> getProductDetailByQrcode( String  clientId,String content){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		OutletProductVoReq outletProductVoReq=new OutletProductVoReq();
		outletProductVoReq.setClientId(clientId);
		outletProductVoReq.setQrCode(content);
		
		OutletProductPojo outletProductPojo=null; 

			try {
				OutletProductVoRes productVoRes= productService.getOutletProduct(outletProductVoReq);
				if(productVoRes != null && !StringUtil.empty(productVoRes.getProductId()) ){
					outletProductPojo=new OutletProductPojo();	
					BeanUtils.copyProperties(outletProductPojo, productVoRes);
					resMap.put("resResult",outletProductPojo);
				}else{
					resMap.put("code","9999");
					resMap.put("message","收银支付已支付或不存在！");
				}
			} catch (TException e) {
				LogCvt.error("根据二维码扫码结果查询面对面商品详情出错", e);
			}
		return resMap;
	}
	
	
	
	//查询商品详情
	public ProductInfoVo getMerchantProductDetail(String clientId, String productId, String merchantId){
		ProductOperateVoReq productOperateVo = new  ProductOperateVoReq();
		productOperateVo.setClientId(clientId);
		productOperateVo.setProductId(productId);
		productOperateVo.setMerchantId(merchantId);
		ProductInfoVo  productInfoVo = null;
		try {
			productInfoVo = productService.getMerchantProductDetail(productOperateVo);
		} catch (TException e) {
			LogCvt.error("商品详情接口调用异常: productId:" + productId , e);
		}
		return productInfoVo;
	}
	
	
	/**
	  * 方法描述：获取预售商品对应区域下的网点信息(分页查询)
	  * @param:  Long areaId（区域ID）,   String productId ( 预售商品ID),     PagePojo pagePojo（分页对象）
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:04:46
	  */
	public HashMap<String, Object> getProductOutletByPage( Long areaId,String productId,String clientId,PagePojo pagePojo ){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		List<ProductOutletVo> list=null;
		PagePojo page=new PagePojo();
		ProductOutletPojo productOutletPojo =null;
		List<ProductOutletPojo> alist=null;
		try {
			ProductOutletPageVo outletPageVo= productService.findProductsForAreaByPage(productId, areaId, clientId, pageVo);
			//copy 分页对象
			if(outletPageVo.isSetPage()){
				BeanUtils.copyProperties(page,outletPageVo.getPage());
			}
			
			list=outletPageVo.getProductOutletVo();
			if(list != null && list.size() !=0 ){
				alist=new ArrayList<ProductOutletPojo>();
				for(ProductOutletVo  temp : list){
					productOutletPojo=new ProductOutletPojo();
						BeanUtils.copyProperties(productOutletPojo, temp);
						alist.add(productOutletPojo);
				}
			}
			
		resMap.put("page",page);	
		resMap.put("resResult",alist);
		
		} catch (TException e) {
			LogCvt.error("查询预售商品对应区域下网点列表出错", e);
		}
		return resMap;
	}
	
	/**
	  * 方法描述：预售商品绑定地区查询
	  * @param:   Long areaId （市级区域ID）,   String productId (预售商品Id)
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:02:07
	  */
	public HashMap<String, Object> getProductAreaList( Long areaId,String productId,String clientId){
		
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		List<AreaVo> list=null;
		AreaPojo areaPojo=null;
		List<AreaPojo> alist=null;
		try {
			LogCvt.info("调用预售商品绑定地区查询接口传入参数:[productId]:"+productId+",[areaId]:"+areaId +",[clientId]:"+clientId);
			list=productService.findAreaForCityByList(productId, areaId, clientId);
			if(list != null && list.size() !=0 ){
				alist=new ArrayList<AreaPojo>();
				for(AreaVo  temp : list){
						areaPojo=new AreaPojo();
						BeanUtils.copyProperties(areaPojo, temp);
						alist.add(areaPojo);
				}
			}
			resMap.put("resResult",alist);
		} catch (TException e) {
			LogCvt.error("地区查询接口出错", e);
		}
		return resMap;
	}
	

	
	/**
	  * 方法描述：获取特惠商品列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年7月3日 下午12:50:17
	  */
	public HashMap<String, Object> getGourpProduct(String clientId,String areaId,String distance,boolean isRecommend,String latitude,String longitude,String productCategoryId,String sortName
			,String name, String merchantId, PagePojo pagePojo){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		
		
		//筛选条件
		QueryProductFilterVo productFilterVo=new QueryProductFilterVo();
		productFilterVo.setClientId(clientId);
		
		if(!StringUtil.empty(merchantId)){
			productFilterVo.setMerchantId(merchantId);
		}
		if(!StringUtil.empty(areaId)){
			productFilterVo.setAreaId(Long.parseLong(areaId));
		}
		//附近距离
		if( !StringUtil.empty(distance) ){
			productFilterVo.setDistance(Long.parseLong(distance));
		}
		//是否推荐
		productFilterVo.setIsRecommend(isRecommend);
		//定位经度和纬度
		if(!StringUtil.empty(latitude) && !StringUtil.empty(longitude) ){
			productFilterVo.setLatitude(Double.parseDouble(latitude));
			productFilterVo.setLongitude(Double.parseDouble(longitude));
		}
		//商品分类ID
		if(!StringUtil.empty(productCategoryId) ){
			productFilterVo.setProductCategoryId(Long.parseLong(productCategoryId));
		}
		//商品类型，默认设置团购商品类型
		productFilterVo.setType("1");
		
		//商品名字
		if(!StringUtil.empty(name)){
			productFilterVo.setProductName(name);
		}
		
		//排序条件
		 List<FiledSort> sort=new ArrayList<FiledSort>(); 
		 FiledSort filedSort=new FiledSort();
		 //正数升序，负数降序
		 filedSort.setSortBy(-1);
		 //排序顺序
		 filedSort.setSortPrior(1);
		 filedSort.setSortName(sortName);
		 sort.add(filedSort);
		 
		try {
			PagePojo page=new PagePojo();
			List<GroupProductPojo> outletList=null;
			LogCvt.info("特惠商品列表查询传入参数："+JSON.toJSONString(productFilterVo));
			GroupProductPageVoRes res=productService.queryGroupProducts(productFilterVo, sort, pageVo);			
			if(res.isSetPage()){
				BeanUtils.copyProperties(page,res.getPage());
			}
			List<GroupProductOutletVo> rlist=res.getProductOutletVos();
			if(!ArrayUtil.empty(rlist)){
				outletList=new ArrayList<GroupProductPojo>();
				GroupProductPojo groupProductPojo = null;
				ProductListPojo productListPojo=null;
				//循环处理门店集合
				for(GroupProductOutletVo temp:rlist){
					groupProductPojo=new GroupProductPojo();
					BeanUtils.copyProperties(groupProductPojo, temp);
					
					//循环处理门店下对应的商品集合
					List<ProductListPojo> plist=new ArrayList<ProductListPojo>();
					List<String> ids = new ArrayList<String>();
					HashMap<String, Integer> map = new HashMap<String, Integer>();
					for(int i=0 ; i < temp.getGroupProductVos().size() ; i++){
						GroupProductVo product = temp.getGroupProductVos().get(i);
						productListPojo=new ProductListPojo();
						ids.add(product.getProductId());
						map.put(product.getProductId(), i);
						BeanUtils.copyProperties(productListPojo, product);
						//特殊处理 ， 秒杀商品为1时，才为正常状态下的秒杀商品
						 if("1".equals(product.getIsSeckill())){
							 if(product.getServerTime() > product.getSecStartTime() && product.getServerTime() < product.getSecEndTime()){
									//秒杀中
								 productListPojo.setIsSeckill("1");
								 }
								 if(product.getServerTime() < product.getSecStartTime()){
									 //即将秒杀
									 productListPojo.setIsSeckill("2");
								 }
								 if(product.getServerTime() > product.getSecEndTime()){
									 //秒杀过期
									 productListPojo.setIsSeckill("0");
								 }
						 }else{
							 productListPojo.setIsSeckill("0");
						 }
						 plist.add(productListPojo);
					}
					
					
					// 处理满减活动 开始
					FindActiveRuleByProductVo findActiveRuleByProductVo = new FindActiveRuleByProductVo();
					findActiveRuleByProductVo.setClientId(clientId);
					findActiveRuleByProductVo.setProductIdList(ids);
					try{
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
						// 处理满减活动 结束
					}catch(Exception e){
						 LogCvt.error("商品相关活动查询接口出错", e); 
					} 
					groupProductPojo.setProductList(plist);
					outletList.add(groupProductPojo);
				}
			}
			resMap.put("outletList", outletList);
			resMap.put("page", page);
		} catch (TException e) {
			LogCvt.error("特惠商品列表查询接口出错", e);
		}
		return resMap;
	}
	
	
	
	/**
	 * getGourpProductDetail:(特惠商品列表商户详情接口)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年8月7日 上午11:56:33
	 * @return
	 * 
	 */
	public HashMap<String, Object> getGourpProductDetail(String clientId,String merchantId,String latitude,String longitude,String areaId,PagePojo pagePojo) {
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		//筛选条件
		QueryProductFilterVo productFilterVo=new QueryProductFilterVo();
		
		productFilterVo.setClientId(clientId);
		productFilterVo.setMerchantId(merchantId);
		if(!StringUtil.empty(areaId)){
			productFilterVo.setAreaId(Long.parseLong(areaId));
		}
		//定位经度和纬度
		if(!StringUtil.empty(latitude) && !StringUtil.empty(longitude) ){
			productFilterVo.setLatitude(Double.parseDouble(latitude));
			productFilterVo.setLongitude(Double.parseDouble(longitude));
		}
				
		MerchantProductPageVoRes res;
		GroupProductPojo groupProductPojo = new GroupProductPojo();
		try {
			res = productService.queryMerchantProducts(productFilterVo, null, pageVo);
			PagePojo page=new PagePojo();
			if(res.isSetPage()){
				BeanUtils.copyProperties(page,res.getPage());
			}
			
			BeanUtils.copyProperties(groupProductPojo,res.getMerchantOutletVo());
			
			//循环处理商品集合
			ProductListPojo productListPojo=null;
			List<ProductListPojo> plist=new ArrayList<ProductListPojo>();
			List<String> ids = new ArrayList<String>();
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			for(int i=0 ; i < res.getGroupProductVos().size() ; i++){
				GroupProductVo product=res.getGroupProductVos().get(i);
				ids.add(product.getProductId());
				map.put(product.getProductId(), i);
				productListPojo=new ProductListPojo();				
				BeanUtils.copyProperties(productListPojo, product);
				//特殊处理 ， 秒杀商品为1时，才为正常状态下的秒杀商品
				 if("1".equals(product.getIsSeckill())){
					 if(product.getServerTime() > product.getSecStartTime() && product.getServerTime() < product.getSecEndTime()){
							//秒杀中
						 productListPojo.setIsSeckill("1");
						 }
						 if(product.getServerTime() < product.getSecStartTime()){
							 //即将秒杀
							 productListPojo.setIsSeckill("2");
						 }
						 if(product.getServerTime() > product.getSecEndTime()){
							 //秒杀过期
							 productListPojo.setIsSeckill("0");
						 }
				 }else{
					 productListPojo.setIsSeckill("0");
				 }
				 plist.add(productListPojo);
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
			groupProductPojo.setProductList(plist);
			resMap.put("page", page);
			resMap.put("result", groupProductPojo);
		} catch (TException e) {
			LogCvt.error("特惠商品门店详情查询接口出错", e);
		}
		return resMap;
	}
	

	/**
	 * getGourpProductByCategory:(类目营销商品搜索)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年10月10日 上午11:14:51
	 * @param clientId
	 * @param merchantId
	 * @param latitude
	 * @param longitude
	 * @param areaId
	 * @param pagePojo
	 * @return
	 * 
	 */
	public HashMap<String, Object> getGourpProductByCategory(String clientId,String merchantId,String categoryId,String type,String latitude,String longitude,String areaId,String productName,String distance,String FiledSort, PagePojo pagePojo) {
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		//筛选条件
		QueryProductFilterVo productFilterVo=new QueryProductFilterVo();
		
		productFilterVo.setClientId(clientId);
		//商户Id
		if(!StringUtil.empty(merchantId)){
			productFilterVo.setMerchantId(merchantId);
		}
		
		if(!StringUtil.empty(areaId)){
			productFilterVo.setAreaId(Long.parseLong(areaId));
		}
		
		//商品类别Id
		if(!StringUtil.empty(categoryId) ){
			productFilterVo.setProductCategoryId(Long.parseLong(categoryId));
		}
		
		//定位经度和纬度
		if(!StringUtil.empty(latitude) && !StringUtil.empty(longitude) ){
			productFilterVo.setLatitude(Double.parseDouble(latitude));
			productFilterVo.setLongitude(Double.parseDouble(longitude));
		}
		//距离条件
		if(!StringUtil.empty(distance)){
			productFilterVo.setDistance(Double.parseDouble(distance));
		}
		//商品名称
		productFilterVo.setProductName(productName);
		//商品类型
		productFilterVo.setType(type);
		
		ProductPageVoRes res;
		List<FiledSort>  filedSorts = new ArrayList<FiledSort>();
		try{
			if(!StringUtil.empty(FiledSort)){
				 FiledSort filedSort = JSON.parseObject (FiledSort, FiledSort.class);
				 filedSorts.add(filedSort);	
			}
		}catch(Exception e ){			
			LogCvt.error("解析类目营销商品查询排序出错", e);
		}
		
		try {
			res = productService.searchProductByType(productFilterVo , filedSorts, pageVo);
			PagePojo page=new PagePojo();
			if(res.isSetPage()){
				BeanUtils.copyProperties(page,res.getPage());
			}
			
			//循环处理商品集合
			ProductListPojo productListPojo=null;
			List<ProductListPojo> plist=new ArrayList<ProductListPojo>();
			if(res.isSetProductVos()){
				List<String> ids = new ArrayList<String>();
				HashMap<String, Integer> map = new HashMap<String, Integer>();
				for( int i=0 ; i < res.getProductVos().size() ; i++ ){
					GroupProductVo product = res.getProductVos().get(i);
					ids.add(product.getProductId());
					map.put(product.getProductId(), i);
					productListPojo=new ProductListPojo();
					BeanUtils.copyProperties(productListPojo, product);
					
					//特殊处理 ， 秒杀商品为1时，才为正常状态下的秒杀商品
					 if("1".equals(product.getIsSeckill())){
						 if(product.getServerTime() > product.getSecStartTime() && product.getServerTime() < product.getSecEndTime()){
								//秒杀中
							 productListPojo.setIsSeckill("1");
							 }
							 if(product.getServerTime() < product.getSecStartTime()){
								 //即将秒杀
								 productListPojo.setIsSeckill("2");
							 }
							 if(product.getServerTime() > product.getSecEndTime()){
								 //秒杀过期
								 productListPojo.setIsSeckill("0");
							 }
					 }else{
						 productListPojo.setIsSeckill("0");
					 }
					 plist.add(productListPojo);
				}
				
				// 处理满减活动 
				FindActiveRuleByProductVo findActiveRuleByProductVo = new FindActiveRuleByProductVo();
				findActiveRuleByProductVo.setClientId(clientId);
				findActiveRuleByProductVo.setProductIdList(ids);
				 try{
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
				 }catch(Exception e){
					 LogCvt.error("商品相关活动查询接口出错", e); 
				 }
				
			}
			resMap.put("page", page);
			resMap.put("list", plist);
		} catch (TException e) {
			LogCvt.error("类目营销商品查询接口出错", e);
		}
		return resMap;
	}
	
	
	
	/**
	 * getProductCategroy:(商品分类分级查询接口)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月2日 下午4:24:39
	 * @param clientId
	 * @param parentId
	 * @param areaId
	 * @return
	 * 
	 */
	public  HashMap<String, Object> getProductCategroy(String clientId ,Long parentId, Long areaId ){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		List<ProductCategoryVo> respList = null;
		ProductCategoryPojo proCategoryPojo = null;
		List<ProductCategoryPojo> categoryList = new ArrayList<ProductCategoryPojo>();
		//请求参数验证
		if(areaId==null){
			areaId=(long) 0;
		}
		if(parentId==null){
			parentId=(long) 0;
		}
		try {
			LogCvt.info(">>商品分类分级查询接口");
			respList = productCategoryService.queryH5ProductCategorys(clientId, parentId, areaId);
			if(!ArrayUtil.empty(respList)){
				for (ProductCategoryVo tmp :respList) {
					proCategoryPojo = new ProductCategoryPojo();
					BeanUtils.copyProperties(proCategoryPojo, tmp);
					categoryList.add(proCategoryPojo);
				}
			}
			resMap.put("list", categoryList);
		} catch (TException e) {
			LogCvt.info("商品分类分级查询接口查询异常" + e.getMessage(), e);
			resMap.put("code", "9999");
			resMap.put("message", "商品分类分级查询接口查询异常");
		}
		return resMap;
	}
	
	
	
	/**
	 * findProductListOfMinatoSingle:(去凑单商品列表支持接口)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月9日 下午2:13:19
	 * @return
	 * 
	 */
	public HashMap<String, Object> findProductListOfMinatoSingle(String clientId,String activeId,String areaId,String productId,Double latitude,Double longitude,String cookieId,String type,PagePojo pagePojo){
		HashMap<String, Object> resMap = new HashMap<String, Object>();		
		
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		//组装请求体
		MinatoSingleParamVo req = new MinatoSingleParamVo();
		req.setClientId(clientId);
		req.setActiveId(activeId);
		req.setProductId(productId);
		req.setAreaId(areaId);
		req.setProductType(type);
		//记录本次请求唯一标识
		req.setCookieId(cookieId);
		//经纬度
		if(longitude != null && latitude != null){
			MinatoSingleLocationVo locationVo = new MinatoSingleLocationVo();
			locationVo.setLatitude(latitude);
			locationVo.setLongitude(longitude);
			req.setMinatoSingleLocation(locationVo);
		}

		PagePojo page=new PagePojo();
		try {
			MinatoSingleProductListVo res = activeSearchService.findProductListOfMinatoSingle(pageVo,req);
			//转换分页实体
			BeanUtils.copyProperties(page, res.getPage());
			
			FindActivePojo activePojo = null;
			if(res.getActiveVo() != null){
				//活动对象
				activePojo = new FindActivePojo();
				BeanUtils.copyProperties(activePojo,res.getActiveVo());
			}
			resMap.put("active", activePojo);
			//商品数据
			List<ProductPojo> plist = new ArrayList<ProductPojo>();
			List<String> ids = new ArrayList<String>();
			HashMap<String, Integer> map = new HashMap<String, Integer>();
			if(!ArrayUtil.empty(res.getMinatoSingleProductVoList() )){
				ProductPojo productPojo = null;
				int i = 0;
				for(MinatoSingleProductVo productVo:res.getMinatoSingleProductVoList()  ){
					productPojo=new ProductPojo();
					ids.add(productVo.getProductId());
					map.put(productVo.getProductId(), i);
					BeanUtils.copyProperties(productPojo,productVo);
					plist.add(productPojo);
					i++;
				}
			}
			//查询商品活动
			FindActiveRuleByProductVo findActiveRuleByProductVo = new FindActiveRuleByProductVo();
			findActiveRuleByProductVo.setClientId(clientId);
			findActiveRuleByProductVo.setProductIdList(ids);
			
			 try{
					FindActiveRuleListResVo activeRuleListResVo = activeSearchService.findActiveRuleByProductIds(findActiveRuleByProductVo);
					if (!ArrayUtil.empty(activeRuleListResVo.getFindActiveRuleResList())) {
						for(FindActiveRuleResVo activeRuleResVo : activeRuleListResVo.getFindActiveRuleResList()){
							String pid = activeRuleResVo.getId();
							 if(!ArrayUtil.empty(activeRuleResVo.getFindActiveList())){
								 FindActivePojo allActivePojo =  null ;
								 List<FindActivePojo> list = new ArrayList<FindActivePojo>();
								for(FindActiveVo findActiveVo:activeRuleResVo.getFindActiveList()){
									allActivePojo = new FindActivePojo();
									BeanUtils.copyProperties(allActivePojo,findActiveVo);
									list.add(allActivePojo);
								} 
								int index = map.get(pid);
								plist.get(index).setActivePojo(list);
							 }				
						}
				    }
					 // 处理活动结束
			 }catch(Exception e){
				 LogCvt.error("商品相关活动查询接口出错", e); 
			 }
			
  			resMap.put("list", plist);
			resMap.put("page", page);
		} catch (TException e) {		
			LogCvt.error("去凑单商品列表支持接口出错", e);
		}
		return resMap;
	}
	
	/**
	 * 获取购物车商品数量
	 * @param clientId
	 * @param memberCode
	 * @author 周煜涵
	 * @date 2016年1月6日 上午10:39:19
	 * @return
	 */
	public Integer getCount(String clientId, long memberCode){
		Integer count = 0;
		try {
			count = shoppingCartService.getCartProductCount(memberCode,clientId);
		} catch (TException e) {
			e.printStackTrace();
		}
		return count;
	}
	
}
