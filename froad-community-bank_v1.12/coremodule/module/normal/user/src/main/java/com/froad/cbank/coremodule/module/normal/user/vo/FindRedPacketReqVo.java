package com.froad.cbank.coremodule.module.normal.user.vo;

import java.util.List;

/**  
 * 优惠码校验，向后端请求时封装对象
 * @date 2015年11月25日 下午2:54:28  
 * 项目名称：coremodule-user  
 * @author 周煜涵
 * 文件名称：FindRedPacketReqVo.java  
 */
public class FindRedPacketReqVo {
	
	private Long memberCode;
	private String vouchersId;
	private String orderMoney;
	//默认查询不支持面对面的
	private Boolean isEnableQrcode;
	private String[] sustainActiveIds;
	
	private List<ProductsReqVo> list;

	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public String getVouchersId() {
		return vouchersId;
	}
	public void setVouchersId(String vouchersId) {
		this.vouchersId = vouchersId;
	}
	public String getOrderMoney() {
		return orderMoney;
	}
	public void setOrderMoney(String orderMoney) {
		this.orderMoney = orderMoney;
	}

	public List<ProductsReqVo> getList() {
		return list;
	}
	public void setList(List<ProductsReqVo> list) {
		this.list = list;
	}
	public Boolean getIsEnableQrcode() {
		return isEnableQrcode;
	}
	public void setIsEnableQrcode(Boolean isEnableQrcode) {
		this.isEnableQrcode = isEnableQrcode;
	}
	public String[] getSustainActiveIds() {
		return sustainActiveIds;
	}
	public void setSustainActiveIds(String[] sustainActiveIds) {
		this.sustainActiveIds = sustainActiveIds;
	}
	
}
