package com.froad.timer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.froad.Constants;
import com.froad.logback.LogCvt;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.bean.TaskList;
import com.froad.timer.bean.TimerTaskConfig;
import com.froad.timer.manager.TaskManager;
import com.froad.util.AllkindsTimeConfig;
import com.froad.util.Checker;
import com.froad.util.PropertiesUtil;
import com.froad.util.XStreamHandle;

/**
 * 定时任务主线程
 * 打包后由java虚拟机调用启动
 * 
 * @author lf 2015.03.12
 * @modify lf 2015.03.12
 * 
 * */
public class TimerMain {

	private static TaskManager manager;

	private static TaskList taskList;

	public static void main(String[] args) {
		PropertiesUtil.load();

		LogCvt.info("====== 定时任务开始启动 ======");
		init();
		
		try {
			List<TaskBean> list = new ArrayList<TaskBean>();
			if (Checker.isNotEmpty(taskList)) {
				manager = TaskManager.getInstance();
				list = taskList.getList();
			} else {
				LogCvt.info("定时配置文件中无配置信息");
				return;
			}
			
			if (Checker.isNotEmpty(list)) {
				LogCvt.info("配置中共有" + list.size() + "个定时任务需要启动 \n");
				for (TaskBean bean : list) {
					LogCvt.info("读取定时任务 "+bean.getTaskName()+" 配置的任务内容");
					TimerTaskConfig config = new TimerTaskConfig(bean);
					LogCvt.info(bean.toString());
					manager.add(config);
				}
				manager.run();
				AllkindsTimeConfig.load();
			}else {
				LogCvt.info("需要启动的定时任务列表为空");
				manager.shutdown();
			}
		} catch (Exception e) {
			LogCvt.error("====== 定时任务启动异常 ======");
			manager.shutdown();
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	/**
	 * 初始化
	 * @Title: init 
	 * @Description: 加载配置文件
	 * @author: froad-huangyihao 2015年4月24日
	 * @modify: froad-huangyihao 2015年4月24日
	 * @throws
	 */
	private static void init() {
		String fileName = "task-config.xml";
		try {
			String path = System.getProperty(Constants.CONFIG_PATH) + File.separatorChar + fileName;
			File xml = new File(path);
			taskList = XStreamHandle.toBean(xml, TaskList.class);
			LogCvt.info("成功加载配置文件"+fileName);
		} catch (Exception e) {
			LogCvt.error("读取定时任务配置文件"+fileName+"异常");
			LogCvt.error(e.getMessage(), e);
		}
	}
}
