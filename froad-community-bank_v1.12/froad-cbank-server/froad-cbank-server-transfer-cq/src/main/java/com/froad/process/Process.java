package com.froad.process;

import com.froad.config.ProcessServiceConfig;


/**
 *  转移处理主流程
  * @ClassName: Process
  * @Description: TODO
  * @author share 2015年4月29日
  * @modify share 2015年4月29日
 */
public interface Process extends Runnable{

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
	 *  程序执行前 to do
	  * @Title: before
	  * @Description: TODO
	  * @author: share 2015年6月25日
	  * @modify: share 2015年6月25日
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void before();
	
	/**
	 *  处理中
	  * @Title: process
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void process();
	
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
	 *  下一步
	  * @Title: next
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param @param config    
	  * @return void    
	  * @throws
	 */
	public void next();
	
	
}

