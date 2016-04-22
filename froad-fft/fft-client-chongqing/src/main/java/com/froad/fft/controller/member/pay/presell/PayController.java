package com.froad.fft.controller.member.pay.presell;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.bean.PayResult;
import com.froad.fft.bean.PayResult.PayCode;
import com.froad.fft.bean.Result;
import com.froad.fft.bean.trans.TransDto;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.AreaDto;
import com.froad.fft.dto.BusinessCircleDto;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.trans.TransCreateSource;
import com.froad.fft.enums.trans.TransPayChannel;
import com.froad.fft.enums.trans.TransPayMethod;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.support.base.impl.ProductPresellSupportImpl;
import com.froad.fft.support.base.impl.TransSupportImpl;
import com.froad.fft.util.NullValueCheckUtil;

@Controller("pay.presell")
@RequestMapping("/member/pay/presell")
public class PayController extends BaseController{
	
	@Resource
	private ProductPresellSupportImpl productPresellSupportImpl;

	@Resource
	private TransSupportImpl transSupportImpl;
	
	/**
	 * 格式化浮点数格式
	 */
	final String floatFormat = "#########.##";
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>创建订单</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月28日 上午10:41:42 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "create_order")
	public String createOrder(HttpServletRequest req,String productId,String buyCount,ModelMap modelMap){
		if(NullValueCheckUtil.isStrEmpty(productId)){
			return ILLEGALITY;
		}
		buyCount = NullValueCheckUtil.isStrEmpty(buyCount) ? "1" : buyCount; 
		Long id = null;
		Integer buyNumber;
		try {
			id = Long.parseLong(productId);
			buyNumber = Integer.parseInt(buyCount);
		} catch (Exception e) {
			return ILLEGALITY;
		}
		
		UserEngineDto userEngineDto = (UserEngineDto)getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);//获取用户登录信息
		
		ProductPresellDto productPresellDto = productPresellSupportImpl.findProductPresellById(id); //获取预售产品信息
		productPresellDto.setTrueBuyerNumber(productPresellDto.getTrueBuyerNumber() == null ? 0 : productPresellDto.getTrueBuyerNumber());
	
		ProductDto productDto = new ProductDto();//获取基础商品信息
		productDto.setProductPresellId(id);
		List<ProductDto> productDtos = productPresellSupportImpl.getProductByCondition(productDto);
		if(NullValueCheckUtil.isListArrayEmpty(productDtos)){
			logger.info("加载预售商品的基础商品信息失败， presellid" + productId);
			return ILLEGALITY;
		}
		productDto = productDtos.get(0);
		
		List<PresellDeliveryDto> presellDeliveryDtos = productPresellSupportImpl.getProductPresellDeliveryByProductId(id);//查询所有自提点
		
		List<BusinessCircleDto> businessCircleDtos = new ArrayList<BusinessCircleDto>();//查出所有自提点对应的商圈（去重）
		BusinessCircleDto tempBusi = null;
		boolean isExist = false;
		for (PresellDeliveryDto presellDeliveryDto : presellDeliveryDtos) {
			tempBusi = (productPresellSupportImpl.getBusinessCircleDtosById(presellDeliveryDto.getBusinessCircleId()));
			//获取该自提点对应的商圈信息
			for (BusinessCircleDto temp : businessCircleDtos) {
				if(temp.getId().equals(tempBusi.getId())){ //已存在
					isExist = true;
					break;
				}
			}
			if(isExist){
				isExist = false;
				continue;
			}else{
				businessCircleDtos.add(tempBusi);
			}
		}
		
		List<AreaDto> areaDtos = new ArrayList<AreaDto>(); //查询商圈对应的地区（去重）
		AreaDto tempArea = null;
		for (BusinessCircleDto businessCircleDto : businessCircleDtos) {
			tempArea = productPresellSupportImpl.getAreaById(businessCircleDto.getAreaId());
			for (AreaDto temp : areaDtos) {
				if(temp.getId().equals(tempArea.getId())){
					isExist = true;
					break;
				}
			}
			if(isExist){
				isExist = false;
				continue;
			}else{
				areaDtos.add(tempArea);
			}
		}
		
		//校验当前用户还能购买的数量
		Integer number= transSupportImpl.getMemberBuyNum(userEngineDto.getMemberCode(), productDto.getId());
		Integer maxNum = productPresellDto.getPerNumber();
		if(maxNum!=0){
			int nowMax = maxNum-number;
			if(nowMax<=0){//购买数量已达上限。
				modelMap.put("cantBuy", true);//不能购买的标识
			}else{
				modelMap.put("maxNum", nowMax);
			}
		}
		modelMap.put("userEngineDto", userEngineDto); 			//获取当前用户的相关信息
		modelMap.put("productPresellDto", productPresellDto); 	//预售信息
		modelMap.put("buyNumber", buyNumber);
		modelMap.put("productDto", productDto);
		
		//===================== 将自提点|商圈以json字符串给页面用于伪二级联动
		modelMap.put("businessCircleJson", JSONObject.toJSONString(businessCircleDtos));
		modelMap.put("presellDeliveryJson", JSONObject.toJSONString(presellDeliveryDtos));
		modelMap.put("areaJson", JSONObject.toJSONString(areaDtos));
		//=====================
		
		//
		removeOrderHistory(req,modelMap);
		return "/member/pay/presell/create_order";
	}
	
	/**
	*<p>从确认订单页返回，需要在创建订单显示历史选择（处理完成后移除）</p>
	* @author larry
	* @datetime 2014-4-18下午05:34:28
	* @return void
	* @throws 
	* @example<br/>
	*
	 */
	private void removeOrderHistory(HttpServletRequest req,ModelMap modelMap){
		//从确认订单页返回，需要在创建订单显示历史选择
		Object history = getSessionValue(req, SessionKey.SYSTEM_TEMP_VAL);
		if(history!=null){
			modelMap.put("history", history);
			removeSessionByKey(req, SessionKey.SYSTEM_TEMP_VAL);//移除临时变量
		}
		
	}
	
	
	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>订单确认展示</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月28日 上午10:51:29 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "confirm_order")
	public String confirmOrder(HttpServletRequest req,
			String buyerName,
			String buyCount,
			String fftpoints,String cashmethod,
			String productId,
			String paymobile,
			String deliveryMobile,
			String deliveryId,
			String areaId,
			String bussId,
			ModelMap modelMap){
		createTokenKey(req);//要求该页面防止重复提交
		if(NullValueCheckUtil.isStrEmpty(buyerName) || NullValueCheckUtil.isStrEmpty(productId) || NullValueCheckUtil.isStrEmpty(buyCount)){
			logger.info("进行确认订单操作，必要参数为空");
			return ILLEGALITY;
		}
		
		Long id = null; //预售商品id
		Integer count = null; //购买数量
		Long deliveryID = null; //用户所选自提点ID
		try {
			id = Long.parseLong(productId);
			count = Integer.parseInt(buyCount);
			deliveryID = Long.parseLong(deliveryId);
		} catch (Exception e) {
			logger.info("进行确认订单操作，参数非法");
			return ILLEGALITY;
		}
		
		PresellDeliveryDto presellDeliveryDto = productPresellSupportImpl.getProductPresellDeliveryById(deliveryID);//查询用户所选提货点信息
		
		ProductPresellDto productPresellDto = productPresellSupportImpl.findProductPresellById(id);
		
		ProductDto productDto = new ProductDto();
		productDto.setProductPresellId(id);
		List<ProductDto> productDtos = productPresellSupportImpl.getProductByCondition(productDto);
		if(NullValueCheckUtil.isListArrayEmpty(productDtos)){
			logger.info("加载预售商品的基础商品信息失败，productPresellid" + productId);
			return ILLEGALITY;
		}
		
		productDto = productDtos.get(0);
		Float totalPrice = Float.parseFloat(productDto.getPrice()) * count; //计算总价
		modelMap.put("productPresellDto", productPresellDto); 	//预售信息
		modelMap.put("productDto", productDto);
		modelMap.put("buyNumber", count);
		modelMap.put("buyerName", buyerName);
		modelMap.put("productId", productDto.getId());
		modelMap.put("totalPrice",new DecimalFormat(floatFormat).format(totalPrice));
		modelMap.put("fftpoints", fftpoints);
		modelMap.put("deliveryMobile", deliveryMobile);
		modelMap.put("presellDeliveryDto", presellDeliveryDto);
		modelMap.put("cashmethod", cashmethod);
		modelMap.put("areaId", areaId);
		modelMap.put("bussId", bussId);
		
		String payMethodMsg = "";
		String moneyMsg = "";
		if(NullValueCheckUtil.isStrEmpty(fftpoints) || Float.parseFloat(fftpoints) <= 0){
			logger.info("用户未使用积分进行交易 使用支付方式为：" + cashmethod);
			if(cashmethod.equals("card")){ 
				logger.info("重庆手机银行卡支付");
				payMethodMsg = "重庆手机银行卡";
				modelMap.put("paymobile", paymobile);
				moneyMsg = "重庆手机银行卡 " + new DecimalFormat(floatFormat).format(totalPrice) + "元";
			}else if(cashmethod.equals("alipay")){
				logger.info("支付宝支付");
				payMethodMsg = "支付宝";
				moneyMsg = "支付宝 " + new DecimalFormat(floatFormat).format(totalPrice) + "元";
			}else if(cashmethod.equals("bank")){
				logger.info("网上银行支付");
				payMethodMsg = "网上银行";
				moneyMsg = "网上银行 " + new DecimalFormat(floatFormat).format(totalPrice) + "元";
			}else{
				return ILLEGALITY;
			}
			
		}else{
			Float pointsUse = Float.parseFloat(fftpoints);//用户使用的分分通积分数量
			if(pointsUse >= totalPrice){
				logger.info("用户选择全部使用积分进行购买");
				payMethodMsg = "纯积分";
				moneyMsg = "积分" + new DecimalFormat(floatFormat).format(totalPrice) + "分";
			}else{
				logger.info("用户选择使用部分积分购买");
				if(cashmethod.equals("card")){ 
					logger.info("积分+重庆手机银行卡支付");
					payMethodMsg = "积分 + 重庆手机银行卡";
					modelMap.put("paymobile", paymobile);
					moneyMsg = "积分" + new DecimalFormat(floatFormat).format(pointsUse) + "分 + " + "重庆手机银行卡 " +new DecimalFormat(floatFormat).format(totalPrice - pointsUse) + "元";
				}else if(cashmethod.equals("alipay")){
					logger.info("积分+支付宝支付");
					payMethodMsg = "积分 + 支付宝";
					moneyMsg = "积分 " + new DecimalFormat(floatFormat).format(pointsUse) + "分 + " + "支付宝 " +new DecimalFormat(floatFormat).format(totalPrice - pointsUse) + "元";
				}else if(cashmethod.equals("bank")){
					logger.info("积分+网银支付");
					payMethodMsg = "积分+网上银行";
					moneyMsg = "积分 " + new DecimalFormat(floatFormat).format(pointsUse) + "分 + " + "网上银行 " +new DecimalFormat(floatFormat).format(totalPrice - pointsUse) + "元";
				}else{
					return ILLEGALITY;
				}
				
			}
		}
		modelMap.put("payMethodMsg", payMethodMsg);
		modelMap.put("moneyMsg", moneyMsg);
		
		//临时保存当前订单信息，解决订单确认页返回创建订单页信息丢失的问题
		createSessionObject(req, SessionKey.SYSTEM_TEMP_VAL, modelMap);
		
		return "/member/pay/presell/confirm_order";
	}
	
	@RequestMapping(value = "order_result" , method = RequestMethod.POST)
	public String orderResult(HttpServletRequest req,TransDto transDto,String productId,String buyNumber,String cashmethod,ModelMap modelMap){
		
		
		
		//基础数据校验
		UserEngineDto userEngineDto = (UserEngineDto)getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		logger.info("用户开始进行交易  发起人帐号：" + userEngineDto.getLoginID());
		
		ProductDto productDto = null;
		Integer buyCount = null;
		//基础信息添加
		try {
			productDto = productPresellSupportImpl.getProductDtoById(Long.parseLong(productId));
			buyCount = Integer.parseInt(buyNumber);
			if(productDto == null){
				logger.info("所查询商品不存在");
				return ILLEGALITY;
			}
		} catch (Exception e) {
			logger.error("传入商品Id或者购买数量为空或者不合法" + productId + "" + buyNumber);
			return ILLEGALITY;
		}
		
		transDto.setMemberCode(userEngineDto.getMemberCode());
		Map<Long, Integer> productMap = new HashMap<Long, Integer>();
		productMap.put(Long.parseLong(productId), Integer.parseInt(buyNumber));
		transDto.setProductMap(productMap);
		transDto.setCreateSource(TransCreateSource.pc);
		transDto.setTransType(TransType.presell);
		
		
		Float totlePrice = Float.parseFloat(productDto.getPrice()) * buyCount;
		Float pointsPay = NullValueCheckUtil.isStrEmpty(transDto.getFftPoints()) ? null : Float.parseFloat(transDto.getFftPoints());
		if(pointsPay == null || pointsPay == 0){
			//纯现金支付
			if(cashmethod.equals("card")){ 
				logger.info("重庆手机银行卡支付");
				transDto.setPayMethod(TransPayMethod.film_card);
				transDto.setPayChannel(TransPayChannel.filmCard);
			}else if(cashmethod.equals("alipay")){
				logger.info("支付宝支付");
				transDto.setPayMethod(TransPayMethod.alipay);
				transDto.setPayChannel(TransPayChannel.alipay);
			}else if(cashmethod.equals("bank")){
				logger.info("网上银行支付");
				transDto.setPayMethod(TransPayMethod.internate_bank);
				transDto.setPayChannel(TransPayChannel.internetBank);
			}else{
				return ILLEGALITY;
			}
		}else{
			if(pointsPay >= totlePrice){
				//纯积分支付
				transDto.setPayMethod(TransPayMethod.points_fft);
			}else{
				updateUserAllInfo(req);
				if(cashmethod.equals("card")){ 
					logger.info("积分+重庆手机银行卡支付");
					transDto.setPayMethod(TransPayMethod.points_fft_film_card);
					transDto.setPayChannel(TransPayChannel.filmCard);
				}else if(cashmethod.equals("alipay")){
					logger.info("积分+支付宝支付");
					transDto.setPayMethod(TransPayMethod.alipay_fft_points);
					transDto.setPayChannel(TransPayChannel.alipay);
				}else if(cashmethod.equals("bank")){
					logger.info("积分+网上银行支付");
					transDto.setPayMethod(TransPayMethod.internate_fft_points);
					transDto.setPayChannel(TransPayChannel.internetBank);
				}else{
					return ILLEGALITY;
				}
			}
		}
		
		//开始创建精品预售交易
		logger.info("开始调用创建交易接口    传入数据：" + JSONObject.toJSONString(transDto));
		Result result = transSupportImpl.createTrans(transDto);
		logger.info("创建支付完成    返回数据：" + JSONObject.toJSONString(result));
		boolean flag = false;
		String transSn = null;
		if(Result.SUCCESS.equals(result.getCode())){
			flag = true; 
			//交易创建成功
			transSn = result.getMsg();
			if(NullValueCheckUtil.isStrEmpty(transSn)){
				logger.equals("创建订单成功后未获取到成功创建的订单编号");
				modelMap.put("result", false);
				modelMap.put("msg", "抱歉交易失败，系统繁忙");
			}
			logger.info("交易创建成功，进行支付操作");
			
		}else{
			//交易创建失败
			modelMap.put("payResult", new PayResult(result.getMsg(), PayCode.error));
		}
		
		//-----------------------------------支付订单
		if(flag){
			
			result = transSupportImpl.doPayTrans(transSn);
			
			logger.info("支付订单完成   返回数据：" + JSONObject.toJSONString(result));
			if(Result.SUCCESS.equals(result.getCode())){
				
				logger.info("开始调用付款订单接口 传入订单号：" + transSn);
				if(transDto.getPayMethod() == TransPayMethod.points_fft){
					//纯积分交易
				}else if(transDto.getPayMethod() == TransPayMethod.alipay_fft_points || transDto.getPayMethod() == TransPayMethod.alipay){
					//涉及到支付宝交易的
					modelMap.put("alipayForm", result.getMsg());
					createSessionObject(req, SessionKey.SYSTEM_TEMP_VAL, transSn);//将transSn存于临时数据存储点中
					return "/member/pay/alipay";
				}else if(transDto.getPayMethod() == TransPayMethod.film_card || transDto.getPayMethod() == TransPayMethod.points_fft_film_card){
					//涉及到贴膜卡支付
					createSessionObject(req, SessionKey.SYSTEM_TEMP_VAL, transSn);//将transSn存于临时数据存储点中
					return "/member/pay/bank_card";
				}else if(transDto.getPayMethod() == TransPayMethod.internate_bank || transDto.getPayMethod() == TransPayMethod.internate_fft_points){
					//涉及到网银支付的
					createSessionObject(req, SessionKey.SYSTEM_TEMP_VAL, transSn);//将transSn存于临时数据存储点中
					modelMap.put("bankForm", result.getMsg());
					return "/member/pay/bank";
				}else{
					//支付方式异常
					logger.error("校验支付方式发生异常");
					return ILLEGALITY;
				}
				modelMap.put("payResult", new PayResult("交易成功", PayCode.success));
				logger.info("支付交易成功，更新用户积分信息");
				logger.info("更新用户积分信息结果：" + updateUserAllInfo(req));
			}else{
				//支付交易失败
				logger.info("支付交易失败");
				modelMap.put("payResult", new PayResult(result.getMsg(), PayCode.error));
			}
		}
		removeOrderHistory(req,modelMap);//订单支付完成后，移除当前订单记录
		return "/member/pay/order_result";
	}
	
	@RequestMapping(value="validateQuantity",method=RequestMethod.POST)
	public @ResponseBody AjaxCallBackBean validateQuantity(Long productId,Integer quantity,HttpServletRequest req){
		ProductPresellDto productPresellDto  = productPresellSupportImpl.getPresellByProductId(productId);
		//基础数据校验
		UserEngineDto userEngineDto = (UserEngineDto)getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		Result result = transSupportImpl.validateQuantity(userEngineDto.getMemberCode(),
				productId, productPresellDto.getPerminNumber(), productPresellDto.getPerNumber(), quantity);
		if(result.getCode().equals(Result.FAIL)){//验证不通过
			return new AjaxCallBackBean(false,result.getMsg());
		}
		return new AjaxCallBackBean(true);
	}
	
}
