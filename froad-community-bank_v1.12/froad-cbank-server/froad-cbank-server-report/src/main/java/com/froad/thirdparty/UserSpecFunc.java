package com.froad.thirdparty;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pay.user.dto.MemberSignSpecDto;
import com.pay.user.dto.Result;

public interface UserSpecFunc {
	
	//查询签约会员列表
	public Result queryMemberByCardBin(String cardBin, String bankId, Date begDate, Date endDate);
	
	public Map<String, List<MemberSignSpecDto>> queryMemberByBankId(String bankId, Date begDate, Date endDate) throws IOException ;
	
}
