package com.froad.cbank.expand.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作接口
 * @ClassName: RedisService 
 * @author LC 
 * @date 2015年3月13日 下午1:43:51 
 *
 */
public interface RedisService {
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @return 成功 返回OK 失败返回 0
	 * String
	 * 
	 * @author: LC
	 * @date:2015年3月16日 上午10:11:17
	 */
	public String putString(String key, String value);
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param seconds 过期时间 单位：秒
	 * @return 成功返回OK 失败和异常返回null
	 * String
	 * 
	 * @author: LC
	 * @date:2015年3月16日 上午10:16:19
	 */
	public String putExpire(String key, String value, int seconds);
	
	/**
	 * 
	 * @param key
	 * @return 成功返回value 失败返回null
	 * String
	 * 
	 * @author: LC
	 * @date:2015年3月16日 上午10:11:50
	 */
	public String getString(String key);
	
	/**
	 * 
	 * @param key
	 * @param valueList
	 * @return 返回list的value个数
	 * String
	 * 
	 * @author: LC
	 * @date:2015年3月16日 下午4:18:36
	 */
	public Long putList(String key,List<String> valueList);
	
	/**
	 * 
	 * @param key
	 * @return
	 * List<String>
	 * 
	 * @author: LC
	 * @date:2015年3月16日 下午4:19:04
	 */
	public List<String> getList(String key);
	
	/**
	 * 
	 * @param key
	 * @param valueMap 
	 * @return 返回OK 异常返回null
	 * String
	 * 
	 * @author: LC
	 * @date:2015年3月16日 上午10:21:56
	 */
	public String putMap(String key, Map<String,String> valueMap);
	
	
	
	/**
	  * 方法描述：获取指定Map缓存的指定fieldKey对应的值
	  * @param: mapKey - 缓存key ，fieldKey - map的可以键
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年7月2日 下午2:58:30
	  */
	public String getMapValue(String mapKey, String fieldKey);
	
	/**
	  * 方法描述：获取指定Map缓存的指定fieldKey对应的值
	  * @param: mapKey - 缓存key ，fieldKey - map的可以键
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年7月2日 下午2:58:30
	  */
	public String hget(String key, String field);

	
	/**
	  * 方法描述：获取指定map缓存的全部值
	  * @param: key
	  * @return: Map<String,String>
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年7月2日 下午3:02:30
	  */
	public Map<String,String> getMap(String key);
	
	public Long put(String key, String... members);
	/**
	 * 
	 * @param key
	 * @param valueSet
	 * @return 添加成功的个数
	 * Long
	 * 
	 * @author: LC
	 * @date:2015年3月16日 下午4:55:42
	 */
	public Long putSet(String key, Set<String> valueSet); 
	
	/**
	 * 
	 * @param key
	 * @return
	 * Set<String>
	 * 
	 * @author: LC
	 * @date:2015年3月16日 下午3:38:41
	 */
	public Set<String> getSet(String key);
	
	/**
	 * 
	 * @param key
	 * @return
	 * Boolean
	 * 
	 * @author: LC
	 * @date:2015年3月16日 上午10:14:13
	 */
	public Boolean exists(String key);
	
	/**
	 * 
	 * @Description:删除指定的key,也可以传入一个包含key的数组
	 * @param keys
	 * @return 返回删除成功的个数
	 * Long
	 * 
	 * @author: LC
	 * @date:2015年3月16日 下午5:14:28
	 */
	public Long del(String...keys);
	
	/**
	 * 
	 * @Description:原子性增加指定的key对一个你的值,增加后必须小于max
	 * @param key
	 * @param max
	 * @return 增加后的值
	 * Long
	 * 
	 * @author: LXB
	 * @date:2015年3月25日 上午11:36:28
	 */
	public Long incr(String key, Long max);
	
	/**
	 * 
	 * 增加指定的key对一个你的值,增加后必须小于max
	 * @param key
	 * @param increment 每次的增量
	 * @param max
	 * @return 增加后的值
	 * 
	 * @author: wangzhangxu
	 * @date:2015年4月20日 上午13:57:28
	 */
	public Long incrBy(String key, Long increment);
	
	/**
	 * 
	 * 减去指定的key对应的减量值
	 * @param key
	 * @param decrement 每次的减量
	 * @return 减去后的值
	 * 
	 * @author: wangzhangxu
	 * @date:2015年4月20日 上午13:57:28
	 */
	public Long decrBy(String key, Long decrement);
	
	/**
	 * 
	 * @Title: srem 
	 * @Description: 删除指定key对应的value
	 * @author: froad-huangyihao 2015年3月31日
	 * @modify: froad-huangyihao 2015年3月31日
	 * @param key
	 * @param values
	 * @return
	 * @throws
	 */
	public Long srem(String key, String...values);
	
	/**
	 * 
	 * @Title: hincrBy 
	 * @Description: 增加/减少指定key-field的value
	 * @author: froad-huangyihao 2015年4月3日
	 * @modify: froad-huangyihao 2015年4月3日
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 * @throws
	 */
	public Long hincrBy(String key, String field, Long value);
	
	/**
	 * 设置key-field的value值
	 * @param key
	 * @param field
	 * @param value
	 * @return	field存在,更新成功,返回0	
	 * 			若field不存在,则新增field,返回1
	 * 			异常返回-1
	 */
	public Long hset(String key, String field, String value);
	
	
	public Long zadd(String key, double score, String member) ;
	
	/**
	 * 
	 * @Title: zrem 
	 * @author vania
	 * @version 1.0
	 * @param key
	 * @param member
	 * @return
	 * @return Long    返回类型 
	 * @throws
	 */
	public Long zrem(String key, String member) ;
	
	public List<String> hmget(String key,String... fields);
	
	public Long hdel (String key,String... fields);
	
	public Set<String> keys (String pattern) ;
	

	public Long lpush(String key, String... values);
	
	public Long rpush(String key, String... values);
	
	public List<String> lrange(String key, long start, long end);
	
	public Long llen(String key);
	
	public Long expire (String key, int seconds);
	
	public Long setnx (String key, String value);
	
	public String setex (String key,int seconds, String value) ;
	

}
