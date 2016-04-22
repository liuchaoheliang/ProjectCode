package dao;
import java.io.IOException;
import java.io.InterruptedIOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import org.apache.log4j.Logger;

import config.SysConfig;

import po.Response;
import po.User;
import po.UserInfo;

import util.SendRequest;
import util.String2Xml;


public class UserDao {
	private Logger logger=Logger.getLogger(UserDao.class);
	private SendRequest sendRequest=new SendRequest();
	
	public Response login(User user) throws IOException{	
		logger.info("用户登录");
		String2Xml root=new String2Xml("req");
		root.append("code", "0001");
		root.addElementName("val");
		root.append("uname", user.getName());
		root.append("psd", user.getPwd());
		String msg=root.toXMLString();
		return sendRequest.sendRequest(msg);		
	}
	
	
	public Response register(User user,UserInfo info) throws IOException{	
		logger.info("用户注册");
		String2Xml root=new String2Xml("req");
		root.append("code", "0002");
		root.addElementName("val");
		root.append("uname", user.getName());
		root.append("psd", user.getPwd());
		root.append("email", info.getMail());
		root.append("nickName", info.getNickname());
		root.append("sex", info.getSex());
		String msg=root.toXMLString();
		return sendRequest.sendRequest(msg);		
	}
}
