package com.froad.fft.service;

import java.io.InputStream;

import com.froad.fft.bean.file.FileInfo;
import com.froad.fft.bean.file.FileResult;

public interface FileService {
	
	/**
	 * 文件验证
	 * @param fileInfo
	 * @return
	 */
	public FileResult isValid(FileInfo fileInfo);
	
	/**
	 * 文件上传
	 * @param fileInfo
	 * @return
	 */
	public FileResult upload(FileInfo fileInfo,InputStream inputStream);
}
