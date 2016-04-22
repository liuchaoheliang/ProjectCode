package com.froad.cbank.coremodule.module.normal.bank.util;

import java.io.File;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.froad.cbank.coremodule.framework.common.util.image.ImgZipUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.exception.BankException;
import com.froad.cbank.coremodule.module.normal.bank.vo.FileVo;

/**
 * 图片处理类
 * @ClassName ImgUpload
 * @author zxl
 * @date 2015年4月1日 下午3:09:15
 */
public class ImgUtil {
	
	/**
	 * 图片压缩并上传
	 * @tilte zipAndUpload
	 * @author zxl
	 * @date 2015年4月1日 下午3:10:46
	 * @param f 
	 * @throws Exception 
	 */
	public static FileVo zipAndUpload(MultipartFile f) throws BankException{
		try {
			
			String fileName = f.getOriginalFilename();
			
			String det = fileName.substring(fileName.lastIndexOf("."));
			
			String uuid = UUID.randomUUID().toString();
			
			String normal = uuid + det;
			
			String large = uuid + "-large" + det, medium = uuid + "-medium"+ det, thumbnail = uuid + "-thumbnail"+ det;
			
			// 原图
			TargetObjectFormat.saveFile(normal, f);
			ImgZipUtil imgCom = new ImgZipUtil(Constants.getImgLocalUrl()+"/"+normal);
			// 大图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("bank.largeW")),Integer.parseInt(Constants.get("bank.largeH")),Constants.getImgLocalUrl()+"/"+large);
			// 中图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("bank.mediumW")),Integer.parseInt(Constants.get("bank.mediumH")),Constants.getImgLocalUrl()+"/"+medium);
			// 小图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("bank.thumbnailW")), Integer.parseInt(Constants.get("bank.thumbnailH")),Constants.getImgLocalUrl()+"/"+thumbnail);
			
			String[] localFilePath = new String[]{
				Constants.getImgLocalUrl()+"/"+normal,
				Constants.getImgLocalUrl()+"/"+large,
				Constants.getImgLocalUrl()+"/"+medium,
				Constants.getImgLocalUrl()+"/"+thumbnail
			};
			String[] scpFileName = new String[]{
				normal,large,medium,thumbnail
			};
			
			ScpUtil.uploadFile(localFilePath, scpFileName, Constants.getScpImgUploadPath(), Constants.SCPCONFIG);
				
			String url = Constants.getImgRemoteUrl();
			
			FileVo po = new FileVo();
			po.setSource(url + normal);
			po.setLarge(url + large);
			po.setMedium(url + medium);
			po.setThumbnail(url + thumbnail);
			
			//clear tmp file
			for(String l : localFilePath){
				File tmpfile = new File(l);
				if(tmpfile.exists()){
					tmpfile.delete();
				}
			}
			
			return po;
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
			throw new BankException(EnumTypes.img_upload_fail.getMessage());
		}
	}
}
