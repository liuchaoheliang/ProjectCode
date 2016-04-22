package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Add_Outlet_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.BasePojo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Fallow_Execute_Service_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.OutletQrcodeReq;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Merchant_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Outlet_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Outlet_Info_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Verify_AccountNum_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Common_Service;
import com.froad.cbank.coremodule.module.normal.merchant.service.Outlet_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.QrcodeDownUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RedisKeys;
import com.froad.cbank.expand.redis.RedisManager;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PlatType;

/**
 * 门店
 * 
 * @ClassName OutletController
 * @author zxl
 * @date 2015年3月21日 下午1:58:58
 */
@Controller
@RequestMapping(value = "/outlet")
public class OutletController {

	@Resource
	Outlet_Service outlet_Service;
	
	@Resource
	Common_Service common_service;
	
	@Resource
	OutletService.Iface outletService;
	
	@Resource
	private RedisManager redisManager;
	
	@Resource
	Common_Service common_Service;
	
    /**
     * 查询商户基本信息
     * @param model
     * @param merchantId
     */
	@CheckPermission(keys={"merchant_outlet_businessinfor_menu"})
	@RequestMapping(value = "info", method = RequestMethod.POST)
	public void query_merchant_info(ModelMap model,HttpServletRequest request,@RequestBody Query_Merchant_Info_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(outlet_Service.query_merchant_info(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 查询商户门店
	 * 
	 * @param model
	 * @param merchantId
	 * @param outletFullName
	 * @param address
	 */
	@CheckPermission(keys={"merchant_outlet_outletlist_menu"})
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void query_merchant_outlet(ModelMap model,HttpServletRequest request, @RequestBody Query_Outlet_Info_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(outlet_Service.query_merchant_outlet(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	
	/**
	 * 查询所有商户门店简单返回
	 * 
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"merchant_order_codemanage_menu","merchant_user_users_menu"})
	@RequestMapping(value = "qall", method = RequestMethod.POST)
	public void query_merchant_outlet_all(ModelMap model,HttpServletRequest request,@RequestBody Query_Outlet_Info_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(outlet_Service.query_merchant_outlet_all(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 查询商户下是否存在门店
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_productlist_menu","merchant_product_addproduct_menu","merchant_product_famousproductlist_menu"})
	@RequestMapping(value = "isoutlet", method = RequestMethod.GET)
	public void query_merchant_outlet_true(ModelMap model,HttpServletRequest request) {
		try {
			MerchantUser use=(MerchantUser)request.getAttribute(Constants.MERCHANT_USER);
			model.putAll(outlet_Service.query_merchant_outlet_true(
					request.getAttribute(Constants.CLIENT_ID).toString(),
					use));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
			
		}
	}
	
	/**
	 * 查询商户是否正常（解约禁用）
	 * 说明   description of the class
	 * 创建日期  2015年11月13日  上午11:45:19
	 * 作者  artPing
	 * 参数  @param model
	 * 参数  @param request
	 */
	@RequestMapping(value = "isOK", method = RequestMethod.GET)
	public void query_merchant_outlet_true_isOK(ModelMap model,HttpServletRequest request) {
		try {
			MerchantUser use=(MerchantUser)request.getAttribute(Constants.MERCHANT_USER);
			model.putAll(outlet_Service.query_merchant_outlet_true(use.getMerchantId()));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
			
		}
	}
	
	
	/**
	 * 商户门店添加
	 * 
	 * @param model
	 * @param outletPojo
	 * @param filedata
	 */
	@CheckPermission(keys={"merchant_outlet_outletlist_menu"})	
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void merchant_outlet_add_(ModelMap model,HttpServletRequest request, @RequestBody Add_Outlet_Info_Req req) {
		try {
			
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			if(StringUtils.isNotEmpty(req.getAcctName()))
			req.setAcctName(req.getAcctName().replaceAll("\\s+",""));//去除所有空白;
			if(StringUtils.isNotEmpty(req.getAcctNumber()))
			req.setAcctNumber(req.getAcctNumber().replaceAll("\\s+",""));//去除所有空白;
			model.putAll(outlet_Service.merchant_outlet_add_(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 商户门店修改
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_outlet_outletlist_menu"})
	@RequestMapping(value = "mdy", method = RequestMethod.POST)
	public void merchant_outlet_update_(ModelMap model,HttpServletRequest request,  @RequestBody Update_Outlet_Info_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			if(StringUtils.isNotEmpty(req.getAcctName()))
			req.setAcctName(req.getAcctName().replaceAll("\\s+",""));//去除所有空白;
			if(StringUtils.isNotEmpty(req.getAcctNumber()))
			req.setAcctNumber(req.getAcctNumber().replaceAll("\\s+",""));//去除所有空白;
			model.putAll(outlet_Service.merchant_outlet_update_(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 商户门店删除
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_outlet_outletlist_menu"})
	@RequestMapping(value = "del", method = RequestMethod.GET)
	public void merchant_outlet_delete(ModelMap model,HttpServletRequest request,String outletId) {
		try {
			if(StringUtils.isBlank(outletId)){
				throw new MerchantException(EnumTypes.fail.getCode(), "门店不能为空");
			}
			BasePojo req = new BasePojo();
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(outlet_Service.merchant_outlet_delete(req,outletId,request.getAttribute(Constants.CLIENT_ID).toString()));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	
	/**
	 * 查询商户门店详情
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_outlet_outletlist_menu"})
	@RequestMapping(value = "ld", method = RequestMethod.GET)
	public void query_outlet_details(ModelMap model,HttpServletRequest request,String outletId,String type) {
		try {
			if(StringUtils.isBlank(outletId)){
				throw new MerchantException(EnumTypes.fail.getCode(), "门店不能为空");
			}
			MerchantUser user = (MerchantUser)request.getAttribute(Constants.MERCHANT_USER);
			model.putAll(outlet_Service.query_outlet_details(user.getMerchantId(),outletId,request.getAttribute(Constants.CLIENT_ID).toString(),type));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 根据id查询子集
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_outlet_outletlist_menu"})
	@RequestMapping(value = "qc", method = RequestMethod.GET)
	public void query_cityId_by_countyId(ModelMap model, String countyId,HttpServletRequest request) {
		try {
			String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
			LogCvt.info("地区级联 :< countyId: "+countyId+">");
			model.putAll(outlet_Service.query_cityId_by_countyId(countyId,clientId));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}


	/**
	 * 查询商户门店图片
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "qi", method = RequestMethod.GET)
	public void query_image(ModelMap model,HttpServletRequest request,String outletId) {
		try {
			if(StringUtils.isBlank(outletId)){
				throw new MerchantException(EnumTypes.fail.getCode(), "门店ID不能为空");
			}
			MerchantUser user = (MerchantUser)request.getAttribute(Constants.MERCHANT_USER);
			model.putAll(outlet_Service.query_image(user.getMerchantId(),outletId));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	/**
	 * 名优特惠不能添加团购商品
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_productlist_menu","merchant_product_addproduct_menu","merchant_product_famousproductlist_menu"})
	@RequestMapping(value = "isgroup", method = RequestMethod.GET)
	public void query_merchant_type_(ModelMap model,HttpServletRequest request) {
		try {
			MerchantUser user = (MerchantUser)request.getAttribute(Constants.MERCHANT_USER);
			model.putAll(outlet_Service.query_merchant_type_(user.getMerchantId()));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 收款帐号验证
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "verifya", method = RequestMethod.POST)
	public void verifyAccountNum(ModelMap model,HttpServletRequest request,@RequestBody Verify_AccountNum_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(outlet_Service.verifyAccountNum(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}	
	
    /**
     * 提交门店审核
     * @param model
     * @param request
     * @param req
     */
	@RequestMapping(value = "fallow", method = RequestMethod.POST)
	public void fallowExecuteService(ModelMap model,HttpServletRequest request,@RequestBody Fallow_Execute_Service_Req req) {
		try {
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(outlet_Service.fallowExecuteService(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	
	@RequestMapping(value = "qpc", method = RequestMethod.GET)
	public void query_outlet_by_category(ModelMap model, String parentId,HttpServletRequest request) {
		try {
			String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
			MerchantUser user = (MerchantUser)request.getAttribute(Constants.MERCHANT_USER);
			LogCvt.info("门店所属分类级联 :< parentId: "+parentId+">");
			model.putAll(outlet_Service.query_outlet_by_category(parentId,clientId,user));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 
	 * qrcodeDown:(门店二维码下载).
	 *
	 * @author wufei
	 * 2016-1-4 上午11:34:39
	 * @param response
	 * @param request
	 * @param model
	 * @param req
	 * @throws Exception 
	 *
	 */
	@RequestMapping(value = "qrcodeDown", method = RequestMethod.GET)
	public void qrcodeDown(HttpServletResponse response, HttpServletRequest request,ModelMap model,
			OutletQrcodeReq req) throws Exception{
		
		
			try {
				if(StringUtils.isBlank(req.getOutletId())){
					throw new MerchantException(EnumTypes.fail.getCode(), "门店ID不能为空");
				}
				if(StringUtils.isBlank(req.getType())){
					throw new MerchantException(EnumTypes.fail.getCode(), "下载类型不能为空");
				}
				String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
				//登录检查
				common_service.verifyUser(request,req.getToken(),req.getUid());
				
				OutletVo outletVo=outletService.getOutletByOutletId(req.getOutletId());
				
				LogCvt.info("门店id："+outletVo.getOutletId()+"    qrcodeUrl："+outletVo.getQrcodeUrl() +"门店名称："+outletVo.getOutletName());
				if(outletVo != null &&  StringUtil.isNotBlank(outletVo.getQrcodeUrl())){
					if(req.getType().equals("0")){ //保存二维码
						 QrcodeDownUtil.qrcodeDown(outletVo.getQrcodeUrl(), response,model,outletVo,req.getType());
						
					}else if(req.getType().equals("1")){//保存完整图片
						//从redis缓存中获取图片
						String key = RedisKeys.merchant_outlet_extend(outletVo.getMerchantId(), outletVo.getOutletId());
						String qrcodeUrl = redisManager.getString(key);
						LogCvt.info("redis缓存中qrcodeUrl："+qrcodeUrl);
						if(StringUtil.isBlank(qrcodeUrl)){
							//合成图片并下载
							String qrcodeUrlScp = QrcodeDownUtil.qrcodeMixed(outletVo,response,model,clientId);
							//生成一张图片放入redis缓存中
							redisManager.putString(key, qrcodeUrlScp);
						}else{
							//缓存中有就直接下载
							QrcodeDownUtil.qrcodeDown(qrcodeUrl, response,model,outletVo,req.getType());
						}
						
					}
				}else{
					throw new MerchantException(EnumTypes.qrcode_down_fail.getCode(),"下载失败");
				}
				
			} catch (Exception e) {
				new RespError(model, e);
			}
		}
		
	}
