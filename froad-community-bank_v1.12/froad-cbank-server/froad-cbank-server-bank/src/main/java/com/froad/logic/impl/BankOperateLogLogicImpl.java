package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BankOperateLogMapper;
import com.froad.enums.OrgLevelEnum;
import com.froad.logback.LogCvt;
import com.froad.logic.BankOperateLogLogic;
import com.froad.po.BankOperateLog;
import com.froad.po.Org;
import com.froad.util.Checker;

/**
 * 
 * <p>@Title: BankOperateLogLogicImpl.java</p>
 * <p>Description: 描述 </p> 银行用户操作日志Logic实现类<>
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月2日
 */
public class BankOperateLogLogicImpl implements BankOperateLogLogic {


    /**
     * 分页查询 BankOperateLog
     * @param page
     * @param BankOperateLog
     * @return List<BankOperateLog>    结果集合 
     */
	@Override
	public Page<BankOperateLog> findBankOperateLogByPage(Page<BankOperateLog> page, BankOperateLog bankOperatorLog) {
		
		List<BankOperateLog> result = new ArrayList<BankOperateLog>(); 
		SqlSession sqlSession = null;
		BankOperateLogMapper bankOperateLogMapper = null;
		try { 
			
			//判断orgCode级别，若为一级，减少多条件查询
			if(Checker.isNotEmpty(bankOperatorLog)){
				if(Checker.isNotEmpty(bankOperatorLog.getClientId()) && Checker.isNotEmpty(bankOperatorLog.getOrgCode())){
					String[] orgArray = bankOperatorLog.getOrgCode().split(",");
					List<String> orgCodes = new ArrayList<String>();
					if (orgArray.length == 1) {
						Org org = new CommonLogicImpl().getOrgByOrgCode(bankOperatorLog.getOrgCode(),bankOperatorLog.getClientId());
						
						if(Checker.isEmpty(org)){
							return new Page<BankOperateLog>();
						}
						
						//若一级则清空orgCode减少连表查询
						if(OrgLevelEnum.orgLevel_one.getLevel().equals(org.getOrgLevel())){
							bankOperatorLog.setOrgCodes(null);
						}else{
							orgCodes.add(bankOperatorLog.getOrgCode());
							bankOperatorLog.setOrgCodes(orgCodes);
						}
					}else{
						for (String orgcode : orgArray) {
							orgCodes.add(orgcode);
						}
						orgCodes = new CommonLogicImpl().queryLastOrgCode(bankOperatorLog.getClientId(), orgCodes);
						bankOperatorLog.setOrgCodes(orgCodes);
					}
				}
			}
			
			
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperateLogMapper = sqlSession.getMapper(BankOperateLogMapper.class);
			
			
			result = bankOperateLogMapper.findByPage(page, bankOperatorLog); 
			page.setResultsContent(result);
			
			
		} catch (Exception e) { 
			LogCvt.error("分页查询BankOperateLog失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}



	
		
		
		
		
		
}