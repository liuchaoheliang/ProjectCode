package com.froad.process.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.froad.common.mongo.OutletDetailMongo;
import com.froad.common.redis.MerchantOutletPhotoRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.MerchantOutletPhoto;
import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.MerchantOutletPhotoMapper;
import com.froad.db.chonggou.mappers.OutletMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;

public class MerchantOutletPhotoProcess extends AbstractProcess{
	
	private MerchantOutletPhotoMapper merchantOutletPhotoMapper = null;
	private OutletMapperCG outletMapper = null;
	private OutletCG outlet = null;

	public MerchantOutletPhotoProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}
	
	
	final Set<Long> set = new HashSet<Long>();
	@Override
	public void process() {
		
		merchantOutletPhotoMapper = sqlSession.getMapper(MerchantOutletPhotoMapper.class);
		outletMapper = sqlSession.getMapper(OutletMapperCG.class);
		
		com.froad.db.ahui.mappers.MerchantPhotoMapper mapper = ahSqlSession.getMapper(com.froad.db.ahui.mappers.MerchantPhotoMapper.class);
		TransferMapper tmapper = sqlSession.getMapper(TransferMapper.class);
		OutletMapperCG outletMapper = sqlSession.getMapper(OutletMapperCG.class);
		List<com.froad.fft.persistent.entity.MerchantPhoto> list = mapper.selectMerchantPhotoAll();
		
		MerchantOutletPhoto mpo = null;
		Transfer transfer = null;
		OutletCG outlet = null;
		
		for (com.froad.fft.persistent.entity.MerchantPhoto obj : list) {
			mpo = new MerchantOutletPhoto();
			mpo.setTitle(obj.getTitle());
			
			mpo.setSource(Constans.getSource(obj.getUrl()));
			mpo.setLarge(Constans.getLarge(obj.getUrl()));
			mpo.setMedium(Constans.getMedium(obj.getUrl()));
			mpo.setThumbnail(Constans.getThumbnail(obj.getUrl()));
			transfer = tmapper.queryNewId(obj.getMerchantOutletId().toString(), TransferTypeEnum.outlet_id);
			
//			mapImage.put(obj.getMerchantOutletId().toString()+obj.getOrderValue().toString(), obj.getUrl());
			
	
			
			if(transfer != null){
				mpo.setOutletId(transfer.getNewId());
				outlet = outletMapper.findOutletByOutletId(mpo.getOutletId());
				if(outlet != null)
					mpo.setMerchantId(outlet.getMerchantId());
				else{
					LogCvt.info("根据旧mpo.getOutletId()查询新outlet对象为null,merchantId填入merchantoutletId->"+obj.getMerchantOutletId());
					mpo.setMerchantId("0");
				}
			}
			else{
				LogCvt.info("根据旧MerchantOutletId查询新id对象为null,outletId/merchantId填入merchantoutletId->"+obj.getMerchantOutletId());
				mpo.setOutletId("0");
				mpo.setMerchantId("0");
			}
			
//			if(mapImage.containsKey(obj.getMerchantOutletId().toString()+obj.getOrderValue().toString())){
//				if(obj.getOrderValue() == 0){
//					mpo.setIsDefault(true);
//				}else{
//					mpo.setIsDefault(false);
//				}
//			}
			if(set.contains(obj.getMerchantOutletId())){
				mpo.setIsDefault(false);
			}else{
				mpo.setIsDefault(true);
			}
			
			set.add(obj.getMerchantOutletId());
			
			
			
			addMerchantOutletPhoto(mpo);
		}
	}
	

    /**
     * 增加 MerchantOutletPhoto
     * @param merchantOutletPhoto
     * @return Long    主键ID
     */
	public void addMerchantOutletPhoto(MerchantOutletPhoto merchantOutletPhoto) {

		Long result = 0l; 
		
		try { 
			/**********************操作MySQL数据库**********************/
			
			if (merchantOutletPhotoMapper.addMerchantOutletPhoto(merchantOutletPhoto) > -1) { 
//				ConsoleLogger.info("添加门店详情表的默认图片转换成功---new Id-->"+merchantOutletPhoto.getId());
				result = merchantOutletPhoto.getId(); 
			}

			/**********************操作Mongodb数据库**********************/
			if(result > 0){ 
				if(merchantOutletPhoto.getIsDefault()) { // 如果是默认的图片才缓存
					OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
					if(StringUtils.isBlank(merchantOutletPhoto.getOutletId())
							|| StringUtils.isBlank(merchantOutletPhoto.getMedium())){
						LogCvt.error("添加门店详情表的默认图片mongodb数据失败!outletId/medium不能为空!");
					}
//					boolean flag = outletDetailMongo.updateOutletDetailDefaultImage(merchantOutletPhoto.getOutletId(), merchantOutletPhoto.getThumbnail()); // 默认图片存小图
//					if(!flag){
//						LogCvt.error("添加门店详情表的默认图片mongodb数据失败!");
//					}
					
					
					/**********************操作Redis缓存**********************/
					
					outlet = outletMapper.findOutletByOutletId(merchantOutletPhoto.getOutletId());
					if(outlet != null ){
						if(StringUtils.isBlank(outlet.getClientId())){
							LogCvt.error("添加门店详情表的默认图片redis数据失败!客户端ID不能为空");
						}
						boolean flag2 = MerchantOutletPhotoRedis.set_cbbank_merchant_outlet_image_client_id_merchant_id_outlet_id(outlet.getClientId(), merchantOutletPhoto);
						if(!flag2){
							LogCvt.error("添加门店详情表的默认图片redis数据失败!");
						}
					}
				}
			} 
		} catch (Exception e) { 
			LogCvt.error("添加MerchantOutletPhoto失败，原因:" + e.getMessage(), e); 
		} 

	}

}

