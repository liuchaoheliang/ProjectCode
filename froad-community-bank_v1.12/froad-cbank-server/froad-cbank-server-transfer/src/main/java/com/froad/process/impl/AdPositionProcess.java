package com.froad.process.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.AdPositionMapper;
import com.froad.db.chonggou.entity.AdPositionCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AdPositionMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.db.chonggou.redis.SupportRedis;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.AdPosition;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;

public class AdPositionProcess extends AbstractProcess{

	public AdPositionProcess(String name,ProcessServiceConfig config) {
		super(name,config);
		// TODO Auto-generated constructor stub
	}
	private final String CLIENT_ID = "anhui";

	@Override
	public void process() {
		// TODO Auto-generated method stub
		boolean result = false;
		//安徽的实体类和Mapper
		AdPositionMapper mapper = ahSqlSession.getMapper(AdPositionMapper.class);
		List<AdPosition> adPositions = new ArrayList<AdPosition>();
		AdPosition adPosition = new AdPosition();
		//重构的实体类和Mapper
		AdPositionMapperCG cgMapper =  sqlSession.getMapper(AdPositionMapperCG.class);
		//记录新旧id
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		adPositions = mapper.findAdPosition(adPosition);
		if(adPositions ==null || adPositions.isEmpty()){
			LogCvt.error("未查询到广告位信息");
			return;
		}
		for(AdPosition adp : adPositions){
			AdPositionCG cgAdPosition = new AdPositionCG();
			cgAdPosition.setId(adp.getId());
			cgAdPosition.setName(adp.getName());
			cgAdPosition.setWidth(adp.getWidth());
			cgAdPosition.setHeight(adp.getHeight());
			cgAdPosition.setDescription(adp.getDescription());
			cgAdPosition.setPositionStyle("1");
			cgAdPosition.setPositionPoint(1);
			cgAdPosition.setPositionPage("1");
			if(adp.getDataState().getCode().equals("30")){
				cgAdPosition.setIsEnable(true);
			}else{
				cgAdPosition.setIsEnable(false);
			}
			cgAdPosition.setClientId(CLIENT_ID);
			result = addAdPosition(cgMapper,cgAdPosition);
			if(result){
				Transfer transfer = new Transfer();
				transfer.setOldId(String.valueOf(adp.getId()));
				transfer.setNewId(String.valueOf(cgAdPosition.getId()));
				transfer.setType(TransferTypeEnum.ad_position_id);
				int i = transferMapper.insert(transfer);
				if(i!=-1){
					LogCvt.info("安徽表fft_ad_position数据"+JSonUtil.toJSonString(adp));
					LogCvt.info("重构表cb_ad_position数据"+JSonUtil.toJSonString(cgAdPosition));
					LogCvt.info("转移成功");
				}
			}else{
				LogCvt.info("转移失败");
				return;
			}
		}
	}
	
	
	public boolean addAdPosition(AdPositionMapperCG cgMapper,
			AdPositionCG cgAdPosition){
		boolean result = false;
		long addresult = -1;
		try {
			//检查重构数据库中与安徽数据库是否有相同数据
			AdPositionCG adpositioncg = findAdPositionById(cgMapper,cgAdPosition.getId());
			if(adpositioncg != null){
				LogCvt.error("该广告位信息已存在,ID:"+cgAdPosition.getId());
				return false;
			}
			
			//重构mysql数据库
			addresult = cgMapper.addAdPosition(cgAdPosition);
			if(addresult == 1){
				result = true;
			}
			//redis
			result = SupportRedis.set_cbbank_adpos_client_id_ad_position_id(cgAdPosition);
			if(result){
				sqlSession.commit(true); 
			}
		} catch (Exception e) {
			result = false;
			LogCvt.info("fft_ad_position表数据转移失败，失败原因:"+e.getMessage());
			// TODO: handle exception
		}
		
		return result;
	}
	
	
	//根据ID检查重构数据库中与安徽数据库是否有相同数据
	public  AdPositionCG findAdPositionById(AdPositionMapperCG cgMapper,Long id){
		AdPositionCG adpositon = new AdPositionCG();
		adpositon.setId(id);
		List<AdPositionCG> list = cgMapper.findAdPosition(adpositon);
		if(Checker.isNotEmpty(list) && list.size()==1){
			return list.get(0);
		}
		return null;
		
	} 

}

