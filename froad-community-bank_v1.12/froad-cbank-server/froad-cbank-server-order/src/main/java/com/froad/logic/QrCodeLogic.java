package com.froad.logic;

import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.thrift.vo.QrCodeResponseVo;

public interface QrCodeLogic {
	/**
	 * 生成二维码
	 * 
	 * @param requestVo
	 * @return
	 */
	public QrCodeResponseVo generateQrCode(QrCodeRequestVo requestVo);
}
