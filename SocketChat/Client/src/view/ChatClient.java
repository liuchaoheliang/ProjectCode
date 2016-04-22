package view;

import java.awt.Adjustable;
import java.awt.Color;
import java.awt.Event;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;

import com.sun.org.apache.bcel.internal.generic.NEW;
import com.sun.org.apache.bcel.internal.generic.SIPUSH;

import common.Session;
import config.SysConfig;
import dao.ChatListener;
import dao.WindowClosing;
import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.UserMsg;
import util.ChatUtil;


	/**
	 * 类描述：群聊界面
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2014年10月21日 下午12:44:27 
	 */
public class ChatClient {
	
	private UserMsg userMsg;
	private ChatMsg chatMsg ;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JFrame jf;
	private JPopupMenu jpm,jpm_1;	
	private JPanel jpl,jpl_1;
	private JTextArea jta,jta_1;
	private JButton btn_send,btn_close;	
	private String nickName;
	private JList jList;
	private JLabel label;
	private JScrollPane jsp,jsp1,bar;
	public SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private String[] names,userIds;
	private HashMap<String, ChatPrivate> cp=new HashMap<String, ChatPrivate>();
	private MainBody body;
	WindowClosing closing;
	public ChatClient(MainBody mainBody){
		this.body=mainBody;
		this.names=body.getUsers();
		this.userIds=body.getUsersId();
		this.ois=mainBody.getOis();
		this.oos=mainBody.getOos();
		closing=new WindowClosing(this,mainBody);
	}

	ChatListener mouselistener=new ChatListener(this);
	public void CreateGui(){
		 try {     // windows风格界面
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			 } catch (Exception e) {
			  e.printStackTrace();
		}
		jf=new JFrame("聊天大厅 ("+Session.nickName+")-----在线人数："+names.length);	
		
		//鼠标快捷菜单
		jpm=new JPopupMenu();		
		jpm.setSize(70, 120);
		JMenuItem popu1=new JMenuItem("复制"); 
		popu1.addActionListener(mouselistener);
		jpm.add(popu1);
		jpm.addSeparator();
		JMenuItem popu2=new JMenuItem("剪切"); 
		popu2.addActionListener(mouselistener);
		jpm.add(popu2);
		jpm.addSeparator();
		JMenuItem popu3=new JMenuItem("粘贴"); 
		popu3.addActionListener(mouselistener);
		jpm.add(popu3);
		jpm.addSeparator();
		JMenuItem popu4=new JMenuItem("清空"); 
		popu4.addActionListener(mouselistener);
		jpm.add(popu4);	
		
		//列表快捷菜单
		jpm_1=new JPopupMenu();		
		jpm_1.setSize(70, 120);
		JMenuItem popu11=new JMenuItem("发送消息"); 
		popu11.addActionListener(mouselistener);
		jpm_1.add(popu11);
		jpm_1.addSeparator();
		JMenuItem popu21=new JMenuItem("查看资料"); 
		popu21.addActionListener(mouselistener);
		jpm_1.add(popu21);
		jpm_1.addSeparator();
		JMenuItem popu31=new JMenuItem("加为好友"); 
		popu31.addActionListener(mouselistener);
		jpm_1.add(popu31);
		jpm_1.addSeparator();
		JMenuItem popu41=new JMenuItem("一起游戏"); 
		popu41.addActionListener(mouselistener);
		jpm_1.add(popu41);	
		
		
		jf.setIconImage(new ImageIcon("images/logo.png").getImage());				
		jpl=new JPanel();		
		jpl.setLayout(null);
		jpl.setOpaque(false);
		jpl.setBackground(Color.BLUE);
		
		label=new JLabel();
		label.setForeground(Color.RED);
		label.setBounds(5, 5, 250, 20);
		
		
		JLabel label_user=new JLabel("在线用户列表");
		label_user.setBounds(360, 5, 75, 20);
		
		jpl.add(label_user);
		jpl.add(label);
		
//		names=new String[]{"23123131","657543453","879453453","13435221231"};
//		userIds=new String[]{"23123131","657543453","879453453","13435221231"};
    	jList=new JList(names);
    	bar=new JScrollPane(jList);
		bar.setBounds(360, 30, 130, 290);
		bar.setWheelScrollingEnabled(true);
//    	jList.setListData(names);
    	bar.getViewport().setView(jList);
    	jList.addMouseListener(mouselistener);
//		jpl_1.setLayout(new GridLayout(10, 1));
//		jpl_1.setBounds(360, 5, 130, 260);
//		jpl_1.add(bar);
		jpl.add(bar);
		
		//消息窗口
		jta =new JTextArea();
//		jta.add(jpm);
		jta.setAutoscrolls(true);//自动滚动
//		jta.setOpaque(false);//透明
		jta.setLineWrap(true);
		jta.setEditable(false);
		jta.addMouseListener(mouselistener);
		jsp=new JScrollPane(jta);
		jsp.setBounds(5, 30,350,290);		
		jsp.setWheelScrollingEnabled(true);
		//设置滚动条在最下面
//		jsp.getVerticalScrollBar().setValue(jsp.getVerticalScrollBar().getMaximum());
		jpl.add(jsp);
		
		
		//发送窗口
		jta_1=new JTextArea();	
//		jta_1.add(jpm);
		jta_1.setAutoscrolls(true);
		jta_1.setLineWrap(true);
		jta_1.requestFocus();

		jta_1.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {  
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyChar()==KeyEvent.VK_ESCAPE){
					jf.dispose();
					body.getCc().remove(Session.userId);
				}else if(e.getKeyChar()==KeyEvent.VK_ENTER){
					String info=jta_1.getText();
					if(info.startsWith("\n")){
						info=info.substring(1);
					}
					if("".equals(info)){
						JOptionPane.showMessageDialog(jf, "发送消息不能够为空");
					}else{
						try {				
							String msg=Session.nickName+"\t"+df.format(new Date())+"\n"+info;
							chatMsg=new ChatMsg("chat", msg, "all");
							oos.writeObject(chatMsg);
							oos.flush();
							jta_1.setText(null);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					}
				}
				
			}
		});
		jta_1.addMouseListener(mouselistener);
		jsp1=new JScrollPane(jta_1);
		jsp1.setBounds(5, 340, 350,75);
		jsp1.setWheelScrollingEnabled(true);
		jpl.add(jsp1);
		
		//发送按钮
		btn_send=new JButton("发送");
		btn_send.setBounds(230, 420, 60, 25);
		btn_send.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String info=jta_1.getText();
				if(info.startsWith("\n")){
					info=info.substring(1);
				}
				if("".equals(info)){
					JOptionPane.showMessageDialog(jf, "发送消息不能够为空");
				}else{
					try {
//						String info=util.chatMessage(Session.nickName, "all", jta_1.getText());
						
						String msg=Session.nickName+"\t"+df.format(new Date())+"\n"+info;
						chatMsg=new ChatMsg("chat", msg, "all");
						oos.writeObject(chatMsg);
						oos.flush();
						jta_1.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
		
		
		//关闭按钮
		btn_close=new JButton("关闭");
		btn_close.setBounds(295, 420, 60, 25);
		btn_close.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int pd=JOptionPane.showConfirmDialog(jpl, "确认退出？","提示",JOptionPane.YES_NO_OPTION);
				if(pd==JOptionPane.YES_OPTION){
					jf.dispose();
					body.getCc().remove(Session.userId);
				}
			}
		});
		
		 JPanel jpl_3=new JPanel();
		 jpl_3.setLayout(null);
		 JLabel imag=new JLabel(new ImageIcon("images/1.gif"));
		 imag.setBounds(0, 0, 125, 115);
		 jpl_3.add(imag);
		 jpl_3.setBackground(Color.BLUE);
		 jpl_3.setBounds(363,327 ,125, 115);	
		
		jpl.add(jpl_3);
		jpl.add(btn_send);
		jpl.add(btn_close);
		jf.getContentPane().add(jpl);
	
		jf.setBounds(400, 200, 506, 480);
//		jf.setAlwaysOnTop(true);
		jf.setResizable(false);
		jf.addWindowListener(closing);
		jf.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		jf.setVisible(true);
	}
	
	
	public static void main(String[] args) {
//		ChatClient chatClient= new ChatClient(new UserMsg());
//		chatClient.CreateGui();
	}


	public JFrame getJf() {
		return jf;
	}


	public void setJf(JFrame jf) {
		this.jf = jf;
	}


	public JPopupMenu getJpm() {
		return jpm;
	}


	public void setJpm(JPopupMenu jpm) {
		this.jpm = jpm;
	}


	public JPopupMenu getJpm_1() {
		return jpm_1;
	}


	public void setJpm_1(JPopupMenu jpm_1) {
		this.jpm_1 = jpm_1;
	}


	public JTextArea getJta() {
		return jta;
	}


	public void setJta(JTextArea jta) {
		this.jta = jta;
	}


	public String[] getNames() {
		return names;
	}


	public void setNames(String[] names) {
		this.names = names;
	}


	public String[] getUserIds() {
		return userIds;
	}


	public void setUserIds(String[] userIds) {
		this.userIds = userIds;
	}


	public HashMap<String, ChatPrivate> getCp() {
		return cp;
	}


	public void setCp(HashMap<String, ChatPrivate> cp) {
		this.cp = cp;
	}


	public MainBody getBody() {
		return body;
	}


	public void setBody(MainBody body) {
		this.body = body;
	}


	public JList getjList() {
		return jList;
	}


	public void setjList(JList jList) {
		this.jList = jList;
	}


	public JTextArea getJta_1() {
		return jta_1;
	}


	public void setJta_1(JTextArea jta_1) {
		this.jta_1 = jta_1;
	}


	public JPanel getJpl() {
		return jpl;
	}


	public void setJpl(JPanel jpl) {
		this.jpl = jpl;
	}


	public JScrollPane getJsp() {
		return jsp;
	}


	public void setJsp(JScrollPane jsp) {
		this.jsp = jsp;
	}

		
	
}
