package dao;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.FriendMsg;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;
import util.ChatUtil;
import view.ChatClient;
import view.MainBody;
import view.UserInfo;

import common.Session;

public class BodyListener implements MouseListener,ActionListener,WindowListener {
	
private MainBody body;	
private ChatClient chatClient;
	public BodyListener( MainBody body1) {
		this.body = body1;
		this.chatClient=body1.getChatClient();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//用户面板
		if(e.getSource()==body.getUser_list()){
			JList current = body.getUser_list();
			if(e.getModifiers()==MouseEvent.BUTTON3_MASK){
				if(current.getSelectedIndex()!=-1){
					int index=current.locationToIndex(new Point(e.getX(), e.getY()));
					current.setSelectedIndex(index);
					body.getJpm_1().show(body.getUser_list(), e.getX(), e.getY());
				}
			}else if(e.getClickCount()==2){
				String chatUserId=body.getUsersId()[current.getSelectedIndex()];
				if(Session.userId.equals(chatUserId)){
					JOptionPane.showMessageDialog(body.getTp(), "不能够与自己发起会话");
				}else{
					body.creatChatPrivate((String) current.getSelectedValue(),chatUserId);
				}
			}else if(e.getClickCount()==1 && e.getModifiers()==MouseEvent.BUTTON1_MASK){
			     int index =current.getSelectedIndex();
			     current.clearSelection();
			     current.setSelectedIndex(index);
			}
		}
		//好友面板
		if(e.getSource()==body.getFriend_list()){
			JList current = body.getFriend_list();
			if(e.getModifiers()==MouseEvent.BUTTON3_MASK){
				if(current.getSelectedIndex()!=-1){
					int index=current.locationToIndex(new Point(e.getX(), e.getY()));
					current.setSelectedIndex(index);
					body.getJpm().show(body.getFriend_list(), e.getX(), e.getY());
				}
			}else if(e.getClickCount()==2){
				String chatUserId=body.getFriendsId()[current.getSelectedIndex()];
				if("N".equals(body.getIsOnLine().get(chatUserId))){
					JOptionPane.showMessageDialog(body, "好友不在线，不能够发送消息");
				}else{
					body.creatChatPrivate((String) current.getSelectedValue(),chatUserId);					
				}
			}else if(e.getClickCount()==1 && e.getModifiers()==MouseEvent.BUTTON1_MASK){
			     int index =current.getSelectedIndex();
			     current.clearSelection();
			     current.setSelectedIndex(index);
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e) {

	}

	@Override
	public void mouseReleased(MouseEvent e) {

	}

	@Override
	public void mouseEntered(MouseEvent e) {

	}

	@Override
	public void mouseExited(MouseEvent e) {

	}


	@Override
	public void actionPerformed(ActionEvent e) {
		JList current = body.getUser_list();
		JList current_f = body.getFriend_list();
		//在线用户面板
		if(e.getActionCommand()=="发送消息"){
			String chatUserId=body.getUsersId()[current.getSelectedIndex()];
			if(Session.userId.equals(chatUserId)){
				JOptionPane.showMessageDialog(body.getTp(), "不能够与自己发起会话");
			}else{
				body.creatChatPrivate((String) current.getSelectedValue(),chatUserId);	
			}
		}

		if(e.getActionCommand()=="加为好友"){
			String chatUserId=body.getUsersId()[current.getSelectedIndex()];

			if(Session.userId.equals(chatUserId)){
				JOptionPane.showMessageDialog(body.getTp(), "不能够添加自己为好友");
			}else if(!(ChatUtil.getIndex(body.getFriendsId(), chatUserId)==-1)){
				JOptionPane.showMessageDialog(body.getTp(), "对方已是您的好友");
			}else{
				ChatMsg chatMsg=new ChatMsg("addfd","Chat 用户 "+Session.nickName+" 请求加您为好友",chatUserId,Session.userId,Session.nickName);
				try {
					body.getOos().writeObject(chatMsg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}	
		if(e.getActionCommand()=="查看资料"){
			String UserId=body.getUsersId()[current.getSelectedIndex()];
			UserMsg userMsg=new UserMsg();
			userMsg.setUserId(UserId);
			UtilConveyingMsg response=ConnectionServer.requestServer("0005", userMsg);
			if(response!=null && "200".equals(response.getCode())){
				UserMsg msg=(UserMsg) response.getObject();
				new UserInfo(msg ,body);
			}else{
				JOptionPane.showMessageDialog(body.getTp(), "网络异常，稍后再试");
			}
		}
		if(e.getActionCommand()=="进入聊天室"){
			if(body.getCc().get(Session.userId)==null){
				 chatClient=new ChatClient(body);
				 chatClient.CreateGui();
				 chatClient.getJta_1().requestFocus();
				 body.getCc().put(Session.userId, chatClient);
			}else{
				body.getCc().get(Session.userId).getJf().setExtendedState(JFrame.NORMAL);
			}
		}
		
		//好友面板
		if(e.getActionCommand()=="删除好友"){	
			String friendId=body.getFriendsId()[current_f.getSelectedIndex()];
			FriendMsg friendMsg=new FriendMsg(Session.userId);
			friendMsg.setFriendId(friendId);
			UtilConveyingMsg response=ConnectionServer.requestServer("0004", friendMsg);
			if("0009".equals(response.getCode())){
				JOptionPane.showMessageDialog(body, "删除好友成功");
				ChatMsg chatMsg=new ChatMsg(Session.userId);				
				chatMsg.setType("fdlist");
				chatMsg.setToSome(friendId);
				try {
					body.getOos().writeObject(chatMsg);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}else{
				JOptionPane.showMessageDialog(body, "连接出错，删除好友失败");
			}
		}
		
		if(e.getActionCommand()=="与他聊天" ){
			String friendId=body.getFriendsId()[current_f.getSelectedIndex()];
			if("N".equals(body.getIsOnLine().get(friendId))){
				JOptionPane.showMessageDialog(body, "好友不在线，不能够发送消息");
			}else{
				body.creatChatPrivate((String) current_f.getSelectedValue(),friendId);					
			}
		}
		if(e.getActionCommand()=="查看消息"){
			String friendId=body.getFriendsId()[current_f.getSelectedIndex()];
			Message.ViewMessage(friendId);
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
		
	}

	@Override
	public void windowIconified(WindowEvent e) {
//		body.setVisible(false);
//		body.hide();
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
//		body.setVisible(false);
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
		
	}
	
}
