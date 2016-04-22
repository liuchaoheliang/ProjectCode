package com.froad.thrift;

import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;
import org.codehaus.janino.util.Benchmark;

import com.alibaba.fastjson.JSON;
import com.froad.po.Activities;
import com.froad.po.AdPosition;
import com.froad.po.Area;
import com.froad.thrift.service.ActivitiesService;
import com.froad.thrift.service.AdService;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.vo.ActivitiesVo;
import com.froad.thrift.vo.AdPositionVo;
import com.froad.thrift.vo.AdVo;
import com.froad.thrift.vo.AreaPageVoRes;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.BossUserOrgVo;
import com.froad.thrift.vo.BossUserVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;

import java.util.Date;
import java.util.List;

import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.transport.TSocket;

import com.froad.thrift.service.ActivitiesService;
import com.froad.thrift.service.AdPositionService;
import com.froad.thrift.service.AgreementService;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BossUserOrgService;
import com.froad.thrift.service.BossUserService;
import com.froad.thrift.service.SmsContentService;
import com.froad.thrift.service.SmsLogService;
import com.froad.thrift.vo.AgreementVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.SmsContentVo;
import com.froad.thrift.vo.SmsLogPageVoRes;
import com.froad.thrift.vo.SmsLogVo;
import com.froad.util.RandomCodeUtils;
import com.froad.util.UUIDHelper;

public class Client {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		try {
//			TSocket transport = new TSocket("localhost", 9096);
//			TBinaryProtocol protocol = new TBinaryProtocol(transport);
//
//			transport.open();
//			
//			TMultiplexedProtocol mp3 = new TMultiplexedProtocol(protocol,"AreaService");
//			AreaService.Client service3 = new AreaService.Client(mp3);
//
//			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"ActivitiesService");
//			ActivitiesService.Client service4 = new ActivitiesService.Client(mp4);
//			
//			TMultiplexedProtocol mp5 = new TMultiplexedProtocol(protocol,"AdService");
//			AdService.Client service5 = new AdService.Client(mp5);
//			
//			TMultiplexedProtocol mp6 = new TMultiplexedProtocol(protocol,"AgreementService");
//			AdService.Client service6 = new AdService.Client(mp6);
//			
//			TMultiplexedProtocol mp7 = new TMultiplexedProtocol(protocol,"CashService");
//			AdService.Client service7 = new AdService.Client(mp7);
//			
//			
//			TMultiplexedProtocol mp8 = new TMultiplexedProtocol(protocol,"DeliveryCorpService");
//			AdService.Client service8 = new AdService.Client(mp8);
//			
//			TMultiplexedProtocol mp9 = new TMultiplexedProtocol(protocol,"SmsContentService");
//			AdService.Client service9 = new AdService.Client(mp9);
//			
//			TMultiplexedProtocol mp0 = new TMultiplexedProtocol(protocol,"SmsLogService");
//			AdService.Client service0 = new AdService.Client(mp0);
		
////			PageVo pagevo = new PageVo();
////			pagevo.setPageCount(5);
////			pagevo.setPageSize(5);
////			pagevo.setPageNumber(1);
////			pagevo.setTotalCount(10);
////			
////			ActivitiesVo activities = new ActivitiesVo();
////			activities.setId((long)100000001);
////			activities.setClientId((long)1001);
////			activities.setActivitiesName("大促销");
////			activities.setActivitiesType("0");
////			activities.setBeginTime(new Date().getTime());
////			activities.setEndTime(System.currentTimeMillis()+100000000);
////			activities.setPoints(1000);
////			activities.setVipPrice("1000000");
////			activities.setIsEnable(true);
////			activities.setRemark("test2");
////			service4.addActivities(activities);
////			service4.deleteActivities(activities);
////			service4.updateActivities(activities);
////			service4.getActivities(activities);
//			
//			
//			
//
////			System.out.println(service1.helloString("测试"));
////
////			System.out.println(service2.getById(1).toString());
//
////			AreaVo areaVo = new AreaVo();
////			areaVo.setId((long)100000000);
////			areaVo.setClientId(10001);
////			areaVo.setName("浦东新区");
////			areaVo.setVagueLetter("PD");
////			areaVo.setTreePath("tp");
////			areaVo.setParentId(100000001);
////			areaVo.setIsEnable(true);
//////			
////			PageVo pagevo = new PageVo();
////			pagevo.setPageCount(5);
////			pagevo.setPageSize(5);
////			pagevo.setPageNumber(2);
////			pagevo.setTotalCount(10);
////////			
////			AreaPageVoRes areapagevores = service3.getAreaByPage(pagevo, areaVo);
////			for(AreaVo vo : areapagevores.getAreaVoList()){
//////				Area po = new Area();
//////				BeanUtils.copyProperties(po, vo);
////				System.out.println(vo.getName());
////			}
////			
////			service3.addArea(areaVo);
////			service3.updateArea(areaVo);
////			service3.deleteArea(areaVo);
////			service3.getArea(areaVo);
////			service3.getAreaByPage(pagevo,areaVo);
////			List<AreaVo> list = service3.getArea(areaVo);
////			for (int i = 0; i < list.size(); i++) {
////				System.out.println(list.get(i).getName());
////			}
//			
//			
////			AdVo advo = new AdVo();
////			advo.setAdPositionId(100001);
////			advo.setBeginTime(2014);
////			advo.setEndTime(2015);
////			advo.setClickCount(100);
////			advo.setClientId(10001);
////			advo.setContent("test");
////			advo.setIsBlankTarge(true);
////			advo.setLink("www.xxx.com");
////			advo.setPath("test/test");
////			advo.setTitle("不要998，只要188");
////			advo.setType(1);
////			service5.addAd(advo);
//			
//			
//			
//			
//			
//			
//			transport.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		try {
			TSocket transport = new TSocket("localhost", 15701);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);

//			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"HelloService");
//			HelloService.Client service1 = new HelloService.Client(mp1);
//
//			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"UserService");
//			UserService.Client service2 = new UserService.Client(mp2);
			
			
			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"AgreementService");
			AgreementService.Client service2 = new AgreementService.Client(mp2);
			
			TMultiplexedProtocol mp3 = new TMultiplexedProtocol(protocol,"ActivitiesService");
			ActivitiesService.Client service3 = new ActivitiesService.Client(mp3);
			

			TMultiplexedProtocol mp4 = new TMultiplexedProtocol(protocol,"AdPositionService");
			AdPositionService.Client service4 = new AdPositionService.Client(mp4);


			TMultiplexedProtocol mp5 = new TMultiplexedProtocol(protocol,"SmsContentService");
			SmsContentService.Client service5 = new SmsContentService.Client(mp5);
			
			
			TMultiplexedProtocol mp6= new TMultiplexedProtocol(protocol,"AreaService");
			AreaService.Client service6 = new AreaService.Client(mp6);
			
			TMultiplexedProtocol mp7= new TMultiplexedProtocol(protocol,"SmsLogService");
			SmsLogService.Client service7 = new SmsLogService.Client(mp7);
			
			TMultiplexedProtocol mp11 = new TMultiplexedProtocol(protocol,"BossUserOrgService");
			BossUserOrgService.Client service11 = new BossUserOrgService.Client(mp11);
			
			TMultiplexedProtocol mp12 = new TMultiplexedProtocol(protocol,"AdService");
			AdService.Client service12 = new AdService.Client(mp12);
			
			transport.open();
			OriginVo originVo = new OriginVo();
			//-----------------------分页参数设置----------------
			PageVo page = new PageVo();
			page.setPageNumber(1);
			page.setPageSize(6);
			
			//----------------------AreaVo对象配置---------------------------------
//			AreaVo areaVo =new AreaVo();
//			areaVo.setClientId(1000);
//			areaVo.setId(100000000);
//			areaVo.setName("1111111");
//			areaVo.setTreePath("222222222");
//			areaVo.setParentId(0);
//			areaVo.setIsEnable(true);
//			areaVo.setVagueLetter("111");
//			areaVo.setTreePath("111");
//			service6.addArea(areaVo);
//			service6.deleteArea(areaVo);
//			List<AreaVo> li = service6.getArea(areaVo);
//			for(AreaVo a: li){
//				System.out.println(a.getName());
//			}
			
//			List<AreaVo> li = service6.findChildrenInfoById(0);
//			System.out.println(li.size());
			
			
			
			AdVo advo = new AdVo();
			advo.setId(100000001);
			advo.setClientId("anhui");
			advo.setTitle("味多美预售");
			advo.setType(1);
			advo.setBeginTime(new Date().getTime());
			advo.setEndTime(new Date().getTime());
			advo.setContent("www.Ssas.com");
			advo.setPath("/froad-cb/coremodule/71d78f61-1f8d-4aab-9878-b93e912b892a-large.jpg");
			advo.setLink("www.o2obill.com");
			advo.setIsEnabled(true);
			advo.setIsBlankTarge(false);
			advo.setClickCount(0);
			advo.setAdPositionId(100000000);
			service12.addAd(originVo, advo);
			
//			AdPositionVo adPosition  = new AdPositionVo();
//			adPosition.setId((long)100000000);
//			adPosition.setClientId("anhui");
//			adPosition.setName("首页");
//			adPosition.setHeight(12);
//			adPosition.setWidth(300);
//			adPosition.setPositionStyle("1");
//			adPosition.setPositionPage("1");
//			adPosition.setPositionPoint(334);
//			adPosition.setDescription("首页");
//			adPosition.setIsEnable(true);
//			service4.addAdPosition(originVo, adPosition);
			
			
			
//			AgreementVo agreement =new AgreementVo();
//			agreement.setContent("2222");
//			agreement.setClientId(1000002);
//			agreement.setId(1000000l);
//			System.out.println("==========="+service2.updateAgreement(agreement));
			
			//----------------------SmsLogVo对象配置---------------------------------
//			SmsLogVo smsLogVo =new SmsLogVo();
//			smsLogVo.setClientId(100000000);
//			smsLogVo.setContent("0");//图片验证码时默认为0
////			smsLog.setSmsType(SmsType.getByCode(smsMessageRequestVo.getSmsType()+""));
//			smsLogVo.setSmsType(1);
//			smsLogVo.setMobile("");
//			smsLogVo.setSendUser("");
//			smsLogVo.setSendIp("");
//			//-------- 生成手机验证码相关 ------
//			String code = RandomCodeUtils.makeCheckCode(6); //生成字母加数字的随机验证码
//			String token = UUIDHelper.getUUID();
//			smsLogVo.setCode(code);
//			smsLogVo.setToken(token);
//			String url="";
//			smsLogVo.setUrl(url);
//			Date d=new Date();
//			smsLogVo.setCreateTime();
//			smsLogVo.setIsSuccess(true);
//			smsLogVo.setIsUsed(false);
//			Date validdate = new Date();
//			Calendar calendar = Calendar.getInstance();
//			calendar.setTime(validdate);
//			validdate = calendar.getTime();
//			calendar.add(Calendar.DATE,1);//有效期为在一天后
//			validdate = calendar.getTime();
//			System.out.println("==========="+service6.addSmsLog(smsLogVo));
//			smsLogVo.setId(100000024);
//			System.out.println("----------"+service7.deleteSmsLog(smsLogVo));
		//----------------------SmsContentVo对象配置--------------------------------------------------------------
			
//			短信模板
//			SmsContentVo  smsContentVo = new SmsContentVo();
//			smsContentVo.setClientId(100000000);
//			smsContentVo.setValidTime(10);
//			smsContentVo.setSmsType(1008);
//			smsContentVo.setContent("{0}这是您的快捷支付验证码，请悄悄告诉我！");
//			smsContentVo.setMsgSuffix("【重庆农商】");
//			smsContentVo.setChannel("CQYS");
//			smsContentVo.setIsEnable(true);
			
//			SmsLogPageVoRes  res = service7.getSmsLogByPage(page, smsLogVo);
//			List<SmsLogVo> smscont=res.getSmsLogVoList();
//			for (SmsLogVo smsContentVo2 : smscont) {
//				System.out.println("smsContentVo2=11=========="+smsContentVo2.getId());
//			}
			
//			 分页查询
//			SmsContentPageVoRes  res = service5.getSmsContentByPage(page, smsContentVo);
//			List<SmsContentVo> smscont=res.getSmsContentVoList();
//			for (SmsContentVo smsContentVo2 : smscont) {
//				System.out.println("smsContentVo2==========="+smsContentVo2.getId());
//				System.out.println("smsContentVo2==========="+smsContentVo2.getContent());
//				System.out.println("smsContentVo2==========="+smsContentVo2.getMsgSuffix());
//				
//			}
			
//			
//			OriginVo originVo = new OriginVo();
//			BossUserOrgVo bossvo = new BossUserOrgVo();
//			bossvo.setId(1000);
//			bossvo.setOrgCode("1000");
//			bossvo.setUserId(1000);
//			service11.addBossUserOrg(originVo, bossvo);
//			
			
			//----------------------ActivitiesVo对象配置--------------------------------------------------------------
			
//			ActivitiesVo activitiesVo=new ActivitiesVo();
////			activitiesVo.setClientId(0l);
//			activitiesVo.setActivitiesName("大促销");
		
			transport.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
//		try {
//			TSocket transport = new TSocket("localhost", 8899);
//			TBinaryProtocol protocol = new TBinaryProtocol(transport);
//
//			TMultiplexedProtocol mp1 = new TMultiplexedProtocol(protocol,"HelloService");
//			HelloService.Client service1 = new HelloService.Client(mp1);
//
//			TMultiplexedProtocol mp2 = new TMultiplexedProtocol(protocol,"UserService");
//			UserService.Client service2 = new UserService.Client(mp2);
//
//			transport.open();
//
//			System.out.println(service1.helloString("测试"));
//
//			System.out.println(service2.getById(1).toString());
//
//			transport.close();
//			
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}

}
