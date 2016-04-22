package com.froad.ftp;

import java.util.Map;

import com.froad.util.PropertiesUtil;

public class FtpConstants {
	
	
	/**
	 * 配置文件Map
	 */
	public static final Map<String, String> SFTP_PROPERTIES = PropertiesUtil.loadProperties(FtpConstants.FTP_PROPERTIES_NAME);
	
    /**
     * 文件服务器ip地址
    */
    public static final String FTP_HOST = "ftp.host";
    
    /**
     * 文件服务器端口号
     */
    public static final String FTP_PORT = "ftp.port";
    
    /**
     * 文件服务器登录名
     */
    public static final String FTP_USERNAME = "ftp.username";
    
    /**
     * 文件服务器登录密码
     */
    public static final String FTP_PASSWORD = "ftp.password";
    
    /**
     * 编码
     */
    public static final String FTP_ENCODING = "ftp.encoding";
    
    /**
     * 默认端口
     */
    public static final int FTP_DEFAULT_PORT = 21;
    
    /**
     * 本地目录
     */
    public static final String FTP_REQ_LOC = "ftp.location";
    
    /**
     * 超时毫秒数
     */
    public static final String FTP_TIMEOUT = "ftp.timeout";
    
    /**
     * 本地excel文件目录
     */
//    public static final String ROOT_FILE_DIR = "/root";
    
    /**
     * 本地excel文件目录
     */
    public static final String LOCAL_FILE_DIR = "local.excel.dir";
    
    /**
     * 服务器excel文件目录
     */
    public static final String REMOTE_FILE_DIR = "remote.excel.dir";
    
    
    /**
     * nginx根目录
     */
    public static final String NGINX_ROOT_DIR = "nginx.root.dir";
    
    /**
     * 配置文件 FTP.properties
     */
    public static final String FTP_PROPERTIES_NAME = "ftp";
    
    
    /**
	 * 斜杠
	 */
	public static final String SLASH = "/";
	
	/**
	 * 双反斜杠
	 */
	public static final String DOUBLE_BACKSLASH = "\\";	
	
	/**
	 * 2003Excel文件后缀
	 */
	public static final String XLS_SUFFIX = ".xls";
	
	/**
	 * 2007Excel文件后缀
	 */
	public static final String XLSX_SUFFIX = ".xlsx";
	
	/**
	 * csv文件后缀
	 */
	public static final String CSV_SUFFIX = ".csv";
	
	/**
	 * zip文件后缀
	 */
	public static final String ZIP_SUFFIX = ".zip";
	
	/**
	 * 设置后进先出的池策略
	 */
	public static final String POOL_LIFO = "pool.lifo";
	/**
	 * 允许最大活动对象数
	 */
	public static final String POOL_MAX_ACTIVE = "pool.maxActive";
	/**
	 * 允许最大空闲对象数
	 */
	public static final String POOL_MAX_IDLE = "pool.maxIdle";
	/**
	 * 被空闲对象回收器回收前在池中保持空闲状态的最小时间毫秒数 
	 */
	public static final String POOL_MIN_EVICTABLE_IDLE_TIMEMILLIS = "pool.minEvictableIdleTimeMillis";
	/**
	 * 允许最小空闲对象数
	 */
	public static final String POOL_MIN_IDLE = "pool.minIdle";
	
	/**
	 * 设定在进行后台对象清理时，每次检查对象数 
	 */
	public static final String POOL_NUM_TESTS_PEREVICTION_RUN = "pool.numTestsPerEvictionRun";
	/**
	 * 指明是否在从池中取出对象前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个.
	 */
	public static final String POOL_TEST_ON_BORROW  = "pool.testOnBorrow";
	/**
	 * 指明是否在从池中取出对象前进行检验,如果检验失败,则从池中去除连接并尝试取出另一个.
	 */
	public static final String POOL_TEST_ON_RETURN  = "pool.testOnReturn";
	/**
	 * 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除. 
	 */
	public static final String POOL_TEST_WHILE_IDLE  = "pool.testWhileIdle";
	/**
	 * 在空闲连接回收器线程运行期间休眠的时间毫秒数. 如果设置为非正数,则不运行空闲连接回收器线程 
	 */
	public static final String POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS  = "pool.timeBetweenEvictionRunsMillis";
	/**
	 * 当池中对象用完时，请求新的对象所要执行的动作 
	 */
	public static final String POOL_WHEN_EXHAUSTED_ACTION  = "pool.whenExhaustedAction";
	
	/**
	 * 最大池容量
	 */
	public static final int POOL_DEFAULT_MAX_ACTIVE = 100;
	
	/**
	 * 池为空时取对象等待的最大毫秒数.
	 */
	public static final String POOL_MAX_WAIT = "pool.maxWait";
	
	/**
	 * 池为空时取对象等待的最大毫秒数.
	 */
	public static final long POOL_DEFAULT_MAX_WAIT = 60 * 1000;
	
	/** 本地字符编码 */
	public static String LOCAL_CHARSET = "GBK";
	 
	/** FTP协议里面，规定文件名编码为iso-8859-1 */
	public static String SERVER_CHARSET = "ISO-8859-1";
    
	/**
	 * 字典sql目录
	 */
    public static final String DICTIONARY_SQL = "dictionary_sql";
    
}
