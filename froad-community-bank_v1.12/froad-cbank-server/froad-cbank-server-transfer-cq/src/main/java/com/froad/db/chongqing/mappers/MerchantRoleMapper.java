package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.MerchantRole;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface MerchantRoleMapper {


	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchantRole
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(MerchantRole merchantRole);
	
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
	public List<MerchantRole> selectOfPage(PageEntity<MerchantRole> pageEntity);
	
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
	public MerchantRole selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchantRole
	  * @param @return    
	  * @return List<AdPosition>    
	  * @throws
	 */
	public List<MerchantRole> selectByCondition(MerchantRole merchantRole);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchantRole
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(MerchantRole merchantRole);
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2014年12月24日
	  * @modify: share 2014年12月24日
	  * @param @param merchantRole
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public  List<MerchantRole> selectByMerchantUserId(Long merchantUserId);

}
