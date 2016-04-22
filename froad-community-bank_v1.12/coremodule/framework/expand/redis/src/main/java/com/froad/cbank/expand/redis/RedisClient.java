package com.froad.cbank.expand.redis;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;

public class RedisClient {
	
//   private Jedis jedis;//非切片额客户端连接 
//   private ShardedJedis shardedJedis;//切片额客户端连接
	
	//连接池类型
	enum PoolType{
		read,     //读取
		write	 //写入
	}
	
    private   JedisPool readPool;//非切片连接池  (读取)
    private   JedisPool writePool;//非切片连接池  (写入)
    
//    private static ShardedJedisPool shardedJedisPool;//切片连接池
    
    
    /**
      * 方法描述：获取非切片额客户端连接（非集群）
      * @param: poolType 连接池类型，读/写
      * @return: 
      * @version: 1.0
      * @author: 刘超 liuchao@f-road.com.cn
      * @time: 2015年7月1日 下午6:23:51
      */
    public  Jedis getJedis(PoolType type){
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
      * 方法描述：正常回收jedis连接
      * @param: 
      * @return: 
      * @version: 1.0
      * @author: 刘超 liuchao@f-road.com.cn
      * @time: 2015年7月2日 上午10:26:23
      */
    public  void returnResource(Jedis jedis,PoolType type){
    	if(PoolType.read.equals(type)){
    		readPool.returnResource(jedis);
    	}else if (PoolType.write.equals(type)){
    		 writePool.returnResource(jedis);
    	}	
    }   
    
    
    /**
      * 方法描述：获取切片额客户端连接（集群）
      * @param: 
      * @return: 
      * @version: 1.0
      * @author: 刘超 liuchao@f-road.com.cn
      * @time: 2015年7月1日 下午6:23:54
      */
//    public static ShardedJedis getShardedJedis(){
//    	return shardedJedisPool.getResource();
//    }
    /**
     * 方法描述：正常回收shardedJedis连接
     * @param:  
     * @return: 
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2015年7月2日 上午10:26:26
     */
//    public static void returnResource(ShardedJedis shardedJedis){
//    	   shardedJedisPool.returnResource(shardedJedis);
//    }
    /**
     * 方法描述：异常回收shardedJedis连接
     * @param:  broken - true
     * @return: 
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2015年7月2日 上午10:26:26
     */
//    public static void returnResource(ShardedJedis shardedJedis,boolean broken){
//        if (broken) {
//            shardedJedisPool.returnBrokenResource(shardedJedis);
//        } else {
//            shardedJedisPool.returnResource(shardedJedis);
//        }
//    }
    
    
    /**
      * 方法描述：获取JedisPool当前连接数量
      * @param: 
      * @return: 
      * @version: 1.0
      * @author: 刘超 liuchao@f-road.com.cn
      * @time: 2015年7月2日 上午10:44:20
      */
    public  int jedisPoolActiveNum(PoolType type){ 	
    	if(PoolType.read.equals(type)){
    		return readPool.getNumActive();
    	}else if (PoolType.write.equals(type)){
    		return  writePool.getNumActive();
    	}	
    	return 0;
    }


	public  JedisPool getReadPool() {
		return readPool;
	}


	public  void setReadPool(JedisPool readPool) {
		this.readPool = readPool;
	}


	public  JedisPool getWritePool() {
		return writePool;
	}


	public  void setWritePool(JedisPool writePool) {
		this.writePool = writePool;
	}
    
   

//	public ShardedJedisPool getShardedJedisPool() {
//		return shardedJedisPool;
//	}
//
//	public void setShardedJedisPool(ShardedJedisPool shardedJedisPool) {
//		this.shardedJedisPool = shardedJedisPool;
//	}
    
}
