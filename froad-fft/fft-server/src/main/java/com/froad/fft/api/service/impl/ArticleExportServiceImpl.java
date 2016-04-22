package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.ArticleExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ArticleDto;
import com.froad.fft.dto.ArticleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Article;
import com.froad.fft.service.ArticleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class ArticleExportServiceImpl implements ArticleExportService {

    @Resource(name = "articleServiceImpl")
      private ArticleService articleService;

      @Override
      public Long addArticle(ClientAccessType clientAccessType, ClientVersion clientVersion, ArticleDto dto) throws FroadException
      {
          Article temp = DtoToBeanSupport.loadByArticle(dto);
          return articleService.saveArticle(temp);
      }

      public Boolean updateArticleById(ClientAccessType clientAccessType, ClientVersion clientVersion, ArticleDto dto) throws FroadException
      {
          Article temp = DtoToBeanSupport.loadByArticle(dto);
          return articleService.updateArticleById(temp);
      }

      @Override
      public PageDto findArticleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
      {
          Page page = loadBy(pageDto);
          PageDto returnPageDto = loadBy(articleService.findArticleByPage(page));
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
                  Article article = DtoToBeanSupport.loadByArticle((ArticleDto) pageDto.getPageFilterDto().getFilterEntity());
                  pageFilter.setFilterEntity(article);
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
                  ArticleDto categoryDto = BeanToDtoSupport.loadByArticleDto((Article) page.getPageFilter().getFilterEntity());
                  pageFilterDto.setFilterEntity(categoryDto);
              }
              pageFilterDto.setProperty(page.getPageFilter().getProperty());
              pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
              pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
              pageDto.setPageFilterDto(pageFilterDto);
          }

          List<Article> list = page.getResultsContent();
          List<ArticleDto> dlist = new ArrayList();
          for (Article article : list)
          {
              dlist.add(BeanToDtoSupport.loadByArticleDto(article));
          }
          pageDto.setResultsContent(dlist);

          return pageDto;
      }

      @Override
      public ArticleDto findArticleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
      {
          return BeanToDtoSupport.loadByArticleDto(articleService.selectArticleById(id));
      }

}
