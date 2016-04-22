package com.froad.fft.support.base;

import java.util.List;

import com.froad.fft.dto.AdDto;

/**
 * 广告
 * 
 * @author FQ
 * 
 */
public interface AdSupport {

	/**
	 * 根据广告位 查询启用的广告
	 * @param adPositionId
	 * @return
	 */
	public List<AdDto> findEnableAdByAdPositionId(Long adPositionId);
}
