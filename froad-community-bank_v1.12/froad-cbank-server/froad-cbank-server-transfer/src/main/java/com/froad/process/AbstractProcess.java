package com.froad.process;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mysql.AhMyBatisManager;
import com.froad.db.bps.mysql.BpsMyBatisManager;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.redis.impl.RedisManager;
import com.froad.pool.ServicePool;

public abstract class AbstractProcess implements Process {
	private static Logger logger = LoggerFactory.getLogger(AbstractProcess.class);
	/**
	 *  模块名称
	 */
	protected String name;
	/**
	 * 服务配置
	 */
	protected ProcessServiceConfig config;
	
	/**
	 * 安徽mysql数据源连接
	 */
	protected SqlSession ahSqlSession = null;
	
	/**
	 * 安徽bps mysql数据源连接
	 */
	protected SqlSession bpsSqlSession = null;
	
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

	
	public AbstractProcess(String name,ProcessServiceConfig config){
		this.name = name;
		this.config = config;
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
		// TODO Auto-generated method stub
		boolean flag = true;
		long begtime = System.currentTimeMillis();
		try {
			begin();
			process();
		} catch (Exception e) {
			flag = false;
			exception(e);
		} finally{
			end();
			// 进行下一个任务
			if(flag){
				next();
			}
			logger.info("数据转移,模块名["+name+"]结束,耗时:"+(System.currentTimeMillis()-begtime)+"毫秒");
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
		// TODO Auto-generated method stub
		logger.info("转移模块["+name+"]开始...");
		try {
			/**
			 *  初始化数据库连接
			 */
			// 安徽
			ahSqlSession = AhMyBatisManager.getSqlSessionFactory().openSession();
			logger.info("安徽加载完成");
			// 重构
			sqlSession   = MyBatisManager.getSqlSessionFactory().openSession();
			logger.info("重构加载完成");
			// bps
			bpsSqlSession = BpsMyBatisManager.getSqlSessionFactory().openSession();
			logger.info("BpS加载完成");
		} catch (Exception e) {
			e.printStackTrace();
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
		// TODO Auto-generated method stub
		try {
			if(ahSqlSession != null){
				ahSqlSession.close();
			}
		} catch (Exception e) {
			logger.error("关闭安徽mysql数据连接异常...",e);
		}
		
		try {
			if(sqlSession != null){
				sqlSession.commit(true);
				sqlSession.close();
			}
		} catch (Exception e) {
			logger.error("关闭重构mysql数据连接异常...",e);
		}
		
		try {
			if(bpsSqlSession != null){
				bpsSqlSession.close();
			}
		} catch (Exception e) {
			logger.error("关闭重构bps mysql数据连接异常...",e);
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
		// TODO Auto-generated method stub
	public void exception(Exception e) {
		// 异常回滚信息
		logger.error("程序处理异常,mysql数据进行异常回滚...", e);
		logger.error("转移模块["+name+"]异常,无法执行下一模块["+config.getServiceNext()+"]");
		sqlSession.rollback(true);
	}

	/**
	 *  下一步
	  * @Title: next
	  * @Description: TODO
	  * @author: share 2015年4月29日
	  * @modify: share 2015年4月29日
	  * @param @param config
	  * @throws
	  * @see com.froad.process.Process#next(com.froad.config.Config)
	 */
	@Override
	public void next() {
		// TODO Auto-generated method stub
		ServicePool.nextService(config);
	}
	
	
}

