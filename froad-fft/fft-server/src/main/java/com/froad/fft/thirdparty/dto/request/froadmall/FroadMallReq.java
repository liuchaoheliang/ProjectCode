package com.froad.fft.thirdparty.dto.request.froadmall;

import com.froad.fft.persistent.common.enums.LotteryNo;
import com.froad.fft.persistent.common.enums.LotteryNumType;
import com.froad.fft.persistent.common.enums.LotteryPlayType;

/**
 * *******************************************************
 *<p> 工程: fft-server </p>
 *<p> 类名: FroadMallReq.java </p>
 *<p> 描述: *-- <b>请求方付通商场的参数</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年1月17日 上午10:12:49 </p>
 ********************************************************
 */
public class FroadMallReq {

	
	//-------------------------------  话费
	private String money;//充值金额
	
	private String CZNo;//充值手机号码
	
	private String salePrice;//售价
	
	private String SPID;//SP订单号
	
	private String tranDate;//交易时间
	
	//-------------------------------- 话费
	
	//-------------------------------- 彩票
	private LotteryNo lotteryNo;//彩票编号
	private String period;//彩票期号
	private LotteryPlayType playType;//彩票玩法
	private LotteryNumType numType;//单负合胆
	private String content;//号码
	private String buyamount;//投注倍数
	private String amount;//总金额
	private String numCount;//投注注数
	private String mobilephone;//接收交易通知的电话号码
	private String orderID;//渠道订单号
	
	//-------------------------------- 彩票
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>手机号码充值 必填参数构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月17日 上午10:12:49 </p>
	 ********************************************************
	 */
	public FroadMallReq(String money, String CZNo, String salePrice,
			String SPID, String tranDate) {
		this.money = money;
		this.CZNo = CZNo;
		this.salePrice = salePrice;
		this.SPID = SPID;
		this.tranDate = tranDate;
	}

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询彩票期号 必填参数构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月17日 上午10:12:49 </p>
	 ********************************************************
	 */
	public FroadMallReq(LotteryNo lotteryNo){
		this.lotteryNo = lotteryNo;
	}
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>创建彩票订单 必填参数构造</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年1月17日 上午10:12:49 </p>
	 ********************************************************
	 */
	public FroadMallReq(LotteryNo lotteryNo,String period,LotteryPlayType playType,LotteryNumType numType,String content,String buyamount,String amount,
			String numCount,String mobilephone,String orderID){
		this.lotteryNo = lotteryNo;
		this.period = period;
		this.playType = playType;
		this.numType = numType;
		this.content = content;
		this.buyamount = buyamount;
		this.amount = amount;
		this.numCount = numCount;
		this.mobilephone = mobilephone;
		this.orderID  = orderID;
	}
	
	public String getMoney() {
		return money;
	}

	public String getCZNo() {
		return CZNo;
	}

	public String getSalePrice() {
		return salePrice;
	}

	public String getSPID() {
		return SPID;
	}

	public String getTranDate() {
		return tranDate;
	}

	public LotteryNo getLotteryNo() {
		return lotteryNo;
	}

	public String getPeriod() {
		return period;
	}

	public LotteryPlayType getPlayType() {
		return playType;
	}

	public LotteryNumType getNumType() {
		return numType;
	}

	public String getContent() {
		return content;
	}

	public String getBuyamount() {
		return buyamount;
	}

	public String getAmount() {
		return amount;
	}

	public String getNumCount() {
		return numCount;
	}

	public String getMobilephone() {
		return mobilephone;
	}

	public String getOrderID() {
		return orderID;
	}
	
}
