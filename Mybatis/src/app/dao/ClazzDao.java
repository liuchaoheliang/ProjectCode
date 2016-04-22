package app.dao;

import java.sql.SQLException;
import java.util.List;

import app.po.Clazz;

public interface ClazzDao {
	public int addClazz(Clazz clazz) throws SQLException;
	public int updateById(Clazz clazz) throws SQLException;
	public Clazz getById(int clazzId) throws SQLException;
	public Clazz deleteById(int clazzId) throws SQLException;
}
