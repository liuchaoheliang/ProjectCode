package com.froad.process.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.cbank.persistent.common.enums.SmsType;
import com.froad.cbank.persistent.entity.SmsLog;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.SmsLogCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.SmsLogMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.SmsLogMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;

public class SmsLogProcess extends AbstractProcess{
  private SmsLogMapperCG cgMapper;
	public SmsLogProcess(String name,ProcessServiceConfig config) {
		super(name,config);
		// TODO Auto-generated constructor stub
	}
	private final String CLIENT_ID = "chongqing";
	@Override
	public void before() {
		cgMapper =  sqlSession.getMapper(SmsLogMapperCG.class);
		LogCvt.info("删除SmsLog数据开始.......................");
		cgMapper.deleteSmsLog(Constans.clientId);
		
	} 
	@Override
	public void process() {
		// TODO Auto-generated method stub
		boolean result = false;
		//重庆的实体类和Mapper
		SmsLogMapper mapper = cqSqlSession.getMapper(SmsLogMapper.class);
		
		SmsLog smsLog = new SmsLog();
		//记录新旧id
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		//只需要转移的券类型  1004-1010
//		List<SmsType> smst = new ArrayList<SmsType>();
//		smst.add(SmsType.presellDelivery);  //精品预售提货
//		smst.add(SmsType.presellRefund);	//精品预售退款
//		smst.add(SmsType.presellClusterFail);//精品预售不成团
//		smst.add(SmsType.presellReturnSale);//精品预售申请退货
//		smst.add(SmsType.presellBookSuccess);//精品预售预定成功
//		smst.add(SmsType.group);//团购
//		smst.add(SmsType.exchange);//积分兑换
//		smst.add(SmsType.advancePresellDelivery);//精品预售提前发码短信
//		smst.add(SmsType.offPointsBankSecurityCode);//线下积分兑换银行验证码
		
//		for(int i=0;i<smst.size();i++){
//			List<SmsLog> smsLogs = new ArrayList<SmsLog>();
//			smsLog.setSmsType(smst.get(i));
//			smsLogs = mapper.findSmsLog(smsLog);
//			if(smsLogs ==null || smsLogs.isEmpty()){
//				LogCvt.error("未查询到短信日志信息,"+smsLog.getSmsType().getCode()+":"+smsLog.getSmsType().getDescribe());
//				continue;
//			}
		List<SmsLog> smsLogs=mapper.findALLSms();
			for(SmsLog sms : smsLogs){
				SmsLogCG cgSmsLog = new SmsLogCG();
				cgSmsLog.setId(sms.getId());
				cgSmsLog.setClientId(CLIENT_ID);
				cgSmsLog.setCreateTime(sms.getCreateTime());
				cgSmsLog.setExpireTime(sms.getUpdateTime());
				cgSmsLog.setSmsType(Integer.parseInt(sms.getSmsType().getCode()));
				cgSmsLog.setMobile(sms.getMobile());
				cgSmsLog.setContent(sms.getContent());
				cgSmsLog.setIsSuccess(sms.getIsSuccess());
				cgSmsLog.setSendUser(sms.getSendUser());
				cgSmsLog.setSendIp(sms.getSendIp());
				cgSmsLog.setIsUsed(checkIsUsed(sms.getIsUsed()));
				if(sms.getCode() != null){
					cgSmsLog.setCode(sms.getCode());
				}else{
					cgSmsLog.setCode("");
				}
				if(sms.getToken() != null){
					cgSmsLog.setToken(sms.getToken());
				}else{
					cgSmsLog.setToken("");
				}
				cgSmsLog.setUrl(null);
				result = addSmsLog(cgMapper,cgSmsLog);
				if(result){
					Transfer transfer = new Transfer();
					transfer.setOldId(String.valueOf(sms.getId()));
					transfer.setNewId(String.valueOf(cgSmsLog.getId()));
					transfer.setType(TransferTypeEnum.sms_log_id);
					int iresult = transferMapper.insert(transfer);
					if(iresult!=-1){
						LogCvt.info("cb_sms_log转移成功");
					}
				}else{
					LogCvt.info("cb_sms_log转移失败");
					return;
				}
			}
		}
		
	
	public boolean checkIsUsed(Boolean isUsed){
		if(isUsed==null){
			return false;
		}
		return isUsed;
	}
	
	public boolean addSmsLog(SmsLogMapperCG cgMapper,
			SmsLogCG cgSmsLog){
		boolean result = false;
		long addresult = -1;
		try {
			//检查重构数据库中与安徽数据库是否有相同数据
			SmsLogCG smslogcg = findSmsLogById(cgMapper,cgSmsLog.getId());
			if(smslogcg != null){
				LogCvt.error("该短信日志信息已存在,ID:"+cgSmsLog.getId());
				return false;
			}
			
			//重构mysql数据库
			addresult = cgMapper.addSmsLog(cgSmsLog);
			if(addresult == 1){
				result = true;
			}
			if(result){
				sqlSession.commit(true); 
			}
		} catch (Exception e) {
			result = false;
			LogCvt.info("cb_sms_log表数据转移失败，失败原因:"+e.getMessage());
		}
		
		return result;
	}
	
	
	//根据ID检查重构数据库中与重庆数据库是否有相同数据
	public  SmsLogCG findSmsLogById(SmsLogMapperCG cgMapper,Long id){
		SmsLogCG smslog = new SmsLogCG();
		smslog.setId(id);
		List<SmsLogCG> list = cgMapper.findSmsLog(smslog);
		if(Checker.isNotEmpty(list) && list.size()==1){
			return list.get(0);
		}
		return null;
		
	}


	

}

