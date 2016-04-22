package com.froad.logic;

import java.util.List;
import java.util.Map;

import com.froad.enums.OrgLevelEnum;
import com.froad.po.Area;
import com.froad.po.BankOperateLog;
import com.froad.po.BankOperator;
import com.froad.po.Client;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.MerchantAccount;
import com.froad.po.Org;
import com.froad.po.Outlet;
import com.froad.po.Product;
import com.froad.po.ProductCategory;
import com.froad.po.ProductOutletInfo;

public interface CommonLogic {
	
	/**
	 * 
	 * @Title: getSuperOrgByMerchantId 
	 * @Description: 根据商户ID获取1-2-3-4级机构号
	 * @author: froad-huangyihao 2015年4月10日
	 * @modify: froad-huangyihao 2015年4月10日
	 * @param merchantId
	 * @param clientId
	 * @return	
	 * @throws
	 */
	public abstract Map<OrgLevelEnum, String> getSuperOrgByMerchantId(String clientId,String merchantId);
	
	/**
	 * 
	 * @Title: getOrgLevelByOrgCode 
	 * @Description: 根据机构号返回该机构等级
	 * @author: froad-huangyihao 2015年4月10日
	 * @modify: froad-huangyihao 2015年4月10日
	 * @param orgCode
	 * @param clientId
	 * @return	机构等级
	 * @throws
	 */
	public abstract OrgLevelEnum getOrgLevelByOrgCode(String orgCode, String clientId);
	
	
	/**
	 * 设置map值,机构级别与orgCode的关系
	 * @param Org对象
	 * @return
	 */
	public abstract Map<OrgLevelEnum, String> setOrgMap(Org resultOrg);
		
	
	/**
	 * 查询有效机构对象(过滤无效的机构)
	 * 
	 * @param orgCode
	 * @param clientId
	 * @return
	 */
	public abstract Org getOrgByOrgCode(String orgCode, String clientId);
	
	/**
	 *  查询机构对象(未过滤无效的机构)
	  * @Title: queryByOrgCode
	  * @Description: TODO
	  * @author: share 2015年4月1日
	  * @modify: share 2015年4月1日
	  * @param @param clientId
	  * @param @param orgCode
	  * @param @return    
	  * @return Org    
	  * @throws
	 */
	public abstract Org queryByOrgCode(String clientId,String orgCode);
	
	
	/**
	 * 获取上级(多级别)机构对象
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @return 上级Org对象集合
	 */
	public abstract List<Org> getSuperOrgList(String clientId, String orgCode);
	
	/**
	 *  根据附件机构号查询子机构
	 * @param clientId
	 * @param orgCode
	 * @return
	 */
	public abstract List<Org> queryByParentOrgCode(String clientId, String orgCode);
	
	/**
	 *  获取最下面层机构集合
	 * @param clientId
	 * @param orgCode
	 * @return
	 */
	public abstract List<String> queryLastOrgCode(String clientId, List<String> orgCode);
	
	/**
     * 根据orgCode集合获取机构对象(循环内部处理)
     * @param clientId 客户端编号
     * @param orgCodes 机构编号集合
     * @return list<OrgVo> 返回机构对象集合
     */
	public abstract List<Org> getOrgByList(String clientId, List<String> orgCodes);
	
	
	/**
	 * 根据商户号获取商户信息
	 * @param clientId
	 * @param merchantId
	 * @return
	 */
	public abstract Map<String,String> getMerchantRedis(String clientId,String merchantId);
	
	/**
	 * 根据供应商Id获取供应商信息
	 * @param merchantId
	 * @return
	 *
	 */
	public Map<String, String> getProviderRedis(String merchantId) ;
	
	/**
	 * 根据商品ID获取商品
	 * @param clientId
	 * @param merchantId
	 * @param productId
	 * @return
	 */
	public abstract Map<String,String> getProductRedis(String clientId,String merchantId,String productId);
	
	/**
     * 根据商品ID获取商品库存
     * @param clientId
     * @param merchantId
     * @param productId
     * @return
     */
    public abstract int getStoreRedis(String clientId,String merchantId,String productId);
    
    
    /**
     * 获取秒杀商品库存
     * @param clientId
     * @param productId
     * @return
     */
    public abstract int getSeckillStoreRedis(String clientId,String productId);
    
    /**
     * 根据商品ID获取商品最大门店提货数
     * @param clientId
     * @param merchantId
     * @param productId
     * @return
     */
    public abstract int getProductPresellMaxRedis(String clientId,String merchantId,String productId);
	
    
    /**
     * 修改商品表中商户名字
     * @Title: updateProduct_MerchantNameByMerchantId 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param merchantId
     * @param merchantName
     * @return
     * @return Boolean    返回类型 
     * @throws
     */
    public Boolean updateProduct_MerchantNameByMerchantId (String merchantId, String merchantName);
    
	/**
	 *  根据商户ID和门店ID获取机构信息
	  * @Title: queryByOrg
	  * @Description: TODO
	  * @author: share 2015年4月1日
	  * @modify: share 2015年4月1日
	  * @param @param merchantId
	  * @param @param outletId
	  * @param @return    
	  * @return Org    
	  * @throws
	 */
	public abstract Org queryByOutleId(String clientId,String outletId);
	
	/**
	 *  按merchantId查询Org机构对象
	  * @Title: queryByMerchantInfo
	  * @Description: TODO
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @return    
	  * @return Org    
	  * @throws
	 */
	public abstract Org queryByMerchantInfo(String clientId,String merchantId);
	
	
	
	/**
	 * 按userId查询BankOperator银行用户对象
	 * @param clientId 
	 * @param userId 
	 * @return
	 */
	public abstract BankOperator getBankOperatorById(String clientId,Long userId);
	
	
	/**
     * 增加 BankOperateLog用户操作日志
     * @param bankOperatorLog 
     * @return Long    主键ID
     */
	public Boolean addBankOperateLog(BankOperateLog bankOperatorLog) throws Exception;
	
	/**
	 * 根据机构号查询虚拟商户
	 * @param clientId
	 * @param orgCode
	 * @return Merchant
	 */
	public abstract Map<String,String> getBankMerchantRedis(String clientId,String orgCode);
	
	/** 
	 * 根据id获取地区
	 * @Title: findAreaById 
	 * @Description: 
	 * @author lf
	 * @param  id
	 * @param 
	 * @return Area
	 * @throws 
	 */
	public Area findAreaById(Long id);
	
	/**
	 * 获取上级机构对象
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @return 上级Org对象
	 */
	public Org getSuperOrg(String clientId,String orgCode);
	
	/**
	 * 获取VIP商品
	 * @param clientId
	 * @return
	 */
	public Map<String,String> getVipProductRedis(String clientId);
	
	/**
     * 获取客户端详情
     * @param clientId
     * @update ll 20150717 挪至到Common包
     * @return Client详情
     */
    public Client getClientById(String clientId);
	
    
	/**
     * 从Mysql中查询客户端支付渠道(内部用)
     * @param clientPaymentChannel
     * @return List<ClientPaymentChannel>    结果集合 
     */
	public List<ClientPaymentChannel> findClientPaymentChannel(ClientPaymentChannel clientPaymentChannel);
	
	
	/**
	 * 获取支付渠道详情
	 * @param clientId 客户端Id
	 * @param paymentChannelId 支付渠道Id
	 * @update ll 20150717 挪至到Common包
	 * @return 支付渠道对象
	 */
	public ClientPaymentChannel getClientPaymentChannelById(String clientId,String paymentChannelId);
	
	
	/**
	 * 获取该客户端下的支付渠道
	 * @param clientId 客户端Id
	 * @update ll 20150717 挪至到Common包
	 * @return 支付渠道集合
	 */
	public List<ClientPaymentChannel> getClientPaymentChannelByClientId(String clientId);

	
	
	/**
	 * 根据商品分类id查询分类信息
	 * @param clientId
	 * @param categoryId
	 * @return ProductCategory
	 */
    public ProductCategory findCategoryById(String clientId, Long categoryId);
    
    /**
     * 
     * get_cbbank_client_bank_outlet_redis:(根据客户端获取虚拟门店缓存,start=0,end=-1是查全部Map<OutletId,ProductOutletInfo>).
     *
     * @author wangyan
     * 2015-8-13 下午12:04:21
     * @param clientId
     * @param start
     * @param end -1是全部
     * @return
     *
     */
    public Map<String,ProductOutletInfo> get_cbbank_client_bank_outlet_redis(String clientId,long start,long end);
    
    /**
     * 
     * set_cbbank_client_bank_outlet_redis:(新增修改虚拟门店时调用).
     *
     * @author wangyan
     * 2015-8-13 下午1:26:42
     * @param clientId
     * @param outlet (id;outletId;outletName;address;areaId;orgCode;phone;这几个字段必须要传值)
     * @param flag '1'新加,'2'修改,'3'删除
     * @return
     *
     */
    public boolean set_cbbank_client_bank_outlet_redis(String clientId, ProductOutletInfo outlet, String flag);
    
    /**
	 * 根据商户id、门店id查询mysql获取商户账户信息.
	 * @param merchantId 商户id
	 * @param outletId 门店 
	 * @return MerchantAccount
	 */    
    public MerchantAccount getMerchantAccoutByMerchantIdOrOutletId(String merchantId, String outletId);   
    
    /**
	 * 根据商户id、门店id查询mysql获取门店信息列表.
	 * @param merchantId 商户id
	 * @param outletId 门店 
	 * @return List<Outlet>
	 */  
    public List<Outlet> getOutletListByMerchantIdOrOutletId(String merchantId,String outletId);

    /**
     *  根据商户ID获取商户或门店账号
     * @param clientId
     * @param merchantId
     * @param string
     * @return
     */
	public abstract MerchantAccount getMerchantAccoutByRedis(String clientId,String merchantId, String outletId); 
	
	/**
	 * 根据商品ID查询商品详情
	 * @param productId
	 * @return
	 */
	public abstract Product getProductById(String productId);
	
	/**
	 *  根据商户ID获取商户和分类Redis
	 * @param clientId
	 * @param merchantId
	 * @return
	 */
	public abstract Map<String,String> getMerchantAndCategoryRedis(String clientId,String merchantId);
}