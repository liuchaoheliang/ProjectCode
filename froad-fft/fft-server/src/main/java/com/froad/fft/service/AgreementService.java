package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Agreement;

public interface AgreementService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存Agreement并返回数据主键（如果主键为null则插入失败）</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午1:31:47 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveAgreement(Agreement agreement);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更改数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午2:50:03 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateAgreementById(Agreement agreement);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午3:26:14 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Agreement selectAgreementById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public Page selectAgreementByPage(Page page);

}
