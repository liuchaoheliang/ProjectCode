package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.GoodsExchangeRackImagesDao;
import com.froad.CB.po.GoodsExchangeRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class GoodsExchangeRackImagesDaoImpl implements
		GoodsExchangeRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addGoodsExchangeRackImages(GoodsExchangeRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("goodsExchangeRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteGoodsExchangeRackImages(GoodsExchangeRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsExchangeRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateGoodsExchangeRackImages(GoodsExchangeRackImages cgcri) {
		boolean result=false;
        int rows = sqlMapClientTemplate.update("goodsExchangeRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public GoodsExchangeRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		GoodsExchangeRackImages cgcri = null;
		cgcri = (GoodsExchangeRackImages) sqlMapClientTemplate.queryForObject("goodsExchangeRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsExchangeRackImages> getAllGoodsExchangeRackImages() {
		List<GoodsExchangeRackImages> cgcri = null;
		cgcri = (List<GoodsExchangeRackImages>)sqlMapClientTemplate.queryForList("goodsExchangeRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<GoodsExchangeRackImages> getByGoodsExchangeRackId(String rackId) {
		if(rackId == null) return null;
		List<GoodsExchangeRackImages> cgcri = null;
		cgcri = (List<GoodsExchangeRackImages>) sqlMapClientTemplate.queryForList("goodsExchangeRackImages.getByGoodsExchangeRackId", rackId);
        return cgcri;
	}
	
	

}
