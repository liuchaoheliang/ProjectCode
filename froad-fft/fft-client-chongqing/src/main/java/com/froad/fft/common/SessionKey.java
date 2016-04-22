package com.froad.fft.common;

/**
 * *******************************************************
 *<p> 工程: fft-client-chongqing </p>
 *<p> 类名: SessionKey.java </p>
 *<p> 描述: *-- <b>系统session Key总集，所有session Key 需要在这里申明</b> --* </p>
 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
 *<p> 时间: 2014年3月25日 下午10:33:57 </p>
 ********************************************************
 */
public enum SessionKey {

	//--------------------------------------user-----------------------------
	LOGIN_IDENTIFICATION("login_identification"),
		//登录标识，只要有任意角色用户登录系统则创建，用于判断用户是否登录的决定因素 如果登录了该KEY 所对应的VALUE 一定非 null
	VALIDATION_COMMON_TEMP_VAL("validation_common_temp_val"),
		//验证数据的临时存储信息（例如随机验证码，或者手机验证码）
	
	
	
	
	//--------------------------------------merchant------------------------
	MERCAHNT_OUTLET_LIST("merchant_outlet_list"),
		//登录商户的门店列表信息，超级管理员是加载所有门店，门店管理员只有该门店信息
	
	
	
	
	//--------------------------------------system--------------------------

	SYSTEM_TEMP_VAL("system_temp_val"),
		//用户临时存储需要的数据
	STSTEM_UUID_TOKEN_KEY("system_uuid_token_key"),
		//用于防止重复提交的表单
	
	//--------------------------------------sso-----------------------------
	SSO_MEMBER_INFO("sso_member_info");
		//SSO用户信息
	
	
	//system--------------------------------------
	private String value;
	private SessionKey(String value){
		this.value = value;
	}
	public String key(){
		return value;
	}
}
