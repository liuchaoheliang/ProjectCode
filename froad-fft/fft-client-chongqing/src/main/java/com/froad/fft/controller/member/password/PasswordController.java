package com.froad.fft.controller.member.password;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
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

@Controller("member.password")
@RequestMapping("/member/password")
public class PasswordController extends BaseController{
	
	@Resource
	private UserEngineSupport userEngineSupport;

	@RequestMapping(value = "index" , method = RequestMethod.GET)
	public String index(HttpServletRequest req,ModelMap model){
		Object obj = getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		if(obj == null){//如果必要信息丢失，则返回加载失败页面
			return ILLEGALITY;
		}
		UserEngineDto userEngineDto = (UserEngineDto)obj;
		model.put("mobile", userEngineDto.getMobileEncrypt());
		return "/member/password/index";
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>校验用户密码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月1日 上午11:49:15 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "new_password" , method = RequestMethod.POST)
	public String validationPwd(HttpServletRequest req,HttpServletResponse res,String password,ModelMap model){
		Object obj = getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		if(obj == null){//如果必要信息丢失，则返回加载失败页面
			return ILLEGALITY;
		}
		UserEngineDto userEngineDto = (UserEngineDto)obj;
		String encoderPwd = new Md5PasswordEncoder().encodePassword(password, userEngineDto.getLoginID());
		
		if(encoderPwd.equals(userEngineDto.getLoginPwd())){//密码匹配成功
			model.put("userEngineDto", userEngineDto);
			//将需要验证的信息临时存储，用于下一步校验，防止跨过该步验证
			createSessionObject(req, SessionKey.VALIDATION_COMMON_TEMP_VAL, password);
			return "/member/password/new_password";
		}else{
			//原始密码输入错误
			model.put("mobile", userEngineDto.getMobileEncrypt());
			model.put("msg", "原密码输入错误，请重试");
			return "/member/password/index";
		}
		
	}
	@RequestMapping(value = "update_pwd" , method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean updatePwd(HttpServletRequest req , HttpServletResponse res,String newpwd,String email){
		if(NullValueCheckUtil.isStrEmpty(newpwd)){
			return new AjaxCallBackBean(false,"请输入新密码");
		}
		Object obj = getSessionValue(req, SessionKey.VALIDATION_COMMON_TEMP_VAL);
		if(obj == null){
			return new AjaxCallBackBean(false,"对不起，错误的访问请求，请重新登录");//无存储的上一步验证密码
		}else{
			String password = (String)obj;
			obj = getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
			if(obj == null){//丢失登录信息
				return new AjaxCallBackBean(false,"对不起，登录超时请重新登录");
			}else{
				UserEngineDto userEngineDto = (UserEngineDto)obj;
				String encoderPwd = new Md5PasswordEncoder().encodePassword(password, userEngineDto.getLoginID());
				if(encoderPwd.equals(userEngineDto.getLoginPwd())){//密码匹配成功
					//进行下一步操作
					if(!NullValueCheckUtil.isStrEmpty(userEngineDto.getEmail())){//如果这个用户有邮箱地址
						//TODO: 用户修改密码存在邮箱地址
					}
					userEngineDto = userEngineSupport.updatePwd(userEngineDto.getMemberCode(), password, newpwd);
					if(userEngineDto.getResult()){
						//修改密码成功
					//	removeAllSession(req); SSO关系，不能清除session信息
						return new AjaxCallBackBean(true,"密码修改成功，请重新登录");
					}else{
						logger.info("修改密码失败");
						return new AjaxCallBackBean(false,"密码修改失败，请稍后重试");
					}
				}else{
					return new AjaxCallBackBean(false,"对不起，错误的访问请求，请重新登录");//上一步存储的密码无法在此校验成功
				}
			}
		}
	}
}
