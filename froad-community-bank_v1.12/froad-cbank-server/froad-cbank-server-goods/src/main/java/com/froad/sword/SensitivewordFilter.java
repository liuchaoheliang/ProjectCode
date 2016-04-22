package com.froad.sword;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.ProductMapper;

import com.froad.logback.LogCvt;
import com.froad.po.SenseWord;

public class SensitivewordFilter {

	private static SqlSession sqlSession = null;
	private static ProductMapper productMapper = null;
	
	private static Map sensitiveWordMap = new HashMap();

	// 最小匹配规则
	public static int minMatchTYpe = 1;

	// 最大匹配规则
	public static int maxMatchType = 2;

	// 单例
	private static SensitivewordFilter inst = null;
	
	/**
	 * 构造函数，初始化敏感词库
	 */
	private SensitivewordFilter() {
	    sensitiveWordMap = initKeyWord();
	}

	/**
	 * 获取单例
	 * 
	 * @return
	 */
	public static SensitivewordFilter getInstance() {
		if (null == inst) {
			inst = new SensitivewordFilter();
		}
		return inst;
	}
	
	private static Set<String> getSensitiveWords(){
	    try{
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            productMapper = sqlSession.getMapper(ProductMapper.class);
            
            List<SenseWord> senseWords = productMapper.findSenseWords();
            Set<String> swSet = new HashSet<String>();
            if(senseWords!=null && senseWords.size()>0){
                for(SenseWord senseWord : senseWords){
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
	
	/**
	 * 初始化敏感字库
	 * 
	 * @return
	 */
	public static Map initKeyWord() {

		// 读取敏感词库
	    Set<String> wordSet = getSensitiveWords();
		// 将敏感词库加入到HashMap中
		return addSensitiveWordToHashMap(wordSet);
	}
	
	/**
	 * 
	 * 有新敏感词加进来.
	 * @param newWordSets
	 * @return void
	 */
	public static void addNewSensitiveWordsToMap(Set<String> newWordSets) {

        if(newWordSets!=null && newWordSets.size()>0){
            Map map = addSensitiveWordToHashMap(newWordSets);
            addSensitiveWordMap(map);
        }
    }
	
	public static void addSensitiveWordMap(Map newSensitiveWords) {
	    for(Object key : newSensitiveWords.keySet()){
	        sensitiveWordMap.put(key, newSensitiveWords.get(key));
	    }
    }

	

	/**
	 * 判断文字是否包含敏感字符
	 * 
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public boolean isContaintSensitiveWord(String txt, int matchType) {
		boolean flag = false;
		for (int i = 0; i < txt.length(); i++) {

			// 判断是否包含敏感字符
			int matchFlag = this.CheckSensitiveWord(txt, i, matchType);

			// 大于0存在，返回true
			if (matchFlag > 0) {
				flag = true;
			}
		}
		return flag;
	}

	/**
	 * 获取文字中的敏感词
	 * 
	 * @param txt
	 * @param matchType
	 * @return
	 */
	public Set<String> getSensitiveWord(String txt, int matchType) {
		Set<String> sensitiveWordList = new HashSet<String>();

		for (int i = 0; i < txt.length(); i++) {

			// 判断是否包含敏感字符
			int length = CheckSensitiveWord(txt, i, matchType);

			// 存在,加入list中
			if (length > 0) {
				sensitiveWordList.add(txt.substring(i, i + length));

				// 减1的原因，是因为for会自增
				i = i + length - 1;
			}
		}

		return sensitiveWordList;
	}

	/**
	 * 替换敏感字字符
	 * 
	 * @param txt
	 * @param matchType
	 * @param replaceChar
	 * @return
	 */
	public String replaceSensitiveWord(String txt, int matchType,
			String replaceChar) {

		String resultTxt = txt;

		// 获取所有的敏感词
		Set<String> set = getSensitiveWord(txt, matchType);
		Iterator<String> iterator = set.iterator();
		String word = null;
		String replaceString = null;
		while (iterator.hasNext()) {
			word = iterator.next();
			replaceString = getReplaceChars(replaceChar, word.length());
			resultTxt = resultTxt.replaceAll(word, replaceString);
		}

		return resultTxt;
	}

	/**
	 * 获取替换字符串
	 * 
	 * @param replaceChar
	 * @param length
	 * @return
	 */
	private String getReplaceChars(String replaceChar, int length) {
		String resultReplace = replaceChar;
		for (int i = 1; i < length; i++) {
			resultReplace += replaceChar;
		}

		return resultReplace;
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
	public int CheckSensitiveWord(String txt, int beginIndex, int matchType) {

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

				// 如果为最后一个匹配规则,结束循环，返回匹配标识数
				if ("1".equals(nowMap.get("isEnd"))) {

					// 结束标志位为true
					flag = true;

					// 最小规则，直接返回,最大规则还需继续查找
					if (SensitivewordFilter.minMatchTYpe == matchType) {
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
		}
		return matchFlag;
	}
	
	public static String filterSenseWord(String content){
	    if(inst!=null){
	        return inst.replaceSensitiveWord(content, 1, "*");
	    } else{
	        SensitivewordFilter filter = getInstance();
	        return filter.replaceSensitiveWord(content, 1, "*");
	    }
	}

	public static void main(String[] args) {

//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
//
//		SensitivewordFilter filter = SensitivewordFilter.getInstance();
//		String txt = "太多的伤感情怀也许只局限于饲养基地 荧幕中的情节，主人公尝试着去用某种方式渐渐的很潇洒地释自杀指南怀那些自己经历的伤感。"
//				+ "然后法轮功 我们的扮演的角色就是跟随着主人公的喜红客联盟 怒哀乐而过于牵强的把自己的情感也附加于银幕情节中，然后感动就流泪，"
//				+ "难过就躺在某一个人的怀里尽情的阐述心扉或者手机卡复制器一个人一杯红酒一部电影在夜三级片 深人静的晚上，关上电话静静的发呆着。";
//
//		Set<String> newWordSets = new HashSet<String>();
//        newWordSets.add("情感");
//        SensitiveWordInit.addNewSensitiveWordsToMap(newWordSets);
//        
//		System.out.println(sdf.format(new Date()));
//		String hou = filter.replaceSensitiveWord(txt, 1, "*");
//		System.out.println(sdf.format(new Date()));
//		System.out.println("替换前的文字为：" + txt);
//		System.out.println("替换后的文字为：" + hou);
	}
}
