package com.froad.action.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import com.froad.action.support.AdvertActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.advert.Advert;
import com.froad.util.Command;
import com.froad.util.JsonUtil;

/** 
 * @author FQ 
 * @date 2013-4-18 下午02:17:01
 * @version 1.0
 * 广告
 */
public class AdvertAction extends BaseActionSupport {
	
	private AdvertActionSupport advertActionSupport;//广告
	private Advert advert;

	//ajax 广告数据
	public void ajaxAdvertList(){
		
		List list=new ArrayList();
		
		advert.setState(Command.FBU_USERED_STATE);
		
		log.info("广告查询条件："+JSONObject.fromObject(advert));
		List<Advert> advertList=advertActionSupport.getAdvertById(advert);
		if(advertList!=null && !advertList.isEmpty()){
			for(Advert advert:advertList){
				Map<String,Object> advertMap=new HashMap<String,Object>();
				advertMap.put("id", advert.getId());
				advertMap.put("name", advert.getName());
				advertMap.put("images", advert.getImages()!=null ? Command.IMAGE_SERVER_URL+advert.getImages():"");
				advertMap.put("link", advert.getLink()!=null ? advert.getLink():"");
				advertMap.put("is_blank", advert.getIsBlankTarge()!=null ? advert.getIsBlankTarge():"1");
				
				list.add(advertMap);
			}
		}
		
		String jsonStr=JsonUtil.getJsonString4List(list);
		//log.info("广告查询数据："+jsonStr);
		
		ajaxJson(jsonStr);
	}
	

	public AdvertActionSupport getAdvertActionSupport() {
		return advertActionSupport;
	}

	public void setAdvertActionSupport(AdvertActionSupport advertActionSupport) {
		this.advertActionSupport = advertActionSupport;
	}
	
	public Advert getAdvert() {
		return advert;
	}


	public void setAdvert(Advert advert) {
		this.advert = advert;
	}
}
