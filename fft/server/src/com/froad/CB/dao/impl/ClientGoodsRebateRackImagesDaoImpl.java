package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.ClientGoodsRebateRackImagesDao;
import com.froad.CB.po.ClientGoodsRebateRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class ClientGoodsRebateRackImagesDaoImpl implements
		ClientGoodsRebateRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("clientGoodsRebateRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId()== null)
    		return result;
        int rows = sqlMapClientTemplate.update("clientGoodsRebateRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateClientGoodsRebateRackImages(ClientGoodsRebateRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId() == null)
    		return result;
    	
        int rows = sqlMapClientTemplate.update("clientGoodsRebateRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public ClientGoodsRebateRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsRebateRackImages cgcri = null;
		cgcri = (ClientGoodsRebateRackImages) sqlMapClientTemplate.queryForObject("clientGoodsRebateRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsRebateRackImages> getAllClientGoodsRebateRackImages() {
		List<ClientGoodsRebateRackImages> cgcri = null;
		cgcri = (List<ClientGoodsRebateRackImages>)sqlMapClientTemplate.queryForList("clientGoodsRebateRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsRebateRackImages> getByClientGoodsRebateRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsRebateRackImages> cgcri = null;
		cgcri = (List<ClientGoodsRebateRackImages>) sqlMapClientTemplate.queryForList("clientGoodsRebateRackImages.getByClientGoodsRebateRackId", rackId);
        return cgcri;
	}
	
	

}
