package app.service.impl;

import java.sql.SQLException;

import app.dao.StudentDao;
import app.po.Student;
import app.service.StudentService;

public class StudentServiceImp implements StudentService {
	private StudentDao studentDao;
	
	public Integer add(Student student) throws SQLException {
		return studentDao.add(student);
	}

	public Integer update(Student student) throws SQLException {
		return studentDao.update(student);
	}

	public Student getById(int id) throws SQLException {
		return studentDao.getById(id);
	}

	public StudentDao getStudentDao() {
		return studentDao;
	}

	public void setStudentDao(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

}
