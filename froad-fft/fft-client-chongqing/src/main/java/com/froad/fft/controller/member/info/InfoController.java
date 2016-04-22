
	 /**
  * 文件名：InfoController.java
  * 版本信息：Version 1.0
  * 日期：2014年4月2日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.controller.member.info;

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
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.support.base.UserEngineSupport;
import com.froad.fft.util.NullValueCheckUtil;


	/**
 * 类描述：用户信息
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月2日 上午10:58:32 
 */

@Controller("member.info")
@RequestMapping("/member/info")
public class InfoController extends BaseController {
	
	@Resource
	private UserEngineSupport userEngineSupport;
	
	@RequestMapping(value = "index" , method = RequestMethod.GET)
	public String index (HttpServletRequest req,ModelMap model){
		Object obj = getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		if(obj == null){//如果必要信息丢失，则返回加载失败页面
			return ILLEGALITY;
		}
		model.put("user", (UserEngineDto)obj);
		return "/member/info/index";
	}
	
	@RequestMapping(value = "update_info" , method = RequestMethod.POST)
	public  @ResponseBody AjaxCallBackBean updateUserInfo(HttpServletRequest req ,String newUsername){
		if(!NullValueCheckUtil.isStrEmpty(newUsername)){
			//获取登录成功用户信息
			UserEngineDto user = (UserEngineDto)getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);	
			Long memberCode = user.getMemberCode();
			String loginID = user.getLoginID();
			user = new UserEngineDto();
			user.setMemberCode(memberCode);
			user.setUname(newUsername);
			user = userEngineSupport.updateMemberInfo(user);
			if(user.getResult()){		
				user = userEngineSupport.queryByLoginId(loginID);
				updateUserAllInfo(req);
				return new AjaxCallBackBean(true,"修改成功");
			}else{
				logger.info("修改用户信息失败，原因：" + user.getErrorMsg());
				return new AjaxCallBackBean(false,user.getErrorMsg());
			}
		}else{
			logger.info("修改用户信息,传入参数为空");
			return new AjaxCallBackBean(false,"请输入名字");
		}
	}
}
