package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.junit.Assert;

import com.froad.enums.PlatTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.vo.CategoryInfoVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.MerchantAddVoRes;
import com.froad.thrift.vo.MerchantPageVoRes;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.MerchantVoReq;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletAddVoRes;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.TypeInfoVo;
import com.froad.util.Md5Utils;

public class MerchantServiceTest {
	



	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantService");
			MerchantService.Client service = new MerchantService.Client(mp);
			transport.open();
			
			//分页查询
//			getMerchantByPage(service);
			
			//商户信息查询
//			getMerchantByMerchantId(service);
			
			//新增商户
//			addMerchant(service);
			
			
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	/**
	 * 商户分页查询
	 * @param service
	 * @throws Exception
	 */
	public static void getMerchantByPage(MerchantService.Client service) throws Exception{

		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(10);
		pageVo.setTotalCount(0);
		pageVo.setPageCount(0);
		pageVo.setBegDate(0);
		pageVo.setEndDate(0);
		pageVo.setLastPageNumber(0);
		pageVo.setFirstRecordTime(0);
		pageVo.setLastRecordTime(0);
		pageVo.setHasNext(false);
		
		MerchantVo merchantVo = new MerchantVo();
		merchantVo.setClientId("chongqing");
		merchantVo.setCountyOrgCode("351406");
		merchantVo.setMerchantStatus(false);
		
		//调用服务
		MerchantPageVoRes result = service.getMerchantByPage(pageVo, merchantVo);
		List<MerchantVo> merchantList = result.getMerchantVoList();
		if(merchantList==null || merchantList.size()==0){
			System.err.println("暂无商户列表信息");
		}else{
			for (MerchantVo merchantVo2 : merchantList) {
				System.err.println(merchantVo2.toString());
			}
		}
	}
	
	
	/**
	 * 商户信息查询
	 * @param service
	 * @throws Exception
	 */
	public static void getMerchantByMerchantId(MerchantService.Client service) throws Exception{

		String merchantId="12EF5F300000";
		
		//调用服务
		MerchantVo merchantVo = service.getMerchantByMerchantId(merchantId);
		if(merchantVo==null){
			System.err.println("商户信息不存在！");
		}else{
			System.err.println(merchantVo.toString());
		}
		
	}
	
	
	/**
	 * 新增商户
	 * @param service
	 * @throws Exception
	 */
	public static void addMerchant(MerchantService.Client service) throws Exception{
		
		OriginVo originVo = new OriginVo();
		originVo.setPlatType(PlatType.bank);
		originVo.setOperatorId(100004630);
		originVo.setOperatorUserName(null);
		originVo.setOperatorIp("192.168.191.173");
		originVo.setClientId("chongqing");

		
		MerchantVoReq mvr = new MerchantVoReq();
		List<CategoryInfoVo> civ = new ArrayList<CategoryInfoVo>();
		CategoryInfoVo ci1 = new CategoryInfoVo();
		ci1.setCategoryId(100000025);
		civ.add(ci1);
		
		List<TypeInfoVo> tiv = new ArrayList<TypeInfoVo>();
		TypeInfoVo ti1 = new TypeInfoVo();
		ti1.setMerchantTypeId(100000001);

		TypeInfoVo ti2 = new TypeInfoVo();
		ti2.setMerchantTypeId(100000000);
		tiv.add(ti1);
		tiv.add(ti2);
		
		MerchantVo merchantVo = new MerchantVo();
		merchantVo.setClientId("chongqing");
		merchantVo.setOrgCode("351406");
		merchantVo.setMerchantName("大苹果");
		merchantVo.setPhone("13326688595");
		merchantVo.setMerchantStatus(false);
		merchantVo.setIsEnable(false);
		merchantVo.setIsTop(false);
		merchantVo.setLicense("336699885522");
		merchantVo.setContractStaff("3208004");
		merchantVo.setAuditState("0");
		merchantVo.setContactPhone("13326655474");
		merchantVo.setLegalName("大苹果");
		merchantVo.setLegalCredentType(1);
		merchantVo.setLegalCredentNo("440402199909099092");
		merchantVo.setUserOrgCode("351406");
		merchantVo.setIsOutsource(true);
		merchantVo.setCompanyId(10000000);

		
		mvr.setMerchantVo(merchantVo);
		mvr.setCategoryInfoVoList(civ);
		mvr.setTypeInfoVoList(tiv);
		
		//调用服务
		MerchantAddVoRes mar = service.addMerchant(originVo,mvr);
		
		if(mar==null){
			System.err.println("新增商户异常!");
		}else{
			System.err.println(mar.getMerchantId());
		}
	}
	
	@org.junit.Test
	public void setMac(){
		//生成mac.
		 StringBuffer macSb = new StringBuffer();
		 macSb.append("6212381001010450961");
		 macSb.append("张三");
		 macSb.append("wxV5KAYqjF9wIPPoRzv7MM2pW7uTbP4T");
		 //2:获取密钥
		 String macValue = Md5Utils.getMD5ofStr(macSb.toString());			
		
		 //3:截取密文约定取最后8字节（我们给银行的接口文件是这样约定的）
		 macValue = StringUtils.substring(macValue, macValue.length()-8,macValue.length());
		 
		 System.out.println("mac="+macValue);
	}

	/**
	 * 商户导出测试.
	 */
	@org.junit.Test
	public void testGetMerchantExport(){
		try{
			TSocket transport = new TSocket("127.0.0.1", 15201);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantService");
			MerchantService.Client service = new MerchantService.Client(mp);
			transport.open();
			
			
			MerchantVo merchantVo = new MerchantVo();
			merchantVo.setClientId("chongqing");
			merchantVo.setMerchantId("106602118000");

			
			ExportResultRes exportResult = service.getMerchantExport(merchantVo);	
			Assert.assertNotNull(exportResult);		

			System.out.println("url="+exportResult.getUrl());			
			
			transport.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}