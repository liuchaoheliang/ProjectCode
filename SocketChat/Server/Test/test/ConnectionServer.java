package test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;

import team.core.beans.conveying.FriendMsg;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;

public class ConnectionServer {

	public static void main(String[] args) throws IOException {
//		UserMsg u = new UserMsg();
//		u.setName("liuchao");
//		u.setPwd("E10ADC3949BA59ABBE56E057F20F883E");
//		System.out.println(conectionServer("0001", u).getObject());
		
		
		FriendMsg friendMsg = new FriendMsg(null);
		friendMsg.setUserId("a");
		friendMsg.setFriendId("bd");
		System.out.println(conectionServer("0004",friendMsg).getCode());
	}

	public static UtilConveyingMsg conectionServer(String code, Object data) {
		try {
			InetSocketAddress isa = new InetSocketAddress(InetAddress.getByName("localhost"), 13520);
			DatagramSocket socket = new DatagramSocket();
			UtilConveyingMsg utilConveyingMsg = new UtilConveyingMsg(code, data);
			byte[] buf = new byte[10240];
			DatagramPacket datagramPacket = new DatagramPacket(objectToBytes(utilConveyingMsg),objectToBytes(utilConveyingMsg).length, isa);
			DatagramPacket receivePacket = new DatagramPacket(buf, buf.length);
			socket.send(datagramPacket);
			socket.receive(receivePacket);
			socket.close();
			return (UtilConveyingMsg) bytesToObject(receivePacket.getData());
		} catch (SocketException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] objectToBytes(UtilConveyingMsg s) {
		ByteArrayOutputStream buffers = new ByteArrayOutputStream();
		try {
			ObjectOutputStream out = new ObjectOutputStream(buffers);
			out.writeObject(s);
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return buffers.toByteArray();
	}

	public static Object bytesToObject(byte[] buffer) {
		Object obj = null;
		try {
			ByteArrayInputStream buffers = new ByteArrayInputStream(buffer);
			ObjectInputStream in = new ObjectInputStream(buffers);
			obj = in.readObject();
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}
