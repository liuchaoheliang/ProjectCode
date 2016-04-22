package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.MerchantCategoryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 商户分类
 *
 * @author FQ
 */
public interface MerchantCategoryExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param merchantCategoryDto
     * @return
     * @throws FroadException
     */
    public Long addMerchantCategory(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantCategoryDto merchantCategoryDto) throws FroadException;

    /**
     * @param clientAccessType 客户端类型
     * @param clientVersion    客户端版本号
     * @param pageDto          分页实体
     * @return 分页数据
     * @author Aides
     * <p>分页查询</p>
     */
    public PageDto<MerchantCategoryDto> findMerchantCategoryByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<MerchantCategoryDto> pageDto)throws FroadException;

}
