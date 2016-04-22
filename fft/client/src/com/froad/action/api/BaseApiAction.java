package com.froad.action.api;

import org.apache.log4j.Logger;

import com.froad.action.api.command.FroadAPICommand;
import com.froad.action.api.user.UserAction;
import com.froad.baseAction.BaseActionSupport;
import com.froad.util.MD5;

/** 
 * @author FQ 
 * @date 2013-2-7 上午09:32:03
 * @version 1.0
 * 客户端基类
 */
public class BaseApiAction extends BaseActionSupport {
	private static Logger logger = Logger.getLogger(BaseApiAction.class);
	protected String mac;//对bodyMac校验
	protected String no;//接口序列号
	protected String body;//主体
	protected String count;//请求标识号
	
	
	
	public String getMac() {
		return mac;
	}
	public void setMac(String mac) {
		this.mac = mac;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getCount() {
		return count;
	}
	public void setCount(String count) {
		this.count = count;
	}
}
