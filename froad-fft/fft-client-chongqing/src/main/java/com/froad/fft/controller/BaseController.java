package com.froad.fft.controller;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import com.csvreader.CsvWriter;
import com.froad.fft.bean.Message;
import com.froad.fft.common.SessionKey;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.extend.DateEditor;
import com.froad.fft.extend.HtmlCleanEditor;
import com.froad.fft.support.base.impl.UserEngineSupportImpl;
import com.froad.fft.util.NullValueCheckUtil;
import com.froad.fft.util.SpringUtil;

/**
 * controller 基类
 * 
 * 
 * 该Controller含有常用的方法 当子类需要类似方法应直接调用无需重写
 * 
 * 
 * @author FQ
 *
 */
public class BaseController {
	
	//日志
	protected transient final Logger logger = Logger.getLogger(getClass());

	//成功消息 
	protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");
	
	//错误消息 
	protected static final Message ERROR_MESSAGE = Message.error("操作错误");

	//----------------------------常用的Controller处理返回值
	/**
	 * 返回该值意味着系统在校验数据时发现异常，将定向到加载失败的友好页面<br />Model.put("illMsg","描述")  将在加载失败页面显示描述信息
	 */
	protected String ILLEGALITY = "/common/illegality";
	
	/**
	 * 数据绑定
	 * 
	 * @param binder
	 *            WebDataBinder
	 */
	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		
		// 忽略字段绑定异常   
        // binder.setIgnoreInvalidFields(true);  
		
		binder.registerCustomEditor(String.class, new HtmlCleanEditor(true, true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>创建session的专用方法</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月25日 下午10:37:25 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	protected void createSessionObject(HttpServletRequest req,SessionKey sessionKey,Object object){
		HttpSession session = req.getSession(false);
		if(session == null){
			logger.info("当前request中session不存在，创建新的session");
			session = req.getSession(true);
		}
		session.setAttribute(sessionKey.key(), object);
		//logger.info("成功创建session : " + JSONObject.toJSONString(object));
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>获取session key对应的属性值</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月25日 下午10:55:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	protected Object getSessionValue(HttpServletRequest req,SessionKey sessionKey){
		HttpSession session = req.getSession(false);
		if(session == null){
			logger.info("当前request中session不存在，无法获取相关数据");
			return null;
		}
		return session.getAttribute(sessionKey.key());
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>移除单个session的专用方法</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月25日 下午10:45:45 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	protected void removeSessionByKey(HttpServletRequest req,SessionKey sessionKey){
		HttpSession session = req.getSession(false);
		if(session == null){
			logger.info("当前request中session不存在，创建新的session");
			session = req.getSession(true);
		}
		session.removeAttribute(sessionKey.key());
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>销毁session</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月25日 下午10:07:45 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	protected void  removeAllSession(HttpServletRequest req) {
		
		HttpSession session = req.getSession(false);
		if(session == null){
			logger.info("当前request中session不存在无需销毁session");
			return;
		}
		@SuppressWarnings("unchecked")
		Enumeration<String> sessionKeys = session.getAttributeNames(); //获取所有的session Key
		while (sessionKeys.hasMoreElements()) {
			session.removeAttribute(sessionKeys.nextElement());//移除所有session Key 对应的Object
		}
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>更新用户积分信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月3日 下午4:46:58 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	protected boolean updateUserAllInfo(HttpServletRequest req) {
		logger.info("更新用户积分信息开始");
		Object object = getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		if(object == null){
			logger.info("更新用户积分时未获取到用户当前信息");
			return false;
		}
		UserEngineDto userEngineDto = (UserEngineDto)object;
		String loginID = userEngineDto.getLoginID();
		try {
			UserEngineSupportImpl userEngineSupportImpl = (UserEngineSupportImpl) SpringUtil.getBean("userEngineSupportImpl");
			userEngineDto = userEngineSupportImpl.updateUserPoints(loginID);
			createSessionObject(req, SessionKey.LOGIN_IDENTIFICATION, userEngineDto);
			return true;
		} catch (Exception e) {
			logger.error("更新用户信息发生系统异常", e);
			return false;
		}
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>如果某个请求需要防止重复提交表单则引用该方法</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月11日 上午12:58:44 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	protected void createTokenKey(HttpServletRequest req){
		UUID tokenKey = UUID.randomUUID();
		logger.info("生成tokenKey " + tokenKey.toString());
		createSessionObject(req, SessionKey.STSTEM_UUID_TOKEN_KEY, tokenKey.toString());
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过帐号或者手机号获取用户信息（将在账户系统时时查询  不存在返回null）</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月15日 上午10:47:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	protected UserEngineDto getUserEngineByLoginID(String loginID){
		UserEngineSupportImpl userEngineSupportImpl = (UserEngineSupportImpl) SpringUtil.getBean("userEngineSupportImpl");
		UserEngineDto userEngineDto = userEngineSupportImpl.queryByLoginId(loginID);
		if(NullValueCheckUtil.isStrEmpty(userEngineDto.getLoginID())){
			return null;
		}
		return userEngineDto;
	}
	
	
	
	
	/**
	  * 方法描述：提供excel导出
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月23日 下午12:39:27
	  */
	
	public void downloadExcel(HttpServletResponse response,String fileName,HSSFWorkbook wb){
		try {
			fileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("content-type", "application/vnd.ms-excel");
			response.setHeader("content-disposition", "attachment;filename="+fileName);
			wb.write(response.getOutputStream());
		} catch (IOException e) {
			logger.error("报表导出异常", e);
		}
	}
	
	
	/**
	  * 方法描述：csv方式导出
	  * @param: List<String[]> content:每一行插入的数据，以'，'隔开
	  * 		String fileName：下载的文件名字
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月23日 下午9:43:12
	  */
	public void downloadCsv(HttpServletResponse response,List<String[]> content,String fileName){
		try {
			fileName = new String(fileName.getBytes(), "ISO8859-1");
			response.setHeader("content-type", "application/vnd.ms-excel");
			response.setHeader("content-disposition", "attachment;filename="+fileName);
			CsvWriter cw = new CsvWriter(response.getOutputStream(), ',',Charset.forName("GBK"));
			for(String[] temp:content){
				cw.writeRecord(temp);				
			}
			cw.endRecord();
			cw.close();
		} catch (IOException e) {
			logger.error("报表导出异常", e);
		}
	}
}
