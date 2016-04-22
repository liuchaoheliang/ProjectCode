package com.froad.fft.thirdparty.response.openapi;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.froad.fft.thirdparty.dto.response.openapi.NoticeFroadApi;
import com.froad.fft.thirdparty.util.HttpReqBodyToStrUtil;
import com.froad.fft.util.XmlBeanUtil;


	/**
	 * 类描述：接收代收通知
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: 2014年4月6日 下午1:18:12 
	 */
public class AgencyCollectServlet extends HttpServlet{

	private static final long serialVersionUID = 1L;
	
	private static Logger logger = Logger.getLogger(AgencyCollectServlet.class);
	

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {	
		logger.info("============接收到代收通知===========");
		String noticeXML = HttpReqBodyToStrUtil.toXMLStr(req);			
		NoticeFroadApi noticeFroadApi = null;
		try {
			Map<String,Class> xmlToBeanMap = new HashMap<String,Class>();
			xmlToBeanMap.put("noticeFroadApi", NoticeFroadApi.class);
			noticeFroadApi = (NoticeFroadApi) XmlBeanUtil.xmlToBean(xmlToBeanMap, noticeXML);		
		} catch (Exception e) {
			logger.error("解析通知XML时异常", e);
		}
		resp.getWriter().write("success");
	}
}
