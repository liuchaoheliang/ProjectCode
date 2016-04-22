/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:ExecuteTaskReq.java
 * Package Name:com.froad.po
 * Date:2015年10月16日下午4:18:52
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.po;

/**
 * ClassName:ExecuteTaskReq Reason: TODO ADD REASON. Date: 2015年10月16日 下午4:18:52
 * 
 * @author vania
 * @version
 * @see
 */
public class ExecuteTaskRes {
	private Result result; // optional
	private String taskId; // optional

	public Result getResult() {
		return result;
	}

	public void setResult(Result result) {
		this.result = result;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

}
