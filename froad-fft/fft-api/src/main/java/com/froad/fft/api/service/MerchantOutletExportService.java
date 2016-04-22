package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.enums.trans.TransType;

import java.util.List;
import java.util.Map;

/**
 * 商户门店
 *
 * @author FQ
 */
public interface MerchantOutletExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param merchantOutletDto
     * @return
     * @throws FroadException
     */
    public Long addMerchantOutlet(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantOutletDto merchantOutletDto) throws FroadException;

    /**
     * 方法描述：
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月1日 愚人节快乐
     */
    public Boolean updateMerchantOutletById(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantOutletDto merchantOutletDto) throws FroadException;

    /**
     * 方法描述：
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月1日 愚人节快乐
     */
    public MerchantOutletDto findMerchantOutletById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年4月1日 愚人节快乐
     */
    public PageDto<MerchantOutletDto> findMerchantOutletByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<MerchantOutletDto> pageDto) throws FroadException;

    /**
     * 查询所有未绑定预售提货点的分店数据
     *
     * @return 分店数据列表
     */
    public List<MerchantOutletDto> selectAllUnboundtoPresellDeliveryOutlet() throws FroadException;

    /**
     * 方法描述：通过实体设置的属性进行条件查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年4月6日 下午2:32:16
     */
    public List<MerchantOutletDto> selectByConditions(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantOutletDto merchantOutletDto) throws FroadException;

 }
