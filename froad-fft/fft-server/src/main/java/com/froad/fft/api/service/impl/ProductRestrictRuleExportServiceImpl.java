package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.ProductRestrictRuleExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.ProductRestrictRuleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.*;
import com.froad.fft.persistent.entity.ProductRestrictRule;
import com.froad.fft.service.ProductRestrictRuleService;

import javax.annotation.Resource;

import java.util.*;

public class ProductRestrictRuleExportServiceImpl implements ProductRestrictRuleExportService
{

    @Resource(name = "productRestrictRuleServiceImpl")
    private ProductRestrictRuleService productRestrictRuleService;

    @Override
    public Long addProductRestrictRule(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductRestrictRuleDto dto) throws FroadException
    {
        ProductRestrictRule temp = DtoToBeanSupport.loadByProductRestrictRule(dto);
        return productRestrictRuleService.saveProductRestrictRule(temp);
    }

    public Boolean updateProductRestrictRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductRestrictRuleDto dto) throws FroadException
    {
        ProductRestrictRule temp = DtoToBeanSupport.loadByProductRestrictRule(dto);
        return productRestrictRuleService.updateProductRestrictRuleById(temp);
    }

    @Override
    public PageDto findProductRestrictRuleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(productRestrictRuleService.findProductRestrictRuleByPage(page));
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
                ProductRestrictRule productRestrictRule = DtoToBeanSupport.loadByProductRestrictRule((ProductRestrictRuleDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(productRestrictRule);
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
                ProductRestrictRuleDto categoryDto = BeanToDtoSupport.loadByProductRestrictRuleDto((ProductRestrictRule) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(categoryDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<ProductRestrictRule> list = page.getResultsContent();
        List<ProductRestrictRuleDto> dlist = new ArrayList();
        for (ProductRestrictRule productRestrictRule : list)
        {
            dlist.add(BeanToDtoSupport.loadByProductRestrictRuleDto(productRestrictRule));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

    @Override
    public ProductRestrictRuleDto findProductRestrictRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByProductRestrictRuleDto(productRestrictRuleService.selectProductRestrictRuleById(id));
    }
}
