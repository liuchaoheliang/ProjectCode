package util;

import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import org.apache.log4j.Logger;

import po.Response;
import config.SysConfig;

public class SendRequest {
	
	private static final int TIMEOUT = 3000;//超时时间(s)
	
	private static final int MAXTRIESTIMES = 5;//重发消息次数
	
	private Logger logger=Logger.getLogger(SendRequest.class);
	
	
	//发送请求
	public  Response sendRequest(String msg) throws IOException{
		
		String [] args = new String[]{SysConfig.IP,msg,SysConfig.PORT};
		
		if ((args.length < 2) || (args.length > 3)) { 
			throw new IllegalArgumentException("请传入正确的参数: 地址 内容 端口");			
		}
		
		InetAddress serverAddress = InetAddress.getByName(args[0]);
		
		byte[] bytesToSend = args[1].getBytes();
		
		int servPort = (args.length == 3) ? Integer.parseInt(args[2]) : 7;
		
		DatagramSocket socket = new DatagramSocket();
		
		socket.setSoTimeout(TIMEOUT);
		
		DatagramPacket sendPacket = new DatagramPacket(bytesToSend,bytesToSend.length, serverAddress, servPort);
		DatagramPacket receivePacket = new DatagramPacket(new byte[2555], 2555);
		int triesTimes = 0;
		boolean receivedResponse = false;
		
		do{
			socket.send(sendPacket);
			try { 
				socket.receive(receivePacket);
				if (!receivePacket.getAddress().equals(serverAddress)) {
					
					throw new IOException("接收到的数据包来自于未知源");
				}
				receivedResponse = true;
			}catch (InterruptedIOException e){
				e.printStackTrace();
				triesTimes += 1;
				logger.info("超时, " + "剩余尝试次数" + (MAXTRIESTIMES - triesTimes));
			}
		} 
		while(((!receivedResponse) && (triesTimes < MAXTRIESTIMES)));
		socket.close();
		if (receivedResponse) { 
			int dataLength = receivePacket.getLength();
			byte[] tempData = new byte[dataLength];
			byte[] data = receivePacket.getData();
			for(int i = 0 ; i < dataLength ; i ++){
				tempData[i] = data[i];
			}
			String message=new String(tempData);
			logger.info("获取到返回数据: " + message);
			
			return ServerXMLAnalysis.analysisXML(message);
		}else{
			logger.info("无法获取返回数据...");
			return null;
		}
	}

}
