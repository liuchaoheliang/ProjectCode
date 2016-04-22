package com.froad.fft.controller.member.orderlist.refunds;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.support.base.RefundsSupport;
import com.froad.fft.support.base.TransSupport;
import com.froad.fft.util.NullValueCheckUtil;

/**
* @ClassName: returnController
* @Description: 退款申请
* @author larry
* @date 2014-4-15 下午05:55:07
*
 */
@Controller("orderlist.refunds")
@RequestMapping("/member/orderlist/refunds")
public class ReturnController extends BaseController{
	
	@Resource
	private TransSupport transSupport; 
	
	@Resource
	private RefundsSupport refundsSupport;
	
	/**
	*<p>退款申请页面</p>
	* @author larry
	* @datetime 2014-4-15下午05:42:32
	* @return String
	* @throws 
	* @example<br/>
	*
	 */
	@RequestMapping(value = "return" , method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean transRetrun(String reason,HttpServletRequest req,ModelMap modelMap){
		Object obj = getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL);
		if(obj==null){
			return new AjaxCallBackBean(false,"退款申请失败，请稍后再试");
		}
		TransQueryDto transQueryDto = null;
		if(obj instanceof TransQueryDto){
			transQueryDto = (TransQueryDto)obj;
		}else{
			return new AjaxCallBackBean(false,"系统异常，请稍后再试");
		}
		//判断是否已经申请退款
		if(refundsSupport.selectRefunds(transQueryDto.getId())!=null){
			return new AjaxCallBackBean(false,"您已经申请退款，请不要重复申请。");
		}
		RefundsDto refundsDto = null;
		if(transQueryDto!=null&&transQueryDto.getId()!=null){
			//退款
			logger.info("开始处理退款：transId"+transQueryDto.getId());
			refundsDto = new RefundsDto();
			refundsDto.setReason(reason);
			refundsDto.setTransId(transQueryDto.getId());
			refundsDto.setState(RefundsDto.State.apply);//处理中
			refundsDto.setSysUserId("");
			refundsDto = refundsSupport.applyRefund(refundsDto);
		}
		logger.info("退款申请编号:"+refundsDto.getSn());
		//退款申请成功，移除sessionKey
		removeSessionByKey(req,  SessionKey.SYSTEM_TEMP_VAL);
		return new AjaxCallBackBean(true,"申请成功，稍后我们会处理...");
	}
	
	/**
	*<p>退款申请</p>
	* @author larry
	* @datetime 2014-4-15下午05:42:32
	* @return String
	* @throws 
	* @example<br/>
	*
	 */
	@RequestMapping(value = "return" , method = RequestMethod.GET)
	public String transRetrun(HttpServletRequest req,String transId,ModelMap modelMap){
		logger.info("退款申请 transId" + transId);
		if(NullValueCheckUtil.isStrEmpty(transId)){
			return ILLEGALITY;
		}
		Long id = null;
		try {
			id = Long.parseLong(transId);
		} catch (Exception e) {
			//传入交易id无效
			return ILLEGALITY;
		}
		TransQueryDto trans = transSupport.getByTransId(id);
		if(trans == null){
			return ILLEGALITY; //交易不存在
		}
		createSessionObject(req, SessionKey.SYSTEM_TEMP_VAL, trans);
		modelMap.put("trans", trans);
		return "/common/ajax/member/orderlist/presell_return";
	}
}
