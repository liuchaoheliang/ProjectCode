package com.froad.timer.task;

public interface ITask extends Runnable {
	
	/**
	 *  开始
	  * @Title: begin
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void begin();
	
	/**
	 *  定时任务
	  * @Title: task
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void task();
	
	/**
	 *  结束
	  * @Title: end
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void end();
	
	/**
	 *  异常处理情况
	  * @Title: exception
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void exception(Exception e);
	
	/**
	 * 启动下一个任务
	 * @Title: next 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年6月11日
	 * @modify: froad-huangyihao 2015年6月11日
	 * @throws
	 */
	public void next();
}
