package team.core.fun.common;


import org.zxy.sqlhelper.api.HelperSQL;

import team.core.bean.os.LoginBean;
import team.core.bean.os.UserInfoBean;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;


public class Login {

	public UtilConveyingMsg login(Object data){
		LoginBean login = new LoginBean();
		UserMsg userMsg = (UserMsg)data;
		login.setName(userMsg.getName());
		login.setPwd(userMsg.getPwd());
		Object object = HelperSQL.select(login, new String[]{"id","name","pwd"}, null);
		if(object == null){
			//登录失败
			return new UtilConveyingMsg("0001",null);
		}else{
			login = (LoginBean)object;
			//登录成功
			UserInfoBean userInfo = new UserInfoBean();
			userInfo.setLid(login.getId());
			
			userInfo = (UserInfoBean)HelperSQL.select(userInfo, new String[]{"id","nickname","autograph"}, null);
			
			userMsg = new UserMsg();
			userMsg.setUserId(login.getId());
			userMsg.setUserInfoId(userInfo.getLid());
			userMsg.setPwd(login.getPwd());
			userMsg.setNickname(userInfo.getNickname());
			userMsg.setAutograph(userInfo.getAutograph());
			return new UtilConveyingMsg("0002",userMsg);
		}
	}
}
