package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Navigation;

public interface NavigationService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存AdPosition并返回数据主键（如果主键为null则插入失败）</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 下午2:36:58 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveNavigation(Navigation navigation);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 下午3:27:02 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateNavigationById(Navigation navigation);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键查询数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 下午3:31:21 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Navigation selectNavigationById(Long id);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 上午11:59:11
     */
    public Page findNavigationByPage(Page page);
}
