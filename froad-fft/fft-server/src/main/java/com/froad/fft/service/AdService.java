package com.froad.fft.service;

import java.util.List;

import com.froad.fft.dto.AdDto;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.Ad;

public interface AdService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存Ad并返回数据主键（如果主键为null则插入失败）</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月3日 下午4:34:01 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveAd(Ad ad);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更改数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午10:00:53 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateAdById(Ad ad);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 上午10:19:18 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Ad selectAdById(Long id);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 上午11:59:11
     */
    public Page findAdByPage(Page page);
    
    /**
     * 根据广告位 查询启用的广告
     * @param adPositionId
     * @return
     */
    public List<Ad> findEnableAdByAdPositionId(Long adPositionId);
}
