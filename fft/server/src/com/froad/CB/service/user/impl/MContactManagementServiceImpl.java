package com.froad.CB.service.user.impl;

import java.util.List;
import javax.jws.WebService;
import com.froad.CB.AppException.AppException;
import com.froad.CB.dao.user.ContactManagementDao;
import com.froad.CB.po.user.Contacter;
import com.froad.CB.service.user.MContactManagementService;

@WebService(endpointInterface="com.froad.CB.service.user.MContactManagementService")
public class MContactManagementServiceImpl implements MContactManagementService{
	private ContactManagementDao contactManagementDao;
	
	public Contacter addContacter(Contacter con) throws AppException{
		return contactManagementDao.addContacter(con);
	}


	public Contacter delContacter(Contacter con) throws AppException{
		return contactManagementDao.delContacter(con);
	}
	
	public Contacter updContacter(Contacter con) throws AppException{
		return contactManagementDao.updContacter(con);
	}

	public List<Contacter> getContacterList(Contacter con)throws AppException {
		return contactManagementDao.getContacterList(con);
	}


	public void setContactManagementDao(ContactManagementDao contactManagementDao) {
		this.contactManagementDao = contactManagementDao;
	}
	
}
