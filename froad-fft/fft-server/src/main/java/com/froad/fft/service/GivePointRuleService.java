/**
 * 文件名称:GivePointRuleService.java
 * 文件描述: 送积分规则 service定义
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.service;

import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.GivePointRule;

import java.util.List;

/**
 * 送积分规则接口定义
 *
 * @author houguoquan_Aides
 * @version 1.0
 */
public interface GivePointRuleService
{
    /**
        * *******************************************************
        * <p> 描述: *-- <b>保存GivePointRule</b> --* </p>
        * <p> 作者: 侯国权 </p>
        * <p> 时间: 2014年1月3日 下午4:29:27 </p>
        * <p> 版本: 1.0.1 </p>
        * ********************************************************
        */
       public Long saveGivePointRule(GivePointRule temp);

       /**
        * *******************************************************
        * <p> 描述: *-- <b>通过主键更新</b> --* </p>
        * <p> 作者: 侯国权 </p>
        * <p> 时间: 2014年1月6日 上午9:59:25 </p>
        * <p> 版本: 1.0.1 </p>
        * ********************************************************
        */
       public Boolean updateGivePointRuleById(GivePointRule temp);

       /**
        * *******************************************************
        * <p> 描述: *-- <b>通过主键获取对象</b> --* </p>
        * <p> 作者: 侯国权 </p>
        * <p> 时间: 2014年1月6日 上午10:18:23 </p>
        * <p> 版本: 1.0.1 </p>
        * ********************************************************
        */
       public GivePointRule selectGivePointRuleById(Long id);

       /**
        * 分页查询
        *
        * @param page
        * @return
        */
       public Page findGivePointRuleByPage(Page page);
}
