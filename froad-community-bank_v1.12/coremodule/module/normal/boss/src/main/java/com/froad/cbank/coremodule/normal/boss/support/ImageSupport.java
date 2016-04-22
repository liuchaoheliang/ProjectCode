package com.froad.cbank.coremodule.normal.boss.support;

import java.io.IOException;
import java.util.ArrayList;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.utils.ImgUtil;

/**
 * 图片上传
 */
@Service
public class ImageSupport {
	
	/**
	 * 图片上传 imgUpload
	 * 
	 * @param reqMap
	 * @param
	 * @throws Exception
	 * @throws IOException
	 */
	public ArrayList<FileVo> imgUpload(MultipartFile[] files) throws BossException {
		ArrayList<FileVo> al = new ArrayList<FileVo>();
		int count=0;
		for(MultipartFile f : files){
			count++;
			FileVo img=ImgUtil.zipAndUpload(f);
			img.setDefault(count==1?true:false);
			al.add(img);
		}
		return al;
	}
	
}
