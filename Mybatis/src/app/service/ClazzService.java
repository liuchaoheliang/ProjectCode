package app.service;

import java.sql.SQLException;

import app.po.Clazz;

public interface ClazzService {
	public int addClazz(Clazz clazz) throws SQLException;
	public int updateById(Clazz clazz) throws SQLException;
	public Clazz getById(int clazzId) throws SQLException;
	public Clazz deleteById(int clazzId) throws SQLException;
}
