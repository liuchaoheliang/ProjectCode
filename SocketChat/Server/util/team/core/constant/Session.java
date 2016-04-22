package team.core.constant;

import java.util.HashMap;
import java.util.Map;

import org.zxy.sqlhelper.api.HelperSQL;

import team.core.bean.os.LoginBean;
import team.core.bean.os.UserInfoBean;
import team.core.beans.conveying.UserMsg;


public class Session {

	private static Map<String, UserMsg> onlineUsers = new HashMap<String, UserMsg>();
	
	public static void saveUser(String userId){
		onlineUsers.put(userId,getUserMsgByUserId(userId));
	}
	public static void removeUser(String userId){
		onlineUsers.remove(userId);
	}
	public static Map<String, UserMsg> getOnLineUsers(){
		return onlineUsers;
	}
	
	public static UserMsg getUserMsgByUserId(String userId){
		if(onlineUsers.get(userId) != null){
			return onlineUsers.get(userId);
		}		
		LoginBean user = new LoginBean();
		user.setId(userId);
		user = (LoginBean)HelperSQL.select(user, new String[]{"id","name"}, null);
		
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setLid(user.getId());
		userInfo = (UserInfoBean)HelperSQL.select(userInfo, new String[]{"id","mail","sex","nickname","autograph"}, null);
		
		UserMsg userMsg = new UserMsg();
		
		userMsg.setUserId(user.getId());
		userMsg.setName(user.getName());
		
		userMsg.setUserInfoId(userInfo.getId());
		userMsg.setMail(userInfo.getMail());
		userMsg.setSex(userInfo.getSex());
		userMsg.setNickname(userInfo.getNickname());
		userMsg.setAutograph(userInfo.getAutograph());
		userMsg.setIsOnline("Y");
		return userMsg;
	}
	public static UserMsg getUserMsgByUserIdForFdList(String userId){
		if(onlineUsers.get(userId) != null){
			return onlineUsers.get(userId);
		}		
		LoginBean user = new LoginBean();
		user.setId(userId);
		user = (LoginBean)HelperSQL.select(user, new String[]{"id","name"}, null);
		
		UserInfoBean userInfo = new UserInfoBean();
		userInfo.setLid(user.getId());
		userInfo = (UserInfoBean)HelperSQL.select(userInfo, new String[]{"id","mail","sex","nickname","autograph"}, null);
		
		UserMsg userMsg = new UserMsg();
		
		userMsg.setUserId(user.getId());
		userMsg.setName(user.getName());
		
		userMsg.setUserInfoId(userInfo.getId());
		userMsg.setMail(userInfo.getMail());
		userMsg.setSex(userInfo.getSex());
		userMsg.setAutograph(userInfo.getAutograph());
		userMsg.setNickname(userInfo.getNickname());
		return userMsg;
	}
	
}
