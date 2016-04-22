package com.froad.process.impl;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.froad.common.mongo.MerchantDetailMongo;
import com.froad.common.mongo.OutletDetailMongo;
import com.froad.common.redis.OutletPositionModelRedis;
import com.froad.common.redis.OutletRedis;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.MerchantOutletMapper;
import com.froad.db.ahui.mappers.OutletBankMapper;
import com.froad.db.chonggou.entity.AreaCG;
import com.froad.db.chonggou.entity.MerchantDetail;
import com.froad.db.chonggou.entity.OutletCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AreaMapperCG;
import com.froad.db.chonggou.mappers.MerchantMapperCG;
import com.froad.db.chonggou.mappers.OutletMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.entity.OutleBank;
import com.froad.logback.LogCvt;
import com.froad.po.Merchant;
import com.froad.process.AbstractProcess;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;
import com.froad.util.SimpleID;

public class OutletProcess extends AbstractProcess{

	private AreaMapperCG areaMapper = null;
	private MerchantMapperCG  merchantMapperCG = null;
	public OutletProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	@Override
	public void process() {
		 outletMapper  = sqlSession.getMapper(OutletMapperCG.class);
		 areaMapper = sqlSession.getMapper(AreaMapperCG.class);
		 merchantMapperCG = sqlSession.getMapper(MerchantMapperCG.class);
		
		TransferMapper tmapper = sqlSession.getMapper(TransferMapper.class);
		MerchantOutletMapper mapper = ahSqlSession.getMapper(MerchantOutletMapper.class);
		OutletBankMapper mapper2 = ahSqlSession.getMapper(OutletBankMapper.class);
		List<MerchantOutlet> list = mapper.selectByProductComment();
		
		
		OutletCG outlet = null;
		Transfer transfer = null;
		Transfer transfer2 = null;
		Merchant merchant = null;
//		OutleBank b = null;
		for (MerchantOutlet obj : list) {
			outlet = new OutletCG();
			outlet.setCreateTime(obj.getCreateTime());
			outlet.setClientId(Constans.clientId);
			outlet.setDisableStatus("0");
			outlet.setOutletId(getOutletID());
			transfer = new Transfer();
			transfer.setOldId(obj.getId().toString());
			transfer.setNewId(outlet.getOutletId());
			transfer.setType(TransferTypeEnum.outlet_id);
			tmapper.insert(transfer);
			
			
			LogCvt.info("new outlet_id->"+outlet.getOutletId() + " old outlet_id-->"+obj.getId());
//			ConsoleLogger.info("new outlet_id->"+outlet.getOutletId() + " old outlet_id-->"+obj.getId());
			
			transfer2 = tmapper.queryNewId(obj.getMerchantId().toString(), TransferTypeEnum.merchant_id);
			if(transfer2 != null)
				outlet.setMerchantId(transfer2.getNewId());
			else{
//				LogCvt.info("根据旧merchantid查询新id对象为null,填入原始值");
				outlet.setMerchantId(obj.getMerchantId().toString());
			}
			outlet.setOutletName(obj.getName());
			outlet.setOutletFullname(obj.getFullName());
			
			int n = mapper2.findByOutletId(obj.getMerchantId());
			if(n > 0){
				outlet.setOutletStatus(true);
			}else{
				outlet.setOutletStatus(false);
			}
			
//			if(obj.getAddress() != null && !"".equals(obj.getAddress()) && obj.getAddress().length() >= 64)
//				outlet.setAddress(obj.getAddress().substring(0, 64));
//			else
//				outlet.setAddress(obj.getAddress());
			outlet.setAddress(obj.getAddress());
			outlet.setBusinessHours(obj.getBusinessHours());
			outlet.setZip(obj.getZip());
			outlet.setFax(obj.getFax());
			outlet.setPhone(obj.getTel());
			outlet.setContactName(obj.getContactName());
			outlet.setContactPhone(obj.getContactPhone());
			outlet.setContactEmail(obj.getContactEmail());
			outlet.setServiceProvider(obj.getServiceProvider());
			
			if("30".equals(obj.getDataState().getCode().trim())){
				outlet.setIsEnable(true);
			}else {
				outlet.setIsEnable(false);
			}
			
			merchant = merchantMapperCG.findMerchantByMerchantId(outlet.getMerchantId());
//			LogCvt.info("merchant===>" + merchant);
//			LogCvt.info("outlet.getMerchantId()===>" + outlet.getMerchantId());
//			LogCvt.info("merchant.getDisableStatus()===>" + merchant.getDisableStatus());
//			LogCvt.info("merchant.getIsEnable()===>" + merchant.getIsEnable());
			if(merchant != null){
				if(!merchant.getIsEnable() 
						&& "2".equals(merchant.getDisableStatus())){
					outlet.setIsEnable(false);
					outlet.setDisableStatus("2");
				}
			}
			
			
			if(isDouble(obj.getLongitude())){
				outlet.setLongitude(obj.getLongitude());
			}else{
				outlet.setLongitude("0");
			}
			
			if(isDouble(obj.getLatitude())){
				outlet.setLatitude(obj.getLatitude());
			}else{
				outlet.setLatitude("0");
			}
			
			outlet.setDescription(Constans.converText(obj.getOutletDetails()));
			outlet.setPreferDetails(Constans.converText(obj.getPreferDetails()));
			
//			if(null != obj.getPreferPeriod() && !"".equals(obj.getPreferPeriod())){
//				outlet.setPreferStartPeriod(null);
//				outlet.setPreferEndPeriod(null);
//			}
			String [] date = dateFromart(obj.getPreferPeriod());
			if(date != null){
				if(date.length == 2){
					outlet.setPreferStartPeriod(parse(date[0]));
					outlet.setPreferEndPeriod(parse(date[1]));
				}else if(date.length == 1)
					outlet.setPreferStartPeriod(parse(date[0]));
				}
			else{
				outlet.setPreferStartPeriod(null);
				outlet.setPreferEndPeriod(null);
			}
			
//			if(null !=obj.getDiscount()){
//				if(obj.getDiscount().length() >= 32)
//					outlet.setDiscount(obj.getDiscount().trim().substring(0, 31));
//				else
//					outlet.setDiscount(obj.getDiscount());
//			}
			outlet.setDiscount(obj.getDiscount());
//			outlet.setAreaId(obj.getAreaId());
			
			transfer  = tmapper.queryNewId(String.valueOf(obj.getAreaId()), TransferTypeEnum.area_id);
			if(transfer != null)
				outlet.setAreaId(Long.parseLong(transfer.getNewId()));
			else{
				outlet.setAreaId(0l);
			}
			
			
			try {
				addOutlet(outlet,Constans.converImage(obj.getLogo()));
				
			} catch (Exception e) {
				LogCvt.error("添加Outlet失败，原因:" + e.getMessage() + " 失败 new Id-->"+outlet.getOutletId() + " old outletId->"+obj.getId(), e);
			}
		}
		
	}
	private   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	public Date parse(String date){
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
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
	
	  
    boolean isDouble(String str)
    {
    	if(null == str || "".equals(str)) return false;
       try
       {
          Double.parseDouble(str);
          return true;
       }
       catch(NumberFormatException ex){}
       return false;
    }
	
    private OutletMapperCG outletMapper  = null;
	private static SimpleID simpleID = new SimpleID(ModuleID.outlet);
	private String getOutletID() {
		return simpleID.nextId();
	}

	private AreaCG area =  null;
	
    /**
     * 增加 Outlet
     * @param outlet
     * @return outletId 门店编号
     */
	public void addOutlet(OutletCG outlet,String defaultImage)  throws Exception{

		
		try { 
			/**********************操作MySQL数据库**********************/
			
			if (outletMapper.addOutlet(outlet) > -1) { 
//				LogCvt.info("MySQL添加outlet成功---> new outletId->"+outlet.getOutletId());

				/**********************操作Mongodb数据库**********************/
				if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加mongo表
//					LogCvt.info("普通门店(非银行机构对应的门店)cb_merchant_detail表中添加门店id");
					
					MerchantDetailMongo merchantDetailMongo = new MerchantDetailMongo();
					MerchantDetail merchantDetail = merchantDetailMongo.findMerchantDetailById(outlet.getMerchantId()); // 查询商户详情
					if(null == merchantDetail) {
						LogCvt.error("商户详细信息不存在-outlet.getMerchantId()-->" + outlet.getMerchantId());
					}
					
					area = areaMapper.findAreaById(outlet.getAreaId());
					if(null == area || StringUtils.isBlank(area.getName())) {
						LogCvt.error("区域对象为空不存在！-outlet.getAreaId()--->" + outlet.getAreaId());
//						return null;
					}
					
					
					merchantDetailMongo.addOutlet(outlet);// Mongo中商户详情 添加门店信息
	
					
	
//					LogCvt.info("普通门店(非银行机构对应的门店)添加cb_outlet_detail表");
					OutletDetailMongo outletDetailMongo = new OutletDetailMongo();
					outletDetailMongo.addOutletDetail(outlet, merchantDetail,area,defaultImage); // 添加门店详情信息
				}
				/**********************操作Redis缓存**********************/
				/* 设置排序缓存 */
				/* 设置全部缓存 */
//				LogCvt.info("设置门店全部缓存");
				OutletRedis.set_cbbank_outlet_client_id_merchant_id_outlet_id(outlet);
				
//				if (!outlet.getOutletStatus()) { // 不为银行默认的商户的时候才添加地理位置缓存
//					/* 设置门店地理位置缓存 */
////					LogCvt.info("普通门店(非银行机构对应的门店)设置地理位置缓存");
//					OutletPositionModelRedis.setCache(outlet);
//				}
			} 
		} catch (Exception e) { 
			LogCvt.error("添加Outlet失败，原因:" + e.getMessage() + " 失败 new Id-->"+outlet.getOutletId(), e);
//			e.printStackTrace();
		} 

	}

}

