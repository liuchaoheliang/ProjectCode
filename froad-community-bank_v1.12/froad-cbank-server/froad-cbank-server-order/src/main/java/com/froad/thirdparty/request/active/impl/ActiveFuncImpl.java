
package com.froad.thirdparty.request.active.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TMultiplexedProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

import com.froad.common.beans.ResultBean;
import com.froad.enums.PayType;
import com.froad.enums.PaymentReason;
import com.froad.enums.PaymentStatus;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.po.Payment;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.po.refund.RefundHistory;
import com.froad.po.refund.RefundPaymentInfo;
import com.froad.po.refund.RefundProduct;
import com.froad.po.refund.RefundShoppingInfo;
import com.froad.support.OrderSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.payment.DataPersistentImpl;
import com.froad.support.payment.DataPersistent;
import com.froad.thirdparty.request.active.ActiveFunc;
import com.froad.thirdparty.request.userengine.UserEngineFunc;
import com.froad.thirdparty.request.userengine.impl.UserEngineFuncImpl;
import com.froad.thrift.service.ActiveRunService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.CloseMarketOrderReqVo;
import com.froad.thrift.vo.active.FullGiveActiveVo;
import com.froad.thrift.vo.active.FullGiveCheckReqVo;
import com.froad.thrift.vo.active.OrderProductVo;
import com.froad.thrift.vo.active.ReturnMarketOrderBackReqVo;
import com.froad.thrift.vo.active.ReturnMarketOrderReqVo;
import com.froad.thrift.vo.active.ReturnMarketOrderResVo;
import com.froad.thrift.vo.active.ReturnSubOrderBackReqVo;
import com.froad.thrift.vo.active.ReturnSubOrderProductBackReqVo;
import com.froad.thrift.vo.active.ReturnSubOrderProductReqVo;
import com.froad.thrift.vo.active.ReturnSubOrderProductResVo;
import com.froad.thrift.vo.active.ReturnSubOrderReqVo;
import com.froad.thrift.vo.active.ReturnSubOrderResVo;
import com.froad.thrift.vo.active.UpdateMarketOrderReqVo;
import com.froad.thrift.vo.active.UpdateMarketOrderResVo;
import com.froad.util.Arith;
import com.froad.util.JSonUtil;
import com.froad.util.active.ActiveConst;
import com.froad.util.payment.EsyT;
import com.pay.user.dto.UserResult;

public class ActiveFuncImpl implements ActiveFunc {
	private DataPersistent dataPersistent = new DataPersistentImpl();
	private OrderSupport orderSupport = new OrderSupportImpl();
	private static final UserEngineFunc userEngineFunc = new UserEngineFuncImpl();
	
	//标准版后缀
	private final static String REQ_ID_PREFIX = "CBK002"; 
    
	//请求的ip
	private static String host = ActiveConst.ACTIVE_HOST;
	
	//请求的端口
	private static int port = ActiveConst.ACTIVE_PORT;

	@Override
	public ResultBean noticeMarketingPlatformPaySuccess(OrderMongo order,boolean status) {

		long memberCode = order.getMemberCode() == null ? 0L : order.getMemberCode();
		if(memberCode == 0 ){
			LogCvt.info("OrderMongo 中memberCode为空");
		}
		long paymentTime = order.getPaymentTime() == null ? new Date().getTime() : order.getPaymentTime();
		if(paymentTime == 0 ){
			LogCvt.info("OrderMongo 中paymentTime为空");
		}
		boolean isF2FOrder = order.getIsQrcode() == 1;
		
		//TODO 2016-02-18 新增payBillNo参数(支付成功记录支付账单号)
		String payBillNo = "";
		if(status){
			List<Payment> payments = this.queryPayments(order.getClientId(), order.getOrderId(), PaymentReason.payment.getCode(),PaymentStatus.pay_success.getCode());
			if(payments != null && payments.size() > 0){
				if(payments.size() == 1){//纯积分或者纯现金支付
					payBillNo = payments.get(0).getBillNo();
				}else{
					payBillNo = ",";
					//积分+现金支付,最多两条记录(逗号前面的是积分支付产生的bill_no，逗号后边的是现金支付产生的bill_no)
					for(Payment p : payments){
						if(PayType.cash.code == p.getPaymentType()){
							payBillNo += p.getBillNo();
						}else{
							payBillNo = p.getBillNo() + payBillNo;
						}
					}
				}
			}
			LogCvt.info("支付成功，获取payBillNo=" + payBillNo);
		}
		
		//TODO 2016-02-22 支付成功,包含满赠信息的订单时,设置成员属性"满赠活动id列表 fullGiveActiveIds
		//记录是否参加满赠活动
		boolean isFullGiveActive = false;
		 List<String> fullGiveActiveIds = null;
		 String loginId = null;
		 if(status){
			 fullGiveActiveIds = getFullGiveActiveIds(order.getClientId(), order.getOrderId(),null);
			 if(fullGiveActiveIds != null && fullGiveActiveIds.size() > 0){
				 loginId = getLoginIdByMemberCode(memberCode);
				 isFullGiveActive = true;
			 }
		 }
		 
		//构造请求参数
		UpdateMarketOrderReqVo vo = new UpdateMarketOrderReqVo(getReqId(), order.getClientId(),
				memberCode, order.getOrderId(), order.getMarketId(), status, paymentTime, isF2FOrder,payBillNo, fullGiveActiveIds,loginId);
		
		//增加红包ID 面对面订单，支付失败，有用红包支付
		List<String> list = new ArrayList<String>();
		//增加红包券ID 
		if(StringUtils.isNotBlank(order.getRedPacketId())){
			String[] redPacketIds = order.getRedPacketId().split(",");
			List<String> l = Arrays.asList(redPacketIds);
			list.addAll(l);
		}
		//增加现金券ID 
		if(StringUtils.isNotBlank(order.getCashCouponId())){
			String[] cashCouponIds = order.getCashCouponId().split(",");
			List<String> l = Arrays.asList(cashCouponIds);
			list.addAll(l);
		}
		
		vo.setVouchersIdList(list);
		
		ActiveRunService.Client client = null;
		UpdateMarketOrderResVo updateMarketOrderResVo = null;
        TTransport transport = null;
        try {
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //构造client对象
            client = getActiveRunServiceClient(transport);
            
            LogCvt.info("支付完成后-通知活动模块-请求：" + JSonUtil.toJSonString(vo));
            
            //接口调用
            updateMarketOrderResVo = client.updateMarketOrder(vo);
            
            //满赠活动，支付完成，更新营销订单的时候，会分别返回满赠金额、满赠积分，要更新到大订单上去
            if(status && isFullGiveActive && ResultCode.success.getCode().equals(updateMarketOrderResVo.getResult().getResultCode())){
            	List<FullGiveActiveVo> fullGiveActiveList = updateMarketOrderResVo.getFullGiveActiveList();
            	if(fullGiveActiveList != null && fullGiveActiveList.size() > 0){
            		double giveMoney = 0.0;
            		double givePoint = 0.0;
            		for(FullGiveActiveVo fullGiveActiveVo : fullGiveActiveList){
            			//2-红包，4-联盟积分
            			if("2".equals(fullGiveActiveVo.getActiveType())){
            				giveMoney += fullGiveActiveVo.getMonry();
            			}else if("4".equals(fullGiveActiveVo.getActiveType())){
            				givePoint += fullGiveActiveVo.getMonry();
            			}
            		}
            		//更新大订单
            		boolean flag = orderSupport.updateOrderForGive(order.getClientId(),  order.getOrderId(), (int)Arith.mul(giveMoney,1000), (int)Arith.mul(givePoint,1000));
            		LogCvt.info("满赠活动，更新满赠金额、满赠积分到大订单，操作结果：" + flag);
            	}
            }
            LogCvt.info("支付完成后-通知活动模块-响应："+JSonUtil.toJSonString(updateMarketOrderResVo));
            
        } catch (Exception e) {
        	LogCvt.error("支付完成后-通知活动模块-连接异常: " + JSonUtil.toJSonString(updateMarketOrderResVo) + " port: " + port + " host: " + host, e);
        	 return new ResultBean(ResultCode.failed);
        }  finally {
        	if(transport != null){
        		IOUtils.closeQuietly(transport);
        	}
        }
		        
        
        return doRetrun(updateMarketOrderResVo.getResult());
	}

	@Override
	public ResultBean noticeMarketingPlatformRefund(RefundHistory refundHis,boolean isQuery) {
		
		List<RefundShoppingInfo> refundShoppingInfoList =  refundHis.getShoppingInfo();
		if(refundShoppingInfoList == null || refundShoppingInfoList.size() == 0){
			return new ResultBean(ResultCode.failed,"退款商品信息为空");
		}
		
		List<ReturnSubOrderReqVo> returnSubOrderList = getreturnSubOrderList(refundShoppingInfoList);
		
		//TODO 2016-02-18 新增退款账单号和退款金额参数
		String returnBillNo = "";	//退款账单号
		double returnMoney = 0;		//退款金额
		if(!isQuery){
			List<Object> returnMsgList = this.getReturnBillNoAndReturnMoney(refundHis);
			returnBillNo = String.valueOf(returnMsgList.get(0));	//退款账单号
			returnMoney = (Double)returnMsgList.get(1);		//退款金额
		}
		
		//构造请求参数
		ReturnMarketOrderReqVo vo = new ReturnMarketOrderReqVo(getReqId(), refundHis.getClientId(),
				refundHis.getOrderId(),isQuery, returnSubOrderList, returnBillNo, returnMoney);
		
		ActiveRunService.Client client = null;
        TTransport transport = null;
        ResultBean resultBean = null;
        try {
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //构造client对象
            client = getActiveRunServiceClient(transport);
            
            LogCvt.info("退款-调用活动模块-请求(isQuery="+isQuery+")：" + JSonUtil.toJSonString(vo));
            
            //接口调用
            ReturnMarketOrderResVo returnMarketOrderResVo = client.returnMarketOrder(vo);
            
            LogCvt.info("退款-调用活动模块-响应(isQuery="+isQuery+")："+JSonUtil.toJSonString(returnMarketOrderResVo));
            
            //接口调用失败，则返回失败
            if(!ActiveConst.SUCCESS_CODE.equals(returnMarketOrderResVo.getResult().getResultCode())){
            	return new ResultBean(ResultCode.failed,"调用营销平台计算优惠价格失败-" + returnMarketOrderResVo.getResult().getResultDesc());
            }
            
            if(isQuery){
            
	            //构造子订单ID和子订单商品集合的映射
	            Map<String,List<RefundProduct>> refundShoppingInfoMap = new HashMap<String, List<RefundProduct>>();
	    		for(RefundShoppingInfo refundShoppingInfo : refundShoppingInfoList){
	    			refundShoppingInfoMap.put(refundShoppingInfo.getSubOrderId(), refundShoppingInfo.getProducts());
	    		}
	    		
	            //接口调用成功，计算优惠的总价格，将其封装到ResultBean中，并且将优惠详细信息设置到refundHis中
	            List<ReturnSubOrderResVo> returnSubOrderResVoList = returnMarketOrderResVo.getReturnSubOrderResList();
	            if(returnSubOrderResVoList != null && returnSubOrderResVoList.size() > 0){
	            	for(ReturnSubOrderResVo returnSubOrderResVo : returnSubOrderResVoList){
	            		//子订单ID
	            		String subOrderId  = returnSubOrderResVo.getSubOrderId();
	            		//子订单对应的商品
	            		List<ReturnSubOrderProductResVo> returnSubOrderProductResList = returnSubOrderResVo.getReturnSubOrderProductResList();
	            		
	            		Map<String,RefundProduct> refundProductMap = getRefundProductList(refundShoppingInfoMap.get(subOrderId));
	            		
	            		if(returnSubOrderProductResList != null &&returnSubOrderProductResList.size() > 0){
	            			for(ReturnSubOrderProductResVo returnSubOrderProducRestVo : returnSubOrderProductResList){
	            				
	            				//累加优惠总金额
	//            				discountPrice += (int)(returnSubOrderProducRestVo.getPrice()*1000 + returnSubOrderProducRestVo.getVipPrice()*1000);
	            				
	            				//将优惠的详细信息保存起来
	            				if(refundProductMap.containsKey(returnSubOrderProducRestVo.getProductId())){
	            					RefundProduct refundProduct = refundProductMap.get(returnSubOrderProducRestVo.getProductId());
	            					refundProduct.setDiscountQuantity(returnSubOrderProducRestVo.getNormalCount());
	            					refundProduct.setDiscountVipQuantity(returnSubOrderProducRestVo.getVipCount());
	//            					refundProduct.setDiscountPrice(returnSubOrderProducRestVo.getPrice());
	//            					refundProduct.setDiscountVipPrice(returnSubOrderProducRestVo.getVipPrice());
	            				}
	            			}
	            		}
	            	}
	            }
	            resultBean =  new ResultBean(ResultCode.success,"调用活动模块查询优惠金额成功");
            }else{
            	resultBean =  new ResultBean(ResultCode.success,"退款成功后通知活动模块成功");
            }
        } catch (Exception e) {
        	LogCvt.error("退款前-调用活动模块查询优惠金额-连接异常:  port: " + port + " host: " + host, e);
        	 return new ResultBean(ResultCode.failed);
        }  finally {
        	if(transport != null){
        		IOUtils.closeQuietly(transport);
        	}
        }
        
        return resultBean;
	}
	
	@Override
	public ResultBean noticeMarketingPlatformRefundFailure(RefundHistory refundHis) {
		
		if(!isActiveOrder(refundHis.getClientId(),refundHis.getOrderId())){
			return new ResultBean(ResultCode.success,"订单未参加营销活动");
		}
		
		List<RefundShoppingInfo> refundShoppingInfoList =  refundHis.getShoppingInfo();
		if(refundShoppingInfoList == null || refundShoppingInfoList.size() == 0){
			return new ResultBean(ResultCode.failed,"退款商品信息为空");
		}
		
		//退款优惠详情
		List<ReturnSubOrderBackReqVo> returnSubOrderBackReqList = getreturnSubOrderBackList(refundShoppingInfoList);
		
		//TODO 2016-02-18 新增退款账单号和退款金额参数
		List<Object> returnMsgList = this.getReturnBillNoAndReturnMoney(refundHis);
		String returnBillNo = String.valueOf(returnMsgList.get(0));	//退款账单号
		double returnMoney = (Double)returnMsgList.get(1);		//退款金额
				
		//构造请求参数
		ReturnMarketOrderBackReqVo vo = new ReturnMarketOrderBackReqVo(getReqId(), refundHis.getClientId(),
				refundHis.getOrderId(), returnSubOrderBackReqList, returnBillNo, returnMoney);
				
		ActiveRunService.Client client = null;
		ResultVo resultVo = null;
        TTransport transport = null;
        try {
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //构造client对象
            client = getActiveRunServiceClient(transport);
            
            LogCvt.info("退款失败-通知活动模块回退-请求：" + JSonUtil.toJSonString(vo));
            
            //接口调用
            resultVo = client.returnMarketOrderBack(vo);
            
            LogCvt.info("退款失败-通知活动模块回退-响应："+JSonUtil.toJSonString(resultVo));
            
        } catch (Exception e) {
        	LogCvt.error("退款失败-通知活动模块回退-连接异常: " + JSonUtil.toJSonString(resultVo) + " port: " + port + " host: " + host, e);
        	 return new ResultBean(ResultCode.failed);
        }  finally {
        	if(transport != null){
        		IOUtils.closeQuietly(transport);
        	}
        }
        
        return doRetrun(resultVo);
	}

	@Override
	public ResultBean noticeMarketingPlatformRefundFinish(RefundHistory refundHis) {
		
		List<RefundShoppingInfo> refundShoppingInfoList =  refundHis.getShoppingInfo();
		if(refundShoppingInfoList == null || refundShoppingInfoList.size() == 0){
			return new ResultBean(ResultCode.failed,"退款商品信息为空");
		}
		
		// 原因  0-系统关单/客户取消订单  1-客户退货关单
		int reason = 1; 
		String reqId = getReqId();
		long memberCode = StringUtils.isEmpty(refundHis.getMemberCode()) ? 0L : Long.parseLong(refundHis.getMemberCode());
		//构造请求参数
		CloseMarketOrderReqVo vo = new CloseMarketOrderReqVo(reqId, refundHis.getClientId(), refundHis.getOrderId(),
				memberCode, reason);
		
		ActiveRunService.Client client = null;
		ResultVo resultVo = null;
        TTransport transport = null;
        try {
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //构造client对象
            client = getActiveRunServiceClient(transport);
            
            LogCvt.info("全部退款完成后-通知活动模块-请求：" + JSonUtil.toJSonString(vo));
            
            //接口调用
            resultVo = client.closeMarketOrder(vo);
            
            LogCvt.info("全部退款完成后-通知活动模块-响应："+JSonUtil.toJSonString(resultVo));
            
        } catch (Exception e) {
        	LogCvt.error("全部退款完成后-通知活动模块-连接异常: " + JSonUtil.toJSonString(resultVo) + " port: " + port + " host: " + host, e);
        	 return new ResultBean(ResultCode.failed);
        }  finally {
        	if(transport != null){
        		IOUtils.closeQuietly(transport);
        	}
        }
        
        return doRetrun(resultVo);
	}
	
	
	@Override
	public ResultBean fullGiveCheck(OrderMongo order) {
		
		//TODO 
		//参加满赠活动的商品ID
		List<OrderProductVo> orderProductList = new ArrayList<OrderProductVo>();
		
		//满赠活动的ID
		List<String> fullGiveActiveIds = getFullGiveActiveIds(order.getClientId(), order.getOrderId(),orderProductList);
		 
		//未参加满赠活动，则不作校验，直接返回
		if(fullGiveActiveIds == null || fullGiveActiveIds.size() == 0){
			return new ResultBean(ResultCode.success);
		}

		Long memberCode = order.getMemberCode() != null ? order.getMemberCode() : 0L;
		String reqId = getReqId();
		FullGiveCheckReqVo vo = new FullGiveCheckReqVo(reqId, order.getClientId(), memberCode, fullGiveActiveIds, orderProductList);
		
		ActiveRunService.Client client = null;
		ResultVo resultVo = null;
        TTransport transport = null;
        try {
            
            //设置调用的服务地址，端口
            transport = new TSocket(host, port); 
            transport.open();
            
            //构造client对象
            client = getActiveRunServiceClient(transport);
            
            LogCvt.info("满赠活动支付前校验-请求活动模块-请求：" + JSonUtil.toJSonString(vo));
            
            //接口调用
            resultVo = client.fullGiveCheck(vo);
            
            LogCvt.info("满赠活动支付前校验-请求活动模块-响应："+JSonUtil.toJSonString(resultVo));
            
        } catch (Exception e) {
        	LogCvt.error("满赠活动支付前校验-请求活动模块-连接异常: " + JSonUtil.toJSonString(resultVo) + " port: " + port + " host: " + host, e);
        	 return new ResultBean(ResultCode.failed);
        }  finally {
        	if(transport != null){
        		IOUtils.closeQuietly(transport);
        	}
        }
        
        return doRetrun(resultVo);
	}
	
	/**
	 * 构造退款请求参数：子订单及对应的商品信息
	 */
	private List<ReturnSubOrderReqVo> getreturnSubOrderList(List<RefundShoppingInfo> refundShoppingInfoList){
		
		//构造数据
		List<ReturnSubOrderReqVo> returnSubOrderList = new ArrayList<ReturnSubOrderReqVo>();
		try {
			for(RefundShoppingInfo refundShoppingInfo : refundShoppingInfoList){
				ReturnSubOrderReqVo returnSubOrderVo = new ReturnSubOrderReqVo();
				returnSubOrderVo.setSubOrderId(refundShoppingInfo.getSubOrderId());
				
				List<RefundProduct> refundProductList = refundShoppingInfo.getProducts();
				if(refundProductList != null && refundProductList.size() > 0){
					List<ReturnSubOrderProductReqVo> returnSubOrderProductList = new ArrayList<ReturnSubOrderProductReqVo>();
					for(RefundProduct refundProduct : refundProductList){
						
						returnSubOrderProductList.add(new ReturnSubOrderProductReqVo(
								refundProduct.getProductId(), refundProduct.getVipQuantity(),refundProduct.getQuantity()));
					}
					returnSubOrderVo.setReturnSubOrderProductReqList(returnSubOrderProductList);
				}
				
				returnSubOrderList.add(returnSubOrderVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnSubOrderList;
	}

	/**
	 * 构造退款回退请求参数：子订单及对应的商品信息（包含商品的优惠详细信息）
	 */
	private List<ReturnSubOrderBackReqVo> getreturnSubOrderBackList(List<RefundShoppingInfo> refundShoppingInfoList){
		
		//构造数据
		List<ReturnSubOrderBackReqVo> returnSubOrderList = new ArrayList<ReturnSubOrderBackReqVo>();
		try {
			for(RefundShoppingInfo refundShoppingInfo : refundShoppingInfoList){
				ReturnSubOrderBackReqVo returnSubOrderVo = new ReturnSubOrderBackReqVo();
				returnSubOrderVo.setSubOrderId(refundShoppingInfo.getSubOrderId());
				
				List<RefundProduct> refundProductList = refundShoppingInfo.getProducts();
				if(refundProductList != null && refundProductList.size() > 0){
					List<ReturnSubOrderProductBackReqVo> returnSubOrderProductList = new ArrayList<ReturnSubOrderProductBackReqVo>();
					for(RefundProduct refundProduct : refundProductList){
						
						returnSubOrderProductList.add(new ReturnSubOrderProductBackReqVo(refundProduct.getProductId(),
								refundProduct.getDiscountVipQuantity(),0.0,
								refundProduct.getDiscountQuantity(), 0.0));
					}
					returnSubOrderVo.setReturnSubOrderProductBackReqList(returnSubOrderProductList);
				}
				returnSubOrderList.add(returnSubOrderVo);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return returnSubOrderList;
	}

	/**
	 * 构造 “子订单Id-子订单中商品集合” 的映射
	 */
	private Map<String,RefundProduct> getRefundProductList(List<RefundProduct> refundProductList){
		Map<String,RefundProduct> map = new HashMap<String, RefundProduct>();
		
		if(refundProductList != null &&refundProductList.size()>0){
			for(RefundProduct refundProduct : refundProductList){
				map.put(refundProduct.getProductId(), refundProduct);
			}
		}
		return map;
	}
	
	/**
	 * 构造请求活动平台的client对象
	 */
	private ActiveRunService.Client getActiveRunServiceClient(TTransport transport){
            
        //设置传输协议为 TBinaryProtocol 
        TProtocol protocol = new TBinaryProtocol(transport); 
        TMultiplexedProtocol multiProtocol = new TMultiplexedProtocol(protocol,ActiveRunService.class.getSimpleName());
        return new ActiveRunService.Client(multiProtocol);
	}
	

	
	/**
	 * 通过大订单判断是否参加了营销活动
	 */
	private boolean isActiveOrder(String clientId,String orderId){
		OrderMongo order = new OrderSupportImpl().getOrderById(clientId, orderId);
		return ActiveConst.IS_ACTIVE_TRUE.equals(order.getIsActive());
	}
	
	/**
	 * 获取请求ID
	 */
	private String getReqId(){
		return REQ_ID_PREFIX + com.froad.util.DateUtil.formatDateTime(com.froad.util.DateUtil.DATE_FORMAT2, new Date()) + EsyT.simpleID();
	}
	
	private ResultBean doRetrun(ResultVo resultVo){
		if (resultVo != null && ActiveConst.SUCCESS_CODE.equals(resultVo.getResultCode())){
            return new ResultBean(ResultCode.success,resultVo);
        }else{
            return new ResultBean(ResultCode.failed,resultVo);
        }
	}
	
	/**
	 * 根据给定的条件查询支付流水记录
	 * Function : queryPayments<br/>
	 * 2016年2月18日 下午3:36:46 <br/>
	 *  
	 * @author KaiweiXiang
	 * @param clientId
	 * @param orderId
	 * @param paymentReason
	 * @return
	 */
	private List<Payment> queryPayments(String clientId,String orderId,String paymentReason,String paymentStatus){
		
		Payment payment = new Payment();
		payment.setClientId(clientId);
		payment.setOrderId(orderId);
		payment.setPaymentReason(paymentReason);
		if(paymentStatus != null){
			payment.setPaymentStatus(paymentStatus);
		}
		
		List<Payment> list = dataPersistent.findByPaymentConditionFromMongoDB(payment);
		
		return list;
	}
	
	private List<SubOrderMongo> getSubOrdersByOrderId(String clientId,String orderId){
		List<SubOrderMongo> subOrderList =  orderSupport.getSubOrderListByOrderId(clientId,orderId);
		return subOrderList;
	}
	
	//获取满赠活动的ID
	private List<String> getFullGiveActiveIds(String clientId,String orderId,List<OrderProductVo> orderProductList){
		List<String> fullGiveActiveIds = new ArrayList<String>();
		List<SubOrderMongo> subOrderList = getSubOrdersByOrderId(clientId, orderId);
		if(subOrderList != null && subOrderList.size() > 0){
			Set<String> set = new HashSet<String>();
			for(SubOrderMongo subOrder : subOrderList){
				List<ProductMongo> productList = subOrder.getProducts();
				if(productList != null && productList.size() > 0){
					for(ProductMongo product : productList){
						//判断是否满赠活动
						if(StringUtils.isNotEmpty(product.getGiveActiveId())){
							String giveActiveId = product.getGiveActiveId();
							set.add(giveActiveId);
							if(orderProductList != null){
								orderProductList.add(new OrderProductVo(product.getProductId(), product.getProductName(), null,
										product.getMoney(), product.getQuantity(), product.getVipMoney(), product.getVipQuantity(), giveActiveId));
							}
						}
					}
				}
			}
			if(set.size() > 0){
				fullGiveActiveIds.addAll(set);
			}
		}
		return fullGiveActiveIds;
	}
	
	//获取退款账单号和退款金额
	private List<Object> getReturnBillNoAndReturnMoney(RefundHistory refundHis){
		//2016-02-18 新增退款账单号和退款金额参数
		String returnBillNo = "";	//退款账单号
		double returnMoney = 0;		//退款金额
		
		List<RefundPaymentInfo> paymentInfoList = refundHis.getPaymentInfo();
		if(paymentInfoList != null && paymentInfoList.size() > 0){
			for(RefundPaymentInfo refundPaymentInfo : paymentInfoList){
				
				//不记录退银行积分
				if(PayType.bankPoint.equals(refundPaymentInfo.getType())){
					continue;
				}
				//查询退款流水
				Payment payment = dataPersistent.findByPaymentIdFromMongoDB(refundPaymentInfo.getPaymentId());
				if(payment != null){
					if(returnBillNo.length() > 0){
						returnBillNo += ",";
					}
					returnBillNo += payment.getBillNo();
					returnMoney += payment.getPaymentValue();
				}
			}
		}
		
		List<Object> list = new ArrayList<Object>();
		list.add(returnBillNo);
		list.add(returnMoney);
		return list;
	}
	
	private String getLoginIdByMemberCode(long memberCode){
		UserResult userResult = userEngineFunc.queryByMemberCode(memberCode);
        if (userResult.getResult()) {// 请求成功
        	if(userResult.getUserList() != null && userResult.getUserList().size() != 0){
        		return userResult.getUserList().get(0).getLoginID();
        	}
        }
        return null;
	}
}
