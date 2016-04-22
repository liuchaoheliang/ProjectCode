package team.core.beans.conveying;

import java.io.Serializable;

public class ChatMsg implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String type;//chat|addfd|userlist|outline|refusefd|agreenfd|fdlist : 
						//用户发的普通聊天|添加好友|用户名单|下线通知|拒绝添加好友|同意添加|获取好友列表请求
	private String msg;//发送的具体内容
	private String toSome;//all 或者 私聊的对方的userid
	private String userId;//自己的userId
	private String nickName;
	private Object data;//如果type为userlist 则data为 list<UserMsg>
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getToSome() {
		return toSome;
	}
	public void setToSome(String toSome) {
		this.toSome = toSome;
	}
	public ChatMsg() {
	}
	public ChatMsg(String type, String msg, String toSome) {
		this.type = type;
		this.msg = msg;
		this.toSome = toSome;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public ChatMsg(String userId){
		this.userId = userId;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}	
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	public ChatMsg(String type, String msg, String toSome, String userId,String nickName) {
		this.type = type;
		this.msg = msg;
		this.toSome = toSome;
		this.userId = userId;
		this.nickName = nickName;
	}
	public ChatMsg(String type, String msg, String toSome, String userId) {
		this.type = type;
		this.msg = msg;
		this.toSome = toSome;
		this.userId = userId;
	}
	public ChatMsg(String type,Object data){
		this.type = type;
		this.data = data;
	}
	public ChatMsg(String userId,String nickeName){
		this.userId = userId;
		this.nickName = nickeName;
	}
}
