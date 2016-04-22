package com.froad.CB.po;
	/**
	 * 类描述：预售商品图片表
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2014 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2014-2-28 上午09:29:03 
	 */
public class GoodsPresellRackImages {
	private Integer id;
	private String goodsPresellRackId;//预售商品ID
	private String imagesUrl;//图片路径
	private String state;	 //状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private String remark;//备注

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getGoodsPresellRackId() {
		return goodsPresellRackId;
	}

	public void setGoodsPresellRackId(String goodsPresellRackId) {
		this.goodsPresellRackId = goodsPresellRackId;
	}

	public String getImagesUrl() {
		return imagesUrl;
	}

	public void setImagesUrl(String imagesUrl) {
		this.imagesUrl = imagesUrl;
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

}
