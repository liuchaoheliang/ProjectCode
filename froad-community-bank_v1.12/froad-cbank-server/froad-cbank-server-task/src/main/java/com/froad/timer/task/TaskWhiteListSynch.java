package com.froad.timer.task;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

import com.froad.Constants;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MerchantAccountMapper;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.enums.AccountAuditState;
import com.froad.enums.AccountSynchState;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.po.MerchantAccount;
import com.froad.po.Result;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.ClientUtil;
import com.froad.util.ThriftConfig;

/**
 * 
 * 白名单同步 - 定时任务
 * 
 * @author lf 2015.09.11
 * @modify lf 2015.09.11
 * 
 * */
public class TaskWhiteListSynch implements Runnable{

	BankCardService.Iface client = (BankCardService.Iface) ThriftClientProxyFactory.createIface(BankCardService.class, ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
    MerchantService.Iface merchantClient=(MerchantService.Iface)ThriftClientProxyFactory.createIface(MerchantService.class, ThriftConfig.MERCHANT_SERVICE_HOST, ThriftConfig.MERCHANT_SERVICE_PORT);
    OutletService.Iface outletClient=(OutletService.Iface)ThriftClientProxyFactory.createIface(OutletService.class, ThriftConfig.MERCHANT_SERVICE_HOST, ThriftConfig.MERCHANT_SERVICE_PORT);
   
	SqlSession sqlSession = null;
	
	MerchantAccountMapper merchantAccountMapper = null;
	MerchantMapper merchantMapper=null;
	public static final String isSynSucc="0";
    public static final String synType ="0";//
	@Override
	public void run() {

//                          _ooOoo_
//                         o8888888o
//                         88" . "88
//                         (| -_- |)
//                          O\ = /O
//                      ____/`---'\____
/*********************************************************************/
/**                                                                  */
/** 1 - 找符合条件的（账户表-同步失败）                                                                                */
/** 2 - 调用Order模块白名单同步接口                                                                                    */
/** 3 - 判断调用结果                                                                                                                 */
/** 4 - 调用结果-成功                                                                                                               */
/**        如果为安徽（修改账户表-同步成功，审核通过）                                                      */
/**        如果为重庆（修改账户表-同步成功，审核中）                                                          */
/** 5 - 调用结果-非成功，不做任何处理                                                                                     */
/**                                                                  */
/*********************************************************************/		
		
		LogCvt.debug("定时任务: 白名单同步------开始------");
		
		
		try{
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);
			merchantMapper=sqlSession.getMapper(MerchantMapper.class);
			// 找符合条件的账户信息（同步失败）
			MerchantAccount merchantAccount = new MerchantAccount();
			merchantAccount.setSynchState(AccountSynchState.synchFail.getCode());
			List<MerchantAccount> merchantAccountList = merchantAccountMapper.queryByConditionList(merchantAccount);
			
//			if( sqlSession != null ) sqlSession.close();
			
			if(CollectionUtils.isEmpty(merchantAccountList)){
				LogCvt.debug("定时任务: 白名单同步------完成(无同步失败的账户信息)------");
				return;
			}
			
			LogCvt.debug("定时任务: 白名单同步------条数 "+merchantAccountList.size()+"------");
			
			Result result = new Result();
			// 循环应同步白名单的账户信息
			for( MerchantAccount account : merchantAccountList ){
				
				// 同步白名单 
				result = synchronousWhiteList(account);
				//result.setResultCode(ResultCode.success.getCode());
				
				// 调用结果 - 成功
				if( ResultCode.success.getCode().equals(result.resultCode) ){
				//	if(true){
					LogCvt.debug("定时任务: 白名单同步------调用Order模块白名单同步接口-----成功");
					
					// 同步成功处理
					synchronousSuccessProcess(account);
					
				}else{ // 调用结果 - 非成功
					LogCvt.debug("定时任务: 白名单同步------调用Order模块白名单同步接口-----不成功");
				}
				
			}
			
		}catch(Exception e){
			LogCvt.error("定时任务: 白名单同步------系统异常------");
			LogCvt.error(e.getMessage(), e);
		}finally {
			if( sqlSession != null ) sqlSession.close();
			LogCvt.debug("定时任务: 白名单同步------结束------");
		}
		
	}
	
	public static final String CHONGQING = "chongqing";
	public static final String ANHUI = "anhui";
	// 同步成功处理
	// 修改账户表-同步成功 
	// 如果为安徽-审核通过;如果为重庆-审核中
	private void synchronousSuccessProcess(MerchantAccount merchantAccount){
		String clientId = merchantAccount.getClientId();
		merchantAccount.setSynchState(AccountSynchState.synchSucc.getCode());
		merchantAccount.setAuditState(AccountAuditState.passAudit.getCode());
		boolean result = false;

		if(!ClientUtil.getClientId(clientId)){
			if(merchantAccountMapper != null ){
				//新增门店审核
				if(!merchantAccount.getOutletId().equals("0")){
					String parm="clientId:"+clientId+",merchantId:"+merchantAccount.getMerchantId()+",OutletId:"+merchantAccount.getOutletId();
					OutletVo outletVo=new OutletVo();
					outletVo.setClientId(merchantAccount.getClientId());
					outletVo.setMerchantId(merchantAccount.getMerchantId());
					outletVo.setOutletId(merchantAccount.getOutletId());
					LogCvt.debug("定时任务: 白名单同步---------调用商户syncOutletInfo接口,请求参数为:"+parm);
					try {
						ResultVo resultVo=outletClient.syncOutletInfo(outletVo, isSynSucc, synType);
						if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
							LogCvt.debug("定时任务: 白名单同步---------调用商户syncOutletInfo接口成功");
							LogCvt.debug("定时任务: 白名单同步---------修改商户账户表开始");	
							result = merchantAccountMapper.updateMerchantAccount(merchantAccount);
							sqlSession.commit();
						}else{
							LogCvt.debug("定时任务: 白名单同步---------调用商户syncOutletInfo接口失败");
						}
					} catch (Exception e) {
						LogCvt.error("定时任务: 白名单同步---------调用商户syncOutletInfo接口失败,原因:"+ e.getMessage(), e);
						sqlSession.rollback(true);
					}
				}else{
					//新增商户调用接口
					String merchantId=merchantAccount.getMerchantId();
					String parm="clientId:"+clientId+",merchantId:"+merchantId;
					LogCvt.debug("定时任务: 白名单同步---------调用商户syncMerchantInfo接口,请求参数为:"+parm);
					try{
						ResultVo resultVo=merchantClient.syncMerchantInfo(merchantId,clientId,isSynSucc,synType);
						if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
							LogCvt.debug("定时任务: 白名单同步---------调用商户syncMerchantInfo接口成功");
							LogCvt.debug("定时任务: 白名单同步---------修改商户账户表开始");
							result = merchantAccountMapper.updateMerchantAccount(merchantAccount);
							sqlSession.commit();
						}else{
							LogCvt.debug("定时任务: 白名单同步---------调用商户syncMerchantInfo接口失败");
						}
					}catch(Exception e){
						LogCvt.error("定时任务: 白名单同步---------调用商户syncMerchantInfo接口失败,原因:"+ e.getMessage(), e);
					}
				}
			}
		}else{
			if( merchantAccountMapper != null ){
				merchantAccount.setAuditState(AccountAuditState.ingAudit.getCode());
				result = merchantAccountMapper.updateMerchantAccount(merchantAccount);
				sqlSession.commit();
			}
		}
		LogCvt.debug("定时任务: 白名单同步------修改账户表:"+(result?"成功":"失败"));
		
	}
	
	public static final String OPTION_TTPE = "0"; // 新增
	//public static final String OPTION_TTPE1 = "1"; // 修改
	// 同步白名单 
	private Result synchronousWhiteList(MerchantAccount merchantAccount){
		String merchentId="";
		if(!merchantAccount.getOutletId().equals("0")){
			merchentId = merchantAccount.getOutletId();
		}else{
			merchentId=merchantAccount.getMerchantId();
		}
		String merchentName = merchantAccount.getMerchantName();
		String accountNo = merchantAccount.getAcctNumber();
		String accountName = merchantAccount.getAcctName();
		String clientId = merchantAccount.getClientId();
		String mac=merchantAccount.getMac();
		Result result = new Result();
		//String OPTION=getOptionType(merchentId,clientId);
		try{
			StringBuffer logParam = new StringBuffer(clientId).append(" 商户 ")
			                                 .append(merchentId).append(merchentName).append(" 账户 ")
			                                 .append(accountNo).append(accountName).append(",mac").append(mac);
			LogCvt.debug("定时任务: 白名单同步------调用Order模块白名单同步接口-----参数:"+logParam.toString());
			ResultVo resultVo = client.setMerchantBankWhiteList(merchentId, 
					                                            merchentName, 
					                                            accountNo,
					                                            mac,
					                                            OPTION_TTPE, 
					                                            clientId, 
					                                            accountName);
			result = (Result) BeanUtil.copyProperties(Result.class, resultVo);
		}catch(Exception e){
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("通讯异常");
			LogCvt.error("定时任务: 白名单同步------调用设置同步白名单接口-----异常------原因: " + e.getMessage(), e);
		}
		return result;
	}
//	public String getOptionType(String merchantId,String clientId){
//		Merchant merchant=merchantMapper.selectMerchantById(merchantId,clientId);
//		//待审核则为新增
//		if(merchant.getAuditState().equals(ProductAuditState.noAudit.getCode())||merchant.getAuditState().equals(ProductAuditState.waitSynchAudit.getCode())){
//			return OPTION_TTPE;
//		}else{
//			return OPTION_TTPE1;
//		}
//		
//	}

}
