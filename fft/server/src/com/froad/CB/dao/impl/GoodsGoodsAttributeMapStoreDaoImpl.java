package com.froad.CB.dao.impl;

import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.froad.CB.dao.GoodsGoodsAttributeMapStoreDao;
import com.froad.CB.po.GoodsGoodsAttributeMapStore;

/** 
 * @author FQ 
 * @date 2013-1-29 下午11:20:45
 * @version 1.0
 * 
 */
public class GoodsGoodsAttributeMapStoreDaoImpl implements
		GoodsGoodsAttributeMapStoreDao {
	
	private SqlMapClientTemplate sqlMapClientTemplate;
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public void addGoodsGoodsAttributeMapStore(
			GoodsGoodsAttributeMapStore goodsGoodsAttributeMapStore) {
		sqlMapClientTemplate.insert("goodsGoodsAttributeMapStore.addGoodsGoodsAttributeMapStore", goodsGoodsAttributeMapStore);
	}

}
