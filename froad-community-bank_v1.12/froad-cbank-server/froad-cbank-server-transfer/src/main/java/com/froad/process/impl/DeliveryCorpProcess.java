package com.froad.process.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.DeliveryCorpMapper;
import com.froad.db.chonggou.entity.DeliveryCorpCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.DeliveryCorpMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chonggou.redis.SupportRedis;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.DeliveryCorp;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;

public class DeliveryCorpProcess extends AbstractProcess{

	public DeliveryCorpProcess(String name,ProcessServiceConfig config) {
		super(name,config);
		// TODO Auto-generated constructor stub
	}
	private final String CLIENT_ID = "anhui";

	@Override
	public void process() {
		// TODO Auto-generated method stub
		boolean result = false;
		//安徽的实体类和Mapper
		DeliveryCorpMapper mapper = ahSqlSession.getMapper(DeliveryCorpMapper.class);
		List<DeliveryCorp> deliveryCorps = new ArrayList<DeliveryCorp>();
		DeliveryCorp deliveryCorp = new DeliveryCorp();
		//重构的实体类和Mapper
		DeliveryCorpMapperCG cgMapper =  sqlSession.getMapper(DeliveryCorpMapperCG.class);
		//记录新旧id
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		deliveryCorps = mapper.findDeliveryCorp(deliveryCorp);
		if(deliveryCorps ==null || deliveryCorps.isEmpty()){
			LogCvt.error("未查询到物流公司信息");
			return;
		}
		for(DeliveryCorp a : deliveryCorps){
			DeliveryCorpCG cgDeliveryCorp = new DeliveryCorpCG();
			cgDeliveryCorp.setId(a.getId());
			cgDeliveryCorp.setClientId(CLIENT_ID);
			cgDeliveryCorp.setName(a.getName());
			cgDeliveryCorp.setUrl(a.getUrl());
			cgDeliveryCorp.setOrderValue(a.getOrderValue());
			cgDeliveryCorp.setIsEnable(true);
			result = addDeliveryCorp(cgMapper,cgDeliveryCorp);
			if(result){
				Transfer transfer = new Transfer();
				transfer.setOldId(String.valueOf(a.getId()));
				transfer.setNewId(String.valueOf(cgDeliveryCorp.getId()));
				transfer.setType(TransferTypeEnum.delivery_corp_id);
				int i = transferMapper.insert(transfer);
				if(i!=-1){
					LogCvt.info("安徽表fft_deliveryCorp数据"+JSonUtil.toJSonString(a));
					LogCvt.info("重构表cb_deliveryCorp数据"+JSonUtil.toJSonString(cgDeliveryCorp));
					LogCvt.info("转移成功");
				}else{
					LogCvt.info("转移失败");
					return;
				}
			}
		}
	}
	
	
	public boolean addDeliveryCorp(DeliveryCorpMapperCG cgMapper,
			DeliveryCorpCG cgDeliveryCorp){
		boolean result = false;
		long addresult = -1;
		try {
			//检查重构数据库中与安徽数据库是否有相同数据
			DeliveryCorpCG adpositioncg = findDeliveryCorpById(cgMapper,cgDeliveryCorp.getId());
			if(adpositioncg != null){
				LogCvt.error("该物流公司信息已存在,ID:"+cgDeliveryCorp.getId());
				return false;
			}
			
			//重构mysql数据库
			addresult = cgMapper.addDeliveryCorp(cgDeliveryCorp);
			if(addresult == 1){
				result = true;
			}
			//redis
			result = SupportRedis.set_deliver_company_client_id_delivery_corp_id(cgDeliveryCorp);
			if(result){
				sqlSession.commit(true); 
			}
		} catch (Exception e) {
			result = false;
			LogCvt.info("fft_deliveryCorp表数据转移失败，失败原因:"+e.getMessage());
			// TODO: handle exception
		}
		
		return result;
	}
	
	
	//根据ID检查重构数据库中与安徽数据库是否有相同数据
	public  DeliveryCorpCG findDeliveryCorpById(DeliveryCorpMapperCG cgMapper,Long id){
		DeliveryCorpCG deliveryCorp = new DeliveryCorpCG();
		deliveryCorp.setId(id);
		List<DeliveryCorpCG> list = cgMapper.findDeliveryCorp(deliveryCorp);
		if(Checker.isNotEmpty(list) && list.size()==1){
			return list.get(0);
		}
		return null;
		
	} 

}

