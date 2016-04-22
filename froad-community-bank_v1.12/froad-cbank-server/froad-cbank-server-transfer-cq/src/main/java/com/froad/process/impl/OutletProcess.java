package com.froad.process.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.froad.cbank.persistent.entity.Merchant;
import com.froad.cbank.persistent.entity.MerchantOutlet;
import com.froad.common.mongo.MerchantDetailMongo;
import com.froad.common.mongo.OutletDetailMongo;
import com.froad.common.redis.OutletRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.MerchantCG;
import com.froad.db.chonggou.entity.MerchantDetail;
import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.OutletMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chongqing.mappers.MerchantMapper;
import com.froad.db.chongqing.mappers.MerchantOutletMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.SimpleID;

public class OutletProcess extends AbstractProcess {

	private OutletMapperCG outletMapperCG = null;
	private MerchantOutletMapper merchantOutletMapper = null;
	private AreaMapperCG areaMapperCG = null;
	private MerchantDetailMongo merchantDetailMongo = null;
	private OutletDetailMongo outletDetailMongo = null;
	private OutletRedis oRedis = null;
	private TransferMapper transferMapper = null;
	private MerchantMapperCG merchantMapperCG = null;
	private MerchantMapper merchantMapper = null;
	
	private final Map<String, String> areaMap = new HashMap<String, String>();
	private final Map<String, String> merchantMap = new HashMap<String, String>();

	public OutletProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void before() {
		merchantDetailMongo = new MerchantDetailMongo(mongo);
		outletDetailMongo = new OutletDetailMongo(mongo);
		oRedis = new OutletRedis(redis);
		
		outletMapperCG = sqlSession.getMapper(OutletMapperCG.class);
		merchantOutletMapper = cqSqlSession
				.getMapper(MerchantOutletMapper.class);
		areaMapperCG = sqlSession.getMapper(AreaMapperCG.class);
		transferMapper = sqlSession.getMapper(TransferMapper.class);
		merchantMapperCG = sqlSession.getMapper(MerchantMapperCG.class);
		merchantMapper = cqSqlSession.getMapper(MerchantMapper.class);
		
		//区域id
		List<Transfer> list = transferMapper.queryGroupList(TransferTypeEnum.area_id);
		for (Transfer t : list) {
			areaMap.put(t.getOldId(), t.getNewId());
		}
		
		//商户id
		List<Transfer> listMerchant = transferMapper.queryGroupList(TransferTypeEnum.merchant_id);
		for (Transfer transfer : listMerchant) {
			merchantMap.put(transfer.getOldId(), transfer.getNewId());
		}
		
		boolean b = outletMapperCG.removeOutlet(Constans.clientId);
		if(b){
			LogCvt.info("重庆迁移数据门店Mysql删除完成!");
		}
		
		boolean flag = outletDetailMongo.deleteOutletDetail(Constans.clientId);
		if(flag){
			LogCvt.info("重庆迁移数据门店Mongo删除完成!");
		}

	}

	@Override
	public void process() {
		transferOutlet();
	}

	public void transferOutlet() {

		OutletCG outletCG = null;
		Transfer transfer = null;
		MerchantCG cg = null;
		Merchant m = null;
		
		List<MerchantOutlet> listOutlet = merchantOutletMapper
				.selectByCondition(new MerchantOutlet());

		for (MerchantOutlet obj : listOutlet) {
			outletCG = new OutletCG();
			transfer = new Transfer();
			
			outletCG.setCreateTime(obj.getCreateTime());
			outletCG.setClientId(Constans.clientId);
			outletCG.setMerchantId(merchantMap.get(obj.getMerchantId().toString()));
			
			outletCG.setOutletId(getOutletID());
			transfer.setOldId(obj.getId().toString());
			transfer.setNewId(outletCG.getOutletId());
			transfer.setType(TransferTypeEnum.outlet_id);
			transferMapper.insert(transfer);
			
			outletCG.setAreaId(Long.valueOf(areaMap.get(obj.getAreaId()+"") == null ? "0" : areaMap.get(obj.getAreaId().toString())));
			outletCG.setOrderValue(obj.getOrderValue());
			outletCG.setOutletName(obj.getName());
			outletCG.setOutletFullname(obj.getFullName());
			
			cg = merchantMapperCG.findMerchantByMerchantId(outletCG.getMerchantId());
			if(cg != null){
				if(cg.getMerchantStatus()){
					outletCG.setOutletStatus(true);
				}else{
					outletCG.setOutletStatus(false);
				}
			}
			outletCG.setAddress(obj.getAddress());
			outletCG.setBusinessHours(obj.getBusinessHours());
			outletCG.setZip(obj.getZip());
			outletCG.setFax(obj.getFax());
			if(obj.getTel().length() > 16){
				outletCG.setPhone(obj.getTel().split("-")[0]);
			}else{
				outletCG.setPhone(obj.getTel());
			}
			outletCG.setContactName(obj.getContactName());
			outletCG.setContactEmail(obj.getContactEmail());
			outletCG.setContactPhone(obj.getContactPhone());
			outletCG.setServiceProvider(obj.getServiceProvider());
			outletCG.setLongitude(obj.getLongitude());
			outletCG.setLatitude(obj.getLatitude());
			if(obj.getIsDelete()){
				outletCG.setIsEnable(false);
				outletCG.setDisableStatus("2");
			}else{
				outletCG.setIsEnable(true);
				outletCG.setDisableStatus("0");
				
				m = merchantMapper.selectById(obj.getMerchantId());
				
				if(m != null && m.getIsDelete()){
					outletCG.setIsEnable(false);
					outletCG.setDisableStatus("2");
				}
			}
			outletCG.setDescription(obj.getOutletDetails());
			outletCG.setPreferDetails(obj.getPreferDetails());


			String [] date = dateFromart(obj.getPreferPeriod());
			if(date != null){
				if(date.length == 2){
					outletCG.setPreferStartPeriod(parse(date[0]));
					outletCG.setPreferEndPeriod(parse(date[1]));
				}else if(date.length == 1)
					outletCG.setPreferStartPeriod(parse(date[0]));
				}
			else{
				outletCG.setPreferStartPeriod(null);
				outletCG.setPreferEndPeriod(null);
			}
			
			outletCG.setDiscount(obj.getDiscount());
			
			
			addOutlet(outletCG, Constans.converImage(obj.getLogo()));
		}
	}
	
	
	private   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public Date parse(String date){
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
		}
		return null;
	}
	
	public   static String[] dateFromart(String str) {
	  	
    	if(str == null || "".equals(str)) 
    		return null;
    	
    	else{
    		String [] arr = null;
    		if(str.indexOf("至") >= 9 && str.indexOf("至") <= 10
    				){
    			arr = str.split("至");
    		}else if( str.indexOf("-") >= 9 && str.indexOf("-") <= 10){
    			arr = str.split("-");
    		}else{
    			arr = str.split(";");
    		}
    		String [] arr2 = new String[arr.length];
    		
	    	for (int i = 0; i < arr.length; i++) {
	    		arr2[i] = arr[i].replaceAll("\\/|\\.", "\\-");
			}
	    	return arr2;
    	}
	}

	private AreaCG area = null;
	private static SimpleID simpleID = new SimpleID(ModuleID.outlet);
	private String getOutletID() {
		return simpleID.nextId();
	}

	public void addOutlet(OutletCG outlet, String defaultImage){


		if (outletMapperCG.addOutlet(outlet) > -1) {

			/********************** 操作Mongodb数据库 **********************/
			if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加mongo表

				MerchantDetail merchantDetail = merchantDetailMongo
						.findMerchantDetailById(outlet.getMerchantId()); // 查询商户详情
				if (null == merchantDetail) {
					LogCvt.error("商户详细信息不存在-outlet.getMerchantId()-->"
							+ outlet.getMerchantId());
				}

				area = areaMapperCG.findAreaById(outlet.getAreaId());
				if (null == area || StringUtils.isBlank(area.getName())) {
					LogCvt.error("区域对象为空不存在！-outlet.getAreaId()--->"
							+ outlet.getAreaId());
				}

				merchantDetailMongo.addOutlet(outlet);// Mongo中商户详情 添加门店信息

				outletDetailMongo.addOutletDetail(outlet, merchantDetail, area,
						defaultImage); // 添加门店详情信息
			}
			/********************** 操作Redis缓存 **********************/
			/* 设置全部缓存 */
			oRedis.set_cbbank_outlet_client_id_merchant_id_outlet_id(outlet);

		}

	}
}
