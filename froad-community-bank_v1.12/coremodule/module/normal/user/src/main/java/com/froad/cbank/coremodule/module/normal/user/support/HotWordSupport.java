/**
 * Project Name:coremodule-user
 * File Name:HotWordSupport.java
 * Package Name:com.froad.cbank.coremodule.module.normal.user.support
 * Date:2015年9月18日下午1:10:51
 * Copyright (c) 2015, F-Road, Inc.
 *
*/

package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.HotWordPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.ProductCategoryPojo;
import com.froad.thrift.service.HotWordService;
import com.froad.thrift.vo.HotWordPageRes;
import com.froad.thrift.vo.HotWordVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ProductCategoryVo;

/**
 * ClassName:HotWordSupport
 * Reason:	 热词搜素支持类
 * Date:     2015年9月18日 下午1:10:51
 * @author   刘超 liuchao@f-road.com.cn
 * @version  
 * @see 	 
 */


@Service
public class HotWordSupport extends BaseSupport{
	
	@Resource
	private HotWordService.Iface hotWordService;
	
	
	/**
	 * searchHotWord:()
	 *	热词搜索接口  categoryType 类型0 全部1 商品2商户   ， areaId 区域ID  ，  clientId 客户端标识
	 * @author 刘超 liuchao@f-road.com.cn
	 * 2015年9月18日 下午1:17:24
	 * @return
	 * 
	 */
	public Map<String, Object> searchHotWord(String clientId,Long areaId,Integer categoryType ,PagePojo pagePojo) {
		HashMap<String, Object> resMap = new HashMap<String, Object>();
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		//设置查询条件
		HotWordVo hotWordVo = new HotWordVo();
		hotWordVo.setAreaId(areaId);
		hotWordVo.setClientId(clientId);
		hotWordVo.setCategoryType(categoryType);
		
		try {
			LogCvt.info("热词查询传入参数：areaId-"+areaId+",clientId-"+clientId+",categoryType-"+categoryType);
			HotWordPageRes res=hotWordService.searchHotWord(pageVo, hotWordVo);			
			//转换分页实体
			PagePojo page=new PagePojo();
			BeanUtils.copyProperties(page, res.getPage());			
			
			//转换分类数据实体
			HotWordPojo hotWordPojo=null;
			List<HotWordPojo> clist =new ArrayList<HotWordPojo>();
			
			if(res.getHotList() != null  &&  res.getHotList().size() !=0  ){
				for(HotWordVo temp:res.getHotList()) {
					hotWordPojo=new HotWordPojo();
					BeanUtils.copyProperties(hotWordPojo, temp);
					clist.add(hotWordPojo);
				}
			}
			resMap.put("page", page );
			resMap.put("hotWordList", clist );
		} catch (TException e) {
			System.out.println(e.getStackTrace());
			LogCvt.info("热词查询接口异常：", e);
		}
		return resMap;
	}
	
}
