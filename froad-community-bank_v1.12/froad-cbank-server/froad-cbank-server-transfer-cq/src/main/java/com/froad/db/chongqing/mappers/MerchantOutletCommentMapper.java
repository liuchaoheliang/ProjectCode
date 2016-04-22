package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.MerchantOutletComment;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 *  商户门店点评mybatis接口
  * @ClassName: MerchantOutletCommentMapper
  * @Description: TODO
  * @author share 2015年01月04日
  * @modify share 2015年01月04日
 */
public interface MerchantOutletCommentMapper {

	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2015年01月04日
	  * @modify: share 2015年01月04日
	  * @param @param merchantOutletComment
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(MerchantOutletComment merchantOutletComment);
	
	/**
	 *  基础分页查询
	  * @Title: selectOfPage
	  * @Description: TODO
	  * @author: share 2015年01月04日
	  * @modify: share 2015年01月04日
	  * @param @param pageEntity
	  * @param @return    
	  * @return List<MerchantOutletComment>    
	  * @throws
	 */
	public List<MerchantOutletComment> selectOfPage(PageEntity<MerchantOutletComment> pageEntity);
	
	/**
	 *  通过数据主键获取数据
	  * @Title: selectById
	  * @Description: TODO
	  * @author: share 2015年01月04日
	  * @modify: share 2015年01月04日
	  * @param @param id
	  * @param @return    
	  * @return MerchantOutletComment    
	  * @throws
	 */
	public MerchantOutletComment selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: share 2015年01月04日
	  * @modify: share 2015年01月04日
	  * @param @param merchantOutletComment
	  * @param @return    
	  * @return List<MerchantOutletComment>    
	  * @throws
	 */
	public List<MerchantOutletComment> selectByCondition(MerchantOutletComment merchantOutletComment);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2015年01月04日
	  * @modify: share 2015年01月04日
	  * @param @param merchantOutletComment
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(MerchantOutletComment merchantOutletComment);
	
	/**
	 *   删除
	  * @Title: delete
	  * @Description: TODO
	  * @author: share 2015年01月04日
	  * @modify: share 2015年01月04日
	  * @param @param merchantOutletComment
	  * @param @return    
	  * @return long    
	  * @throws
	 */
	public long delete(MerchantOutletComment merchantOutletComment);
}
