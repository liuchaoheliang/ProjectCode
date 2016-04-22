package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.MerchantOutletExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.SysClientDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.*;
import com.froad.fft.persistent.entity.MerchantOutlet;
import com.froad.fft.persistent.bean.OutletStatistic;
import com.froad.fft.service.MerchantOutletService;
import com.froad.fft.service.SysClientService;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;

public class MerchantOutletExportServiceImpl implements MerchantOutletExportService
{
    @Resource(name = "merchantOutletServiceImpl")
    private MerchantOutletService merchantOutletService;

    @Resource(name = "sysClientServiceImpl")
    SysClientService sysClientService;

    @Override
    public Long addMerchantOutlet(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantOutletDto merchantOutletDto) throws FroadException
    {
        return merchantOutletService.saveMerchantOutlet(DtoToBeanSupport.loadByMerchantOutlet(merchantOutletDto));
    }

    public Boolean updateMerchantOutletById(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantOutletDto merchantOutletDto)
    {
        return merchantOutletService.updateMerchantOutletById(DtoToBeanSupport.loadByMerchantOutlet(merchantOutletDto));
    }

    public MerchantOutletDto findMerchantOutletById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByMerchantOutletDto(merchantOutletService.selectMerchantOutletById(id));
    }

    public PageDto<MerchantOutletDto> findMerchantOutletByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<MerchantOutletDto> pageDto)
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(merchantOutletService.findMerchantOutletByPage(page));
        return returnPageDto;
    }

    public List<MerchantOutletDto> selectAllUnboundtoPresellDeliveryOutlet()
    {
        List<MerchantOutletDto> result = new ArrayList<MerchantOutletDto>();
        List<MerchantOutlet> tempList = merchantOutletService.selectAllUnboundtoPresellDeliveryOutlet();
        for (MerchantOutlet outlet : tempList)
        {
            result.add(BeanToDtoSupport.loadByMerchantOutletDto(outlet));
        }
        return result;
    }

    @Override
    public List<MerchantOutletDto> selectByConditions(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantOutletDto merchantOutletDto) throws FroadException
    {
        List<MerchantOutlet> mlist = merchantOutletService.selectByConditions(DtoToBeanSupport.loadByMerchantOutlet(merchantOutletDto));
        List<MerchantOutletDto> dlist = new ArrayList();
        for (MerchantOutlet outlet : mlist)
        {
            dlist.add(BeanToDtoSupport.loadByMerchantOutletDto(outlet));
        }
        return dlist;
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
                MerchantOutlet merchantOutlet = DtoToBeanSupport.loadByMerchantOutlet((MerchantOutletDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(merchantOutlet);
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
                MerchantOutletDto merchantOutletDto = BeanToDtoSupport.loadByMerchantOutletDto((MerchantOutlet) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(merchantOutletDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<MerchantOutlet> list = page.getResultsContent();
        List<MerchantOutletDto> dlist = new ArrayList();
        for (MerchantOutlet outlet : list)
        {
            dlist.add(BeanToDtoSupport.loadByMerchantOutletDto(outlet));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }




}
