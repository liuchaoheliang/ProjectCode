package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.AgreementDto;
import com.froad.fft.enums.AgreementType;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 协议
 *
 * @author FQ
 */
public interface AgreementExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param agreementDto
     * @return
     * @throws FroadException
     */
    public Long addAgreement(ClientAccessType clientAccessType, ClientVersion clientVersion, AgreementDto agreementDto) throws FroadException;

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateAgreementById(ClientAccessType clientAccessType, ClientVersion clientVersion, AgreementDto dto) throws FroadException;

    /**
     * 根据ID 查找广告位
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param id               数据实体Id
     * @return 对饮该数据Id的实体
     */
    public AgreementDto findAgreementById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException;

    /**
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param pageDto          数据实体
     * @return 分页结果
     * @author houguoquan
     * <p>分页查询</p>
     */
    public PageDto<AgreementDto> findAgreementByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<AgreementDto> pageDto) throws FroadException;

    /**
    *<p>获取客户端指定类型的协议</p>
    * @author larry
    * @datetime 2014-4-11下午05:53:52
    * @return AgreementDto
    * @throws 
    * @example<br/>
    *
     */
	public AgreementDto findAgreementByClient(
			ClientAccessType clientAccessType, ClientVersion version10,
			AgreementType agreementType);


}
