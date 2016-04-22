package com.froad.thrift;

import java.util.ArrayList;
import java.util.List;

import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.BossMerchantCategoryService;
import com.froad.thrift.service.MerchantActivityTagService;
import com.froad.thrift.service.ProductActivityTagService;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;
import com.froad.thrift.vo.merchant.MerchantCategoryVo;
import com.froad.util.PropertiesUtil;

public class MerchantActivityTagServiceTest {
	
//	private static final String post = "10.24.248.187";
	private static final String post = "127.0.0.1";
	private static final Integer host = PropertiesUtil.port;
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		
		try {
			MerchantActivityTagService.Iface merchantActivityTagService = 
					(MerchantActivityTagService.Iface)ThriftClientProxyFactory.createIface(MerchantActivityTagService.class.getName(), MerchantActivityTagService.class.getSimpleName(), post, host);
			
			ProductActivityTagService.Iface productActivityTagService = 
					(ProductActivityTagService.Iface)ThriftClientProxyFactory.createIface(ProductActivityTagService.class.getName(), ProductActivityTagService.class.getSimpleName(), post, host);
			
			BossMerchantCategoryService.Iface bossMerchantCategoryService = 
					(BossMerchantCategoryService.Iface)ThriftClientProxyFactory.createIface(BossMerchantCategoryService.class.getName(), BossMerchantCategoryService.class.getSimpleName(), post, host);
			
			
//			RecommendActivityTagVo vo = new RecommendActivityTagVo();
//			vo.setActivityName("安徽");
//			PageVo pageVo = new PageVo();
//			
//			productActivityTagService.findProductTagByPage(vo, pageVo);
			
			
//			RecommendActivityTagVo recommendVo = new RecommendActivityTagVo();
//			recommendVo.setClientId("chongqing");
//			recommendVo.setActivityNo("chongqing-2015-011");
//			recommendVo.setActivityName("重庆商户测试活动标签11");
//			recommendVo.setOperator("admin");
//			recommendVo.setStatus(ActivityTagStatusEnum.enable.getStatus());
//			recommendVo.setActivityDesc("重庆商户测试活动标签11");
//			recommendVo.setCreateTime(System.currentTimeMillis());
//			merchantActivityTagService.addMerchantActivityTag(recommendVo);
			
//			RelateMerchantActivityVo relateVo = new RelateMerchantActivityVo();
//			relateVo.setClientId("chongqing");
//			relateVo.setActivityId(12);
//			relateVo.setActivityNo("chongqing-2015-001");
//			relateVo.setWeight("4");
//			relateVo.setLicense("500105600953748");
//			relateVo.setOperator("admin");
//			merchantActivityTagService.relateMerchantInfo(relateVo);
			
			
//			RecommendActivityTagVo recommendVo = new RecommendActivityTagVo();
//			recommendVo.setId(16l);
//			recommendVo.setClientId("chongqing");
//			recommendVo.setActivityNo("chongqing-2015-011");
//			recommendVo.setOperator("admin");
//			merchantActivityTagService.updateMerchantActivityTag(recommendVo);
			
//			MerchantActivityTagDetailReqVo reqVo = new MerchantActivityTagDetailReqVo();
//			reqVo.setActivityId(16l);
//			reqVo.setClientId("chongqing");
//			reqVo.setActivityNo("chongqing-2015-011");
//			reqVo.setOperator("admin1");
//			merchantActivityTagService.findMerchantTagDetail(reqVo);
			
//			MerchantWeightActivityTagPageReqVo pageReqVo = new MerchantWeightActivityTagPageReqVo();
//			pageReqVo.setActivityId(16l);
//			pageReqVo.setClientId("chongqing");
//			pageReqVo.setActivityNo("chongqing-2015-011");
//			pageReqVo.setPageVo(pageVo);
//			merchantActivityTagService.findRelateMerchantInfoByPage(pageReqVo);
			
//			GenerateActivityNoReq req = new GenerateActivityNoReq();
//			req.setClientId("chongqing");
//			req.setActivityType(ActivityTypeEnum.merchant.getType());
//			GenerateActivityNoRes res = businessZoneTagService.generateActivityNo(req);
//			System.out.println(res);
//			
//			GenerateActivityNoReq req1 = new GenerateActivityNoReq();
//			req1.setClientId("chongqing");
//			req1.setActivityType(ActivityTypeEnum.merchant.getType());
//			GenerateActivityNoRes res1 = businessZoneTagService.generateActivityNo(req1);
//			System.out.println(res1);
			
			
			OriginVo originVo = new OriginVo();
			originVo.setPlatType(PlatType.boss);
			originVo.setClientId("chongqing");
			
			List<MerchantCategoryVo> vos = new ArrayList<MerchantCategoryVo>();
			MerchantCategoryVo vo1 = new MerchantCategoryVo();
			vo1.setId(1l);
			vo1.setClientName("重庆");
			vo1.setMerchantCategory("日用百货");
			vo1.setMerchantCategryDetail("日用百货");
			vo1.setMerchantName("au02706164794467");
			vo1.setMerchantId("10BC83C20000");
			vo1.setLicense("1360270616441680");
			vos.add(vo1);
			
			bossMerchantCategoryService.merchantCategoryInput(originVo, vos);
			
			
			
			
			
			
			
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
