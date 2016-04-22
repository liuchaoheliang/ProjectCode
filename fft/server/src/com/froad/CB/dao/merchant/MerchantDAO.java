
package com.froad.CB.dao.merchant;
import java.sql.SQLException;
import java.util.List;

import com.froad.CB.common.PreferType;
import com.froad.CB.po.merchant.Merchant;

public interface MerchantDAO {
	
    /**
      * 方法描述：添加商户信息
      * @param: merchant
      * @return: 主键编号
      * @version: 1.0
      */
    Integer insert(Merchant record) throws Exception ;

    
    /**
     * 方法描述：按主键更新商户编号
     * @param: merchant
     * @return: 主键编号
     * @version: 1.0
     */
    int updateById(Merchant record) throws Exception;
    
    
    /**
	 * 方法描述：按主键查询商户信息
	 * 
	 * @param: 主键编号
	 * @return: Merchant
	 * @version: 1.0
	 */
    Merchant selectByPrimaryKey(Integer id) ;

    
    /**
     * 方法描述：按主键删除商户信息
     * @param: 主键编号
     * @return: 
     * @version: 1.0
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    
    /**
     * 方法描述：查询商户列表
     * @param: 查询条件
     * @return: List<Merchant>
     * @version: 1.0
     */
    List<Merchant> select(Merchant queryCon)throws SQLException;
    
    
    /**
      * 方法描述：按注册时间段查询商户列表
      * @param: beginTime 起始时间
      * @param：endTime 结束时间
      * @return: List<Merchant>
      * @version: 1.0
      * @author: 李金魁 lijinkui@f-road.com.cn
      * @time: Dec 13, 2012 3:34:09 PM
      */
    List<Merchant> selectByRegTime(String beginTime,String endTime)throws SQLException;
    
    
    /**
      * 方法描述：查询商户编号
      * @param: userId
      * @return: 商户编号
      * @version: 1.0
      * @author: 李金魁 lijinkui@f-road.com.cn
      * @throws Exception 
      * @time: Oct 30, 2012 4:02:46 PM
      */
    Integer getMerchantIdByUserId(String userId);
    
    
    /**
      * 方法描述：查询商户信息
      * @param: userId 用户编号
      * @return: List<Merchant>
      * @version: 1.0
      * @author: 李金魁 lijinkui@f-road.com.cn
      * @time: Dec 13, 2012 3:35:34 PM
      */
    List<Merchant> getMerchantByUserId(String userId);
    
    
    /**
	 * 描述：可进行标签ID查询商户信息
	 * @param 
	 * @return List<Merchant>
	 */
	public List<Merchant> getMerchantListByTagId(Merchant mercahnt);
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
	  * 方法描述：多条件分页查询商户信息
	  * @param: Merchant
	  * @return: Merchant
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Jan 10, 2013 1:17:46 PM
	  */
	public Merchant getMerchantByPager(Merchant merchant);
	
	
	/**
	  * 方法描述：按商户编号更新商户审核人员，商户状态
	  * @param: merchant(id,auditStaff,state)
	  * @return: 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 20, 2012 2:25:14 PM
	  */
	public void auditMerchant(Merchant merchant)throws SQLException;
	
	/**
	  * 方法描述：按商户编号更新商户复核人员，商户状态
	  * @param: merchant(id,mReviewStaff,state)
	  * @return: 
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 20, 2012 2:25:14 PM
	  */
	public void reviewMerchant(Merchant merchant)throws SQLException;
	
	/**
	  * 方法描述：查询所有商户
	  * @return: List<Merchant>
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Dec 28, 2012 1:41:41 PM
	  */
	public List<Merchant> getAllMerchant(Merchant merchant);
	
	
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
	  * 方法描述：查询非第三类商户(PreferentialType 为1、2)
	  * @return: List<Merchant>
	  * @version: 1.0
	  * @author: 刘超
	  * @time: Jun 9, 2013 11:31:43 AM
	  */
	public Merchant getMerchantsPreferentialType(Merchant merchant);
}