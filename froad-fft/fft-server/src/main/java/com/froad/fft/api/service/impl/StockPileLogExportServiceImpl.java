
	 /**
  * 文件名：StockPileLogExportServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年3月28日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.api.service.StockPileLogExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.dto.StockPileLogDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.StockPile;
import com.froad.fft.persistent.entity.StockPileLog;
import com.froad.fft.service.StockPileLogService;
import com.froad.fft.service.StockPileService;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年3月28日 下午7:36:12 
 */
public class StockPileLogExportServiceImpl implements StockPileLogExportService{
		@Resource(name="stockPileLogServiceImpl")
		StockPileLogService stockPileLogService;
		
		@Override
		public Long addStockPileLog(ClientAccessType clientAccessType,ClientVersion clientVersion,StockPileLogDto stockPileLogDto)throws FroadException {
			
			return stockPileLogService.saveStockPileLog(DtoToBeanSupport.loadByStockPileLog(stockPileLogDto));
		}

		

		@Override
		public PageDto findStockPileLogByPage(ClientAccessType clientAccessType,ClientVersion clientVersion,PageDto pageDto)throws FroadException {
			Page page=loadBy(pageDto);
			PageDto returnPageDto=loadBy(stockPileLogService.findStockPileLogByPage(page));
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
					StockPileLog stockPileLog=DtoToBeanSupport.loadByStockPileLog((StockPileLogDto)pageDto.getPageFilterDto().getFilterEntity());
					pageFilter.setFilterEntity(stockPileLog);
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
					StockPileLogDto stockPileLogDto=BeanToDtoSupport.loadByStockPileLogDto((StockPileLog)page.getPageFilter().getFilterEntity());
					pageFilterDto.setFilterEntity(stockPileLogDto);
				}
				pageFilterDto.setProperty(page.getPageFilter().getProperty());
				pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
				pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
				pageDto.setPageFilterDto(pageFilterDto);
			}
			
			List<StockPileLog> list=page.getResultsContent();
			List<StockPileLogDto> dlist=new ArrayList();
			for(StockPileLog stockPileLog:list){
				dlist.add(BeanToDtoSupport.loadByStockPileLogDto(stockPileLog));
			}
			pageDto.setResultsContent(dlist);
			
			return pageDto;
		}

}
