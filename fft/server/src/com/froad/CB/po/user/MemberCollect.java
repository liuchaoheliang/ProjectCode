package com.froad.CB.po.user;

import com.froad.CB.common.Pager;
import com.froad.CB.po.merchant.Merchant;


	/**
	 * 类描述：会员收藏
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Mar 11, 2013 4:44:36 PM 
	 */
public class MemberCollect extends Pager{
    
    
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;

	private Integer id;

    private String userid;

    private String merchantId;
    
    private Merchant merchant;

    private String state;
    
    private String createTime;
    
    private String updateTime;

    private String remark;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public String getUserid() {
        return userid;
    }

    
    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

    
    public String getMerchantId() {
        return merchantId;
    }

    
    public void setMerchantId(String merchantId) {
        this.merchantId = merchantId == null ? null : merchantId.trim();
    }

    
    public String getState() {
        return state;
    }

    
    public void setState(String state) {
        this.state = state == null ? null : state.trim();
    }

    
    public String getCreateTime() {
        return createTime;
    }

    
    public void setCreateTime(String createTime) {
        this.createTime = createTime == null ? null : createTime.trim();
    }

    
    public String getUpdateTime() {
        return updateTime;
    }

    
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    
    public String getRemark() {
        return remark;
    }

    
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }


	public Merchant getMerchant() {
		return merchant;
	}


	public void setMerchant(Merchant merchant) {
		this.merchant = merchant;
	}
}