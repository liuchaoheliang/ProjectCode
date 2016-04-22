package view;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;

import common.Session;

import config.SysConfig;
import dao.BodyListener;
import dao.BodyReader;

import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.UserMsg;
import util.ChatUtil;

public class MainBody extends JDialog{
	private JTabbedPane tp;
	private Socket s;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private JPanel all_user,my_friend,least;
	private String[] users,friends,history;
	private String[] usersId,friendsId,historyId;
	private JList user_list,friend_list,history_list;
	private JScrollPane user_scroll,friend_scroll,history_scroll;
	private JPopupMenu jpm,jpm_1;
	private UserMsg userMsg;
	private boolean runnable=true;
	private ChatClient chatClient;
	private HashMap<String, ChatPrivate> cp=new HashMap<String, ChatPrivate>();
	private HashMap<String, ChatClient> cc=new HashMap<String, ChatClient>();
	private HashMap<String, String> isOnLine=new HashMap<String, String>();
	
	public MainBody(UserMsg userMsg1){
		this.userMsg=userMsg1;
	}
	
	
	public void createMainBody(){
		 try {     // windows风格界面
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
			 } catch (Exception e) {
			  e.printStackTrace();
		}
		this.setLayout(null);
		this.setUndecorated(false);
		
		jpm=new JPopupMenu();		
		jpm.setSize(70, 120);
		JMenuItem popu1=new JMenuItem("与他聊天"); 
		popu1.addActionListener(new BodyListener(this));
		jpm.add(popu1);
		jpm.addSeparator();
		JMenuItem popu2=new JMenuItem("查看消息"); 
		popu2.addActionListener(new BodyListener(this));
		jpm.add(popu2);
		jpm.addSeparator();
		JMenuItem popu3=new JMenuItem("删除好友"); 
		popu3.addActionListener(new BodyListener(this));
		jpm.add(popu3);
		jpm.addSeparator();
		JMenuItem popu4=new JMenuItem("添加备注"); 
		popu4.addActionListener(new BodyListener(this));
		jpm.add(popu4);	
		
		//列表快捷菜单
		jpm_1=new JPopupMenu();		
		jpm_1.setSize(70, 120);
		JMenuItem popu01=new JMenuItem("进入聊天室"); 
		popu01.addActionListener(new BodyListener(this));
		jpm_1.add(popu01);
		jpm_1.addSeparator();
		JMenuItem popu11=new JMenuItem("发送消息"); 
		popu11.addActionListener(new BodyListener(this));
		jpm_1.add(popu11);
		jpm_1.addSeparator();
		JMenuItem popu21=new JMenuItem("查看资料"); 
		popu21.addActionListener(new BodyListener(this));
		jpm_1.add(popu21);
		jpm_1.addSeparator();
		JMenuItem popu31=new JMenuItem("加为好友"); 
		popu31.addActionListener(new BodyListener(this));
		jpm_1.add(popu31);
		jpm_1.addSeparator();
		JMenuItem popu41=new JMenuItem("一起游戏"); 
		popu41.addActionListener(new BodyListener(this));
		jpm_1.add(popu41);	
		
		
		
		JPanel info=new JPanel();
		info.setBounds(0, 0, 200, 103);
		info.setLayout(null);
		JLabel head=new JLabel( new ImageIcon("images/boy.jpg"));

		if("1".equals(userMsg.getSex())){
			head.setIcon(new ImageIcon("images/girl.jpg"));
		}
		head.setBounds(15, 15, 60, 60);
		head.setCursor(new Cursor(Cursor.HAND_CURSOR));
		JLabel name=new JLabel(userMsg.getNickname());
		name.setFont(new Font(Font.SERIF, Font.BOLD , 14));
		name.setBounds(95, 30, 90, 25);
		JLabel sign=new JLabel("个性签名:"+userMsg.getAutograph());
		sign.setBounds(5, 75, 250, 25);
		
		info.add(name);
		info.add(head);
		info.add(sign);		
		
		tp=new JTabbedPane();
		tp.setBounds(0, 100, 245, 355);
		all_user=new JPanel();
		all_user.setLayout(null);
		my_friend=new JPanel();
		my_friend.setLayout(null);
		least=new JPanel();
		least.setLayout(null);
		
//		users=new String[]{"liuchao","helinag","zhoujing","lijinkui"};
//		friends=new String[]{"liuchao","helinag","zhoujing","lijinkui","qiaopeng","zhaoxiaoyao"};
		history=new String[]{"liqing","linmengxiao"};
		
		
		user_list=new JList(users);
		user_list.addMouseListener(new BodyListener(this));
		
		friend_list=new JList(friends); 
		friend_list.addMouseListener(new BodyListener(this));
		
		history_list=new JList(history);
		history_list.addMouseListener(new BodyListener(this));
		
		user_scroll=new JScrollPane(user_list);
		user_scroll.setBounds(0, 0, 250, 390);
		user_scroll.setWheelScrollingEnabled(true);
		user_scroll.getViewport().setView(user_list);
		
		friend_scroll=new JScrollPane(friend_list);
		friend_scroll.setBounds(0, 0, 250, 390);
		friend_scroll.setWheelScrollingEnabled(true);
		friend_scroll.getViewport().setView(friend_list);
		
		history_scroll=new JScrollPane(history_list);
		history_scroll.setBounds(0, 0, 250, 390);
		history_scroll.setWheelScrollingEnabled(true);
		history_scroll.getViewport().setView(history_list);
		
		all_user.add(user_scroll);
		my_friend.add(friend_scroll);
		least.add(history_scroll);
	
		
		tp.addTab("用 户 列 表", null, all_user, null);
		tp.addTab(" 我 的 好 友", null, my_friend, null);
		tp.addTab("最 近 联 系", null, least, null);
		
		JLabel label=new JLabel(new ImageIcon("images/tool.jpg"));
		label.setBounds(2, 460, 250, 53);
		this.add(label);
		this.add(info);
		this.add(tp);	
		this.setAlwaysOnTop(true);
//		this.addWindowListener(new BodyListener(this));		
		this.setTitle("Chat-2013");
//		this.setSize(250, 500);
		this.setIconImage(new ImageIcon("images/logo.png").getImage());	 
//		this.setLocationRelativeTo(this);
		this.setBounds(1100, 100, 250, 550);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setResizable(false);
		this.setVisible(true);			 
	}
	
	
	public void connect(){
		try {			
			s=new Socket(SysConfig.IP,Integer.parseInt(SysConfig.PORT));
			oos=new ObjectOutputStream(s.getOutputStream());			
			oos.writeObject(new ChatMsg(null,null,null,Session.userId,userMsg.getNickname()));			
			ois=new ObjectInputStream(s.getInputStream());
			try {
				ChatMsg msg=(ChatMsg) ois.readObject();
				users=ChatUtil.CastToNickNameArr(msg);
				usersId=ChatUtil.CastToUserIdArr(msg);
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "与服务器连接失败");
			System.exit(EXIT_ON_CLOSE);
			e.printStackTrace();
		}
	}
	
	
	public void creatChatPrivate(String chatNickName,String chatUserId){
		if(!cp.containsKey(chatUserId)){
			ChatPrivate private1=new ChatPrivate(chatNickName,chatUserId, this);
			private1.getSend_view().requestFocus();
			cp.put(chatUserId, private1);
		}else{
			cp.get(chatUserId).setExtendedState(JFrame.NORMAL);
		}
	}
	
	//下线
	public void shutDown(){
		try {
			oos.writeObject(new ChatMsg("outline",null,null,null,userMsg.getNickname()));
			this.stop();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			this.ois.close();
			this.oos.close();
			this.s.close();
			this.dispose();
			System.exit(EXIT_ON_CLOSE);
		} catch (Exception e) {		
			JOptionPane.showMessageDialog(null, "异常关闭");
			System.exit(EXIT_ON_CLOSE);
			e.printStackTrace();
		}		
	}
	
	
	public void stop(){
		runnable=false;
	}
	
	public boolean isRunnable() {
		return runnable;
	}
	
	public void creatThread(){
		BodyReader reader=new BodyReader(this );
		reader.start();
	}
	public JTabbedPane getTp() {
		return tp;
	}
	public void setTp(JTabbedPane tp) {
		this.tp = tp;
	}
	public Socket getS() {
		return s;
	}
	public void setS(Socket s) {
		this.s = s;
	}
	public ObjectInputStream getOis() {
		return ois;
	}
	public void setOis(ObjectInputStream ois) {
		this.ois = ois;
	}
	public ObjectOutputStream getOos() {
		return oos;
	}
	public void setOos(ObjectOutputStream oos) {
		this.oos = oos;
	}
	public JPanel getAll_user() {
		return all_user;
	}
	public void setAll_user(JPanel all_user) {
		this.all_user = all_user;
	}
	public JPanel getMy_friend() {
		return my_friend;
	}
	public void setMy_friend(JPanel my_friend) {
		this.my_friend = my_friend;
	}
	public JPanel getLeast() {
		return least;
	}
	public void setLeast(JPanel least) {
		this.least = least;
	}
	public String[] getUsers() {
		return users;
	}
	public void setUsers(String[] users) {
		this.users = users;
	}
	public String[] getFriends() {
		return friends;
	}
	public void setFriends(String[] friends) {
		this.friends = friends;
	}
	public String[] getHistory() {
		return history;
	}
	public void setHistory(String[] history) {
		this.history = history;
	}
	public JList getUser_list() {
		return user_list;
	}
	public void setUser_list(JList user_list) {
		this.user_list = user_list;
	}
	public JList getFriend_list() {
		return friend_list;
	}
	public void setFriend_list(JList friend_list) {
		this.friend_list = friend_list;
	}
	public JList getHistory_list() {
		return history_list;
	}
	public void setHistory_list(JList history_list) {
		this.history_list = history_list;
	}
	public JScrollPane getUser_scroll() {
		return user_scroll;
	}
	public void setUser_scroll(JScrollPane user_scroll) {
		this.user_scroll = user_scroll;
	}
	public JScrollPane getFriend_scroll() {
		return friend_scroll;
	}
	public void setFriend_scroll(JScrollPane friend_scroll) {
		this.friend_scroll = friend_scroll;
	}
	public JScrollPane getHistory_scroll() {
		return history_scroll;
	}
	public void setHistory_scroll(JScrollPane history_scroll) {
		this.history_scroll = history_scroll;
	}
	public JPopupMenu getJpm_1() {
		return jpm_1;
	}
	public void setJpm_1(JPopupMenu jpm_1) {
		this.jpm_1 = jpm_1;
	}
	public UserMsg getUserMsg() {
		return userMsg;
	}
	public void setUserMsg(UserMsg userMsg) {
		this.userMsg = userMsg;
	}


	public HashMap<String, ChatPrivate> getCp() {
		return cp;
	}


	public void setCp(HashMap<String, ChatPrivate> cp) {
		this.cp = cp;
	}


	public void setRunnable(boolean runnable) {
		this.runnable = runnable;
	}


	public String[] getUsersId() {
		return usersId;
	}


	public void setUsersId(String[] usersId) {
		this.usersId = usersId;
	}


	public String[] getFriendsId() {
		return friendsId;
	}


	public void setFriendsId(String[] friendsId) {
		this.friendsId = friendsId;
	}



	public String[] getHistoryId() {
		return historyId;
	}


	public void setHistoryId(String[] historyId) {
		this.historyId = historyId;
	}
	

	public JPopupMenu getJpm() {
		return jpm;
	}


	public void setJpm(JPopupMenu jpm) {
		this.jpm = jpm;
	}


	public ChatClient getChatClient() {
		return chatClient;
	}


	public void setChatClient(ChatClient chatClient) {
		this.chatClient = chatClient;
	}


	public HashMap<String, ChatClient> getCc() {
		return cc;
	}


	public void setCc(HashMap<String, ChatClient> cc) {
		this.cc = cc;
	}


	public HashMap<String, String> getIsOnLine() {
		return isOnLine;
	}


	public void setIsOnLine(HashMap<String, String> isOnLine) {
		this.isOnLine = isOnLine;
	}


	public static void main(String[] args) {
		MainBody body=new MainBody(new UserMsg());
		body.createMainBody();
	}
	
	
	
	
}
