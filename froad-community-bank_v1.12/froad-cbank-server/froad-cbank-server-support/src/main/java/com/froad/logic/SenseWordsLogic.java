package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossSenseWords;
import com.froad.po.Result;

/**
 * 
 * @Title: SenseWordsLogic.java
 * @Description 敏感词 业务逻辑
 * @see: TODO 
 * @author lf
 * @date 2015年5月6日
 */
public interface SenseWordsLogic {
	 /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return Long    主键ID
     */
	public Long addBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 删除 BossSenseWords
     * @param bossSenseWords
     * @return Boolean    是否成功
     */
	public Boolean deleteBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 修改 BossSenseWords
     * @param bossSenseWords
     * @return Boolean    是否成功
     */
	public Boolean updateBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWords>    结果集合 
     */
	public List<BossSenseWords> findBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 分页查询 BossSenseWords
     * @param page
     * @param bossSenseWords
     * @return Page<BossSenseWords>    结果集合 
     */
	public Page<BossSenseWords> findBossSenseWordsByPage(Page<BossSenseWords> page, BossSenseWords bossSenseWords);

	/**
     * 判断文字是否包含敏感字符
     * @param word
     * @return bool
     */
    public Result isContaintSensitiveWord(String word);
}
