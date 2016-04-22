package com.froad.logic.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.AuditMongo;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AuditProcessCommonMapper;
import com.froad.db.mysql.mapper.AuditTaskCommonMapper;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.db.mysql.mapper.ProductMapper;
import com.froad.db.mysql.mapper.ProductSeckillMapper;
import com.froad.db.redis.AuditRedis;
import com.froad.enums.AuditTaskState;
import com.froad.enums.ProductAuditStage;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ProductStatus;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.log.BankLogs;
import com.froad.log.vo.HeadKey;
import com.froad.log.vo.ProductDetailLog;
import com.froad.log.vo.ProductLog;
import com.froad.logback.LogCvt;
import com.froad.logic.BankAuditLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantAduitCommonLogic;
import com.froad.logic.res.PreAuditNumRes;
import com.froad.po.AuditProcess;
import com.froad.po.AuditTask;
import com.froad.po.BankAudit;
import com.froad.po.BankOperator;
import com.froad.po.Org;
import com.froad.po.Product;
import com.froad.po.Result;
import com.froad.support.Support;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.OrgSuperUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.SerialNumberUtil;

/**
 * 
 * <p>@Title: BankAuditLogicImpl.java</p>
 * <p>Description: 描述 </p> 银行审核Logic实现类<>
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月3日
 */
public class BankAuditLogicImpl implements BankAuditLogic {


	/**
     * 批量审核
     * @param auditFlag 审核标志 1-商品 2-商户 3-秒杀商品
     * @param userId 审核人id
     * @param bankAuditList 审核对象集合
     * @return String    审核失败的审核对象id及名称(id:name;)  审核成功返回OK  审核异常返回ERROR
     */
	@Override
	public String[] auditBatch(int auditFlag, long userId, List<BankAudit> bankAuditList)  throws FroadServerException, Exception{
        
        String auditName="";
        boolean isSuccess = false;//默认审核操作异常
        SqlSession sqlSession = null;
        String[] result = new String[2];
        String isFinalAudit = "0";//是否最终审核
        
        try{
            BankAudit resultBankAudit=null;//根据审核id查询得出的信息
            AuditTask resultAuditTask = null;//db中查询出的auditTask对象
            ProductMapper productMapper =null;
            MerchantMapper merchantMapper=null;
            ProductSeckillMapper productSeckillMapper =null;
            MerchantAduitCommonLogic merchantAuditCommonLogic = null;

            StringBuilder message = new StringBuilder();
            CommonLogic commonLogic = null;
            
            Org org=null;
            String clientId="";//客户端id
            String auditStaff="";//审核人username
            String dbAuditOrgCode="";//商品/商户表中原待审核机构
            String dbAuditEndOrgCode="";//商品/商户表中的终审机构
            String auditOrgCode="";//审核完成后的待审核机构
            String auditComment="";//审核备注
            String auditState="";//审核状态 0-待审核 1-审核通过 2-审核不通过
            String dbAuditStage="";//审核步骤
            String dbEditAuditState="";//编辑审核状态
            String auditId="";//审核对象Id(商品id或商户id)
            
            
            
            /**符合数据由下执行审核操作*/
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            if(auditFlag==1){//商品
            	auditName="商品";
        		productMapper = sqlSession.getMapper(ProductMapper.class);
            }else if(auditFlag==2){//商户
            	auditName="商户";
            	merchantMapper = sqlSession.getMapper(MerchantMapper.class);
            }else if(auditFlag==3){//秒杀商品
            	auditName="秒杀商品";
            	productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);
            	productMapper = sqlSession.getMapper(ProductMapper.class);
            }
            
            
        	for(BankAudit filterBankAudit:bankAuditList){

            	//判断条件数据是否符合自定义，不符合不执行后续程序
            	if(auditFlag!=1 && auditFlag!=2 && auditFlag!=3){
            		message.append("审核标志不符合系统自定义类型，只允许：1-商品 2-商户 3-秒杀商品");
            		continue;
            	}
            	if(filterBankAudit==null){
            		message.append("所需审核对象不可为空");
            		continue;
            	}
            	if(!ProductAuditState.failAudit.getCode().equals(filterBankAudit.getAuditState())
            			&&
            		!ProductAuditState.passAudit.getCode().equals(filterBankAudit.getAuditState()))
            	{
            		 message.append("审核状态不符合系统自定义状态");
            		 continue;
            	}
            	
            	//审核对象id
            	auditId = filterBankAudit.getAuditId();
                if(auditFlag==1){//商品
            		resultBankAudit=productMapper.findProductById(auditId);
                }else if(auditFlag==2){//商户
                	resultBankAudit=merchantMapper.findMerchantById(auditId);
                }else if(auditFlag==3){//秒杀商品
                	resultBankAudit=productSeckillMapper.findProductSeckillById(auditId);
                }

                /**
                 * 判断查询审核对象是否存在
                 */
                if(Checker.isEmpty(resultBankAudit)){
            	    message.append(auditName+":"+auditId+"记录不存在;");
            	    continue;
                }
                
                commonLogic =  new CommonLogicImpl();

                //db中的客户端id
            	clientId=resultBankAudit.getClientId();
            	//db中的待审核机构
            	dbAuditOrgCode=resultBankAudit.getAuditOrgCode();
            	//db中的终审机构
            	dbAuditEndOrgCode = resultBankAudit.getAuditEndOrgCode();
            	//db中的审核步骤
            	dbAuditStage=resultBankAudit.getAuditStage();
            	//审核人username
            	auditStaff= filterBankAudit.getAuditStaff();
            	//审核备注
            	auditComment=filterBankAudit.getAuditComment();
            	//审核状态 0-待审核 1-审核通过 2-审核不通过
            	auditState=filterBankAudit.getAuditState();
            	
            	
            	/***********************记录打印日志跟踪***********************************/
            	String auditType = "" ;//审核类型：录入审核 或者 编辑审核
            	int auditTypeFlag = 0;//审核类型标识：0-录入审核 1-编辑审核
            	if(ProductAuditState.passAudit.getCode().equals(resultBankAudit.getAuditState())
            			|| ProductAuditState.failAudit.getCode().equals(resultBankAudit.getAuditState())){
            		if(auditFlag==2){//商户
            			//db中的编辑审核状态
                    	dbEditAuditState=resultBankAudit.getEditAuditState();
            			if(ProductAuditState.noAudit.getCode().equals(dbEditAuditState)){
            				merchantAuditCommonLogic = new MerchantAduitCommonLogicImpl();
            				resultAuditTask = merchantAuditCommonLogic.getAuditTaskByMerchantId(auditId);
                			//判断查询审核任务订单对象是否存在
                            if(Checker.isEmpty(resultAuditTask)){
                        	    message.append(auditName+":"+auditId+"审核任务订单记录不存在;");
                        	    continue;
                            }
                            
            				auditType="编辑审核：";
            				auditTypeFlag=1;
            			}
            		}
            	}else{
            		auditType="录入审核：";
            	}
            	//打印日志跟踪
            	String desc = ProductAuditState.failAudit.getCode().equals(auditState)?ProductAuditState.failAudit.getDescribe():ProductAuditState.passAudit.getDescribe();
            	LogCvt.info("客户端"+clientId+"的"+auditName+auditId+"正在被"+auditStaff+"进行"+auditType+desc+"的操作");
            	/***********************记录打印日志跟踪***********************************/
            	
            	
            	
            	//控制当前操作员orgCode是否等于该审核对象的待审核orgCode
            	if(auditFlag!=3){//秒杀商品不需要此控制
            		BankOperator auditStaffObject = commonLogic.getBankOperatorById(clientId, userId);

	            	if(Checker.isNotEmpty(auditStaffObject)){
	            		if(!auditStaffObject.getOrgCode().equals(dbAuditOrgCode)){
	            			 message.append(auditName+":"+auditId+"在当前机构下已处理过审核操作，不需再次提交;");
	                    	 continue;
	            		}
	            	}else{
	            		message.append(auditStaff+"审核人记录不存在;");
	               	 	continue;
	            	}
            	}
            	
            	
            	//录入审核
            	if(auditTypeFlag == 0){
	            		/**
	                	 * 录入审核
	                     * 根据商品/商户所属的orgCode判断该机构是否需要双人审核
	                     * 		不需要双人审核：
	                     * 		--->a.更新auditTime,auditStage,auditStaff,auditOrgCode
	                     * 		双人审核：
	                     * 		--->a.初审
	                     * 		--->b.复审
	                     * 
	                     * 
	                     */
	                	//公共修改内容 auditTime,auditComment
            			BankAudit setBankAudit = new BankAudit();//需要修改sql的审核对象
	                	setBankAudit.setAuditTime(new Date());
	                	setBankAudit.setAuditComment(auditComment);
	                	
	                    //审核未通过
	                    if(ProductAuditState.failAudit.getCode().equals(auditState)){
	                    	setBankAudit.setAuditState(auditState);
	                    	setBankAudit.setAuditStaff(auditStaff);//设置审核人员
	                    }
	                    //审核通过
	                    else if(ProductAuditState.passAudit.getCode().equals(auditState)){
	                    	
	                    	//取商品或商户所属的待审核机构判断该机构是否需要双人审核
	                    	org=commonLogic.getOrgByOrgCode(dbAuditOrgCode, clientId);
	                    	//待审核机构
	                    	auditOrgCode=this.getAuditOrgCode(dbAuditOrgCode,dbAuditEndOrgCode,clientId);
	                    	
	                    	
	                    	//是否需要双人审核
	                    	if(auditFlag == 3 || !org.getNeedReview()){//不需要双人审核（或者是秒杀商品）
	                    		//设置待审核机构
	                    		setBankAudit.setAuditOrgCode(auditOrgCode);
	                    		setBankAudit.setAuditStaff(auditStaff);//设置审核人员
	                    		
	                    		//最终审核通过把is_enable改为有效，把auditState改为1审核通过
	                    		if("0".equals(auditOrgCode)){
	                    			 if(auditFlag==2){//商户
	                    				 //最终审核通过后将商户改为有效(isEnable/audit_state统一由调商户模块的接口修改 20150915修改)
	                    				 //setBankAudit.setIsEnable(true);
	                    				 
	                    				 //审核商户时：重庆需将帐号同步到银行系统，故其设置状态为4-审核通过待同步
		                    			 //if(Constants.CHONGQING.equals(clientId)){
		                    				 //setBankAudit.setAuditState(ProductAuditState.waitSynchAudit.getCode());
		                    			 //}
		                    			 
	                    			 }else{
	                    				 //最终审核通过后将商品改为已上架
	                    				 setBankAudit.setIsMarketable(ProductStatus.onShelf.getCode());
	                    				 setBankAudit.setRackTime(new Date());//上架时间 ll add 20151019 
	                    				 setBankAudit.setAuditState(auditState);
	                    			 }
	                    			 isFinalAudit="1";   
	                    			
	                    		}
	                    		
	                    	}else{//需要双人审核
	                    		/**
	                    		 * 判断是否初审复审
	                    		 */
	                    		if(Checker.isEmpty(dbAuditStage)){
	                    			//说明：第一次添加商品/商户，并无进行审核动作，第一次审核为初审
	                    			//进行初审操作 <更新auditStage=0>
	                            	setBankAudit.setAuditStage(ProductAuditStage.firstAudit.getCode());
	                            	setBankAudit.setAuditStaff(auditStaff);//设置审核人员
	                    			
	                    		}else {
	                    			if(ProductAuditStage.firstAudit.getCode().equals(dbAuditStage)){
	                                    //进行复审操作  <更新 auditStage=1,reviewStaff,auditOrgCode>
	                                	setBankAudit.setAuditStage(ProductAuditStage.secondAudit.getCode());
	                                	setBankAudit.setReviewStaff(auditStaff);//复核人
	                                	//设置待审核机构
	                            		setBankAudit.setAuditOrgCode(auditOrgCode);
	                            		
	                            		
	                            		//最终审核通过把is_enable改为有效，把auditState改为1审核通过
	                            		if("0".equals(auditOrgCode)){
	                            			if(auditFlag==2){//商户
	                            				//最终审核通过后将商户改为有效(isEnable/audit_state统一由调商户模块的接口修改 20150915修改)
	                           				 	//setBankAudit.setIsEnable(true);
	                            				
	                            				//审核商户时：重庆需将帐号同步到银行系统，故其设置状态为4-审核通过待同步
			   	                    			//if(Constants.CHONGQING.equals(clientId)){
			   	                    				//setBankAudit.setAuditState(ProductAuditState.waitSynchAudit.getCode());
			   	                    			//}
			   	                    			
	                           			 	}else{
	                           			 		//最终审核通过后将商品改为已上架
	                           			 		setBankAudit.setIsMarketable(ProductStatus.onShelf.getCode());
	                           			 		setBankAudit.setRackTime(new Date());//上架时间 ll add 20151019 
		                           			 	setBankAudit.setAuditState(auditState);
	                           			 	}
	                            			isFinalAudit="1";
	                            		}
	                            		
	                                } else if(ProductAuditStage.secondAudit.getCode().equals(dbAuditStage)){
	                                	//说明：下一个审核机构的初审，因上一个审核机构时并未将auditStage清空
	                                	//进行初审操作 <更新auditStage=0>
	                                	setBankAudit.setAuditStage(ProductAuditStage.firstAudit.getCode());
	                                	setBankAudit.setAuditStaff(auditStaff);//设置审核人员
	                                }
	                    		}
	                    	}
	                    }
	                    
	                    
	                    if(Checker.isNotEmpty(setBankAudit)){
	                    	setBankAudit.setClientId(filterBankAudit.getClientId());
	                    	setBankAudit.setAuditId(auditId);
	                    	
	                    	 try{
	    	                	//调用mapper修改sql语句
	    	                	if(auditFlag==1){//商品
	    	                		setBankAudit.setMerchantId(resultBankAudit.getMerchantId());
	    	                		isSuccess=productMapper.auditProduct(setBankAudit);
	    	            		}else if(auditFlag==2){//商户
	    	            			//签约时间为审核通过时间，签约到期时间为签约时间增加1年 modify fgy 2015/09/16
	    	            			Date begDate = DateUtil.getNow();
	    	            	    	Date endDate = DateUtil.skipDateTime(begDate, 365);
	    	            	    	setBankAudit.setContractBegintime(begDate);
	    	            	    	setBankAudit.setContractEndtime(endDate);
	    	            			
	    	            			isSuccess=merchantMapper.auditMerchant(setBankAudit);
	    	            		}else if(auditFlag==3){//秒杀商品
	    	            			isSuccess=productSeckillMapper.auditProductSeckill(setBankAudit);
	    	            			if (isSuccess) {
	    	            				Product p = new Product();
	    	            				p.setProductId(setBankAudit.getAuditId());
	    	            				p.setClientId(setBankAudit.getClientId());
	    	            				if (ProductStatus.onShelf.getCode().equals(setBankAudit.getIsMarketable())) {
	    	            					p.setIsSeckill("1"); // 秒杀已上架
	    	            				} else {
	    	            					p.setIsSeckill("2"); // 秒杀未上架
	    	            				}
	    	            				productMapper.updateProductIsSeckill(p);
	    	            				setBankAudit.setIsSeckill(p.getIsSeckill());
	    	            			}
	    	            		}
	    	                	//设置Redis同步数据
	    	                	isSuccess = AuditRedis.audit(auditFlag, setBankAudit);
	    	                	
	    	                	//设置Mongo同步数据
	    	                	isSuccess = AuditMongo.audit(auditFlag, setBankAudit);
	    	                	
	    	                	if(isSuccess){
	    	                		sqlSession.commit(true);
	    	                	}
	    	                	
	    	                	 //终审将同步白名单
			                    if("1".equals(isFinalAudit)){
			                    	//审核商户时需同步白名单
			                    	if(auditFlag==2){
			                    		Result resultReturn = new Support().synchMerchantBankWhiteList(auditId, clientId, "0","0");
				                    	if(!resultReturn.getResultCode().equals(ResultCode.success.getCode())){
				                    		message.append(resultReturn.getResultDesc());
				                    	}
			                    	}
			                    }
			                    
			                    // 商品录入审核落地日志添加
			                    if(auditFlag==1 && ProductAuditState.passAudit.getCode().equals(filterBankAudit.getAuditState())){//商品
			                    	productAuditLog(auditId);
			                    }
			                    
	                    	 } catch (Exception e) { 
	                    		 sqlSession.rollback(true);
	                    		 if(auditFlag==1){//商品
	                    			 message.append(setBankAudit.getAuditId()+":"+resultBankAudit.getName()+";");
	                    		 }else if(auditFlag==2){//商户
	                    			 message.append(setBankAudit.getAuditId()+":"+resultBankAudit.getMerchantName()+";");
	                    		 }else if(auditFlag==3){//秒杀商品
	                    			 message.append(setBankAudit.getAuditId());
	                    		 }
	                             LogCvt.error("批量审核"+auditName+"失败:"+setBankAudit.getAuditId()+"，原因:" + e.getMessage()); 
	                         }
	                    	 LogCvt.info("批量审核"+auditName+":"+auditId+"完成！next");
	                    	 
	                    }
	                
            	}
            	//编辑审核
            	else{
            		//商户-商品暂无需求
            		if(auditFlag==2){
                    	/**
                    	 * 1.取待审核机构判断是否双人审核
                    	 * 2.取待审核机构判断是否终审流程
                    	 * 
                    	 */
                    	
                    	//需要修改sql的对象
                    	AuditTask setAuditTask = null;
                    	AuditProcess setAuditProcess = null; 
                    	BankAudit setBankAudit = null;//cb_merchant及cb_product公共对象po
                    	AuditProcess addAuditProcess = null; //新增审核任务表对象
                    	
                    	//db中待审核机构
            			dbAuditOrgCode = resultAuditTask.getAuditOrgCode();
            			//取商户所属的待审核机构判断该机构是否需要双人审核
                    	org = commonLogic.getOrgByOrgCode(dbAuditOrgCode, clientId);
            			
                    	//审核未通过
	                    if(ProductAuditState.failAudit.getCode().equals(auditState)){
	                    	setAuditTask = new AuditTask() ;
	                    	setAuditProcess = new AuditProcess(); 
	                    	setBankAudit = new BankAudit();
	                    	
	                    	//公共修改内容
                    		setAuditProcess.setAuditTime(new Date());
	                    	setAuditProcess.setAuditComment(auditComment);
	                    	setAuditProcess.setAuditState(auditState);
	                    	
	                    	setAuditTask.setAuditState(auditState);
	                    	setAuditTask.setState(AuditTaskState.endFile.getType());
	                    	setAuditTask.setAuditTime(new Date());
	                    	
	                    	setBankAudit.setEditAuditState(auditState);
	                    	
	                    	//是否需要双人审核
	                    	if(!org.getNeedReview()){//不需要双人审核
	                    		/**
		                    	 * 1.修改cb_audit_task表中的audit_state=2、state=1、audit_time
		                    	 * 2.修改cb_audit_process表中的audit_time、audit_comment、audit_staff、audit_state=2
		                    	 * 3.修改cb_merchant表中的edit_audit_state=2
		                    	 */
		                    	setAuditProcess.setAuditStaff(auditStaff);
		                    	
	                    	}else{
	                    		//db中审核步骤(需要从cb_audit_process取审核步骤)
		            			dbAuditStage = merchantAuditCommonLogic.getAuditProcessByAuditId(resultAuditTask.getAuditId()).getAuditStage();
		           
	                    		/**
	                    		 * 判断是否初审复审
	                    		 */
	                    		if(Checker.isEmpty(dbAuditStage)){/**进行初审操作 **/
	                    			//说明：第一次编辑商户，并无进行审核动作，第一次审核为初审
	                    			
	                    			/**
        	                    	 * 1.修改cb_audit_task表中的audit_state=2、state=1、audit_time
        	                    	 * 2.修改cb_audit_process表中的audit_time、audit_comment、audit_staff、audit_stage=0、audit_state=2
        	                    	 * 3.修改cb_merchant表中的edit_audit_state=2
        	                    	 */
	                    			setAuditProcess.setAuditStaff(auditStaff);
	                    			setAuditProcess.setAuditStage(ProductAuditStage.firstAudit.getCode());
	                    			
        	                    	
	                    		}else if(ProductAuditStage.secondAudit.getCode().equals(dbAuditStage)){/**进行复审操作 **/
	                    			/**
        	                    	 * 1.修改cb_audit_task表中的audit_state=2、state=1、audit_time
        	                    	 * 2.修改cb_audit_process表中的audit_time、audit_comment、review_staff、audit_state=2
        	                    	 * 3.修改cb_merchant表中的edit_audit_state=2
        	                    	 */
	                    			setAuditProcess.setReviewStaff(auditStaff);
        	                    	
	                    		}
	                    	}
	                    }
	                    //审核通过
	                    else if(ProductAuditState.passAudit.getCode().equals(auditState)){
	            			//db中审核结束机构
	            			dbAuditEndOrgCode = resultAuditTask.getAuditEndOrgCode();
	                    	
	                    	//待审核机构
	                    	auditOrgCode = this.getAuditOrgCode(dbAuditOrgCode,dbAuditEndOrgCode,clientId);
	                    	
	                    	
	                    	setAuditProcess = new AuditProcess(); 
	                    	//公共修改内容
                    		setAuditProcess.setAuditTime(new Date());
	                    	setAuditProcess.setAuditComment(auditComment);
	                    	setAuditProcess.setAuditState(auditState);
	                    	
	                    	//是否需要双人审核
	                    	if(!org.getNeedReview()){//不需要双人审核
	                    		//公共修改内容
		                    	setAuditProcess.setAuditStaff(auditStaff);
		                    	setAuditTask = new AuditTask() ;
		                    	setAuditTask.setAuditOrgCode(auditOrgCode);//待审核机构
		                    	setBankAudit = new BankAudit();
		                    	
	                    		/**
    	                    	 * 判断是否终审
    	                    	 *  -->是
    	                    	 * 		1.修改cb_audit_task表中的audit_state=1、state=1、audit_time、audit_org_code
    	                    	 * 		2.修改cb_audit_process表中的audit_time、audit_comment、audit_staff、audit_state=1
    	                    	 * 		3.修改cb_merchant表中的edit_audit_state=1
    	                    	 * 	-->否
    	                    	 * 		1.修改cb_audit_process表中的audit_time、audit_comment、audit_staff、audit_state=1
    	                    	 * 		2.新增cb_audit_process下一个机构的待审核记录
    	                    	 * 		3.修改cb_merchant表中的audit_org_code 
    	                    	 * 		4.修改cb_audit_task表中的audit_org_code
    	                    	 * 	
    	                    	 */
    	                    	if("0".equals(auditOrgCode)){//终审
    	                    		
    	                    		setAuditTask.setAuditState(auditState);
        	                    	setAuditTask.setState(AuditTaskState.endFile.getType());
        	                    	setAuditTask.setAuditTime(new Date());
        	                    	
        	                    	setBankAudit.setEditAuditState(auditState);
        	                    	
        	                    	isFinalAudit="1";
        	                    	
    	                    	}else{
    	                    		
        	                    	addAuditProcess = new AuditProcess();
        	                    	addAuditProcess.setCreateTime(new Date());
        	                    	addAuditProcess.setClientId(clientId);
        	                    	addAuditProcess.setAuditId(resultAuditTask.getAuditId());
        	                    	addAuditProcess.setTaskId(SerialNumberUtil.getTaskIdSerialNumber());
        	                    	addAuditProcess.setOrgCode(auditOrgCode);
        	                    	addAuditProcess.setOrgName(commonLogic.queryByOrgCode(clientId, auditOrgCode).getOrgName());
        	                    	addAuditProcess.setAuditState(ProductAuditState.noAudit.getCode());
        	                    	
        	                    	setBankAudit.setAuditOrgCode(auditOrgCode);
        	                    	
    	                    	}
	                    	}else{
	                    		//db中审核步骤(需要从cb_audit_process取审核步骤)
		            			dbAuditStage = merchantAuditCommonLogic.getAuditProcessByAuditId(resultAuditTask.getAuditId()).getAuditStage();
		           
	                    		/**
	                    		 * 判断是否初审复审
	                    		 */
	                    		if(Checker.isEmpty(dbAuditStage)){/**进行初审操作 **/
	                    			/**
	                    			 * 1.修改cb_audit_process表中的audit_time、audit_comment、audit_staff、audit_stage=0、audit_state=1
	                    			 * 2.新增cb_audit_process当前待审核机构下一个用户的复审记录
	                    			 */
	                    			setAuditProcess.setAuditStaff(auditStaff);
	                    			setAuditProcess.setAuditStage(ProductAuditStage.firstAudit.getCode());
	                    			
	                    			addAuditProcess = new AuditProcess();
        	                    	addAuditProcess.setCreateTime(new Date());
        	                    	addAuditProcess.setClientId(clientId);
        	                    	addAuditProcess.setAuditId(resultAuditTask.getAuditId());
        	                    	addAuditProcess.setTaskId(SerialNumberUtil.getTaskIdSerialNumber());
        	                    	addAuditProcess.setOrgCode(dbAuditOrgCode);
        	                    	addAuditProcess.setOrgName(commonLogic.queryByOrgCode(clientId, dbAuditOrgCode).getOrgName());
        	                    	addAuditProcess.setAuditStaff(auditStaff);
        	                    	addAuditProcess.setAuditStage(ProductAuditStage.secondAudit.getCode());	
        	                    	addAuditProcess.setAuditState(ProductAuditState.noAudit.getCode());
        	                    	
	                    		}else if(ProductAuditStage.secondAudit.getCode().equals(dbAuditStage)){/**进行复审操作 **/
	                    			/**
        	                    	 * 判断是否终审
        	                    	 *  -->是
        	                    	 * 		1.修改cb_audit_task表中的audit_state=1、state=1、audit_time、audit_org_code
        	                    	 * 		2.修改cb_audit_process表中的audit_time、audit_comment、review_staff、audit_state=1
        	                    	 * 		3.修改cb_merchant表中的edit_audit_state=1
        	                    	 * 	-->否
        	                    	 * 		1.修改cb_audit_process表中的audit_time、audit_comment、review_staff、audit_state=1
        	                    	 * 		2.新增cb_audit_process下一个机构的待审核记录
        	                    	 * 		3.修改cb_merchant表中的audit_org_code 
        	                    	 * 		4.修改cb_audit_task表中的audit_org_code
        	                    	 * 	
        	                    	 */
	                    			//公共修改内容
	                    			setAuditProcess.setReviewStaff(auditStaff);
	                    			setAuditTask = new AuditTask() ;
	                    			setAuditTask.setAuditOrgCode(auditOrgCode);//待审核机构
	                    			setBankAudit = new BankAudit();
	                    			
        	                    	if("0".equals(auditOrgCode)){//终审
        		                    	
        	                    		setAuditTask.setAuditState(auditState);
            	                    	setAuditTask.setState(AuditTaskState.endFile.getType());
            	                    	setAuditTask.setAuditTime(new Date());
            	                    	
            	                    	setBankAudit.setEditAuditState(auditState);
            	                    	
            	                    	isFinalAudit="1";
            	                    	
        	                    	}else{
        	                    		setBankAudit.setAuditOrgCode(auditOrgCode);
        	                    		
        	                    		addAuditProcess = new AuditProcess();
            	                    	addAuditProcess.setCreateTime(new Date());
            	                    	addAuditProcess.setClientId(clientId);
            	                    	addAuditProcess.setAuditId(resultAuditTask.getAuditId());
            	                    	addAuditProcess.setTaskId(SerialNumberUtil.getTaskIdSerialNumber());
            	                    	addAuditProcess.setOrgCode(auditOrgCode);
            	                    	addAuditProcess.setOrgName(commonLogic.queryByOrgCode(clientId, auditOrgCode).getOrgName());
            	                    	addAuditProcess.setAuditState(ProductAuditState.noAudit.getCode());
        	                    	}
	                    		}
	                    	}
	                    }
                    	/********************审核通过/不通过逻辑End*************************************/
	                    try{
	                    	AuditTaskCommonMapper auditTaskCommonMapper = sqlSession.getMapper(AuditTaskCommonMapper.class);
	                    	AuditProcessCommonMapper auditProcessCommonMapper = sqlSession.getMapper(AuditProcessCommonMapper.class);
	                    	//修改cb_audit_task
		                    if(Checker.isNotEmpty(setAuditTask)){
		                    	setAuditTask.setThridId(auditId);//修改sql条件
		                    	isSuccess = auditTaskCommonMapper.updateAuditTask(setAuditTask);
		                    }
		                    //修改cb_audit_process
		                    if(Checker.isNotEmpty(setAuditProcess)){
		                    	setAuditProcess.setAuditId(resultAuditTask.getAuditId());//修改sql条件
		                    	isSuccess = auditProcessCommonMapper.updateAuditProcess(setAuditProcess);
		                    }
		                    //新增cb_audit_process
		                    if(Checker.isNotEmpty(addAuditProcess)){
		                    	Long count = auditProcessCommonMapper.addAuditProcess(addAuditProcess);
		                    	if(count>0){
		                    		isSuccess = true;
		                    	}
		                    }
		                    //修改商户表
		                    if(Checker.isNotEmpty(setBankAudit)){
		                    	setBankAudit.setClientId(filterBankAudit.getClientId());
		                    	setBankAudit.setAuditId(auditId);
		                    	
		                    	isSuccess=merchantMapper.auditMerchant(setBankAudit);
		                    }
		                    
		                    if(isSuccess){
    	                		sqlSession.commit(true);
    	                	}
		                    
		                    //终审将cb_merchant_temp数据同步到cb_merchant主表，调用商户模块接口
		                    if("1".equals(isFinalAudit)){
		                    	Result resultReturn = new Support().synchMerchantEdit(resultAuditTask.getAuditId());
		                    	if(!resultReturn.getResultCode().equals(ResultCode.success.getCode())){
		                    		message.append(resultReturn.getResultDesc());
		                    	}
		                    }
		                    
		                    
		                    
	                    }catch(Exception e){
	                    	sqlSession.rollback(true);
		                    if(auditFlag==2){//商户
	               			 	message.append(setBankAudit.getAuditId()+":"+resultBankAudit.getMerchantName()+";");
		               		}
		                    LogCvt.error("编辑审核"+auditName+"失败:"+setBankAudit.getAuditId()+"，原因:" + e.getMessage()); 
	                    }
	                    LogCvt.info("编辑审核"+auditName+":"+auditId+"完成！next");
            		}
            	}
            	/********************编辑审核End*************************************/
        	}
        	
        	if(message.length()==0){
                message.append("OK");
            }
        	
        	//设置值进行返回
        	result[0]=message.toString();
        	
        }catch (FroadServerException e) { 
        	result[0]=e.getMessage();
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			throw e;
		} catch (Exception e) { 
			result[0]=e.getMessage();
        	if(null != sqlSession)  
				sqlSession.rollback(true);  
            throw e;
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        
        result[1]=isFinalAudit;
        return result;
	
	}

	
	/**
     * 返回待审核数量
     * @param  clientId 客户端id
     * @param   orgCode 机构编号
     * @return getPreAuditNumRes 待审核数量对象
     */
	@Override
	public PreAuditNumRes getPreAuditNumRes(String clientId, String orgCode) {
		

		SqlSession sqlSession = null;
		ProductMapper productMapper =null;
		ProductSeckillMapper productSeckillMapper = null;
        MerchantMapper merchantMapper=null;
        PreAuditNumRes preAuditNum = null;
		
        int preauditMerchant=0;//待审核商户
		int preauditPresell=0;//待审核预售商品
		int preauditGroup=0;//待审核团购商品
		int preauditMingyou=0;//待审核名优特惠商品
		int preauditDuihuan=0;//待审核线下积分兑换
		int preauditSeckill=0;//待审核秒杀商品数量
		

		try{
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			merchantMapper = sqlSession.getMapper(MerchantMapper.class);
			productMapper = sqlSession.getMapper(ProductMapper.class);
			productSeckillMapper = sqlSession.getMapper(ProductSeckillMapper.class);
				
				
			//查询待审核商户数量
			preauditMerchant = merchantMapper.findCountByMerchant(clientId, orgCode);
			
			//查询待审核商品数量
			List<Map<String, Object>> productCountList = productMapper.findCountByProduct(clientId, orgCode);
			Map<String, Long> typeCounts = new HashMap<String, Long>(); 
			String keys = "";
			Long values = 0l;
			for (Map<String, Object> map : productCountList) {
				keys = "";
				values = 0l;
				for (Map.Entry<String, Object> kv : map.entrySet()) {
					String key = kv.getKey();
					Object value = kv.getValue();

					if (key.equals("type")) {
						keys = (String) value;
					}
					if (key.equals("auditCount")) {
						values = (Long) value;
					}
				}
				typeCounts.put(keys, values);

			}
			//将typeCounts的值set到字段中
			for(Map.Entry<String, Long> kv:typeCounts.entrySet()){
				String key = kv.getKey();
				Object value = kv.getValue();
				if(ProductType.presell.getCode().equals(key)) {// 预售
					preauditPresell = Integer.parseInt(String.valueOf(value));
				}
				if(ProductType.group.getCode().equals(key)) {// 团购
					preauditGroup = Integer.parseInt(String.valueOf(value));
				}
				if(ProductType.special.getCode().equals(key)) {// 名优特惠
					preauditMingyou = Integer.parseInt(String.valueOf(value));
				}
				if(ProductType.dotGift.getCode().equals(key)) {// 线下积分兑换
					preauditDuihuan = Integer.parseInt(String.valueOf(value));
				}
				
			}
			
			// 秒杀商品待审核数量
			preauditSeckill = productSeckillMapper.findCountByProductSeckill(clientId, orgCode);
			
			preAuditNum=new PreAuditNumRes();
			preAuditNum.setPreauditMerchant(preauditMerchant);
			preAuditNum.setPreauditPresell(preauditPresell);
			preAuditNum.setPreauditGroup(preauditGroup);
			preAuditNum.setPreauditMingYou(preauditMingyou);
			preAuditNum.setPreauditDuihuan(preauditDuihuan);	
			preAuditNum.setPreauditSeckill(preauditSeckill);
			


		}catch (Exception e) { 
			LogCvt.error("获取待审核数量对象失败，原因:" + e.getMessage(), e);
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return preAuditNum;
		
	}



	/**
	 * 获取待审核机构
	 * @param auditOrgCode 审核对象表中存储的待审核机构
	 * @param auditOrg 最终审核机构
	 * @return 待审核机构
	 */
	public String getAuditOrgCode(String auditOrgCode,String auditOrg,String clientId){
		
		/**
		 * 若数据库中存储的待审核机构与最终审核机构相同，则待审核机构编号完成置0
		 */
		if(auditOrgCode.equals(auditOrg)){
			return "0";
		}
		
		//最终审核机构可为空，若为空，则为一级审核，起始审核完之后，就置为审核通过，不需后续审核
		if(Checker.isEmpty(auditOrg)){
			return "0";
		}
		
		/**
		 * 取待审核机构
		 * 	-->A->B->C->D
		 */
		//根据数据库中待审核机构取出上一级机构对象
		String resultOrgCode=auditOrg;//待审核机构编号<一级默认待审核机构>
		Org orgAuditOrgCode= new CommonLogicImpl().getOrgByOrgCode(auditOrgCode,clientId);//待审核机构属机构对象
		String orgTop=OrgSuperUtil.getOrgSuper(orgAuditOrgCode);
		
		if(Checker.isNotEmpty(orgTop)){
    		//上一级机构与最终审核机构相同的话，则待审核机构就是最终审核机构，否则即上一级机构为最终审核机构
    		if(orgTop.equals(auditOrg)){
    			resultOrgCode=auditOrg;
    		}else{
    			resultOrgCode=orgTop;
    		}
    		
		}
		
		
		return resultOrgCode;
	}

	
	/**
	 *  商品录入审核落地日志文件
	 */
	private void productAuditLog(String productId) {
		try {
			CommonLogic commonLogic = new CommonLogicImpl();
			Product productViewInfo = commonLogic.getProductById(productId);
			String clientId = productViewInfo.getClientId();
			String merchantId = productViewInfo.getMerchantId();
			Map<String,String> dataRedis = commonLogic.getMerchantRedis(clientId, merchantId);
			
			ProductLog log = new ProductLog();
			log.setAction("PRODUCTAUDIT");
			log.setTime(new Date().getTime());
			log.setClient_id(productViewInfo.getClientId());
			HeadKey key = new HeadKey();
			key.setProduct_id(productViewInfo.getProductId());
			log.setKey(key);
			ProductDetailLog data = new ProductDetailLog();
			data.setProduct_id(productViewInfo.getProductId());
			data.setProduct_name(productViewInfo.getName());
			data.setProduct_type(productViewInfo.getType());
			data.setMerchant_id(productViewInfo.getMerchantId());
			if(productViewInfo.getCategoryId() != null){
				data.setCategory_id(productViewInfo.getCategoryId());
			}
			if(productViewInfo.getCategoryTreePath() != null){
				data.setCategory_tree_path(productViewInfo.getCategoryTreePath());
			}
			
			data.setOrg_code(productViewInfo.getOrgCode());
			data.setOrg_code(productViewInfo.getOrgCode());
			data.setAudit_state(productViewInfo.getAuditState());
			if(productViewInfo.getStartTime()!=null){
				data.setStart_time(productViewInfo.getStartTime().getTime());
			}
			if(productViewInfo.getEndTime()!=null){
				data.setEnd_time(productViewInfo.getEndTime().getTime());
			}
			if(productViewInfo.getExpireStartTime()!=null){
				data.setExpire_start_time(productViewInfo.getExpireStartTime().getTime());
			}
			if(productViewInfo.getExpireEndTime()!=null){
				data.setExpire_end_time(productViewInfo.getExpireEndTime().getTime());
			}
			data.setMarket_price(productViewInfo.getMarketPrice());
			data.setPrice(productViewInfo.getPrice());
			data.setCreate_time(productViewInfo.getCreateTime().getTime());
			data.setIs_marketable(productViewInfo.getIsMarketable());
			if(productViewInfo.getDownTime()==null){
				data.setIs_one_up("1");
			}
			if(productViewInfo.getDownTime()==null){
				data.setIs_one_down("1");
			}
			data.setMerchant_name(dataRedis.get("merchant_name"));
			data.setMerchant_category_id(dataRedis.get("category_id"));
			data.setMerchant_category_name(dataRedis.get("category_name"));
			data.setAudit_time(System.currentTimeMillis());
			log.setData(data);
			LogCvt.info(JSON.toJSONString(log)); 
			BankLogs.updateProduct(log);
		} catch (Exception e) {
			LogCvt.error("商品录入审核输出日志异常...", e);
		}
		
	}
	
	
	/**
	 * 设置缓存数量
	 * @param type 类型:1团购、2预售、3名优特惠、5网点礼品  6-商户（4在线积分兑换 -boss添加不需审核）
	 * @param orgCode 机构编号
	 * @param increment 增量1或-1
	 * @return
	 */
//	private boolean setRedisCount(String type,String clientId,String orgCode,Long increment) {
//	
//		boolean result = false;
//		
//		try{
//			if(type.equals(ProductType.group.getCode())){//团购
//				PendingAuditRedis.incr_cbbank_preaudit_group_count_org_code(clientId,orgCode, increment);
//			}else if(type.equals(ProductType.presell.getCode())){//预售
//				PendingAuditRedis.incr_cbbank_preaudit_presell_count_org_code(clientId,orgCode, increment);
//			}else if(type.equals(ProductType.special.getCode())){//名优特惠
//				PendingAuditRedis.incr_cbbank_preaudit_mingyou_count_org_code(clientId,orgCode, increment);
//			}else if(type.equals(ProductType.dotGift.getCode())){//网点礼品(线下积分兑换)
//				PendingAuditRedis.incr_cbbank_preaudit_duihuan_count_org_code(clientId,orgCode, increment);
//			}else if(type.equals("6")){//商户
//				PendingAuditRedis.incr_cbbank_preaudit_merchant_count_org_code(clientId,orgCode, increment);
//			}
//			result=true;
//		}catch (Exception e) { 
//			LogCvt.error("设置缓存数量失败，原因:" + e.getMessage(), e);
//		} 
//		
//		if(result){
//			LogCvt.info("type:"+type+"clientId"+clientId+"orgCode:"+orgCode+"increment:"+increment+"设置缓存待审核数量完成！");
//		}else{
//			LogCvt.error("type:"+type+"clientId"+clientId+"orgCode:"+orgCode+"increment:"+increment+"设置缓存待审核数量异常！");
//		}
//		
//		return result;
//	}
		
	
	/**
	 * 获取mysql中待审核数量
	 * @param type 类型:1团购、2预售、3名优特惠、5网点礼品  6-商户（4在线积分兑换 -boss添加不需审核）
	 * @param orgCode 机构编号
	 * @param clientId 客户端Id
	 * @return
	 */
//	public String getAuditCount(String type,String clientId,String orgCode){
//
//		
//		String count = "";
//		SqlSession sqlSession = null;
//		ProductMapper productMapper =null;
//        MerchantMapper merchantMapper=null;
//		
//		try{
//			if(type.equals(ProductType.group.getCode())){//团购
//				//若有负数重新查询设置
//				if(PendingAuditRedis.get_cbbank_preaudit_group_count_org_code(clientId, orgCode)<0){
//					//从mysql中查询重新设置缓存
//					sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//					productMapper = sqlSession.getMapper(ProductMapper.class);
//					
//					count = String.valueOf(productMapper.findCountByProduct(clientId, orgCode,type));
//					
//					//重新设置Redis缓存
//					PendingAuditRedis.set_cbbank_preaudit_group_count_org_code(clientId, orgCode, count);
//					
//				}else{
//					//从Redis取
//					count=String.valueOf(PendingAuditRedis.get_cbbank_preaudit_group_count_org_code(clientId, orgCode));
//				}
//			}else if(type.equals(ProductType.presell.getCode())){//预售
//				//若有负数重新查询设置
//				if(PendingAuditRedis.get_cbbank_preaudit_presell_count_org_code(clientId, orgCode)<0){
//					//从mysql中查询重新设置缓存
//					sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//					productMapper = sqlSession.getMapper(ProductMapper.class);
//					
//					count = String.valueOf(productMapper.findCountByProduct(clientId, orgCode,type));
//					
//					//重新设置Redis缓存
//					PendingAuditRedis.set_cbbank_preaudit_presell_count_org_code(clientId, orgCode, count);
//					
//				}else{
//					count=String.valueOf(PendingAuditRedis.get_cbbank_preaudit_presell_count_org_code(clientId, orgCode));
//				}
//				
//				
//			}else if(type.equals(ProductType.special.getCode())){//名优特惠
//				
//				//若有负数重新查询设置
//				if(PendingAuditRedis.get_cbbank_preaudit_mingyou_count_org_code(clientId, orgCode)<0){
//					//从mysql中查询重新设置缓存
//					sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//					productMapper = sqlSession.getMapper(ProductMapper.class);
//					
//					count = String.valueOf(productMapper.findCountByProduct(clientId, orgCode,type));
//					
//					//重新设置Redis缓存
//					PendingAuditRedis.set_cbbank_preaudit_mingyou_count_org_code(clientId, orgCode, count);
//					
//				}else{
//					count=String.valueOf(PendingAuditRedis.get_cbbank_preaudit_mingyou_count_org_code(clientId, orgCode));
//				}
//				
//			}else if(type.equals(ProductType.dotGift.getCode())){//网点礼品(线下积分兑换)
//				
//				//若有负数重新查询设置
//				if(PendingAuditRedis.get_cbbank_preaudit_duihuan_count_org_code(clientId, orgCode)<0){
//					//从mysql中查询重新设置缓存
//					sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//					productMapper = sqlSession.getMapper(ProductMapper.class);
//					
//					count = String.valueOf(productMapper.findCountByProduct(clientId, orgCode,type));
//					
//					//重新设置Redis缓存
//					PendingAuditRedis.set_cbbank_preaudit_duihuan_count_org_code(clientId, orgCode, count);
//					
//				}else{
//					count=String.valueOf(PendingAuditRedis.get_cbbank_preaudit_duihuan_count_org_code(clientId, orgCode));
//				}
//				
//				
//			}else if(type.equals("6")){//商户
//				
//				//若有负数重新查询设置
//				if(PendingAuditRedis.get_cbbank_preaudit_merchant_count_org_code(clientId, orgCode)<0){
//					//从mysql中查询重新设置缓存
//					sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
//					merchantMapper = sqlSession.getMapper(MerchantMapper.class);
//					
//					count = String.valueOf(merchantMapper.findCountByMerchant(clientId, orgCode));
//					
//					//重新设置Redis缓存
//					PendingAuditRedis.set_cbbank_preaudit_merchant_count_org_code(clientId, orgCode, count);
//					
//				}else{
//					count=String.valueOf(PendingAuditRedis.get_cbbank_preaudit_merchant_count_org_code(clientId, orgCode));
//				}
//				
//			}
//		}catch (Exception e) { 
//			LogCvt.error("获取缓存数量失败，原因:" + e.getMessage(), e);
//		} finally { 
//			if(null != sqlSession)  
//				sqlSession.close();  
//		} 
//		
//		return count;
//	}
}
