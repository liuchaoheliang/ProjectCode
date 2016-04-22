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
 * @Title: Test.java
 * @Package com.froad.test.ticket
 * @Description: TODO
 * @author vania
 * @date 2015年6月8日
 */

package com.froad.test.ticket;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ModuleID;
import com.froad.enums.ProductType;
import com.froad.enums.TicketStatus;
import com.froad.po.Ticket;
import com.froad.support.TicketSupport;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ticket.TicketDetailVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketListResponseVo;
import com.froad.thrift.vo.ticket.TicketSummaryVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerRequestVo;
import com.froad.util.PropertiesUtil;
import com.froad.util.SimpleID;


/**    
 * <p>Title: Test.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年6月8日 下午5:21:01   
 */

public class TicketTest {
	static {
		PropertiesUtil.load();
	}
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
		TSocket transport = null;
		try {
			String host= "10.43.2.3";
			int port = 0;
			host = "127.0.0.1";
//			host = "10.43.1.9";
//			host = "10.24.248.189";
			port = 15501;
			transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"TicketService");
			TicketService.Client service = new TicketService.Client(mp);
			transport.open();
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.merchant_h5);
			originVo.setOperatorId(100000000);
			originVo.setOperatorIp("192.168.19.105");
			originVo.setDescription("团购验券");
			
			
			TicketListRequestVo ticketListRequestVo = new TicketListRequestVo();
			ticketListRequestVo.setClientId("anhui");
			ticketListRequestVo.setResource("2");
			ticketListRequestVo.setSource("3");
			ticketListRequestVo.setType("1");
			ticketListRequestVo.setStatus("2");
//			ticketListRequestVo.setMemberCode("52001974302");
			ticketListRequestVo.setMerchantId("0D5473C40000");
//			ticketListRequestVo.setSubOrderId("078394138000");
//			ticketListRequestVo.setTicketId("166030000004");
//			ticketListRequestVo.setOutletId("222222");
//			ticketListRequestVo.setOrgCode("340000");
			ticketListRequestVo.setSortField("1");
			long currDate = System.currentTimeMillis();
			long startDate = 1434902400000l;
			long endDate = 1442764799999l;
			ticketListRequestVo.setStartDate(String.valueOf(startDate));
			ticketListRequestVo.setEndDate(String.valueOf(endDate));
//			ticketListRequestVo.setStartDate("1433692800000");
//			ticketListRequestVo.setEndDate("2433779199999");
//			ticketListRequestVo.setStatus("1");
//			ticketListRequestVo.setType("1");
			
			PageVo pageVo = new PageVo();
			pageVo.setPageNumber(1);
			pageVo.setPageSize(10);
//			pageVo.setTotalCount(12761);
			pageVo.setPageCount(0);
			pageVo.setBegDate(0);
			pageVo.setEndDate(0);
//			pageVo.setLastPageNumber(62);
//			pageVo.setFirstRecordTime(1423196655000l);
//			pageVo.setLastRecordTime(1423182348000l);
			pageVo.setHasNext(false);
//			ticketListRequestVo.setPageVo(pageVo);
			ticketListRequestVo.setPageVo(null);
			
			
			/*********************************getTicketList*************************************/
//			TicketListResponseVo res = service.getTicketList(ticketListRequestVo);
//			List<TicketDetailVo> list = res.getTicketDetailList();
////			System.out.println("第一条:" + JSON.toJSONString(list.get(0), true));
////			System.out.println("最后一条:" + JSON.toJSONString(list.get(list.size()-1), true));
//			System.out.println("getTicketList返回结果:"+ JSON.toJSONString(res, true));
			
			
			/*********************************exportTickets*************************************/
//			ExportResultRes resultRes = service.exportTickets(ticketListRequestVo);
//			System.out.println("exportTickets返回结果:"+ JSON.toJSONString(resultRes, true));
			
			
			/*********************************getVerifyTicketListByPage*************************************/
//			System.out.println("getVerifyTicketListByPage返回结果:"+ JSON.toJSONString(service.getVerifyTicketListByPage(ticketListRequestVo), true));

			
			/*********************************getTicketListOfMerger*************************************/
//			System.out.println("getTicketListofMerger返回结果:"+ JSON.toJSONString(service.getTicketListOfMerger(ticketListRequestVo), true));
			
			
			/*********************************verifyGroupTicketsOfMerger*************************************/
			TicketVerifyOfMergerRequestVo  requestVo = new TicketVerifyOfMergerRequestVo();
			requestVo.setMemberCode("52001970709");
			requestVo.setOriginVo(originVo);
			requestVo.setMerchantUserName("vania666");
			requestVo.setMustValidTicketId("051825371260");
			requestVo.setMerchantId("0DEAA9200000");
//			requestVo.setSubOrderId("11CA58F18000");
//			requestVo.setOutletId("DFDFGDFGDFG");
//			requestVo.setOutletName("XXX玉兰路店");
			
			List<TicketSummaryVo> ticketList = new ArrayList<TicketSummaryVo>();
			TicketSummaryVo t1 = new TicketSummaryVo();
			t1.setProductId("102567E40000");
			t1.setQuantity(9); // 提货数量
			ticketList.add(t1);
			
			requestVo.setTicketList(ticketList);
			System.out.println("verifyGroupTicketsOfMerger返回结果:"+ JSON.toJSONString(service.verifyGroupTicketsOfMerger(requestVo), true));
			
			
//			addTicket(52222);
		}catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(transport != null)transport.close();
		}
	}
	
	public static void addTicket(int count){
		TicketSupport ticketSupport = new TicketSupportImpl();
		SimpleID simpleId = new SimpleID(ModuleID.ticket);
		String[] products = { "兰州拉面-泪牛满面", "阿牛嫂-蘑菇炒肉", "东池-鸡腿饭", "湖南米粉-小炒茄子", "毛氏红烧肉", "星星小厨-山药骨髓汤", "上海大众-打死奥拓", "东昌宝马-S320" };
		// startDate:1435075200000, endDate:1442937599999
		for (int i = 0; i<count;i++) {
			Ticket ticket = new Ticket();
//			Long createTime = ((long) (Math.random() * 1000000000)) + 1440000000000l - 100000l;
			Long createTime = 1440000000000l - (i * 10);
			ticket.setCreateTime(createTime);
			ticket.setClientId("chongqing");
			ticket.setMerchantId("0DD300B28000");
			ticket.setMerchantName("测试团购券");
			ticket.setMemberCode(String.valueOf(50000002));
			ticket.setMemberName("vania");//提货人
			ticket.setOrderId(((int) (Math.random() * 1000000000)) + "");
			ticket.setSubOrderId(((int) (Math.random() * 1000000000)) + "");
			ticket.setMobile("13022222222");
			String ticketId = simpleId.nextId();
			ticket.setTicketId(ticketId);
			ticket.setType(ProductType.group.getCode());
			int pd = new Random().nextInt(products.length);			
			ticket.setProductId("ABC"+StringUtils.leftPad(i + "", 8, '0'));
			ticket.setProductName("商品" + StringUtils.leftPad(i + "", 5, '0'));
			ticket.setQuantity(1);// 一个团购商品对应一张券
			ticket.setStatus(TicketStatus.consumed.getCode());//
			ticket.setExpireTime(1435323200000l);
			
			ticket.setMerchantUserId(32870l);
			ticket.setMerchantUserName("bmdbjsh");
			// 1442678400   2015-09-20 00:00:00
			Long consumeTime = 1442678400000l - (i * 10000);
			ticket.setConsumeTime(consumeTime);
//			if (null != orgLevelMap && orgLevelMap.size() > 0){
//				if (null != orgLevelMap.get(OrgLevelEnum.orgLevel_one)){
//					ticket.setForgCode(orgLevelMap.get(OrgLevelEnum.orgLevel_one));
//				} else if (null != orgLevelMap.get(OrgLevelEnum.orgLevel_two)){
//					ticket.setForgCode(orgLevelMap.get(OrgLevelEnum.orgLevel_two));
//				} else if (null != orgLevelMap.get(OrgLevelEnum.orgLevel_two)){
//					ticket.setForgCode(orgLevelMap.get(OrgLevelEnum.orgLevel_two));
//				} else if (null != orgLevelMap.get(OrgLevelEnum.orgLevel_three)){
//					ticket.setForgCode(orgLevelMap.get(OrgLevelEnum.orgLevel_three));
//				}
//			}
			
			ticket.setPrice(new Random().nextInt(100000));
			
			System.out.println("添加第" + i + "条:" + JSON.toJSONString(ticket));
			// 添加团购券
			ticketSupport.addTicket(ticket);
		}
	}

}
