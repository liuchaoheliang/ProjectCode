/**
 * 文件名称:PresellDeliveryService.java
 * 文件描述: 自提点接口实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.service.impl;

import com.froad.fft.persistent.api.PresellDeliveryMapper;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.entity.PresellDelivery;
import com.froad.fft.service.PresellDeliveryService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
@Service("presellDeliveryServiceImpl")
public class PresellDeliveryServiceImpl implements PresellDeliveryService
{
    private static Logger logger = Logger.getLogger(PresellDeliveryService.class);

    @Resource
    private PresellDeliveryMapper presellDeliveryMapper;

    public Long savePresellDelivery(PresellDelivery temp)
    {
        return presellDeliveryMapper.savePresellDelivery(temp);
    }

    public Boolean updatePresellDeliveryById(PresellDelivery temp)
    {
        if (temp.getId() == null)
        {
            logger.error("更新对象缺少必要Id字段值");
            return false;
        }
        return presellDeliveryMapper.updatePresellDeliveryById(temp);
    }

    public PresellDelivery selectPresellDeliveryById(Long id)
    {
        if (id == null)
        {
            logger.error("查询数据id为空");
            return null;
        }
        return presellDeliveryMapper.selectPresellDeliveryById(id);
    }

    public List<PresellDelivery> selectPresellDeliveryByPage(Page page)
    {
        return null;
    }

	
	@Override
	public Page findPresellDeliveryByPage(Page page) {
		if(page == null){
			page=new Page();
		}
		page.setResultsContent(presellDeliveryMapper.selectPresellDeliveryByPage(page));
		page.setTotalCount(presellDeliveryMapper.selectPresellDeliveryByPageCount(page));		
		return page;
	}
}
