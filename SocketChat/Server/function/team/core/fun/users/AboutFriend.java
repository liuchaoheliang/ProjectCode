package team.core.fun.users;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zxy.sqlhelper.api.HelperSQL;

import team.core.bean.os.FriendBean;
import team.core.bean.os.UserInfoBean;
import team.core.beans.conveying.FriendMsg;
import team.core.beans.conveying.UserMsg;
import team.core.beans.conveying.UtilConveyingMsg;
import team.core.constant.Session;
import team.core.fn.util.TimeFun;

public class AboutFriend {

	/**
	 * 添加好友
	 */
	public UtilConveyingMsg addFriend(Object data){
		
		FriendMsg friendMsg = (FriendMsg)data;//获取客户端数据
		FriendBean friendBean = new FriendBean();
		
		String userInfoId = ((UserInfoBean)HelperSQL.select(new UserInfoBean(friendMsg.getUserId()), new String[]{"id"}, null)).getId();
		String friendInfoId = ((UserInfoBean)HelperSQL.select(new UserInfoBean(friendMsg.getFriendId()), new String[]{"id"}, null)).getId();
		
		friendBean.setIid(userInfoId);
		friendBean.setFiid(friendInfoId);
		
		friendBean = (FriendBean)HelperSQL.select(friendBean, new String[]{"id","state"}, null);
		
		if(friendBean == null){
			friendBean = new FriendBean();
			//向自己的好友关系表添加数据
			friendBean.setIid(userInfoId);
			friendBean.setFiid(friendInfoId);
			friendBean.setId(HelperSQL.uuid());
			String time = TimeFun.getNowTimeType();
			
			friendBean.setUpdateTime(time);
			friendBean.setCreateTime(time);
			
			//向对方好友关系表添加数据
			FriendBean friend = new FriendBean();
			friend.setId(HelperSQL.uuid());
			friend.setIid(friendInfoId);
			friend.setFiid(userInfoId);
			friend.setUpdateTime(time);
			friend.setCreateTime(time);
			time = null;
			List<Object> inserts = new ArrayList<Object>();
			inserts.add(friendBean);
			inserts.add(friend);
			if(HelperSQL.inserts(inserts)){
				return new UtilConveyingMsg("0007", null);
			}else{
				return new UtilConveyingMsg("0008", null);
			}
		}else if("0".equals(friendBean.getState())){
			return new UtilConveyingMsg("0011", null);
		}else{
			FriendBean condition = new FriendBean();
			condition.setId(friendBean.getId());
			friendBean.setId(null);
			friendBean.setState("0");
			friendBean.setUpdateTime(TimeFun.getNowTimeType());
			Map<Object, Object> update = new HashMap<Object, Object>();
			update.put(friendBean, condition);
			
			FriendBean friendBean_ = new FriendBean();
			friendBean_.setIid(friendInfoId);
			friendBean_.setFiid(userInfoId);
			
			friendBean_ = (FriendBean)HelperSQL.select(friendBean_, new String[]{"id","state"}, null);			
			FriendBean condition_ = new FriendBean();
			condition_.setId(friendBean_.getId());
			FriendBean friendBeanupdate_ = new FriendBean();
			friendBeanupdate_.setState("0");
			friendBeanupdate_.setUpdateTime(TimeFun.getNowTimeType());
			update.put(friendBeanupdate_, condition_);
			
			if(HelperSQL.update(update)){
				return new UtilConveyingMsg("0007", null);
			}else{
				return new UtilConveyingMsg("0008", null);
			}
		}
	}
	
	
	/**
	 * 删除好友
	 */
	public UtilConveyingMsg removeFriend(Object data){
		
		FriendMsg friendMsg = (FriendMsg)data;
		
		FriendBean friendBean = new FriendBean();
		String userInfoId = ((UserInfoBean)HelperSQL.select(new UserInfoBean(friendMsg.getUserId()), new String[]{"id"}, null)).getId();
		String friendInfoId = ((UserInfoBean)HelperSQL.select(new UserInfoBean(friendMsg.getFriendId()), new String[]{"id"}, null)).getId();
		
		friendBean.setIid(userInfoId);
		friendBean.setFiid(friendInfoId);
		friendBean = (FriendBean)HelperSQL.select(friendBean, new String[]{"id"}, null);
		
		FriendBean friendBeanUpteade = new FriendBean();
		friendBeanUpteade.setState("1");
		friendBeanUpteade.setUpdateTime(TimeFun.getNowTimeType());
		
		Map<Object, Object> updateBean = new HashMap<Object, Object>();
		updateBean.put(friendBeanUpteade, friendBean);
		
		FriendBean friendBean_ = new FriendBean();
		friendBean_.setIid(friendInfoId);
		friendBean_.setFiid(userInfoId);
		FriendBean friendBeanUpteade_ = new FriendBean();
		friendBeanUpteade_.setState("1");
		friendBeanUpteade_.setUpdateTime(TimeFun.getNowTimeType());
		updateBean.put(friendBeanUpteade_, friendBean_);
		
		if(HelperSQL.update(updateBean)){
			return new UtilConveyingMsg("0009", null);
		}else{
			return new UtilConveyingMsg("0010", null);
		}
	}
	
	/**
	 * 获取好友列表
	 */
	public UtilConveyingMsg getFriendList(Object data){		
		
		FriendMsg friendMsg = (FriendMsg)data;		
		String userInfoId = ((UserInfoBean)HelperSQL.select(new UserInfoBean(friendMsg.getUserId()), new String[]{"id"}, null)).getId();
		FriendBean friendBean = new FriendBean();
		friendBean.setState("0");		
		
		friendBean.setIid(userInfoId);
		@SuppressWarnings("unchecked")
		List<FriendBean> friendBeans = (List<FriendBean>)HelperSQL.selects(friendBean, new String[]{"fiid"}, null);
		List<UserMsg> userMsgs = new ArrayList<UserMsg>();
		UserInfoBean userInfoBean = null;
		for (FriendBean fb : friendBeans) {
			userInfoBean = new UserInfoBean();
			userInfoBean.setId(fb.getFiid());
			userInfoBean = (UserInfoBean)HelperSQL.select(userInfoBean, new String[]{"lid"}, null);
			userMsgs.add(Session.getUserMsgByUserIdForFdList(userInfoBean.getLid()));
		}
		return new UtilConveyingMsg("200", userMsgs);
	}
}
