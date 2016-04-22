package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.HotWord;
import com.froad.po.HotWordDetaill;

public interface HotWordMapper {
    /**
     * 分页查询 HotWord
     * @param page 
     * @param hotWord
     * @return List<HotWord>    返回分页查询结果集
     */
	public List<HotWord> findByPage(Page page, @Param("hotWord")HotWord hotWord);
    /**
     * 分页查询 HotWord
     * @param page 
     * @param hotWord
     * @return List<HotWord>    返回分页查询结果集
     */
	public Integer findByCount(@Param("hotWord")HotWord hotWord);
}
