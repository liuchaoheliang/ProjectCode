package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.ProductRestrictRuleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * @author 侯国权
 *         限购规则
 */
public interface ProductRestrictRuleExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param dto
     * @return
     * @throws FroadException
     */
    public Long addProductRestrictRule(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductRestrictRuleDto dto) throws FroadException;

    /**
     * @param clientAccessType
     * @param clientVersion
     * @param pageDto
     * @return
     * @author larry
     * <p>分页查询</p>
     */
    public PageDto findProductRestrictRuleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException;

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键查询限购规则</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年4月6日 下午5:27:03 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public ProductRestrictRuleDto findProductRestrictRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id);

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateProductRestrictRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductRestrictRuleDto dto) throws FroadException;


}
