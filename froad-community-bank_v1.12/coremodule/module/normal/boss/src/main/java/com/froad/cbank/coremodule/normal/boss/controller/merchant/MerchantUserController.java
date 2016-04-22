package com.froad.cbank.coremodule.normal.boss.controller.merchant;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.MerchantUserInfoReq;
import com.froad.cbank.coremodule.normal.boss.support.merchant.MerchantOutletSupport;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.MerchantUserVo;

/**
 * 类描述：商户用户相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-3下午1:43:37 
 */

@Controller
@RequestMapping(value = "/merchantUser")
public class MerchantUserController{
	@Resource
	private MerchantOutletSupport merchantOutletSupport;
	/**
	  * 方法描述：商户门店用户列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_merchant_user","boss_merchant_user_menu"})
	@RequestMapping(value="lt", method=RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,MerchantUserInfoReq voReq){
		LogCvt.info("商户门店用户列表查询条件："+JSON.toJSONString(voReq));
		try {
			model.clear();
			MerchantUserVo merchantUserVo=new MerchantUserVo();
			if(voReq!=null && StringUtil.isNotBlank(voReq.getOutletId())){
				merchantUserVo.setOutletId(voReq.getOutletId());	
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.getMerchantId())){
				merchantUserVo.setMerchantId(voReq.getMerchantId());	
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.getUserName())){
				merchantUserVo.setUsername(voReq.getUserName());	
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.isDelete())){
				merchantUserVo.setIsDelete(voReq.isDelete());	
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.getBeginTime()) && voReq.getBeginTime() != 0){
				Date dd = DateUtil.longToDate(Long.valueOf(voReq.getBeginTime()));
				String dateTmp = DateUtil.formatDate(dd, false);
				String t = dateTmp+" 00:00:00";
				SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				voReq.setStartDate(re.parse(t).getTime());	
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.getEndTime())  && voReq.getEndTime() != 0){
				Date dd = DateUtil.longToDate(Long.valueOf(voReq.getEndTime()));
				String dateTmp = DateUtil.formatDate(dd, false);
				String t2 = dateTmp+" 23:59:59";
				SimpleDateFormat re = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				voReq.setEndDate(re.parse(t2).getTime());
			}
			voReq.setMerchantUserVo(merchantUserVo);
			model.putAll(merchantOutletSupport.queryOutletUserList(voReq));
		} catch (Exception e) {
			LogCvt.error("商户门店用户列表请求异常"+e.getMessage(), e);
			new RespError(model, "商户门店用户列表失败!!!");
		}		
	}

}
