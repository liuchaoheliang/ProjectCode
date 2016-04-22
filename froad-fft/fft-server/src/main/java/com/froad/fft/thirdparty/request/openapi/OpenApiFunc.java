package com.froad.fft.thirdparty.request.openapi;

import com.froad.fft.common.AppException;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.fft.thirdparty.dto.request.openapi.OpenApiRes;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: OpenApiFunc.java </p>
 *<p> 描述: *-- <b>用于该系统对接openapi系统的相关接口</b> --* </p>
 *<p> 作者: 赵肖瑶 </p>
 *<p> 时间: 2014年1月15日 上午10:29:54 </p>
 ********************************************************
 */
public interface OpenApiFunc {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>退款申请接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月15日 上午11:01:46 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes refund(OpenApiReq openApiReq) throws AppException;
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>校验查询 接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月15日 下午3:44:14 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes accountCheck(OpenApiReq openApiReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>转账接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月16日 上午9:31:10 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes transferCurrency(OpenApiReq openApiReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>代收|代扣接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月16日 下午2:01:06 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes agencyCollectOrDeduct(OpenApiReq openApiReq) throws AppException;
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>合并订单支付</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月21日 下午3:13:25 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes combineOrder(OpenApiReq openApiReq) throws AppException;
}
