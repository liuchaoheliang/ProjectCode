/**
 * @Title: RegisterRuleInfoHandler.java
 * @Package com.froad.handler
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月1日
 * @version V1.0
 **/

package com.froad.handler;

import java.util.List;

import com.froad.db.mysql.bean.Page;
import com.froad.po.RegisteredExportDetail;
import com.froad.po.RegisteredRuleInfo;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;

/**
 * @ClassName: RegisterRuleInfoHandler
 * @Description: TODO
 * @author froad-Joker 2015年12月1日
 * @modify froad-Joker 2015年12月1日
 */

public interface RegisterRuleInfoHandler {
	public ResultVo addRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfo registeredRuleInfo);

	public RegisteredRuleInfo getRegisteredRuleInfoById(String clientId,
			String activeId);

	/**
	 * @Title: updateRegisteredRuleInfo
	 * @Description: 更新注册活动规则
	 * @author: shenshaocheng 2015年12月2日
	 * @modify: shenshaocheng 2015年12月2日
	 * @param originVo
	 *            源对象信息(包含平台,操作ip,操作员id等...)
	 * @param registeredRuleInfoVo
	 *            规则活动信息
	 * @return 返回更新结果
	 */
	public ResultVo updateRegisteredRuleInfo(OriginVo originVo,
			RegisteredRuleInfo registeredRuleInfo);

	/**
	 * @Title: getRegisteredRuleInfoByPage
	 * @Description: 分页查询注册活动信息
	 * @author: shenshaocheng 2015年12月2日
	 * @modify: shenshaocheng 2015年12月2日
	 * @param pageVo
	 *            分页信息
	 * @param registeredRuleInfo
	 *            分页信息查询条件
	 * @return 返回分页信息
	 */
	public List<RegisteredRuleInfo> getRegisteredRuleInfoByPage(Page pageVo,
			RegisteredRuleInfo registeredRuleInfo);

	/**
	 * @Title: getRegisteredExportDetail
	 * @Description: 获取注册活动交易明细（红包实物）
	 * @author: shenshaocheng 2015年12月2日
	 * @modify: shenshaocheng 2015年12月2日
	 * @param registeredRuleInfo
	 *            注册活动查询条件
	 * @return 返回注册活动交易明细
	 */
	public List<RegisteredExportDetail> getRegisteredExportDetail(
			RegisteredRuleInfo registeredRuleInfo);
}
