package team.core.thread.common;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;


import org.apache.log4j.Logger;

import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.FriendMsg;
import team.core.beans.conveying.UserMsg;
import team.core.constant.Session;
import team.core.constant.SysConfig;
import team.core.fun.users.AboutFriend;

/**
 *<p>聊天系统</p> 
 */
public class TCPChat extends Thread {

	private static Logger log = Logger.getLogger(TCPChat.class);	
	private ServerSocket serverSocket;
	private Socket socket;
	
	private static Map<String, ChatSender> chatSenders = new HashMap<String, ChatSender>();	
	private static Map<String, ObjectOutputStream> objectOss = new HashMap<String, ObjectOutputStream>();
	
	public void run(){		
		log.info("TCP聊天系统启动完毕");
		try {
			serverSocket = new ServerSocket(SysConfig.PORT);
		} catch (IOException e) {
			log.error("创建TCP ServerSocket IO异常", e);
		}			
		while(true){
			try {
				socket = serverSocket.accept();
				log.info("(ServerSocket)收到 IP: " + socket.getInetAddress().toString().replace("/", "") + " 端口: " + socket.getPort() + " 的通信信息");			
		
				ObjectInputStream objectIs = new ObjectInputStream(socket.getInputStream());
				
				ChatMsg chatMsg = (ChatMsg)objectIs.readObject();
				
				String userId = chatMsg.getUserId();
				String nickName = chatMsg.getNickName();
				
				Session.saveUser(userId);//在线用户更新		
				sendUserOnline(nickName,userId);//向在线用户发送上线通知和向自己在线好友发送更新好友列表通知
				
				ChatSender chatSender = new ChatSender(userId,socket,objectIs);
				
				log.info("userId:" + userId + "对应聊天系统线程已开启,当前聊天线程堆具有:" + (chatSenders.size()+1) + "个线程");
				
				chatSender.start();				
						
				chatSenders.put(userId, chatSender);//将该线程添加都线程堆
				
				ObjectOutputStream objectOs = new ObjectOutputStream(socket.getOutputStream());
				objectOs.writeObject(new ChatMsg(null, getOnlineUsers()));
				objectOs.flush(); //获取当前用户列表				
				
				objectOss.put(userId, objectOs);//将该ObjectOutputStream流保存到流栈
				
				chatSender = null;
				userId = null;
				chatMsg = null;
				nickName = null;
				objectOs = null;
				
			} catch (IOException e) {
				log.error("创建TCP聊天系统IO异常", e);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 *<p>客户端线程</p>
	 */
	class ChatSender extends Thread {		
		String userId;
		private ObjectInputStream objectIs;
		private Socket socket;
		int errorTimes = 0;
		public ChatSender(String userid,Socket socket,ObjectInputStream objectIs) {
			this.userId = userid;
			this.socket = socket;
			this.objectIs = objectIs;
		}
		public Socket getSocket(){
			return this.socket;
		}
		
		@SuppressWarnings("deprecation")
		@Override
		public void run() {			
			while (true) {
				try {
					replyDataToClient((ChatMsg)objectIs.readObject(),userId);
				} catch (IOException e) {
					log.error("聊天系统获取信息异常", e);
					errorTimes ++;
					if(errorTimes == 5){
						objectOss.remove(userId);
						try {
							socket.close();
						} catch (IOException e1) {
							log.error("关闭Socket发生IO异常", e1);
						}
						chatSenders.remove(userId);
						objectOss.remove(userId);
						
						Session.removeUser(userId);
						log.info("由于客户端异常关闭,服务端关闭该用户的聊天线程,移除对应的流;线程堆数量为:" + chatSenders.size() + ",ObjectOutputStream流栈数量为:" + objectOss.size());
						currentThread().stop();
					}
				} catch (ClassNotFoundException e) {
					log.error("userId:" + this.userId + "线程异常", e);
				}
			}
		}
	}
	
	/**
	 *<p>发送聊天信息</p> 
	 */
	private void replyDataToClient(ChatMsg chatMsg,String userId){
		String toSomeOne = chatMsg.getToSome();
		ObjectOutputStream temp;
		String type = chatMsg.getType();
		ChatMsg resChatMsg = new ChatMsg();
		if("outline".equals(type)){//发送下线通知
			sendUserOutline(userId,chatMsg.getNickName());
			return;
		}else if("addfd".equals(type) || "refusefd".equals(type) || "agreenfd".equals(type) || "fdlist".equals(type)){//添加好友相关通知
			temp = objectOss.get(toSomeOne);
			try {
				temp.writeObject(chatMsg);
				temp.flush();
				if("fdlist".equals(type)){
					temp = objectOss.get(chatMsg.getUserId());
					temp.writeObject(chatMsg);
					temp.flush();
				}
			} catch (IOException e) {
				log.error("发送添加好友相关通知异常 Msg:" + chatMsg.getMsg(), e);
			}
			return;
		}
		
		if("all".equals(toSomeOne)){//发送给每个人
			for(Entry<String, ObjectOutputStream> objectOs : objectOss.entrySet()){
				temp = objectOs.getValue();
				resChatMsg = new ChatMsg("chat",chatMsg.getMsg(),null);
				resChatMsg.setToSome("all");
				try {
					temp.writeObject(resChatMsg);
					temp.flush();
				} catch (IOException e) {
					log.error("发送聊天信息失败", e);
				}				
			}
			
		}else{	//私聊
			
			resChatMsg.setToSome(toSomeOne);
			resChatMsg.setType("chat");
			resChatMsg.setMsg(chatMsg.getMsg());
			resChatMsg.setUserId(chatMsg.getUserId());
			
			try {
				
				//向自己发送私聊信息
				temp = objectOss.get(chatMsg.getUserId());
				temp.writeObject(resChatMsg);
				temp.flush();
				
				resChatMsg.setToSome(chatMsg.getUserId());
				resChatMsg.setUserId(chatMsg.getToSome());
				resChatMsg.setNickName(chatMsg.getNickName());
			
				//向私聊对象发送私聊信息
				temp = objectOss.get(toSomeOne);
				
				if(temp != null){
					temp.writeObject(resChatMsg);
					temp.flush();
				}				
				
			} catch (IOException e) {
				log.error("发送聊天信息失败", e);
			}
		}		
		resChatMsg = null;
		temp = null;
		type = null;
		toSomeOne = null;
	}
	
	/**
	 *<p>向在线用户发送新用户上线相关信息<p>
	 */
	private void sendUserOnline(String nickName,String userId){
//		log.info("向所有在线用户发送nickName:" + nickName + "的上线通知并更新在线列表");
		ObjectOutputStream temp;
		List<UserMsg> userList = getOnlineUsers();
		for (Map.Entry<String, ObjectOutputStream> objectOs : objectOss.entrySet()) {
			temp = objectOs.getValue();			
			try {
//				temp.writeObject(new ChatMsg("online", nickName + "\t"+"上线了", null));
//				temp.flush();
				
				temp.writeObject(new ChatMsg("userlist", userList));//发送在线列表
				temp.flush();
			} catch (IOException e) {
				log.error("向在线用户发送通知出现IO异常", e);
			}			
		}
		
		
		//获取该用户的好友列表
		@SuppressWarnings("unchecked")
		List<UserMsg> friendList = (List<UserMsg>) (new AboutFriend().getFriendList((Object)new FriendMsg(Session.getUserMsgByUserIdForFdList(userId).getUserId()))).getObject();
		for (UserMsg userMsg : friendList) {
			if("Y".equals(userMsg.getIsOnline())){
				temp = objectOss.get(userMsg.getUserId());
				if(temp != null){
					try {
						temp.writeObject(new ChatMsg("fdlist", null,null,null));
						temp.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		temp = null;
	}
	
	/**
	 *<p>发送下线通知</p> 
	 */
	@SuppressWarnings("deprecation")
	private void sendUserOutline(String userId,String nickName){
		//log.info("向所有在线用户发送nickName:" + nickName + "的下线通知");
		
		Session.removeUser(userId);
		
		ObjectOutputStream temp;
		List<UserMsg> userList = getOnlineUsers();
		for (Entry<String, ObjectOutputStream> objectOs : objectOss.entrySet()) {
			temp = objectOs.getValue();
			try {
//				temp.writeObject(new ChatMsg("outline" , nickName + "\t"+"下线了", null));
//				temp.flush();
				
				temp.writeObject(new ChatMsg("userlist", userList));//发送在线列表
				temp.flush();
			} catch (IOException e) {
				log.error("向在线用户发送通知出现IO异常", e);
			}			
		}
		
		
		
		//获取该用户的好友列表
		@SuppressWarnings("unchecked")
		List<UserMsg> friendList = (List<UserMsg>) (new AboutFriend().getFriendList((Object)new FriendMsg(Session.getUserMsgByUserIdForFdList(userId).getUserId()))).getObject();
		for (UserMsg userMsg : friendList) {
			if("Y".equals(userMsg.getIsOnline())){
				temp = objectOss.get(userMsg.getUserId());
				if(temp != null){
					try {
						temp.writeObject(new ChatMsg("fdlist", null,null,null));
						temp.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		temp = null;
		ChatSender chatSender = chatSenders.get(userId);
		try {
			chatSender.getSocket().close();
			log.info("userId:" + userId + " 通讯socket已关闭");
		} catch (IOException e) {
			log.error("关闭Socket发生IO异常", e);
		}
		objectOss.remove(userId);
		chatSenders.remove(userId);	
		log.info("userId:" + userId + " 聊天系统线程已关闭,当前聊天系统线程堆有:" + chatSenders.size() + "个线程" + ",ObjectOutputStream流栈数量为:" + objectOss.size());
		chatSender.stop();
	}
	
	
	private static List<UserMsg> getOnlineUsers(){
		List<UserMsg> onlineusers = new ArrayList<UserMsg>();
		for (Map.Entry<String, UserMsg> onlineUser : Session.getOnLineUsers().entrySet()) {
			onlineusers.add(onlineUser.getValue());
		}
		return onlineusers;
	}
}
