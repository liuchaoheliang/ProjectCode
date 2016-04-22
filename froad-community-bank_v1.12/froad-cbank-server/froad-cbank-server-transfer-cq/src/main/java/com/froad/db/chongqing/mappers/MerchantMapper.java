package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.Merchant;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface MerchantMapper {


	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchant
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(Merchant merchant);
	
	/**
	 *  基础分页查询
	  * @Title: selectOfPage
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param pageEntity
	  * @param @return    
	  * @return List<AdPosition>    
	  * @throws
	 */
	public List<Merchant> selectOfPage(PageEntity<Merchant> pageEntity);
	
	/**
	 *  通过数据主键获取数据
	  * @Title: selectById
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param id
	  * @param @return    
	  * @return AdPosition    
	  * @throws
	 */
	public Merchant selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchant
	  * @param @return    
	  * @return List<AdPosition>    
	  * @throws
	 */
	public List<Merchant> selectByCondition(Merchant merchant);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchant
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(Merchant merchant);
	
	/**
	 *  按商户用户ID查询商户信息
	  * @Title: selectByMerchantUserId
	  * @Description: TODO
	  * @author: share 2015年1月5日
	  * @modify: share 2015年1月5日
	  * @param @param userId
	  * @param @return    
	  * @return Merchant    
	  * @throws
	 */
	public Merchant selectByMerchantUserId(Long userId);

	/**
	 * 新增商户审核接口
	  * @Title: updateMerchantById
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-1-9
	  * @modify: zhangxiaohua 2015-1-9
	  * @param @param merchant
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateByAudit(Merchant merchant);
	/**
	 * 新增商户待审核数量接口
	  * @Title: selectofMerchantNumber
	  * @Description: TODO
	  * @author: Yankee Liang 2015年1月10日
	  * @modify: Yankee Liang 2015年1月10日
	  * @param @return    
	  * @return Integer    
	  * @throws
	 */
	public Integer selectofMerchantNumber(PageEntity<Merchant> pageEntity);
	/**
	 * 新增商户解约接口
	  * @Title: updateByCancel
	  * @Description: TODO
	  * @author: Yankee Liang 2015年1月19日
	  * @modify: Yankee Liang 2015年1月19日
	  * @param @param merchant
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateByCancel(Merchant merchant);

	/**
	 * 查询客户端对应的方付通收款商户
	* <p>Function: selectFroadCollect</p>
	* <p>Description: </p>
	* @author zhaoxy@thankjava.com
	* @date 2015年1月20日 下午1:58:26
	* @version 1.0
	* @param clientId
	* @return
	 */
	public Merchant selectFroadCollect(Long clientId);
}
