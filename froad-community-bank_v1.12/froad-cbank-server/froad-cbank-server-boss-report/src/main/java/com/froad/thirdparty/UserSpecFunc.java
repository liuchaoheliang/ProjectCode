package com.froad.thirdparty;

import java.util.Date;

import com.pay.user.dto.Result;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.ClientChannel;

public interface UserSpecFunc {
	
	/**
	 * VIP明细列表分页查询
	 * 
	 * @param beginTime
	 *            VIP开通开始时间
	 * @param endTime
	 *            VIP开通结束时间
	 * @param bankOrg
	 *            银行标识
	 * @param labelId
	 *            VIP会员标签ID
	 * @param clientChannel
	 *            客户端渠道
	 * @param pageSize
	 *            每一页包含的数据数量
	 * @param pageNum
	 *            页码
	 * @return 分页数据信息对象,默认按照VIP开通时间降序
	 */
	public Result queryVIPSpecList(Date beginTime, Date endTime, BankOrg bankOrg,
			String labelId, ClientChannel clientChannel, int pageSize, int pageNum);
	
	
	public Result queryMemberByCardBin(String cardBin, String bankId, Date begDate, Date endDate);
}
