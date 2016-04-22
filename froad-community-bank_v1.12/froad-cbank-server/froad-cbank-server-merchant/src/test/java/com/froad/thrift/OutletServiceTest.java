package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.junit.Assert;

import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletAddVoRes;
import com.froad.thrift.vo.OutletPreferVo;
import com.froad.thrift.vo.OutletTempVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.ResultVo;


public class OutletServiceTest {


	public static void main(String[] args) {
		try {
//			TSocket transport = new TSocket("10.43.1.122", 15201);
//			TSocket transport = new TSocket("10.24.248.186", 15201);
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol, OutletService.class.getSimpleName());
			OutletService.Client service = new OutletService.Client(mp);

			transport.open();
			
			//==============================获取商户分类 By h5 ==========================================
//			MerchantCategoryVo mcv = new MerchantCategoryVo();
//			mcv.setClientId("chongqing");
//			mcv.setParentId(0);
//			mcv.setAreaId(0);			
//			mcv.setClientId("chongqing");
//			mcv.setParentId(100000020);
//			mcv.setAreaId(2095);	
			
//			List<MerchantCategoryVo> x = service.getMerchantCategoryByH5(mcv);
			
			//===============================新增门店=======================================
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.merchant_pc);
			originVo.setOperatorId(100155813);
			originVo.setOperatorIp("10.24.239.38");
			originVo.setDescription("添加门店");
			originVo.setClientId("chongqing");

			OutletVo vo = new OutletVo();
			vo.setClientId("chongqing");
			vo.setMerchantId("0DDBE1738000");
			vo.setAreaId(2107);
			vo.setOutletName("测试");
			vo.setOutletFullname("测试");
			vo.setOutletStatus(false);
			vo.setAddress("珠海啦");
			vo.setBusinessHours("2016-01-02");
			vo.setFax("0756-3023023023");
			vo.setPhone("13323023232");
			vo.setContactName("测试");
			vo.setContactPhone("020-3020322");
			vo.setLongitude("106.274508");
			vo.setLatitude("30.001284");
			vo.setIsEnable(false);
			vo.setDisableStatus("0");
			vo.setDescription("开挖机来世纪公园");
			vo.setPreferDetails("开挖机来世纪公园");
			vo.setPreferStartPeriod(0);
			vo.setPreferEndPeriod(0);
			vo.setDiscountCode("1");
			vo.setDiscountRate("1");
			vo.setAuditState("3");
			
			List<CategoryInfoVo> listCiv = new ArrayList<CategoryInfoVo>();
			CategoryInfoVo civ = new CategoryInfoVo();
			civ.setCategoryId(100000044);
			listCiv.add(civ);
			vo.setCategoryInfo(listCiv);
			
			service.addOutlet(originVo, vo);
			
			
			//===============================查询门店=======================================
//			OutletVo queryVo = service.getOutletByOutletId("167A29440000");
//			System.err.println(JSonUtil.toJSonString(queryVo));
			
			
			//===============================惠付开通与关闭=======================================
//			OriginVo originVo = new OriginVo();
//			originVo.setPlatType(PlatType.merchant_pc);
//			originVo.setOperatorId(100383864);
//			originVo.setOperatorIp("127.0.0.1");
//			originVo.setDescription("更新门店");
//			originVo.setClientId("chongqing");
//			
//			
//			OutletVo updateVo = new OutletVo();
//			updateVo.setClientId("chongqing");
//			updateVo.setOutletId("167A29440000");
//			updateVo.setPreferStatus(false);
//			
//			service.updateOutlet(originVo, updateVo);
			
			//===============================分页查询=======================================
			
//			PageVo p = new PageVo();
//			OutletDetailVo outletDetailVo = new OutletDetailVo();
//			outletDetailVo.setMerchantId("0027b3180000");
//			outletDetailVo.setClientId(1000);
//			service.getOutletDetailByPage(p, outletDetailVo);
//			System.out.println(JSON.toJSONString(service.getOutletDetail("006c49910000")));
//			service.getOutletDetailByPage(page, outletDetailVo);
//			System.out.println();
//			service.updateOutlet(vo);
//			service.deleteOutlet(vo);
//			System.out.println( java.util.Calendar.getInstance().getTimeInMillis());
			
			//0.08938085154015578
//			OutletDetailVo outletDetailVo = new OutletDetailVo();
//			LocationVo location = new LocationVo();
//			location.setLongitude(101.01650000000001);
//			location.setLatitude(50.006200000000007);
//			outletDetailVo.setLocation(location);
//			outletDetailVo.setOutletName("可丽");
//			outletDetailVo.setAreaId(100000015l);
//			outletDetailVo.setId("05C2B130800A");
//			location.setLongitude(121.55671956912);
//			121.56035177762
//			31.228206194397
//			location.setLatitude(31.230481337318);
//			outletDetailVo.setLocation(location);
//			outletDetailVo.setOutletName("可丽");
//			outletDetailVo.setAreaId(100000015l);
//			outletDetailVo.setId("05C2B130800A");
//			outletDetailVo.setIsEnable(true);
//			outletDetailVo.setClientId("anhui");
//			outletDetailVo.setOutletName("ktv");
//			outletDetailVo.setParentAreaId(2507l);
//			outletDetailVo.setClientId("anhui");
//			outletDetailVo.setOutletName("ktv");
//			PageVo page = new PageVo();
//			page.setPageNumber(1);
//			page.setPageSize(10);
//			OutletDetailSimplePageVoRes odvs = service.getNearbyOutletByPage(page, outletDetailVo, 0, -1);
//			System.out.println(JSON.toJSONString(odvs,true));
			
			
//			System.out.println(service.getSubBankOutlet("anhui", "340101"));
//			double longitude = 121.542;
//			double latitude = 31.209;
//			double longitude = 101.01650000000001;
//			double latitude = 50.006200000000007;
//			page.setPageSize(3);
//			System.out.println(JSON.toJSON(service.getOutletMongoInfoVoByPage(page, 0, 0, "04B456B00000")));
//			double longitude = 121.542;
//			double latitude = 31.209;
//			PageVo page = new PageVo();
//			page.setPageSize(3);
//			System.out.println(JSON.toJSON(service.getOutletMongoInfoVoByPage(page, longitude, latitude, "051B6A638000")));
//			OutletVo o = new OutletVo();
//			o.setOutletId("035CF57F0000");
//			o.setMerchantId("035CF57F0000");
//			System.out.println(service.deleteOutlet(new OriginVo(), o));
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@org.junit.Test
	public void testGetOutletExport(){
		try {
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"OutletService");
			OutletService.Client service = new OutletService.Client(mp);
			transport.open();
			
			OutletPreferVo outletVo = new OutletPreferVo();
			outletVo.setClientId("chongqing");
			
			ExportResultRes exportResulteRes=	service.getOutletExport(outletVo);
//			System.out.println("url="+exportResulteRes.getUrl());
			Assert.assertNotNull(exportResulteRes.getUrl());
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@org.junit.Test
	public void testGetOutletModifyInfoByAuditBox(){
		try {
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"OutletService");
			OutletService.Client service = new OutletService.Client(mp);
			transport.open();
			
			
			OutletTempVo outletTempVo =	service.getOutletModifyInfoByAuditBox("10BB84C18000", "2015121500000002");
//			System.out.println("url="+exportResulteRes.getUrl());
//			System.out.println(outletTempVo.getOutletName());
//			Assert.assertNotNull(outletTempVo);
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	@org.junit.Test
	public void testGetOutletModifyInfos(){
		try {
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"OutletService");
			OutletService.Client service = new OutletService.Client(mp);
			transport.open();
			
			
			OutletTempVo outletTempVo =	service.getOutletModifyInfos("130966C18000");
//			System.out.println("url="+exportResulteRes.getUrl());
			System.out.println(outletTempVo.getOutletName());
			Assert.assertNotNull(outletTempVo);
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 新增门店mongo缓存冗余优惠折扣比、优惠折扣码信息测试.
	 */
	@org.junit.Test
	public void testaddOutletInfo(){
		try{
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"OutletService");
			OutletService.Client service = new OutletService.Client(mp);
			transport.open();
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.merchant_pc);
			originVo.setOperatorId(100155813);
			originVo.setOperatorIp("10.24.239.38");
			originVo.setDescription("添加门店");
			originVo.setClientId("chongqing");

			OutletVo vo = new OutletVo();
			vo.setClientId("chongqing");
			vo.setMerchantId("10BB84520001");
			vo.setAreaId(2105);
			vo.setOutletName("测试新增门店冗余优惠信息");
			vo.setOutletFullname("测试新增门店冗余优惠信息");
			vo.setOutletStatus(false);
			vo.setAddress("珠海啦");
			vo.setBusinessHours("2016-01-27");
			vo.setFax("0756-3023023023");
			vo.setPhone("13323023232");
			vo.setContactName("测试");
			vo.setContactPhone("020-3020322");
			vo.setLongitude("106.274508");
			vo.setLatitude("30.001284");
			vo.setIsEnable(false);
			vo.setDisableStatus("0");
			vo.setDescription("test");
			vo.setPreferDetails("test");
			vo.setPreferStartPeriod(0);
			vo.setPreferEndPeriod(0);
			vo.setDiscountCode("12");
			vo.setDiscountRate("11");
			vo.setAuditState("3");
			
			List<CategoryInfoVo> listCiv = new ArrayList<CategoryInfoVo>();
			CategoryInfoVo civ = new CategoryInfoVo();
			civ.setCategoryId(100000044);
			listCiv.add(civ);
			vo.setCategoryInfo(listCiv);
			
			OutletAddVoRes outletAddVoRes = service.addOutlet(originVo, vo);	
			Assert.assertNotNull(outletAddVoRes);		

			System.out.println("outletId="+outletAddVoRes.getOutletId());			
			
			transport.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 修改门店操作mongo冗余缓存优惠折扣比、优惠折扣码信息测试.
	 */
	@org.junit.Test
	public void testUpdateOutletInfo(){
		try{
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"OutletService");
			OutletService.Client service = new OutletService.Client(mp);
			transport.open();
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.merchant_pc);
			originVo.setOperatorId(100155813);
			originVo.setOperatorIp("10.24.239.38");
			originVo.setDescription("添加门店");
			originVo.setClientId("chongqing");

			OutletVo vo = new OutletVo();
			vo.setOutletId("184CE9A30000");
			vo.setClientId("chongqing");
			vo.setMerchantId("10BB84520001");
			vo.setAreaId(2105);
			vo.setOutletName("测试新增门店冗余优惠信息");
			vo.setOutletFullname("测试新增门店冗余优惠信息");
			vo.setOutletStatus(false);
			vo.setAddress("珠海啦");
			vo.setBusinessHours("2016-01-27");
			vo.setFax("0756-3023023023");
			vo.setPhone("13323023232");
			vo.setContactName("测试");
			vo.setContactPhone("020-3020322");
			vo.setLongitude("106.274508");
			vo.setLatitude("30.001284");
			vo.setIsEnable(false);
			vo.setDisableStatus("0");
			vo.setDescription("test");
			vo.setPreferDetails("test");
			vo.setPreferStartPeriod(0);
			vo.setPreferEndPeriod(0);
			vo.setDiscountCode("22");
			vo.setDiscountRate("33");
			vo.setAuditState("3");
			
			List<CategoryInfoVo> listCiv = new ArrayList<CategoryInfoVo>();
			CategoryInfoVo civ = new CategoryInfoVo();
			civ.setCategoryId(100000044);
			listCiv.add(civ);
			vo.setCategoryInfo(listCiv);
			
			ResultVo resultVo = service.updateOutlet(originVo, vo);	
			Assert.assertTrue(resultVo.getResultCode().equals("0000"));		
			
			transport.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}	
}