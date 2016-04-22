package com.froad.po;

/**
 * 组织关系po
 * @author froad-ll
 * @createtime 20160104 
 *
 */
public class FFTOrgRe implements java.io.Serializable {

    
    private Long id;                    //id(自增主键)
	private String orgId;				//组织Id
	private String reOrgId;				//权限组织Id
	private String code;				//组织代码(boss对应主键ID，bank对应机构号，merchant对应商户Id)
	private String platform;			//平台名称(boss、bank、merchant)
	
	
	public FFTOrgRe(){}


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


	public String getReOrgId() {
		return reOrgId;
	}


	public void setReOrgId(String reOrgId) {
		this.reOrgId = reOrgId;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}


	public String getPlatform() {
		return platform;
	}


	public void setPlatform(String platform) {
		this.platform = platform;
	}



	
	
    
}