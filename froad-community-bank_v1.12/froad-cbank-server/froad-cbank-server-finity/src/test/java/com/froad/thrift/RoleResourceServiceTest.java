/**
 * Project Name:froad-cbank-server-finity
 * File Name:RoleResourceServiceTest.java
 * Package Name:com.froad.thrift
 * Date:2015��9��23������8:13:36
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.alibaba.fastjson.JSON;
import com.froad.logic.RoleResourceLogic;
import com.froad.logic.impl.RoleResourceLogicImpl;
import com.froad.po.RoleResource;


/**
 * ClassName: RoleResourceServiceTest
 * Function: TODO ADD FUNCTION
 * date: 2015年9月23日 下午8:29:48
 *
 * @author 刘超 liuchao@f-road.com.cn
 * @version 
 */

public class RoleResourceServiceTest {
	
	private  RoleResourceLogic roleResourceLogic=new RoleResourceLogicImpl();
	
	@Test
	public void add() {
		
		List<Long> list=new ArrayList<Long>();
		list.add(3333333333L);
		list.add(3333333334L);
		
		RoleResource roleResource=new RoleResource();
		roleResource.setRoleId(1000000001L);
		roleResource.setResourceId(500000001L);
		
//		Long n=roleResourceLogic.addRoleResource(roleResource);
//		System.out.println("添加成功："+n);
		
		Boolean b = roleResourceLogic.updateRoleResource(1000000001L, list);
		System.out.println("添加成功："+b);
	}
	
	@Test
	public void select(){
		List<RoleResource> list=roleResourceLogic.findRoleResource(1000000001L);
		System.out.println("查询结果："+JSON.toJSON(list));
	}
	
}
