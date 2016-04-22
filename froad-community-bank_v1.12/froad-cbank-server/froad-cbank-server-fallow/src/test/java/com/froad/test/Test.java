/**
 * Project Name:Froad Cbank Server Fallow
 * File Name:Test.java
 * Package Name:com.froad.test
 * Date:2015年10月16日上午10:12:44
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.test;

import java.lang.reflect.InvocationTargetException;
import java.util.Calendar;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.froad.enums.AuditType;
import com.froad.enums.FieldMapping;
import com.froad.enums.PlatTypeEnum;
import com.froad.po.Origin;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.util.BeanUtil;
import com.froad.util.PropertiesUtil;

/**
 * ClassName:Test Reason: TODO ADD REASON. Date: 2015年10月16日 上午10:12:44
 * 
 * @author vania
 * @version
 * @see
 */
public class Test {
	static {
		PropertiesUtil.load();
	}

	public static void main(String[] args) throws IllegalAccessException, InvocationTargetException, InterruptedException {
		JSONObject bessDate = null;
		System.out.println(">>>>>>>>>>>>>>>>"+5000%1000);
		System.out.println(">>>>>>>>>>>>>>>>"+MapUtils.isEmpty(bessDate));
		System.out.println("1------>"+MapUtils.isEmpty(bessDate));
		MapUtils.isEmpty(bessDate);
		bessDate = new JSONObject();
		System.out.println("2------>"+MapUtils.isEmpty(bessDate));
		
		System.exit(0);
		List<String> list = null;
		System.out.println(CollectionUtils.sizeIsEmpty(list));
		
		
		System.out.println(StringUtils.isNotBlank(""));
		System.out.println(StringUtils.isNotBlank("   "));
		
		Calendar ca = Calendar.getInstance();
		System.out.println("curr time: " + ca.getTime());
		Thread.currentThread().sleep(10000);
		System.out.println("10000 time: " + ca.getTime());
		
		System.out.println(FieldMapping.valueOf("ID"));
		
		
		OriginVo originVo = new OriginVo();
		originVo.setClientId("anhui");
//		originVo.setPlatType(PlatType.merchant_pc);

		Origin originVo2 = (Origin) BeanUtil.copyProperties(Origin.class, originVo);
		
		originVo2.setPlatType(null);
		originVo = (OriginVo) BeanUtil.copyProperties(OriginVo.class, originVo2);

		System.out.println("originVo====>" + JSON.toJSONString(originVo, true));
		System.out.println("originVo2===>" + JSON.toJSONString(originVo2, true));
		System.out.println("originVo2.plat===>" + originVo2);
		
		PlatType platType = PlatType.bank;
		PlatTypeEnum platTypePo = PlatTypeEnum.merchant_h5;
		Enum a;
//		System.out.println(Set.class.isAssignableFrom(clazz));
		System.out.println(Enum.class.isAssignableFrom(PlatType.class));
		System.out.println(Enum.class.isAssignableFrom(PlatTypeEnum.class));
		
		
//		System.out.println(PlatType.class);
//		System.out.println(BeanUtil.isJavaClass(PlatType.class));
//		System.out.println(BeanUtil.isJavaClass(PlatTypePo.class));
//		System.out.println("PlatTypePo===>" + JSON.toJSONString((PlatTypePo) BeanUtil.copyProperties(PlatTypePo.class, platType), true));
//		BeanUtils.copyProperty(destObject, destField.getName(), origField.get(origObject));
		BeanUtils.copyProperties(platTypePo, platType);
		System.out.println("PlatTypePo===>" + JSON.toJSONString(platTypePo, true));
		
		AuditType t = AuditType.merchant;
		System.out.println(t);
		System.out.println(t.name());
		System.out.println(t.values());
	}

}

