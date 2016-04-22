package test;

import java.awt.AWTException;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.ImageIcon;

import com.sun.java.swing.plaf.windows.resources.windows;

public class Swing extends javax.swing.JFrame implements ActionListener,
		WindowListener {

	// Variables declaration - do not modify
	private javax.swing.JLabel L_img;
	private javax.swing.JLabel L_img2;
	private PopupMenu pop;
	private MenuItem open, close;
	private TrayIcon trayicon;


	public Swing() {
		this.setTitle("j o ");
		this.setLocation(300, 300);
		initComponents();
	}

	private void initComponents() {
		L_img = new javax.swing.JLabel(new ImageIcon(this.getClass()
				.getClassLoader().getResource("test/img.png")));
		L_img2 = new javax.swing.JLabel(new ImageIcon(this.getClass()
				.getClassLoader().getResource("test/img.png")));

		pop = new PopupMenu();
		open = new MenuItem("open");
		open.addActionListener(this);

		close = new MenuItem("close");
		close.addActionListener(this);

		pop.add(open);
		pop.add(close);

		if (SystemTray.isSupported()) {
			SystemTray tray = SystemTray.getSystemTray();
			Image icon = this.getToolkit().getImage(
					this.getClass()
							.getClassLoader()
							.getResource(
									"test/img.png"));
			trayicon = new TrayIcon(icon, "j o ", pop);
			trayicon.addMouseListener(new MouseListener() {

				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() == 2) {
						openFrame();
					}
				}

				public void mouseEntered(MouseEvent e) {

				}

				public void mouseExited(MouseEvent e) {

				}

				public void mousePressed(MouseEvent e) {

				}

				public void mouseReleased(MouseEvent e) {

				}

			});

			try {
				tray.add(trayicon);
			} catch (AWTException e) {
				e.printStackTrace();
			}
		}

		javax.swing.GroupLayout layout = new javax.swing.GroupLayout(
				getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout
				.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
				.addGroup(
						javax.swing.GroupLayout.Alignment.TRAILING,
						layout.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										layout.createParallelGroup(
												javax.swing.GroupLayout.Alignment.TRAILING)
												.addComponent(
														L_img2,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														380, Short.MAX_VALUE)
												.addComponent(
														L_img,
														javax.swing.GroupLayout.Alignment.LEADING,
														javax.swing.GroupLayout.DEFAULT_SIZE,
														380, Short.MAX_VALUE))
								.addContainerGap()));
		layout.setVerticalGroup(layout.createParallelGroup(
				javax.swing.GroupLayout.Alignment.LEADING).addGroup(
				layout.createSequentialGroup()
						.addContainerGap()
						.addComponent(L_img)
						.addGap(29, 29, 29)
						.addComponent(L_img2,
								javax.swing.GroupLayout.PREFERRED_SIZE, 222,
								javax.swing.GroupLayout.PREFERRED_SIZE)
						.addContainerGap(39, Short.MAX_VALUE)));

		pack();
	}// </editor-fold>

	public static void main(String args[]) {
		java.awt.EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Swing().setVisible(false);
			}
		});
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == open) {
			openFrame();
		}
		if (e.getSource() == close) {
			System.exit(-1);
		}
	}

	public void openFrame() {
		this.setVisible(true);
		this.setAlwaysOnTop(true);
	}

	public void windowActivated(WindowEvent arg0) {

		System.out.println("windowActivated");
	}

	public void windowClosed(WindowEvent arg0) {
		System.out.println("windowClosed");
		this.setVisible(false);
		this.dispose();
	}

	public void windowClosing(WindowEvent arg0) {

		System.out.println("windowClosing");
	}

	public void windowDeactivated(WindowEvent arg0) {

		System.out.println("windowDeactivated");
	}

	public void windowDeiconified(WindowEvent arg0) {

		System.out.println("windowDeiconified");
	}

	// 窗口最小化
	public void windowIconified(WindowEvent arg0) {
		System.out.println("windowIconified");
		//this.dispose();
	}

	public void windowOpened(WindowEvent arg0) {

		System.out.println("windowOpened");
	}

}