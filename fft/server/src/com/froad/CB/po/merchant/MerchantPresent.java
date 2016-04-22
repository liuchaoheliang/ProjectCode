package com.froad.CB.po.merchant;

import java.io.Serializable;


	/**
	 * 类描述：商户介绍实体
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2012 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 13, 2012 5:32:31 PM 
	 */
public class MerchantPresent implements Serializable {
    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String merchantId;//关联FBU_Merchant
    private String photoUrl;//展示大图url
    private String photoFormat;//展示大图规格(长*宽*高)  
    private String txt1;
    private String txt;//介绍文本
    private String youHui;
    private String tagValue;
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

	public String getPhotoFormat() {
		return photoFormat;
	}

	public void setPhotoFormat(String photoFormat) {
		this.photoFormat = photoFormat;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getState() {
		return state;
	}
    public String getYouHui() {
        return youHui;
    }

    public void setYouHui(String youHui) {
        this.youHui = youHui == null ? null : youHui.trim();
    }
    
    public String getTagValue() {
        return tagValue;
    }

    public void setTagValue(String tagValue) {
        this.tagValue = tagValue == null ? null : tagValue.trim();
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

	public String getCreateTime() {
		return createTime;
	}

	public String getTxt1() {
		return txt1;
	}

	public void setTxt1(String txt1) {
		this.txt1 = txt1;
	}

	public void setState(String state) {
		this.state = state;
	}
	
    
}