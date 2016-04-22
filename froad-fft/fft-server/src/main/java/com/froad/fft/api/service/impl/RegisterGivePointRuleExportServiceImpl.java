package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.RegisterGivePointRuleExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.RegisterGivePointRuleDto;
import com.froad.fft.dto.RegisterGivePointRuleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.RegisterGivePointRule;
import com.froad.fft.service.RegisterGivePointRuleService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class RegisterGivePointRuleExportServiceImpl implements RegisterGivePointRuleExportService
{

    @Resource(name = "registerGivePointRuleServiceImpl")
    private RegisterGivePointRuleService registerGivePointRuleService;

    @Override
    public Long addRegisterGivePointRule(ClientAccessType clientAccessType, ClientVersion clientVersion, RegisterGivePointRuleDto dto) throws FroadException
    {
        RegisterGivePointRule temp = DtoToBeanSupport.loadByRegisterGivePointRule(dto);
        return registerGivePointRuleService.saveRegisterGivePointRule(temp);
    }

    public Boolean updateRegisterGivePointRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, RegisterGivePointRuleDto dto) throws FroadException
    {
        RegisterGivePointRule temp = DtoToBeanSupport.loadByRegisterGivePointRule(dto);
        return registerGivePointRuleService.updateRegisterGivePointRuleById(temp);
    }

    @Override
    public PageDto findRegisterGivePointRuleByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(registerGivePointRuleService.findRegisterGivePointRuleByPage(page));
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
                RegisterGivePointRule registerGivePointRule = DtoToBeanSupport.loadByRegisterGivePointRule((RegisterGivePointRuleDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(registerGivePointRule);
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
                RegisterGivePointRuleDto categoryDto = BeanToDtoSupport.loadByRegisterGivePointRuleDto((RegisterGivePointRule) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(categoryDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<RegisterGivePointRule> list = page.getResultsContent();
        List<RegisterGivePointRuleDto> dlist = new ArrayList();
        for (RegisterGivePointRule registerGivePointRule : list)
        {
            dlist.add(BeanToDtoSupport.loadByRegisterGivePointRuleDto(registerGivePointRule));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

    @Override
    public RegisterGivePointRuleDto findRegisterGivePointRuleById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByRegisterGivePointRuleDto(registerGivePointRuleService.selectRegisterGivePointRuleById(id));
    }

    @Override
    public RegisterGivePointRuleDto findRegisterGivePointRuleByClientId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByRegisterGivePointRuleDto(registerGivePointRuleService.selectRegisterGivePointRuleByClientId(id));
    }

}
