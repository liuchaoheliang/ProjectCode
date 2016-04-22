package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.RecommendActivityTag;

public interface RecommendActivityTagMapper {
	
	/**
	 * 
	 * findByPage:(分页查询推荐活动标签).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:47:27
	 * @param rat
	 * @param page
	 * @return
	 *
	 */
	List<RecommendActivityTag> findByPage(@Param("rat")RecommendActivityTag rat, Page<RecommendActivityTag> page);
	
	
	/**
	 * 
	 * findActivityDetail:(根据单个活动标签详情).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:47:50
	 * @param rat
	 * @return
	 *
	 */
	RecommendActivityTag findActivityDetail(@Param("rat")RecommendActivityTag rat);
	
	
	/**
	 * 
	 * updateRecommendActivityTag:(更新推荐活动标签信息).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:48:10
	 * @param rat
	 * @return
	 *
	 */
	Boolean updateRecommendActivityTag(@Param("rat")RecommendActivityTag rat);
	
	
	/**
	 * 
	 * addRecommendActivityTag:(添加推荐活动标签信息).
	 *
	 * @author huangyihao
	 * 2015年10月26日 上午11:59:26
	 * @param recommend
	 * @return
	 *
	 */
	Long addRecommendActivityTag(RecommendActivityTag recommend);
	
	
	
	
	public int findActivityCount(@Param("type")String type, @Param("clientId")String clientId);
	
}
