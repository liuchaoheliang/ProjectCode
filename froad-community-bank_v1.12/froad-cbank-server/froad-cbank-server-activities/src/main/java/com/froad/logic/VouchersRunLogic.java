package com.froad.logic;

import com.froad.db.mysql.bean.Page;
import com.froad.po.CheckVouchersReq;
import com.froad.po.CheckVouchersRes;
import com.froad.po.FindVouchersOfSubmitReq;
import com.froad.po.PayResultNoticeReq;
import com.froad.po.VouchersInfo;
import com.froad.po.VouchersToRedPackReq;
import com.froad.thrift.vo.ResultVo;

public interface VouchersRunLogic {
	public Page<VouchersInfo> findVouchersOfSubmit(Page<VouchersInfo> page,
			FindVouchersOfSubmitReq findVouchersOfSubmitReq);

	public CheckVouchersRes checkVoucherVaild(CheckVouchersReq checkVouchersReq);
	
	public ResultVo payResultNotice(PayResultNoticeReq payResultNoticeReq);
	
	public ResultVo vouchersToRedPack(VouchersToRedPackReq vouchersToRedPackReq );
}
