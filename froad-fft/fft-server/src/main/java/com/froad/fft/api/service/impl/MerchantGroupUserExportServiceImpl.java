/**
 * 文件名称:MerchantGroupUserExportServiceImpl.java
 * 文件描述: 商圈用户组接口实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.fft.api.service.MerchantGroupUserExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.enums.*;
import com.froad.fft.persistent.entity.MerchantGroupUser;
import com.froad.fft.service.MerchantGroupUserService;

import javax.annotation.Resource;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class MerchantGroupUserExportServiceImpl implements MerchantGroupUserExportService
{
    @Resource(name = "merchantGroupUserServiceImpl")
    private MerchantGroupUserService merchantGroupUserService;
    public Long saveMerchantGroupUser(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantGroupUserDto dto) throws FroadException
    {
        MerchantGroupUser temp = DtoToBeanSupport.loadMerchantGroupUser(dto);
        return merchantGroupUserService.saveMerchantGroupUser(temp);
    }

    public Boolean updateMerchantGroupUserById(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantGroupUserDto dto) throws FroadException
    {
        MerchantGroupUser temp = DtoToBeanSupport.loadMerchantGroupUser(dto);
        return merchantGroupUserService.updateMerchantGroupUserById(temp);
    }

    public MerchantGroupUserDto findMerchantGroupUserById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        MerchantGroupUser temp = merchantGroupUserService.selectMerchantGroupUserById(id);
        MerchantGroupUserDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadMerchantGroupUserDto(temp);
        }
        return result;
    }

	@Override
	public List<MerchantGroupUserDto> findAllMerchantGroupUser()throws FroadException {
		List<MerchantGroupUserDto> merchantGroupUserDtos = new ArrayList<MerchantGroupUserDto>();
		List<MerchantGroupUser> merchantGroupUsers = merchantGroupUserService.findAllMerchantGroupUser();
		for (MerchantGroupUser merchantGroupUser : merchantGroupUsers) {
			merchantGroupUserDtos.add(BeanToDtoSupport.loadMerchantGroupUserDto(merchantGroupUser));
		}
		return merchantGroupUserDtos;
	}

	@Override
	public MerchantGroupUserDto findMerchantGroupUserByUserId(
			ClientAccessType management, ClientVersion version10, Long userId)throws FroadException {
		MerchantGroupUser merchantGroupUser = merchantGroupUserService.findMerchantGroupUserByUserId(userId);
		return BeanToDtoSupport.loadByMerchantGroupUserDto(merchantGroupUser);
	}

	
	@Override
	public List<MerchantGroupUserDto> selectByConditions(
			ClientAccessType management, ClientVersion version10,
			MerchantGroupUserDto merchantGroupUserDto) throws FroadException {
		List<MerchantGroupUserDto> merchantGroupUserDtos = new ArrayList<MerchantGroupUserDto>();
		List<MerchantGroupUser> merchantGroupUsers = merchantGroupUserService.selectByConditions(DtoToBeanSupport.loadMerchantGroupUser(merchantGroupUserDto));
		for (MerchantGroupUser merchantGroupUser : merchantGroupUsers) {
			merchantGroupUserDtos.add(BeanToDtoSupport.loadMerchantGroupUserDto(merchantGroupUser));
		}
		return merchantGroupUserDtos;
	}


}
