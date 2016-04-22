package team.core.thread.common;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

import org.apache.log4j.Logger;

import team.core.beans.conveying.UtilConveyingMsg;
import team.core.constant.SysConfig;
import team.core.factory.CoreFactory;

public class UDPMain extends Thread{
	
	private static Logger log = Logger.getLogger(UDPMain.class);
	
	public void run() {
		log.info("UDP主线程启动完毕");
		byte[] buf = new byte[10240];
		try {
			DatagramSocket socket = new DatagramSocket(SysConfig.PORT);	
			DatagramPacket datagramPacket;
			while(true){
				datagramPacket = new DatagramPacket(buf, buf.length);
				socket.receive(datagramPacket);
				log.info("(DatagramSocket)收到 IP: " + datagramPacket.getAddress().getHostAddress() + " 端口: " + datagramPacket.getPort() + " 的通信信息");
				datagramPacket.setData(objectToBytes(CoreFactory.core((UtilConveyingMsg)bytesToObject(datagramPacket.getData()))));
				socket.send(datagramPacket);
			}
		} catch (SocketException e) {
			log.error("UDP通讯Scoket异常", e);
		} catch (IOException e) {
			log.error("UDP通讯IO异常", e);
		}		
	}
	
	private static Object bytesToObject(byte[] buffer) {  
        Object obj = null;  
        try {  
            ByteArrayInputStream buffers = new ByteArrayInputStream(buffer);  
            ObjectInputStream in = new ObjectInputStream(buffers);  
            obj = in.readObject();  
            in.close();  
        } catch (Exception e) {  
        	log.error("byte数据转成Object异常", e);
        }  
        return obj;  
    } 
	
	private static byte[] objectToBytes(UtilConveyingMsg utilConveyingMsg) {  
        ByteArrayOutputStream buffers = new ByteArrayOutputStream();  
        try {  
            ObjectOutputStream out = new ObjectOutputStream(buffers);  
            out.writeObject(utilConveyingMsg);  
            out.close();  
        } catch (Exception e) {
        	log.error("Object数据转成byte异常", e);
            return null;  
        }  
        return buffers.toByteArray();  
    }  
}
