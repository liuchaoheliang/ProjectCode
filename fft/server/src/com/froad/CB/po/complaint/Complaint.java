package com.froad.CB.po.complaint;

import java.io.Serializable;
import java.util.List;

import com.froad.CB.common.Pager;
import com.froad.CB.po.ComplaintReply;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.user.User;

public class Complaint extends Pager implements Serializable{
	
	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String complaintCategoryId;//分类ID
	private String orderId;//订单ID
	private String content;//内容
	private String isRead; //是否已读 10-未读 20-已读
	private String isReply;//是否回复 10-未回复 20-已回复
	private String deleteStatus;//删除状态 10-未删除 20-已删除
	private String fromUserId ; //发出会员,为null时表示管理员
	private String toUserId;	//接收会员,为null时表示管理员
	private String state;
	private String createTime; 
	private String updateTime;
	private String remark;
	private ComplaintCategory complaintCategory;
	private User fromUser;
	private User toUser;
	private Merchant merchant;
	private List<ComplaintReply> replyList;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getComplaintCategoryId() {
		return complaintCategoryId;
	}
	public void setComplaintCategoryId(String complaintCategoryId) {
		this.complaintCategoryId = complaintCategoryId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getIsRead() {
		return isRead;
	}
	public void setIsRead(String isRead) {
		this.isRead = isRead;
	}
	public String getIsReply() {
		return isReply;
	}
	public void setIsReply(String isReply) {
		this.isReply = isReply;
	}
	public String getDeleteStatus() {
		return deleteStatus;
	}
	public void setDeleteStatus(String deleteStatus) {
		this.deleteStatus = deleteStatus;
	}
	public String getFromUserId() {
		return fromUserId;
	}
	public void setFromUserId(String fromUserId) {
		this.fromUserId = fromUserId;
	}
	public String getToUserId() {
		return toUserId;
	}
	public void setToUserId(String toUserId) {
		this.toUserId = toUserId;
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
	public ComplaintCategory getComplaintCategory() {
		return complaintCategory;
	}
	public void setComplaintCategory(ComplaintCategory complaintCategory) {
		this.complaintCategory = complaintCategory;
	}
	public User getFromUser() {
		return fromUser;
	}
	public void setFromUser(User fromUser) {
		this.fromUser = fromUser;
	}
	public User getToUser() {
		return toUser;
	}
	public void setToUser(User toUser) {
		this.toUser = toUser;
	}
	
	public Merchant getMerchant() {
		return merchant;
	}
	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
	public List<ComplaintReply> getReplyList() {
		return replyList;
	}
	public void setReplyList(List<ComplaintReply> replyList) {
		this.replyList = replyList;
	}
	
}
