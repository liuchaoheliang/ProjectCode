package com.froad.test.ticket;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.ModuleID;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.handler.TicketHandler;
import com.froad.handler.TicketQueryHandler;
import com.froad.handler.impl.TicketHandlerImpl;
import com.froad.handler.impl.TicketQueryHandlerImpl;
import com.froad.logic.SmsLogic;
import com.froad.logic.TicketLogic;
import com.froad.logic.impl.SmsLogicImpl;
import com.froad.logic.impl.TicketLogicImpl;
import com.froad.po.Ticket;
import com.froad.support.TicketSupport;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.ticket.TicketDetailRequestVo;
import com.froad.thrift.vo.ticket.TicketDetailResponseVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketListResponseVo;
import com.froad.thrift.vo.ticket.TicketSummaryVo;
import com.froad.thrift.vo.ticket.TicketVerifyRequestVo;
import com.froad.util.DateUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.SimpleID;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class TicketClient {

	public static void main(String[] args) {
		PropertiesUtil.load();
		
//		insertTicket();
//		ticketQuery();
		generateTicket();
//		System.out.println(DateUtil.skipDateTime(new Date(), 7).getTime());
//		sendSms();
//		verifyTicket();
//		try {
//			getTicketList(null);
//		} catch (TException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		TicketSupport support = null;
//		support = new TicketSupportImpl();
//		DBObject queryObj = null;
//		queryObj = new BasicDBObject("status", new BasicDBObject("$ne", "0"));
//		List<Ticket> ticketList = support.getListByDBObject(queryObj);
//		System.out.println(ticketList);
		
//		MongoManager manager = null;;
//		MongoPage page = null;
//		DBObject queryObj = null;
//		
//		
//		page = new MongoPage();
//		page.setCurrentPage(1);
//		page.setPageSize(2);
//		
//		queryObj = new BasicDBObject();
//		queryObj.put("area_id", 100000006L);
//		
//		manager = new MongoManager();
//		manager.findArrayByPage("cb_area_products", queryObj, "product_ids", page);
	}
	
	public static void ticketQuery(){
		TTransport transport = null;
		TProtocol protocol = null;
		TMultiplexedProtocol multiProtocol = null;
		TicketService.Client client = null;
		insertTicket();
        try { 
            // 设置调用的服务地址为本地，端口为 8899 
            transport = new TSocket("10.43.2.3", 9094); 
            transport.open(); 
            
            // 设置传输协议为 TBinaryProtocol 
            protocol = new TBinaryProtocol(transport); 
            multiProtocol = new TMultiplexedProtocol(protocol,"TicketService");
            client = new TicketService.Client(multiProtocol);
            
            // 调用服务的 获取券详情方法
//            getTicketDetails(client);
            
            // 获取券列表
            getTicketList(client);
            
            transport.close(); 
        } catch (TTransportException e) { 
            e.printStackTrace(); 
        } catch (TException e) { 
            e.printStackTrace(); 
        } 
	}
	
	public static void insertTicket(){
		TicketSupport ticketSupport = null;
		Ticket ticket = null;
		
		ticketSupport = new TicketSupportImpl();
		
		ticket = new Ticket();
		ticket.setClientId("1000");
		ticket.setCreateTime(DateUtil.skipDateTime(new Date(), -6).getTime());
		ticket.setExpireTime(DateUtil.skipDateTime(new Date(), 5).getTime());
		ticket.setMemberCode("9521");
		ticket.setMemberName("张三");
		ticket.setMerchantId("00268fa60000");
		ticket.setMerchantName("安徽省农村信用社");
		ticket.setMerchantUserId(1472583691L);
		ticket.setMobile("13800138000");
		ticket.setOrderId(new SimpleID(ModuleID.order).nextId());
		ticket.setProductId("100000000");
		ticket.setProductName("iphone 6");
		ticket.setSubOrderId(new SimpleID(ModuleID.suborder).nextId());
		ticket.setTicketId(new SimpleID(ModuleID.ticket).nextId());
		ticket.setOutletId("0026c6260000");
		ticket.setOutletName("锦江之星花木店");
		
		ticket.setType(SubOrderType.group_merchant.getCode());
		ticket.setQuantity(1);
		ticket.setStatus(TicketStatus.sent.getCode());
		
		ticketSupport.addTicket(ticket);
	}
	
	public static void getTicketDetails(TicketService.Client client) throws TException{
		TicketDetailRequestVo requestVo = null;
		TicketDetailResponseVo responseVo = null;
		
		requestVo = new TicketDetailRequestVo();
		requestVo.setOption("1");
		requestVo.setClientId("1000");
		requestVo.setTicketId("003ea0f08000");
		
		responseVo = client.getTicketDetails(requestVo);
	}
	
	public static void getTicketList(TicketService.Client client) throws TException{
		TicketListRequestVo requestVo = null;
		TicketListResponseVo responseVo = null;
		
		requestVo = new TicketListRequestVo();
		requestVo.setClientId("1000");
//		requestVo.setSource("1");
//		requestVo.setMemberCode("9527");
		
//		requestVo.setSource("2");
//		requestVo.setStartDate(String.valueOf(0));
//		requestVo.setEndDate(String.valueOf(System.currentTimeMillis()));
		
//		requestVo.setSource("3");
//		requestVo.setMerchantId("0026a2770000");
		
		requestVo.setSource("4");
		requestVo.setClientId("1000");
		requestVo.setOrgCode("3405177879");
//		responseVo = client.getTicketList(requestVo);
		
		TicketQueryHandler handler = null;
		handler = new TicketQueryHandlerImpl();
		handler.findListWithPage(requestVo);
	}

	public static void generateTicket(){
		TicketLogic ticketLogic = null;
		
		ticketLogic = new TicketLogicImpl();
//		ticketLogic.addTicket("013C85CF0000");
	}
	
	public static void verifyTicket(){
		TicketVerifyRequestVo request = null;
		List<TicketSummaryVo> voList = null;
		TicketSummaryVo vo = null;
		TicketHandler handler = null;
		
		request = new TicketVerifyRequestVo();
		request.setResource("2");
		request.setClientId("1000");
		request.setMerchantId("00932ef20000");
		request.setMerchantUserId(9527);
		request.setOutletName("肥西农村商业银行肥光支行 ");
		request.setOutletId("00948cb60000");
		voList = new ArrayList<TicketSummaryVo>();
		//
		vo = new TicketSummaryVo();
		vo.setTicketId("012B52E30000");
		vo.setProductId("01126E450000");
		vo.setQuantity(1);
		voList.add(vo);
		
		vo = new TicketSummaryVo();
		vo.setTicketId("012B54630000");
		vo.setProductId("011252A50000");
		vo.setQuantity(1);
		voList.add(vo);
//		// 
//		vo = new TicketSummaryVo();
//		vo.setTicketId("00A4E79A8000");
//		vo.setProductId("0093b77f0000");
//		vo.setQuantity(1);
//		voList.add(vo);
//		// 
//		vo = new TicketSummaryVo();
//		vo.setTicketId("00A4E81A8000");
//		vo.setProductId("0093b77f0000");
//		vo.setQuantity(1);
//		voList.add(vo);
		
		request.setTicketList(voList);
		
		handler = new TicketHandlerImpl();
		handler.verifyTickets(request);
	}
	
	public static void sendSms(){
		SmsLogic smsLogic = new SmsLogicImpl();
		smsLogic.sendSMS("13024323415", 1109, "1000", null);
	}
}
