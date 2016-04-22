package com.froad.CB.dao;

import java.util.List;
import com.froad.CB.po.MerchantUserSet;
import com.froad.CB.po.Suggestion;

public interface MerchantUserSetDao {

	
	/**
	  * 方法描述：添加商户会员组
	  * @param: MerchantUserSet
	  * @return: boolean
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 25, 2013 8:38:37 PM
	  */
	public Integer addMerchantUserSet(MerchantUserSet merchantUserSet);
	
	/**
	  * 方法描述：查询有效操作员
	  * @param: userId,loginName,beCode(userId和loginName必须有一个)
	  * @return: List<MerchantUserSet>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 6:04:09 PM
	  */
	public List<MerchantUserSet> getMerchantClerk(MerchantUserSet merchantUserSet);
	
	
	/**
	  * 方法描述：查询操作员最大工号
	  * @return: String
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Mar 5, 2013 6:04:09 PM
	  */
	public String getMaxClerkBeCode(MerchantUserSet merchantUserSet);
	
	/**
	  * 方法描述：修改操作员信息
	  * @param: MerchantUserSet
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 2:45:59 PM
	  */
	public Integer updateByUserIdAndBecode(MerchantUserSet merchantUserSet);
	
	/**
	  * 方法描述：修改操作员密码
	  * @param: MerchantUserSet
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 2:45:59 PM
	  */
	public Integer updatePwdByUserIdAndBecode(MerchantUserSet merchantUserSet);
	
	/**
	  * 方法描述：商户删除操作员(逻辑删除)
	  * @param: userId,beCode(两个必须)
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 7, 2013 2:46:18 PM
	  */
	public Integer deleteByUserIdAndBecode(MerchantUserSet merchantUserSet);
	
	/**
	 * 分页查询操作员信息
	 * @param MerchantUserSet
	 * @return MerchantUserSet
	 */
	public MerchantUserSet getMerchantUserSetListByPager(MerchantUserSet merchantUserSet);

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>通过sotreid查询belongUserBecode 返回格式  becode,becode..... 财务查询功能专用</b> --* </p>
	 *<p> 作者: 赵肖瑶 </p>
	 *<p> 时间: 2013-11-22 下午02:54:39 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	public String getBelongUserBecode(String storeId);
}
