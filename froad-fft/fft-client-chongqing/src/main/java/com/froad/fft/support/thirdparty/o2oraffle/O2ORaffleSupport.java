package com.froad.fft.support.thirdparty.o2oraffle;

import java.util.List;

import com.o2obill.dto.AwardRes;
import com.o2obill.dto.CheckLotteryReadyRes;
import com.o2obill.dto.CouponReq;
import com.o2obill.dto.CouponRes;
import com.o2obill.dto.LotteryRandomReq;
import com.o2obill.dto.LotteryRandomRes;
import com.o2obill.dto.LotteryWinnerDto;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: O2ORaffle.java </p>
 *<p> 描述: *-- <b>O2O抽奖模块</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年4月25日 上午11:28:35 </p>
 ********************************************************
 */
public interface O2ORaffleSupport {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>进行抽奖模块检查</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月25日 上午11:32:28 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public CheckLotteryReadyRes checkLotteryReadyRes(String loginID,Long memberCode);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询中奖用户信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月25日 下午1:37:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public List<LotteryWinnerDto> loadWinners();
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>进行抽奖</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月25日 下午1:56:18 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public LotteryRandomRes lotteryRandom(LotteryRandomReq lotteryRandomReq);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过中奖ID查询中奖信息（中奖ID为O2O平台信息）</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月25日 下午2:17:36 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public LotteryWinnerDto getWinnerByWinnerID(String winnerID);
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>进行派奖</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月25日 下午2:21:42 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public AwardRes award(LotteryWinnerDto lotteryWinnerDto);
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询指定用户的中奖信息</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月25日 下午2:28:53 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public CouponRes loadCouponsBy(CouponReq couponReq);
}
