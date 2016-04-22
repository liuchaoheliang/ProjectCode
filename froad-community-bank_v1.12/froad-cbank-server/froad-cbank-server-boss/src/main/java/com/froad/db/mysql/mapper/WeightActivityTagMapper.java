package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.MerchantWeightActivityTag;
import com.froad.po.OutletWeightActivityTag;
import com.froad.po.ProductWeightActivityTag;
import com.froad.po.WeightActivityTag;

public interface WeightActivityTagMapper {

	/**
	 * 
	 * findMerchantDetailByPage:(分页查询关联商户信息).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:48:29
	 * @param clientId
	 * @param activityId
	 * @param page
	 * @return
	 *
	 */
	List<MerchantWeightActivityTag> findMerchantDetailByPage(@Param("weightTag")WeightActivityTag weightTag, Page<MerchantWeightActivityTag> page);
	
	/**
	 * 
	 * findOutletDetailByPage:(分页查询关联门店信息).
	 *
	 * @author liuyanyun
	 * 2015年10月26日 下午13:45:29
	 * @param clientId
	 * @param activityId
	 * @param page
	 * @return
	 *
	 */
	List<OutletWeightActivityTag> findOutletDetailByPage(@Param("weightTag")WeightActivityTag weightTag, Page<OutletWeightActivityTag> page);
	
	
	/**
	 * 
	 * findProductDetailByPage:(分页查询关联商户信息).
	 *
	 * @author huangyihao
	 * 2015年10月28日 上午11:51:12
	 * @param weightTag
	 * @param page
	 * @return
	 *
	 */
	List<ProductWeightActivityTag> findProductDetailByPage(@Param("weightTag")WeightActivityTag weightTag, Page<ProductWeightActivityTag> page);
	
	
	/**
	 * 
	 * updateWeightActivityTag:(更新权重活动关联信息).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:48:40
	 * @param weight
	 * @return
	 *
	 */
	Boolean updateWeightActivityTag(@Param("weight")WeightActivityTag weight);
	
	
	/**
	 * 
	 * delWeightActivityTag:(删除权重活动关联信息).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:49:01
	 * @param id
	 * @param clientId
	 * @return
	 *
	 */
	Boolean delWeightActivityTag(@Param("id")Long id, @Param("clientId")String clientId);
	
	
	/**
	 * 
	 * addWeightActivityTag:(添加推荐活动权重关联信息).
	 *
	 * @author huangyihao
	 * 2015年10月26日 下午1:37:55
	 * @param weightTag
	 * @return
	 *
	 */
	Long addWeightActivityTag(WeightActivityTag weightTag);
	
	
	/**
	 * 
	 * deleteTempWeightInfoByActivityNo:(根据活动编号清空权重关联临时表数据).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午9:15:05
	 * @param activityId
	 * @param clientId
	 * @return
	 *
	 */
	Boolean deleteTempWeightInfoByActivityNo(@Param("activityId")Long activityId, @Param("activityNo")String activityNo, @Param("clientId")String clientId);
	
	
	/**
	 * 
	 * insertTempWeightInfoByActivityNo:(根据活动编号把主表数据加载到临时表中).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午9:24:14
	 * @param activityId
	 * @param clientId
	 * @return
	 *
	 */
	Boolean insertTempWeightInfoByActivityNo(@Param("activityId")Long activityId, @Param("activityNo")String activityNo, @Param("clientId")String clientId);
	
	
	/**
	 * 
	 * findWeightInfoByActivityNo:(根据活动编号查询权重关联主表信息).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午9:50:05
	 * @param activityId
	 * @param clientId
	 * @return
	 *
	 */
	List<WeightActivityTag> findWeightInfoByActivityNo(@Param("activityId")Long activityId, @Param("activityNo")String activityNo, @Param("clientId")String clientId);

	
	/**
	 * 
	 * insertWeightInfoByActivityNo:(根据活动编号把临时表数据加载到主表中).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午10:08:14
	 * @param activityId
	 * @param clientId
	 * @return
	 *
	 */
	Boolean insertWeightInfoByActivityNo(@Param("activityId")Long activityId, @Param("activityNo")String activityNo, @Param("clientId")String clientId);
	
	
	/**
	 * 
	 * deleteWeightInfoByActivityNo:(根据活动编号清空权重关联主表数据).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午10:21:32
	 * @param activityId
	 * @param clientId
	 * @return
	 *
	 */
	Boolean deleteWeightInfoByActivityNo(@Param("activityId")Long activityId, @Param("activityNo")String activityNo, @Param("clientId")String clientId);
	
	
	/**
	 * 
	 * addTempWeightActivityTag:(添加权重关联活动临时表信息).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午11:46:15
	 * @param weightTag
	 * @return
	 *
	 */
	Long addTempWeightActivityTag(WeightActivityTag weightTag);
	
	
	/**
	 * 
	 * findRelateActivityCount:(查询临时表中某个关联信息存不存在).
	 *
	 * @author huangyihao
	 * 2015年10月28日 上午9:28:22
	 * @param activityNo
	 * @param clientId
	 * @param elementId
	 * @return
	 *
	 */
	Integer findTempRelateActivityCount(@Param("activityId")Long activityId, @Param("activityNo")String activityNo, @Param("clientId")String clientId, @Param("elementId")String elementId);
	
	
	/**
	 * 
	 * updateActivityIdByActivityNo:(根据活动编号更新活动id[新增时使用]).
	 *
	 * @author huangyihao
	 * 2015年10月28日 上午11:00:11
	 * @param activityId
	 * @param activityNo
	 * @param clientId
	 * @return
	 *
	 */
	Boolean updateTempActivityIdByActivityNo(@Param("activityId")Long activityId, @Param("activityNo")String activityNo, @Param("clientId")String clientId);
	
	
	/**
	 * 
	 * queryRelateMerchants:(查询关联商户活动信息).
	 *
	 * @author huangyihao
	 * 2016年1月12日 下午1:51:46
	 * @param activityId
	 * @param clientId
	 * @param activityNo
	 * @return
	 *
	 */
	List<WeightActivityTag> queryRelateMerchants(@Param("activityId")Long activityId, @Param("clientId")String clientId, @Param("activityNo")String activityNo);
	
	
	/**
	 * 
	 * queryRelateOutlets:(查询关联门店活动信息).
	 *
	 * @author huangyihao
	 * 2016年1月12日 下午2:03:18
	 * @param activityId
	 * @param clientId
	 * @param activityNo
	 * @return
	 *
	 */
	List<WeightActivityTag> queryRelateOutlets(@Param("activityId")Long activityId, @Param("clientId")String clientId, @Param("activityNo")String activityNo);
	
	
	/**
	 * 
	 * queryRelateProducts:(查询关联商品活动信息).
	 *
	 * @author huangyihao
	 * 2016年1月12日 下午2:03:47
	 * @param activityId
	 * @param clientId
	 * @param activityNo
	 * @return
	 *
	 */
	List<WeightActivityTag> queryRelateProducts(@Param("activityId")Long activityId, @Param("clientId")String clientId, @Param("activityNo")String activityNo);
	
	
}
