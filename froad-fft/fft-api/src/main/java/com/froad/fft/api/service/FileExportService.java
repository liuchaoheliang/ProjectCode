package com.froad.fft.api.service;

import java.io.InputStream;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.file.FileInfoDto;
import com.froad.fft.bean.file.FileResultDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

public interface FileExportService {
	
	/**
	 * 文件验证
	 * @param fileInfo
	 * @return
	 */
	public FileResultDto isValid(ClientAccessType clientAccessType,ClientVersion clientVersion,FileInfoDto fileInfoDto)throws FroadException;

	/**
	 * 文件上传
	 * @param fileInfo
	 * @return
	 */
	public FileResultDto upload(ClientAccessType clientAccessType,ClientVersion clientVersion,FileInfoDto fileInfoDto,InputStream inputStream)throws FroadException;
}
