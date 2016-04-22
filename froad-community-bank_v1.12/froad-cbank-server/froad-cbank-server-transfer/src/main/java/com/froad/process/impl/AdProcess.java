package com.froad.process.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.AdMapper;
import com.froad.db.chonggou.entity.AdCG;
import com.froad.db.chonggou.entity.Transfer;
import com.froad.db.chonggou.mappers.AdMapperCG;
import com.froad.db.chonggou.mappers.TransferMapper;
import com.froad.enums.TransferTypeEnum;
import com.froad.fft.persistent.entity.Ad;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.JSonUtil;

public class AdProcess extends AbstractProcess{

	public AdProcess(String name,ProcessServiceConfig config) {
		super(name,config);
	}

	private final String CLIENT_ID = "anhui";

	@Override
	public void process() {
		LogCvt.info(name+"Process.........");
		boolean result = false;
		//安徽
		AdMapper admapper = ahSqlSession.getMapper(AdMapper.class);
		List<Ad> ads = new ArrayList<Ad>();
		Ad ad = new Ad();
		//重构
		AdMapperCG cgMapper =  sqlSession.getMapper(AdMapperCG.class);
		//记录新旧id
		TransferMapper transferMapper = sqlSession.getMapper(TransferMapper.class);
		
		ads = admapper.findAd(ad);
		if(ads ==null || ads.isEmpty()){
			LogCvt.error("未查询到广告信息");
			return;
		}
		
		for(Ad a : ads){
			AdCG cgAd = new AdCG();
			cgAd.setId(a.getId());
			cgAd.setClientId(CLIENT_ID);
			cgAd.setTitle(a.getTitle());
			cgAd.setType(Integer.parseInt(a.getType().getCode()));
			cgAd.setBeginTime(a.getBeginTime());
			cgAd.setEndTime(a.getEndTime());
			cgAd.setContent(a.getContent());
			cgAd.setPath(a.getPath());
			cgAd.setLink(a.getLink());
			cgAd.setIsEnabled(a.getIsEnabled());
//			a.getDataState()
			cgAd.setIsBlankTarge(a.getIsBlankTarge());
			cgAd.setClickCount(a.getClickCount());
			Transfer transfer2 = transferMapper.queryNewId(a.getAdPositionId().toString(), TransferTypeEnum.ad_position_id);
			cgAd.setAdPositionId(Long.parseLong(transfer2.getNewId()));
			
			result = addAd(cgMapper, cgAd);
			if(result){
				Transfer transfer = new Transfer();
				transfer.setOldId(String.valueOf(a.getId()));
				transfer.setNewId(String.valueOf(cgAd.getId()));
				transfer.setType(TransferTypeEnum.ad_id);
				int i = transferMapper.insert(transfer);
				if(i!=-1){
					LogCvt.info("安徽表fft_ad数据"+JSonUtil.toJSonString(ad));
					LogCvt.info("重构表cb_ad数据"+JSonUtil.toJSonString(cgAd));
					LogCvt.info("转移成功");
				}
			}else{
				LogCvt.info("转移失败");
				return;
			}
		}
		
		
	}

	
	public boolean addAd(AdMapperCG cgMapper,
			AdCG cgAd){
		boolean result = false;
		long addresult = -1;
		
		try {
			AdCG adcg = findAdById(cgMapper,cgAd.getId());
			if(adcg!=null){
				LogCvt.error("该广告信息已存在,ID:"+cgAd.getId());
				return false;
			}
			//重构mysql数据库
			addresult = cgMapper.addAd(cgAd);
			if(addresult == 1){
				result = true;
			}
			if(result){
				sqlSession.commit(true); 
			}
		} catch (Exception e) {
			result = false;
			LogCvt.info("fft_ad表数据转移失败，失败原因:"+e.getMessage());
			// TODO: handle exception
		}
		return result;
	}
	
	public AdCG findAdById(AdMapperCG cgMapper,Long id){
		AdCG adcg = new AdCG();
		adcg.setId(id);
		List<AdCG> list = cgMapper.findAd(adcg);
		if(Checker.isNotEmpty(list) && list.size()==1){
			return list.get(0);
		}
		return null;
	}
	
}

