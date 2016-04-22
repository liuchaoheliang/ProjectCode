package test;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import team.core.beans.conveying.ChatMsg;



public class TempServerSocket{

	static ServerSocket serverSocket;
	
	public TempServerSocket(){
		try {
			serverSocket = new ServerSocket(13520);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) throws IOException {
		new TempServerSocket();
		Socket socket;
		while(true){
			socket = serverSocket.accept();
			ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
			try {
				System.out.println(((ChatMsg)is.readObject()).getMsg());
				ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
				os.writeObject(new ChatMsg(null , "userids", null));
				os.flush();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}	
}