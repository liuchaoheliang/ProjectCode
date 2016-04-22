package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.BossSenseWords;

public interface SenseWordsMapper {
	 /**
     * 增加 BossSenseWords
     * @param bossSenseWords
     * @return Long    主键ID
     */
	public Long addBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 批量增加 BossSenseWords
     * @param List<BossSenseWords>
     * @return Boolean    是否成功
     */
	public Boolean addBossSenseWordsByBatch(List<BossSenseWords> bossSenseWordsList);



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
     * 查询一个 BossSenseWords
     * @param bossSenseWords
     * @return BossSenseWords    返回结果
     */
	public BossSenseWords findBossSenseWordsById(Long id);



    /**
     * 查询 BossSenseWords
     * @param bossSenseWords
     * @return List<BossSenseWords>    返回结果集
     */
	public List<BossSenseWords> findBossSenseWords(BossSenseWords bossSenseWords);



    /**
     * 分页查询 BossSenseWords
     * @param page 
     * @param bossSenseWords
     * @return List<BossSenseWords>    返回分页查询结果集
     */
	public List<BossSenseWords> findByPage(Page page, @Param("bossSenseWords")BossSenseWords bossSenseWords);

	/**
     * 敏感词查询
     * @return List<SenseWord>
     */
    public List<BossSenseWords> findSenseWords();
}
