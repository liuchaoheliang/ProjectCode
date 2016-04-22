package com.froad.db.mysql;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

//import com.froad.db.mysql.mapper.UserMapper;
//import com.froad.po.User;

public class MyBatisManager {

	private final static SqlSessionFactory sqlSessionFactory;

	static {
		String resource = "mybatis-config.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (IOException e) {
			e.printStackTrace();

		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public static void main(String[] args) {
		
		SqlSession sqlSession = sqlSessionFactory.openSession();
		try {
//			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
//			User user = userMapper.getUser("fangquan");
//			System.out.println("name: " + user.getName() + "|age: "
//					+ user.getAge());
		} finally {
			sqlSession.close();
		}
	}

}
