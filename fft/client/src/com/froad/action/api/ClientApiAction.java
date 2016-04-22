package com.froad.action.api;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletInputStream;

import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONObject;

import com.froad.action.api.command.ActionMap;
import com.froad.action.api.command.FroadAPICommand;
import com.froad.util.MD5;

/** 
 * @author FQ 
 * @date 2013-2-7 上午09:32:03
 * @version 1.0
 * 客户端基类
 */
public class ClientApiAction extends BaseApiAction {
	private static Logger logger = Logger.getLogger(BaseApiAction.class);
	
	private String reno;//操作结果码
	private String remsg;//响应信息
	private Map<String,Object> rebody=new HashMap<String,Object>();//主体
	private String recount;//返回请求标识号（原样返回）
	private String actionName;//跳转action名字
	
	@Override
	public String execute() throws Exception {
		
		String date= initData(getRequest().getInputStream(),getRequest().getContentLength());
		
		
		logger.info("===============手机客户端接入===================");
		logger.info("请求body："+date);
		JSONObject object = new JSONObject(date);
		
		body = object.getString("body");
		no = object.getString("no");
		count = object.getString("count");
		mac = object.getString("mac");
		
		if(!mac_check()){
			reno="0002";
		    logger.info("mac 校验失败!");
			return SUCCESS;
		}
		actionName = ActionMap.actionNameMap.get(no);
		
		if(actionName == null){
			reno="9999";
		    logger.info("没有该方法的存在!");
			return SUCCESS;
		}
//		if("0305".equals(no))
//			body=URLEncoder.encode(body, FroadAPICommand.Charset);
		return "redirec";
	}
	
	public String initData(ServletInputStream sis,int size) throws IOException{
		String xmlData = null;
		
		// 用于缓存每次读取的数据
		byte[] buffer = new byte[size];
		// 用于存放结果的数组
		byte[] xmldataByte = new byte[size];
		int count = 0;
		int rbyte = 0;
		// 循环读取
		while (count < size) {
			// 每次实际读取长度存于rbyte中
			rbyte = sis.read(buffer);
			for (int i = 0; i < rbyte; i++) {
				xmldataByte[count + i] = buffer[i];
			}
			count += rbyte;
		}
		sis.close();
		xmlData = new String(xmldataByte, "UTF-8");
		return xmlData;
	}
	
	public boolean mac_check(){
		MD5 md5= new MD5();
		String keyStr = md5.getMD5ofStr(body + FroadAPICommand.KEY);
		logger.info("传入mac="+mac+"  keyStr="+keyStr);
		if(!keyStr.equalsIgnoreCase(mac)){
			 return false;
	 	}
		return true;
	}

	public String getReno() {
		return reno;
	}

	public void setReno(String reno) {
		this.reno = reno;
	}

	public String getRemsg() {
		return FroadAPICommand.getValidateMsg(reno);
	}

	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}

	public Map<String, Object> getRebody() {
		return rebody;
	}

	public void setRebody(Map<String, Object> rebody) {
		this.rebody = rebody;
	}

	public String getRecount() {
		return recount;
	}

	public void setRecount(String recount) {
		this.recount = recount;
	}

	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	
	
	
}
