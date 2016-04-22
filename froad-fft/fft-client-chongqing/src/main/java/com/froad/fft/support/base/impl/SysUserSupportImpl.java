package com.froad.fft.support.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.SysUserExportService;
import com.froad.fft.common.UserRoleType;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.shiro.Principal;
import com.froad.fft.support.base.MerchantGroupUserSupport;
import com.froad.fft.support.base.SysUserRoleSupport;
import com.froad.fft.support.base.SysUserSupport;

@Service
public class SysUserSupportImpl extends BaseSupportImpl implements
		SysUserSupport {

	@Resource(name="sysUserService")
	private SysUserExportService sysUserService;
	
	@Resource
	private MerchantGroupUserSupport merchantGroupUserSupport;
	
	@Resource
	private SysUserRoleSupport sysUserRoleSupport;
	
	@Override
	public SysUserDto findSysUserById(Long id) {
		return sysUserService.findSysUserById(ClientAccessType.chongqing, ClientVersion.version_1_0, id);
	}

	@Override
	public SysUserDto findSysUserByUsername(String username) {
		return sysUserService.findSysUserByUsername(ClientAccessType.chongqing, ClientVersion.version_1_0, username);
	}

	@Override
	public Boolean updateSysUserById(SysUserDto sysUserDto) {
		return sysUserService.updateSysUserById(ClientAccessType.chongqing, ClientVersion.version_1_0, sysUserDto);
	}

	@Override
	public List<SysResourceDto> findSysResourceListByUserId(Long id) {
		return sysUserService.findSysResourceListByUserId(ClientAccessType.chongqing, ClientVersion.version_1_0, id);
	}

	@Override
	public SysUserDto getCurrentSysUser() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return sysUserService.findSysUserById(ClientAccessType.chongqing, ClientVersion.version_1_0, principal.getId());
			}
		}
		return null;
	}

	@Override
	public String getCurrentUsername() {
		Subject subject = SecurityUtils.getSubject();
		if (subject != null) {
			Principal principal = (Principal) subject.getPrincipal();
			if (principal != null) {
				return principal.getUsername();
			}
		}
		return null;
	}

	
	@Override
	public List<SysUserDto> getOperatorsBySysUser(SysUserDto sysUserDto) {

		List<SysUserRoleDto> userRoles=sysUserRoleSupport.getSysUserRoleByUserId(sysUserDto.getId());
		SysUserRoleDto userRoleDto=userRoles.get(0);
		//获取角色类型
		String RoleType=userRoleDto.getSysRoleDto().getValue();
		List<SysUserDto> listDtos=new ArrayList<SysUserDto>();
		
		if(UserRoleType.ROLE_ADMINISTRATORS.equals(RoleType)){
			//超级管理员			
			MerchantGroupUserDto temp=merchantGroupUserSupport.getBySysUserId(userRoleDto.getUserId());
			//获取商户ID
			MerchantGroupUserDto groupUser=new MerchantGroupUserDto();
			groupUser.setMerchantId(temp.getMerchantId());
			//查询商户对应的所有操作员信息
			List<MerchantGroupUserDto> list=merchantGroupUserSupport.getByConditions(groupUser);			
			for(MerchantGroupUserDto merGroupUserDto:list){
				SysUserDto tmpUserDto=this.findSysUserById(merGroupUserDto.getSysUserId());
				listDtos.add(tmpUserDto);
			}
			return listDtos;
		}else if(UserRoleType.ROLE_OUTLET_ADMIN.equals(RoleType)){
			//门店管理员
			MerchantGroupUserDto temp=merchantGroupUserSupport.getBySysUserId(userRoleDto.getUserId());
			//获取门店ID
			MerchantGroupUserDto groupUser=new MerchantGroupUserDto();
			groupUser.setMerchantOutletId(temp.getMerchantOutletId());
			//查询该门店下对应的所有操作员信息
			List<MerchantGroupUserDto> list=merchantGroupUserSupport.getByConditions(groupUser);			
			for(MerchantGroupUserDto merGroupUserDto:list){
				SysUserDto tmpUserDto=this.findSysUserById(merGroupUserDto.getSysUserId());
				listDtos.add(tmpUserDto);
			}
			return listDtos;
		}else if(UserRoleType.ROLE_OUTLET_OPERATOR.equals(RoleType)){
			//普通操作员
			listDtos.add(sysUserDto);
		}
		return listDtos;
	}

}
