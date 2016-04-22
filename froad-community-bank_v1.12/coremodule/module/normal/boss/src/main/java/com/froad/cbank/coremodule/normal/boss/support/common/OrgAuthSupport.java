package com.froad.cbank.coremodule.normal.boss.support.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.FFTOrgService;
import com.froad.thrift.vo.orgRoleManager.FFTOrgListVoRes;
import com.froad.thrift.vo.orgRoleManager.FFTOrgVo;

@Service
public class OrgAuthSupport {
	
	@Resource
	FFTOrgService.Iface fftOrgService;
	
	public List<ClientRes> getBank(long userId) throws Exception {
		
		List<ClientRes> list = new ArrayList<ClientRes>();
		//调用server后台接口获取数据
		FFTOrgListVoRes res = fftOrgService.getFFTOrgInOneByBank(userId);	
		
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			for(FFTOrgVo vo :res.getVoList()){
				ClientRes r = new ClientRes();
				r.setClientId(vo.getClientId());
				r.setClientName(vo.getOrgName());
				list.add(r);
			}
		}else{
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}
		return list;
	}
	
	public List<HashMap<String,String>> getOrg(long userId,String clientId) throws Exception {
		
		List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
		//调用server后台接口获取数据
		FFTOrgListVoRes res = fftOrgService.getFFTOrgByUserIdPlatform(userId,clientId);	
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			for(FFTOrgVo vo :res.getVoList()){
				HashMap<String,String> r = new HashMap<String,String>();
				r.put("id", vo.getId()+"");
				r.put("parentId", vo.getParentId()+"");
				r.put("orgCode", vo.getCode());
				r.put("orgName", vo.getOrgName());
				list.add(r);
			}
		}else{
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}
		return list;
	}
}
