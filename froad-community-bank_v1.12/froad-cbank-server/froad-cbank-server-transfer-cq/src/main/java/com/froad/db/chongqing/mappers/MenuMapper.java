package com.froad.db.chongqing.mappers;

import java.util.List;

import com.froad.cbank.persistent.entity.Menu;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface MenuMapper {

	/**
	 *  数据插入
	  * @Title: insert
	  * @Description: TODO
	  * @author: share 2014年12月30日
	  * @modify: share 2014年12月30日
	  * @param @param menu
	  * @param @return    
	  * @return Long    
	  * @throws
	 */
	public Long insert(Menu menu);
	
	/**
	 *  基础分页查询
	  * @Title: selectOfPage
	  * @Description: TODO
	  * @author: share 2014年12月30日
	  * @modify: share 2014年12月30日
	  * @param @param pageEntity
	  * @param @return    
	  * @return List<Menu>    
	  * @throws
	 */
	public List<Menu> selectOfPage(PageEntity<Menu> pageEntity);
	
	/**
	 *  通过数据主键获取数据
	  * @Title: selectById
	  * @Description: TODO
	  * @author: share 2014年12月30日
	  * @modify: share 2014年12月30日
	  * @param @param id
	  * @param @return    
	  * @return Menu    
	  * @throws
	 */
	public Menu selectById(Long id);

	
	/**
	 *  通过传入的JavaBean非空属性组合查询条件
	  * @Title: selectByCondition
	  * @Description: TODO
	  * @author: share 2014年12月30日
	  * @modify: share 2014年12月30日
	  * @param @param menu
	  * @param @return    
	  * @return List<Menu>    
	  * @throws
	 */
	public List<Menu> selectByCondition(Menu menu);
	
	
	/**
	 *  通过传入的JavaBean id 属性确定数据，将该JavaBean的其他非空属性执行更新命令
	  * @Title: updateById
	  * @Description: TODO
	  * @author: share 2014年12月30日
	  * @modify: share 2014年12月30日
	  * @param @param menu
	  * @param @return    
	  * @return boolean    
	  * @throws
	 */
	public boolean updateById(Menu menu);
}
