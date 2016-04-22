package com.froad.cbank.coremodule.framework.web.basic.controller;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.web.basic.common.Constants;

/** 
 * Spring Controller 基础类
 * @ClassName: BaseSpringController 
 * @Description: TODO(这里用一句话描述这个类的作用) 
 * @Create Author: hjz
 * @Create Date: 2015-3-18 下午6:25:33 
 */ 
public abstract class BasicSpringController {
	
	/** 
	 * 返回值
	 * @ClassName: Results 
	 * @Description: TODO(这里用一句话描述这个类的作用) 
	 * @Create Author: hjz
	 * @Create Date: 2015年4月8日 下午5:39:07 
	 */ 
	public static class Results {
		/**
		 * 状态码
		 * @Title code
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:31
		 * 含义 TODO
		 */
		public static final String code = Constants.Results.code;
		/**
		 * 返回信息
		 * @Title msg
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:37
		 * 含义 TODO
		 */
		public static final String msg = Constants.Results.msg;
		
		/**
		 * 返回数据
		 * @Title result
		 * @type Results
		 * @Create Author: hjz
		 * @date 2015年4月3日 下午2:51:47
		 * 含义 TODO
		 */
		public static final String result = Constants.Results.result;
	}
	
	/**
	  * 方法描述：将HttpServletRequest请求所有参数转换成jsonObj
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月26日 下午4:22:54
	  */
//	public JSONObject toJsonObj(HttpServletRequest req){
//		Map<String, Object> dataReq = new HashMap<String, Object>();
//		Enumeration<String> paramNames=req.getParameterNames();
//		while(paramNames.hasMoreElements()){
//			 String paramName = (String) paramNames.nextElement();
//			 String[] paramValues = req.getParameterValues(paramName);
//			 if(paramValues==null || paramValues.length == 0){
//				 dataReq.put(paramName, null);
//			 }else if(paramValues.length == 1){
//				 dataReq.put(paramName, paramValues[0]);
//			 }else{
//				 dataReq.put(paramName, paramValues);
//			 }
//		}
//		return JSON.parseObject(JSON.toJSONString(dataReq));
//	}
	
	
	
	
	/**
	  * 方法描述：将HttpServletRequest请求所有参数转换成Map
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月26日 下午4:44:20
	  */
	public Map<String , Object> toMap(HttpServletRequest req){  
		Map<String, Object> dataReq = new HashMap<String, Object>();
		Enumeration<String> paramNames=req.getParameterNames();
		while(paramNames.hasMoreElements()){
			 String paramName = (String) paramNames.nextElement();
			 String[] paramValues = req.getParameterValues(paramName);
			 if(paramValues==null || paramValues.length == 0){
				 dataReq.put(paramName, null);
			 }else if(paramValues.length == 1){
				 dataReq.put(paramName, paramValues[0]);
			 }else{
				 dataReq.put(paramName, paramValues);
			 }
		}
		return dataReq;
	}  
	
	
	/**
	 * 
	 * <p>功能简述：—— 功能简述</p> 
	 * <p>使用说明：—— 将Get请求参数封装到对象中</p> 
	 * <p>创建时间：2015-5-2下午4:47:39</p>
	 * <p>作者: 高峰</p>
	 * @param req
	 * @param type
	 * @return
	 */
	public <T> T convertMap(HttpServletRequest req, Class<T> type) {
		
		T obj = JSON.parseObject(JSON.toJSONString(toMap(req)), type);
		
		return obj;
	}
	
	 
	/**
	  * 方法描述：获取客户端标识（属性名称 ： clientId）
	  * @param:  HttpServletRequest
	  * @return:  
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月23日 下午3:19:57
	  */
	public String getClient(HttpServletRequest req){
		String client="";
		client =(String) req.getAttribute("clientId");
		return client ;
	}
	
	/**
	 * 获取当前登录IP
	 * @param request
	 * @return
	 */
	public static String getIpAddr(HttpServletRequest request) {      
			String ip = request.getHeader("X-Forwarded-For");
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getHeader("Proxy-Client-IP");
		   }
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getHeader("WL-Proxy-Client-IP");
		   }
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getHeader("HTTP_CLIENT_IP");
		   }
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		       ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		   }
		   if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
		      ip = request.getRemoteAddr();
		   }
		   return ip;     
    }
	
	
	/**
	 * 
	 * @Description: 成功处理返回
	 * @Create Author: liaolibiao@f-road.com.cn
	 * @Create Date: 2015年9月7日 下午5:53:27
	 */
	public void respSuccess(ModelMap model,String msg){
		model.put(Results.code, "0000");
		model.put(Results.msg, msg);
		return;
	}
	
	/**
	 * 
	 * @Description: 错误处理返回
	 * @Create Author: liaolibiao@f-road.com.cn
	 * @Create Date: 2015年9月7日 上午11:58:09
	 * @param model
	 * @param msg
	 */
	public void respError(ModelMap model,String msg){
		model.clear();
		model.put(Results.code, "9999");
		model.put(Results.msg, msg);
		return;
	}
	
	/**
	 * 
	 * @Description: 错误处理返回
	 * @Create Author: liaolibiao@f-road.com.cn
	 * @Create Date: 2015年9月7日 下午5:49:04
	 * @param model
	 * @param msg
	 */
	public void respError(ModelMap model,String code, String msg){
		model.clear();
		model.put(Results.code, code);
		model.put(Results.msg, msg);
		return;
	}
}
