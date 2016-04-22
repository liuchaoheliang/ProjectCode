package com.froad.fft.bean.file;

import java.io.Serializable;

/**
 * 文件操作结果
 * @author FQ
 *
 */
public class FileResultDto implements Serializable{
	
	private Boolean result;//结果
	private String path;//路径
	private String describe;//描述
	
	public Boolean getResult() {
		return result;
	}
	public void setResult(Boolean result) {
		this.result = result;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	
}
