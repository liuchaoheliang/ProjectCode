package com.froad.db.redis;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.froad.db.redis.impl.RedisManager;
import com.froad.po.WeightActivityTag;
import com.froad.util.Checker;
import com.froad.util.RedisKeyUtil;

public class WeightActivityTagRedis {
	
	private RedisManager redis = new RedisManager();
	
	/**
	 * 
	 * delWeightActivityTagInfo:(删除缓存中的权重活动关联信息).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:46:39
	 * @param clientId
	 * @param id
	 *
	 */
	public void delWeightActivityTagInfo(String clientId, Long id){
		String key = RedisKeyUtil.cbbank_weight_activity_tag_clientId_id(clientId, id);
		redis.del(key);
	}
	
	
	
	public Boolean addWeightActivityTagRedis(WeightActivityTag weightTag){
		String key = RedisKeyUtil.cbbank_weight_activity_tag_clientId_id(weightTag.getClientId(), weightTag.getId());
		
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", ObjectUtils.toString(weightTag.getId(), ""));
		map.put("client_id", ObjectUtils.toString(weightTag.getClientId(), ""));
		map.put("activity_id", ObjectUtils.toString(weightTag.getActivityId(), ""));
		map.put("activity_no", ObjectUtils.toString(weightTag.getActivityNo(), ""));
		map.put("element_id", ObjectUtils.toString(weightTag.getElementId(), ""));
		map.put("weight", ObjectUtils.toString(weightTag.getWeight(), ""));
		map.put("activity_type", ObjectUtils.toString(weightTag.getActivityType(), ""));
		map.put("operator", ObjectUtils.toString(weightTag.getOperator(), ""));
		map.put("create_time", ObjectUtils.toString(weightTag.getCreateTime().getTime(), ""));
		map.put("update_time", Checker.isNotEmpty(weightTag.getUpdateTime()) ? String.valueOf(weightTag.getUpdateTime().getTime()) : "");
		
		String result = redis.putMap(key, map);
		return "OK".equals(result);
	}
	
	
	
	
	
	
	
}
