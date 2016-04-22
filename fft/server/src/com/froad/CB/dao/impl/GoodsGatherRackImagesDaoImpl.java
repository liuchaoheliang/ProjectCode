package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsGatherRackImagesDao;
import com.froad.CB.po.GoodsGatherRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class GoodsGatherRackImagesDaoImpl implements
		GoodsGatherRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addGoodsGatherRackImages(GoodsGatherRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("goodsGatherRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteGoodsGatherRackImages(GoodsGatherRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsGatherRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateGoodsGatherRackImages(GoodsGatherRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsGatherRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public GoodsGatherRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsGatherRackImages cgcri = null;
		cgcri = (GoodsGatherRackImages) sqlMapClientTemplate.queryForObject("goodsGatherRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsGatherRackImages> getAllGoodsGatherRackImages() {
		List<GoodsGatherRackImages> cgcri = null;
		cgcri = (List<GoodsGatherRackImages>)sqlMapClientTemplate.queryForList("goodsGatherRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsGatherRackImages> getByGoodsGatherRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsGatherRackImages> cgcri = null;
		cgcri = (List<GoodsGatherRackImages>) sqlMapClientTemplate.queryForList("goodsGatherRackImages.getByGoodsGatherRackId", rackId);
        return cgcri;
	}
	
	

}
