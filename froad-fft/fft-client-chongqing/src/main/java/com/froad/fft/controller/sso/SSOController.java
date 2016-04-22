package com.froad.fft.controller.sso;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.util.NullValueCheckUtil;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: SSOController.java </p>
 *<p> 描述: *-- <b>用于辅助SSO功能</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月23日 下午4:59:51 </p>
 ********************************************************
 */
@Controller("sso.core")
@RequestMapping("/sso/core")
public class SSOController extends BaseController{

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>空请求，用于请求以达到100%请求SSO服务器</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月23日 下午5:05:01 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping("support")
	public @ResponseBody void support(){}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>sso退出</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月24日 上午11:15:30 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping("logout")
	public String logout(){
		return "/shop/sso/sso_logout";
	}
	
	@RequestMapping("query_info")
	public @ResponseBody AjaxCallBackBean queryInfo(HttpServletRequest req,String uri){
		if(NullValueCheckUtil.isStrEmpty(uri)){
			if(getSessionValue(req, SessionKey.SSO_MEMBER_INFO) != null){
				return new AjaxCallBackBean(true);
			}
			return new AjaxCallBackBean(false);
		}else{
			if(getSessionValue(req, SessionKey.SSO_MEMBER_INFO) != null){
				String memberInfo = (String)getSessionValue(req, SessionKey.SSO_MEMBER_INFO);
				if(memberInfo.indexOf("\\|") == -1){
					memberInfo += "\\|";
					createSessionObject(req, SessionKey.SSO_MEMBER_INFO, memberInfo);
					return new AjaxCallBackBean(true);
				}else{
					//首页已刷新使用
					return new AjaxCallBackBean(false);
				}
			}else{
				return new AjaxCallBackBean(false);
			}
		}
	}
}
