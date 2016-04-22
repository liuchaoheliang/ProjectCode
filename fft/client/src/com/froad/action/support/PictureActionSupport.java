package com.froad.action.support;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.struts2.ServletActionContext;

import com.froad.common.WatermarkPosition;
import com.froad.util.Assert;
import com.froad.util.Command;
import com.froad.util.CommonUtil;
import com.froad.util.ImageUtil;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-2-20  
 * @version 1.0
 * 图片 client service  ActionSupport
 */
public class PictureActionSupport {
	private static Logger logger = Logger.getLogger(PictureActionSupport.class);
	
//	public static final String IMAGE_FILE_EXTENSION = "jpg";// 商品图片文件名扩展名
	public static final String BIG_IMAGE_FILE_NAME_SUFFIX = "_big";// 商品图片（大）文件名后缀
	public static final String SMALL_IMAGE_FILE_NAME_SUFFIX = "_small";// 商品图片（小）文件名后缀
	public static final String THUMBNAIL_IMAGE_FILE_NAME_SUFFIX = "_thumbnail";// 商品缩略图文件名后缀
	public static final String EXTENSION_SEPARATOR = ",";// 文件扩展名分隔符
	
	///home/froadmall/tomcat6.0.33-froadmall/webapps
	public String uploadPicture(File uploadImageFile,String imgfilefileName,String id,String fileType){
		String sourceImagePath = "";
		String uuid=CommonUtil.getUUID();
		
		if (uploadImageFile == null) {
			logger.equals("图片File为空。");
			return "";
		}
		
		if (StringUtils.isEmpty(Command.ALLOWED_UPLOAD_IMAGE_EXTENSION.toLowerCase())){
			logger.equals("不允许上传图片文件!");
			return "";
		}
		String imageExtension =  StringUtils.substringAfterLast(imgfilefileName, ".").toLowerCase();
		String[] imageExtensionArray = Command.ALLOWED_UPLOAD_IMAGE_EXTENSION.split(EXTENSION_SEPARATOR);
		if (!ArrayUtils.contains(imageExtensionArray, imageExtension)) {
			logger.equals("图片格式【"+imageExtension+"】在限制范围，无法上传");
			return "";
		}
		int uploadLimit = Integer.valueOf(Command.UPLOAD_LIMIT) * 1024;
		if (uploadLimit != 0) {
			if (uploadImageFile != null && uploadImageFile.length() > uploadLimit) {
				logger.equals("图片大小【"+imageExtension+"】大于1M，无法上传");
				return "";
			}
		}
		
		//File uploadImageDir = new File(ServletActionContext.getServletContext().getRealPath(UPLOAD_IMAGE_DIR));//暂不用
		File uploadImageDir = new File(Command.JUEDUI_URL+Command.UPLOAD_IMAGE_DIR);
		//File uploadImageDir = new File("E:/worksofts/apache-tomcat-6.0.32/webapps/communityBusiness_client"+UPLOAD_IMAGE_DIR);//本地

		
		if (!uploadImageDir.exists()) {
			uploadImageDir.mkdirs();
		}

		if(Assert.empty(fileType)){
			fileType = "logo";
		}
		
		sourceImagePath = Command.UPLOAD_IMAGE_DIR + id + "/"+fileType +"/" + uuid + "." + imageExtension;
		String bigImagePath = Command.UPLOAD_IMAGE_DIR + id + "/"+fileType +"/" + uuid + BIG_IMAGE_FILE_NAME_SUFFIX + "." + imageExtension;
		String smallImagePath = Command.UPLOAD_IMAGE_DIR + id + "/"+fileType +"/" + uuid + SMALL_IMAGE_FILE_NAME_SUFFIX + "." + imageExtension;
		String thumbnailImagePath = Command.UPLOAD_IMAGE_DIR + id + "/"+fileType +"/" + uuid + THUMBNAIL_IMAGE_FILE_NAME_SUFFIX + "." + imageExtension;
		
		logger.info("sourceImagePath："+sourceImagePath);
		logger.info("bigImagePath："+bigImagePath);
		logger.info("smallImagePath："+smallImagePath);
		logger.info("thumbnailImagePath："+thumbnailImagePath);
		
		//本地测试
//		File sourceImageFile = new File(ServletActionContext.getServletContext().getRealPath(sourceImagePath));
//		File bigImageFile = new File(ServletActionContext.getServletContext().getRealPath(bigImagePath));
//		File smallImageFile = new File(ServletActionContext.getServletContext().getRealPath(smallImagePath));
//		File thumbnailImageFile = new File(ServletActionContext.getServletContext().getRealPath(thumbnailImagePath));
//		File watermarkImageFile = new File(ServletActionContext.getServletContext().getRealPath(Command.WATERMARK_IMAGE_PATH));
//		
//		File sourceImageParentFile = sourceImageFile.getParentFile();
//		File bigImageParentFile = bigImageFile.getParentFile();
//		File smallImageParentFile = smallImageFile.getParentFile();
//		File thumbnailImageParentFile = thumbnailImageFile.getParentFile();
//
//		if (!sourceImageParentFile.exists()) {
//			sourceImageParentFile.mkdirs();
//		}
//		if (!bigImageParentFile.exists()) {
//			bigImageParentFile.mkdirs();
//		}
//		if (!smallImageParentFile.exists()) {
//			smallImageParentFile.mkdirs();
//		}
//		if (!thumbnailImageParentFile.exists()) {
//			thumbnailImageParentFile.mkdirs();
//		}
		
		//正式
		File sourceImageFile = new File(Command.JUEDUI_URL+sourceImagePath);
		File bigImageFile = new File(Command.JUEDUI_URL+bigImagePath);
		File smallImageFile = new File(Command.JUEDUI_URL+smallImagePath);
		File thumbnailImageFile = new File(Command.JUEDUI_URL+thumbnailImagePath);
		File watermarkImageFile = new File(Command.JUEDUI_URL+Command.WATERMARK_IMAGE_PATH);
		
		try {
			BufferedImage srcBufferedImage = ImageIO.read(uploadImageFile);
			if (srcBufferedImage == null){
				return null;
			}
			// 将上传图片复制到原图片目录
			FileUtils.copyFile(uploadImageFile, sourceImageFile);
			// 商品图片（大）缩放、水印处理
			ImageUtil.zoomAndWatermark(srcBufferedImage, bigImageFile, Integer.parseInt(Command.BIG_IMAGE_WIDTH), Integer.parseInt(Command.BIG_IMAGE_HEIGHT), watermarkImageFile,WatermarkPosition.valueOf(Command.WATERMARK_POSITION), Integer.parseInt(Command.WATERMARK_ALPHA));
			// 商品图片（小）缩放、水印处理
			ImageUtil.zoomAndWatermark(srcBufferedImage, smallImageFile, Integer.parseInt(Command.SMALL_IMAGE_WIDTH), Integer.parseInt(Command.SMALL_IMAGE_HEIGHT), watermarkImageFile, WatermarkPosition.valueOf(Command.WATERMARK_POSITION), Integer.parseInt(Command.WATERMARK_ALPHA));
			// 商品图片缩略图处理
			ImageUtil.zoom(srcBufferedImage, thumbnailImageFile, Integer.parseInt(Command.THUMBNAIL_IMAGE_WIDTH), Integer.parseInt(Command.THUMBNAIL_IMAGE_HEIGHT));
			
		} catch (Exception e) {
			logger.error("imgUpload上传图片出错");
			e.printStackTrace();
			sourceImagePath="";
		}
		return sourceImagePath;
	}
	

	public static String getExtensionSeparator() {
		return EXTENSION_SEPARATOR;
	}
}
