package com.froad.test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TestMain {

	//增加测试
	public static void main(String[] args) throws InterruptedException {  
		
		//System.out.println(Runtime.getRuntime().availableProcessors());
		
        ThreadPoolExecutor pool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.SECONDS,   
                new ArrayBlockingQueue<Runnable>(1));//设置线程池只启动一个线程 阻塞队列一个元素  
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());  
        //设置策略为挤掉最旧的  
        for (int i = 0; i < 10; i++) {  
            final int  j = i;  
            pool.submit(new Runnable() {  
                public void run() {  
                    System.out.println("线程:"+j + Thread.currentThread().getName()+"  开始执行");  
                    try {  
                        Thread.sleep(1000L);  
                    } catch (Exception e) {  
                        e.printStackTrace();  
                    }  
                    System.out.println("线程:"+j + Thread.currentThread().getName()+"  执行完毕");  
                }  
            });  
        }  
        Thread.sleep(5000L);  
        pool.shutdown();  
        System.out.println("关闭后线程终止了吗？" + pool.isTerminated());  
    }  
  

}
