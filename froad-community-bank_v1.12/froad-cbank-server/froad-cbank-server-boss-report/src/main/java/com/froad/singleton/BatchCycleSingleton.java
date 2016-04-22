package com.froad.singleton;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.rp_mappers.BatchCycleMapper;
import com.froad.enums.BatchCycleStatus;
import com.froad.po.BatchCycle;

public class BatchCycleSingleton {
	private static volatile Map<Integer, BatchCycle> instance = null;
	
	private BatchCycleSingleton(){
	}
	
	/**
	 * get batch cycle instance
	 * 
	 * @param rpSession
	 * @return
	 */
	public static Map<Integer, BatchCycle> getInstance(SqlSession rpSession){
		BatchCycleMapper mapper = null;
		List<BatchCycle> list = null;
		BatchCycle batchCycle = null;
		
		if (null == instance){
			synchronized (BatchCycleSingleton.class){
				if (null == instance){
					mapper = rpSession.getMapper(BatchCycleMapper.class);
					list = mapper.findAll();
					
					if (null == list || list.size() == 0){
						return instance;
					}
					
					instance = new HashMap<Integer, BatchCycle>();
					for (int i = 0; i < list.size(); i++){
						batchCycle = list.get(i);
						instance.put(batchCycle.getBatchId(), batchCycle);
					}
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * get current batch cycle
	 * 
	 * @param rpSession
	 * @return
	 */
	public static BatchCycle getCurrentCycle(SqlSession rpSession){
		Iterator<BatchCycle> iterator = null;
		BatchCycle batchCycle = null;
		BatchCycle currentCycle = null;
		
		getInstance(rpSession);
		
		iterator = instance.values().iterator();
		while (iterator.hasNext()){
			batchCycle = iterator.next();
			if (batchCycle.getStatus() == BatchCycleStatus.ready.getCode()){
				currentCycle = batchCycle;
				break;
			}
		}
		
		return currentCycle;
	}
	
	
	public static void destroyInstance(){
		if (instance != null){
			instance.clear();
			instance = null;
		}
	}
}
