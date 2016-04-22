package com.froad.cbank.coremodule.normal.boss.pojo.ad;

/**
 * 广告详情响应对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月23日 上午11:08:40
 */
public class AdDetailRes {
	private long id;//广告ID
	private String clientId;//客户端ID
	private String clientName;//客户端名称
	private String title;//广告标题
	private long adLocationId;//广告位ID
	private String adLocationName;//广告位名称
	private String type;//类型 0-文本 1-图片 2-flash 3-链接
	private int orderSn;//序号
	private long beginTime;//开始时间
	private long endTime;//结束时间
	private String paramOneType;//第一参数-类型
	private String paramTwoType;//第二参数-类型
	private String paramThreeType;//第三参数-类型
	private String paramOneValue;//第一参数-内容
	private String paramTwoValue;//第二参数-内容
	private String paramThreeValue;//第三参数-内容
	private String paramOneName;//第一参数-名称
	private String paramTwoName;//第二参数-名称
	private String paramThreeName;//第三参数-名称
	private String content;//内容
	private String link;//链接地址
	private String path;//路径
	private Boolean isBlankTarge;//是否在新窗打开
	private String enableStatus;//启用状态 0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中
	private int clickCount;//点击次数
	private String description;//描述
	private String terminalType;//终端类型
	private String positionPage;//页面标识
	
	public String getClientName() {
		return clientName;
	}
	public void setClientName(String clientName) {
		this.clientName = clientName;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(long beginTime) {
		this.beginTime = beginTime;
	}
	public long getEndTime() {
		return endTime;
	}
	public void setEndTime(long endTime) {
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
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getAdLocationId() {
		return adLocationId;
	}
	public void setAdLocationId(long adLocationId) {
		this.adLocationId = adLocationId;
	}
	public String getAdLocationName() {
		return adLocationName;
	}
	public void setAdLocationName(String adLocationName) {
		this.adLocationName = adLocationName;
	}
	public int getOrderSn() {
		return orderSn;
	}
	public void setOrderSn(int orderSn) {
		this.orderSn = orderSn;
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
	public String getParamOneName() {
		return paramOneName;
	}
	public void setParamOneName(String paramOneName) {
		this.paramOneName = paramOneName;
	}
	public String getParamTwoName() {
		return paramTwoName;
	}
	public void setParamTwoName(String paramTwoName) {
		this.paramTwoName = paramTwoName;
	}
	public String getParamThreeName() {
		return paramThreeName;
	}
	public void setParamThreeName(String paramThreeName) {
		this.paramThreeName = paramThreeName;
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
