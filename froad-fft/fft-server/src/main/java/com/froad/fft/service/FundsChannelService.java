package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.FundsChannel;

public interface FundsChannelService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午6:56:19 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public FundsChannel selectFundsChannelById(Long id);

    /**
     * 保存资金渠道
     *
     * @param fundsChannel 资金渠道
     * @return 保存资金渠道
     */
    public Long saveFundsChannel(FundsChannel fundsChannel);

    /**
     * 修改资金渠道
     *
     * @param fundsChannel 资金渠道
     * @return 资金渠道修改操作状态
     */
    public Boolean updateFundsChannelById(FundsChannel fundsChannel);

    /**
     * 资金渠道分页查询
     *
     * @param page 分页查询page
     * @return 分页结果
     */
    public Page findFundsChannelByPage(Page page);


}
