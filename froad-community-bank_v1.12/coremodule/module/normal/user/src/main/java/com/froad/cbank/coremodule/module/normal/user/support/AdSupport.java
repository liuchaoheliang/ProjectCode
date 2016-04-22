package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.AdPojo;
import com.froad.cbank.coremodule.module.normal.user.utils.Constants;
import com.froad.thrift.service.AdService;
import com.froad.thrift.service.AdvertisingService;
import com.froad.thrift.vo.AdvertisingVo;
import com.froad.thrift.vo.FindAllAdvertisingParamVo;
import com.froad.thrift.vo.FindAllAdvertisingResultVo;


	/**
	 * 类描述：广告接口支持
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年4月1日 下午4:02:30 
	 */
@Service
public class AdSupport extends BaseSupport {
	
	@Resource
	private AdService.Iface adService;
	
	@Resource
	private AdvertisingService.Iface advertisingService;
	
	
	
	
	/**
	  * 方法描述：根据页面标识获取客户端对应广告位上的广告列表
	  * @param: clientId - 客户端编号     ，positionPage - 页面标识
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月1日 下午4:04:32
	  */
	public HashMap<String, Object>  getAd(String clientId, String positionPage, String terminalType, String paramOne, String paramTwo, String paramThree){
		LogCvt.info(String.format("广告列表查询>> clientId：%s, positionPage: %s, terminalType:%s", clientId,positionPage,terminalType));
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		
		FindAllAdvertisingParamVo paramVo = new FindAllAdvertisingParamVo();
		paramVo.setClientId(clientId);
		paramVo.setPositionPage(positionPage);
		paramVo.setTerminalType(terminalType);
		paramVo.setParamOneValue(paramOne);
		paramVo.setParamTwoValue(paramTwo);
		paramVo.setParamThreeValue(paramThree);
		try {
//			LogCvt.info("调用接口传入参数:"+JSON.toJSONString(adPositionVo)  );
			AdPojo adPojo =null ;
			List<AdPojo> adList = new ArrayList<AdPojo>();
			FindAllAdvertisingResultVo resultVo = advertisingService.pageOptFindAdvertisings(paramVo);
			if(Constants.RESULT_CODE_SUCCESS.equals(resultVo.getResultVo().getResultCode())){
				for(AdvertisingVo adVo : resultVo.getAdvertisingVoList()){
								adPojo=new AdPojo();
					BeanUtils.copyProperties(adPojo, adVo);
								adList.add(adPojo);
							}
					}
			
			resMap.put("adList", adList);
			
			
		} catch (TException e) {
			LogCvt.error("广告列表查询出错", e);
		}
		return resMap;
	}
	
	
	
}
