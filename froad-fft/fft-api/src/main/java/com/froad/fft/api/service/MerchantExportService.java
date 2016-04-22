package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.Result;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.MerchantDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

import java.util.List;

/**
 * 商户
 *
 * @author FQ
 */
public interface MerchantExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param merchantDto
     * @return
     * @throws FroadException
     */
    public Long addMerchant(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantDto merchantDto) throws FroadException;

    /**
     * 方法描述：
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 下午12:03:45
     */
    public Boolean updateMerchantById(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantDto merchantDto) throws FroadException;

    /**
     * 方法描述：
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 下午12:03:48
     */
    public MerchantDto findMerchantById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 下午12:06:13
     */
    public PageDto<MerchantDto> findMerchantByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<MerchantDto> pageDto) throws FroadException;

    /**
     * 获取所有已绑定分店的商户
     *
     * @param clientAccessType
     * @param clientVersion
     * @return
     */
    public List<MerchantDto> findAllOutletMerchant(ClientAccessType clientAccessType, ClientVersion clientVersion, Long clientId);

    /**
     * 审核操作
     *
     * @param clientAccessType 客户端类型
     * @param clientVersion    客户端版本号
     * @param merchantId       商户Id
     * @param auditFlag        操作类型
     * @param userName         审核操作员
     * @return 操作结果
     */
    public Result auditMerchant(ClientAccessType clientAccessType, ClientVersion clientVersion, Long merchantId, Boolean auditFlag, String userName);
}
