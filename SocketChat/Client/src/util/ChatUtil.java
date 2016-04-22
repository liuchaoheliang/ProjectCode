package util;

import java.util.HashMap;
import java.util.List;

import common.Session;

import dao.ConnectionServer;

import team.core.beans.conveying.ChatMsg;
import team.core.beans.conveying.FriendMsg;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;
import view.MainBody;

public class ChatUtil {
	
	//组装聊天信息发生报文
	public String chatMessage(String nickName,String to,String info){
		String2Xml xml=new String2Xml("msg");
		xml.append("nickname", nickName);
		xml.append("to", to);
		xml.append("info", info);
		return xml.toXMLString();
	}
//	public static void main(String[] args) {
//		ChatUtil chatUtil=new ChatUtil();
//		chatUtil.chatMessage("abc", "all", "撒旦卡斯达克哈");
//		System.out.println(chatUtil.chatMessage("abc", "all", "撒旦卡斯达克哈"));
//	}
	
	//获取昵称名字数组
	public static  String[] CastToNickNameArr(ChatMsg chatMsg){
		Object obj = chatMsg.getData();
		if(obj != null){
			List<UserMsg> list=(List<UserMsg>) chatMsg.getData();
			if(list.size()>0){
				String [] nickName=new String[list.size()];
				for(int i=0;i<list.size();i++){
					nickName[i]=list.get(i).getNickname();
				}
				return nickName;
			}
		}
		return new String[]{};
	} 
	
	//获取userId 数字，与昵称相对应
	public static  String[] CastToUserIdArr(ChatMsg chatMsg){
		Object obj = chatMsg.getData();
		if(obj != null){
			List<UserMsg> list=(List<UserMsg>) chatMsg.getData();
			if(list.size()>0){
				String [] nickName=new String[list.size()];
				for(int i=0;i<list.size();i++){
					nickName[i]=list.get(i).getUserId();
				}
				return nickName;
			}
		}
		return new String[]{};
	} 
	
	
	//获取好友列表名单
	public static String[] CastToFriendName(UtilConveyingMsg resp){
		List<UserMsg>  list= (List<UserMsg>) resp.getObject();
		if(list != null && list.size()>0){	
			String [] nickName=new String[list.size()];
			for(int i=0;i<list.size();i++){
				nickName[i]=list.get(i).getNickname();				
			}
			return nickName;
		}
		return new String[]{};
	}
	//获取好友id列表
	public static String[] CastToFriendId(UtilConveyingMsg resp){
		List<UserMsg>  list= (List<UserMsg>) resp.getObject();
		if(list != null && list.size()>0){	
			String [] nickName=new String[list.size()];
			for(int i=0;i<list.size();i++){
				nickName[i]=list.get(i).getUserId();				
			}
			return nickName;
		}
		return new String[]{};
	}
	
	public static HashMap<String, String> putOnLine(UtilConveyingMsg resp){
		HashMap<String, String> online=new HashMap<String, String>();
		List<UserMsg>  list= (List<UserMsg>) resp.getObject();
		if(list != null && list.size()>0){	
			for(int i=0;i<list.size();i++){
				String userid=list.get(i).getUserId();
				String isonline=list.get(i).getIsOnline();
				online.put(userid, isonline);
			}
		}
		return online;
	}
	
	
	//设置mainbody里面的好友列表相关信息
	public static void dealFriendListinfo(MainBody body){
		FriendMsg friendMsg=new FriendMsg(Session.userId);
//		friendMsg.setUserId(Session.userId);
		UtilConveyingMsg resp=ConnectionServer.requestServer("0006", friendMsg);
		String[] friends=ChatUtil.CastToFriendName(resp);
		String[] friendId=ChatUtil.CastToFriendId(resp);
		body.setIsOnLine(ChatUtil.putOnLine(resp));
		body.setFriends(friends);
		body.setFriendsId(friendId);
	}
	
	//返回字符串在数组中的下标
	public static int getIndex(String[] arr,String str ){
		int index=-1;
		for(int i=0 ; i<arr.length ; i++){
			if(str.equals(arr[i])){
				index=i;
			}
		}
		return index;
	}
	
}
