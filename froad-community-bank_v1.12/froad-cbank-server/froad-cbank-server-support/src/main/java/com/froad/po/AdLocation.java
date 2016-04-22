package com.froad.po;

import java.io.Serializable;

/**
 * 广告位
 */
public class AdLocation implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	// Fields
	private Long id; // 主键ID 广告位ID
	private String name; // 名称
	private String terminalType; // 终端类型
	private String positionPage; // 页面标识
	private Integer sizeLimit; // 大小限制-单位为k
	private Integer width; // 宽度
	private Integer height; // 高度
	private String paramOneType; // 第一附加参数类型 1-地区 2-商户类型 3-商品类型
	private String paramTwoType; // 第二附加参数类型 1-地区 2-商户类型 3-商品类型
	private String paramThreeType; // 第三附加参数类型 1-地区 2-商户类型 3-商品类型
	private String description; // 描述
	private String enableStatus; // 启用状态 0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中
	
	/** default constructor */
	public AdLocation() {
	}

	/** minimal constructor */
	public AdLocation(String name, String terminalType, String positionPage, Integer sizeLimit, Integer width, Integer height, String enableStatus) {
		this.name = name;
		this.terminalType = terminalType;
		this.positionPage = positionPage; 
		this.sizeLimit = sizeLimit;
		this.width = width;
		this.height = height;
		this.enableStatus = enableStatus;
	}

	/** full constructor */
	public AdLocation(String name, String terminalType, String positionPage, Integer sizeLimit, Integer width, Integer height, String paramOneType, String paramTwoType, String paramThreeType, String description, String enableStatus) {
		this.name = name;
		this.terminalType = terminalType;
		this.positionPage = positionPage; 
		this.sizeLimit = sizeLimit;
		this.width = width;
		this.height = height;
		this.paramOneType = paramOneType;
		this.paramTwoType = paramTwoType;
		this.paramThreeType = paramThreeType;
		this.description = description;
		this.enableStatus = enableStatus;
	}
	
	// Property accessors
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(String terminalType) {
		this.terminalType = terminalType;
	}

	public String getPositionPage() {
		return positionPage;
	}

	public void setPositionPage(String positionPage) {
		this.positionPage = positionPage;
	}

	public Integer getSizeLimit() {
		return sizeLimit;
	}

	public void setSizeLimit(Integer sizeLimit) {
		this.sizeLimit = sizeLimit;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getHeight() {
		return height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public String getParamOneType() {
		return paramOneType;
	}

	public void setParamOneType(String paramOneType) {
		this.paramOneType = paramOneType;
	}

	public String getParamTwoType() {
		return paramTwoType;
	}

	public void setParamTwoType(String paramTwoType) {
		this.paramTwoType = paramTwoType;
	}

	public String getParamThreeType() {
		return paramThreeType;
	}

	public void setParamThreeType(String paramThreeType) {
		this.paramThreeType = paramThreeType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnableStatus() {
		return enableStatus;
	}

	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	
	
}
