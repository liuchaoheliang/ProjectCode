package team.core.fun.users;

import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;
import team.core.constant.Session;

public class AboutUser {

	/**
	 * 查询用户详细信息
	 */
	public UtilConveyingMsg getUserDetailInfo(Object data){
		return new UtilConveyingMsg("200", Session.getUserMsgByUserId(((UserMsg)data).getUserId()));
	}
}
