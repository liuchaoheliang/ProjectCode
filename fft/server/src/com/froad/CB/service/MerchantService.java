package com.froad.CB.service;

import java.util.List;

import javax.jws.WebService;

import com.froad.CB.AppException.AppException;
import com.froad.CB.common.PreferType;
import com.froad.CB.common.ReMerchantResultBean;
import com.froad.CB.po.MerchantUserSet;
import com.froad.CB.po.merchant.Merchant;
import com.froad.CB.po.merchant.MerchantPresent;
import com.froad.CB.po.merchant.MerchantTrain;
import com.froad.CB.po.tag.TagMAP;
import com.froad.CB.po.user.User;

/** 
 * @author FQ 
 * @date 2013-2-4 下午03:23:33
 * @version 1.0
 * 商户
 */
@WebService
public interface MerchantService {


	/**
	 * 方法描述：查询用户是否是商户
	 * 
	 * @param: userId
	 * @return: true:是商户 false:不是商户
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Oct 30, 2012 5:32:46 PM
	 */
	public boolean isMerchant(String userId);

	/**
	 * 方法描述：添加商户
	 * 
	 * @param: merchant
	 * @return: 商户编号
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Oct 30, 2012 5:53:58 PM
	 */
	public Integer addMerchant(Merchant merchant);

	/**
	 * 方法描述：查询商户信息
	 * 
	 * @param: userId
	 * @return: 商户信息
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Oct 30, 2012 5:55:24 PM
	 */
	public List<Merchant> getMerchantByUserId(String userId);


	/**
	 * 描述：根据标签ID(分类ID或者商圈ID)查询商户
	 * 
	 * @param
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByTagId(Merchant merchant);
	public Integer getMerchantListByTagIdCount(Merchant merchant);
	
	/**
	 * 描述：根据标签ID(分类AID和分类BID商圈bID)查询商户 
	 * @param 
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByClassAClassBDistrictBId(Merchant merchant);
	public Integer getMerchantListByClassAClassBDistrictBIdCount(Merchant merchant);
	
	/**
	 * 描述：根据(分类A标签id和分类B标签id和商圈A标签id商圈B标签id)查找商户
	 * @param 
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByAllTagId(Merchant merchant);
	public Integer getMerchantListByAllTagIdCount(Merchant merchant);
	
	/**
	 * 方法描述：首页商户搜索
	 * 
	 * @param: Merchant
	 * @return: List<Merchant>
	 * @version: 1.0
	 * @time: Oct 30, 2012 6:00:15 PM
	 */
	public Merchant getMerchantListIndex(Merchant merchant);

	/**
	 * 方法描述：根据条件进行搜索，显示商户列表，每一个商户有简短信息描述，包括地址、标签、优惠。
	 * 
	 * @param: tagMap
	 * @return: true:提交成功 false：提交失败
	 * @version: 1.0
	 * @time: Oct 30, 2012 6:00:15 PM
	 */
	public Merchant getMerchantList(Merchant merchant);

	public Merchant getMerchantTagAndPre(String merchantId);

	
	/**
	  * 方法描述：多条件查询商户
	  * @param: Merchant
	  * @return: List<Merchant>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 6, 2013 10:33:46 AM
	  */
	List<Merchant> select(Merchant queryCon);
 
	
	/**
	 * 方法描述：查询商户列表
	 * @param: beginTime
	 * @param: endTime
	 * @return: List<Merchant>
	 * @version: 1.0
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Dec 5, 2012 11:39:54 AM
	 */
	List<Merchant> selectByRegTime(String beginTime, String endTime);

	
	/**
	  * 方法描述：多条件分页查询商户信息
	  * @param: Merchant
	  * @return: Merchant
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 10, 2013 1:17:46 PM
	  */
	public Merchant getMerchantByPager(Merchant merchant);
	
	
	/**
	  * 方法描述：按商户编号查询商户
	  * @param: id
	  * @return: Merchant
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 10, 2013 2:17:23 PM
	  */
	public Merchant getMerchantById(Integer id);
	
	
	/**
	  * 方法描述：审核商户
	  * @param: merchant(id,auditStaff)
	  * @return: 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 20, 2012 2:58:15 PM
	  */
	public boolean auditMerchant(Merchant merchant);

	
	/**
	  * 方法描述：复核商户
	  * @param: merchant(id,mReviewStaff)
	  * @return: 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 20, 2012 2:58:15 PM
	  */
	public boolean reviewMerchant(Merchant merchant);
	
	
	/**
	  * 方法描述：查询所有商户
	  * @return: List<Merchant>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 28, 2012 1:41:41 PM
	  */
	public List<Merchant> getAllMerchant(Merchant merchant);
	
	
	/**
     * 方法描述：按主键更新商户编号
     * @param: merchant
     * @return: 主键编号
     * @version: 1.0
     */
	public int updateById(Merchant record);
	
	
	/**
	  * 方法描述：按优惠类型查询商户列表
	  * @param: PreferType
	  * @return: List<Merchant>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Feb 22, 2013 10:55:49 AM
	  */
	public List<Merchant> getMerchantByType(PreferType preferType);
	
	
	/**
	  * 方法描述：查询内部商户
	  * @return: List<Merchant>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jun 9, 2013 11:31:43 AM
	  */
	public List<Merchant> getInnerMerchant();
	
	
	/**
	 * 注册商户
	 * @param isSendSms			是否发短信
	 * @param user
	 * @param merchant
	 * @param merchantPresent
	 * @param merchantTrain
	 * @param tagMap
	 * @param merchantUserSet
	 * @return
	 * 		ReMerchantResultBean
			
		抛出异常为失败
	 */
	public ReMerchantResultBean addMerchantByMap(boolean isSendSms,User user,Merchant merchant,
			MerchantPresent merchantPresent,MerchantTrain merchantTrain,
			TagMAP tagMap,MerchantUserSet merchantUserSet)throws AppException;
	
	
	/**
	  * 方法描述：查询非第三类商户(PreferentialType 为1、2)
	  * @return: List<Merchant>
	  * @version: 1.0
	  * @author: 刘超
	  * @time: Jun 9, 2013 11:31:43 AM
	  */
	public Merchant getMerchantsPreferentialType(Merchant merchant);
}
