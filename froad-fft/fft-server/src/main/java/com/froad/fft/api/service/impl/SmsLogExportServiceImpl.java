package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.SmsLogExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.SmsLogDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.SmsLog;
import com.froad.fft.service.SmsLogService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class SmsLogExportServiceImpl implements SmsLogExportService
{

    @Resource(name = "smsLogServiceImpl")
    private SmsLogService smsLogService;

    @Override
    public Long addSmsLog(ClientAccessType clientAccessType, ClientVersion clientVersion, SmsLogDto smsLogDto) throws FroadException
    {
        return smsLogService.saveSmsLog(DtoToBeanSupport.loadBySmsLog(smsLogDto));
    }

    public Boolean updateSmsLogStatusById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return smsLogService.updateSmsLogStatusById(id);
    }

    public SmsLogDto findSmsLogById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        SmsLog temp = smsLogService.selectSmsLogById(id);
        SmsLogDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadBySmsLogDto(temp);
        }
        return result;
    }

    public PageDto<SmsLogDto> findSmsLogByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<SmsLogDto> pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(smsLogService.findSmsLogByPage(page));
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
                SmsLog temp = DtoToBeanSupport.loadBySmsLog((SmsLogDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(temp);
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
                SmsLogDto dto = BeanToDtoSupport.loadBySmsLogDto((SmsLog) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(dto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<SmsLog> list = page.getResultsContent();
        List<SmsLogDto> dlist = new ArrayList();
        for (SmsLog temp : list)
        {
            dlist.add(BeanToDtoSupport.loadBySmsLogDto(temp));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

}
