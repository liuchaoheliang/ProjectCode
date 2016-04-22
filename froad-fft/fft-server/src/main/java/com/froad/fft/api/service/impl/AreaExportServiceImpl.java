package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.AreaExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.AreaDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.*;
import com.froad.fft.persistent.entity.Area;
import com.froad.fft.service.AreaService;

import javax.annotation.Resource;

import java.util.*;

public class AreaExportServiceImpl implements AreaExportService
{

    @Resource(name = "areaServiceImpl")
    private AreaService areaService;

    @Override
    public Long addArea(ClientAccessType clientAccessType, ClientVersion clientVersion, AreaDto dto) throws FroadException
    {
        Area temp = DtoToBeanSupport.loadByArea(dto);
        return areaService.saveArea(temp);
    }

    public Boolean updateAreaById(ClientAccessType clientAccessType, ClientVersion clientVersion, AreaDto dto) throws FroadException
    {
        Area temp = DtoToBeanSupport.loadByArea(dto);
        return areaService.updateAreaById(temp);
    }

    @Override
    public PageDto findAreaByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(areaService.findAreaByPage(page));
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
                Area area = DtoToBeanSupport.loadByArea((AreaDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(area);
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
                AreaDto categoryDto = BeanToDtoSupport.loadByAreaDto((Area) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(categoryDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<Area> list = page.getResultsContent();
        List<AreaDto> dlist = new ArrayList();
        for (Area area : list)
        {
            dlist.add(BeanToDtoSupport.loadByAreaDto(area));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

    @Override
    public AreaDto findAreaById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByAreaDto(areaService.selectAreaById(id));
    }
}
