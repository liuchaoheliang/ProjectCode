package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.enums.AuditFlagEnum;
import com.froad.cbank.coremodule.module.normal.bank.enums.ProductTypeEnum;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.service.PreferentProductService;
import com.froad.cbank.coremodule.module.normal.bank.service.PresaleProductService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.PreferentProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductInfoVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;

/**
 * 名优特惠商品管理
 * @author ylchu
 *
 */
@Controller
@RequestMapping(value = "/preferentialProduct")
public class PreferentProductController extends BasicSpringController {

	@Resource
	private PreferentProductService preferentProductService;
	@Resource
	private PresaleProductService presaleProductService;
	@Resource
	private LoginService loginService;
	/**
	 * @Title: 商品列表查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"audit_famous_menu","audit_famous_select_bind","to_audit_seckilldetail_myth","preferential_product_list_menu","preferential_product_select_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
	public void productList(ModelMap model,HttpServletRequest req,@RequestBody ProductVoReq voReq){
		try {
			long begin = System.currentTimeMillis();
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			//条件过滤
			Map<String,String> filter=new HashMap<String, String>();
			filter.put("orgCode", voReq.getOrgCode());// 机构网点
			filter.put("auditOrgCode", voReq.getAuditOrgCode());// 审核机构
			// 上下架状态
			filter.put("isMarketable", voReq.getIsMarketable());
			filter.put("name",voReq.getProductName());
			filter.put("merchantName",voReq.getMerchantName());
			if(StringUtil.isNotEmpty(voReq.getStartDate())){
				filter.put("startTimeStart", DateUtil.DateFormat(voReq.getStartDate())+"");
			}
			if(StringUtil.isNotEmpty(voReq.getEndDate())){
				filter.put("startTimeEnd",DateUtil.DateFormatEnd(voReq.getEndDate())+"");//结束时间
			}
			if(StringUtil.isNotEmpty(voReq.getAuditStartTime())){
				filter.put("auditStartTime", DateUtil.DateFormat(voReq.getAuditStartTime())+"");//审核开始时间
			}
			if(StringUtil.isNotEmpty(voReq.getAuditEndTime())){
				filter.put("auditEndTime",DateUtil.DateFormatEnd(voReq.getAuditEndTime())+"");//审核结束时间
			}
			filter.put("auditState", voReq.getAuditState());
			filter.put("type",ProductTypeEnum.PREFERENTIAL.getCode());
			filter.put("filterAuditState",AuditFlagEnum.NO_NEW.getCode());//过滤未提交审核状态
			filter.put("orderField", "{'1':'createTime-desc'}");
			QueryResult<PreferentProductVoRes> queryVo = preferentProductService
					.findPageByConditions(voReq.getClientId(),JSON.toJSONString(filter), 
							voReq.getPageNumber(), voReq.getPageSize(),voReq.getLastPageNumber(),
							voReq.getFirstRecordTime(),voReq.getLastRecordTime());
			
			model.put("productList", queryVo.getResult());//商品列表
			model.put("totalCount", queryVo.getTotalCount());//总记录数
			model.put("pageCount", queryVo.getPageCount());//总页数
			model.put("pageNumber", queryVo.getPageNumber());//总页
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
			
			Monitor.send(MonitorEnums.bank_preferentialProduct_lt, String.valueOf(System.currentTimeMillis()-begin));
			
			model.put("message", "名优特惠商品信息列表条件查询成功");	
		
		} catch (Exception e) {
			LogCvt.info("名优特惠商品信息列表条件查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	
	/**
	 * @Title: 商品新增
	 * @author yfy
	 * @date 2015-3-23 上午10:53:17
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"add_fam_ad"})
	@RequestMapping(value = "/ad",method = RequestMethod.POST)
	public void productAdd(ModelMap model,HttpServletRequest req,@RequestBody ProductInfoVoReq preferentProductVo){
		try {
			preferentProductVo.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = preferentProductService.verify(preferentProductVo);
			if(msgVo.getResult()){
				msgVo = preferentProductService.productAdd(preferentProductVo, loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", msgVo.getMsg());	
			}
		} catch (Exception e) {
			LogCvt.info("名优特惠商品新增"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 商品修改
	 * @author yfy
	 * @date 2015-3-23 上午10:53:17
	 * @param model
	 * @param req
	 */
	

	@CheckPermission(keys={"mod_fam_ue"})
	@RequestMapping(value = "/ue",method = RequestMethod.PUT)
	public void productUpdate(ModelMap model,HttpServletRequest req,@RequestBody ProductInfoVoReq preferentProductVo){
		
		try {
			preferentProductVo.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = preferentProductService.verify(preferentProductVo);
			if(msgVo.getResult() && StringUtil.isNotEmpty(preferentProductVo.getProductId())){
				msgVo = preferentProductService.productUpdate(preferentProductVo, loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				if(msgVo.getResult()){
					model.put("message", "商品ID不能为空");
				}else{
					model.put("message", msgVo.getMsg());
				}
				model.put("code", EnumTypes.empty.getCode());
			}
	
		} catch (Exception e) {
			LogCvt.info("名优特惠商品修改"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 商品详情
	 * @author yfy
	 * @date 2015-3-23 上午10:53:17
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"seckill_product_detail_bind","preferential_product_edit","preferential_product_detail_bind","audit_famous_detail_detail","mod_fam","famous_pre_detail","seckilldetail_myth","audit_famous_detail_bind","to_audit_seckill_list_detail_bind"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
    public void productDetail(ModelMap model,HttpServletRequest req,String productId){
		
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(productId)){
				ProductInfoVoReq product = preferentProductService.productDetail(clientId,productId);
				if(product != null){
					model.put("product", product);
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "商品ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("名优特惠商品信息详情查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
}
