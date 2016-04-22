package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.InputRelateMerchantActivityPo;
import com.froad.po.MerchantWeightActivityTag;
import com.froad.po.RecommendActivityTag;
import com.froad.po.RelateMerchantActivityPo;
import com.froad.po.WeightActivityTag;

public interface MerchantActivityTagLogic {
	
	/**
	 * 
	 * findMerchantTagByPage:(商户推荐活动列表).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:41:59
	 * @param recommend
	 * @param page
	 * @return
	 * @throws Exception
	 *
	 */
	Page<RecommendActivityTag> findMerchantTagByPage(RecommendActivityTag recommend, Page<RecommendActivityTag> page) throws Exception;
	
	
	/**
	 * 
	 * findMerchantTagDetail:(商户推荐活动详情).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:42:13
	 * @param recommend
	 * @param page
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	RecommendActivityTag findMerchantTagDetail(Long id, String clientId, String activityNo, String operator) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * findRelateMerchantInfoByPage:(关联商户信息分页查询).
	 *
	 * @author huangyihao
	 * 2015年10月26日 上午10:13:25
	 * @param weightTag
	 * @param page
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Page<MerchantWeightActivityTag> findRelateMerchantInfoByPage(WeightActivityTag weightTag, Page<MerchantWeightActivityTag> page) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * enableMerchantRecommendActivityTag:(启用/禁用商户推荐活动标签).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:42:27
	 * @param recommend
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean enableMerchantRecommendActivityTag(RecommendActivityTag recommend) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * adjustMerchantWeight:(调整关联商户权重).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:42:41
	 * @param weight
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean adjustMerchantWeight(WeightActivityTag weight) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * deleteRelateMerchant:(删除关联商户).
	 *
	 * @author huangyihao
	 * 2015年10月23日 下午4:43:02
	 * @param id
	 * @param weight
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean deleteRelateMerchant(WeightActivityTag weight) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * addMerchantActivityTag:(添加商户推荐活动).
	 *
	 * @author huangyihao
	 * 2015年10月26日 上午11:47:33
	 * @param recommend
	 * @return
	 *
	 */
	Boolean addMerchantActivityTag(RecommendActivityTag recommend) throws Exception;
	
	
	/**
	 * 
	 * updateMerchantActivityTag:(更新商户推荐活动标签信息).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午9:41:10
	 * @param recommend
	 * @return
	 * @throws Exception
	 *
	 */
	Boolean updateMerchantActivityTag(RecommendActivityTag recommend) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * relateMerchantInfo:(关联商户信息).
	 *
	 * @author huangyihao
	 * 2015年10月27日 上午10:54:46
	 * @param po
	 * @return
	 * @throws Exception
	 *
	 */
	Boolean relateMerchantInfo(RelateMerchantActivityPo po) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * queryMerchantNameByLicense:(根据营业执照查询商户名称).
	 *
	 * @author huangyihao
	 * 2015年10月27日 下午4:29:32
	 * @param license
	 * @return
	 *
	 */
	String queryMerchantNameByLicense(String clientId, String license) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * inputrelateMerchantInfo:(导入关联商户信息).
	 *
	 * @author huangyihao
	 * 2015年10月27日 下午2:16:25
	 * @param infos
	 * @param clientId
	 * @param activityNo
	 * @param operator
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean inputrelateMerchantInfo(List<InputRelateMerchantActivityPo> infos, String clientId, Long activityId, String activityNo, String operator) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * findAllRelateMerchants:(查询所有关联商户活动标签信息).
	 *
	 * @author huangyihao
	 * 2016年1月12日 上午11:48:35
	 * @param activityId
	 * @param clientId
	 * @param activityNo
	 * @return
	 *
	 */
	List<WeightActivityTag> findAllRelateMerchants(Long activityId, String clientId, String activityNo) throws Exception;
}
