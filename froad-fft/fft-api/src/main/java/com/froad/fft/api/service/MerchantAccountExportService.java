/**
 * 文件名称:MerchantAccountExportService.java
 * 文件描述: 商户账户exportapi *
 * 产品标识: fft
 * 单元描述: fft-api
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-1
 * 历史修改:  
 */
package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.MerchantAccountDto;
import com.froad.fft.enums.*;

/**
 * 商户账户exportapi *
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface MerchantAccountExportService
{
    /**
     * 增加
     * add by 侯国权
     *
     * @param clientAccessType   客户端类型
     * @param clientVersion      客户端版本号
     * @param merchantAccountDto 商户帐号dto
     * @return 账户Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Long addMerchantAccount(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantAccountDto merchantAccountDto) throws FroadException;

    /**
     * 方法描述：
     * <p/>
     * add by 侯国权
     *
     * @param clientAccessType   客户端类型
     * @param clientVersion      客户端版本号
     * @param merchantAccountDto 商户帐号dto
     * @return 保存结果
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateMerchantAccountById(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantAccountDto merchantAccountDto)throws FroadException;

    /**
     * 方法描述：
     * <p/>
     * add by 侯国权
     *
     * @param clientAccessType 客户端类型
     * @param clientVersion    客户端版本号
     * @param id               商户帐号id
     * @return 账户实体
     * @throws com.froad.fft.bean.FroadException
     */
    public MerchantAccountDto findMerchantAccountById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)throws FroadException;

    /**
     * 方法描述：分页查询
     * <p/>
     * add by 侯国权
     *
     * @param clientAccessType 客户端类型
     * @param clientVersion    客户端版本号
     * @param pageDto          分页查询dto
     * @return 分页结果
     * @throws com.froad.fft.bean.FroadException
     */
    public PageDto<MerchantAccountDto> findMerchantAccountByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<MerchantAccountDto> pageDto)throws FroadException;


}
