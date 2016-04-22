/*   
* Copyright © 2008 F-Road All Rights Reserved.
*  
* This software is the confidential and proprietary information of   
* Founder. You shall not disclose such Confidential Information   
* and shall use it only in accordance with the terms of the agreements   
* you entered into with Founder.   
*   
*/

/**
 * 
 * @Title: OutletLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.froad.common.beans.MerchantExportData;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.enums.OutletDisableStatusEnum;
import com.froad.exceptions.FroadServerException;
import com.froad.po.Outlet;
import com.froad.po.OutletPrefer;
import com.froad.po.Result;
import com.froad.po.mongo.OutletDetail;

/**
 * 
 * <p>@Title: OutletLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface OutletLogic {


    /**
     * 增加 Outlet
     * @param outlet
     * @return outletId 门店编号
     */
	public String addOutlet(Outlet outlet) throws FroadServerException, Exception;


	/**
     * 批量增加 Outlet
     * @param list<outlet>
     * @return list<Result>    结果集
     * 
     * @param outletList
     */
	public List<Result> addOutletByBatch(List<Outlet> outletList) throws FroadServerException, Exception;
	
	/**
	 * 删除 指定商户id下的Outlet
	 * @Title: deleteOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @throws FroadServerException, Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean deleteOutlet(String merchantId)  throws FroadServerException, Exception ;
	
	
    /**
     * 删除 Outlet
     * @Title: deleteOutlet 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param outlet
     * @param outletDisableStatusEnum
     * @return
     * @throws FroadServerException
     * @throws Exception
     * @return Boolean    返回类型 
     * @throws
     */
	public Boolean deleteOutlet(Outlet outlet, OutletDisableStatusEnum outletDisableStatusEnum) throws FroadServerException, Exception;
	
	/**
	 * 根据商户id删除门店
	 * @Title: deleteOutletByMerchantId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @param outletDisableStatusEnum
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean deleteOutletByMerchantId(String merchantId , OutletDisableStatusEnum outletDisableStatusEnum) throws FroadServerException, Exception;
	/**
	 * 根据商户id删除门店
	 * @Title: deleteOutletByMerchantId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean deleteOutletByMerchantId(String merchantId) throws FroadServerException, Exception;
	
	/**
	 * 根据商户id禁用门店
	 * @Title: disableOutletByMerchantId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean disableOutletByMerchantId(String merchantId) throws FroadServerException, Exception;
	
	/**
	 * 根据门店id禁用门店
	 * @Title: disableOutletByOutletId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean disableOutletByOutletId(String outletId) throws FroadServerException, Exception;
	
	/**
     * 物理删除 Outlet
     * @param outlet
     * @return Boolean    是否成功
     */
	public Boolean removeOutlet(String outletId) throws FroadServerException, Exception;


	/**
	 * 根据商户id启用门店
	 * @Title: enableOutletByMerchantId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean enableOutletByMerchantId(String merchantId) throws FroadServerException, Exception;
	
	/**
	 * 根据门店id启用门店
	 * @Title: enableOutletByOutletId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletId
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean enableOutletByOutletId(String outletId) throws FroadServerException, Exception;
	
	
    /**
     * 修改 Outlet
     * @param outlet
     * @return Boolean    是否成功
     */
	public Boolean updateOutlet(Outlet outlet) throws FroadServerException, Exception;



    /**
     * 查询 Outlet
     * @param outlet
     * @return List<Outlet>    结果集合 
     */
	public List<Outlet> findOutlet(Outlet outlet);

	/**
     * 统计 Outlet
     * @param outlet
     * @return Integer
     */
	public Integer countOutlet(Outlet outlet);
	
	/**
	 * 根据机构查询对应的虚拟门店
	 * @Title: findBankOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @return Outlet    返回类型 
	 * @throws
	 */
	public Outlet findBankOutlet(String clientId, String orgCode);
	
	/**   
	 * 查询预售商品提货网点  
	 * @param merchantIdList
	 * @return    
	 * @see com.froad.logic.OutletLogic#findBankOutlet(java.util.List)    
	 */	
	public List<Outlet> findBankOutlet(List<String> merchantIdList);
	
    /**
     * 分页查询 Outlet
     * @param page
     * @param outlet
     * @return Page<Outlet>    结果集合 
     */
	public Page<Outlet> findOutletByPage(Page<Outlet> page, Outlet outlet, LinkedHashMap<String, String> orderBy);
	
	
	/**
	 * 附近搜索门店(个人h5用到)
	 * @Title: findNearbyOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param mongoPage
	 * @param outletDetail
	 * @return
	 * @return List<OutletDetailSimpleInfo>    返回类型 
	 * @throws
	 */
	public MongoPage findNearbyOutlet(MongoPage mongoPage, OutletDetail outletDetail,double distance,int orderBy) ;
	
	
	/**
	 * 附近搜索门店(个人h5用到)
	 * @Title: findNearbyOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param mongoPage
	 * @param outletDetail
	 * @return
	 * @return List<OutletDetailSimpleInfo>    返回类型 
	 * @throws
	 */
	public MongoPage findNearbyOutletByPage(MongoPage mongoPage, OutletDetail outletDetail,double distance,int skip) ;

	/**
	 * 分页查询门店详情
	 * @Title: findOutletDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param mongoPage
	 * @param outlet
	 * @param distace
	 * @return
	 * @return Page<Outlet>    返回类型 
	 * @throws
	 */
	public MongoPage findOutletDetailByPage(MongoPage mongoPage, OutletDetail outletDetail) ;
	
	/**
	 * 分页查询最热门店详情(个人h5用到)
	 * @Title: findHottestOutletDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param mongoPage
	 * @param outletDetail
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public MongoPage  findHottestOutletDetailByPage (MongoPage mongoPage, OutletDetail outletDetail) ;
	
	 /**
     * 查询 OutletDetail
     * @param outlet
     * @return OutletVo    
     */
	public OutletDetail findOutletDetailByOutletId(String outletId);
	
	/**
	 * 查询门店缓存
	 * @Title: findOutletRedis 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param merchantId
	 * @param outletId
	 * @return
	 * @return Map<String,String>    返回类型 
	 * @throws
	 */
	public Map<String, String> findOutletRedis(String clientId, String merchantId, String outletId) ;
	
	/**
     * 查询 Outlet
     * @param outletId
     * @return Outlet 
     */
	public Outlet findOutletByOutletId(String outletId);
	
	/**
	 * 根据门店id集合查询详情
	 * @Title: findOutletDetailbyOutletIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletIdList
	 * @return
	 * @return List<OutletDetail>    返回类型 
	 * @throws
	 */
	public List<OutletDetail> findOutletDetailbyOutletIdList(List<String> outletIdList);
	
	/**
	 * 查询提货网点(预售用到)  
	 * @Title: findSubBankOutlet 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @return List<Outlet>    返回类型 
	 * @throws
	 */
	public List<Outlet> findSubBankOutlet(String clientId,String orgCode);
	
	
	
	/**
	 * 根据经纬度和商户ID查询门店信息列表和相对距离
	  * @Title: findOutletMongoInfoByPage
	  * @Description: TODO
	  * @author: zhangxiaohua 2015-6-10
	  * @modify: zhangxiaohua 2015-6-10
	  * @param @param merchantId
	  * @param @param start
	  * @param @param end
	  * @param @param longitude
	  * @param @param latitude
	  * @param @return    
	  * @return List<OutletMongoInfo>    
	  * @throws
	 */
	MongoPage findOutletMongoInfoByPage(MongoPage mongoPage,double longitude, double latitude, String merchantId) ;
	
	
	public boolean disableOutlet(Outlet outlet)throws Exception;
	
	/**
	 * 
	 * @Title: syncOultetInfo 
	 * @Description: TODO
	 * @author: Yaolong Liang 2015年10月20日
	 * @modify: Yaolong Liang 2015年10月20日
	 * @param outlet
	 * @param isSynSucc
	 * @param synType
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 * @return Result
	 * @throws
	 */
	public Result syncOultetInfo(Outlet outlet,String isSynSucc,int synType) throws FroadServerException, Exception;	
	
	/**
	 * 敏感词校验.
	 * @param o
	 * @throws Exception
	 */
	public void checkSensitiveWordByObject(Object o) throws Exception;	
	
	
	/**
	 * 门店列表信息导出.
	 * @param OutletPreferList
	 * @return
	 */
	public MerchantExportData exportOutletManage(List<OutletPrefer> OutletPreferList);
	
    /**
     * 门店惠付分页查询 Outlet
     * @param page
     * @param outletPrefer
     * @return Page<OutletPrefer>    结果集合 
     */
	public Page<OutletPrefer> findOutletPreferByPage(Page<OutletPrefer> page, OutletPrefer outletPrefer);
}