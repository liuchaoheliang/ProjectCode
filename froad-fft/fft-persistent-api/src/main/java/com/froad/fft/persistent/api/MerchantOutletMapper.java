package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.bean.OutletStatistic;

import java.util.List;
import java.util.Map;

public interface MerchantOutletMapper
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存MerchantOutlet</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 上午11:29:16 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveMerchantOutlet(MerchantOutlet merchantOutlet);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 上午11:46:59 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateMerchantOutletById(MerchantOutlet merchantOutlet);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 上午11:58:38 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public MerchantOutlet selectMerchantOutletById(Long id);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 上午10:32:48
     */
    public List<MerchantOutlet> selectMerchantOutletByPage(Page page);

    /**
     * 方法描述：分页总条数
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年3月28日 上午10:32:50
     */
    public Integer selectMerchantOutletByPageCount(Page page);

    /**
     * 获取所有未绑定预售提货点的商户分店
     *
     * @return 商户分店列表
     */
    public List<MerchantOutlet> selectAllUnboundtoPresellDeliveryOutlet();

    /**
     * 方法描述：通过实体设置的属性进行条件查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年4月6日 下午2:32:16
     */
    public List<MerchantOutlet> selectByConditions(MerchantOutlet merchantOutlet);



}
