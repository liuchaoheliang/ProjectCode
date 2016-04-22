include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/BizMonitor.thrift"
include "../../../../../../../froad-cbank-server-common/src/main/java/com/froad/thrift/Common.thrift"
include "BankCardVo.thrift"
namespace java com.froad.thrift.service


service BankCardService extends BizMonitor.BizMonitorService {
	/** 查询指定用户下指定客户端下已签约的银行卡*/
    BankCardVo.BankCardResponse selectSignedBankCardByClientId(1:string clientId, 2:i64 memberCode);

    /**签约指定客户端快捷银行卡*/
    Common.ResultVo signBankCardByClientId(1:string clientId, 2:i64 memberCode, 3:string cardNo, 4:string uname, 5:string idcard, 6:string phone, 7:string singlePenLimit, 8:string dayLimit, 9:string monthLimit,
            10:string mobileToken,11:string pointCardNo);

    /**设置默认的签约快捷银行卡*/
    Common.ResultVo setDefaultSignerBankCard(1:i64 memberCode, 2:i64 cardId);

    /**使用银行提供的签约短信验证码接口（需银行支持）*/
    Common.ResultVo sendSignBankCardMobileToken(1:string clientId, 2:string phone, 3:string cardNo);

    /**解约已绑定的银行卡*/
    Common.ResultVo cancelSignedBankCard(1:string clientId, 2:i64 memberCode, 3:string cardNo);

    /**设置已签约的银行卡限额*/
    Common.ResultVo updateSignBankCardLimitCash(1:string clientId, 2:string cardNo, 3:string singlePenLimit, 4:string dailyLimit, 5:string monthlyLimit);

	/**设置商户银行卡白名单*/
	Common.ResultVo setMerchantBankWhiteList(1:string merchantId,2:string merchantName,3:string accountNo,4:string mac,5:string optionType,6:string clientId,7:string accountName);
	
	/**同步银行标签*/
	Common.ResultVo synchBankLabel(1:string bankLabelID,2:string bankLabelName,3:i32 state,4:string clientId);
	
	/**
	*查询同步银行标签的审核结果
	*/
	Common.ResultVo auditStatusQuery(1:string clientId,2:string accountName,3:string accountNo);
	
	/**
	*查询同步银行标签的审核结果
	*/
	Common.ResultVo bankCardAccountCheck(1:string clientId,2:string accountName,3:string accountNo,4:string certificateType,5:string certificateNo);
	
}