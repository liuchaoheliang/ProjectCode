package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.MerchantGroupUser;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 *  商户用户组mybatis接口
  * @ClassName: MerchantGroupUserMapper
  * @Description: TODO
  * @author luofan 2015年01月05日
  * @modify luofan 2015年01月05日
 */
public interface MerchantGroupUserMapper {
	
	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: luofan 2015年01月05日
	  * @modify: luofan 2015年01月05日
	  * @param @param merchantGroupUser
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(MerchantGroupUser merchantGroupUser);
	
	/**
	 *  基础分页查询
	  * @Title: selectOfPage
	  * @Description: TODO
	  * @author: luofan 2015年01月05日
	  * @modify: luofan 2015年01月05日
	  * @param @param pageEntity
	  * @param @return    
	  * @return List<MerchantGroupUser>    
	  * @throws
	 */
	public List<MerchantGroupUser> selectOfPage(PageEntity<MerchantGroupUser> pageEntity);
	
	/**
	 *  通过数据主键获取数据
	  * @Title: selectById
	  * @Description: TODO
	  * @author: luofan 2015年01月05日
	  * @modify: luofan 2015年01月05日
	  * @param @param id
	  * @param @return    
	  * @return MerchantGroupUser    
	  * @throws
	 */
	public MerchantGroupUser selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: luofan 2015年01月05日
	  * @modify: luofan 2015年01月05日
	  * @param @param merchantGroupUser
	  * @param @return    
	  * @return List<MerchantGroupUser>    
	  * @throws
	 */
	public List<MerchantGroupUser> selectByCondition(MerchantGroupUser merchantGroupUser);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: luofan 2015年01月05日
	  * @modify: luofan 2015年01月05日
	  * @param @param merchantGroupUser
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(MerchantGroupUser merchantGroupUser);
	
	/**
	 *   删除
	  * @Title: delete
	  * @Description: TODO
	  * @author: luofan 2015年01月05日
	  * @modify: luofan 2015年01月05日
	  * @param @param merchantGroupUser
	  * @param @return    
	  * @return long    
	  * @throws
	 */
	public long delete(MerchantGroupUser merchantGroupUser);

	
	/**
	 *  通过数据主键获取数据
	  * @Title: selectByMerchantUserId
	  * @Description: TODO
	  * @author: luofan 2015年01月11日
	  * @modify: luofan 2015年01月11日
	  * @param @param merchantUserId
	  * @param @return    
	  * @return MerchantGroupUser    
	  * @throws
	 */
	public MerchantGroupUser selectByMerchantUserId(Long merchantUserId);
}
