package com.froad.cbank.coremodule.normal.boss.support.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.system.FinityRoleVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.system.FinityRoleVoRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.FinityRoleService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.finity.FinityRoleListVoRes;
import com.froad.thrift.vo.finity.FinityRoleVo;
/**
 * 角色管理support
 * @author liaopeixin
 *	@date 2016年1月5日 上午9:41:41
 */
@Service
public class FinityRoleSupport {

	@Resource
	private FinityRoleService.Iface finityRoleService;
	
	/**
	 * 角色管理列表请求
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 *	@date 2016年1月5日 上午9:41:59
	 */
	public Map<String,Object> list(String platform,long userId) throws TException, BossException{
		Map<String,Object> map =new HashMap<String,Object>();
		List<FinityRoleVoRes> list=new ArrayList<FinityRoleVoRes>();
		FinityRoleVoRes roleVo=null;
		FinityRoleListVoRes res=finityRoleService.getFinityRoleByUserId(userId, platform);
		ResultVo resultVo=res.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			List<FinityRoleVo> roleList = res.getVoList();
			if(null !=roleList&&roleList.size()>0 ){
				for (FinityRoleVo vo : roleList) {
					roleVo=new FinityRoleVoRes();
					BeanUtils.copyProperties(roleVo, vo);
					list.add(roleVo);
				}
			}
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}		
		map.put("list", list);
		return map;
	}
	/**
	 * 角色详情
	 * @return
	 * @throws TException
	 * @author liaopeixin
	 *	@date 2016年1月5日 下午4:50:39
	 */
	public Map<String,Object> detail(Long roleId) throws TException{
		Map<String,Object> map =new HashMap<String,Object>();
		FinityRoleVoRes roleRes=new FinityRoleVoRes();
		FinityRoleVo role=new FinityRoleVo();
		FinityRoleListVoRes res=finityRoleService.getFinityRoleById(roleId);
		List<FinityRoleVo> roleList=res.getVoList();
		if(null !=roleList&&roleList.size()>0 ){
			role=roleList.get(0);
			BeanUtils.copyProperties(roleRes, role);
		}
		map.put("role", roleRes);
		return map;
	}
	/**
	 * 角色新增
	 * @param req
	 * @param originVo
	 * @param resourceIds
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 *	@date 2016年1月5日 下午4:24:46
	 */
	public Map<String,Object> add(FinityRoleVoReq req,OriginVo originVo,List<Long> resourceIds) throws TException, BossException{
	   Map<String,Object> map =new HashMap<String,Object>();
		FinityRoleVo vo =new FinityRoleVo();
		BeanUtils.copyProperties(vo, req);
		
		CommonAddVoRes res=finityRoleService.addFinityRole(originVo, vo, resourceIds);
		ResultVo resultVo=res.getResult();
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", resultVo.getResultCode());
			map.put("message",resultVo.getResultDesc());
			map.put("id", res.getId());
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}	
		return map;	
	}
	/**
	 * 角色 修改
	 * @param req
	 * @param originVo
	 * @param resourceIds
	 * @return
	 * @throws TException
	 * @throws BossException
	 * @author liaopeixin
	 *	@date 2016年1月5日 下午4:45:54
	 */
	public Map<String,Object> update(FinityRoleVoReq req,OriginVo originVo,List<Long> resourceIds) throws TException, BossException{
		 Map<String,Object> map =new HashMap<String,Object>();
			FinityRoleVo vo =new FinityRoleVo();
			BeanUtils.copyProperties(vo, req);
			ResultVo resultVo=finityRoleService.updateFinityRole(originVo, vo, resourceIds);
			if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
				map.put("code", resultVo.getResultCode());
				map.put("message",resultVo.getResultDesc());
			}else{
				throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
			}	
			return map;	
	}
	
	/**
	 *	角色删除 
	 * @param originVo
	 * @param roleId
	 * @param platform
	 * @return
	 * @author liaopeixin
	 * @throws TException 
	 * @throws BossException 
	 *	@date 2016年1月5日 下午4:46:04
	 */
	public Map<String,Object> delete(OriginVo originVo,Long roleId,String platform) throws TException, BossException{
		Map<String,Object> map =new HashMap<String,Object>();
		
		//调用后台接口
		ResultVo	resultVo=finityRoleService.deleteFinityRole(originVo, platform, roleId);
		if(Constants.RESULT_SUCCESS.equals(resultVo.getResultCode())){
			map.put("code", resultVo.getResultCode());
			map.put("message",resultVo.getResultDesc());
		}else{
			throw new BossException(resultVo.getResultCode(),resultVo.getResultDesc());
		}	
		return map;
	}

}
