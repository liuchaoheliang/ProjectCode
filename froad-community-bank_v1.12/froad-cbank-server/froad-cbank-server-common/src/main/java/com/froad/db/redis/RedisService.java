package com.froad.db.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * redis操作接口
 * @ClassName: RedisService 
 * @author FQ 
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
	 * @author: FQ
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
	 * @author: FQ
	 * @date:2015年3月16日 上午10:16:19
	 */
	public String putExpire(String key, String value, int seconds);
	
	/**
	 * 
	 * @param key
	 * @return 成功返回value 失败返回null
	 * String
	 * 
	 * @author: FQ
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
	 * @author: FQ
	 * @date:2015年3月16日 下午4:18:36
	 */
	public Long putList(String key,List<String> valueList);
	
	/**
	 * 
	 * @param key
	 * @return
	 * List<String>
	 * 
	 * @author: FQ
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
	 * @author: FQ
	 * @date:2015年3月16日 上午10:21:56
	 */
	public String putMap(String key, Map<String,String> valueMap);
	
	
	public String getMapValue(String mapKey, String fieldKey);
	
	/**
	 * Get field value from redis hash
	 * 
	 * @param mapKey
	 * @param fieldKey
	 * @return
	 */
	public String hget(String key, String field);
	/**
	 * 
	 * @param key
	 * @return
	 * Map<String,String>
	 * 
	 * @author: FQ
	 * @date:2015年3月16日 上午10:28:50
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
	 * @author: FQ
	 * @date:2015年3月16日 下午4:55:42
	 */
	public Long putSet(String key, Set<String> valueSet); 
	/**
	 * 返回set的member个数
	 * @Title: scard 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param key
	 * @return
	 * @return Long    返回类型 
	 * @throws
	 */
	public Long scard(String key) ;
	
	/**
	 * 
	 * @Title: sismember 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param key
	 * @param member
	 * @return
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean sismember(String key, String member) ;
	
	/**
	 * 
	 * @param key
	 * @return
	 * Set<String>
	 * 
	 * @author: FQ
	 * @date:2015年3月16日 下午3:38:41
	 */
	public Set<String> getSet(String key);
	
	/**
	 * 
	 * @param key
	 * @return
	 * Boolean
	 * 
	 * @author: FQ
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
	 * @author: FQ
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
	
	public Long incr(String key);
	
	public String get(String key);
	
	public String set(String key,String value);
	
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
	
	/**
	 * 获取分页查找总页数
	 * 
	 * @param pageKey
	 * @return
	 */
	public Integer getPageQueryTotalCount(String md5PageKey);
	
	/**
	 * 获取分页查找总页数
	 * 
	 * @param pageKey
	 * @param count
	 * @return
	 */
	public void setPageQueryTotalCount(String md5PageKey, Integer count);	
	
	/**
	 * 返回名称为key的zset（元素已按score从大到小排序）中的index从start到end的所有元素
	 * @param key
	 * @param start
	 * @param end
	 * @return Set<String>
	 */
	public Set<String> zRange(String key, long start, long end);
	
	/**
	 * 获取有序交集
	 *<一句话功能简述>.
	 *
	 * <pre>
	 * TODO wangyan <功能详细描述>
	 * </pre>
	 * @param dstkey
	 * @param sets
	 * @return
	 * @return Long
	 */
	public Long zinterstore(String dstkey,String... sets);
	
	
	/**
	 *  阻塞队列BRPOP
	 * @param key
	 * @return
	 */
	public List<String> brpop(int timeout,String key);
	
	/**
	 *  阻塞队列BRPOP
	 * @param key
	 * @return
	 */
	public String rpop(String key);
	
	/**
	 *  阻塞队列blpop
	 * @param key
	 * @return
	 */
	public List<String> blpop(int timeout,String key);
	
	
		
	
	/**
	 * 返回名称为key的zset(按score从大到小排序)中的index从start到end的所有元素
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Set<String> zrevrange(String key, long start, long end);
	
	/**
	 * 返回给定元素对应的score
	 * @param key
	 * @param member
	 * @return
	 */
	public Double zscore(String key, String member);
	
	/**
	 * 返回集合中score在给定区间的元素
	 * @param key
	 * @param min
	 * @param max
	 * @return
	 */
	public Set<String> zrangeByScore(String key, double min, double max);
	
	/**
	 * 获取zset集合中的数量
	 * @param key
	 * @return
	 */
	public Long zcard(String key);
	
	/**
	 * 删除score范围的member
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public Long zremrangeByScore(String key, double start, double end);
	
}
