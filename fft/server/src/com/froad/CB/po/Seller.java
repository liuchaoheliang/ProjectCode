package com.froad.CB.po;

import java.util.List;

import com.froad.CB.po.user.User;

/**  
 * @author Qiaopeng.Lee 
 * @date 2013-1-29  
 * @version 1.0
 */
public class Seller {
	private Integer id;//主键ID
	private String userId;//会员ID
	private String merchantId;//商户ID
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private String remark;//备注
	private String sellerType;//卖家类型 与TranCommand里的transType一致
	private String sellerTypeName;//卖家类型 名字
	private String isInternal;//是否内部卖家 0否 1是
	private User user;
	private List<SellerChannel> sellerChannelList;
	
	
	public String getSellerTypeName() {
		return sellerTypeName;
	}
	public void setSellerTypeName(String sellerTypeName) {
		this.sellerTypeName = sellerTypeName;
	}
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
	public String getMerchantId() {
		return merchantId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
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
	public List<SellerChannel> getSellerChannelList() {
		return sellerChannelList;
	}
	public void setSellerChannelList(List<SellerChannel> sellerChannelList) {
		this.sellerChannelList = sellerChannelList;
	}
	public String getSellerType() {
		return sellerType;
	}
	public void setSellerType(String sellerType) {
		this.sellerType = sellerType;
	}
	public String getIsInternal() {
		return isInternal;
	}
	public void setIsInternal(String isInternal) {
		this.isInternal = isInternal;
	}
	
	
}
