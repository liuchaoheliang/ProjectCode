package com.froad.fft.thirdparty.request.points;

import com.froad.fft.common.AppException;
import com.froad.fft.thirdparty.dto.request.points.PointsReq;
import com.froad.fft.thirdparty.dto.request.points.PointsRes;

/**
 * 积分相关接口
 * @author FQ
 *
 */
public interface PointsFunc {
	
	/**
	 * 积分查询
	 */
	public PointsRes queryParAccountPoints(PointsReq pointsReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>消费积分接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 上午11:52:50 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes consumePoints(PointsReq pointsReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>退还积分接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月13日 下午5:19:33 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes refundPoints(PointsReq pointsReq) throws AppException;

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>赠送积分接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月14日 上午9:54:30 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes donatePoints(PointsReq pointsReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>兑充积分接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2014年1月14日 下午1:42:14 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes fillPoints(PointsReq pointsReq) throws AppException;
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>积分提现申请接口 （异步）</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年2月10日 上午11:49:43 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public PointsRes applyForPointsToCash(PointsReq pointsReq) throws AppException;

}
