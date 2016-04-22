package com.froad.thrift;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;

import com.froad.util.DateUtil;


public class Test {
	public static void main(String[] args) {
		
		
			long endTime=Long.valueOf(DateUtil.formatDateTime("HHmmss",Calendar.getInstance().getTime()));
			
			System.out.println(endTime);
		
//		com.froad.po.SmsContent  model = new com.froad.po.SmsContent();
//		 model.setClientId(12323l);
//		 model.setMsgSuffix("aaaa");
//		 model.setIsEnable(true);//给对象赋值
//		 
//		 Test fd = new Test(); 
//		 fd.getField(model);
	}

	private void getField(Object model) {
		try {
			// 获取对象所有方法
			java.lang.reflect.Method[] method = model.getClass().getDeclaredMethods();
			for (java.lang.reflect.Method m : method) {
				if (m.getName().startsWith("get")) { // 获取get方法
					Object o = m.invoke(model); // 执行
					if (o == null || "".equals(o.toString())) {
					} else {
						System.out.println(o.toString());// 输出相应的属性值
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
