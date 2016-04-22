package dao;

import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import common.Session;

import view.ChatClient;
import view.MainBody;

public class WindowClosing implements WindowListener {
	
	public ChatClient client;
	private MainBody body;
	public WindowClosing(ChatClient client,MainBody mainBody) {
		this.client = client;
		this.body=mainBody;
	}	


	@Override
	public void windowActivated(WindowEvent arg0) {

	}

	@Override
	public void windowClosed(WindowEvent arg0) {
	}

	@Override
	public void windowClosing(WindowEvent arg0) {
		client.getJf().dispose();
		body.getCc().remove(Session.userId);
	}

	@Override
	public void windowDeactivated(WindowEvent arg0) {

	}

	@Override
	public void windowDeiconified(WindowEvent arg0) {

	}

	@Override
	public void windowIconified(WindowEvent arg0) {

	}

	@Override
	public void windowOpened(WindowEvent arg0) {
		
	}

}
