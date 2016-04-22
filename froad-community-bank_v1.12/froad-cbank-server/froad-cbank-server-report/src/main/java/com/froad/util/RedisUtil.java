package com.froad.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.ObjectUtils;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import com.froad.Constants;
import com.froad.logback.LogCvt;

public class RedisUtil {

	private static JedisPool jedisPool = null;
	private static int dbIndex = 0;
	
	private static final String DEFAULT_PADDING = "*";
	public static final String MONGO_COLLECTION_KEYS = "cbbank:mongo:collection:keys";
	
	// 初始化Redis连接池
	static {
		FileReader fr = null;
		try {
			Properties props = new Properties();
			fr = new FileReader(new File(System.getProperty(Constants.CONFIG_PATH)+File.separatorChar+"rp_redis.properties"));
			props.load(fr);

			dbIndex = Integer.valueOf(props.getProperty("redis.db.index"));
			
			// 创建jedis连接池配置实例
			JedisPoolConfig poolConfig = new JedisPoolConfig();

			// 设置池配置项值
			poolConfig.setMaxTotal(Integer.valueOf(props.getProperty("redis.pool.maxTotal")));
			poolConfig.setMaxIdle(Integer.valueOf(props.getProperty("redis.pool.maxIdle")));
			poolConfig.setMaxWaitMillis(Long.valueOf(props.getProperty("redis.pool.maxWaitMillis")));
			poolConfig.setTestOnBorrow(Boolean.valueOf(props.getProperty("redis.pool.testOnBorrow")));
			poolConfig.setTestOnReturn(Boolean.valueOf(props.getProperty("redis.pool.testOnReturn")));

			// 根据配置实例化jedis池
			jedisPool = new JedisPool(poolConfig, props.getProperty("redis.ip"),Integer.valueOf(props.getProperty("redis.port")));
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
	
	public static String cbbank_merchant_contract_md5key(String md5Key) {
		return MessageFormat.format("cbbank:report:merchant:contract:{0}", ObjectUtils.toString(md5Key, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_info_md5key(String md5Key) {
		return MessageFormat.format("cbbank:report:merchant:info:{0}", ObjectUtils.toString(md5Key, DEFAULT_PADDING));
	}
	
	public static String cbbank_product_sale_md5key(String md5Key) {
		return MessageFormat.format("cbbank:report:product:sale:{0}", ObjectUtils.toString(md5Key, DEFAULT_PADDING));
	}
	
	public static String cbbank_user_summary_md5key(String md5Key){
		return MessageFormat.format("cbbank:report:user:summary:{0}", ObjectUtils.toString(md5Key, DEFAULT_PADDING));
	}
	
	public static String cbbank_merchant_sale_md5key(String md5Key){
		return MessageFormat.format("cbbank:report:merchant:sale:{0}", ObjectUtils.toString(md5Key, DEFAULT_PADDING));
	}
	
	/**
	 * 连接池获取jedis实例
	 * 
	 * @return
	 */
	public Jedis getJedis(){
		Jedis jedis =null;
		
		if (jedisPool != null) {
			jedis = jedisPool.getResource();
//			jedis.select(dbIndex);
		} 
		
		return jedis;
	}
	
	/**
	 * 规划jedis到连接池
	 * 
	 * @param jedis
	 */
	public static void returnJedis(Jedis jedis) {
		if (jedis != null) {
			jedisPool.returnResource(jedis);
		}
	}
	
	/**
	 * Set field value for redis hash
	 * 
	 * @param key
	 * @param field
	 * @param value
	 * @return
	 */
	public Long hset(String key, String field, String value){
		Jedis jedis = null;
		Long result = null;
		
		try {
			jedis = getJedis();// 获得jedis实例
			result = jedis.hset(key, field, value);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis);
		}
		return result;
	}
	
	/**
	 * Get field value from redis hash
	 * 
	 * @param key
	 * @param field
	 * @return
	 */
	public String hget(String key, String field){
		Jedis jedis = null;
		String value = null;
		
		try {
			jedis = getJedis();// 获得jedis实例
			value = jedis.hget(key, field);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis);
		}
		
		return value;
	}
	
	/**
	 * Set field value for redis hash
	 * 
	 * @param key
	 * @param hash
	 * @return
	 */
	public void hmset(String key, Map<String, String> hash){
		Jedis jedis = null;
		
		try {
			jedis = getJedis();// 获得jedis实例
			jedis.hmset(key, hash);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis);
		}
	}
	
	/**
	 * 获取hash多个字段值
	 * 
	 * @param key
	 * @param fields
	 * @return
	 */
	public List<String> hmget(String key,String... fields){
		Jedis jedis = null;
		List<String> value = null;
		try {
			jedis = getJedis();// 获得jedis实例
			value = jedis.hmget(key, fields);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis);
		}
		
		return value;
	}
	
	/**
	 * 获取整个hash
	 * 
	 * @param key
	 * @return
	 */
	public Map<String, String> hgetAll(String key){
		Jedis jedis = null;
		Map<String, String> resultHash = null;
		
		try {
			jedis = getJedis();// 获得jedis实例
			resultHash = jedis.hgetAll(key);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		} finally {
			returnJedis(jedis);
		}
		
		return resultHash;
	}
	
	/**
	 * 
	 * 删除键值
	 * @param keys
	 * @return
	 */
	public Long del(String... keys) {
		Jedis jedis = null;
		try {
			jedis = getJedis();// 获得jedis实例
			return jedis.del(keys);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			return 0L;
		} finally {
			returnJedis(jedis);
		}
	}
	
	/**
	 * Add the string value to the head of the list
	 * 
	 * @param key
	 * @param values
	 * @return
	 */
	public Long lpush(String key, String... values){
		Jedis jedis = null;
		Long result = 0l;
		try {
			jedis = getJedis();
			result = jedis.lpush(key, values);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			result= 0l;
		} finally {
			returnJedis(jedis);
		}
		return result;
	}
	
	/**
	 * Return the specified elements of the list stored at the specified key.
	 * 	0 is the first element of the list
	 * 	-1 is the last element of the list
	 * 
	 * @param key
	 * @param start
	 * @param end
	 * @return
	 */
	public List<String> lrange(String key, long start, long end) {
		Jedis jedis = null;
		List<String> result = null;
		try {
			jedis = getJedis();
			result = jedis.lrange(key, start, end);
		} catch (Exception e) {
			e.printStackTrace();
			LogCvt.error(e.getMessage(), e);
			result = null;
		} finally {
			returnJedis(jedis);
		}
		return result;
	}
}
