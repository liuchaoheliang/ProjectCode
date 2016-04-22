package team.core.fun.common;

import java.util.ArrayList;
import java.util.List;

import org.zxy.common.CommonFn;
import org.zxy.mail.api.Mail;
import org.zxy.sqlhelper.api.HelperSQL;

import team.core.bean.os.LoginBean;
import team.core.bean.os.UserInfoBean;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;
import team.core.fn.util.MailConfig;
import team.core.fn.util.TimeFun;


public class Register {
	
	public UtilConveyingMsg register(Object data){
		UserMsg userMsg = (UserMsg)data;
		LoginBean user = new LoginBean();
		user.setName(userMsg.getName());
		
		if(HelperSQL.select(user, new String[]{"name"}, null) != null){
			return new UtilConveyingMsg("0003", null);
			
		}else{
			UserInfoBean userInfo = new UserInfoBean();
			userInfo.setMail(userMsg.getMail());
			
			if(HelperSQL.select(userInfo, new String[]{"id"}, null) == null ){
				String time = TimeFun.getNowTimeType();			
				user.setPwd(userMsg.getPwd());			
				user.setCreateTime(time);
				user.setUpdateTime(time);
				user.setId(HelperSQL.uuid());
				user.setState("0");
				
				
				userInfo.setId(HelperSQL.uuid());
				userInfo.setLid(user.getId());
				
				userInfo.setNickname(userMsg.getNickname());
				userInfo.setSex(userMsg.getSex());
				userInfo.setUpdateTime(time);
				userInfo.setCreateTime(time);
				
				List<Object> beans = new ArrayList<Object>();
				beans.add(user);
				beans.add(userInfo);
				
				if(HelperSQL.inserts(beans)){
					if(!CommonFn.emptyString(userInfo.getMail())){
						String[] msgVal = new String[]{
								userInfo.getNickname(),
								"暂无链接",
								"暂无链接",
								"暂未设置过期时间",
								"暂无重新获取地址",
								"暂无主页地址",
								"暂无帮助连接","暂无帮助连接"
							};
						Mail.sendMail(
								userInfo.getMail(),
								MailConfig.serviceMail,
								MailConfig.servicePwd,
								MailConfig.getRegisterMailTitle(),
								MailConfig.getRegisterMail(msgVal)
								);
					}
					//操作成功
					return new UtilConveyingMsg("0004", null);
				}else{
					//向数据库添加数据失败
					return new UtilConveyingMsg("0005", null);
				}
			}else{
				//邮箱存在
				return new UtilConveyingMsg("0006", null);
			}			
		}		
	}
}
