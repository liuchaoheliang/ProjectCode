package view;
import java.awt.CheckboxGroup;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import org.zxy.api.MD5PWD;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.zxy.api.Match;
import org.zxy.common.CommonFn;
import org.zxy.valid.MatchType;

import config.RecodeMsg;

import dao.ConnectionServer;
import dao.UserDao;

import po.Response;
import po.User;
import po.UserInfo;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;


public class Register extends JFrame {
	private UserDao userDao=new UserDao();
	private UtilConveyingMsg response=null;
	public Register(final JFrame jFrame){
		// 使用Windows的界面风格	
		try {     
	
			  UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
	
			 } catch (Exception e) {
	
			  e.printStackTrace();
	
			 }
		final JFrame jf=new JFrame("注册用户");
		 jf.setIconImage(new ImageIcon("images/logo.png").getImage());
		JPanel jp=new JPanel();
		jp.setLayout(null);
		
		JLabel label=new JLabel("用户名：");
		label.setBounds(50, 30, 70, 25);
		final JTextField textField=new JTextField(12);
		textField.setBounds(110, 30, 110, 25);
		
		JLabel label2=new JLabel("密  码：");
		label2.setBounds(50, 70, 70, 25);
		final JPasswordField textField1=new JPasswordField(12);
		textField1.setBounds(110, 70, 110, 25);
		textField1.setEchoChar('*');
		
		JLabel label3=new JLabel("确认密码：");
		label3.setBounds(40, 110, 70, 25);
		final JPasswordField textField2=new JPasswordField(12);
		textField2.setBounds(110, 110, 110, 25);
		textField2.setEchoChar('*');
		
		JLabel labe4=new JLabel("昵  称：");
		labe4.setBounds(50, 145, 70, 25);
		final JTextField textField3=new JTextField(12);
		textField3.setBounds(110, 145, 110, 25);
		
		JLabel labe5=new JLabel("邮  箱：");
		labe5.setBounds(50, 180, 70, 25);
		final JTextField textField4=new JTextField(12);
		textField4.setBounds(110, 180, 110, 25);
		
		ButtonGroup group = new ButtonGroup();
			
		JLabel labe6=new JLabel("性  别：");
		labe6.setBounds(50, 220, 70, 25);
		JRadioButton radioButton1=new JRadioButton("男",true);
		radioButton1.setBounds(110, 220,40,25);
		final JRadioButton radioButton2=new JRadioButton("女");
		radioButton2.setBounds(170, 220,40,25);
		
		group.add(radioButton1);
		group.add(radioButton2);
		
		JButton button=new JButton("注册(R)");
		button.setBounds(70, 260, 75, 25);
		button.setMnemonic('r');
		button.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent arg0) {
				String name=textField.getText();
				String psd=textField1.getText();
				String psdd=textField2.getText();				
				String email=textField4.getText();
				String nickName=textField3.getText();
				String sex="0";
				if(radioButton2.isSelected()){
					sex="1";
				}
				
				if("".equals(name)||"".equals(psd)||"".equals(psdd)||"".equals(nickName)||"".equals(email)){
					JOptionPane.showMessageDialog(jf, "注册信息不能为空");
				}else if(!psd.equals(psdd)){
					JOptionPane.showMessageDialog(jf, "两次输入的密码不一致");
					textField1.setText("");
					textField2.setText("");
					textField1.requestFocus();
				}else {
//					User user=new User(name, psd);
//					UserInfo userInfo=new UserInfo(email, sex, nickName);
					UserMsg user=new UserMsg();
					user.setName(name);
					user.setPwd(MD5PWD.encryptMD5(psd));
					user.setNickname(nickName);
					user.setMail(CommonFn.emptyString(email)?null:email);
					user.setSex(sex);
					if(!CommonFn.emptyString(email) && !Match.match(email, MatchType.MAIL)){
						JOptionPane.showMessageDialog(jf, "邮箱格式不正确");
						return;
					}
//					response= userDao.register(user,userInfo);
					response=ConnectionServer.requestServer("0002", user);
					System.out.println(response.getCode());
					if("0004".equals(response.getCode())){
						JOptionPane.showMessageDialog(jf, RecodeMsg.getReCodeMessage(response.getCode()));
						jf.dispose();
						jFrame.show();
					}else if("0003".equals(response.getCode())){
						JOptionPane.showMessageDialog(jf, RecodeMsg.getReCodeMessage(response.getCode()));
						textField.setText("");
						textField.requestFocus();
					}else if("0006".equals(response.getCode())){
						JOptionPane.showMessageDialog(jf,RecodeMsg.getReCodeMessage(response.getCode()));
					}else{
						JOptionPane.showMessageDialog(jf, "系统繁忙请稍后再试");
					}					
				}
			}
		});
		JButton button1=new JButton("取消(Q)");
		button1.setBounds(160, 260, 75, 25);
		button1.setMnemonic('q');
		button1.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				jf.dispose();
				jFrame.show();
			}
		});
		jp.add(label);
		jp.add(textField);
		jp.add(label2);
		jp.add(textField1);
		jp.add(label3);
		jp.add(textField2);
		jp.add(labe4);
		jp.add(textField3);
		jp.add(labe5);
		jp.add(textField4);
		jp.add(labe6);
		jp.add(radioButton1);
		jp.add(radioButton2);
		jp.add(button);
		jp.add(button1);

		
		jf.add(jp);
		
		
		jf.setSize(300, 330);
		jf.setLocationRelativeTo(this);
		jf.setResizable(false);
		jf.setVisible(true);
	}
	
//	public static void main(String[] args) {
//		new Register(null);
//	}
}
