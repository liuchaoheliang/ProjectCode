package team.core.bean.os;


public class LoginBean {
	

	private String id;
	
	private String name;
	
	private String pwd;
	
	private String lastTryTime;
	
	private String state;
	
	private Integer loginFailTimes;
	
	private String unlockTime;
		
	private String createTime;
	
	private String updateTime;

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public String getLastTryTime() {
		return lastTryTime;
	}
	public void setLastTryTime(String lastTryTime) {
		this.lastTryTime = lastTryTime;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getLoginFailTimes() {
		return loginFailTimes;
	}
	public void setLoginFailTimes(Integer loginFailTimes) {
		this.loginFailTimes = loginFailTimes;
	}
	public String getUnlockTime() {
		return unlockTime;
	}
	public void setUnlockTime(String unlockTime) {
		this.unlockTime = unlockTime;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	
}
