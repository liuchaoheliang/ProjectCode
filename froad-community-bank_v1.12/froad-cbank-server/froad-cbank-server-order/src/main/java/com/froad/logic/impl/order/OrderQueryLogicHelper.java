package com.froad.logic.impl.order;

import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;

public class OrderQueryLogicHelper {
	/**
     * 商品实付款：子订单下所有商品的实付款（包含满减的钱，不含积分抵扣的钱，也不含运费）
     * 1.子订单商品总价（不含运费） 2.子订单商品实付款（含满减金额） 3.子订单商品积分抵扣的钱
     */
    public static OrderDataResult getSubOrderMoney(SubOrderMongo subOrder){
    	double totalSubOrderPrice = 0;//商品总价（不含运费）
    	double totalSubOrderPayCashAndCutMoney = 0;//子订单商品实付款（含满减金额）
    	double totalPointMoney = 0;//子订单商品积分抵扣的钱
    	double deliveryMoney = 0;//总运费
    	
    	OrderDataResult orderReuslt = new OrderDataResult();
    	if(EmptyChecker.isNotEmpty(subOrder)){
    		if(EmptyChecker.isNotEmpty(subOrder.getProducts())){
    			for(ProductMongo product : subOrder.getProducts()){
    				OrderDataResult orderReusltTemp = getProductMoney(product);
    				totalSubOrderPrice = Arith.add(totalSubOrderPrice, orderReusltTemp.getTotalPrice());
    				totalSubOrderPayCashAndCutMoney = Arith.add(totalSubOrderPayCashAndCutMoney, orderReusltTemp.getPayCashAndCutMoney());
    				totalPointMoney = Arith.add(totalPointMoney, orderReusltTemp.getPayPointMoney());
        		}
    		}
    	}
    	orderReuslt.setTotalPrice(totalSubOrderPrice);
    	orderReuslt.setPayCashAndCutMoney(totalSubOrderPayCashAndCutMoney);
    	orderReuslt.setPayPointMoney(totalPointMoney);
    	return orderReuslt;
    }
    
    
    /**
     * 获取子订单一种商品多个数量商品的总金额
     * 1.商品总价（不含运费） 2.商品实付款（含满减金额） 3.商品积分抵扣的钱
     */
    public static OrderDataResult getProductMoney(ProductMongo product) {
    	OrderDataResult orderReuslt = new OrderDataResult();
    	int money = EmptyChecker.isEmpty(product.getMoney()) ? 0 : product.getMoney();
		int quantity = EmptyChecker.isEmpty(product.getQuantity()) ? 0 : product.getQuantity();
		int vipMoney = EmptyChecker.isEmpty(product.getVipMoney()) ? 0 : product.getVipMoney();
		int vipQuantity = EmptyChecker.isEmpty(product.getVipQuantity()) ? 0 : product.getVipQuantity();
		int deliveryMoney = EmptyChecker.isEmpty(product.getDeliveryMoney()) ? 0 : product.getDeliveryMoney();
		int totalCash = EmptyChecker.isEmpty(product.getTotalCash()) ? 0 : product.getTotalCash();
		int totalPoint = EmptyChecker.isEmpty(product.getTotalPoint()) ? 0 : product.getTotalPoint();
		int totalCutMoney = EmptyChecker.isEmpty(product.getTotalCutMoney()) ? 0 : product.getTotalCutMoney();
		
		//商品总价（不含运费）
		double productTotalPrice = Arith.div(Arith.add(Arith.mul(money, quantity),Arith.mul(vipMoney, vipQuantity)), 1000);
		
		//商品总价（含运费）
		double productTotalPriceAndDeiveryMoney = Arith.div(Arith.add(productTotalPrice,deliveryMoney), 1000);
		
		//商品实付款（含满减金额）
		double productPayCashAndCutMoney = Arith.div(Arith.add(totalCash,totalCutMoney),1000);
		if(productPayCashAndCutMoney == 0 && totalPoint == 0){//老数据
			productPayCashAndCutMoney = productTotalPrice;
		}
		
		//商品积分抵扣的钱
		double productPayPointMoney = Arith.sub(productTotalPrice, productPayCashAndCutMoney);
		if(totalPoint == 0){
			productPayPointMoney = 0;
		}
		
		//商品支付积分
		double productPayPoint = Arith.div(totalPoint,1000);
		
		//商品实付款（不含满减金额）
		double productPayCash = Arith.div(totalCash,1000);
		
		//运费
		double productDeliveryMoney = Arith.div(deliveryMoney,1000);
		
		//商品总价（不含运费）
		orderReuslt.setTotalPrice(productTotalPrice);
		//商品实付款（含满减金额）
		orderReuslt.setPayCashAndCutMoney(productPayCashAndCutMoney);
		//商品积分抵扣的钱
		orderReuslt.setPayPointMoney(productPayPointMoney);
		
        return orderReuslt;
    }

}
