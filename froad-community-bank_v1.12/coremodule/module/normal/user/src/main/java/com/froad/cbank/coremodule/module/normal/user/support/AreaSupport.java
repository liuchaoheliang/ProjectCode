package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.AreaPojo;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.OrgVo;

@Service
public class AreaSupport {
	@Resource
	private AreaService.Iface areaService;
	
	@Resource
	private OrgService.Iface orgService;
	
	/**
	  * 方法描述：地区查询接口(父级ID查询子级)
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:02:07
	  */
	public HashMap<String, Object> getAreaList(Long parentId,String areaCode,String clientId){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		List<AreaVo> list=null;
		AreaPojo areaPojo=null;
		List<AreaPojo> alist=null;
		try {
			LogCvt.info("调用地区查询接口传入参数:[parentId]:"+parentId+",[areaCode]:"+areaCode );
			list=areaService.findChildrenInfo(parentId,areaCode,clientId);
			if(list != null && list.size() !=0 ){
				alist=new ArrayList<AreaPojo>();
				for(AreaVo  temp : list){
					if(temp.getId() != 2430){//屏蔽2340云南省
						areaPojo=new AreaPojo();
						BeanUtils.copyProperties(areaPojo, temp);
						alist.add(areaPojo);
					}
				}
			}
			resMap.put("resResult",alist);
		} catch (TException e) {
			LogCvt.error("地区查询接口出错", e);
		}
		return resMap;
	}
	
	
	
	/**
	  * 方法描述：首页定位查询区域列表专用（clientId + areaCode）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:02:07
	  */
	public List<AreaPojo> getIndexList(String clientId){
		AreaPojo areaPojo=null;
		List<AreaPojo> alist=new ArrayList<AreaPojo>();
		try {
			LogCvt.info("首页定位一级地区查询接口传入参数:[clientId]:"+clientId);
			List<AreaVo> list=areaService.findProvinceAreaByClientId(clientId);
			if(!ArrayUtil.empty(list) ){
				for(AreaVo temp:list){
					areaPojo=new AreaPojo();
					BeanUtils.copyProperties(areaPojo, temp);
					alist.add(areaPojo);
				}
			}
		} catch (TException e) {
			LogCvt.error("首页定位地区查询接口出错", e);
		}
		return alist;
	}
	
	
	/**
	  * 方法描述：查询当前客户端是否支持指定的areaCode区域（clientId + areaCode）
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月3日 上午10:02:07
	  */
	public HashMap<String, Object> isSupport(String clientId,String areaCode){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		boolean flag=false;
		AreaPojo areaPojo=null;
		try {
			LogCvt.info("查询当前客户端是否支持指定的areaCode区域传入参数:[clientId]:"+clientId+",[areaCode]:"+areaCode );
			flag=areaService.isAreaCodeScopeOfClientId(areaCode,clientId);
			if(flag){
				LogCvt.info("当前客户端支持指定的areaCode:["+areaCode+"]，区域,查询该areaCode对应的区域对象");
				AreaVo temp=areaService.findAreaByAreaCodeAndClientId(areaCode,clientId);
				areaPojo=new AreaPojo();
				BeanUtils.copyProperties(areaPojo, temp);
			}
			resMap.put("area",areaPojo);
			resMap.put("resResult",flag);
		} catch (TException e) {
			LogCvt.error("查询当前客户端是否支持指定的areaCode区域接口出错", e);
		}
		return resMap;
	}
	
	
	
	/**
	 * 查询client下所有有下属网点机构的市级行政区域
	 * @param clientId
	 * @return 精简的地区数据列表
	 */
	public List<AreaPojo> getOrgAreaByClient(String clientId){
		List<AreaPojo> alist = new ArrayList<AreaPojo>();
		
		List<OrgVo> cOrgList = null;
		AreaPojo areaPojo = null;
		List<Long> areaIds = null;
		
		try {
			//查询市级区域
			List<AreaVo> plist = areaService.findProvinceAreaByClientId(clientId);
			
			if(plist != null && plist.size() > 0){
				for(AreaVo vo : plist){
					//查询县区区域
					List<AreaVo> clist = areaService.findChildrenInfoById(vo.getId(), "");
					if(clist != null && clist.size() > 0 ){//县区级集合
						areaIds = new ArrayList<Long>();
						for(AreaVo xArea : clist){
							areaIds.add(xArea.getId());//县区集合Ids
						}
						//市级下所有机构（通过所有县的ID查询）
						cOrgList = orgService.getOrgByAreaIdsList(clientId, areaIds);
						
						//有下属的二级机构
						if(cOrgList != null && cOrgList.size() > 0){
							areaPojo=new AreaPojo();
							areaPojo.setId(String.valueOf(vo.getId()));
							areaPojo.setName(vo.getName());
							areaPojo.setAreaCode(vo.getAreaCode());
							alist.add(areaPojo);
						}
					}
				}
			}
			
		} catch (TException e) {
			LogCvt.error("地区查询接口出错", e);
		}
		return alist;
		
	}
	
	
	/**
	 * 
	 * @Description: 根据areaCode获取地区
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月18日 下午3:06:29
	 * @param clientId
	 * @param areaCode
	 * @return
	 */
	public AreaPojo findAreaByAreaCode(String clientId, String areaCode){
		AreaVo areaVo = null;
		AreaPojo areaPojo = null;
		try {
			areaVo=areaService.findAreaByAreaCodeAndClientId(areaCode, clientId);
			if(areaVo != null){
				areaPojo = new AreaPojo();
				areaPojo.setId(String.valueOf(areaVo.getId()));
				areaPojo.setName(areaVo.getName());
				areaPojo.setAreaCode(areaVo.getAreaCode());
				
			}

		} catch (TException e) {
			LogCvt.error("地区查询接口出错", e);
		}
		return areaPojo;
	}
	
	
	
	/**
	 * 
	 * @Description: 根据areaId获取地区
	 * @Author: liaolibiao@f-road.com.cn
	 * @Date: 2015年10月23日 下午3:06:29
	 * @param clientId
	 * @param areaId
	 * @return
	 */
	public AreaPojo findAreaByAreaId(Long areaId){
		AreaVo areaVo = null;
		AreaPojo areaPojo = null;
		try {
			areaVo=areaService.findAreaById(areaId);
			if(areaVo != null){
				areaPojo = new AreaPojo();
				areaPojo.setId(String.valueOf(areaVo.getId()));
				areaPojo.setName(areaVo.getName());
				areaPojo.setAreaCode(areaVo.getAreaCode());
				areaPojo.setClientId(areaVo.getClientId());
			}

		} catch (TException e) {
			LogCvt.error("地区查询接口出错", e);
		}
		return areaPojo;
	}
}
