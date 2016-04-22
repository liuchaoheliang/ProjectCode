package com.froad.cbank.coremodule.module.normal.user.utils;

import java.math.BigDecimal;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.thrift.vo.shopingcart.ShoppingCartVoRes;

public class AmountUtils {


	/**
	 * 购物车 计算单个购物车商品总金额
	 * @param product
	 * @return
	 */
	public static BigDecimal getTotalAmount(ShoppingCartVoRes product) {
		BigDecimal vipAmount = new BigDecimal("0.00");
		BigDecimal amount = new BigDecimal("0.00");
		BigDecimal totalAmount = new BigDecimal("0.00");
		
		vipAmount = BigDecimal.valueOf(product.getVipMoney()).multiply(
				BigDecimal.valueOf(product.getVipQuantity()));
		amount = BigDecimal.valueOf(product.getMoney()).multiply(
				BigDecimal.valueOf(product.getQuantity()));
		return totalAmount.add(vipAmount.add(amount).setScale(2,
				BigDecimal.ROUND_HALF_UP)).add(BigDecimal.valueOf(product.getDeliveryMoney()));
	}

	
	/**
	 * 获取积分可兑换的金额数值<br>
	 * ***保存小数点后两位，舍弃后面的数字，不进位
	 * @param Point
	 * @return
	 */
	public static BigDecimal getPointExchangeAmount(String point,String exchageRate){
		BigDecimal exchageAmount = new BigDecimal("0.00");
		if(StringUtil.isBlank(point) || StringUtil.isEmpty(exchageRate)){
			return exchageAmount;
		}		
		BigDecimal pt= new BigDecimal(point);
		exchageAmount =pt.divide(new BigDecimal(exchageRate), 2, BigDecimal.ROUND_DOWN);
		return exchageAmount;
	}
	
	/**
	 * <b>方付通积分支付计算方法，按实际结果计算</b>
	 * 
	 * <p>获取实际支付积分  
	 * 
	 * @return
	 */
	public static BigDecimal getPayFroadPoint(double totalPrice,String exchageRate){
		return BigDecimal.valueOf(totalPrice).multiply(new BigDecimal(exchageRate));
	}
	
	/**
	 * <b>银行积分支付计算方法，舍弃小数部分，向上取整</b>
	 * <p>获取实际支付积分  
	 * 
	 * @return
	 */
	public static BigDecimal getPayBankPoint(double totalPrice,String exchageRate){
		return BigDecimal.valueOf(totalPrice).multiply(new BigDecimal(exchageRate)).setScale(0, BigDecimal.ROUND_UP);
	}
	
	
	/**
	 * 计算商品优惠金额
	 * @param totalPrice
	 * @param exchageRate
	 * @return
	 */
	public static BigDecimal getVipAmount(double normalPrice,double vipPrice,int vipQuantity){
		return BigDecimal.valueOf(normalPrice).subtract(new BigDecimal(vipPrice)).multiply(new BigDecimal(vipQuantity));
	}

}
