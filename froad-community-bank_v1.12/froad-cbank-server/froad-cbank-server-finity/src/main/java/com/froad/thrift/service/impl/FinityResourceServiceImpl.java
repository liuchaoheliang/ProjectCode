package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;

import com.froad.enums.MonitorPointEnum;
import com.froad.enums.Platform;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.BossResourceLogic;
import com.froad.logic.ResourceLogic;
import com.froad.logic.impl.BossResourceLogicImpl;
import com.froad.logic.impl.ResourceLogicImpl;
import com.froad.monitor.MonitorService;
import com.froad.monitor.impl.MonitorManager;
import com.froad.po.Resource;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.FinityResourceService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.finity.FinityResourceVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.LogUtils;

public class FinityResourceServiceImpl extends BizMonitorBaseService implements FinityResourceService.Iface{

	private ResourceLogic resourceLogic = new ResourceLogicImpl();
	private BossResourceLogic bossResourceLogic = new BossResourceLogicImpl();
	 
	public FinityResourceServiceImpl(String name, String version) {
		super(name, version);
	}
   
	/**
	 * 监控服务
	 * */
	private MonitorService monitorService = new MonitorManager();
	
	
    /**银行/商户管理平台老接口
     * 查询 FinityResource
     * @param finityResourceVo
     * @return List<FinityResourceVo>
     */
	@Override
	public List<FinityResourceVo> getFinityResource(FinityResourceVo finityResourceVo) throws TException {
		List<Resource> resourceList  = new ArrayList<Resource>();
		try{
			Resource resource=(Resource)BeanUtil.copyProperties(Resource.class, finityResourceVo);
			resourceList = resourceLogic.findResource(resource);
		}catch (FroadServerException e) {
			LogCvt.error("获取资源信息getFinityResource失败，原因:" + e.getMessage(), e);
			// - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Resource_Select_Failure);
		} catch (Exception e) {
			LogCvt.error("获取资源信息getFinityResource异常，原因:" + e.getMessage(), e);
			// - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Resource_Select_Failure);
		}
		return resourceCommon(resourceList);
	}
	
	
	/**Boss平台新接口
     * 查询 FinityResource
     * @param finityResourceVo
     * @return List<FinityResourceVo>
     */
	@Override
	public List<FinityResourceVo> getBossFinityResource(FinityResourceVo finityResourceVo) throws TException {
		List<Resource> resourceList  = new ArrayList<Resource>();
		try{
			Resource resource=(Resource)BeanUtil.copyProperties(Resource.class, finityResourceVo);
			resourceList = bossResourceLogic.findResource(resource);
		}catch (FroadServerException e) {
			LogCvt.error("获取资源信息getBossFinityResource失败，原因:" + e.getMessage(), e);
			// - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Resource_Select_Failure);
		} catch (Exception e) {
			LogCvt.error("获取资源信息getBossFinityResource异常，原因:" + e.getMessage(), e);
			// - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Resource_Select_Failure);
		}
		return resourceCommon(resourceList);
	}

	 /**
     * 查询 FinityResource --根据角色查询
     * @param finityResourceVo
     * @return List<FinityResourceVo>
     */
	@Override
	public List<FinityResourceVo> getFinityResourceByRole(FinityResourceVo finityResourceVo, List<Long> roles)
			throws TException {
		List<FinityResourceVo> resourceVoList = new ArrayList<FinityResourceVo>();
		try{
			Resource resource=(Resource)BeanUtil.copyProperties(Resource.class, finityResourceVo);
			List<Resource> resourceList = new ArrayList<Resource>();
			if(!Checker.isEmpty(resource.getPlatform()) && (resource.getPlatform().equals(Platform.boss.getType()) 
					|| resource.getPlatform().equals(Platform.merchant.getType())
					|| resource.getPlatform().equals(Platform.bank.getType()))){
				if(roles!=null && roles.size()==1){//单个角色查询
					resourceList = bossResourceLogic.findResourceByRole(resource, roles.get(0));
				}else if(roles!=null && roles.size()>1){//多个角色查询
					resourceList = bossResourceLogic.findResourceByRoles(resource, roles);
				}else{
					return new ArrayList<FinityResourceVo>();
				} 
			}else{
				if(roles!=null && roles.size()==1){//单个角色查询
					resourceList = resourceLogic.findResourceByRole(resource, roles.get(0));
				}else if(roles!=null && roles.size()>1){//多个角色查询
					resourceList = resourceLogic.findResourceByRoles(resource, roles);
				}else{
					return new ArrayList<FinityResourceVo>();
				} 
			}
			resourceVoList = resourceCommon(resourceList);
			
		}catch (FroadServerException e) {
			LogCvt.error("根据角色获取对应资源信息getFinityResourceByRole失败，原因:" + e.getMessage(), e);
			//用户登录获取资源 - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Resource_Login_Failure);
		} catch (Exception e) {
			LogCvt.error("根据角色获取对应资源信息getFinityResourceByRole异常，原因:" + e.getMessage(), e);
			//用户登录获取资源 - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Resource_Login_Failure);
		}
		return resourceVoList;
	}
	
	/**
	 * 资源树整理
	 * @param ResourceList
	 * @return
	 */
	public  List<FinityResourceVo> resourceCommon(List<Resource> resourceList){
		List<FinityResourceVo> resourceVoList=(List<FinityResourceVo>)BeanUtil.copyProperties(FinityResourceVo.class, resourceList);
		//List<FinityResourceVo> rootResources = new ArrayList<FinityResourceVo>();
       
		/*for (FinityResourceVo tree : resourceVoList) {
            if(tree.getParentResourceId() == 100000000){
                rootResources.add(tree);
            }
            for (FinityResourceVo t : resourceVoList) {
                if(t.getParentResourceId() == tree.getId()){
                    if(tree.getFinityResourceList() == null){
                        List<FinityResourceVo> myChildrens = new ArrayList<FinityResourceVo>();
                        myChildrens.add(t);
                        tree.setFinityResourceList(myChildrens);
                    }else{
                        tree.getFinityResourceList().add(t);
                    }
                }
            }
        }   */
		return resourceVoList==null?new ArrayList<FinityResourceVo>():resourceVoList;
	}

	/**  
	 * 根据用户查询资源信息 
	 * @description 
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015年10月15日 下午7:08:26  
	 * @param finityResourceVo
	 * @param userId
	 * @return
	 * @throws TException  
	 * @throws  
	 */
	@Override
	public List<FinityResourceVo> getFinityResourceByUser(FinityResourceVo finityResourceVo, long userId,int userType)
			throws TException {
		List<Resource> resourceList  = new ArrayList<Resource>();
		try{
			Resource resource=(Resource)BeanUtil.copyProperties(Resource.class, finityResourceVo);
			if(!Checker.isEmpty(resource.getPlatform()) && (resource.getPlatform().equals(Platform.boss.getType()) 
//					|| resource.getPlatform().equals(Platform.merchant.getType())
//					|| resource.getPlatform().equals(Platform.bank.getType())
					)){
				resourceList = bossResourceLogic.findResourceByUser(resource, userId); 
			}else{
				resourceList = resourceLogic.findResourceByUser(resource, userId,userType); 
				
			}
		}catch (FroadServerException e) {
			LogCvt.error("根据用户获取对应资源信息getFinityResourceByUser失败，原因:" + e.getMessage(), e);
			//用户登录获取资源 - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Resource_Login_Failure);
		} catch (Exception e) {
			LogCvt.error("根据用户获取对应资源信息getFinityResourceByUser异常，原因:" + e.getMessage(), e);
			//用户登录获取资源 - 发送监控
			monitorService.send(MonitorPointEnum.Finity_Resource_Login_Failure);
		}
		return resourceCommon(resourceList);
	}

	
	
	
	@Override
	public CommonAddVoRes addFinityResource(OriginVo originVo,
			FinityResourceVo finityResourceVo) throws TException {
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加资源");
			LogUtils.addLog(originVo);
			Long id = 0l;
			if(Checker.isEmpty(finityResourceVo)){
				throw new FroadServerException("添加FinityResource失败,原因:添加对象不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.getResourceName())){
				throw new FroadServerException("添加FinityResource失败,原因:资源名称不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.getType())){
				throw new FroadServerException("添加FinityResource失败,原因:资源类型不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.getParentResourceId())){
				throw new FroadServerException("添加FinityResource失败,原因:父级资源ID不能为空!");
			}
//			if(Checker.isEmpty(finityResourceVo.getResourceIcon())){
//				throw new FroadServerException("添加FinityResource失败,原因:资源图标不能为空!");
//			}
			if(Checker.isEmpty(finityResourceVo.getTreePath())){
				throw new FroadServerException("添加FinityResource失败,原因:treePath不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.isIsDelete())){
				throw new FroadServerException("添加FinityResource失败,原因:删除标志不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.getOrderValue())){
				throw new FroadServerException("添加FinityResource失败,原因:排序值不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.isIsSystem())){
				throw new FroadServerException("添加FinityResource失败,原因:系统资源标志不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.isIsMenu())){
				throw new FroadServerException("添加FinityResource失败,原因:菜单标志不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.getPlatform())){
				throw new FroadServerException("添加FinityResource失败,原因:平台名称不能为空!");
			}
			if(Checker.isEmpty(finityResourceVo.getResourceKey())){
				throw new FroadServerException("添加FinityResource失败,原因:资源Key不能为空!");
			}
//			if(Checker.isEmpty(finityResourceVo)){
//				throw new FroadServerException("添加FinityResource失败,原因:添加对象不能为空!");
//			}
			if(Checker.isEmpty(finityResourceVo.isIsLimit())){
				throw new FroadServerException("添加FinityResource失败,原因:数据权限标志不能为空!");
			}
			//vo 转 po 
			Resource resource=(Resource)BeanUtil.copyProperties(Resource.class, finityResourceVo);
			id = bossResourceLogic.addResource(resource);
			if(id != null && id > 0){
				voRes.setId(id);
			}else if(id == -2){
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("资源编号已存在，请重新输入");
			}else{
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加资源失败");
			}
			voRes.setId(id);
		}catch (FroadServerException e) {
			LogCvt.error("添加资源信息addFinityResource失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加资源信息addFinityResource异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}

		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
	}

	@Override
	public ResultVo deleteFinityResource(OriginVo originVo, String platform,
			long resourceId) throws TException {
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除资源");
			LogUtils.addLog(originVo);
			
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(resourceId)){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			//参数不全
			if(Checker.isEmpty(platform)){
				result = new Result(ResultCode.notAllParameters);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			Resource resource = new Resource();
			resource.setPlatform(platform);
			resource.setId(resourceId);
			if (!bossResourceLogic.deleteResource(resource)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除资源DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除资源deleteResource失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除资源deleteResource异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

	@Override
	public ResultVo updateFinityResource(OriginVo originVo,
			FinityResourceVo finityResourceVo) throws TException {
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改资源信息");
			LogUtils.addLog(originVo);
			
			//验证update和delete时id不能为空
			if(Checker.isEmpty(finityResourceVo.getId())){
				result = new Result(ResultCode.no_id_error);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			// vo 转 po 
			Resource resource=(Resource)BeanUtil.copyProperties(Resource.class, finityResourceVo);
						
			if (!bossResourceLogic.updateResource(resource)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改资源DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改资源updateResource失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改资源updateResource异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}


	@Override
	public ResultVo moveMenu(OriginVo originVo,List<FinityResourceVo> FinityResourceVoList)
			throws TException {
		Result result = new Result();
		List<Resource> resourceList = null;
		try{
			//参数不全
			if(Checker.isEmpty(FinityResourceVoList)){
				result = new Result(ResultCode.notAllParameters);
				return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
			}
			
			resourceList = (List<Resource>)BeanUtil.copyProperties(Resource.class, FinityResourceVoList);
			if (!bossResourceLogic.moveMenu(resourceList)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("调整资源DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("调整资源moveMenu失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("调整资源moveMenu异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}

}
