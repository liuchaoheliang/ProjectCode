package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.cbank.persistent.entity.MerchantOutletFavorite;
import com.froad.cbank.persistent.entity.ProductFavorite;
import com.froad.cbank.persistent.entity.Receiver;
import com.froad.common.mongo.MerchantOutletFavoriteMongo;
import com.froad.common.mongo.OutletDetailMongo;
import com.froad.common.mongo.ProductFavoriteMongo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.DeliverInfo;
import com.froad.db.chonggou.entity.OutletDetail;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.ProductImageCG;
import com.froad.db.chonggou.entity.RecvInfo;
import com.froad.db.chonggou.entity.StoreOutletInfo;
import com.froad.db.chonggou.entity.StoreProductInfo;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantOutletFavoriteMapper;
import com.froad.db.chongqing.mappers.ProductFavoriteMapper;
import com.froad.db.chongqing.mappers.ReceiverMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.SimpleID;

public class MerchantOutletFavoriteProcess extends AbstractProcess {

	private static SimpleID simpleID = new SimpleID(ModuleID.recvInfo);
	private MerchantOutletFavoriteMongo mongos = null;
	private ReceiverMapper receiverMapper = null;
	private TransferMapper transferMapper = null;
	private MerchantOutletFavoriteMapper mOutletFavoriteMapper = null;
	private ProductFavoriteMapper pFavoriteMapper = null;
	
	private final Map<String, String> map = new HashMap<String, String>();
	private final Map<String, String> areamap = new HashMap<String, String>();
	//门店
	private final Map<String, String> outletMap = new HashMap<String, String>();
	
	//商户
	private final Map<String, String> merchantMap = new HashMap<String, String>();
	
	private final Map<String, String> productMap = new HashMap<String, String>();
	

	// 商品详情mongo操作
	private OutletDetailMongo outletDetailMongo = null;
	private ProductFavoriteMongo productMongos = new ProductFavoriteMongo();

	public MerchantOutletFavoriteProcess(String name,
			ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		outletDetailMongo = new OutletDetailMongo(mongo);
		mongos = new MerchantOutletFavoriteMongo(mongo);
		receiverMapper = cqSqlSession.getMapper(ReceiverMapper.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		mOutletFavoriteMapper = cqSqlSession.getMapper(MerchantOutletFavoriteMapper.class);
		pFavoriteMapper = cqSqlSession.getMapper(ProductFavoriteMapper.class);
		
		List<Transfer> listArea = transferMapper.queryGroupList(TransferTypeEnum.area_id);
		for (Transfer t : listArea) {
			areamap.put(t.getOldId(), t.getNewId());
		}
		
		List<Transfer> listOutlet = transferMapper.queryGroupList(TransferTypeEnum.outlet_id);
		for (Transfer t : listOutlet) {
			outletMap.put(t.getOldId(), t.getNewId());
		}
		
		List<Transfer> listMerchant = transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
		for (Transfer t : listMerchant) {
			merchantMap.put(t.getOldId(), t.getNewId());
		}
		
		List<Transfer> listProduct = transferMapper.queryGroupList(TransferTypeEnum.product_id);
		for (Transfer t : listProduct) {
			productMap.put(t.getOldId(), t.getNewId());
		}
		
		
		int n = productMongos.deleteMerchantOutletFavoriteByClientId(Constans.clientId);
		if(n > 0){
			LogCvt.info("删除成功重庆收藏表数据");
		}
		
	}

	@Override
	public void process() {

		transferRecvInfo();
		transferStoreOutletInfo();
		transferStoreProductInfo();
	}
	
	//收货地址
	public void transferRecvInfo(){
		List<Receiver> listReceiver = receiverMapper.selectByCondition(new Receiver());
		RecvInfo info = null;
		Transfer transfer = null;
		for (Receiver obj : listReceiver) {
				info = new RecvInfo();
				info.setAddress(StringUtils.isBlank(obj.getAddress()) ? "" : obj.getAddress());
				info.setAreaId(Long.valueOf(areamap.get(obj.getAreaId()+"") == null ?
						"0" : areamap.get(obj.getAreaId()+"")));
				info.setConsignee(obj.getConsignee());
				info.setIsDefault(obj.getIsDefault() ? "1" : "0");
//				if(StringUtils.isBlank(obj.getAddress())){
//					info.setIsEnable("0");
//				}else{
//					info.setIsEnable(obj.getIsDelete() ? "0" : "1");
//				}
				info.setIsEnable(obj.getIsDelete() ? "0" : "1");
				info.setPhone(obj.getPhone());
				
				addRecvInfo(Constans.clientId, obj.getMemberCode(),info);
				
				transfer = new Transfer();
				transfer.setOldId(obj.getId().toString());
				transfer.setNewId(info.getRecvId());
				transfer.setType(TransferTypeEnum.receiver_id);
				transferMapper.insert(transfer);
		}
	}
	
	//
	public void transferStoreOutletInfo(){
		List<MerchantOutletFavorite> list = mOutletFavoriteMapper.selectByCondition(new MerchantOutletFavorite());
		StoreOutletInfo info = null;
		for (MerchantOutletFavorite obj : list) {
			info = new StoreOutletInfo();
			info.setMerchantId(merchantMap.get(obj.getMerchantId()+""));
			info.setOutletId(outletMap.get(obj.getMerchantOutletId()+""));
			info.setOutletName(obj.getMerchantOutletName());
//			info.setOutletImage();
			addStoreOutletInfo(Constans.clientId, obj.getMemberCode(), info);
		}
	}
	
	public void transferStoreProductInfo(){
		List<ProductFavorite> list = pFavoriteMapper.selectByCondition(new ProductFavorite());
		StoreProductInfo info = null;
		for (ProductFavorite obj : list) {
			info = new StoreProductInfo();
			info.setProductId(productMap.get(obj.getProductId()+""));
			info.setProductName(obj.getProductName());
			
			addStoreProductInfo(Constans.clientId, obj.getMemberCode(), info);
		}
	}

	// 商户门店收藏
	public void addStoreOutletInfo(String clientId, long memberCode,
			StoreOutletInfo storeOutletInfo) {
		OutletDetail outletDetail = null;
		int store_count = 1;
		String id = clientId + "_" + memberCode;
		String outletId = storeOutletInfo.getOutletId();
		outletDetail = outletDetailMongo.findOutletDetailByoutletId(outletId);

		if (outletDetail == null) {
			LogCvt.error("添加失败，门店详情表中不存在该门店-->" + outletId);
		} else {
			storeOutletInfo.setOutletImage(outletDetail.getDefaultImage());
			storeOutletInfo.setOutletName(outletDetail.getOutletName());

			Integer storeCount = outletDetail.getStoreCount();
			if (storeCount == null) {
				mongos.addStoreOutletStoreCount(outletId);
				storeCount = 0;
			}
			store_count = storeCount + store_count;

			if (map.containsKey(id)) {
				int count = isExitsStoreOutletInfo(clientId, memberCode,
						storeOutletInfo.getOutletId());
				if (count == 0) {
					mongos.updateStoreOutletInfo(clientId, memberCode,
							storeOutletInfo);
					mongos.updateoutletDetail(clientId, memberCode, outletId,
							store_count);
				} else {
					LogCvt.error("门店已收藏，不能再次收藏");
				}
			} else {

				mongos.addStoreOutletInfos(clientId, memberCode,
						storeOutletInfo);
				mongos.updateoutletDetail(clientId, memberCode, outletId,
						store_count);
				map.put(id, id + outletId);
			}

		}
	}

	public int isExitsStoreOutletInfo(String clientId, long memberCode,
			String outletId) {
		int result = mongos.isExitsStoreOutletInfo(clientId, memberCode,
				outletId);
		return result;
	}

	/**
	 * 添加收货信息
	 * 
	 * @Title: addRecvInfo
	 * @Description:
	 * @author longyunbo
	 * @param @param clientId
	 * @param @param memberCode
	 * @param @param recvInfo
	 * @param @return
	 * @return int
	 * @throws
	 */

	public int addRecvInfo(String clientId, long memberCode, RecvInfo recvInfo) {
		int result = 0;
		recvInfo.setRecvId(simpleID.nextId());
		String id = clientId + "_" + memberCode;
		if (map.containsKey(id)) {

			// //查询是否存在
			int count = isExitsRecvInfo(clientId, memberCode,
					recvInfo.getRecvId());
			if (count == 0) { // 不存在
				if ("1".equals(recvInfo.getIsDefault())) {
					mongos.updateDefaultRecvInfo(clientId, memberCode, "0", "1");
				}
				result = mongos.appedRecvInfo(clientId, memberCode, recvInfo);
			}
		} else {
			List<RecvInfo> stoList = new ArrayList<RecvInfo>();
			stoList.add(recvInfo);
			result = mongos.addRecvInfo2(clientId, memberCode, stoList);
			map.put(id, recvInfo.getRecvId());
		}

		return result;
	}

	/**
	 * 收货信息是否存在
	 * 
	 * @Title: isExitsRecvInfo
	 * @Description:
	 * @author longyunbo
	 * @param @param clientId
	 * @param @param memberCode
	 * @param @param recvId
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int isExitsRecvInfo(String clientId, long memberCode, String recvId) {
		int result = mongos.isExitsRecvInfo(clientId, memberCode, recvId);
		return result;
	}

	/**
	 * 增加商品收藏
	 * 
	 * @Title: addStoreProductInfo
	 * @Description:
	 * @author longyunbo
	 * @param @param clientId
	 * @param @param memberCode
	 * @param @param productId
	 * @param @param storeProductInfo
	 * @param @return
	 * @return int
	 * @throws
	 */
	public void addStoreProductInfo(String clientId, long memberCode,
			StoreProductInfo storeProductInfo) {
		
		ProductDetail productDetail = null;
		int store_count = 1;
		String id = clientId + "_" + memberCode;
		String productId = storeProductInfo.getProductId();
		productDetail = productMongos.findProductDetailByoutletId(productId);
		if (productDetail != null) {

			ProductImageCG image = productDetail.getImageInfo().get(0);
			storeProductInfo.setProductImage(image.getThumbnail());

			Integer storeCount = productDetail.getStoreCount();
			if (storeCount == null) {
				productMongos.addStoreOutletStoreCount(productId);
				productDetail = productMongos.findProductDetailByoutletId(productId);
				storeCount = 0;
			}
			store_count = storeCount + store_count;
			if (map.containsKey(id)) {
				int count = isExitsStoreProductInfo(clientId, memberCode,
						storeProductInfo.getProductId());
				if (count == 0) {
					productMongos.updateStoreProductInfo(clientId, memberCode,
							storeProductInfo);
					productMongos.updateproductDetail(clientId, memberCode, productId,
							store_count);
				} else {
					LogCvt.error("商品已收藏，不能再次收藏");
				}
			} else {

				productMongos.addStoreProductInfos(clientId, memberCode,
						storeProductInfo);
				productMongos.updateproductDetail(clientId, memberCode, productId,
						store_count);
				map.put(id, id + productId);
			}

		} else {
			LogCvt.error("添加失败，商品详情表中不存在该商品-->" + productId);
		}
	}

	/**
	 * 是否收藏商品
	 * 
	 * @Title: countProducts
	 * @Description:
	 * @author longyunbo
	 * @param @param clientId
	 * @param @param memberCode
	 * @param @param productId
	 * @param @return
	 * @return int
	 * @throws
	 */
	public int isExitsStoreProductInfo(String clientId, long memberCode,
			String productId) {
		ProductFavoriteMongo mongos = new ProductFavoriteMongo();
		int result = mongos.isExitsStoreProductInfo(clientId, memberCode,
				productId);
		return result;
	}
}
