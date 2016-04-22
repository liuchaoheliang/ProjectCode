package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.RegisterGivePointRule;

public interface RegisterGivePointRuleService
{

    /**
     * *******************************************************
     * <p> 描述: *-- <b>保存RegisterGivePointRule并返回数据主键（如果主键为null则插入失败）</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午3:44:47 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Long saveRegisterGivePointRule(RegisterGivePointRule registerGivePointRule);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键更新</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午4:08:25 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public Boolean updateRegisterGivePointRuleById(RegisterGivePointRule registerGivePointRule);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午4:15:54 </p>
     * <p> 版本: 1.0.1 </p>
     * ********************************************************
     */
    public RegisterGivePointRule selectRegisterGivePointRuleById(Long id);

    /**
     * *******************************************************
     * <p> 描述: *-- <b>通过客户端编号获取对象</b> --* </p>
     * <p> 作者: 赵肖瑶 </p>
     * <p> 时间: 2014年1月6日 下午4:15:54 </p>
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
     * @time: 2014年4月15日 上午11:59:11
     */
    public Page findRegisterGivePointRuleByPage(Page page);
}
