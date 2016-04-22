package com.froad.cbank.coremodule.normal.boss.utils;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
/**
 * 图片压缩处理
 * @author 
 */
@SuppressWarnings({ "restriction", "unused" })
public class AdImgZipUtil {
	private Image img;
	private int width;
	private int height;
	
	/**
	 * 构造函数
	 */
	public AdImgZipUtil(String fileName) throws IOException {
		File file = new File(fileName);// 读入文件
		img = ImageIO.read(file);      // 构造Image对象
		width =new BigDecimal(img.getWidth(null)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();    // 得到源图宽
		height =new BigDecimal(img.getHeight(null)).setScale(0, BigDecimal.ROUND_HALF_UP).intValue();  // 得到源图长
	}
	/**
	 * 按照宽度还是高度进行压缩
	 * @param w int 最大宽度
	 * @param h int 最大高度
	 */
	public void resizeFix(int w, int h,String desFileUrl) throws IOException {
		if (width / height > w / h) {
			resizeByWidth(w,desFileUrl);
		} else {
			resizeByHeight(h,desFileUrl);
		}
	}
	/**
	 * 以宽度为基准，等比例放缩图片
	 * @param w int 新宽度
	 */
	public void resizeByWidth(int w,String desFileUrl) throws IOException {
		int h = (int) (height * w / width);
		resize(w, h,desFileUrl);
	}
	/**
	 * 以高度为基准，等比例缩放图片
	 * @param h int 新高度
	 */
	public void resizeByHeight(int h,String desFileUrl) throws IOException {
		int w = (int) (width * h / height);
		resize(w, h,desFileUrl);
	}
	
	/**
	 * 强制压缩/放大图片到固定的大小
	 * @param w int 新宽度
	 * @param h int 新高度
	 */
	public void resize(int w, int h,String desFileUrl) throws IOException {
		BufferedImage image = new BufferedImage(w, h,BufferedImage.TYPE_INT_RGB ); 
		image.getGraphics().drawImage(img, 0, 0, w, h, null); // 绘制缩小后的图
		File destFile = new File(desFileUrl);
		FileOutputStream out = new FileOutputStream(destFile); // 输出到文件流
		// 可以正常实现bmp、png、gif转jpg
		JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
		encoder.encode(image); // JPEG编码
		out.close();
		
	}
}