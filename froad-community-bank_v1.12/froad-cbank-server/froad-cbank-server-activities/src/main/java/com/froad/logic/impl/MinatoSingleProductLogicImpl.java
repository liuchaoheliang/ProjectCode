package com.froad.logic.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.froad.db.redis.RedisCommon;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.ActiveLimitType;
import com.froad.enums.ProductType;
import com.froad.handler.ActiveSearchProductHandler;
import com.froad.handler.MinatoSingleProductHandler;
import com.froad.handler.impl.ActiveSearchProductHandlerImpl;
import com.froad.handler.impl.MinatoSingleProductHandlerImpl;
import com.froad.logback.LogCvt;
import com.froad.logic.MinatoSingleProductLogic;
import com.froad.po.ActiveResultInfo;
import com.froad.po.AreaInfo;
import com.froad.po.MinatoSingleLocationInfo;
import com.froad.po.MinatoSingleParamInfo;
import com.froad.po.MinatoSingleProductResultInfo;
import com.froad.po.OutletDetail;
import com.froad.po.WeightActivityTag;
import com.froad.po.mongo.Location;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.active.FindActiveVo;
import com.froad.thrift.vo.active.MinatoSingleProductListVo;
import com.froad.thrift.vo.active.MinatoSingleProductVo;
import com.froad.util.BeanUtil;
import com.froad.util.DistanceUtils;
import com.froad.util.RedisKeyUtil;
import com.froad.util.Validator;

/**
 * @ClassName: MinatoSingleProductLogicImpl
 * @Description: 凑单商品业务逻辑实现
 * @author froad-shenshaocheng 2015年11月10日
 * @modify froad-shenshaocheng 2015年11月10日
 */
public class MinatoSingleProductLogicImpl implements MinatoSingleProductLogic {

	/**
	 * @Fields activeSearchHandler : 查询商品活动信息数据逻辑接口
	 */
	private ActiveSearchProductHandler activeSearchHandler = new ActiveSearchProductHandlerImpl();

	/**
	 * @Fields minatoSingleProductHandler : 凑单数据查询接口
	 */
	private MinatoSingleProductHandler minatoSingleProductHandler = new MinatoSingleProductHandlerImpl();

	/**
	 * @Fields activeSearchHandler : Redis处理方法.
	 */
	private RedisManager redisManager = new RedisManager();

	/**
	 * @Title: findProductListOfMinatoSingle
	 * @Description: 商品凑单逻辑处理
	 * @author: shenshaocheng 2015年11月9日
	 * @modify: shenshaocheng 2015年11月9日
	 * @param minatoSingleParamInfo 前端参数
	 * @param pageVo 分页信息
	 * @return 凑单商品结果
	 * @see com.froad.logic.MinatoSingleProductLogic#findProductListOfMinatoSingle(com.froad.po.MinatoSingleParamInfo)
	 */
	@Override
	public MinatoSingleProductListVo findProductListOfMinatoSingle(
			MinatoSingleParamInfo minatoSingleParamInfo, PageVo pageVo) {
		MinatoSingleProductListVo minatoSingleProductListVo = new MinatoSingleProductListVo();
		long begtime = System.currentTimeMillis();
		// 判断前端传入CookieId，AreaId，ActiveId是否为空，任意一个为空，返回一个空数据集。
		if (minatoSingleParamInfo != null
				&& !Validator.isEmptyStr(minatoSingleParamInfo.getCookieId())
				&& !Validator.isEmptyStr(minatoSingleParamInfo.getAreaId())
				&& !Validator.isEmptyStr(minatoSingleParamInfo.getActiveId())) {
			try {
				// 判断该客户端是否进行过请求，是否已经全部区域请求完毕。
				if(redisManager.exists(
						RedisKeyUtil.cbbank_active_products_cookie_cookie_id(minatoSingleParamInfo.getCookieId()))) {
					List<String> areaList = redisManager.lrange(
							RedisKeyUtil.cbbank_active_products_cookie_cookie_id(minatoSingleParamInfo.getCookieId()), 
							0, -1);
					if(areaList != null && areaList.size() >0 && "-1".equals(areaList.get(0))) {
						LogCvt.debug("所有区域数据已经查询完毕。");
						minatoSingleProductListVo.setAreaId(minatoSingleParamInfo.getAreaId());
						minatoSingleProductListVo.setClientId(minatoSingleParamInfo.getClientId());
						minatoSingleProductListVo.setReqId(minatoSingleParamInfo.getReqId());
						LogCvt.debug(" 查询凑单商品结束,耗时:"+(System.currentTimeMillis()-begtime)+"毫秒");
						pageVo.hasNext = false;
						minatoSingleProductListVo.setPage(pageVo);
						return minatoSingleProductListVo;
					}		
				}
				// 前端没有获取到经纬度，则从Mysql查找补充
				if(minatoSingleParamInfo.getMinatoSingleLocation() == null) {
					Location location = this.minatoSingleProductHandler.findAreaLocation(
									minatoSingleParamInfo.getAreaId());
					if(location == null) {
						minatoSingleProductListVo.setAreaId(minatoSingleParamInfo.getAreaId());
						minatoSingleProductListVo.setClientId(minatoSingleParamInfo.getClientId());
						minatoSingleProductListVo.setReqId(minatoSingleParamInfo.getReqId());
						LogCvt.debug("输入区域信息有误。");
						pageVo.hasNext = false;
						minatoSingleProductListVo.setPage(pageVo);
						return minatoSingleProductListVo;
					}
					
					MinatoSingleLocationInfo minatoSingleLocationInfo = new MinatoSingleLocationInfo();
					minatoSingleLocationInfo.setLatitude(location.getLatitude());
					minatoSingleLocationInfo.setLongitude(location.getLongitude());
					minatoSingleParamInfo.setMinatoSingleLocation(minatoSingleLocationInfo); 
				}
				
				ActiveResultInfo activeBaseRule = new ActiveResultInfo();
				// 获取活动信息.
				Map<String, String> activeMap = RedisCommon.readFullCutActive(
						minatoSingleParamInfo.getClientId(),
						minatoSingleParamInfo.getActiveId());
				activeBaseRule.setActiveId(minatoSingleParamInfo.getActiveId());
				activeBaseRule.setActiveName(activeMap.get("activeName"));
				activeBaseRule.setDescription(activeMap.get("description"));
				activeBaseRule.setActiveType(activeMap.get("type"));
				// 限制类型
				String limitType = activeMap.get("limitType");
				Set<String> productIdsSet = new HashSet<String>();
				List<MinatoSingleProductResultInfo> firstProductList = new ArrayList<MinatoSingleProductResultInfo>();
				if(this.isFirstQuery(minatoSingleParamInfo)) {
					MinatoSingleProductResultInfo product = this.findFirstProduct(
							minatoSingleParamInfo.getClientId(), minatoSingleParamInfo.getProductId());
					firstProductList.add(product);
				}
				if(!redisManager.exists(RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
						minatoSingleParamInfo.getCookieId()))) {
					//productIdsSet = redisManager.getSet(RedisKeyUtil.cbbank_active_product_info_active_id(activeBaseRule
					//		.getActiveId()));
					//if (productIdsSet == null || productIdsSet.size() == 0) {
					productIdsSet = this.findProductIdSetByType(
							minatoSingleParamInfo, activeBaseRule, limitType,
							productIdsSet);
						
					//}
					// 是否初次请求凑单并包含商品ID
					if(this.isFirstQuery(minatoSingleParamInfo)) {
						productIdsSet.remove(minatoSingleParamInfo.getProductId());
					}
					// Redis特性，当对应主键的Set中值被清空后，对应key值也会删除。
					// 所以需要保留一个值，避免出现相同cookie查完所有商品后再请求又能被当成第一次查询的问题。
					productIdsSet.add("baseId");
					// 把参加活动的商品缓存起来，保留30分钟。
					redisManager.putSet(RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
							minatoSingleParamInfo.getCookieId()), productIdsSet);
					redisManager.expire(RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
							minatoSingleParamInfo.getCookieId()), 30 * 60);
				} else {
					productIdsSet = redisManager.getSet(RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
							minatoSingleParamInfo.getCookieId()));
				}
				// 同一个cookie内，productIdsSet始终有一个baseId,当只剩一个baseId时，表示所有商品查询已结束，直接返回，不再查询。
				if (productIdsSet.size() < 2) {
					LogCvt.debug("所有参加该活动的商品已查询完毕。");
					LogCvt.debug(" 查询凑单商品结束,耗时:"+(System.currentTimeMillis()-begtime)+"毫秒");
					minatoSingleProductListVo.setAreaId(minatoSingleParamInfo.getAreaId());
					minatoSingleProductListVo.setClientId(minatoSingleParamInfo.getClientId());
					minatoSingleProductListVo.setReqId(minatoSingleParamInfo.getReqId());
					pageVo.hasNext = false;
					minatoSingleProductListVo.setPage(pageVo);
					return minatoSingleProductListVo;
				} else {
					LogCvt.debug("取到参加活动的商品Id集合条数：" + (productIdsSet.size() - 1));
				}
				
				List<MinatoSingleProductResultInfo> returnProductList = this.minatoSingleProductHandler.queryGroupProductsByPage(
								minatoSingleParamInfo, productIdsSet);
				List<MinatoSingleProductResultInfo> returnProductPage = new ArrayList<MinatoSingleProductResultInfo>();
				if(ProductType.presell.getCode().equals(minatoSingleParamInfo.getProductType())) {
					LogCvt.debug("------精品预售凑单-----" + minatoSingleParamInfo.getProductType());
					for(MinatoSingleProductResultInfo product:returnProductList) {
						if(returnProductPage.size() < pageVo.pageSize) {
							returnProductPage.add(product);
							// 每取到一个商品，即移除缓存中的一个商品ID
							redisManager.srem(RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
									minatoSingleParamInfo.getCookieId()), product.getProductId());
						} else {
							break;
						}
					}
				} else {
					LogCvt.debug("------团购商品凑单-----" + minatoSingleParamInfo.getProductType());
					// 获取门店、商品相关数据并排序好的交集
					returnProductPage = this.findProductListbyPage(
							minatoSingleParamInfo, pageVo, 
							returnProductList, 0);
				}				
				
				FindActiveVo activeVo = new FindActiveVo();
				activeVo = (FindActiveVo) BeanUtil.copyProperties(
						FindActiveVo.class, activeBaseRule);
				// 组装最终凑单商品信息
				this.assembleProductListInfo(minatoSingleParamInfo, pageVo,
						minatoSingleProductListVo, firstProductList,
						returnProductPage, activeVo, returnProductList);
			} catch (Exception e) {
				LogCvt.error("查询凑单商品异常 " + e.getMessage(), e);
			}
		}
		
		LogCvt.debug(" 查询凑单商品结束,耗时:"+(System.currentTimeMillis()-begtime)+"毫秒");
		return minatoSingleProductListVo;
	}

	
	 /**
	  * @Title: isFirstQuery
	  * @Description: 是否第一次进入且包含前端商品ID
	  * @author: shenshaocheng 2015年11月18日
	  * @modify: shenshaocheng 2015年11月18日
	  * @param minatoSingleParamInfo 前端传入参数集
	  * @return 返回结果
	 */	
	@Override
	public boolean isFirstQuery(MinatoSingleParamInfo minatoSingleParamInfo) {
		if(!Validator.isEmptyStr(minatoSingleParamInfo.getProductId()) 
				&& !redisManager.exists(RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
						minatoSingleParamInfo.getCookieId()))){
			return true;
		}
		
		return false;
	}
	

	 /**
	  * @Title: findProductIdSetByType
	  * @Description: 根据活动限制类型，获取参加活动的商品集合
	  * @author: shenshaocheng 2015年11月23日
	  * @modify: shenshaocheng 2015年11月23日
	  * @param minatoSingleParamInfo 前端参数
	  * @param activeBaseRule 活动信息
	  * @param limitType 限制类型
	  * @param productIdsSet 商品ID集合
	  * @return 商品ID集合
	 */	
	private Set<String> findProductIdSetByType(
			MinatoSingleParamInfo minatoSingleParamInfo,
			ActiveResultInfo activeBaseRule, String limitType,
			Set<String> productIdsSet) {
		// 1商户活动；2门店活动；3商品活动;0不限
		List<WeightActivityTag> productIdsList = new ArrayList<WeightActivityTag>();
		try {
			if (limitType.equals(ActiveLimitType.Notlimited.getCode())) {
				productIdsSet = redisManager.getSet(RedisKeyUtil
						.cbbank_active_product_info_active_id(activeBaseRule
								.getActiveId()));
			} else if (limitType.equals(ActiveLimitType.limitMerchant.getCode())) {
				productIdsList = this.activeSearchHandler.findProductIdsByMerchant(
						activeBaseRule.getActiveId(),
						minatoSingleParamInfo.getClientId());
			} else if (limitType.equals(ActiveLimitType.limitMendian.getCode())) {
				productIdsList = this.activeSearchHandler.findProductIdsByOutlet(
						activeBaseRule.getActiveId(),
						minatoSingleParamInfo.getClientId());
			} else if (limitType.equals(ActiveLimitType.limitGoods.getCode())) {
				productIdsList = this.activeSearchHandler.findProductIdsByactiveId(
						activeBaseRule.getActiveId(),
						minatoSingleParamInfo.getClientId());
			}

			for (WeightActivityTag weightActivityTag : productIdsList) {
				productIdsSet.add(weightActivityTag.getElementId());
			}
		} catch (Exception e) {
			LogCvt.error("活动明细信息获取异常 " + e.getMessage(), e);
		}
		
		return productIdsSet;
	}

	 /**
	  * @Title: loopFindProductsList
	  * @Description: 循环查询不同区域的凑单商品。
	  * @author: shenshaocheng 2015年11月14日
	  * @modify: shenshaocheng 2015年11月14日
	  * @param minatoSingleParamInfo 前端参数
	  * @param pageVo 分页信息
	  * @param returnProductList 已查出的商品列表
	  * @param allProductList 所有参加活动的商品
	  * @return 凑单商品列表
	 */ 
	private List<MinatoSingleProductResultInfo> loopFindProductsList(
			MinatoSingleParamInfo minatoSingleParamInfo, PageVo pageVo,
			List<MinatoSingleProductResultInfo> returnProductList,
			List<MinatoSingleProductResultInfo> allProductList) {
		// 数据是否查询完毕（redis中获取是否还存在未查区域）
		boolean isFinish = true;
		// 用户所在区域已经找到的商品数量
		int productCounts = returnProductList.size();
		// 数据不够，需要继续从下一个区查找
		while (isFinish) {
			List<MinatoSingleProductResultInfo> nearByProductsList = new ArrayList<MinatoSingleProductResultInfo>();
			LogCvt.debug("正在查找区域" +  minatoSingleParamInfo.getAreaId() 
					+ "本次查询累计查找到 " + returnProductList.size() + " 条凑单商品");
			if ((returnProductList.size() == 0 || returnProductList
					.size() < pageVo.getPageSize()) && isFinish) {
				nearByProductsList = this
						.MinatoSingleProductVoLsitNearByArear(
								minatoSingleParamInfo, pageVo, allProductList, productCounts);
				Set<String> productIdSet = redisManager.getSet(
						RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
								minatoSingleParamInfo.getCookieId()));
				int areaCount = redisManager
						.lrange(
								RedisKeyUtil
										.cbbank_active_products_cookie_time_cookie_id(minatoSingleParamInfo
												.getCookieId()), 0, -1).size();
				if (areaCount < 2 || productIdSet.size() < 2) {
					isFinish = false;
				}

				returnProductList.addAll(nearByProductsList);
			} else {
				isFinish = false;
			}
		}
		return returnProductList;
	}

	 /**
	  * @Title: findProductListbyPage
	  * @Description: 获取商品、门店交集
	  * @author: shenshaocheng 2015年11月14日
	  * @modify: shenshaocheng 2015年11月14日
	  * @param minatoSingleParamInfo 前端参数
	  * @param pageVo 分页信息
	  * @param productResultInfoList 商品明细信息
	  * @return 商品、门店交集信息
	 */	
	private List<MinatoSingleProductResultInfo> findProductListbyPage(
			MinatoSingleParamInfo minatoSingleParamInfo, PageVo pageVo,
			List<MinatoSingleProductResultInfo> productResultInfoList, int productCount) {		
		List<MinatoSingleProductResultInfo> returnProductList = new ArrayList<MinatoSingleProductResultInfo>();
		try {
			// 获取区域内门店列表，并按距离排好序
			List<OutletDetail> outletDetailList = 
					this.minatoSingleProductHandler.getOutletDetail(minatoSingleParamInfo);
			// 团购商品 需要取门店交集，精品预售只需要按销量、上架时间排序
			if(ProductType.group.getCode().equals(minatoSingleParamInfo.getProductType())) {
				// 取门店和商品交集			
				if(productResultInfoList != null && productResultInfoList.size() > 0) {
					for (OutletDetail outLetVo : outletDetailList) {
						//for (MinatoSingleProductResultInfo product : productResultInfoList) {
						for(int i = 0; i < productResultInfoList.size(); i++) {
							MinatoSingleProductResultInfo product = productResultInfoList.get(i);
							if (outLetVo.getMerchantId()
									.equals(product.getMerchantId())
									&& !returnProductList.contains(product)) {
								returnProductList.add(product);
								// 每取到一个商品，即移除缓存中的一个商品ID
								redisManager.srem(RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
										minatoSingleParamInfo.getCookieId()), product.getProductId());
							}
							// 达到了每页需要显示数目，即可返回
							if(returnProductList.size() == pageVo.getPageSize() - productCount) {
								return returnProductList;
							}
						}
					}
				}	
			} 		
		} catch (Exception e) {
			LogCvt.error("获取商品明细信息异常 " + e.getMessage(), e);
		}

		return returnProductList;
	}

	/**
	 * @Title: MinatoSingleProductVoLsitNearByArear
	 * @Description: 查询下一个区域的商品
	 * @author: shenshaocheng 2015年11月11日
	 * @modify: shenshaocheng 2015年11月11日
	 * @param: minatoSingleParamInfo 凑单参数信息
	 * @param:pageVo分页信息
	 * @param :productIdsSet商品ID
	 * @param :filterVo 过滤条件
	 * @return 返回下一个区域的商品
	 */
	private List<MinatoSingleProductResultInfo> MinatoSingleProductVoLsitNearByArear(
			MinatoSingleParamInfo minatoSingleParamInfo, PageVo pageVo,
			List<MinatoSingleProductResultInfo> allProductList, int productCount) {
		List<MinatoSingleProductResultInfo> minatoSingleProductVoList = new ArrayList<MinatoSingleProductResultInfo>();

		// 查询所有区域的信息
		
		List<AreaInfo> areaListWithCoordinateList = new ArrayList<AreaInfo>();
		List<String> areaStringList = new ArrayList<String>();
		
		try {
			if (!redisManager
					.exists(RedisKeyUtil
							.cbbank_active_products_cookie_cookie_id(minatoSingleParamInfo
									.getCookieId()))) {
				List<AreaInfo> areaList = this.minatoSingleProductHandler.findareaList(
						minatoSingleParamInfo.getClientId(),
						minatoSingleParamInfo.getAreaId());
				// 计算各个区与当前位置的距离
				for (AreaInfo area : areaList) {
					if (!Validator.isEmptyStr(area.getLongitude())
							&& !Validator.isEmptyStr(area.getLatitude())) {
						double distance = DistanceUtils.Distance(
								minatoSingleParamInfo.getMinatoSingleLocation()
										.getLongitude(), minatoSingleParamInfo
										.getMinatoSingleLocation()
										.getLatitude(), Double.parseDouble(area
										.getLongitude()), Double
										.parseDouble(area.getLatitude()));
						area.setOrder(distance);
						areaListWithCoordinateList.add(area);						
					}
				}

				LogCvt.debug("获取到区域数量：" + areaListWithCoordinateList.size());
				// 根据各个区与当前距离进行排序
				Collections.sort(areaListWithCoordinateList);
				for(AreaInfo area : areaListWithCoordinateList) {
					areaStringList.add(area.getId().toString());
				}
				// 30分钟内，当所有区域查完后，依然需要一个baseId占位，继续请求时，表示所有区域查询完毕
				areaStringList.add("-1");
				redisManager
						.putList(
								RedisKeyUtil
										.cbbank_active_products_cookie_cookie_id(minatoSingleParamInfo
												.getCookieId()), areaStringList);
				redisManager
						.putList(
								RedisKeyUtil
										.cbbank_active_products_cookie_time_cookie_id(minatoSingleParamInfo
												.getCookieId()), areaStringList);
				// cookie缓存30分钟
				redisManager
						.expire(RedisKeyUtil
								.cbbank_active_products_cookie_cookie_id(minatoSingleParamInfo
										.getCookieId()), 30 * 60);
			}
			
			this.findAreaInfoByRedis(minatoSingleParamInfo);
			/*Set<String> productIdSet = redisManager.getSet(
					RedisKeyUtil.cbbank_active_products_cookie_product_id_set(
							minatoSingleParamInfo.getCookieId()));*/
			/*List<MinatoSingleProductResultInfo> productResultInfoList = 
					this.minatoSingleProductHandler.queryGroupProductsByPage(
							minatoSingleParamInfo, productIdSet);	*/
			// 重新获取下一个区的商品
			minatoSingleProductVoList = 
					 this.findProductListbyPage(minatoSingleParamInfo, pageVo, allProductList, productCount);
			if (minatoSingleProductVoList.size() == 0) {
				redisManager
						.lrem(RedisKeyUtil
								.cbbank_active_products_cookie_cookie_id(minatoSingleParamInfo
										.getCookieId()), 1, minatoSingleParamInfo.getAreaId());
			}
		} catch (Exception e) {
			LogCvt.error("获取最近区域异常 ：" + e.getMessage(),e);
		}
		
		return minatoSingleProductVoList;
	}

	/**
	 * @Title: findAreaInfoByRedis
	 * @Description: 处理Redis中区域缓存信息
	 * @author: shenshaocheng 2015年11月14日
	 * @modify: shenshaocheng 2015年11月14日
	 * @param minatoSingleParamInfo
	 *            前端参数
	 */
	private void findAreaInfoByRedis(MinatoSingleParamInfo minatoSingleParamInfo) {
		// 30分钟内多次进入缓存
		List<String> newAreaIdList = redisManager
				.lrange(RedisKeyUtil
						.cbbank_active_products_cookie_cookie_id(minatoSingleParamInfo
								.getCookieId()), 0, -1);
		if (!redisManager
				.exists(RedisKeyUtil
						.cbbank_active_products_cookie_time_cookie_id(minatoSingleParamInfo
								.getCookieId()))) {
			redisManager
					.putList(
							RedisKeyUtil
									.cbbank_active_products_cookie_time_cookie_id(minatoSingleParamInfo
											.getCookieId()), newAreaIdList);

		}

		List<String> newAreaIdListOntime = redisManager
				.lrange(RedisKeyUtil
						.cbbank_active_products_cookie_time_cookie_id(minatoSingleParamInfo
								.getCookieId()), 0, -1);
		String areaId = newAreaIdListOntime.get(0);
		minatoSingleParamInfo.setAreaId(areaId);
		redisManager
				.ltrim(RedisKeyUtil
						.cbbank_active_products_cookie_time_cookie_id(minatoSingleParamInfo
								.getCookieId()), 1, -1);
	}
	
	 /**
	  * @Title: findFirstProduct
	  * @Description: 查找第一个商品
	  * @author: shenshaocheng 2015年11月18日
	  * @modify: shenshaocheng 2015年11月18日
	  * @param clientId 客户端ID
	  * @param productId 商品ID
	  * @return 商品详情
	 */	
	private MinatoSingleProductResultInfo findFirstProduct(String clientId, String productId) {
		MinatoSingleProductResultInfo product = new MinatoSingleProductResultInfo();
		product = this.minatoSingleProductHandler.findFirstProduct(clientId, productId);
		return product;
	} 
	
	 /**
	  * @Title: assembleProductListInfo
	  * @Description: 组装最终返回凑单商品VO
	  * @author: shenshaocheng 2015年11月19日
	  * @modify: shenshaocheng 2015年11月19日
	  * @param minatoSingleParamInfo 前端条件
	  * @param pageVo 分页信息
	  * @param minatoSingleProductListVo 凑单商品返回VO
	  * @param firstProductList 最终返回的商品列表
	  * @param returnProductPage 查询到的商品列表
	  * @param allProductList 参加活动的商品列表
	  * @param activeVo 活动信息
	 */	
	private void assembleProductListInfo(
			MinatoSingleParamInfo minatoSingleParamInfo, PageVo pageVo,
			MinatoSingleProductListVo minatoSingleProductListVo,
			List<MinatoSingleProductResultInfo> firstProductList,
			List<MinatoSingleProductResultInfo> returnProductPage,			
			FindActiveVo activeVo,
			List<MinatoSingleProductResultInfo> allProductList) {
		// 把交集后的商品，转回VO传出
		List<MinatoSingleProductVo> minatoSingleProductVoList = new ArrayList<MinatoSingleProductVo>();				
		// 团购才需要区域循环
		if(ProductType.group.getCode().equals(minatoSingleParamInfo.getProductType())) {
			returnProductPage = this.loopFindProductsList(minatoSingleParamInfo,
					pageVo, returnProductPage, allProductList);
		}
		
		// 如果存在传入了商品ID，则把该商品放在第一个。
		if(firstProductList.size() > 0) {					
			firstProductList.addAll(returnProductPage);
		} else {
			firstProductList =  returnProductPage;
		}
		
		if(firstProductList != null && firstProductList.size() > 0) {
			for (MinatoSingleProductResultInfo productResultInfo : firstProductList) {
				MinatoSingleProductVo minatoSingleProductVo = new MinatoSingleProductVo();
				minatoSingleProductVo = (MinatoSingleProductVo) BeanUtil
						.copyProperties(MinatoSingleProductVo.class,
								productResultInfo);
				// 取商品小图
				if(productResultInfo.getImageInfoList() != null
						&& productResultInfo.getImageInfoList().size() > 0) {
					minatoSingleProductVo.setSmallImgUrl(
							productResultInfo.getImageInfoList().get(0).getThumbnail());
				}
				
				minatoSingleProductVo.setPrice(minatoSingleProductVo.getPrice()/1000);
				minatoSingleProductVo.setMarketPrice(minatoSingleProductVo.getMarketPrice()/1000);
				minatoSingleProductVo.setVipPrice(minatoSingleProductVo.getVipPrice()/1000);
				minatoSingleProductVoList.add(minatoSingleProductVo);
			}
			minatoSingleProductListVo.setActiveVo(activeVo);
			minatoSingleProductListVo.setAreaId(minatoSingleParamInfo.getAreaId());
			minatoSingleProductListVo.setReqId(minatoSingleParamInfo
					.getReqId());
			minatoSingleProductListVo.setClientId(minatoSingleParamInfo
					.getClientId());
		}		
		// 如果单页返回的数据少于每页要求数目，则告知前端，没有更多数据了。
		pageVo.hasNext = firstProductList.size() < pageVo.pageSize ? false : true;
		minatoSingleProductListVo.setPage(pageVo);
		minatoSingleProductListVo
				.setMinatoSingleProductVoList(minatoSingleProductVoList);
		// 凑单查询完毕，删除当次区域记录
		redisManager.del(RedisKeyUtil
				.cbbank_active_products_cookie_time_cookie_id(minatoSingleParamInfo
						.getCookieId()));
		LogCvt.debug("返回凑单商品数量:"
				+ minatoSingleProductListVo.getMinatoSingleProductVoList()
						.size() + " 个");
	}
}
