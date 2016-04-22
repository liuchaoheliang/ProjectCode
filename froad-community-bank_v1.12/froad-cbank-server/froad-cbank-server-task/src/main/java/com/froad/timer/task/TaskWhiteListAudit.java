package com.froad.timer.task;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

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
 * 白名单审核 - 定时任务
 * 
 * @author lf 2015.09.11
 * @modify lf 2015.09.11
 * 
 * */
public class TaskWhiteListAudit implements Runnable{

	SqlSession sqlSession = null;
	BankCardService.Iface client = (BankCardService.Iface) ThriftClientProxyFactory.createIface(BankCardService.class, ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
    MerchantService.Iface merchantClient=(MerchantService.Iface)ThriftClientProxyFactory.createIface(MerchantService.class, ThriftConfig.MERCHANT_SERVICE_HOST, ThriftConfig.MERCHANT_SERVICE_PORT);
    OutletService.Iface outletClient=(OutletService.Iface)ThriftClientProxyFactory.createIface(OutletService.class, ThriftConfig.MERCHANT_SERVICE_HOST, ThriftConfig.MERCHANT_SERVICE_PORT);

	MerchantAccountMapper merchantAccountMapper = null;
	MerchantMapper merchantMapper=null;
    public static final String isSynSucc="0";//同步成功
    public static final String isSynFail="1";//同步失败
    public static final String synType ="1";//1、审核通知 0.同步通知
	@Override
	public void run() {

//                             _ooOoo_
//                            o8888888o
//                            88" . "88
//                            (| -_- |)
//                             O\ = /O
//                         ____/`---'\____
/*********************************************************************/
/**                                                                  */
/** 1 - 找符合条件的（账户表-同步成功，审核中）                                                                   */
/** 2 - 调用Order模块查询审核状态接口                                                                                  */
/** 3 - 判断调用结果                                                                                                                 */
/** 4 - 调用结果-成功   修改账户表-审核通过，修改商户表-审核通过                                       */
/** 5 - 调用结果-非成功  修改账户表-审核不通过，修改商户表-审核不通过和备注                    */
/**                                                                  */
/*********************************************************************/			
		
		LogCvt.debug("定时任务: 白名单审核------开始------");
		
		try{
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantAccountMapper = sqlSession.getMapper(MerchantAccountMapper.class);
			merchantMapper=sqlSession.getMapper(MerchantMapper.class);
			// 找符合条件的账户信息（同步成功 & 审核中）
			MerchantAccount merchantAccount = new MerchantAccount();
			merchantAccount.setSynchState(AccountSynchState.synchSucc.getCode());
			merchantAccount.setAuditState(AccountAuditState.ingAudit.getCode());
			List<MerchantAccount> merchantAccountList = merchantAccountMapper.queryByConditionList(merchantAccount);
			
			if(CollectionUtils.isEmpty(merchantAccountList)){
				LogCvt.debug("定时任务: 白名单审核------完成(无同步成功&审核中的账户信息)------");
				return;
			}
			
			LogCvt.debug("定时任务: 白名单审核------条数 "+merchantAccountList.size()+"------");
			
			// 循环应审核白名单的账户信息
			for( MerchantAccount account : merchantAccountList ){
				Result result = new Result();
				
				// 台州硬编码处理====是否审核白名单 9999: 查询异常  00 审核成功  01 审核失败  02 审核中
				if(ClientUtil.getClientId(account.getClientId())){
					result = ifAuditWhiteList(account);
					//result.resultCode="00";
						if( "00".equals(result.resultCode) ){
								LogCvt.debug("定时任务: 白名单审核-------调用Order模块白名单同步接口-----成功,resultCode="+result.resultCode);
								// 审核成功处理
								auditSuccessProcess(account);
							}else if("01".equals(result.resultCode)){ // 审核 - 非成功
								LogCvt.debug("定时任务: 白名单审核-------调用Order模块白名单同步接口-----不成功,resultCode"+result.resultCode);
								
								// 审核非成功处理
								auditNotSuccessProcess(account);
							}else if("9999".equals(result.resultCode)){
								LogCvt.debug("定时任务: 白名单审核-------调用Order模块白名单同步查询异常-----不成功,resultCode"+result.resultCode);
			
							}else{
								LogCvt.debug("定时任务: 白名单审核-------调用Order模块白名单同步审核中-----不成功,resultCode"+result.resultCode);
							}
						
				}else{
                    LogCvt.debug("定时任务: 白名单审核-------调用Order模块白名单同步接口-----成功,resultCode="+result.resultCode);
					
					// 审核成功处理
					auditSuccessProcess(account);
				}
				
				
			}
			
		}catch(Exception e){
			LogCvt.error("定时任务: 白名单审核------系统异常------");
			LogCvt.error(e.getMessage(), e);
		}finally {
			if( sqlSession != null ) sqlSession.close();
			LogCvt.debug("定时任务: 白名单审核------结束------");
		}
		
	}

	// 审核成功处理
	private void auditSuccessProcess(MerchantAccount account){
		/** 修改账户表审核通过*/
		account.setAuditState(AccountAuditState.passAudit.getCode());
				LogCvt.debug("定时任务: 白名单审核----------商户账户"+account.getAcctName()+",id="+account.getId()+"审核更新开始");
				/** 修改商户户表审核通过,调用商户模块接口*/
				String clientId=account.getClientId();
				String merchantId=account.getMerchantId();
				String outletId=account.getOutletId();
				//新增门店编辑审核
				if(!outletId.equals("0")){
					String parm="clientId:"+clientId+",merchantId:"+merchantId+",outletId:"+outletId;	
					LogCvt.debug("定时任务: 白名单审核---------调用商户syncOutletInfo接口,请求参数为:"+parm);
					OutletVo outletVo=new OutletVo();
					outletVo.setClientId(clientId);
					outletVo.setMerchantId(merchantId);
					outletVo.setOutletId(outletId);
					try {
						ResultVo resultVo=outletClient.syncOutletInfo(outletVo, isSynSucc, synType);
						if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
							LogCvt.debug("定时任务: 白名单审核---------调用商户syncOutletInfo接口成功");
							LogCvt.debug("定时任务: 白名单审核---------修改商户账户表状态开始");
							try{
								merchantAccountMapper.updateMerchantAccount(account);
								sqlSession.commit();
							}catch(Exception e){
								sqlSession.rollback(true);
								LogCvt.error("定时任务: 白名单审核----------审核更新操作异常--------"+account.getAcctName()+",id="+account.getId()+"审核更新操作失败",e);	
							}
						}
					} catch (Exception e) {
						LogCvt.error("定时任务: 白名单审核---------调用商户syncOutletInfo接口失败,原因:"+ e.getMessage(), e);
					}
				}else{
					String parm="clientId:"+clientId+",merchantId:"+merchantId;
					LogCvt.debug("定时任务: 白名单审核---------调用商户syncMerchantInfo接口,请求参数为:"+parm);
					try{
						ResultVo resultVo=merchantClient.syncMerchantInfo(merchantId,clientId,isSynSucc,synType);
						if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
							LogCvt.debug("定时任务: 白名单审核---------调用商户syncMerchantInfo接口成功");
							LogCvt.debug("定时任务: 白名单审核---------修改商户账户表状态开始");
							try{
								merchantAccountMapper.updateMerchantAccount(account);
								sqlSession.commit();
							}catch(Exception e){
								sqlSession.rollback(true);
								LogCvt.error("定时任务: 白名单审核----------审核更新操作异常--------"+account.getAcctName()+",id="+account.getId()+"审核更新操作失败",e);	
							}
						}else{
							LogCvt.debug("定时任务: 白名单审核---------调用商户syncMerchantInfo接口失败");
						}
					}catch(Exception e){
						LogCvt.error("定时任务: 白名单审核---------调用商户syncMerchantInfo接口失败,原因:"+ e.getMessage(), e);
					}
				}
				

		
	}
	
	// 审核非成功处理
	private void auditNotSuccessProcess(MerchantAccount account){
		/** 修改账户表审核不通过*/
		account.setAuditState(AccountAuditState.failAudit.getCode());
		try{
				/** 修改商户户表审核不通过*/
				String clientId=account.getClientId();
				String merchantId=account.getMerchantId();
				String outletId=account.getOutletId();
				if(!outletId.equals("0")){
					String parm="clientId:"+clientId+",merchantId:"+merchantId+",outletId:"+outletId;	
					LogCvt.debug("定时任务: 白名单审核---------调用商户syncOutletInfo接口,请求参数为:"+parm);
					OutletVo outletVo=new OutletVo();
					outletVo.setClientId(clientId);
					outletVo.setMerchantId(merchantId);
					outletVo.setOutletId(outletId);
					try {
						ResultVo resultVo=outletClient.syncOutletInfo(outletVo, isSynFail, synType);
						if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
							LogCvt.debug("定时任务: 白名单审核---------调用商户syncOutletInfo接口成功");
							LogCvt.debug("定时任务: 白名单审核---------修改商户账户表状态开始");
							try{
								merchantAccountMapper.updateMerchantAccount(account);
								sqlSession.commit();
							}catch(Exception e){
								sqlSession.rollback(true);
								LogCvt.error("定时任务: 白名单审核----------审核更新操作异常--------"+account.getAcctName()+",id="+account.getId()+"审核更新操作失败",e);	
							}
						}
					} catch (Exception e) {
						LogCvt.error("定时任务: 白名单审核---------调用商户syncOutletInfo接口失败,原因:"+ e.getMessage(), e);
					}
				}else{
					String parm="clientId:"+clientId+",merchantId:"+merchantId;
					LogCvt.debug("定时任务: 白名单审核---------调用商户syncMerchantInfo接口,请求参数为:"+parm);
					try{
						ResultVo resultVo=merchantClient.syncMerchantInfo(merchantId,clientId,isSynFail,synType);
						if(resultVo.getResultCode().equals(ResultCode.success.getCode())){
							LogCvt.debug("定时任务: 白名单审核---------调用商户syncMerchantInfo接口成功");
							try{
								merchantAccountMapper.updateMerchantAccount(account);
								sqlSession.commit();
								LogCvt.debug("定时任务: 白名单审核-----------商户账户"+account.getAcctName()+",id="+account.getId()+"审核不通过更新成功");
							}catch(Exception e){
								sqlSession.rollback(true);
								LogCvt.error("定时任务: 白名单审核----------审核更新操作异常--------"+account.getAcctName()+",id="+account.getId()+"审核更新操作失败",e);	
							}
						}else{
							LogCvt.debug("定时任务: 白名单审核---------调用商户syncMerchantInfo接口失败");
						}
					}catch(Exception e){
						LogCvt.error("定时任务: 白名单审核---------调用商户syncMerchantInfo接口失败,原因:"+ e.getMessage(), e);
					}
				}
		}catch(Exception e){
					LogCvt.error("定时任务: 白名单审核-----------审核更新操作异常:"+account.getAcctName()+",id="+account.getId()+"审核更新操作失败",e);
					
				}
	   

	}
	
	// 是否审核白名单
	private Result ifAuditWhiteList(MerchantAccount account){
		Result result =new Result();
		String clientId=account.getClientId();
		String acctName=account.getAcctName();
		String acctNumber=account.getAcctNumber();
		String parm="clientId:"+clientId+",AcctName:"+acctName+",AcctNumber:"+acctNumber;
		LogCvt.debug("定时任务: 白名单审核--------调用order模块接口,请求参数："+parm);
		try {
		ResultVo resultVo=new ResultVo();
		 resultVo=client.auditStatusQuery(clientId, acctName, acctNumber);
		result = (Result) BeanUtil.copyProperties(Result.class, resultVo);
		} catch (TException e) {
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc("通讯异常");
			LogCvt.error("定时任务: 白名单审核-------调用order模块接口-----异常------原因: " + e.getMessage(), e);
		}
		return result;
	}
}
