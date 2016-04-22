package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.FundsChannel;

import java.util.List;

public interface FundsChannelMapper
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存FundsChannel</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午6:00:32 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveFundsChannel(FundsChannel fundsChannel);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新数据</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午6:31:38 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateFundsChannelById(FundsChannel fundsChannel);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午6:55:39 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public FundsChannel selectFundsChannelById(Long id);

    /**
     * 资金渠道分页查询
     *
     * @param page 分页page
     * @return 分页list
     */
    public List<FundsChannel> selectFundsChannelByPage(Page page);

    /**
     * 资金渠道分页统计
     *
     * @param page 分页page
     * @return 分页数据量
     */
    public Integer selectFundsChannelByPageCount(Page page);
    
    
    /**
      * 方法描述：查询资金渠道列表
      * @param: clientId
      * @return: List<FundsChannel>
      * @version: 1.0
      * @author: 李金魁 lijinkui@f-road.com.cn
      * @time: 2014年4月30日 上午10:20:48
      */
    public List<FundsChannel> selectByClientId(Long clientId);

}
