/**
 * Project Name:froad-cbank-server-fallow
 * File Name:QueryTaskRes.java
 * Package Name:com.froad.po
 * Date:2015年10月22日上午11:29:36
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.po;

import java.util.List;


/**
 * ClassName:QueryTaskRes
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月22日 上午11:29:36
 * @author   wm
 * @version  
 * @see 	 
 */
public class QueryTaskRes {
	
	public Result result; 
	public List<QueryTaskListRes> taskListRes;
	
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<QueryTaskListRes> getTaskListRes() {
		return taskListRes;
	}
	public void setTaskListRes(List<QueryTaskListRes> taskListRes) {
		this.taskListRes = taskListRes;
	}
	
}
