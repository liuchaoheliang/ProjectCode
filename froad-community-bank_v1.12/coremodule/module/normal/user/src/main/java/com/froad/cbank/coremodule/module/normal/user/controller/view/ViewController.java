package com.froad.cbank.coremodule.module.normal.user.controller.view;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController.Results;
import com.froad.cbank.coremodule.module.normal.user.annotation.FunctionModule;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;
import com.froad.cbank.coremodule.module.normal.user.enums.ProductType;
import com.froad.cbank.coremodule.module.normal.user.enums.ResultCode;
import com.froad.cbank.coremodule.module.normal.user.enums.SeckillProductStatus;
import com.froad.cbank.coremodule.module.normal.user.enums.TerminalType;
import com.froad.cbank.coremodule.module.normal.user.pojo.AreaPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.CartResActivePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.CookiePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductOfFindUseReqPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ReceiverPojo;
import com.froad.cbank.coremodule.module.normal.user.support.AdSupport;
import com.froad.cbank.coremodule.module.normal.user.support.AreaSupport;
import com.froad.cbank.coremodule.module.normal.user.support.BoutiqueProductSupport;
import com.froad.cbank.coremodule.module.normal.user.support.CartSupport;
import com.froad.cbank.coremodule.module.normal.user.support.CashierSupport;
import com.froad.cbank.coremodule.module.normal.user.support.CommentSupport;
import com.froad.cbank.coremodule.module.normal.user.support.IntegralSupport;
import com.froad.cbank.coremodule.module.normal.user.support.MerchantSupport;
import com.froad.cbank.coremodule.module.normal.user.support.OrderSupport;
import com.froad.cbank.coremodule.module.normal.user.support.ProductSupport;
import com.froad.cbank.coremodule.module.normal.user.support.ReceiverSupport;
import com.froad.cbank.coremodule.module.normal.user.support.RedPacketSupport;
import com.froad.cbank.coremodule.module.normal.user.support.RefundSupport;
import com.froad.cbank.coremodule.module.normal.user.support.SeckillProductSupport;
import com.froad.cbank.coremodule.module.normal.user.support.UserEngineSupport;
import com.froad.cbank.coremodule.module.normal.user.utils.IpSeekUtils;
import com.froad.cbank.coremodule.module.normal.user.utils.SimpleUtils;
import com.froad.cbank.coremodule.module.normal.user.vo.CartViewVo;
import com.froad.cbank.coremodule.module.normal.user.vo.PreferenceViewVo;
import com.froad.cbank.coremodule.module.normal.user.vo.ProductViewVo;
import com.pay.user.dto.UserSpecDto;

/**
 * ClassName: ViewController Function: 页面接口合并整合控制层 date: 2015年8月24日 下午4:08:37
 * 
 * @author 刘超 liuchao@f-road.com.cn
 * @version
 */

@Controller
@RequestMapping("/view")
public class ViewController extends BasicSpringController {

	@Resource
	private AdSupport adSupport;

	@Resource
	private CartSupport cartSupport;

	@Resource
	private SeckillProductSupport seckillProductSupport;

	@Resource
	private ProductSupport productSupport;

	@Resource
	private MerchantSupport merchantSupport;

	@Resource
	private CommentSupport commentSupport;

	@Resource
	private ReceiverSupport receiverSupport;
	
	@Resource
	private RefundSupport refundSupport;
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@Resource
	private OrderSupport orderSupport;
	
	@Resource
	private AreaSupport areaSupport;

	
	@Resource
	private CashierSupport cashierSupport;
	
	@Resource
	private RedPacketSupport redPacketSupport;
	
	@Resource
	private IntegralSupport integralSupport;
	
	@Resource
	private BoutiqueProductSupport boutiqueProductSupport; 
	
	/**
	 * index:(个人H5首页整合后接口)
	 * 
	 * @author 刘超 liuchao@f-road.com.cn 2015年8月24日 下午4:12:31
	 * 
	 */
	@FunctionModule
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response,@RequestHeader(value="memberCode",defaultValue="0" ) Long memberCode) {
		ModelAndView model = new ModelAndView();
		Map<String,Object> resMap = new HashMap<String,Object>();
		String clientId = getClient(request);
		
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
		
		//获取精品商城分类   -- 开始
		resMap.putAll(boutiqueProductSupport.getRecommendProductCategorys(clientId, false));
		//获取精品商城分类   -- 结束
		//广告逻辑 -- 开始
		
		TerminalType terminalType = SimpleUtils.getTerminalTypeByRequest(request);
		terminalType = (terminalType == null) ? TerminalType.iphone : terminalType;
		resMap.putAll(adSupport.getAd(clientId, "INDEX", terminalType.getCode(), clientAreaId, null, null));
		
		//广告逻辑 -- 结束

		
		// 首页秒杀商品 -- 开始
		Map<String, String> filter = new HashMap<String, String>();
		filter.put("status", SeckillProductStatus.ing.getCode());
		PagePojo pagePojo = new PagePojo();
		pagePojo.setPageSize(3);
		resMap.putAll(seckillProductSupport.getProductList(clientId, JSON.toJSONString(filter), pagePojo));
		// 首页秒杀商品 -- 结束
		
		//判断是否是白名单用户
		boolean isInWhiteList = userEngineSupport.isWhiteUser(clientId, memberCode);
		model.addObject("isInWhiteList", isInWhiteList);
		
		model.addObject("userInfo", userInfoMap);
		model.addObject("data",resMap);
		return model;
	}

	
	
	/**
	 * groupDetail:(个人H5团购商品详情，整合后接口)
	 * 
	 * @author 刘超 liuchao@f-road.com.cn 2015年8月24日 下午4:13:11
	 * 
	 */
	@FunctionModule
	@RequestMapping(value = "/cheapDetail", method = RequestMethod.GET)
	public void groupDetail(ModelMap model, HttpServletRequest req,
			@RequestHeader(value = "memberCode", defaultValue = "") Long memberCode, String productId) {
		model.clear();
		
		Long areaId = null;
		Double latitude = null;
		Double longitude = null;
		
		CookiePojo cookiePojo = queryCookie(req);
		if(null != cookiePojo){
			areaId = cookiePojo.getAreaId();
			latitude = cookiePojo.getLatitude();
			longitude = cookiePojo.getLongitude();
		}
		String clientId = getClient(req);
		if (!StringUtil.empty(clientId) && !StringUtil.empty(productId)) {
			//购物车商品数量
			Integer productCount = 0;
			if (memberCode!=null) {
				productCount = productSupport.getCount(clientId, memberCode);
			}
			model.put("productCount", productCount);
			
			// 商品详情
			HashMap<String, Object> product = productSupport.getProductDetail(clientId, memberCode, productId, null,
					Long.valueOf(areaId));

			ProductDetailPojo detail = (ProductDetailPojo) product.get("resResult");
			if(detail != null){
			String merchantId = detail.getMerchantId();
			model.put("productDetail", detail);

			// 门店列表默认查询1页，pageSize为1
			HashMap<String, Object> outlets = merchantSupport.getOutletListByMerchantId(clientId, merchantId, 1, 1, 1,
					longitude, latitude, 0L, 0L);
			PagePojo pagePojo = (PagePojo) outlets.get("page");
			outlets.remove("page");
			outlets.put("totalCount", pagePojo.getTotalCount());
			model.put("outletInfo", outlets);
			}

			// 商品评论(默认设置2条)
			HashMap<String, Object> comments = commentSupport.getProductCommentList(clientId, null, productId, null,
					null, 1, 2, 1, 0L, 0L);
			PagePojo pagePo = (PagePojo) comments.get("page");
			comments.remove("page");
			comments.put("totalCount", pagePo.getTotalCount());
			model.put("commentInfo", comments);
			
		} else {
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	

	/**
	 * famousDetail:(个人H5，名优特惠商品商品详情整合后接口)
	 * 
	 * @author 刘超 liuchao@f-road.com.cn 2015年8月24日 下午4:14:09
	 * 
	 */
	@RequestMapping(value = "/famousDetail", method = RequestMethod.GET)
	public void famousDetail(ModelMap model, HttpServletRequest req,
			@RequestHeader(value = "memberCode", defaultValue = "") Long memberCode, String productId) {
		model.clear();
		String clientId = getClient(req);
		if (!StringUtil.empty(clientId) && !StringUtil.empty(productId)) {
			// 商品详情
			HashMap<String, Object> product = productSupport.getProductDetail(clientId, memberCode, productId, null,
					null);
			ProductDetailPojo detail = (ProductDetailPojo) product.get("resResult");
			model.put("productDetail", detail);

			// 商品评论(默认设置5条)
			HashMap<String, Object> comments = commentSupport.getProductCommentList(clientId, null, productId, null,
					null, 1, 5, 1, 0L, 0L);
			PagePojo pagePo = (PagePojo) comments.get("page");
			comments.remove("page");
			comments.put("totalCount", pagePo.getTotalCount());
			model.put("commentInfo", comments);

		} else {
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}

	}
	
	

	/**
	 * tradeOrderConfirm:(个人H5 确认下单页面整合接口) productIds : 商品ID组成的数组 ,  type(进入的渠道) ： 0-直接购买  1-购物车
	 * @param productType 商品类型 1-团购2-预售3-名优特惠4-在线积分兑换5-网点礼品6-精品商城商品
	 * @areaId  重新选择地区ID
	 * @author 刘超 liuchao@f-road.com.cn 2015年8月24日 下午4:15:46
	 * 
	 */
	@Token
	@FunctionModule
	@RequestMapping(value = "/tradeOrderConfirm", method = RequestMethod.GET)
	public void carOrderConfirm(ModelMap model, HttpServletRequest req, String[] productIds,
			@RequestHeader Long memberCode ,@RequestParam(value="type" , defaultValue ="0") String type, String rtype,
			Double productTotalMoney,Double vipTotalMoney,String productName,String productType,String areaId) {
		model.clear();
		String clientId = getClient(req);
		boolean isNeedReciver = false;
		Integer boutiqueProductCount = 0;
		List<String> ids=Arrays.asList(productIds);
		//查询可用红包数量
		List<ProductOfFindUseReqPojo> redPacketList = new ArrayList<ProductOfFindUseReqPojo>();
		ProductOfFindUseReqPojo findUseReqPojo = null;
		List<String> sustainActiveIds = new ArrayList<String>();
			//只有从购物车过来时，需要拉取购物车信息
			if("1".equals(type)){
				// 确认订单页面
				Map<String, Object> cart = cartSupport.getCartList(clientId, memberCode,ids);
				
				model.put("fullGive",cart.get("fullGive"));
				List<CartViewVo> cartViewVoList = (List<CartViewVo>) cart.get(Results.result);
				
				for (int n = 0 ; n < cartViewVoList.size() ; n++ ) {
					CartViewVo temp=cartViewVoList.get(n);
					//对应商户的商品列表不为空，则进入去判断是否包含传入的商品ID
					if(!ArrayUtil.empty(temp.getProductViewVoList())){
						List<ProductViewVo> list=temp.getProductViewVoList();
						for(int i=0;i<list.size();i++){
							//判断如果不是传入的商品Id，则移除
							if(!ids.contains(list.get(i).getProductId())){
								temp.getProductViewVoList().remove(i--);
							}else {
								//过滤正常的活动
								List<CartResActivePojo> activeList =  list.get(i).getActiveList();
								if(!ArrayUtil.empty(activeList)){
									for(int m=0 ; m<activeList.size() ; m++){
										CartResActivePojo active =  activeList.get(m);
//										LogCvt.info("======"+JSON.toJSONString(active));
										if(!"0".equals(active.getActiveStatus()) || active.getIsMinato() ){
											//如果当前活动状态非正常，则移除 或者 商品参加了活动，但是还展示了去凑单，即没有满最小额度，也移除
											activeList.remove(m--);
										}
									}
								}
								//判断是否需要收货人
								if(StringUtil.isBlank(list.get(i).getOrgCode()) && (!list.get(i).getType().equals(ProductType.group.getCode()))){
									//OrgCode为空，且不是团购商品，则是需要收货人的商品
									isNeedReciver = true;
								}
							} 
						}
						//如果当前商户下面没有任何商品，则移除当前商户节点
						if( ArrayUtil.empty(temp.getProductViewVoList()) ){
							//移除当前值，将循环值回减1
							cartViewVoList.remove(n--);
						}
					}
				}
				if(cartViewVoList!= null && cartViewVoList.size() > 0){
					for(CartViewVo cartViewVo : cartViewVoList){
						if(cartViewVo != null && cartViewVo.getProductViewVoList() != null 
								&& cartViewVo.getProductViewVoList().size() > 0){
							for(ProductViewVo productViewVo : cartViewVo.getProductViewVoList()){
								if(productViewVo.getType().equals(ProductType.boutique.getCode())){
									boutiqueProductCount++;
								}
							}
						}
					}
				}
				model.put("confirmOrderList", cartViewVoList);
				//查询可用红包数量
				for(CartViewVo carVo:cartViewVoList){
					for(ProductViewVo product:carVo.getProductViewVoList()){
						findUseReqPojo = new ProductOfFindUseReqPojo();
						if(!ArrayUtil.empty(product.getActiveList())){
							//暂时目前商品只关联一个活动（后期如果有多个，需要改造购物车）
							sustainActiveIds.add(product.getActiveList().get(0).getActiveId());
						}
						findUseReqPojo.setProductId(product.getProductId());
						findUseReqPojo.setProductName(product.getProductName());
						findUseReqPojo.setGeneralMoney(product.getMoney());
//						findUseReqPojo.setGeneralCount(product.getQuantity());
//						findUseReqPojo.setVipCount(product.getVipQuantity());
						findUseReqPojo.setVipMoney(product.getVipMoney());
						redPacketList.add(findUseReqPojo);
					}
				}
				
				HashMap<String, Object> map = redPacketSupport.canUsePackets(clientId, memberCode, true, false,sustainActiveIds,redPacketList, new PagePojo());
				PagePojo page = (PagePojo) (map.get("page")!=null ? map.get("page"): new PagePojo());
				model.put("canUsePacketCount", page.getTotalCount());
				//查询可用红包数量
				
			//需要什么类型的地址 1收货人 2提货人
			rtype = isNeedReciver ? "1" : "2";

		} else {
			//直接购买
			if(productIds.length == 0){
				model.put("code", "9999");
				model.put("message", "请求参数错误");
				return ;
			}
			//营销平台购物车检验
			List<PreferenceViewVo> pvList = new ArrayList<PreferenceViewVo>();
			PreferenceViewVo preferenceViewVo = new PreferenceViewVo();
			preferenceViewVo.setProductId(productIds[0]);
			preferenceViewVo.setProductTotalMoney(productTotalMoney);
			preferenceViewVo.setVipTotalMoney(vipTotalMoney);
			preferenceViewVo.setProductName(productName);
			pvList.add(preferenceViewVo);
			Map<String, Object> map = cartSupport.carPreference(clientId, memberCode, pvList);
			model.put("fullGive",map.get("fullGive"));
			List<CartResActivePojo> list = (List<CartResActivePojo>) map.get(productIds[0]);
			//过滤正常的活动
			if(!ArrayUtil.empty(list)){
				for(int i=0 ; i<list.size() ; i++){
					CartResActivePojo temp =  list.get(i);
//					LogCvt.info("======"+JSON.toJSONString(temp));
					if(!"0".equals(temp.getActiveStatus()) || temp.getIsMinato() ){
						//如果当前活动状态非正常，则移除 或者 商品参加了活动，但是还展示了去凑单，即没有满最小额度，也移除
						list.remove(i--);
					}
				}
			}
			model.put("active", list);
			
			//查询可用红包数量
			findUseReqPojo = new ProductOfFindUseReqPojo();
			if(!ArrayUtil.empty(list)){
				//暂时目前商品只关联一个活动（后期如果有多个，需要改造购物车）
				sustainActiveIds.add(list.get(0).getActiveId());
			}
			findUseReqPojo.setProductId(productIds[0]);
			findUseReqPojo.setProductName(productName);
			findUseReqPojo.setGeneralMoney(productTotalMoney);
//			findUseReqPojo.setGeneralCount(product.getQuantity());
//			findUseReqPojo.setVipCount(product.getVipQuantity());
			findUseReqPojo.setVipMoney(vipTotalMoney);
			redPacketList.add(findUseReqPojo);
			
			HashMap<String, Object> redPacketMap = redPacketSupport.canUsePackets(clientId, memberCode, true, false,sustainActiveIds,redPacketList, new PagePojo());
			PagePojo page = (PagePojo) (redPacketMap.get("page")!=null ? redPacketMap.get("page"): new PagePojo());
			model.put("canUsePacketCount", page.getTotalCount());
			//查询可用红包数量
		}
			
		//针对用户名为身份证加密的情况
		String userName = "";
		UserSpecDto user = userEngineSupport.queryMemberByMemberCode(memberCode);
		if(user != null){
			userName = user.getLoginID();
		}
		//获取用户积分信息
		Map<String,Object> point =integralSupport.getUserPointsAmount(clientId, userName);
		model.putAll(point);
		
		//提示是否需要更换配送地址
		model.put("msgResult", true);
		// 获取用户默认的收获地址
		HashMap<String, Object> receiver = receiverSupport.getReceiverList(clientId, memberCode, "1", rtype);
		List<ReceiverPojo> receiverList = (List<ReceiverPojo>) receiver.get("receiverList");
		if (ArrayUtil.empty(receiverList)) {
			model.put("deliveryInfo", null);
		} else {
			ReceiverPojo receiverPojo = receiverList.get(0);
			model.put("deliveryInfo", receiverPojo);
			//如何没有选择收货地址则校验默认收货地址
			if(StringUtil.isBlank(areaId)) {
				areaId = receiverPojo.getAreaId()+"";
			}
			if(StringUtil.isNotBlank(areaId)){
				if("1".equals(type)){
					if(boutiqueProductCount > 0){//购物车里有精品商城精品则需要限制配送地区
						AreaPojo areaPojo = areaSupport.findAreaByAreaId(Long.valueOf(areaId));
						if(areaPojo != null){
							if(StringUtil.isBlank(areaPojo.getClientId())){
								model.put("msgResult", false);
								model.put("message", "精品商城商品不支持配送至该地区，请更换地址");
							}else{
								if(!areaPojo.getClientId().equals(clientId)){
									model.put("msgResult", false);
									model.put("message", "精品商城商品不支持配送至该地区，请更换地址");
								}
							}
						}
					}
				}else{
					 if(StringUtil.isNotBlank(productType)){
						if(productType.equals(ProductType.boutique.getCode())){//精品商城商品类型则需要限制配送地区
							AreaPojo areaPojo = areaSupport.findAreaByAreaId(Long.valueOf(areaId));
							if(areaPojo != null){
								if(StringUtil.isBlank(areaPojo.getClientId())){
									model.put("msgResult", false);
									model.put("message", "精品商城商品不支持配送至该地区，请更换地址");
								}else{
									if(!areaPojo.getClientId().equals(clientId)){
										model.put("msgResult", false);
										model.put("message", "精品商城商品不支持配送至该地区，请更换地址");
									}
								}
							}
						}
					 }
				}
			}
		}
		model.put(Results.result, null);
	
	}
	


	/**
	 * receivingConfirm:(个人H5 确认收货整合接口)
	 * 
	 * @author 刘超 liuchao@f-road.com.cn 2015年8月24日 下午4:17:14
	 * 
	 */
	@Token
	@RequestMapping(value = "/receivingConfirm", method = RequestMethod.GET)
	public void receivingConfirm(ModelMap model, HttpServletRequest req,String subOrderId) {
		model.clear();
		String clientId = getClient(req);
		if (!StringUtil.empty(clientId) && !StringUtil.empty(subOrderId)) {
			Map<String, Object> map=orderSupport.getSubOrderDetail(subOrderId, clientId);
			model.put("receivingConfirmInfo",map.get(Results.result));
		} else {
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
		
		
	}

	/**
	 * refundInfo:(个人H5 根据子订单号获取订单信息)
	 * 
	 * @author 刘超 liuchao@f-road.com.cn 2015年8月24日 下午4:17:47
	 * 
	 */
	@Token
	@RequestMapping(value = "/refundInfo", method = RequestMethod.GET)
	public void refundInfo(ModelMap model, HttpServletRequest req,String subOrderId ) {
		model.clear();
		String clientId = getClient(req);
		if (!StringUtil.empty(clientId) && !StringUtil.empty(subOrderId)) {
			Map<String, Object> map=orderSupport.getSubOrderDetail(subOrderId, clientId);
			model.put("refundInfo",map.get(Results.result));
		} else {
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}

	
	
	/**
	 * userInfo:(获取登录用户的信息)
	 * 
	 * @author 刘超 liuchao@f-road.com.cn 2015年8月24日 下午4:18:32
	 * 
	 */
	@Token
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public void userInfo(ModelMap model, HttpServletRequest request,@RequestHeader Long memberCode) {
		model.clear();
		String clientId = getClient(request);
		if (!StringUtil.empty(clientId) && memberCode != null) {
			model.put("userAcct",userEngineSupport.getUserInfo(memberCode, clientId));
		} else {
			model.put("code", "9999");
			model.put("message", "必要参数为空");
		}
	}
	
	/**
	 * groupProductList:特惠商品首页/列表 直出接口
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年12月25日 上午11:28:06
	 * 
	 */
	@FunctionModule
	@RequestMapping(value = {"/cheapIndex","/cheapList"}, method = RequestMethod.GET)
	public void groupSearch(ModelMap model,HttpServletRequest req,@RequestParam(value="isRecommend", defaultValue="false")Boolean isRecommend,
			Long parentId,String merchantId,String productCategoryId,String positionPage){
		model.clear();
		String clientId=getClient(req);
		String areaId = "";
		String latitude = "";
		String longitude = "";
		
		CookiePojo cookiePojo = queryCookie(req);
		if(null != cookiePojo){
			areaId = (cookiePojo.getAreaId() == null) ? "" : cookiePojo.getAreaId().toString();
			latitude = (cookiePojo.getLatitude() == null) ? "" : cookiePojo.getLatitude().toString();
			longitude = (cookiePojo.getLongitude() == null) ? "" : cookiePojo.getLongitude().toString();
		}

		if(!StringUtil.empty(clientId) ){
			//团购商品分类
			model.putAll(productSupport.getProductCategroy(clientId, null, Long.parseLong(areaId)));
			
			String tempId = (areaId == null) ? "" : areaId.toString();
			//广告逻辑 -- 开始
			TerminalType terminalType = SimpleUtils.getTerminalTypeByRequest(req);
			terminalType = (terminalType == null) ? TerminalType.iphone : terminalType;
			//广告位
			model.putAll(adSupport.getAd(clientId, positionPage, terminalType.getCode(), tempId, "", ""));
			//广告逻辑 -- 结束
			
			//商品列表
			PagePojo pagePojo = new PagePojo();
			pagePojo.setPageNumber(1);
			pagePojo.setPageSize(10);
			

			//排序字段
			String sortName=req.getParameter("sortName")==null ? "":req.getParameter("sortName");
			//商品名字
			String name=req.getParameter("name")==null ? "":req.getParameter("name");
			//附近距离 ：500\1000\1500
			String distance=req.getParameter("distance");
			
			if(!StringUtil.empty(areaId)){
//				if(!StringUtil.empty(merchantId)){
//					model.putAll(productSupport.getGourpProductDetail(clientId,merchantId,latitude, longitude,areaId,pagePojo));
//				}else{
				model.putAll(productSupport.getGourpProduct(clientId, areaId, distance, isRecommend, latitude, longitude, productCategoryId, sortName,name,merchantId,pagePojo));
//				}
				
			}else{
				LogCvt.info("查询团购列表必传参数为空");
			}
		}else{
			model.put(Results.code,  ResultCode.failed.getCode());
			model.put(Results.msg, "必要参数为空");
		}
	}
	
	
	
	/**
	 * groupProductList:特惠商户首页/列表 直出接口
	 *
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年12月25日 上午11:28:06
	 * @throws UnsupportedEncodingException 
	 * 
	 */
	@FunctionModule
	@RequestMapping(value ={"/merchantIndex","/merchantList"}, method = RequestMethod.GET)
	public void merchantSearch(ModelMap model,HttpServletRequest req, Long parentAreaId,Long categoryId,Double distance,String keyword,String positionPage) throws UnsupportedEncodingException{
		model.clear();
		String clientId=getClient(req);
		Long areaId = null;
		Double latitude = null;
		Double longitude = null;
		String cityName = "";
		distance = distance == null ? 0 : distance;  //距离范围，单位（米），不传默认为0，查询全部
		
		CookiePojo cookiePojo = queryCookie(req);
		if(null != cookiePojo){
			areaId = cookiePojo.getAreaId();
			latitude = cookiePojo.getLatitude();
			longitude = cookiePojo.getLongitude();
			cityName = cookiePojo.getCityName();
		}
		model.put("cityName", URLDecoder.decode(cityName, "UTF-8"));

		if(!StringUtil.empty(clientId) ){
			//特惠商户分类
			model.putAll(merchantSupport.getMerchantCategoryList(clientId, null, areaId));
			String tempId = (areaId == null) ? "" : areaId.toString();
			//广告逻辑 -- 开始
			TerminalType terminalType = SimpleUtils.getTerminalTypeByRequest(req);
			terminalType = (terminalType == null) ? TerminalType.iphone : terminalType;
			String categoryIds = (categoryId == null) ? "" : categoryId.toString();
			//广告
			model.putAll(adSupport.getAd(clientId, positionPage, terminalType.getCode(), tempId, categoryIds, ""));
			//广告逻辑 -- 结束
			//商户列表
			//暂定把parentAreaId设置成areaId
			model.putAll(merchantSupport.getOutletList(clientId, null, areaId, longitude, latitude, categoryId, keyword, 1, 10,0,0, 0, 0L, 0L, -1, distance));
		}else {
			model.put(Results.code,  ResultCode.failed.getCode());
			model.put(Results.msg, "必要参数为空");
		}
	}
	
	
	/**
	 * 获取cookie值
	 * @param req
	 * @return
	 */
	public CookiePojo queryCookie(HttpServletRequest req){
		CookiePojo cookiePojo = new CookiePojo();
		// 获取客户端定位信息cookie
		Cookie[] clientCookies = req.getCookies();
		if (clientCookies != null) {
			for (Cookie clientCookie : clientCookies) {
				if ("areaId".equals(clientCookie.getName())) {
					cookiePojo.setAreaId(Long.valueOf(clientCookie.getValue()));
				}
				if ("cityName".equals(clientCookie.getName())) {
					cookiePojo.setCityName(clientCookie.getValue());
				}
				if ("latitude".equals(clientCookie.getName())) {
					cookiePojo.setLatitude(Double.valueOf(clientCookie.getValue()));
				}
				if ("longitude".equals(clientCookie.getName())) {
					cookiePojo.setLongitude(Double.valueOf(clientCookie.getValue()));
				}
			}
		}
		return cookiePojo;
	}

}
