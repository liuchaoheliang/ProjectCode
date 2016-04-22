package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.ArticleCategoryExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ArticleCategoryDto;
import com.froad.fft.dto.ArticleCategoryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.ArticleCategory;
import com.froad.fft.service.ArticleCategoryService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ArticleCategoryExportServiceImpl implements ArticleCategoryExportService
{
    @Resource(name = "articleCategoryServiceImpl")
    private ArticleCategoryService articleCategoryService;

    @Override
    public Long addArticleCategory(ClientAccessType clientAccessType, ClientVersion clientVersion, ArticleCategoryDto dto) throws FroadException
    {
        ArticleCategory temp = DtoToBeanSupport.loadByArticleCategory(dto);
        return articleCategoryService.saveArticleCategory(temp);
    }

    public Boolean updateArticleCategoryById(ClientAccessType clientAccessType, ClientVersion clientVersion, ArticleCategoryDto dto) throws FroadException
    {
        ArticleCategory temp = DtoToBeanSupport.loadByArticleCategory(dto);
        return articleCategoryService.updateArticleCategoryById(temp);
    }

    @Override
    public PageDto findArticleCategoryByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(articleCategoryService.findArticleCategoryByPage(page));
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
                ArticleCategory articleCategory = DtoToBeanSupport.loadByArticleCategory((ArticleCategoryDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(articleCategory);
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
                ArticleCategoryDto categoryDto = BeanToDtoSupport.loadByArticleCategoryDto((ArticleCategory) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(categoryDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<ArticleCategory> list = page.getResultsContent();
        List<ArticleCategoryDto> dlist = new ArrayList();
        for (ArticleCategory articleCategory : list)
        {
            dlist.add(BeanToDtoSupport.loadByArticleCategoryDto(articleCategory));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

    @Override
    public ArticleCategoryDto findArticleCategoryById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByArticleCategoryDto(articleCategoryService.selectArticleCategoryById(id));
    }

}
