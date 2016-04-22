package com.froad.cbank.coremodule.normal.boss.support.order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.order.BusinessOrderInfoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.BusinessOrderRefundRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.BusinessOrderTradeRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.BusinessOrderVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.BusinessOrderVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.DeliveryBillVo;
import com.froad.cbank.coremodule.normal.boss.pojo.order.DeliveryCorpVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.ProviderOrderQueryVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.ProviderOrderQueryVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.ShippingInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.support.external.KuaidiSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.NumberUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BusinessOrderQueryService;
import com.froad.thrift.service.DeliveryCorpService;
import com.froad.thrift.service.DeliveryWayBillService;
import com.froad.thrift.service.ProviderOrderService;
import com.froad.thrift.service.RefundService;
import com.froad.thrift.vo.DeliveryCorpVo;
import com.froad.thrift.vo.DeliveryWayBillVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.Business.BusinessOrderListReq;
import com.froad.thrift.vo.Business.BusinessOrderListRes;
import com.froad.thrift.vo.Business.BusinessOrderPyamentInfoRes;
import com.froad.thrift.vo.Business.BusinessOrderPyamentInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderRefundInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderRefundInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderShippingInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderShippingInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderTradeInfoVo;
import com.froad.thrift.vo.Business.BusinessOrderTradeInfoVoRes;
import com.froad.thrift.vo.Business.BusinessOrderVo;
import com.froad.thrift.vo.providerorder.ProviderOrderQueryPageVo;
import com.froad.thrift.vo.providerorder.ProviderOrderQueryReq;
import com.froad.thrift.vo.providerorder.ProviderOrderQueryVo;
import com.froad.thrift.vo.providerorder.ShippingExcelInfoVo;
import com.froad.thrift.vo.providerorder.UpdateShippingInfoReq;
import com.froad.thrift.vo.refund.RefundResponseVo;

@Service
public class CompetiviteOrderSupport {
	
	@Resource
	private ProviderOrderService.Iface providerOrderService;
	@Resource
	private BusinessOrderQueryService.Iface businessOrderQueryService;
	@Resource
	private DeliveryCorpService.Iface deliveryCorpService;
	@Resource
	private RefundService.Iface refundService;
	@Resource
	private KuaidiSupport kuaidiSupport;
	@Resource
	private DeliveryWayBillService.Iface deliveryWayBillService;
	
	/**
	 * 根据客户端或 物流信息
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object>  queryDeliveryCorpList() throws TException{
		HashMap<String, Object> map=new HashMap<String, Object>();
		List<DeliveryCorpVoRes> list=new ArrayList<DeliveryCorpVoRes>();
		DeliveryCorpVo deliveryCorpVo=new DeliveryCorpVo();
		List<DeliveryCorpVo> deliveryCorpVos = deliveryCorpService.getDeliveryCorp(deliveryCorpVo);
		
		DeliveryCorpVoRes vo; 
		if(deliveryCorpVos!=null&&deliveryCorpVos.size()!=0){
			for (DeliveryCorpVo temp : deliveryCorpVos) {
				vo=new DeliveryCorpVoRes();
				BeanUtils.copyProperties(vo, temp);
				list.add(vo);
			}
		}
		map.put("list", list);
		return map;
	}
	
	/**
	 * 查看物流信息
	 *@description 
	 *@author yfy
	 * @throws BossException 
	 * @throws TException 
	 *@date 2015年12月1日 下午15:29:31
	 */
	public Map<String,Object> getLogistics(String shippingId) throws BossException, TException {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		DeliveryWayBillVo deliveryWayBillVo = new DeliveryWayBillVo();
		deliveryWayBillVo.setShippingId(shippingId);
		DeliveryWayBillVo res = deliveryWayBillService.getDeliveryWayBillById(deliveryWayBillVo);
		if(StringUtil.isNotBlank(res.getId())){
			DeliveryBillVo deliveryBillVo = new DeliveryBillVo();
			BeanUtils.copyProperties(deliveryBillVo, res);
			String message = res.getMessage();
			if(StringUtil.isNotBlank(message)){
				ArrayList<HashMap<String, Object>> msgList = new ArrayList<HashMap<String, Object>>();
				String[] msg = message.split("##");
				for(String strs : msg){
					String[] str = strs.split("@@");
					HashMap<String, Object> temp = new HashMap<String, Object>();
					temp.put("createTime", str[0]);//时间
					temp.put("context", str[1]);//事件
					msgList.add(temp);
				} 
				Collections.reverse(msgList);
				deliveryBillVo.setMsgList(msgList);
			}
			resMap.put("deliveryBillVo", deliveryBillVo);
		}else{
			throw new BossException(Constants.RESULT_FAIL,"查询物流信息失败");
		}
		return resMap;
	}
	
	/**
	 * 运营商订单列表分页查询
	 * @throws ParseException 
	 * @throws TException 
	 */
	public HashMap<String, Object> queryBusinessOrderList(BusinessOrderVoReq req) throws ParseException, TException{
		//初始化对象
		HashMap<String, Object> map=new HashMap<String, Object>();
		BusinessOrderListReq temp=new BusinessOrderListReq(); 
		List<BusinessOrderVoRes> list=new ArrayList<BusinessOrderVoRes>();
		
		//封装数据
		if(StringUtil.isNotBlank(req.getOrderId())){
			temp.setOrderId(req.getOrderId());
		}
		if(StringUtil.isNotBlank(req.getClientId())){
			temp.setClientId(req.getClientId());
		}
		if(StringUtil.isNotBlank(req.getMemberCode())){
			temp.setMemberCode(req.getMemberCode().longValue());
		}
		if(StringUtil.isNotBlank(req.getDeliveryStatus())){
			temp.setDeliveryStatus(req.getDeliveryStatus());
		}
		if(StringUtil.isNotBlank(req.getPaymentMethod())){
			temp.setPaymentMethod(req.getPaymentMethod());
		}
		if(StringUtil.isNotBlank(req.getOrderStatus())){
			temp.setOrderStatus(req.getOrderStatus());
		}
		if(StringUtil.isNotBlank(req.getCreateStartTime())){
			temp.setCreateStartTime(PramasUtil.DateFormat(req.getCreateStartTime()));
		}
		if(StringUtil.isNotBlank(req.getCreateEndTime())){
			temp.setCreateEndTime(PramasUtil.DateFormat(req.getCreateEndTime()));
		}
		
		//封装分页对象
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		temp.setPageVo(pageVo);
		
		//调用server端接口
		BusinessOrderListRes businessOrderListRes =  businessOrderQueryService.queryBusinessOrderList(temp);
		BeanUtils.copyProperties(pageVo, businessOrderListRes.getPageVo());
		
		BusinessOrderVoRes resVo; 
		if(businessOrderListRes.getVoListSize()>0){
			List<BusinessOrderVo> resList= businessOrderListRes.getVoList();
			for (BusinessOrderVo businessOrderVo : resList) {
				resVo=new BusinessOrderVoRes();
				BeanUtils.copyProperties(resVo, businessOrderVo);
				//把server端返回的时间 转换成字符串
				if(businessOrderVo.getCreateTime()==0){
					resVo.setCreateTime("-");
				}else{
					resVo.setCreateTime(DateUtil.longToString(businessOrderVo.getCreateTime()));
				}
				if(businessOrderVo.getPaymentTime()==0){
					resVo.setPaymentTime("-");
				}else{
					resVo.setPaymentTime(DateUtil.longToString(businessOrderVo.getPaymentTime()));
				}
				list.add(resVo);
			}
		}
		
		map.put("list", list);
		map.put("page", pageVo);
		
		return map;
	}
	
	/**
	 * 运营商订单导出
	 * @throws ParseException 
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String, Object> exportBusinessOrder(BusinessOrderVoReq req) throws ParseException, TException, BossException{
		//初始化对象
		HashMap<String, Object> map=new HashMap<String, Object>();
		BusinessOrderListReq temp=new BusinessOrderListReq(); 
		
		//封装数据
		if(StringUtil.isNotBlank(req.getOrderId())){
			temp.setOrderId(req.getOrderId());
		}
		if(StringUtil.isNotBlank(req.getClientId())){
			temp.setClientId(req.getClientId());
		}
		if(StringUtil.isNotBlank(req.getMemberCode())){
			temp.setMemberCode(req.getMemberCode().longValue());
		}
		if(StringUtil.isNotBlank(req.getDeliveryStatus())){
			temp.setDeliveryStatus(req.getDeliveryStatus());
		}
		if(StringUtil.isNotBlank(req.getPaymentMethod())){
			temp.setPaymentMethod(req.getPaymentMethod());
		}
		if(StringUtil.isNotBlank(req.getOrderStatus())){
			temp.setOrderStatus(req.getOrderStatus());
		}
		if(StringUtil.isNotBlank(req.getCreateStartTime())){
			temp.setCreateStartTime(PramasUtil.DateFormat(req.getCreateStartTime()));
		}
		if(StringUtil.isNotBlank(req.getCreateEndTime())){
			temp.setCreateEndTime(PramasUtil.DateFormat(req.getCreateEndTime()));
		}
		
		//调用server端接口
		ExportResultRes exportResultRes = businessOrderQueryService.exportBusinessOrder(temp);
		ResultVo result = exportResultRes.getResultVo();
		
		//判断请求处理是否成功
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())){
			map.put("url", exportResultRes.getUrl());
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		}else{
			throw new BossException(result.getResultCode(),result.getResultDesc());
		}
		
		return map;
	}
	
	
	/**
	 * 运营商订单详细信息查询
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String, Object> queryBusinessOrderdetail(String clientId, String subOrderId) throws TException, BossException{
		//初始化对象
		HashMap<String, Object> map=new HashMap<String, Object>();
		BusinessOrderInfoRes res=new BusinessOrderInfoRes(); 
		//退款信息商品ID  SET
		Set<String> productIdSet = new HashSet<String>();
		//查询运营订单支付信息对象
		BusinessOrderPyamentInfoRes  pyamentInfoRes= businessOrderQueryService.queryBusinessOrderPaymentInfo(clientId, subOrderId);
		//获取订单支付 的基本信息
		BusinessOrderPyamentInfoVo pyamentInfoVo = pyamentInfoRes.getInfoVo();
		BeanUtils.copyProperties(res, pyamentInfoVo);// 封装数据基本信息
		if(pyamentInfoVo.getPaymentTime()==0){
			res.setPaymentTime("-");
		}else{
			res.setPaymentTime(DateUtil.longToString(pyamentInfoVo.getPaymentTime()));
		}
		if(pyamentInfoVo.getCreateTime()==0){
			res.setCreateTime("-");
		}else{
			res.setCreateTime(DateUtil.longToString(pyamentInfoVo.getCreateTime()));
		}
		
		
		//查询运营订单物流信息对象
		BusinessOrderShippingInfoVoRes  shippingInfoRes= businessOrderQueryService.queryBusinessOrderShippingInfo(clientId, subOrderId);
		//获取订单物流的基本信息
		BusinessOrderShippingInfoVo shippingInfoVo= shippingInfoRes.getInfoVo();
		BeanUtils.copyProperties(res, shippingInfoVo);// 封装数据基本信息
		if(shippingInfoVo.getShippingTime()==0){
			res.setShippingTime("-");
		}else{
			res.setShippingTime(DateUtil.longToString(shippingInfoVo.getShippingTime()));
		}
		String message = shippingInfoVo.getMessage();
		if(StringUtil.isNotBlank(message)){
			ArrayList<HashMap<String, Object>> msgList = new ArrayList<HashMap<String, Object>>();
			String[] msg = message.split("##");
			for(String strs : msg){
				String[] str = strs.split("@@");
				HashMap<String, Object> temp = new HashMap<String, Object>();
				temp.put("createTime", str[0]);//时间
				temp.put("context", str[1]);//事件
				msgList.add(temp);
			} 
			Collections.reverse(msgList);
			res.setMsgList(msgList);
		}
		
		//查询运营订单退款信息对象
		BusinessOrderRefundInfoVoRes  refundInfoRes = businessOrderQueryService.queryBusinessOrderRefundInfo(clientId, subOrderId);
		//查询运营订单交易概要信息对象
		BusinessOrderTradeInfoVoRes tradeInfoRes = businessOrderQueryService.queryBusinessOrderTradeInfo(clientId, subOrderId);
		
		
		
		
		//如果存在退款信息  显示退款商品的交易概要信息
		if(refundInfoRes.getInfoVoSize()>0){
			//获取退款信息基本数据
			List<BusinessOrderRefundInfoVo> refundInfoVos = refundInfoRes.getInfoVo();
			//获取交易概要的基本信息
			List<BusinessOrderTradeInfoVo> tradeInfoVos = tradeInfoRes.getInfoVos();
			Map<String, BusinessOrderTradeInfoVo> tradeMap = new HashMap<String, BusinessOrderTradeInfoVo>();
			for(BusinessOrderTradeInfoVo trade : tradeInfoVos){
				tradeMap.put(trade.getProductId(), trade);
			}
			
			//返回的退款信息
			List<BusinessOrderRefundRes> refundRes=new ArrayList<BusinessOrderRefundRes>();
			//返回的交易概要信息
			List<BusinessOrderTradeRes> trades=new ArrayList<BusinessOrderTradeRes>();
			
			for (BusinessOrderRefundInfoVo refundInfoVo : refundInfoVos) {
				//封装退款信息的基本数据
				BusinessOrderRefundRes refund=new BusinessOrderRefundRes();
				BeanUtils.copyProperties(refund, refundInfoVo);// 封装数据基本信息
				if(refundInfoVo.getApplyTime()==0){
					refund.setApplyTime("-");
				}else{
					refund.setApplyTime(DateUtil.longToString(refundInfoVo.getApplyTime()));
				}
				if(refundInfoVo.getRefundTime()==0){
					refund.setRefundTime("-");
				}else{
					refund.setRefundTime(DateUtil.longToString(refundInfoVo.getRefundTime()));
				}
				
				//退款信息的交易概要信息
				List<BusinessOrderTradeRes> refundTrades=new ArrayList<BusinessOrderTradeRes>();
				for (String productId : refundInfoVo.getProductIdList()) {
					BusinessOrderTradeInfoVo tradeInfoVo = tradeMap.get(productId);
					if(tradeInfoVo != null){
						//封装 退款信息的交易概要信息
						BusinessOrderTradeRes tradeRes=convertToBusinessOrderTradeRes(tradeInfoVo);
						//如果退款信息的商品ID 与 交易概要的商品ID一致 //在退款信息的下级加上 交易概要信息
						refundTrades.add(tradeRes);
						productIdSet.add(productId);
					}
				}
				refund.setTradeRes(refundTrades);
				refundRes.add(refund);
			}
			//退款订单列表
			res.setRefunds(refundRes);
			//给已退款的加上标识
			for(String productId : productIdSet){
				BusinessOrderTradeInfoVo tradeInfoVo=tradeMap.get(productId);
				tradeInfoVo.setIsRefund(true);
			}
			for(String productId : tradeMap.keySet()){
				BusinessOrderTradeInfoVo tradeInfoVo = tradeMap.get(productId);
				BusinessOrderTradeRes tradeRes=convertToBusinessOrderTradeRes(tradeInfoVo);
				trades.add(tradeRes);
			}
			res.setTrades(trades);
			
		}else{
			//不存在退款信息  则把所有的 交易概要信息显示
			
			//获取交易概要的基本信息
			List<BusinessOrderTradeInfoVo> tradeInfoVos = tradeInfoRes.getInfoVos();
			List<BusinessOrderTradeRes> trades=new ArrayList<BusinessOrderTradeRes>();
			for (BusinessOrderTradeInfoVo tradeInfoVo : tradeInfoVos) {
				BusinessOrderTradeRes tradeRes=convertToBusinessOrderTradeRes(tradeInfoVo);
				trades.add(tradeRes);
			}
			res.setTrades(trades);
		} 
		
		
		
		map.put("info", res);
		return map;
	}
	
	
	
	
	/**
	 * 供应商列表分页查询
	 * @return
	 * @throws ParseException 
	 * @throws TException 
	 */
	public HashMap<String, Object> queryProviderOrderInfoByPage(ProviderOrderQueryVoReq req) throws ParseException, TException{
		//初始化对象
		HashMap<String, Object> map=new HashMap<String, Object>();
		ProviderOrderQueryReq temp= new ProviderOrderQueryReq();
		List<ProviderOrderQueryVoRes> list=new ArrayList<ProviderOrderQueryVoRes>();
		
		//封装数据
		if(StringUtil.isNotBlank(req.getClientId())){
			temp.setClientId(req.getClientId());
		}
		if(StringUtil.isNotBlank(req.getOrderId())){
			temp.setOrderId(req.getOrderId());
		}
		if(StringUtil.isNotBlank(req.getPhone())){
			temp.setPhone(req.getPhone());
		}
		if(StringUtil.isNotBlank(req.getShippingStatus())){
			temp.setShippingStatus(req.getShippingStatus());
		}
		if(StringUtil.isNotBlank(req.getMemberCode())){
			temp.setMemberCode(req.getMemberCode().longValue());
		}
		if(StringUtil.isNotBlank(req.getEndTime())){
			temp.setEndTime(PramasUtil.DateFormat(req.getEndTime()));
		}
		if(StringUtil.isNotBlank(req.getBegTime())){
			temp.setBegTime(PramasUtil.DateFormat(req.getBegTime()));
		}
		
		//封装分页对象数据
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		
		//调用server端接口
		ProviderOrderQueryPageVo prores=providerOrderService.queryProviderOrderInfoByPage(temp, pageVo);
		BeanUtils.copyProperties(pageVo, prores.getPageVo());
		
		ProviderOrderQueryVoRes Res=null;
		if(prores.getOrderListSize()>0){
			for (ProviderOrderQueryVo ProviderOrderQueryVo : prores.getOrderList()) {
				Res=new ProviderOrderQueryVoRes();
				BeanUtils.copyProperties(Res, ProviderOrderQueryVo);
				if(ProviderOrderQueryVo.getCreateTime()==0){
					Res.setCreateTime("-");
				}else{
					Res.setCreateTime(DateUtil.longToString(ProviderOrderQueryVo.getCreateTime()));
				}
				if(ProviderOrderQueryVo.getInputTime()==0){
					Res.setInputTime("-");
				}else{	
					Res.setInputTime(DateUtil.longToString(ProviderOrderQueryVo.getInputTime()));
				}
				list.add(Res);
			}
			
		}
		map.put("list",list);
		map.put("page",pageVo);
		return map;
	}
	
	/**
	 * 导出供应商订单信息
	 * @return
	 * @throws ParseException 
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String, Object> exportProviderOrderInfo(ProviderOrderQueryVoReq req) throws ParseException, TException, BossException{
		//初始化对象
		HashMap<String,Object> map=new HashMap<String,Object>();
		ProviderOrderQueryReq temp= new ProviderOrderQueryReq();
		
		//封装数据
		if(StringUtil.isNotBlank(req.getClientId())){
			temp.setClientId(req.getClientId());
		}
		if(StringUtil.isNotBlank(req.getOrderId())){
			temp.setOrderId(req.getOrderId());
		}
		if(StringUtil.isNotBlank(req.getPhone())){
			temp.setPhone(req.getPhone());
		}
		if(StringUtil.isNotBlank(req.getShippingStatus())){
			temp.setShippingStatus(req.getShippingStatus());
		}
		if(StringUtil.isNotBlank(req.getMemberCode())){
			temp.setMemberCode(req.getMemberCode().longValue());
		}
		if(StringUtil.isNotBlank(req.getEndTime())){
			temp.setEndTime(PramasUtil.DateFormat(req.getEndTime()));
		}
		if(StringUtil.isNotBlank(req.getBegTime())){
			temp.setBegTime(PramasUtil.DateFormat(req.getBegTime()));
		}
		
		//调用server端接口
		ExportResultRes exportResultRes=providerOrderService.exportProviderOrderInfo(temp);
		ResultVo result= exportResultRes.getResultVo();
		
		//判断返回结果
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())){
			map.put("url", exportResultRes.getUrl());
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		}else{
			throw new BossException(result.getResultCode(),result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 导入供应商订单物流信息
	 * @return
	 * @throws BossException 
	 * @throws Exception 
	 * @throws Exception 
	 */
	public HashMap<String, Object> inputShippingInfo(List<List<String>> data) throws BossException,Exception{
		//初始化对象
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<ShippingExcelInfoVo> excelInfoVos=new ArrayList<ShippingExcelInfoVo>();
		ShippingExcelInfoVo sev=null; 
		for (int i = 0; i < data.size(); i++) {
			List<String> l = data.get(i);
			// 首行
			if (i == 0) {
				continue;
			}else{
				sev=new ShippingExcelInfoVo();
				if(StringUtil.isNotBlank(l.get(0).toString())){
					sev.setOrderId(l.get(0).toString());//订单编号
				}
				if(StringUtil.isNotBlank(l.get(1).toString())){
					sev.setOrderStatus(l.get(1).toString());//订单状态 
				}
				if(StringUtil.isNotBlank(l.get(2).toString())){
					sev.setMemberCode(l.get(2).toString());//用户编号 
				}
				if(StringUtil.isNotBlank(l.get(3).toString())){
					sev.setPhone(l.get(3).toString());// 用户手机号 
				}
				if(StringUtil.isNotBlank(l.get(4).toString())){
					sev.setTotalPrice(l.get(4).toString());//订单总金额 
				}
				if(StringUtil.isNotBlank(l.get(5).toString())){
					sev.setCreateTime(l.get(5).toString());//创建时间 
				}
				if(StringUtil.isNotBlank(l.get(6).toString())){
					sev.setClientName(l.get(6).toString());//客户端名称 
				}

				if(StringUtil.isNotBlank(l.get(7).toString())){
					sev.setConsignee(l.get(7).toString());//收货人
				}
				if(StringUtil.isNotBlank(l.get(8).toString())){
					sev.setProductInfo(l.get(8).toString());//商品信息
				}
				
				if(StringUtil.isNotBlank(l.get(9).toString())){
					sev.setRecvAddress(l.get(9).toString());//收货地址 
				}
				if(StringUtil.isNotBlank(l.get(10).toString())){
					sev.setShippingStatusDesc(l.get(10).toString());//发货状态
				}
				if(StringUtil.isNotBlank(l.get(11).toString())){
					sev.setShippingCorp(l.get(11).toString());//物流公司
				}
				if(StringUtil.isNotBlank(l.get(12).toString())){
					sev.setShippingId(NumberUtil.subZeroAndDot(l.get(12).toString()));//物流单号
				}
				if(StringUtil.isNotBlank(l.get(13).toString())){
					sev.setInputTime(l.get(13).toString());//上传时间 *
				}
				excelInfoVos.add(sev);
			}
		}
		
		//调用server端接口 
		LogCvt.info("导入的有效数据为:"+JSON.toJSONString(excelInfoVos));
		ExportResultRes exportResult= providerOrderService.inputShippingInfo(excelInfoVos);
		ResultVo result = exportResult.getResultVo();
		
		//判断返回结果
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			if(excelInfoVos != null && excelInfoVos.size() > 0){
				List<DeliveryCorpVo> deliveryCorpVos = deliveryCorpService.getDeliveryCorp(new DeliveryCorpVo());
				for(ShippingExcelInfoVo voReq : excelInfoVos){
					String crpCode = "";
					if(deliveryCorpVos != null && deliveryCorpVos.size() > 0){
						for(DeliveryCorpVo corpVo : deliveryCorpVos){
							if(corpVo.getName().equals(voReq.getShippingCorp())){
								crpCode = corpVo.getCorpCode();//存放物流公司编码
							}
						}
						if(StringUtil.isNotBlank(crpCode)
								&& StringUtil.isNotBlank(voReq.getShippingId())
								//&& StringUtil.isNotBlank(voReq.getRecvAddress())
								){
							//导入成功之后发送订阅请求
							kuaidiSupport.sendKuaidiSubscribe(crpCode,voReq.getShippingId(),voReq.getRecvAddress());
						}
					}
				}
			}
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else if("11004".equals(result.getResultCode())) {//11004为部分数据导入失败的结果码
			//返回导出Excel下载路径
			map.put("url", exportResult.getUrl());
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	
	/**
	 * 供应商订单设置出库
	 * @param orderId
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String, Object> shippingByOrderId(String orderId) throws TException, BossException{
		//初始化数据
		HashMap<String, Object> map=new HashMap<String, Object>();
		//调用server端接口
		ResultVo resultVo=providerOrderService.shippingByOrderId(orderId);
		//判断返回结果
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", resultVo.getResultCode());
			map.put("message", resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}
		return map;
	}
	
	
	/**
	 * 供应商订单信息更新
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 * @throws ParseException 
	 */
	public HashMap<String, Object> updateShippingInfo(ShippingInfoVoReq req) throws TException, BossException, ParseException{
		//初始化对象
		HashMap<String, Object> map=new HashMap<String, Object>();
		UpdateShippingInfoReq temp=new UpdateShippingInfoReq();
		
		//封装数据
		if(StringUtil.isNotBlank(req.getSubOrderId())){
			temp.setSubOrderId(req.getSubOrderId());
		}
		if(StringUtil.isNotBlank(req.getShippingCorpCode())){
			temp.setShippingCorpCode(req.getShippingCorpCode());
		}
		if(StringUtil.isNotBlank(req.getShippingId())){
			temp.setShippingId(req.getShippingId());
		}
		if(StringUtil.isNotBlank(req.getInputTime())){
			temp.setInputTime(PramasUtil.DateFormat(req.getInputTime()));
		}
		
		//调用server端接口
		ResultVo  resultVo =providerOrderService.updateShippingInfo(temp);
		//判断返回结果
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			//更新物流信息后发送订阅请求
			if(StringUtil.isNotBlank(temp.getShippingCorpCode())
					&& StringUtil.isNotBlank(temp.getShippingId())
					//&& StringUtil.isBlank(req.getRecvAddress())
					){
				DeliveryCorpVo deliveryCorpVo = new DeliveryCorpVo();
				deliveryCorpVo.setId(Long.valueOf(temp.getShippingCorpCode()));
				List<DeliveryCorpVo> deliveryCorpVos = deliveryCorpService.getDeliveryCorp(deliveryCorpVo);
				if(deliveryCorpVos != null && deliveryCorpVos.size()>0){
					kuaidiSupport.sendKuaidiSubscribe(deliveryCorpVos.get(0).getCorpCode(),temp.getShippingId(),req.getRecvAddress());
				}
				
			}
			map.put("code", resultVo.getResultCode());
			map.put("message", resultVo.getResultDesc());
			
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}
		return map;
	}
	

	private BusinessOrderTradeRes convertToBusinessOrderTradeRes(BusinessOrderTradeInfoVo tradeInfoVo){
		BusinessOrderTradeRes tradeRes=new BusinessOrderTradeRes();
		BeanUtils.copyProperties(tradeRes, tradeInfoVo);
		if(tradeInfoVo.getSettlementTime()==0){
			tradeRes.setSettlementTime("-");
		}else{
			tradeRes.setSettlementTime(DateUtil.longToString(tradeInfoVo.getSettlementTime()));
		}
		if(tradeInfoVo.getDeliveryTime()==0){
			tradeRes.setDeliveryTime("-");
		}else{
			tradeRes.setDeliveryTime(DateUtil.longToString(tradeInfoVo.getDeliveryTime()));
		}
		if(tradeInfoVo.getDealineConsumeTime()==0){
			tradeRes.setDealineConsumeTime("-");
		}else{
			tradeRes.setDealineConsumeTime(DateUtil.longToString(tradeInfoVo.getDealineConsumeTime()));
		}
		
		return tradeRes;
	}
	/**
	 * 运营发起退款
	 * @param subOrderId
	 * @param refundReason
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @author liaopeixin
	 *	@date 2016年1月5日 上午11:15:08
	 */
	public Map<String,Object> refundForBoss(String subOrderId,String refundReason,String productId,Integer quantity,String clientId) throws TException, BossException{
		Map<String,Object> map =new HashMap<String,Object>();
		//调用后台接口
		RefundResponseVo res=refundService.doRefundOfBoutiqueBoss(subOrderId, refundReason,productId,quantity,clientId);
		ResultVo  resultVo=res.getResultVo();
		
		//判断返回结果
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", resultVo.getResultCode());
			map.put("message", "订单"+subOrderId+"发起退款成功！");
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}
		return map;
	}
}
