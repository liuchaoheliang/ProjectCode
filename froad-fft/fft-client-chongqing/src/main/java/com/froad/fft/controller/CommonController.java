package com.froad.fft.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;






import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.bean.SystemConfig.CaptchaType;
import com.froad.fft.common.CookieKey;
import com.froad.fft.common.SessionKey;
import com.froad.fft.dto.SmsDto;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.SmsType;
import com.froad.fft.support.base.CaptchaSupport;
import com.froad.fft.support.base.impl.SmsSupportImpl;
import com.froad.fft.util.NullValueCheckUtil;
import com.froad.fft.util.SMSCommonCodeUtil;
import com.froad.fft.util.WebUtil;

/**
 * 公共  controller
 * @author FQ
 *
 */
@Controller("common")
@RequestMapping("/common")
public class CommonController extends BaseController{
	
	@Resource
	private CaptchaSupport captchaSupport;
	
	@Resource(name = "smsSupportImpl")
	private SmsSupportImpl smsSupportImpl;
	
	private final static String codeTimeType = "yyyy-MM-dd HH:mm:ss";
	private static SimpleDateFormat sdf = new SimpleDateFormat(codeTimeType);
	private final int loseEffSecound = 300;//短信随机验证码失效时间
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>资源不存在警告</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月27日 上午10:08:33 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping("warn_404")
	public void warm_404(){}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>服务器内部错误</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月27日 上午10:08:57 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping("error_500")
	public void error_500(){}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>参数丢失或者校验异常的统一处理页面</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月15日 下午1:05:17 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping("illegality")
	public void illegality(){}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>校验验证码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月29日 下午1:58:29 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "validate_code" , method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean validateCode(String captchaId,String captcha,String captchaType){
		return new AjaxCallBackBean(captchaSupport.isValid(CaptchaType.valueOf(captchaType), captchaId, captcha));
	}
	
	/**
	 * 验证码
	 */
	@RequestMapping(value = "captcha", method = RequestMethod.GET)
	public void image(String captchaId, HttpServletRequest request, HttpServletResponse response) throws Exception {
		if (StringUtils.isEmpty(captchaId)) {
			captchaId = request.getSession().getId();
		}
		String pragma = new StringBuffer().append("yB").append("-").append("der").append("ewoP").reverse().toString();
		String value = new StringBuffer().append("ten").append(".").append("xxp").append("ohs").reverse().toString();
		response.addHeader(pragma, value);
		response.setHeader("Pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setHeader("Cache-Control", "no-store");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		ServletOutputStream servletOutputStream = null;
		try {
			servletOutputStream = response.getOutputStream();
			BufferedImage bufferedImage = captchaSupport.buildImage(captchaId);
			ImageIO.write(bufferedImage, "jpg", servletOutputStream);
			servletOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(servletOutputStream);
		}
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用于发送ajax时检查是否登录</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月25日 下午10:52:54 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "check_user_login" , method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean checkUserLogin(HttpServletRequest req) {
		if(getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION) == null){
			return new AjaxCallBackBean(false);
		}
		return new AjaxCallBackBean(true);
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用于发送常用短信随机验证码的工具类</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月29日 下午2:11:59 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "send_code_sms", method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean sendSysSMS(HttpServletRequest req,String smsType,String toPhoneNumber){
		SmsType sType = null;
		
		try {
			sType = SmsType.valueOf(smsType);
		} catch (Exception e) {
			return new AjaxCallBackBean(false, "系统繁忙请稍后重试");
		}
		
		if(SmsType.authcodeRegister.equals(sType) || SmsType.authcodeModifiedMobile.equals(sType)){
			//注册需要进行用户唯一校验
			UserEngineDto userEngineDto = getUserEngineByLoginID(toPhoneNumber);
			if(userEngineDto != null){
				return new AjaxCallBackBean(false, "该手机号码已注册");
			}
		}else if(SmsType.authcodeResetPwd.equals(sType)){
			//重置密码需要进行用户是否注册校验
			UserEngineDto userEngineDto = getUserEngineByLoginID(toPhoneNumber);
			if(userEngineDto == null){
				return new AjaxCallBackBean(false, "该手机号码未注册");
			}
		}
		
		String randomCode = SMSCommonCodeUtil.getRandomCode();
		String[] args = new String[]{randomCode,new SimpleDateFormat("MM月dd日 HH:mm").format(new Date())};
		SmsDto smsDto = null;
		try {
			smsDto = new SmsDto(sType, null, args, toPhoneNumber,req.getRemoteAddr(),true);
		} catch (Exception e) {
			logger.error("页面传入的smsType非法" + smsType);
			return new AjaxCallBackBean(false);
		}
		boolean flag = (smsSupportImpl.sendSms(smsDto)).isFlag();
		if(flag){
			createSessionObject(req, SessionKey.VALIDATION_COMMON_TEMP_VAL, randomCode+ "|" + sdf.format(new Date()));
			return new AjaxCallBackBean(flag);
		}
		return new AjaxCallBackBean(false);
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>校验手机短信验证码</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月29日 下午7:49:12 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	private static Date addMinutes(Date time, int delaySecound) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(Calendar.SECOND, delaySecound);
		return calendar.getTime();
	}
	@RequestMapping(value = "validate_code_sms" , method = RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean validateCodeMsm(HttpServletRequest req,String smsCode){
		String smsOirgCode = (String)getSessionValue(req, SessionKey.VALIDATION_COMMON_TEMP_VAL);//获取验证码真实数据
		if(NullValueCheckUtil.isStrEmpty(smsOirgCode)){
			return new AjaxCallBackBean(false,"请获取验证码再操作！");
		}
		String[] strArray = smsOirgCode.split("\\|");
		String oirgCode = strArray[0];
		String dateStr = strArray[1];
		try {
			Date dateOirg = addMinutes(sdf.parse(dateStr), loseEffSecound);
			Date dateNow = new Date();
			if(dateNow.before(dateOirg)){//如果当前时间在追加时间之前则有效
				if(oirgCode.equals(smsCode)){
					return new AjaxCallBackBean(true);
				}else{
					return new AjaxCallBackBean(false, "验证码错误，请重新输入");
				}
			}else{
				//已过期
				return new AjaxCallBackBean(false, "验证码已过期，请重新获取");
			}
		} catch (ParseException e) {
			logger.error("验证短信验证码发送时间异常", e);
			return new AjaxCallBackBean(false,"验证码无效，请重新获取");
		}
	}
	
	
	
	

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用户退出</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月10日 下午8:33:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "logout" )
	public String loginOut(HttpServletRequest req,HttpServletResponse res){
		removeAllSession(req);
		WebUtil.removeCookie(req, res, CookieKey.COOKIE_LOGIN_NAME);
		return "/shop/login/index";
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>用于静态页面获取头部信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月11日 上午12:02:00 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "index_header_info" , method = RequestMethod.POST )
	public String getHeaderInfo(){
		return "/include/system/sso/sso_index_header";
	}
	
	/**
	 * 权限
	 */
	@RequestMapping("unauthorized")
	public String unauthorized(HttpServletRequest request, HttpServletResponse response) {
		String requestType = request.getHeader("X-Requested-With");
		if (requestType != null && requestType.equalsIgnoreCase("XMLHttpRequest")) {
			response.addHeader("loginStatus", "unauthorized");
			try {
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		return "/common/unauthorized";
	}
//	/**
//	 * 列表
//	 */
//	@RequestMapping(value = "/common/{name}", method = RequestMethod.GET)
//	public String path(@PathVariable String name) {
//		return "/common/" + name;
//	}
}
