package com.froad.db.redis;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.RecommendActivityTag;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class RecommendActivityTagRedis {
	
	private RedisManager redis = new RedisManager();
	
	/**
	 * 
	 * addActivityTagRedis:(储存cb_recommend_activity_tag表信息到redis中).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:43:59
	 * @param tag
	 * @return
	 *
	 */
	public Boolean addActivityTagRedis(RecommendActivityTag tag){
		String redisKey = RedisKeyUtil.cbbank_recommend_activity_tag_clientId_id(tag.getClientId(), tag.getId());
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", ObjectUtils.toString(tag.getId(), ""));
		map.put("client_id", ObjectUtils.toString(tag.getClientId(), ""));
		map.put("activity_name", ObjectUtils.toString(tag.getActivityName(), ""));
		map.put("activity_no", ObjectUtils.toString(tag.getActivityNo(), ""));
		map.put("operator", ObjectUtils.toString(tag.getOperator(), ""));
		map.put("activity_desc", ObjectUtils.toString(tag.getActivityDesc(), ""));
		map.put("status", ObjectUtils.toString(tag.getStatus(), ""));
		map.put("activity_type", ObjectUtils.toString(tag.getActivityType(), ""));
		map.put("logo_url", ObjectUtils.toString(tag.getLogoUrl(), ""));
		map.put("create_time", ObjectUtils.toString(tag.getCreateTime().getTime(), ""));
		map.put("update_time", Checker.isNotEmpty(tag.getUpdateTime()) ? String.valueOf(tag.getUpdateTime().getTime()) : "");
		
		String result = redis.putMap(redisKey, map);
		return "OK".equals(result);
	}
	
	
	/**
	 * 
	 * findRecommendActivityTagInfo:(根据clientId和id查询活动标签缓存信息).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:44:45
	 * @param clientId
	 * @param id
	 * @return
	 *
	 */
	public RecommendActivityTag findRecommendActivityTagInfo(String clientId, Long id){
		String redisKey = RedisKeyUtil.cbbank_recommend_activity_tag_clientId_id(clientId, id);
		Map<String, String> map = redis.getMap(redisKey);
		
		RecommendActivityTag tag = null;
		if(Checker.isNotEmpty(map)){
			tag = new RecommendActivityTag();
			tag.setId(id);
			tag.setClientId(clientId);
			tag.setActivityName(map.containsKey("activity_name") ? map.get("activity_name") : null);
			tag.setActivityNo(map.containsKey("activity_no") ? map.get("activity_no") : null);
			tag.setOperator(map.containsKey("operator") ? map.get("operator") : null);
			tag.setActivityDesc(map.containsKey("activity_desc") ? map.get("activity_desc") : null);
			tag.setStatus(map.containsKey("status") ? map.get("status") : null);
			tag.setActivityType(map.containsKey("activity_type") ? map.get("activity_type") : null);
			tag.setLogoUrl(map.containsKey("logo_url") ? map.get("logo_url") : null);
			tag.setCreateTime(map.containsKey("create_time") ? new Date(Long.valueOf(map.get("create_time"))) : null);
			tag.setUpdateTime(Checker.isNotEmpty(map.get("update_time")) ? new Date(Long.valueOf(map.get("update_time"))) : null);
		}
		
		return tag;
	}
	
	
	/**
	 * 
	 * updateRecommendStatus:(根据clientId和id更新缓存中的状态).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:45:20
	 * @param clientId
	 * @param id
	 * @param status
	 *
	 */
	public void updateRecommendStatus(String clientId, Long id, String status){
		String redisKey = RedisKeyUtil.cbbank_recommend_activity_tag_clientId_id(clientId, id);
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("status", status);
		map.put("update_time", String.valueOf(System.currentTimeMillis()));
		
		redis.putMap(redisKey, map);
	}
	
	
	
	public void recordActivityId(RecommendActivityTag tag){
		String redisKey = RedisKeyUtil.cbbank_recommend_activity_tag_clientId_activityType(tag.getClientId(), tag.getActivityType());
		Set<String> set = new HashSet<String>();
		set.add(String.valueOf(tag.getId()));
		redis.putSet(redisKey, set);
	}
	
	
	public Boolean updateActivityTagRedis(RecommendActivityTag tag){
		String redisKey = RedisKeyUtil.cbbank_recommend_activity_tag_clientId_id(tag.getClientId(), tag.getId());
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("operator", ObjectUtils.toString(tag.getOperator(), ""));
		map.put("activity_desc", ObjectUtils.toString(tag.getActivityDesc(), ""));
		map.put("logo_url", ObjectUtils.toString(tag.getLogoUrl(), ""));
		map.put("update_time", String.valueOf(tag.getUpdateTime().getTime()));
		String result = "";
		if(Checker.isNotEmpty(map)){
			result = redis.putMap(redisKey, map);
		}
		return "OK".equals(result);
	}
	
	
	
	
}
