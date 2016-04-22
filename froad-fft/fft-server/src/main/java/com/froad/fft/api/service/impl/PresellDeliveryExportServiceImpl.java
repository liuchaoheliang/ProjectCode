/**
 * 文件名称:PresellDeliveryExportServiceImpl.java
 * 文件描述: 自提点exportservice实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.froad.fft.api.service.PresellDeliveryExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.enums.*;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.PresellDelivery;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.service.PresellDeliveryService;

import javax.annotation.Resource;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class PresellDeliveryExportServiceImpl implements PresellDeliveryExportService
{
    @Resource(name = "presellDeliveryServiceImpl")
    private PresellDeliveryService presellDeliveryService;

    public Long savePresellDelivery(ClientAccessType clientAccessType, ClientVersion clientVersion, PresellDeliveryDto dto) throws FroadException
    {
        return presellDeliveryService.savePresellDelivery( DtoToBeanSupport.loadByPresellDelivery(dto));
    }

    public Boolean updatePresellDeliveryById(ClientAccessType clientAccessType, ClientVersion clientVersion, PresellDeliveryDto dto) throws FroadException
    {
        PresellDelivery temp = DtoToBeanSupport.loadByPresellDelivery(dto);
        return presellDeliveryService.updatePresellDeliveryById(temp);
    }

    public PresellDeliveryDto findPresellDeliveryById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        PresellDelivery temp = presellDeliveryService.selectPresellDeliveryById(id);
        PresellDeliveryDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadByPresellDeliveryDto(temp);
        }
        return result;
    }

    public PageDto<PresellDeliveryDto> findPresellDeliveryByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<PresellDeliveryDto> pageDto)
    {
		Page page=loadBy(pageDto);
		PageDto returnPageDto=loadBy(presellDeliveryService.findPresellDeliveryByPage(page));
		return returnPageDto;
    }
    
    
private Page loadBy(PageDto pageDto){
		
		Page page=new Page();		
		page.setPageNumber(pageDto.getPageNumber());
		page.setPageSize(pageDto.getPageSize());
		
		//排序
		if(pageDto.getOrderDto() != null){
			Order order=new Order();
			order.setProperty(pageDto.getOrderDto().getProperty());
			order.setDirection(pageDto.getOrderDto().getDirection() != null ? com.froad.fft.persistent.bean.page.Order.Direction.valueOf(pageDto.getOrderDto().getDirection().toString()) : null);
			page.setOrder(order);
		}
		
		//过滤条件
		if(pageDto.getPageFilterDto() != null){
			PageFilter pageFilter=new PageFilter();
			if(pageDto.getPageFilterDto().getFilterEntity() != null){
				PresellDelivery presellDelivery=DtoToBeanSupport.loadByPresellDelivery((PresellDeliveryDto)pageDto.getPageFilterDto().getFilterEntity());
				pageFilter.setFilterEntity(presellDelivery);
			}
			pageFilter.setProperty(pageDto.getPageFilterDto().getProperty());
				
			
			if(pageDto.getPageFilterDto().getStartTime() !=null){
				pageFilter.setStartTime(pageDto.getPageFilterDto().getStartTime());
			}
			
			if(pageDto.getPageFilterDto().getEndTime()!=null){
				pageFilter.setEndTime(pageDto.getPageFilterDto().getEndTime());
			}

			page.setPageFilter(pageFilter);
		}
		return page;
	}


private PageDto loadBy(Page page){
	
	PageDto pageDto=new PageDto();
	
	pageDto.setPageNumber(page.getPageNumber());
	pageDto.setPageSize(page.getPageSize());
	pageDto.setTotalCount(page.getTotalCount());
	pageDto.setPageCount(page.getPageCount());
	
	if(page.getOrder() != null){
		OrderDto orderDto=new OrderDto();
		orderDto.setProperty(page.getOrder().getProperty());
		orderDto.setDirection(page.getOrder().getDirection() != null ? com.froad.fft.bean.page.OrderDto.Direction.valueOf(page.getOrder().getDirection().toString()) : null);
		pageDto.setOrderDto(orderDto);
	}
	
	if(page.getPageFilter() != null){
		PageFilterDto pageFilterDto=new PageFilterDto();
		if(page.getPageFilter().getFilterEntity() != null){
			PresellDeliveryDto deliveryDto=BeanToDtoSupport.loadByPresellDeliveryDto((PresellDelivery)page.getPageFilter().getFilterEntity());
			pageFilterDto.setFilterEntity(deliveryDto);
		}
		pageFilterDto.setProperty(page.getPageFilter().getProperty());
		pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
		pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
		pageDto.setPageFilterDto(pageFilterDto);
	}
	
	List<PresellDelivery> list=page.getResultsContent();
	List<PresellDeliveryDto> dlist=new ArrayList();
	for(PresellDelivery presellDelivery:list){
		dlist.add(BeanToDtoSupport.loadByPresellDeliveryDto(presellDelivery));
	}
	pageDto.setResultsContent(dlist);
	
	return pageDto;
}

}
