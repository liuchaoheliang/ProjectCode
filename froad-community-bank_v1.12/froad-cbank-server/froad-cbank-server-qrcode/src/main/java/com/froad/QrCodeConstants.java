package com.froad;

import java.awt.Color;
import java.awt.Font;

public class QrCodeConstants {
	/**
	 * 空白字符串
	 */
	public static final String BLANK_STRING = "";
	/**
	 * 斜杠
	 */
	public static final String SLASH = "/";
	/**
	 * 双反斜杠
	 */
	public static final String DOUBLE_BACKSLASH = "\\";	
	
	/**
	 * 正常返回
	 */
	public static final String NORMAL_RESPONSE = "0000";
	/**
	 * 异常返回
	 */
	public static final String ABNORMAL_RESPONSE = "9999";
	
	/**
	 * 二维码properties文件名-qrcode.properties
	 */
	public static final String QR_PROPERTIES_NAME = "qrcode";
	/**
	 * FTP主机地址
	 */
	public static final String PROPERTY_SCP_HOST = "scp.host";
	/**
	 * FTP端口
	 */
	public static final String PROPERTY_SCP_PORT = "scp.port";
	/**
	 * FTP用户名
	 */
	public static final String PROPERTY_SCP_USER = "scp.username";
	/**
	 * FTP登录密码
	 */
	public static final String PROPERTY_SCP_PASSWORD = "scp.password";
	/**
	 * FTP传输编码
	 */
	public static final String PROPERTY_SCP_ENCODING = "scp.encoding";
	/**
	 * FTP二维码文件上传目录
	 */
	public static final String PROPERTY_SCP_QRCODE_DIR = "scp.qrcode.dir";
	/**
	 * FTP文字验证码上传目录
	 */
	public static final String PROPERTY_SCP_WRODIMAGE_DIR = "scp.wordimage.dir";	

	/**
	 * 二维码本地存储目录
	 */
	public static final String PROPERTY_QRCODE_LOCAL_DIR = "qrcode.qrcodeDir";
	/**
	 * 银行logo本地存储目录
	 */
	public static final String PROPERTY_LOGO_LOCAL_DIR = "qrcode.logoDir";
	/**
	 * 图片验证码本地存储目录
	 */
	public static final String PROPERTY_WORDIMAGE_LOCAL_DIR = "qrcode.wordImageDir";
	/**
	 * 二维码图片URI前缀
	 */
	public static final String PROPERTY_ENGINX_ROOT_QRCODE = "nginx.root.qrcode";
	/**
	 * 图片验证码图片URI前缀
	 */
	public static final String PROPERTY_ENGINX_ROOT_WORDIMAGE = "nginx.root.wordimage";
	
	/**
	 * Redis 客户端表-cb_client
	 */
	public static final String REDIS_CB_CLIENT_KEY_PREFIX = "cbbank:client:";
	/**
	 * 数据表cb_client字段qr_logo
	 */
	public static final String REDIS_CB_CLIENT_QR_LOGO = "qr_logo";
	
	/**
	 * 二维码图片默认宽度
	 */
	public static final int QR_IMAGE_WIDTH = 300;
	/**
	 * 二维码图片默认长度
	 */
	public static final int QR_IMAGE_HEIGHT = 300;
	
	/** 图片宽度 */
	public static final int IMAGE_WIDTH = 80;

	/** 图片高度 */
	public static final int IMAGE_HEIGHT = 35;

	/** 最小字体大小 */
	public static final int MIN_FONT_SIZE = 18;

	/** 最大字体大小 */
	public static final int MAX_FONT_SIZE = 20;

	/** 最小字符个数 */
	public static final int MIN_WORD_LENGTH = 4;

	/** 最大字符个数 */
	public static final int MAX_WORD_LENGTH = 4;

	/** 随机背景图片路径 */
	public static final String BACKGROUND_IMAGE_PATH = "/config/captcha/";
	
	/*public static final Font[] FONTS = new Font[] { new Font("nyala", Font.BOLD, MAX_FONT_SIZE), new Font("Arial", Font.BOLD, MAX_FONT_SIZE), new Font("nyala", Font.BOLD, MAX_FONT_SIZE), new Font("Bell", Font.BOLD, MAX_FONT_SIZE), new Font("Bell MT", Font.BOLD, MAX_FONT_SIZE), new Font("Credit", Font.BOLD, MAX_FONT_SIZE), new Font("valley", Font.BOLD, MAX_FONT_SIZE),
		new Font("Impact", Font.BOLD, MAX_FONT_SIZE) };*/
	public static final Font[] FONTS = new Font[] { 
		Font.decode("华文细黑") };
	
	
	public static final Color[] COLORS = new Color[] { new Color(255, 255, 255), new Color(255, 220, 220), new Color(220, 255, 255), new Color(220, 220, 255), new Color(255, 255, 220), new Color(220, 255, 220) };
	
}
