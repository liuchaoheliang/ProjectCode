package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import com.froad.fft.api.service.SmsContentExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.SmsContentDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.SmsContent;
import com.froad.fft.service.SmsContentService;

public class SmsContentExportServiceImpl implements SmsContentExportService {
	
	final static Logger logger = Logger.getLogger(SmsContentExportServiceImpl.class);
	
	@Resource(name = "smsContentServiceImpl")
	SmsContentService smsContentService;
	@Override
	public Long addSmsContent(ClientAccessType clientAccessType,
			ClientVersion clientVersion, SmsContentDto smsContentDto)
			throws FroadException {
		SmsContent smsContent = DtoToBeanSupport.loadBySmsContent(smsContentDto);
		return smsContentService.saveSmsContent(smsContent);
	}

	@Override
	public PageDto findSmsContentByPage(ClientAccessType management,
			ClientVersion version10, PageDto<SmsContentDto> pageDto)throws FroadException {
		if(pageDto == null){
			logger.error("参数不能为空");
			return null;
		}
		Page page=loadBy(pageDto);
		PageDto returnPageDto=loadBy(smsContentService.findSmsContentByPage(page));
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
				SmsContent smsContent=DtoToBeanSupport.loadBySmsContent((SmsContentDto)pageDto.getPageFilterDto().getFilterEntity());
				pageFilter.setFilterEntity(smsContent);
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
				SmsContentDto smsContent=BeanToDtoSupport.loadBySmsContentDto((SmsContent)page.getPageFilter().getFilterEntity());
				pageFilterDto.setFilterEntity(smsContent);
			}
			pageFilterDto.setProperty(page.getPageFilter().getProperty());
			pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
			pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
			pageDto.setPageFilterDto(pageFilterDto);
		}
		
		List<SmsContent> listSmsContent=page.getResultsContent();
		List<SmsContentDto> listSmsContentDto=new ArrayList();
		for(SmsContent smsContent:listSmsContent){
			listSmsContentDto.add(BeanToDtoSupport.loadBySmsContentDto(smsContent));
		}
		pageDto.setResultsContent(listSmsContentDto);
		
		return pageDto;
	}

	@Override
	public SmsContentDto getSmsContentById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id) {
		SmsContent smsContent  = smsContentService.selectSmsContentById(id);
		SmsContentDto smsContentDto = BeanToDtoSupport.loadBySmsContentDto(smsContent);
		return smsContentDto;
	}

	@Override
	public Boolean updateSmsContent(ClientAccessType management,
			ClientVersion version10, SmsContentDto smsContentDto)throws FroadException {
		SmsContent smsContent =DtoToBeanSupport.loadBySmsContent(smsContentDto);
		return smsContentService.updateSmsContentById(smsContent);
	}

}
