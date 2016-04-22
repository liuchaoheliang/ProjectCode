package com.froad.jetty;

public class JettyMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
//		ServerRun serverRun=new ServerRun(args[0],args[1],args[2]);
		ServerRun serverRun=new ServerRun();
		try {
			serverRun.run();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
