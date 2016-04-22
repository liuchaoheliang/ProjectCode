package com.froad.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class Send {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		DatagramSocket ds = null; // 建立套间字udpsocket服务

		try {
			ds = new DatagramSocket(); // 
		} catch (SocketException e) {
			System.out.println("Cannot open port!");
			System.exit(1);
		}

		byte[] buf = "Hello, I am sender! 加一段中文".getBytes(); // 数据
		InetAddress destination = null;
		try {
			destination = InetAddress.getByName("127.0.0.1"); // 需要发送的地址
		} catch (UnknownHostException e) {
			System.out.println("Cannot open findhost!");
			System.exit(1);
		}
		DatagramPacket dp = new DatagramPacket(buf, buf.length, destination,
				10000);
		// 打包到DatagramPacket类型中（DatagramSocket的send()方法接受此类，注意10000是接受地址的端口，不同于自己的端口！）

		try {
			ds.send(dp); // 发送数据
		} catch (IOException e) {
		}
		ds.close();
		System.out.println("send end");
	}

}
