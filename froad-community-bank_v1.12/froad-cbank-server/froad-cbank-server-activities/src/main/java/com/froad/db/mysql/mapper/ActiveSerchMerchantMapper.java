package com.froad.db.mysql.mapper;

import java.util.List;

import com.froad.po.ActiveResultInfo;
import com.froad.po.ActiveSearchMerchantInfo;

public interface ActiveSerchMerchantMapper {
	 /**
	  * @Title: getActiveListByMerchantIdId
	  * @Description: 获取商户信息.
	  * @author: shenshaocheng 2015年11月6日
	  * @modify: shenshaocheng 2015年11月6日
	  * @param merchantIds 商户ID
	  * @return
	 */	
	public List<ActiveResultInfo> findActiveMerchantInfo(ActiveSearchMerchantInfo activeSearchMerchantInfo);
}
