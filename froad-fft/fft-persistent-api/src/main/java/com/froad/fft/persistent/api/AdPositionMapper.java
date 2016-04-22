package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.AdPosition;

import java.util.List;

public interface AdPositionMapper
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存AdPosition</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午10:37:57 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveAdPosition(AdPosition adPosition);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午11:33:34 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateAdPositionById(AdPosition adPosition);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午11:44:26 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public AdPosition selectAdPositionById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public List<AdPosition> selectAdPositionByPage(Page page);

    /**
     * 方法描述：分页总条数
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author:侯国权
     * @time: 2014年4月17日 上午10:32:50
     */
    public Integer selectAdPositionByPageCount(Page page);


}
