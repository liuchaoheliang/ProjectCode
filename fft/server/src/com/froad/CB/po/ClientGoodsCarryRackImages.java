package com.froad.CB.po;

/** 
 * @author TXL 
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class ClientGoodsCarryRackImages {
	private Integer id;
	private String clientGoodsCarryRackId;//提现商品ID
	private String imagesUrl;//图片路径
	private String state;	 //状态(10-创建，20-录入，30-启用，40-停用，50-删除)
	private String createTime;//创建时间
	private String updateTime;//更新时间
	private String remark;//备注
	private String pixel;//图片大小 如：120*120

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getClientGoodsCarryRackId() {
		return clientGoodsCarryRackId;
	}

	public void setClientGoodsCarryRackId(String clientGoodsCarryRackId) {
		this.clientGoodsCarryRackId = clientGoodsCarryRackId;
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

	public String getPixel() {
		return pixel;
	}

	public void setPixel(String pixel) {
		this.pixel = pixel;
	}

}
