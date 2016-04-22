package com.froad.process.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.cbank.persistent.entity.MerchantOutlet;
import com.froad.cbank.persistent.entity.MerchantOutletPhoto;
import com.froad.common.mongo.OutletDetailMongo;
import com.froad.common.redis.MerchantOutletPhotoRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MerchantOutletPhotoCG;
import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantOutletPhotoMapperCG;
import com.froad.db.chonggou.mappers.OutletMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantOutletMapper;
import com.froad.db.chongqing.mappers.MerchantOutletPhotoMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.RedisKeyUtil;

public class MerchantOutletPhotoProcess extends AbstractProcess {

	private MerchantOutletPhotoMapper merPhotoMapper = null;
	private MerchantOutletPhotoMapperCG mPhotoMapperCG = null;
	private OutletMapperCG outletMapperCG = null;
	private TransferMapper transferMapper = null;
	private MerchantOutletMapper mOutletMapper = null;

	private OutletCG outlet = null;
	final Set<Long> set = new HashSet<Long>();

	private final Map<String, String> outletMap = new HashMap<String, String>();
	private final Map<String, String> merchantMap = new HashMap<String, String>();

	public MerchantOutletPhotoProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		mPhotoMapperCG = sqlSession
				.getMapper(MerchantOutletPhotoMapperCG.class);
		merPhotoMapper = cqSqlSession
				.getMapper(MerchantOutletPhotoMapper.class);
		outletMapperCG = sqlSession.getMapper(OutletMapperCG.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		mOutletMapper  = cqSqlSession.getMapper(MerchantOutletMapper.class);

	}

	@Override
	public void process() {

		List<Transfer> listOutlet = transferMapper
				.queryGroupList(TransferTypeEnum.outlet_id);
		for (Transfer t : listOutlet) {
			outletMap.put(t.getOldId(), t.getNewId());
		}

		List<Transfer> listMerchant = transferMapper
				.queryGroupList(TransferTypeEnum.merchant_id);
		for (Transfer t : listMerchant) {
			merchantMap.put(t.getOldId(), t.getNewId());
			mPhotoMapperCG.deleteMerchantOutletPhotoByMerchantId(t.getNewId());
		}
		
		transferMerchantOutletPhoto();

	}

	public void transferMerchantOutletPhoto() {
		
		
//		List<MerchantOutletPhoto> listPhotoGroup = merPhotoMapper.selectByGroup();
		MerchantOutletPhotoCG mCg = null;
		MerchantOutlet mOutlet = null;
//		for (MerchantOutletPhoto merchantOutletPhoto : listPhotoGroup) {
//			
//			mOutlet = mOutletMapper.selectById(merchantOutletPhoto.getMerchantOutletId());
//			
//			mCg = new MerchantOutletPhotoCG();
//			mCg.setMerchantId(merchantMap.get(merchantOutletPhoto.getMerchantId() + "") == null ? merchantOutletPhoto
//					.getMerchantId().toString() : merchantMap.get(merchantOutletPhoto.getMerchantId() + ""));
//			mCg.setOutletId(outletMap.get(merchantOutletPhoto.getMerchantOutletId() + "") == null ? merchantOutletPhoto
//					.getMerchantOutletId().toString() : outletMap.get(merchantOutletPhoto.getMerchantOutletId() + ""));
//			
//			mCg.setTitle("logo图");
//			mCg.setSource(Constans.getSource(mOutlet.getLogo()));
//			mCg.setLarge(Constans.getLarge(mOutlet.getLogo()));
//			mCg.setMedium(Constans.getMedium(mOutlet.getLogo()));
//			mCg.setThumbnail(Constans.getThumbnail(mOutlet.getLogo()));
//			mCg.setIsDefault(true);
//			addMerchantOutletPhoto(mCg);
//			
//		}
		
		List<MerchantOutletPhoto> listPhoto = merPhotoMapper
				.selectMerchantPhotoAll();

		MerchantOutletPhotoCG mPhotoCG = null;
		for (MerchantOutletPhoto obj : listPhoto) {
			mPhotoCG = new MerchantOutletPhotoCG();
			mPhotoCG.setMerchantId(merchantMap.get(obj.getMerchantId() + "") == null ? obj
					.getMerchantId().toString() : merchantMap.get(obj.getMerchantId() + ""));
			mPhotoCG.setOutletId(outletMap.get(obj.getMerchantOutletId() + "") == null ? obj
					.getMerchantOutletId().toString() : outletMap.get(obj.getMerchantOutletId() + ""));
			
			mPhotoCG.setTitle(obj.getTitle());
			mPhotoCG.setSource(Constans.getSource(obj.getSource()));
			mPhotoCG.setLarge(Constans.getLarge(obj.getLarge()));
			mPhotoCG.setMedium(Constans.getMedium(obj.getMedium()));
			mPhotoCG.setThumbnail(Constans.getThumbnail(obj.getThumbnail()));
			mPhotoCG.setIsDefault(false);
//			if(set.contains(obj.getMerchantOutletId())){
//				mPhotoCG.setIsDefault(false);
//			}else{
//				
//				mPhotoCG.setIsDefault(true);
//			}
//			set.add(obj.getMerchantOutletId());
			
			addMerchantOutletPhoto(mPhotoCG);
		}
		
//		if(listPhoto.size() == 0){
			long id = 0;
//			MerchantOutletPhoto mPhoto = null;
			for (Map.Entry<String, String> entry : outletMap.entrySet()) {
				id = Long.parseLong(entry.getKey());
				mOutlet = mOutletMapper.selectById(id);
//				mPhoto = merPhotoMapper.selectById(id);
//				if(mPhoto == null){
					mPhotoCG = new MerchantOutletPhotoCG();
					mPhotoCG.setMerchantId(merchantMap.get(mOutlet.getMerchantId() + "") == null ? mOutlet
							.getMerchantId().toString() : merchantMap.get(mOutlet.getMerchantId() + ""));
					mPhotoCG.setOutletId(entry.getValue());
					
					mPhotoCG.setTitle("logo图");
					mPhotoCG.setSource(Constans.getSource(mOutlet.getLogo()));
					mPhotoCG.setLarge(Constans.getLarge(mOutlet.getLogo()));
					mPhotoCG.setMedium(Constans.getMedium(mOutlet.getLogo()));
					mPhotoCG.setThumbnail(Constans.getThumbnail(mOutlet.getLogo()));
					mPhotoCG.setIsDefault(true);
					
					addMerchantOutletPhoto(mPhotoCG);
//				}
			}
//		}
	}

	public void addMerchantOutletPhoto(MerchantOutletPhotoCG merchantOutletPhoto) {

		/********************** 操作MySQL数据库 **********************/

		if (mPhotoMapperCG.addMerchantOutletPhoto(merchantOutletPhoto) > -1) {

			if (merchantOutletPhoto.getIsDefault()) { // 如果是默认的图片才缓存
				
				

				outlet = outletMapperCG.findOutletByOutletId(merchantOutletPhoto.getOutletId());
				if (outlet != null) {
					if (StringUtils.isBlank(outlet.getClientId())) {
						LogCvt.error("添加门店详情表的默认图片redis数据失败!客户端ID不能为空");
					}
					boolean flag2 = set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(
							outlet.getClientId(), merchantOutletPhoto);
					if (!flag2) {
						LogCvt.error("添加门店详情表的默认图片redis数据失败!");
					}
				}
			}
		}

	}

	public boolean set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(
			String client_id, MerchantOutletPhotoCG merchantOutletPhoto) {
		boolean flag = false;
		Map<String, String> hash = new HashMap<String, String>();
		hash.put("title",
				ObjectUtils.toString(merchantOutletPhoto.getTitle(), ""));
		hash.put("source",
				ObjectUtils.toString(merchantOutletPhoto.getSource(), ""));
		hash.put("large",
				ObjectUtils.toString(merchantOutletPhoto.getLarge(), ""));
		hash.put("medium",
				ObjectUtils.toString(merchantOutletPhoto.getMedium(), ""));
		hash.put("thumbnail",
				ObjectUtils.toString(merchantOutletPhoto.getThumbnail(), ""));
		flag = set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(
				client_id, merchantOutletPhoto.getMerchantId(),
				merchantOutletPhoto.getOutletId(), hash);
		return flag;
	}

	public boolean set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(
			String client_id, String merchant_id, String oulet_id,
			Map<String, String> hash) {
		String key = RedisKeyUtil
				.cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(
						client_id, merchant_id, oulet_id);
		String result = redis.putMap(key, hash);
		return "OK".equals(result);
	}

}
