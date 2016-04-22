/**
 * 商户业务逻辑接口Logic
 * @Title: MerchantLogic.java
 * @Package com.froad.logic
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic;

import java.util.List;
import java.util.Map;

import org.apache.thrift.TException;

import com.froad.common.beans.MerchantExportData;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.MerchantMapper;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.exceptions.FroadServerException;
import com.froad.po.Merchant;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.TypeInfo;

/**
 * 
 * <p>@Title: MerchantLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public interface MerchantLogic {


    /**
     * 增加 Merchant
     * @Title: addMerchant 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param merchant
     * @param categoryInfoList
     * @param typeInfoList
     * @return
     * @return String    商户编号 
     * @throws
     */
	public String addMerchant(Merchant merchant, List<CategoryInfo> categoryInfoList, List<TypeInfo> typeInfoList)  throws FroadServerException, Exception;

	/**
     * 批量增加 Merchant
     * @param list<merchant>
     * @return list<Result>    结果集
     * 
     * @param merchantList
     */
	public List<Result> addMerchantByBatch(List<Merchant> merchantList)  throws FroadServerException, Exception;

    /**
     * 删除 Merchant
     * @Title: deleteMerchant 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param merchant
     * @param disableStatus 禁用状态
     * @return
     * @throws FroadServerException, Exception
     * @return Boolean    返回类型 
     * @throws
     */
	public Boolean deleteMerchant(Merchant merchant,MerchantDisableStatusEnum disableStatus)  throws FroadServerException, Exception;

	/**
	 * 物理删除 Merchant
	 * @Title: removeMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @throws FroadServerException, Exception
	 * @return Boolean    返回类型 
	 * @throws
	 */
	public Boolean removeMerchant(String merchantId)  throws FroadServerException, Exception;

    /**
     * 修改 Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	public Boolean updateMerchant(Merchant merchant, List<CategoryInfo> categoryInfoList, List<TypeInfo> typeInfoList)  throws FroadServerException, Exception;

	/**
	 * 查询一个Merchant
	 * @Title: findOneMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return
	 * @return Merchant    返回类型 
	 * @throws
	 */
	public Merchant findOneMerchant (Merchant merchant);

    /**
     * 查询 Merchant
     * @param merchant
     * @return List<Merchant>    结果集合 
     */
	public List<Merchant> findMerchant(Merchant merchant);

	/**
     * 统计 Merchant
     * @param merchant
     * @return Integer
     */
	public Integer countMerchant(Merchant merchant) ;
	
	/**
	 * 查询单个商户
	 * @Title: findMerchantByMerchantId 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @return Merchant    返回类型 
	 * @throws
	 */
	public Merchant findMerchantByMerchantId(String merchantId);

    /**
     * 分页查询 Merchant
     * @param page
     * @param merchant
     * @return Page    结果集合 
     */
	public Page<Merchant> findMerchantByPage(Page<Merchant> page, Merchant merchant);
	
	
	
	/**
     * 商户列表查询报表导出分页查询 Merchant
     * @param page
     * @param merchant
     * @return Page    结果集合 
     */
	public Page<Merchant> findExportByPage(Page<Merchant> page, Merchant merchant);

	/**
	 * 查询商户详情
	 * @param merchat_id
	 * @return    
	 * @see com.froad.logic.MerchantLogic#findMerchatDetailByMerchantId(java.lang.Long)
	 */
	public MerchantDetail findMerchatDetailByMerchantId(String merchat_id);
	
	/**
	 * 分页查询商户详情
	 * @Title: findMerchantDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param mongoPage
	 * @param merchantDetail
	 * @return
	 * @return MongoPage    返回类型 
	 * @throws
	 */
	public MongoPage findMerchantDetailByPage(MongoPage mongoPage, MerchantDetail merchantDetail);
	
	/**
	 * 禁用指定id商户()
	 * @Title: disableMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @throws FroadServerException, Exception
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean disableMerchant(String merchantId)  throws FroadServerException, Exception ;
	
	/**     
	 * 禁用指定机构下的所有商户()
	 * @param client_id
	 * @param org_code
	 * @return
	 * @throws TException    
	 * @see com.froad.thrift.service.MerchantService.Iface#disableMerchant(java.lang.String)    
	 */	
	public boolean disableMerchant(String client_id,String org_code)  throws FroadServerException, Exception;
	
	/**
	 * 启用商户
	 * 
	 * @Title: enableMerchant
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchant
	 * @return
	 * @throws FroadServerException, Exception
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean enableMerchant(Merchant merchant) throws FroadServerException, Exception;

	/**
	 * 启用商户
	 * 
	 * @Title: enableMerchant
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @throws FroadServerException, Exception
	 * @return boolean 返回类型
	 * @throws
	 */
	public boolean enableMerchant(String merchantId) throws FroadServerException, Exception;
	
	/**
	 * 续约商户
	 * @Title: extensionMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @param contractEndtime
	 * @return
	 * @return boolean    返回类型 
	 * @throws
	 */
	public boolean extensionMerchant(String merchantId, Long contractEndtime) throws FroadServerException, Exception;
	
	/**
	 * 查询orgCodeList机构所有的预售网点
	 * @Title: findBankMerchant 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param clientId
	 * @param orgCodeList
	 * @return
	 * @return List<Merchant>    返回类型 
	 * @throws
	 */
	public List<Merchant> findBankMerchant(String clientId, List<String> orgCodeList);
	
	/**
	 * 根据商户id集合查询详情
	 * @Title: findMerchantDetailbyOutletIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param outletIdList
	 * @return
	 * @return List<MerchantDetail>    返回类型 
	 * @throws
	 */
	public List<MerchantDetail> findMerchantDetailbyMerchantIdList(List<String> merchantIdList);
	
	/**
	 * 根据商户id集合商户简称(key为商户id,value为商户名称)
	 * @Title: findMerchantNamebyMerchantIdList 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantIdList
	 * @return
	 * @return Map<String,String>    返回类型 
	 * @throws
	 */
	public Map<String, String> findMerchantNamebyMerchantIdList(String clientId,List<String> merchantIdList);
	
	
	/**
	 * 查询商户分类
	 * @Title: findMerchantCategoryInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @return List<CategoryInfo>    返回类型 
	 * @throws
	 */
	public List<CategoryInfo> findMerchantCategoryInfo(String merchantId);
	
	/**
	 * 查询商户类型
	 * @Title: findMerchantCategoryInfo 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param merchantId
	 * @return
	 * @return List<TypeInfo>    返回类型 
	 * @throws
	 */
	public List<TypeInfo> findMerchantTypeInfo(String merchantId);
	
	
	/**
     * 修改 审核通过后的商户Merchant
     * @param merchant
     * @return Boolean    是否成功
     */
	public Boolean updateMerchant(Merchant merchant)  throws FroadServerException, Exception;

	/**
	 * 商户列表信息导出.
	 * @param merchantList
	 * @return
	 */
	public MerchantExportData exportMerchantManage(List<Merchant> merchantList,Merchant tempMerchant);
	
	/**
	 * 同步更改商户状态、同步redis、mongo.
	 * @param merchantId 商户Id
	 * @param clientId  客户端Id
	 * @param isSynSucc true-同步成功，false-同步失败
	 * @param synType  0-同步通知，1-审核通知
	 * @return
	 * @throws FroadServerException
	 * @throws Exception
	 */
	public Boolean syncMerchantInfo(String merchantId,String clientId,String isSynSucc,int synType) throws FroadServerException, Exception;


	
	/**
	 * 同步白名单，内部同步方法.
	 * 
	 * @param merchantId
	 * @param clientId
	 * @param merchant
	 * @param merchantMapper
	 * @param isSynSuccess
	 * @return
	 * @throws TException
	 */
	public boolean synMerchantSynType(int auditTypeFlag,Merchant merchant,MerchantMapper merchantMapper,boolean isSynSuccess) throws FroadServerException, Exception;

}