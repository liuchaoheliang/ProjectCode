package com.froad.timer.task;

import com.froad.logback.LogCvt;
import com.froad.timer.bean.TaskBean;

public abstract class AbstractPreTask extends AbstractTask {
	
	protected String name;
	
	protected TaskBean task;
	
	public AbstractPreTask(String name, TaskBean task) {
		super(name, task);
	}
	
	@Override
	public void run() {
		long begtime = System.currentTimeMillis();
		boolean flag = true;
		try {
			begin();
			
			initialize();
			
			task();
		} catch (Exception e) {
			flag = false;
			exception(e);
		} finally{
			end();
			LogCvt.info("定时任务["+name+"]结束,耗时:"+(System.currentTimeMillis()-begtime)+"毫秒");
			if(flag) {
				next();
			}
		}
	}
	
}
