package com.froad.db.chongqing.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.cbank.persistent.entity.MerchantOutlet;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface MerchantOutletMapper {


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
	public Long insert(MerchantOutlet outlet);
	
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
	public List<MerchantOutlet> selectOfPage(PageEntity<MerchantOutlet> pageEntity);
	
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
	public MerchantOutlet selectById(Long id);

	
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
	public List<MerchantOutlet> selectByCondition(MerchantOutlet outlet);
	
	
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
	public boolean updateById(MerchantOutlet outlet);
	
	/**
	 *  按产品ID获取门店信息
	  * @Title: selectByProductId
	  * @Description: TODO
	  * @author: share 2015年1月7日
	  * @modify: share 2015年1月7日
	  * @param @param client
	  * @param @param productId
	  * @param @return    
	  * @return List<MerchantOutlet>    
	  * @throws
	 */
	public List<MerchantOutlet> selectByProductId(@Param("clientId")Long client,@Param("productId")Long productId);

	/**
	 * 商户id 查询商户门店信息
	  * @Title: selectByMerchantId
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-1-10
	  * @modify: zhangxiaohua 2015-1-10
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @return    
	  * @return List<MerchantOutlet>    
	  * @throws
	 */
	public List<MerchantOutlet> selectByMerchantId(@Param("clientId")Long clientId,@Param("merchantId")Long merchantId);
	
	/**
	 * 附近搜索门店
	 * @Title: selectAccessoryOfPage 
	 * @Description: TODO
	 * @author vania
	 * @param outlet
	 * @return
	 * @return List<MerchantOutlet>    返回类型 
	 * @throws
	 */
	public List<MerchantOutlet> selectAccessoryOfPage(PageEntity<MerchantOutlet> pageEntity);
	
	/**
	 * 
	  * @Title: selectOutletByOultetId
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月29日
	  * @modify: Yaren Liang 2015年6月29日
	  * @param @return    
	  * @return List<MerchantOutlet>    
	  * @throws
	 */
	public MerchantOutlet selectOutletByOultetId(Long id);
}
