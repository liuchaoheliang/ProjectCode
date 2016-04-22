package com.froad.cbank.coremodule.normal.boss.pojo.ad;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.framework.common.valid.Regular;

/**
 * 广告新增请求对象
 * @author luwanquan@f-road.com.cn
 * @date 2015年9月21日 下午4:39:56
 */
public class AdAddReq {
	@NotEmpty("客户端ID为空")
	private String clientId;//客户端ID
	@NotEmpty("广告名称为空")
	@Regular(reg = "^[\\s\\S]{1,30}$", value = "广告名称限30字符内")
	private String title;//标题
	private Long adLocationId;//广告位ID
	@NotEmpty("广告类型为空")
	@Regular(reg = "^[0|1|2|3]$", value = "请传入有效广告类型")
	private String type;//类型  0-文本 1-图片 2-flash 3-链接 
	private Integer orderSn;//序号
	@NotEmpty("开始时间为空")
	private String beginTime;//开始时间
	@NotEmpty("结束时间为空")
	private String endTime;//结束时间
	private String paramOneValue;//第一参数-内容
	private String paramTwoValue;//第二参数-内容
	private String paramThreeValue;//第三参数-内容
	@Regular(reg = "^[\\s\\S]{0,100}$", value = "广告内容限100字符内")
	private String content;//内容
	@Regular(reg = "^[\\s\\S]{0,300}$", value = "链接限300字符内")
	private String link;//链接地址
	private String path;//路径
	@NotEmpty("是否启用为空")
	@Regular(reg = "^[0|1]$", value = "请传入有效启用状态值")
	private String enableStatus;//启用状态 0-启用 1-禁用 2-新增审核中 3-编辑审核中 4-禁用审核中
	@Regular(reg = "^[\\s\\S]{0,300}$", value = "描述限300字符内")
	private String description;//描述
	private Boolean isBlankTarge;//是否新窗口打开
	
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
	public String getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(String beginTime) {
		this.beginTime = beginTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getParamOneValue() {
		return paramOneValue;
	}
	public void setParamOneValue(String paramOneValue) {
		this.paramOneValue = StringUtil.isBlank(paramOneValue) ? null : paramOneValue;
	}
	public String getParamTwoValue() {
		return paramTwoValue;
	}
	public void setParamTwoValue(String paramTwoValue) {
		this.paramTwoValue = StringUtil.isBlank(paramTwoValue) ? null : paramTwoValue;
	}
	public String getParamThreeValue() {
		return paramThreeValue;
	}
	public void setParamThreeValue(String paramThreeValue) {
		this.paramThreeValue = StringUtil.isBlank(paramThreeValue) ? null : paramThreeValue;
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
	public String getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(String enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Boolean getIsBlankTarge() {
		return isBlankTarge;
	}
	public void setIsBlankTarge(Boolean isBlankTarge) {
		this.isBlankTarge = isBlankTarge;
	}
}
