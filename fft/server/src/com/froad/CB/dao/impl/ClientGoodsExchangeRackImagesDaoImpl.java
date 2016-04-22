package com.froad.CB.dao.impl;

import java.util.List;
import org.springframework.orm.ibatis.SqlMapClientTemplate;
import com.froad.CB.dao.ClientGoodsExchangeRackImagesDao;
import com.froad.CB.po.ClientGoodsExchangeRackImages;

/** 
 * @author TXL
 * @date 2013-2-26 am
 * @version 1.0
 * 
 */
public class ClientGoodsExchangeRackImagesDaoImpl implements
		ClientGoodsExchangeRackImagesDao {

	private SqlMapClientTemplate sqlMapClientTemplate;

	public SqlMapClientTemplate getSqlMapClientTemplate() {
		return sqlMapClientTemplate;
	}
	
	public void setSqlMapClientTemplate(SqlMapClientTemplate sqlMapClientTemplate) {
		this.sqlMapClientTemplate = sqlMapClientTemplate;
	}

	@Override
	public Integer addClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri) {
		return (Integer)sqlMapClientTemplate.insert("clientGoodsExchangeRackImages.insertObject", cgcri);
	}

	@Override
	public boolean deleteClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId()== null)
    		return result;
        int rows = sqlMapClientTemplate.update("clientGoodsExchangeRackImages.UpdateObject",cgcri);
        if(rows>0)
            result=true;
        return result;
        
	}

	@Override
	public boolean updateClientGoodsExchangeRackImages(ClientGoodsExchangeRackImages cgcri) {
		boolean result=false;
    	if(cgcri == null || cgcri.getId() == null)
    		return result;
    	
        int rows = sqlMapClientTemplate.update("clientGoodsExchangeRackImages.updateObject", cgcri);
        if(rows>0)
            result=true;
        return result;
	}

	@Override
	public ClientGoodsExchangeRackImages getByPrimaryKey(Integer id) {
		if(id == null) return null;
		ClientGoodsExchangeRackImages cgcri = null;
		cgcri = (ClientGoodsExchangeRackImages) sqlMapClientTemplate.queryForObject("clientGoodsExchangeRackImages.getByPrimaryKey", id);
        return cgcri;
        
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsExchangeRackImages> getAllClientGoodsExchangeRackImages() {
		List<ClientGoodsExchangeRackImages> cgcri = null;
		cgcri = (List<ClientGoodsExchangeRackImages>)sqlMapClientTemplate.queryForList("clientGoodsExchangeRackImages.getAll");
        return cgcri;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<ClientGoodsExchangeRackImages> getByClientGoodsExchangeRackId(String rackId) {
		if(rackId == null) return null;
		List<ClientGoodsExchangeRackImages> cgcri = null;
		cgcri = (List<ClientGoodsExchangeRackImages>) sqlMapClientTemplate.queryForList("clientGoodsExchangeRackImages.getByClientGoodsExchangeRackId", rackId);
        return cgcri;
	}
	
	

}
