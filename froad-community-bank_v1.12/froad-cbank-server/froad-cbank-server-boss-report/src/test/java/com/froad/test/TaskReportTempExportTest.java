/**
 * Project Name:froad-cbank-server-boss-report
 * File Name:TaskReportTempExportTest.java
 * Package Name:com.froad.test
 * Date:2015年9月1日下午5:30:37
 * Copyright (c) 2015, F-Road, Inc.
 */
package com.froad.test;

import com.froad.po.BatchChunk;
import com.froad.timer.task.impl.TaskReportTempExport;

/**
 * ClassName: TaskReportTempExportTest <br/>
 * Reason: TODO ADD REASON. <br/>
 * Date: 2015年9月1日 下午5:30:37
 * 
 * @author zachary.wang
 * @version
 * @see
 */
public class TaskReportTempExportTest {

	public static void main(String[] args) {
		TaskReportTempExport rte = new TaskReportTempExport("test", null);
		rte.begin();
		rte.initialize();
		BatchChunk batchChunk = new BatchChunk();
		batchChunk.setBatchDate(20150902);
		batchChunk.setLastBatchDate(20150901);
		rte.processByChunk(batchChunk);
	}
}
