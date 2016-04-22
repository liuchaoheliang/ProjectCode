package view;

	import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Shape;

	import java.awt.Dimension;

	import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.ImageObserver;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.AttributedCharacterIterator;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
	import java.util.Random;
import java.util.Vector;

import javax.swing.JButton;
	import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollBar;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

	import javax.swing.JPanel;

	import javax.swing.JScrollPane;

	import javax.swing.JTree;

	import javax.swing.UIManager;

	import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

import javax.swing.tree.TreeSelectionModel;

import sun.net.www.content.image.jpeg;
import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.UserMsg;




	/**

	 *JTreeTest鐏炴洜銇氶弽锟�
	 */

	public class Test1  extends JFrame{

	    private JPanel panel;
	    private JList jList;
	    private AudioClip sys,msg;
	    public Test1() {
	    	
	    	this.setLayout(new FlowLayout());
	    	panel=new JPanel();
	    	JTabbedPane tabbedPane=new JTabbedPane();
//	    	tabbedPane.
	    	
	    	panel.add(tabbedPane);
//	    	panel.setLayout(new GridLayout(3,1));
	    	
//	    	final JScrollPane bar=new JScrollPane();
//	    	bar.setSize(50, 80);
	    	Button button=new Button("11111");
	    	button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						sys= Applet.newAudioClip(new URL("file:sound/Global.wav"));
						sys.play();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
				}
			});
	    	Button button1=new Button("22222");
	    	button1.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try {
						msg= Applet.newAudioClip(new URL("file:sound/msg.wav"));		
						msg.play();
					} catch (MalformedURLException e1) {
						e1.printStackTrace();
					}
				}
			});
	    	Button button3=new Button("adsfkasd'ql");
	    	Button button2=new Button("asdnfkadm;afe");
	    	panel.add(button);
	    	panel.add(button1);
	    	panel.add(button2);
	    	panel.add(button3);
//	    	JLabel label=new JLabel("111111111111");
//	    	final JLabel label2=new JLabel("22222222222");
//	    	final JLabel label3=new JLabel("33333333333");
//	    	final JLabel label4=new JLabel("44444444444");
//	    	panel.add(label);
//	    	panel.add(label2);
//	    	panel.add(label3);
//	    	panel.add(label4);
//	    	String[] s={"sadasdasd","asdasdffgre","1234564433"};
//	    	final ChatMsg[] msg={new ChatMsg("123123"),new ChatMsg("12312", "sdkjsksj")};
//	    	List<ChatMsg>[] list=new ArrayList<ChatMsg>();
//	    	listData.copyInto(s);
//	    	jList=new JList(s);
//	    	bar.getViewport().setView(jList);
//	    	JButton button=new JButton("test");
//	    	button.addActionListener(new ActionListener() {
//				
//				@Override
//				public void actionPerformed(ActionEvent e) {
//					String[] ss={"123131","657454342"}; 
//					jList.setListData(ss);
//					panel.remove(label2);
//					panel.removeAll();					
//					panel=new JPanel();
//					panel.setLayout(new FlowLayout());
//					JLabel label0=new JLabel("fsdsadasfa");
//					panel.add(label0);
//					panel.add(label3);
//					panel.add(label4);
//					JLabel labe2=new JLabel("dfgfgjgjfhy");
//					panel.add(label);
//					panel.add(labe2);
//					
//					panel.updateUI();
//				}
//			});
//	    	this.add(jList);
//	    	this.add(bar);
	    	this.add(panel);
	    	this.add(button);
	    	this.setSize(300, 300);
	    	this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	    	this.setLocationRelativeTo(this);
	    	this.setVisible(true);
	    	
	    	
	    }

	    public static void main(String args[]) {
	    	


	        

	        //閸掓稑缂撴稉锟介嚋Window

//	        final JFrame frame=new JFrame("Tree Test");

//	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);




	//濞ｈ濮為崘鍛啇
	        
	        
//	         Test1 t=new Test1();
//	        new Test1();
                UserMsg msg=new UserMsg();
	    	MainBody body= new MainBody(msg);
	    	body.createMainBody();
	    	TrayMain tm=new TrayMain(body);
	    	tm.createTray();
	}

}
