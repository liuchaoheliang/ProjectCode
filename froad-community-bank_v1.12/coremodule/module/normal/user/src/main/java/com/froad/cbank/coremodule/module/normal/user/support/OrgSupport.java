package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.OrgPojo;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.OrgVo;

/**
 * 机构支持类
 * @ClassName OrgSupport
 */
@Service
public class OrgSupport extends BaseSupport {

	@Resource
	private OrgService.Iface orgService;
	
	@Resource
	private AreaService.Iface areaService;

	
	/**
	 * 获取市级区域下的机构列表
	 * @param clientId
	 * @param areaId 市级地区ID
	 * @return
	 */
	public List<OrgPojo> getCityAreaOrg(String clientId, Long areaId){
		
		List<OrgVo> cOrgList = null;
		
		try {
			//查询市级下所有县区集合
			List<AreaVo> xlist = areaService.findChildrenInfoById(areaId, "");
			List<Long> areaIds = new ArrayList<Long>();
			for(AreaVo xArea : xlist){
				areaIds.add(xArea.getId());//县区集合Ids
			}
			//市级下所有机构（通过所有县的ID查询）
			cOrgList = orgService.getOrgByAreaIdsList(clientId, areaIds);
		} catch (TException e) {
			LogCvt.error("查询机构异常",e);
		}
		
		List<OrgPojo> orgList = new ArrayList<OrgPojo>();
		if(cOrgList != null && cOrgList.size() > 0){
			OrgPojo pojo = null;
			for(OrgVo vo : cOrgList){
				pojo = new OrgPojo();
				BeanUtils.copyProperties(pojo,vo);
				orgList.add(pojo);
			}
		}
		return orgList;
	}
	
	
	
	
	/**
	 * 查询机构
	 * @param clientId
	 * @param orgLevel 机构等级 （重庆模式为1级）
	 * @return
	 */
	public List<OrgPojo> getOrg(String clientId, int orgLevel){
		OrgVo req = new OrgVo();
		req.setClientId(clientId);
		req.setOrgLevel(Integer.toString(orgLevel));
		req.setIsEnable(true);
		
		List<OrgVo> cOrgList = null;
		try {
			cOrgList = orgService.getOrg(req);
		} catch (TException e) {
			LogCvt.error("查询机构异常",e);
		}
		
		List<OrgPojo> orgList = new ArrayList<OrgPojo>();
		if(cOrgList != null && cOrgList.size() > 0){
			OrgPojo pojo = null;
			for(OrgVo vo : cOrgList){
				pojo = new OrgPojo();
				BeanUtils.copyProperties(pojo,vo);
				orgList.add(pojo);
			}
		}
		return orgList;
		
	}
	
	
	
	
	
	
}
