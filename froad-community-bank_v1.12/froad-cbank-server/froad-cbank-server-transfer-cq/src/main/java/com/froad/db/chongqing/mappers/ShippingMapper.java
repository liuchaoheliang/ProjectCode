package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.Shipping;
import com.froad.cbank.persistent.entity.base.PageEntity;

/**
 *  订单发货单mybatis接口
  * @ClassName: ShippingMapper
  * @Description: TODO
  * @author luofan 2015年01月07日
  * @modify luofan 2015年01月07日
 */
public interface ShippingMapper {
	
	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: luofan 2015年01月07日
	  * @modify: luofan 2015年01月07日
	  * @param @param shipping
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(Shipping shipping);
	
	/**
	 *  基础分页查询
	  * @Title: selectOfPage
	  * @Description: TODO
	  * @author: luofan 2015年01月07日
	  * @modify: luofan 2015年01月07日
	  * @param @param pageEntity
	  * @param @return    
	  * @return List<Shipping>    
	  * @throws
	 */
	public List<Shipping> selectOfPage(PageEntity<Shipping> pageEntity);
	
	/**
	 *  通过数据主键获取数据
	  * @Title: selectById
	  * @Description: TODO
	  * @author: luofan 2015年01月07日
	  * @modify: luofan 2015年01月07日
	  * @param @param id
	  * @param @return    
	  * @return Shipping    
	  * @throws
	 */
	public Shipping selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: luofan 2015年01月07日
	  * @modify: luofan 2015年01月07日
	  * @param @param shipping
	  * @param @return    
	  * @return List<Shipping>    
	  * @throws
	 */
	public List<Shipping> selectByCondition(Shipping shipping);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: luofan 2015年01月07日
	  * @modify: luofan 2015年01月07日
	  * @param @param shipping
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(Shipping shipping);
	
	/**
	 *   删除
	  * @Title: delete
	  * @Description: TODO
	  * @author: luofan 2015年01月07日
	  * @modify: luofan 2015年01月07日
	  * @param @param shipping
	  * @param @return    
	  * @return long    
	  * @throws
	 */
	public long delete(Shipping shipping);

}
