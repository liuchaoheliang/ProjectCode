package com.froad.fft.bean;



/**
 * 消息
 * @author FQ
 *
 */
public class Message {
	
	/**
	 * 类型
	 */
	public enum Type {

		// 成功 
		success,

		//警告 
		warn,

		//错误 
		error
	}
	
	public Message() {}
	
	public Message(Type type, String content) {
		this.type = type;
		this.content = content;
	}

	private Type type;//类型
	private String content;//内容
	
	
	/**
	 * 返回成功消息
	 * 
	 * @param content 内容
	 * @return 成功消息
	 */
	public static Message success(String content) {
		return new Message(Type.success, content);
	}

	/**
	 * 返回警告消息
	 * 
	 * @param content 内容
	 * @return 警告消息
	 */
	public static Message warn(String content) {
		return new Message(Type.warn, content);
	}

	/**
	 * 返回错误消息
	 * 
	 * @param content 内容
	 * @return 错误消息
	 */
	public static Message error(String content) {
		return new Message(Type.error, content);
	}

	
	
	public Type getType() {
		return type;
	}
	public void setType(Type type) {
		this.type = type;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	

}
