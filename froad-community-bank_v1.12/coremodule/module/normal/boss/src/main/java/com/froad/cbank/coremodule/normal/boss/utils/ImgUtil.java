package com.froad.cbank.coremodule.normal.boss.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

import com.froad.cbank.coremodule.framework.common.util.image.ImgZipUtil;
import com.froad.cbank.coremodule.framework.common.util.scp.ScpUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;

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
	public static FileVo zipAndUpload(MultipartFile f) throws BossException{
		try {
			
			String fileName = f.getOriginalFilename();
			
			String det = fileName.substring(fileName.lastIndexOf("."));
			
			String uuid = UUID.randomUUID().toString();
			
			String normal = uuid + det;
			
			String large = uuid + "-large" + det, medium = uuid + "-medium"+ det, thumbnail = uuid + "-thumbnail"+ det;
			
			// 原图
			saveFile(normal, f);
			
			LogCvt.info("filesize:"+f.getSize());
			//小于150k不压缩
			if(f.getSize() < 150*1024){
				String[] localFilePath = new String[]{
					Constants.getImgLocalUrl()+"/"+normal,
				};
				String[] scpFileName = new String[]{
					normal
				};
				ScpUtil.uploadFile(localFilePath, scpFileName, Constants.getScpImgUploadPath(), Constants.SCPCONFIG);
					
				String url = Constants.getImgRemoteUrl();
				FileVo po = new FileVo();
				po.setSource(url + normal);
				po.setLarge(url + normal);
				po.setMedium(url + normal);
				po.setThumbnail(url + normal);
				//clear tmp file
				for(String l : localFilePath){
					File tmpfile = new File(l);
					if(tmpfile.exists()){
						tmpfile.delete();
					}
				}
				return po;
			}
			
			ImgZipUtil imgCom = new ImgZipUtil(Constants.getImgLocalUrl()+"/"+normal);
			// 大图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("boss.largeW")),Integer.parseInt(Constants.get("boss.largeH")),Constants.getImgLocalUrl()+"/"+large);
			// 中图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("boss.mediumW")),Integer.parseInt(Constants.get("boss.mediumH")),Constants.getImgLocalUrl()+"/"+medium);
			// 小图片
			imgCom.resizeFix(Integer.parseInt(Constants.get("boss.thumbnailW")), Integer.parseInt(Constants.get("boss.thumbnailH")),Constants.getImgLocalUrl()+"/"+thumbnail);
			
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
			throw new BossException(ErrorEnums.img_upload_fail.getMsg());
		}
	}
	
	
	public static void saveFile(String newFileName, MultipartFile filedata) throws BossException {
		try {
			String saveFilePath = Constants.getImgLocalUrl();
			/* 构建文件目录 */
			File fileDir = new File(saveFilePath);
			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}
			FileOutputStream out = new FileOutputStream(saveFilePath + "/" + newFileName);
			out.write(filedata.getBytes());
			out.flush();
			out.close();
		} catch (Exception e) {
			throw new BossException("99991", "图片保存失败");
		}

	}
	
	/**
	 * 图片检查
	 * @tilte checkImage
	 * @author zxl
	 * @date 2015年12月8日 上午10:13:28
	 */
	public static void checkImage(MultipartFile img) throws BossException{
		String[] type = {"jpg","jpeg","bmp","png"};
		if(img != null && img.getOriginalFilename().indexOf(".")>0){
			String det = img.getOriginalFilename().substring(img.getOriginalFilename().lastIndexOf(".")+1).toLowerCase();
			for(String t : type){
				if(t.equals(det)){
					return;
				}
			}
			throw new BossException("只允许上传JPG,JPEG,PNG,BMP格式图片!");
		}else{
			throw new BossException("图片格式不正确!");
		}
	}
}
