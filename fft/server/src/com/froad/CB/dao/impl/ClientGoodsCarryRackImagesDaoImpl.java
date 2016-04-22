package com.froad.CB.dao.impl;

import java.util.List;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.ClientGoodsCarryRackImagesDao;
import com.froad.CB.po.ClientGoodsCarryRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class ClientGoodsCarryRackImagesDaoImpl implements
		ClientGoodsCarryRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("clientGoodsCarryRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId()== null)
    		return result;
        int rows = sqlMapClientTemplate.update("clientGoodsCarryRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateClientGoodsCarryRackImages(ClientGoodsCarryRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId() == null)
    		return result;
    	
        int rows = sqlMapClientTemplate.update("clientGoodsCarryRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public ClientGoodsCarryRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsCarryRackImages cgcri = null;
		cgcri = (ClientGoodsCarryRackImages) sqlMapClientTemplate.queryForObject("clientGoodsCarryRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsCarryRackImages> getAllClientGoodsCarryRackImages() {
		List<ClientGoodsCarryRackImages> cgcri = null;
		cgcri = (List<ClientGoodsCarryRackImages>)sqlMapClientTemplate.queryForList("clientGoodsCarryRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsCarryRackImages> getByClientGoodsCarryRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsCarryRackImages> cgcri = null;
		cgcri = (List<ClientGoodsCarryRackImages>) sqlMapClientTemplate.queryForList("clientGoodsCarryRackImages.getByClientGoodsCarryRackId", rackId);
        return cgcri;
	}
	
	

}
