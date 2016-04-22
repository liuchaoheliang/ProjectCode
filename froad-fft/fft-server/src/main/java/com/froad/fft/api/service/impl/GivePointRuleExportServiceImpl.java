/**
 * 文件名称:GivePointRuleExportServiceImpl.java
 * 文件描述: 送积分规则exportservice实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.GivePointRuleExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.GivePointRuleDto;
import com.froad.fft.enums.*;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.GivePointRule;
import com.froad.fft.service.GivePointRuleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class GivePointRuleExportServiceImpl implements GivePointRuleExportService
{
    @Resource(name = "givePointRuleServiceImpl")
    private GivePointRuleService givePointRuleService;

    public Long saveGivePointRule(ClientAccessType clientAccessType, ClientVersion clientVersion, GivePointRuleDto dto) throws FroadException
    {
        GivePointRule temp = DtoToBeanSupport.loadGivePointRule(dto);
        return givePointRuleService.saveGivePointRule(temp);
    }

    public Boolean updateGivePointRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, GivePointRuleDto dto) throws FroadException
    {
        GivePointRule temp = DtoToBeanSupport.loadGivePointRule(dto);
        return givePointRuleService.updateGivePointRuleById(temp);
    }

    public GivePointRuleDto findGivePointRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        GivePointRule temp = givePointRuleService.selectGivePointRuleById(id);
        GivePointRuleDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadGivePointRuleDto(temp);
        }
        return result;
    }

    public PageDto findGivePointRuleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(givePointRuleService.findGivePointRuleByPage(page));
        return returnPageDto;
    }

    private Page loadBy(PageDto pageDto)
    {

        Page page = new Page();
        page.setPageNumber(pageDto.getPageNumber());
        page.setPageSize(pageDto.getPageSize());

        //排序
        if (pageDto.getOrderDto() != null)
        {
            Order order = new Order();
            order.setProperty(pageDto.getOrderDto().getProperty());
            order.setDirection(pageDto.getOrderDto().getDirection() != null ? com.froad.fft.persistent.bean.page.Order.Direction.valueOf(pageDto.getOrderDto().getDirection().toString()) : null);
            page.setOrder(order);
        }

        //过滤条件
        if (pageDto.getPageFilterDto() != null)
        {
            PageFilter pageFilter = new PageFilter();
            if (pageDto.getPageFilterDto().getFilterEntity() != null)
            {
                GivePointRule ad = DtoToBeanSupport.loadGivePointRule((GivePointRuleDto) pageDto.getPageFilterDto().getFilterEntity());

                pageFilter.setFilterEntity(ad);
            }
            pageFilter.setProperty(pageDto.getPageFilterDto().getProperty());


            if (pageDto.getPageFilterDto().getStartTime() != null)
            {
                pageFilter.setStartTime(pageDto.getPageFilterDto().getStartTime());
            }

            if (pageDto.getPageFilterDto().getEndTime() != null)
            {
                pageFilter.setEndTime(pageDto.getPageFilterDto().getEndTime());
            }

            page.setPageFilter(pageFilter);
        }
        return page;
    }

    private PageDto loadBy(Page page)
    {

        PageDto pageDto = new PageDto();

        pageDto.setPageNumber(page.getPageNumber());
        pageDto.setPageSize(page.getPageSize());
        pageDto.setTotalCount(page.getTotalCount());
        pageDto.setPageCount(page.getPageCount());

        if (page.getOrder() != null)
        {
            OrderDto orderDto = new OrderDto();
            orderDto.setProperty(page.getOrder().getProperty());
            orderDto.setDirection(page.getOrder().getDirection() != null ? com.froad.fft.bean.page.OrderDto.Direction.valueOf(page.getOrder().getDirection().toString()) : null);
            pageDto.setOrderDto(orderDto);
        }

        if (page.getPageFilter() != null)
        {
            PageFilterDto pageFilterDto = new PageFilterDto();
            if (page.getPageFilter().getFilterEntity() != null)
            {
                GivePointRuleDto categoryDto = BeanToDtoSupport.loadGivePointRuleDto((GivePointRule) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(categoryDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<GivePointRule> list = page.getResultsContent();
        List<GivePointRuleDto> dlist = new ArrayList();
        for (GivePointRule rule : list)
        {
            dlist.add(BeanToDtoSupport.loadGivePointRuleDto(rule));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }
}
