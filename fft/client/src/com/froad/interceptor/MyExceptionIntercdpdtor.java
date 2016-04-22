package com.froad.interceptor;

import java.util.Map;

import org.apache.log4j.Logger;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.util.ValueStack;

/** 
 * @author FQ 
 * @date 2013-6-8 下午03:45:16
 * @version 1.0
 * 
 */
public class MyExceptionIntercdpdtor extends AbstractInterceptor{

	private static final long serialVersionUID = 1008901298342362080L;  
    private static final Logger log = Logger.getLogger(MyExceptionIntercdpdtor.class);  
	
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		String actionName = invocation.getInvocationContext().getName();
		try {
			String result = invocation.invoke();
			return result;
		} catch (Exception e) {
			
			ValueStack vs =  invocation.getStack();
			vs.getContext();
			Map<String, Object> contenxt =  vs.getContext();
			log.info("=====================请求异常 actioName ：" + actionName + "   参数列表 打印开始 =================================");
			for (Map.Entry<String, Object> entry  : contenxt.entrySet()) {
				System.out.println("参数：" + entry.getKey() + " | 值：" + entry.getValue());
			}
			log.info("=====================请求异常   参数列表 打印结束 =================================");
			
			log.error(actionName, e);
			return "globalException";
		}
	}

}
