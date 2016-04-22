/**
 * 文件名称:NavigationExportServiceImpl.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-14
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.NavigationExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;

import com.froad.fft.dto.NavigationDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;

import com.froad.fft.persistent.entity.Navigation;
import com.froad.fft.service.AdService;
import com.froad.fft.service.NavigationService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class NavigationExportServiceImpl implements NavigationExportService
{

    @Resource(name = "navigationServiceImpl")
    private NavigationService navigationService;

    @Override
    public Long addNavigation(ClientAccessType clientAccessType, ClientVersion clientVersion, NavigationDto adDto) throws FroadException
    {
        Navigation temp = DtoToBeanSupport.loadByNavigation(adDto);
        return navigationService.saveNavigation(temp);
    }

    public PageDto findNavigationByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(navigationService.findNavigationByPage(page));
        return returnPageDto;
    }

    public NavigationDto findNavigationById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByNavigationDto(navigationService.selectNavigationById(id));
    }

    public Boolean updateNavigationById(ClientAccessType clientAccessType, ClientVersion clientVersion, NavigationDto dto) throws FroadException
    {
        Navigation temp = DtoToBeanSupport.loadByNavigation(dto);
        return navigationService.updateNavigationById(temp);
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
                Navigation navigation = DtoToBeanSupport.loadByNavigation((NavigationDto) pageDto.getPageFilterDto().getFilterEntity());

                pageFilter.setFilterEntity(navigation);
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
                NavigationDto dto = BeanToDtoSupport.loadByNavigationDto((Navigation) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(dto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<Navigation> list = page.getResultsContent();
        List<NavigationDto> dlist = new ArrayList();
        for (Navigation navigation : list)
        {
            dlist.add(BeanToDtoSupport.loadByNavigationDto(navigation));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }
}
