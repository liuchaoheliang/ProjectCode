package com.froad.fft.api.service.impl;

import java.io.InputStream;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;

import com.froad.fft.api.service.FileExportService;
import com.froad.fft.bean.file.FileInfo;
import com.froad.fft.bean.file.FileInfoDto;
import com.froad.fft.bean.file.FileResult;
import com.froad.fft.bean.file.FileResultDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.service.FileService;

public class FileExportServiceImpl implements FileExportService {

	@Resource(name = "fileServiceImpl")
	private FileService fileService;

	@Override
	public FileResultDto isValid(ClientAccessType clientAccessType,ClientVersion clientVersion,FileInfoDto fileInfoDto) {
		
		FileResultDto fileResultDto=new FileResultDto();
		FileResult fileResult=fileService.isValid(loadBy(fileInfoDto));
		BeanUtils.copyProperties(fileResult, fileResultDto);
		
		return fileResultDto;
	}

	@Override
	public FileResultDto upload(ClientAccessType clientAccessType,ClientVersion clientVersion,FileInfoDto fileInfoDto,InputStream inputStream) {
		FileResultDto fileResultDto=new FileResultDto();
		FileResult fileResult=fileService.upload(loadBy(fileInfoDto),inputStream);
		BeanUtils.copyProperties(fileResult, fileResultDto);
		
		return fileResultDto;
	}
	
	public FileInfo loadBy(FileInfoDto fileInfoDto){
		
		FileInfo fileInfo=new FileInfo();
		fileInfo.setName(fileInfoDto.getName());
		fileInfo.setSize(fileInfoDto.getSize());
		fileInfo.setType(com.froad.fft.bean.file.FileInfo.FileType.valueOf(fileInfoDto.getType().toString()));
		
		return fileInfo;
	}
}
