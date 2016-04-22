package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.HashMap;
import java.util.List;
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
import com.froad.cbank.coremodule.module.normal.bank.service.MerchantManageService;
import com.froad.cbank.coremodule.module.normal.bank.service.PresaleProductService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.PresaleProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductInfoVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;

/**
 * 预售商品管理
 * @author yfy
 */
@Controller
@RequestMapping(value = "/presaleProduct")
public class PresaleProductController extends BasicSpringController{
	
	@Resource
	private PresaleProductService presaleProductService;
	@Resource
	private MerchantManageService mmService;
	@Resource
	private LoginService loginService;
	/**
	 * @Title: 预售商品信息列表条件查询
	 * @author yfy
	 * @date 2015-3-23 上午9:33:10
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"fomous_yushou_menu","fomous_yushou_select_bind","presale_product_list_menu","presale_product_select_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
    public void productList(ModelMap model,HttpServletRequest req,@RequestBody ProductVoReq voReq) {
		try {
			long begin = System.currentTimeMillis();
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			//条件过滤
			Map<String,String> filter=new HashMap<String, String>();
			filter.put("orgCode",voReq.getOrgCode());
			filter.put("auditOrgCode",voReq.getAuditOrgCode());
			//上下架状态
			filter.put("isMarketable", voReq.getIsMarketable());
			filter.put("name",voReq.getProductName());
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
			filter.put("type",ProductTypeEnum.PRESALE.getCode());
			filter.put("filterAuditState",AuditFlagEnum.NO_NEW.getCode());//过滤未提交审核状态
			filter.put("orderField", "{'1':'createTime-desc'}");
			
			QueryResult<PresaleProductVoRes> queryVo = presaleProductService
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
			
			Monitor.send(MonitorEnums.bank_presaleProduct_lt, String.valueOf(System.currentTimeMillis()-begin));
			
			model.put("message", "预售商品信息列表条件查询成功");	
			
		} catch (Exception e) {
			LogCvt.info("预售商品信息列表条件查询"+e.getMessage(), e);
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
	
	@CheckPermission(keys={"add_pre_ad"})
	@RequestMapping(value = "/ad",method = RequestMethod.POST)
	public void productAdd(ModelMap model,HttpServletRequest req,@RequestBody ProductInfoVoReq voReq){
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = presaleProductService.verify(voReq);
			if(msgVo.getResult()){
				msgVo = presaleProductService.productAdd(voReq, loginService.getOriginVo(req));
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
			LogCvt.info("预售商品新增"+e.getMessage(), e);
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
	
	@CheckPermission(keys={"mod_pre_ue"})
	@RequestMapping(value = "/ue",method = RequestMethod.PUT)
	public void productUpdate(ModelMap model,HttpServletRequest req,@RequestBody ProductInfoVoReq voReq){
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = presaleProductService.verify(voReq);
			if(msgVo.getResult() && StringUtil.isNotEmpty(voReq.getProductId())){
				msgVo = presaleProductService.productUpdate(voReq, loginService.getOriginVo(req));
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
			LogCvt.info("预售商品修改"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 商品上/下架
	 * @author yfy
	 * @date 2015-3-23 上午10:53:17
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"presale_product_it_up","presale_product_it_down","group_product_it_up","group_product_it_down","preferential_product_it_up","preferential_product_it_down"})
	@RequestMapping(value = "/it",method = RequestMethod.POST)
    public void productIsMarketable(ModelMap model,HttpServletRequest req,@RequestBody ProductVoReq voReq){
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(StringUtil.isNotEmpty(voReq.getProductId()) && StringUtil.isNotEmpty(voReq.getIsMarketable())){
				MsgVo msgVo = presaleProductService.upDownProduct(voReq.getClientId(), 
						voReq.getProductId(),voReq.getIsMarketable(), 
						loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "商品ID或状态不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("商品信息上/下架"+e.getMessage(), e);
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
	@CheckPermission(keys={"seckill_product_detail_bind","presale_product_edit","presale_product_detail_bind","to_audit_seckill_list_detail_bind","foumos_yushou_detail_detail","fomous_yushou_detail_bind","to_audit_seckilldetail_jpys","mod_pre","yushou_detail","seckilldetail_jpys"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
    public void productDetail(ModelMap model,HttpServletRequest req,String productId){
		
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(productId)){
				ProductInfoVoReq product = presaleProductService.productDetail(productId,clientId);
				if(product != null){
					model.put("product", product);
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "商品ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("预售商品信息详情查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 商品删除
	 * @author yfy
	 * @date 2015-4-7 下午17:53:17
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"presale_product_de","preferential_product_de"})
	@RequestMapping(value = "/de",method = RequestMethod.DELETE)
    public void productDelete(ModelMap model,HttpServletRequest req,String productId){
		
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(productId)){
				MsgVo msgVo = presaleProductService.productDelete(clientId,
						productId, loginService.getOriginVo(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "商品ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("商品删除"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 获取当前登录用户下的所有门店（根据当前机构号）
	 * @author yfy
	 * @date 2015-3-23 下午14:26:10
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/sc",method = RequestMethod.POST)
	 public void selectOrgCode(ModelMap model,HttpServletRequest req,@RequestBody BankOrgVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(StringUtil.isNotEmpty(voReq.getOrgCode())){
				List<BankOrgRes> bankOrgList = mmService.getBankOrgList(voReq.getClientId(), voReq.getOrgCode());
				model.put("bankOrgList", bankOrgList);
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "组织编码不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("获取当前登录用户下的所有门店查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 是否有权限录入商品
	 * @author yfy
	 * @param model
	 * @param myOrgCode
	 */
	@RequestMapping(value = "/vap",method = RequestMethod.GET)
	public void verifyAddMerchant(ModelMap model,HttpServletRequest req,String orgCode,String type){
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			model.putAll(presaleProductService.verifyAddProduct(clientId,orgCode,type));
		} catch (Exception e) {
			LogCvt.info("是否有权限录入商品"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	
	
}
