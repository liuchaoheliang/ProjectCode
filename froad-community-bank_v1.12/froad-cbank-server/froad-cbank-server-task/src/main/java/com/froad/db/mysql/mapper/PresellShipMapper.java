package com.froad.db.mysql.mapper;

import com.froad.po.PresellShip;

public interface PresellShipMapper {

	/**
	 * 
	  * @Title: addPresellShip
	  * @Description: TODO
	  * @author: Yaren Liang 2015年6月15日
	  * @modify: Yaren Liang 2015年6月15日
	  * @param     
	  * @return void    
	  * @throws
	 */
	public void addPresellShip(PresellShip presellShip);
	
	/**
	 * 
	  * @Title: findPresellOfCountBySubOrderId
	  * @Description: TODO
	  * @author: lf 2015年6月27日
	  * @param @return    
	  * @return int    
	  * @throws
	 */
	public int findPresellOfCountBySubOrderId(String subOrderId);
}

