package com.froad.jetty.servlet;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.froad.enums.CashType;
import com.froad.enums.H5ResultCode;
import com.froad.enums.MonitorPointEnum;
import com.froad.enums.PaymentMethod;
import com.froad.jetty.service.OrderService;
import com.froad.jetty.service.SupportService;
import com.froad.jetty.service.impl.OrderServiceImpl;
import com.froad.jetty.service.impl.SupportServiceImpl;
import com.froad.jetty.servlet.AbstractHttpServlet;
import com.froad.jetty.vo.PlaceOrderRequestVo;
import com.froad.jetty.vo.PlaceOrderResponseVo;
import com.froad.jetty.vo.ResponseVo;
import com.froad.logback.LogCvt;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.mq.beanstalkd.impl.SimpleClusterBeanstalkManager;
import com.froad.thrift.client.ThriftPoolClient;
import com.froad.thrift.client.ThriftService;
import com.froad.util.JSonUtil;
import com.froad.util.PropertiesUtil;
import com.froad.util.ServletResultUtil;
import com.pay.user.dto.UserSpecDto;
import com.pay.user.helper.VIPStatus;

/**
 * 秒杀下单接口服务
 * 
 * @author wangzhangxu
 * @date 2015年4月15日 上午10:14:35
 * @version v1.0
 */
public class PlaceOrder2Servlet extends AbstractHttpServlet {
	
	private static final long serialVersionUID = -6876034782040410977L;
	
	private OrderService orderService;
	
	private SupportService supportService;
	
	private MonitorService monitorService;
	
	
	/**
	 * 
	 */
	public PlaceOrder2Servlet() {
		super();
	}

	/**
	 * 初始化
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		
		orderService = new OrderServiceImpl();
		
		supportService = new SupportServiceImpl();
		
		monitorService = new MonitorManager();
		
		// 初始化Beanstalkd集群管理器
		SimpleClusterBeanstalkManager.getInstance();
		
		// 初始化配置文件工具
		PropertiesUtil.load();
		
		// 初始化连接池
		ThriftPoolClient.initPool();
	}

	/**
	 * 下单请求处理
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		long start = System.currentTimeMillis();
		PlaceOrderRequestVo reqVo = initParameters(request);
		ResponseVo responseVo = validParameters(reqVo);
		if (!responseVo.success()) {
			ServletResultUtil.render(responseVo, request, response);
			return;
		}
		
		// 统计流量
		monitorService.send(MonitorPointEnum.Seckill_Place_Order_Count);
		
		// 压力测试模式不验证密码
		if (!"test".equals(reqVo.getMode())) {
			// 验证用户登录
			long checkTokenStart = System.currentTimeMillis();
			responseVo = supportService.checkToken(reqVo.getMemberCode(), reqVo.getToken());
			long checkTokenEnd = System.currentTimeMillis();
			LogCvt.debug("下单接口-校验登录状态，耗时：" + (checkTokenEnd - checkTokenStart) + " ms");
			if (!responseVo.success()) {
				ServletResultUtil.render(responseVo, request, response);
				return;
			}
		}
		
		// 如果包含贴膜卡支付方式，则校验贴膜卡号
		if (reqVo.getCashType() != null && CashType.foil_card.code() == reqVo.getCashType().intValue()) {
			long checkFoilCardStart = System.currentTimeMillis();
			responseVo = orderService.checkFoilCardNum(reqVo);
			long checkFoilCardEnd = System.currentTimeMillis();
			LogCvt.debug("下单接口-校验贴膜卡号，耗时：" + (checkFoilCardEnd - checkFoilCardStart) + " ms");
			if (!responseVo.success()) {
				ServletResultUtil.render(responseVo, request, response);
				return;
			}
		}
		
		// 验证用户短信验证码
		long checkSmsCodeStart = System.currentTimeMillis();
		responseVo = supportService.checkSmsCode(reqVo.getClientId(), reqVo.getSmsToken(), reqVo.getSmsCode());
		long checkSmsCodeEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-校验短信验证码，耗时：" + (checkSmsCodeEnd - checkSmsCodeStart) + " ms");
		if (!responseVo.success()) {
			ServletResultUtil.render(responseVo, request, response);
			return;
		}
		
		if (PaymentMethod.cash.getCode().equals(reqVo.getPayType().toString()) 
				&& reqVo.getCashType() != null 
				&& CashType.foil_card.code() == reqVo.getCashType().intValue()) {
			// 纯贴膜卡支付，不需要校验支付密码
		} else {
			// 压力测试模式不验证密码
			if (!"test".equals(reqVo.getMode())) {
				// 验证用户支付密码
				long checkPayPasswordStart = System.currentTimeMillis();
				responseVo = supportService.checkPayPassword(reqVo.getMemberCode(), reqVo.getPayPwd(), reqVo.getSource());
				long checkPayPasswordEnd = System.currentTimeMillis();
				LogCvt.debug("下单接口-校验支付密码，耗时：" + (checkPayPasswordEnd - checkPayPasswordStart) + " ms");
				if (!responseVo.success()) {
					ServletResultUtil.render(responseVo, request, response);
					return;
				}
			}
		}
		
		// 填充用户信息
		long userInfoStart = System.currentTimeMillis();
		UserSpecDto userInfo = supportService.getUserInfo(reqVo.getMemberCode());
		long userInfoEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-获取用户信息，耗时：" + (userInfoEnd - userInfoStart) + " ms");
		if (userInfo != null) {
			reqVo.setMemberName(userInfo.getLoginID());
			if (userInfo.getVip() != null 
					&& VIPStatus.NORMAL.compareTo(userInfo.getVip().getVipStatus()) == 0) {
				LogCvt.debug("是VIP用户[memberCode=" + reqVo.getMemberCode() + "]");
				reqVo.setVip(true);
			} else {
				reqVo.setVip(false);
			}
		} else {
			responseVo = new ResponseVo(H5ResultCode.userLoginCenterException);
			ServletResultUtil.render(responseVo, request, response);
			return;
		}
		
		// 下单
		long placeOrderStart = System.currentTimeMillis();
		PlaceOrderResponseVo resVo = new PlaceOrderResponseVo();
		responseVo = orderService.placeOrder2(userInfo.getLoginID(), reqVo, resVo);
		long placeOrderEnd = System.currentTimeMillis();
		LogCvt.debug("下单接口-去下单，耗时：" + (placeOrderEnd - placeOrderStart) + " ms");
		if (responseVo.success()) {
			String json = JSonUtil.toJSonString(resVo);
			ServletResultUtil.render(json, request, response);
		} else {
			ServletResultUtil.render(responseVo, request, response);
		}
		long end = System.currentTimeMillis();
		LogCvt.info("下单接口，总耗时：" + (end - start) + " ms");
		
		// 统计耗时
		monitorService.send(MonitorPointEnum.Seckill_Place_Order_Timeout, "" + (end - start));
	}
	
	/**
	 * 初始化参数
	 */
	public PlaceOrderRequestVo initParameters(HttpServletRequest request){
		PlaceOrderRequestVo reqVo = new PlaceOrderRequestVo();
		try {
			String json = IOUtils.toString(request.getInputStream());
			reqVo = JSONObject.parseObject(json, new TypeReference<PlaceOrderRequestVo>(){});
		} catch (IOException e) {
			LogCvt.error("下单接口解析请求数据异常", e);
		}
		
		Long memberCode = null;
		try{
			memberCode = Long.parseLong(request.getHeader("memberCode"));
		}catch(Exception e){}
		reqVo.setMemberCode(memberCode);
		reqVo.setToken(request.getHeader("token"));
		
		reqVo.setClientId((String)request.getAttribute("clientId"));
		
		LogCvt.debug("下单接口请求参数：" + JSonUtil.toJSonString(reqVo));
		
		return reqVo;
	}
	
	public ResponseVo validParameters(PlaceOrderRequestVo reqVo){
		ResponseVo errorResVo = new ResponseVo(H5ResultCode.missingParam.getCode(), H5ResultCode.missingParam.getMsg());
		// required
		if (reqVo.getMemberCode() == null 
				|| StringUtils.isEmpty(reqVo.getToken()) 
				|| StringUtils.isEmpty(reqVo.getClientId()) 
				|| StringUtils.isEmpty(reqVo.getProductId()) 
				|| reqVo.getBuyNum() == null 
				|| reqVo.getPayType() == null
				|| StringUtils.isEmpty(reqVo.getRecvId())) {
			return errorResVo;
		}
		
		/* 
		 * 适配前端和后端的类型
		 * 1.联盟积分（纯积分）        payType:2 cashType:null
		 * 2.银行积分（纯积分）        payType:3 cashType:null
		 * 3.联盟积分+银行快捷         payType:4 cashType:51 
		 * 4.银行积分+银行快捷         payType:5 cashType:51 
		 * 5.联盟积分+贴膜卡支付     payType:4 cashType:20 
		 * 6.银行积分+贴膜卡支付     payType:5 cashType:20 
		 * 7.纯现金快捷支付               payType:1 cashType:51 
		 * 8.纯贴膜卡支付                   payType:1 cashType:20 
		 */
		boolean checkPassword = true; // 是否需要校验密码
		if ("1".equals(reqVo.getPayType().toString())) {
			reqVo.setPayType(Integer.parseInt(PaymentMethod.froadPoints.getCode()));
			reqVo.setCashType(null);
			
			reqVo.setFoilCardNum(null);
		} else if ("2".equals(reqVo.getPayType().toString())) {
			reqVo.setPayType(Integer.parseInt(PaymentMethod.bankPoints.getCode()));
			reqVo.setCashType(null);
			
			reqVo.setFoilCardNum(null);
		} else if ("3".equals(reqVo.getPayType().toString())) {
			reqVo.setPayType(Integer.parseInt(PaymentMethod.froadPointsAndCash.getCode()));
			reqVo.setCashType(CashType.bank_fast_pay.code());
			
			reqVo.setFoilCardNum(null);
		} else if ("4".equals(reqVo.getPayType().toString())) {
			reqVo.setPayType(Integer.parseInt(PaymentMethod.bankPointsAndCash.getCode()));
			reqVo.setCashType(CashType.bank_fast_pay.code());
			
			reqVo.setFoilCardNum(null);
		} else if ("5".equals(reqVo.getPayType().toString())) {
			reqVo.setPayType(Integer.parseInt(PaymentMethod.froadPointsAndCash.getCode()));
			reqVo.setCashType(CashType.foil_card.code());
		} else if ("6".equals(reqVo.getPayType().toString())) {
			reqVo.setPayType(Integer.parseInt(PaymentMethod.bankPointsAndCash.getCode()));
			reqVo.setCashType(CashType.foil_card.code());
		} else if ("7".equals(reqVo.getPayType().toString())) {
			reqVo.setPayType(Integer.parseInt(PaymentMethod.cash.getCode()));
			reqVo.setCashType(CashType.bank_fast_pay.code());
			
			reqVo.setPointOrgNo(null);
		} else if ("8".equals(reqVo.getPayType().toString())) {
			reqVo.setPayType(Integer.parseInt(PaymentMethod.cash.getCode()));
			reqVo.setCashType(CashType.foil_card.code());
			
			reqVo.setPointOrgNo(null);
			checkPassword = false;
		} else {
			LogCvt.debug("payType is illegal value. [payType=" + reqVo.getPayType() + "]");
			return errorResVo;
		}
		
		if (checkPassword) {
			if (StringUtils.isEmpty(reqVo.getPayPwd()) || StringUtils.isEmpty(reqVo.getSource())) {
				return errorResVo;
			}
		}
		
		// complex required
		// ====== 从PayType维度校验 ======
		// 纯积分
		if (PaymentMethod.froadPoints.getCode().equals(reqVo.getPayType().toString()) 
				|| PaymentMethod.bankPoints.getCode().equals(reqVo.getPayType().toString())) {
			
			if (StringUtils.isEmpty(reqVo.getPointOrgNo())) {
				return errorResVo;
			} else if (reqVo.getCashType() != null) {
				return errorResVo;
			}
		} 
		// 积分+现金
		else if (PaymentMethod.froadPointsAndCash.getCode().equals(reqVo.getPayType().toString()) 
				|| PaymentMethod.bankPointsAndCash.getCode().equals(reqVo.getPayType().toString())) {
			
			if (StringUtils.isEmpty(reqVo.getPointOrgNo()) 
					|| StringUtils.isEmpty(reqVo.getCashOrgNo())) {
				return errorResVo;
			} else if (CashType.foil_card.code() != reqVo.getCashType().intValue()
					&& CashType.bank_fast_pay.code() != reqVo.getCashType().intValue()) {
				return errorResVo;
			}
		}
		
		// ====== 从CashType维度校验 ======
		if (reqVo.getCashType() == null) {
			if (PaymentMethod.cash.getCode().equals(reqVo.getPayType().toString())) {
				return errorResVo;
			}
		} else if (CashType.foil_card.code() == reqVo.getCashType().intValue()) {
			if (StringUtils.isEmpty(reqVo.getFoilCardNum())) {
				return errorResVo;
			}
		} else if (CashType.bank_fast_pay.code() == reqVo.getCashType().intValue()) {
			if (StringUtils.isEmpty(reqVo.getCashOrgNo())) {
				return errorResVo;
			}
		} else {
			LogCvt.debug("cashType is illegal value. [cashType=" + reqVo.getCashType()+ "]");
			return errorResVo;
		}
		
		return new ResponseVo(H5ResultCode.success.getCode(), H5ResultCode.success.getMsg());
	}
	
}
