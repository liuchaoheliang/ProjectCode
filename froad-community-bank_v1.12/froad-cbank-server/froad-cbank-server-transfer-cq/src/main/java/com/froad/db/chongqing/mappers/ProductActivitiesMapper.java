package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.ProductActivities;

/**
 * 商品活动
 * 
 * @author luofan
 * @version $Id: ProductActivitiesMapper.java, v 0.1 2015年1月20日 下午2:4:21 Exp $
 */
public interface ProductActivitiesMapper {

	/**
     * 新增
     * @author luofan
     * @version $Id: 2015年1月20日 下午2:4:21 Exp $
     * @param productActivities
     * @return Long
     */
	public Long insert(ProductActivities productActivities);
	
	/**
     * 删除
     * @author luofan
     * @version $Id: 2015年1月20日 下午2:4:21 Exp $
     * @param productActivities
     * @return Long
     */
	public Long delete(ProductActivities productActivities);
	
	/**
     * 查询
     * @author luofan
     * @version $Id: 2015年1月20日 下午2:4:21 Exp $
     * @param productActivities
     * @return List<ProductActivities>
     */
	public List<ProductActivities> selectByCondition(ProductActivities productActivities);
}
