package com.froad.cbank.coremodule.module.normal.merchant.utils;

import java.io.File;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.froad.cbank.coremodule.framework.common.util.image.ImgZipUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Image_Info_Res;

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
	 */
	public static Image_Info_Res zipAndUpload(MultipartFile f) throws MerchantException{
		try {
			String tmpdir = Constants.getImgLocalUrl();
			
			String fileName = f.getOriginalFilename();
			
			String det = fileName.substring(fileName.lastIndexOf("."));
			
			String uuid = UUID.randomUUID().toString();
			
			String normal = uuid + det;
			
			String large = uuid + "-large" + det, medium = uuid + "-medium"+ det, thumbnail = uuid + "-thumbnail"+ det;
			
			// 原图
			TargetObjectFormat.saveFile(normal, f);
			ImgZipUtil imgCom = new ImgZipUtil(tmpdir+"/"+normal);
			// 大图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("merchant.largeW")),Integer.parseInt(Constants.get("merchant.largeH")),tmpdir+"/"+large);
			// 中图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("merchant.mediumW")),Integer.parseInt(Constants.get("merchant.mediumH")),tmpdir+"/"+medium);
			// 小图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("merchant.thumbnailW")), Integer.parseInt(Constants.get("merchant.thumbnailH")),tmpdir+"/"+thumbnail);
			
			String[] localFilePath = new String[]{
					tmpdir+"/"+normal,
					tmpdir+"/"+large,
					tmpdir+"/"+medium,
					tmpdir+"/"+thumbnail
			};
			String[] scpFileName = new String[]{
				normal,large,medium,thumbnail
			};
			
			ScpUtil.uploadFile(localFilePath, scpFileName, Constants.getScpImgUploadPath(), Constants.SCPCONFIG);
			
			String url = Constants.getImgRemoteUrl();
			
			Image_Info_Res po = new Image_Info_Res();
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
			throw new MerchantException(EnumTypes.img_upload_fail);
		}
	}
}
