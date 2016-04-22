package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsGroupRackImagesDao;
import com.froad.CB.po.GoodsGroupRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class GoodsGroupRackImagesDaoImpl implements
		GoodsGroupRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addGoodsGroupRackImages(GoodsGroupRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("goodsGroupRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteGoodsGroupRackImages(GoodsGroupRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsGroupRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateGoodsGroupRackImages(GoodsGroupRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsGroupRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public GoodsGroupRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsGroupRackImages cgcri = null;
		cgcri = (GoodsGroupRackImages) sqlMapClientTemplate.queryForObject("goodsGroupRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsGroupRackImages> getAllGoodsGroupRackImages() {
		List<GoodsGroupRackImages> cgcri = null;
		cgcri = (List<GoodsGroupRackImages>)sqlMapClientTemplate.queryForList("goodsGroupRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsGroupRackImages> getByGoodsGroupRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsGroupRackImages> cgcri = null;
		cgcri = (List<GoodsGroupRackImages>) sqlMapClientTemplate.queryForList("goodsGroupRackImages.getByGoodsGroupRackId", rackId);
        return cgcri;
	}
	
	

}
