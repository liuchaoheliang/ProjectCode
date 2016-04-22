package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;

import com.froad.common.mongo.ShippingMongo;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.entity.TransDto;
import com.froad.db.ahui.mappers.ShippingMapper;
import com.froad.db.ahui.mappers.SysUserMapper;
import com.froad.db.ahui.mappers.TransMapper;
import com.froad.db.chonggou.entity.ShippingCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chonggou.redis.SupportRedis;
import com.froad.enums.ShippingStatus;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.Shipping;
import com.froad.fft.persistent.entity.SysUser;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;

public class ShippingProcess extends AbstractProcess{

	public ShippingProcess(String name,ProcessServiceConfig config) {
		super(name,config);
		// TODO Auto-generated constructor stub
	}
	private final String CLIENT_ID = "anhui";
	
	ShippingMongo shippingMongo = new ShippingMongo();
	
	private TransMapper transMapper = null;

	@Override
	public void process() {
		// TODO Auto-generated method stub
		boolean result = false;
		//安徽的实体类和Mapper
		ShippingMapper mapper = ahSqlSession.getMapper(ShippingMapper.class);
		List<Shipping> shippings = new ArrayList<Shipping>();
		Shipping shipping = new Shipping();
		
		SysUserMapper usermapper = ahSqlSession.getMapper(SysUserMapper.class);
		
		transMapper = ahSqlSession.getMapper(TransMapper.class);
		
		//记录新旧id
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		Map<String, String> orderMap = new HashMap<String, String>();
		Map<String, String> userMap = new HashMap<String, String>();
		
		List<Transfer> orderList = transferMapper.queryGroupList(TransferTypeEnum.order_id);
		if(orderList != null && orderList.size() >0) {
			for(Transfer tf : orderList) {
				orderMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		List<Transfer> userList = transferMapper.queryGroupList(TransferTypeEnum.merchant_user_id);
		if(userList != null && userList.size() >0) {
			for(Transfer tf : userList) {
				userMap.put(tf.getOldId() , tf.getNewId());
			}
		}
		
		shippings = mapper.findShipping(shipping);
		
		if(shippings ==null || shippings.isEmpty()){
			LogCvt.error("未查询到子订单发货信息");
			return;
		}
		
		//商户用户Id
		String merchantUserId = "";
		
		for(Shipping sh : shippings){
			ShippingCG cgShipping = new ShippingCG();
//			long id = sh.getTransId();
//			Transfer transferorder = transferMapper.queryNewId(ObjectUtils.toString(id), TransferTypeEnum.order_id);
			
			String newid =  orderMap.get(String.valueOf(sh.getTransId()));
			String a[] = newid.split(",");
			String order_id = a[0];
			String sub_order_id = a[1];
			
			cgShipping.setId(order_id+"_"+sub_order_id);
			
			SysUser sysUser = usermapper.selectSysUserByName(sh.getOperator());
			if(sysUser==null){
				merchantUserId = "";
				LogCvt.error("该用户不存在，Name:"+sh.getOperator());
			}else{
				merchantUserId = userMap.get(String.valueOf(sysUser.getId()));
			}
			cgShipping.setMerchantUserId(merchantUserId);
			
			TransDto  dto = transMapper.queryById(sh.getTransId());
			if(dto != null){
				
				//物流状态 0：待发货  1:已发货  2:已收货
				String shipState = dto.getShipState();
				String type = dto.getType().getCode();
				
//				unshipped("0","未发货"),
//				shipped("1","已发货"),
//				receipt("2","已收货"),
//				untake("3","未提货"),
//				token("4","已提货");
				if(StringUtils.isNotBlank(type) && "12".equals(type)){
					if("0".equals(shipState)){
						cgShipping.setShippingStatus(ShippingStatus.unshipped.getCode());
					}else if("1".equals(shipState)){
						cgShipping.setShippingStatus(ShippingStatus.shipped.getCode());
					}else if("2".equals(shipState)){
						cgShipping.setShippingStatus(ShippingStatus.receipt.getCode());
					}
				}
				
			}
			
//			cgShipping.setShippingStatus("1");
			cgShipping.setDeliveryCorpId(sh.getDeliveryCorpId().toString());
			cgShipping.setDeliveryCorpName(sh.getDeliveryCorpName());
			cgShipping.setTrackingNo(sh.getTrackingNo());
			cgShipping.setRemark(sh.getRemark());
			if(sh.getShippingTime() !=null){
				cgShipping.setShippingTime(sh.getShippingTime().getTime());
			}else{
				cgShipping.setShippingTime(null);
			}
			if(sh.getReceiptTime() != null){
				cgShipping.setReceiptTime(sh.getReceiptTime().getTime());
			}else{
				cgShipping.setReceiptTime(null);
			}
			
			result = addShipping(cgShipping);
			if(result){
				Transfer transfer = new Transfer();
				transfer.setOldId(String.valueOf(sh.getId()));
				transfer.setNewId(String.valueOf(cgShipping.getId()));
				transfer.setType(TransferTypeEnum.shipping_id);
				int i = transferMapper.insert(transfer);
				if(i!=-1){
//					LogCvt.info("安徽表fft_shipping数据"+JSonUtil.toJSonString(sh));
//					LogCvt.info("重构表cb_shipping数据"+JSonUtil.toJSonString(cgShipping));
					LogCvt.debug("fft_shipping转移成功");
				}
			}else{
				LogCvt.info("转移失败,ID:"+ sh.getId());
				continue;
			}
		}
	}
	
	final Map<String,String> map = new HashMap<String,String>();
	public boolean addShipping(ShippingCG cgShipping){
		boolean result = false;
		long addresult = -1;
		String order_id = "";
		String sub_order_id = "";
		try {
			//检查重构数据库中与安徽数据库是否有相同数据
			String newid = cgShipping.getId();
			String a[] = newid.split("_");
			order_id = a[0];
			sub_order_id = a[1];
			
//			int count = shippingMongo.isExitsShippingInfo(order_id, sub_order_id);
			if(map.containsKey(order_id+sub_order_id)){
				LogCvt.error("该子订单发货信息已存在,ID:"+cgShipping.getId());
				return false;
			}
			
//			if(count > 0){
//				LogCvt.error("该子订单发货信息已存在,ID:"+cgShipping.getId());
//				return false;
//			}
			
			//重构mysql数据库
			addresult =shippingMongo.addShipping(order_id, sub_order_id, cgShipping);
			if(addresult != -1){
				map.put(order_id+sub_order_id,"order_id");
				result = true;
			}
//			if(result){
//				sqlSession.commit(true); 
//			}
		} catch (Exception e) {
			result = false;
			LogCvt.error("fft_shipping表数据转移失败，失败原因:"+e.getMessage(),e);
		}
		
		return result;
	}
	

}

