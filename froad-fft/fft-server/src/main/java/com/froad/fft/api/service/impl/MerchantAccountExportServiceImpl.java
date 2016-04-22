/**
 * 文件名称:MerchantAccountExportServiceImpl.java
 * 文件描述: 商户账户export接口实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-1
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.MerchantAccountExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.*;
import com.froad.fft.dto.MerchantAccountDto;
import com.froad.fft.enums.*;
import com.froad.fft.persistent.bean.page.*;
import com.froad.fft.persistent.entity.MerchantAccount;
import com.froad.fft.service.MerchantAccountService;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class MerchantAccountExportServiceImpl implements MerchantAccountExportService
{
    @Resource(name = "merchantAccountServiceImpl")
    private MerchantAccountService merchantAccountService;

    @Override
    public Long addMerchantAccount(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantAccountDto merchantAccountDto) throws FroadException
    {
        return merchantAccountService.saveMerchantAccount(DtoToBeanSupport.loadByMerchantAccount(merchantAccountDto));
    }

    public Boolean updateMerchantAccountById(ClientAccessType clientAccessType, ClientVersion clientVersion, MerchantAccountDto merchantAccountDto)
    {
        return merchantAccountService.updateMerchantAccountById(DtoToBeanSupport.loadByMerchantAccount(merchantAccountDto));
    }

    public MerchantAccountDto findMerchantAccountById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByMerchantAccountDto(merchantAccountService.selectMerchantAccountById(id));
    }

    public PageDto<MerchantAccountDto> findMerchantAccountByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<MerchantAccountDto> pageDto)
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(merchantAccountService.findMerchantAccountByPage(page));
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
                MerchantAccount account = DtoToBeanSupport.loadByMerchantAccount((MerchantAccountDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(account);
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
                MerchantAccountDto merchantOutletDto = BeanToDtoSupport.loadByMerchantAccountDto((MerchantAccount) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(merchantOutletDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<MerchantAccount> list = page.getResultsContent();
        List<MerchantAccountDto> dlist = new ArrayList();
        for (MerchantAccount account : list)
        {
            dlist.add(BeanToDtoSupport.loadByMerchantAccountDto(account));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

}
