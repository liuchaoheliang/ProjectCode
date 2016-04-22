package com.froad.fft.persistent.api;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.RegisterGivePointRule;

import java.util.List;

public interface RegisterGivePointRuleMapper
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存RegisterGivePointRule</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午3:42:59 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveRegisterGivePointRule(RegisterGivePointRule registerGivePointRule);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午4:06:31 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateRegisterGivePointRuleById(RegisterGivePointRule registerGivePointRule);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午4:14:35 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public RegisterGivePointRule selectRegisterGivePointRuleById(Long id);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过客户端编号获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午4:14:35 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public RegisterGivePointRule selectRegisterGivePointRuleByClientId(Long id);

    /**
     * 方法描述：分页查询
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author: 侯国权
     * @time: 2014年3月28日 上午10:32:48
     */
    public List<RegisterGivePointRule> selectRegisterGivePointRuleByPage(Page page);

    /**
     * 方法描述：分页总条数
     *
     * @param:
     * @return:
     * @version: 1.0
     * @author:侯国权
     * @time: 2014年3月28日 上午10:32:50
     */
    public Integer selectRegisterGivePointRuleByPageCount(Page page);
}
