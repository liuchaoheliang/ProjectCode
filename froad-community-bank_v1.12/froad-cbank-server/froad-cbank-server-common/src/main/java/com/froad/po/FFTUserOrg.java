package com.froad.po;

/**
 * 用户组织po
 * @author froad-ll
 * @createtime 20160104 
 *
 */
public class FFTUserOrg implements java.io.Serializable {

    
    private Long id;                    //id(自增主键)
	private String orgId;				//组织Id
	private Long userId;				//用户Id
	
	
	public FFTUserOrg(){}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getOrgId() {
		return orgId;
	}


	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}


	public Long getUserId() {
		return userId;
	}


	public void setUserId(Long userId) {
		this.userId = userId;
	}

	
	
    
}