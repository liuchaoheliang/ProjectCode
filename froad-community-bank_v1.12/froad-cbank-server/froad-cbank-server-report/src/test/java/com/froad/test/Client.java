package com.froad.test;

import java.util.Iterator;
import java.util.Map;

import com.froad.logback.LogCvt;
import com.froad.thrift.service.impl.ReportMerchantContractServiceImpl;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.report.CommonParamVo;
import com.froad.util.Checker;
import com.froad.util.MongoUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.RedisUtil;

public class Client {
	
	private static final String post = "127.0.0.1";
	private static final Integer host = PropertiesUtil.port;
	
	public static void main(String[] args) {
		PropertiesUtil.load();
		try {
			clearCache();
			
			ReportMerchantContractServiceImpl service = null;
			PageVo pageVo = null;
			CommonParamVo commonParamVo = null;
			
			service = new ReportMerchantContractServiceImpl();
			pageVo = new PageVo();
			pageVo.setPageNumber(18);
			commonParamVo = new CommonParamVo();
			commonParamVo.setBegDate(1436112000000l);
			commonParamVo.setClientId("anhui");
			commonParamVo.setEndDate(1440518399000l);
			commonParamVo.setOrgCode("340000");
			commonParamVo.setFlag(false);
			service.merchantContractDetailListByPage(pageVo, commonParamVo);
			
//			SqlSession rpSqlSession = ReportMyBatisManager.getSqlSessionFactory().openSession();
//			ReportOrderMapper reportOrderMapper = rpSqlSession.getMapper(ReportOrderMapper.class);
//			List<ReportOrder> orderList = null;
//			ReportOrder orderQuery = new ReportOrder();
//			orderQuery.setClientId("anhui");
//			orderQuery.setMerchantId("09F6CDA00000");
//			orderQuery.setCreateDate(20150807);
//			orderQuery.setOrderType("1");
//			orderList = reportOrderMapper.findByCondition(orderQuery);
//			System.out.println(orderList.size());
//			ReportMerchantInfoService.Iface reportMerchantInfoService = 
//					(ReportMerchantInfoService.Iface)ThriftClientProxyFactory.createIface(ReportMerchantInfoService.class.getName(), ReportMerchantInfoService.class.getSimpleName(), post, host);
//			
//			ReportMerchantContractService.Iface reportMerchantContractService = 
//					(ReportMerchantContractService.Iface) ThriftClientProxyFactory.createIface(ReportMerchantContractService.class.getName(), ReportMerchantContractService.class.getSimpleName(), post, host);
//			
//			ReportProductInfoService.Iface reportProductInfoService = 
//					(ReportProductInfoService.Iface)ThriftClientProxyFactory.createIface(ReportProductInfoService.class.getName(), ReportProductInfoService.class.getSimpleName(), post, host);
//			
//			CommonParamVo vo = new CommonParamVo();
//			vo.setClientId("anhui");
//			vo.setOrgCode("340000");
			
//			SaleTrendResVo vos = reportProductInfoService.getSaleTrend();
//			List<SaleTrendVo> list= vos.getSaleTrendVos();
//			for(SaleTrendVo v : list){
//				System.out.println(v.getWeek()+" "+v.getSaleProductAmount());
//			}
			
//			SaleTypePercentResVo vos = reportProductInfoService.getSaleTypePercent(vo);
//			List<SaleTypePercentVo> list = vos.getSaleTypePercentVos();
//			for(SaleTypePercentVo o : list){
//				System.out.println(o.getType()+"-------"+o.getPercent());
//			}
			
//			PayTypePercentResVo vos = reportProductInfoService.getPayTypePercent(vo);
//			List<PayTypePercentVo> list = vos.getPayTypePercentVos();
//			for(PayTypePercentVo v  : list){
//				System.out.println(v.getType()+"--------------"+v.getPercent());
//			}
//			
//			SaleCountDetailResVo vos = reportProductInfoService.getSaleCountDetail(vo);
//			List<SaleCountDetailVo> list = vos.getSaleCountDetailVos();
//			for(SaleCountDetailVo v : list){
//				System.out.println(v.getOrgCode()+" "+ v.getOrgName() + " " + v.getTotalAmount());
//			}
//			
			
//			MerchantSaleDetailResVo vos = reportProductInfoService.getMerchantSaleDetail(vo);
//			List<MerchantSaleDetailVo> list  = vos.getMerchantSaleDetailVos();
//			for(MerchantSaleDetailVo v : list){
//				System.out.println(v.getOrgCode()+ " " + v.getOrgName() + " "+ v.getMerchantName() + " "  + v.getFaceOrderCount() + " "  );
//			}
			
//			BusinessSaleDetailResVo vos = reportProductInfoService.getBusinessSaleDetail(vo);
//			List<BusinessSaleDetailVo> list = vos.getBusinessSaleDetailVos();
//			for(BusinessSaleDetailVo v : list){
//				System.out.println(v.getOrgCode()+" "+v.getOrgName()+" "+ v.getType() + " "+v.getCashAmount());
//			}
			
//			CommonParamVo vo = new CommonParamVo();
//			vo.setOrgCode("3401020427");
//			List<TypePercentVo> types = reportMerchantInfoService.getMerchantTypePercent(vo);
//			for(TypePercentVo t : types){
//				System.out.println(t.getType()+" "+t.getPercent());
//			}
			
			
			
//			List<SaleTypePercentVo> list= reportProductInfoService.getSaleTypePercent(vo);
//			for(SaleTypePercentVo sp : list){
//				System.out.println(sp.getType());
//				System.out.println(sp.getPercent());
//			}
			
//			List<TypePercentVo> types = reportMerchantInfoService.getMerchantBussinessPercent(vo);
//			for(TypePercentVo t : types){
//				System.out.println(t.getType()+" "+t.getPercent());
			
//			CommonParamVo vo = new CommonParamVo();
//			vo.setClientId("anhui");
//			vo.setOrgCode("340101");
//			MerchantDetailResVo res = reportMerchantInfoService.getMerchantDetailList(vo);
//			CommonParamVo vo = new CommonParamVo();
//			vo.setClientId("anhui");
//			vo.setOrgCode("340101");
//			ReportMerchantDetailResVo res = reportMerchantInfoService.getMerchantDetailList(vo);
//			System.out.println(res.getResultVo().getResultCode());
//			System.out.println(res.getResultVo().getResultCode());
//			for(ReportMerchantDetailVo m : res.getMerchantDetailVos()){
//				System.out.println(JSonUtil.toJSonString(m));
//			}
			
//			ReportMerchantBussinessResVo res = reportMerchantInfoService.getMerchantBussinessList("anhui", "340101");
//			System.out.println(res.getResultCode());
//			System.out.println(res.getResultDesc());
//			for(ReportMerchantBussinessVo m : res.getMerchantBussinessVos()){
//				System.out.println(JSonUtil.toJSonString(m));
//			}
			
//			MerchantBussinessResVo res = reportMerchantInfoService.getMerchantBussinessList("anhui", "340101");
//			System.out.println(res.getResultCode());
//			System.out.println(res.getResultDesc());
//			for(MerchantBussinessVo m : res.getMerchantBussinessVos()){
//				System.out.println(JSonUtil.toJSonString(m));
//			}
//			ReportMerchantBussinessResVo res = reportMerchantInfoService.getMerchantBussinessList("anhui", "340101");
//			MerchantContractRankResVo res = reportMerchantContractService.merchantContractRank("anhui", "340000");
//			System.out.println(res.getResultCode());
//			System.out.println(res.getResultDesc());
//			for(MerchantContractRankVo m : res.getMerchantContractRankVos()){
//				System.out.println(JSonUtil.toJSonString(m));
//			}
			
//			PayTypePercentResVo res =  reportProductInfoService.getPayTypePercent(vo);
//			List<PayTypePercentVo> re = res.getPayTypePercentVos();
//			for(PayTypePercentVo v : re){
//				System.out.println(v.getType()+v.getPercent());
//			}
//			CommonParamVo vo = new CommonParamVo();
//			vo.setClientId("anhui");
//			vo.setOrgCode("340000");
//			ReportMerchantContractDeatailResVo res = reportMerchantContractService.merchantContractDetailList(vo);
//			System.out.println(res.getResultCode());
//			System.out.println(res.getResultDesc());
//			for(ReportMerchantContractDeatailVo m : res.getMerchantContractDeatailVos()){
//				System.out.println(JSonUtil.toJSonString(m));
//			}
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	public static void clearCache(){
		RedisUtil redisUtil = null;
		MongoUtil mongoUtil = null;
		Map<String, String> keyHash = null;
		Iterator<String> redisKeyIt = null;
		String redisKey = null;
		Iterator<String> collectionIt = null;
		String collection = null;
		
		try {
			redisUtil = new RedisUtil();
			mongoUtil = new MongoUtil();
			
			keyHash = redisUtil.hgetAll(RedisUtil.MONGO_COLLECTION_KEYS);
			
			if (Checker.isNotEmpty(keyHash)){
				redisKeyIt = keyHash.keySet().iterator();
				collectionIt = keyHash.values().iterator();
				
				// 清除redis缓存
				while (redisKeyIt.hasNext()){
					redisKey = redisKeyIt.next();
					redisUtil.del(redisKey);
					LogCvt.info(new StringBuffer(redisKey).append(" redis缓存已清除").toString());
				}
				
				// 清除mongo临时集合
				while (collectionIt.hasNext()){
					collection = collectionIt.next();
					mongoUtil.dropCollection(collection);
					LogCvt.info(new StringBuffer(collection).append(" mongo集合已清除").toString());
				}
				
				redisUtil.del(RedisUtil.MONGO_COLLECTION_KEYS);
			}
		} catch (Exception e){
			LogCvt.error("清除缓存失败", e);
		}
	}
}
