/**
 * Project Name:coremodule-user
 * File Name:BoutiqueProductController.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.controller.product
 * Date:2015年11月27日下午2:59:05
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.controller.product;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.enums.TerminalType;
import com.froad.cbank.coremodule.module.normal.user.pojo.AreaPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.BoutiqueProductReqPojo;
import com.froad.cbank.coremodule.module.normal.user.support.AdSupport;
import com.froad.cbank.coremodule.module.normal.user.support.AreaSupport;
import com.froad.cbank.coremodule.module.normal.user.support.BoutiqueProductSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.IpSeekUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
/**
 * ClassName:BoutiqueProductController
 * Reason:	 TODO ADD REASON.
 * Date:     2015年11月27日 下午2:59:05
 * @author   wm
 * @version  
 * @see 	 
 */

@Controller
@RequestMapping
public class BoutiqueProductController extends BasicSpringController{

	@Resource
	private BoutiqueProductSupport boutiqueProductSupport; 
	
	@Resource
	private AreaSupport areaSupport;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private AdSupport adSupport;
	
	/**
	 * 
	 * index:精品商城首页直出
	 * @author wm
	 * 2015年12月1日 下午1:24:35
	 * @param request
	 * @param response
	 * @return
	 * @throws TException 
	 *
	 */
	@RequestMapping(value = "/view/froadMallIndex", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws TException {
		ModelAndView model = new ModelAndView();
		Map<String,Object> resMap = new HashMap<String,Object>();
		String clientId = (String)request.getAttribute("clientId");
//		Map<String,String> userInfoMap = userInfoMap(request, response, clientId);
		//广告逻辑 -- 开始
		TerminalType terminalType = SimpleUtils.getTerminalTypeByRequest(request);
		terminalType = (terminalType == null) ? TerminalType.iphone : terminalType;
		resMap.putAll(boutiqueProductSupport.getfroadMallIndex(clientId,terminalType));
		//广告逻辑 -- 结束
//		model.addObject("userInfo", userInfoMap);
		model.addObject("data",resMap);
		return model;
	}
	
	
	/**
	 * froadMallList:查询精品商场商品列表.
	 * @author wm
	 * 2015年11月27日 下午4:12:21
	 * @param model
	 * @param memberCode
	 * @param request
	 * @throws Exception
	 */
	@RequestMapping(value = "/view/froadMallList", method = RequestMethod.GET)
	public void froadMallList(ModelMap model, HttpServletRequest req) throws Exception {
		model.clear();
		BoutiqueProductReqPojo reqPojo = convertMap(req, BoutiqueProductReqPojo.class);
		LogCvt.info("查询精品商场商品列表请求参数------------》: " + JSON.toJSONString(reqPojo));
		String clientId = (String)req.getAttribute("clientId");
		reqPojo.setClientId(clientId);
		model.putAll(boutiqueProductSupport.getBoutiqueProductList(reqPojo));
	}
	
	
	/**
	 * 
	 * foradMallDetail:根据商品ID查询精品商场商品信息
	 *
	 * @author wm
	 * 2015年11月27日 下午3:38:53
	 * @param model
	 * @param req
	 * @param productId
	 *
	 */
	@RequestMapping(value = "/view/froadMallDetail" , method = RequestMethod.GET)
	public void foradMallDetail(ModelMap model, HttpServletRequest req, @RequestHeader(value="memberCode",defaultValue="" ) Long memberCode, String productId){
		String clientId = (String)req.getAttribute("clientId");
		if(StringUtil.isNotBlank(productId)){
			model.putAll(boutiqueProductSupport.getProductDetail(clientId, productId,memberCode));
		}else{
			model.put(Results.code, ResultCode.failed.getCode());
			model.put(Results.msg, "商品Id不能为空!");
		}
	}
	
	/**
	 * getProductCategory:H5精品商城商品分类查询
	 *
	 * @author wm
	 * 2015年11月30日 下午1:37:59
	 * @param model
	 * @param req
	 * @param parentId
	 *
	 */
	@RequestMapping(value = "/product/boutiqueCategorys" , method = RequestMethod.GET)
	public void getBoutiqueCategorys(ModelMap model,HttpServletRequest req,Long parentId){
		model.clear();
		String clientId = (String)req.getAttribute("clientId");
		if(StringUtil.isNotBlank(clientId)){
			model.putAll(boutiqueProductSupport.getBoutiqueGoodsCategorys(clientId,parentId));
		}else{
			model.put(Results.code, ResultCode.failed.getCode());
			model.put(Results.msg, "请求参数不能为空!");
		}
	}
	
	
	/**
	 * 
	 * getBoutiqueAd:H5 精品商城广告位查询 
	 *
	 * @author wm
	 * 2015年11月30日 下午1:53:50
	 * @param model
	 * @param req
	 * @param parentId
	 *
	 */
	@RequestMapping(value = "/product/boutiqueAdCategorys" , method = RequestMethod.GET)
	public void getBoutiqueAd(ModelMap model,HttpServletRequest req,boolean isMall){
		model.clear();
		String clientId = (String)req.getAttribute("clientId");
		if(StringUtil.isNotBlank(clientId)){
			model.putAll(boutiqueProductSupport.getRecommendProductCategorys(clientId,isMall));
		}else{
			model.put(Results.code, ResultCode.failed.getCode());
			model.put(Results.msg, "请求参数不能为空!");
		}
	}
	
	/**
	 * 
	 * userInfoMap:客户端定位
	 *
	 * @author wm
	 * 2015年12月1日 下午1:23:59
	 * @param request
	 * @param response
	 * @param clientId
	 * @return
	 *
	 */
	public Map<String,String> userInfoMap(HttpServletRequest request,HttpServletResponse response,String clientId){
		//客户端定位 -- 开始
		String clientCityName = "";
		String clientCityCode = "";
		String clientAreaId = "";
				
		//获取客户端定位信息cookie
		Cookie[] clientCookies = request.getCookies();
		if(clientCookies != null){
			for(Cookie clientCookie : clientCookies){
				if("cityCode".equals(clientCookie.getName())){
					clientCityCode = clientCookie.getValue();
				}
				if("areaId".equals(clientCookie.getName())){
					clientAreaId = clientCookie.getValue();
				}
			}
		}
		
		if(StringUtil.isBlank(clientAreaId)){
			//客户端无定位Cookie的逻辑
			//后台根据IP自动定位
			String ip = getIpAddr(request);
			//解决多重代理下解析到多个IP地址的问题
			if(ip.contains(",")){
				ip = ip.split(",")[0];
			}
			LogCvt.info("[首页直出定位] 客户端无定位信息，开始根据IP定位>> IP:" + ip);
			//根据ip定位返回cityCode
			String ipCityCode = IpSeekUtils.getIpCityCode(ip);
			LogCvt.info("[首页直出定位] IP定位查询结果>> ipCityCode:" + ipCityCode);
			clientCityCode = ipCityCode;
			
			if(StringUtil.isBlank(clientCityCode)){
				//IP定位查询结果为空，返回客户端第一条地区数据
				List<AreaPojo> alist = areaSupport.getIndexList(clientId);
				LogCvt.info("[首页直出定位] IP定位查询结果为空，默认使用客户端支持第一个地区数据: " + JSON.toJSONString(alist.get(0)));
				clientCityCode = alist.get(0).getAreaCode();
				clientAreaId = alist.get(0).getId();
				clientCityName = alist.get(0).getName();
			}else{
				//IP定位有结果，查询当前客户端是否支持此cityCode数据
				HashMap<String,Object> hash = areaSupport.isSupport(clientId, clientCityCode);
				boolean flag = (Boolean)hash.get("resResult");
				if(flag){
					LogCvt.info("[首页直出定位] 查询客户端是否支持此cityCode：支持");
					//支持此数据，设置此cityCode为客户端cityCode
					AreaPojo areaVo = (AreaPojo)hash.get("area");
					clientCityCode = areaVo.getAreaCode();
					clientAreaId = areaVo.getId();
					clientCityName = areaVo.getName();
				}else{
					LogCvt.info("[首页直出定位] 查询客户端是否支持此cityCode：不支持");
					//不支持此数据，根据clientId查询地址，返回第一个地址
					List<AreaPojo> alist = areaSupport.getIndexList(clientId);
					LogCvt.info("[首页直出定位] 定位结果不支持，默认使用客户端第一个地区数据");
					clientCityCode = alist.get(0).getAreaCode();
					clientAreaId = alist.get(0).getId();
					clientCityName = alist.get(0).getName();
				}
			}
			
		}else{
			//客户端有定位Cookie数据的逻辑
			LogCvt.info(String.format("[首页直出定位] 客户端存在定位信息>> Cookies: cityCode:%s, areaId:%s",clientCityCode,clientAreaId));
			
			AreaPojo area = areaSupport.findAreaByAreaId(Long.parseLong(clientAreaId));
			clientCityCode = area.getAreaCode();
			clientAreaId = area.getId();
			clientCityName = area.getName();
		}
		
		//向客户端设置cookie
		LogCvt.info(String.format("[首页直出定位] 向客户端设置cookie>> cityCode:%s, areaId:%s", clientCityCode, clientAreaId));
		
		String COOKIE_DOMAIN = userEngineSupport.getUbankTokenCookieDomain(request);
		int expireTime = 2592000;//过期时间一个月
		
		Cookie c_cityCode = new Cookie("cityCode",clientCityCode);
		c_cityCode.setDomain(COOKIE_DOMAIN);
		c_cityCode.setPath("/");
		c_cityCode.setMaxAge(expireTime);
		response.addCookie(c_cityCode);
		
		Cookie c_areaId = new Cookie("areaId",clientAreaId);
		c_areaId.setDomain(COOKIE_DOMAIN);
		c_areaId.setPath("/");
		c_areaId.setMaxAge(expireTime);
		response.addCookie(c_areaId);
		
		Map<String,String> userInfoMap = new HashMap<String,String>();
		userInfoMap.put("cityName", clientCityName);
		userInfoMap.put("areaId", clientAreaId);
		userInfoMap.put("cityCode", clientCityCode);
		
		//客户端定位 -- 结束
		return userInfoMap;
	}
	
}
