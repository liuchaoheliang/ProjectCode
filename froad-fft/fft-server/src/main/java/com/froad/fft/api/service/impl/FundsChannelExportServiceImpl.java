/**
 * 文件名称:FundsChannelExportServiceImpl.java
 * 文件描述: todo
 * 产品标识: todo
 * 单元描述: todo
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-2
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.FundsChannelExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.FundsChannelDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.FundsChannel;
import com.froad.fft.service.FundsChannelService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class FundsChannelExportServiceImpl implements FundsChannelExportService
{
    @Resource(name = "fundsChannelServiceImpl")
    private FundsChannelService fundsChannelService;

    public Long addFundsChannel(ClientAccessType clientAccessType, ClientVersion clientVersion, FundsChannelDto fundsChannelDto) throws FroadException
    {
        return fundsChannelService.saveFundsChannel(DtoToBeanSupport.loadByFundsChannel(fundsChannelDto));
    }

    public Boolean updateFundsChannelById(ClientAccessType clientAccessType, ClientVersion clientVersion, FundsChannelDto fundsChannelDto)throws FroadException
    {
        return fundsChannelService.updateFundsChannelById(DtoToBeanSupport.loadByFundsChannel(fundsChannelDto));
    }

    public FundsChannelDto findFundsChannelById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)throws FroadException
    {
        return BeanToDtoSupport.loadByFundsChannelDto(fundsChannelService.selectFundsChannelById(id));
    }

    public PageDto<FundsChannelDto> findFundsChannelByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<FundsChannelDto> pageDto)throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(fundsChannelService.findFundsChannelByPage(page));
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
                FundsChannel fundsChannel = DtoToBeanSupport.loadByFundsChannel((FundsChannelDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(fundsChannel);
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
                FundsChannelDto fundsChannelDto = BeanToDtoSupport.loadByFundsChannelDto((FundsChannel) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(fundsChannelDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<FundsChannel> list = page.getResultsContent();
        List<FundsChannelDto> dlist = new ArrayList();
        for (FundsChannel channel : list)
        {
            dlist.add(BeanToDtoSupport.loadByFundsChannelDto(channel));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }


}
