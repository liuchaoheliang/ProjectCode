package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Add_Product_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.BasePojo;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Del_Product_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.ProductAudditReq;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.QueryProductListReq;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Product_Down_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Update_Product_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.HandleProduct_Service;
import com.froad.cbank.coremodule.module.normal.merchant.service.Outlet_Service;
import com.froad.cbank.coremodule.module.normal.merchant.service.Product_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;
import com.froad.thrift.vo.PlatType;

/**
 * 商品
 * @ClassName ProductController
 * @author zxl
 * @date 2015年3月21日 下午1:52:30
 */
@Controller
@RequestMapping(value = "/product" )
public class ProductController {
	
	
	@Resource
	Product_Service product_Service;
	@Resource
	Outlet_Service outlet_Service;
	
	@Resource
	HandleProduct_Service handleProduct_Service;
	
	/**
	 * 商品查询
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_productlist_menu","merchant_product_famousproductlist_menu"})
	@RequestMapping(value = "list", method = RequestMethod.POST)
	public void product_query(ModelMap model,HttpServletRequest request,@RequestBody QueryProductListReq req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(product_Service.product_query(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 商品详情
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_productlist_menu","merchant_product_famousproductlist_menu"})
	@RequestMapping(value = "ld", method = RequestMethod.POST)
	public void query_product_details(ModelMap model,HttpServletRequest request,@RequestBody Query_Product_Detail_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(product_Service.product_details(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 商品添加
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_addproduct_menu"})
	@RequestMapping(value = "add", method = RequestMethod.POST)
	public void product_add(ModelMap model,HttpServletRequest request,@RequestBody Add_Product_Req req) {
		try {

			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(product_Service.product_add(req));
		} catch (MerchantException e) {
			new RespError(model, e);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 商品修改
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_productlist_menu","merchant_product_famousproductlist_menu"})
	@RequestMapping(value = "mdy", method = RequestMethod.POST)
	public void product_update(ModelMap model,HttpServletRequest request,@RequestBody Update_Product_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(product_Service.product_update(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
//	/**
//	 * 商品部分信息修改
//	 * @tilte product_update_part
//	 * @author zxl
//	 * @date 2015年5月5日 下午5:15:25
//	 * @param model
//	 * @param request
//	 * @param req
//	 */
//	@CheckPermission(keys={"merchant_product_productlist_menu"})
//	@RequestMapping(value = "mdyp", method = RequestMethod.POST)
//	public void product_update_part(ModelMap model,HttpServletRequest request,@RequestBody Update_Product_Part_Req req) {
//		try {
//			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
//			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
//			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
//			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
//			model.putAll(product_Service.product_update_part(req));
//		} catch (MerchantException e) {
//			new RespError(model, e);
//		} catch (Exception e) {
//			new RespError(model);
//			LogCvt.error(e.getMessage(), e);
//		}
//	}
	
	
	/**
	 * 商品删除
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_productlist_menu"})
	@RequestMapping(value = "pd", method = RequestMethod.POST)
	public void query_product_delete(ModelMap model,HttpServletRequest request,@RequestBody Del_Product_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(product_Service.query_product_delete(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 *  查询商品操作权限
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "vp", method = RequestMethod.POST)
	public void verity_(ModelMap model,HttpServletRequest request,@RequestBody Del_Product_Req req) {
		try {
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(outlet_Service.query_merchant_type_(req.getMerchantUser().getMerchantId()));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 商品上下架
	 * @tilte update_product_delete
	 * @author zxl
	 * @date 2015年4月17日 下午3:02:43
	 * @param model
	 * @param request
	 * @param req
	 */
	
	@CheckPermission(keys={"merchant_product_productlist_menu"})
	@RequestMapping(value = "upd", method = RequestMethod.POST)
	public void update_product_delete(ModelMap model,HttpServletRequest request,@RequestBody Update_Product_Down_Req req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			model.putAll(product_Service.update_product_updown(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 商品批量审核
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_productlist_menu"})
	@RequestMapping(value = "tth", method = RequestMethod.POST)
	public void auddit_product_batch(ModelMap model,HttpServletRequest request,@RequestBody ProductAudditReq req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			req.setIp(request.getAttribute(Constants.CLIENT_IP).toString());
			req.setPlatType((PlatType)request.getAttribute(Constants.PLAT_TYPE));
			req.setOrgCode(handleProduct_Service.getorgCode(req.getMerchantUser().getMerchantId()));
			model.putAll(handleProduct_Service.auddit_product_batch(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

	
	/**
	 * 查询机构编号
	 * 
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "qo", method = RequestMethod.GET)
	public void query_user_orgCode(ModelMap model,String merchantId,String outletId) {
		try {
			if(StringUtils.isBlank(merchantId)){
				throw new MerchantException(EnumTypes.fail.getCode(), "商户ID不能为空");
			}
			model.putAll(product_Service.query_user_orgCode(merchantId,outletId));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 查询团购商品分类
	 * 
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_product_productlist_menu","merchant_product_addproduct_menu","merchant_product_famousproductlist_menu"})
	@RequestMapping(value = "qc", method = RequestMethod.POST)
	public void query_product_categorye(ModelMap model,HttpServletRequest request,@RequestBody BasePojo req) {
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			model.putAll(product_Service.query_product_categorye(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	@RequestMapping(value = "qpc", method = RequestMethod.GET)
	public void query_product_by_categorye(ModelMap model, String parentId,HttpServletRequest request) {
		try {
			String clientId = request.getAttribute(Constants.CLIENT_ID).toString();
			LogCvt.info("商品分类级联 :< parentId: "+parentId+">");
			model.putAll(product_Service.product_by_categorye(parentId,clientId));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
} 
