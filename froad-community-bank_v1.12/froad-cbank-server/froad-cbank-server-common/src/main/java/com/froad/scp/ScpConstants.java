package com.froad.scp;


public class ScpConstants {
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
	 * properties文件名-scp.properties
	 */
	public static final String SCP_PROPERTIES_NAME = "ftp";
	/**
	 * FTP主机地址
	 */
	public static final String PROPERTY_SCP_HOST = "ftp.host";
	/**
	 * FTP端口
	 */
	public static final String PROPERTY_SCP_PORT = "scp.port";
	/**
	 * FTP用户名
	 */
	public static final String PROPERTY_SCP_USER = "ftp.username";
	/**
	 * FTP登录密码
	 */
	public static final String PROPERTY_SCP_PASSWORD = "ftp.password";

	/**
	 * 服务器excel文件目录
	 */
	public static final String REMOTE_FILE_DIR = "remote.excel.dir";
	/**
	 * FTP传输编码
	 */
	public static final String PROPERTY_SCP_ENCODING = "ftp.encoding";
	/**
	 * nginx根目录
	 */
	public static final String NGINX_ROOT_DIR = "nginx.root.dir";

}
