package com.froad.CB.po.announcement;

import java.io.Serializable;

import com.froad.CB.common.Pager;


/** 
 * 类描述：公告类
 * @author 李巧鹏 E-mail:liqiaopeng@f-road.com.cn 
 * @version 创建时间：2012-9-11 下午04:08:00 
 */
public class Announcement extends Pager implements Serializable{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	private Integer id;//
	private String title;//公告标题
	private String content;//公告内容  储存内容为html代码格式，否则按默认格式显示。
	private String releaseDate;//公告发布日期
	private String endDate;//结束日期
	private String type;//公告类型 0-默认
	private String important;//是否关键标识  默认为1,当为0时，视为关键公告，在前端会做处理（高亮，加粗等）。
	private String releaser;//发布人
	private Integer page;//
	private Integer maxCounts;//
	private String selType;
	private String respCode;
	private String respMsg;
	private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//
	private String updateTime;//
	private String remark;//
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(String releaseDate) {
		this.releaseDate = releaseDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getImportant() {
		return important;
	}
	public void setImportant(String important) {
		this.important = important;
	}
	public String getReleaser() {
		return releaser;
	}
	public void setReleaser(String releaser) {
		this.releaser = releaser;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}
	public Integer getMaxCounts() {
		return maxCounts;
	}
	public void setMaxCounts(Integer maxCounts) {
		this.maxCounts = maxCounts;
	}
	public String getSelType() {
		return selType;
	}
	public void setSelType(String selType) {
		this.selType = selType;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getRespMsg() {
		return respMsg;
	}
	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}
	
}
