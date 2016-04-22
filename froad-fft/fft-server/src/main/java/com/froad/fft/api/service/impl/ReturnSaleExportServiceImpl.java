package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.api.service.ReturnSaleExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ReturnSaleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.ReturnSale;
import com.froad.fft.service.ReturnSaleService;

/**
* @ClassName: ReturnSaleExportServiceImpl
* @Description: TODO
* @author larry
* @date 2014-4-3 下午04:32:01
*
 */
public class ReturnSaleExportServiceImpl implements ReturnSaleExportService {

	@Resource(name = "returnSaleServiceImpl")
	ReturnSaleService returnSaleService;
	@Override
	public PageDto<ReturnSaleDto> findReturnSaleByPage(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			PageDto<ReturnSaleDto> pageDto) {
		Page page=loadBy(pageDto);
		PageDto returnPageDto=loadBy(returnSaleService.findReturnSaleByPage(page));
		return returnPageDto;
	}

	@Override
	public ReturnSaleDto getReturnSaleById(ClientAccessType clientAccessType,
			ClientVersion clientVersion, Long id) {
		ReturnSaleDto  returnSaleDto = BeanToDtoSupport.loadByReturnSaleDto(returnSaleService.getReturnSaleById(id));
		return returnSaleDto;
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
				ReturnSale returnSale=DtoToBeanSupport.loadByReturnSale((ReturnSaleDto)pageDto.getPageFilterDto().getFilterEntity());
				pageFilter.setFilterEntity(returnSale);
			}
			pageFilter.setProperty(pageDto.getPageFilterDto().getProperty());
			
			SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			
			
			if(pageDto.getPageFilterDto().getStartTime() != null){
				String start=sdf_1.format(pageDto.getPageFilterDto().getStartTime())+" 00:00:00";
				try {
					pageFilter.setStartTime(sdf_2.parse(start));
				} catch (ParseException e) {
					
				}
			}
			
			if(pageDto.getPageFilterDto().getEndTime() != null){
				String end=sdf_1.format(pageDto.getPageFilterDto().getEndTime())+" 23:59:59";
				try {
					pageFilter.setEndTime(sdf_2.parse(end));
				} catch (ParseException e) {
					
				}
			}
//			pageFilter.setStartTime(pageDto.getPageFilterDto().getStartTime());
//			pageFilter.setEndTime(pageDto.getPageFilterDto().getEndTime());
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
				ReturnSaleDto returnSaleDto=BeanToDtoSupport.loadByReturnSaleDto((ReturnSale)page.getPageFilter().getFilterEntity());
				pageFilterDto.setFilterEntity(returnSaleDto);
			}
			pageFilterDto.setProperty(page.getPageFilter().getProperty());
			pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
			pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
			pageDto.setPageFilterDto(pageFilterDto);
		}
		
		List<ReturnSale> listReturnSale=page.getResultsContent();
		List<ReturnSaleDto> listReturnSaleDto=new ArrayList();
		for(ReturnSale returnSale:listReturnSale){
			listReturnSaleDto.add(BeanToDtoSupport.loadByReturnSaleDto(returnSale));
		}
		pageDto.setResultsContent(listReturnSaleDto);
		
		return pageDto;
	}



@Override
public Long saveReturnSaleDto(ClientAccessType clientAccessType,
		ClientVersion clientVersion, ReturnSaleDto returnSaleDto)
		throws FroadException {		
		return returnSaleService.saveReturnSale(DtoToBeanSupport.loadByReturnSale(returnSaleDto));
}



@Override
public List<ReturnSaleDto> getByConditions(ClientAccessType clientAccessType,
		ClientVersion clientVersion, ReturnSaleDto returnSaleDto)
		throws FroadException {
		List<ReturnSale> listReturnSale=returnSaleService.selectByConditions(DtoToBeanSupport.loadByReturnSale(returnSaleDto));
		List<ReturnSaleDto> listReturnSaleDto=new ArrayList();
		for(ReturnSale returnSale:listReturnSale){
			listReturnSaleDto.add(BeanToDtoSupport.loadByReturnSaleDto(returnSale));
		}
	return listReturnSaleDto;
}

}
