package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.MerchantCategoryExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.MerchantCategoryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.*;
import com.froad.fft.persistent.entity.MerchantCategory;
import com.froad.fft.service.MerchantCategoryService;

import javax.annotation.Resource;
import java.util.*;

public class MerchantCategoryExportServiceImpl implements MerchantCategoryExportService
{
    @Resource(name = "merchantCategoryServiceImpl")
    private MerchantCategoryService merchantCategoryService;

    @Override
    public Long addMerchantCategory(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantCategoryDto merchantCategoryDto) throws FroadException
    {
        // TODO Auto-generated method stub
        return null;
    }

    public PageDto<MerchantCategoryDto> findMerchantCategoryByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<MerchantCategoryDto> pageDto)throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(merchantCategoryService.findMerchantCategoryByPage(page));
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
                MerchantCategory category = DtoToBeanSupport.loadByMerchantCategory((MerchantCategoryDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(category);
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
                MerchantCategoryDto categoryDto = BeanToDtoSupport.loadByMerchantCategoryDto((MerchantCategory) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(categoryDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<MerchantCategory> list = page.getResultsContent();
        List<MerchantCategoryDto> dlist = new ArrayList();
        for (MerchantCategory category : list)
        {
            dlist.add(BeanToDtoSupport.loadByMerchantCategoryDto(category));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

}
