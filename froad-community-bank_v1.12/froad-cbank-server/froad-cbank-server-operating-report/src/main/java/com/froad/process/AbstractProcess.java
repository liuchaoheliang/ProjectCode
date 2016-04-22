package com.froad.process;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.redis.impl.RedisManager;
import com.froad.db.user.mysql.UserMyBatisManager;
import com.froad.logback.LogCvt;

public abstract class AbstractProcess implements Process {
	
	/**
	 *  报表名称
	 */
	protected String name;
	/**
	 * 用户mysql数据源连接
	 */
	protected SqlSession userSqlSession = null;
	
	/**
	 * 重构mysql数据源连接
	 */
	protected SqlSession sqlSession = null;
	
	/**
	 *  mongo操作
	 */
	protected MongoManager mongo = new MongoManager();
	/**
	 * Redis操作
	 */
	protected RedisManager redis = new RedisManager();

	
	public AbstractProcess(String name){
		this.name = name;
	}
	
	/**
	 *  线程执行主体流程体系聚合
	  * @Title: run
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param 
	  * @throws
	  * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		long begtime = System.currentTimeMillis();
		try {
			begin();
			before();
			process();
		} catch (Exception e) {
			exception(e);
		} finally{
			end();
			// 进行下一个任务
			LogCvt.info("报表["+name+"]处理结束,耗时:"+(System.currentTimeMillis()-begtime)+"毫秒");
		}
	}
	
	/**
	 *  初始化相关信息
	  * @Title: begin
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param 
	  * @throws
	  * @see com.froad.process.Process#begin()
	 */
	@Override
	public void begin() {
		try {
			/**
			 *  初始化数据库连接
			 */
			// 重构
			sqlSession   = MyBatisManager.getSqlSessionFactory().openSession();
			LogCvt.info("重构加载完成");
			// user
			userSqlSession = UserMyBatisManager.getSqlSessionFactory().openSession();
			LogCvt.info("user加载完成");
		} catch (Exception e) {
			LogCvt.error(e.getMessage(),e);
		}
	
		
	}
	
	/**
	 *  处理完成后续事情
	  * @Title: end
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param 
	  * @throws
	  * @see com.froad.process.Process#end()
	 */
	@Override
	public void end() {
		try {
			if(userSqlSession != null){
				userSqlSession.close();
			}
		} catch (Exception e) {
			LogCvt.error("关闭用户mysql数据连接异常...",e);
		}
		
		try {
			if(sqlSession != null){
				sqlSession.commit(true);
				sqlSession.close();
			}
		} catch (Exception e) {
			LogCvt.error("关闭重构mysql数据连接异常...",e);
		}
		
		try {
			if(userSqlSession != null){
				userSqlSession.close();
			}
		} catch (Exception e) {
			LogCvt.error("关闭重构bps mysql数据连接异常...",e);
		}
		
	}

	/**
	 * 异常处理
	  * @Title: exception
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param @param e
	  * @throws
	  * @see com.froad.process.Process#exception(java.lang.Exception)
	 */
	@Override
	public void exception(Exception e) {
		// 异常回滚信息
		LogCvt.error("程序处理异常,mysql数据进行异常回滚...", e);
		LogCvt.error("报表["+name+"]处理异常");
		sqlSession.rollback(true);
	}

	
}

