package com.froad.cbank.coremodule.module.normal.bank.util;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;


public class ImageUtil {
	

	private static Graphics2D g = null;

	private static int base_width = 854;  //底图宽度
	private static int font_size = 38; //门店名称字体大小
	private static Font font = new Font("宋体", Font.BOLD,font_size);// 添加默认字体的属性设置

	static{//使用微软雅黑字体
		try { 
			font=Font.createFont(Font.TRUETYPE_FONT, new File(Constants.getFontPath()+"msyh.ttf"));
			font=font.deriveFont(Font.BOLD, font_size);;
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
   
	/**
	 * 导入本地图片到缓冲区
	 */
	public static BufferedImage loadImageLocal(String imgName) {
		try {
			File newFile = new File(imgName);
			return ImageIO.read(newFile);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 导入网络图片到缓冲区
	 */
	public static BufferedImage loadImageUrl(String imgName) {
		try {
			URL url = new URL(imgName);
			return ImageIO.read(url);
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return null;
	}

	/**
	 * 生成新图片到本地
	 */
	public  static void writeImageLocal(String newImage, BufferedImage img) {
		if (newImage != null && img != null) {
			try {
				File outputfile = new File(newImage);
				ImageIO.write(img, "jpg", outputfile);
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}

  
	/**
	 * @param bimg 背景图
	 * @param dimg 合成图
	 * @param content 合成文字
	 * @return
	 */
	public static BufferedImage modifyImage(BufferedImage bimg, BufferedImage dimg, String content) {

		try {
			g = bimg.createGraphics();
			/* 消除java.awt.Font字体的锯齿 */  
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); // 图形抗锯齿
			g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			/**合成图片begin*/
 			g.drawImage(bimg, 0, 0, bimg.getWidth(), bimg.getHeight(), null);
			AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER);
			g.setComposite(ac); 
			g.drawImage(dimg,(int)(bimg.getWidth()-dimg.getWidth()*1.25)/2,332, (int) (dimg.getWidth()*1.25), (int)(dimg.getHeight()*1.25), null);
			/**合成图片end*/
			if (font != null)
				g.setFont(font); 
			FontMetrics fm = g.getFontMetrics();
			int len = fm.stringWidth(content.toString());
			while (len > bimg.getWidth()) {//自动缩放文字
				font_size = font_size - 1;
				font=font.deriveFont(Font.BOLD,font_size);
				fm = g.getFontMetrics();
				len = fm.stringWidth(content.toString());
				g.setFont(font);
			}
			int x = (base_width - len) / 2;
			if (content != null) {
				g.setColor(Color.RED);// 设置字体颜色
				g.setStroke(new BasicStroke(1));
				g.drawString(content.toString(), x, 294);
			}
			g.dispose();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bimg;
	}
	public static void main(String[] args) {

		
		BufferedImage baseImage = ImageUtil.loadImageLocal("D:\\xx\\chongqing.jpg");
		BufferedImage qrcodeImage = ImageUtil.loadImageLocal("D:\\xx\\qr.png");
		ImageUtil.writeImageLocal("D:\\xx\\t5444.jpg", ImageUtil.modifyImage(baseImage, qrcodeImage, "避风港（联洋广场店）测试是水水水水飒飒啊啊啊啊的顶顶顶顶顶滚滚滚滚滚滚滚滚滚滚"));
		System.out.println("success");
		
		
	}
	
}
