package com.froad.cbank.expand.redis;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;


/**
 * 
 * @ClassName: RedisCommon
 * @author FQ
 * @date 2015年3月12日 下午12:00:24
 *
 */
public class RedisCommon implements RedisService{

	private static JedisPool readPool = null;
	private static JedisPool writePool = null;
	private static String path="/data/ubank/config/web/merchant/";
//	private static String path="E:\\data\\ubank\\config\\web\\merchant\\";
	public static final int read  = 0;	// 读
	public static final int write = 1;	// 写

//	private static int pageCountExpireTime = 0;
	
	// 初始化Redis连接池
	static {
		FileReader fr = null;
		try {
			Properties props = new Properties();
			fr = new FileReader(new File(path+"redis.properties"));
			props.load(fr);

			// 创建读jedis池配置实例
			JedisPoolConfig readConfig = new JedisPoolConfig();

			// 设置池配置项值
			readConfig.setMaxTotal(Integer.valueOf(props.getProperty("read.redis.pool.maxTotal")));
			readConfig.setMaxIdle(Integer.valueOf(props.getProperty("read.redis.pool.maxIdle")));
			readConfig.setMaxWaitMillis(Long.valueOf(props.getProperty("read.redis.pool.maxWaitMillis")));
			readConfig.setTestOnBorrow(Boolean.valueOf(props.getProperty("read.redis.pool.testOnBorrow")));
			readConfig.setTestOnReturn(Boolean.valueOf(props.getProperty("read.redis.pool.testOnReturn")));

			// 根据配置实例化jedis池
			readPool = new JedisPool(readConfig, props.getProperty("read.redis.ip"),Integer.valueOf(props.getProperty("read.redis.port")));
			
			// 创建写jedis池配置实例
			JedisPoolConfig writeConfig = new JedisPoolConfig();

			// 设置池配置项值
			writeConfig.setMaxTotal(Integer.valueOf(props.getProperty("write.redis.pool.maxTotal")));
			writeConfig.setMaxIdle(Integer.valueOf(props.getProperty("write.redis.pool.maxIdle")));
			writeConfig.setMaxWaitMillis(Long.valueOf(props.getProperty("write.redis.pool.maxWaitMillis")));
			writeConfig.setTestOnBorrow(Boolean.valueOf(props.getProperty("write.redis.pool.testOnBorrow")));
			writeConfig.setTestOnReturn(Boolean.valueOf(props.getProperty("write.redis.pool.testOnReturn")));

			// 根据配置实例化jedis池
			writePool = new JedisPool(writeConfig, props.getProperty("write.redis.ip"),Integer.valueOf(props.getProperty("write.redis.port")));
			
//			// 分页查找总数量过期时间
//			pageCountExpireTime = Integer.valueOf(props.getProperty("page.query.expire.time"));

		} catch (IOException e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		}finally{
			try {
				if(fr != null){
					fr.close();
				}
			} catch (IOException e) {
			}
			
		}
	}
	
	/**
	 * 获取Redis实例
	 * @return
	 * Jedis
	 * 
	 * @author: FQ
	 * @date:2015年3月12日 下午1:03:37
	 */
	public static Jedis getJedis(int type) {
		switch (type) {
		case RedisCommon.read:
			if (readPool != null) {
				return readPool.getResource();
			} 
			break;
		case RedisCommon.write:
			if (writePool != null) {
				return writePool.getResource();
			}
			break;
		}
		return null;
	}
	
	/**
	 * 释放redis实例到连接池
	 * @param jedis
	 * void
	 * 
	 * @author: FQ
	 * @date:2015年3月12日 下午1:03:47
	 */
	public static void returnJedis(Jedis jedis,int type) {
		
		switch (type) {
		case RedisCommon.read:
			if (jedis != null) {
				readPool.returnResource(jedis);
			} 
			break;
		case RedisCommon.write:
			if (jedis != null) {
				writePool.returnResource(jedis);
			}
			break;
		}
		
	}

	public String putString(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			return jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return "0";
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}
	
	@SuppressWarnings("resource")
	public String putMset(Set<String> keys, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			for(String set : keys)
			{
				return jedis.mset(set,value);
			}
			return "1";
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return "0";
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}
	

	public String putExpire(String key, String value, int seconds) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			return jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return res;
	}
	
	
	public String getString(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = getJedis(RedisCommon.read);// 获得jedis实例
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
		return value;
	}

	public String putMap(String key, Map<String, String> valueMap) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			res = jedis.hmset(key, valueMap);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return res;
	}

	public Map<String, String> getMap(String key) {
		Jedis jedis = null;
		Map<String, String> res = null;
		try {
			jedis = getJedis(RedisCommon.read);// 获得jedis实例
			res = jedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
		return res;
	}

	public Long putSet(String key, Set<String> valueSet) {
		Jedis jedis = null;
		Long res = null;
		try {
			String[] members = null;
			members = new String[valueSet.size()];
			valueSet.toArray(members);

			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			res = jedis.sadd(key, members);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return res;
	}
	
	public Long put(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		try {		

			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			res = jedis.sadd(key, members);
		} catch (Exception e) {
			e.printStackTrace();
			res = -1l;
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return res;
	}

	public Set<String> getSet(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = getJedis(RedisCommon.read);// 获得jedis实例
			res = jedis.smembers(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
		return res;
	}

	public Boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.read);// 获得jedis实例
			return jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return false;
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
	}

	public Long putList(String key, List<String> valueList) {
		Jedis jedis = null;
		Long res = null;
		try {
			String[] strings = null;
			strings = new String[valueList.size()];
			valueList.toArray(strings);

			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			res = jedis.rpush(key, strings);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return res;
	}
	
	/**
	 * 删除区间之外的值
	 * @param key
	 * @param valueList
	 * @return 
	 */
	public String ltrim (String key, long start, long end) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			return jedis.ltrim (key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return "error";
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}
	
	 /**
	  * @Title: lrem
	  * @Description: 删除count个key的list中值为value的元素
	  * @param key
	  * @param count
	  * @param value
	  * @return 
	 */
	public Long lrem(String key, int count, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			return jedis.lrem(key, count, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return (long) 9999;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}

	public List<String> getList(String key) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = getJedis(RedisCommon.read);// 获得jedis实例
			res = jedis.hvals(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
		return res;
	}
	
	public Long del(String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			return jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return 0L;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}
	
	/**
	 * Get field value from redis hash
	 * 
	 * @param mapKey
	 * @param fieldKey
	 * @return
	 */
	public String getMapValue(String mapKey, String fieldKey){
		
		return this.hget(mapKey, fieldKey);
	}
	
	/**
	 * Get field value from redis hash
	 * 
	 * @param mapKey
	 * @param fieldKey
	 * @return
	 */
	public String hget(String key, String field){
		Jedis jedis = null;
		String value = null;
		
		try {
			jedis = getJedis(RedisCommon.read);// 获得jedis实例
			value = jedis.hget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
		
		return value;
	}
	
	/**
	 * Set field value for redis hash
	 * 
	 * @param mapKey
	 * @param fieldKey
	 * @return
	 */
	public Long hset(String key, String field, String value){
		Jedis jedis = null;
		Long result = null;
		
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			result = jedis.hset(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return result;
	}
	
	public Long incr(String key, Long max){
		Jedis jedis = null;
		Long value = -1l;
		
		try {
			jedis = getJedis(RedisCommon.write);
			value = jedis.incr(key);
			if(value > max){
				jedis.set(key, "0");
				value = 0l;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return value;
	}

	@Override
	public Long srem(String key, String... values) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);
			return jedis.srem(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return 0l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}
	
	public Long hincrBy(String key, String field, Long value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);
			return jedis.hincrBy(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return 0l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}
	
	/**
	 * 将 key中储存的数字值加increment
	 * @param key
	 * @param value
	 * @return
	 */
	public Long incrBy(String key, Long increment) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);
			return jedis.incrBy(key, increment);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}
	
	/**
	 * 将 key中储存的数字值减value
	 * @param key
	 * @param value
	 * @return
	 */
	public Long decrBy(String key, Long value){
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);
			return jedis.decrBy(key,value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
	}
	
	/**
	 * 
	 * @Title: zadd 
	 * @author vania
	 * @version 1.0
	 * @param key
	 * @param score
	 * @param member
	 * @return
	 * @return Long    返回类型 
	 * @throws
	 */
	public Long zadd(String key, double score, String member) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);
			System.out.print(jedis.type(key));
			return jedis.zadd(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		
	}
	
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
	public Long zrem(String key, String member) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);
			return jedis.zrem(key, member);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		
	}
	
	
	public List<String> hmget(String key,String... fields){
		Jedis jedis = null;
		List<String> value = null;
		try {
			jedis = getJedis(RedisCommon.read);// 获得jedis实例
			value = jedis.hmget(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
		
		return value;
	}
	
	public Long hdel (String key,String... fields){
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis(RedisCommon.write);
			result = jedis.hdel(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result= -1l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return result;
	}
	
	public Set<String> keys (String pattern) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			jedis = getJedis(RedisCommon.read);
			result = jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result= null;
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
		return result;
	}
	
	public Long lpush(String key, String... values){
		Jedis jedis = null;
		Long result = 0l;
		try {
			jedis = getJedis(RedisCommon.write);
			result = jedis.lpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result= 0l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return result;
	}
	
	public Long rpush(String key, String... values){
		Jedis jedis = null;
		Long result = 0l;
		try {
			jedis = getJedis(RedisCommon.write);
			result = jedis.rpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result= 0l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return result;
	}
	
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		List<String> result = null;
		try {
			jedis = getJedis(RedisCommon.read);
			result = jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result = null;
		} finally {
			returnJedis(jedis, RedisCommon.read);
		}
		return result;
	}
	
	public Long llen(String key) {
		Jedis jedis = null;
		Long result = 0l;
		try {
			jedis = getJedis(RedisCommon.read);
			result = jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result = 0l;
		} finally {
			returnJedis(jedis, RedisCommon.read);
		}
		return result;
	}
	
	public Long expire (String key, int seconds) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			return jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return res;
	}
	
	public Long setnx (String key, String value) {
		Jedis jedis = null;
		Long res = 0l;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			return jedis.setnx(key, value);
		} catch (Exception e) {
			res = 0l;
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return res;
	}
	
	public String setex (String key,int seconds, String value) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = getJedis(RedisCommon.write);// 获得jedis实例
			return jedis.setex(key,seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		return res;
	}


    /**
     * 
     * zremrangebyscore:(移除score值介于start和end之间（等于）的成员).
     *
     * @author wangyan
     * 2015-8-13 下午2:57:27
     * @param key
     * @param start
     * @param end
     * @return 被移除成员的数量
     *
     */
	public Long zremrangebyscore(String key, double start, double end) {
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.write);
			return jedis.zremrangeByScore(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,RedisCommon.write);
		}
		
	}
	
	/**
	 * Redis读取阻塞队列BRPOP
	 * @param timeout
	 * @param key
	 * @return
	 */
	public List<String> brpop(int timeout,String key){
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.read);
			return jedis.brpop(timeout,key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return null;
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
	}
	
	
	/**
	 * Redis读取阻塞队列BRPOP
	 * @param timeout
	 * @param key
	 * @return
	 */
	public String rpop(String key){
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.read);
			return jedis.rpop(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return null;
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
	}
	
	public List<String> blpop(int timeout,String key){
		Jedis jedis = null;
		try {
			jedis = getJedis(RedisCommon.read);
			return jedis.blpop(timeout,key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return null;
		} finally {
			returnJedis(jedis,RedisCommon.read);
		}
	}
}
