/**
 * 文件名称:GivePointRuleServiceImpl.java
 * 文件描述: 送积分规则接口实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.service.impl;

import com.froad.fft.persistent.api.GivePointRuleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.GivePointRule;
import com.froad.fft.service.GivePointRuleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@Service("givePointRuleServiceImpl")
public class GivePointRuleServiceImpl implements GivePointRuleService
{
    private static Logger logger = Logger.getLogger(GivePointRuleServiceImpl.class);

    @Resource
    private GivePointRuleMapper givePointRuleMapper;

    public Long saveGivePointRule(GivePointRule temp)
    {
        return givePointRuleMapper.saveGivePointRule(temp);
    }

    public Boolean updateGivePointRuleById(GivePointRule temp)
    {
        if (temp.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return givePointRuleMapper.updateGivePointRuleById(temp);
    }

    public GivePointRule selectGivePointRuleById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return givePointRuleMapper.selectGivePointRuleById(id);
    }

    public Page findGivePointRuleByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(givePointRuleMapper.selectGivePointRuleByPage(page));
        page.setTotalCount(givePointRuleMapper.selectGivePointRuleByPageCount(page));
        return page;
    }

}
