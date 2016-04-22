package test;


import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/**
 * @author tony
 */
public class TextFrame extends JFrame {
	JLabel jb1 = new JLabel();
	JTextField txtUserName = new JTextField(10);
	JLabel jb2 = new JLabel();
	JPasswordField txtPassWord = new JPasswordField(6);
	FlowLayout flow = new FlowLayout();
	JTextArea jt1 = new JTextArea();
	JButton button = new JButton();
	
	public TextFrame(){
		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 生成
	 */
	private void jbInit()throws Exception {
		setSize(new Dimension(300,175));
		getContentPane().setLayout(flow);
		jb1.setText("用户名");
		txtUserName.setText("");
        jb2.setText("密码");
        txtPassWord.setText("");
        jt1.setText("");
        button.setText("注册");
        button.addActionListener(new TextFrame_jButton1_actionAdapter(this));
		
		this.getContentPane().add(jb1);
		this.getContentPane().add(txtUserName);
		this.getContentPane().add(jb2);
		this.getContentPane().add(txtPassWord);
		this.getContentPane().add(jt1);
		this.getContentPane().add(button);
		
	}

	/**
	 * 2012-5-21
	 */
	public static void main(String[] args) {
		TextFrame textFrame=new TextFrame();
		textFrame.setVisible(true);

	}
}

class TextFrame_jButton1_actionAdapter implements ActionListener{
	private TextFrame adaptee;

	TextFrame_jButton1_actionAdapter(TextFrame adaptee){
		this.adaptee=adaptee;
	}
	/* (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		SwingUtilities.invokeLater(new Runnable(){

				@Override
				public void run() {
					try {
						UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
					} catch (Exception e2) {
						e2.printStackTrace();
					}
					String username=adaptee.txtUserName.getText();
					char[] pwd=adaptee.txtPassWord.getPassword();
					adaptee.jt1.setText("您的用户名是："+username+"您的输入密码是："+String.valueOf(pwd));
					
				}
			
		});
		
	}
	
}