package com.froad.logic.impl;

import java.util.Date;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.TerminalStartMapper;
import com.froad.db.redis.SupportsRedis;
import com.froad.logback.LogCvt;
import com.froad.logic.TerminalStartLogic;
import com.froad.po.TerminalStart;

/**
 * 
 * <p>@Title: TerminalStartLogicImpl.java</p>
 * <p>Description: 描述 </p> 客户端启动页Logic实现类
 * @author f-road-ll 
 * @version 1.2
 * @created 2015年9月16日
 */
public class TerminalStartLogicImpl implements TerminalStartLogic {

	
	/**
     * 客户端启动页查询
     * @param clientId 客户端id
     * @param appType 客户端类型  1-个人 2-商户
     * @param terminalType 终端类型 100-pc 200-andriod 300-ios
     * @return 启动页详情 TerminalStart
     */
	@Override
	public TerminalStart getTerminalStart(String clientId, String appType,String terminalType) {
		TerminalStart terminalStart = null;
		SqlSession sqlSession = null;
		TerminalStartMapper terminalStartMapper = null;
		try { 
			
			Date now = new Date();
			
			Map<String,String> operatorRedis = SupportsRedis.getAll_cbbank_terminal_start_client_id_app_type_terminal_type(clientId, appType, terminalType);
			if( operatorRedis != null ){
				Date beginTime = new Date(Long.parseLong(operatorRedis.get("begin_time")));
				Date endTime = new Date(Long.parseLong(operatorRedis.get("end_time")));
				boolean isEnabled = "1".equals(operatorRedis.get("is_enabled"));
				long nowLong = now.getTime();
				if( beginTime.getTime() <= nowLong && endTime.getTime() >= nowLong && isEnabled ){
					LogCvt.debug("从缓存中找到记录 cbbank:terminal_start:"+clientId+":"+appType+":"+terminalType);
					terminalStart = new TerminalStart();
					terminalStart.setId(Long.parseLong(operatorRedis.get("id")));
					terminalStart.setCreateTime(new Date(Long.parseLong(operatorRedis.get("create_time"))));
					terminalStart.setClientId(clientId);
					terminalStart.setAppType(appType);
					terminalStart.setTerminalType(terminalType);
					terminalStart.setImageId(operatorRedis.get("image_id"));
					terminalStart.setImagePath(operatorRedis.get("image_path"));
					terminalStart.setBeginTime(beginTime);
					terminalStart.setEndTime(endTime);
					terminalStart.setIsEnabled(isEnabled);
				}
			}
			
			if( terminalStart == null ){
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				terminalStartMapper = sqlSession.getMapper(TerminalStartMapper.class);

				TerminalStart find = new TerminalStart();
				find.setClientId(clientId);
				find.setAppType(appType);
				find.setTerminalType(terminalType);
				find.setBeginTime(now);//查询当前时间下符合有效的启动页数据
				find.setIsEnabled(true);
				terminalStart = terminalStartMapper.findTerminalStart(find);
				if( terminalStart != null && terminalStart.getId() != null && terminalStart.getId() > 0 ){
					SupportsRedis.set_cbbank_terminal_start_client_id_app_type_terminal_type(terminalStart);
				}
			}
			
		} catch (Exception e) { 
			LogCvt.error("客户端启动页查询TerminalStart失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return terminalStart; 
	}


    


}