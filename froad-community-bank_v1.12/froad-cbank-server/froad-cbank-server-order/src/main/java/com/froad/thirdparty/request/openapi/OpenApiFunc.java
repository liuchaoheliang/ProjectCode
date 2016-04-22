package com.froad.thirdparty.request.openapi;

import com.froad.thirdparty.dto.request.openapi.CreateMobileTokenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiReq;
import com.froad.thirdparty.dto.request.openapi.OpenApiRes;
import com.froad.thirdparty.dto.request.openapi.QueryParamApiReq;
import com.froad.thirdparty.dto.request.openapi.SendSmsApiReq;
import com.froad.thirdparty.dto.request.openapi.SignApiReq;
import com.froad.thirdparty.dto.request.openapi.SignCancelApiReq;
import com.froad.thirdparty.exception.AppException;
import com.froad.thirdparty.dto.request.openapi.LimitReq;

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
	@Deprecated
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
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>快捷支付-认证签约</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年5月8日 上午10:30:25 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes fastPaySign(SignApiReq openApiReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>快捷支付-认证解约</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年5月8日 上午10:31:58 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes fastPaySignCancel(SignCancelApiReq openApiReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>快捷支付-发送短信校验</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年5月8日 上午10:33:52 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes fastPayMoblieToken(CreateMobileTokenApiReq openApiReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>快捷支付-发送短信</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年5月8日 上午10:36:16 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes fastPaySendSMS(SendSmsApiReq openApiReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>设置限额</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年6月10日 上午11:17:12 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public OpenApiRes setFastPayLimit(LimitReq req) throws AppException;
	
	
	/**
	  * 方法描述：openApi账单查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年9月3日 上午10:44:20
	  */
	public OpenApiRes query(QueryParamApiReq paramApiReq);
	
	
	/**
	 * 设置银行前置可收款白名单
	* <p>Function: whiteListSet</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015-5-3 上午9:18:02
	* @version 1.0
	* @return
	 */
	public OpenApiRes whiteListSet(OpenApiReq openApiReq);
	
	/**
	 * 登录验码接口
	* <p>Function: employeeCodeVerify</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年7月23日 下午1:16:08
	* @version 1.0
	* @param openApiReq
	* @return
	 */
	public OpenApiRes employeeCodeVerify(OpenApiReq openApiReq);
	
	/**
	 * 
	 * auditStatusQuery:审核状态查询
	 *
	 * @author vania
	 * 2015年9月14日 下午5:43:10
	 * @param openApiReq
	 * @return
	 *
	 */
	public OpenApiRes auditStatusQuery(OpenApiReq openApiReq);
	
	/**
	 * 银行卡账户校验接口
	 * bankCardAccCheck:(这里用一句话描述这个方法的作用).
	 *
	 * @author Zxy
	 * 2015-9-21 上午10:32:51
	 * @param openApiReq
	 * @return
	 *
	 */
	public OpenApiRes bankCardAccCheck(OpenApiReq openApiReq);
}
