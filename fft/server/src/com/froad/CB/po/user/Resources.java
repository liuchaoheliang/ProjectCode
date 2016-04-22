package com.froad.CB.po.user;

import java.io.Serializable;


	/**
	 * 类描述：用户资源类
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:49:28 PM 
	 */
public class Resources implements Serializable {

	private static final long serialVersionUID = -7823148563132964768L;
	private String resources_id;
	private String resources_name;
	private String p_resources_id;
	private String p_resources_name;
	private String resources_value;

	public String getResources_id() {
		return resources_id;
	}

	public String getResources_value() {
		return resources_value;
	}

	public void setResources_value(String resources_value) {
		this.resources_value = resources_value;
	}

	public void setResources_id(String resources_id) {
		this.resources_id = resources_id;
	}

	public String getResources_name() {
		return resources_name;
	}

	public void setResources_name(String resources_name) {
		this.resources_name = resources_name;
	}

	public String getP_resources_id() {
		return p_resources_id;
	}

	public void setP_resources_id(String p_resources_id) {
		this.p_resources_id = p_resources_id;
	}

	public String getP_resources_name() {
		return p_resources_name;
	}

	public void setP_resources_name(String p_resources_name) {
		this.p_resources_name = p_resources_name;
	}

}
