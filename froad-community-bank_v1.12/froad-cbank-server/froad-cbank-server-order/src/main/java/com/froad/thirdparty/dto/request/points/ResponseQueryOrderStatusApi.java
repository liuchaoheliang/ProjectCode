package com.froad.thirdparty.dto.request.points;

import com.froad.thirdparty.dto.response.points.PointsApiQueryParams;
import com.froad.thirdparty.dto.response.points.System;

public class ResponseQueryOrderStatusApi {


	private PointsApiQueryParams queryParam;
	private System system;

	public PointsApiQueryParams getQueryParam() {
		return queryParam;
	}
	public void setQueryParam(PointsApiQueryParams queryParam) {
		this.queryParam = queryParam;
	}
	
	public System getSystem() {
		return system;
	}
	public void setSystem(System system) {
		this.system = system;
	}
}
