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
 * @Title: MerchantTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年3月21日
 */

package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mongo.page.MongoPage;
import com.froad.po.mongo.MerchantDetail;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.MerchantVoReq;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.TypeInfoVo;
import com.froad.util.PropertiesUtil;


/**    
 * <p>Title: MerchantTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年3月21日 下午1:47:47   
 */

public class MerchantTest {

	/** 
	 * @Title: main 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) {
		PropertiesUtil.load();
		// TODO Auto-generated method stub
		try {
			String host= "10.43.2.3";
			int port = 0;
			host = "127.0.0.1";
			port = 15201;
//			host = "10.43.1.9";
//			port = 15201;
			TSocket transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantService");
			MerchantService.Client service = new MerchantService.Client(mp);

			transport.open();
			MerchantVoReq merchantVoReq = new MerchantVoReq();
			
//			create_time                 			zip             
//			client_id                   			fax             
//			merchant_id                 			phone           
//			outlet_id                   			contact_name    
//			area_id                     			contact_phone   
//			order_value                 			contact_email   
//			outlet_name                 			service_provider
//			outlet_fullname             			longitude       
//			outlet_status               			latitude        
//			address                     			is_enable       
//			business_hours              			description                         			
//			prefer_details                          discount  
//  			prefer_period                       store_count
  			      
			// 新增
			MerchantVo vo = new MerchantVo();
//			vo.setId(100000063);
			vo.setClientId("anhui");
			vo.setCreateTime(1427957465433l);
			vo.setMerchantId("10000002");
			vo.setProOrgCode("340000");
			vo.setCityOrgCode("");
			vo.setCountyOrgCode("");
			vo.setOrgCode("340000");
			vo.setMerchantName("安徽省农村信用社");
			vo.setMerchantFullname("安徽省农村信用社");
			vo.setLogo("");
			vo.setAddress("安徽省,合肥市");
			vo.setPhone("15532226999");
			vo.setAreaId(100000011);
			vo.setMerchantStatus(true);
			vo.setIsEnable(true);
			vo.setDisableStatus("0");
			vo.setIsTop(false);
			vo.setIntroduce("安徽省农村信用社");
			vo.setLicense("11952766699");
			vo.setTaxReg("2211195233331");
			vo.setContractBegintime(111111l);
			vo.setStartContractBegintime(System.currentTimeMillis());
			vo.setContractEndtime(9927957465433l);
			vo.setContractStaff("张三");
			vo.setAuditStartOrgCode("0");
			vo.setAuditEndOrgCode("0");
			vo.setAuditState("1");
			vo.setAuditOrgCode("0");
			vo.setAuditStaff("");
			vo.setAuditTime(0);
			vo.setAuditComment("");
			vo.setReviewStaff("boduolaoshi");
			vo.setContactName("luofan");
			vo.setContactPhone("15900000000");
			vo.setContactEmail("fange@438.com.cn");
			vo.setLegalName("fange");
			vo.setLegalCredentType(1);
			vo.setLegalCredentNo("3325115457852122322");
			vo.setComplaintPhone("15954782214");
			
			merchantVoReq.setMerchantVo(vo);
			
			TypeInfoVo type = new TypeInfoVo();
			type.setMerchantTypeId(100000000l);
//			type.setTypeName("团购");
			List<TypeInfoVo> typeInfo = new ArrayList<TypeInfoVo>();
			typeInfo.add(type);
			
			merchantVoReq.setTypeInfoVoList(typeInfo);
			
			List<CategoryInfoVo> categoryInfo = new ArrayList<CategoryInfoVo>();
			CategoryInfoVo category = new CategoryInfoVo();
			category.setCategoryId(100000001l);
//			category.setName("餐饮美食");
			categoryInfo.add(category);
			
			merchantVoReq.setCategoryInfoVoList(categoryInfo);
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.bank);
			originVo.setOperatorId(100000000);
			originVo.setOperatorIp("192.168.19.105");
			originVo.setDescription("添加商户");
//			System.out.println(service.addMerchant(originVo , merchantVoReq));
			
			
			System.out.println(service.getMerchantTypeInfo("0308393E8000"));
			
			
//			PropertiesUtil.load();
			MerchantDetailMongo m = new MerchantDetailMongo();
			MongoPage mongoPage = new MongoPage (); 
			MerchantDetail merchantDetail = new MerchantDetail();
			merchantDetail.setMerchantName("测商");
//			System.out.println(com.alibaba.fastjson.JSON.toJSONString(m.findMerchantDetailByPage(mongoPage, merchantDetail), true));
			
			
			
//			System.out.println(service.disableMerchantByMerchantId(originVo, "0266990F0000"));
			
//			PageVo p = new PageVo();
//			OutletDetailVo outletDetailVo = new OutletDetailVo();
//			outletDetailVo.setMerchantId("0027b3180000");
//			outletDetailVo.setClientId(1000);
//			service.getOutletDetailByPage(p, outletDetailVo);
//			System.out.println(JSON.toJSONString(service.getOutletDetail("005680958000")));
//			service.getOutletDetailByPage(page, outletDetailVo);
//			System.out.println();
//			service.updateOutlet(vo);
//			service.deleteOutlet(vo);
//			System.out.println( java.util.Calendar.getInstance().getTimeInMillis());
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
