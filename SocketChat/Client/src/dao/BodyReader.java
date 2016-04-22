package dao;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import common.Session;
import config.RecodeMsg;


import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.FriendMsg;
import team.core.beans.conveying.UtilConveyingMsg;
import util.ChatUtil;
import view.ChatClient;
import view.ChatPrivate;
import view.MainBody;

public class BodyReader extends Thread {

	private MainBody body;	
	private ChatMsg chatMsg;
	private AudioClip sys,msg,goble;
	private ChatClient chatClient;
	public BodyReader(MainBody body1 ) {
		this.body = body1;
		this.chatClient=this.body.getChatClient();
	}


	@SuppressWarnings("unchecked")
	@Override
	public void run() {
//		String info;
		 chatMsg=new ChatMsg();
		ObjectInputStream ois=body.getOis();
		ObjectOutputStream oos=body.getOos();
		JList user_List=body.getUser_list();
		JList friend=body.getFriend_list();
		JList history=body.getHistory_list();
		String[] name;
		String[] userId;
		try {
			sys= Applet.newAudioClip(new URL("file:sound/system.wav"));
			msg= Applet.newAudioClip(new URL("file:sound/msg.wav"));
			goble= Applet.newAudioClip(new URL("file:sound/Global.wav"));
		} catch (MalformedURLException e1) {
			e1.printStackTrace();
		}
		while(body.isRunnable()){
			try {
				chatMsg=(ChatMsg) ois.readObject();    
				if("online".equals(chatMsg.getType()) || "outline".equals(chatMsg.getType())){
					goble.play();
					ChatUtil.dealFriendListinfo(body);
//					label.setText("系统提示   "+chatMsg.getMsg()+"");
				}else if("userlist".equals(chatMsg.getType())){
					name= ChatUtil.CastToNickNameArr(chatMsg);
					userId= ChatUtil.CastToUserIdArr(chatMsg);
					body.setUsers(name);
					body.setUsersId(userId);
					user_List.setListData(name);
					if(body.getCc().get(Session.userId)!=null){
						body.getCc().get(Session.userId).setUserIds(userId);						
						body.getCc().get(Session.userId).setNames(name);
						body.getCc().get(Session.userId).getjList().setListData(name);
						body.getCc().get(Session.userId).getJf().setTitle("聊天大厅 ("+Session.nickName+")-----在线人数："+name.length);
					}
				}else if("addfd".equals(chatMsg.getType())){
					sys.play();
					int yes= JOptionPane.showConfirmDialog(body.getTp(), chatMsg.getMsg(),"添加好友",JOptionPane.YES_NO_OPTION);
					String tosome=chatMsg.getToSome();
					chatMsg.setToSome(chatMsg.getUserId());
					chatMsg.setUserId(tosome);
					if(yes==JOptionPane.YES_OPTION){
						chatMsg.setType("agreenfd");
						body.getOos().writeObject(chatMsg);
					}else{
						chatMsg.setType("refusefd");
						body.getOos().writeObject(chatMsg);
					}
				}else if("refusefd".equals(chatMsg.getType()) || "agreenfd".equals(chatMsg.getType())){
					sys.play();
					if("refusefd".equals(chatMsg.getType())){
						JOptionPane.showMessageDialog(body, "对方拒绝添加您为好友");
					}else{
						FriendMsg msg=new FriendMsg(Session.userId);
						msg.setFriendId(chatMsg.getUserId());
//						msg.setUserId(Session.userId);
						UtilConveyingMsg response=ConnectionServer.requestServer("0003", msg);
						if("0007".equals(response.getCode())){
							JOptionPane.showMessageDialog(body, "添加好友成功");							
							chatMsg.setType("fdlist");
							oos.writeObject(chatMsg);
						}else{
							JOptionPane.showMessageDialog(body, "添加好友失败");
						}
					}
				}else if ("fdlist".equals(chatMsg.getType())){
					goble.play();
					ChatUtil.dealFriendListinfo(body);
					body.getFriend_list().setListData(body.getFriends());
				}else if( "all".equals(chatMsg.getToSome())){		
					msg.play(); 
					String info=chatMsg.getMsg()+"\n";
					if(!body.getCc().containsKey(Session.userId)){
						chatClient=new ChatClient(body);
						chatClient.CreateGui();
						body.getCc().put(Session.userId, chatClient);
					}
					chatClient=body.getCc().get(Session.userId);
					chatClient.getJta().append(info);
//					Message.InputMessage(userid, info);
					int num=chatClient.getJsp().getVerticalScrollBar().getMaximum();
					chatClient.getJsp().getVerticalScrollBar().setValue(num);
				}else if(!"all".equals(chatMsg.getToSome()) && !"".equals(chatMsg.getToSome())){
					msg.play(); 
					String userid=chatMsg.getToSome();
					body.creatChatPrivate(chatMsg.getNickName(),chatMsg.getToSome());
					ChatPrivate chatPrivate=body.getCp().get(userid);
					String info=chatMsg.getMsg()+"\n";
					chatPrivate.getChat_view().append(info);
					Message.InputMessage(userid, info);
					int num=chatPrivate.getJsp().getVerticalScrollBar().getMaximum();
					chatPrivate.getJsp().getVerticalScrollBar().setValue(num);
//					jta.append(chatMsg.getMsg()+"\n");
//					int num=jsp.getVerticalScrollBar().getMaximum();
//					jsp.getVerticalScrollBar().setValue(num);
				}
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "服务器连接中断");
				e.printStackTrace();
				body.shutDown();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
		}


	}
	
}
