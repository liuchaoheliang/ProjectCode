package com.froad.fft.service;


import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.common.enums.TransType;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.bean.OutletStatistic;

import java.util.List;

public interface MerchantOutletService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>方法说明</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 上午11:30:37 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveMerchantOutlet(MerchantOutlet merchantOutlet);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月7日 上午11:47:49 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateMerchantOutletById(MerchantOutlet merchantOutlet);

    /**
     * 根据Id获取商户分店信息
     *
     * @param id 商户分店Id
     * @return 商户分店实体
     */
    public MerchantOutlet selectMerchantOutletById(Long id);

    /**
     * 商户分店分页查询
     *
     * @param page 分页查询page
     * @return 分页结果
     */
    public Page findMerchantOutletByPage(Page page);

    /**
     * 获取所有未绑定预售提货点的商户分店
     *
     * @return 商户分店列表
     */
    public List<MerchantOutlet> selectAllUnboundtoPresellDeliveryOutlet();

    /**
     * 方法描述：根据实体设置的属性进行条件查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 刘超 liuchao@f-road.com.cn
     * @time: 2014年4月6日 下午2:36:33
     */
    public List<MerchantOutlet> selectByConditions(MerchantOutlet merchantOutlet);


}
