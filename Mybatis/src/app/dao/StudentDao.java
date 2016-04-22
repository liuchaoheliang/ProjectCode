package app.dao;

import java.sql.SQLException;

import app.po.Student;

public interface StudentDao {
	public Integer add(Student student)throws SQLException ;
	public Integer update(Student student) throws SQLException ;
	public Student getById(int id) throws SQLException ;
}
