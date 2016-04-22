package com.froad.cbank.coremodule.normal.boss.pojo.ad;

/**
 * 广告位响应对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月22日 上午11:44:25
 */
public class AdPositionDetailRes {
	private Long id;//广告位ID
	private String name;//名称
	private String terminalType;//终端类型 1-pc 2-android 3-ios
	private String positionPage;//页面标识
	private int sizeLimit;//大小限制-单位为K
	private int width;//宽度
	private int height;//高度
	private String paramOneType;//第一附加参数类型 1-地区 2-商户类型 3-商品类型
	private String paramTwoType;//第二附加参数类型 1-地区 2-商户类型 3-商品类型
	private String paramThreeType;//第三附加参数类型 1-地区 2-商户类型 3-商品类型
	private String description;//描述
	private String enableStatus;//启用状态 0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中
	
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
	public int getSizeLimit() {
		return sizeLimit;
	}
	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
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
