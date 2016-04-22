/**
 * <p>Project: ubank</p>
 * <p>module: coremouule</p>
 * <p>@version: Copyright © 2015 F-Road All Rights Reserved</p>
 */
package com.froad.cbank.coremodule.normal.boss.pojo.ad;

import java.io.Serializable;
import java.util.List;

import com.froad.cbank.coremodule.framework.common.valid.NotEmpty;
import com.froad.cbank.coremodule.normal.boss.pojo.FileVo;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;


/**
 * 
 * <p>标题: —— 标题</p>
 * <p>说明: —— 简要描述职责、应用范围、使用注意事项等</p>
 * <p>创建时间：2015-5-11下午2:38:47</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
public class AdRes extends Page implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public long id;
	public String clientId;
	
	@NotEmpty(value = "广告标题不能为空")
	public String title;
	
	@NotEmpty(value = "广告类型不能为空")
	public int type;
	
	public String content;
	public String path;
	public String link;
	public Integer isEnabled;
	public boolean isBlankTarge;
	public int clickCount;
	
	@NotEmpty(value = "广告位不能为空")
	public long adPositionId;
	
	public String adPositionName;//广告位名称
	public int width;  //广告位宽
	public int height;//广告位高
	public int sizeLimit;//广告大小限制
	
	private String beginDate; 
	
	private String terminalDate;
	
	private List<FileVo> files;
	
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	
	public String getTerminalDate() {
		return terminalDate;
	}
	public void setTerminalDate(String terminalDate) {
		this.terminalDate = terminalDate;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public Integer getIsEnabled() {
		return isEnabled;
	}
	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}
	public boolean isBlankTarge() {
		return isBlankTarge;
	}
	public void setBlankTarge(boolean isBlankTarge) {
		this.isBlankTarge = isBlankTarge;
	}
	public int getClickCount() {
		return clickCount;
	}
	public void setClickCount(int clickCount) {
		this.clickCount = clickCount;
	}
	public long getAdPositionId() {
		return adPositionId;
	}
	public void setAdPositionId(long adPositionId) {
		this.adPositionId = adPositionId;
	}
	public String getAdPositionName() {
		return adPositionName;
	}
	public void setAdPositionName(String adPositionName) {
		this.adPositionName = adPositionName;
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
	public int getSizeLimit() {
		return sizeLimit;
	}
	public void setSizeLimit(int sizeLimit) {
		this.sizeLimit = sizeLimit;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<FileVo> getFiles() {
		return files;
	}
	public void setFiles(List<FileVo> files) {
		this.files = files;
	}
	
	
}
