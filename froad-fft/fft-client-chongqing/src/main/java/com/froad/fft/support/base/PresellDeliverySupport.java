package com.froad.fft.support.base;

import com.froad.fft.dto.PresellDeliveryDto;

/**
* @ClassName: PresellDeliverySupport
* @Description: 提货点
* @author larry
* @date 2014-4-11 下午07:36:32
*
 */
public interface PresellDeliverySupport {
	
	/**
	*<p>根据ID查询提货点</p>
	* @author larry
	* @datetime 2014-4-11下午07:39:15
	* @return PresellDeliveryDto
	* @throws 
	* @example<br/>
	*
	 */
	public PresellDeliveryDto getById(Long id);
}
