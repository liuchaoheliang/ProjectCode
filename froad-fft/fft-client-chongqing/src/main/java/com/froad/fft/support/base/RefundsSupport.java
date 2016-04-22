package com.froad.fft.support.base;

import com.froad.fft.dto.RefundsDto;

/**
* @ClassName: RefundsSupport
* @Description: 退款
* @author larry
* @date 2014-4-15 下午06:33:42
*
 */
public interface RefundsSupport {
	
	/**
	*<p>申请退款</p>
	* @author larry
	* @datetime 2014-4-16上午09:20:11
	* @return RefundsDto
	* @throws 
	* @example<br/>
	*
	 */
	public RefundsDto applyRefund(RefundsDto refundsDto);
	
	/**
	*<p>查看交易是否申请退款</p>
	* @author larry
	* @datetime 2014-4-16上午09:20:08
	* @return Boolean
	* @throws 
	* @example<br/>
	*
	 */
	public RefundsDto selectRefunds(Long TransId);
}
