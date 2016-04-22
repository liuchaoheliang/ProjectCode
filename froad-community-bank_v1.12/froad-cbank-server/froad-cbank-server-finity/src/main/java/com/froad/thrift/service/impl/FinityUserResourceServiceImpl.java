/**   
 * @author liuhuangle@f-road.com.cn  
 * @date 2015年10月16日 下午7:34:48 
 */
package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.logic.UserResourceLogic;
import com.froad.logic.impl.UserResourceLogicImpl;
import com.froad.po.UserResource;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.FinityUserResourceService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.finity.UserResourceVo;
import com.froad.util.BeanUtil;

public class FinityUserResourceServiceImpl extends BizMonitorBaseService  implements FinityUserResourceService.Iface{

	public UserResourceLogic userResourceLogic=new UserResourceLogicImpl();
	
	public FinityUserResourceServiceImpl(String name, String version) {
		super(name, version);
	}
	
	@Override
	public ResultVo addUserResourceByBatch(List<UserResourceVo> userResourceVoList) throws TException {
		
		List<UserResource> userResourceList=new ArrayList<UserResource>();
		if(userResourceVoList==null){
			return new ResultVo("9999","新增商户用户资源失败");
		}
		
		for(UserResourceVo vo:userResourceVoList){
			UserResource ur=(UserResource) BeanUtil.copyProperties(UserResource.class, vo); 
			userResourceList.add(ur);
		}
		
		Boolean r=userResourceLogic.addUserResourceByBatch(userResourceList);
		
 		if(r){
 			return new ResultVo("0000","新增商户用户资源成功");
 		}else{
 			return new ResultVo("9999","新增商户用户资源失败");
 		}
	}

}
