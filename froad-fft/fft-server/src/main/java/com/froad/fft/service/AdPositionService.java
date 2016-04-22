package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.AdPosition;

public interface AdPositionService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存AdPosition并返回数据主键（如果主键为null则插入失败）</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午10:39:56 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveAdPosition(AdPosition adPosition);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更改数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午11:34:49 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateAdPositionById(AdPosition adPosition);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午11:46:16 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public AdPosition selectAdPositionById(Long id);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 上午11:59:11
     */
    public Page findAdPositionByPage(Page page);


}
