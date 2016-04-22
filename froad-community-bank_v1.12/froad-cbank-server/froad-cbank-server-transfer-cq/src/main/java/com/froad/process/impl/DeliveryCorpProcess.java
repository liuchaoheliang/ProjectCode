package com.froad.process.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.cbank.persistent.entity.DeliveryCorp;
import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.DeliveryCorpCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.DeliveryCorpMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chonggou.redis.SupportRedis;
import com.froad.db.chongqing.mappers.DeliveryCorpMapper;
import com.froad.enums.ModuleID;
import com.froad.enums.TransferTypeEnum;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;
import com.froad.util.SimpleID;

public class DeliveryCorpProcess extends AbstractProcess{
   private DeliveryCorpMapperCG cgMapper;
	public DeliveryCorpProcess(String name,ProcessServiceConfig config) {
		super(name,config);
		
	}
	@Override
	public void before() {
		LogCvt.info("删除DeliveryCorpProcess开始...........");
		cgMapper=  sqlSession.getMapper(DeliveryCorpMapperCG.class);
		cgMapper.deleteDeliveryCorp(Constans.clientId);
	} 
	@Override
	public void process() {
		boolean result = false;
		//安徽的实体类和Mapper
		DeliveryCorpMapper mapper = cqSqlSession.getMapper(DeliveryCorpMapper.class);
		List<DeliveryCorp> deliveryCorps = new ArrayList<DeliveryCorp>();
		DeliveryCorp deliveryCorp = new DeliveryCorp();
		//重构的实体类和Mapper
		 
		//记录新旧id
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		deliveryCorps = mapper.findDeliveryCorp(deliveryCorp);
		if(deliveryCorps ==null || deliveryCorps.isEmpty()){
			LogCvt.error("未查询到物流公司信息");
			return;
		}
		for(DeliveryCorp a : deliveryCorps){
			DeliveryCorpCG cgDeliveryCorp = new DeliveryCorpCG();
			cgDeliveryCorp.setClientId(Constans.clientId);
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
					LogCvt.info("重庆表cb_deliveryCorp数据"+JSonUtil.toJSonString(a));
					LogCvt.info("迁移后重构表cb_deliveryCorp数据"+JSonUtil.toJSonString(cgDeliveryCorp));
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
			LogCvt.info("cb_deliveryCorp表数据转移失败，失败原因:"+e.getMessage());
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

