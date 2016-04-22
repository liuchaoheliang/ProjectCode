package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ActiveSerchMerchantMapper;
import com.froad.handler.ActiveSearchMerchantHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchMerchantInfo;

public class ActiveSearchMerchantHandlerImpl implements ActiveSearchMerchantHandler{

	 /**
	  * @Title: findActiveRuleByMerchantIds
	  * @Description: 查询商户活动信息数据逻辑实现
	  * @author: shenshaocheng 2015年11月6日
	  * @modify: shenshaocheng 2015年11月6日
	  * @param activeSearchMerchantInfo
	  * @return
	  * @see com.froad.handler.ActiveSearchMerchantHandler#findActiveRuleByMerchantIds(
	  * com.froad.po.ActiveSearchMerchantInfo)
	  */	
	@Override
	public List<ActiveResultInfo> findActiveRuleByMerchantIds(
			ActiveSearchMerchantInfo activeSearchMerchantInfo) {
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		List<ActiveResultInfo> activeResultInfoList = new ArrayList<ActiveResultInfo>();
		try {
			ActiveSerchMerchantMapper mapper = sqlSession.getMapper(ActiveSerchMerchantMapper.class); 		
			activeResultInfoList = 
					mapper.findActiveMerchantInfo(activeSearchMerchantInfo);
		} catch (Exception e) {
			LogCvt.error("获取商户活动信息失败：" + e.getMessage(), e);
		} finally {
			if(null != sqlSession) {  
				sqlSession.close(); 
			} 
		}	
		
		return activeResultInfoList;
	}

}
