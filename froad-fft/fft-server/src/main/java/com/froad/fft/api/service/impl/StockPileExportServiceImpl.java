
	 /**
  * 文件名：StockPileExportServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.api.service.StockPileExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.persistent.entity.StockPile;
import com.froad.fft.service.StockPileService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午7:37:07 
 */
public class StockPileExportServiceImpl implements StockPileExportService{
		@Resource(name="stockPileServiceImpl")
		StockPileService stockPileService;
		
		@Override
		public Long addStockPile(ClientAccessType clientAccessType,ClientVersion clientVersion,StockPileDto stockPileDto)throws FroadException {	
			
			return stockPileService.saveStockPile(DtoToBeanSupport.loadByStockPile(stockPileDto));
		}

		
		@Override
		public Boolean updateStockPileById(ClientAccessType clientAccessType,ClientVersion clientVersion,StockPileDto stockPileDto)throws FroadException {
			return stockPileService.updateStockPileById(DtoToBeanSupport.loadByStockPile(stockPileDto));
		}

		
		@Override
		public StockPileDto findStockPileById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id)throws FroadException {
			return BeanToDtoSupport.loadByStockPileDto(stockPileService.findStockPileById(id));
		}

		
		@Override
		public PageDto findStockPilebyPage(ClientAccessType clientAccessType,ClientVersion clientVersion,PageDto pageDto)throws FroadException {
			Page page=loadBy(pageDto);
			PageDto returnPageDto=loadBy(stockPileService.findStockPileByPage(page));
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
					StockPile stockPile=DtoToBeanSupport.loadByStockPile((StockPileDto)pageDto.getPageFilterDto().getFilterEntity());
					pageFilter.setFilterEntity(stockPile);
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
					StockPileDto stockPileDto=BeanToDtoSupport.loadByStockPileDto((StockPile)page.getPageFilter().getFilterEntity());
					pageFilterDto.setFilterEntity(stockPileDto);
				}
				pageFilterDto.setProperty(page.getPageFilter().getProperty());
				pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
				pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
				pageDto.setPageFilterDto(pageFilterDto);
			}
			
			List<StockPile> list=page.getResultsContent();
			List<StockPileDto> dlist=new ArrayList();
			for(StockPile stockPile:list){
				dlist.add(BeanToDtoSupport.loadByStockPileDto(stockPile));
			}
			pageDto.setResultsContent(dlist);
			
			return pageDto;
		}


	

	@Override
	public List<StockPileDto> selectByConditions(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			StockPileDto stockPileDto) throws FroadException {
			List<StockPile> list=stockPileService.selectByCondtions(DtoToBeanSupport.loadByStockPile(stockPileDto));
			List<StockPileDto> dlist=new ArrayList();
			for(StockPile stockPile:list){
				dlist.add(BeanToDtoSupport.loadByStockPileDto(stockPile));
			}
			return dlist;
	}
}
