package test;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import team.core.beans.conveying.ChatMsg;



public class TempSocket extends Thread{

	public static void main(String[] args) {
		Socket socket;
		try {
			socket = new Socket("localhost",13520);
			ChatMsg chatMsg = new ChatMsg(null, "userid", null);
			ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
			ObjectInputStream is = null;
			os.writeObject(chatMsg);
			os.flush();
			
			ObjectInputStream objectIs = new ObjectInputStream(socket.getInputStream());
			while(true){
				currentThread().sleep(1000);
				chatMsg = new ChatMsg("chat", "sdfasdf", "all");
				os.writeObject(chatMsg);
				os.flush();
				System.out.println(objectIs.readObject());
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		
	}
}
