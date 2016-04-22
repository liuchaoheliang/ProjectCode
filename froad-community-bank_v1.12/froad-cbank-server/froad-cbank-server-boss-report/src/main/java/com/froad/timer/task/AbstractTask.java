package com.froad.timer.task;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.ReportMyBatisManager;
import com.froad.enums.MasterNode;
import com.froad.logback.LogCvt;
import com.froad.po.BatchNode;
import com.froad.singleton.BatchNodeSingleton;
import com.froad.timer.bean.TaskBean;
import com.froad.timer.manager.TaskManager;
import com.froad.util.IPUtil;

public abstract class AbstractTask implements ITask {
	
	protected SqlSession sqlSession;
	
	protected SqlSession rpSqlSession;
	
	protected String name;	
	
	protected TaskBean task;
	
	protected List<BatchNode> batchNodeList;
	
	protected boolean isMasterNode = false;
	
	public AbstractTask(String name, TaskBean task){
		this.name = name;
		this.task = task;
	}
	
	
	@Override
	public void begin() {
		LogCvt.info("定时任务["+name+"]开始");
		
		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
		
		rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
		
		batchNodeList = retrieveBatchNodes();
		isMasterNode = isMasterNode();
	}
	
	
	@Override
	public void exception(Exception e) {
		rpSqlSession.rollback(true);
		LogCvt.error("程序处理异常,mysql数据进行异常回滚...", e);
	}
	
	@Override
	public void end() {
		try {
			if(sqlSession != null){
				//sqlSession.commit(true);
				//sqlSession.clearCache();
				sqlSession.close();
			}
		} catch (Exception e) {
			LogCvt.error("mysql数据连接异常...",e);
		}
		
		try {
			if(rpSqlSession != null){
				rpSqlSession.commit(true);
				rpSqlSession.clearCache();
				rpSqlSession.close();
			}
		} catch (Exception e) {
			LogCvt.error("关闭报表 mysql数据连接异常...",e);
		}
	}

	@Override
	public void next() {
		TaskManager.nextTask(task);
	}
	
	
	public abstract void initialize();
	
	
	/**
	 * retrieve all batch nodes
	 * 
	 * @return
	 */
	protected List<BatchNode> retrieveBatchNodes(){
		List<BatchNode> batchNodeList = null;
		
		batchNodeList = new ArrayList<BatchNode>(BatchNodeSingleton.getInstance(rpSqlSession).values());
		
		return batchNodeList;
	}
	
	
	/**
	 * check whether current node is master node
	 * 
	 * @param batchNodeList
	 * 
	 * @return
	 */
	protected boolean isMasterNode(){
		String localIp = null;
		Integer nodePort = null;
		BatchNode batchNode = null;
		boolean isMaster = false;
		
		localIp = IPUtil.getLocalIp();
		nodePort = IPUtil.getNodePort();
		
		LogCvt.info("IP: " + localIp);
		
		if (null == batchNodeList){
			LogCvt.error("Batch node is not definded in table cb_report_batch_node!");
			return false;
		}
		
		/**
		 * identity the master node by ip & port & master node indicator
		 */
		for (int i = 0; i < batchNodeList.size(); i++){
			batchNode = batchNodeList.get(i);
			if (batchNode.getNodeIp().equals(localIp)
					&& batchNode.getNodePort().equals(nodePort)
					&& batchNode.getMasterNode() == MasterNode.master.getCode()) {
				isMaster = true;
				break;
			}
		}
		
		return isMaster;
	}
	
	
}
