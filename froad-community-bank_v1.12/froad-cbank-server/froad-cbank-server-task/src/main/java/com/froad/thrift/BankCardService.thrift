include "BizMonitor.thrift"
include "Common.thrift"

namespace java com.froad.thrift.service


service BankCardService extends BizMonitor.BizMonitorService {


	/**设置商户银行卡白名单*/
	Common.ResultVo setMerchantBankWhiteList(1:string merchantId,2:string merchantName,3:string accountNo,4:string mac,5:string optionType,6:string clientId,7:string accountName);
	
	
	/**
	*查询同步银行标签的审核结�?
	*/
	Common.ResultVo auditStatusQuery(1:string clientId,2:string accountName,3:string accountNo);
}