/**
 * 文件名称:BusinessCircleServiceImpl.java
 * 文件描述: 商圈服务实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-26
 * 历史修改:  
 */
package com.froad.fft.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.froad.fft.persistent.api.BusinessCircleMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.BusinessCircle;
import com.froad.fft.service.BusinessCircleService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */

@Service("businessCircleServiceImpl")
public class BusinessCircleServiceImpl implements BusinessCircleService
{
    private static Logger logger = Logger.getLogger(BusinessCircleServiceImpl.class);

    @Resource
    private BusinessCircleMapper businessCircleMapper;

    public Long saveBusinessCircle(BusinessCircle temp)
    {
        return businessCircleMapper.saveBusinessCircle(temp);
    }

    public Boolean updateBusinessCircleById(BusinessCircle temp)
    {
        if (temp.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return businessCircleMapper.updateBusinessCircleById(temp);
    }

    public BusinessCircle selectBusinessCircleById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return businessCircleMapper.selectBusinessCircleById(id);
    }

    public Page selectBusinessCircleByPage(Page page)
    {
        if (page == null)
        {
            page = new Page();
        }
        page.setResultsContent(businessCircleMapper.selectBusinessCircleByPage(page));
        page.setTotalCount(businessCircleMapper.selectBusinessCircleByPageCount(page));
        return page;
    }


}
