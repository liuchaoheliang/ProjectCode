package com.froad.cbank.coremodule.normal.boss.controller.merchant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.valid.ValidBeanField;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantAccountInfoReq;
import com.froad.cbank.coremodule.normal.boss.support.merchant.MerchantAccountSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 类描述：商户账户相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-3下午7:09:15 
 */
@Controller
@RequestMapping(value = "/merchantAccount")
public class MerchantAccountController{
	@Resource
	private MerchantAccountSupport merchantAccountSupport;
	/**
	  * 方法描述：商户账户列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_merchant_acct"})
	@RequestMapping(value="lt", method=RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,MerchantAccountInfoReq voReq){
		LogCvt.info("商户账户列表查询条件："+JSON.toJSONString(voReq));
		try {
			model.clear();
			//校验数据
			ValidBeanField.valid(voReq);
			//商户条件筛选
			model.putAll(merchantAccountSupport.queryMerchantAccountList(voReq));
			
		} catch (Exception e) {
			LogCvt.error("商户账户列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "商户账户列表查询失败!!!");
		}

		
	}
}
