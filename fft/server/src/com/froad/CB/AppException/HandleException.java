package com.froad.CB.AppException;
import org.apache.log4j.Logger;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;



	/**
	 * 类描述：处理异常
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2011 
	 * @author: meicy meiwenxiang@f-road.com.cn
	 * @time: Sep 10, 2011 5:47:46 PM 
	 */
public class HandleException extends QuartzJobBean {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HandleException.class);
	/**
	  * 方法描述：通知异常处理
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: meicy meiwenxiang@f-road.com.cn
	  * @time: Sep 10, 2011 5:47:43 PM
	  */
	public void informException() throws AppException {
							
	}
	@Override
	protected void executeInternal(JobExecutionContext arg0)
			throws JobExecutionException {
		// TODO Auto-generated method stub

	}
	
	public static void main(String[] args) throws AppException {

	}
}
