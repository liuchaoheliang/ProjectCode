package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.froad.fft.api.service.SysResourceExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.SysResource;
import com.froad.fft.service.SysResourceService;

public class SysResourceExportServiceImpl implements SysResourceExportService{

	final static Logger logger = Logger.getLogger(SysResourceExportServiceImpl.class);
	
	@Resource(name="sysResourceServiceImpl")
	private SysResourceService sysResourceService;
	
	@Override
	public Long addSysResource(ClientAccessType clientAccessType,ClientVersion clientVersion,SysResourceDto sysResourceDto)throws FroadException {
		
		if(sysResourceDto == null){
			logger.error("参数不能为空");
			return null;
		}
		
		return sysResourceService.saveSysResource(DtoToBeanSupport.loadBySysResource(sysResourceDto));
	}


	@Override
	public SysResourceDto findSysResourceById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id) throws FroadException {
		
		if(id == null){
			logger.error("参数不能为空");
			return null;
		}
		
		SysResource sysResource=sysResourceService.findSysResourceById(id);
		if(sysResource != null){
			return BeanToDtoSupport.loadBySysResourceDto(sysResource);
		}
		return null;
	}
	
	@Override
	public List<SysResourceDto> findAllSysResource(ClientAccessType clientAccessType,
			ClientVersion clientVersion) throws FroadException {
		List<SysResourceDto> sysResourceDtoList=new ArrayList<SysResourceDto>();
		
		List<SysResource> sysResourceList=sysResourceService.findAllSysResource();
		
		for(SysResource sysResource:sysResourceList){
			sysResourceDtoList.add(BeanToDtoSupport.loadBySysResourceDto(sysResource));
		}
		
		return sysResourceDtoList;
	}
	
	@Override
	public List<SysResourceDto> findRootSysResourceList(ClientAccessType clientAccessType,
			ClientVersion clientVersion) {
		List<SysResourceDto> sysResourceDtoList=new ArrayList<SysResourceDto>();
		
		List<SysResource> sysResourceList=sysResourceService.findRootSysResourceList();
		
		for(SysResource sysResource:sysResourceList){
			sysResourceDtoList.add(BeanToDtoSupport.loadBySysResourceDto(sysResource));
		}
		
		return sysResourceDtoList;
	}


	@Override
	public List<SysResourceDto> findSysResourceByClientId(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long clientId)throws FroadException {
		if(clientId == null){
			logger.error("参数不能为空");
			return null;
		}
		
		List<SysResourceDto> sysResourceDtoList=new ArrayList<SysResourceDto>();
		
		List<SysResource> sysResourceList=sysResourceService.findSysResourceByClientId(clientId);
		
		for(SysResource sysResource:sysResourceList){
			sysResourceDtoList.add(BeanToDtoSupport.loadBySysResourceDto(sysResource));
		}
		
		return sysResourceDtoList;
	}

	@Override
	public PageDto findSysResourceByPage(ClientAccessType clientAccessType,
			ClientVersion clientVersion, PageDto pageDto)throws FroadException {
		
		if(pageDto == null){
			logger.error("参数不能为空");
			return null;
		}
		Page page=loadBy(pageDto);
		PageDto returnPageDto=loadBy(sysResourceService.findSysResourceByPage(page));
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
				SysResource sysResource=DtoToBeanSupport.loadBySysResource((SysResourceDto)pageDto.getPageFilterDto().getFilterEntity());
				pageFilter.setFilterEntity(sysResource);
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
				SysResourceDto sysResourceDto=BeanToDtoSupport.loadBySysResourceDto((SysResource)page.getPageFilter().getFilterEntity());
				pageFilterDto.setFilterEntity(sysResourceDto);
			}
			pageFilterDto.setProperty(page.getPageFilter().getProperty());
			pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
			pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
			pageDto.setPageFilterDto(pageFilterDto);
		}
		
		List<SysResource> listSysResource=page.getResultsContent();
		List<SysResourceDto> listSysResourceDto=new ArrayList();
		for(SysResource sysResource:listSysResource){
			listSysResourceDto.add(BeanToDtoSupport.loadBySysResourceDto(sysResource));
		}
		pageDto.setResultsContent(listSysResourceDto);
		
		return pageDto;
	}


	@Override
	public Boolean updateSysResource(ClientAccessType management,
			ClientVersion version10, SysResourceDto sysResourceDto) {
		SysResource sysResource  = DtoToBeanSupport.loadBySysResource(sysResourceDto);
		return sysResourceService.updateSysResourceById(sysResource);
	}
}
