
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;

import com.alibaba.druid.util.StringUtils;
import com.froad.common.beans.ResultBean;
import com.froad.enums.PointsType;
import com.froad.enums.ResultCode;
import com.froad.logic.MemberInformationLogic;
import com.froad.logic.impl.MemberInformationLogicImpl;
import com.froad.thirdparty.bean.PointInfoDto;
import com.froad.thirdparty.bean.TotalPointsInfosDto;
import com.froad.thirdparty.dto.response.pe.MemberInfo;
import com.froad.thirdparty.dto.response.pe.MemberVipInfo;
import com.froad.thirdparty.dto.response.pe.QueryProtocolDto;
import com.froad.thirdparty.enums.ProtocolType;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.member.MemberInfoVo;
import com.froad.thrift.vo.member.MemberVIPInfoVo;
import com.froad.thrift.vo.member.PeAcctVo;
import com.froad.thrift.vo.member.PointsAccountVo;
import com.froad.thrift.vo.member.QueryInfoVo;
import com.froad.thrift.vo.member.QueryProtocolVo;
import com.froad.thrift.vo.member.TotalPointsInfosVo;
import com.froad.thrift.vo.member.UserEnginePointsRecordVo;
import com.froad.thrift.vo.member.UserEnginePointsVo;
import com.froad.util.BeanUtil;
import com.froad.util.DateUtil;
import com.froad.util.payment.EsyT;

public class MemberInformationServiceImpl extends BizMonitorBaseService implements MemberInformationService.Iface {
	public MemberInformationServiceImpl() {}
	public MemberInformationServiceImpl(String name, String version) {
		super(name, version);
	}
    private static final MemberInformationLogic memberInformation = new MemberInformationLogicImpl();

    @Override
    public MemberInfoVo selectUserByMemberCode(long memberCode,String clientId) throws TException {
        ResultBean result = memberInformation.queryMemberInfoByMemberCode(memberCode,clientId);
        MemberInfoVo resultVo = new MemberInfoVo();
        if(EsyT.isResultBeanSuccess(result)){
        	MemberInfo memberInfo = (MemberInfo) result.getData();
        	resultVo = (MemberInfoVo) BeanUtil.copyProperties(MemberInfoVo.class, memberInfo);
        	resultVo.setCreateTime(memberInfo.getCreateTime().getTime());
            MemberVIPInfoVo memberVIPInfoVo = new MemberVIPInfoVo();
            MemberVipInfo vipInfo = memberInfo.getUserVipInfo();
            
            if(vipInfo != null){
            	memberVIPInfoVo.setBankOrg(vipInfo.getBankOrgNo());
                memberVIPInfoVo.setVipLevel(vipInfo.getVipLevel());
                if(vipInfo.getVipExpiratioinTime() != null){
                	if(vipInfo.getVipExpiratioinTime().before(new Date())){
                		memberVIPInfoVo.setExpiratioinDays(0);
                	}else{
                		memberVIPInfoVo.setExpiratioinDays(vipInfo.getAvailableDays());
                	}
                }
                memberVIPInfoVo.setVipStatus(vipInfo.getVipStatus());
                memberVIPInfoVo.setBankLabelName(vipInfo.getBankLabelName());
                
                resultVo.setMemberVIPInfoVo(memberVIPInfoVo);
            }else{
            	resultVo.setMemberVIPInfoVo(null);
            }
        }
        
        
        resultVo.setResultSuccess(result.isSuccess());
        resultVo.setResultMessage(result.getMsg());
        return resultVo;

    }

    @Override
    public MemberInfoVo selectUserByLoginID(String loginID,String clientId) throws TException {
        ResultBean result = memberInformation.queryMemberInfoByLoginID(loginID,clientId);
        MemberInfoVo resultVo = (MemberInfoVo) BeanUtil.copyProperties(MemberInfoVo.class, result.getData());
        resultVo.setResultSuccess(result.isSuccess());
        resultVo.setResultMessage(result.getMsg());
        return resultVo;
    }

    @Override
    public UserEnginePointsVo selectMemberPointsInfoByLoginID(String clientId, String loginID) throws TException {
        ResultBean result = memberInformation.queryMemberPointsInfoByLoginID(clientId, loginID);
        UserEnginePointsVo vo = new UserEnginePointsVo();;
        if(result.isSuccess()){
        	vo = (UserEnginePointsVo) BeanUtil.copyProperties(UserEnginePointsVo.class, result.getData());
        	if(vo==null){
        		vo = new UserEnginePointsVo();
        	}
        	vo.setResultSuccess(true);
        }else{
        	vo.setResultSuccess(false);
        }
        vo.setResultMessage(result.getMsg());
        return vo;
    }

    @Override
    public ResultVo updateUserMobile(long memberCode, String mobile) throws TException {
        return convert(memberInformation.updateMemberBindMobile(memberCode, mobile));
    }

    @Override
    public PeAcctVo sendSignBankCardPhoneToken(String clientId, String phone, String cardNo) throws TException {
        ResultBean result = memberInformation.sendSignBankCardPhoneToken(clientId, phone, cardNo);
        PeAcctVo peAcctVo = new PeAcctVo();
        peAcctVo.setFlag(false);
        setPeAcctVoProperties(result, peAcctVo);
        return peAcctVo;
    }

	private void setPeAcctVoProperties(ResultBean result, PeAcctVo peAcctVo) {
		if (isSuccess(result)) {
            peAcctVo.setFlag(true);
            peAcctVo.setResultSuccess(true);
        }else{
        	peAcctVo.setResultSuccess(false);
        }
        peAcctVo.setMsg(result.getMsg());
        peAcctVo.setResultMessage(peAcctVo.getResultMessage());
	}

    private boolean isSuccess(ResultBean result) {
        return ResultCode.success.getCode().equals(result.getCode());
    }

    @Override
    public PeAcctVo fastPayMoblieToken(String clientId, String bankCardNo, String phone, String remark) throws TException {
        ResultBean result = memberInformation.sendFastPayMoblieToken(clientId, bankCardNo, phone, remark);
        PeAcctVo peAcctVo = new PeAcctVo();
        peAcctVo.setFlag(false);
        setPeAcctVoProperties(result, peAcctVo);
        return peAcctVo;
    }

    @Override
    public ResultVo validateFilmMobile(String clientId, String filmMobile) throws TException {
        return convert(memberInformation.validateFilmMobile(clientId, filmMobile));
    }

    @Override
    public QueryProtocolVo getPointTransBypage(String clientId, String userName, long fromTime, long toTime, String protocolType,String pointsType, int pageSize, int pageNum) throws TException {
        
        Date start = fromTime>0 ?new Date(fromTime):new Date(System.currentTimeMillis()-2592000);//默认30天内的
        Date end = toTime>0 ?new Date(toTime):new Date();
        ProtocolType protocolTypeEnum = convertStringToProtocolType(protocolType);
        PointsType pointsTypeEnum = convertStringToPointsType(pointsType);
        ResultBean result = memberInformation.queryPointsTradeHistory(clientId, userName, start, end, protocolTypeEnum,pointsTypeEnum, pageSize, pageNum);
        QueryProtocolVo resultVo = new QueryProtocolVo();
        QueryProtocolDto resultDto = (QueryProtocolDto) result.getData();
        if(resultDto!=null){
            if(resultDto.getPointInfos()!=null){
                List<UserEnginePointsRecordVo> list= new ArrayList<UserEnginePointsRecordVo>();
                resultVo.setPointInfos(list);
                UserEnginePointsRecordVo vo = new UserEnginePointsRecordVo();
                for(PointInfoDto dto : resultDto.getPointInfos()){
                	try {
                		vo = (UserEnginePointsRecordVo) BeanUtil.copyProperties(UserEnginePointsRecordVo.class, dto);
					} catch (Exception e) {
					}
                    if(!StringUtils.isEmpty(dto.getTime())){
                        Date time = DateUtil.parse("yyyy-M-d H:m:s", dto.getTime());
                        vo.setTime(time!=null?time.getTime():0);
                    }
                    if(dto.getProtocolType()!=null){
                        vo.setProtocolType(dto.getProtocolType().getCode());
                    }
                    list.add(vo);
                }
            }
            
            if(resultDto.getQueryInfo()!=null){
            	try {
            		resultVo.setQueryInfo((QueryInfoVo) BeanUtil.copyProperties(QueryInfoVo.class, resultDto.getQueryInfo()));
				} catch (Exception e) {
				}
            }
            
            if(resultDto.getTotalPointsInfosDtos()!=null){
                List<TotalPointsInfosVo> list= new ArrayList<TotalPointsInfosVo>();
                resultVo.setTotalPointsInfosDtos(list);
                TotalPointsInfosVo vo;
                for(TotalPointsInfosDto dto : resultDto.getTotalPointsInfosDtos()){
                    vo = new TotalPointsInfosVo();
                    if(dto.getProtocolType()!=null){
                        vo.setProtocolType(dto.getProtocolType().getCode());
                    }
                    vo.setTotalPoints(dto.getTotalPoints());
                    list.add(vo);
                }
            }
        }
        resultVo.setResultMessage(result.getMsg());
        resultVo.setResultSuccess(result.isSuccess());
        return resultVo;
    }

    @Override
    public ResultVo sendCheckCode(String clientId, String mobile, String points) throws TException {
        return convert(memberInformation.sendExchangeCheckCode(clientId, mobile, points));
    }

    @Override
    public ResultVo findBankPointsByMobile(String clientId, String mobile) throws TException {
        ResultBean result = memberInformation.queryBankPointsByMobile(clientId, mobile);
        String resultStr = null;
        if(isSuccess(result)){
            if(result.getData()==null){
                resultStr = "";
            }else{
                resultStr = result.getData().toString();
            }
        }else{
            resultStr = result.getMsg();
        }
        return new ResultVo(result.getCode(),resultStr);
    }

    private ResultVo convert(ResultBean result) {
        return new ResultVo(result.getCode(), result.getMsg());
    }

    private ProtocolType convertStringToProtocolType(String code) {
        if(code==null){
            return null;
        }
        for (ProtocolType cs : ProtocolType.values()) {
            if (cs.getCode().equals(code)) {
                return cs;
            }
        }
        return null;
    }
    
    private PointsType convertStringToPointsType(String code){
        if(code==null){
            return null;
        }
        for (PointsType pointsType : PointsType.values()) {
            if (pointsType.getCode().equals(code)) {
                return pointsType;
            }
        }
        return null;
    }

	@Override
	public PointsAccountVo queryBankPointsByBankNo(String clientId,String bankNo) throws TException {
		ResultBean result = memberInformation.queryBankPointsByBankCardNo(clientId, bankNo);
		PointsAccountVo resultVo = null;
		if(isSuccess(result)){
			resultVo = (PointsAccountVo) BeanUtil.copyProperties(PointsAccountVo.class, result.getData());
		}
		if(resultVo==null){
			resultVo = new PointsAccountVo();
		}
        resultVo.setResultMessage(result.getMsg());
        resultVo.setResultSuccess(result.isSuccess());
		return resultVo;
	}

    @Override
    public ResultVo payPointsByMobileNo(String clientId,String orderId,String payReason,String remark,String outletOrgNo,String loginId,int points) throws TException {
        ResultBean result = memberInformation.payPointsByMobile(clientId, orderId, payReason,remark,outletOrgNo, loginId, points,""); //FIXME 新增银行卡号
        return convert(result);
    }
	@Override
	public ResultVo employeeCodeVerify(String clientId, String verifyOrg, String employeeCode, String password,
			String clientNo) throws TException {
		ResultBean result = memberInformation.employeeCodeVerify(clientId, verifyOrg, employeeCode, password, clientNo);
		return convert(result);
	}


}
