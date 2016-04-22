package com.froad.thrift;

import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.vo.MerchantPageVoRes;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.PageVo;
//import com.froad.thrift.service.HelloService;
//import com.froad.thrift.service.UserService;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			String host = "10.43.2.3";
			int port = 15201;
			
			host = "127.0.0.1";
			port = 15201;
			TSocket transport = new TSocket(host, port);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			transport.open();
			
			/**
			 *  商户类别查询
			 */
			
			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantService");
			MerchantService.Client ms = new MerchantService.Client(mp);
			
			PageVo pg = new PageVo();
			pg.setPageSize(10);
			pg.setPageNumber(1);
			
			MerchantVo mv = new MerchantVo();
			mv.setClientId("chongqing");
			
			mv.setOrgCode("020000,050000,020701,050102");
			
			MerchantPageVoRes mpv = ms.getMerchantByPage(pg, mv);
			List rlist = mpv.getMerchantVoList();
			System.err.println(JSON.toJSONString(rlist, true));
			
			
			/**
			 * 查询待审核商户列表
			 */
//			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantService");
//			MerchantService.Client ms = new MerchantService.Client(mp);
//			
//			PageVo pg = new PageVo();
//			pg.setPageSize(10);
//			pg.setPageNumber(1);
//			
//			MerchantVo mv = new MerchantVo();
//			mv.setClientId("anhui");
//			mv.setCityOrgCode("340101");
//			mv.setMerchantStatus(false);
//			mv.setAuditState("0");
//			mv.setAuditOrgCode("340101");
//			
//			MerchantPageVoRes mpv = ms.getMerchantByPage(pg, mv);
//			List rlist = mpv.getMerchantVoList();
//			System.err.println(JSON.toJSONString(rlist, true));
			
			/**
			 * 商户信息修改保存接口（需核审流程--单审）
			 */
//			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantAuditService");
//			MerchantAuditService.Client mas = new MerchantAuditService.Client(mp);
//
//			OriginVo originVo = new OriginVo();
//			originVo.setOperatorId(16337);//单审
//			originVo.setOperatorIp("127.0.0.1");
//			originVo.setPlatType(PlatType.bank);
//			originVo.setClientId("chongqing");
//			
//			MerchantTempVoReq mtr = new MerchantTempVoReq();
//			MerchantTempVo mtv = new MerchantTempVo();
//			//联系人姓名、所属分类、法人名、证件类型、证件号、开户行机构号、商户类型、收款账户名、收款人账户、登录人手机
//			mtv.setClientId("chongqing");//客户端id
//			mtv.setMerchantId("0B9D51620000");//商户id
//			mtv.setContactName("李宁88");//联系人姓名
//			mtv.setMerchantCategoryId(100000056);//所属分类
//			mtv.setMerchantCategoryName("婚纱摄影");//所属分类名称
//			mtv.setLegalName("李宁宁");//法人名
//			mtv.setLegalCredentType("1");//法人证件类型
//			mtv.setLegalCredentNo("360722188002048888");//法人证件号
//			mtv.setOrgCode("021001");//开户机构
//			mtv.setMerchantTypeId("100000001");//商户类型id
//			mtv.setMerchantTypeName("直接优惠");//商户类型名称
//			mtv.setMerchantTypeValue("1");//商户类型分类 
////			mtv.setAccountName("新大昌");//收款账户名
////			mtv.setAcountNo("6226008593698888");//收款账号
////			mtv.setLoginMobile("13926655353");//登录手机号
//			
//			mtr.setMerchantTempVo(mtv);
//			MerchantAuditVoRes mavr = mas.auditMerchant(originVo,mtr);
//			System.err.println(mavr.getResult().getResultCode());
//			System.err.println(mavr.getResult().getResultDesc());
			
			
			/**
			 * 商户信息修改保存接口（需核审流程--双审）
			 */
//			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantAuditService");
//			MerchantAuditService.Client mas = new MerchantAuditService.Client(mp);
//
//			OriginVo originVo = new OriginVo();
//			originVo.setOperatorId(15312);//双审
//			originVo.setOperatorIp("127.0.0.1");
//			originVo.setPlatType(PlatType.bank);
//			originVo.setClientId("anhui");
//			
//			MerchantTempVoReq mtr = new MerchantTempVoReq();
//			MerchantTempVo mtv = new MerchantTempVo();
//			//联系人姓名、所属分类、法人名、证件类型、证件号、开户行机构号、商户类型、收款账户名、收款人账户、登录人手机
//			mtv.setClientId("anhui");//客户端id
//			mtv.setMerchantId("05C2A2608018");//商户id
//			mtv.setContactName("双联系");//联系人姓名
//			mtv.setMerchantCategoryId(100000006);//所属分类
//			mtv.setMerchantCategoryName("日用百货");//所属分类名称
//			mtv.setLegalName("双法人");//法人名
//			mtv.setLegalCredentType("1");//法人证件类型
//			mtv.setLegalCredentNo("440402199909099029");//法人证件号
//			mtv.setOrgCode("341465");//开户机构
//			mtv.setMerchantTypeId("100000000,100000001,100000002");//商户类型id
//			mtv.setMerchantTypeName("团购,直接优惠,名优特惠");//商户类型名称
//			mtv.setMerchantTypeValue("0,1,2");//商户类型分类 
//			mtv.setAccountName("双收款");//收款账户名
//			mtv.setAcountNo("6226008593690000");//收款账号
//			mtv.setLoginMobile("13825656788");//登录手机号
//			
//			mtr.setMerchantTempVo(mtv);
//			MerchantAuditVoRes mavr = mas.auditMerchant(originVo,mtr);
//			System.err.println(mavr.getResult().getResultCode());
//			System.err.println(mavr.getResult().getResultDesc());
			
			
			/**
			 * 商户信息修改信息查询
			 */
//			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantAuditService");
//			MerchantAuditService.Client mas = new MerchantAuditService.Client(mp);
//
//			OriginVo originVo = new OriginVo();
//			originVo.setOperatorId(16337);
//			originVo.setOperatorIp("127.0.0.1");
//			originVo.setPlatType(PlatType.bank);
//			originVo.setClientId("chongqing");
//			MerchantTempVo mtv = mas.getAuditMerchant(originVo, "2015090900000001");
//			System.err.println(mtv.toString());	
			
			/**
			 * 商户类型查询
			 */
//			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantTypeService");
//			MerchantTypeService.Client mas = new MerchantTypeService.Client(mp);
//
//			MerchantTypeVo mtv =mas.getMerchantTypeById(100000000, "anhui");
//			
//			System.err.println(mtv.toString());
			
			
			
			
//			TMultiplexedProtocol mp = new TMultiplexedProtocol(protocol,"MerchantAuditService");
//			MerchantAuditService.Client mas = new MerchantAuditService.Client(mp);
//
//			mas.synchMerchantEdit("2015082600000033");
			
//			System.err.println(mtv.toString());
			
			
			
			
			transport.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
