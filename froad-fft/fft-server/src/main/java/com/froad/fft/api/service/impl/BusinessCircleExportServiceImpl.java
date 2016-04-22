/**
 * 文件名称:BusinessCircleExportServiceImpl.java
 * 文件描述: 商圈对外暴露接口实现
 * 产品标识: 分分通
 * 单元描述: fft-server
 * 编写人: gq.hou_Mimosa
 * 编写时间: 14-3-26
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.BusinessCircleExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.BusinessCircleDto;
import com.froad.fft.enums.*;
import com.froad.fft.persistent.bean.page.*;
import com.froad.fft.persistent.entity.BusinessCircle;
import com.froad.fft.service.BusinessCircleService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author Aides
 * @version 1.0
 */
public class BusinessCircleExportServiceImpl implements BusinessCircleExportService
{
    @Resource(name = "businessCircleServiceImpl")
    private BusinessCircleService businessCircleService;

    public Long saveBusinessCircle(ClientAccessType clientAccessType, ClientVersion clientVersion, BusinessCircleDto dto) throws FroadException
    {
        BusinessCircle temp = DtoToBeanSupport.loadBusinessCircle(dto);
        return businessCircleService.saveBusinessCircle(temp);
    }

    public Boolean updateBusinessCircleById(ClientAccessType clientAccessType, ClientVersion clientVersion, BusinessCircleDto dto) throws FroadException
    {
        BusinessCircle temp = DtoToBeanSupport.loadBusinessCircle(dto);
        return businessCircleService.updateBusinessCircleById(temp);
    }

    public BusinessCircleDto findBusinessCircleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        BusinessCircle temp = businessCircleService.selectBusinessCircleById(id);
        BusinessCircleDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadBusinessCircleDto(temp);
        }
        return result;
    }

    public PageDto<BusinessCircleDto> findBusinessCircleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<BusinessCircleDto> pageDto)throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(businessCircleService.selectBusinessCircleByPage(page));
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
                   BusinessCircle circle = DtoToBeanSupport.loadBusinessCircle((BusinessCircleDto) pageDto.getPageFilterDto().getFilterEntity());
                   pageFilter.setFilterEntity(circle);
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
                   BusinessCircleDto circleDto = BeanToDtoSupport.loadBusinessCircleDto((BusinessCircle) page.getPageFilter().getFilterEntity());
                   pageFilterDto.setFilterEntity(circleDto);
               }
               pageFilterDto.setProperty(page.getPageFilter().getProperty());
               pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
               pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
               pageDto.setPageFilterDto(pageFilterDto);
           }

           List<BusinessCircle> list = page.getResultsContent();
           List<BusinessCircleDto> dlist = new ArrayList();
           for (BusinessCircle circle : list)
           {
               dlist.add(BeanToDtoSupport.loadBusinessCircleDto(circle));
           }
           pageDto.setResultsContent(dlist);

           return pageDto;
       }

}
