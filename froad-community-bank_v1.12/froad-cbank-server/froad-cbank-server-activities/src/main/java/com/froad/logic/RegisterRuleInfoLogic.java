/**
 * @Title: RegisterRuleInfoLogic.java
 * @Package com.froad.logic
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月1日
 * @version V1.0
**/

package com.froad.logic;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.ResultBean;
import com.froad.po.RegisteredRuleInfo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.active.AddResultVo;
import com.froad.thrift.vo.active.ExportRegisteredRuleInfoInfoRes;
import com.froad.thrift.vo.active.FindPageRegisteredRuleInfoVoResultVo;

 /**
 * @ClassName: RegisterRuleInfoLogic
 * @Description: TODO
 * @author froad-Joker 2015年12月1日
 * @modify froad-Joker 2015年12月1日
 */

public interface RegisterRuleInfoLogic {
	
	 /**
	  * @Title: addRegisteredRuleInfo
	  * @Description: 
	  * @author: yefeifei 2015年11月26日
	  * @modify: yefeifei 2015年11月26日
	  * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	  * @param registeredRuleInfo 
	  * @return 返回 新增结果
	  * @throws TException
	  * @see com.froad.thrift.service.VouchersRuleInfoService.Iface#addVouchersRuleInfo(
	  * com.froad.thrift.vo.OriginVo, com.froad.thrift.vo.active.VouchersRuleInfoVo)
	  */	
	public AddResultVo addRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfo registeredRuleInfo);

	
	public ResultBean disableActiveRuleInfo(String clientId, String activeId, String operator);
	
	public RegisteredRuleInfo getRegisteredRuleInfoById(String clientId, String activeId);
	
	 /**
	  * @Title: updateRegisteredRuleInfo
	  * @Description: 更新注册活动规则
	  * @author: shenshaocheng 2015年12月2日
	  * @modify: shenshaocheng 2015年12月2日
	  * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
	  * @param registeredRuleInfoVo 规则活动信息
	  * @return 返回更新结果
	 */	
	public ResultVo updateRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfo registeredRuleInfo);
	
	 /**
	  * @Title: getRegisteredRuleInfoByPage
	  * @Description: 分页查询注册活动信息
	  * @author: shenshaocheng 2015年12月2日
	  * @modify: shenshaocheng 2015年12月2日
	  * @param pageVo 源对象信息(包含平台,操作ip,操作员id等...)
	  * @param registeredRuleInfoVo 注册活动条线信息
	  * @return 返回注册活动列表信息
	 */	
	public FindPageRegisteredRuleInfoVoResultVo getRegisteredRuleInfoByPage(
			PageVo pageVo, RegisteredRuleInfo registeredRuleInfo);
	
	 /**
	  * @Title: exportRegisteredRuleInfoInfoResUrl
	  * @Description: 下载注册活动交易明细
	  * @author: shenshaocheng 2015年12月2日
	  * @modify: shenshaocheng 2015年12月2日
	  * @param registeredRuleInfoVo 下载活动查询条件
	  * @return 返回导出excel路径
	 */	
	public ExportRegisteredRuleInfoInfoRes exportRegisteredRuleInfoInfoResUrl(
			RegisteredRuleInfo registeredRuleInfo);
}
