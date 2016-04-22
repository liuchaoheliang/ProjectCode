/**
 * @Title: VouchersRunHandler.java
 * @Package com.froad.handler
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年11月26日
 * @version V1.0
**/

package com.froad.handler;

import java.util.List;

import com.froad.po.FindVouchersOfSubmitReq;
import com.froad.po.PayResultNoticeReq;
import com.froad.po.VouchersInfo;
import com.froad.thrift.vo.ResultVo;

 /**
 * @ClassName: VouchersRunHandler
 * @Description: TODO
 * @author froad-Joker 2015年11月26日
 * @modify froad-Joker 2015年11月26日
 */

public interface VouchersRunHandler {
	
	
	public VouchersInfo findOneVouchersInfo(VouchersInfo vouchersInfo);
	
	public ResultVo payResultNotice(PayResultNoticeReq payResultNoticeReq);
	
	public List<VouchersInfo> getAllVouchersInfoByClientIdAndMemberCode(FindVouchersOfSubmitReq findVouchersOfSubmitReq);

}
