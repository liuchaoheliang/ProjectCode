package com.froad.logic.impl.order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.alibaba.fastjson.JSON;
import com.froad.logback.LogCvt;
import com.froad.po.mongo.OrderMongo;
import com.froad.po.mongo.ProductMongo;
import com.froad.po.mongo.SubOrderMongo;
import com.froad.util.Arith;
import com.froad.util.EmptyChecker;
import com.froad.util.PropertiesUtil;

public class OrderSplitPointCash {

	/**
	 * 支付信息
	 */
	public class Payment{
		/**
		 * 支付现金
		 */
		private double payCash;
		
		/**
		 * 支付银行积分
		 */
		private int payBankPoint;
		
		/**
		 * 积分转换的钱
		 */
		private double pointToMoney;
		
		/**
		 * 积分兑换比例
		 */
		private int pointRate;
		
		public Payment(){};
		
		/**
		 * @param totalPrice 总价
		 * @param payBankPoint 总积分
		 * @param pointRate  积分兑换比例
		 */
		public Payment(double totalPrice, int payBankPoint,int pointRate){
			this.payBankPoint = payBankPoint;
			this.pointToMoney = Arith.divFloor(payBankPoint, pointRate);
			this.pointRate = pointRate;
			this.payCash = Arith.sub(totalPrice, this.pointToMoney);
			LogCvt.info("支付信息：[支付的总金额："+totalPrice+"，支付总积分:"+this.payBankPoint+"（对应的钱："+this.pointToMoney+"），支付的现金："+this.payCash+"]");
		}
		
		public double getPayCash() {
			return payCash;
		}
		public void setPayCash(double payCash) {
			this.payCash = payCash;
		}
		public int getPayBankPoint() {
			return payBankPoint;
		}
		public void setPayBankPoint(int payBankPoint) {
			this.payBankPoint = payBankPoint;
		}
		public double getPointToMoney() {
			return pointToMoney;
		}
		public void setPointToMoney(double pointToMoney) {
			this.pointToMoney = pointToMoney;
		}
		public int getPointRate() {
			return pointRate;
		}
		public void setPointRate(int pointRate) {
			this.pointRate = pointRate;
		}
	}
	
	/**
	 * 商品信息
	 */
	public class Product{
		//商品ID
		private String productId;
		
		//商品普通价
		private Double money;
		
		//商品VIP价
		private Integer quantity;
		
		//商品VIP价
		private Double vipMoney;
		
		//商品VIP数量
		private Integer vipQuantity;
		
		//退款到第N个商品的标记
		private Integer refoundIndex;
		
		private Double[] pointArray;
		
		private Double[] vipPointArray;
		
		//商品普通价实付现金
		private Double[] cashArray;
		
		//商品VIP价实付现金
		private Double[] vipCashArray;
		
		//商品总实付现金
		private Double totalCash;
		
		//商品总抵扣积分
		private Double totalCutPoint;
		//商品总抵扣积分对应的金额
		private Double totalCutPointMoney;//X
		
		//商品总优惠金额
		private Double totalCutMoney;
		
		//每单位普通价商品优惠金额数组
		private Double[] cutMoneyArray;
		
		//每单位VIP价商品优惠金额数组
		private Double[] vipCutMoneyArray;
		
		//普通价商品总抵扣积分
		private Double cutPoints;//X
		//每个单位的普通价商品积分对应的钱
		private Double[] pointMoneyArray;//X
		//VIP价商品总抵扣积分
		private Double vipCutPoints;//X
		//每个单位的VIP价商品积分对应的钱
		private Double[] vipPointMoneyArray;//X
		
		public Product(){}
		
		public Product(String productId,double money,int quantity,double vipMoney,int vipQuantity){
			this.productId = productId;
			this.money = money;
			this.quantity = quantity;
			this.vipMoney = vipMoney;
			this.vipQuantity = vipQuantity;
		}

		public String getProductId() {
			return productId;
		}

		public void setProductId(String productId) {
			this.productId = productId;
		}

		public Double getMoney() {
			return money;
		}

		public void setMoney(Double money) {
			this.money = money;
		}

		public Integer getQuantity() {
			return quantity;
		}

		public void setQuantity(Integer quantity) {
			this.quantity = quantity;
		}

		public Double getVipMoney() {
			return vipMoney;
		}

		public void setVipMoney(Double vipMoney) {
			this.vipMoney = vipMoney;
		}

		public Integer getVipQuantity() {
			return vipQuantity;
		}

		public void setVipQuantity(Integer vipQuantity) {
			this.vipQuantity = vipQuantity;
		}

		public Integer getRefoundIndex() {
			return refoundIndex;
		}

		public void setRefoundIndex(Integer refoundIndex) {
			this.refoundIndex = refoundIndex;
		}

		public Double[] getPointArray() {
			return pointArray;
		}

		public void setPointArray(Double[] pointArray) {
			this.pointArray = pointArray;
		}

		public Double[] getVipPointArray() {
			return vipPointArray;
		}

		public void setVipPointArray(Double[] vipPointArray) {
			this.vipPointArray = vipPointArray;
		}

		public Double[] getCashArray() {
			return cashArray;
		}

		public void setCashArray(Double[] cashArray) {
			this.cashArray = cashArray;
		}

		public Double[] getVipCashArray() {
			return vipCashArray;
		}

		public void setVipCashArray(Double[] vipCashArray) {
			this.vipCashArray = vipCashArray;
		}

		public Double getTotalCash() {
			return totalCash;
		}

		public void setTotalCash(Double totalCash) {
			this.totalCash = totalCash;
		}

		public Double getTotalCutPoint() {
			return totalCutPoint;
		}

		public void setTotalCutPoint(Double totalCutPoint) {
			this.totalCutPoint = totalCutPoint;
		}

		public Double getTotalCutPointMoney() {
			return totalCutPointMoney;
		}

		public void setTotalCutPointMoney(Double totalCutPointMoney) {
			this.totalCutPointMoney = totalCutPointMoney;
		}

		public Double getTotalCutMoney() {
			return totalCutMoney;
		}

		public void setTotalCutMoney(Double totalCutMoney) {
			this.totalCutMoney = totalCutMoney;
		}

		public Double[] getCutMoneyArray() {
			return cutMoneyArray;
		}

		public void setCutMoneyArray(Double[] cutMoneyArray) {
			this.cutMoneyArray = cutMoneyArray;
		}

		public Double[] getVipCutMoneyArray() {
			return vipCutMoneyArray;
		}

		public void setVipCutMoneyArray(Double[] vipCutMoneyArray) {
			this.vipCutMoneyArray = vipCutMoneyArray;
		}

		public Double getCutPoints() {
			return cutPoints;
		}

		public void setCutPoints(Double cutPoints) {
			this.cutPoints = cutPoints;
		}

		public Double[] getPointMoneyArray() {
			return pointMoneyArray;
		}

		public void setPointMoneyArray(Double[] pointMoneyArray) {
			this.pointMoneyArray = pointMoneyArray;
		}

		public Double getVipCutPoints() {
			return vipCutPoints;
		}

		public void setVipCutPoints(Double vipCutPoints) {
			this.vipCutPoints = vipCutPoints;
		}

		public Double[] getVipPointMoneyArray() {
			return vipPointMoneyArray;
		}

		public void setVipPointMoneyArray(Double[] vipPointMoneyArray) {
			this.vipPointMoneyArray = vipPointMoneyArray;
		}

	}
	
	/**
	 * 将商品按照商品总金额大小倒序排
	 * （金额大的在前，金额小的在后面，这样前面的商品积分抵扣的部分多，现金退的少，赚）
	 * @param list
	 */
	public void sortProductByMoney(List<Product> list){
		//TODO
	}
	
	public class Discount{
		private Map<String,ProductDiscount> discountMoneyMap;
	}
	
	public class ProductDiscount{
		private String productId;
		private double[] discountMoneyArray;
		private double[] vipDiscountMoneyArray;
		public ProductDiscount(double[] discountMoneyArray,double[] vipDiscountMoneyArray){
			this.discountMoneyArray = discountMoneyArray;
			this.vipDiscountMoneyArray = vipDiscountMoneyArray;
		}
		public String getProductId() {
			return productId;
		}
		public void setProductId(String productId) {
			this.productId = productId;
		}
		public double[] getDiscountMoneyArray() {
			return discountMoneyArray;
		}
		public void setDiscountMoneyArray(double[] discountMoneyArray) {
			this.discountMoneyArray = discountMoneyArray;
		}
		public double[] getVipDiscountMoneyArray() {
			return vipDiscountMoneyArray;
		}
		public void setVipDiscountMoneyArray(double[] vipDiscountMoneyArray) {
			this.vipDiscountMoneyArray = vipDiscountMoneyArray;
		}
	}
	
	/**
	 * 获取商品总价（运费不计算在内）
	 * @param product
	 * @return
	 */
	private double getProductTotalMoney(Product product){
		double money = 0;
		double vipMoney = 0;
		int quantity = 0;
		int vipQuantity = 0;
		if(EmptyChecker.isNotEmpty(product.getMoney())){
			money = product.getMoney();
		}
		if(EmptyChecker.isNotEmpty(product.getVipMoney())){
			vipMoney = product.getVipMoney();
		}
		if(EmptyChecker.isNotEmpty(product.getQuantity())){
			quantity = product.getQuantity();
		}
		if(EmptyChecker.isNotEmpty(product.getVipQuantity())){
			vipQuantity = product.getVipQuantity();
		}
		return Arith.add(Arith.mul2(money, quantity), Arith.mul2(vipMoney, vipQuantity));
	}
	
	/**
	 * 获取商品总价（运费不计算在内）
	 * @param product
	 * @return
	 */
	private double getProductTotalMoney(Product product,Boolean isVip){
		double money = 0;
		double vipMoney = 0;
		int quantity = 0;
		int vipQuantity = 0;
		if(EmptyChecker.isNotEmpty(product.getMoney())){
			money = product.getMoney();
		}
		if(EmptyChecker.isNotEmpty(product.getVipMoney())){
			vipMoney = product.getVipMoney();
		}
		if(EmptyChecker.isNotEmpty(product.getQuantity())){
			quantity = product.getQuantity();
		}
		if(EmptyChecker.isNotEmpty(product.getVipQuantity())){
			vipQuantity = product.getVipQuantity();
		}
		double result = 0;
		if(EmptyChecker.isEmpty(isVip)){
			result = Arith.add(Arith.mul2(money, quantity), Arith.mul2(vipMoney, vipQuantity));
		}
		if(!isVip){
			result = Arith.mul2(money, quantity);
		}
		if(isVip){
			result = Arith.mul2(vipMoney, vipQuantity);
		}
		return result;
	}
	
	/**
	 * 获取全部商品总金额（运费不计算在内）
	 * @param productList
	 * @return
	 */
	private double getProductListTotalMoney(List<Product> productList){
		double totalMoney = 0;
		for(Product product : productList){
			totalMoney = Arith.add(totalMoney, getProductTotalMoney(product));
		}
		return totalMoney;
	}
	
	public double divMul(double v1,double v2,double v3){
		if(v2==v3){
			return v1;
		}
		if(v3!=0 && v2%v3==0){
			return Arith.div(v1, Arith.div(v2, v3));
		}
		if(v2!=0 && v3%v2==0){
			return Arith.mul(v1, Arith.div(v3, v2));
		}
		return Arith.mul(Arith.div(v1, v2), v3);
	}
	
	/**
	 * 传入不同对象，获取满减金额
	 * @param order
	 * @param product
	 * @param productMongo
	 * @return
	 */
	public double getTotalCutMoney(OrderMongo order,Product product,ProductMongo productMongo){
		double result = 0;
		if(EmptyChecker.isNotEmpty(order) && EmptyChecker.isNotEmpty(order.getCutMoney())){
			result = Arith.div(order.getCutMoney(), 1000);
		}
		if(EmptyChecker.isNotEmpty(product) && EmptyChecker.isNotEmpty(product.getTotalCutMoney())){
			result = product.getTotalCutMoney();
		}
		if(EmptyChecker.isNotEmpty(productMongo) && EmptyChecker.isNotEmpty(productMongo.getTotalCutMoney())){
			result = Arith.div(productMongo.getTotalCutMoney(), 1000);
		}
		return result;
	}
	
	/**
	 * 设置单位商品抵扣积分
	 * @param productIdList 商品ID集合
	 * @param order 大订单
	 * @param subOrderList  子订单集合
	 * @param productMap 商品MAP
	 * @param totalDeliveryMoney 总运费
	 * @param points 总积分
	 * @param isPayFroadPoint  是否方付通积分支付： true 是，false 否
	 * @param marketProductMap 营销活动商品满减金额
	 */
	public void setUnitProductPoint(List<String> productIdList,OrderMongo order,List<SubOrderMongo> subOrderList,Map<String,ProductMongo> productMap,double totalDeliveryMoney,int points,boolean isPayFroadPoint){
		LogCvt.info("------------------订单积分拆分主逻辑开始-------------------");
		
		//订单优惠金额
		double totalCutMoney = getTotalCutMoney(order,null,null);
		
		//订单实际总价（订单商品总价-运费，包含满减金额）
		double orderTotalMoney = Arith.div(Arith.sub(order.getTotalPrice(), totalDeliveryMoney), 1000);
		
		//订单总积分
		double orderTotalPoint = Arith.div(points, 1000);
		
		//积分比例
		int pointRate = 0;
		if(EmptyChecker.isNotEmpty(order.getPointRate())){
			pointRate = Integer.valueOf(order.getPointRate());
		}
		if(pointRate==0 && isPayFroadPoint){
			pointRate = 1;
		}
		double cash = Arith.div(order.getRealPrice(), 1000);
		
		//商品ID排序
		if(EmptyChecker.isNotEmpty(productIdList)){
			Collections.sort(productIdList);
			
			OrderLogger.info("订单模块", "订单积分拆分-开始", null, new Object[]{
					"订单号",order.getOrderId(),"订单总价",Arith.div(order.getTotalPrice(),1000),"满减/优惠金额",totalCutMoney,
					"支付的现金",cash,"支付的总积分",orderTotalPoint,"总运费",Arith.div(totalDeliveryMoney, 1000),"是否联盟积分",isPayFroadPoint,
					"商品排序",productIdList});
			
			List<ProductMongo> productMongoList = new ArrayList<ProductMongo>();
			for (int i = 0; i < productIdList.size(); i++) {
				ProductMongo product = productMap.get(productIdList.get(i));
				productMongoList.add(product);
			}
			//拆订单积分
			splitOrderPoint(productMongoList,orderTotalMoney,orderTotalPoint,pointRate,cash,totalCutMoney,isPayFroadPoint);
			for (ProductMongo  product : productMongoList) {
				productMap.put(product.getProductId(),product);
			}
		}
		
		LogCvt.info("------------------订单积分拆分主逻辑结束-------------------");
	}
	
	/**
	 * 将支付的总积分对应的钱，分摊到每种商品上
	 * @param productList
	 * @param payment
	 * @param discount
	 */
	public void splitPointMoney(List<Product> productList,Payment payment,Discount discount){
		double pointToMoney = payment.getPointToMoney();
		int pointRate = payment.getPointRate();
		int payBankPoint = payment.getPayBankPoint();
		//商品总价
		double allProductTotalMoney = getProductListTotalMoney(productList);
		
		LogCvt.info("积分拆分开始，[所有商品总价："+allProductTotalMoney+",总积分："+payBankPoint+"(对应的钱："+pointToMoney+"])");
		
		double sumPointMoney = 0;
		double sumPoint = 0;
		for (int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);
			//当前商品总价
			double productTotalMoney = getProductTotalMoney(product);
			//当前商品抵扣积分对应的钱（小数后第三位都进位）
			double totalProductCutPointMoney = Arith.roundUp(divMul(productTotalMoney,allProductTotalMoney,pointToMoney),2);
			//当前商品总抵扣积分
			double totalCutPoint = 0;
			if(totalProductCutPointMoney > 0){
				//当前商品总抵扣积分（小数后第三位进位）
				totalCutPoint = Arith.roundUp(Arith.mul2(totalProductCutPointMoney, pointRate),0);
				LogCvt.info("积分的钱（"+totalProductCutPointMoney+"）*积分比例（"+pointRate+"）："+Arith.mul2(totalProductCutPointMoney, pointRate));
				LogCvt.info("进位："+Arith.roundUp(Arith.mul2(totalProductCutPointMoney, pointRate),0));
				
				//如果积分对应的钱已经分完，或者超额，不同的商品拆分到此结束
				if(Arith.add(sumPointMoney, totalProductCutPointMoney) >= pointToMoney){
					totalProductCutPointMoney = Arith.sub(pointToMoney, sumPointMoney);
					totalCutPoint = Arith.sub(payBankPoint, sumPoint);
				}
				
				product.setTotalCutPoint(totalCutPoint);
				product.setTotalCutPointMoney(totalProductCutPointMoney);
				
				//全部积分累加
				sumPoint = Arith.add(sumPoint,totalCutPoint);
				
				splitPoint(totalProductCutPointMoney, product, payment, discount);
			}
			
			LogCvt.info("商品:"+product.productId+"[ 商品总价："+productTotalMoney+"，抵扣积分："+totalCutPoint+"（积分对应的金额为："+totalProductCutPointMoney+"）");
//			LogCvt.info("商品:"+product.getProductId()+"，积分拆分结果："+JSON.toJSONString(product,true));
			sumPointMoney = Arith.add(sumPointMoney, totalProductCutPointMoney);
		}
	}
	
	public void checkOrderMoney(double points,int pointRate,double pointToMoney){
		if(points == 0 || pointRate == 0){
			return;
		}
		double pointToMoneyByRate = pointToMoneyByRate(points,pointRate);
		if(pointToMoneyByRate != pointToMoney){
			LogCvt.error("支付信息有误：[积分："+points+"按积分比例"+pointRate+":1 转现金为："+pointToMoneyByRate(points,pointRate)+"，但（总价-现金）="+pointToMoney+"]");
			OrderLogger.error("订单模块", "订单积分拆分-校验", "积分扣除有误：积分"+points+"(按"+pointRate+":1)转成现金为"+pointToMoneyByRate+"元，实际支付积分对应金额为"+pointToMoney+"元",null);
		}
	}
	
	/**
	 * 将支付的总积分对应的钱，分摊到每种商品上
	 * @param productMongoList
	 * @param totalPrice 商品总价（不含运费）
	 * @param points 支付的总积分  联盟积分/银行积分
	 * @param pointRate  积分兑换比
	 * @param cash  支付的现金
	 * @param totalCutMoney  订单总优惠金额
	 * @param isFroadPoint 是否联盟积分支付
	 */
	public void splitOrderPoint(List<ProductMongo> productMongoList,double totalPrice,double points,int pointRate,double cash,double totalCutMoney,boolean isFroadPoint){
		List<Product> productList = new ArrayList<Product>();
		beanFrom(productList, productMongoList);
		
		if (points > 0) {
			//应付的积分对应金额
			double pointToMoney = Arith.sub(totalPrice, cash);
			
			checkOrderMoney(points,pointRate,pointToMoney);
			
			//商品总价（减去满加金额）
			double allProductTotalMoney = getProductListTotalMoney(productList);
			allProductTotalMoney = Arith.sub(allProductTotalMoney, totalCutMoney);
			
			OrderLogger.info("订单模块", "订单积分拆分-不同商品拆积分", null, new Object[]{
					"商品总价(不含运费，不含满减)",allProductTotalMoney,"实付总金额(订单总价-运费)",totalPrice,
					"支付的现金",cash,"支付的总积分(对应的钱)",points+"("+pointToMoney+")","是否联盟积分",isFroadPoint});
			
			double sumPointMoney = 0;
			double sumPoint = 0;
			for (int i = 0; i < productList.size(); i++) {
				Product product = productList.get(i);
				//当前商品总价
				double productTotalMoney = getProductTotalMoney(product);
				//商品满减金额
				double productTotalCutMoney = getTotalCutMoney(null, product, null);
				productTotalMoney = Arith.sub(productTotalMoney, productTotalCutMoney);
				
				//当前商品抵扣积分对应的钱（小数后第三位都进位）
				double totalProductCutPointMoney = Arith.roundUp(divMul(productTotalMoney, allProductTotalMoney, pointToMoney),2);
				//当前商品总抵扣积分
				double totalCutPoint = 0;
				if(totalProductCutPointMoney > 0){
					//当前商品总抵扣积分（小数后第三位进位）
					if(isFroadPoint){
						totalCutPoint = totalProductCutPointMoney;
					}else{
						totalCutPoint = Arith.roundUp(Arith.mul2(totalProductCutPointMoney, pointRate),0);
					}
					
					//如果积分对应的钱已经分完，或者超额，不同的商品拆分到此结束
					if(Arith.add(sumPointMoney, totalProductCutPointMoney) >= pointToMoney){
						totalProductCutPointMoney = Arith.sub(pointToMoney, sumPointMoney);
						totalCutPoint = Arith.sub(points, sumPoint);
					}
					
					product.setTotalCutPoint(totalCutPoint);
					product.setTotalCutPointMoney(totalProductCutPointMoney);
					
					//全部积分累加
					sumPoint = Arith.add(sumPoint, totalCutPoint);
					
					//商品拆积分
					if(isFroadPoint){
						if(cash == 0 && points>0 ){//纯方付通积分支付
							splitFroadPointOnlyByPoint(product);
						}else{
							splitFroadPoint(totalProductCutPointMoney, product);
						}
					}else{
						if(cash == 0 && points>0 ){//纯银行积分支付
							splitBankPointOnlyByPoint(totalProductCutPointMoney, product, pointRate);
						}else{
							splitBankPoint(totalProductCutPointMoney, product, pointRate);
						}
					}
				}
				sumPointMoney = Arith.add(sumPointMoney, totalProductCutPointMoney);
			}
		}
		
		if(EmptyChecker.isNotEmpty(productList)){
			for(Product product : productList){
				//计算实付金额
				cashCalulate(product,cash);
			}
		}
		
		beanTo(productList,productMongoList);
		
	}	
	
	/**
	 * 拆分优惠金额
	 * @param productList
	 * @param discountMoney 满减金额
	 */
	public void splitDiscountMoney(List<Product> productList,double discountMoney){
		//商品总价
		double allProductTotalMoney = getProductListTotalMoney(productList);
		
		LogCvt.info("优惠金额拆分开始，[所有商品总价："+allProductTotalMoney+",优惠金额："+discountMoney+"]");
		
		double sumCutMoney = 0;
		for (int i = 0; i < productList.size(); i++) {
			Product product = productList.get(i);
			//当前商品总价
			double productTotalMoney = getProductTotalMoney(product);
			//当前商品优惠金额对应的钱（小数后第三位都进位）
			double totalProductCutMoney = Arith.roundUp(divMul(productTotalMoney, allProductTotalMoney, discountMoney),2);
			if(totalProductCutMoney > 0){
				//如果优惠已经分完，或者超额，拆分到不同商品结束
				if(Arith.add(sumCutMoney, totalProductCutMoney) >= discountMoney){
					totalProductCutMoney = Arith.sub(discountMoney, sumCutMoney);
				}
				
				product.setTotalCutMoney(totalProductCutMoney);
				
				splitDiscount(totalProductCutMoney, product);
			}
			
			LogCvt.info("商品:"+product.productId+"[ 商品总价："+productTotalMoney+"，优惠金额："+totalProductCutMoney+"]");
//			LogCvt.info("商品:"+product.getProductId()+"，积分拆分结果："+JSON.toJSONString(product,true));
			//已分摊的优惠金额累加
			sumCutMoney = Arith.add(sumCutMoney, totalProductCutMoney);
		}
	}	
	
	/**
	 * 【算法】（每单位的商品）拆分积分对应的钱
	 * @param money 积分对应的钱
	 * @param count 购买数量
	 * @return
	 */
	public Double[] splitMoney(double money,int count){
		Double[] result = new Double[count];
		if(money <= 0 || count <=0){
			return result;
		}
		
		double average = Arith.roundUp(Arith.div(money, count),2);
		
		if(average > 0){
			//钱够分
			double sum = 0;
			for (int i = 0; i < count; i++) {
				result[i] = average;
				if (Arith.add(sum, result[i]) >= money) {
					result[i] = Arith.sub(money, sum);
					setDoubleNullValue(result, count);
					return result;
				}
				sum = Arith.add(sum, result[i]);
			}
		}else{
			//钱不够分
			//算法二
			for(int j = count;j>0;j--){
				double averageTemp = Arith.roundUp(Arith.div(money, j),2);
				if(averageTemp>0){
					//钱够分
					double sum = 0;
					for (int i = 0; i < count; i++) {
						result[i] = average;
						if (Arith.add(sum, result[i]) >= money) {
							result[i] = Arith.sub(money, sum);
							setDoubleNullValue(result, count);
							return result;
						}
						sum = Arith.add(sum, result[i]);
					}
				}
			}
		}
		setDoubleNullValue(result, count);
		return result;
	}
	
	/**
	 * 【算法】（每单位的商品）拆分积分对应的钱
	 * @param money 积分对应的钱
	 * @param count 购买数量
	 * @return
	 */
	public Double[] splitMoney(double money,int count,double price,Double[] discountArray){
		Double[] result = new Double[count];
		if(money <= 0 || count <=0){
			return result;
		}
		
		double average = Arith.roundUp(Arith.div(money, count),2);
		
		if(average > 0){
			//钱够分
			double sum = 0;
			for (int i = 0; i < count; i++) {
				result[i] = average;
				if (Arith.add(sum, result[i]) >= money) {
					result[i] = Arith.sub(money, sum);
					setDoubleNullValue(result, count);
					resetArrayByLimit(discountArray,result,price,count);
					return result;
				}
				sum = Arith.add(sum, result[i]);
			}
		}else{
			//钱不够分
			//算法一
			/*int maxCount = Arith.intValue(Arith.div(money, 0.01));
			for(int i=0;i<count;i++){
				if(i<maxCount){
					result[i] = 0.01;
				}else{
					return result;
				}
			}*/
			
			
			//算法二
			for(int j = count;j>0;j--){
				double averageTemp = Arith.roundUp(Arith.div(money, j),2);
				if(averageTemp>0){
					//钱够分
					double sum = 0;
					for (int i = 0; i < count; i++) {
						result[i] = average;
						if (Arith.add(sum, result[i]) >= money) {
							result[i] = Arith.sub(money, sum);
							setDoubleNullValue(result, count);
							resetArrayByLimit(discountArray,result,price,count);
							return result;
						}
						sum = Arith.add(sum, result[i]);
					}
				}
			}
		}
		setDoubleNullValue(result, count);
		return result;
	}
	
	/**
	 * 每单位商品的积分抵扣金额+满减金额不得大于商品单价
	 * @param maxArray
	 * @param array
	 * @param price
	 * @param count
	 */
	public void resetArrayByLimit(Double[] maxArray,Double[] array,double price,int count){
		if(maxArray!=null && maxArray.length>0){
			setDoubleNullValue(maxArray, count);
			double sum = 0;
			for (int i = 0; i < count; i++) {
				if(Arith.add(array[i], maxArray[i]) > price){
					double v1 = array[i];
					array[i] = Arith.sub(price, maxArray[i]);
					double v2 = array[i];
					sum = Arith.add(sum, Arith.sub(v1, v2));
				}
			}
			if(sum>0){
				for (int i = 0; i < count; i++) {
					if(sum == 0){
						return;
					}
					if(Arith.add(array[i], maxArray[i]) < price){
						double v1 = array[i];
						double maxAdd = Math.min(Arith.sub(price, maxArray[i]), sum);
						array[i] = Arith.add(v1, maxAdd);
						sum = Arith.sub(sum, maxAdd);
					}
				}
			}
		}
	}
	
	/**
	 * 【算法】（每单位的商品）拆分积分对应的钱
	 * @param money 积分对应的钱
	 * @param count 购买数量
	 * @return
	 */
	public Double[] splitOnlyByFroadPoint(Double[] discountArray,double money,int count){
		Double[] result = new Double[count];
		if(money <= 0 || count <=0){
			return result;
		}
		setDoubleNullValue(discountArray, count);
		for (int i = 0; i < count; i++) {
			if(discountArray != null && discountArray.length>0){
				result[i] = Arith.sub(money, discountArray[i]);
			}else{
				result[i] = money;
			}
		}
		return result;
	}
	
	/**
	 * 【算法】拆积分
	 * @param pointMoneyArray 积分对应的钱的数组
	 * @param pointRate  积分比例
	 * @param totalPoint  总积分
	 * @param count 商品数量
	 * @return  返回积分数组
	 */
	public Double[] splitPoint(Double[] pointMoneyArray,int pointRate,double totalPoint,int count){
		Double[] result = new Double[count];
		if(totalPoint ==0){
			return result;
		}
		double sumPoint = 0;
		for(int i = 0;i<count;i++){
			double cutPoint = 0;
			if(pointMoneyArray[i]>0){
				cutPoint = Arith.roundUp(Arith.mul2(pointMoneyArray[i], pointRate),0);
				result[i] = cutPoint;
				if(Arith.add(sumPoint, cutPoint)>=totalPoint){
					result[i] = Arith.sub(totalPoint, sumPoint);
					setDoubleNullValue(result, count);
					return result;
				}else{
					if(i==(count-1)){
						result[i] = Arith.sub(totalPoint, sumPoint);
					}
				}
			}
			sumPoint = Arith.add(sumPoint, cutPoint);
		}
		setDoubleNullValue(result, count);
		return result;
	}
	
	/**
	 * 【算法】纯银行积分支付的拆积分
	 * @param pointMoneyArray
	 * @param pointRate
	 * @param totalPoint
	 * @param count
	 * @return  返回积分数组
	 */
	public Double[] splitOnlyByBankPoint(Double[] pointMoneyArray,int pointRate,double totalPoint,int count){
		Double[] result = new Double[count];
		if(totalPoint ==0){
			return result;
		}
		double sumPoint = 0;
		for(int i = 0;i<count;i++){
			double cutPoint = 0;
			if(pointMoneyArray[i]>0){
				//舍去小数位
				cutPoint = Arith.roundDown(Arith.mul2(pointMoneyArray[i], pointRate),0);
				result[i] = cutPoint;
				if(Arith.add(sumPoint, cutPoint)>=totalPoint){
					result[i] = Arith.sub(totalPoint, sumPoint);
					setDoubleNullValue(result, count);
					return result;
				}
			}
			sumPoint = Arith.add(sumPoint, cutPoint);
		}
		setDoubleNullValue(result, count);
		double last = Arith.sub(totalPoint, sumPoint);
		if(last>0 && result != null){
			int[] indexArray = getSequence(Arith.intValue(count));
			for (int i = 0; i < last; i++) {
				int index = indexArray[i];
				result[index] = Arith.add(result[index], 1);
			}
			OrderLogger.info("订单模块", "纯银行积分支付", "剩余积分:"+last+",随机(随机数组"+JSON.toJSONString(indexArray)+")分到"+last+"个商品上，最终分配结果："+JSON.toJSONString(result), null);
		}
		return result;
	}
	
    /**
     * 【算法】数组乱序
     * 对给定数目的自0开始步长为1的数字序列进行乱序
     * @param no 给定数目
     * @return 乱序后的数组
     */
    public static int[] getSequence(int no) {
        int[] sequence = new int[no];
        for(int i = 0; i < no; i++){
            sequence[i] = i;
        }
        Random random = new Random();
        for(int i = 0; i < no; i++){
            int p = random.nextInt(no);
            int tmp = sequence[i];
            sequence[i] = sequence[p];
            sequence[p] = tmp;
        }
        random = null;
        return sequence;
    }
	
	/**
	 * 设置数组中为空的项为0
	 * @param array
	 * @param count
	 */
	public void setDoubleNullValue(Double[] array,int count){
		if(array!=null && array.length>0){
			for(int i = 0;i<count;i++){
				if(EmptyChecker.isEmpty(array[i])){
					array[i] = 0.0;
				}
			}
		}
	}
	
	/**
	 * 拆每单位的商品上的银行积分对应的钱，并对应计算出银行积分
	 * @param totalProductCutPointMoney 每种商品总抵扣积分对应的钱
	 * @param product  商品
	 * @param payment  支付
	 * @param discount 优惠
	 */
	public void splitBankPoint(double totalProductCutPointMoney,Product product,int pointRate){
		if(totalProductCutPointMoney == 0 || product.getTotalCutPoint() == 0){
			return;
		}
		
		//普通价的商品总价
		double totalPrice = Arith.mul2(product.getMoney(), product.getQuantity());
		double totalVipPrice = Arith.mul2(product.getVipMoney(), product.getVipQuantity());
		
		//优惠金额
		double cutMoney = getSum(product.getCutMoneyArray());
		double vipCutMoney = getSum(product.getVipCutMoneyArray());
		double totalCutMoney = getTotalCutMoney(null, product, null);
		
		//普通价商品对应的积分的钱（小数后第三位进位）
//		double cutPointMoney = Arith.roundUp(divMul(totalPrice, getProductTotalMoney(product), totalProductCutPointMoney),2);
		double cutPointMoney = Arith.roundUp(divMul(Arith.sub(totalPrice, cutMoney), Arith.sub(getProductTotalMoney(product), totalCutMoney), totalProductCutPointMoney),2);
		//VIP价商品对应的积分的钱
		double vipCutPointMoney = Arith.sub(totalProductCutPointMoney, cutPointMoney);
		
		//普通价商品抵扣的总积分
		double cutPoints = Arith.intValue(Arith.roundUp(Arith.mul2(cutPointMoney, pointRate),0));//进位取整
		//VIP价商品抵扣的总积分
		double vipCutPoints = 0;
		if (vipCutPointMoney > 0) {
			vipCutPoints = Arith.sub(product.getTotalCutPoint(), cutPoints);
		} else {
			cutPoints = product.getTotalCutPoint();
		}
		product.setCutPoints(cutPoints);
		product.setVipCutPoints(vipCutPoints);
		
		//普通价每个单位商品积分抵扣金额
		Double[] cutMoneyArray = splitMoney(cutPointMoney,product.getQuantity(),product.getMoney(),product.getCutMoneyArray());
		//VIP价每个单位商品积分抵扣金额
		Double[] vipCutMoneyArray = splitMoney(vipCutPointMoney,product.getVipQuantity(),product.getVipMoney(),product.getVipCutMoneyArray());
		product.setPointMoneyArray(cutMoneyArray);
		product.setVipPointMoneyArray(vipCutMoneyArray);
		
		//普通价每个单位商品抵扣积分
		Double[] pointArray = splitPoint(cutMoneyArray,pointRate,cutPoints,product.getQuantity());
		//VIP价每个单位商品抵扣积分
		Double[] vipPointArray = splitPoint(vipCutMoneyArray,pointRate,vipCutPoints,product.getVipQuantity());
		product.setPointArray(pointArray);
		product.setVipPointArray(vipPointArray);
		
		OrderLogger.info("订单模块", "订单积分拆分-同种商品(银行积分)拆分结果", null, new Object[]{
				"商品ID",product.getProductId(),"共抵扣银行积分",product.getTotalCutPoint(),"积分对应的钱",totalProductCutPointMoney,
				"总满减金额",totalCutMoney,"普通价满减金额",cutMoney,"VIP价满减金额",vipCutMoney,
				"普通价积分(对应的钱)",JSON.toJSONString(pointArray)+"("+cutPointMoney+")","普通价总积分",cutPoints,
				"VIP价积分(对应的钱)",JSON.toJSONString(vipPointArray)+"("+vipCutPointMoney+")","VIP价总积分",vipCutPoints});
	}
	
	/**
	 * [纯银行积分支付]拆每单位的商品上的银行积分对应的钱，并对应计算出银行积分
	 * @param totalProductCutPointMoney 每种商品总抵扣积分对应的钱
	 * @param product  商品
	 * @param payment  支付
	 * @param discount 优惠
	 */
	public void splitBankPointOnlyByPoint(double totalProductCutPointMoney,Product product,int pointRate){
		if(totalProductCutPointMoney == 0 || product.getTotalCutPoint() == 0){
			return;
		}
		
		//普通价的商品总价
		double totalPrice = Arith.mul2(product.getMoney(), product.getQuantity());
		double totalVipPrice = Arith.mul2(product.getVipMoney(), product.getVipQuantity());
		
		//优惠金额
		double cutMoney = getSum(product.getCutMoneyArray());
		double vipCutMoney = getSum(product.getVipCutMoneyArray());
		double totalCutMoney = getTotalCutMoney(null, product, null);
		
		//普通价商品对应的积分的钱（小数后第三位进位）
//		double cutPointMoney = Arith.roundUp(divMul(totalPrice, getProductTotalMoney(product), totalProductCutPointMoney),2);
		double cutPointMoney = Arith.roundUp(divMul(Arith.sub(totalPrice, cutMoney), Arith.sub(getProductTotalMoney(product), totalCutMoney), totalProductCutPointMoney),2);
		//VIP价商品对应的积分的钱
		double vipCutPointMoney = Arith.sub(totalProductCutPointMoney, cutPointMoney);
		
		//普通价商品抵扣的总积分
		double cutPoints = Arith.intValue(Arith.roundUp(Arith.mul2(cutPointMoney, pointRate),0));//进位取整
		//VIP价商品抵扣的总积分
		double vipCutPoints = 0;
		if (vipCutPointMoney > 0) {
			vipCutPoints = Arith.sub(product.getTotalCutPoint(), cutPoints);
		} else {
			cutPoints = product.getTotalCutPoint();
		}
		product.setCutPoints(cutPoints);
		product.setVipCutPoints(vipCutPoints);
		
		//普通价每个单位商品积分抵扣金额
		Double[] cutMoneyArray = splitMoney(cutPointMoney,product.getQuantity(),product.getMoney(),product.getCutMoneyArray());
		//VIP价每个单位商品积分抵扣金额
		Double[] vipCutMoneyArray = splitMoney(vipCutPointMoney,product.getVipQuantity(),product.getVipMoney(),product.getVipCutMoneyArray());
		product.setPointMoneyArray(cutMoneyArray);
		product.setVipPointMoneyArray(vipCutMoneyArray);
		
		//普通价每个单位商品抵扣积分
		Double[] pointArray = splitOnlyByBankPoint(cutMoneyArray,pointRate,cutPoints,product.getQuantity());
		//VIP价每个单位商品抵扣积分
		Double[] vipPointArray = splitOnlyByBankPoint(vipCutMoneyArray,pointRate,vipCutPoints,product.getVipQuantity());
		product.setPointArray(pointArray);
		product.setVipPointArray(vipPointArray);
		
		OrderLogger.info("订单模块", "订单积分拆分-同种商品(纯银行积分)拆分结果", null, new Object[]{
				"商品ID",product.getProductId(),"共抵扣银行积分",product.getTotalCutPoint(),"积分对应的钱",totalProductCutPointMoney,
				"总满减金额",totalCutMoney,"普通价满减金额",cutMoney,"VIP价满减金额",vipCutMoney,
				"普通价积分(对应的钱)",JSON.toJSONString(pointArray)+"("+cutPointMoney+")","普通价总积分",cutPoints,
				"VIP价积分(对应的钱)",JSON.toJSONString(vipPointArray)+"("+vipCutPointMoney+")","VIP价总积分",vipCutPoints});
	}
	
	/**
	 * 拆每单位的商品上积分对应的钱，并对应计算出积分
	 * @param totalProductCutPointMoney 每种商品总抵扣积分对应的钱
	 * @param product  商品
	 * @param payment  支付
	 * @param discount 优惠
	 */
	public void splitFroadPoint(double totalProductCutPointMoney,Product product){
		if(totalProductCutPointMoney == 0 || product.getTotalCutPoint() == 0){
			return;
		}
		
		//普通价的商品总价
		double totalPrice = Arith.mul2(product.getMoney(), product.getQuantity());
		double totalVipPrice = Arith.mul2(product.getVipMoney(), product.getVipQuantity());
		
		//优惠金额
		double cutMoney = getSum(product.getCutMoneyArray());
		double vipCutMoney = getSum(product.getVipCutMoneyArray());
		double totalCutMoney = getTotalCutMoney(null, product, null);
		
		//普通价商品对应的积分的钱（小数后第三位进位）
		double cutPointMoney = Arith.roundUp(divMul(Arith.sub(totalPrice, cutMoney), Arith.sub(getProductTotalMoney(product), totalCutMoney), totalProductCutPointMoney),2);
		//VIP价商品对应的积分的钱
		double vipCutPointMoney = Arith.sub(totalProductCutPointMoney, cutPointMoney);
		
		//普通价每个单位商品积分抵扣金额
		Double[] cutMoneyArray = splitMoney(cutPointMoney,product.getQuantity(),product.getMoney(),product.getCutMoneyArray());
		//VIP价每个单位商品积分抵扣金额
		Double[] vipCutMoneyArray = splitMoney(vipCutPointMoney,product.getVipQuantity(),product.getVipMoney(),product.getVipCutMoneyArray());
		product.setPointMoneyArray(cutMoneyArray);
		product.setVipPointMoneyArray(vipCutMoneyArray);
		
		//普通价商品对应的积分（小数后第三位进位）
		double cutPoints = cutPointMoney;
		//VIP价商品对应的积分
		double vipCutPoints = Arith.sub(product.getTotalCutPoint(), cutPoints);
		product.setCutPoints(cutPoints);
		product.setVipCutPoints(vipCutPoints);
		
		//普通价每个单位商品联盟积分
		Double[] pointArray = splitMoney(cutPoints,product.getQuantity(),product.getMoney(),product.getCutMoneyArray());
		//VIP价每个单位商品联盟积分
		Double[] vipPointArray = splitMoney(vipCutPoints,product.getVipQuantity(),product.getVipMoney(),product.getVipCutMoneyArray());
		product.setPointArray(pointArray);
		product.setVipPointArray(vipPointArray);
		
		OrderLogger.info("订单模块", "订单积分拆分-同种商品(联盟积分)拆分结果", null, new Object[]{
				"商品ID",product.getProductId(),"共抵扣联盟积分",product.getTotalCutPoint(),"积分对应的钱",totalProductCutPointMoney,
				"总满减金额",totalCutMoney,"普通价满减金额",cutMoney,"VIP价满减金额",vipCutMoney,
				"普通价积分(对应的钱)",JSON.toJSONString(pointArray)+"("+cutPointMoney+")","普通价总积分",cutPoints,
				"VIP价积分(对应的钱)",JSON.toJSONString(vipPointArray)+"("+vipCutPointMoney+")","VIP价总积分",vipCutPoints});
	}
	
	/**
	 * 【使用纯方付通积分支付时】- 拆每单位的商品上积分对应的钱，并对应计算出积分
	 * @param totalProductCutPointMoney 每种商品总抵扣积分对应的钱
	 * @param product  商品
	 * @param payment  支付
	 * @param discount 优惠
	 */
	public void splitFroadPointOnlyByPoint(Product product){
		if(product.getTotalCutPoint() == 0){
			return;
		}
		
		//普通价的商品总价
//		double totalPrice = Arith.mul2(product.getMoney(), product.getQuantity());
//		double totalVipPrice = Arith.mul2(product.getVipMoney(), product.getVipQuantity());
		
		Double[] discountArray = product.getCutMoneyArray();
		Double[] vipDiscountArray = product.getVipCutMoneyArray();
		
		//普通价每个单位商品积分抵扣金额
		Double[] cutMoneyArray = null;
		//VIP价每个单位商品积分抵扣金额
		Double[] vipCutMoneyArray = null;
		if(EmptyChecker.isNotEmpty(product.getQuantity()) && product.getQuantity()>0){
			cutMoneyArray = splitOnlyByFroadPoint(discountArray,product.getMoney(),product.getQuantity());
		}
		if(EmptyChecker.isNotEmpty(product.getVipQuantity()) && product.getVipQuantity()>0){
			vipCutMoneyArray = splitOnlyByFroadPoint(vipDiscountArray,product.getVipMoney(),product.getVipQuantity());
		}
		product.setPointMoneyArray(cutMoneyArray);
		product.setVipPointMoneyArray(vipCutMoneyArray);
		
		
		//普通价商品对应的积分的钱（小数后第三位进位）
		double cutPointMoney = getSum(cutMoneyArray);
		//VIP价商品对应的积分的钱
		double vipCutPointMoney = getSum(vipCutMoneyArray);
		
		//普通价商品对应的积分（小数后第三位进位）
		double cutPoints = cutPointMoney;
		//VIP价商品对应的积分
		double vipCutPoints = vipCutPointMoney;
		product.setCutPoints(cutPoints);
		product.setVipCutPoints(vipCutPoints);
		
		//普通价每个单位商品联盟积分
		Double[] pointArray = cutMoneyArray;
		//VIP价每个单位商品联盟积分
		Double[] vipPointArray = vipCutMoneyArray;
		product.setPointArray(pointArray);
		product.setVipPointArray(vipPointArray);
		
		//商品总满减金额
		double totalCutMoney = 0;
		if(EmptyChecker.isNotEmpty(product.getTotalCutMoney())){
			totalCutMoney = product.getTotalCutMoney();
		}
		
		product.setTotalCutPoint(Arith.sub(getProductTotalMoney(product), totalCutMoney));
		
		OrderLogger.info("订单模块", "订单积分拆分-同种商品(纯联盟积分支付)拆分结果", null, new Object[]{
				"商品ID",product.getProductId(),"共抵扣联盟积分",product.getTotalCutPoint(),"积分对应的钱",Arith.add(cutPointMoney, vipCutPointMoney),
				"普通价积分(对应的钱)",JSON.toJSONString(pointArray)+"("+cutPointMoney+")","普通价总积分",cutPoints,
				"VIP价积分(对应的钱)",JSON.toJSONString(vipPointArray)+"("+vipCutPointMoney+")","VIP价总积分",vipCutPoints});
	}
	
	/**
	 * 拆每单位的商品上积分对应的钱，并对应计算出积分
	 * @param totalProductCutPointMoney 每种商品总抵扣积分对应的钱
	 * @param product  商品
	 * @param payment  支付
	 * @param discount 优惠
	 */
	public void splitPoint(double totalProductCutPointMoney,Product product,Payment payment,Discount discount){
		if(totalProductCutPointMoney == 0 || product.getTotalCutPoint() == 0){
			return;
		}
		
		int pointRate = payment.getPointRate();
		
		//普通价的商品总价
		double totalPrice = Arith.mul2(product.getMoney(), product.getQuantity());
		double totalVipPrice = Arith.mul2(product.getVipMoney(), product.getVipQuantity());
		
		//普通价商品对应的积分的钱（小数后第三位进位）
		double cutPointMoney = Arith.roundUp(divMul(totalPrice, getProductTotalMoney(product), totalProductCutPointMoney),2);
		//VIP价商品对应的积分的钱
		double vipCutPointMoney = Arith.sub(totalProductCutPointMoney, cutPointMoney);
		
		//普通价商品抵扣的总积分
		double cutPoints = Arith.roundUp(Arith.mul2(cutPointMoney, pointRate),0);//进位取整
		//VIP价商品抵扣的总积分
		double vipCutPoints = Arith.sub(product.getTotalCutPoint(), cutPoints);
		product.setCutPoints(cutPoints);
		product.setVipCutPoints(vipCutPoints);
		
		//普通价每个单位商品积分抵扣金额
		Double[] cutMoneyArray = splitMoney(cutPointMoney,product.getQuantity(),product.getMoney(),product.getCutMoneyArray());
		//VIP价每个单位商品积分抵扣金额
		Double[] vipCutMoneyArray = splitMoney(vipCutPointMoney,product.getVipQuantity(),product.getVipMoney(),product.getVipCutMoneyArray());
		product.setPointMoneyArray(cutMoneyArray);
		product.setVipPointMoneyArray(vipCutMoneyArray);
		
		//普通价每个单位商品抵扣积分
		Double[] pointArray = splitPoint(cutMoneyArray,pointRate,cutPoints,product.getQuantity());
		//VIP价每个单位商品抵扣积分
		Double[] vipPointArray = splitPoint(vipCutMoneyArray,pointRate,vipCutPoints,product.getVipQuantity());
		product.setPointArray(pointArray);
		product.setVipPointArray(vipPointArray);
		LogCvt.info("商品:"+product.getProductId()+"，积分拆分结果："+JSON.toJSONString(product,true));
	}
	
	/**
	 * 拆每单位的商品上积分对应的钱，并对应计算出积分
	 * @param totalProductCutPointMoney 每种商品总抵扣积分对应的钱
	 * @param product  商品
	 * @param payment  支付
	 * @param discount 优惠
	 */
	public void splitDiscount(double totalProductCutMoney,Product product){
		if(totalProductCutMoney == 0){
			return;
		}
		
		//普通价的商品总价
		double totalPrice = Arith.mul2(product.getMoney(), product.getQuantity());
		double totalVipPrice = Arith.mul2(product.getVipMoney(), product.getVipQuantity());
		
		//普通价商品对应的优惠金额（小数后第三位进位）
		double cutMoney = Arith.roundUp(divMul(totalPrice, getProductTotalMoney(product), totalProductCutMoney),2);
		//VIP价商品对应的积分的钱
		double vipCutMoney = Arith.sub(totalProductCutMoney, cutMoney);
		
		//普通价每个单位商品优惠金额
		Double[] cutMoneyArray = splitMoney(cutMoney,product.getQuantity(),product.getMoney(),product.getCutMoneyArray());
		//VIP价每个单位商品优惠金额
		Double[] vipCutMoneyArray = splitMoney(vipCutMoney,product.getVipQuantity(),product.getVipMoney(),product.getVipCutMoneyArray());
		product.setCutMoneyArray(cutMoneyArray);
		product.setVipCutMoneyArray(vipCutMoneyArray);
		
		//计算普通价/VIP价每个单位商品实付现金
		cashCalulate(product);
		LogCvt.info("商品:"+product.getProductId()+"，优惠金额拆分结果："+JSON.toJSONString(product,true));
	}
	
	/**
	 * 计算出每单位商品实付现金
	 * @param product
	 */
	public void cashCalulate(Product product,double cash){
		if (cash <= 0) {
			return;
		}
		double totalCash = 0;
		Double[] cashArray = new Double[product.getQuantity()];
		if(product.getQuantity()>0){
			for (int i = 0; i < product.getQuantity(); i++) {
				double sum = 0;
				if(product.getCutMoneyArray() != null && product.getCutMoneyArray().length==product.getQuantity()){
					sum = Arith.add(sum, product.getCutMoneyArray()[i]);
				}
				if(product.getPointMoneyArray() != null && product.getPointMoneyArray().length==product.getQuantity()){
					sum = Arith.add(sum, product.getPointMoneyArray()[i]);
				}
				cashArray[i] = Arith.sub(product.getMoney(), sum);
			}
			if (cashArray != null && cashArray.length > 0) {
				product.setCashArray(cashArray);
				totalCash = Arith.add(totalCash, getSum(cashArray));
			}
		}
		
		Double[] vipCashArray = new Double[product.getVipQuantity()];
		if(product.getVipQuantity()>0){
			for(int i = 0 ; i<product.getVipQuantity();i++){
				double sum = 0;
				if(product.getVipCutMoneyArray()!=null && product.getVipCutMoneyArray().length==product.getVipQuantity()){
					sum = Arith.add(sum, product.getVipCutMoneyArray()[i]);
				}
				if(product.getVipPointMoneyArray()!=null && product.getVipPointMoneyArray().length==product.getVipQuantity()){
					sum = Arith.add(sum, product.getVipPointMoneyArray()[i]);
				}
				vipCashArray[i] = Arith.sub(product.getVipMoney(), sum);
			}
			if(vipCashArray!=null && vipCashArray.length>0){
				product.setVipCashArray(vipCashArray);
				totalCash = Arith.add(totalCash, getSum(vipCashArray));
			}
		}
		if (totalCash > 0) {
			product.setTotalCash(totalCash);
		}
		OrderLogger.info("订单模块", "订单积分拆分-计算每单位商品实付现金", null, new Object[]{"商品ID",product.getProductId(),"普通价("+product.getQuantity()+"个)实付现金",cashArray,"VIP价("+product.getVipQuantity()+"个)实付现金",vipCashArray});
	}
	
	/**
	 * 计算出每单位商品实付现金
	 * @param product
	 */
	public void cashCalulate(Product product){
		if(product.getQuantity()>0){
			Double[] cashArray = new Double[product.getQuantity()];
			for(int i = 0 ; i<product.getQuantity();i++){
				double sum = 0;
				if(product.getCutMoneyArray()!=null && product.getCutMoneyArray().length==product.getQuantity()){
					sum = Arith.add(sum, product.getCutMoneyArray()[i]);
				}
				if(product.getPointMoneyArray()!=null && product.getPointMoneyArray().length==product.getQuantity()){
					sum = Arith.add(sum, product.getPointMoneyArray()[i]);
				}
				cashArray[i] = Arith.sub(product.getMoney(), sum);
			}
			product.setCashArray(cashArray);
		}
		
		if(product.getVipQuantity()>0){
			Double[] vipCashArray = new Double[product.getVipQuantity()];
			for(int i = 0 ; i<product.getVipQuantity();i++){
				double sum = 0;
				if(product.getVipCutMoneyArray()!=null && product.getVipCutMoneyArray().length==product.getVipQuantity()){
					sum = Arith.add(sum, product.getVipCutMoneyArray()[i]);
				}
				if(product.getVipPointMoneyArray()!=null && product.getVipPointMoneyArray().length==product.getVipQuantity()){
					sum = Arith.add(sum, product.getVipPointMoneyArray()[i]);
				}
				vipCashArray[i] = Arith.sub(product.getVipMoney(), sum);
			}
			product.setVipCashArray(vipCashArray);
		}
	}
	
	public void checkValue(List<Product> list){
		StringBuffer first = new StringBuffer();
		first.append("商品 | 总价 | 总积分(金额) | 单价 | 数量 | 普通价每单位抵扣积分 | 普通价每单位优惠金额 | 普通价每单位实付现金 | VIP单价 | VIP数量 | VIP价每单位抵扣积分 | VIP价每单位优惠金额 | VIP价每单位实付现金 |");
		LogCvt.info(first.toString());
		double a1=0;
		double a2=0;
		double a3=0;
		double a4=0;
		int a5=0;
		int a6=0;
		double a7=0;
		double a8=0;
		int a9=0;
		double a10=0;
		double a11=0;
		for(Product product : list){
			StringBuffer sb = new StringBuffer();
			sb.append("  ");
			sb.append(product.getProductId());
			sb.append(" |");
			sb.append(getProductTotalMoney(product));a1 = Arith.add(a1,getProductTotalMoney(product));
			sb.append("|");
			sb.append(product.getCutPoints());a2 = Arith.add(product.getCutPoints()==null?0:product.getCutPoints(), a2);
			sb.append("(");
			sb.append(product.getTotalCutPointMoney());a3 = Arith.add(a3,product.getTotalCutPointMoney()==null?0:product.getTotalCutPointMoney());
			sb.append(")");
			sb.append("|");
			sb.append(product.getMoney());
			sb.append("|");
			sb.append(product.getQuantity());
			sb.append("|");
			sb.append(JSON.toJSONString(product.getPointArray()));a6 += getSum(product.getPointArray());
			sb.append("|");
			sb.append(JSON.toJSONString(product.getPointMoneyArray()));a7 = Arith.add(a7,getSum(product.getPointMoneyArray()));
			sb.append("|");
			sb.append(JSON.toJSONString(product.getCutMoneyArray()));a8 = Arith.add(a8,getSum(product.getCutMoneyArray()));
			sb.append("|");
			sb.append(product.getVipMoney());
			sb.append("|");
			sb.append(product.getVipQuantity());
			sb.append("|");
			sb.append(JSON.toJSONString(product.getVipPointArray()));a9 += getSum(product.getVipPointArray());
			sb.append("|");
			sb.append(JSON.toJSONString(product.getVipPointMoneyArray()));a10 = Arith.add(a10,getSum(product.getVipPointMoneyArray()));
			sb.append("|");
			sb.append(JSON.toJSONString(product.getVipCutMoneyArray()));a11 = Arith.add(a11,getSum(product.getVipCutMoneyArray()));
			LogCvt.info(sb.toString());
		}
		LogCvt.info("--------------------------------------------------------------------------------------------------------");
		LogCvt.info("总计 | "+a1+" | "+a2+" | "+a3+" | 单价 | 数量  | "+a6+" | "+a7+" | "+a8+" | VIP单价 | VIP数量 | "+a9+" | "+a10+" | "+a11+"|");
	}
	
	public double getSum(Double[] array){
		double result = 0;
		if(array!=null && array.length>0){
			for(Double arr : array){
				result = Arith.add(result, arr);
			}
		}
		return result;
	}
	
	public int getSum(Integer[] array){
		int result = 0;
		if(array!=null && array.length>0){
			for(Integer arr : array){
				result += arr;
			}
		}
		return result;
	}
	
	public Integer[] toIntegerArray(Double[] array){
		Integer[] result = null;
		if(array!=null && array.length>0){
			result = new Integer[array.length];
			for(int i =0;i<array.length;i++){
				result[i] = Arith.mul(array[i], 1000);
			}
			
		}
		return result;
	}
	
	public Double[] toDoubleArray(Integer[] array){
		Double[] result = null;
		if(array!=null && array.length>0){
			result = new Double[array.length];
			for(int i =0;i<array.length;i++){
				result[i] = Arith.div(array[i], 1000);
			}
			
		}
		return result;
	}
	
	public void beanTo(Product product,ProductMongo productMongo){
		if(EmptyChecker.isNotEmpty(product)){
			//积分
			if(product.getPointArray()!=null){
				productMongo.setPointArray(toIntegerArray(product.getPointArray()));
			}
			
			if(product.getVipPointArray()!=null){
				productMongo.setVipPointArray(toIntegerArray(product.getVipPointArray()));
			}
			
			if(EmptyChecker.isNotEmpty(product.getTotalCutPoint())){
				productMongo.setTotalPoint(Arith.mul(product.getTotalCutPoint(), 1000));;
			}
			
			//优惠金额
			if(product.getCutMoneyArray()!=null){
				productMongo.setCutMoneyArray(toIntegerArray(product.getCutMoneyArray()));
			}
			
			if(product.getVipCutMoneyArray()!=null){
				productMongo.setVipCutMoneyArray(toIntegerArray(product.getVipCutMoneyArray()));
			}
			
			if(EmptyChecker.isNotEmpty(product.getTotalCutMoney())){
				productMongo.setTotalCutMoney(Arith.mul(product.getTotalCutMoney(), 1000));;
			}
			
			//现金
			if(product.getCashArray()!=null){
				productMongo.setCashArray(toIntegerArray(product.getCashArray()));
			}
			
			if(product.getVipCashArray()!=null){
				productMongo.setVipCashArray(toIntegerArray(product.getVipCashArray()));
			}
			
			if(EmptyChecker.isNotEmpty(product.getTotalCash())){
				productMongo.setTotalCash(Arith.mul(product.getTotalCash(), 1000));;
			}
		}
	}
	
	/**
	 * 前面的对象转成后面的对象
	 * @param productMongo
	 * @param product
	 */
	public void beanTo(ProductMongo productMongo,Product product){
		if(EmptyChecker.isNotEmpty(productMongo)){
			product.setProductId(productMongo.getProductId());
			product.setMoney(Arith.div(productMongo.getMoney(), 1000));
			product.setQuantity(productMongo.getQuantity());
			product.setVipMoney(Arith.div(productMongo.getVipMoney(), 1000));
			product.setVipQuantity(productMongo.getVipQuantity());
			if(productMongo.getCutMoneyArray()!=null){
				product.setCutMoneyArray(toDoubleArray(productMongo.getCutMoneyArray()));
			}
			if(productMongo.getVipCutMoneyArray()!=null){
				product.setVipCutMoneyArray(toDoubleArray(productMongo.getVipCutMoneyArray()));
			}
			if(EmptyChecker.isNotEmpty(productMongo.getTotalCutMoney())){
				product.setTotalCutMoney(Arith.div(productMongo.getTotalCutMoney(), 1000));
			}
			
			if(productMongo.getPointArray()!=null){
				product.setPointArray(toDoubleArray(productMongo.getPointArray()));
			}
			if(productMongo.getVipPointArray()!=null){
				product.setVipPointArray(toDoubleArray(productMongo.getVipPointArray()));
			}
			if(EmptyChecker.isNotEmpty(productMongo.getTotalPoint())){
				product.setTotalCutPoint(Arith.div(productMongo.getTotalPoint(), 1000));
			}
			
			if(productMongo.getCashArray()!=null){
				product.setCashArray(toDoubleArray(productMongo.getCashArray()));
			}
			if(productMongo.getVipCashArray()!=null){
				product.setVipCashArray(toDoubleArray(productMongo.getVipCashArray()));
			}
			if(EmptyChecker.isNotEmpty(productMongo.getTotalCash())){
				product.setTotalCash(Arith.div(productMongo.getTotalCash(), 1000));
			}
		}
	}
	
	/**
	 * 前面的对象值传到后面的对象
	 * @param productList
	 * @param productMongoList
	 */
	public void beanTo(List<Product> productList,List<ProductMongo> productMongoList){
		if(EmptyChecker.isNotEmpty(productList) && EmptyChecker.isNotEmpty(productMongoList)){
			Map<String,Product> map = new HashMap<String,Product>();
			for(Product product : productList){
				map.put(product.getProductId(), product);
			}
			for(ProductMongo productMongo : productMongoList){
				if(EmptyChecker.isNotEmpty(map.get(productMongo.getProductId()))){
					beanTo(map.get(productMongo.getProductId()),productMongo);
				}
			}
		}
	}
	
	/**
	 * 后面的对象值传到前面的对象
	 * @param productList
	 * @param productMongoList
	 */
	public void beanFrom(List<Product> productList,List<ProductMongo> productMongoList){
		if(EmptyChecker.isNotEmpty(productMongoList)){
			for(ProductMongo productMongo : productMongoList){
				Product product = new Product();
				beanTo(productMongo,product);
				productList.add(product);
			}
		}
	}
	
	/**
	 * 积分按积分比例转现金
	 * @param points
	 * @param pointRate
	 * @return
	 */
	public double pointToMoneyByRate(double points,int pointRate){
		if (pointRate == 0) {
			if (points > 0) {
				return points;
			}else{
				return 0;
			}
		}
		double result = Arith.divFloor(points, pointRate);
		return result;
	}

	public static void main(String[] args) {
		PropertiesUtil.load();
		OrderSplitPointCash split = new OrderSplitPointCash();
		/*List<Product> productList = new ArrayList<Product>();
		Payment payment = split.new Payment();
		Discount discount = split.new Discount();
		
		//测试数据
		Product product1 = split.new Product("A",30.21,2,9.76,4);
		Product product2 = split.new Product("B",22.11,11,5.12,5);
		Product product3 = split.new Product("C",13.02,7,11.06,2);
		productList.add(product1);
		productList.add(product2);
		productList.add(product3);
		
		int pointRate = 2141;
		int userTotalPoint = 0;
		
		double totalPrice = split.getProductListTotalMoney(productList);
		double totalPointMoney = Arith.divFloor(userTotalPoint, pointRate);
		int payBankPoint = Arith.intValue(Arith.roundUp(Arith.mul2(totalPointMoney, pointRate), 0));
		LogCvt.info(userTotalPoint+"积分换成钱："+totalPointMoney);
		LogCvt.info(totalPointMoney+"钱换成积分："+payBankPoint);
		payment = split.new Payment(totalPrice,payBankPoint,pointRate);
		
		split.splitPointMoney(productList,payment,discount);
		split.splitDiscountMoney(productList,20);
		split.checkValue(productList);*/
		
		Double[] aa = new Double[]{0.02, 0.02, 0.02, 0.01, null};
		Double[] bb = new Double[3];
		split.setDoubleNullValue(aa, 5);
		System.out.println(JSON.toJSONString(aa));
		System.out.println(JSON.toJSONString(EmptyChecker.isNotEmpty(aa)));
		System.out.println(bb);
		System.out.println("asdfasdf"+JSON.toJSONString(EmptyChecker.isNotEmpty(bb)));
		System.out.println(aa!=null);
		System.out.println(bb.length);
	}

}
