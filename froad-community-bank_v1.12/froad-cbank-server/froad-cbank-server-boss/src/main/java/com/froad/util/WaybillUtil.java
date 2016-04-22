/**
 * Project Name:froad-cbank-server-boss-1.8.0-SNAPSHOT
 * File Name:WaybillUtil.java
 * Package Name:com.froad.util
 * Date:2015年12月8日下午4:21:45
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.util;

import java.util.regex.Pattern;

import com.froad.enums.DeliveryCorpCodeEnum;

/**
 * ClassName:WaybillUtil
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月8日 下午4:21:45
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public class WaybillUtil {
	
	//顺丰快递	由12位数字组成，目前常见以电话区号后三位开头
	private static final String sfRegex = "\\d{12}";
	
	//圆通快递	由10 / 12 / 18位字母数字组成，目前常见以数字或字母等开头，后面9位是数字
	private static final String ytRegex = "([0-9a-zA-Z]{10})|([0-9a-zA-Z]{12})|([0-9a-zA-Z]{18})";
	
	//中通快递	由12位数字组成，目前常见以2010**、6**、7**、010、2008**等开头
	private static final String ztRegex = "\\d{12}";
	
	//申通快递	申通单号由12位数字组成，常见以268**、368**、58**等开头
//	private static final String stRegex = "\\d{12}";
	
	//韵达速递	由13位数字组成，目前常见以10*、12*、19*等开头
	private static final String ydRegex = "\\d{13}";
	
	//天天快递	由14位或12位数字组成
	private static final String ttRegex = "(\\d{12})|(\\d{14})";
	
	//百世汇通	百世汇通单号由12-14位数字字母组成，常见以50*开头
	private static final String bshtRegex = "[a-zA-Z0-9]{12,14}";
	
	//国通快递	由10位数字组成，目前常见以2*…开头为单号
	private static final String gtRegex = "\\d{10}";
	
	//宅急送	由10位数字组成，目前常见以7**、6**、5**等开头
	private static final String zjsRegex = "\\d{10}";
	
	//全峰快递	由10位或12位纯数字组成
	private static final String qfRegex = "(\\d{10})|(\\d{12})";
	
	//EMS	由13位字母和数字组成，一般开头和结尾二位是字母，中间是数字
	private static final String emsRegex = "[0-9a-zA-Z]{13}$";
	
	//中国邮政	KA开头为普通快包，SA开头为挂号印刷品，XA/XB/XC/XN/SB等开头为挂号信函，PA开头为普通包裹
//	private static final String zgyzRegex = "^(KA|SA|XA|XB|XC|XN|SB|PA)[0-9a-zA-Z]{11}";
	
	//速尔快递	由12位数字组成的
//	private static final String seRegex = "\\d{12}";
	
	//德邦	由8或9位纯数字组成
	private static final String dbRegex = "\\d{8,9}";
	
	//快捷快递	由12或13位数字组成
	private static final String kjRegex = "\\d{12,13}";
	
	//全一快递	旧的为9位数字，被DHL合并后的中外运全一为12位数字
	private static final String qyRegex = "(\\d{9})|(\\d{12})";
	
	//优速快递	由12位数字组成
	private static final String ysRegex = "\\d{12}";
	
	//佳吉快运	由9位数字组成
	private static final String jjRegex = "\\d{9}";
	
	//天地华宇物流	常见邮8位数字组成
	private static final String hyRegex = "\\d{8}";
	
	//恒路物流	一般由数字构成
	private static final String hlRegex = "\\d+";
	
	public static Boolean verifyShippingId(DeliveryCorpCodeEnum corpCode, String shippingId){
		boolean flag = false;
		
		if(Checker.isEmpty(corpCode) || Checker.isEmpty(shippingId)){
			return flag;
		}
		
		switch (corpCode) {
		case ZJS:
			flag = Pattern.matches(zjsRegex, shippingId);
			break;
		case EMS:
			flag = Pattern.matches(emsRegex, shippingId);
			break;
		case SFSY:
			flag = Pattern.matches(sfRegex, shippingId);
			break;
		case TTKD:
			flag = Pattern.matches(ttRegex, shippingId);
			break;
		case YTSD:
			flag = Pattern.matches(ytRegex, shippingId);
			break;
		case DB:
			flag = Pattern.matches(dbRegex, shippingId);
			break;
		case ZTKD:
			flag = Pattern.matches(ztRegex, shippingId);
			break;
		case BSHT:
			flag = Pattern.matches(bshtRegex, shippingId);
			break;
		case YUNDAYDSD:
			flag = Pattern.matches(ydRegex, shippingId);
			break;
		case KJKD:
			flag = Pattern.matches(kjRegex, shippingId);
			break;
		case QFKD:
			flag = Pattern.matches(qfRegex, shippingId);
			break;
		case QYKD:
			flag = Pattern.matches(qyRegex, shippingId);
			break;
		case YSKD:
			flag = Pattern.matches(ysRegex, shippingId);
			break;
		case GTKD:
			flag = Pattern.matches(gtRegex, shippingId);
			break;
		case JJKY:
			flag = Pattern.matches(jjRegex, shippingId);
			break;
		case TDHYWL:
			flag = Pattern.matches(hyRegex, shippingId);
			break;
		case HLWL:
//			flag = Pattern.matches(hlRegex, shippingId);
//			break;
		case LBSD:
		case ZGYZ:
//		case QCTWL:
		case BSWL:
//		case SHFXWLYXGS:
//		case SHQXWLYXGS:
//		case SHJXWL:
//		case YWWL:
//		case SBWL:
		case ANWL:
//		case JFWLYXGS:
//		case SHQLWLYXGS:
//		case SHHYWLYXGS:
			flag = true;
			break;
		default:
			break;
		}
		
		return flag;
	}
	
	
	public static void main(String[] args) {
		DeliveryCorpCodeEnum corp = DeliveryCorpCodeEnum.YTSD;
		String id = "12345678999999991111";
		System.out.println(id.length());
		System.out.println(verifyShippingId(corp, id));
	}
	
	
}
