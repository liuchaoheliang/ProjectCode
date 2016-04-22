package com.froad.fft.support.base;

import java.awt.image.BufferedImage;

import com.froad.fft.bean.SystemConfig.CaptchaType;

/**
 * 验证码
 * @author FQ
 *
 */
public interface CaptchaSupport {

	
	/**
	 * 生成验证码图片
	 * 
	 * @param captchaId 验证ID
	 * @return 验证码图片
	 */
	public BufferedImage buildImage(String captchaId) ;

	/**
	 * 验证码验证
	 * 
	 * @param captchaType 验证码类型
	 * @param captchaId 验证ID
	 * @param captcha 验证码(忽略大小写)
	 * @return 验证码验证是否通过
	 */
	public boolean isValid(CaptchaType captchaType, String captchaId, String captcha);
}
