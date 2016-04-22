package com.froad.db.mysql.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.Org;

public interface OrgMapper {
	
	/**
	 * 查询机构信息
	 * @Title: findAllOrgInfo 
	 * @Description: TODO
	 * @author: froad-huangyihao 2015年5月20日
	 * @modify: froad-huangyihao 2015年5月20日
	 * @return
	 * @throws
	 */
	List<Org> findAllOrgInfoByPage(Page<Org> page);
	
	
	Org findByOrgCode(@Param("orgCode")String orgCode);
	
}
