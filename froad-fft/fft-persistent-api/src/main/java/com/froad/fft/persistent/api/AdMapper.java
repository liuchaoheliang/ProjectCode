package com.froad.fft.persistent.api;

import java.util.List;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Ad;

public interface AdMapper
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存Ad</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月3日 下午4:29:27 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveAd(Ad ad);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午9:59:25 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateAdById(Ad ad);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午10:18:23 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Ad selectAdById(Long id);

    /**
     * 分页查询
     *
     * @param page
     * @return
     */
    public List<Ad> selectAdByPage(Page page);

    /**
     * 方法描述：分页总条数
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author:侯国权
     * @time: 2014年4月17日 上午10:32:50
     */
    public Integer selectAdByPageCount(Page page);
    
    /**
     * 根据广告位 查询启用的广告
     * @param id
     * @return
     */
    public List<Ad> selectEnableAdByAdPositionId(Long adPositionId);

}
