package com.froad.jetty.service;

import com.froad.jetty.vo.PlaceOrderRequestVo;
import com.froad.jetty.vo.ResponseVo;
import com.pay.user.dto.UserSpecDto;

/**
 * 支撑逻辑层
 * 
 * @author wangzhangxu
 * @date 2015年4月18日 上午10:44:02
 * @version v1.0
 */
public interface SupportService {
	
	/**
	 * 验证登录用户的Token
	 * 
	 * @param memberCode 用户唯一标识
	 * @param token 登录安全令牌
	 * 
	 * @return ResponseVo 
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月20日 上午10:26:03
	 */
	public ResponseVo checkToken(Long memberCode, String token);	
	
	/**
	 * 验证短信验证码
	 * 
	 * @param clientId 客户端ID
	 * @param smsToken 短信验证码令牌
	 * @param smsCode 短信验证码
	 * 
	 * @return ResponseVo
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月17日 下午7:26:03
	 */
	public ResponseVo checkSmsCode(String clientId, String smsToken, String smsCode);
	
	/**
	 * 验证短信验证码
	 * 
	 * @param memberCode 用户唯一标识
	 * @param password 支付密码
	 * @param source 来源
	 * 
	 * @return ResponseVo
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月28日 上午10:26:03
	 */
	public ResponseVo checkPayPassword(Long memberCode, String password, String source);
	
	/**
	 * 获取用户信息
	 * 
	 * @param memberCode 用户唯一标识
	 * 
	 * @return UserSpecDto
	 * 
	 * @author wangzhangxu
	 * @date 2015年4月28日 上午10:26:03
	 */
	public UserSpecDto getUserInfo(Long memberCode);
	
	/**
	 * 计算下单的金额
	 * 
	 * @param reqVo 请求数据
	 * @param totalAmount 支付总金额
	 * @return ResponseVo
	 * 
	 * @author wangzhangxu
	 * @date 2015年5月08日 上午11:23:56
	 */
	public ResponseVo calculateAmount(PlaceOrderRequestVo reqVo, Double totalAmount);
	
	
}
