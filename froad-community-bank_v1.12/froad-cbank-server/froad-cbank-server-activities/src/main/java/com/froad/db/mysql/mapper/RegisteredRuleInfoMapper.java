/**
 * @Title: RegisteredRuleInfoMapper.java
 * @Package com.froad.db.mysql.mapper
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月2日
 * @version V1.0
 **/

package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ActiveBaseRule;
import com.froad.po.RegisteredExportDetail;
import com.froad.po.RegisteredInfo;
import com.froad.po.RegisteredRuleInfo;

/**
 * @ClassName: RegisteredRuleInfoMapper
 * @Description: TODO
 * @author froad-Joker 2015年12月2日
 * @modify froad-Joker 2015年12月2日
 */

public interface RegisteredRuleInfoMapper {
	/**
	 * @Title: findRegisteredRuleInfoList
	 * @Description: 查找红包券码
	 * @author: yefeifei 2015年11月28日
	 * @modify: yefeifei 2015年11月28日
	 * @param registeredRuleInfo
	 * @return
	 */
	public RegisteredRuleInfo findRegisteredRuleInfo(
			RegisteredRuleInfo registeredRuleInfo);

	/**
	 * @Title: findregisterRuleListByPage
	 * @Description: 分页查询注册活动信息
	 * @author: shenshaocheng 2015年12月2日
	 * @modify: shenshaocheng 2015年12月2日
	 * @param registeredRuleInfo
	 *            注册活动信息查询条件
	 * @return 返回分页信息
	 */
	public List<RegisteredInfo> findregisterRuleListByPage(Page page,
			@Param("activeBaseRule") ActiveBaseRule activeBaseRule);

	/**
	 * @Title: findRegisteredExportDetailInfo
	 * @Description: 查询注册活动交易明细（红包实物）
	 * @author: shenshaocheng 2015年12月2日
	 * @modify: shenshaocheng 2015年12月2日
	 * @param registeredRuleInfo
	 *            查询条件
	 * @return 返回注册活动交易明细
	 */
	public List<RegisteredExportDetail> findRegisteredExportDetailInfo(
			RegisteredRuleInfo registeredRuleInfo);
}
