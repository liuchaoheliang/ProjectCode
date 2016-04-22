package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import common.Session;

import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.UserMsg;

public class UserInfo extends JFrame {
	private JFrame jf;
	private JLabel head_img,acount,name,sex,email;
	private JTextField text_acount,text_name,text_sex,text_eamil;
	private MainBody mainBody;
	private UserMsg userMsg;
	public UserInfo(UserMsg userMsg1,MainBody body){
		this.userMsg=userMsg1;
		this.mainBody=body;
		 try {     // windows风格界面
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			 } catch (Exception e) {
			  e.printStackTrace();
		}
		jf=new JFrame();
		String uname=userMsg.getNickname() == null?"":userMsg.getNickname() ;
		String uemail=userMsg.getMail()==null?"":userMsg.getMail();
		String usex="男";
		if(userMsg!=null&&"1".equals(userMsg.getSex())){
			usex="女";
		}
		final String uacount=userMsg.getName()==null?"":userMsg.getName();
		jf.setTitle(uname+ "的资料");
		jf.setIconImage(new ImageIcon("images/logo.png").getImage());
		jf.setLayout(null);
		jf.getContentPane().setBackground(Color.WHITE);
		head_img=new JLabel(new ImageIcon("images/head.jpg"));
		head_img.setBounds(20, 40, 128, 134);
		
		acount=new JLabel("帐 号："+uacount);
		acount.setBounds(170, 40, 200, 25);
		
		name=new JLabel("昵  称: "+uname);
		name.setBounds(170, 70, 200, 25); 
		
		sex=new JLabel("性  别: "+usex);
		sex.setBounds(170, 100, 200, 25);
		
		email=new JLabel("邮  箱: "+uemail);
		email.setBounds(170, 130, 200, 25);

		JButton add_friend=new JButton("加为好友");
		add_friend.setBounds(170, 170, 110, 25);
		add_friend.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ChatMsg chatMsg=new ChatMsg("addfd","Chat 用户 "+Session.nickName+" 请求加您为好友",userMsg.getUserId(),Session.userId,Session.nickName);
				try {
					mainBody.getOos().writeObject(chatMsg);
					jf.setVisible(false);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		
		jf.add(head_img);
		jf.add(acount);
		jf.add(name);
		jf.add(sex);
		jf.add(email);
		if(!Session.userId.equals(userMsg.getUserId())){
			jf.add(add_friend);
		}
		
		
		jf.setSize(400, 250);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		jf.setLocationRelativeTo(jf);
		jf.setVisible(true);
	}
	
	
	
//	public static void main(String[] args) {
//		UserMsg userMsg=new UserMsg();
//		userMsg.setName("liuchao");
//		userMsg.setMail("liuchaoheliang@qq.com");
//		userMsg.setSex("女");
//		userMsg.setNickname("痴心绝对");
//		new UserInfo(userMsg,null);
//	}
}
