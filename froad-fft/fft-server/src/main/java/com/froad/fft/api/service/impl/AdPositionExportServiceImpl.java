package com.froad.fft.api.service.impl;

import javax.annotation.Resource;

import com.froad.fft.api.service.AdPositionExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.AdPositionDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.AdPosition;
import com.froad.fft.service.AdPositionService;

import java.util.ArrayList;
import java.util.List;

public class AdPositionExportServiceImpl implements AdPositionExportService
{

    @Resource(name = "adPositionServiceImpl")
    private AdPositionService adPositionService;

    @Override
    public Long addAdPosition(ClientAccessType clientAccessType, ClientVersion clientVersion, AdPositionDto adPositionDto) throws FroadException
    {
        return adPositionService.saveAdPosition(DtoToBeanSupport.loadByAdPosition(adPositionDto));
    }

    @Override
    public AdPositionDto findAdPositionById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        return BeanToDtoSupport.loadByAdPositionDto(adPositionService.selectAdPositionById(id));
    }

    public Boolean updateAdPositionById(ClientAccessType clientAccessType, ClientVersion clientVersion, AdPositionDto dto) throws FroadException
    {
        return adPositionService.updateAdPositionById(DtoToBeanSupport.loadByAdPosition(dto));
    }

    public PageDto findAdPositionByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(adPositionService.findAdPositionByPage(page));
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
                  AdPosition ad = DtoToBeanSupport.loadByAdPosition((AdPositionDto) pageDto.getPageFilterDto().getFilterEntity());

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
                  AdPositionDto categoryDto = BeanToDtoSupport.loadByAdPositionDto((AdPosition) page.getPageFilter().getFilterEntity());
                  pageFilterDto.setFilterEntity(categoryDto);
              }
              pageFilterDto.setProperty(page.getPageFilter().getProperty());
              pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
              pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
              pageDto.setPageFilterDto(pageFilterDto);
          }

          List<AdPosition> list = page.getResultsContent();
          List<AdPositionDto> dlist = new ArrayList();
          for (AdPosition ad : list)
          {
              dlist.add(BeanToDtoSupport.loadByAdPositionDto(ad));
          }
          pageDto.setResultsContent(dlist);

          return pageDto;
      }





}
