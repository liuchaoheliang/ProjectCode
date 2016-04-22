package com.froad.timer.task;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

import com.froad.TaskConstants;
import com.froad.logback.LogCvt;
import com.froad.util.AllkindsTimeConfig;
import com.froad.util.DateUtil;
import com.froad.util.PropertiesUtil;

/**
 * 定时删除验证码图片
 * @ClassName: TaskRemoveImage 
 * @Description: TODO
 * @author: huangyihao
 * @date: 2015年4月25日
 */
public class TaskRemoveImage implements Runnable {
	
	private Connection scpConnection = null;
	
	private final Map<String, String> scpInfoMap = PropertiesUtil.loadProperties(TaskConstants.SCP_FILE_NAME);
	
	private final String RM_COMMAND = "rm -rf ";
	
	private void connectServer(){
		String hostname = null;
		int port = 0;
		String userName = null;
		String password = null;
		boolean isAuthed = false;
		
		try {
			hostname = scpInfoMap.get(TaskConstants.SCP_HOST).toString();
			port = Integer.valueOf(scpInfoMap.get(TaskConstants.SCP_PORT));
			userName = scpInfoMap.get(TaskConstants.SCP_USERNAME).toString();
			password = scpInfoMap.get(TaskConstants.SCP_PASSWORD).toString();
			
			scpConnection = new Connection(hostname, port);
			scpConnection.connect();
			
			isAuthed = scpConnection.authenticateWithPassword(userName, password);
			
			if(!isAuthed){
				return;
			}
		} catch (Exception e){
			LogCvt.error("连接SCP异常");
			LogCvt.error(e.getMessage(), e);
			return;
		}
	}
	
	@Override
	public void run() {
		LogCvt.debug("定时任务: 移除验证码图片======开始======");
		Session session = null;
		try {
			if(scpConnection == null){
				connectServer();
			}
			
			try {
				session = scpConnection.openSession();
			} catch (Exception e) {
				LogCvt.error("session失效,重新连接", e);
				connectServer();
				session = scpConnection.openSession();
			}
			
			String dirName = scpInfoMap.get(TaskConstants.SCP_WORDIMAGER_DIR);
			// 获取昨天日期
//			String yesterday = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, DateUtils.addDays(new Date(), -1));
			// 获取配置的小时之前的时间
			String date = DateUtil.formatDateTime(DateUtil.DATE_TIME_FROMAT6, DateUtils.addHours(new Date(), -AllkindsTimeConfig.getRemoveImageAnHourBefore()));
			// 验证码图片文件夹位置 ./.../yyyy-MM-dd-HH
			String targetDir = dirName + date;
			String cmd = RM_COMMAND + targetDir;
			session.execCommand(cmd);
			LogCvt.debug("验证码图片文件夹[dir = "+targetDir+"]移除成功");
		} catch (Exception e) {
			LogCvt.error("定时任务: 移除验证码图片======系统异常======");
			LogCvt.error(e.getMessage(), e);
		} finally {
			if(session != null) session.close();
			if (scpConnection != null) scpConnection.close();
			LogCvt.debug("定时任务: 移除验证码图片======结束======");
		}
	}

}
