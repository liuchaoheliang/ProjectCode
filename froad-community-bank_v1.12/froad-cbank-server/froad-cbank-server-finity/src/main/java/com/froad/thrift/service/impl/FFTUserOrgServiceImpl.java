package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.List;
import org.apache.thrift.TException;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadServerException;
import com.froad.logback.LogCvt;
import com.froad.logic.FFTUserOrgLogic;
import com.froad.logic.impl.FFTUserOrgLogicImpl;
import com.froad.po.Result;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.orgRoleManager.FFTUserOrgListVoRes;
import com.froad.util.BeanUtil;
import com.froad.thrift.service.FFTUserOrgService;


/**
 * 
 * <p>@Title: FFTUserRoleServiceImpl.java</p>
 * <p>Description: 描述 </p> 用户组织Service实现类
 * @author f-road-ll 
 * @version 1.0
 * @created 2016年1月5日
 */
public class FFTUserOrgServiceImpl extends BizMonitorBaseService implements FFTUserOrgService.Iface {
	private FFTUserOrgLogic fftUserOrgLogic = new FFTUserOrgLogicImpl();
	public FFTUserOrgServiceImpl(String name, String version)
	{
		super(name, version);
	}


	/**
     * 用户组织查询
     * @param userId 用户Id
     * @return FFTUserOrgListVoRes
     * 
     **/
	@Override
	public FFTUserOrgListVoRes getFFTUserOrg(long userId) throws TException {
		FFTUserOrgListVoRes voRes = new FFTUserOrgListVoRes();
		Result result = new Result();
		List<String> orgIds = null;
		try{
			if(userId <= 0){
				throw new FroadServerException("用户组织查询getFFTUserOrg:userId数据有误！");
			}
			
			orgIds = fftUserOrgLogic.findFFTUserOrgByUserId(userId);					
			
			
		}catch (FroadServerException e) {
			LogCvt.error("用户组织查询getFFTUserOrg失败，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		} catch (Exception e) {
			LogCvt.error("用户组织查询getFFTUserOrg异常，原因:" + e.getMessage(), e);
			result.setResultCode(ResultCode.finity_error.getCode());
			result.setResultDesc(ResultCode.finity_error.getMsg());
		}
		
		voRes.setResultVo((ResultVo) BeanUtil.copyProperties(ResultVo.class, result));
		voRes.setOrgIds(orgIds==null?new ArrayList<String>():orgIds);
		return voRes;
	}

	
}

