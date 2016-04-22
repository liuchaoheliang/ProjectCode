package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.CommonParam;
import com.froad.po.TypePercentInfo;
import com.froad.po.UserSummary;
import com.froad.po.UserTradeDetail;
import com.froad.po.UserTradeInfo;
import com.froad.po.UserTrend;

public interface ReportUserLogic {
	
	List<UserTrend> userTrend(CommonParam param) throws FroadBusinessException, Exception;
	
	List<TypePercentInfo> userTradeTypePercent(CommonParam param) throws FroadBusinessException, Exception;
	
	List<TypePercentInfo> userConsumeTypePercent(CommonParam param) throws FroadBusinessException, Exception;
	
	List<UserSummary> userSummaryList(CommonParam param) throws FroadBusinessException, Exception;
	
	Page<UserSummary> userSummaryListByPage(CommonParam param, Page<UserSummary> page) throws FroadBusinessException, Exception;
	
	List<UserTradeDetail> userTradeDetailList(CommonParam param) throws FroadBusinessException, Exception;
	
	List<UserTradeInfo> userTradeInfoList(CommonParam param) throws FroadBusinessException, Exception;
}
