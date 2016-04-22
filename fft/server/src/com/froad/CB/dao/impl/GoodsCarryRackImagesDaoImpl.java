package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsCarryRackImagesDao;
import com.froad.CB.po.GoodsCarryRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 pm
 * @version 1.0
 * 
 */
public class GoodsCarryRackImagesDaoImpl implements
		GoodsCarryRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addGoodsCarryRackImages(GoodsCarryRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("goodsCarryRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteGoodsCarryRackImages(GoodsCarryRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsCarryRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateGoodsCarryRackImages(GoodsCarryRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsCarryRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public GoodsCarryRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsCarryRackImages cgcri = null;
		cgcri = (GoodsCarryRackImages) sqlMapClientTemplate.queryForObject("goodsCarryRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsCarryRackImages> getAllGoodsCarryRackImages() {
		List<GoodsCarryRackImages> cgcri = null;
		cgcri = (List<GoodsCarryRackImages>)sqlMapClientTemplate.queryForList("goodsCarryRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsCarryRackImages> getByGoodsCarryRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsCarryRackImages> cgcri = null;
		cgcri = (List<GoodsCarryRackImages>) sqlMapClientTemplate.queryForList("goodsCarryRackImages.getByGoodsCarryRackId", rackId);
        return cgcri;
	}
	
	

}
