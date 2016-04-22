package com.froad.cbank.coremodule.module.normal.user.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.enums.CreateSource;
import com.froad.cbank.coremodule.module.normal.user.enums.TerminalType;
import com.froad.thrift.vo.SmsTypeEnum;
import com.pay.user.helper.CreateChannel;

/**
 * @Description: 简单方法工具
 * @Author: liaolibiao@f-road.com.cn
 * @Date: 2015年9月17日 下午4:20:49
 */
public class SimpleUtils {

	
	public static CreateSource codeToCreateSource(String code) {
        for (CreateSource cs : CreateSource.values()) {
            if (cs.getCode().equals(code)) {
                return cs;
            }
        }
        return null;
    }
	
	public static CreateChannel valueToCreateChannel(String value) {
        for (CreateChannel cc : CreateChannel.values()) {
            if (cc.getValue().equals(value)) {
                return cc;
            }
        }
        return null;
    }
	
	public static SmsTypeEnum valueToSmsType(int value){
		for (SmsTypeEnum st : SmsTypeEnum.values()) {
            if (st.getValue() == value) {
                return st;
            }
        }
        return null;
	}
	
	/**
	 * 
	 * @Description: 从UserAgent获取请求终端类型
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月14日 下午3:34:26
	 * @param req
	 * @return
	 */
	public static TerminalType getTerminalTypeByRequest(HttpServletRequest req){
		String userAgent = req.getHeader("User-Agent");

		Pattern p = Pattern.compile(".*(iPhone).*");
		Matcher m = p.matcher(userAgent);
		if (m.find()) {
			return TerminalType.iphone;
		}
		
		p = Pattern.compile(".*(Android).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			return TerminalType.android;
		}
		
		p = Pattern.compile(".*(Windows NT|iPad).*");
		m = p.matcher(userAgent);
		if (m.find()) {
			return TerminalType.pc;
		}
		
		return null;
		
	}
	
	/**
	 * 
	 * @Description: 解密密码
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月17日 下午4:12:44
	 * @param ciphertextPwd
	 * @param createSource
	 * @return
	 */
	public static String decodePwd(String ciphertextPwd, CreateSource createSource) {
        String payPwd = null;
        // 根据不同渠道解密
        try {
            if (CreateSource.pc.equals(createSource) || createSource == null) {
                LogCvt.info("[密码解密] >> 渠道：PC，不解密");
                payPwd = ciphertextPwd;
            } else {
                LogCvt.info("[密码解密] >> 渠道：客户端，使用统一解密方式");
                payPwd = PasswordProcessor.decrypt(ciphertextPwd);
            }
        } catch (Exception e) {
            LogCvt.error("[密码解密] >> 解密出现异常 " + createSource.toString(), e);
        }

        if (payPwd == null) {
            LogCvt.error("[密码解密] >> 解密失败，解密内容为null");
        }
        return payPwd;
    }
	
	
	
	
	/**
	 * 
	 * @Description: 解密密码
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年9月17日 下午4:12:44
	 * @param ciphertextPwd
	 * @param createChannel
	 * @return
	 */
	public static String decodePwd(String ciphertextPwd, CreateChannel createChannel) {
        String payPwd = null;
        // 根据不同渠道解密
        try {
            if (CreateChannel.CB_P.equals(createChannel) || createChannel == null) {
                LogCvt.info("[密码解密] >> 渠道：PC，不解密");
                payPwd = ciphertextPwd;
            } else {
                LogCvt.info("[密码解密] >> 渠道：客户端，使用统一解密方式");
                payPwd = PasswordProcessor.decrypt(ciphertextPwd);
            }
        } catch (Exception e) {
            LogCvt.error("[密码解密] >> 解密出现异常 " + createChannel.toString(), e);
        }

        if (payPwd == null) {
            LogCvt.error("[密码解密] >> 解密失败，解密内容为null");
        }
        return payPwd;
    }
}
