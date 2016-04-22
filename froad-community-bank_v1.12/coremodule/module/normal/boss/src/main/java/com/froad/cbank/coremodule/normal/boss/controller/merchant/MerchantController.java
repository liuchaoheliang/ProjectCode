package com.froad.cbank.coremodule.normal.boss.controller.merchant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantReq;
import com.froad.cbank.coremodule.normal.boss.support.merchant.MerchantSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;


/**
 * 类描述：商户相关接口
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年4月27日 下午2:52:10 
 */
@Controller
@RequestMapping(value="/merchant")
public class MerchantController{	
	@Resource
	private MerchantSupport merchantSupport;
		
	/**
	  * 方法描述：商户列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_merchant_menu"})
	@RequestMapping(value="lt", method=RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,MerchantReq voReq){
		LogCvt.info("商户列表查询条件："+JSON.toJSONString(voReq));
		try {
			model.clear();
			if(StringUtils.isBlank(voReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(voReq.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(merchantSupport.queryList(voReq));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("商户列表请求异常"+e.getMessage(), e);
			new RespError(model, "商户列表失败!!!");
		}
	}
			
	/**
	  * 方法描述：商户详情
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:44:01
	  */
	@RequestMapping(value="rp", method=RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,String merchantId){
		LogCvt.info("商户详情查询条件：merchantId "+merchantId);	
		model.clear();
		try {
			if(StringUtil.isNotBlank(merchantId)){
				model.putAll(merchantSupport.detail(merchantId));
			}else{
				new RespError(model, "商户id不能为空!!!");		
			}
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("商户信息详情请求异常"+e.getMessage(), e);
			new RespError(model, "商户信息详情失败!!!");
		}
	}
	
	/**
	 * 根据客户端ID获取商户类型
	 * @author yfy
	 * @date 2015-7-17 上午11:15:32
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "mtList",method = RequestMethod.GET)
	public void getMerchantType(ModelMap model,HttpServletRequest req,String clientId){
		try {
			model.putAll(merchantSupport.getMerchantType(clientId));
		} catch (TException e) {
			LogCvt.error("获取商户类型请求异常"+e.getMessage(), e);
			new RespError(model, "获取商户类型失败!!!");
		}
	}
	
	/**
	 * @Title: 商户列表条件查询导出
	 * @author froad
	 * @date 2015-5-09下午15:08:32
	 * @param model
	 * @param req
	 * @param resp
	 */
	@Auth(keys={"boss_merchant_export"})
	@RequestMapping(value = "merchantExport",method = RequestMethod.GET)
	public void merchantExport(ModelMap model,HttpServletRequest request,MerchantReq voReq){
		LogCvt.info("商户列表条件查询导出条件："+JSON.toJSONString(voReq));
		try{
			model.clear();
			if(StringUtils.isBlank(voReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(voReq.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(merchantSupport.export(voReq));
		} catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			new RespError(model, "下载异常!");
		}
    }
}
