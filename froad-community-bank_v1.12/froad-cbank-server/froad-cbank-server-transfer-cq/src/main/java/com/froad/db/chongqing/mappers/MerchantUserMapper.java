package com.froad.db.chongqing.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.cbank.persistent.entity.MerchantUser;
import com.froad.cbank.persistent.entity.base.PageEntity;
import com.froad.db.chonggou.entity.MerchantUserCG;

public interface MerchantUserMapper {


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
	public Long insert(MerchantUser user);
	
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
	public List<MerchantUser> selectOfPage(PageEntity<MerchantUser> pageEntity);
	
	/**
	 *  基础分页查询
	  * @Title: selectMerchantUserOfPageByCondition
	  * @Description: TODO
	  * @author: luofan 2015年1月14日
	  * @param @param pageEntity
	  * @param @return    
	  * @return List<MerchantUser>    
	  * @throws
	 */
	public List<MerchantUser> selectMerchantUserOfPageByCondition(PageEntity<MerchantUser> pageEntity);
	
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
	public MerchantUser selectById(Long id);

	
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
	public List<MerchantUser> selectByCondition(MerchantUser user);
	
	
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
	public boolean updateById(MerchantUser user);

	/**
	 *  按用户名称查找
	  * @Title: selectByUserName
	  * @Description: TODO
	  * @author: share 2015年1月5日
	  * @modify: share 2015年1月5日
	  * @param @param clientId
	  * @param @param userName
	  * @param @return    
	  * @return MerchantUser    
	  * @throws
	 */
	public MerchantUser selectByUserName(@Param("clientId")Long clientId, @Param("userName")String userName);
	
	public MerchantUser selectMerchantUserById(Long id);
}

