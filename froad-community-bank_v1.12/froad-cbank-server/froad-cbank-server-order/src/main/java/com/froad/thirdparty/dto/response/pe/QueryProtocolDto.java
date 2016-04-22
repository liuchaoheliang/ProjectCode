package com.froad.thirdparty.dto.response.pe;

import java.io.Serializable;
import java.util.List;

import com.froad.thirdparty.bean.PointInfoDto;
import com.froad.thirdparty.bean.QueryInfoDto;
import com.froad.thirdparty.bean.TotalPointsInfosDto;

public class QueryProtocolDto implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/* pointInfos: 消费明细 */
	private List<PointInfoDto> pointInfos;
	
	
	/* queryInfo: 分页参数 */
	private QueryInfoDto queryInfo;
	
	
	/* protocolDtos: 统计分类 */
	private List<TotalPointsInfosDto> totalPointsInfosDtos;
	
	
	public List<PointInfoDto> getPointInfos() {
		return pointInfos;
	}
	public void setPointInfos(List<PointInfoDto> pointInfos) {
		this.pointInfos = pointInfos;
	}
	public QueryInfoDto getQueryInfo() {
		return queryInfo;
	}
	public void setQueryInfo(QueryInfoDto queryInfo) {
		this.queryInfo = queryInfo;
	}
	public List<TotalPointsInfosDto> getTotalPointsInfosDtos() {
		return totalPointsInfosDtos;
	}
	public void setTotalPointsInfosDtos(
			List<TotalPointsInfosDto> totalPointsInfosDtos) {
		this.totalPointsInfosDtos = totalPointsInfosDtos;
	}
	
	
}
