package view;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import common.Session;
import dao.Message;

import team.core.beans.conveying.ChatMsg;


	/**
	 * 类描述：1vs1私聊界面
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2014年10月21日 下午12:44:43 
	 */
public class ChatPrivate extends JFrame {
	private JTextArea chat_view,send_view;
	private MainBody body;
	private JButton send,close,view,delete;
	private ChatMsg chatMsg;
	private String chatNickName;
	private String chatUserId;
	private JScrollPane jsp;
	
	public SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	private WindowClosing closing=new WindowClosing(client);
	
	
	
	public ChatPrivate(String chatNickName1,String chatUserId1,MainBody body1){
		this.body=body1;
		this.chatNickName=chatNickName1;
		this.chatUserId=chatUserId1;
		
		 try {     // windows风格界面
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			 } catch (Exception e) {
			  e.printStackTrace();
		}
		this.setTitle("与  "+chatNickName+"  私聊中");
		this.setIconImage(new ImageIcon("images/logo.png").getImage());
		this.setLayout(null);
		
		//鼠标快捷菜单
//		jpm=new JPopupMenu();		
//		jpm.setSize(70, 120);
//		JMenuItem popu1=new JMenuItem("复制"); 
//		popu1.addActionListener(mouselistener);
//		jpm.add(popu1);
//		jpm.addSeparator();
//		JMenuItem popu2=new JMenuItem("剪切"); 
//		popu2.addActionListener(mouselistener);
//		jpm.add(popu2);
//		jpm.addSeparator();
//		JMenuItem popu3=new JMenuItem("粘贴"); 
//		popu3.addActionListener(mouselistener);
//		jpm.add(popu3);
//		jpm.addSeparator();
//		JMenuItem popu4=new JMenuItem("清空"); 
//		popu4.addActionListener(mouselistener);
//		jpm.add(popu4);	
		
		
		
		//聊天信息窗口
		chat_view=new JTextArea();
		chat_view.setLineWrap(true);
		chat_view.setAutoscrolls(true);
		chat_view.setEditable(false);
//		chat_view.addMouseListener();
		jsp=new JScrollPane(chat_view);
		jsp.setBounds(5, 10,350,280);		
		this.add(jsp);
		
		view =new JButton("聊天记录");
		view.setBounds(185, 290, 80, 20);
		view.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Message.ViewMessage(chatUserId);
			}
		});
		delete=new JButton("删除记录");
		delete.setBounds(275, 290, 80, 20);
		delete.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				Message.DeleteMessage(chatUserId);
			}
		});
		
		this.add(view);
		this.add(delete);
		
		
		//发送文本窗口
		send_view=new JTextArea();
		JScrollPane jsp1=new JScrollPane(send_view);
		jsp1.setBounds(5, 310, 350,75);
		send_view.setAutoscrolls(true);
		send_view.setLineWrap(true);
		send_view.requestFocus();
		send_view.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				ObjectOutputStream oos=body.getOos();
				if(e.getKeyChar()==KeyEvent.VK_ESCAPE){
					closewindow();
				}else if(e.getKeyChar()==KeyEvent.VK_ENTER){
					String info=send_view.getText();
					if(info.startsWith("\n")){
						info=info.substring(1);
					}
					if("".equals(info)){
						JOptionPane.showMessageDialog(chat_view, "发送消息不能够为空");
				}else{
						try {				
							String msg=Session.nickName+"\t"+df.format(new Date())+"\n"+info;
							chatMsg=new ChatMsg("chat", msg,chatUserId,Session.userId,Session.nickName);
							oos.writeObject(chatMsg);
							oos.flush();
							send_view.setText(null);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
				}
			}
				
				
			}
		});
		this.add(jsp1);
		
		
		send=new JButton("发送");
		send.setBounds(210, 390, 60, 25);
		send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ObjectOutputStream oos=body.getOos();
				String info=send_view.getText();
				if(info.startsWith("\n")){
					info=info.substring(1);
				}
				if("".equals(info)){
					JOptionPane.showMessageDialog(chat_view, "发送消息不能够为空");
				}else{
					String msg=Session.nickName+"\t"+df.format(new Date())+"\n"+info;
					chatMsg=new ChatMsg("chat", msg,chatUserId,Session.userId,Session.nickName);
					try {
						oos.writeObject(chatMsg);
						oos.flush();
						send_view.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
				
			}
		});
		
		
		close=new JButton("关闭");
		close.setBounds(290, 390, 60, 25);
		close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int pd=JOptionPane.showConfirmDialog(chat_view, "确认退出？","提示",JOptionPane.YES_NO_OPTION);
				if(pd==JOptionPane.YES_OPTION){
					closewindow();
				}
			}
		});
		this.add(send);
		this.add(close);
		
		
		JLabel image=new JLabel(new ImageIcon("images/23.jpg"));
		image.setBounds(363,10 ,125, 280);
		
		JLabel image1=new JLabel(new ImageIcon("images/1.gif"));
		image1.setBounds(363,300 ,125, 115);
		
		
		this.add(image);
		this.add(image1);

		
		this.addWindowListener(new WindowListener() {
			
			@Override
			public void windowOpened(WindowEvent e) {
			}
			
			@Override
			public void windowIconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeiconified(WindowEvent e) {
			}
			
			@Override
			public void windowDeactivated(WindowEvent e) {
			}
			
			@Override
			public void windowClosing(WindowEvent e) {
				closewindow();
			}
			
			@Override
			public void windowClosed(WindowEvent e) {
			}
			
			@Override
			public void windowActivated(WindowEvent e) {
			}
		});
		this.setSize(500, 450);
//		this.setAlwaysOnTop(true);
		this.setLocationRelativeTo(this);
		this.setResizable(false);
		this.setVisible(true);
	}
	
	
	
	
	public void closewindow(){
		dispose();
		body.getCp().remove(chatUserId);
	}
	
	
	public JTextArea getChat_view() {
		return chat_view;
	}





	public void setChat_view(JTextArea chat_view) {
		this.chat_view = chat_view;
	}





	public JTextArea getSend_view() {
		return send_view;
	}





	public void setSend_view(JTextArea send_view) {
		this.send_view = send_view;
	}





	public MainBody getBody() {
		return body;
	}




	public void setBody(MainBody body) {
		this.body = body;
	}




	public JButton getSend() {
		return send;
	}





	public void setSend(JButton send) {
		this.send = send;
	}





	public JButton getClose() {
		return close;
	}





	public void setClose(JButton close) {
		this.close = close;
	}




	public JScrollPane getJsp() {
		return jsp;
	}




	public void setJsp(JScrollPane jsp) {
		this.jsp = jsp;
	}





	public static void main(String[] args) {
		new ChatPrivate("123",null,null);
	}
}
