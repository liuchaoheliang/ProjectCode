package app.test;

import java.sql.SQLException;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import app.po.Clazz;
import app.service.ClazzService;

public class Test {
	public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        ClazzService clazzService = (ClazzService) context.getBean("clazzService");
        try {
			Clazz c=clazzService.getById(100001006);
			System.out.println(c.getClazz()+"===="+c.getTeacher());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
