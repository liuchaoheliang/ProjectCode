package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Area;

public interface AreaService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存Area并返回数据主键（如果主键为null则插入失败）</b> --*</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年2月14日 下午1:22:40 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveArea(Area area);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新数据</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年2月14日 下午1:23:16 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateAreaById(Area area);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
     * <p> 时间: 2014年2月14日 下午1:23:49 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Area selectAreaById(Long id);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 上午11:59:11
     */
    public Page findAreaByPage(Page page);
}
