package com.froad;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.froad.thrift.ServerRun;
import com.froad.timer.ReportTaskRun;

public class ReportMain {
	
	private static ExecutorService pool = Executors.newFixedThreadPool(5);
	
	public static void main(String[] args) {
		ServerRun thrift = new ServerRun();
		ReportTaskRun task = new ReportTaskRun();
		pool.execute(thrift);
		pool.execute(task);
	}
}
