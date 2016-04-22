/**
 * Project Name:froad-cbank-server-boss
 * File Name:ProductActivityTagLogic.java
 * Package Name:com.froad.logic
 * Date:2015年10月28日上午11:26:27
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.logic;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadBusinessException;
import com.froad.po.InputRelateProductActivityPo;
import com.froad.po.ProductWeightActivityTag;
import com.froad.po.RecommendActivityTag;
import com.froad.po.RelateProductActivityPo;
import com.froad.po.WeightActivityTag;

/**
 * ClassName:ProductActivityTagLogic
 * Reason:	 TODO ADD REASON.
 * Date:     2015年10月28日 上午11:26:27
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public interface ProductActivityTagLogic {

	/**
	 * 
	 * findProductTagByPage:(商品推荐活动列表).
	 *
	 * @author huangyihao
	 * 2015年10月28日 上午11:27:02
	 * @param recommend
	 * @param page
	 * @return
	 * @throws Exception
	 *
	 */
	Page<RecommendActivityTag> findProductTagByPage(RecommendActivityTag recommend, Page<RecommendActivityTag> page) throws Exception;
	
	
	/**
	 * 
	 * findProductTagDetail:(商品推荐活动详情).
	 *
	 * @author huangyihao
	 * 2015年10月28日 上午11:28:04
	 * @param id
	 * @param clientId
	 * @param activityNo
	 * @param operator
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	RecommendActivityTag findProductTagDetail(Long id, String clientId, String activityNo, String operator) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * findRelateProductInfoByPage:(关联商品信息分页查询).
	 *
	 * @author huangyihao
	 * 2015年10月28日 上午11:32:52
	 * @param weightTag
	 * @param page
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Page<ProductWeightActivityTag> findRelateProductInfoByPage(WeightActivityTag weightTag, Page<ProductWeightActivityTag> page) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * enableProductRecommendActivityTag:(启用/禁用商品推荐活动标签).
	 *
	 * @author huangyihao
	 * 2015年10月28日 下午2:56:09
	 * @param recommend
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean enableProductRecommendActivityTag(RecommendActivityTag recommend) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * adjustProductWeight:(调整关联商品权重).
	 *
	 * @author huangyihao
	 * 2015年10月29日 上午9:49:21
	 * @param weight
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean adjustProductWeight(WeightActivityTag weight) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * deleteRelateProduct:(删除关联商品).
	 *
	 * @author huangyihao
	 * 2015年10月29日 上午9:49:57
	 * @param weight
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean deleteRelateProduct(WeightActivityTag weight) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * addProductActivityTag:(添加商品推荐活动).
	 *
	 * @author huangyihao
	 * 2015年10月29日 上午9:50:43
	 * @param recommend
	 * @return
	 * @throws Exception
	 *
	 */
	Boolean addProductActivityTag(RecommendActivityTag recommend) throws Exception;
	
	
	/**
	 * 
	 * updateProductActivityTag:(更新商品推荐活动标签信息).
	 *
	 * @author huangyihao
	 * 2015年10月29日 上午9:51:26
	 * @param recommend
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean updateProductActivityTag(RecommendActivityTag recommend) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * relateProductInfo:(关联商品信息).
	 *
	 * @author huangyihao
	 * 2015年10月29日 上午10:46:54
	 * @param po
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean relateProductInfo(RelateProductActivityPo po) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * queryProductNameByProductId:(根据商品id查询商品名称).
	 *
	 * @author huangyihao
	 * 2015年10月29日 上午10:53:42
	 * @param clientId
	 * @param productId
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	String queryProductNameByProductId(String clientId, String productId) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * inputrelateProductInfo:(导入关联商品信息).
	 *
	 * @author huangyihao
	 * 2015年10月29日 上午11:44:01
	 * @param infos
	 * @param clientId
	 * @param activityId
	 * @param activityNo
	 * @param operator
	 * @return
	 * @throws FroadBusinessException
	 * @throws Exception
	 *
	 */
	Boolean inputrelateProductInfo(List<InputRelateProductActivityPo> infos, String clientId, Long activityId, String activityNo, String operator) throws FroadBusinessException, Exception;
	
	
	/**
	 * 
	 * findAllRelateProducts:(查询所有关联商品活动标签信息).
	 *
	 * @author huangyihao
	 * 2016年1月12日 上午11:49:47
	 * @param activityId
	 * @param clientId
	 * @param activityNo
	 * @return
	 *
	 */
	List<WeightActivityTag> findAllRelateProducts(Long activityId, String clientId, String activityNo) throws Exception;
}
