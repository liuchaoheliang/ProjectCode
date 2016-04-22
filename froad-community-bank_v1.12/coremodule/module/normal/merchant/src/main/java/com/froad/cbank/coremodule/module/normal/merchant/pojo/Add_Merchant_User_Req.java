package com.froad.cbank.coremodule.module.normal.merchant.pojo;

import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;
import com.froad.cbank.coremodule.framework.common.valid.Regulars;

public class Add_Merchant_User_Req extends BasePojo{
	
	@NotEmpty(value="门店不能为空")
	private String outletId; 
	
	
	private String merchantRoleId; 
	
	@NotEmpty(value="登录名不能为空")
	@Regular(reg="^[A-Za-z0-9]{4,16}$",value="登录名格式不正确,只能4-16位字母、数字")
	private String username; 
	
	private String email; 
	
	@NotEmpty(value="手机号不能为空")
	@Regular(reg=Regulars.MOBILE,value="手机号格式不正确")
	private String phone; 
	
	@NotEmpty(value="联系人不能为空")
	private String realname;
	
	
	private Long operatorUserId;
	
	@NotEmpty(value="资源不能为空")
	private List<Long> resourceIds;
	
	
	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getOutletId() {
		return outletId;
	}

	public void setOutletId(String outletId) {
		this.outletId = outletId;
	}

	public String getMerchantRoleId() {
		return merchantRoleId;
	}

	public void setMerchantRoleId(String merchantRoleId) {
		this.merchantRoleId = merchantRoleId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public List<Long> getResourceIds() {
		return resourceIds;
	}

	public void setResourceIds(List<Long> resourceIds) {
		this.resourceIds = resourceIds;
	}

	public Long getOperatorUserId() {
		return operatorUserId;
	}

	public void setOperatorUserId(Long operatorUserId) {
		this.operatorUserId = operatorUserId;
	}
	
}
