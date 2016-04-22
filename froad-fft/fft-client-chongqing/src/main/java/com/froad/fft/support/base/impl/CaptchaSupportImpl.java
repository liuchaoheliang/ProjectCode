package com.froad.fft.support.base.impl;

import java.awt.image.BufferedImage;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.SystemConfig.CaptchaType;
import com.froad.fft.support.base.CaptchaSupport;

@Service
public class CaptchaSupportImpl extends BaseSupportImpl implements CaptchaSupport {

	final static Logger logger = Logger.getLogger(CaptchaSupportImpl.class);

	@Resource(name = "imageCaptchaService")
	private com.octo.captcha.service.CaptchaService imageCaptchaService;
	
	@Override
	public BufferedImage buildImage(String captchaId) throws FroadException {
		return (BufferedImage) imageCaptchaService.getChallengeForID(captchaId);
	}

	@Override
	public boolean isValid(CaptchaType captchaType, String captchaId,
			String captcha) {
		if (StringUtils.isNotEmpty(captchaId) && StringUtils.isNotEmpty(captcha)) {
			try {
				return imageCaptchaService.validateResponseForID(captchaId,captcha.toUpperCase());
			} catch (Exception e) {
				return false;
			}
		} else {
			return false;
		}
	}

}
