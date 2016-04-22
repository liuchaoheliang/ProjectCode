package com.froad.db.ahui.mysql;

import java.io.IOException;
import java.io.Reader;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class AhMyBatisManager {

	private static SqlSessionFactory sqlSessionFactory = null;

	static {
		String resource = "ah-mybatis-config.xml";
		Reader reader = null;
		try {
			reader = Resources.getResourceAsReader(resource);
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
		} catch (Exception e) {
			e.printStackTrace();

		}
		
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
