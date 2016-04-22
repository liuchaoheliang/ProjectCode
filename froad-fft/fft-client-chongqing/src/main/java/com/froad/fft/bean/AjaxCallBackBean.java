package com.froad.fft.bean;

public class AjaxCallBackBean {
	
	private Boolean flag;	//请求是否符合预期结果（请求成功，业务是否成功）
	
	private String msg;		//用于展示给用户的反馈信息
	
	private Object data;	//该请求携带的反馈参数
	
	public Boolean getFlag() {
		return flag;
	}

	public void setFlag(Boolean flag) { 
		this.flag = flag;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}
	
	public AjaxCallBackBean(Boolean flag, String msg, Object data) {
		this.flag = flag;
		this.msg = msg;
		this.data = data;
	}

	public AjaxCallBackBean(Boolean flag, String msg) {
		this.flag = flag;
		this.msg = msg;
	}

	public AjaxCallBackBean(Boolean flag) {
		this.flag = flag;
	}

	public AjaxCallBackBean(Object data) {
		this.data = data;
	}
	
	
}