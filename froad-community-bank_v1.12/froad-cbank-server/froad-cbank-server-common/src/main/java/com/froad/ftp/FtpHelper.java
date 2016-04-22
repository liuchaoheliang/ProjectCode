package com.froad.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPListParseEngine;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.commons.pool.impl.GenericObjectPool;
import org.apache.commons.pool.impl.GenericObjectPool.Config;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.pool.FTPPool;
import com.froad.logback.LogCvt;
import com.froad.util.Checker;
import com.froad.util.PropertiesUtil;

public class FtpHelper {
	private static Object lock = new Object();
	
	/**
	 * 配置文件Map
	 */
	public static final Map<String, String> SFTP_PROPERTIES = PropertiesUtil.loadProperties(FtpConstants.FTP_PROPERTIES_NAME);
	
    /**
     * 本地文件名
     */
    private String localFileName;
    /**
     * 远程文件名
     */
    private String remoteFileName;
	
	/**
	 * Ftp连接池
	 */
	private static FTPPool pool;

	static {
		synchronized (lock) {
			if (pool == null) {
				initPool();
			}
		}
	}
	
	private FtpHelper(){};
	
	/**
	 * 初始化连接池
	 */
	private static void initPool() {
		long begin = System.currentTimeMillis();
		// Ftp服务器
		String host = SFTP_PROPERTIES.get(FtpConstants.FTP_HOST);
		// 端口
		int port = FtpConstants.FTP_DEFAULT_PORT;
		if (SFTP_PROPERTIES.get(FtpConstants.FTP_PORT) != null && Integer.valueOf(SFTP_PROPERTIES.get(FtpConstants.FTP_PORT)) > 0) {
			port = Integer.valueOf(SFTP_PROPERTIES.get(FtpConstants.FTP_PORT));
		}
		// 用户名
		String username = SFTP_PROPERTIES.get(FtpConstants.FTP_USERNAME);
		// 密码
		String password = SFTP_PROPERTIES.get(FtpConstants.FTP_PASSWORD);

		GenericObjectPool.Config config = new Config();
		// 设置后进先出的池策略
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_LIFO)) {
			config.lifo = Boolean.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_LIFO));
		}
		// 允许最大活动对象数
		if (SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_ACTIVE) != null && Integer.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_ACTIVE)) > 0) {
			config.maxActive = Integer.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_ACTIVE));
		} else {
			config.maxActive = FtpConstants.POOL_DEFAULT_MAX_ACTIVE;
		}
		// 允许最大空闲对象数
		if (SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_IDLE)!=null && Integer.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_IDLE))>0) {
			config.maxIdle = Integer.parseInt(SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_IDLE));
		}
		// 池为空时取对象等待的最大毫秒数.
		if (SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_WAIT) != null && Integer.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_WAIT)) > 0) {
			config.maxWait = Long.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_MAX_WAIT));
		} else {
			config.maxWait = FtpConstants.POOL_DEFAULT_MAX_WAIT;			
		}
		// 被空闲对象回收器回收前在池中保持空闲状态的最小时间毫秒数
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_MIN_EVICTABLE_IDLE_TIMEMILLIS)) {
			config.minEvictableIdleTimeMillis = Integer.parseInt(SFTP_PROPERTIES.get(FtpConstants.POOL_MIN_EVICTABLE_IDLE_TIMEMILLIS));
		}
		// 允许最小空闲对象数
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_MIN_IDLE)) {
			config.minIdle = Integer.parseInt(SFTP_PROPERTIES.get(FtpConstants.POOL_MIN_IDLE));
		}
		// 设定在进行后台对象清理时，每次检查对象数
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_NUM_TESTS_PEREVICTION_RUN)) {
			config.numTestsPerEvictionRun = Integer.parseInt(SFTP_PROPERTIES.get(FtpConstants.POOL_NUM_TESTS_PEREVICTION_RUN));
		}
		// 取出对象时验证(此处设置成验证ftp是否处于连接状态).
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_TEST_ON_BORROW)) {
			config.testOnBorrow = Boolean.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_TEST_ON_BORROW));
		}
		// 还回对象时验证(此处设置成验证ftp是否处于连接状态).
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_TEST_ON_RETURN)) {
			config.testOnReturn = Boolean.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_TEST_ON_RETURN));
		}
		// 指明连接是否被空闲连接回收器(如果有)进行检验.如果检测失败,则连接将被从池中去除.
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_TEST_WHILE_IDLE)) {
			config.testWhileIdle = Boolean.valueOf(SFTP_PROPERTIES.get(FtpConstants.POOL_TEST_WHILE_IDLE));
		}
		// 在空闲连接回收器线程运行期间休眠的时间毫秒数. 如果设置为非正数,则不运行空闲连接回收器线程
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS)) {
			config.timeBetweenEvictionRunsMillis = Integer.parseInt(SFTP_PROPERTIES.get(FtpConstants.POOL_TIME_BETWEEN_EVICTION_RUNS_MILLIS));
		}
		// 从池中取对象达到最大时,继续创建新对象.
		if (SFTP_PROPERTIES.containsKey(FtpConstants.POOL_WHEN_EXHAUSTED_ACTION)) {
			config.whenExhaustedAction = Byte.parseByte(SFTP_PROPERTIES.get(FtpConstants.POOL_WHEN_EXHAUSTED_ACTION));
		} else {
			config.whenExhaustedAction = GenericObjectPool.WHEN_EXHAUSTED_GROW;
		}
		LogCvt.info("FTP连接池配置信息: " + JSON.toJSONString(config, true));
		// FTP字符集
		String encoding = "UTF-8"; // 默认字符集
		if(SFTP_PROPERTIES.containsKey(FtpConstants.FTP_ENCODING)){
			encoding = SFTP_PROPERTIES.get(FtpConstants.FTP_ENCODING);
		}
		pool = new FTPPool(config, host, port, username, password, "true", encoding);
		LogCvt.info("初始化FTP连接池成功！borrowSize = " + pool.borrowSize() + ",inPoolSize = " + pool.inPoolSize() + ",耗时:" + (System.currentTimeMillis() - begin));
	}
	
	public FTPClient connectFTPServer(FTPClient ftp) throws Exception {
		//Ftp服务器
		String host = SFTP_PROPERTIES.get(FtpConstants.FTP_HOST);
		//端口
		int port = FtpConstants.FTP_DEFAULT_PORT;
		if (SFTP_PROPERTIES.get(FtpConstants.FTP_PORT)!=null && Integer.valueOf(SFTP_PROPERTIES.get(FtpConstants.FTP_PORT)) > 0){
			port = Integer.valueOf(SFTP_PROPERTIES.get(FtpConstants.FTP_PORT));
		}
		//用户名
		String username = SFTP_PROPERTIES.get(FtpConstants.FTP_USERNAME);
		//密码
		String password = SFTP_PROPERTIES.get(FtpConstants.FTP_PASSWORD);
		return connectFTPServer( host,  port,  username, password,ftp);
	}
	
	/**
	 * 连接FTP服务器
	 * 
	 * @param server
	 * @param uname
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public FTPClient connectFTPServer(String host, int port, String username,String password,FTPClient ftp) throws Exception {
		try {
			ftp.configure(getFTPClientConfig());
			ftp.connect(host, port);
			if (!ftp.login(username, password)) {
				ftp.logout();
				ftp = null;
				return ftp;
			}

			// 文件类型,默认是ASCII
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.setControlEncoding("GBK");
			// 设置被动模式
			ftp.enterLocalPassiveMode();
			ftp.setConnectTimeout(2000);
			ftp.setBufferSize(1024);
			// 响应信息
			int replyCode = ftp.getReplyCode();
			if ((!FTPReply.isPositiveCompletion(replyCode))) {
				// 关闭Ftp连接
				closeFTPClient(ftp);
				// 释放空间
				ftp = null;
				throw new Exception("登录FTP服务器失败,请检查![Server Host:" + host
						+ ",User:" + username + ",Password:" + password);
			} else {
				return ftp;
			}
		} catch (Exception e) {
			ftp.disconnect();
			ftp = null;
			throw e;
		}
	}

	/**
	 * 配置FTP连接参数
	 * 
	 * @return
	 * @throws Exception
	 */
	public FTPClientConfig getFTPClientConfig() throws Exception {
		String systemKey = FTPClientConfig.SYST_NT;
		String serverLanguageCode = "zh";
		FTPClientConfig conf = new FTPClientConfig(systemKey);
		conf.setServerLanguageCode(serverLanguageCode);
		conf.setDefaultDateFormatStr("yyyy-MM-dd");
		return conf;
	}
	
	/**
     * 上传文件
     * @param localFile 本地文件
     * @param remoteFile 远程文件
     * @author 张开
     * @throws FroadBusinessException 
     * @date   2015-08-26
     */
	private String uploadFile(String fileName) throws FroadBusinessException {
		return uploadFile(fileName,false,null);
	}
	
	/**
	 * 向FTP根目录上传文件
	 * 
	 * @param localFile
	 * @param newName
	 *            新文件名
	 * @throws FroadBusinessException
	 */
	private String uploadFile(String fileName,boolean isLocalFileInFolder,String localFileFolder) throws FroadBusinessException {
		long startTime = System.currentTimeMillis();
		String localFileDir = SFTP_PROPERTIES.get(FtpConstants.LOCAL_FILE_DIR);
		String remoteFileDir = SFTP_PROPERTIES.get(FtpConstants.REMOTE_FILE_DIR) + getSubFileDir();
		String nginxRootDir = SFTP_PROPERTIES.get(FtpConstants.NGINX_ROOT_DIR);
		String backslash = FtpConstants.SLASH;
		/*if (isWindowsOs()) {
			backslash = FtpConstants.DOUBLE_BACKSLASH;
		}*/
		if(isLocalFileInFolder){
			if(Checker.isEmpty(localFileFolder)){
				localFileFolder = "";
			}
			this.localFileName = localFileDir + backslash + localFileFolder + backslash + fileName;
		}else{
			this.localFileName = localFileDir + backslash + fileName;
		}
		
		this.remoteFileName = remoteFileDir + FtpConstants.SLASH +fileName;
		
		FTPClient ftpClient = pool.getResource();
		//远程目录是否存在
		try {
			if(!this.changeDirectory(remoteFileDir,ftpClient)){
				boolean isSuc = this.createDirecroty(remoteFileDir,ftpClient);
				if(!isSuc){
					LogCvt.error("创建服务器文件目录失败！当前路径："+ftpClient.printWorkingDirectory()+"，请检查创建的路径："+remoteFileDir);
					throw new FroadBusinessException(ResultCode.failed.getCode(),"创建服务器文件目录失败");
				}
			}
		} catch (Exception e) {
			LogCvt.error("创建服务器文件目录失败！",e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),"创建服务器文件目录失败");
		}
		
		InputStream input = null;
		boolean success = false;
		int fileSize = 0;
		try {
			File file = null;
			if (checkFileExist(this.localFileName)) {
				file = new File(this.localFileName);
			}
			input = new FileInputStream(file);
			fileSize = input.available();
			
			success = ftpClient.storeFile(new String(fileName.getBytes(),FtpConstants.SERVER_CHARSET), input);
			
			if (!success) {
				throw new FroadBusinessException(ResultCode.failed.getCode(),"文件上传失败!");
			}
		} catch (Exception e) {
			LogCvt.error("异常："+e.getMessage(),e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),"文件上传失败");
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					LogCvt.error("关闭FileInputStream失败！" , e);
				}
			}
			pool.returnResource(ftpClient);
		}
		
		long endTime = System.currentTimeMillis();
		String url = this.remoteFileName.replace(nginxRootDir, "");
		LogCvt.info("[文件：" + fileName + " (" + FormetFileSize(fileSize) + ")]上传成功！上传耗时：" + (endTime - startTime) + "ms");
		return url;
	}
	
	private boolean isWindowsOs(){
		return System.getProperty("os.name").contains("windows") || System.getProperty("os.name").contains("Windows");
	}
	
	/**
	 * 获取子文件夹名（yyyyMMdd）
	 * @return
	 */
	private String getSubFileDir() {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		return format.format(new Date());
	}
	
	private String FormetFileSize(long fileSize) {// 转换文件大小
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileSize < 1024) {
			fileSizeString = df.format((double) fileSize) + "B";
		} else if (fileSize < 1048576) {
			fileSizeString = df.format((double) fileSize / 1024) + "K";
		} else if (fileSize < 1073741824) {
			fileSizeString = df.format((double) fileSize / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileSize / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 向FTP根目录上传文件
	 * 
	 * @param localFile
	 * @param newName
	 *            新文件名
	 * @throws Exception
	 */
	public Boolean uploadFile(String localFile, String newName,FTPClient ftp)
			throws Exception {
		InputStream input = null;
		boolean success = false;
		try {
			File file = null;
			if (checkFileExist(localFile)) {
				file = new File(localFile);
			}
			input = new FileInputStream(file);
			success = ftp.storeFile(newName, input);
			if (!success) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return success;
	}

	/**
	 * 向FTP根目录上传文件
	 * 
	 * @param input
	 * @param newName
	 *            新文件名
	 * @throws Exception
	 */
	public Boolean uploadFile(InputStream input, String newName,FTPClient ftp)
			throws Exception {
		boolean success = false;
		try {
			success = ftp.storeFile(newName, input);
			if (!success) {
				throw new Exception("文件上传失败!");
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (input != null) {
				input.close();
			}
		}
		return success;
	}

	/**
	 * 获取FTP服务器上指定路径下的文件列表
	 * 
	 * @param filePath
	 * @return
	 */
	public List<FTPFile> getFtpServerFileList(String remotePath,FTPClient ftp)
			throws Exception {

		FTPListParseEngine engine = ftp.initiateListParsing(remotePath);
		List<FTPFile> ftpfiles = Arrays.asList(engine.getNext(25));

		return ftpfiles;
	}

	/**
	 * 获取FTP服务器上[指定路径]下的文件列表
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public List<FTPFile> getFileList(String remotePath,FTPClient ftp) throws Exception {

		List<FTPFile> ftpfiles = Arrays.asList(ftp.listFiles(remotePath));

		return ftpfiles;
	}

	/**
	 * 获取FTP服务器[当前工作路径]下的文件列表
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public List<FTPFile> getFileList(FTPClient ftp) throws Exception {

		List<FTPFile> ftpfiles = Arrays.asList(ftp.listFiles());
		return ftpfiles;
	}

	/**
	 * 改变FTP服务器工作路径 
	 * 
	 * @param remoteFoldPath
	 */
	public Boolean changeDirectory(String remoteFoldPath,FTPClient ftp) throws IOException {
		return ftp.changeWorkingDirectory(remoteFoldPath);
	}

	/**
	 * 删除文件
	 * 
	 * @param remoteFilePath
	 * @return
	 * @throws Exception
	 */
	public Boolean deleteFtpServerFile(String remoteFilePath,FTPClient ftp) throws Exception {
		return ftp.deleteFile(remoteFilePath);
	}

	/**
	 * 创建文件夹
	 * 
	 * @param remoteFoldPath
	 * @return
	 */
	public boolean createFolder(String folderName,FTPClient ftp) throws Exception {
		LogCvt.info("创建服务器目录：" + folderName);
		boolean flag = ftp.makeDirectory(folderName);
		if (!flag) {
			throw new Exception("创建目录失败");
		}
		return false;
	}
	
	/**
	 * 递归创建远程服务器目录
	 * 
	 * @param remote 远程服务器文件绝对路径
	 * 
	 * @return 目录创建是否成功
	 * @throws IOException
	 */
	public boolean createDirecroty(String remote,FTPClient ftp) throws IOException {
		StringBuffer currentDir = new StringBuffer();
		if (remote != null && remote.length() > 0) {
			String[] dirs = remote.split("/");
			if(Checker.isNotEmpty(ftp.printWorkingDirectory())){
				ftp.changeToParentDirectory();
			}
			for(String str : dirs){
				if(Checker.isEmpty(str)){
					continue;
				}
				currentDir.append("/");
				currentDir.append(str);
				if(!this.changeDirectory(new String(currentDir),ftp)){
					boolean createResult = ftp.makeDirectory(new String(str));
					this.changeDirectory(new String(currentDir.toString()),ftp);
					if(!createResult){
						return false;
					}else{
						this.changeDirectory(new String(currentDir),ftp);
						continue;
					}
				}else{
					continue;
				}
			}
		}
		if(!this.changeDirectory(remote,ftp)){
			return false;
		}
		return true;
	}



	/**
	 * 删除目录
	 * @param remoteFoldPath
	 * @return
	 * @throws Exception
	 */
	public boolean deleteFold(String remoteFoldPath,FTPClient ftp) throws Exception {
		
		return ftp.removeDirectory(remoteFoldPath) ;
	}

	/**
	 * 删除目录以及文件
	 * 
	 * @param remoteFoldPath
	 * @return
	 */
	public boolean deleteFoldAndsubFiles(String remoteFoldPath,FTPClient ftp)
			throws Exception {
		boolean success = false;
		List<FTPFile> list = this.getFileList(remoteFoldPath,ftp);
		if (list == null || list.size() == 0) {
			return deleteFold(remoteFoldPath,ftp);
		}
		for (FTPFile ftpFile : list) {
			
			String name = ftpFile.getName();
			if (ftpFile.isDirectory()) {
				success = deleteFoldAndsubFiles(remoteFoldPath + "/" + name,ftp);
				if (!success)
					break;
			} else {
				success = deleteFtpServerFile(remoteFoldPath + "/" + name,ftp);
				if (!success)
					break;
			}
		}
		if (!success)
			return false;
		success = deleteFold(remoteFoldPath,ftp);
		return success;
	}

	/**
	 * 检查本地路径是否存在
	 * 
	 * @param filePath
	 * @return
	 * @throws Exception
	 */
	public boolean checkFileExist(String filePath) throws Exception {
		boolean flag = false;
		File file = new File(filePath);
		if (!file.exists()) {
			throw new Exception("本地路径不存在,请检查:"+filePath);
		} else {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 关闭FTP连接
	 * 
	 * @param ftp
	 * @throws Exception
	 */
	public void closeFTPClient(FTPClient ftp) throws Exception {

		try {
			if (ftp.isConnected())
				ftp.logout();
				ftp.disconnect();
		} catch (Exception e) {
			throw new Exception("关闭FTP服务出错!");
		}
	}

	public static String upload(String fileName)throws FroadBusinessException {
		String url = "";
		try {
			FtpHelper ftp = new FtpHelper();
			url = ftp.uploadFile(fileName);
		} catch (Exception e) {
			LogCvt.error("文件上传异常：",e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),"文件上传失败!");
		}
		
		return url;
	}
	
	public static String upload(String fileName, String folder)throws FroadBusinessException {
		String url = "";
		try {
			FtpHelper ftp = new FtpHelper();
			boolean isFileInFolder = false;
			if (Checker.isNotEmpty(folder)) {
				isFileInFolder = true;
			}
			url = ftp.uploadFile(fileName, isFileInFolder, folder);
		} catch (Exception e) {
			LogCvt.error("文件上传异常：",e);
			throw new FroadBusinessException(ResultCode.failed.getCode(),"文件上传失败!");
		}
		
		return url;
	}

}

