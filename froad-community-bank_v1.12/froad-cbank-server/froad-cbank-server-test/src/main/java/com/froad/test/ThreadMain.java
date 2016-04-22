package com.froad.test;

import java.util.concurrent.CountDownLatch;

public class ThreadMain {

	public static void main(String[] args) {

		for(int i = 0;; i++) {
			System.out.println("i= " + i);
			new Thread(new HoldThread()).start();
		}

	}
}

class HoldThread extends Thread {

	CountDownLatch cdl = new CountDownLatch(1);

	public HoldThread() {
		this.setDaemon(true);
	}

	public void run() {
		try
		{
			cdl.await();
		} catch
		(InterruptedException e) {

		}

	}

}
