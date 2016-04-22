/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: BeanUtilTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年3月29日
 */

package com.froad.thrift;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.froad.db.mysql.bean.Page;
import com.froad.logic.MerchantCategoryLogic;
import com.froad.logic.impl.MerchantCategoryLogicImpl;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.MerchantCategory;
import com.froad.po.MerchantUserLoginRes;
import com.froad.po.Result;
import com.froad.thrift.vo.MerchantAccountVo;
import com.froad.thrift.vo.MerchantCategoryVo;
import com.froad.thrift.vo.MerchantPageVoRes;
import com.froad.thrift.vo.MerchantUserLoginVoRes;
import com.froad.thrift.vo.MerchantVo;
//import com.froad.util.BeanUtil;
import com.froad.util.BeanUtil;
import com.froad.util.PropertiesUtil;


/**    
 * <p>Title: BeanUtilTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月29日 下午8:34:33   
 */

public class BeanUtilTest {
	static{
		PropertiesUtil.load();
	}

	/**
	 * @throws IllegalAccessException 
	 * @throws IllegalArgumentException  
	 * @Title: main 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) throws Exception {
		String regexStr = ".*";
		
		
		System.out.println((regexStr+="aaa"+".*"));
		Map<String,String> ora = new HashMap<String,String>();
		ora.put("111", "aaa");
		
		System.out.println(BeanUtil.copyProperties(Map.class, ora));
		System.exit(1);
		
		MerchantCategoryLogic merchantCategoryLogic = new MerchantCategoryLogicImpl();
		MerchantCategory merchantCategory = new MerchantCategory();
List<MerchantCategory> merchantCategoryList = merchantCategoryLogic.findMerchantCategory(merchantCategory);
		
		List<MerchantCategoryVo> merchantCategoryVoList = (List<MerchantCategoryVo>)BeanUtil.copyProperties(MerchantCategoryVo.class, merchantCategoryList);
		
		System.exit(1);
		
		
		Page<Merchant> page = new Page<Merchant>();
		
		List<Merchant> list = new ArrayList<Merchant>();
		Merchant m = new Merchant();
		m.setId(1000001l);
		m.setMerchantName("亚麻地");
		list.add(m);
		
		Merchant m2 = new Merchant();
		m2.setId(1000002l);
		m2.setMerchantName("哟西");
		list.add(m2);
		
		Merchant m3 = new Merchant();
		m3.setId(1000003l);
		m3.setMerchantName("你懂得!!!");
		list.add(m3);
		
		page.setPageCount(3);
		page.setResultsContent(list);
		
		MerchantPageVoRes merchantPageVoRes = new MerchantPageVoRes();
		List<MerchantVo> voList = new ArrayList<MerchantVo>();
		
		
		List<Merchant> list2 = new ArrayList<Merchant>();
		
//		BeanUtil.copyProperties(list2, list); // po------------->vo
//		BeanUtils.copyProperties(list2, list);
//		
//		BeanUtil.copyProperties(merchantPageVoRes, page); // po------------->vo
//		BeanUtil.copyProperties(voList, list); // po------------->vo
//		BeanUtil.copyProperties(page, merchantPageVoRes); // vo------------->po
		
		
		System.out.println("voList-------->" + JSON.toJSONString(BeanUtil.copyProperties(MerchantVo.class, list), true));
		
//		System.out.println("list2-------->" + JSON.toJSONString(list2, true));
//		System.out.println("voList-------->" + JSON.toJSONString(voList, true));
//		System.out.println("po-------->" + JSON.toJSONString(page, true));
//		System.out.println("vo-------->" + JSON.toJSONString(merchantPageVoRes, true));
//		System.exit(0);
		
		
		MerchantUserLoginVoRes merchantUserLoginVoRes = new MerchantUserLoginVoRes();
//		ResultVo resultVo = new ResultVo();
//		resultVo.setResultCode("9999");
//		resultVo.setResultDesc("9999哟西!!!");
//		merchantUserLoginVoRes.setResult(resultVo);
		
		MerchantUserLoginRes merchantUserLoginRes = new MerchantUserLoginRes();
		Result result = new Result();
		result.setResultCode("8888");
		result.setResultDesc("哟西!!!");
		merchantUserLoginRes.setResult(result);
		
		merchantUserLoginRes.setToken("sjdflsuflisdfuasdhfsdlhfkhs");
		merchantUserLoginRes.setLoginFailureCount(100);
		
		merchantUserLoginVoRes = (MerchantUserLoginVoRes)BeanUtil.copyProperties(MerchantUserLoginVoRes.class, merchantUserLoginRes); // po------------->vo
//		BeanUtil.copyProperties(merchantUserLoginRes, merchantUserLoginVoRes); // vo------------->po
		
		System.out.println("po-------->" + JSON.toJSONString(merchantUserLoginRes, true));
		System.out.println("vo-------->" + JSON.toJSONString(merchantUserLoginVoRes, true));
		System.exit(0);
		
		
		
		
		
		// TODO Auto-generated method stub
		T t = new T();
		Field[] fs = t.getClass().getDeclaredFields();
		for (Field field : fs) {
			field.setAccessible(true);
			System.out.println(field.get(t));
		}
		
		
		
		findAllSuperClass(MerchantAccountVo.class);
		
		
		System.out.println("isDelete".matches(".*isDelete.*"));
		MerchantAccount po = new MerchantAccount();
		po.setAcctName("zhangsan");
		
		
		MerchantAccountVo vo = new MerchantAccountVo();
		vo.setAcctName("yoxi");
		vo.setId(125846l);
		vo.setAcctNumber("9527");
		vo.setIsDelete(true);
		
		System.out.println("isSetIsDelete====>"+vo.isSetIsDelete());
		System.out.println("isSetIsDelete====>"+vo.isSetAcctName());
		
		vo.isIsDelete();
		vo.setIsDelete(true);
		
		try {
			BeanUtil.copyProperties(vo, po);
//			BeanUtil.copyProperties(po, vo);
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("VO=================>"+JSON.toJSONString(vo,SerializerFeature.PrettyFormat));
		System.out.println("PO=================>"+JSON.toJSONString(po,SerializerFeature.PrettyFormat));
	}
	
	public static void findAllSuperClass(Class<?> clazz) {
		Class<?> superClass = clazz.getSuperclass();
		if (superClass != null) {
			System.out.println(superClass.getName());
			findAllSuperClass(superClass);
		}
	}
	
	static class  T{
		private List<String> list = new ArrayList<String>();
		{
			list.add("111111111");
		}
	}

}
