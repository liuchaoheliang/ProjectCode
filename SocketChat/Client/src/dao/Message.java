package dao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

public class Message {
	
	
	//写入消息
	public static void InputMessage(String fileName,String words){
		try {
			BufferedWriter bw=new BufferedWriter(new FileWriter(".\\Message\\"+fileName+".txt",true));
			String str=words+"\r\n";
			bw.write(str);
			bw.flush();
			bw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	//删除记录
	public static void DeleteMessage(String fileName){
		File file=new File("Message\\"+fileName+".txt");
		if(file.exists()){
			if(file.delete()){
				JOptionPane.showMessageDialog(null, "删除记录成功");
				try {
					BufferedWriter bw=new BufferedWriter (new FileWriter("Message\\"+fileName+".txt"));
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	
	//查看消息
	public static void ViewMessage(String fileName){
		try {
			Runtime.getRuntime().exec("cmd /c refresh Message\\"+fileName+".txt");
			Runtime.getRuntime().exec("cmd /c start Message\\"+fileName+".txt");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
}
