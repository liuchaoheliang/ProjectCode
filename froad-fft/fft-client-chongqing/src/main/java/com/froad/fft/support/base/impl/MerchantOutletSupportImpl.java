/**
 * 文件名：MerchantOutletSupportImpl.java
 * 版本信息：Version 1.0
 * 日期：2014年4月6日
 * author: 刘超 liuchao@f-road.com.cn
 */
package com.froad.fft.support.base.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.froad.fft.api.service.TransStatisticExportService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.MerchantOutletExportService;
import com.froad.fft.common.UserRoleType;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.support.base.MerchantGroupUserSupport;
import com.froad.fft.support.base.MerchantOutletSupport;
import com.froad.fft.support.base.SysUserRoleSupport;

/**
 * 类描述：
 *
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月6日 下午1:54:26
 */
@Service
public class MerchantOutletSupportImpl implements MerchantOutletSupport
{

    private static Logger logger = Logger.getLogger(MerchantOutletSupportImpl.class);

    @Resource(name = "merchantOutletService")
    private MerchantOutletExportService merchantOutletExportService;

    @Resource(name = "transStatisticExportService")
    private TransStatisticExportService transStatisticExportService;

    @Resource
    private SysUserRoleSupport sysUserRoleSupport;

    @Resource
    private MerchantGroupUserSupport merchantGroupUserSupport;

    private final ClientAccessType clientAccessType = ClientAccessType.chongqing;

    @Override
    public MerchantOutletDto getById(Long id)
    {
        if (id == null)
        {
            logger.info("传入查询条件为空");
            return null;
        }
        return merchantOutletExportService.findMerchantOutletById(clientAccessType, ClientVersion.version_1_0, id);
    }

    @Override
    public List<MerchantOutletDto> getByConditions(MerchantOutletDto merchantOutletDto)
    {
        return merchantOutletExportService.selectByConditions(clientAccessType, ClientVersion.version_1_0, merchantOutletDto);
    }

    @Override
    public List<MerchantOutletDto> getOutletBySysUser(SysUserDto userDto)
    {
        List<MerchantOutletDto> list = new ArrayList<MerchantOutletDto>();
        List<SysUserRoleDto> userRoles = sysUserRoleSupport.getSysUserRoleByUserId(userDto.getId());
        SysUserRoleDto userRoleDto = userRoles.get(0);
        String RoleType = userRoleDto.getSysRoleDto().getValue();
        //获取角色类型
        MerchantGroupUserDto groupUserDto = merchantGroupUserSupport.getBySysUserId(userDto.getId());
        MerchantOutletDto merchantOutletDto = new MerchantOutletDto();

        if (UserRoleType.ROLE_ADMINISTRATORS.equals(RoleType))
        {
            //超级管理员
            merchantOutletDto.setMerchantId(groupUserDto.getMerchantId());
            list = this.getByConditions(merchantOutletDto);
        }
        else if (UserRoleType.ROLE_OUTLET_ADMIN.equals(RoleType))
        {
            //门店管理员
            merchantOutletDto.setId(groupUserDto.getMerchantOutletId());
            list = this.getByConditions(merchantOutletDto);
        }
        else if (UserRoleType.ROLE_OUTLET_OPERATOR.equals(RoleType))
        {
            //普通操作员
            return null;
        }
        return list;
    }

    @Override
    public List<Map<String, String>> getSaleRank(Long merchantId, TransType transType)
    {
        if (merchantId == null || transType == null)
        {
            logger.info("传入查询参数有误");
            return null;
        }
        return transStatisticExportService.selectOutletStatistic(clientAccessType, ClientVersion.version_1_0, merchantId, transType);
    }

}
