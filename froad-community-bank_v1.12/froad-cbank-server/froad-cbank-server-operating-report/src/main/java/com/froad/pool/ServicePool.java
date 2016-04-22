package com.froad.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.froad.process.Process;


public class ServicePool {
	
	private static ExecutorService pool = Executors.newFixedThreadPool(50);
	

	public static void execute(Process process){
		pool.submit(process);
	}
	
	public static void close(){
		if(!pool.isShutdown()){
			pool.shutdown();
		}
	}
	
}

