package com.froad.db.chongqing.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.cbank.persistent.entity.Area;
import com.froad.cbank.persistent.entity.base.PageEntity;

public interface AreaMapper {

/**
 * 
  * @Title: findArea
  * @Description: TODO
  * @author: Yaren Liang 2015年6月25日
  * @modify: Yaren Liang 2015年6月25日
  * @param @param area
  * @param @return    
  * @return List<Area>    
  * @throws
 */
public List<Area> findArea(Area area);
	
	
	public Area findById(@Param("areaId")Long areaId);
	
	public List<Area> findAllArea();

}
