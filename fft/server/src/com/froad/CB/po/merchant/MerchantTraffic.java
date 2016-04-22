package com.froad.CB.po.merchant;

import java.io.Serializable;


	/**
	 * 类描述：商户交通信息实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:33:29 PM 
	 */
public class MerchantTraffic implements Serializable{
    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;
    private String merchantId;//关联 FBU_Merchant
    private String photoUrl;//展示大图url
    private String photoFormat1;//展示大图规格(长*宽*高)1
    private String photoFormat2;//展示大图规格(长*宽*高)2
    private String trafficMAPId;//关联地图id
    private String txt1;//介绍文本1
    private String txt2;
    private String txt3;
    private String state;//状态(10-创建，20-录入，30-启用，40-停用，50-删除)
    private String createTime;
    private String updateTime;
    private String remark;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getPhotoUrl() {
		return photoUrl;
	}

	public void setPhotoUrl(String photoUrl) {
		this.photoUrl = photoUrl;
	}

	public String getPhotoFormat1() {
		return photoFormat1;
	}

	public void setPhotoFormat1(String photoFormat1) {
		this.photoFormat1 = photoFormat1;
	}

	public String getPhotoFormat2() {
		return photoFormat2;
	}

	public void setPhotoFormat2(String photoFormat2) {
		this.photoFormat2 = photoFormat2;
	}

	public String getTrafficMAPId() {
		return trafficMAPId;
	}

	public void setTrafficMAPId(String trafficMAPId) {
		this.trafficMAPId = trafficMAPId;
	}

	public String getTxt1() {
		return txt1;
	}

	public void setTxt1(String txt1) {
		this.txt1 = txt1;
	}

	public String getTxt2() {
		return txt2;
	}

	public void setTxt2(String txt2) {
		this.txt2 = txt2;
	}

	public String getTxt3() {
		return txt3;
	}

	public void setTxt3(String txt3) {
		this.txt3 = txt3;
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