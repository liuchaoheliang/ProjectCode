/**
 * Project Name:froad-cbank-server-common-1.8.0-SNAPSHOT
 * File Name:DeliveryCorpCodeEnum.java
 * Package Name:com.froad.enums
 * Date:2015年12月18日上午9:44:08
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.enums;

import com.froad.util.Checker;

/**
 * 物流公司code
 * ClassName:DeliveryCorpCodeEnum
 * Reason:	 TODO ADD REASON.
 * Date:     2015年12月18日 上午9:44:08
 * @author   huangyihao
 * @version  
 * @see 	 
 */
public enum DeliveryCorpCodeEnum {

	ZJS("zhaijisong", "宅急送", "宅急送"),
	EMS("ems", "EMS", "EMS"),
	SFSY("shunfeng", "顺丰", "顺丰速运"),
	TTKD("tiantian", "天天", "天天快递"),
	YTSD("yuantong", "圆通", "圆通速递"),
	DB("debangwuliu", "德邦", "德邦物流"),
	ZTKD("zhongtong", "中通", "中通快递"),
	BSHT("huitongkuaidi", "汇通", "百世汇通"),
	YUNDAYDSD("yunda", "韵达", "韵达快递"),
	KJKD("kuaijiesudi", "快捷", "快捷快递"),
	LBSD("longbanwuliu", "龙邦", "龙邦速递"),
	QFKD("quanfengkuaidi", "全峰", "全峰快递"),
	ZGYZ("youzhengguonei", "邮政", "中国邮政"),
	QYKD("quanyikuaidi", "全一", "全一快递"),
	YSKD("youshuwuliu", "优速", "优速快递"),
	GTKD("guotongkuaidi", "国通", "国通快递"),
	JJKY("jiajiwuliu", "佳吉", "佳吉快运"),
	TDHYWL("tiandihuayu", "华宇", "天地华宇"),
//	QCTWL("QCTWL", "全城通", "全城通物流"),
	BSWL("baishiwuliu", "百世物流", "百世物流"),
//	SHFXWLYXGS("SHFXWLYXGS", "峰昕", "上海峰昕物流有限公司"),
//	SHQXWLYXGS("SHQXWLYXGS", "清喜", "上海清喜物流有限公司"),
//	SHJXWL("SHJXWL", "锦雄", "上海锦雄物流"),
//	YWWL("YWWL", "辕威", "辕威物流"),
//	SBWL("SBWL", "上邦", "上邦物流"),
	ANWL("annengwuliu", "安能","安能物流"),
//	JFWLYXGS("JFWLYXGS", "尖峰","尖峰物流有限公司"),
//	SHQLWLYXGS("SHQLWLYXGS", "庆朗","上海庆朗物流有限公司"),
//	SHHYWLYXGS("SHHYWLYXGS", "惠盈","上海惠盈物流有限公司"),
	HLWL("hengluwuliu", "恒路","恒路物流"),
	STKD("shentong", "申通", "申通快递"),
	;
	
	private DeliveryCorpCodeEnum(String code, String name, String fullName){
		this.code = code;
		this.name = name;
		this.fullName = fullName;
	}
	
	private String code;
	
	private String name;
	
	private String fullName;

	public String getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public String getFullName() {
		return fullName;
	}

	public static DeliveryCorpCodeEnum getByCode(String code){
		if(Checker.isNotEmpty(code)){
			for(DeliveryCorpCodeEnum corp : values()){
				if(corp.getCode().equals(code)){
					return corp;
				}
			}
		}
		return null;
	}
	
	
	public static DeliveryCorpCodeEnum getByName(String name){
		if(Checker.isNotEmpty(name)){
			for(DeliveryCorpCodeEnum corp : values()){
				if(name.contains(corp.getFullName())){
					return corp;
				}
			}
		}
		return null;
	}
	
}
