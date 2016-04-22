package com.froad.po;

import java.io.Serializable;
import java.util.Date;

/**
 * 广告
 */
public class Advertising implements Serializable {

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3899562323951576700L;
	private Long id; // 主键ID
	private String clientId; // 客户端ID
	private String title; // 标题
	private Long adLocationId; // 广告位ID
	private String type; // 类型 0-文本 1-图片 2-flash
	private Integer orderSn; // 序号
	private Date beginTime; // 开始时间
	private Date endTime; // 结束时间
	private String paramOneValue; // 第一参数-内容
	private String paramTwoValue; // 第二参数-内容
	private String paramThreeValue; // 第三参数-内容
	private String content; // 内容
	private String link; // 链接地址
	private String path; // 路径
	private Boolean isBlankTarge; // 是否在新窗打开
	private String enableStatus; // 启用状态 0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中
	private Integer clickCount; // 点击次数
	private String description; // 描述
	private String adLocationName; // 广告位名称
	private String paramOneType; // 第一附加参数类型 1-地区 2-商户类型 3-商品类型
	private String paramTwoType; // 第二附加参数类型 1-地区 2-商户类型 3-商品类型
	private String paramThreeType; // 第三附加参数类型 1-地区 2-商户类型 3-商品类型
	private String terminalType; // 终端类型
	private String positionPage; // 页面标识
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Long getAdLocationId() {
		return adLocationId;
	}
	public void setAdLocationId(Long adLocationId) {
		this.adLocationId = adLocationId;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(Integer orderSn) {
		this.orderSn = orderSn;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public String getParamOneValue() {
		return paramOneValue;
	}
	public void setParamOneValue(String paramOneValue) {
		this.paramOneValue = paramOneValue;
	}
	public String getParamTwoValue() {
		return paramTwoValue;
	}
	public void setParamTwoValue(String paramTwoValue) {
		this.paramTwoValue = paramTwoValue;
	}
	public String getParamThreeValue() {
		return paramThreeValue;
	}
	public void setParamThreeValue(String paramThreeValue) {
		this.paramThreeValue = paramThreeValue;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getLink() {
		return link;
	}
	public void setLink(String link) {
		this.link = link;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public Boolean getIsBlankTarge() {
		return isBlankTarge;
	}
	public void setIsBlankTarge(Boolean isBlankTarge) {
		this.isBlankTarge = isBlankTarge;
	}
	public String getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	public Integer getClickCount() {
		return clickCount;
	}
	public void setClickCount(Integer clickCount) {
		this.clickCount = clickCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getAdLocationName() {
		return adLocationName;
	}
	public void setAdLocationName(String adLocationName) {
		this.adLocationName = adLocationName;
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
	
	
}
