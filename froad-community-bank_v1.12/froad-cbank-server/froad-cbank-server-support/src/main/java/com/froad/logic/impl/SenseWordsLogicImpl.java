package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.BossSenseWordsMapper;
import com.froad.db.mysql.mapper.SenseWordsMapper;
import com.froad.db.redis.impl.RedisManager;
import com.froad.logback.LogCvt;
import com.froad.logic.ProductSenseService;
import com.froad.logic.SenseWordsLogic;
import com.froad.po.BossSenseWords;
import com.froad.po.Result;
import com.froad.po.SenseWord;
import com.froad.util.RedisKeyUtil;

/**
 * 
 * <p>@Title: SenseWordsLogicImpl.java</p>
 * <p>Description: 敏感词 </p> 
 * @author lf 
 * @version 1.0
 * @created 2015年5月6日
 */
public class SenseWordsLogicImpl implements SenseWordsLogic{

	private static Map sensitiveWordMap = new HashMap();
	
	// 最小匹配规则
	public static int MIN_MATCH_TYPE = 1;

	// 最大匹配规则
	public static int MAX_MATCH_TYPE = 2;
	
	static{
		sensitiveWordMap = initKeyWord();
	}
	 /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return Long    主键ID
     */
	@Override
	public Long addBossSenseWords(BossSenseWords bossSenseWords) {

		Long result = null; 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			if (bossSenseWordsMapper.addBossSenseWords(bossSenseWords) > -1) { 
				result = bossSenseWords.getId(); 
			}
			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			sqlSession.commit(true); 
			onRefresh();//重新刷新
			/**********************通知商品模块**********************/
			if(bossSenseWords.getIsEnable()){
			    RedisManager redis = new RedisManager();
			    Set<String> goodHosts = redis.getSet(RedisKeyUtil.cbank_good_ip_port());
			
			    for(String str : goodHosts){
			    	String strArr[] = str.split(":");
			        TSocket transport = new TSocket(strArr[0], Integer.parseInt(strArr[1]));
			        TBinaryProtocol protocol = new TBinaryProtocol(transport);
			        TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"ProductService");
			
			        ProductSenseService.Client service = new ProductSenseService.Client(mp);
			        transport.open();
			
			        service.modifySenseWord(1, bossSenseWords.getWord());
			        transport.close();
			    }
			}
		} catch (Exception e) { 
			sqlSession.rollback(true);  
		result = null; 
			LogCvt.error("添加BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 删除 BossSenseWords
     * @param bossSenseWords
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteBossSenseWords(BossSenseWords bossSenseWords) {

		Boolean result = null; 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			bossSenseWordsMapper.deleteBossSenseWords(bossSenseWords); 
			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			sqlSession.commit(true);
			onRefresh();
			result = true;

		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("删除BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 修改 BossSenseWords
     * @param bossSenseWords
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateBossSenseWords(BossSenseWords bossSenseWords) {

		Boolean result = true; 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			bossSenseWordsMapper.updateBossSenseWords(bossSenseWords); 
			/**********************操作Mongodb数据库**********************/

			/**********************操作Redis缓存**********************/
			sqlSession.commit(true);
			onRefresh();
			result = true;

		} catch (Exception e) { 
			sqlSession.rollback(true);  
			result = false; 
			LogCvt.error("删除BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWords>    结果集合 
     */
	@Override
	public List<BossSenseWords> findBossSenseWords(BossSenseWords bossSenseWords) {

		List<BossSenseWords> result = new ArrayList<BossSenseWords>(); 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			result = bossSenseWordsMapper.findBossSenseWords(bossSenseWords); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
		result = null; 
			LogCvt.error("查询BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 分页查询 BossSenseWords
     * @param page
     * @param bossSenseWords
     * @return Page<BossSenseWords>    结果集合 
     */
	@Override
	public Page<BossSenseWords> findBossSenseWordsByPage(Page<BossSenseWords> page, BossSenseWords bossSenseWords) {

		List<BossSenseWords> result = new ArrayList<BossSenseWords>(); 
		SqlSession sqlSession = null;
		BossSenseWordsMapper bossSenseWordsMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bossSenseWordsMapper = sqlSession.getMapper(BossSenseWordsMapper.class);

			result = bossSenseWordsMapper.findByPage(page, bossSenseWords); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
			// sqlSession.rollback(true);  
			LogCvt.error("分页查询BossSenseWords失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}

	/**
     * 判断文字是否包含敏感字符
     * @param word
     * @return bool
     */
	@Override
	public Result isContaintSensitiveWord(String word) {
		Result result=null;
		boolean flag = false;
		String resultCode="9999";
		StringBuffer sb=new StringBuffer();
		for (int i = 0; i < word.length(); i++) {
			// 判断是否包含敏感字符
			String senWord = CheckSensitiveWord(word, i, MAX_MATCH_TYPE);
			//只记录敏感词
			if(!senWord.equals("")){
				sb.append(senWord).append(",");
			}
//			// 大于0存在，返回true
//			if (matchFlag > 0) {
//				flag = true;
//			}
		}

		if(sb.length()>0){//不是词语则返回没有敏感词
			result=new Result(resultCode,sb.toString().substring(0, sb.toString().length()-1));
			LogCvt.info("敏感词为:"+sb.toString());
		}else{
			result=new Result("0000","");
		}
		
		return result;
	}

	/**
	 * 检查文字中是否包含敏感字符，检查规则如下：<br>
	 * 如果存在，则返回敏感词字符的长度，不存在返回0
	 * 
	 * @param txt
	 * @param beginIndex
	 * @param matchType
	 * @return
	 */
	private static String CheckSensitiveWord(String txt, int beginIndex, int matchType) {
         //敏感词返回码,如果是9999则代表不含敏感词
         StringBuffer resultDesc= new StringBuffer();
		// 敏感词结束标识位：用于敏感词只有1位的情况
		boolean flag = false;

		// 匹配标识数默认为0
		int matchFlag = 0;
		Map nowMap = sensitiveWordMap;
		for (int i = beginIndex; i < txt.length(); i++) {
			char word = txt.charAt(i);

			// 获取指定key
			nowMap = (Map) nowMap.get(word);

			// 存在，则判断是否为最后一个
			if (nowMap != null) {

				// 找到相应key，匹配标识+1
				matchFlag++;
                
				//添加敏感词
				resultDesc.append(word);
				
				// 如果为最后一个匹配规则,结束循环，返回匹配标识数
				if ("1".equals(nowMap.get("isEnd"))) {

					// 结束标志位为true
					flag = true;

					// 最小规则，直接返回,最大规则还需继续查找
					if (MIN_MATCH_TYPE == matchType) {
						break;
					}
				}
			}

			// 不存在，直接返回
			else {
				break;
			}
		}

		// 长度必须大于等于1，为词
		if (matchFlag < 2 || !flag) {
			matchFlag = 0;
			resultDesc.delete(0, resultDesc.length());//不是词语则清空
		}
//		if(resultDesc.length()>1){//不是词语则返回没有敏感词
//			result=new Result("0000",resultDesc.toString());
//		}else{
//			result=new Result(resultCode,resultDesc.toString());
//		}
		return resultDesc.toString();
	}
	
	/**
	 * 初始化敏感字库
	 * 
	 * @return
	 */
	private static Map initKeyWord() {

		// 读取敏感词库
	    Set<String> wordSet = getSensitiveWords();
		// 将敏感词库加入到HashMap中
		return addSensitiveWordToHashMap(wordSet);
	}
	
	private static Set<String> getSensitiveWords(){
		SqlSession sqlSession = null;
		SenseWordsMapper senseWordsMapper = null;
		try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            senseWordsMapper = sqlSession.getMapper(SenseWordsMapper.class);
            
            List<BossSenseWords> senseWords = senseWordsMapper.findSenseWords();
            Set<String> swSet = new HashSet<String>();
            if(senseWords!=null && senseWords.size()>0){
                for(BossSenseWords senseWord : senseWords){
                    swSet.add(senseWord.getWord());
                }
            }
            return swSet; 
        } catch (Exception e) { 
            LogCvt.error("查询敏感词失败，原因:" + e.getMessage()); 
        } finally { 
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        } 
        return null;
	}
	
	private static Map addSensitiveWordToHashMap(Set<String> wordSet) {

	    if(wordSet==null){
	        return new HashMap();
	    }
		// 初始化敏感词容器，减少扩容操作
		Map wordMap = new HashMap(wordSet.size());

		for (String word : wordSet) {
			Map nowMap = wordMap;
			for (int i = 0; i < word.length(); i++) {

				// 转换成char型
				char keyChar = word.charAt(i);

				// 获取
				Object tempMap = nowMap.get(keyChar);

				// 如果存在该key，直接赋值
				if (tempMap != null) {
					nowMap = (Map) tempMap;
				}

				// 不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
				else {

					// 设置标志位
					Map<String, String> newMap = new HashMap<String, String>();
					newMap.put("isEnd", "0");

					// 添加到集合
					nowMap.put(keyChar, newMap);
					nowMap = newMap;
				}

				// 最后一个
				if (i == word.length() - 1) {
					nowMap.put("isEnd", "1");
				}
			}
		}

		return wordMap;
	}
	public void onRefresh(){
		sensitiveWordMap = initKeyWord();
	}
}
