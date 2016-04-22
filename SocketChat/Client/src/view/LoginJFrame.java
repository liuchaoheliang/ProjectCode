package view;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.zxy.api.MD5PWD;

import common.Session;
import common.Version;

import config.RecodeMsg;
import config.SysConfig;
import po.User;
import team.core.beans.conveying.FriendMsg;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;
import util.ChatUtil;
import dao.ConnectionServer;
import dao.UserDao;


public class LoginJFrame extends JFrame {
	private static TrayMain trayMain;
	private static MainBody body;
	private static  JTextField textField;
	private static JPasswordField textField1;
	private static JFrame jf;
//	private Response response=null;
	public LoginJFrame(){
		// 使用Windows的界面风格	
		try {     
	
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	
			 } catch (Exception e) {
	
			  e.printStackTrace();
	
			 }
			 jf=new JFrame("欢迎登录");
			 jf.setIconImage(new ImageIcon("images/logo.png").getImage());
			 JPanel jp=new JPanel();
			 jp.setLayout(null);
			 
			 JLabel label=new JLabel("用户名：");
			 label.setBounds(50, 30, 50, 15);
			 textField=new JTextField();
			 textField.setBounds(120, 30, 120, 20);
			 
			 JLabel label2=new JLabel("密  码：");
			 label2.setBounds(50, 70, 50, 15);
			 textField1=new JPasswordField();
			 textField1.setBounds(120, 70, 120, 20);
			 textField1.setEchoChar('*');
			 textField1.addKeyListener(new KeyListener() {
				
				@Override
				public void keyTyped(KeyEvent e) {
				}
				
				@Override
				public void keyReleased(KeyEvent e) {
				}
				
				@Override
				public void keyPressed(KeyEvent e) {
					if(e.getKeyChar()==KeyEvent.VK_ENTER){
						login();
					}
					
				}
			});
			 
			 JButton button=new JButton("登录(L)");
			 button.setBounds(50, 110, 80, 30);
			 button.setMnemonic('l');
			 button.setCursor(new Cursor(HAND_CURSOR));
			 button.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					login();
				}
			});
			 JButton button1=new JButton("退出(Q)");
			 button1.setBounds(170, 110, 80, 30);
			 button1.setMnemonic('q');
			 button1.setCursor(new Cursor(HAND_CURSOR));
			 button1.addActionListener(new ActionListener() {
				
				public void actionPerformed(ActionEvent e) {
					System.exit(DISPOSE_ON_CLOSE);
				}
			});
			 
			 JLabel label3=new JLabel("注册用户");
			 label3.setForeground(Color.RED);
			 label3.setCursor(new Cursor(HAND_CURSOR));
			 label3.setBounds(260, 140, 120, 30);
			 label3.addMouseListener(new MouseListener() {
				
				public void mouseReleased(MouseEvent arg0) {
				}
				
				public void mousePressed(MouseEvent arg0) {
				}
				
				public void mouseExited(MouseEvent arg0) {
				}
				
				public void mouseEntered(MouseEvent arg0) {
				}
				
				public void mouseClicked(MouseEvent arg0) {
					new Register(jf);
					jf.dispose();
				}
			});
			 
			 jp.add(label);
			 jp.add(textField);
			 jp.add(label2);
			 jp.add(textField1);
			 jp.add(button);
			 jp.add(button1);
			 jp.add(label3);			 
			 jf.add(jp);			 
			 jf.setSize( 330, 200);
			 jf.setLocationRelativeTo(this);
			 jf.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
			 jf.setResizable(false);
			 jf.setVisible(true);
	}
	
	public static void login(){
		String name=textField.getText();
		String psd=textField1.getText();
		if("".equals(name)||"".equals(psd)){
			JOptionPane.showMessageDialog(jf, "用户名或密码不能为空");
		}else{
			UserMsg userMsg=new UserMsg();
			userMsg.setName(name);
			userMsg.setPwd(MD5PWD.encryptMD5(psd));
			UtilConveyingMsg response=ConnectionServer.requestServer("0001", userMsg);
			if(response!=null && "0002".equals(response.getCode())){
				userMsg=(UserMsg) response.getObject();
				jf.dispose();
				Session.userId=userMsg.getUserId();
				Session.nickName=userMsg.getNickname();
				body=new MainBody(userMsg);
				body.connect();
				//获取好友列表
				ChatUtil.dealFriendListinfo(body);
	//			body.getFriend_list().setListData(ChatUtil.CastToFriendName(resp));
				body.createMainBody();
				body.creatThread();
				trayMain=new TrayMain(body);	
				trayMain.createTray();
				response=ConnectionServer.requestServer("0007", null);
				String version=(String) response.getObject();
				if(version.compareTo(Version.version)==1){
					int pd=JOptionPane.showConfirmDialog(body, "有更新版本是否立刻下载","提示",JOptionPane.YES_NO_OPTION);
					if(pd==JOptionPane.YES_OPTION){
//						URL url = new URL("http://localhost:8083/shopunion/alluserlogin");
//						 HttpURLConnection con = (HttpURLConnection) url.openConnection();
						try {
							Runtime.getRuntime().exec("cmd   /c   start  "+SysConfig.ADDRESS+"");
//							Runtime.getRuntime().exec("explore.exe http://www.baidu.com");
						} catch (IOException e) {
							JOptionPane.showMessageDialog(body, "网络连接异常");
							e.printStackTrace();
						}
					}
				}
			}else{
				JOptionPane.showMessageDialog(jf,RecodeMsg.getReCodeMessage(response.getCode()));
				textField1.setText("");
				textField.requestFocus();
			}	
		}
	}
	
	public static void main(String[] args) {
		new LoginJFrame();
	}
	
}
