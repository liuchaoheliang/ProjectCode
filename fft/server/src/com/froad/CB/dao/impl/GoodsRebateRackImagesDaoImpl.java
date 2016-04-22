package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsRebateRackImagesDao;
import com.froad.CB.po.GoodsRebateRackImages;

/**
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class GoodsRebateRackImagesDaoImpl implements
		GoodsRebateRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addGoodsRebateRackImages(GoodsRebateRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("goodsRebateRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteGoodsRebateRackImages(GoodsRebateRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsRebateRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateGoodsRebateRackImages(GoodsRebateRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsRebateRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public GoodsRebateRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsRebateRackImages cgcri = null;
		cgcri = (GoodsRebateRackImages) sqlMapClientTemplate.queryForObject("goodsRebateRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsRebateRackImages> getAllGoodsRebateRackImages() {
		List<GoodsRebateRackImages> cgcri = null;
		cgcri = (List<GoodsRebateRackImages>)sqlMapClientTemplate.queryForList("goodsRebateRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsRebateRackImages> getByGoodsRebateRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsRebateRackImages> cgcri = null;
		cgcri = (List<GoodsRebateRackImages>) sqlMapClientTemplate.queryForList("goodsRebateRackImages.getByGoodsRebateRackId", rackId);
        return cgcri;
	}
	
	

}
