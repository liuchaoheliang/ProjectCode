package com.froad.CB.service;

import javax.jws.WebService;

import com.froad.CB.common.StickerCareBean;

/** 
 * @author FQ 
 * @date 2013-4-3 下午08:48:02
 * @version 1.0
 * 贴膜卡
 */
@WebService
public interface StickerCardService {
	
	/**
	 * 导入贴膜卡
	 * @param stickerCardBean
	 * @return
	 */
	public boolean addStickerCard(StickerCareBean stickerCardBean);
}
