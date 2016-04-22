package com.froad.util.cache;

import java.net.URL;
import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/** 
 * @author FQ 
 * @date 2013-3-13 下午05:41:00
 * @version 1.0
 * 
 */
public class EhCacheUtil {
	
	private static final String PATH = "/ehcache.xml";  
	public static final String CACHE_NAME = "fenfentongCache";//
	
    private URL url;  
    private CacheManager manager;  
  
    /**
     * 初始化缓存管理容器
     * @param path
     */
    private EhCacheUtil(String path) {  
        url = getClass().getResource(path);  
        manager = CacheManager.create(url);  
    }  
    
    private static EhCacheUtil instance=new EhCacheUtil(PATH);
	
	public static EhCacheUtil getInstance(){
		return instance;
	}
  
    /**
     * 增加缓存
     * @param cacheName  cache名称
     * @param key		  关键字
     * @param value      值
     */
    public void put(String cacheName,Object key, Object value) {  
        Cache cache = manager.getCache(cacheName);  
        Element element = new Element(key, value);  
        cache.put(element);  
    }  
  
    /**
     * 获取缓存
     * @param cacheName  cache名称
     * @param key        关键字
     * @return
     */
    public Object get(String cacheName, Object key) {  
        Cache cache = manager.getCache(cacheName);  
        Element element = cache.get(key);  
        return element == null ? null : element.getObjectValue();  
    }  
  
    /**
     * 删除缓存
     * @param cacheName cache名称
     * @param key       关键字
     */
    public void remove(String cacheName, String key) {  
        Cache cache = manager.getCache(cacheName);  
        cache.remove(key);  
    } 
    
    /**
     * 获取指定 cache 的甩的 key
     * @param cacheName
     * @return
     */
    public List getKeys(String cacheName){
    	Cache cache = manager.getCache(cacheName);
    	return cache.getKeys();
    }
    
    /**
     * 获取所有的 cache 名称
     * @return
     */
    public String[] getAllCacheNames(){
    	return manager.getCacheNames();
    }
    
    
    public Cache get(String cacheName) {  
        return manager.getCache(cacheName);  
    } 
    
	
	public static void main(String[] args){
		
		EhCacheUtil ehcacheUtil=EhCacheUtil.getInstance();
		
		ehcacheUtil.put("fenfentongCache", "test", "test_value");
		ehcacheUtil.put("fenfentongCache", "test1", "test1_value");
		System.out.println(ehcacheUtil.get("fenfentongCache", "test"));
		System.out.println(ehcacheUtil.get("fenfentongCache", "test1"));
		System.out.println(ehcacheUtil.getAllCacheNames());
//		ehcacheUtil.remove("fenfentongCache", "test");
		
		System.out.println(ehcacheUtil.get("fenfentongCache", "test")==null?"空":"不空");
	}
}
