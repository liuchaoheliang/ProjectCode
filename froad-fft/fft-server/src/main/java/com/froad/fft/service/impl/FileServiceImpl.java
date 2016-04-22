package com.froad.fft.service.impl;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.bean.SystemConfig;
import com.froad.fft.bean.file.FileInfo;
import com.froad.fft.bean.file.FileInfo.FileType;
import com.froad.fft.bean.file.FileResult;
import com.froad.fft.service.FileService;
import com.froad.fft.util.FtpUtil;
import com.froad.fft.util.SystemConfigUtil;

@Service("fileServiceImpl")
public class FileServiceImpl implements FileService {
	
	private static Logger logger = Logger.getLogger(FileServiceImpl.class);

	@Override
	public FileResult isValid(FileInfo fileInfo) {
		
		FileResult fileResult=new FileResult();
		
		//上传文件是否为空
//		if(inputStream == null)
//		{
//			fileResult.setResult(false);
//			fileResult.setDescribe("上传文件不能为空");
//			return fileResult;
//		}
		
		SystemConfig systemConfig=SystemConfigUtil.getSystemConfig();
		
		//上传文件大小
		if (systemConfig.getUploadMaxSize() != null && systemConfig.getUploadMaxSize() != 0 && fileInfo.getSize() > systemConfig.getUploadMaxSize() * 1024L * 1024L) {
			fileResult.setResult(false);
			fileResult.setDescribe("文件大小超出限制");
			return fileResult;
		}
		
		//允许上传的类型
		String[] uploadExtensions;
		if (fileInfo.getType() == FileType.file) {
			uploadExtensions = systemConfig.getAllowedUploadFileExtension().split(SystemConfig.EXTENSION_SEPARATOR);;
		} else {
			uploadExtensions = systemConfig.getAllowedUploadImageExtension().split(SystemConfig.EXTENSION_SEPARATOR);;
		}
		if (ArrayUtils.isNotEmpty(uploadExtensions)) {
			Boolean isExtension=FilenameUtils.isExtension(fileInfo.getName(), uploadExtensions);
			
			if(isExtension){
				fileResult.setResult(true);
				fileResult.setDescribe("文件验证通过");
			}
			else{
				fileResult.setResult(false);
				fileResult.setDescribe("文件大小类型不允许");
			}
			return fileResult;
		}
		
		fileResult.setResult(false);
		fileResult.setDescribe("文件验证失败");
		return fileResult;
	}

	@Override
	public FileResult upload(FileInfo fileInfo,InputStream inputStream) {
		
		FileResult fileResult=new FileResult();
		
		//上传文件是否为空
		if(inputStream == null)
		{
			fileResult.setResult(false);
			fileResult.setDescribe("上传文件不能为空");
			return fileResult;
		}
		
		SystemConfig systemConfig=SystemConfigUtil.getSystemConfig();
		String uploadPath;
		if (fileInfo.getType()  == FileType.file) {
			uploadPath = systemConfig.UPLOAD_FILE_DIR;
		} else {
			uploadPath = systemConfig.UPLOAD_IMAGE_DIR;
		}
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMM");
		String dateString = simpleDateFormat.format(new Date());
		String destPath =systemConfig.getFtpDirectoryPath()+ uploadPath + dateString + "/" + UUID.randomUUID() + "." + FilenameUtils.getExtension(fileInfo.getName());
		
		boolean ftpResult=FtpUtil.uploadFile(destPath, inputStream);
		
		if(ftpResult){
			fileResult.setResult(true);
			fileResult.setPath(destPath);
			fileResult.setDescribe("文件上传成功");
		}
		else{
			fileResult.setResult(false);
			fileResult.setDescribe("文件上传失败");
		}
		
		return fileResult;
	}

}
