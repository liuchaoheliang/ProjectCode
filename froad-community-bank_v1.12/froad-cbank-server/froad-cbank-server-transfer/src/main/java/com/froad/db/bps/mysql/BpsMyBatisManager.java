package com.froad.db.bps.mysql;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class BpsMyBatisManager {

	private final static SqlSessionFactory sqlSessionFactory;

	static {
		String resource = "bps-mybatis-config.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
		} catch (Exception e) {
			e.printStackTrace();

		}
		sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
	}

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public static void main(String[] args) {
		
//		SqlSession sqlSession = sqlSessionFactory.openSession();
//		try {
//			UserMapper userMapper = sqlSession.getMapper(UserMapper.class);
////			User user = userMapper.getUser("fangquan");
////			System.out.println("name: " + user.getName() + "|age: "
////					+ user.getAge());
//			
//			Page page=new Page();
//			page.setPageCount(1);
//			page.setPageNumber(1);
//			User u=new User();
//			u.setName("fangquan");
//			List<User> list=userMapper.findByPage(page, u);
//			page.setResultsContent(list);
//			
//			System.out.println(JSON.toJSONString(page));
			
//		} finally {
//			sqlSession.close();
//		}
	}

}