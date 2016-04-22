package com.froad.logic.process;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.Constants;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.mysql.mapper.MerchantTempMapper;
import com.froad.db.redis.MerchantRedis;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.MerchantAccountLogic;
import com.froad.logic.MerchantAuditLogic;
import com.froad.logic.MerchantLogic;
import com.froad.logic.impl.MerchantAccountLogicImpl;
import com.froad.logic.impl.MerchantAuditLogicImpl;
import com.froad.logic.impl.MerchantLogicImpl;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.MerchantTemp;
import com.froad.po.Result;
import com.froad.po.mq.AuditMQ;
import com.froad.service.AbstractProcessService;
import com.froad.support.DataPlatLogSupport;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.JSonUtil;

/**
 * 商户审核的阻塞队列
 * @author ll
 *
 */
public class MerchantAuditProcess extends AbstractProcessService {
	
	private MerchantLogic merchantLogic = new MerchantLogicImpl();
	private MerchantAccountLogic merchantAccountLogic = new MerchantAccountLogicImpl();
	private MerchantAuditLogic merchantAuditLogic = new MerchantAuditLogicImpl();
	
	
	/**
	 * 商户审核：
	 * 		录入审核和编辑审核
	 * 
	 * @author ll 20151014 
	 */
	@Override
	public void processMsg(String msg) {
		LogCvt.info("处理商户审核MQ消息:"+msg);
		//将json转换成AuditMQ请求处理对象
		AuditMQ auditMQ = JSonUtil.toObject(msg, AuditMQ.class);
		SqlSession sqlSession = null;
		MerchantMapper merchantMapper = null;
		MerchantTempMapper merchantTempMapper = null;
		
		try{
			
			String mqAuditState =  auditMQ.getAuditState();//审核状态
			
			if(ProductAuditState.noAudit.getCode().equals(mqAuditState)
					|| ProductAuditState.passAudit.getCode().equals(mqAuditState)
        			|| ProductAuditState.failAudit.getCode().equals(mqAuditState)){
				
				String merchantId = auditMQ.getBusinessId();
				String clientId = auditMQ.getClientId();
				
				/***********************判断录入审核/编辑审核****************************/
				int auditTypeFlag = Constants.INPUT_AUDIT;//审核类型标识：0-录入审核 1-编辑审核
				Merchant resultMerchant = merchantLogic.findMerchantByMerchantId(merchantId);//mysql源数据
				if(Checker.isEmpty(resultMerchant)){
					LogCvt.error("商户"+merchantId+"记录不存在");
					return;
				}
				if(StringUtils.equals(resultMerchant.getDisableStatus(),MerchantDisableStatusEnum.unregistered.getCode())){
					LogCvt.error("商户"+merchantId+"已解约");
					return;
				}
				if(ProductAuditState.passAudit.getCode().equals(resultMerchant.getAuditState())){
        			if(ProductAuditState.noAudit.getCode().equals(resultMerchant.getEditAuditState())
        					//新增录入的时候，编辑状态是1-审核通过，编辑的时候，此1状态也做为编辑审核标识
        					|| ProductAuditState.passAudit.getCode().equals(resultMerchant.getEditAuditState())
        					|| ProductAuditState.failAudit.getCode().equals(resultMerchant.getEditAuditState())){
        				auditTypeFlag = Constants.EDIT_AUDIT;
        			}else{
        				LogCvt.error("商户"+merchantId+"已审核通过");
    					return;
        			}
            	}
				LogCvt.info((auditTypeFlag==Constants.INPUT_AUDIT?"录入":"编辑")+"审核："+resultMerchant.getMerchantName()+"["+merchantId+"]进行业务处理逻辑……");

				
				/***********************业务处理****************************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				merchantMapper = sqlSession.getMapper(MerchantMapper.class);
				merchantTempMapper = sqlSession.getMapper(MerchantTempMapper.class);
				
				
									
				//待审核(修改审核流水号、编辑审核状态)
				if(ProductAuditState.noAudit.getCode().equals(mqAuditState)){
					//编辑审核
	            	if(auditTypeFlag == Constants.EDIT_AUDIT){
	            		if(ResultCode.success.getCode().equals(auditMQ.getResult().getResultCode())){
	            			/**
	            			 * 修改商户表编辑审核状态、商户临时表流水号
	            			 */
		            		//编辑审核状态
		            		Merchant setMerchant = new Merchant();
							setMerchant.setMerchantId(merchantId);
							setMerchant.setEditAuditState(mqAuditState);
							merchantMapper.updateAuditMerchantByMerchantId(setMerchant);
							
							//审核流水号
							MerchantTemp setMerchantTemp = new MerchantTemp();
							setMerchantTemp.setMerchantId(merchantId);
							setMerchantTemp.setAuditId(auditMQ.getAuditId());
							merchantTempMapper.updateMerchantTemp(setMerchantTemp);
	            		}else{
	            			//删除MerchantTemp数据
							merchantTempMapper.deleteMerchantTemp(merchantId);
	            		}
	            	}
				}
				//审核通过
				else if(ProductAuditState.passAudit.getCode().equals(mqAuditState)){
					if(!ResultCode.success.getCode().equals(auditMQ.getResult().getResultCode())){
						LogCvt.error("MQ队列信息Result异常，非成功的不做处理……");
						return;
					}
					
					//是否终审
					if(auditMQ.getIsFinalAudit()){
						//录入审核
		            	if(auditTypeFlag == Constants.INPUT_AUDIT){
		            		this.inputAudit(merchantMapper,resultMerchant, auditMQ);
						}
		            	//编辑审核
		            	else{
		            		this.editAudit(sqlSession,merchantMapper,merchantTempMapper,resultMerchant, auditMQ);
						}
					}else{
						LogCvt.info("商户"+merchantId+"非终审状态，不做后续审核通过处理操作……");
					}
				}
				//审核不通过
				else{
					if(!ResultCode.success.getCode().equals(auditMQ.getResult().getResultCode())){
						LogCvt.error("MQ队列信息Result异常，非成功的不做处理……");
						return;
					}
					
					boolean isSuccess = true;
					//审核不通过
					Merchant setMerchant = new Merchant();
					setMerchant.setMerchantId(merchantId);
					setMerchant.setClientId(clientId);
					//修改内容(审核状态、审核人、审核时间、审核备注)
					setMerchant.setAuditStaff(auditMQ.getAuditStaff());
					//setMerchant.setReviewStaff(auditMQ.getReviewStaff());
					setMerchant.setAuditComment(auditMQ.getAuditComment());
					setMerchant.setAuditTime(auditMQ.getFinishTime()==null?new Date():new Date(auditMQ.getFinishTime()));
					if(auditTypeFlag == Constants.INPUT_AUDIT){//录入审核
						setMerchant.setAuditState(mqAuditState);
						//1:更新redis
						isSuccess = MerchantRedis.updateAuditMerchant(setMerchant);
					}else{//编辑审核
						setMerchant.setEditAuditState(mqAuditState);
					}
					
					//2:更新商户信息
					if(isSuccess){
						merchantMapper.updateMerchant(setMerchant);
					}
					
				}
				
				//提交事务
				sqlSession.commit(true);
				//是否终审
				if(ProductAuditState.passAudit.getCode().equals(mqAuditState) && auditMQ.getIsFinalAudit() && auditTypeFlag == Constants.INPUT_AUDIT){
					// 录入编辑审核商户日志落地
					new DataPlatLogSupport().auditMerchantLog(clientId,merchantId);
				}else{
					// 修改商户日志落地
					new DataPlatLogSupport().modifyMerchantLog(clientId,merchantId);
				}
				
			}else{
				LogCvt.error("审核状态"+auditMQ.getAuditState()+"有误，只处理审核通过["+ProductAuditState.passAudit.getCode()+"],审核未通过["+ProductAuditState.failAudit.getCode()+"]的数据");
			}
			
			
			
		}catch(Exception e){
			if (null != sqlSession)
				sqlSession.rollback(true);
			LogCvt.error("商户审核异常："+e.getMessage(),e);
		}finally{
			if (null != sqlSession)
				sqlSession.close();
		}
	}
	
	
	
	/**
	 * 录入审核
	 * @param resultMerchant
	 * @param auditMQ
	 */
	private void inputAudit(MerchantMapper merchantMapper,Merchant resultMerchant,AuditMQ auditMQ) throws Exception{
		String merchantId = resultMerchant.getMerchantId();
		String clientId = auditMQ.getClientId();
		
		/**
		 * 
		 * 1.同步白名单
		 * 2.mysql(商户表、商户账户表、发短信)
		 * 3.redis
		 * 4.mongo
		 * 
		 */
		
		//1.同步白名单
		MerchantAccount merchantAccount = merchantAccountLogic.findMerchantAccountByMerchantId(clientId, merchantId, "0");
		if(Checker.isEmpty(merchantAccount)){
			return;
		}
		
		boolean isSync = false;//同步结果标识
		//注册银行白名单，并根据注册结果更新账户信息表的同步状态及审核状态！
		Result result = merchantAccountLogic.registBankWhiteList(merchantAccount, "0", Constants.INPUT_AUDIT_MERCHANT_TYPE);
		if(result.getResultCode().equals(ResultCode.success.getCode())){
			isSync = true;
		}			
		
		
		//2.mysql(修改商户表、商户帐号表、发短信)
		boolean isResultSucc = false;
		Merchant setMerchant = null;
		
		//修改商户表(mysql/redis/mongo/发短信)
		setMerchant = new Merchant();
		setMerchant.setMerchantId(merchantId);
		setMerchant.setClientId(clientId);
		setMerchant.setAuditStaff(auditMQ.getAuditStaff());
		setMerchant.setAuditComment(auditMQ.getAuditComment());
		setMerchant.setAuditTime(auditMQ.getFinishTime()==null?new Date():new Date(auditMQ.getFinishTime()));
		//签约时间为审核通过时间，签约到期时间为签约时间增加1年
		Date begDate = DateUtil.getNow();
    	Date endDate = DateUtil.skipDateTime(begDate, 365);
		setMerchant.setContractBegintime(begDate);
		setMerchant.setContractEndtime(endDate);
				
		//根据同步结果修改商户表对应状态值		
		isResultSucc = merchantLogic.synMerchantSynType(Constants.INPUT_AUDIT,setMerchant,merchantMapper,isSync);
		
		if(isResultSucc){
			// 添加落地日志，录入审核
			LogCvt.info("录入审核同步白名单,商户ID："+merchantId+",账号:"+merchantAccount.getAcctNumber()+",账户名:"+merchantAccount.getAcctName()+"业务逻辑处理完成！！！！");
		}
		
		
	}
	
	
	/**
	 * 编辑审核
	 * @param resultMerchant
	 * @param auditMQ
	 */
	private void editAudit(SqlSession sqlSession,MerchantMapper merchantMapper,MerchantTempMapper merchantTempMapper,Merchant resultMerchant,AuditMQ auditMQ) throws Exception{
		String merchantId = resultMerchant.getMerchantId();
		String clientId = auditMQ.getClientId();
		
		/**
		 * 1.mysql(商户表)
		 * 2.临时表同步到主表、白名单同步
		 * 
		 */
		Merchant setMerchant = null;
		
		//修改商户表(审核相关信息)
		setMerchant = new Merchant();
		setMerchant.setMerchantId(merchantId);
		setMerchant.setClientId(clientId);
		setMerchant.setAuditStaff(auditMQ.getAuditStaff());
		//setMerchant.setReviewStaff(auditMQ.getReviewStaff());
		setMerchant.setAuditComment(auditMQ.getAuditComment());
		setMerchant.setAuditTime(auditMQ.getFinishTime()==null?new Date():new Date(auditMQ.getFinishTime()));
		//默认编辑审核状态：审核通过， 再后续帐号、户名做了变更后， 再修改对应的状态
		setMerchant.setEditAuditState(ProductAuditState.passAudit.getCode());
		

		//1:更新商户信息
		merchantMapper.updateMerchant(setMerchant);
		
		//2：更新审核流水号(总行的直接审核通过没有状态0的mq，故其没有更新审核流水号，需在做业务处理之前将其流水号改掉)
		//总行的才更新到记录条数，其余级别在待审核0的mq中已处理，此处更新条数为0
		MerchantTemp setMerchantTemp = new MerchantTemp();
		setMerchantTemp.setMerchantId(merchantId);
		setMerchantTemp.setAuditId(auditMQ.getAuditId());
		merchantTempMapper.updateMerchantTemp(setMerchantTemp);
		
		sqlSession.commit();
		
		//3：临时表同步主表、白名单同步
		merchantAuditLogic.auditMerchantEdit(auditMQ.getAuditId());
		
		
	}
	
	
	
}
