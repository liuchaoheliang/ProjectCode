package com.froad.fft.thirdparty.request.froadmall;

import com.froad.fft.thirdparty.dto.request.froadmall.FroadMallReq;
import com.froad.fft.thirdparty.dto.request.froadmall.FroadMallRes;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: FroadMallFunc.java </p>
 *<p> 描述: *-- <b>对接方付通商场相关接口</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年1月17日 下午3:25:37 </p>
 ********************************************************
 */
public interface FroadMallFunc {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>充值话费 接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月17日 下午2:21:04 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public FroadMallRes onlineRecharge(FroadMallReq froadMallReq);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询彩票期数 接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月17日 下午3:44:11 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public FroadMallRes queryLotteryPeriods(FroadMallReq froadMallReq);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>创建彩票订单 接口</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月20日 上午9:38:34 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public FroadMallRes createLotteryTrans(FroadMallReq froadMallReq);
}
