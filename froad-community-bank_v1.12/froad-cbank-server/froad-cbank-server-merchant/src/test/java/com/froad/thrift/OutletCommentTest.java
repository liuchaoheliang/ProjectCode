package com.froad.thrift;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.junit.Test;

import com.froad.thrift.service.OutletCommentService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletCommentVo;
import com.froad.thrift.vo.PlatType;

public class OutletCommentTest {
	
	/**
	 * 测试面对面评论中是否已进行了评论.
	 */
	@Test
	public void testIsExitsFaceToFaceComment(){
		String memberCode = "52001970895";
		String outletId = "0DDC16F40000";
//		String orderId = null;
		String orderId = "0D821DA18000";
		
//		String memberCode = "50002969529";
//		String outletId = "073B8E920089";
		try {
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"OutletCommentService");
			OutletCommentService.Client service = new OutletCommentService.Client(mp);

			transport.open();
			boolean isExits = service.isExitsFaceToFaceComment(memberCode, outletId, orderId);
		    //org.junit.Assert.assertTrue(isExits == true);
			System.out.println(isExits);
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public void testGetOutletCommentPageByOrgCode(){
		try {
//			TSocket transport = new TSocket("localhost", 9091);
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"OutletCommentService");
			OutletCommentService.Client service = new OutletCommentService.Client(mp);
			
			transport.open();
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.bank);
			originVo.setOperatorId(100000000);
			originVo.setOperatorIp("192.168.19.105");
			
			// 新增
			OutletCommentVo outletCommentVo = new OutletCommentVo(); 
			outletCommentVo.setMerchantId("0DDBE1738000");
			outletCommentVo.setMerchantName("0922商户");
			outletCommentVo.setOutletId("0DDC16F40000"); // 00934e820000 00936a220000 009550b58000
			outletCommentVo.setOutletName("晶晶门店");
			outletCommentVo.setOrderValue(0);
			outletCommentVo.setMemberCode("52001970895");
			outletCommentVo.setMemberName("yyjj2嚯");
			outletCommentVo.setClientId("chongqing");
			outletCommentVo.setCommentDescription("不错55555555555");
			outletCommentVo.setStarLevel(3);
			outletCommentVo.setCreateTime(new java.util.Date().getTime());
			outletCommentVo.setOutletPhone("13800138000");
			outletCommentVo.setCommentType(1);
			outletCommentVo.setOrderId("0D821DA18000");
			System.out.println(service.addOutletComment(originVo, outletCommentVo));
			
			// 增加回复
//			OutletCommentVo outletCommentVo = new OutletCommentVo();
//			outletCommentVo.setId("007ff6548000");
//			outletCommentVo.setRecomment("感谢您的反馈 商家会及时改进");
//			outletCommentVo.setRecommentTime(new java.util.Date().getTime());
//			outletCommentVo.setMerchantUserName("zhaoky");
//			boolean result = service.addOutletCommentOfRecomment(outletCommentVo);
//			System.out.println("增加回复操作结果 " + result);
			
			// 查询门店评论数量
//			OutletCommentVo outletCommentVo = new OutletCommentVo(); 
//			outletCommentVo.setMerchantId("10021547");
//			outletCommentVo.setOutletId("2010057");
//			outletCommentVo.setMemberCode("200155");
//			int result = service.getOutletCommentSum(outletCommentVo);
//			System.out.println("门店评论数量 " + result);
			
			// 删除
//			OriginVo originVo = new OriginVo();
//			originVo.setPlatType(PlatType.bank);
//			originVo.setOperatorId(100000000);
//			originVo.setOperatorIp("192.168.19.105");
////			System.out.println("删除操作结果 " + JSON.toJSONString(originVo));
//			
//			
//			OutletCommentVo outletCommentVo = new OutletCommentVo();
//			
//			outletCommentVo.setOutletId("055628750000");
//			
//			System.out.println(service.getOutletCommentByPage(new PageVo(), outletCommentVo));
			
			// 修改
//			OutletCommentVo outletCommentVo = new OutletCommentVo();
//			outletCommentVo.setId("00A95D308000");
//			outletCommentVo.setOutletImage("http://192.168.18.180/img/3b43ad42-4e9c-471a-9130-a5478ef7cb9c-thumbnail.JPG");
//			boolean result = service.updateOutletComment(outletCommentVo);
//			System.out.println("修改操作结果 " + result);
			
			
			
			// 查多条
//			OutletCommentVo outletCommentVo = new OutletCommentVo(); 
//			outletCommentVo.setMerchantId("0027c9c80000");
//			outletCommentVo.setOutletId("0027c9c60000");
			// 回复不是为空
//			RecommentNotEmptyVo recommentNotEmpty = new RecommentNotEmptyVo();
//			recommentNotEmpty.setNotEmpty(true);
//			outletCommentVo.setRecommentNotEmpty(recommentNotEmpty);
			// 等级
//			StarLevelFilterVo starLevelFilter = new StarLevelFilterVo();
//			starLevelFilter.setStarLevel(1);
//			outletCommentVo.setStarLevelFilter(starLevelFilter);
			// 开始结束时间
//			CreateTimeFilterVo createTimeFilter = new CreateTimeFilterVo();
//			createTimeFilter.setBegTime(1428916643500l);
//			createTimeFilter.setEndTime(1428917108375l);
//			outletCommentVo.setCreateTimeFilter(createTimeFilter);
			
//			List<OutletCommentVo> ooList = service.getOutletComment(outletCommentVo);
//			if( ooList != null && ooList.size() > 0 ){
//				int index = 1;
//				for( OutletCommentVo oo : ooList ){
//					System.out.println((index++)+" - "+JSON.toJSONString(oo));
//				}
//			}else{
//				System.out.println("null data");
//			}
			
			
			// 查一条
//			OutletCommentVo outletCommentVo = service.getOutletCommentById("00406a208000");
//			System.out.println(JSON.toJSONString(outletCommentVo));
			
			
			// 分页查询
//			PageVo page = new PageVo();
//			page.setPageNumber(1);
//			page.setPageSize(10);
//			OutletCommentVo outletCommentVo = new OutletCommentVo(); 
//			outletCommentVo.setOutletId("006c49910000");
//			OutletCommentPageVoRes res = service.getOutletCommentByPage(page, outletCommentVo);
//			System.out.println(JSON.toJSONString(res));
			
			// 查询门店评论级别数量
//			OutletCommentVo outletCommentVo = new OutletCommentVo(); 
//			outletCommentVo.setMerchantId("10021547");
//			outletCommentVo.setOutletId("2010059");
//			OutletCommentLevelAmountVo outletCommentLevelAmountVo = service.getOutletCommentLevelAmount(outletCommentVo);
//			System.out.println("门店评论级别数量" + JSON.toJSONString(outletCommentLevelAmountVo));
			
			// 商户评论级别数量查询
//			List<OutletCommentLevelAmountVo> outletCommentLevelAmountVoList = service.getMerchantCommentLevelAmount("10021547");
//			System.out.println("商户评论级别数量" + JSON.toJSONString(outletCommentLevelAmountVoList));
			
			// 分页查询 - 用 orgCode
//			PageVo page = new PageVo();
//			page.setPageNumber(1);
//			page.setPageSize(10);
//			OutletCommentVo outletCommentVo = new OutletCommentVo(); 
//			outletCommentVo.setOrgCode("000000,050000,010000,010103");
//			outletCommentVo.setClientId("chongqing");
//			OutletCommentPageVoRes res = service.getOutletCommentPageByOrgCode(page, outletCommentVo);
//			System.out.println(JSON.toJSONString(res));
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}