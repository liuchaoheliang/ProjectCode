package com.froad.db.mysql.mapper;

import com.froad.po.QrCode;
import com.froad.thrift.vo.QrCodeRequestVo;

/**
 * @author F-road
 *
 */
public interface QrCodeMapper {
	/**
	 * 获取二维码记录
	 * 
	 * @param keyword
	 * @param clientId
	 * @return
	 */
	public QrCode getQrCode(QrCodeRequestVo qrcodeRequestVo);
	
	/**
	 * 创建新二维码记录
	 * 
	 * @param qrCode
	 */
	public void addQrCode(QrCode qrCode);
}
