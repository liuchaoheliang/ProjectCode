package com.froad.scp;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.SFTPv3Client;

import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.logback.LogCvt;
import com.froad.util.PropertiesUtil;

public class ScpUtil {
	private static SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	private static Object lock = new Object();
	private static Connection scpConnection = null;
	private static final Map<String, String> scpInfoMap = PropertiesUtil.loadProperties(ScpConstants.SCP_PROPERTIES_NAME);

	private static String hostname = null;
	private static int port = 22;
	private static String userName = null;
	private static String password = null;

	private static String remoteFileDir = null;
	private static String nginxRootDir = null;
	static {
		hostname = scpInfoMap.get(ScpConstants.PROPERTY_SCP_HOST).toString();
		port = Integer.valueOf(scpInfoMap.get(ScpConstants.PROPERTY_SCP_PORT));
		userName = scpInfoMap.get(ScpConstants.PROPERTY_SCP_USER).toString();
		password = scpInfoMap.get(ScpConstants.PROPERTY_SCP_PASSWORD).toString();

		remoteFileDir = scpInfoMap.get(ScpConstants.REMOTE_FILE_DIR).toString();
		nginxRootDir = scpInfoMap.get(ScpConstants.NGINX_ROOT_DIR).toString();
		LogCvt.info("SCP初始化成功! host:" + hostname + ", port:" + port + ", user:" + userName);
	}

	/**
	 * Creates a new instance of ScpUtil.
	 * 
	 */
	private ScpUtil() {
	}

	private static boolean connectServer() {
		boolean isAuthed = false;
		try {
			scpConnection = new Connection(hostname, port);
			scpConnection.connect();

			isAuthed = scpConnection.authenticateWithPassword(userName, password);
			if (isAuthed) {
				LogCvt.info("SCP登陆验证成功!!!");
			}
		} catch (Exception e) {
			LogCvt.error("SCP登陆验证失败! ", e);
		}
		return isAuthed;
	}

	/**
	 * 
	 * uploadFile:(这里用一句话描述这个方法的作用).
	 * 
	 * @author vania 2015年9月17日 下午4:08:44
	 * @param localFilePath 本地文件全路径
	 * @param remoteFileName 远程文件名字
	 * @param isRetry
	 * @throws FroadBusinessException
	 * 
	 */
	public static String uploadFile(String localFilePath, String remoteFileName) throws FroadBusinessException {
		StringBuffer scpDir = new StringBuffer("");

		scpDir.append(remoteFileDir).append(ScpConstants.SLASH).append(format.format(new Date()));
		try {
			createDir(scpDir.toString()); // 创建远程目录
		} catch (Exception e) {
			throw new FroadBusinessException("创建远程目录失败!");
		}
		return uploadFile(localFilePath, remoteFileName, scpDir.toString());
	}

	/**
	 * 
	 * uploadFile:SCP 上传文件
	 * 
	 * @author vania 2015年9月17日 下午3:30:54
	 * @param localFilePath
	 *            本地文件全路径
	 * @param remoteFileName
	 *            远程文件名字
	 * @param scpDir
	 *            远程文件目录
	 * @param isRetry
	 * 
	 */
	public static String uploadFile(String localFilePath, String remoteFileName, String scpDir) throws FroadBusinessException {
		long startTime = System.currentTimeMillis();
		SCPClient scpClient = null;
		try {
			// 如ssh连接已失效，重新连接
			if (null == scpConnection) {
				connectServer();
			}

			scpClient = scpConnection.createSCPClient();

			try {
				// 其他用户组仅允许读和执行权限
				scpClient.put(localFilePath, remoteFileName, scpDir, "0774");
			} catch (Exception e) {
				// 如连接超出可用连接，重新连接
				try {
					LogCvt.info("SCP超出可用连接，重新连接", e);
					uploadFile(localFilePath, remoteFileName, scpDir);					
				} catch (Exception e2) {
					throw e2;
				}
			}

		} catch (Exception e) {
			LogCvt.error("文件上传失败：" + e.getMessage(), e);
			throw new FroadBusinessException(ResultCode.failed.getCode(), "文件上传失败");
		}
		long endTime = System.currentTimeMillis();

		String url = scpDir.replace(nginxRootDir, "") + ScpConstants.SLASH + remoteFileName;
		LogCvt.info("[文件：" + remoteFileName + "]上传成功！上传耗时：" + (endTime - startTime) + "ms");
		return url;
	}

	/**
	 * 创建目录
	 * 
	 * @param dirName
	 * @return
	 * @throws IOException
	 */
	public static void createDir(String dirName) throws IOException {
		SFTPv3Client sftpClient = null;

		try {
			// 如ssh连接已失效，重新连接
			if (null == scpConnection) {
				connectServer();
			}

			sftpClient = new SFTPv3Client(scpConnection);
			// ls判断目录是否存在，若目录文件过多，效率较差
			// sftpClient.ls(dirName);

			sftpClient.mkdir(dirName, 0775);
		} catch (Exception e) {
			// 若目录存在，不作任何处理
		} finally {
			if (null != sftpClient) {
				sftpClient.close();
			}
		}
	}

	/**
	 * 关闭SCP连接
	 */
	public void closeServer() {
		try {
			scpConnection.close();
		} catch (Exception e) {
			LogCvt.error("SCP关闭连接失败! ", e);
		}
	}
}
