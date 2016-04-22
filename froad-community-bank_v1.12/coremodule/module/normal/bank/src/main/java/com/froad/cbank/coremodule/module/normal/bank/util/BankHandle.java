
package com.froad.cbank.coremodule.module.normal.bank.util;

import javax.servlet.http.HttpServletRequest;

import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.thrift.vo.BankOperatorVo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PlatType;

/**
 * 说明 description of the class 创建日期 2015年9月11日 上午11:15:20 作者 artPing *** 更新时间
 * 2015年9月11日 上午11:15:20 标签 SVN版本 最后更新者
 */
public class BankHandle {

	/**
	 * 
	 * 说明 公共获取请求路径信息
	 * 
	 * @param request
	 * @return
	 * 
	 * 		创建日期 2015年9月11日 上午11:22:27 作者 artPing
	 */
	public static OriginVo getOriginVo(HttpServletRequest request) {
		OriginVo vo = new OriginVo();
		String userId = request.getAttribute(Constants.USER_ID) + "";
		vo.setClientId(request.getAttribute(Constants.CLIENT_ID) + "");
		if (StringUtil.isNotEmpty(userId)) {
			vo.setOperatorId(Long.valueOf(userId));
		}
		vo.setOperatorIp(TargetObjectFormat.getIpAddr(request));
		vo.setPlatType(PlatType.bank);
		return vo;
	}

	public static OriginVo getOriginVo4FailsCount(HttpServletRequest request) {
		OriginVo vo = new OriginVo();
		vo.setClientId(request.getAttribute(Constants.CLIENT_ID) + "");
		vo.setOperatorIp(TargetObjectFormat.getIpAddr(request));
		vo.setPlatType(PlatType.bank);
		return vo;
	}

	/**
	 * 
	 * @Title: getOriginVo @Description: 获取操作对象 @param
	 *         bankOperatorVo @return @return OriginVo @throws
	 */
	public static OriginVo getOriginVo(BankOperatorVo bankOperatorVo) {
		OriginVo originVo = new OriginVo();
		originVo.setClientId(bankOperatorVo.getClientId());
		originVo.setOperatorId(bankOperatorVo.getId());
		// originVo.setOperatorIp(operatorIp);
		originVo.setPlatType(PlatType.bank);
		originVo.setOperatorUserName(bankOperatorVo.getUsername());
		originVo.setOrgId(bankOperatorVo.getOrgCode());
		return originVo;
	}
}
