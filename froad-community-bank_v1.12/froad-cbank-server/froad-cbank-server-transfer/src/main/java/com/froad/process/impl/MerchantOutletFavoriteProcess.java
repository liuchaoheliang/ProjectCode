package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.common.mongo.MerchantOutletFavoriteMongo;
import com.froad.common.mongo.OutletDetailMongo;
import com.froad.common.mongo.ProductFavoriteMongo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.DeliverInfo;
import com.froad.db.chonggou.entity.MerchantOutletFavorite;
import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.chonggou.entity.OutletDetail;
import com.froad.db.chonggou.entity.ProductDetail;
import com.froad.db.chonggou.entity.ProductImageCG;
import com.froad.db.chonggou.entity.RecvInfo;
import com.froad.db.chonggou.entity.StoreOutletInfo;
import com.froad.db.chonggou.entity.StoreProductInfo;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.OutletMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.mongo.impl.MongoManager;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.MerchantFavorite;
import com.froad.fft.persistent.entity.ProductFavorite;
import com.froad.fft.persistent.entity.Receiver;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;
import com.froad.util.SimpleID;

public class MerchantOutletFavoriteProcess extends AbstractProcess{

	
	MerchantOutletFavoriteMongo mongo = new MerchantOutletFavoriteMongo();
	MongoManager manager = new MongoManager();
	
    private static SimpleID simpleID = new SimpleID(ModuleID.recvInfo);
    private static SimpleID simpledelID = new SimpleID(ModuleID.deliverInfo);
	
	//收藏上限
	private static int FAVORITELIMIT = 100;
	
	//收货地址限
	private static int RECVLIMIT = 5;
	
	//提货信息上限
	private static int DELIVERYLIMIT = 5;
	
	//商品详情mongo操作
	private OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
	
	public MerchantOutletFavoriteProcess(String name,
			ProcessServiceConfig config) {
		super(name, config);
	}
	
	private final Map<String,String> map = new HashMap<String,String>();
	
//	private final Map<String,String> mapOutlet = new HashMap<String,String>();
//	private final Map<String,String> mapProduct = new HashMap<String,String>();
//	private final Map<String,String> mapRecvInfo = new HashMap<String,String>();
//	

	@Override
	public void process() {
		
		TransferMapper mapper = sqlSession.getMapper(TransferMapper.class);
		Transfer transfer = null;
		
		//商户收藏
		com.froad.db.ahui.mappers.MerchantFavoriteMapper mapper1 = ahSqlSession.getMapper(com.froad.db.ahui.mappers.MerchantFavoriteMapper.class);
		
		//商品收藏
		com.froad.db.ahui.mappers.ProductFavoriteMapper mapper2 = ahSqlSession.getMapper(com.froad.db.ahui.mappers.ProductFavoriteMapper.class);
		
		com.froad.db.ahui.mappers.ReceiverMapper mapper3 = ahSqlSession.getMapper(com.froad.db.ahui.mappers.ReceiverMapper.class);
		
		
		List<MerchantFavorite> lMerchant = mapper1.selectByConditions();
			
		//门店
		StoreOutletInfo info1 = null;
		for (MerchantFavorite merchantFavorite : lMerchant) {
			info1 = new StoreOutletInfo();
			
			transfer = mapper.queryNewId(String.valueOf(merchantFavorite.getMerchantOutletId()), TransferTypeEnum.outlet_id);
			if(transfer != null)
				info1.setOutletId(transfer.getNewId());
			else{
//				LogCvt.error("==========数据迁移transfer对象为空 获取不到outlet_id 门店收藏StoreOutletInfo对象 outlet_id设置为0===============");
				info1.setOutletId(String.valueOf(merchantFavorite.getMerchantOutletId()));
			}

			transfer = mapper.queryNewId(String.valueOf(merchantFavorite.getMerchantId()), TransferTypeEnum.merchant_id);
			if(transfer != null)
				info1.setMerchantId(transfer.getNewId());
			else{
//				LogCvt.error("==========数据迁移transfer对象为空 获取不到merchantId 门店收藏StoreOutletInfo对象 merchantId设置为0===============");
				info1.setMerchantId(String.valueOf(merchantFavorite.getMerchantId()));
			}
			
			addStoreOutletInfo(Constans.clientId, merchantFavorite.getMemberCode(), info1);
		}
		
		
		//商品
		List<ProductFavorite> lProduct = mapper2.selectByCondition();
		StoreProductInfo info2 = null;
//		ProductDetail pd  = null;
		for (ProductFavorite productFavorite : lProduct) {
			info2 = new StoreProductInfo();
			
			transfer = mapper.queryNewId(String.valueOf(productFavorite.getProductId()), TransferTypeEnum.product_id);
			if(transfer != null){
				info2.setProductId(transfer.getNewId());
			}else{
				info2.setProductId(String.valueOf(productFavorite.getProductId()));
			}
//			pd = 
//			info2.setProductName("");
//			info2.setProductImage("");
			
			addStoreProductInfo(Constans.clientId, productFavorite.getMemberCode(), info2);
			
		}
		
		RecvInfo info3 = null;
//		DeliverInfo info4 = null;
		List<Receiver> lreceiver = mapper3.findReceiverByCondition(new Receiver());
		for (Receiver receiver : lreceiver) {
			transfer = mapper.queryNewId(String.valueOf(receiver.getAreaId()), TransferTypeEnum.area_id);
//			if(receiver.getType() == 2){
			
			if(transfer != null 
					&& !StringUtils.isBlank(receiver.getAddress() )){
				info3 = new RecvInfo();
				info3.setConsignee(receiver.getConsignee());
				info3.setAddress(receiver.getAddress());
				info3.setPhone(receiver.getPhone());
				info3.setIsDefault(receiver.getIsDefault() ? "1" : "0");
				
				info3.setAreaId(Long.valueOf(transfer.getNewId()));
				info3.setIsEnable("1");
				
				addRecvInfo(Constans.clientId, receiver.getMemberCode(),info3);
			}
//			}else{
//				info4 = new DeliverInfo();
//				info4.setConsignee(receiver.getConsignee());
//				if(transfer != null)
//					info4.setAreaId(Long.valueOf(transfer.getNewId()));
//				else
//					info4.setAreaId(0l);
//				info4.setDeliveryId("");//带确认
//				info4.setIsDefault(String.valueOf(receiver.getIsDefault()));
//				info4.setPhone(receiver.getPhone());
//				addDeliverInfo(Constans.clientId, receiver.getMemberCode(), info4);
//			}
		}
		
		
	}

	
	
	//商户门店收藏
	public void addStoreOutletInfo(String clientId, long memberCode,StoreOutletInfo storeOutletInfo){
		OutletDetail outletDetail = null;
		int store_count=1;
		String id=clientId+"_"+memberCode;
		String outletId = storeOutletInfo.getOutletId();
		try {
			outletDetail = outletDetailMongo.findOutletDetailByoutletId(outletId);
			
			if(outletDetail == null){
				LogCvt.error("添加失败，门店详情表中不存在该门店-->"+outletId);
			}else{
				storeOutletInfo.setOutletImage(outletDetail.getDefaultImage());
				storeOutletInfo.setOutletName(outletDetail.getOutletName());
				
				Integer storeCount = outletDetail.getStoreCount();
				if(storeCount == null){
					mongo.addStoreOutletStoreCount(outletId);
					storeCount =  0;
				}
				store_count =storeCount+store_count;
//				MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
				
//				if(m != null){
				if(map.containsKey(id)){
//					int storlutletSize = 0;
//					List<StoreOutletInfo> stoList = mongo.queryStoreOutletInfo(clientId, memberCode);
//					if(stoList == null){
//						storlutletSize = 0;
//					}else{
//						storlutletSize = stoList.size();
//					}
//					if(storlutletSize >= FAVORITELIMIT)
//					{
//						LogCvt.error("添加失败，添加门店收藏数已达上限"+FAVORITELIMIT);
//					}
					int count = isExitsStoreOutletInfo(clientId,memberCode,storeOutletInfo.getOutletId());
					if(count == 0){
						mongo.updateStoreOutletInfo(clientId, memberCode, storeOutletInfo);
						mongo.updateoutletDetail(clientId, memberCode, outletId, store_count);
					}else{
						LogCvt.error("门店已收藏，不能再次收藏");
					}
				}else{
					
					mongo.addStoreOutletInfos(clientId, memberCode, storeOutletInfo);
					mongo.updateoutletDetail(clientId, memberCode, outletId, store_count);
					map.put(id, id+outletId);
				}
				
			}
//			LogCvt.info("门店收藏成功");
		} catch (Exception e) {
			LogCvt.error("Mongo门店收藏失败，原因:" + e.getMessage(), e);
		}
	}
	
	public int isExitsStoreOutletInfo(String clientId, long memberCode,String outletId){
		int result = 0;
		try {
			result = mongo.isExitsStoreOutletInfo(clientId, memberCode, outletId);
		} catch (Exception e) {
			LogCvt.error("查询StoreOutletInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	
	
	/** 添加收货信息
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
	
	
	public int  addRecvInfo(String clientId, long memberCode,RecvInfo recvInfo){
		int result =0;
		try {
			recvInfo.setRecvId(simpleID.nextId());
			String id = clientId+"_"+memberCode;
//			MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
//			if(m != null){
			if(map.containsKey(id)){
				
//				List<RecvInfo> list=mongo.queryRecvInfo(clientId, memberCode);
//				
//				if(list !=null && list.size() >= RECVLIMIT) 
//				{
//					LogCvt.error("添加失败，添加收货信息数已达上限"+RECVLIMIT);
//					return 0;
//				}
//				//查询是否存在
				int count = isExitsRecvInfo(clientId,memberCode,recvInfo.getRecvId());
				if(count == 0){  //不存在
					if("1".equals(recvInfo.getIsDefault())){
						mongo.updateDefaultRecvInfo(clientId,memberCode,"0","1");
					}
					result =mongo.appedRecvInfo(clientId, memberCode,  recvInfo);
				}
			}else{
				List<RecvInfo> stoList = new ArrayList<RecvInfo>();
				stoList.add(recvInfo);
				result = mongo.addRecvInfo2(clientId, memberCode, stoList);
				map.put(id, recvInfo.getRecvId());
			}
			
		} catch (Exception e) {
			LogCvt.error("插入RecvInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	/** 收货信息是否存在
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
	public int isExitsRecvInfo(String clientId, long memberCode,String recvId){
		int result = 0;
		try {
			result = mongo.isExitsRecvInfo(clientId, memberCode, recvId);
		} catch (Exception e) {
			LogCvt.error("查询RecvInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	
	/** 
	 * 增加商品收藏
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
	public void addStoreProductInfo(String clientId, long memberCode,StoreProductInfo storeProductInfo){
		ProductFavoriteMongo mongo = new ProductFavoriteMongo();
		ProductDetail productDetail = null;
		int store_count=1;
		String id=clientId+"_"+memberCode;
		String productId = storeProductInfo.getProductId();
		try {
			productDetail = mongo.findProductDetailByoutletId(productId);
			if(productDetail != null){
				
				storeProductInfo.setProductName(productDetail.getName());
//				List<ProductImage> list = productDetail.getImageInfo();
//				for (ProductImage productImage : list) {
//					
//				}
				ProductImageCG image = productDetail.getImageInfo().get(0);
				storeProductInfo.setProductImage(image.getThumbnail());
				
				
				Integer storeCount = productDetail.getStoreCount();
				if(storeCount == null){
					mongo.addStoreOutletStoreCount(productId);
					productDetail = mongo.findProductDetailByoutletId(productId);
					storeCount =  0;
				}
				store_count =storeCount+store_count;
//				MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
				if(map.containsKey(id)){
//				if(m != null){
//					int storlutletSize = 0;
//					List<StoreProductInfo> proList=mongo.queryStoreProductInfo(clientId, memberCode);
//					if(proList == null){
//						storlutletSize = 0;
//					}else{
//						storlutletSize = proList.size();
//					}
//					if(storlutletSize >= FAVORITELIMIT)
//					{
//						LogCvt.error("添加失败，添加商品收藏数已达上限"+FAVORITELIMIT);
//					}
					int count = isExitsStoreProductInfo(clientId,memberCode,storeProductInfo.getProductId());
					if(count == 0){
						mongo.updateStoreProductInfo(clientId, memberCode, storeProductInfo);
						mongo.updateproductDetail(clientId, memberCode, productId, store_count);
					}else{
						LogCvt.error("商品已收藏，不能再次收藏");
					}
				}else{

					
					mongo.addStoreProductInfos(clientId, memberCode, storeProductInfo);
					mongo.updateproductDetail(clientId, memberCode, productId, store_count);
					map.put(id, id+productId);
				}
				
			}else{
				LogCvt.error("添加失败，商品详情表中不存在该商品-->"+productId);
			}
//			LogCvt.info("商品收藏成功");
		} catch (Exception e) {
			LogCvt.error("插入StoreProductInfo失败，原因:" + e.getMessage(), e);
		}
	}
	
	
	/** 是否收藏商品
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
	public int isExitsStoreProductInfo(String clientId, long memberCode,String productId){
		ProductFavoriteMongo mongo = new ProductFavoriteMongo();
		int result = 0;
		try {
			result = mongo.isExitsStoreProductInfo(clientId, memberCode, productId);
		} catch (Exception e) {
			LogCvt.error("查询MerchantOutletFavorite失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
	
	
	/** 增加提货信息
	* @Title: addDeliverInfo 
	* @Description: 
	* @author longyunbo
	* @param @param clientId
	* @param @param memberCode
	* @param @param deliveryId
	* @param @param deliverInfo
	* @param @return
	* @return int
	* @throws 
	*/
	public void addDeliverInfo(String clientId, long memberCode,DeliverInfo deliverInfo) {
//		int result =0;
		try {
			deliverInfo.setDeliverId(simpledelID.nextId());
			String id = clientId+"_"+memberCode;
			MerchantOutletFavorite m= mongo.findMerchantOutletFavoriteById(id);
			if(m != null){
				
//				List<DeliverInfo> list=mongo.queryDeliverInfo(clientId, memberCode);
//				
//				if(list !=null && list.size() >= DELIVERYLIMIT) 
//				{
//					LogCvt.error("添加失败，添加提货信息数已达上限"+DELIVERYLIMIT);
//				}
//				//查询是否存在
				int count = isExitsDeliverInfo(clientId,memberCode,deliverInfo.getDeliveryId());
				if(count == 0){  //不存在
					if("1".equals(deliverInfo.getIsDefault())){
						mongo.updateDefaultDeliverInfo(clientId,memberCode,"0","1");
					}
					mongo.appedDeliverInfo(clientId, memberCode,  deliverInfo);
				}
			}else{
				List<DeliverInfo> stoList = new ArrayList<DeliverInfo>();
				stoList.add(deliverInfo);
				mongo.addDeliverInfo2(clientId, memberCode, stoList);
			}
//			ConsoleLogger.info("添加提货信息成功");
		} catch (Exception e) {
			LogCvt.error("插入DeliverInfo失败，原因:" + e.getMessage(), e);
		}
	}
	
	public int isExitsDeliverInfo(String clientId, long memberCode,String deliveryId){
		int result = 0;
		try {
			result = mongo.isExitsDeliverInfo(clientId, memberCode, deliveryId);
		} catch (Exception e) {
			LogCvt.error("查询DeliverInfo失败，原因:" + e.getMessage(), e);
		}
		return result;
	}
}

