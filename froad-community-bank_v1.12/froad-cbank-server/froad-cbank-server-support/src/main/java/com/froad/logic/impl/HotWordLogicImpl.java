package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;


import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.HotWordMapper;
import com.froad.db.redis.SupportRedis;
import com.froad.db.redis.SupportsRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.logic.HotWordLogic;
import com.froad.po.HotWord;
import com.froad.po.HotWordDetaill;
import com.froad.po.mongo.ProductDetail;
import com.froad.thread.FroadThreadPool;
import com.froad.util.BeanUtil;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Support;

public class HotWordLogicImpl implements HotWordLogic{
    private static RedisManager redisManager = new RedisManager();
	@Override
	public Page<HotWord> findHotWordByPage(Page<HotWord> page,
			HotWord hotWord,String orderSort,int index) {
		List<HotWord> result = new ArrayList<HotWord>(); 
		Page<HotWord> hotPage=new Page<HotWord>();
		SqlSession sqlSession = null;
		HotWordMapper hotWordMapper = null;
		try { 
		      /**********************redis的key值**********************/
            final String categoryType = ObjectUtils.toString(hotWord.getCategoryType(), ""); // 分类id product.getCategoryId();
            final String type = ObjectUtils.toString(hotWord.getType(), "");  //类型
            final String areaId = ObjectUtils.toString(hotWord.getAreaId(), "");//地区id
            final String clientId = ObjectUtils.toString(hotWord.getClientId(), "");//客户端id
            final String key = RedisKeyUtil.cbbank_hotword_h5_index_client_id_areaId_type_categoryType_sort(index, clientId, areaId, type, categoryType, orderSort);
            final String lockKey = RedisKeyUtil.cbbank_hotword_h5_lock_index_client_id_areaId_type_categoryType_sort(index, clientId, areaId, type, categoryType, orderSort);
            int  time = 30;
            long lockResult = SupportsRedis.setLock(lockKey, time);// 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
            if(lockResult == 1) {
			      /**********************操作MySQL数据库**********************/
			     sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			     hotWordMapper = sqlSession.getMapper(HotWordMapper.class);
			     result = hotWordMapper.findByPage(page, hotWord); 
			     Integer totalCount=hotWordMapper.findByCount(hotWord);
			     hotPage.setResultsContent(result);
                 hotPage.setPageCount(page.getPageCount());
                 hotPage.setTotalCount(totalCount);// 设置总页数
                 hotPage.setPageSize(page.getPageSize());
                 hotPage.setPageNumber(page.getPageNumber());
			     this.setH5HotWordCache(page, index, hotWord, key, time);
            }else{
    			/**********************操作Redis缓存**********************/
                LogCvt.info("个人h5查询热词key[" + key + "非首次查询, 先看redis有没有数据  如有则返回  无则查mysql");
                long count = redisManager.llen(key); // 获取缓存key中有多少条数据
                //count >= page.getPageNumber() * page.getPageSize()
                if(count >0) { // 缓存中的数据  足够本次查询的数据
                    Integer pageSize = page.getPageSize();
                    pageSize = null == pageSize ? 10 : pageSize; // 默认10条                  
                    int start = (page.getPageNumber() - 1) * page.getPageSize();
                    int end = (page.getPageNumber() - 1) * page.getPageSize() + page.getPageSize();
                    List<HotWordDetaill> pds = SupportRedis.get_cbbank_hotword_h5_redis(key, start, end - 1);
                    hotPage.setPageCount(page.getPageCount()+1);
                    hotPage.setTotalCount((int) count);// 设置总页数
                    hotPage.setPageNumber(page.getPageNumber());
                    hotPage.setPageSize(page.getPageSize());
                    List<HotWord> list = (List) BeanUtil.copyProperties(HotWord.class, pds);
                    hotPage.setResultsContent(list);
                } 
            }
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询hotWord失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return hotPage; 
	}
    /** 
     * @Title: setH5HotWordCache 向缓存里放数据
     * @version 1.0
     * @param page
     * @param index
     * @param HotWord
     * @param key
     * @param time
     * @return void
     */
    private void setH5HotWordCache(final Page<HotWord> page,final int index, final HotWord hotWord , final String key, final int time) {
        FroadThreadPool.execute(new Runnable() { // 异步塞缓存
            @Override
            public void run() {
            	int queryDataCount = 100;
        		SqlSession sqlSession = null;
        		HotWordMapper hotWordMapper = null;
        		List<HotWord> result = new ArrayList<HotWord>(); 
        	    long startTime = System.currentTimeMillis();
            	try{
                 page.setPageNumber(1);
            	 page.setPageSize(queryDataCount);
			     sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			     hotWordMapper = sqlSession.getMapper(HotWordMapper.class);
			     result = hotWordMapper.findByPage(page, hotWord); 

                LogCvt.info("个人h5查询热词异步[ThreadPool]设置redis缓存,currentPage" + page.getPageNumber() + ",pageSize" + page.getPageSize() + "数据[开始...]时间戳=" + startTime);
                    if(result!=null && result.size()>0){
                    	List<HotWordDetaill> list = (List) BeanUtil.copyProperties(HotWordDetaill.class, result);
                    	SupportRedis.set_cbbank_hotword_h5_redis(key, list);
                    }
                } catch (Exception e) {
                    LogCvt.error("个人h5查询热词异步[ThreadPool]设置redis缓存失败, 原因:" + e.getMessage(), e);
                } finally {
                    long endTime = System.currentTimeMillis();
                    LogCvt.info("个人h5查询热词异步[ThreadPool]设置redis缓存,currentPage" + page.getPageNumber() + ",pageSize" + page.getPageSize() + "数据数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
        			if(null != sqlSession)  
        				sqlSession.close();  
                }
            }
        });
    }
}
