package team.core.init;

import team.core.thread.common.TCPChat;
import team.core.thread.common.UDPMain;


public class SystemInit {
	
	public void startUDP(){
		new UDPMain().start();
	}
	
	public void startTCP(){
		new TCPChat().start();//启动聊天系统
	}
}
