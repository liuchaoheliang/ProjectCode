package com.froad.singleton;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.rp_mappers.BatchNodeMapper;
import com.froad.po.BatchNode;

public class BatchNodeSingleton {
	private static volatile Map<Integer, BatchNode> instance = null;
	
	private BatchNodeSingleton(){
	}
	
	/**
	 * get batch node instance
	 * 
	 * @param rpSession
	 * @return
	 */
	public static Map<Integer, BatchNode> getInstance(SqlSession rpSession){
		List<BatchNode> list = null;
		BatchNodeMapper mapper = null;
		BatchNode batchNode = null;
		
		if (null == instance){
			synchronized (BatchNodeSingleton.class){
				if (null == instance){
					mapper = rpSession.getMapper(BatchNodeMapper.class);
					list = mapper.findAll();
					
					if (null == list || list.size() == 0){
						return instance;
					}
					
					instance = new HashMap<Integer, BatchNode>();
					for (int i = 0; i < list.size(); i++){
						batchNode = list.get(i);
						instance.put(batchNode.getNodeId(), batchNode);
					}
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * destroy instance
	 */
	public static void destroyInstance(){
		if (instance != null){
			instance.clear();
			instance = null;
		}
	}
	
	/**
	 * find batch node by ip and port
	 * 
	 * @param rpSession
	 * @param nodeIp
	 * @param nodePort
	 * @return
	 */
	public static BatchNode findByIpAndNode(SqlSession rpSession, String nodeIp, Integer nodePort){
		BatchNode batchNode = null;
		BatchNode resultNode = null;
		Iterator<BatchNode> iterator = null;
		
		if (null == instance){
			getInstance(rpSession);
		}
		
		iterator = instance.values().iterator();
		while (iterator.hasNext()){
			batchNode = iterator.next();
			if (batchNode.getNodeIp().equals(nodeIp) && batchNode.getNodePort().equals(nodePort)){
				resultNode = batchNode;
				break;
			}
		}
		
		return resultNode;
	}
}
