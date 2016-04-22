package com.froad.baseAction;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;


import com.froad.AppException.AppException;
import com.froad.action.IUser;
import com.froad.client.user.User;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

	/**
	 * 类描述：action基类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: Oct 11, 2012 12:15:41 AM 
	 */
public abstract class BaseActionSupport extends ActionSupport implements IUser{
	
	protected Logger log = Logger.getLogger(getClass());
	
	private static final long serialVersionUID = 9006932684578500673L;
	public static final String VIEW = "view";
	public static final String LIST = "list";
	public static final String STATUS = "status";
	public static final String WARN = "warn";
	public static final String SUCCESS = "success";
	
	
	@Override
	public String execute() throws Exception {
		String str=null;
		try{
			
			str=super.execute();
		}
		catch(Throwable e){
			log.error("", e);
		}
		return str;
	}

	public static final String ERROR = "error";
	public static final String MESSAGE = "message";
	public static final String CONTENT = "content";
	public static final String SUCCESS_DIALOG = "success_dialog";
	public static final String ERROR_DIALOG = "error_dialog";

	protected String id;
	protected String[] ids;
	protected String redirectionUrl;// 操作提示后的跳转URL,为null则返回前一页
	protected String menu_flag = "home";//商户菜单标记
	
	//登录用户的常用信息
    protected User loginUser;
    
	public User getLoginUser() {
		return loginUser;
	}

	public void setLoginUser(User loginUser) {
		this.loginUser = loginUser;
	}

	// 获取Application
	public ServletContext getApplication() {
		return ServletActionContext.getServletContext();
	}
	
	// 获取Request
	public HttpServletRequest getRequest() {
		return ServletActionContext.getRequest();
	}

	// 获取Response
	public HttpServletResponse getResponse() {
		return ServletActionContext.getResponse();
	}

	// 获取Attribute
	public Object getAttribute(String name) {
		return ServletActionContext.getRequest().getAttribute(name);
	}

	// 设置Attribute
	public void setAttribute(String name, Object value) {
		ServletActionContext.getRequest().setAttribute(name, value);
	}

	// 获取Parameter
	public String getParameter(String name) {
		return getRequest().getParameter(name);
	}

	// 获取Parameter数组
	public String[] getParameterValues(String name) {
		return getRequest().getParameterValues(name);
	}
	
	// 获取Session
	public Object getSession(String name) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session.get(name);
	}

	// 获取Session
	public Map<String, Object> getSession() {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		return session;
	}

	// 设置Session
	public void setSession(String name, Object value) {
		ActionContext actionContext = ActionContext.getContext();
		Map<String, Object> session = actionContext.getSession();
		session.put(name, value);
	}

	// AJAX输出，返回null
	public String ajax(String content, String type) {
		try {
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(type + ";charset=UTF-8");
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
			response.getWriter().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// AJAX输出文本，返回null
	public String ajaxText(String text) {
		return ajax(text, "text/plain");
	}

	// AJAX输出HTML，返回null
	public String ajaxHtml(String html) {
		return ajax(html, "text/html");
	}

	// AJAX输出XML，返回null
	public String ajaxXml(String xml) {
		return ajax(xml, "text/xml");
	}

	// 根据字符串输出JSON，返回null
	public String ajaxJson(String jsonString) {
		return ajax(jsonString, "text/html");
	}
	
	// 根据Map输出JSON，返回null
	public String ajaxJson(Map<String, String> jsonMap) {
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON错误消息，返回null
	public String ajaxJsonErrorMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, ERROR);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON警告消息，返回null
	public String ajaxJsonWarnMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, WARN);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	// 输出JSON成功消息，返回null
	public String ajaxJsonSuccessMessage(String message) {
		Map<String, String> jsonMap = new HashMap<String, String>();
		jsonMap.put(STATUS, SUCCESS);
		jsonMap.put(MESSAGE, message);
		JSONObject jsonObject = JSONObject.fromObject(jsonMap);
		return ajax(jsonObject.toString(), "text/html");
	}
	
	/**
	  * 方法描述：获取当前用户信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: meicy meiwenxiang@f-road.com.cn
	  * @time: Oct 10, 2012 10:33:32 AM
	  */
//	public LoginManager getUser(HttpServletRequest request){
//		LoginManager loginManager=new LoginManager();
//		loginManager=(LoginManager) request.getSession().getAttribute(request.getSession().getId());
//		return loginManager;
//	}
	
	
	
	
	

	
	
	
	
	
	


	/**
	  * 方法描述：获取Session 里面的值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-3-14 上午9:42:19
	  */
	public String getSessionValue(String name){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session =  request.getSession(false);
		String value = null;
		if(session != null)
			value = (String)session.getAttribute(name);
		return value;
	}
	
	
	/**
	  * 方法描述：保存入Session
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-8-11 下午11:03:50
	  */
	public boolean saveToSession(String key,String value){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session =  request.getSession(false);
		boolean is = false;
		if(session != null){
			is = true;
			session.setAttribute(key,value);
		}
			
		return is;
	}
	
	
	

	
	/**
	  * 方法描述：获取购买者
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: Sep 26, 2011 1:34:34 PM
	  */
	public String getUserName(){
		HttpServletRequest request = ServletActionContext.getRequest();
		HttpSession session = request.getSession(true);
		String name= null;
//		String loginType = (String)session.getAttribute(MallCommand.CUSTOMER_SERVICE_BUY_LOGINTYPE);
//		if(loginType != null && MallCommand.AUTO_LOGIN_LOGINTYPE_USER.equals(loginType)){
//			name = (String)session.getAttribute(MallCommand.CUSTOMER_SERVICE_BUY_CNAME);
//		} else {
//			name = getLoginUserName();
//		}
	    return name;
	}
	
	
	/**
	  * 方法描述：登陆用户名
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2012-8-1 下午3:42:19
	  */
	public String getLoginUserName(){
		User user = null;
	    SecurityContext sectx = SecurityContextHolder.getContext();
	    if (!sectx.getAuthentication().getPrincipal().equals("anonymousUser")) {
	      user = (User)sectx.getAuthentication().getPrincipal();
	    }
	    return  user==null?"":user.getUsername();
	}
	
	/**
	  * 方法描述：获取手机号
	  * @param: 
	  * @return: mobilephone
	  * @time: Sep 26, 2011 1:34:34 PM
	  */
	public String getMobilePhone(){
//		User user = null;
//	    SecurityContext sectx = SecurityContextHolder.getContext();
//	    if (!sectx.getAuthentication().getPrincipal().equals("anonymousUser")) {
//	      user = (User)sectx.getAuthentication().getPrincipal();
//	    }
//	    return user==null?"":user.getUsername();
		return "";
	}
	
	/**
	  * 方法描述：抛出公共异常
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 勾君伟 goujunwei@f-road.com.cn
	  * @time: Apr 22, 2011 4:33:00 PM
	  */
	public void handleException(Exception e) throws AppException{
//		throw new AppException(e.getMessage());
		e.printStackTrace();
	}

	
	
	/**
	  * 方法描述：运用post方法给js传值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-5-6 上午11:50:51
	  */
	public void sendMsg(String content){       
        try {
			HttpServletResponse response = ServletActionContext.getResponse();       
			response.setCharacterEncoding("UTF-8");   
			response.setHeader("Pragma", "No-cache");
			response.setHeader("Cache-Control", "no-cache");
			response.setDateHeader("Expires", 0);
			response.getWriter().write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}       
	} 
	
	
	/**
	  * 方法描述：针对使用urlrewrite后参数a变为a,a的情况
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 何庆均 heqingjun@f-road.com.cn
	  * @update:
	  * @time: 2011-5-25 上午10:50:09
	  */
	public String getTrueValue(String value){
	    
	    if("".equals(value) || value == null){
		value =null;
	    } else {
		value = value.split(",")[0];
	    }
	    return value;
	}
	
	public String urlEncode(String name,String charset) {
		try {
			name = URLEncoder.encode(name, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;

	}
	
    public String urlDecode(String name,String charset) {
    	try {
			name = URLDecoder.decode(name, charset);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return name;
    }
    
    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}
	
	public String getRedirectionUrl() {
		return redirectionUrl;
	}

	public void setRedirectionUrl(String redirectionUrl) {
		this.redirectionUrl = redirectionUrl;
	}
	
//	public static void main(String[] args){
//		//%25E9%25A6%2599%25E6%25B4%25B2
//		BaseActionSupport test = new BaseActionSupport();
//		String tagValue = test.urlDecode(test.urlDecode("%25E9%25A6%2599%25E6%25B4%25B2","utf-8"), "utf-8");
//		System.out.println("tagValue = "+tagValue);
//	}
	
	public String getMenu_flag() {
		return menu_flag;
	}

	public void setMenu_flag(String menuFlag) {
		menu_flag = menuFlag;
	}
}
