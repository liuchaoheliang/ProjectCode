package com.froad.singleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.rp_mappers.BatchJobMapper;
import com.froad.po.BatchJob;

public class BatchJobSingleton {
	private static volatile Map<String, BatchJob> instance = null;
	
	private BatchJobSingleton(){
	}
	
	/**
	 * get instance, if not exist in cache
	 * 
	 * @param rpSession
	 * @return
	 */
	public static Map<String, BatchJob> getInstance(SqlSession rpSession){
		BatchJobMapper mapper = null;
		List<BatchJob> list = null;
		BatchJob batchJob = null;
		
		if (instance == null){
			synchronized (BatchJobSingleton.class){
				if (instance == null){
					mapper = rpSession.getMapper(BatchJobMapper.class);
					list = mapper.findAll();
					
					if (null == list || list.size() == 0){
						return instance;
					}
					
					instance = new HashMap<String, BatchJob>();
					for (int i = 0; i < list.size(); i++){
						batchJob = list.get(i);
						instance.put(batchJob.getJobName(), batchJob);
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
	 * find batch jobs by file type
	 * 
	 * @param rpSession
	 * @param fileType
	 * @return
	 */
	public static List<BatchJob> findByFileType(SqlSession rpSession, char fileType){
		List<BatchJob> list = null;
		Iterator<BatchJob> iterator = null;
		BatchJob batchJob = null;
		
		getInstance(rpSession);
		
		list = new ArrayList<BatchJob>();
		iterator = instance.values().iterator();
		while (iterator.hasNext()){
			batchJob = iterator.next();
			if (batchJob.getInputFileType() == fileType){
				list.add(batchJob);
			}
		}
		
		return list;
	}
	
	/**
	 * find batch job by job name
	 * 
	 * @param rpSession
	 * @param jobName
	 * @return
	 */
	public static BatchJob findByJobName(SqlSession rpSession, String jobName){
		BatchJob batchJob = null;
		
		getInstance(rpSession);
		
		batchJob = instance.get(jobName);
		
		return batchJob;
	}
}
