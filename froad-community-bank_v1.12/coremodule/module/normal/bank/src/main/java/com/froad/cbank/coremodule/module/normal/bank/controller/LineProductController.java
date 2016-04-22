package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.froad.cbank.coremodule.module.normal.bank.service.BankOrgService;
import com.froad.cbank.coremodule.module.normal.bank.service.LineProductService;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.util.TargetObjectFormat;
import com.froad.cbank.coremodule.module.normal.bank.vo.BankOrgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.LineProductVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.LineProductVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductInfoVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.ProductVoReq;

/**
 * 线下积分兑换管理
 * @author yfy
 * @date 2015-4-7 下午16:36:28
 */
@Controller
@RequestMapping(value = "/lineProduct")
public class LineProductController extends BasicSpringController{

	@Resource
	private LineProductService lineProductService;
	@Resource
	private BankOrgService bankOrgService;
	@Resource
	private LoginService loginService;


	/**
	 * @Title: 线下积分兑换列表条件查询
	 * @author yfy
	 * @date 2015-4-7 下午16:36:28
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"outline_integral_menu","outline_integral_product_select_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
    public void lineList(ModelMap model,HttpServletRequest req,@RequestBody ProductVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			BankOrgVo vo = bankOrgService.getOrgNameByOrgCode(voReq.getClientId(),voReq.getOrgCode());
			if(StringUtil.isNotEmpty(vo.getOutletId())){
				//条件过滤
				Map<String,String> filter=new HashMap<String, String>();
				filter.put("outletId",vo.getOutletId());
				filter.put("priceStart",voReq.getPriceStart());
				filter.put("priceEnd",voReq.getPriceEnd());
				filter.put("name",voReq.getProductName());
				filter.put("type",ProductTypeEnum.LINEUP.getCode());
				
				QueryResult<LineProductVoRes> queryVo = lineProductService.findPageByConditions(
						voReq.getClientId(),JSON.toJSONString(filter), 
						voReq.getPageNumber(), voReq.getPageSize());
				model.put("lineProductList", queryVo.getResult());
				model.put("totalCount", queryVo.getTotalCount());
				model.put("pageCount", queryVo.getPageCount());
				model.put("pageNumber", queryVo.getPageNumber());//总页
			}else{
				model.put("lineProductList", null);
				model.put("totalCount", 0);
				model.put("pageCount", 0);
				model.put("pageNumber", 1);//总页
			}
			model.put("message", "线下积分兑换列表条件查询成功");	
			
		} catch (Exception e) {
			LogCvt.info("线下积分兑换列表条件查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}

	/**
	 * @Title: 线下积分兑换礼品列表条件查询
	 * @author yfy
	 * @date 2015-4-7 下午16:36:28
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"fomous_offline_menu","fomous_offline_select_bind","outline_present_menu","outline_present_select_bind"})
	@RequestMapping(value = "/plt",method = RequestMethod.POST)
    public void lineProductList(ModelMap model,HttpServletRequest req,@RequestBody ProductVoReq voReq) {
		try {
			long begin = System.currentTimeMillis();
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			//条件过滤
			Map<String,String> filter=new HashMap<String, String>();
			filter.put("orgCode",voReq.getOrgCode());
			filter.put("auditOrgCode",voReq.getAuditOrgCode());
			filter.put("priceStart",voReq.getPriceStart());
			filter.put("priceEnd",voReq.getPriceEnd());
			filter.put("name",voReq.getProductName());
			filter.put("isMarketable", voReq.getIsMarketable());
			filter.put("auditState", voReq.getAuditState());
			if(StringUtil.isNotEmpty(voReq.getAuditStartTime())){
				filter.put("auditStartTime", DateUtil.DateFormat(voReq.getAuditStartTime())+"");//审核开始时间
			}
			if(StringUtil.isNotEmpty(voReq.getAuditEndTime())){
				filter.put("auditEndTime",DateUtil.DateFormatEnd(voReq.getAuditEndTime())+"");//审核结束时间
			}
			filter.put("type",ProductTypeEnum.LINEUP.getCode());
			filter.put("filterAuditState",AuditFlagEnum.NO_NEW.getCode());//过滤未提交审核状态
			filter.put("orderField", "{'1':'createTime-desc'}");
			
			QueryResult<LineProductVoRes> queryVo = lineProductService.findPageByProductConditions(
					voReq.getClientId(),JSON.toJSONString(filter), voReq.getPageNumber(),
					voReq.getPageSize(),voReq.getLastPageNumber(),voReq.getFirstRecordTime(),
					voReq.getLastRecordTime());
			model.put("lineProductList", queryVo.getResult());
			model.put("totalCount", queryVo.getTotalCount());
			model.put("pageCount", queryVo.getPageCount());
			model.put("pageNubmer", queryVo.getPageNumber());
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
			
			model.put("message", "线下积分礼品列表条件查询成功");	
			
			Monitor.send(MonitorEnums.bank_lineProduct_lt, String.valueOf(System.currentTimeMillis()-begin));
		} catch (Exception e) {
			LogCvt.info("线下积分礼品列表条件查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 线下积分兑换新增
	 * @author yfy
	 * @date 2015-3-20 下午13:58:13
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"add_offline_save"})
	@RequestMapping(value = "/ad",method = RequestMethod.POST)
    public void lineDownAdd(ModelMap model,HttpServletRequest req,@RequestBody ProductInfoVoReq lineProduct){
		try {
			lineProduct.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = lineProductService.verify(lineProduct);
			if(msgVo.getResult()){
				msgVo = lineProductService.productAdd(lineProduct, loginService.getOriginVo(req));
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
			LogCvt.info("线下积分礼品新增"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 线下积分兑换修改
	 * @author yfy
	 * @date 2015-3-20 下午14:13:18
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/ue",method = RequestMethod.PUT)
	 public void lineDownUpdate(ModelMap model,HttpServletRequest req,@RequestBody ProductInfoVoReq lineProduct) {
		try {
			lineProduct.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = lineProductService.verify(lineProduct);
			if(msgVo.getResult() && StringUtil.isNotEmpty(lineProduct.getProductId())){
				msgVo = lineProductService.productUpdate(lineProduct, loginService.getOriginVo(req));
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
			LogCvt.info("线下积分礼品修改"+e.getMessage(), e);
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
	@CheckPermission(keys={"outline_present_add","outline_present_detail_bind","fomous_offline_detail_detail","outline_present_dl","outline_integral_gift","fomous_offline_detail_bind"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
    public void productDetail(ModelMap model,HttpServletRequest req,String productId){
		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(productId)){
				ProductInfoVoReq product = lineProductService.productDetail(clientId,productId);
				if(product != null){
					model.put("product", product);
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "商品ID不能为空");
			}		
		} catch (Exception e) {
			LogCvt.info("线下积分礼品详情查询"+e.getMessage(), e);
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
	@RequestMapping(value = "/de",method = RequestMethod.DELETE)
    public void productDelete(ModelMap model,HttpServletRequest req,ProductVoReq voReq){
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(voReq.getProductIdList() != null && voReq.getProductIdList().size() > 0){
				MsgVo msgVo = lineProductService.productDelete(voReq.getClientId(),voReq.getProductIdList(),
						loginService.getOriginVo(req));
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
			LogCvt.info("线下积分礼品删除"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 商品上/下架
	 * @author yfy
	 * @date 2015-4-7 下午17:53:17
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/it",method = RequestMethod.POST)
    public void productIsMarketable(ModelMap model,HttpServletRequest req,@RequestBody ProductVoReq voReq){
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(voReq.getProductIdList() != null && voReq.getProductIdList().size() > 0
					&& StringUtil.isNotEmpty(voReq.getIsMarketable())){
				MsgVo msgVo = lineProductService.upDownProduct(voReq.getClientId(), 
						voReq.getProductIdList(),voReq.getIsMarketable(), 
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
			LogCvt.info("商品信息"+(voReq.getIsMarketable()=="1"?"上":"下")+"架"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 获取兑换码（根据卡号）
	 * @author yfy
	 * @date 2015-4-7 下午17:53:17
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"outline_integral_gift_getcode"})
	@RequestMapping(value = "/st",method = RequestMethod.POST)
    public void selectByTakeCode(ModelMap model,HttpServletRequest req,@RequestBody LineProductVoReq voReq){
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(StringUtil.isNotEmpty(voReq.getAccountCode()) && voReq.getPoints() != null){
				if(voReq.getAccountCode().length() > 14){
					MsgVo msgVo = lineProductService.selectByTakeCode(voReq.getClientId(),voReq.getAccountCode(),voReq.getPoints());
					if(msgVo.getResult()){
						model.put("code", EnumTypes.success.getCode());
					}else{
						model.put("code", EnumTypes.fail.getCode());
					}
					model.put("message", msgVo.getMsg());
				}else{
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", "卡号不能少于15位");
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "卡号或积分不能为空");
			}	
		} catch (Exception e) {
			LogCvt.info("根据卡号获取兑换码"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	/**
	 * @Title: 验证兑换码
	 * @author yfy
	 * @date 2015-4-7 下午17:53:17
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"outline_integral_gift_sub"})
	@RequestMapping(value = "/vc",method = RequestMethod.POST)
    public void verifyExchangeCode(ModelMap model,HttpServletRequest req,@RequestBody LineProductVoReq voReq){
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			MsgVo msgVo = lineProductService.verifyExchangeCode(voReq);
			if(msgVo.getResult()){
				msgVo = lineProductService.verifyExchangeCode(voReq,TargetObjectFormat.getIpAddr(req));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
					model.put("message", msgVo.getMsg());
				}else{
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", msgVo.getMsg());
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message",  msgVo.getMsg());
			}	
		} catch (Exception e) {
			LogCvt.info("兑换码验证"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	/**
	 * @Title: 积分查询（根据卡号）
	 * @author yfy
	 * @date 2015-4-7 下午17:53:17
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"outline_integral_select_bind"})
	@RequestMapping(value = "/sl",method = RequestMethod.POST)
    public void productSelectPoint(ModelMap model,HttpServletRequest req,@RequestBody LineProductVoReq voReq){
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(StringUtil.isNotEmpty(voReq.getAccountCode())){
				if(voReq.getAccountCode().length() > 14){
					String price = lineProductService.productSelectPoint(voReq.getClientId(),voReq.getAccountCode());
					if(StringUtils.isNotEmpty(price)){
						model.put("price", price);
					}
				}else{
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", "卡号不能少于15位");
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "卡号不能为空");
			}	
		} catch (Exception e) {
			LogCvt.info("根据卡号查询积分"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
}
