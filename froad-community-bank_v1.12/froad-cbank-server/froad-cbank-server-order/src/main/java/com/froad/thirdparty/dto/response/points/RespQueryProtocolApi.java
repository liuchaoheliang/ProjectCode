package com.froad.thirdparty.dto.response.points;

import java.util.List;

import com.froad.thirdparty.bean.BindAccountInfo;
import com.froad.thirdparty.bean.PointInfoDto;
import com.froad.thirdparty.bean.QueryInfoDto;
import com.froad.thirdparty.bean.TotalPointsInfosDto;
import com.froad.thirdparty.dto.request.points.PartnerAccount;

public class RespQueryProtocolApi {
	
	private QueryInfoDto queryInfo;
	private PartnerAccount partnerAccount;
	private List<TotalPointsInfosDto> totalPointsInfos;
	private List<PointInfoDto> protocolInfos;
	private List<BindAccountInfo> bindAccountInfos;
	private System system;
	
	
	public QueryInfoDto getQueryInfo() {
		return queryInfo;
	}
	public void setQueryInfo(QueryInfoDto queryInfo) {
		this.queryInfo = queryInfo;
	}
	public List<TotalPointsInfosDto> getTotalPointsInfos() {
		return totalPointsInfos;
	}
	public void setTotalPointsInfos(List<TotalPointsInfosDto> totalPointsInfos) {
		this.totalPointsInfos = totalPointsInfos;
	}
	public List<PointInfoDto> getProtocolInfos() {
		return protocolInfos;
	}
	public void setProtocolInfos(List<PointInfoDto> protocolInfos) {
		this.protocolInfos = protocolInfos;
	}
	public System getSystem() {
		return system;
	}
	public void setSystem(System system) {
		this.system = system;
	}
	public List<BindAccountInfo> getBindAccountInfos() {
		return bindAccountInfos;
	}
	public void setBindAccountInfos(List<BindAccountInfo> bindAccountInfos) {
		this.bindAccountInfos = bindAccountInfos;
	}
	public PartnerAccount getPartnerAccount() {
		return partnerAccount;
	}
	public void setPartnerAccount(PartnerAccount partnerAccount) {
		this.partnerAccount = partnerAccount;
	}
	

}
