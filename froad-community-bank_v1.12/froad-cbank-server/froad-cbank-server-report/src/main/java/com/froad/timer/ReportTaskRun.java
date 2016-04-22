package com.froad.timer;

import java.io.File;

import com.froad.Constants;
import com.froad.logback.LogCvt;
import com.froad.timer.bean.TaskList;
import com.froad.timer.manager.TaskManager;
import com.froad.util.XStreamHandle;

public class ReportTaskRun implements Runnable {
	
	private TaskList taskList;
	
	/**
	 * 初始化
	 * @Title: init 
	 * @Description: 加载配置文件
	 * @author: froad-huangyihao 2015年4月24日
	 * @modify: froad-huangyihao 2015年4月24日
	 * @throws
	 */
	private void init(){
		String fileName = "task-config.xml";
		try {
			String path = System.getProperty(Constants.CONFIG_PATH) + File.separatorChar + fileName;
			File xml = new File(path);
			taskList = XStreamHandle.toBean(xml, TaskList.class);
			LogCvt.info("成功加载配置文件"+fileName);
		} catch (Exception e) {
			LogCvt.error("读取定时任务配置文件"+fileName+"异常", e);
		}
	}
	

	@Override
	public void run() {
		LogCvt.info("====== 定时任务开始启动 ======");
		init();
		
		try {
			TaskManager.load(taskList, false);
		} catch (Exception e) {
			TaskManager.shutdown();
			LogCvt.error("====== 定时任务启动异常 ======", e);
		}
	}
	
}
