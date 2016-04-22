package com.froad.fft.bean.file;

import java.io.InputStream;
import java.io.Serializable;

/**
 * 文件
 * 
 * @author FQ
 * 
 */
public class FileInfoDto implements Serializable{

	/**
	 * 文件类型
	 */
	public enum FileType {

		/** 图片 */
		image,

		/** 文件 */
		file
	}

	private String name;//名称
	private Long size;//大小
	private FileType type;//类型
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
	public FileType getType() {
		return type;
	}
	public void setType(FileType type) {
		this.type = type;
	}

}
