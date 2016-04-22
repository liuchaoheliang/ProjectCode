package com.froad.timer.task;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.SmsLogMongoService;
import com.froad.db.mongo.impl.SmsLogMongoServiceImpl;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.SmsLogMapper;
import com.froad.logback.LogCvt;
import com.froad.po.SmsLog;
import com.froad.po.mongo.SmsLogMongo;

/**
 * 
 * 移除过期 - 定时任务
 * 
 * 
 * @author lf 2015.03.20
 * @modify lf 2015.03.21
 * 
 * */
public class TaskRemoveExpired implements Runnable {

	private SmsLogMongoService smsLogMongoService = new SmsLogMongoServiceImpl();
	
	@Override
	public void run() {
		LogCvt.debug("定时任务: 移除过期短信日志------开始------");
		SqlSession sqlSession = null;
		
		try{
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			
			// 查DB（短信日志）的过期时间
			SmsLogMapper smsLogMapper = sqlSession.getMapper(SmsLogMapper.class);
			List<SmsLog> smsLogList = smsLogMapper.selectByExpired();
			if(CollectionUtils.isEmpty(smsLogList)){
				LogCvt.debug("定时任务: 移除过期短信日志------完成(无过期的短信日志)------");
				return;
			}
			
			LogCvt.debug("定时任务: 移除过期短信日志------条数 "+smsLogList.size()+"------");
			
			// 移动到MongoDB
			// 删除过期信息
			for( SmsLog smsLog : smsLogList ){
				LogCvt.debug("短信日志 [id="+smsLog.getId()+"]移除到MongoDB");
				try{
					smsLogMapper.deleteById(smsLog.getId());
					sqlSession.commit(true);
					SmsLogMongo add = getSmsLogMongo(smsLog);
					boolean result = smsLogMongoService.addSmsLog(add);
					LogCvt.debug("短信日志[id="+smsLog.getId()+"]移除"+(result?"成功":"失败"));
					
				}catch(Exception e){
					sqlSession.rollback(true);
					LogCvt.error("短信日志[id="+smsLog.getId()+"]移除异常");
					LogCvt.error(e.getMessage(), e);
					continue;
				}
			}
			
		}catch(Exception e){
			LogCvt.error("定时任务: 移除过期短信日志------系统结束------");
			LogCvt.error(e.getMessage(), e);
		}finally {
			if( sqlSession != null ) sqlSession.close();
			LogCvt.debug("定时任务: 移除过期短信日志------结束------");
		}
	}
	
	/**
	 * 
	 * 转换 SmsLog 对象得到 DBObject
	 * 
	 * @author lf 2015.03.22
	 * @modify lf 2015.03.22
	 * 
	 * */
	private SmsLogMongo getSmsLogMongo(SmsLog smsLog){
		SmsLogMongo sms = new SmsLogMongo();
		sms.setId(smsLog.getId());
		sms.setClientId(smsLog.getClientId());
		sms.setCreateTime(smsLog.getCreateTime().getTime());
		sms.setExpireTime(smsLog.getExpireTime().getTime());
		sms.setContent(smsLog.getContent());
		sms.setMobile(smsLog.getMobile());
		sms.setSendUser(smsLog.getSendUser());
		sms.setSendIp(smsLog.getSendIp());
		sms.setIsUsed(smsLog.getIsUsed());
		sms.setIsSuccess(smsLog.getIsSuccess());
		sms.setCode(smsLog.getCode());
		sms.setToken(smsLog.getToken());
		sms.setUrl(smsLog.getUrl());
		return sms;
	}

}
