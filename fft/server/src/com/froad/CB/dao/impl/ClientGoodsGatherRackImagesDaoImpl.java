package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.ClientGoodsGatherRackImagesDao;
import com.froad.CB.po.ClientGoodsGatherRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class ClientGoodsGatherRackImagesDaoImpl implements
		ClientGoodsGatherRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("clientGoodsGatherRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId()== null)
    		return result;
        int rows = sqlMapClientTemplate.update("clientGoodsGatherRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateClientGoodsGatherRackImages(ClientGoodsGatherRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId() == null)
    		return result;
    	
        int rows = sqlMapClientTemplate.update("clientGoodsGatherRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public ClientGoodsGatherRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsGatherRackImages cgcri = null;
		cgcri = (ClientGoodsGatherRackImages) sqlMapClientTemplate.queryForObject("clientGoodsGatherRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsGatherRackImages> getAllClientGoodsGatherRackImages() {
		List<ClientGoodsGatherRackImages> cgcri = null;
		cgcri = (List<ClientGoodsGatherRackImages>)sqlMapClientTemplate.queryForList("clientGoodsGatherRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsGatherRackImages> getByClientGoodsGatherRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsGatherRackImages> cgcri = null;
		cgcri = (List<ClientGoodsGatherRackImages>) sqlMapClientTemplate.queryForList("clientGoodsGatherRackImages.getByClientGoodsGatherRackId", rackId);
        return cgcri;
	}
	
	

}
