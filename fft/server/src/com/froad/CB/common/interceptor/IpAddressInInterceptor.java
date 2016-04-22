package com.froad.CB.common.interceptor;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;

import org.apache.cxf.interceptor.Fault;
import org.apache.cxf.message.Message;
import org.apache.cxf.phase.AbstractPhaseInterceptor;
import org.apache.cxf.phase.Phase;
import org.apache.cxf.transport.http.AbstractHTTPDestination;

import com.froad.CB.common.Command;

import common.Logger;



	/**
	 * 类描述：IP地址拦截器
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: Oct 9, 2012 9:51:05 AM 
	 */
public class IpAddressInInterceptor extends AbstractPhaseInterceptor<Message> {
	private static Logger logger = Logger.getLogger(IpAddressInInterceptor.class);
	public IpAddressInInterceptor() {
		super(Phase.RECEIVE);
	}

	public void handleMessage(Message message) throws Fault {
		HttpServletRequest request = (HttpServletRequest) message.get(AbstractHTTPDestination.HTTP_REQUEST);

		Properties pro = new Properties();
		InputStream inputStream = Command.class.getClassLoader().getResourceAsStream("config/IpAddressConfig.properties");
		String []allowed=null;
		try {
			pro.load(inputStream);
			inputStream.close();
			String allowedIP=pro.getProperty("allowedIP");
			allowed=allowedIP.split("[|]");
			logger.info("allowedIP:"+allowedIP+"\n");
		}catch (FileNotFoundException e) {
			logger.error("IP拦截器异常", e);
	  	} catch (IOException e) {
	  		logger.error("IP拦截器异常", e);
	  	}
		if(request!=null)
		{
			//取客户端IP地址
		    String ipAddress = request.getRemoteAddr(); 
	        logger.info("判断来访地址:"+ipAddress);
	        int j=0;
	        for(int i=0;i<allowed.length;i++){
	        	if (allowed[i].equals(ipAddress)) {
					j++;
				}
	        }
	        if(j>0){
	        	logger.info("允许IP:"+ipAddress+"访问");
	        }
	        else{
	        	logger.info("不允许IP:"+ipAddress+"访问");
	        	throw new Fault(new IllegalAccessException("拒绝IP:"+ipAddress+"访问.商户IP验证错误"));
	        }
	   }else{
		
	   }
      
	}
}
