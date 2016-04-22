package com.froad.handler.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSON;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.db.mongo.page.MongoPage;
import com.froad.db.mongo.page.OrderBy;
import com.froad.db.mongo.page.Sort;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.mapper.MinatoSingleProductMapper;
import com.froad.handler.MinatoSingleProductHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveResultInfo;
import com.froad.po.AreaInfo;
import com.froad.po.MinatoSingleParamInfo;
import com.froad.po.MinatoSingleProductResultInfo;
import com.froad.po.OutletDetail;
import com.froad.po.mongo.Location;
import com.froad.po.mongo.MerchantDetail;
import com.froad.util.DistanceUtils;
import com.froad.util.MongoTableName;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class MinatoSingleProductHandlerImpl implements
		MinatoSingleProductHandler {

	/**
	 * @Fields manager : MongoDB操作方法
	 */
	private MongoManager manager = new MongoManager();

	@Override
	public MongoPage queryMinatoSingleProduct(MongoPage mongoPage,
			ActiveResultInfo activeInfo) {
		Integer pageSize = mongoPage.getPageSize();
		// 默认3条
		pageSize = null == pageSize ? 3 : pageSize;
		/**
		 * 需要查询的字段
		 */
		BasicDBObject pro = new BasicDBObject();
		pro.put("_id", "$_id");
		pro.put("name", "$name");
		pro.put("price", "$price");
		pro.put("market_price", "$market_price");
		pro.put("sell_count", "$sell_count");
		pro.put("start_time", "$start_time");
		pro.put("end_time", "$end_time");
		pro.put("full_name", "$full_name");
		pro.put("brief_introduction", "$brief_introduction");
		pro.put("image_info", "$image_info");
		pro.put("vip_price", "$vip_price");
		pro.put("rack_time", "$rack_time");
		List<String> orArray = new ArrayList<String>();
		orArray.add("$vip_price");
		orArray.add("$price");
		pro.put("you_rate", new BasicDBObject("$divide", JSON.toJSON(orArray)));

		// 排序
		DBObject sort = new BasicDBObject();
		sort.put("", "");
		sort.put("sell_count", -1); // 距离相同，销售数量排最前。
		sort.put("rack_time", -1);// 销售数量相同，最新上架的商品排最前。
		return null;
	}

	/**
	 * @Title: findareaList
	 * @Description: 查询区域信息
	 * @author: shenshaocheng 2015年11月12日
	 * @modify: shenshaocheng 2015年11月12日
	 * @param clientId
	 * @param areaId
	 * @return
	 * @see com.froad.handler.MinatoSingleProductHandler#findareaList(java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<AreaInfo> findareaList(String clientId, String areaId) {
		List<AreaInfo> findareaList = new ArrayList<AreaInfo>();
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		try {
			MinatoSingleProductMapper mapper = sqlSession
					.getMapper(MinatoSingleProductMapper.class);
			findareaList = mapper.findAreaListInfo(clientId, areaId);
		} catch (Exception e) {
			LogCvt.error("获取区域信息 " + e.getMessage(), e);
		} finally {
			if (null != sqlSession) {
				sqlSession.close();
			}
		}

		return findareaList;
	}

	/**
	 * @Title: findAreaLocation
	 * @Description: 查找区域坐标信息
	 * @author: shenshaocheng 2015年11月17日
	 * @modify: shenshaocheng 2015年11月17日
	 * @param areaId
	 * @return 返回区域坐标信息
	 * @see com.froad.handler.MinatoSingleProductHandler#findAreaLocation(java.lang.String)
	 */
	public Location findAreaLocation(String areaId) {
		Location location = new Location();
		SqlSession sqlSession = MyBatisManager.getSqlSessionFactory()
				.openSession();
		try {			
			MinatoSingleProductMapper mapper = sqlSession
					.getMapper(MinatoSingleProductMapper.class);
			location = mapper.findAreaLocation(areaId);
		} catch (Exception e) {
			LogCvt.error("查找区域坐标信息异常 " + e.getMessage(), e);
		} finally {
			if(null != sqlSession) {  
				sqlSession.close(); 
			}
		}

		return location;
	}

	/**
	 * @Title: queryGroupProductsByPage
	 * @Description: 商品列表明细查询
	 * @author: shenshaocheng 2015年11月14日
	 * @modify: shenshaocheng 2015年11月14日
	 * @param minatoSingleParamInfo
	 *            前端参数
	 * @param productIdSet
	 *            参加活动商品ID集合
	 * @return
	 */
	@SuppressWarnings({ "unchecked" })
	public List<MinatoSingleProductResultInfo> queryGroupProductsByPage(
			MinatoSingleParamInfo minatoSingleParamInfo,
			Set<String> productIdSet) {
		BasicDBObject query = new BasicDBObject();
		// 查询条件
		// query.put("merchant_id", new BasicDBObject("$in", merchantIds));
		query.put("client_id", minatoSingleParamInfo.getClientId());
		query.put("_id", new BasicDBObject("$in", productIdSet));
		query.put("is_marketable", "1");// 只查询已上架商品
		// 商品按照所在门店距离由近至远进行排序，如距离相同按照商品销售数量由高到低排序，如销售数量相同则按商品上架时间由远至近排序
		Sort sort = new Sort("sell_count", OrderBy.DESC).on("rack_time",
				OrderBy.ASC);
		List<MinatoSingleProductResultInfo> productDetailList = (List<MinatoSingleProductResultInfo>) manager
				.findAll(query, sort, MongoTableName.CB_PRODUCT_DETAIL,
						MinatoSingleProductResultInfo.class);
		// mongoPage = manager.findByPageWithSortObject(mongoPage, query, new
		// BasicDBObject(), MongoTableName.CB_PRODUCT_DETAIL,
		// MinatoSingleProductResultInfo.class);
		return productDetailList;
	}

	/**
	 * @Title: getOutLetDetail
	 * @Description: 把区域内门店找出，并按当前位置由近及远排序
	 * @author: shenshaocheng 2015年11月12日
	 * @modify: shenshaocheng 2015年11月12日
	 * @param minatoSingleParamInfo
	 *            前端传入参数
	 * @param productIdsSet参加活动的商品ID
	 * @return 返回门店列表
	 */
	@SuppressWarnings("unchecked")
	public List<OutletDetail> getOutletDetail(
			MinatoSingleParamInfo minatoSingleParamInfo) {
		List<OutletDetail> result = null;
		try {
			DBObject dbObject = new BasicDBObject();
			dbObject.put("area_id",
					Integer.parseInt(minatoSingleParamInfo.getAreaId()));
			dbObject.put("client_id", minatoSingleParamInfo.getClientId());
			result = (List<OutletDetail>) manager.findAll(dbObject,
					MongoTableName.CB_OUTLET_DETAIL, OutletDetail.class);
			List<OutletDetail> orderByOutDetailList = new ArrayList<OutletDetail>();
			for (OutletDetail outletDetail : result) {
				if (outletDetail.getLocation() != null
						&& outletDetail.getLocation().getLongitude() > 0
						&& outletDetail.getLocation().getLatitude() > 0) {
					double distance = DistanceUtils.Distance(
							minatoSingleParamInfo.getMinatoSingleLocation()
									.getLongitude(), minatoSingleParamInfo
									.getMinatoSingleLocation().getLatitude(),
							outletDetail.getLocation().getLongitude(),
							outletDetail.getLocation().getLatitude());
					outletDetail.setDis(distance);
					orderByOutDetailList.add(outletDetail);
				}
			}
			Collections.sort(orderByOutDetailList);
			return orderByOutDetailList;
		} catch (Exception e) {
			LogCvt.error("查询门店明细信息列表失败，原因:" + e.getMessage(), e);
			result = null;
		}
		return result;
	}
	
	 /**
	  * @Title: findFirstProduct
	  * @Description: 查找第一个商品
	  * @author: shenshaocheng 2015年11月18日
	  * @modify: shenshaocheng 2015年11月18日
	  * @param clientId 客户端ID
	  * @param productId 商品ID
	  * @return 商品详情
	  * @see com.froad.handler.MinatoSingleProductHandler#findFirstProduct(java.lang.String, java.lang.String)
	  */	
	public MinatoSingleProductResultInfo findFirstProduct(String clientId, String productId) {
		BasicDBObject query = new BasicDBObject();
		// 查询条件
		// query.put("merchant_id", new BasicDBObject("$in", merchantIds));
		query.put("client_id", clientId);
		query.put("_id", productId);
		query.put("is_marketable", "1");// 只查询已上架商品
		MinatoSingleProductResultInfo productDetail = manager.findOne(query, MongoTableName.CB_PRODUCT_DETAIL,
						MinatoSingleProductResultInfo.class);
		return productDetail;
	}

	 /**
	  * @Title: getMerchantDetail
	  * @Description: TODO
	  * @author: shenshaocheng 2015年11月23日
	  * @modify: shenshaocheng 2015年11月23日
	  * @param minatoSingleParamInfo
	  * @return
	  * @see com.froad.handler.MinatoSingleProductHandler#getMerchantDetail(com.froad.po.MinatoSingleParamInfo)
	  */	
	@SuppressWarnings("unchecked")
	@Override
	public List<MerchantDetail> getMerchantDetail(
			MinatoSingleParamInfo minatoSingleParamInfo) {
		List<MerchantDetail> result = null;
		DBObject dbObject = new BasicDBObject();
		dbObject.put("area_id",
				Integer.parseInt(minatoSingleParamInfo.getAreaId()));
		dbObject.put("client_id", minatoSingleParamInfo.getClientId());
		result = (List<MerchantDetail>) manager.findAll(dbObject,
				MongoTableName.CB_OUTLET_DETAIL, MerchantDetail.class);
		return result;
	}
}
