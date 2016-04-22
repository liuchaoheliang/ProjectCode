package com.froad.action.api.command;

import java.math.BigDecimal;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import com.froad.client.clientGoodsExchangeRack.ClientGoodsExchangeRack;
import com.froad.client.clientGoodsGroupRack.ClientGoodsGroupRack;
import com.froad.util.Assert;

public class ClientCommon {
	private static Logger logger = Logger.getLogger(ClientCommon.class);
	private static String  enableType="1";
	
	
	public static JSONObject payTypeObject(JSONObject goods,String fftToCashrato,String bankToCashrato){
		return payTypeObject(goods,goods.getString("groupPrice"),  fftToCashrato,  bankToCashrato);
	}
	
	//根据商品的支付方式，计算商品金额和积分(查询用 )
	public static JSONObject payTypeObject(JSONObject goods,String malketPrice,String fftToCashrato,String bankToCashrato){
//		String fftToCashrato="1";
//		String bankToCashrato="0.1";
		String isCash  = goods.getString("isCash");
		String cashPricing  = goods.getString("cashPricing");
		String cashPricingDesc  = goods.getString("cashPricingDesc");
		
		String isFftPoint  = goods.getString("isFftPoint");
		String fftPointPricing  = goods.getString("fftPointPricing");
		String fftPointPricingDesc  = goods.getString("fftPointPricingDesc");
		
		String isFftpointCash  = goods.getString("isFftpointCash");
		String fftpointCashPricing  = goods.getString("fftpointCashPricing");
		String fftPointCashPricingDesc  = goods.getString("fftPointCashPricingDesc");
		
		String isFftpointcashRatioPricing  = goods.getString("isFftpointcashRatioPricing");
		String fftpointcashRatioPricing  = goods.getString("fftpointcashRatioPricing");
		String fftPointCashRatioPricingDesc  = goods.getString("fftPointCashRatioPricingDesc");
		
		String isBankPoint= goods.getString("isBankPoint");
		String bankPointPricing  = goods.getString("bankPointPricing");
		String bankPointPricingDesc  = goods.getString("bankPointPricingDesc");
		
		String isBankpointCash= goods.getString("isBankpointCash");
		String bankpointCashPricing  = goods.getString("bankpointCashPricing");
		String bankPointCashPricingDesc  = goods.getString("bankPointCashPricingDesc");
		
		String isBankpointcashRatioPricing = goods.getString("isBankpointcashRatioPricing");
		String bankpointcashRatioPricing  = goods.getString("bankpointcashRatioPricing");
		String bankPointCashRatioPricingDesc  = goods.getString("bankPointCashRatioPricingDesc");
		
		JSONObject payType = new JSONObject();
		//JSONObject price = new JSONObject();
		//price.put("price", malketPrice);
		payType.put("price", malketPrice);
		JSONObject cashType = new JSONObject();
		if(enableType.equals(isCash)){
			cashType.put("isCash", isCash);
			cashType.put("cashPrice",cashPricing);
			cashType.put("desc",cashPricingDesc);
		}else{
			cashType.put("isCash", "0");  
		}
		payType.put("cashPay", cashType);
		JSONObject fftType = new JSONObject();
		if(enableType.equals(isFftPoint)){
			fftType.put("isfftPoint", "1");
			fftType.put("cashPrice", "0" );
			fftType.put("pointPrice", fftPointPricing );
			fftType.put("desc", fftPointPricingDesc);
		}else if(enableType.equals(isFftpointCash)){
			fftType.put("isfftPoint", "1");
			fftType.put("pointPrice", fftpointCashPricing.split("\\|")[0]  );
			fftType.put("cashPrice", fftpointCashPricing.split("\\|")[1] );
			fftType.put("desc", fftPointCashPricingDesc);
		}else if(enableType.equals(isFftpointcashRatioPricing)){
			fftType.put("isfftPoint", "2");  
			String pointRule = calculatePointRule(malketPrice,fftpointcashRatioPricing, fftToCashrato);  
			fftType.put("pointRule", pointRule);
			fftType.put("desc", fftPointCashRatioPricingDesc);
		}else{
			fftType.put("isfftPoint", "0");  
		}
		payType.put("fftPointPay", fftType);
		JSONObject bankType = new JSONObject();
		if(enableType.equals(isBankPoint)){
			bankType.put("isbankPoint", "1");
			bankType.put("pointPrice",bankPointPricing );
			bankType.put("cashPrice", "0" );
			bankType.put("desc", bankPointPricingDesc );
		}else if(enableType.equals(isBankpointCash )){
			bankType.put("isbankPoint", "1");
			bankType.put("pointPrice", bankpointCashPricing.split("\\|")[0]);
			bankType.put("cashPrice", bankpointCashPricing.split("\\|")[1]); 
			bankType.put("desc", bankPointCashPricingDesc);
		}else if(enableType.equals(isBankpointcashRatioPricing)){
			bankType.put("isbankPoint", "2");
			String pointRule = calculatePointRule(malketPrice,bankpointcashRatioPricing,bankToCashrato);  
			bankType.put("pointRule",pointRule);
			bankType.put("desc",bankPointCashRatioPricingDesc );
		}else{
			bankType.put("isbankPoint", "0");
		}
		payType.put("bankPointPay", bankType);
		
		return payType;
	}

	//浮动支付计算(查询用 )
	public static String 	calculatePointRule(String groupPrice,String pointRule,String ratio){
		logger.info("获取数据库积分配置:"+pointRule);
		String pointsratio1 = pointRule.split("\\|")[0];
		String pointsratio2 = pointRule.split("\\|")[1];
		BigDecimal ratio1= new BigDecimal(pointsratio1).divide(new BigDecimal("100"));
		BigDecimal ratio2= new BigDecimal(pointsratio2).divide(new BigDecimal("100"));
		BigDecimal points =new BigDecimal(groupPrice).divide(new BigDecimal(ratio)).setScale(0, BigDecimal.ROUND_UP);
		
		String lowPoint = points.multiply(ratio1).setScale(0, BigDecimal.ROUND_UP).toString();
		String higPoint = points.multiply(ratio2).setScale(0, BigDecimal.ROUND_DOWN).toString();
		System.out.println("points:"+points.toString()+",ratio1:"+ratio1+",ratio2:"+ratio2+",lowPoint:"+lowPoint+",hogPOint:"+higPoint);
		return lowPoint+"|"+higPoint+"|"+ratio;
		//return "200|500|1";
	}
	
	
	//根据支付类别，返回给客户端信息(支付调用)
	public static String getTranReturnByPayMethod(String payMethod){
		if("00".equals(payMethod)){
			return "支付成功";
		}else if("01".equals(payMethod)){
			return "支付成功";
		}else if("02".equals(payMethod)){
			return "交易受理成功，该笔账单已下推到您的手机银行卡上，请注意查询支付。";
		}else if("03".equals(payMethod)){
			return "积分抵扣成功，剩余支付账单已下推到您的手机银行卡上，请注意查询支付。";
		}else if("04".equals(payMethod)){
			return "积分抵扣成功，剩余支付账单已下推到您的手机银行卡上，请注意查询支付。";
		}else if("05".equals(payMethod)){
			return "积分抵扣成功，剩余支付账单已下推到您的手机银行卡上，请注意查询支付。";
		}else if("06".equals(payMethod)){
			return "积分抵扣成功，剩余支付账单已下推到您的手机银行卡上，请注意查询支付。";
		}else{
			return "成功了";
		}
	}
	
	//验证商品价格
	public static boolean checkGoodsPrice(String goodsNum,String goodsPrice,String cashPrice,String fftPointPrice,String bankPointPrice,String fftToCashrato,String bankToCashrato){
		 BigDecimal clientPrice =new BigDecimal(fftPointPrice).multiply( new BigDecimal(fftToCashrato));
		 clientPrice  = new BigDecimal(bankPointPrice).multiply( new BigDecimal(bankToCashrato)).add(new BigDecimal(cashPrice)).add(clientPrice);
		 BigDecimal gPrice = new BigDecimal(goodsNum).multiply(new BigDecimal(goodsPrice));
		 if(clientPrice.compareTo(gPrice)!=0){
				return false;
		 }else{
			return true;
		} 
	}
	
	/**
	 * 
	  * 方法描述： 判断用户支付方式(支付调用)
	  * @param: cashPrice 等 ，为单价
	  * @return: 
	  * @version: 1.0
	  * @author: guoming guoming@f-road.com.cn
	  * @time: 2013-3-21 下午04:59:30
	 */
 	public static String payMethod(Object object,String cashPrice,String fftPointPrice,String bankPointPrice){
		if(object==null) return null;
		String clientPayCode=clientPayMethod(cashPrice,fftPointPrice,bankPointPrice);
		if(object instanceof ClientGoodsExchangeRack){
			ClientGoodsExchangeRack clientGoodsExchangeRack = (ClientGoodsExchangeRack)object;
			 
			if(clientPayCode.equals("01")&&enableType.equals(clientGoodsExchangeRack.getIsCash())){
				return "02";
			}else if(clientPayCode.equals("02")&&enableType.equals(clientGoodsExchangeRack.getIsFftPoint())){
				return "00";
			}else if(clientPayCode.equals("03")&&enableType.equals(clientGoodsExchangeRack.getIsBankPoint())){
				return "01";
			}
			//使用了分分通积分
			if(clientPayCode.equals("11")&&enableType.equals(clientGoodsExchangeRack.getIsFftpointCash())){
				String fftpoints = clientGoodsExchangeRack.getFftpointCashPricing().split("\\|")[0];
				String fftcash = clientGoodsExchangeRack.getFftpointCashPricing().split("\\|")[1];
				if((new BigDecimal(fftpoints)).compareTo(new BigDecimal(fftPointPrice))==0 && (new BigDecimal(fftcash)).compareTo(new BigDecimal(cashPrice))==0){
					return "03";
				} 
			}
			if(clientPayCode.equals("11")&&enableType.equals(clientGoodsExchangeRack.getIsFftpointcashRatioPricing())){
				return "05";
			}
			//使用了银行积分
			if(clientPayCode.equals("12")&&enableType.equals(clientGoodsExchangeRack.getIsBankpointCash() )){
				String bankpoints = clientGoodsExchangeRack.getBankpointCashPricing().split("\\|")[0];
				String bankcash = clientGoodsExchangeRack.getBankpointCashPricing().split("\\|")[1];
				if((new BigDecimal(bankpoints)).compareTo(new BigDecimal(bankPointPrice))==0 && (new BigDecimal(bankcash)).compareTo(new BigDecimal(cashPrice))==0){
					return "04";
				}
			}
			if(clientPayCode.equals("12")&&enableType.equals(clientGoodsExchangeRack.getIsBankpointcashRatioPricing())){
				return "06";	
			} 
			
		}else if(object instanceof ClientGoodsGroupRack){
			ClientGoodsGroupRack  clientGoodsExchangeRack = (ClientGoodsGroupRack)object;
  
			if(clientPayCode.equals("01")&&enableType.equals(clientGoodsExchangeRack.getIsCash())){
				return "02";
			}else if(clientPayCode.equals("02")&&enableType.equals(clientGoodsExchangeRack.getIsFftPoint())){
				return "00";
			}else if(clientPayCode.equals("03")&&enableType.equals(clientGoodsExchangeRack.getIsBankPoint())){
				return "01";
			}
			//使用了分分通积分
			if(clientPayCode.equals("11")&&enableType.equals(clientGoodsExchangeRack.getIsFftpointCash())){
				String fftpoints = clientGoodsExchangeRack.getFftpointCashPricing().split("\\|")[0];
				String fftcash = clientGoodsExchangeRack.getFftpointCashPricing().split("\\|")[1];
				if((new BigDecimal(fftpoints)).compareTo(new BigDecimal(fftPointPrice))==0 && (new BigDecimal(fftcash)).compareTo(new BigDecimal(cashPrice))==0){
					return "03";
				} 
			}
			if(clientPayCode.equals("11")&&enableType.equals(clientGoodsExchangeRack.getIsFftpointcashRatioPricing())){
				return "05";
			}
			//使用了银行积分
			if(clientPayCode.equals("12")&&enableType.equals(clientGoodsExchangeRack.getIsBankpointCash() )){
				String bankpoints = clientGoodsExchangeRack.getBankpointCashPricing().split("\\|")[0];
				String bankcash = clientGoodsExchangeRack.getBankpointCashPricing().split("\\|")[1];
				if((new BigDecimal(bankpoints)).compareTo(new BigDecimal(bankPointPrice))==0 && (new BigDecimal(bankcash)).compareTo(new BigDecimal(cashPrice))==0){
					return "04";
				}
			}
			if(clientPayCode.equals("12")&&enableType.equals(clientGoodsExchangeRack.getIsBankpointcashRatioPricing())){
				return "06";	
			} 
			
		
		}
		
		return null;
	}
	
	//先判断客户端支付类别(支付调用)
	public static String clientPayMethod(String cashPrice,String fftPointPrice,String bankPointPrice){
		if(!validatePriceNull(cashPrice)&&validatePriceNull(fftPointPrice)&&validatePriceNull(bankPointPrice)){
			 return "01";
		}else if(validatePriceNull(cashPrice)&&!validatePriceNull(fftPointPrice)&&validatePriceNull(bankPointPrice)){
			 return "02";
		}else if(validatePriceNull(cashPrice)&&validatePriceNull(fftPointPrice)&&!validatePriceNull(bankPointPrice)){
			 return "03";
		}else if(!validatePriceNull(cashPrice)&&!validatePriceNull(fftPointPrice)&&validatePriceNull(bankPointPrice)){
			 return "11";
		}else if(!validatePriceNull(cashPrice)&&validatePriceNull(fftPointPrice)&&!validatePriceNull(bankPointPrice)){
			 return "12";
		}
		
		return "";
	}
	
	public static boolean validatePriceNull(String price){
		if(Assert.empty(price)){
			return true;
		}
		if(new BigDecimal(price).compareTo(new BigDecimal("0"))==0){
			return true;
		}
		return false;
	}
	//price
	public static String payString(String cash,String fftPoint,String bankPoint){
		String payPrice="";
		if(!validatePriceNull(cash)){
			payPrice+="金额："+cash + " ";
		}
		if(!validatePriceNull(fftPoint)){
			payPrice+="分分通积分："+fftPoint + " ";
		}
		if(!validatePriceNull(bankPoint)){
			payPrice+="银行积分："+bankPoint + " ";
		}
		//logger.info("payString:"+payPrice);
		return payPrice;
	}
	 
	//去除html
	public static String getTxtWithoutHTMLElement (String element) //静态方法
	{  
		if(null==element||"".equals(element.trim())) //判断element参数是否为空,如果为空直接返回element.即空
		{ 
			return ""; 
		} 
		Pattern pattern=Pattern.compile("<[^<|^>]*>"); //正则表达式
		Matcher matcher=pattern.matcher(element); //使用正则表达式验证参数element
		StringBuffer sb = new StringBuffer();
		boolean result1 = matcher.find();
		while (result1) {
			matcher.appendReplacement(sb, "");
			result1 = matcher.find();
		}
		matcher.appendTail(sb); //实现终端添加和替换步骤。
		return sb.toString().replaceAll("&nbsp;", "");  //将StringBuffer转换为String类型并返回
	}
	 
}
