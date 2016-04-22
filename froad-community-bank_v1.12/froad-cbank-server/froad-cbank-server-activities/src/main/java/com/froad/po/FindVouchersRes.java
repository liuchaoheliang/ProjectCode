package com.froad.po;

import java.io.Serializable;
import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.VouchersInfoVo;

public class FindVouchersRes implements Serializable {

	/**
	 * @Fields serialVersionUID : TODO
	 */

	private static final long serialVersionUID = 1L;
	/** 请求id */
	private String reqId;
	/** 客户端id */
	private String clientId;
	/** 会员编号 */
	private Long memberCode;
	/** 分页 */
	private Page page;
	/** 代金券 列表 */
	private List<VouchersInfo> vouchersInfoList;
	public String getReqId() {
		return reqId;
	}
	public void setReqId(String reqId) {
		this.reqId = reqId;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public Long getMemberCode() {
		return memberCode;
	}
	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}
	public Page getPage() {
		return page;
	}
	public void setPage(Page page) {
		this.page = page;
	}
	public List<VouchersInfo> getVouchersInfoList() {
		return vouchersInfoList;
	}
	public void setVouchersInfoList(List<VouchersInfo> vouchersInfoList) {
		this.vouchersInfoList = vouchersInfoList;
	}



}
