package com.froad.cbank.expand.redis;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;

/**
 * 
 * @ClassName: RedisManager
 * @author LC
 * @date 2015年3月12日 下午12:00:24
 *
 */
public class RedisManager implements InitializingBean, DisposableBean, RedisService{

	private JedisPool readPool = null;
	private JedisPool writePool = null;
	private Properties redisConf;
	enum PoolType{
		read,     //读取
		write	 //写入
	}
	
	
	
	@Override
	public void afterPropertiesSet(){
		try{
		//读
		JedisPoolConfig readConfig = new JedisPoolConfig();
		readConfig.setMaxTotal(Integer.valueOf(redisConf.getProperty("read.redis.maxTotal")));
		readConfig.setMaxIdle(Integer.valueOf(redisConf.getProperty("read.redis.maxIdle")));
		readConfig.setMaxWaitMillis(Long.valueOf(redisConf.getProperty("read.redis.maxWaitMillis")));
		readConfig.setTestOnBorrow(Boolean.valueOf(redisConf.getProperty("read.redis.testOnBorrow")));
		readConfig.setTestOnReturn(Boolean.valueOf(redisConf.getProperty("read.redis.testOnReturn")));
		
		readPool = new JedisPool(readConfig, redisConf.getProperty("read.redis.ip"), Integer.valueOf(redisConf.getProperty("read.redis.port")));
		
		
		//写
		JedisPoolConfig writeConfig = new JedisPoolConfig();
		readConfig.setMaxTotal(Integer.valueOf(redisConf.getProperty("write.redis.maxTotal")));
		readConfig.setMaxIdle(Integer.valueOf(redisConf.getProperty("write.redis.maxIdle")));
		readConfig.setMaxWaitMillis(Long.valueOf(redisConf.getProperty("write.redis.maxWaitMillis")));
		readConfig.setTestOnBorrow(Boolean.valueOf(redisConf.getProperty("write.redis.testOnBorrow")));
		readConfig.setTestOnReturn(Boolean.valueOf(redisConf.getProperty("write.redis.testOnReturn")));
		
		writePool = new JedisPool(writeConfig, redisConf.getProperty("write.redis.ip"), Integer.valueOf(redisConf.getProperty("write.redis.port")));
		}catch(Exception e){
			LogCvt.error("初始化redis连接池出错", e);
		}
	}
	
	
	@Override
	public void destroy() throws Exception {
		if(readPool != null){
			readPool.destroy();
		}
		if(writePool != null){
			writePool.destroy();
		}
		
	}
	
	
	
	
	/**
	 * 获取Redis实例
	 * @return
	 * Jedis
	 * @author: LC
	 * @date:2015年3月12日 下午1:03:37
	 */
	public Jedis getJedis(PoolType type) {
		if(PoolType.read.equals(type)){
    		if(readPool != null ){
    			return readPool.getResource();
    		}
    	}else if (PoolType.write.equals(type)){
    		if(writePool != null ){
    			return writePool.getResource();
    		}
    	}
    	return  null ;
	}
	
	/**
	 * 释放redis实例到连接池
	 * @param jedis
	 * void
	 * @author: LC
	 * @date:2015年3月12日 下午1:03:47
	 */
	public void returnJedis(Jedis jedis,PoolType type) {
		if(PoolType.read.equals(type)){
    		if(readPool != null ){
    			readPool.returnResource(jedis);
    		}
    	}else if (PoolType.write.equals(type)){
    		if(writePool != null ){
    			writePool.returnResource(jedis);
    		}
    	}
	}

	public String putString(String key, String value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(PoolType.write);// 获得jedis实例
			return jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return "0";
		} finally {
			returnJedis(jedis,PoolType.write);
		}
	}

	public String putExpire(String key, String value, int seconds) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = getJedis(PoolType.write);// 获得jedis实例
			return jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return res;
	}
	
	
	public String getString(String key) {
		Jedis jedis = null;
		String value = null;
		try {
			jedis = getJedis(PoolType.read);// 获得jedis实例
			value = jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.read);
		}
		return value;
	}

	public String putMap(String key, Map<String, String> valueMap) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = getJedis(PoolType.write);// 获得jedis实例
			res = jedis.hmset(key, valueMap);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return res;
	}

	public Map<String, String> getMap(String key) {
		Jedis jedis = null;
		Map<String, String> res = null;
		try {
			jedis = getJedis(PoolType.read);// 获得jedis实例
			res = jedis.hgetAll(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.read);
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

			jedis = getJedis(PoolType.write);// 获得jedis实例
			res = jedis.sadd(key, members);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return res;
	}
	
	public Long put(String key, String... members) {
		Jedis jedis = null;
		Long res = null;
		try {		

			jedis = getJedis(PoolType.write);// 获得jedis实例
			res = jedis.sadd(key, members);
		} catch (Exception e) {
			e.printStackTrace();
			res = -1l;
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return res;
	}

	public Set<String> getSet(String key) {
		Jedis jedis = null;
		Set<String> res = null;
		try {
			jedis = getJedis(PoolType.read);// 获得jedis实例
			res = jedis.smembers(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.read);
		}
		return res;
	}

	public Boolean exists(String key) {
		Jedis jedis = null;
		try {
			jedis = getJedis(PoolType.read);// 获得jedis实例
			return jedis.exists(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return false;
		} finally {
			returnJedis(jedis,PoolType.read);
		}
	}

	public Long putList(String key, List<String> valueList) {
		Jedis jedis = null;
		Long res = null;
		try {
			String[] strings = null;
			strings = new String[valueList.size()];
			valueList.toArray(strings);

			jedis = getJedis(PoolType.write);// 获得jedis实例
			res = jedis.rpush(key, strings);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return res;
	}

	public List<String> getList(String key) {
		Jedis jedis = null;
		List<String> res = null;
		try {
			jedis = getJedis(PoolType.read);// 获得jedis实例
			res = jedis.hvals(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.read);
		}
		return res;
	}
	
	public Long del(String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis(PoolType.write);// 获得jedis实例
			return jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return 0L;
		} finally {
			returnJedis(jedis,PoolType.write);
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
			jedis = getJedis(PoolType.read);// 获得jedis实例
			value = jedis.hget(key, field);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.read);
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
			jedis = getJedis(PoolType.write);// 获得jedis实例
			result = jedis.hset(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return result;
	}
	
	public Long incr(String key, Long max){
		Jedis jedis = null;
		Long value = -1l;
		
		try {
			jedis = getJedis(PoolType.write);
			value = jedis.incr(key);
			if(value > max){
				jedis.set(key, "0");
				value = 0l;
			}
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return value;
	}

	@Override
	public Long srem(String key, String... values) {
		Jedis jedis = null;
		try {
			jedis = getJedis(PoolType.write);
			return jedis.srem(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return 0l;
		} finally {
			returnJedis(jedis,PoolType.write);
		}
	}
	
	public Long hincrBy(String key, String field, Long value) {
		Jedis jedis = null;
		try {
			jedis = getJedis(PoolType.write);
			return jedis.hincrBy(key, field, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return 0l;
		} finally {
			returnJedis(jedis,PoolType.write);
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
			jedis = getJedis(PoolType.write);
			return jedis.incrBy(key, increment);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,PoolType.write);
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
			jedis = getJedis(PoolType.write);
			return jedis.decrBy(key,value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,PoolType.write);
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
			jedis = getJedis(PoolType.write);
			return jedis.zadd(key, score, member);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,PoolType.write);
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
			jedis = getJedis(PoolType.write);
			return jedis.zrem(key, member);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return -1l;
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		
	}
	
	
	public List<String> hmget(String key,String... fields){
		Jedis jedis = null;
		List<String> value = null;
		try {
			jedis = getJedis(PoolType.read);// 获得jedis实例
			value = jedis.hmget(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.read);
		}
		
		return value;
	}
	
	public Long hdel (String key,String... fields){
		Jedis jedis = null;
		Long result = null;
		try {
			jedis = getJedis(PoolType.write);
			result = jedis.hdel(key, fields);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result= -1l;
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return result;
	}
	
	public Set<String> keys (String pattern) {
		Jedis jedis = null;
		Set<String> result = null;
		try {
			jedis = getJedis(PoolType.read);
			result = jedis.keys(pattern);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result= null;
		} finally {
			returnJedis(jedis,PoolType.read);
		}
		return result;
	}
	
	public Long lpush(String key, String... values){
		Jedis jedis = null;
		Long result = 0l;
		try {
			jedis = getJedis(PoolType.write);
			result = jedis.lpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result= 0l;
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return result;
	}
	
	public Long rpush(String key, String... values){
		Jedis jedis = null;
		Long result = 0l;
		try {
			jedis = getJedis(PoolType.write);
			result = jedis.rpush(key, values);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result= 0l;
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return result;
	}
	
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		List<String> result = null;
		try {
			jedis = getJedis(PoolType.read);
			result = jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result = null;
		} finally {
			returnJedis(jedis, PoolType.read);
		}
		return result;
	}
	
	public Long llen(String key) {
		Jedis jedis = null;
		Long result = 0l;
		try {
			jedis = getJedis(PoolType.read);
			result = jedis.llen(key);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result = 0l;
		} finally {
			returnJedis(jedis, PoolType.read);
		}
		return result;
	}
	
	public Long expire (String key, int seconds) {
		Jedis jedis = null;
		Long res = null;
		try {
			jedis = getJedis(PoolType.write);// 获得jedis实例
			return jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return res;
	}
	
	public Long setnx (String key, String value) {
		Jedis jedis = null;
		Long res = 0l;
		try {
			jedis = getJedis(PoolType.write);// 获得jedis实例
			return jedis.setnx(key, value);
		} catch (Exception e) {
			res = 0l;
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return res;
	}
	
	public String setex (String key,int seconds, String value) {
		Jedis jedis = null;
		String res = null;
		try {
			jedis = getJedis(PoolType.write);// 获得jedis实例
			return jedis.setex(key,seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis,PoolType.write);
		}
		return res;
	}


	public JedisPool getReadPool() {
		return readPool;
	}


	public void setReadPool(JedisPool readPool) {
		this.readPool = readPool;
	}


	public JedisPool getWritePool() {
		return writePool;
	}


	public void setWritePool(JedisPool writePool) {
		this.writePool = writePool;
	}


	public Properties getRedisConf() {
		return redisConf;
	}


	public void setRedisConf(Properties redisConf) {
		this.redisConf = redisConf;
	}

	

	

}
