package com.froad.fft.support.base;

import com.froad.fft.dto.AdPositionDto;

/**
 * 广告位
 * @author FQ
 *
 */
public interface AdPositionSupport {
	
	/**
	 * ID 查找
	 * @param id
	 * @return
	 */
	public AdPositionDto findAdPositionById(Long id);
}
