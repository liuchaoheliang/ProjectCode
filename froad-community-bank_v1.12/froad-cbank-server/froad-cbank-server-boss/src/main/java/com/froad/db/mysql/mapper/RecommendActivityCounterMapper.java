package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.RecommendActivityCounter;

public interface RecommendActivityCounterMapper {
	
	
	List<RecommendActivityCounter> findByCondition(@Param("rat")RecommendActivityCounter rat);
	
	Boolean update(@Param("rat")RecommendActivityCounter rat);
	
	Long add(RecommendActivityCounter recommend);
	
}
