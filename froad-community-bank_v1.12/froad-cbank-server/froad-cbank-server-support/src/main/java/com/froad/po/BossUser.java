package com.froad.po;
/**
 * CbBossUser entity. @author MyEclipse Persistence Tools
 */
public class BossUser implements java.io.Serializable {

	// Fields

	private Long id;
	private String username;
	private String password;
	private Long roleId;
	private String mobile;
	private String email;
	private String name;
	private String department;
	private String position;
	private Boolean isEnable;
	private Boolean isReset;
	private String remark;
	private String roleName;

	// Constructors

	/** default constructor */
	public BossUser() {
	}

	/** minimal constructor */
	public BossUser(String username, String password, Long roleId, Boolean isEnable, Boolean isReset) {
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.isEnable = isEnable;
		this.isReset = isReset;
	}

	/** full constructor */
	public BossUser(String username, String password, Long roleId, String mobile, String email, String name, String department, String position, Boolean isEnable, Boolean isReset) {
		this.username = username;
		this.password = password;
		this.roleId = roleId;
		this.mobile = mobile;
		this.email = email;
		this.name = name;
		this.department = department;
		this.position = position;
		this.isEnable = isEnable;
		this.isReset = isReset;
	}

	// Property accessors
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return this.department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public Boolean getIsEnable() {
		return this.isEnable;
	}

	public void setIsEnable(Boolean isEnable) {
		this.isEnable = isEnable;
	}

	public Boolean getIsReset() {
		return this.isReset;
	}

	public void setIsReset(Boolean isReset) {
		this.isReset = isReset;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

}