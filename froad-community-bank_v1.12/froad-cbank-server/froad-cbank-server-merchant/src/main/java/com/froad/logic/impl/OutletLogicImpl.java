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
 * @Title: OutletLogicImpl.java
 * @Package com.froad.logic.impl
 * @see: TODO 
 * @author f-road
 * @date 2015年3月17日
 */
package com.froad.logic.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.thrift.TException;

import com.froad.Constants;
import com.froad.common.beans.MerchantExportData;
import com.froad.db.mongo.MerchantDetailMongo;
import com.froad.db.mongo.OutletDetailMongo;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.MerchantAccountMapper;
import com.froad.db.mysql.mapper.OutletMapper;
import com.froad.db.redis.OutletRedis;
import com.froad.enums.AccountAuditState;
import com.froad.enums.AccountSynchState;
import com.froad.enums.MerchantDisableStatusEnum;
import com.froad.enums.OutletDisableStatusEnum;
import com.froad.enums.ProductAuditState;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.AreaLogic;
import com.froad.logic.CommonLogic;
import com.froad.logic.MerchantUserLogic;
import com.froad.logic.OutletLogic;
import com.froad.monitor.OutletMonitor;
import com.froad.po.Area;
import com.froad.po.Merchant;
import com.froad.po.MerchantAccount;
import com.froad.po.MerchantUser;
import com.froad.po.Outlet;
import com.froad.po.OutletPrefer;
import com.froad.po.ProductOutletInfo;
import com.froad.po.Result;
import com.froad.po.mongo.CategoryInfo;
import com.froad.po.mongo.Location;
import com.froad.po.mongo.MerchantDetail;
import com.froad.po.mongo.OutletDetail;
import com.froad.po.mongo.OutletDetailSimpleInfo;
import com.froad.support.DataPlatLogSupport;
import com.froad.support.Support;
import com.froad.thread.FroadThreadPool;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.SenseWordsService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.util.BeanUtil;
import com.froad.util.ClientUtil;
import com.froad.util.DateUtil;
import com.froad.util.GeoUtil;
import com.froad.util.JSonUtil;
import com.froad.util.MD5Util;
import com.froad.util.RedisKeyUtil;
import com.froad.util.ThriftConfig;

/**
 * 
 * <p>@Title: OutletLogic.java</p>
 * <p>Description: 描述 </p> 
 * @author f-road 
 * @version 1.0
 * @created 2015年3月17日
 */
public class OutletLogicImpl implements OutletLogic {
//	private MerchantLogic merchantLogic = new MerchantLogicImpl();
	private MerchantDetailMongo merchantDetailMongo = new MerchantDetailMongo();
	private OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
	private AreaLogic areaLogic = new AreaLogicImpl();
	private Support support = new Support();
	private static final String COMMENT_DESC_EXIST_SENSE = "您录入的门店相关字段信息出现敏感词！";
    /**
     * 增加 Outlet
     * @param outlet
     * @return outletId 门店编号
     */
	@Override
	public String addOutlet(Outlet outlet)  throws FroadServerException, Exception{

		String result = null; 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			if(!outlet.getOutletStatus())
				checkSensitiveWordByObject(outlet);//敏感词过滤
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

//			Outlet outletCNT = new Outlet();
//			outletCNT.setOutletName(outlet.getOutletName());
//			outletCNT.setOutletFullname(outlet.getOutletFullname());
//			outletCNT.setIsEnable(true);
//			
//			Integer count = outletMapper.countOutlet(outletCNT);
//			if(count > 0) {
//				throw new FroadServerException("门店已经存在!");
//			}
			
			
			
			Merchant merchant = new MerchantLogicImpl().findMerchantByMerchantId(outlet.getMerchantId());
			if(null == merchant) {
				throw new FroadServerException("商户不存在!");
			}			
			if(MerchantDisableStatusEnum.disable.getCode().equals(merchant.getDisableStatus())) {
				throw new FroadServerException("商户已经被禁用!");
			}
			if(MerchantDisableStatusEnum.unregistered.getCode().equals(merchant.getDisableStatus())) {
				throw new FroadServerException("商户已经被解约!");
			}
			if(!ProductAuditState.passAudit.getCode().equals(merchant.getAuditState())) {
				throw new FroadServerException("商户还未通过审核!");
			}
			if(false == merchant.getIsEnable()) {
				throw new FroadServerException("商户不可用!");
			}
			if(!merchant.getMerchantStatus().equals(outlet.getOutletStatus())) {
				if(outlet.getOutletStatus()) 
					throw new FroadServerException("所属商户必须为银行虚拟商户!");	
				else
					throw new FroadServerException("所属商户必须为普通商户!");
			}
			Long areaId = outlet.getAreaId();
			//迭代1.7.0门店优化--查看是否第一次添加的门店,第一次添加自动为默认   XXXX已修改为第一个审核通过门店才为默认门店
//			if(isFirstTimeAdd(outletMapper,outlet.getMerchantId())){
//				outlet.setIsDefault(true);
//			}else{
//			}
				outlet.setIsDefault(false);
			Area area = areaLogic.findAreaById(areaId);
			if(null == area || StringUtils.isBlank(area.getName())) {
				throw new FroadServerException("地区不存在!");
			}
			
			if (outletMapper.addOutlet(outlet) > -1) { 
				LogCvt.info("MySQL添加outlet成功");
				result = outlet.getOutletId(); 

				/**********************操作Mongodb数据库**********************/
				if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加mongo表
					MerchantDetail merchantDetail = merchantDetailMongo.findMerchantDetailById(outlet.getMerchantId()); // 查询商户详情
					if(null == merchantDetail) {
						throw new FroadServerException("商户详细信息不存在!");
					}
					
					LogCvt.info("普通门店(非银行机构对应的门店)cb_merchant_detail表中添加门店id");
					merchantDetailMongo.addOutlet(outlet);// Mongo中商户详情 添加门店信息
	
	
					LogCvt.info("普通门店(非银行机构对应的门店)添加cb_outlet_detail表");
					OutletDetail outletDetail = new OutletDetail();
					
					outletDetail = (OutletDetail) BeanUtil.copyProperties(OutletDetail.class, outlet);
					
					outletDetail.setId(outlet.getOutletId());
					outletDetail.setParentAreaId(area.getParentId()); // 设置上级地区id
					
					Location location = new Location();
					location.setLongitude(Double.parseDouble(outlet.getLongitude()));
					location.setLatitude(Double.parseDouble(outlet.getLatitude()));
					
					outletDetail.setLocation(location);
					
					outletDetail.setMerchantName(merchantDetail.getMerchantName());
					
					if(outlet.getCategoryInfo()==null || outlet.getCategoryInfo().size()==0){
						outletDetail.setCategoryInfo(merchantDetail.getCategoryInfo());
					}else{
						outletDetail.setCategoryInfo(outlet.getCategoryInfo());
					}
					outletDetail.setTypeInfo(merchantDetail.getTypeInfo());
					
					//录入门店冗余“优惠折扣码、优惠折扣比”
					if(StringUtils.isNotBlank(outlet.getDiscountCode())){
						outletDetail.setDiscountCode(outlet.getDiscountCode());
					}
					if(StringUtils.isNotBlank(outlet.getDiscountRate())){
						outletDetail.setDiscountRate(outlet.getDiscountRate());
					}
					
//					outletDetailMongo.addOutletDetail(outlet, merchantDetail); // 添加门店详情信息
					outletDetailMongo.addOutletDetail(outletDetail); // 添加门店详情信息
				}
				/**********************操作Redis缓存**********************/
				/* 设置排序缓存 */
	//			OutletRedis.set_cbbank_merchant_outlet_client_id_merchant_id(outlet); /*不需要这个缓存了*/
				/* 设置全部缓存 */
				LogCvt.info("设置门店全部缓存");
				OutletRedis.set_cbbank_outlet_client_id_merchant_id_outlet_id(outlet);
				
				if(outlet.getOutletStatus()) {
					LogCvt.info("设置银行机构虚拟门店缓存");
					OutletRedis.set_cbbank_bank_outlet_client_id_org_code(merchant.getOrgCode(), outlet);
					setBankOutlet(merchant.getOrgCode(),outlet,"1");
				}
				
//				if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加地理位置缓存
//					/* 设置门店地理位置缓存 */
//					LogCvt.info("普通门店(非银行机构对应的门店)设置地理位置缓存");
//					OutletPositionModelRedis.setCache(outlet);
//				}
				sqlSession.commit(true); 
				new DataPlatLogSupport().addOutletLog(outlet.getClientId(),outlet.getOutletId());
				
			} else {
				sqlSession.rollback(true); 
			} 
		} catch (FroadServerException e) { 
			result = null;
			if(null != sqlSession)  
				sqlSession.rollback(true); 
//			LogCvt.error("添加Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			result = null;
			if(null != sqlSession)  
				sqlSession.rollback(true); 
//			LogCvt.error("添加Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	public boolean isFirstTimeAdd(OutletMapper outletMapper,String merchantId){
		Outlet outlet=new Outlet();
		outlet.setMerchantId(merchantId);
		Integer num=outletMapper.countOutlet(outlet);
		if(num!=null&&num>0){
			return false;
		}else{
			return true;
		}
	}
	public void setBankOutlet(String orgCode,Outlet outlet,String flag){
		CommonLogic logic = new CommonLogicImpl(); 
		ProductOutletInfo info = new ProductOutletInfo();
		info.setId(outlet.getId());
		info.setOutletId(outlet.getOutletId());
		info.setOutletName(outlet.getOutletName());
		info.setAddress(outlet.getAddress());
		info.setAreaId(outlet.getAreaId());
		info.setOrgCode(orgCode);
		info.setPhone(outlet.getPhone());
		logic.set_cbbank_client_bank_outlet_redis(outlet.getClientId(),info,flag);
	}
	
	
	/**
	 * 敏感词校验.
	 */
	@Override
	public void checkSensitiveWordByObject(Object o) throws Exception {

		Field[] fs = o.getClass().getDeclaredFields();

		Class<? extends Object> cla = o.getClass();

		String methodName = null;
		Object returnValue = null;

		String fieldName = "";
		Method method = null;
		for (Field field : fs) {
			field.getName();
			field.setAccessible(true);

			fieldName = field.getName();

			try {
				if ("outletName".equals(fieldName)
						|| "outletFullname".equals(fieldName)
						|| "outletFullName".equals(fieldName)
						|| "description".equals(fieldName)
						|| "preferDetails".equals(fieldName)
						|| "address".equals(fieldName)) {

					methodName = "get"
							+ fieldName.substring(0, 1).toUpperCase()
							+ fieldName.substring(1);

					method = cla.getDeclaredMethod(methodName, null);
					returnValue = method.invoke(o, null);
//					System.out.println(returnValue);

					if (null != returnValue && isChineseChar(returnValue.toString())) {
						SenseWordsService.Iface swService = (SenseWordsService.Iface) ThriftClientProxyFactory
								.createIface(
										SenseWordsService.class.getName(),
										SenseWordsService.class.getSimpleName(),
										ThriftConfig.SUPPORT_SERVICE_HOST,
										ThriftConfig.SUPPORT_SERVICE_PORT);
						ResultVo isSense = swService
								.isContaintSensitiveWord(returnValue.toString());
						if (isSense.getResultCode().equals(
								ResultCode.failed.getCode())) {
							LogCvt.info(COMMENT_DESC_EXIST_SENSE + "敏感词:"
									+ isSense.getResultDesc());
							throw new FroadServerException(
									COMMENT_DESC_EXIST_SENSE + "敏感词:"
											+ isSense.getResultDesc());
						}
					}
				}

			} catch (Exception e) {
				throw e;
			}

		}
	}
	
	public  boolean isChineseChar(String str){
        boolean temp = false;
        Pattern p=Pattern.compile("[\u4e00-\u9fa5]"); 
        Matcher m=p.matcher(str); 
        if(m.find()){ 
            temp =  true;
        }
        return temp;
    }

	/**
     * 批量增加 Outlet
     * @param list<outlet>
     * @return list<Result>    结果集
     * 
     * @param outletList
     */
	@Override
	public List<Result> addOutletByBatch(List<Outlet> outletList)  throws FroadServerException, Exception{
		// TODO Auto-generated method stub
		List<Result> resultList = new ArrayList<Result>();
		String id = null;
		for( Outlet outlet : outletList ){
			id = this.addOutlet(outlet);
			Result result = new Result();
			if( id != null ){
				result.setResultCode(ResultCode.success.getCode());
			}else{
				result.setResultCode(ResultCode.failed.getCode());
			}
			result.setResultDesc(outlet.getOutletName());
			resultList.add(result);
		}
		return resultList;
	}

	/**
	 * 删除 指定商户id下的Outlet
	 * @param merchantId
	 * @return
	 * @throws FroadServerException, Exception    
	 * @see com.froad.logic.OutletLogic#deleteOutlet(java.lang.String)
	 */
	@Override
	public Boolean deleteOutlet(String merchantId)  throws FroadServerException, Exception {
		Boolean result = null; 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			
			Outlet outlet = new Outlet();
			outlet.setMerchantId(merchantId);
			outlet.setIsEnable(true);
			List<Outlet> list = outletMapper.findOutlet(outlet);
			for (Outlet o : list) {
				this.deleteOutlet(o, OutletDisableStatusEnum.delete);
			}
			result = true; 
		} catch (FroadServerException e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true); 
//			LogCvt.error("删除Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true); 
//			LogCvt.error("删除Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}
	
    /**
     * 删除 Outlet
     * @param outlet
     * @return Boolean    是否成功
     */
	@Override
	public Boolean deleteOutlet(Outlet outlet, OutletDisableStatusEnum outletDisableStatusEnum)  throws FroadServerException, Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

//			outlet.setDisableStatus(outletDisableStatusEnum.getCode());
//			result = outletMapper.deleteOutlet(outlet); 
			//迭代1.7.0默认门店不允许删除
//			if(isDefaultOultet(outletMapper,outlet.getOutletId())){
//				LogCvt.info("门店ID为"+outlet.getOutletId()+"的门店是默认门店,不允许删除");	
//				return false;
//			}
			Outlet outletDelete = new Outlet();
			outletDelete.setOutletId(outlet.getOutletId());
			outletDelete.setMerchantId(outlet.getMerchantId());
			
			outletDelete.setIsEnable(false);
			outletDelete.setDisableStatus(outletDisableStatusEnum.getCode());
			
			result = outletMapper.updateOutlet(outletDelete);
			if(result) {
				LogCvt.info("MySQL删除outlet成功");

				/**********************操作Mongodb数据库**********************/
				List<Outlet> list = findOutlet(outlet);
				for (int i = 0; i < list.size(); i++) {
					outlet = list.get(i);
					if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加mongo表
						/*删除商户详细信息*/
						LogCvt.info("普通门店(非银行机构对应的门店)cb_merchant_detail表中删除门店id");
						merchantDetailMongo.deleteOutlet(outlet.getMerchantId(), outlet.getOutletId()); // 删除门店
						
						
						LogCvt.info("普通门店(非银行机构对应的门店)删除cb_outlet_detail表");
						outletDetailMongo.deleteOutletDetail(outlet.getOutletId()); // 修改门店详情信息
					}
					
					/**********************操作Redis缓存**********************/
					/* 设置排序缓存 */
//					outlet = outletMapper.findOutletByOutletId(outlet.getOutletId());
					
		//			OutletRedis.del_cbbank_merchant_outlet_client_id_merchant_id(outlet);/*不需要这个缓存了*/
					
					/* 设置全部缓存 */
					LogCvt.info("删除门店信息缓存");
					OutletRedis.del_cbbank_outlet_client_id_merchant_id_outlet_id(outlet);
					
//					if (outlet.getOutletStatus()) {
//						LogCvt.info("删除银行机构虚拟门店缓存");
//						Merchant merchant = merchantLogic.findMerchantByMerchantId(outlet.getMerchantId());
//						if (null != merchant && StringUtils.isNotBlank(merchant.getOrgCode()))
//							OutletRedis.del_cbbank_bank_outlet_client_id_org_code(outlet.getClientId(), merchant.getOrgCode());
//					}
					
					/* 删除门店地理位置缓存 */
//					if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加地理位置缓存
//						LogCvt.info("删除地理位置缓存");
//						OutletPositionModelRedis.delCache(outlet);
//					}
				}			
				
				sqlSession.commit(true); 
				new DataPlatLogSupport().deleteOutletLog(outlet.getClientId(),outlet.getOutletId());
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);
//			LogCvt.error("删除Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);
//			LogCvt.error("删除Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	public Boolean isDefaultOultet(OutletMapper outletMapper,String outletId){
		  Outlet outlet=   outletMapper.findOutletByOutletId(outletId);
		  if(outlet.getIsDefault()){
			  return true;
		  }else{
			  return false;
		  }
	}

	public Boolean deleteOutletByMerchantId(String merchantId , OutletDisableStatusEnum outletDisableStatusEnum) throws FroadServerException, Exception{
		Outlet outlet = new Outlet();
		outlet.setMerchantId(merchantId);
		return this.deleteOutlet(outlet, outletDisableStatusEnum);
//		return null;
	}
	public Boolean deleteOutletByMerchantId(String merchantId) throws FroadServerException, Exception{
		Outlet outlet = new Outlet();
		outlet.setMerchantId(merchantId);
		return this.deleteOutlet(outlet, OutletDisableStatusEnum.delete);
//		return null;
	}
	
	public Boolean disableOutletByMerchantId(String merchantId) throws FroadServerException, Exception{
		Outlet outlet = new Outlet();
		outlet.setMerchantId(merchantId);
		outlet.setIsEnable(true);
		return disableOutlet(outlet);
	}
	public Boolean disableOutletByOutletId(String outletId) throws FroadServerException, Exception{
		Outlet outlet = new Outlet();
		outlet.setOutletId(outletId);
		return this.deleteOutlet(outlet, OutletDisableStatusEnum.disable);
//		return null;
	}
	
	/**     
	 * 物理删除 Outlet
	 * @param outletId
	 * @return
	 * @throws FroadServerException, Exception    
	 * @see com.froad.logic.OutletLogic#removeOutlet(java.lang.String)    
	 */	
	@Override
	public Boolean removeOutlet(String outletId) throws FroadServerException,Exception {
		Boolean result = null; 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			
			if (StringUtils.isBlank(outletId)) {
				throw new FroadServerException("门店id不能为空");
			}
			Outlet outlet = outletMapper.findOutletByOutletId(outletId);
			
			result = outletMapper.removeOutlet(outletId); 
			if(result) {
				LogCvt.info("MySQL删除outlet成功");

				/**********************操作Mongodb数据库**********************/
//				outlet = outletMapper.findOutletByOutletId(outlet.getOutletId());
				
				if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加mongo表
					/*删除商户详细信息*/
					LogCvt.info("普通门店(非银行机构对应的门店)cb_merchant_detail表中删除门店id");
					merchantDetailMongo.deleteOutlet(outlet.getMerchantId(), outlet.getOutletId()); // 删除门店
					
					LogCvt.info("普通门店(非银行机构对应的门店)删除cb_outlet_detail表");
					outletDetailMongo.deleteOutletDetail(outlet.getOutletId()); // 修改门店详情信息
				}
				
				/**********************操作Redis缓存**********************/
				/* 设置排序缓存 */
//				outlet = outletMapper.findOutletByOutletId(outlet.getOutletId());
				
	//			OutletRedis.del_cbbank_merchant_outlet_client_id_merchant_id(outlet);/*不需要这个缓存了*/
				
				/* 设置全部缓存 */
				LogCvt.info("物理删除门店信息缓存");
				OutletRedis.remove_cbbank_outlet_client_id_merchant_id_outlet_id(outlet);
				
				Merchant merchant = new MerchantLogicImpl().findMerchantByMerchantId(outlet.getMerchantId());
				if(merchant != null)
					setBankOutlet(merchant.getOrgCode(), outlet, "3");
				
//				if (outlet.getOutletStatus()) {
//					LogCvt.info("物理删除银行机构虚拟门店缓存");
//					Merchant merchant = merchantLogic.findMerchantByMerchantId(outlet.getMerchantId());
//					if (null != merchant && StringUtils.isNotBlank(merchant.getOrgCode()))
//						OutletRedis.remove_cbbank_bank_outlet_client_id_org_code(outlet.getClientId(), merchant.getOrgCode());
//				}
				
				/* 删除门店地理位置缓存 */
//				if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加地理位置缓存
//					LogCvt.info("删除地理位置缓存");
//					OutletPositionModelRedis.delCache(outlet);
//				}
				sqlSession.commit(true); 
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);
//			LogCvt.error("物理删除Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);
//			LogCvt.error("物理删除Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	
	}
	
	
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
	@Override	
	public Boolean enableOutletByMerchantId(String merchantId) throws FroadServerException, Exception{
		Boolean result;
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

			Outlet where = new Outlet();
			where.setMerchantId(merchantId);
			
			List<Outlet> list = outletMapper.findOutlet(where); 
			if(CollectionUtils.isEmpty(list)) {
				LogCvt.info("根据商户id启用门店,merchantId=" + merchantId + "无对应门店!");
				return true;
//				throw new FroadServerException("门店不存在!");
			}
			
			for (Outlet dbOutlet : list) {
				if(dbOutlet.getDisableStatus().equals(OutletDisableStatusEnum.delete.getCode())) { // 过滤掉已经删除的门店
					continue;					
				}
				Outlet outlet = new Outlet();
				outlet.setOutletId(dbOutlet.getOutletId());
				
				outlet.setIsEnable(true);
				outlet.setDisableStatus(OutletDisableStatusEnum.normal.getCode());
				
				result = this.updateOutlet(outlet);
				
			}
			result = true;
			// sqlSession.commit(true);
		} catch (FroadServerException e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
//			LogCvt.error("启用Outlet失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
//			LogCvt.error("启用Outlet失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}
	
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
	@Override	
	public Boolean enableOutletByOutletId(String outletId) throws FroadServerException, Exception{
		Boolean result;
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

			Outlet dbOutlet = outletMapper.findOutletByOutletId(outletId); 
			if(null == dbOutlet) {
				LogCvt.info("根据门店id启用门店,outletId=" + outletId + "无对应门店!");
				return true;
//				throw new FroadServerException("门店不存在!");
			}
			if(dbOutlet.getDisableStatus().equals(OutletDisableStatusEnum.delete.getCode())) {
				throw new FroadServerException("门店已经删除不能启用!");
			}
			
			Outlet outlet = new Outlet();
			outlet.setOutletId(outletId);
			
			outlet.setIsEnable(true);
			outlet.setDisableStatus(OutletDisableStatusEnum.normal.getCode());
			
			result = this.updateOutlet(outlet);
			// sqlSession.commit(true);
		} catch (FroadServerException e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
//			LogCvt.error("启用Outlet失败，原因:" + e.getMessage(), e); 
			throw e;
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
//			LogCvt.error("启用Outlet失败，原因:" + e.getMessage(), e); 
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		
		return result;
	}

    /**
     * 修改 Outlet
     * @param outlet
     * @return Boolean    是否成功
     */
	@Override
	public Boolean updateOutlet(Outlet outlet)  throws FroadServerException, Exception{

		Boolean result = null; 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			
			checkSensitiveWordByObject(outlet);//敏感词过滤
			
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

			result = outletMapper.updateOutlet(outlet); 
			
			List<CategoryInfo> ciList = outlet.getCategoryInfo();
					
			
			if(result) {
				LogCvt.info("MySQL修改outlet成功");

				/**********************操作Mongodb数据库**********************/
				outlet = outletMapper.findOutletByOutletId(outlet.getOutletId());
				if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加mongo表
					MerchantDetail merchantDetail = merchantDetailMongo.findMerchantDetailById(outlet.getMerchantId()); // 查询商户详情
					if(ciList!=null && ciList.size()>0){
						outlet.setCategoryInfo(ciList);
					}
					LogCvt.info("普通门店(非银行机构对应的门店)修改cb_outlet_detail表");
					outletDetailMongo.updateOutletDetail(outlet, merchantDetail); // 修改门店详情信息
					
				}
	
				/**********************操作Redis缓存**********************/
				/* 设置排序缓存 */
	//			OutletRedis.set_cbbank_merchant_outlet_client_id_merchant_id(outlet);/*不需要这个缓存了*/
				
				/* 设置全部缓存 */
				LogCvt.info("修改门店信息缓存");
				OutletRedis.set_cbbank_outlet_client_id_merchant_id_outlet_id(outlet);
				
				if(outlet.getOutletStatus()){
					Merchant merchant = new MerchantLogicImpl().findMerchantByMerchantId(outlet.getMerchantId());
					if(merchant != null)
						setBankOutlet(merchant.getOrgCode(),outlet,"2");
				}
				
//				if(outlet.getOutletStatus()) {
//					LogCvt.info("设置银行机构虚拟门店缓存");
//					Merchant merchant = merchantLogic.findMerchantByMerchantId(outlet.getMerchantId());
//					if (null != merchant && StringUtils.isNotBlank(merchant.getOrgCode()))
//						OutletRedis.set_cbbank_bank_outlet_client_id_org_code(merchant.getOrgCode(), outlet);
//				}
				
//				if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加地理位置缓存
	//				/* 设置门店地理位置缓存 */
	//				LogCvt.info("普通门店(非银行机构对应的门店)设置地理位置缓存");
	//				OutletPositionModelRedis.setCache(outlet);
//				}
				sqlSession.commit(true); 
				new DataPlatLogSupport().updateOutletLog(outlet.getClientId(),outlet.getOutletId());
			} else {
				sqlSession.rollback(true); 
			}
		} catch (FroadServerException e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);
//			LogCvt.error("修改Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} catch (Exception e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);
//			LogCvt.error("修改Outlet失败，原因:" + e.getMessage(), e);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}


    /**
     * 查询 Outlet
     * @param outlet
     * @return List<Outlet>    结果集合 
     */
	@Override
	public List<Outlet> findOutlet(Outlet outlet) {

		List<Outlet> result = new ArrayList<Outlet>(); 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

			result = outletMapper.findOutlet(outlet); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			LogCvt.error("查询Outlet失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
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
	public List<Outlet> findSubBankOutlet(String clientId,String orgCode) {

		List<Outlet> result = new ArrayList<Outlet>(); 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

			result = outletMapper.findSubBankOutlet(clientId,orgCode); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			LogCvt.error("查询提货网点(预售用到) 失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}
	
	/**
     * 统计 Outlet
     * @param outlet
     * @return Integer
     */
	@Override
	public Integer countOutlet(Outlet outlet) {

		Integer result = -1; 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

			result = outletMapper.countOutlet(outlet); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			LogCvt.error("统计Outlet失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 

	}

	/**
	 * 查询银行网点对应的虚拟门店
	 * @param clientId
	 * @param orgCode
	 * @return    
	 * @see com.froad.logic.OutletLogic#findBankOutlet(java.lang.String, java.lang.String)
	 */
	@Override
	public Outlet findBankOutlet(String clientId, String orgCode) {
		Outlet outlet = null;
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作Redis缓存**********************/
			Map<String, String> map = OutletRedis.get_cbbank_bank_outlet_client_id_org_code(clientId, orgCode);
			if (null != map && !map.isEmpty()) {
				LogCvt.info("银行虚拟门店缓存存在");
				outlet = new Outlet();
				outlet.setClientId(clientId);
				outlet.setMerchantId(map.get("merchant_id"));
				outlet.setOutletId(map.get("outlet_id"));
				outlet.setOutletName(map.get("outlet_name"));
				outlet.setIsEnable(BooleanUtils.toBooleanObject(map.get("is_enable"), "1", "0", null));
				outlet.setDisableStatus(map.get("disable_status"));
			} else {
				LogCvt.info("银行虚拟门店缓存不存在, 查询数据库");
				/**********************操作Mongodb数据库**********************/
	
				/**********************操作MySQL数据库**********************/
				sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
				outletMapper = sqlSession.getMapper(OutletMapper.class);
	
				outlet = outletMapper.findBankOutlet(clientId,orgCode); 
				
				OutletRedis.set_cbbank_bank_outlet_client_id_org_code(orgCode, outlet);
			}
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("查询银行网点对应的虚拟门店  失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return outlet; 
	}
	
	/**   
	 * 查询预售商品提货网点  
	 * @param merchantIdList
	 * @return    
	 * @see com.froad.logic.OutletLogic#findBankOutlet(java.util.List)    
	 */
	
	public List<Outlet> findBankOutlet(List<String> merchantIdList) {
		List<Outlet> result = new ArrayList<Outlet>(); 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);

			result = outletMapper.findBankOutletByMerchantIdList(merchantIdList); 
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("查询预售商品提货网点  失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return result; 
	}


    /**
     * 分页查询 Outlet
     * @param page
     * @param outlet
     * @return Page<Outlet>    结果集合 
     */
	@Override
	public Page<Outlet> findOutletByPage(Page<Outlet> page, Outlet outlet, LinkedHashMap<String, String> orderBy) {

		List<Outlet> result = new ArrayList<Outlet>(); 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作Redis缓存**********************/

			/**********************操作Mongodb数据库**********************/

			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			//迭代1.7.0按照默认门店排序
			orderBy=new LinkedHashMap<String, String>();
			result = outletMapper.findByPage(page, outlet, orderBy); 
			page.setResultsContent(result);
			// sqlSession.commit(true);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true); 
			LogCvt.error("分页查询Outlet失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 

	}
	
	
	public OutletDetail setOutletDetail(OutletDetail outletDetail){
		 if(outletDetail.getParentAreaId() != null && outletDetail.getParentAreaId()>0){
	            Area area = areaLogic.findAreaById(outletDetail.getParentAreaId());
	            if(area!=null){
	              //最后的节点
	                int level = 0;
	                if(area.getTreePath()!=null){
	                    String[] areas = area.getTreePath().split(",");
	                    level = areas.length;
	                }
	                if(level==3){//三级代表区
	                	outletDetail.setAreaId(outletDetail.getParentAreaId());
	                	outletDetail.setParentAreaId(null);//地区ID
	                	
	                } 
	            } 
	        }
		 return outletDetail;
	}
	
	/**
	 * 分页查询门店详情
	 * @Title: findOutletDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param outlet
	 * @param distace
	 * @return
	 * @return Page<Outlet>    返回类型 
	 * @throws
	 */
	public MongoPage findOutletDetailByPage(MongoPage mongoPage, OutletDetail outletDetail) {
		try {
//			MongoPage mongoPage = new MongoPage();
//			mongoPage.setCurrentPage(page.getPageNumber());
//			mongoPage.setPageSize(page.getPageSize());
			mongoPage.setSort(new Sort("store_count", OrderBy.DESC));
			

			setOutletDetail(outletDetail);
			
			mongoPage = outletDetailMongo.findOutletDetailByPage(mongoPage, outletDetail);

//			page.setPageCount(mongoPage.getTotalPage());
//			page.setTotalCount(mongoPage.getTotalNumber());
//			page.setPageCount(mongoPage.getTotalPage());
//			page.setResultsContent((List<OutletDetail>) mongoPage.getItems());
		} catch (Exception e) {
			LogCvt.error("分页查询门店详情，原因:" + e.getMessage(), e);
		}
		return mongoPage;
	}
	
	
	/**
	 * 分页查询最热门店详情(个人h5用到)
	 * @Title: findOutletDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param outlet
	 * @param distace
	 * @return
	 * @return Page<Outlet>    返回类型 
	 * @throws
	 */
	public MongoPage findHottestOutletDetailByPage1(MongoPage mongoPage,final OutletDetail outletDetail) {
		final List<OutletDetailSimpleInfo> list = new ArrayList<OutletDetailSimpleInfo>();
		try {
//			MongoPage mongoPage = new MongoPage();
//			mongoPage.setCurrentPage(page.getPageNumber());
//			mongoPage.setPageSize(page.getPageSize());

//			mongoPage = outletDetailMongo.findHottestOutletDetailByPage(mongoPage, mongoPage = outletDetailMongo.findHottestOutletDetailByPage(mongoPage, outletDetail););

//			page.setPageCount(mongoPage.getTotalPage());
//			page.setTotalCount(mongoPage.getTotalNumber());
//			page.setPageCount(mongoPage.getTotalPage());
//			page.setResultsContent((List<OutletDetail>) mongoPage.getItems());

//			LogCvt.info("附近搜索门店传入的PO==>" + JSON.toJSONString(outletDetail));
//			final String client_id = ObjectUtils.toString(outletDetail.getClientId(), "");
//			final String parent_area_id = ObjectUtils.toString(outletDetail.getParentAreaId(), "");
//			List<CategoryInfo> cis = outletDetail.getCategoryInfo();
//			final StringBuilder category_id = new StringBuilder(""); // 分类id
//			if(CollectionUtils.isNotEmpty(cis)) {
//				for (CategoryInfo categoryInfo : cis) {
//					if(null != categoryInfo && categoryInfo.getCategoryId() != null) {
//						category_id.append(categoryInfo.getCategoryId());
//					}
//				}
//			}
			
//			String outletName = outletDetail.getOutletName();
//			final String condition = ObjectUtils.toString(outletName, ""); // 只按照门店简称查询
			
			String json = JSonUtil.toJSonString(outletDetail);
			LogCvt.info("搜索最热门店传入的查询条件:" + json);
			final String condition = MD5Util.getMD5Str(json);
			LogCvt.info("搜索最热门店传入的查询条件MD5串:" + condition);
			
			mongoPage.setHasNext(true);
			
			Integer pageSize = mongoPage.getPageSize();
			pageSize = null == pageSize ? 10 : pageSize; // 默认10条
			
			int start = (mongoPage.getPageNumber() - 1) * pageSize;
			int end = start + pageSize;
			
			List<OutletDetailSimpleInfo> odsList = new ArrayList<OutletDetailSimpleInfo>();
			
			final int queryMongodbDataCount = 100; // 一次性从mongodb查询1000条数据
			final int index = end <= queryMongodbDataCount ? 1 : (int) ((end / queryMongodbDataCount) + (end % queryMongodbDataCount > 0 ? 1 : 0)); // 向mongodb第几次查询queryMongodbDataCount条数据
			
			
			final String key = RedisKeyUtil.cbbank_hottest_outlet_condition(index, condition);
			String lockKey = RedisKeyUtil.cbbank_hottest_outlet_lock_index_condition(index, condition);
			long lockResult = OutletRedis.setLock(lockKey); // 将 key 的值设为 value ，当且仅当 key 不存在。 设置成功，返回 1 。设置失败，返回 0 。
			
			if(lockResult == 1) {// key不存在   此次设置成功   说明原来没设置过这个值   需要查询mongodb
				LogCvt.info("搜索最热门店key[" + key + "]是第一次查询, 查询mongodb");
				
				long startTime = System.currentTimeMillis();
				LogCvt.info("搜索最热门店mongodb查询[" + queryMongodbDataCount + "]条数据[开始...]");
				list.addAll(outletDetailMongo.findHottestOutletDetailByPage(outletDetail, start, end)); // 查询mongodb1000条
				long endTime = System.currentTimeMillis();
				
				OutletMonitor.sendHottestOutletMongodbConsumingTime(endTime - startTime);// 发送业务监控
				
				LogCvt.info("搜索最热门店mongodb查询[" + queryMongodbDataCount + "]条数据[结束...], 耗时:" + (endTime - startTime) + "毫秒!");
				
				if (CollectionUtils.isEmpty(list)) {
					mongoPage.setHasNext(false);
					return mongoPage;
				}
				mongoPage.setTotalCount(list.size()); // 设置总页数
				
				odsList = new ArrayList<OutletDetailSimpleInfo>();
				int mongoDataSize = list.size(); // 获取mongodb数据list大小
				int loop = pageSize > mongoDataSize ? mongoDataSize : pageSize; // 取最小的集合
				for (int i = 0; i < loop; i++) { // 返回前 pageSize 条数据给前端
					odsList.add(list.get(i));
				}
				
				mongoPage.build(odsList);
				
				startTime = System.currentTimeMillis();
				LogCvt.info("搜索最热门店异步设置redis缓存[" + mongoDataSize + "]条数据[开始...]");
				setHottestCache(index, queryMongodbDataCount, outletDetail, condition);
				endTime = System.currentTimeMillis();
				LogCvt.info("搜索最热门店异步设置redis缓存[" + mongoDataSize + "]条数据[结束...], 耗时:" + (endTime - startTime) + "毫秒!");
			} else {
				LogCvt.info("搜索最热门店key[" + key + "]非首次查询, 先看redis有没有数据  如有则返回  无则查mongodb");
				
				long count = OutletRedis.getListSize(key); // 获取缓存key中有多少条数据
				if(count >= end) { // 缓存中的数据  足够本次查询的数据
					LogCvt.info("搜索最热门店key[" + key + "非首次查询, redis有[" + count + "]条数据, 足够本次查询的数据, 直接返回redis中的下标[" + start + "]到[" + (end - 1) + "]的数据");
					odsList.addAll(OutletRedis.get_cbbank_hottest_outlet_condition(key, start, end - 1));
					mongoPage.setTotalCount((int) count); // 设置总页数
					mongoPage.build(odsList);
					
				} else {// 缓存中的数据  不够本次查询的数据  需要查询mongo数据库 pageSize条数据返回给前端   然后  起多线程查queryMongodbDataCount条数据添加到redis
					LogCvt.info("搜索最热门店店key[" + key + "非首次查询, redis有[" + count + "]条数据, 不够本次查询的数据, 查询mongodb中下标[" + start + "]到[" + (end) + "]的数据");
					long startTime = System.currentTimeMillis();
					LogCvt.info("搜索最热门店mongodb查询[" + start + "]至[" + end + "]条数据[开始...]");
					odsList.addAll(outletDetailMongo.findHottestOutletDetailByPage(outletDetail, start, end)); // 查询10条返回给前端
					long endTime = System.currentTimeMillis();
					LogCvt.info("搜索最热门店mongodb查询[" + start + "]至[" + end + "]条数据[结束...], 耗时:" + (endTime - startTime) + "毫秒!");
					
					if (CollectionUtils.isEmpty(odsList)) {
//						mongoPage.setTotalNumber(0); // 设置总页数
						mongoPage.setHasNext(false);
						return mongoPage;
					}
					
//					mongoPage.setTotalNumber(list.size()); // 设置总页数
					mongoPage.setTotalCount(queryMongodbDataCount); // 设置总页数
					mongoPage.build(odsList);
					
				}
				
			}
			/*
			long count = OutletRedis.getListSize(key); // 获取缓存key中有多少条数据
			if (count < 1) { // 缓存中不存在数据   说明是第一次查询			
			
//			mongoPage.setTotalNumber(totalNumber);
				
//				int totalNumber = 1000; // 一次性从mongodb查询1000条数据
//				mongoPage.setPageSize(totalNumber);//一次性从mongodb查询1000条数据
				long startTime = System.currentTimeMillis();
				LogCvt.info("搜索最热门店mongodb查询[" + queryMongodbDataCount + "]条数据[开始...]时间戳=" + startTime);
				list.addAll(outletDetailMongo.findHottestOutletDetailByPage(outletDetail, queryMongodbDataCount)); // 查询mongodb1000条
				long endTime = System.currentTimeMillis();
				LogCvt.info("搜索最热门店mongodb查询[" + queryMongodbDataCount + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
				
				if (CollectionUtils.isEmpty(list)) {
					mongoPage.setHasNext(false);
					return mongoPage;
				}
				mongoPage.setTotalCount(list.size()); // 设置总页数
				
				odsList = new ArrayList<OutletDetailSimpleInfo>();
				int mongoDataSize = list.size(); // 获取mongodb数据list大小
				int loop = pageSize > mongoDataSize ? mongoDataSize : pageSize; // 取最小的集合
				for (int i = 0; i < loop; i++) { // 返回前 pageSize 条数据给前端
					odsList.add(list.get(i));
				}
				
				mongoPage.build(odsList);
				
			//	mongoPage.setTotalPage(Math.ceil(totalNumber / pageSize));
			//	mongoPage.setCurrentPage(1);
				
				startTime = System.currentTimeMillis();
				LogCvt.info("搜索最热门店异步设置redis缓存[" + mongoDataSize + "]条数据[开始...]时间戳=" + startTime);
				FroadThreadPool.execute(new Runnable() { // 异步塞缓存
					
					@Override
					public void run() {
						long startTime = System.currentTimeMillis();
						LogCvt.info("搜索最热门店异步[ThreadPool]设置redis缓存[" + list.size() + "]条数据[开始...]时间戳=" + startTime);
						try {
							// 把数据添加到缓存
							OutletRedis.set_cbbank_hottest_outlet_client_id_parent_area_id_category_id_condition(key, list); // 把数据添加到缓存
						} catch (Exception e) {
							LogCvt.error("分页查询最热门店详情把数据添加到缓存失败, 原因:" + e.getMessage(), e);
						} finally {
							long endTime = System.currentTimeMillis();
							LogCvt.info("搜索最热门店异步[ThreadPool]设置redis缓存[" + list.size() + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
						}
						
					}
				});
				endTime = System.currentTimeMillis();
				LogCvt.info("搜索最热门店异步设置redis缓存[" + mongoDataSize + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
			} else {// 如果不为空  说明是不是第一次查询 直接返回数据
				odsList.addAll(OutletRedis.get_cbbank_hottest_outlet_client_id_parent_area_id_category_id_condition(key, start, end));
//				long count = OutletRedis.count_cbbank_hottest_outlet_client_id_parent_area_id_category_id_condition(client_id, parent_area_id, category_id.toString(), condition); // 统计缓存中集合大小
				mongoPage.setTotalCount((int) count); // 设置总页数
				mongoPage.build(odsList);
			}

			*/
			
			Location location = outletDetail.getLocation();
			if(null != location) {
				Double lng = location.getLongitude();
				Double lat = location.getLatitude();
				setDistance(lng, lat, (List<OutletDetailSimpleInfo>) mongoPage.getItems());
			}
		} catch (Exception e) {
			LogCvt.error("分页查询最热门店详情失败，原因:" + e.getMessage(), e);
		}
		return mongoPage;
	}

	
	/** 
	 * 
	 * @Title: setHottestCache 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param index
	 * @param queryMongodbDataCount
	 * @param outletDetail
	 * @param condition
	 * @return void    返回类型 
	 * @throws
	 */
	private void setHottestCache(final int index, final int queryMongodbDataCount, final OutletDetail outletDetail, final String condition) {
		FroadThreadPool.execute(new Runnable() { // 异步塞缓存
			
			@Override
			public void run() {
				int mongoStart = (index - 1) * queryMongodbDataCount;
				int mongoEnd = mongoStart + queryMongodbDataCount;	
				long countstartTime = System.currentTimeMillis();
				long startTime = 0;
				long endTime = 0;
				LogCvt.info("搜索最热门店异步[ThreadPool]查询mongodb且设置redis缓存[" + mongoStart + "]至[" + mongoEnd + "]条数据[开始...]");
				try {
					// 把数据添加到缓存
					LogCvt.info("搜索最热门店异步[ThreadPool]查询mongodb[" + mongoStart + "]至[" + mongoEnd + "]条数据[开始...]");
					startTime = System.currentTimeMillis();
					List<OutletDetailSimpleInfo> list = outletDetailMongo.findHottestOutletDetailByPage(outletDetail, mongoStart, mongoEnd);
					endTime = System.currentTimeMillis();
					LogCvt.info("搜索最热门店异步[ThreadPool]查询mongodb[" + mongoStart + "]至[" + mongoEnd + "]条数据[结束...], 耗时:" + (endTime - startTime) + "毫秒!");
					
					String key = RedisKeyUtil.cbbank_hottest_outlet_condition(index, condition);
					LogCvt.info("搜索最热门店异步[ThreadPool]设置redis缓存[" + mongoStart + "]至[" + mongoEnd + "]条数据[开始...]");
					startTime = System.currentTimeMillis();
					OutletRedis.set_cbbank_hottest_outlet_condition(key, list); // 把数据添加到缓存
					endTime = System.currentTimeMillis();
					LogCvt.info("搜索最热门店异步[ThreadPool]设置redis缓存[" + mongoStart + "]至[" + mongoEnd + "]条数据[结束...], 耗时:" + (endTime - startTime) + "毫秒!");
				} catch (Exception e) {
					LogCvt.error("搜索最热门店异步[ThreadPool]查询mongodb且设置redis缓存失败, 原因:" + e.getMessage(), e);
				} finally {
					endTime = System.currentTimeMillis();
					LogCvt.info("搜索最热门店异步[ThreadPool]查询mongodb且设置redis缓存[" + mongoStart + "]至[" + mongoEnd + "]条数据[结束...], 耗时:" + (endTime - countstartTime) + "毫秒!");
				}
				
			}
		});
	}
	
	
	
	/**
	 * 设置距离
	 * @Title: setDistance 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param lng
	 * @param lat
	 * @param outletDetailList
	 * @return
	 * @return List<OutletDetailSimpleInfo>    返回类型 
	 * @throws
	 */
	private static void setDistance(Double lng, Double lat, List<OutletDetailSimpleInfo> outletDetailList) {
		if (lng == null || lat == null ||   CollectionUtils.isEmpty(outletDetailList))return; 
		for (OutletDetailSimpleInfo outletDetailSimpleInfo : outletDetailList) {			
			Location location = outletDetailSimpleInfo.getLocation();
			if (null != location) {
				Double latitude = location.getLatitude();
				Double longitude = location.getLongitude();
				if (latitude != null && longitude != null) {
					outletDetailSimpleInfo.setDis(GeoUtil.getDistanceOfMeter(lat, lng, latitude, longitude));// 计算距离
				}
			}			
		}
	}
	
	/**
	 * 分页查询最热门店详情(个人h5用到)
	 * @Title: findOutletDetailByPage 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param page
	 * @param outlet
	 * @param distace
	 * @return
	 * @return Page<Outlet>    返回类型 
	 * @throws
	 */
	public MongoPage findHottestOutletDetailByPage(MongoPage mongoPage, final OutletDetail outletDetail) {
		Integer pageSize = mongoPage.getPageSize();
		pageSize = null == pageSize ? 10 : pageSize; // 默认10条
		
		int start = (mongoPage.getPageNumber() - 1) * pageSize;
		int end = start + pageSize;
		
		final int queryMongodbDataCount = 100; // 每次向mongodb查询的数据量
		mongoPage.setHasNext(true);
		try {
			long startTime = System.currentTimeMillis();
			LogCvt.info("搜索最热门店mongodb查询[" + start + "]至[" + end + "]条数据[开始...]");
			List<OutletDetailSimpleInfo> list = outletDetailMongo.findHottestOutletDetailByPage(outletDetail, start, pageSize);
			long endTime = System.currentTimeMillis();
			
			OutletMonitor.sendHottestOutletMongodbConsumingTime(endTime - startTime);// 发送业务监控
			
			if (CollectionUtils.isEmpty(list)) {
				mongoPage.setHasNext(false);
				return mongoPage;
			}
			
			
			mongoPage.setTotalCount(queryMongodbDataCount); // 设置总页数
			mongoPage.build(list);
			// 设置距离
			Location location = outletDetail.getLocation();
			if(null != location) {
				Double lng = location.getLongitude();
				Double lat = location.getLatitude();
				setDistance(lng, lat, (List<OutletDetailSimpleInfo>) mongoPage.getItems());
			}
		} catch (Exception e) {
			LogCvt.error("分页查询最热门店详情失败，原因:" + e.getMessage(), e);
		}
		return mongoPage;
	}
	
	
	/**
	 * 附近搜索门店(分页对象为OutletDetailSimpleInfo)(个人h5用到)
	 * @param outletDetail
	 * @param distance
	 * @param pageSize
	 * @param lastDis
	 * @return    
	 * @see com.froad.logic.OutletLogic#findNearbyOutlet(com.froad.po.mongo.OutletDetail, double, double)
	
	public MongoPage findNearbyOutlet_____old(MongoPage mongoPage,final OutletDetail outletDetail) {
		final List<OutletDetailSimpleInfo> list = new ArrayList<OutletDetailSimpleInfo>();
		try{
//			LogCvt.info("附近搜索门店传入的PO==>" + JSON.toJSONString(outletDetail));
			final String client_id = ObjectUtils.toString(outletDetail.getClientId(), "");
			Location location = outletDetail.getLocation();
			final String longitude = String.valueOf(location.getLongitude());
			final String latitude = String.valueOf(location.getLatitude());

			List<CategoryInfo> cis = outletDetail.getCategoryInfo();
			final StringBuilder category_id = new StringBuilder(""); // 分类id
			if(CollectionUtils.isNotEmpty(cis)) {
				for (CategoryInfo categoryInfo : cis) {
					if(null != categoryInfo && categoryInfo.getCategoryId() != null) {
						category_id.append(categoryInfo.getCategoryId());
					}
				}
			}
			
			String outletName = outletDetail.getOutletName();
			final String condition = ObjectUtils.toString(outletName, ""); // 只按照门店简称查询
			
			Integer pageSize = mongoPage.getPageSize();
			pageSize = null == pageSize ? 10 : pageSize; // 默认10条
			
			long start = (mongoPage.getCurrentPage() - 1)* mongoPage.getPageSize();
			long end = (mongoPage.getCurrentPage() - 1)* mongoPage.getPageSize() + mongoPage.getPageSize() -1;
			
			
			String key = RedisKeyUtil.cbbank_near_outlet_index_client_id_longitude_latitude_category_id_condition(client_id, longitude, latitude, category_id.toString(), condition);
			long count = OutletRedis.getListSize(key); // 获取缓存key中有多少条数据
			
			List<OutletDetailSimpleInfo> odsList = new ArrayList<OutletDetailSimpleInfo>();
			if (count < 1) { // 缓存中不存在数据	说明是第一次查询
				
				int totalNumber = 500; // 一次性从mongodb查询1000条数据
//			mongoPage.setTotalNumber(totalNumber);
				
				long startTime = System.currentTimeMillis();
				LogCvt.info("附近搜索门店mongodb查询[" + totalNumber + "]条数据[开始...]时间戳=" + startTime);
				list.addAll(outletDetailMongo.findNearbyOutlet(outletDetail, totalNumber)); // 查询mongodb  1000条
				long endTime = System.currentTimeMillis();
				LogCvt.info("附近搜索门店mongodb查询[" + totalNumber + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");

				if (CollectionUtils.isEmpty(list)) {
					return mongoPage;
				}
				mongoPage.setTotalNumber(list.size()); // 设置总页数
				
//				odsList = new ArrayList<OutletDetailSimpleInfo>();
				int mongoDataSize = list.size(); // 获取mongodb数据list大小
				int loop = pageSize > mongoDataSize ? mongoDataSize : pageSize; // 取最小的集合
				for (int i = 0; i < loop; i++) { // 返回前 pageSize 条数据给前端
					odsList.add(list.get(i));
				}
				
				mongoPage.build(odsList);
				
//				mongoPage.setTotalPage(Math.ceil(totalNumber / pageSize));
//				mongoPage.setCurrentPage(1);
				
				startTime = System.currentTimeMillis();
				LogCvt.info("附近搜索门店异步设置redis缓存[" + mongoDataSize + "]条数据[开始...]时间戳=" + startTime);
				FroadThreadPool.execute(new Runnable() { // 异步塞缓存
					
					@Override
					public void run() {
						long startTime = System.currentTimeMillis();
						LogCvt.info("附近搜索门店异步[ThreadPool]设置redis缓存[" + list.size() + "]条数据[开始...]时间戳=" + startTime);
						try {
							// 把数据添加到缓存
							OutletRedis.set_cbbank_near_outlet_client_id_longitude_latitude_category_id_condition(client_id, longitude, latitude,category_id.toString(), condition, list);
						} catch (Exception e) {
							LogCvt.error("附近搜索门店把数据添加到缓存失败, 原因:" + e.getMessage(), e);
						} finally {
							long endTime = System.currentTimeMillis();
							LogCvt.info("附近搜索门店异步[ThreadPool]设置redis缓存[" + list.size() + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
						}
						
					}
				});
				endTime = System.currentTimeMillis();
				LogCvt.info("附近搜索门店异步设置redis缓存[" + mongoDataSize + "]条数据[结束...]时间戳=" + endTime + ", 耗时:" + (endTime - startTime) + "毫秒!");
				
			} else {// 如果不为空  说明是不是第一次查询 直接返回数据
				odsList.addAll(OutletRedis.get_cbbank_near_outlet_client_id_longitude_latitude_category_id_condition(key, start, end));
//				long count = OutletRedis.count_cbbank_near_outlet_client_id_longitude_latitude_category_id_condition(client_id, longitude, latitude,category_id.toString(), condition); // 统计缓存中集合大小
				mongoPage.setTotalNumber((int) count); // 设置总页数
				mongoPage.build(odsList);
			}
			
			
		} catch(Exception e){
			LogCvt.error("附近搜索门店，原因:" + e.getMessage(), e); 
		}
		return mongoPage;
	}
	*/
	

	
	public MongoPage findNearbyOutlet(MongoPage mongoPage, final OutletDetail outletDetail,double distance,int orderBy) {
		try{
			long startTime = System.currentTimeMillis();
			setOutletDetail(outletDetail);
			mongoPage = outletDetailMongo.findNearByOutlet(mongoPage,outletDetail,distance,orderBy);
			 // 发送业务监控
			OutletMonitor.sendNearOutletMongodbConsumingTime(System.currentTimeMillis() - startTime);
			
			//此处注释掉，确认是否有下一页，在findNearByOutlet中处理掉  mdy by trimray 2016/01/27
			// 检查是否有下一页
//			mongoPage.setHasNext(true);
//			if (CollectionUtils.isEmpty(mongoPage.getItems()) || mongoPage.getItems().size() < mongoPage.getPageSize()) {
//				mongoPage.setHasNext(false);
//				return mongoPage;
//			}
		} catch(Exception e){
			LogCvt.error("附近搜索门店，原因:" + e.getMessage(), e); 
		}
		return mongoPage;
	}

	
	@Override
	public OutletDetail findOutletDetailByOutletId(String outletId) {
		OutletDetail od = null;
		try{
			od = outletDetailMongo.findOutletDetailByoutletId(outletId);
		} catch(Exception e){
			LogCvt.error("根据门店id查询门店详情OutletDetail失败，原因:" + e.getMessage(), e); 
		}
		return od;
	}

	/**
	 * 查询门店缓存
	 * @param clientId
	 * @param merchantId
	 * @param outletId
	 * @return    
	 * @see com.froad.logic.OutletLogic#findOutletRedis(java.lang.String, java.lang.String, java.lang.String)
	 */
	public Map<String, String> findOutletRedis(String clientId, String merchantId, String outletId) {
		Map<String, String> map = OutletRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(clientId, merchantId, outletId);
		if (map == null || map.isEmpty()) {
			LogCvt.info("查询门店缓存为空, 查询数据库");
			Outlet outlet = this.findOutletByOutletId(outletId);
			if(null == outlet)
				return null;
			LogCvt.info("查询数据库之后再设置门店缓存!");
			OutletRedis.set_cbbank_outlet_client_id_merchant_id_outlet_id(outlet);
			map = OutletRedis.get_cbbank_outlet_client_id_merchant_id_outlet_id(clientId, merchantId, outletId);
		}
		return map;
	}
	
	/**
     * 查询 Outlet
     * @param outletId
     * @return Outlet 
     */
	@Override
	public Outlet findOutletByOutletId(String outletId){
		Outlet outlet = null; 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			outlet = outletMapper.findOutletByOutletId(outletId);
		} catch (Exception e) { 
//			if(null != sqlSession)  
//				sqlSession.rollback(true);
			outlet = null;
			LogCvt.error("根据门店id查询单个 Outlet失败，原因:" + e.getMessage(), e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return outlet; 
		
	}

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
	public List<OutletDetail> findOutletDetailbyOutletIdList(List<String> outletIdList){
//		LogCvt.info("根据门店id集合查询详情传入的PO==>" + JSON.toJSONString(outletIdList));
		List<OutletDetail> list = null;
		try{
			
			list = outletDetailMongo.findOutletDetailbyOutletIdList(outletIdList);
		} catch(Exception e){
			LogCvt.error("根据门店id集合查询详情，原因:" + e.getMessage(), e); 
		}
		return list;
	}

	/**
	 *  查看商品对于商户的所有消费门店
	 *  场景：1、特惠商品，商品详情  2、卷码查询门店列表
	  * @Title: findOutletMongoInfoByPage
	  * @Description: TODO
	  * @author: share 2015年6月11日
	  * @modify: share 2015年6月11日
	  * @param @param mongoPage
	  * @param @param longitude
	  * @param @param latitude
	  * @param @param merchantId
	  * @param @return
	  * @throws
	  * @see com.froad.logic.OutletLogic#findOutletMongoInfoByPage(com.froad.db.mongo.page.MongoPage, double, double, java.lang.String)
	 */
	@Override
	public MongoPage findOutletMongoInfoByPage(MongoPage mongoPage,double longitude, double latitude, String merchantId) {
		try{
			Integer pageSize = mongoPage.getPageSize();
			 // 默认10条
			pageSize = null == pageSize ? 10 : pageSize;
			
			int start = (mongoPage.getPageNumber() - 1) * pageSize;
			int end = start + pageSize;
			
			LogCvt.info("经纬度和商户ID查询[" + start + "]至[" + end + "]条数据[开始...]");
			
			OutletDetail outletDetail = new OutletDetail();
			if(longitude == 0 && latitude == 0){
				outletDetail.setLocation(null);
			}else{
				Location location = new Location();
				location.setLatitude(latitude);
				location.setLongitude(longitude);
				outletDetail.setLocation(location);
			}
			outletDetail.setMerchantId(merchantId);
			
			mongoPage = outletDetailMongo.findOutletMongoInfoByMerchantId(outletDetail, mongoPage);
			
		} catch(Exception e){
			LogCvt.error("附近搜索门店，原因:" + e.getMessage(), e); 
		}
		return mongoPage;
	}

	@Override
	public MongoPage findNearbyOutletByPage(MongoPage mongoPage,
			OutletDetail outletDetail, double distance,int skip) {
		try{
			long startTime = System.currentTimeMillis();
			setOutletDetail(outletDetail);
			mongoPage = outletDetailMongo.findNearByOutletPage(mongoPage,outletDetail,distance,skip);
			 // 发送业务监控
			OutletMonitor.sendNearOutletMongodbConsumingTime(System.currentTimeMillis() - startTime);
			// 检查是否有下一页
			if(skip >= 0)
				mongoPage.setHasNext(true);
			if (CollectionUtils.isEmpty(mongoPage.getItems()) || mongoPage.getItems().size() < mongoPage.getPageSize()) {
				mongoPage.setHasNext(false);
				return mongoPage;
			}
		} catch(Exception e){
			LogCvt.error("附近搜索门店，原因:" + e.getMessage(), e); 
		}
		return mongoPage;
	}


	@Override
	public boolean disableOutlet(Outlet outlet) throws Exception {


		Boolean result = false; 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			
			List<Outlet> listOutlet = findOutlet(outlet);
			
			for (Outlet outlet2 : listOutlet) {
				
				result = outletMapper.disableOutlet(outlet2.getOutletId());
				
				if(result) {
					LogCvt.info("禁用outlet成功");

					/**********************操作Mongodb数据库**********************/
					if (!outlet2.getOutletStatus()) { // 不为银行默认的商户的时候才添加mongo表
						/*删除商户详细信息*/
						LogCvt.info("普通门店(非银行机构对应的门店)cb_merchant_detail表中删除门店id");
						merchantDetailMongo.deleteOutlet(outlet2.getMerchantId(), outlet2.getOutletId()); // 删除门店
						
						LogCvt.info("普通门店(非银行机构对应的门店)删除cb_outlet_detail表");
						outletDetailMongo.deleteOutletDetail(outlet2.getOutletId()); // 修改门店详情信息
					}
					
					/**********************操作Redis缓存**********************/
					/* 设置全部缓存 */
					LogCvt.info("删除门店信息缓存");
					OutletRedis.del_cbbank_outlet_client_id_merchant_id_outlet_id(outlet2);
					
					sqlSession.commit(true); 
				} else {
					sqlSession.rollback(true); 
					break;
				}
			}

			return result;
			

		} catch (FroadServerException e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);
			throw e;
		} catch (Exception e) { 
			result = false;
			if(null != sqlSession)  
				sqlSession.rollback(true);
			throw e;
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
	
	}

	@Override
	public Result syncOultetInfo(Outlet outlet,String isSynSucc, int synType)
			throws FroadServerException, Exception {
	    Result resultBean=new Result();
		Boolean isSuccess = false;
		boolean isSynSuccess = (Constants.SYN_OR_AUDIT_SUCCESS.equals(isSynSucc)?true:false);
		try {
			Merchant merchant = findMerchantByMerchantId(outlet.getMerchantId(),outlet.getClientId());
			Outlet outletOld=findOutletByOuletId(outlet.getOutletId(),outlet.getClientId());
			/***********************判断录入审核/编辑审核****************************/
			int auditTypeFlag = Constants.INPUT_AUDIT;//审核类型标识：0-录入审核 1-编辑审核
			if(ProductAuditState.passAudit.getCode().equals(outletOld.getAuditState())){
				if(ProductAuditState.passAudit.equals(outletOld.getEditAuditState())){
					LogCvt.info("门店已审核通过，门店id:"+outlet.getOutletId()+",审核状态:"+outletOld.getAuditState());
					resultBean.setResultCode(ResultCode.failed.getCode());
					resultBean.setResultDesc("门店编辑审核已经通过，门店id:"+outlet.getOutletId()+",审核状态:"+outlet.getAuditState());
				    return resultBean;
				}
    			if(ProductAuditState.noAudit.getCode().equals(outletOld.getEditAuditState())
    			   || ProductAuditState.waitSynchAudit.getCode().equals(outletOld.getEditAuditState())){
    				auditTypeFlag = Constants.EDIT_AUDIT;//编辑审核
    			}
        	}
			if(null != merchant){
				if(!merchant.getIsEnable()){
					LogCvt.debug("无效商户，商户id:"+outlet.getMerchantId());
					resultBean.setResultCode(ResultCode.failed.getCode());
					resultBean.setResultDesc("无效商户，商户id:"+outlet.getMerchantId());
					return resultBean;	
				}
				if(!merchant.getAuditState().equals(ProductAuditState.passAudit.getCode())){
					LogCvt.debug("商户审核未通过，商户id:"+outlet.getMerchantId()+",审核状态:"+merchant.getAuditState());
					resultBean.setResultCode(ResultCode.failed.getCode());
					resultBean.setResultDesc("商户审核未通过，商户id:"+outlet.getMerchantId()+",审核状态:"+merchant.getAuditState());
					return resultBean;
				}
				if(outletOld!=null){
					if(outletOld.getAuditState()!=null&&outletOld.getAuditState().equals(ProductAuditState.passAudit.getCode())&&outletOld.getEditAuditState().equals(ProductAuditState.passAudit.getCode())&&(auditTypeFlag== Constants.INPUT_AUDIT)){
						LogCvt.debug("门店审核已经通过，门店id:"+outlet.getOutletId()+",审核状态:"+outletOld.getAuditState());	
						//门店审核通过处理账户表
						updateMerchantAccount(outlet.getMerchantId(),outlet.getOutletId(),outlet.getClientId());
						resultBean.setResultCode(ResultCode.failed.getCode());
						resultBean.setResultDesc("门店审核已经通过，门店id:"+outlet.getOutletId()+",审核状态:"+outletOld.getAuditState());
					    return resultBean;
					}
//					else if(outletOld.getAuditState()!=null&&synType==0&&outletOld.getAuditState().equals(ProductAuditState.passAudit.getCode())&&outletOld.getEditAuditState().equals(ProductAuditState.passAudit.getCode())){
//						LogCvt.debug("门店同步编辑审核已经通过，门店id:"+outlet.getOutletId()+",审核状态:"+outletOld.getAuditState());	
//						//门店审核通过处理账户表
//						updateMerchantAccount(outlet.getMerchantId(),outlet.getOutletId(),outlet.getClientId());
//						resultBean.setResultCode(ResultCode.failed.getCode());
//						resultBean.setResultDesc("门店审核已经通过，门店id:"+outlet.getOutletId()+",审核状态:"+outlet.getAuditState());
//					    return resultBean;
//					}
					else if(outletOld.getEditAuditState()!=null&&outletOld.getEditAuditState().equals(ProductAuditState.passAudit.getCode())&&(auditTypeFlag == Constants.EDIT_AUDIT)){
						LogCvt.debug("门店编辑审核已经通过，门店id:"+outlet.getOutletId()+",审核状态:"+outletOld.getEditAuditState());
						updateMerchantAccount(outlet.getMerchantId(),outlet.getOutletId(),outlet.getClientId());
						resultBean.setResultCode(ResultCode.failed.getCode());
						resultBean.setResultDesc("门店编辑审核已经通过，门店id:"+outlet.getOutletId()+",审核状态:"+outlet.getAuditState());
					    return resultBean;
					}else{
						//同步操作
						if(Constants.SYN_TYPE == synType){	
							resultBean= synOuletSynType(auditTypeFlag,isSynSuccess,outlet);							
						}else if(Constants.AUDIT_TYPE ==synType){
							resultBean =  synOutletAuditType(auditTypeFlag,isSynSuccess,outlet);
						}
						
					}
				}
				
			}else{
				LogCvt.info("商户信息不存在,商户Id:"+outlet.getMerchantId());
				resultBean.setResultCode(ResultCode.failed.getCode());
				resultBean.setResultDesc("商户信息不存在,商户Id:"+outlet.getMerchantId());
			}

				
		}catch (FroadServerException e) { 
			LogCvt.error("同步更改门店状态失败,门店ID："+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState()+",原因:" + e.getMessage() , e);
			resultBean.setResultCode(ResultCode.failed.getCode());
			resultBean.setResultDesc("同步更改商户状态失败,门店ID："+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState());
		} catch (Exception e) { 
			LogCvt.error("同步更改商户状态操作[系统异常],商户ID："+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState()+",原因:" + e.getMessage(), e);
			resultBean.setResultCode(ResultCode.failed.getCode());
			resultBean.setResultDesc("同步更改商户状态操作[系统异常],商户ID："+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState());
		}
		LogCvt.info("同步更改商户状态结束");
	return resultBean; 
	}
	
  public Merchant findMerchantByMerchantId(String merchanId,String clientId){
	  CommonLogic comLogic = new CommonLogicImpl();
	  Merchant merchant=new Merchant();
	  //redis中获取merchant
	  Map<String,String> merchantMap = comLogic.getMerchantRedis(clientId,merchanId);
	  if(merchantMap!=null){
		  merchant.setClientId(clientId);
		  merchant.setMerchantId(merchanId);
		  merchant.setIsEnable(merchantMap.get("is_enable").equals("1")?true:false);
		  merchant.setAuditState(merchantMap.get("audit_state").toString());
	  }
	  return merchant;
  }
  public Result synOuletSynType(int auditTypeFlag,boolean isSynSuccess,Outlet outlet){
	  Result result =new Result();
	  if(isSynSuccess){
		//不走行内审核
		if (!ClientUtil.getClientId(outlet.getClientId())) {
			//非安徽的门店状态改成审核通过，isenable =true； 同步redis、mongo,发短信
			if(auditTypeFlag == Constants.INPUT_AUDIT ){//录入审核
			//	outlet.setAuditTime(new Date());
				outlet.setAuditState(ProductAuditState.passAudit.getCode()); 
			//	outlet.setAuditComment("门店审核通过");
				outlet.setIsEnable(true);
				//修改状态
				SetAuditPass(outlet,"1",null);
				LogCvt.info("非行内审核模式审核通过，门店id:"+outlet.getOutletId()+",审核状态:"+outlet.getAuditState());	
				result.setResultDesc("非行内审核模式审核通过");
				//4:发短信
//				LogCvt.info("发送短信信息");
//				try {
//					sendSmeMessage(outlet.getMerchantId(),outlet.getClientId());
//				} catch (TException e) {
//					LogCvt.error("发送短信信息失败,原因"+e.getMessage());
//				}
			}else{
				  outlet.setEditAuditState(ProductAuditState.passAudit.getCode());
				  SetAuditPass(outlet,"1","1");
				  LogCvt.info("非行内编辑模式审核通过，门店id:"+outlet.getOutletId()+",审核状态:"+outlet.getEditAuditState());	
				  result.setResultDesc("非行内编辑模式审核通过");
			}
			
		}else{
			if(auditTypeFlag == Constants.INPUT_AUDIT ){//录入审核
				outlet.setAuditState(ProductAuditState.waitSynchAudit.getCode());
				SetAuditPass(outlet,"0",null);		
				LogCvt.info("行内审核模式同步成功，门店id:"+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState());
				result.setResultDesc("行内审核模式同步成功");
			}else{
				  outlet.setEditAuditState(ProductAuditState.waitSynchAudit.getCode());
				  SetAuditPass(outlet,"0","1");	
				  LogCvt.info("行内编辑模式审核通过，门店id:"+outlet.getOutletId()+",审核状态:"+outlet.getEditAuditState());	
				  result.setResultDesc("行内编辑模式审核通过");
			}
		}
	  }else{
			// 重庆改成更改商户状态为审核通过白名单待同步4，isenable不用改，reids要改审核状态。mongo不用改。
			// 安徽，同步失败不处理定时任务同步成功结果后通知，此时商户是待审核状态。
			if (ClientUtil.getClientId(outlet.getClientId())) {
				if(auditTypeFlag == Constants.INPUT_AUDIT ){//录入审核
					outlet.setAuditState(ProductAuditState.waitSynchAudit.getCode());
					SetAuditPass(outlet,"0",null);		
					LogCvt.info("行内审核模式同步失败，门店id:"+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState());	
					result.setResultDesc("行内审核模式同步失败");
				}else{
					 outlet.setEditAuditState(ProductAuditState.waitSynchAudit.getCode());
					  SetAuditPass(outlet,"0","1");	
					  LogCvt.info("行内编辑模式审核通过，门店id:"+outlet.getOutletId()+",审核状态:"+outlet.getEditAuditState());	
					  result.setResultDesc("行内编辑模式审核通过");
				}
			}
	  }
	  
	  
	  return result;
  }
  private Result synOutletAuditType(int auditTypeFlag,boolean isSynSuccess,Outlet outlet) throws TException{
		boolean isSuccess = false;
		Result resultBean=new Result();
		//审核通过通知
		if(isSynSuccess){
			//行内审核模式的商户门店状态改成审核通过，isenable =true； 同步redis、mongo,发短信
			if(auditTypeFlag == Constants.INPUT_AUDIT ){//录入审核
				if (ClientUtil.getClientId(outlet.getClientId())) {
					outlet.setAuditState(ProductAuditState.passAudit.getCode());
				//	outlet.setAuditTime(new Date());
				//	outlet.setAuditComment("门店录入审核通过");
					outlet.setIsEnable(true);
					SetAuditPass(outlet,"1",null);
					LogCvt.info("行内编辑审核录入模式审核通过，商户id:"+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState());				
					resultBean.setResultDesc("行内编辑审核录入模式审核通过");
					//3:发短信
					//sendSmeMessage(outlet.getMerchantId(),outlet.getClientId());
				}
			}else{
				outlet.setEditAuditState(ProductAuditState.passAudit.getCode());
				SetAuditPass(outlet,"1","1");
				LogCvt.info("行内编辑审核模式审核通过，商户id:"+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState());				
				resultBean.setResultDesc("行内编辑审核模式审核通过");
			}
			
		}else{
			//行内审核模式的商户门店状态改成审核不通过，isenable =false,审核备注=商户账户白名单银行审核不通过， 同步redis、mongo,
			if (ClientUtil.getClientId(outlet.getClientId())) {
				if(auditTypeFlag == Constants.INPUT_AUDIT ){//录入审核
					outlet.setAuditState(ProductAuditState.failAudit.getCode());
					outlet.setIsEnable(false);
					outlet.setAuditComment("商户门店账户白名单银行审核不通过");
					
					SetAuditPass(outlet,"1",null);
					LogCvt.info("行内录入审核模式审核不通过，门店id:"+outlet.getOutletId()+",审核状态:"+outlet.getAuditState());	
					resultBean.setResultDesc("行内编辑录入审核模式审核不通过");
				}else{
					outlet.setEditAuditState(ProductAuditState.failAudit.getCode());
					SetAuditPass(outlet,"1","1");
					LogCvt.info("行内编辑审核模式审核不通过，商户id:"+outlet.getMerchantId()+",审核状态:"+outlet.getAuditState());				
					resultBean.setResultDesc("行内编辑审核模式审核不通过");
				}
			}
		}
		return resultBean;
	}
  
  public Outlet findOutletByOuletId(String outletId,String clientId){
	  SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	  OutletMapper outletMapper=sqlSession.getMapper(OutletMapper.class);
	 Outlet outlet= outletMapper.findOutletByOutletId(outletId);
	  return outlet;
  }
  public boolean SetAuditPass(Outlet outlet,String flag,String isEdit){
	  //修改mysql状态
	  SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	  OutletMapper outletMapper=sqlSession.getMapper(OutletMapper.class);
	  boolean result=false;
	  try{
		  if(isEdit==null){
			  result= outletMapper.updateOutletByOutletId(outlet);
			  if(result){
				  LogCvt.debug("门店Mysql信息修改成功"); 
				  try{
					  //修改redis
					  OutletRedis.updateOutletRedis(outlet);
					  //修改mongo
					  if(flag.equals("1")){
						  OutletDetail outletDetail=new OutletDetail();
						  outletDetail.setId(outlet.getOutletId());
						  outletDetail.setIsEnable(outlet.getIsEnable());
						  OutletDetailMongo outletDetailMongo=new OutletDetailMongo();
						  outletDetailMongo.updateOutletDetail(outletDetail);
					  }
				  }catch(Exception e){
					  LogCvt.debug("门店redis or mongo操作异常");
					  sqlSession.rollback(true);
				  }
			  }
		  }else{
			  result= outletMapper.updateOutletByOutletId(outlet);
		  }
		  sqlSession.commit();
		  
	  }catch(Exception e){
		  LogCvt.error("修改mysql出错:"+e.getMessage(),e); 
	  }finally{
		  sqlSession.close();
	  }
	  
	  return result;
  }
  public void updateMerchantAccount(String merchantId,String outletId,String clientId){
	  SqlSession sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
	  MerchantAccountMapper merchantAccountMapper=sqlSession.getMapper(MerchantAccountMapper.class);
	  MerchantAccount merchantAccount=new MerchantAccount();
	  merchantAccount.setClientId(clientId);
	  merchantAccount.setMerchantId(merchantId);
	  merchantAccount.setOutletId(outletId);
	  merchantAccount.setAuditState(AccountAuditState.passAudit.getCode());
	  merchantAccount.setSynchState(AccountSynchState.synchSucc.getCode());
	  try{
		  boolean result= merchantAccountMapper.updateMerchantAccount(merchantAccount);
		  sqlSession.commit();
		  if(result){
			  LogCvt.debug("修改商户账户状态成功，门店id:"+outletId+",审核状态:"+merchantAccount.getAuditState());  
		  }else{
			  LogCvt.debug("修改商户账户状态失败，门店id:"+outletId+",审核状态:"+merchantAccount.getAuditState()); 
		  }
	  }catch(Exception e){
		  sqlSession.rollback(true);
		  LogCvt.error("修改商户账户状态[异常]，门店id:"+outletId+",审核状态:"+merchantAccount.getAuditState());
	  }finally{
		  sqlSession.close();
	  }
  }
  public Date setTime(){
	  SimpleDateFormat simple=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	  Date date=new Date();
	  Date nowdate=null;
	  try {
		 nowdate=simple.parse(date.toString());
	} catch (ParseException e) {
		e.printStackTrace();
	}
	  return nowdate;
  }
  /**
	 * 同步白名单，发送短信操作.
	 * 
	 * @param merchantId 商户Id
	 * @param clientId 客户端Id
	 * @throws TException
	 */
	private void sendSmeMessage(String merchantId,String clientId) throws TException{
		List<String> valueList = new ArrayList<String>();
		MerchantUserLogic userLogic = new MerchantUserLogicImpl();
		MerchantUser merchantUser = new MerchantUser();
		merchantUser.setClientId(clientId);
		merchantUser.setMerchantId(merchantId);
		merchantUser.setMerchantRoleId(100000000l);
		String userName = "";
		String phone = "";
		String password = "";
		List<MerchantUser> merchantUserList = userLogic.findMerchantUser(merchantUser);
		if(null != merchantUserList && merchantUserList.size()>0){
			merchantUser = merchantUserList.get(0);
			
			userName = merchantUser.getUsername() == null ? "": merchantUser.getUsername();
			password = merchantUser.getPassword() == null?"":merchantUser.getPassword();
			
			//构建短信内容
			valueList.add(userName);
			valueList.add(password);
			
			//构建短信VO对象
			SmsMessageVo smsMessageVo = new SmsMessageVo();
			smsMessageVo.setSmsType(Constants.RESET_PASSWORD_SMS_TYPE);
			smsMessageVo.setMobile(phone);
			smsMessageVo.setValues(valueList);
			smsMessageVo.setClientId(clientId);	
			
			support.sendSmsMessage(smsMessageVo);
		}		
	}
	
	/**
     * 门店惠付分页查询
     * @param page
     * @param outletPrefer
     * @return Page    结果集合 
     */
	@Override
	public Page<OutletPrefer> findOutletPreferByPage(Page<OutletPrefer> page, OutletPrefer outletPrefer) {
		LogCvt.info("门店惠付分页查询开始");
		long st = System.currentTimeMillis();
		List<OutletPrefer> result = new ArrayList<OutletPrefer>(); 
		SqlSession sqlSession = null;
		OutletMapper outletMapper = null;
		try { 
			/**********************操作MySQL数据库**********************/
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			outletMapper = sqlSession.getMapper(OutletMapper.class);
			// 多机构级别查询
			if(outletPrefer.getOrgCode()!= null &&outletPrefer.getOrgCode().split(",").length > 0){
				List<String> orgCodeList = new ArrayList<String>(Arrays.asList(outletPrefer.getOrgCode().split(",")));
				List<String> orgCodesCondition = new CommonLogicImpl().queryLastOrgCode(outletPrefer.getClientId(), orgCodeList);
				outletPrefer.setOrgCodesCondition(orgCodesCondition);
				outletPrefer.setOrgCode(null);
			}
			
			result = outletMapper.findOutletPreferByPage(page, outletPrefer); 
			page.setResultsContent(result);			
			LogCvt.info("[银行管理平台]-门店惠付分页查询-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms" );
		} catch (Exception e) { 
			LogCvt.error("门门店惠付分页查询Outlet失败，原因:" + e.getMessage(),e); 
		} finally { 
			if(null != sqlSession)  
				sqlSession.close();  
		} 
		return page; 
	}
	
	/**
	 * 门店导出组装数据.
	 */
	@Override
	public MerchantExportData exportOutletManage(List<OutletPrefer> OutletPreferList) {
		LogCvt.info("构建门店列表报表Outlet");
		MerchantExportData merchantExportData = new MerchantExportData();
		// 构建报表头部
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("营业执照号");
		header.add("商户名称");
		header.add("门店名称");
		header.add("所属地区");
		header.add("门店地址");
		header.add("创建时间");
		header.add("审核时间");
		header.add("门店状态");
		header.add("所属机构");

		// 构建报表内容体
		List<List<String>> data = new ArrayList<List<String>>();
		int serNo = 0;
		StringBuffer auditStateSb = new StringBuffer();
		if (null != OutletPreferList && OutletPreferList.size() > 0) {
			List<String> columnList = null;
			for (OutletPrefer outletPrefer : OutletPreferList) {
				columnList = new ArrayList<String>();
				columnList.add("" + (++serNo));
				columnList.add(outletPrefer.getLicense()==null?"":outletPrefer.getLicense());	
				columnList.add(outletPrefer.getMerchantName()==null?"":outletPrefer.getMerchantName());	
				columnList.add(outletPrefer.getOutletName()==null?"":outletPrefer.getOutletName());
				columnList.add(outletPrefer.getAreaName()==null?"":outletPrefer.getAreaName());
				columnList.add(outletPrefer.getAddress()==null?"":outletPrefer.getAddress());
				String createTimeValue = "";
				String auditTimeValue = "";
				if (null != outletPrefer.getCreateTime()) {
					createTimeValue = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1,outletPrefer.getCreateTime());
				}
				columnList.add(createTimeValue);				
				if (null != outletPrefer.getAuditTime()) {
					auditTimeValue = DateUtil.formatDateTime(DateUtil.DATE_FORMAT1,outletPrefer.getAuditTime());
				}
				columnList.add(auditTimeValue);	
				
				auditStateSb.delete(0, auditStateSb.length());
				if(!outletPrefer.getDisableStatus().equals(Constants.DISABLE_STATUS_NODELETE)){				
					if (outletPrefer.getAuditState().equals(ProductAuditState.noAudit.getCode())) {
						auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_ADD);
					} else if (outletPrefer.getAuditState().equals(ProductAuditState.passAudit.getCode())) {
						auditStateSb.append(ProductAuditState.passAudit.getDescribe());
					} else if (outletPrefer.getAuditState().equals(ProductAuditState.failAudit.getCode())) {
						auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_ADD_FAIL);
					} else if (outletPrefer.getAuditState().equals(ProductAuditState.noCommit.getCode())) {
						auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_ADD_NOSUBMIT);
					} else if (outletPrefer.getAuditState().equals(ProductAuditState.waitSynchAudit.getCode())) {
						auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_ADD_WAIT_SYNCH);
					} else  {
						auditStateSb.append("");
					}				
					if (outletPrefer.getEditAuditState().equals(ProductAuditState.noAudit.getCode())) {
						if(auditStateSb.length()>0){
							auditStateSb.append("(").append(com.froad.util.Constants.OUTLET_AUDIT_STATE_EDIT_ADD).append(")");							
						}else{
							auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_EDIT_ADD);	
						}						
					} else if (outletPrefer.getEditAuditState().equals(ProductAuditState.passAudit.getCode())) {
						    auditStateSb.append(ProductAuditState.passAudit.getDescribe());	
					} else if (outletPrefer.getEditAuditState().equals(ProductAuditState.failAudit.getCode())) {
						if(auditStateSb.length()>0){
							auditStateSb.append("(").append(com.froad.util.Constants.OUTLET_AUDIT_STATE_EDIT_ADD_FAIL).append(")");							
						}else{
							auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_EDIT_ADD_FAIL);	
						}
					} else if (outletPrefer.getEditAuditState().equals(ProductAuditState.noCommit.getCode())) {
						if(auditStateSb.length()>0){
							auditStateSb.append("(").append(com.froad.util.Constants.OUTLET_AUDIT_STATE_EDIT_ADD_NOSUBMIT).append(")");							
						}else{
							auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_EDIT_ADD_NOSUBMIT);	
						}
					} else if (outletPrefer.getEditAuditState().equals(ProductAuditState.waitSynchAudit.getCode())) {
						if(auditStateSb.length()>0){
							auditStateSb.append("(").append(com.froad.util.Constants.OUTLET_AUDIT_STATE_EDIT_WAIT_SYNCH).append(")");							
						}else{
							auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_EDIT_WAIT_SYNCH);	
						}
					} else  {
						auditStateSb.append("");
					}	
				}else{
					auditStateSb.append(com.froad.util.Constants.OUTLET_AUDIT_STATE_DELETE);
				}
				columnList.add(auditStateSb.toString());				
				columnList.add(outletPrefer.getOrgName()==null?"":outletPrefer.getOrgName());
				data.add(columnList);
			}
		}
		merchantExportData.setHeader(header);
		merchantExportData.setData(data);
		merchantExportData.setSheetName("门店列表");
		merchantExportData.setExcelFileName("门店列表查询");
		return merchantExportData;
	}
}