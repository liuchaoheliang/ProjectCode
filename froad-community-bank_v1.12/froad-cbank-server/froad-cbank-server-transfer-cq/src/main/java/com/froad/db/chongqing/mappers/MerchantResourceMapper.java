package com.froad.db.chongqing.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.cbank.persistent.entity.MerchantResource;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface MerchantResourceMapper {


	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchantResource
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(MerchantResource merchantResource);
	
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
	public List<MerchantResource> selectOfPage(PageEntity<MerchantResource> pageEntity);
	
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
	public MerchantResource selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchantResource
	  * @param @return    
	  * @return List<AdPosition>    
	  * @throws
	 */
	public List<MerchantResource> selectByCondition(MerchantResource merchantResource);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchantResource
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(MerchantResource merchantResource);

	/**
	 *  按角色查询资源
	  * @Title: selectByRoleId
	  * @Description: TODO
	  * @author: share 2015年1月7日
	  * @modify: share 2015年1月7日
	  * @param @param clientId
	  * @param @param roleId
	  * @param @return    
	  * @return List<MerchantResource>    
	  * @throws
	 */
	public List<MerchantResource> selectByRoleId(@Param("clientId")Long clientId, @Param("roleId")Long roleId);
	
	/**
	 * 获取顶级菜单资源
	  * @Title: selectByRoot
	  * @Description: TODO
	  * @author: wuhelian 2015年1月8日
	  * @modify: wuhelian 2015年1月8日
	  * @param @param clientAccessType
	  * @param @param clientVersion
	  * @param @return    
	  * @return List<MerchantResourceDto>    
	  * @throws
	 */
	public List<MerchantResource> selectByRoot(Long clientId);

	/**
	 * 根据用户id查询资源
	  * @Title: selectByMerchantUserId
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-1-16
	  * @modify: zhangxiaohua 2015-1-16
	  * @param @param clientId
	  * @param @param merchantUserId
	  * @param @return    
	  * @return List<MerchantResource>    
	  * @throws
	 */
	public List<MerchantResource> selectByMerchantUserId(@Param("clientId")Long clientId,@Param("merchantUserId")Long merchantUserId);

}
