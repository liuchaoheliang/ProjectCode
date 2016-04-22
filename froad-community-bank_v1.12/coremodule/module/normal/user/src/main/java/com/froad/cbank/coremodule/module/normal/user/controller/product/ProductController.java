package com.froad.cbank.coremodule.module.normal.user.controller.product;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.support.ProductSupport;


	/**
	 * 类描述：商品相关
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年3月23日 下午5:00:58 
	 */

@Controller
@RequestMapping
public class ProductController extends BasicSpringController {

	@Resource
	private ProductSupport productSupport;   
	
	/**
	  * 方法描述：获取商品列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月23日 下午5:41:50
	  */
	@FunctionModule
	@RequestMapping(value = "/product/list" , method = RequestMethod.GET)
	public void ProductList(ModelMap model, HttpServletRequest req,PagePojo pagePojo){
		model.clear();
		String clientId=getClient(req);
		
//		JSONObject obj=toJsonObj(req);
		//基础数据
		String merchantId=req.getParameter("merchantId");
		
		//条件过滤
		Map<String,Object> filter=new HashMap<String, Object>();
		filter.put("merchantName",req.getParameter("merchantName") );
		filter.put("type",req.getParameter("type") );
		filter.put("categoryId",req.getParameter("categoryId") );
		filter.put("isSellHot",req.getParameter("isSellHot") );
		filter.put("presellNum",req.getParameter("presellNum") );
		filter.put("orderField",req.getParameter("orderField") );
		filter.put("name",req.getParameter("name")==null?req.getParameter("name"):req.getParameter("name").trim() );
		filter.put("areaId",req.getParameter("areaId") );
		filter.put("outletId",req.getParameter("outletId") );
		filter.put("cityId",req.getParameter("cityId") );
		filter.put("areaCode",req.getParameter("areaCode") );
		
		//特惠商品查询所有商品
/*		if(req.getParameter("type") != null && "1".equals(req.getParameter("type"))) {
			filter.put("endTimeEnd", TimeUtil.getNextYearEnd().getTime()+"");
			filter.put("endTimeStart", Calendar.getInstance().getTimeInMillis()+"");
			filter.put("startTimeStart", TimeUtil.getPreviousYearFirst().getTime()+"");
		}*/
		
		//执行查询
		if( !StringUtil.empty(clientId) ){
			model.putAll(productSupport.getProductList(clientId,merchantId, JSON.toJSONString(filter),pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	/**
	  * 方法描述：获取商品详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月23日 下午5:41:50
	  */
	@FunctionModule
	@RequestMapping(value = "/product/detail" , method = RequestMethod.GET)
	public void getPresellDetail(ModelMap model, HttpServletRequest req, @RequestHeader(value="memberCode",defaultValue="" ) Long memberCode, String productId, String type,Long areaId){
		String clientId=getClient(req);
		if(!StringUtil.empty(productId) && !StringUtil.empty(clientId) ){
			model.putAll(productSupport.getProductDetail(clientId, memberCode, productId, type,areaId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
	}
	
	/**
	  * 方法描述：获取商品分类
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月23日 下午5:41:50
	  */
	@RequestMapping(value = "/product/category" , method = RequestMethod.GET)
	public void getCategory(ModelMap model,@RequestParam(value="pageNumber", defaultValue="1") int pageNumber,@RequestParam(value="pageSize", defaultValue="10") int pageSize,
							HttpServletRequest req,Long parentId,PagePojo pagePojo ){
		model.clear();
		String clientId=getClient(req);
		if(  !StringUtil.empty(clientId) ){
			model.putAll(productSupport.queryProductCategorys(pageNumber, pageSize, clientId, parentId,pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
	}
	
	
	
	/**
	  * 方法描述：根据商品ID+区域ID获取对应商品在该区域下的网店列表（限预售商品）
	  * @param: productId 预售商品ID  ， areaId 指定区域ID
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:08:45
	  */
	@RequestMapping(value = "/product/outlet" , method = RequestMethod.GET)
	public void getProductOutlet(ModelMap model,Long cityId,Long areaId,String productId){
		if(productId!=null && areaId!=null && cityId!=null ){
			model.putAll(productSupport.getProductOutlet(cityId,areaId, productId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	/**
	  * 方法描述：根据扫码结果查询面对面商品详情
	  * @param: clientId 客户端标识   ，   content  扫码结果内容
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:08:45
	  */
	@RequestMapping(value = "/product/detail/qrcode" , method = RequestMethod.GET)
	public void getProductDetail(ModelMap model,HttpServletRequest req,String content){
		String clientId=getClient(req);
		if(StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(content) ){
			model.putAll(productSupport.getProductDetailByQrcode(clientId, content));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	/**
	  * 方法描述：获取指定区域下该商品所绑定的区域集合
	  * @param: Long areaId : 定位到的市级区域ID，   String productId 指定的预售商品ID
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:08:45
	  */
	@RequestMapping(value = "/product/area/list" , method = RequestMethod.GET)
	public void getProductArea(ModelMap model,HttpServletRequest req,@RequestParam(value="areaId", defaultValue="0")Long areaId,String productId){
		String clientId=getClient(req);
		if(StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(productId) && areaId != null ){ 
			model.putAll(productSupport.getProductAreaList(areaId, productId, clientId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
	
	
	/**
	  * 方法描述：获取指定区域下该商品所绑定的网点集合
	  * @param: Long areaId : 定位到的市级区域ID，   String productId 指定的预售商品ID
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:08:45
	  */
	@RequestMapping(value = "/product/outlet/page" , method = RequestMethod.GET)
	public void getProductOutletByPage(ModelMap model,HttpServletRequest req,@RequestParam(value="areaId", defaultValue="0")Long areaId,String productId,PagePojo pagePojo){
		model.clear();
		String clientId=getClient(req);
		if(StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(productId) ){ 
			model.putAll(productSupport.getProductOutletByPage(areaId, productId, clientId,pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	/**
	  * 方法描述：获取特惠商品列表
	  * @param: Long areaId : 定位到的市级区域ID，   String distance 距离
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:08:45
	  */
	@FunctionModule
	@RequestMapping(value = "/product/group" , method = RequestMethod.GET)
	public void getGroupProduct(ModelMap model,HttpServletRequest req,@RequestParam(value="isRecommend", defaultValue="false")Boolean isRecommend,String merchantId, PagePojo pagePojo){
		model.clear();
		String clientId=getClient(req);
		String areaId=req.getParameter("areaId");
		//附近距离 ：500\1000\1500
		String distance=req.getParameter("distance");
		//定位经纬度
		String latitude=req.getParameter("latitude");
		String longitude=req.getParameter("longitude");
		//商品类型
		String productCategoryId=req.getParameter("productCategoryId");
		//排序字段
		String sortName=req.getParameter("sortName")==null ? "":req.getParameter("sortName");
		//商品名字
		String name=req.getParameter("name")==null ? "":req.getParameter("name");
		
		if(StringUtil.isNotBlank(clientId)){ 
			model.putAll(productSupport.getGourpProduct(clientId, areaId, distance, isRecommend, latitude, longitude, productCategoryId, sortName,name,merchantId,pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
		
	}
	
	/**
	 * getGroupProductOutLetDetail:(特惠商品列表的商户门店详情接口)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年8月7日 上午11:50:01
	 * @param model
	 * @param req
	 * @param isRecommend
	 * @param pagePojo
	 * 
	 */
	@FunctionModule
	@RequestMapping(value = "/product/group/detail" , method = RequestMethod.GET)
	public void getGroupProductOutLetDetail(ModelMap model,HttpServletRequest req,String merchantId,PagePojo pagePojo){
		model.clear();
		String clientId=getClient(req);

		//定位经纬度
		String latitude=req.getParameter("latitude");
		String longitude=req.getParameter("longitude");	
		String areaId=req.getParameter("areaId");
		if(StringUtil.isNotBlank(clientId) || StringUtil.isNotBlank(merchantId)){ 
			model.putAll(productSupport.getGourpProductDetail(clientId,merchantId,latitude, longitude,areaId,pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
		
	}
	
	
	/**
	 * getGourpProductByCategory:团购商品类目营销商品搜索接口
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年12月16日 下午2:05:27
	 * @param model
	 * @param req
	 * @param merchantId
	 * @param pagePojo
	 * 
	 */
	@RequestMapping(value = "/product/group/search" , method = RequestMethod.GET)
	public void getGourpProductByCategory(ModelMap model,HttpServletRequest req,String merchantId,PagePojo pagePojo){
		model.clear();
		String clientId=getClient(req);

		//定位经纬度
		String latitude=req.getParameter("latitude");
		String longitude=req.getParameter("longitude");
		//区域ID
		String areaId=req.getParameter("areaId");
		//分类ID
		String categoryId=req.getParameter("categoryId");
		//商品名称
		String productName=req.getParameter("productName");
		//附近距离
		String distance=req.getParameter("distance");
		//数据排序
		String FiledSort=req.getParameter("filedSort");
		
		//固定查询团购商品
		String type="1";
		
		if(StringUtil.isNotBlank(clientId)){ 
			model.putAll(productSupport.getGourpProductByCategory(clientId, merchantId, categoryId, type, latitude, longitude, areaId, productName, distance, FiledSort, pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}	
	}
	
	
	/**
	 * getProductCategory:(商品分类分级查询)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月2日 下午4:11:49
	 * @param model
	 * @param req
	 * @param areaId
	 * @param parentId
	 * 
	 */
	@RequestMapping(value = "/product/category/list" , method = RequestMethod.GET)
	public void getProductCategory(ModelMap model,HttpServletRequest req,Long parentId,Long areaId){
		model.clear();
		String clientId=getClient(req);
		if(StringUtil.isNotBlank(clientId)){
			model.putAll(productSupport.getProductCategroy(clientId,parentId, areaId));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	/**
	 * getProductSingle:(去凑单商品列表查询接口)
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年11月9日 下午2:34:57
	 * @param model
	 * @param req
	 * 
	 */
	
	@RequestMapping(value = "/product/single/list" , method = RequestMethod.GET)
	public void getProductSingle(ModelMap model,HttpServletRequest req,String activeId ,String areaId, Double latitude , Double longitude ,String cookieId ,String type,PagePojo pagePojo){
		model.clear();
		String clientId=getClient(req);		
		if(StringUtil.isNotBlank(clientId) && StringUtil.isNotBlank(activeId) && StringUtil.isNotBlank(type)){ 
			model.putAll(productSupport.findProductListOfMinatoSingle(clientId, activeId, areaId, "", latitude, longitude,cookieId,type,pagePojo));
		}else{
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	
}
