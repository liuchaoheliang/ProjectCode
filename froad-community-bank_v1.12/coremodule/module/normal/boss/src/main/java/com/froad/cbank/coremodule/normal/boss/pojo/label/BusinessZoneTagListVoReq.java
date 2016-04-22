package com.froad.cbank.coremodule.normal.boss.pojo.label;

import com.froad.thrift.vo.PageVo;

/**
 * 商户商圈标列表签查询req
 * @author liaopeixin
 *	@date 2015年10月22日 下午4:52:17
 */
public class BusinessZoneTagListVoReq extends PageVo{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**客户端号*/
	private String clientId;

	/**状态：全部0；启用1; 禁用2; 新增审核中3; 编辑审核中4; 禁用审核中5*/
	private String status;

	/**商户标签名称*/
	private String tagName;

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	
	
}
