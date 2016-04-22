package com.froad.cbank.coremodule.normal.boss.controller.merchant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.OutletInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.support.merchant.MerchantOutletSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;


/**
 * 类描述：商户门店相关接口
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年4月27日 下午3:02:40 
 */
@Controller
@RequestMapping(value="/merchantOutlet")
public class MerchanrOutletController{
	@Resource
	private MerchantOutletSupport merchantOutletSupport;
	/**
	  * 方法描述：商户门店列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_outlet_menu"})
	@RequestMapping(value="lt", method=RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,OutletInfoVoReq voReq){
		LogCvt.info("商户门店列表查询条件："+JSON.toJSONString(voReq));
		try {
			model.clear();
			if(StringUtils.isBlank(voReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(voReq.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(merchantOutletSupport.queryOutletList(voReq));
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			LogCvt.error("商户门店列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "商户门店列表查询失败!!!");
		}
	}
}
