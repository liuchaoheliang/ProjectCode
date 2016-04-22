package com.froad.db.mysql.rp_mappers;

import java.util.List;

import com.froad.po.BatchNode;

public interface BatchNodeMapper {
	
	/**
	 * find all batch nodes
	 * 
	 * @return
	 */
	public List<BatchNode> findAll();
}
