package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.ClientGoodsGroupRackImagesDao;
import com.froad.CB.po.ClientGoodsGroupRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class ClientGoodsGroupRackImagesDaoImpl implements
		ClientGoodsGroupRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("clientGoodsGroupRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId()== null)
    		return result;
        int rows = sqlMapClientTemplate.update("clientGoodsGroupRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateClientGoodsGroupRackImages(ClientGoodsGroupRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId() == null)
    		return result;
    	
        int rows = sqlMapClientTemplate.update("clientGoodsGroupRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public ClientGoodsGroupRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsGroupRackImages cgcri = null;
		cgcri = (ClientGoodsGroupRackImages) sqlMapClientTemplate.queryForObject("clientGoodsGroupRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsGroupRackImages> getAllClientGoodsGroupRackImages() {
		List<ClientGoodsGroupRackImages> cgcri = null;
		cgcri = (List<ClientGoodsGroupRackImages>)sqlMapClientTemplate.queryForList("clientGoodsGroupRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsGroupRackImages> getByClientGoodsGroupRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsGroupRackImages> cgcri = null;
		cgcri = (List<ClientGoodsGroupRackImages>) sqlMapClientTemplate.queryForList("clientGoodsGroupRackImages.getByClientGoodsGroupRackId", rackId);
        return cgcri;
	}
	
	

}
