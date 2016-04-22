package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.enums.ActiveType;
import com.froad.enums.ResultCode;
import com.froad.handler.ActiveOrderDetailHandler;
import com.froad.handler.impl.ActiveOrderDetailHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.ActiveFindMarketOrderLogic;
import com.froad.po.ActiveOrderDetail;
import com.froad.po.FindMarketOrderReq;
import com.froad.po.FindMarketOrderRes;
import com.froad.po.FindMarketSubOrder;
import com.froad.po.FindMarketSubOrderProduct;
import com.froad.po.Result;
import com.froad.util.ActiveUtils;

public class ActiveFindMarketOrderLogicImpl implements ActiveFindMarketOrderLogic {
	private ActiveOrderDetailHandler activeOrderDetailHandler =new ActiveOrderDetailHandlerImpl();

	@Override
	public FindMarketOrderRes findMarketOrder(FindMarketOrderReq findMarketOrderReq) {
		
		FindMarketOrderRes findMarketOrderRes = new FindMarketOrderRes();
		
		try{
			
			String clientId = findMarketOrderReq.getClientId();
			String orderId = findMarketOrderReq.getOrderId();
			
			ActiveOrderDetail activeOrderDetail=new ActiveOrderDetail();
			activeOrderDetail.setClientId(clientId);
			activeOrderDetail.setOrderId(orderId);
			activeOrderDetail.setProductId(findMarketOrderReq.getProductId());
			activeOrderDetail.setSubOrderId(findMarketOrderReq.getSubOrderId());
			List<ActiveOrderDetail> activeOrderDetailList = activeOrderDetailHandler.findActiveOrderDetail(activeOrderDetail);
			if( activeOrderDetailList == null || activeOrderDetailList.size()==0 ){
				LogCvt.info("--查询订单交易--根据条件查询信息失败--");
				findMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "根据条件查询订单为空"));
				return findMarketOrderRes;
			}
			
			findMarketOrderRes.setReqId(findMarketOrderReq.getReqId());
			findMarketOrderRes.setClientId(clientId);
			findMarketOrderRes.setOrderId(orderId);
			
//			for (ActiveOrderDetail detail : activeOrderDetailList) {
//				FindMarketSubOrderProduct product = new FindMarketSubOrderProduct();
//				product.setCutMoney(((double)detail.getOrgNormalPriceMarket())/1000);
//				product.setVipCutMoney(((double)detail.getOrgVipPriceMarket())/1000);
//				product.setProductId(detail.getProductId());
//				FindMarketSubOrder subOrder = new FindMarketSubOrder();
//				subOrder.setSubOrderId(detail.getSubOrderId());
//				List<FindMarketSubOrderProduct> findMarketSubOrderProductList=null;
//				if(subOrder.getFindMarketSubOrderProductList()==null||subOrder.getFindMarketSubOrderProductList().size()==0){
//					 findMarketSubOrderProductList=new ArrayList<FindMarketSubOrderProduct>();
//				}else{
//					 findMarketSubOrderProductList=subOrder.getFindMarketSubOrderProductList();;	
//				}
//				findMarketSubOrderProductList.add(product);
//				subOrder.setFindMarketSubOrderProductList(findMarketSubOrderProductList);
//				List<FindMarketSubOrder> findMarketSubOrderList=null; 
//				if(findMarketOrderRes.getFindMarketSubOrderList()==null||findMarketOrderRes.getFindMarketSubOrderList().size()==0){
//					findMarketSubOrderList=new ArrayList<FindMarketSubOrder>();
//				}else{
//					findMarketSubOrderList=findMarketOrderRes.getFindMarketSubOrderList();
//				}
//				findMarketSubOrderList.add(subOrder);
//				findMarketOrderRes.setFindMarketSubOrderList(findMarketSubOrderList);
//			}
			
			List<FindMarketSubOrder> findMarketSubOrderList = new ArrayList<FindMarketSubOrder>();
			List<String> subOrderIdList = ActiveUtils.countSubOrderId(activeOrderDetailList);
			for( String subOrderId : subOrderIdList ){
				FindMarketSubOrder findMarketSubOrder = new FindMarketSubOrder();
				List<FindMarketSubOrderProduct> findMarketSubOrderProductList = new ArrayList<FindMarketSubOrderProduct>();
				List<ActiveOrderDetail> sameSubOrderIdDetailList = ActiveUtils.countSameSubOrderId(activeOrderDetailList, subOrderId);
				for( ActiveOrderDetail detail : sameSubOrderIdDetailList ){
					String type = detail.getType();
					String productId = detail.getProductId();
					FindMarketSubOrderProduct findMarketSubOrderProduct = null;
					findMarketSubOrderProduct = findOne(productId, findMarketSubOrderProductList);
					if( ActiveType.fullCut.getCode().equals(type) ){
						findMarketSubOrderProduct.setCutMoney(((double)detail.getOrgNormalPriceMarket())/1000);
						findMarketSubOrderProduct.setVipCutMoney(((double)detail.getOrgVipPriceMarket())/1000);
					}else if( ActiveType.vouchers.getCode().equals(type) ){
						findMarketSubOrderProduct.setVouMoney(((double)detail.getOrgNormalPriceMarket())/1000);
						findMarketSubOrderProduct.setVipVouMoney(((double)detail.getOrgVipPriceMarket())/1000);
					}
				}
				findMarketSubOrder.setSubOrderId(subOrderId);
				findMarketSubOrder.setFindMarketSubOrderProductList(findMarketSubOrderProductList);
				findMarketSubOrderList.add(findMarketSubOrder);
			}
			
			findMarketOrderRes.setFindMarketSubOrderList(findMarketSubOrderList);
			findMarketOrderRes.setResult(new Result(ResultCode.success));
			return findMarketOrderRes;
		}catch(Exception ex){
			LogCvt.error("系统操作异常", ex);
			findMarketOrderRes.setResult(new Result(ResultCode.failed.getCode(), "系统操作异常"));
			return findMarketOrderRes;
		}
	}

	/**
	 * 查出某一个
	 * <br>
	 * 没有则新建 并放入列表中
	 * */
	private static FindMarketSubOrderProduct findOne(String productId, List<FindMarketSubOrderProduct> findMarketSubOrderProductList){
		FindMarketSubOrderProduct findMarketSubOrderProduct = null;
		for( FindMarketSubOrderProduct tProduct : findMarketSubOrderProductList ){
			if( productId.equals(tProduct.getProductId()) ){
				return tProduct;
			}
		}
		findMarketSubOrderProduct = new FindMarketSubOrderProduct();
		findMarketSubOrderProduct.setProductId(productId);
		findMarketSubOrderProductList.add(findMarketSubOrderProduct);
		return findMarketSubOrderProduct;
	}
}
