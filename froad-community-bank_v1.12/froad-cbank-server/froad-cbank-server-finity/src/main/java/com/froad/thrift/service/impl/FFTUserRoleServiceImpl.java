package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.FFTUserRoleLogic;
import com.froad.logic.impl.FFTUserRoleLogicImpl;
import com.froad.po.FFTUserRole;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.CommonAddVoRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.orgRoleManager.FFTUserRoleIdListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTUserRoleListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTUserRoleVo;
import com.froad.util.BeanUtil;
import com.froad.util.LogUtils;
import com.froad.thrift.service.FFTUserRoleService;


/**
 * 
 * <p>@Title: FFTUserRoleServiceImpl.java</p>
 * <p>Description: 描述 </p> 用户角色Service实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public class FFTUserRoleServiceImpl extends BizMonitorBaseService implements FFTUserRoleService.Iface {
	private FFTUserRoleLogic fftUserRoleLogic = new FFTUserRoleLogicImpl();
	public FFTUserRoleServiceImpl(String name, String version)
	{
		super(name, version);
	}


	/**
     * 新增 用户角色
     * @param originVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param userId 用户Id
     * @param orgIds 组织配置
     * @param roleIds 角色分配
     * @return CommonAddVoRes
     */
	@Override
	public CommonAddVoRes addFFTUserRole(OriginVo originVo,long userId, List<String> orgIds, List<Long> roleIds) throws TException {
		
		CommonAddVoRes voRes = new CommonAddVoRes();
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("添加用户角色、用户组织信息");
			LogUtils.addLog(originVo);
						
			if(userId<=0){
				throw new FroadServerException("添加FFTUserRole失败,原因:userId数据有误!");
			}
//			if(orgIds.size()<=0){
//				throw new FroadServerException("添加FFTUserRole失败,原因:orgIds集合有误!");
//			}
//			if(roleIds.size()<=0){
//				throw new FroadServerException("添加FFTUserRole失败,原因:roleIds集合有误!");
//			}
			

			Long id = fftUserRoleLogic.addFFTUserRole(userId,orgIds,roleIds);
			if(id == null || id<0){
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("添加组织信息异常");
			}
			
			voRes.setId(id);
			
		}catch (FroadServerException e) {
			LogCvt.error("添加用户角色addFFTUserRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}catch (Exception e) {
			LogCvt.error("添加用户角色addFFTUserRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		
		voRes.setResult((ResultVo)BeanUtil.copyProperties(ResultVo.class,result));
		return voRes;
		
	}


	/**
     * 修改用户角色
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param userId 用户Id
     * @param orgIds 组织配置
     * @param roleIds 角色分配
     * @return ResultVo
     */
	@Override
	public ResultVo updateFFTUserRole(OriginVo originVo,long userId, List<String> orgIds, List<Long> roleIds) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("修改用户角色信息");
			LogUtils.addLog(originVo);
			
			if(userId<=0){
				throw new FroadServerException("修改FFTUserRole失败,原因:userId数据有误!");
			}
//			if(orgIds.size()<=0){
//				throw new FroadServerException("修改FFTUserRole失败,原因:orgIds集合有误!");
//			}
//			if(roleIds.size()<=0){
//				throw new FroadServerException("修改FFTUserRole失败,原因:roleIds集合有误!");
//			}
			
			if (!fftUserRoleLogic.updateFFTUserRole(userId,orgIds,roleIds)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("修改用户角色DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("修改用户角色关系updateFFTUserRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("修改用户角色关系updateFFTUserRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	

	/**
     * 删除  用户角色
     * @param OriginVo 源对象信息(包含平台,操作ip,操作员id等...)
     * @param userId 用户编号
     * @return ResultVo
     */
	@Override
	public ResultVo deleteFFTUserRole(OriginVo originVo, long userId) throws TException {
		
		Result result = new Result();
		try{
			//添加操作日志记录
			originVo.setDescription("删除用户角色关系");
			LogUtils.addLog(originVo);
			
			if(userId<=0){
				throw new FroadServerException("删除deleteFFTUserRole失败,原因:条件userId数据异常!");
			}
			
			if (!fftUserRoleLogic.deleteFFTUserRole(userId)) {
				result.setResultCode(ResultCode.failed.getCode());
				result.setResultDesc("删除用户角色关系DB操作异常");
			}
		}catch (FroadServerException e) {
			LogCvt.error("删除用户角色关系deleteFFTUserRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("删除用户角色关系deleteFFTUserRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		return (ResultVo) BeanUtil.copyProperties(ResultVo.class, result);
	}
	
	
	/**
     * 用户角色查询
     * @param userId 用户Id
     * @return FFTUserRoleListVoRes
     * 
     **/
	@Override
	public FFTUserRoleListVoRes getFFTUserRole(long userId) throws TException {
		FFTUserRoleListVoRes voRes = new FFTUserRoleListVoRes();
		Result result = new Result();
		List<FFTUserRoleVo> fftUserRoleVoList = null;
		try{
			
			List<FFTUserRole> fftUserRoleList = fftUserRoleLogic.findFFTUserRoleByUserId(userId);					
			fftUserRoleVoList =(List<FFTUserRoleVo>)BeanUtil.copyProperties(FFTUserRoleVo.class, fftUserRoleList);
			
			
		}catch (FroadServerException e) {
			LogCvt.error("用户角色查询getFFTUserRole失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("用户角色查询getFFTUserRole异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setVoList(fftUserRoleVoList==null?new ArrayList<FFTUserRoleVo>():fftUserRoleVoList);
		return voRes;
	}

	
	/**
	 * 用户角色id查询
	 * @param userId 用户Id
	 * @return FFTUserRoleIdListVoRes
	 * 
	 */
	@Override
	public FFTUserRoleIdListVoRes getFFTUserRoleId(long userId) throws TException {
		FFTUserRoleIdListVoRes voRes = new FFTUserRoleIdListVoRes();
		Result result = new Result();
		List<Long> fftUserRoleIdVoList = null;
		try{
			
			List<FFTUserRole> fftUserRoleList = fftUserRoleLogic.findFFTUserRoleByUserId(userId);	
			Set<Long> set = new HashSet<Long>();
			for(FFTUserRole f : fftUserRoleList){
				set.add(f.getRoleId());
			}
			fftUserRoleIdVoList = new ArrayList<Long>(set);
			
		}catch (FroadServerException e) {
			LogCvt.error("用户角色查询getFFTUserRoleId失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("用户角色查询getFFTUserRoleId异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setRoleIdList(fftUserRoleIdVoList==null?new ArrayList<Long>():fftUserRoleIdVoList);
		return voRes;
	}
	
	
}

