package com.froad.cbank.coremodule.normal.boss.controller.merchant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.merchant.OutletCommentVoReq;
import com.froad.cbank.coremodule.normal.boss.support.merchant.MerchantOutletSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.RecommentNotEmptyVo;

/**
 * 类描述：商户门店评相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2015年4月27日 下午3:02:40 
 */

@Controller
@RequestMapping(value = "/merchantComment")
public class OutletCommentController{
	@Resource
	private MerchantOutletSupport merchantOutletSupport;
	/**
	  * 方法描述：商户门店评价列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: @f-road.com.cn
	  * @time: 2015年4月27日 下午1:42:15
	  */
	@Auth(keys={"boss_merchant_co_menu"})
	@RequestMapping(value="lt", method=RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,OutletCommentVoReq voReq){
		LogCvt.info("商户门店评价列表查询条件："+JSON.toJSONString(voReq));
		try {
			model.clear();
			if(StringUtils.isBlank(voReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			//商户条件筛选
			OutletCommentVo merchantOutletVo=new OutletCommentVo();
			if(voReq!=null && StringUtil.isNotBlank(voReq.getClientId())){
				merchantOutletVo.setClientId(voReq.getClientId());
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.getMerchantName())){
				merchantOutletVo.setMerchantName(voReq.getMerchantName());
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.getRecommentStatus())){
				if("0".equals(voReq.getRecommentStatus())){
					RecommentNotEmptyVo recommentNotEmpty =new RecommentNotEmptyVo(); 
					recommentNotEmpty.setNotEmpty(false);
					merchantOutletVo.setRecommentNotEmpty(recommentNotEmpty);
				}
				if("1".equals(voReq.getRecommentStatus())){
					RecommentNotEmptyVo recommentNotEmpty2 =new RecommentNotEmptyVo();
					recommentNotEmpty2.setNotEmpty(true);
					merchantOutletVo.setRecommentNotEmpty(recommentNotEmpty2);
				}
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.getBeginTime()) && voReq.getBeginTime() != 0){
				voReq.setStartDate(voReq.getBeginTime());	
			}
			if(voReq!=null && StringUtil.isNotBlank(voReq.getEndTime())  && voReq.getEndTime() != 0){
				voReq.setEndDate(voReq.getEndTime());
			}
			voReq.setOutletCommentVo(merchantOutletVo);
			model.putAll(merchantOutletSupport.queryList(voReq));
		} catch(BossException e){
			new RespError(model,e);
		}catch (Exception e) {
			LogCvt.error("商户门店评价列表请求异常"+e.getMessage(), e);
			new RespError(model, "商户门店评价列表失败!!!");
		}
	}
}
