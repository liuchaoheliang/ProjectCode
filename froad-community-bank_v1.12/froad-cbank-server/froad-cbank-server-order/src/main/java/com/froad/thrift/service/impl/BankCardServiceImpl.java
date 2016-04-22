
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.common.beans.ResultBean;
import com.froad.enums.ResultCode;
import com.froad.logic.BankCardLogic;
import com.froad.logic.impl.BankCardLogicImpl;
import com.froad.thirdparty.util.DateUtil;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.bankcard.BankCardInfo;
import com.froad.thrift.vo.bankcard.BankCardResponse;
import com.froad.util.BeanUtil;
import com.froad.util.payment.EsyT;
import com.pay.user.dto.MemberBankSpecDto;

public class BankCardServiceImpl extends BizMonitorBaseService implements BankCardService.Iface {
	public BankCardServiceImpl() {}
	public BankCardServiceImpl(String name, String version) {
		super(name, version);
	}
    private BankCardLogic bankCardLogic = new BankCardLogicImpl();

    @SuppressWarnings("unchecked")
    @Override
    public BankCardResponse selectSignedBankCardByClientId(String clientId, long memberCode) throws TException {
        ResultBean result = bankCardLogic.selectSignedBankCardByClientId(clientId, memberCode);
        List<BankCardInfo> bankCardInfos = new ArrayList<BankCardInfo>();
        if (ResultCode.success.getCode().equals(result.getCode())) {
            List<MemberBankSpecDto> memberBankSpecDtos = (List<MemberBankSpecDto>) result.getData();
            if (memberBankSpecDtos != null && memberBankSpecDtos.size() > 0) {
                BankCardInfo bankCardInfo = null;
                for (MemberBankSpecDto memberBankSpecDto : memberBankSpecDtos) {
                    bankCardInfo = (BankCardInfo) BeanUtil.copyProperties(BankCardInfo.class, memberBankSpecDto);
                    bankCardInfo.setCreateTimeStr(DateUtil.formatDate2Str(memberBankSpecDto.getCreateTime()));
                    bankCardInfo.setUpdateTimeStr(DateUtil.formatDate2Str(memberBankSpecDto.getUpdateTime()));
                    if("taizhou".equals(clientId)){
                    	bankCardInfo.setPointCardNo(bankCardLogic.getSignPointCardNo(memberCode));
                    }
                    bankCardInfos.add(bankCardInfo);
                }
            }
        }
        BankCardResponse response = new BankCardResponse();
        response.setResultCode(result.getCode());
        response.setResultDesc(result.getMsg());
        response.setCardList(bankCardInfos);
        return response;
    }

    @Override
    public ResultVo signBankCardByClientId(String clientId, long memberCode, String cardNo, String uname, String idcard, String phone, String singlePenLimit, String dayLimit, String monthLimit, String mobileToken,String pointCardNo) throws TException {
        ResultBean result = bankCardLogic.signBankCardByClientId(clientId, memberCode, cardNo, uname, idcard, phone, singlePenLimit, dayLimit, monthLimit, mobileToken,pointCardNo);
        return new ResultVo(result.getCode(), result.getMsg());
    }

    @Override
    public ResultVo setDefaultSignerBankCard(long memberCode, long cardId) throws TException {
        ResultBean result = bankCardLogic.setDefaultSignerBankCard(memberCode,cardId);
        return new ResultVo(result.getCode(), result.getMsg());
    }

    @Override
    public ResultVo sendSignBankCardMobileToken(String clientId, String phone, String cardNo) throws TException {
        ResultBean result = bankCardLogic.sendSignBankCardMobileToken(clientId, phone, cardNo);
        return new ResultVo(result.getCode(), result.getMsg());
    }

    @Override
    public ResultVo cancelSignedBankCard(String clientId, long memberCode, String cardNo) throws TException {
        ResultBean result = bankCardLogic.cancelSignedBankCard(clientId, memberCode, cardNo);
        return new ResultVo(result.getCode(), result.getMsg());
    }

    @Override
    public ResultVo updateSignBankCardLimitCash(String clientId, String cardNo, String singlePenLimit, String dailyLimit, String monthlyLimit)throws TException {
        ResultBean result = bankCardLogic.updateSignBankCardLimitCash(clientId, cardNo, singlePenLimit, dailyLimit, monthlyLimit);
        return new ResultVo(result.getCode(), result.getMsg());
    }

	@Override
	public ResultVo setMerchantBankWhiteList(String merchantId,String merchantName, String accountNo, String mac, String optionType,String clientId, String accountName)throws TException {
		ResultBean result = bankCardLogic.setMerchantWhiteList(clientId, merchantId, merchantName, accountNo, mac, optionType, accountName);
        return new ResultVo(result.getCode(), result.getMsg());
	}
	@Override
	public ResultVo synchBankLabel(String bankLabelID, String bankLabelName, int state,String clientId) throws TException {
		ResultBean result = bankCardLogic.synchBankLabel(bankLabelID, bankLabelName, state,clientId);
		return new ResultVo(result.getCode(), result.getMsg());
	}
	@Override
	public ResultVo auditStatusQuery(String clientId, String accountName,String accountNo) throws TException {
		ResultBean resultBean = bankCardLogic.auditStatusQuery(clientId, accountName, accountNo);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			return new ResultVo("9999",resultBean.getMsg());
		}
		//此处的resultCode 00：账户审核成功 		01：账户审核失败		02：账户审核中
		return new ResultVo((String)resultBean.getData(), resultBean.getMsg());
	}
	@Override
	public ResultVo bankCardAccountCheck(String clientId, String accountName,String accountNo, String certificateType, String certificateNo) throws TException {
		ResultBean resultBean = bankCardLogic.bankCardAccountCheck(clientId, accountName, accountNo, certificateType, certificateNo);
		if(!EsyT.isResultBeanSuccess(resultBean)){
			return new ResultVo("9999",resultBean.getMsg());
		}
		return new ResultVo((String)resultBean.getData(), resultBean.getMsg());
	}

}
