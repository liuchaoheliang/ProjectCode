package com.froad.fft.api.service;

import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.RegisterGivePointRuleDto;
import com.froad.fft.dto.RegisterGivePointRuleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 文章
 *
 * @author FQ
 */
public interface RegisterGivePointRuleExportService
{

    /**
     * 增加
     *
     * @param clientAccessType
     * @param clientVersion
     * @param registerGivePointRuleDto
     * @return
     * @throws FroadException
     */
    public Long addRegisterGivePointRule(ClientAccessType clientAccessType, ClientVersion clientVersion, RegisterGivePointRuleDto registerGivePointRuleDto) throws FroadException;

    /**
     * @param clientAccessType
     * @param clientVersion
     * @param pageDto
     * @return
     * @author larry
     * <p>分页查询</p>
     */
    public PageDto findRegisterGivePointRuleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException;

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键查询地区</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年4月6日 下午5:27:03 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public RegisterGivePointRuleDto findRegisterGivePointRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过客户端获取送分规则</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年4月6日 下午5:27:03 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public RegisterGivePointRuleDto findRegisterGivePointRuleByClientId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id);

    /**
     * 增加
     *
     * @param clientAccessType 应用端类型
     * @param clientVersion    应用端版本号
     * @param dto              保存数据实体
     * @return 数据实体Id
     * @throws com.froad.fft.bean.FroadException
     */
    public Boolean updateRegisterGivePointRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, RegisterGivePointRuleDto dto) throws FroadException;


}
