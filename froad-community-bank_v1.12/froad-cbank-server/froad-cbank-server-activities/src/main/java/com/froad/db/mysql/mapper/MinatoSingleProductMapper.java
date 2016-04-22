package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.AreaInfo;
import com.froad.po.mongo.Location;

public interface MinatoSingleProductMapper {
	
	 /**
	  * @Title: findAreaListInfo
	  * @Description: 查找区域信息
	  * @author: shenshaocheng 2015年11月12日
	  * @modify: shenshaocheng 2015年11月12日
	  * @param clientId
	  * @param areaId
	  * @return 返回区域信息
	 */	
	public List<AreaInfo> findAreaListInfo(@Param("clientId")String clientId, @Param("areaId")String areaId);

	 /**
	  * @Title: findAreaLocation
	  * @Description: 查找区域坐标信息
	  * @author: shenshaocheng 2015年11月17日
	  * @modify: shenshaocheng 2015年11月17日
	  * @param areaId 区域ID
	  * @return  区域坐标信息
	 */	
	public Location findAreaLocation(@Param("areaId")String areaId);
}
