package view;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;

import common.Session;
public class TrayMain {
	private PopupMenu pm;
	private TrayIcon ti;
	private MainBody body;
	public TrayMain(MainBody body1){
		this.body=body1;
	}
	public void createTray(){
		ti = new TrayIcon(new ImageIcon("images/logo.png").getImage());
		ti.setImageAutoSize(true);

		if (SystemTray.isSupported()) {
			try {
				SystemTray.getSystemTray().add(ti);
			} catch (AWTException e1) {
				e1.printStackTrace();
			}
			ti.setToolTip("Chat ("+Session.nickName+")");
		}
		ActionListener l = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String ac = e.getActionCommand();
				if (ac.equalsIgnoreCase("action 1")) {
					if(!body.isVisible()){
						body.setVisible(true);
					}
					} else if (ac.equalsIgnoreCase("action 2")) {
						JOptionPane.showMessageDialog(null, "功能尚未开发");
					} else if (ac.equalsIgnoreCase("action 3")) {
						System.out.println(Session.nickName+" 退出系统");
						SystemTray.getSystemTray().remove(ti);
						body.shutDown();
					}else if(ac.equalsIgnoreCase("action 4")){
						JOptionPane.showMessageDialog(null, "Chat-2013 是由Dreamer和Jery共同开发.是实现聊天和交友的应用软件，具有良好的兼容性，适用与各种平台。");
					}
				 }
			};
			pm = new PopupMenu();
			MenuItem mi = new MenuItem("打开(O)");
			mi.setActionCommand("action 1");
			mi.addActionListener(l);
	
			pm.add(mi);
			mi = new MenuItem("设置(S)");
			mi.setActionCommand("action 2");
			mi.addActionListener(l);
			pm.add(mi);
			mi = new MenuItem("关于Chat..");
			mi.setActionCommand("action 4");
			mi.addActionListener(l);
			pm.add(mi);			
			pm.addSeparator();
			mi = new MenuItem("退出(Q)");
			mi.setActionCommand("action 3");
			mi.addActionListener(l);
			pm.add(mi);
			ti.setPopupMenu(pm);
			ti.addMouseListener(new MouseListener() {
				
				@Override
				public void mouseReleased(MouseEvent e) {
				}
				
				@Override
				public void mousePressed(MouseEvent e) {
				}
				
				@Override
				public void mouseExited(MouseEvent e) {
				}
				
				@Override
				public void mouseEntered(MouseEvent e) {
				}
				
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getModifiers()==MouseEvent.BUTTON1_MASK){
							if(!body.isVisible()){
								body.setVisible(true);
								body.show();
							}
						}
					}
				});
			
	}
	
	
//	public static void main(String[] args) throws Exception {
//		new TrayMain().createTray(null);
//	}


	public PopupMenu getPm() {
		return pm;
	}


	public void setPm(PopupMenu pm) {
		this.pm = pm;
	}


	public TrayIcon getTi() {
		return ti;
	}


	public void setTi(TrayIcon ti) {
		this.ti = ti;
	}


	public MainBody getBody() {
		return body;
	}


	public void setBody(MainBody body) {
		this.body = body;
	}
	
	
	
}
