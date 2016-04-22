package dao;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.JList;
import javax.swing.JOptionPane;

import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;
import view.ChatClient;
import view.UserInfo;

import common.Session;

public class ChatListener implements MouseListener,ActionListener {
	
private ChatClient client;	

	public ChatListener(ChatClient client) {
		this.client = client;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if(e.getModifiers()==MouseEvent.BUTTON3_MASK&&e.getSource()==client.getJta()){
			client.getJpm().show(client.getJta(), e.getX(), e.getY());		
		}
		if(e.getModifiers()==MouseEvent.BUTTON3_MASK&&e.getSource()==client.getJta_1()){
			client.getJpm().show(client.getJta_1(), e.getX(), e.getY());
		}
		if(e.getSource()==client.getjList()){
			JList current = client.getjList();
			if(e.getModifiers()==MouseEvent.BUTTON3_MASK){
				if(current.getSelectedIndex()!=-1){
					int index=current.locationToIndex(new Point(e.getX(), e.getY()));
					current.setSelectedIndex(index);
					client.getJpm_1().show(client.getjList(), e.getX(), e.getY());
				}
			}else if(e.getClickCount()==2){
				String chatUserId=client.getUserIds()[current.getSelectedIndex()];
				String chatNickName=(String) current.getSelectedValue();
				if(Session.userId.equals(chatUserId)){
					JOptionPane.showMessageDialog(client.getJpl(), "不能够与自己发起会话");
				}else{
					client.getBody().creatChatPrivate(chatNickName, chatUserId);
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
		JList current = client.getjList();

		String chatNickName=(String) current.getSelectedValue();
				if(e.getActionCommand()=="复制"){
					client.getJta_1().copy();
					client.getJta().copy();
				}
				if(e.getActionCommand()=="剪切"){
					client.getJta_1().cut();
				}
				if(e.getActionCommand()=="粘贴"){
					client.getJta_1().paste();					
				}
				if(e.getActionCommand()=="清空"){
					client.getJta_1().setText("");
					client.getJta().setText("");					
				}
				if(e.getActionCommand()=="发送消息"){
					String chatUserId=client.getUserIds()[current.getSelectedIndex()];
					if(Session.userId.equals(chatUserId)){
						JOptionPane.showMessageDialog(client.getJpl(), "不能够与自己发起会话");
					}else{
						client.getBody().creatChatPrivate(chatNickName, chatUserId);
					}
				}
				if(e.getActionCommand()=="查看资料"){
					String UserId=client.getUserIds()[current.getSelectedIndex()];
					UserMsg userMsg=new UserMsg();
					userMsg.setUserId(UserId);
					UtilConveyingMsg response=ConnectionServer.requestServer("0005", userMsg);
					if(response!=null && "200".equals(response.getCode())){
						UserMsg msg=(UserMsg) response.getObject();
						new UserInfo(msg,client.getBody());
					}else{
						JOptionPane.showMessageDialog(client.getJpl(), "网络异常，稍后再试");
					}
				}
				if(e.getActionCommand()=="加为好友"){
					String chatUserId=client.getUserIds()[current.getSelectedIndex()];
					if(Session.userId.equals(chatUserId)){
						JOptionPane.showMessageDialog(client.getBody().getTp(), "不能够添加自己为好友");
					}else{
						ChatMsg chatMsg=new ChatMsg("addfd","Chat 用户 "+Session.nickName+" 请求加您为好友",chatUserId,Session.userId,Session.nickName);
						try {
							client.getBody().getOos().writeObject(chatMsg);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}	
	}
	
}
