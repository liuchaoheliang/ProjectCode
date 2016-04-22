package com.froad.fft.persistent.entity;

/**
 * 日志
 * @author FQ
 *
 */
public class Log extends BaseEntity{
	
	private String operation;//操作 
	private String operator;//操作员
	private String content;//内容 
	private String parameter;//请求参数
	private String ip;//IP

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	
}
