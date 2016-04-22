package com.froad.db.chongqing.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.cbank.persistent.entity.MerchantOutletBank;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface MerchantOutletBankMapper {


	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param ad
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(MerchantOutletBank mob);
	
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
	public List<MerchantOutletBank> selectOfPage(PageEntity<MerchantOutletBank> pageEntity);
	
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
	public MerchantOutletBank selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param ad
	  * @param @return    
	  * @return List<AdPosition>    
	  * @throws
	 */
	public List<MerchantOutletBank> selectByCondition(MerchantOutletBank mob);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param ad
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(MerchantOutletBank mob);

	/**
	 *  获取顶层数据
	  * @Title: selectByRoot
	  * @Description: TODO
	  * @author: share 2014年12月31日
	  * @modify: share 2014年12月31日
	  * @param @param clientId
	  * @param @return    
	  * @return List<MerchantOutletBank>    
	  * @throws
	 */
	public List<MerchantOutletBank> selectByRoot(Long clientId);

	/**
	 *  按银行机构查询商户门店机构信息
	  * @Title: selectByBankOrg
	  * @Description: TODO
	  * @author: share 2014年12月31日
	  * @modify: share 2014年12月31日
	  * @param @param clientId
	  * @param @param bankOrg
	  * @param @return    
	  * @return MerchantOutletBank    
	  * @throws
	 */
	public MerchantOutletBank selectByBankOrg(@Param("clientId")Long clientId, @Param("bankOrg")String bankOrg);
	
	
	/**
	 *  按门店id查询对应机构信息
	  * @Title: selectByMerchantOutletId
	  * @Description: TODO
	  * @author: ll 2015年1月12日
	  * @modify: ll 2015年1月12日
	  * @param @param clientAccessType
	  * @param @param clientVersion
	  * @param @param merchantOutletId
	  * @param @throws DBFroadException    
	  * @return MerchantOutletBankDto    
	  * @throws
	 */
	public MerchantOutletBank selectByMerchantOutletId(@Param("clientId")Long clientId, @Param("merchantOutletId")String merchantOutletId);
	
	
	
	public int findMerchantOutletBankByMerchantId(@Param("merchantId")long merchantId);
	/**
	 * 
	  * @Title: findMerchantOutletBankByProductId
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月28日
	  * @modify: Yaren Liang 2015年6月28日
	  * @param @param id
	  * @param @return    
	  * @return List<MerchantOutletBank>    
	  * @throws
	 */
	public List<MerchantOutletBank> findMerchantOutletBankByProductId(long id);
	
	/**
	 *  查询所有预售订单的机构和机构名称
	  * @Title: selectPresellOutlet
	  * @Description: TODO
	  * @author: share 2015年7月27日
	  * @modify: share 2015年7月27日
	  * @param @return    
	  * @return List<MerchantOutletBank>    
	  * @throws
	 */
	public List<MerchantOutletBank> selectPresellOutlet();
	
	
}
