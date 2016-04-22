/**
 * Project Name:froad-cbank-server-boss
 * File Name:OutletActivityTest.java
 * Package Name:com.froad.thrift
 * Date:2015年10月27日上午10:57:57
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.BossProductCategoryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.product.ProductCategoryVo;
import com.froad.util.PropertiesUtil;

/**
 * ClassName:ProductCategoryInputAndExportTest
 * Reason:	 
 * Date:     2015年11月5日 上午10:31:57
 * @author   liuyanyun
 * @version  
 * @see 	 
 */
public class ProductCategoryInputAndExportTest {

	public static void main(String[] args) throws TException {
		PropertiesUtil.load();
		//本地测试
		TSocket transport = new TSocket("127.0.0.1", 16001);
		transport.open();
		TBinaryProtocol protocol = new TBinaryProtocol(transport);
		TMultiplexedProtocol multiplexedProtocol = new TMultiplexedProtocol(protocol, BossProductCategoryService.class.getSimpleName());
		BossProductCategoryService.Iface productCategoryService = new BossProductCategoryService.Client(multiplexedProtocol);
		
		//productCategoryProductInput(productCategoryService);//商品分类商品导入
		productCategoryDetailExport(productCategoryService);//商品分类商品详细导出
	}
	
	/**
	 * 商品分类商品导入
	 */
	public static void productCategoryProductInput(BossProductCategoryService.Iface productCategoryService) throws TException{
		OriginVo originVo = new OriginVo();
		originVo.setClientId("重庆");
		originVo.setDescription("商品分类商品导入");
		originVo.setOperatorId(1);
		originVo.setPlatType(PlatType.bank);
		originVo.setRoleId("2");
		List<ProductCategoryVo> vos = new ArrayList<ProductCategoryVo>();
		ProductCategoryVo vo1 = new ProductCategoryVo();
		vo1.setClientName("重庆");
		vo1.setMerchantId("0D8201410014");
		vo1.setMerchantName("小天鹅洗衣");
		vo1.setProductCategory("洗车");
		vo1.setProductCategryDetail("汽车服务-洗车");
		vo1.setProductId("0D820C218002");
		vo1.setProductName("毛衣清洗护理");
		

		ProductCategoryVo vo2 = new ProductCategoryVo();
		vo2.setClientName("重庆");
		vo2.setMerchantId("0D820151000D");
		vo2.setMerchantName("奇艺美发");
		vo2.setProductCategory("休闲娱乐");
		vo2.setProductCategryDetail("休闲娱乐");
		vo2.setProductId("0D820C418000");
		vo2.setProductName("奇艺烫染套餐");
		vos.add(vo1);
		vos.add(vo2);
		ExportResultRes res = productCategoryService.productCategoryProductInput(originVo, "1",vos);
		System.out.println(res.getResultVo());
		System.out.println(res.getUrl());
	}
	
	/**
	 * 商品分类商品详细导出
	 */
	@SuppressWarnings("unchecked")
	public static void productCategoryDetailExport(BossProductCategoryService.Iface productCategoryService) throws TException{
		OriginVo originVo = new OriginVo();
		originVo.setClientId("重庆");
		originVo.setDescription("商品分类商品详细导出");
		originVo.setOperatorId(1);
		originVo.setPlatType(PlatType.bank);
		originVo.setRoleId("2");
		
		String clientId="chongqing";
		long categoryId=100000017;
		
		ExportResultRes res = productCategoryService.productCategoryDetailExport(originVo,"1", clientId, categoryId);
		System.out.println(res.getResultVo());
		System.out.println(res.getUrl());
	}
		
	
	
}
