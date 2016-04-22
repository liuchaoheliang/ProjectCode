package app.service.impl;

import java.sql.SQLException;

import app.dao.ClazzDao;
import app.po.Clazz;
import app.service.ClazzService;

public class ClazzServiceImp implements ClazzService{
	private ClazzDao clazzDao;
	public int addClazz(Clazz clazz) throws SQLException {
		
		return clazzDao.addClazz(clazz);
	}

	public int updateById(Clazz clazz) throws SQLException {
		return clazzDao.updateById(clazz);
	}

	public Clazz getById(int clazzId) throws SQLException {
		return clazzDao.getById(clazzId);
	}

	public Clazz deleteById(int clazzId) throws SQLException {
		return clazzDao.deleteById(clazzId);
	}

	public ClazzDao getClazzDao() {
		return clazzDao;
	}

	public void setClazzDao(ClazzDao clazzDao) {
		this.clazzDao = clazzDao;
	}

	
}
