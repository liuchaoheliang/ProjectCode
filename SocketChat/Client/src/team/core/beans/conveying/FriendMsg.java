package team.core.beans.conveying;

import java.io.Serializable;

public class FriendMsg implements Serializable{

	private static final long serialVersionUID = 1L;

	private String userId; //要加好友的Userid
	
	private String friendId; //好友的Userid

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getFriendId() {
		return friendId;
	}

	public void setFriendId(String friendId) {
		this.friendId = friendId;
	}

	public FriendMsg(String userId) {
		this.userId = userId;
	}

	
	
}
