package com.froad.fft.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Ehcache;

import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

import com.froad.fft.bean.SystemConfig;
import com.froad.fft.common.ClientSiteUrlMapCommand;

/**
 * 系统
 * @author FQ
 *
 */
public final class SystemConfigUtil {
	
	final static Logger logger = Logger.getLogger(SystemConfigUtil.class);
	
	/** 
	 * CacheManager 
	 */
	private static final CacheManager cacheManager = CacheManager.create();
	
	/**
	 * 不可实例化
	 */
	private SystemConfigUtil(){
	}
	
	public static SystemConfig getSystemConfig(){
		
		Ehcache cache = cacheManager.getEhcache(SystemConfig.CACHE_NAME);
		net.sf.ehcache.Element cacheElement = cache.get(SystemConfig.CACHE_KEY);
		SystemConfig systemConfig;
		if (cacheElement != null) {
			systemConfig = (SystemConfig) cacheElement.getObjectValue();
		} else {
			systemConfig = new SystemConfig();
			try {
				File systemConfigXmlFile = new ClassPathResource(SystemConfig.CONFIG_FILE_NAME).getFile();
				Document document = new SAXReader().read(systemConfigXmlFile);
				
				systemConfig.setSystemName(document.selectSingleNode("/systemConfig/systemName").getText());
				systemConfig.setSystemVersion(document.selectSingleNode("/systemConfig/systemVersion").getText());
				systemConfig.setIsSystemDebug(Boolean.valueOf(document.selectSingleNode("/systemConfig/isSystemDebug").getText()));
				systemConfig.setIsLoginFailureLock(Boolean.valueOf(document.selectSingleNode("/systemConfig/isLoginFailureLock").getText()));
				systemConfig.setLoginFailureLockCount(Integer.valueOf(document.selectSingleNode("/systemConfig/loginFailureLockCount").getText()));
				systemConfig.setLoginFailureLockTime(Integer.valueOf(document.selectSingleNode("/systemConfig/loginFailureLockTime").getText()));
				systemConfig.setUploadMaxSize(Integer.valueOf(document.selectSingleNode("/systemConfig/uploadMaxSize").getText()));
				systemConfig.setAllowedUploadImageExtension(document.selectSingleNode("/systemConfig/allowedUploadImageExtension").getText());
				systemConfig.setAllowedUploadFileExtension(document.selectSingleNode("/systemConfig/allowedUploadFileExtension").getText());
				systemConfig.setCookiePath(document.selectSingleNode("/systemConfig/cookiePath").getText());
				systemConfig.setCookieDomain(document.selectSingleNode("/systemConfig/cookieDomain").getText());
				systemConfig.setFtpHost(document.selectSingleNode("/systemConfig/ftpHost").getText());
				systemConfig.setFtpPort(Integer.valueOf(document.selectSingleNode("/systemConfig/ftpPort").getText()));
				systemConfig.setFtpUsername(document.selectSingleNode("/systemConfig/ftpUsername").getText());
				systemConfig.setFtpPassword(document.selectSingleNode("/systemConfig/ftpPassword").getText());
				systemConfig.setFtpEncoding(document.selectSingleNode("/systemConfig/ftpEncoding").getText());
				systemConfig.setFtpDirectoryPath(document.selectSingleNode("/systemConfig/ftpDirectoryPath").getText());
				systemConfig.setFtpUrl(document.selectSingleNode("/systemConfig/ftpUrl").getText());
				
				systemConfig.setClientSiteUrl_243(ClientSiteUrlMapCommand.CLIENT_SITE_URL.get("243"));
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			cache.put(new net.sf.ehcache.Element(SystemConfig.CACHE_KEY, systemConfig));
		}
		return systemConfig;
	}
	
	/**
	 * 设置
	 * @param systemConfig
	 */
	public static boolean set(SystemConfig systemConfig) {
		boolean result = true;
		try {
			File systemConfigXmlFile = new ClassPathResource(SystemConfig.CONFIG_FILE_NAME).getFile();
			Document document = new SAXReader().read(systemConfigXmlFile);
			
			Element rootElement = document.getRootElement();
			Element systemConfigElement = rootElement.element("systemConfig");
			Node systemNameNode=document.selectSingleNode("/systemConfig/systemName");
			Node systemVersionNode=document.selectSingleNode("/systemConfig/systemVersion");
			Node isSystemDebugNode=document.selectSingleNode("/systemConfig/isSystemDebug");
			Node isLoginFailureLockNode = document.selectSingleNode("/systemConfig/isLoginFailureLock");
			Node loginFailureLockCountNode = document.selectSingleNode("/systemConfig/loginFailureLockCount");
			Node loginFailureLockTimeNode = document.selectSingleNode("/systemConfig/loginFailureLockTime");
			Node uploadMaxSizeNode = document.selectSingleNode("/systemConfig/uploadMaxSize");
			Node allowedUploadImageExtensionNode = document.selectSingleNode("/systemConfig/allowedUploadImageExtension");
			Node allowedUploadFileExtensionNode = document.selectSingleNode("/systemConfig/allowedUploadFileExtension");
			Node cookiePathNode = document.selectSingleNode("/systemConfig/cookiePath");
			Node cookieDomainNode = document.selectSingleNode("/systemConfig/cookieDomain");
			Node ftpHostNode = document.selectSingleNode("/systemConfig/ftpHost");
			Node ftpPortNode = document.selectSingleNode("/systemConfig/ftpPort");
			Node ftpUsernameNode = document.selectSingleNode("/systemConfig/ftpUsername");
			Node ftpPasswordNode = document.selectSingleNode("/systemConfig/ftpPassword");
			Node ftpEncodingNode = document.selectSingleNode("/systemConfig/ftpEncoding");
			Node ftpDirectoryPathNode = document.selectSingleNode("/systemConfig/ftpDirectoryPath");
			Node ftpUrlNode = document.selectSingleNode("/systemConfig/ftpUrl");
			
			if(systemNameNode == null){
				systemNameNode = systemConfigElement.addElement("systemName");
			}
			if(systemVersionNode == null){
				systemVersionNode = systemConfigElement.addElement("systemVersion");
			}
			if(isSystemDebugNode == null){
				isSystemDebugNode = systemConfigElement.addElement("isSystemDebug");
			}
			if(isLoginFailureLockNode == null){
				isLoginFailureLockNode = systemConfigElement.addElement("isLoginFailureLock");
			}
			if(loginFailureLockCountNode == null){
				loginFailureLockCountNode = systemConfigElement.addElement("loginFailureLockCount");
			}
			if(loginFailureLockTimeNode == null){
				loginFailureLockTimeNode = systemConfigElement.addElement("loginFailureLockTime");
			}
			if(uploadMaxSizeNode == null){
				uploadMaxSizeNode = systemConfigElement.addElement("uploadMaxSize");
			}
			if(allowedUploadImageExtensionNode == null){
				allowedUploadImageExtensionNode = systemConfigElement.addElement("allowedUploadImageExtension");
			}
			if(allowedUploadFileExtensionNode == null){
				allowedUploadFileExtensionNode = systemConfigElement.addElement("allowedUploadFileExtension");
			}
			if(cookiePathNode == null){
				cookiePathNode = systemConfigElement.addElement("cookiePath");
			}
			if(cookieDomainNode == null){
				cookieDomainNode = systemConfigElement.addElement("cookieDomain");
			}
			if(ftpHostNode == null){
				ftpHostNode = systemConfigElement.addElement("ftpHost");
			}
			if(ftpPortNode == null){
				ftpPortNode = systemConfigElement.addElement("ftpPort");
			}
			if(ftpUsernameNode == null){
				ftpUsernameNode = systemConfigElement.addElement("ftpUsername");
			}
			if(ftpPasswordNode == null){
				ftpPasswordNode = systemConfigElement.addElement("ftpPassword");
			}
			if(ftpEncodingNode == null){
				ftpEncodingNode = systemConfigElement.addElement("ftpEncoding");
			}
			if(ftpDirectoryPathNode == null){
				ftpDirectoryPathNode = systemConfigElement.addElement("ftpDirectoryPath");
			}
			if(ftpUrlNode == null){
				ftpUrlNode = systemConfigElement.addElement("ftpUrl");
			}
			//修改不为空的配置
			if(systemConfig.getSystemName()!=null)
			systemNameNode.setText(systemConfig.getSystemName());
			if(systemConfig.getSystemVersion()!=null)
			systemVersionNode.setText(systemConfig.getSystemVersion());
			if(systemConfig.getIsSystemDebug()!=null)
			isSystemDebugNode.setText(String.valueOf(systemConfig.getIsSystemDebug()));
			if(systemConfig.getIsLoginFailureLock()!=null)
			isLoginFailureLockNode.setText(String.valueOf(systemConfig.getIsLoginFailureLock()));
			if(systemConfig.getLoginFailureLockCount()!=null)
			loginFailureLockCountNode.setText(String.valueOf(systemConfig.getLoginFailureLockCount()));
			if(systemConfig.getLoginFailureLockTime()!=null)
			loginFailureLockTimeNode.setText(String.valueOf(systemConfig.getLoginFailureLockTime()));
			if(systemConfig.getUploadMaxSize()!=null)
			uploadMaxSizeNode.setText(String.valueOf(systemConfig.getUploadMaxSize()));
			if(systemConfig.getAllowedUploadImageExtension()!=null)
			allowedUploadImageExtensionNode.setText(systemConfig.getAllowedUploadImageExtension());
			if(systemConfig.getAllowedUploadFileExtension()!=null)
			allowedUploadFileExtensionNode.setText(systemConfig.getAllowedUploadFileExtension());
			if(systemConfig.getCookiePath()!=null)
			cookiePathNode.setText(systemConfig.getCookiePath());
			if(systemConfig.getCookieDomain()!=null)
			cookieDomainNode.setText(systemConfig.getCookieDomain());
			if(systemConfig.getFtpHost()!=null)
			ftpHostNode.setText(systemConfig.getFtpHost());
			if(systemConfig.getFtpPort()!=null)
			ftpPortNode.setText(String.valueOf(systemConfig.getFtpPort()));
			if(systemConfig.getFtpUsername()!=null)
			ftpUsernameNode.setText(systemConfig.getFtpUsername());
			if(systemConfig.getFtpPassword()!=null)
			ftpPasswordNode.setText(systemConfig.getFtpPassword());
			if(systemConfig.getFtpEncoding()!=null)
			ftpEncodingNode.setText(systemConfig.getFtpEncoding());
			if(systemConfig.getFtpDirectoryPath()!=null)
			ftpDirectoryPathNode.setText(systemConfig.getFtpDirectoryPath());
			if(systemConfig.getFtpUrl()!=null)
			ftpUrlNode.setText(systemConfig.getFtpUrl());
			
			FileOutputStream fileOutputStream = null;
			XMLWriter xmlWriter = null;
			try {
				OutputFormat outputFormat = OutputFormat.createPrettyPrint();//设置XML文档输出格式
				outputFormat.setEncoding("UTF-8");// 设置XML文档的编码类型
				outputFormat.setIndent(true);// 设置是否缩进
				outputFormat.setIndent("	");// 以TAB方式实现缩进
				outputFormat.setNewlines(true);// 设置是否换行
				fileOutputStream = new FileOutputStream(systemConfigXmlFile);
				xmlWriter = new XMLWriter(fileOutputStream, outputFormat);
				xmlWriter.write(document);
			} catch (Exception e) {
				e.printStackTrace();
				result = false;
			} finally {
				if (xmlWriter != null) {
					try {
						xmlWriter.close();
					} catch (IOException e) {
						result = false;
					}
				}
				IOUtils.closeQuietly(fileOutputStream);
			}

			Ehcache cache = cacheManager.getEhcache(systemConfig.CACHE_NAME);
			cache.put(new net.sf.ehcache.Element(systemConfig.CACHE_KEY, systemConfig));
		} catch (Exception e) {
			e.printStackTrace();
			result= false;
		}
		return result;
	}
}
