package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.MongoService;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.AreaCommonMapper;
import com.froad.db.mysql.mapper.BankOperateLogCommonMapper;
import com.froad.db.mysql.mapper.BankOperatorCommonMapper;
import com.froad.db.mysql.mapper.ClientCommonMapper;
import com.froad.db.mysql.mapper.ClientPaymentChannelCommonMapper;
import com.froad.db.mysql.mapper.MerchantCommonMapper;
import com.froad.db.mysql.mapper.OrgCommonMapper;
import com.froad.db.mysql.mapper.ProductCategoryCommonMapper;
import com.froad.db.mysql.mapper.ProductCommonMapper;
import com.froad.db.mysql.mapper.ProviderCommonMapper;
import com.froad.db.redis.BankOperatorRedis;
import com.froad.db.redis.ClientPaymentChannelRedis;
import com.froad.db.redis.ClientRedis;
import com.froad.db.redis.MerchantRedis;
import com.froad.db.redis.OrgRedis;
import com.froad.db.redis.ProviderMerchantRedis;
import com.froad.db.redis.ProductCommonRedis;
import com.froad.db.redis.SupportRedis;
import com.froad.db.redis.VipRedis;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.AccountTypeEnum;
import com.froad.enums.OrgLevelEnum;
import com.froad.enums.ProductType;
import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.logic.CommonLogic;
import com.froad.po.Area;
import com.froad.po.BankOperateLog;
import com.froad.po.BankOperator;
import com.froad.po.Client;
import com.froad.po.ClientPaymentChannel;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.Org;
import com.froad.po.Outlet;
import com.froad.po.Product;
import com.froad.po.ProductCategory;
import com.froad.po.ProductGroup;
import com.froad.po.ProductOutletInfo;
import com.froad.po.ProductPresell;
import com.froad.po.ProductSeckill;
import com.froad.po.Provider;
import com.froad.po.VipProduct;
import com.froad.po.mongo.MerchantDetail;
import com.froad.util.Checker;
import com.froad.util.MongoTableName;
import com.froad.util.OrgSuperUtil;
import com.froad.util.RedisKeyUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CommonLogicImpl implements CommonLogic {
	
	private static final RedisManager redis = new RedisManager();
	private static final MongoService mongo = new MongoManager ();
	
	/**
	 *  Rdis获取的Map，如果为空业务返回Map不是空对象
	 *  如果size是空返回null，利于前端检查
	  * @Title: converRedisMap
	  * @Description: 
	  * @author: share 2015年4月11日
	  * @modify: share 2015年4月11日
	  * @param @param redisMap
	  * @param @return    
	  * @return Map<String,String>    
	  * @throws
	 */
	private static Map<String,String> converRedisMap(Map<String,String> redisMap){
		if(redisMap != null && redisMap.isEmpty()){
			return null;
		}
		return redisMap;
	}
	
	/**
	 * 根据merchant_id查到1、2、3、4级机构号
	 * @Title: getSuperOrgByMerchantId 
	 * @Description: 根据商户ID获取本级机构号与上级机构号
	 * @author: froad-huangyihao 2015年4月10日
	 * @modify: froad-huangyihao 2015年4月10日
	 * @param merchantId
	 * @param clientId
	 * @return	
	 * @throws
	 */
	@Override
	public Map<OrgLevelEnum, String> getSuperOrgByMerchantId(String clientId,String merchantId) {
		Map<OrgLevelEnum, String> map = new HashMap<OrgLevelEnum, String>();
		
		if(Checker.isEmpty(merchantId) || Checker.isEmpty(clientId)){
			return map;
		}
		
		
		Merchant merchant = null;
		try { 
			
			/**********************操作Redis缓存**********************/
			Map<String,String> orgMerchantRedis =  OrgRedis.getAll_cbbank_merchant_org_level_merchant_id(merchantId);
			if(Checker.isNotEmpty(orgMerchantRedis)){
				map.put(OrgLevelEnum.orgLevel_one, orgMerchantRedis.get("province_agency"));
				map.put(OrgLevelEnum.orgLevel_two, orgMerchantRedis.get("city_agency"));
				map.put(OrgLevelEnum.orgLevel_three, orgMerchantRedis.get("county_agency"));
				map.put(OrgLevelEnum.orgLevel_four, orgMerchantRedis.get("org_code"));
				return map;
			}
			
			
			/**********************重新从mysql中查询设置到Redis中**********************/
			//根据clientId+merchantId查询机构对象
			Org resultOrg=this.queryByMerchantInfo(clientId, merchantId);
			
			//机构商户
			if(Checker.isNotEmpty(resultOrg)){
				map=this.setOrgMap(resultOrg);
				
			}else{//普通商户
				Map<String,String> merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
				if(Checker.isNotEmpty(merchantMap)){
					//商户缓存数据
					merchant = new Merchant();
					merchant.setOrgCode(merchantMap.get("org_code"));
					merchant.setClientId(clientId);
				}else{
					//mysql查询
					merchant=this.getMerchantInfo(merchantId);
					//重新设置到Redis缓存中
					MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
				}
				
				
				
				if(Checker.isNotEmpty(merchant)){
					//结果机构对象
					resultOrg=this.queryByOrgCode(merchant.getClientId(), merchant.getOrgCode());
					if(Checker.isNotEmpty(resultOrg)){
						map=this.setOrgMap(resultOrg);
					}
				}
			}
			
			
			//设置缓存Redis(商户Id对应的1-2-3-4级机构关系)
			if(Checker.isNotEmpty(map)){
				OrgRedis.set_cbbank_merchant_org_level_merchant_id(merchantId,map);
			}
				
			
		}catch (Exception e) { 
			LogCvt.error("根据merchantId查询1-2-3-4级机构失败，原因:" + e.getMessage(),e); 
		}  
		
		return map;
	}

	/**
	 * 设置map值,机构级别与orgCode的关系
	 * @param Org对象
	 * @return
	 */
	@Override
	public Map<OrgLevelEnum, String> setOrgMap(Org resultOrg){
		Map<OrgLevelEnum, String> map = new HashMap<OrgLevelEnum, String>();
		//如果merchantid属于3级机构发展，那么4就返回0，1、2、3返回有效值。
		//如果merchantid属于4级机构，那么0、1、2、3都返回有效值。
		String orgLevel=resultOrg.getOrgLevel();
		System.err.println(orgLevel);
		if(OrgLevelEnum.orgLevel_one.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getOrgCode());
			map.put(OrgLevelEnum.orgLevel_two, "");
			map.put(OrgLevelEnum.orgLevel_three, "");
			map.put(OrgLevelEnum.orgLevel_four, "");
		}else if(OrgLevelEnum.orgLevel_two.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getOrgCode());
			map.put(OrgLevelEnum.orgLevel_three, "");
			map.put(OrgLevelEnum.orgLevel_four, "");
		}else if(OrgLevelEnum.orgLevel_three.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getCityAgency());
			map.put(OrgLevelEnum.orgLevel_three, resultOrg.getOrgCode());
			map.put(OrgLevelEnum.orgLevel_four, "");
		}else if(OrgLevelEnum.orgLevel_four.getLevel().equals(orgLevel)){
			map.put(OrgLevelEnum.orgLevel_one, resultOrg.getProvinceAgency());
			map.put(OrgLevelEnum.orgLevel_two, resultOrg.getCityAgency());
			map.put(OrgLevelEnum.orgLevel_three, resultOrg.getCountyAgency());
			map.put(OrgLevelEnum.orgLevel_four, resultOrg.getOrgCode());
		}
		
		return map;
	}
	
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
	@Override
	public OrgLevelEnum getOrgLevelByOrgCode(String orgCode, String clientId) {
		OrgLevelEnum orgLevelEnum = null;
		Org org = null;
		
		org = this.getOrgByOrgCode(orgCode, clientId);
		if(Checker.isNotEmpty(org)){
			orgLevelEnum = OrgLevelEnum.getByLevel(org.getOrgLevel());
		}
		
		
		return orgLevelEnum;
	}

	/**
	 * 取有效机构
	 * 根据orgCode+clientId查询Org对象
	 */
	@Override
	public Org getOrgByOrgCode(String orgCode, String clientId) {
		SqlSession sqlSession = null;
		Org resultOrg = null;
		
		try {
			if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCode)){
				throw new Exception(ResultCode.notAllParameters.getMsg());
			}
			
			/**********************操作Redis缓存**********************/
			Map<String,String> orgRedis = OrgRedis.getAll_cbbank_org_client_id_org_code(clientId, orgCode);
			if(Checker.isNotEmpty(orgRedis)){
				resultOrg = this.setOrgRedis(orgRedis);		
				if(!resultOrg.getIsEnable()){//若机构无效则返回null
					return null;
				}
			}else{
				/**********************操作MySQL数据库**********************/
				
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				OrgCommonMapper orgMapper = sqlSession.getMapper(OrgCommonMapper.class);
				
				Org temp = new Org();
				temp.setOrgCode(orgCode);
				temp.setClientId(clientId);
				temp.setIsEnable(true);
				List<Org> list = orgMapper.findOrg(temp);
				if(Checker.isNotEmpty(list)){
					resultOrg = list.get(0);
				}
				
				if(Checker.isNotEmpty(resultOrg)){
					//重新设置到redis中
					String key=RedisKeyUtil.cbbank_org_client_id_org_code(clientId, orgCode);
					OrgRedis.set_cbbank_org(key, resultOrg);
				}
				
			}
		} catch (Exception e) {
			LogCvt.error("根据机构号返回该机构等级查询异常"+e.getMessage(), e);
		} finally {
			if(sqlSession != null){
				sqlSession.close(); 
			}
		}
		
		return resultOrg;
	}
	
	
	/**
	 * 获取上级(多级别)机构对象，包括自身orgCode
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @return 上级Org对象集合
	 */
	@Override
	public List<Org> getSuperOrgList(String clientId, String orgCode) {
		List<Org> superOrgs = new ArrayList<Org>(); 
		try { 
			if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCode)){
				throw new Exception(ResultCode.notAllParameters.getMsg());
			}
			
			//根据当前orgCode查出机构级别 
			Org org=this.getOrgByOrgCode(orgCode, clientId);
			if(Checker.isNotEmpty(org)){
				List<String> orgCodes = new ArrayList<String>();
				orgCodes.add(orgCode);
				if(Checker.isNotEmpty(org.getCountyAgency())){
					orgCodes.add(org.getCountyAgency());
				}
				if(Checker.isNotEmpty(org.getCityAgency())){
					orgCodes.add(org.getCityAgency());
				}
				if(Checker.isNotEmpty(org.getProvinceAgency())){
					orgCodes.add(org.getProvinceAgency());
				}
				
				superOrgs = this.getOrgByList(clientId, orgCodes);
				
			}
			
			
		} catch (Exception e) { 
			LogCvt.error("获取上级(多级别)机构Org失败，原因:" + e.getMessage(), e); 
		}
		
		return superOrgs;
	}
	
	/**
	 *  根据父级机构查询子机构信息
	 */
	@Override
	public List<Org> queryByParentOrgCode(String clientId, String orgCode) {
		List<Org> orgList = null; 
		SqlSession sqlSession = null;
		OrgCommonMapper orgMapper = null;
		try { 
			if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCode)){
				throw new Exception(ResultCode.notAllParameters.getMsg());
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgCommonMapper.class);
			
			orgList = orgMapper.queryByParentOrgCode(clientId, orgCode);
			
		} catch (Exception e) { 
			LogCvt.error("根据父级机构查询子机构信息失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return orgList;
	}
	
	
	/**
     * 根据orgCode集合获取机构对象(循环内部处理)
     * @param clientId 客户端编号
     * @param orgCodes 机构编号集合
     * @return list<OrgVo> 返回机构对象集合
     */
	@Override
	public List<Org> getOrgByList(String clientId, List<String> orgCodes){
		List<Org> orgList = new ArrayList<Org>(); 
		SqlSession sqlSession = null;
		OrgCommonMapper orgMapper = null;
		try {
			if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCodes)){
				throw new Exception(ResultCode.notAllParameters.getMsg());
			}
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			orgMapper = sqlSession.getMapper(OrgCommonMapper.class);

			//判断list中的orgCode是否相同，相同的则过滤，减少对数据库sql组装的操作
			Set<String> set = new HashSet<String>();
			set.addAll(orgCodes);
			
			orgCodes = new ArrayList<String>();
			orgCodes.addAll(set);
			
			orgList = orgMapper.findOrgByList(clientId, orgCodes);
			
			
		} catch (Exception e) { 
			LogCvt.error("根据orgCode集合获取机构对象失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return orgList; 
	}
	
	
	/**
	 * 内部方法，从mysql获取商户
	 * @param merchantId
	 * @return
	 */
	private Merchant getMerchantInfo(String merchantId) {
		SqlSession sqlSession = null;
		Merchant merchant = null;
		
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			MerchantCommonMapper mapper = sqlSession.getMapper(MerchantCommonMapper.class);
			
			merchant = mapper.findMerchantByMerchantId(merchantId);
		} catch (Exception e) {
			LogCvt.error("根据商户号返回该商户查询异常"+e.getMessage(), e);
		} finally {
			if(sqlSession != null){
				sqlSession.close(); 
			}
		}
		return merchant;
	}
	
	
	/**
	 * 内部方法，从mysql获取供应商
	 * @param merchantId
	 * @return
	 */
	private Provider getProviderInfo(String merchantId) {
		SqlSession sqlSession = null;
		Provider provider = null;
		
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			ProviderCommonMapper mapper = sqlSession.getMapper(ProviderCommonMapper.class);
			
			provider = mapper.findByMerchantId(merchantId);
		} catch (Exception e) {
			LogCvt.error("根据供应商Id返回该供应商查询异常"+e.getMessage(), e);
		} finally {
			if(sqlSession != null){
				sqlSession.close(); 
			}
		}
		return provider;
	}
	
	/**
	 * 内部方法，根据商户id、门店id查询mysql获取商户账户信息.
	 * @param merchantId 商户id
	 * @param outletId 门店 
	 * @return MerchantAccount
	 */
	@Override
	public MerchantAccount getMerchantAccoutByMerchantIdOrOutletId(
			String merchantId, String outletId) {
		SqlSession sqlSession = null;
		MerchantAccount merchantAccount = new MerchantAccount();
		merchantAccount.setMerchantId(merchantId);
		merchantAccount.setOutletId(outletId);
		merchantAccount.setIsDelete(false);
		merchantAccount.setAcctType(AccountTypeEnum.SQ.getCode()); // 1-收款 2-付款
		try {
			if(Checker.isEmpty(merchantId) || Checker.isEmpty(outletId)){
				throw new Exception(ResultCode.notAllParameters.getMsg());
			}
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			MerchantCommonMapper merchantCommonMapper = sqlSession.getMapper(MerchantCommonMapper.class);
			List<MerchantAccount> merchantAccoutList = merchantCommonMapper.findMerchantAccount(merchantAccount);
			if (CollectionUtils.isEmpty(merchantAccoutList)) {
				merchantAccount.setOutletId("0"); 
				merchantAccoutList = merchantCommonMapper.findMerchantAccount(merchantAccount);
				if (CollectionUtils.isEmpty(merchantAccoutList)) {
					LogCvt.error("商户门店账号信息不存在或已经被删除，商户编号：" + merchantId	+ "，门店编号：" + outletId);
				}
			}
			if (null != merchantAccoutList && merchantAccoutList.size() > 0) {
				merchantAccount = merchantAccoutList.get(0); // 拿第一个
				LogCvt.info("根据商户ID和门店ID返回该商户账户查询,商户编号：商户编号：" + merchantId
						+ "，门店编号：" + outletId + "，商户账号："
						+ merchantAccount.getAcctNumber() == null ? ""
						: merchantAccount.getAcctNumber());
			}
		} catch (Exception e) {
			LogCvt.error("根据商户ID和门店ID返回该商户账户查询异常"+ e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return merchantAccount;
	}
	
	
	/**
	 * 根据机构号查询虚拟商户
     * @param clientId
     * @param orgCode
     * @return Merchant
     */
	private Merchant getBankMerchantInfo(String clientId,String orgCode) {
		SqlSession sqlSession = null;
		Merchant merchant = null;
		
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			MerchantCommonMapper mapper = sqlSession.getMapper(MerchantCommonMapper.class);
			
			Merchant temp = new Merchant();
			temp.setClientId(clientId);
			temp.setOrgCode(orgCode);
			temp.setIsEnable(true);
			temp.setMerchantStatus(true); // 只查询虚拟商户
			List<Merchant> merchantList = mapper.findMerchant(temp);
			if(Checker.isNotEmpty(merchantList)){
				merchant = merchantList.get(0);
			}
		} catch (Exception e) {
			LogCvt.error("根据客户端id和机构码返回该银行商户异常"+e.getMessage(), e);
		} finally {
			if(sqlSession != null){
				sqlSession.close(); 
			}
		}
		return merchant;
	}
	
	/**
	 * 获取商户信息redis，redis中不存在就从mysql取
	 * @param clientId
	 * @param merchantId
	 * @return
	 */
	@Override
	public Map<String,String> getMerchantAndCategoryRedis(String clientId,String merchantId){
		Map<String,String> merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
		
		//如果redis为空，从mysql取
		if (Checker.isEmpty(merchantMap)
				|| !merchantMap.containsKey("merchant_name")
				|| !merchantMap.containsKey("is_enable")
				|| !merchantMap.containsKey("merchant_status")
				|| !merchantMap.containsKey("category_id")
				|| !merchantMap.containsKey("type_id")) {
			
			LogCvt.info("redis获取商户信息查询为空，从mysql查询商户信息，查询条件：merchantId:"+merchantId);
			
			Merchant merchant = this.getMerchantInfo(merchantId);
			if(Checker.isNotEmpty(merchant)){
				try {
					// mongo获取商户分类和类型的信息
					DBObject where = new BasicDBObject();
					where.put("_id", merchantId);
					MerchantDetail detail = mongo.findOne(where, MongoTableName.CB_MERCHANT_DETAIL, MerchantDetail.class);
					if(detail != null){
						merchant.setCategoryInfoObjList(detail.getCategoryInfo());
						merchant.setTypeInfoObjList(detail.getTypeInfo());
					}
					
					LogCvt.info("redis获取商户信息查询为空，重新设置缓存");
					MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
				} catch (Exception e) {
					LogCvt.error("Common设置商户缓存失败, 原因:"+e.getMessage(), e);
				}
				merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
				
			}
		}
		return merchantMap;
	}
	

	
	@Override
	public Map<String,String> getMerchantRedis(String clientId,String merchantId){
		Map<String,String> merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
		
		//如果redis为空，从mysql取
		if (Checker.isEmpty(merchantMap)
				|| !merchantMap.containsKey("merchant_name")
				|| !merchantMap.containsKey("is_enable")
				|| !merchantMap.containsKey("merchant_status")) {
			
			LogCvt.info("redis获取商户信息查询为空，从mysql查询商户信息，查询条件：merchantId:"+merchantId);
			
			Merchant merchant = this.getMerchantInfo(merchantId);
			if(Checker.isNotEmpty(merchant)){
				try {
					LogCvt.info("redis获取商户信息查询为空，重新设置缓存");
					MerchantRedis.set_cbbank_merchant_client_id_merchant_id(merchant);
				} catch (Exception e) {
					LogCvt.error("Common设置商户缓存失败, 原因:"+e.getMessage(), e);
				}
				merchantMap = MerchantRedis.get_cbbank_merchant_client_id_merchant_id(clientId, merchantId);
			}
		}
		return merchantMap;
	}
	
	/**
	 * 
	 * getProviderRedis:(这里用一句话描述这个方法的作用).
	 * @author zhouzhiwei
	 * 2015年11月26日 上午11:44:03
	 * @param merchantId: 供应商ID
	 * @return
	 */
	public Map<String, String> getProviderRedis(String merchantId) {
		Map<String, String> providerMap = ProviderMerchantRedis.getAll_cbbank_provider_merchant_Id(merchantId);
		if(Checker.isEmpty(providerMap)
				|| !providerMap.containsKey("merchant_name")) {
			LogCvt.info("redis获取供应商信息查询为空，从mysql查询供应商信息，查询条件：merchantId:"+merchantId);
			Provider provider = this.getProviderInfo(merchantId);
			if(Checker.isNotEmpty(provider)) {
				try {
					LogCvt.info("redis获取供应商信息查询为空，重新设置缓存");
					ProviderMerchantRedis.set_cbbank_provider_merchant_Id(provider);
				} catch (Exception e) {
					LogCvt.error("Common设置供应商缓存失败, 原因:"+e.getMessage(), e);
				}
				providerMap = ProviderMerchantRedis.getAll_cbbank_provider_merchant_Id(merchantId);
			}
		}
		return providerMap; 
	}
	
	
	/**
	 * 查询银行机构对应的虚拟商户信息
	 * @Title: getBankMerchantRedis 
	 * @author vania
	 * @version 1.0
	 * @see: 
	 * @param clientId
	 * @param orgCode
	 * @return
	 * @return Map<String,String>    返回类型 
	 * @throws
	 */
	@Override
	public Map<String,String> getBankMerchantRedis(String clientId,String orgCode){
		Map<String,String> merchantMap = MerchantRedis.get_cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
		
		//如果redis为空，从mysql取
		if (Checker.isEmpty(merchantMap)) {
			
//			LogCvt.info("redis获取银行虚拟商户信息查询为空，从mysql查询商户信息，查询条件：orgCode:"+orgCode);
			
			Merchant merchant = this.getBankMerchantInfo(clientId, orgCode);
			if(Checker.isNotEmpty(merchant)){
				try {
					LogCvt.info("redis获取银行虚拟商户信息查询为空，重新设置缓存");
					MerchantRedis.set_cbbank_bank_merchant_client_id_org_code(merchant);
				} catch (Exception e) {
					LogCvt.error("Common设置银行虚拟商户缓存失败, 原因:"+e.getMessage(), e);
				}
				merchantMap = MerchantRedis.get_cbbank_bank_merchant_client_id_org_code(clientId, orgCode);
//				
//				//将mysql信息刷回redis
//				redis.putMap(merchantRedisKey, merchantMap);
			}
		}
		return merchantMap;
//		return converRedisMap(merchantMap);
	}
	
	/**
	 * 获取商品信息Redis，redis中不存在就从mysql取
	 * @param clientId
	 * @param merchantId
	 * @param productId
	 * @return
	 */
	@Override
	public Map<String,String> getProductRedis(String clientId,String merchantId,String productId){
		String prodcutKey = RedisKeyUtil.cbbank_product_client_id_merchant_id_product_id(clientId, merchantId, productId);
		Map<String,String> productMap = redis.getMap(prodcutKey);
		//如果redis为空，从mysql取
		if (Checker.isEmpty(productMap)
				|| !productMap.containsKey("is_marketable")
				|| !productMap.containsKey("product_name")
				|| !productMap.containsKey("product_type")
				|| !productMap.containsKey("price")
				|| !productMap.containsKey("point")
				|| !productMap.containsKey("category_tree_path")) {
			LogCvt.debug("redis获取商品信息查询为空，从mysql查询商品信息，查询条件：clientId:" + merchantId + ",productId:"+productId);
			
			Map<String,Object> productInfoMap = this.getProductInfo(clientId, productId);
			Product product = (Product)productInfoMap.get("product");
			if(Checker.isNotEmpty(product)){
			    try {

                  LogCvt.debug("redis获取商品信息查询为空，重新设置缓存");
                  productMap = ProductCommonRedis.set_cbbank_product_client_id_merchant_id_product_id(productInfoMap);
                } catch (Exception e) {
                    LogCvt.error("Common设置商品信息缓存失败, 原因:"+e.getMessage(), e);
                }
			}
		}
		return converRedisMap(productMap);
	}
	
	/**
     * 内部方法，从mysql获取商品
     * @param clientId
     * @param productId
     * @return Map
     */
    private Map<String,Object> getProductInfo(String clientId,String productId) {
        
        Map<String,Object> productMap = new HashMap<String,Object>();
        
        SqlSession sqlSession = null;
        ProductCommonMapper mapper = null;
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(ProductCommonMapper.class);
            
            Map<String,String> param = new HashMap<String,String>();
            param.put("clientId", clientId);
            param.put("productId", productId);
            
            List<Product> products = mapper.getProductByPid(param);
            if(products!=null && products.size()>0){
                productMap.put("product", products.get(0));
                if(ProductType.group.getCode().equals(products.get(0).getType())){
                    List<ProductGroup> productGroups = mapper.getProductGroupByPid(param);
                    if(productGroups!=null && productGroups.size()>0){
                        productMap.put("productGroup", productGroups.get(0));
                    }
                } else if(ProductType.presell.getCode().equals(products.get(0).getType())){
                    List<ProductPresell> productPresells = mapper.getProductPresellByPid(param);
                    if(productPresells!=null && productPresells.size()>0){
                        productMap.put("productPresell", productPresells.get(0));
                    }
                }
            }
            return productMap;
        } catch (Exception e) { 
            LogCvt.error("获取商品信息异常，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return productMap; 
    }
	
	
	/**
     * 获取商品库存信息Redis，redis中不存在就从mysql取
     * @param clientId
     * @param merchantId
     * @param productId
     * @return int
     */
    @Override
    public int getStoreRedis(String clientId,String merchantId,String productId){
        String storeKey = RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(clientId,merchantId,productId);
        String store = redis.getString(storeKey);
        if(Checker.isEmpty(store)) {
            Product product = this.getStore(clientId, productId);
            
            if(Checker.isNotEmpty(product) && Checker.isNotEmpty(product.getStore())){
                try {
                    LogCvt.info("redis获取商品库存信息查询为空，重新设置缓存");
                    redis.putString(RedisKeyUtil.cbbank_product_store_client_id_merchant_id_product_id(product.getClientId(), product.getMerchantId(), product.getProductId()), ObjectUtils.toString(product.getStore(), ""));
                } catch (Exception e) {
                    LogCvt.error("Common设置商品库存缓存失败, 原因:"+e.getMessage(), e);
                }
                return product.getStore();
            } else {
                return 0;
            }
        } else {
            return Integer.parseInt(store);
        }
    }
    
    /**
     * 获取秒杀商品库存信息Redis，redis中不存在就从mysql取
     * @param clientId
     * @param productId
     * @return int
     */
    @Override
    public int getSeckillStoreRedis(String clientId,String productId){
    	String storeKey = RedisKeyUtil.cbbank_seckill_product_store_client_id_product_id(clientId,productId);
    	String store = redis.getString(storeKey);
    	if(Checker.isEmpty(store)) {
    		ProductSeckill product = this.getSeckillStore(clientId, productId);
    		
    		if(Checker.isNotEmpty(product) && Checker.isNotEmpty(product.getSecStore())){
    			try {
    				LogCvt.info("redis获取秒杀商品库存信息查询为空，重新设置缓存");
    				redis.putString(storeKey, ObjectUtils.toString(product.getSecStore(), ""));
    			} catch (Exception e) {
    				LogCvt.error("Common设置秒杀商品库存缓存失败, 原因:"+e.getMessage(), e);
    			}
    			return product.getSecStore();
    		} else {
    			return 0;
    		}
    	} else {
    		return Integer.parseInt(store);
    	}
    }
    
    /**
     * 内部方法，从mysql获取商品库存
     * @param clientId
     * @param productId
     * @return Map
     */
    private Product getStore(String clientId,String productId) {
        SqlSession sqlSession = null;
        ProductCommonMapper mapper = null;
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(ProductCommonMapper.class);
            
            Map<String,String> param = new HashMap<String,String>();
            param.put("clientId", clientId);
            param.put("productId", productId);
            
            List<Product> products = mapper.getProductStoreByPid(param);
            if(products!=null && products.size()>0){
                return products.get(0);
            }
        } catch (Exception e) { 
            LogCvt.error("获取商品库存信息异常，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return null; 
    }
    
    /**
     * 内部方法，从mysql获取秒杀商品库存
     * @param clientId
     * @param productId
     * @return Map
     */
    private ProductSeckill getSeckillStore(String clientId,String productId) {
    	SqlSession sqlSession = null;
    	ProductCommonMapper mapper = null;
    	try { 
    		sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
    		mapper = sqlSession.getMapper(ProductCommonMapper.class);
    		
    		Map<String,String> param = new HashMap<String,String>();
    		param.put("clientId", clientId);
    		param.put("productId", productId);
    		
    		List<ProductSeckill> products = mapper.getSeckillProductStoreByPid(param);
    		if(Checker.isNotEmpty(products)){
    			return products.get(0);
    		}
    	} catch (Exception e) { 
    		LogCvt.error("获取商品库存信息异常，原因:" + e.getMessage(),e); 
    	} finally { 
    		if(null != sqlSession)  
    			sqlSession.close();  
    	} 
    	return null; 
    }
    
    /**
     * 获取商品最大门店提货数Redis，redis中不存在就从mysql取
     * @param clientId
     * @param merchantId
     * @param productId
     * @return int
     */
    @Override
    public int getProductPresellMaxRedis(String clientId,String merchantId,String productId){
        String productMaxKey = RedisKeyUtil.cbbank_product_presell_max_product_id(productId);
        String max = redis.getString(productMaxKey);
        if(Checker.isEmpty(max)) {
            ProductPresell presellProduct = this.getProductPresellMax(clientId, productId);
            if(Checker.isNotEmpty(presellProduct) && Checker.isNotEmpty(presellProduct.getMaxPerOutlet())){
                try {
                    LogCvt.info("redis获取商品最大提货数量信息查询为空，重新设置缓存");
                    redis.putString(RedisKeyUtil.cbbank_product_presell_max_product_id(presellProduct.getProductId()), ObjectUtils.toString(presellProduct.getMaxPerOutlet(), ""));
                } catch (Exception e) {
                    LogCvt.error("Common设置商品最大提货数量缓存失败, 原因:"+e.getMessage(), e);
                }
                return presellProduct.getMaxPerOutlet();
            } else {
                return 0;
            }
        } else {
            return Integer.parseInt(max);
        }
    }
    
    /**
     * 内部方法，从mysql获取商品每个门店最大提货数量
     * @param clientId
     * @param productId
     * @return Map
     */
    private ProductPresell getProductPresellMax(String clientId,String productId) {
        SqlSession sqlSession = null;
        ProductCommonMapper mapper = null;
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(ProductCommonMapper.class);
            
            Map<String,String> param = new HashMap<String,String>();
            if(clientId!=null && !"".equals(clientId)){
                param.put("clientId", clientId);
            }
            param.put("productId", productId);
            
            List<ProductPresell> productPresells = mapper.getProductMaxPerOutletByPid(param);
            if(productPresells!=null && productPresells.size()>0){
                return productPresells.get(0);
            }
        } catch (Exception e) { 
            LogCvt.error("获取商品每个门店最大提货数量信息异常，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return null; 
    }
    
    /**
     * 修改商品表中商户名字
     * @Title: updateProduct_MerchantNameByMerchantId 
     * @author vania
     * @version 1.0
     * @see: 
     * @param merchantId
     * @param merchantName
     * @return
     * @return Boolean    返回类型 
     * @throws
     */
    public Boolean updateProduct_MerchantNameByMerchantId (String merchantId, String merchantName) {
    	Boolean result = false;
    	SqlSession sqlSession = null;
        ProductCommonMapper mapper = null;
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(ProductCommonMapper.class);
            
            /*修改MySQL*/
            result = mapper.updateMerchantNameByMerchantId(merchantId, merchantName);
            
            if(result) {
	            DBObject where = new BasicDBObject();
	            where.put("merchant_id", merchantId);
	            
	            DBObject value = new BasicDBObject();
	            value.put("merchant_name", merchantName);
	            
	            /*修改mongodb*/
	            mongo.updateMulti(value, where, MongoTableName.CB_PRODUCT_DETAIL, "$set");
	            
	            sqlSession.commit();
            } else {
            	sqlSession.rollback();             	
            }
        } catch (Exception e) { 
        	if(null != sqlSession)  
                sqlSession.rollback(); 
            LogCvt.error("根据商户id修改商品表中商户名字异常，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return result; 
    }
	
	/**
	 *  查询结构信息
	  * @Title: queryByMerchantInfo
	  * @Description: 
	  * @author: share 2015年4月1日
	  * @modify: share 2015年4月1日
	  * @param @param merchantId
	  * @param @param outletId
	  * @param @return
	  * @throws
	  * @see com.froad.support.CommonSupport#queryByMerchantInfo(java.lang.String, java.lang.String)
	 */
	@Override
	public Org queryByOutleId(String clientId, String outletId) {
		SqlSession sqlSession = null;
		OrgCommonMapper mapper = null;
		Org resultOrg = null;
		try { 

			if(Checker.isEmpty(clientId) || Checker.isEmpty(outletId)){
				throw new Exception(ResultCode.notAllParameters.getMsg());
			}
			
			/**********************操作Redis缓存**********************/
			Map<String,String> orgRedis = OrgRedis.getAll_cbbank_outlet_org_client_id_outlet_id(clientId, outletId);
			if(Checker.isNotEmpty(orgRedis)){
				resultOrg = this.setOrgRedis(orgRedis);		
			}else{
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				mapper = sqlSession.getMapper(OrgCommonMapper.class);
				
				Org org = new Org();
				org.setClientId(clientId);
				org.setOutletId(outletId);
	
				resultOrg = mapper.queryByOrgInfo(org);
				
				if(Checker.isNotEmpty(resultOrg)){
					//重新设置到redis中
					String key=RedisKeyUtil.cbbank_outlet_org_client_id_outlet_id(clientId, outletId);
					OrgRedis.set_cbbank_org(key, resultOrg);
				}
			}
		} catch (Exception e) { 
			LogCvt.error("获取机构信息异常，原因:" + e.getMessage(),e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultOrg; 
	}
	
	/**
	 *  根据机构号获取机构信息
	  * @Title: queryByOrgCode
	  * @Description: 
	  * @author: share 2015年4月1日
	  * @modify: share 2015年4月1日
	  * @param @param clientId
	  * @param @param orgCode
	  * @param @return    
	  * @return Org    
	  * @throws
	 */
	@Override
	public Org queryByOrgCode(String clientId,String orgCode) {
		SqlSession sqlSession = null;
		OrgCommonMapper mapper = null;
		Org resultOrg = null;
		try { 
			if(Checker.isEmpty(clientId) || Checker.isEmpty(orgCode)){
				throw new Exception(ResultCode.notAllParameters.getMsg());
			}
			
			/**********************操作Redis缓存**********************/
			Map<String,String> orgRedis = OrgRedis.getAll_cbbank_org_client_id_org_code(clientId, orgCode);
			if(Checker.isNotEmpty(orgRedis)){
				resultOrg = this.setOrgRedis(orgRedis);		
			}else{
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				mapper = sqlSession.getMapper(OrgCommonMapper.class);
				
				Org org = new Org();
				org.setClientId(clientId);
				org.setOrgCode(orgCode);
	
				resultOrg = mapper.queryByOrgInfo(org);
				
				if(Checker.isNotEmpty(resultOrg)){
					//重新设置到redis中
					String key=RedisKeyUtil.cbbank_org_client_id_org_code(clientId, orgCode);
					OrgRedis.set_cbbank_org(key, resultOrg);
				}
			}
			
		} catch (Exception e) { 
			LogCvt.error("获取机构信息异常，原因:" + e.getMessage(),e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultOrg; 
	}

	/**
	 *  按商户ID查询机构
	  * @Title: queryByMerchantInfo
	  * @Description: 
	  * @author: share 2015年4月2日
	  * @modify: share 2015年4月2日
	  * @param @param clientId
	  * @param @param merchantId
	  * @param @return
	  * @throws
	  * @see com.froad.support.CommonSupport#queryByMerchantInfo(long, java.lang.String)
	 */
	@Override
	public Org queryByMerchantInfo(String clientId, String merchantId) {
		SqlSession sqlSession = null;
		OrgCommonMapper mapper = null;
		Org resultOrg = null;
		try { 
			
			if(Checker.isEmpty(clientId) || Checker.isEmpty(merchantId)){
				throw new Exception(ResultCode.notAllParameters.getMsg());
			}
			
			/**********************操作Redis缓存**********************/
			Map<String,String> orgRedis = OrgRedis.getAll_cbbank_merchant_org_client_id_merchant_id(clientId, merchantId);
			if(Checker.isNotEmpty(orgRedis)){
				resultOrg = this.setOrgRedis(orgRedis);		
			}else{
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				mapper = sqlSession.getMapper(OrgCommonMapper.class);
				
				Org org = new Org();
				org.setClientId(clientId);
				org.setMerchantId(merchantId);

				resultOrg = mapper.queryByOrgInfo(org);
				
				if(Checker.isNotEmpty(resultOrg)){
					//重新设置到redis中
					String key=RedisKeyUtil.cbbank_merchant_org_client_id_merchant_id(clientId, merchantId);
					OrgRedis.set_cbbank_org(key, resultOrg);
				}
			}
			
		} catch (Exception e) { 
			LogCvt.error("获取机构信息异常，原因:" + e.getMessage(),e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return resultOrg; 
	}
	
	/**
	 * 将缓存中取出的值orgRedis重新设置到Org对象中进行返回
	 * @param orgRedis
	 * @return
	 */
	private Org setOrgRedis(Map<String,String> orgRedis){
		Org resultOrg = new Org();
		
		resultOrg.setClientId(orgRedis.get("client_id"));
		resultOrg.setBankType(orgRedis.get("bank_type"));
		resultOrg.setOrgCode(orgRedis.get("org_code"));
		resultOrg.setOrgName(orgRedis.get("org_name"));
		resultOrg.setProvinceAgency(orgRedis.get("province_agency"));
		resultOrg.setCityAgency(orgRedis.get("city_agency"));
		resultOrg.setCountyAgency(orgRedis.get("county_agency"));
		resultOrg.setPhone(orgRedis.get("phone"));
		resultOrg.setMerchantId(orgRedis.get("merchant_id"));
		resultOrg.setOutletId(orgRedis.get("outlet_id"));
		resultOrg.setAreaId(Long.parseLong(orgRedis.get("area_id")));
		resultOrg.setOrgLevel(orgRedis.get("org_level"));
		
		resultOrg.setNeedReview(BooleanUtils.toBooleanObject(orgRedis.get("need_review"), "1", "0", ""));
		resultOrg.setOrgType(BooleanUtils.toBooleanObject(orgRedis.get("org_type"), "1", "0", ""));
		resultOrg.setIsEnable(BooleanUtils.toBooleanObject(orgRedis.get("is_enable"), "1", "0", ""));

		return resultOrg;
	}
	
	  /**
     * 增加 BankOperateLog用户操作日志
     * @param bankOperator
     * @return Long    主键ID
     */
	@Override
	public Boolean addBankOperateLog(BankOperateLog bankOperatorLog)  throws Exception{
		
		SqlSession sqlSession = null;
		BankOperateLogCommonMapper bankOperateLogMapper = null;
		boolean result = false;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			bankOperateLogMapper = sqlSession.getMapper(BankOperateLogCommonMapper.class);
			
			bankOperatorLog.setCreateTime(new Date());
			Long id = bankOperateLogMapper.addBankOperateLog(bankOperatorLog);
			if(id>0){
				result=true;
				sqlSession.commit(true); 
			}
			
		} catch (Exception e) { 
			if(null != sqlSession)  
				sqlSession.rollback(true);  
			LogCvt.error("添加BankOperateLog失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	
	/**
     * 根据id银行用户 BankOperator
     * @param clientId 客户端id
     * @param userId 用户编号
     * @return BankOperator    银行用户po
     */
	@Override
	public BankOperator getBankOperatorById(String clientId, Long userId) {

		BankOperator operResult = null;
		SqlSession sqlSession = null;
		BankOperatorCommonMapper bankOperatorMapper = null;
		try { 
			if(Checker.isEmpty(userId)){
				return new BankOperator();
			}
			
			//判断clientId是否有值才查缓存
			if(Checker.isNotEmpty(clientId)){
				/**********************操作Redis缓存**********************/
				Map<String,String> operatorRedis=BankOperatorRedis.getAll_cbbank_bank_user_client_id_user_id(clientId,userId);
				if(Checker.isNotEmpty(operatorRedis)){//操作Redis缓存
					LogCvt.debug("查询缓存cbbank:bank_user:client_id:user_id有值"+operatorRedis.values());
					operResult = new BankOperator();
					operResult.setClientId(clientId);
					operResult.setId(userId);
					//username/password/org_code/status/is_reset/name/role_id/department/position
					operResult.setUsername(operatorRedis.get("username"));
					operResult.setPassword(operatorRedis.get("password"));
					operResult.setOrgCode(operatorRedis.get("org_code"));
					
					operResult.setStatus(BooleanUtils.toBooleanObject(operatorRedis.get("status"), "1", "0", ""));
					operResult.setIsReset(BooleanUtils.toBooleanObject(operatorRedis.get("is_reset"), "1", "0", ""));
					operResult.setName(ObjectUtils.toString(operatorRedis.get("name"),""));
					operResult.setRoleId(Long.parseLong(operatorRedis.get("role_id")));
					operResult.setDepartment(ObjectUtils.toString(operatorRedis.get("department"),""));
					operResult.setPosition(ObjectUtils.toString(operatorRedis.get("position"),""));
					operResult.setRemark(ObjectUtils.toString(operatorRedis.get("remark"),""));
					
					//operResult.setType(ObjectUtils.toString(operatorRedis.get("type"),""));
					//operResult.setLastLoginIp(ObjectUtils.toString(operatorRedis.get("last_login_ip"),""));
					//operResult.setLastLoginTime(Date.parse(ObjectUtils.toString(operatorRedis.get("last_login_time"),"")));
					
					
				}
			}
			
			//缓存中没查到值从mysql数据库中查
			if(Checker.isEmpty(operResult)){
				LogCvt.info("查询数据库，缓存cbbank:bank_user:client_id:user_id中未查到值");
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				bankOperatorMapper = sqlSession.getMapper(BankOperatorCommonMapper.class);
				operResult = bankOperatorMapper.findBankOperatorById(userId); 
				if(Checker.isNotEmpty(operResult)){
					//重新设置Redis缓存
					BankOperatorRedis.set_cbbank_bank_user_client_id_user_id(operResult);
				}
			}
			
			
			
		} catch (Exception e) { 
			LogCvt.error("根据id查询BankOperator失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return operResult; 
	}

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
	@Override
	public Area findAreaById(Long id) {
		SqlSession sqlSession = null;
		AreaCommonMapper areaMapper = null;
		Area area = null;
		try {
			Map<String, String> redisR = SupportRedis.get_cbbank_area_area_id(id);
			if( redisR != null && !redisR.isEmpty() ){
				LogCvt.debug("在缓存中查询到Area信息 id:"+id);
				area = changeMapToArea(redisR);
				area.setId(id);
				return area;
			}
			LogCvt.debug("在缓存中没有查询到Area信息 id:"+id);
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			areaMapper = sqlSession.getMapper(AreaCommonMapper.class);
			area = areaMapper.findAreaById(id);
			if( area == null ){
				area = new Area();// 确保service中copy时不出错
			}else{
				Boolean tr = SupportRedis.set_cbbank_area_client_id_area_id(area);
				LogCvt.debug("Again addArea Redis Operating:"+tr);
			}
		} catch (Exception e) {
			LogCvt.error("获取地区信息失败:"+e.getMessage(), e);
		}finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return area;
	}
	// 把Map中的值传成Area对象
	private Area changeMapToArea(Map<String, String> redisR){
	    LogCvt.debug("changeMapToArea:"+redisR);
		Area area = new Area();
		String clientId = redisR.get("client_id");
		if( clientId != null ){
			area.setClientId(clientId);
		}
		String name = redisR.get("name");
		if( name != null ){
			area.setName(name);
		}
		String vague_letter = redisR.get("vague_letter");
		if( vague_letter != null ){
			area.setVagueLetter(vague_letter);
		}
		String tree_path = redisR.get("tree_path");
		if( tree_path != null ){
			area.setTreePath(tree_path);
		}
		String parent_id = redisR.get("parent_id");
		if( parent_id != null && !"".equals(parent_id)){
			area.setParentId(Long.parseLong(parent_id));
		}
		String is_enable = redisR.get("is_enable");
		if( is_enable != null ){
			area.setIsEnable("1".equals(is_enable));
		}
		String tree_path_name = redisR.get("tree_path_name");
		if( tree_path_name != null ){
			area.setTreePathName(tree_path_name);
		}
		String area_code = redisR.get("area_code");
		if( area_code != null ){
			area.setAreaCode(area_code);
		}
		return area;
	}
	
	/**
	 * 获取上级机构对象
	 * @param clientId 客户端id
	 * @param orgCode 机构编号
	 * @return 上级Org对象
	 */
	@Override
	public Org getSuperOrg(String clientId,String orgCode){
		
		Org org=this.getOrgByOrgCode(orgCode, clientId);
		String orgTop=OrgSuperUtil.getOrgSuper(org);
		
		Org top=new Org();
		//一级机构上级没有，机构对象为null
		if(Checker.isNotEmpty(orgTop)){
			top=this.getOrgByOrgCode(orgTop, clientId);
		}
		
		return top;
	}
	
	/**
	 * 获取VIP商品信息Redis，redis中不存在就从mysql取
	 * @param clientId
	 * @return
	 */
	@Override
	public Map<String,String> getVipProductRedis(String clientId){
		String prodcutKey = RedisKeyUtil.cbbank_vip_product_client_id(clientId);
		Map<String,String> productMap = redis.getMap(prodcutKey);
		//如果redis为空，从mysql取
		if (Checker.isEmpty(productMap)
				|| !productMap.containsKey("vip_id")
				|| !productMap.containsKey("create_time")
				|| !productMap.containsKey("vip_price")
				|| !productMap.containsKey("status")
				|| !productMap.containsKey("count")
				|| !productMap.containsKey("remark")) {
			LogCvt.info("redis获取VIP商品信息查询为空，从mysql查询VIP商品信息，查询条件：clientId:" + clientId);
			VipProduct vipProduct = this.getVipProductInfo(clientId);
            if(Checker.isNotEmpty(vipProduct)){
                try {
                    LogCvt.info("redis获取VIP商品信息查询为空，重新设置缓存");
                    VipRedis.set_cbbank_vip_product_client_id(vipProduct);
                    productMap = VipRedis.get_cbbank_vip_product_client_id(clientId);
                } catch (Exception e) {
                    LogCvt.error("Common设置VIP商品信息缓存失败, 原因:"+e.getMessage(), e);
                }
            }
		}
		return converRedisMap(productMap);
	}
	
	/**
     * 内部方法，从mysql获取VIP商品
     * @param clientId
     * @return Map
     */
    private VipProduct getVipProductInfo(String clientId) {
        SqlSession sqlSession = null;
        ProductCommonMapper mapper = null;
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(ProductCommonMapper.class);
            
            VipProduct vipProduct = mapper.getVipProductByClientId(clientId);
            return vipProduct;
        } catch (Exception e) { 
            LogCvt.error("获取VIP商品信息异常，原因:" + e.getMessage(),e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return null; 
    }

    /**
     * 根据id查询 Client
     * @param clientId 客户端id
     * @return Client对象
     */
    @Override
    public Client getClientById(String clientId) {
        if(Checker.isEmpty(clientId)){
            LogCvt.error("获取客户端详情时clientId为空");
            return null;
        }
        
        Client clientResult = new Client();
        try { 
                
            /**********************操作Redis缓存**********************/
            Map<String,String> clientRedis=ClientRedis.getAll_cbbank_client_client_id(clientId);
            if(Checker.isNotEmpty(clientRedis)){//操作Redis缓存
                LogCvt.debug("查询缓存cbbank:client:"+clientId+"有值"+clientRedis.values());
				clientResult.setClientId(clientId);
				//name/point_partner_no/openapi_partner_no/appkey/appsecret/order_display/return_url/bank_name/qr_logo/bank_type/bank_id
				clientResult.setName(clientRedis.get("name"));
				clientResult.setPointPartnerNo(clientRedis.get("point_partner_no"));
				clientResult.setOpenapiPartnerNo(clientRedis.get("openapi_partner_no"));
				clientResult.setAppkey(ObjectUtils.toString(clientRedis.get("appkey"),""));
				clientResult.setAppsecret(ObjectUtils.toString(clientRedis.get("appsecret"),""));
				clientResult.setOrderDisplay(clientRedis.get("order_display"));
				clientResult.setReturnUrl(ObjectUtils.toString(clientRedis.get("return_url"),""));
				clientResult.setBankName(ObjectUtils.toString(clientRedis.get("bank_name"),""));
				clientResult.setQrLogo(ObjectUtils.toString(clientRedis.get("qr_logo"),""));
				clientResult.setBankType(ObjectUtils.toString(clientRedis.get("bank_type"),""));
				clientResult.setBankId(ObjectUtils.toString(clientRedis.get("bank_id"),""));
				clientResult.setSettlePayOrg(ObjectUtils.toString(clientRedis.get("settle_pay_org"),""));
				clientResult.setBankOrg(ObjectUtils.toString(clientRedis.get("bank_org"),""));	
				clientResult.setBankZH(ObjectUtils.toString(clientRedis.get("bank_ZH"),""));//银行组号			
			}else{

                LogCvt.info("查询数据库，缓存cbbank:client:"+clientId+"中未查到值");
                
                /**********************操作MySQL数据库**********************/
                clientResult  = this.findClientById(clientId); 
                if(Checker.isNotEmpty(clientResult)){
                    //重新设置Redis缓存
                    ClientRedis.set_cbbank_client_client_id(clientResult);
                }
            }
            
        } catch (Exception e) { 
            LogCvt.error("根据id查询Client失败，原因:" + e.getMessage(), e); 
        } 
        return clientResult; 
    }
    
    /**
     * 查询 Client
     * @param client
     * @return List<Client>    结果集合 
     */
    private Client findClientById(String clientId) {

        SqlSession sqlSession = null;
        ClientCommonMapper clientMapper = null;
        Client client = new Client();
        try { 
            /**********************操作MySQL数据库**********************/
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            clientMapper = sqlSession.getMapper(ClientCommonMapper.class);

            client = clientMapper.findClientById(clientId);
            
        } catch (Exception e) { 
            LogCvt.error("从mysql中查询Client详情失败，原因:" + e.getMessage(), e); 
        } finally { 
            if(null != sqlSession)  
                sqlSession.close();  
        } 
        return client; 

    }

    
    /**
     * 查询 ClientPaymentChannel
     * @param clientPaymentChannel
     * @param ll 20150717 挪至到Common包
     * @return List<ClientPaymentChannel>    结果集合 
     */
	@Override
	public List<ClientPaymentChannel> findClientPaymentChannel(ClientPaymentChannel clientPaymentChannel) {

		SqlSession sqlSession = null;
		ClientPaymentChannelCommonMapper clientPaymentChannelMapper = null;
		List<ClientPaymentChannel> result = new ArrayList<ClientPaymentChannel>(); 
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			clientPaymentChannelMapper = sqlSession.getMapper(ClientPaymentChannelCommonMapper.class);
			
			result = clientPaymentChannelMapper.findClientPaymentChannel(clientPaymentChannel); 
			
		} catch (Exception e) { 
			LogCvt.error("查询Mysql数据库中的ClientPaymentChannel失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
	
	/**
     * 根据id查询 ClientPaymentChannel
     * @param clientId 客户端id
     * @param paymentChannelId 支付渠道id
     * @param ll 20150717 挪至到Common包
     * @return ClientPaymentChannel 对象
     */
	@Override
	public ClientPaymentChannel getClientPaymentChannelById(String clientId,String paymentChannelId) {

		if(Checker.isEmpty(clientId) || Checker.isEmpty(paymentChannelId)){
			LogCvt.error("获取客户端支付渠道信息时clientId/paymentChannelId为空");
			return null;
		}
		
		ClientPaymentChannel resultPayment = new ClientPaymentChannel();
		try { 
				
			/**********************操作Redis缓存**********************/
			Map<String, String> redisMap = ClientPaymentChannelRedis.getAll_cbbank_client_channel_client_id_payment_channel_id(clientId,paymentChannelId);
			if(Checker.isNotEmpty(redisMap)){
				LogCvt.debug("查询缓存cbbank:client_channel:"+clientId+":"+paymentChannelId+"有值"+redisMap.values());
				resultPayment.setClientId(clientId);
				resultPayment.setPaymentChannelId(paymentChannelId);
				//name/full_name/type/ico_url/payment_org_no/is_froad_check_token/point_rate
				resultPayment.setName(redisMap.get("name"));
				resultPayment.setFullName(redisMap.get("full_name"));
				resultPayment.setType(redisMap.get("type"));
				resultPayment.setIcoUrl(ObjectUtils.toString(redisMap.get("ico_url"),""));
				resultPayment.setPaymentOrgNo(ObjectUtils.toString(redisMap.get("payment_org_no"),""));
				//redis中bool类型存的0和1
				resultPayment.setIsFroadCheckToken(BooleanUtils.toBooleanObject(redisMap.get("is_froad_check_token"),"1","0",""));
				resultPayment.setPointRate(redisMap.get("point_rate"));
			}else{
				LogCvt.info("查询数据库，缓存cbbank:client_channel:"+clientId+":"+paymentChannelId+"中未查到值");
				/**********************操作MySQL数据库**********************/
				
				ClientPaymentChannel clientPaymentChannel = new ClientPaymentChannel();
				clientPaymentChannel.setClientId(clientId);
				clientPaymentChannel.setPaymentChannelId(paymentChannelId);
				clientPaymentChannel.setIsDelete(false);
				List<ClientPaymentChannel> result = this.findClientPaymentChannel(clientPaymentChannel);
				if(Checker.isNotEmpty(result)){
					resultPayment=result.get(0);
					//重新设置Redis缓存：cbbank:client_channel:client_id:payment_channel_id
					ClientPaymentChannelRedis.set_cbbank_client_channel_client_id_payment_channel_id(resultPayment);
					
					//重新设置Redis缓存：cbbank:client_channels:client_id
					ClientPaymentChannelRedis.set_cbbank_client_channels_client_id(clientPaymentChannel);
					
				}
				
			}
			
		} catch (Exception e) { 
			LogCvt.error("查询ClientPaymentChannel详情失败，原因:" + e.getMessage(), e); 
		}  
		return resultPayment; 
	}
	
	
	/**
	 * 获取该客户端下的支付渠道
	 * @param clientId 客户端Id
	 * @update ll 20150717 挪至到Common包
	 * @return 支付渠道集合
	 */
	@Override
	public List<ClientPaymentChannel> getClientPaymentChannelByClientId(String clientId){
		if(Checker.isEmpty(clientId)){
			LogCvt.error("获取客户端支付渠道信息时clientId为空");
			return null;
		}
		List<ClientPaymentChannel> result = new ArrayList<ClientPaymentChannel>();
		
		String key = RedisKeyUtil.cbbank_client_channels_client_id(clientId);
		Set<String> channelSet = ClientPaymentChannelRedis.get_cbbank_client_channels_client_id(clientId);
		if(channelSet == null || channelSet.size() == 0){  // Set<channelId> 不存在 , 重建Set<channelId>
			LogCvt.info("未能从客户端支付渠道Redis缓存中获取到缓存信息,准备重建缓存");
			ClientPaymentChannel filter = new ClientPaymentChannel();
			filter.setClientId(clientId);
			filter.setIsDelete(false);
			result = this.findClientPaymentChannel(filter);
			if(Checker.isNotEmpty(result)){
				for(ClientPaymentChannel paymentChannel : result){
					
					//重新设置redis
					ClientPaymentChannelRedis.set_cbbank_client_channel_client_id_payment_channel_id(paymentChannel);
					//从Mysql中的clientId下所有的支付渠道方式查出来set到集合中
					ClientPaymentChannelRedis.set_cbbank_client_channels_client_id(paymentChannel);
				}
			}else{
				LogCvt.error("重建 KEY=" + key + "缓存失败: 未能从MySQL中获得原始数据");
			}
			
		}else{ // Set<channelId> 存在 
			//判断redis中的集合是否与mysql中的一致
			ClientPaymentChannel filter = new ClientPaymentChannel();
			filter.setClientId(clientId);
			filter.setIsDelete(false);
			List<ClientPaymentChannel> resultPaymentChannel = this.findClientPaymentChannel(filter);
			if(Checker.isEmpty(resultPaymentChannel)){
				LogCvt.error("重建 KEY=" + key + "缓存失败: 未能从MySQL中获得原始数据");
				return null;
			}
			
			if(channelSet.size()==resultPaymentChannel.size()){
				//取缓存中的Set
				for(String channelId:channelSet){
					result.add(this.getClientPaymentChannelById(clientId, channelId));
				}
			}else{
				LogCvt.error("缓存中的 KEY=" + key + "集合数量与mysql不一致，重建缓存中……");
				ClientPaymentChannel delClientPaymentChannel = null;
				//取缓存中的Set-删除key(避免出现redis集合中有记录而mysql删除的数据)
				for(String channelId:channelSet){
					delClientPaymentChannel = new ClientPaymentChannel();
					delClientPaymentChannel.setClientId(clientId);
					delClientPaymentChannel.setPaymentChannelId(channelId);
					//删除key
					ClientPaymentChannelRedis.del_cbbank_client_channel_client_id_payment_channel_id(delClientPaymentChannel);
					ClientPaymentChannelRedis.srem_cbbank_client_channels_client_id(delClientPaymentChannel);

				}
				
				//数量不符，重建redis
				for(ClientPaymentChannel paymentChannel : resultPaymentChannel){
					
					//重新设置redis
					ClientPaymentChannelRedis.set_cbbank_client_channel_client_id_payment_channel_id(paymentChannel);
					//从Mysql中的clientId下所有的支付渠道方式查出来set到集合中
					ClientPaymentChannelRedis.set_cbbank_client_channels_client_id(paymentChannel);
				}
				result = resultPaymentChannel;
			}
			
			
		}
		
		
		return result;
	}
	
	
	
    /**
     * 根据商品分类id查询分类信息
     */
    @Override
    public ProductCategory findCategoryById(String clientId, Long categoryId) {
        if(Checker.isEmpty(clientId)){
            LogCvt.error("common获取商品分类详情时clientId为空");
            return null;
        }
        ProductCategory productCategory = null;
        
        SqlSession sqlSession = null;
        try{
            Map<String,String> categoryMap = redis.getMap(RedisKeyUtil.cbbank_product_category_client_id_product_category_id(clientId,categoryId));
            if(Checker.isNotEmpty(categoryMap)){
                productCategory=new ProductCategory();
                productCategory.setClientId(clientId);
                productCategory.setId(categoryId);
                productCategory.setName(categoryMap.get("name"));
                productCategory.setParentId(Long.valueOf(categoryMap.get("parent_id")));
                productCategory.setTreePath(categoryMap.get("tree_path"));
                productCategory.setIsDelete(BooleanUtils.toBooleanObject(categoryMap.get("is_delete"), "1", "0", ""));
            }else{
                sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
                ProductCategoryCommonMapper productCategoryMapper = sqlSession.getMapper(ProductCategoryCommonMapper.class);
                
                //从mysql中查询
                productCategory = productCategoryMapper.findProductCategoryById(categoryId);
                
                //重新设置到redis中
                if(productCategory!=null){
                    Map<String, String> hash = new HashMap<String, String>();
                    hash.put("name", ObjectUtils.toString(productCategory.getName(), ""));
                    hash.put("tree_path", ObjectUtils.toString(productCategory.getTreePath(),""));
                    hash.put("parent_id", ObjectUtils.toString(productCategory.getParentId(), ""));
                    hash.put("is_delete", BooleanUtils.toString(productCategory.getIsDelete(), "1", "0", "0"));
                    String key = RedisKeyUtil.cbbank_product_category_client_id_product_category_id(productCategory.getClientId(), productCategory.getId());
                    RedisManager redis = new RedisManager();
                    redis.putMap(key, hash);
                }
            }
        }catch(Exception e){
            LogCvt.error("common查询商品分类失败，原因:" + e.getMessage(),e); 
        }finally{
            if(null != sqlSession) {
                sqlSession.close(); 
            } 
        }
        return productCategory;
    }

	/* (non-Javadoc)
	 * @see com.froad.logic.CommonLogic#get_cbbank_client_bank_outlet_redis(java.lang.String, long, long)
	 */
	@Override
	public Map<String, ProductOutletInfo> get_cbbank_client_bank_outlet_redis(String clientId, long start, long end) {
		String key = RedisKeyUtil.cbbank_bank_outlet_client_id(clientId);
		Set<String> sets = redis.zRange(key, start, end);
		if(sets!=null && sets.size()>0){
			Map<String, ProductOutletInfo> outletMap = new HashMap<String, ProductOutletInfo>();
			ProductOutletInfo outlet = null;
			for (String serStr : sets) {
				outlet = JSON.parseObject(serStr, ProductOutletInfo.class);
				if(outlet!=null && outlet.getId()>0){
					outletMap.put(outlet.getOutletId(), outlet);
				}
			}
			return outletMap;
		} else {//去mysql查
			SqlSession sqlSession = null;
			try {
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				MerchantCommonMapper merchantMapper = sqlSession.getMapper(MerchantCommonMapper.class);
				List<ProductOutletInfo> outlets = merchantMapper.findBankOutletsByClientId(clientId);
				if(outlets!=null && outlets.size()>0){
					Map<String, ProductOutletInfo> outletMap = new HashMap<String, ProductOutletInfo>();
					for(ProductOutletInfo outlet : outlets){
						outletMap.put(outlet.getOutletId(), outlet);
						this.set_cbbank_client_bank_outlet_redis(clientId, outlet, "1");//新加到zset中
					}
					return outletMap;
				}
			} catch (Exception e) {
				LogCvt.error("查询客户端的所有虚拟门店异常"+e, e);
			} finally {
				if(sqlSession != null){
					sqlSession.close(); 
				}
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see com.froad.logic.CommonLogic#set_cbbank_client_bank_outlet_redis(java.lang.String, com.froad.po.ProductOutletInfo)
	 */
	@Override
	public boolean set_cbbank_client_bank_outlet_redis(String clientId, ProductOutletInfo outlet, String flag) {
		String key = RedisKeyUtil.cbbank_bank_outlet_client_id(clientId);
		String member = JSON.toJSONString(outlet);
		Long result = 0L;
		if("1".equals(flag)){
			result = redis.zadd(key, outlet.getId(), member);
		} else if("2".equals(flag)){
			redis.zremrangebyscore(key, outlet.getId(), outlet.getId());
			result = redis.zadd(key, outlet.getId(), member);
		} else if("3".equals(flag)){
			redis.zrem(key, member);
		}
		LogCvt.debug("将虚拟门店更新或新加到缓存的zset中key:[" + key + "]结果:"+result+",缓存数据value[" +member+ "]!");
		if(result>0){
			return true;
		}
		return false;
	}
	
	/**
	 * 根据商户id、门店id查询mysql获取门店信息列表.
	 * 
	 * @param merchantId  商户id
	 * @param outletId    门店
	 * @return List<Outlet>
	 */ 
	@Override
	public List<Outlet> getOutletListByMerchantIdOrOutletId(String merchantId,String outletId) {
		SqlSession sqlSession = null;
		List<Outlet> outletList = null;
		try {
			if(Checker.isNotEmpty(merchantId) || Checker.isNotEmpty(outletId)){
				Outlet outlet = new Outlet();
				outlet.setOutletId(outletId);
				outlet.setMerchantId(merchantId);
				outlet.setIsEnable(true);
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				MerchantCommonMapper merchantCommonMapper = sqlSession.getMapper(MerchantCommonMapper.class);
				outletList = merchantCommonMapper.findOutlet(outlet);
			}
		} catch (Exception e) {
			LogCvt.error("根据商户ID和门店ID返回该门店列表查询异常"+ e.getMessage(), e);
		} finally {
			if (sqlSession != null) {
				sqlSession.close();
			}
		}
		return outletList;
	}

	
	/**
	 *  获取底层orgList信息
	 */
	@Override
	public List<String> queryLastOrgCode(String clientId, List<String> orgCodes) {
		List<Org> orgList = this.getOrgByList(clientId, orgCodes);
		List<String> lastOrgCode = new ArrayList<String>();
		for(Org org : orgList){
			String provinceAgency = org.getProvinceAgency();
			String cityAgency = org.getCityAgency();
			String countyAgency = org.getCountyAgency();
			String orgCode = org.getOrgCode();
			
			lastOrgCode.add(orgCode);
			if(provinceAgency == null || "".equals(provinceAgency)){
				continue;
			}
			
			if(cityAgency == null || "".equals(cityAgency)){
				lastOrgCode.remove(provinceAgency);
				continue;
			}
			
			if(countyAgency == null || "".equals(countyAgency)){
				lastOrgCode.remove(provinceAgency);
				lastOrgCode.remove(cityAgency);
				continue;
			}
			lastOrgCode.remove(provinceAgency);
			lastOrgCode.remove(cityAgency);
			lastOrgCode.remove(countyAgency);
			
		}
		
		return lastOrgCode;
	}

	/**
	 *  获取Redis的商户账号和门店账号
	 */
	@Override
	public MerchantAccount getMerchantAccoutByRedis(String clientId,String merchantId, String outletId) {
		MerchantAccount account = new MerchantAccount();
		try {
			String key = RedisKeyUtil.cbbank_merchant_outlet_account_client_id_merchant_id_outlet_id(clientId, merchantId, outletId);
			Map<String,String> redisMap = redis.getMap(key);
			account.setAcctName(redisMap.get("acct_name"));
			account.setAcctNumber(redisMap.get("acct_number"));
			account.setAcctType(redisMap.get("acct_type"));
		} catch (Exception e) {
			LogCvt.error("获取商户或门店账号Redis异常...", e);
 		}
		return account;
	}

	/**
	 * 根据商品ID查询商品
	 * 
	 */
	@Override
	public Product getProductById(String productId) {
		Product product = null;
		ProductCommonMapper mapper = null;
		SqlSession  sqlSession = null;
        try { 
            sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
            mapper = sqlSession.getMapper(ProductCommonMapper.class);
            
            Map<String,String> param = new HashMap<String, String>();
            param.put("productId", productId);
            List<Product> productList = mapper.getProductByPid(param);
            if(productList != null && !productList.isEmpty()){
            	product = productList.get(0);
            	if(ProductType.group.getCode().equals(product.getType())){
            		List<ProductGroup> group=mapper.getProductGroupByPid(param);
            		ProductGroup pg=group.get(0);
            		if(pg!=null){
            		  product.setExpireStartTime(pg.getExpireStartTime());
            		  product.setExpireEndTime(pg.getExpireEndTime());
            		}
            	}
            	if(ProductType.presell.getCode().equals(product.getType())){
            		List<ProductPresell> group=mapper.getProductPresellByPid(param);
            		ProductPresell pg=group.get(0);
            		if(pg!=null){
            		  product.setExpireStartTime(pg.getExpireStartTime());
            		  product.setExpireEndTime(pg.getExpireEndTime());
            		}
            	}
            	if(product!=null && product.getCategoryTreePath()!=null){
            		String[] cas=product.getCategoryTreePath().split(" ");
            		product.setCategoryId(Long.parseLong(cas[0]));
            	}
            }
		} catch (Exception e) {
			LogCvt.error("根据商品id获取商品异常...", e);
 		} finally{
 			if(sqlSession != null){
 				sqlSession.close();
 			}
 		}
		return product;
	} 
}
