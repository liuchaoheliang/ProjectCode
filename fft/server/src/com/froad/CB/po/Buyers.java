package com.froad.CB.po;

import java.util.List;

import com.froad.CB.po.user.User;

/** 
 * @author Qiaopeng.Lee 
 * @date 2013-1-29  
 * @version 1.0
 */
public class Buyers {
	private Integer id;//主键ID
	private String userId;//会员ID
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private String remark;//备注
	private User user;
	private List<BuyerChannel> buyerChannelList;
	
	/**用作接口的返回***/
	private String respCode;
	
	private String respMsg;
	
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
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
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<BuyerChannel> getBuyerChannelList() {
		return buyerChannelList;
	}
	public void setBuyerChannelList(List<BuyerChannel> buyerChannelList) {
		this.buyerChannelList = buyerChannelList;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
}
