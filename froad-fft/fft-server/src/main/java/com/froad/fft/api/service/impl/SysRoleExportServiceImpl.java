package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.froad.fft.api.service.SysRoleExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.SysRoleDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.SysRole;
import com.froad.fft.service.SysRoleService;

public class SysRoleExportServiceImpl implements SysRoleExportService{

	final static Logger logger = Logger.getLogger(SysRoleExportServiceImpl.class);
	
	@Resource(name="sysRoleServiceImpl")
	SysRoleService sysRoleService;
	@Override
	public Long saveSysRole(ClientAccessType clientAccessType,
			ClientVersion clientVersion, SysRoleDto sysRole)
			throws FroadException {
		SysRole sysrole = DtoToBeanSupport.loadBySysRole(sysRole);
		return sysRoleService.saveSysRole(sysrole);
	}

	@Override
	public SysRoleDto findSysRoleById(ClientAccessType clientAccessType,
			ClientVersion clientVersion, Long id) throws FroadException {
		SysRole sysRole  = sysRoleService.selectSysRoleById(id);
		return BeanToDtoSupport.loadBySysRoleDto(sysRole);
	}

	@Override
	public PageDto<SysRoleDto> findSysRoleByPage(ClientAccessType management,
			ClientVersion version10, PageDto<SysRoleDto> pageDto) {
		if(pageDto == null){
			logger.error("参数不能为空");
			return null;
		}
		Page page=loadBy(pageDto);
		PageDto returnPageDto=loadBy(sysRoleService.selectSysResourceByPage(page));
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
				SysRole sysRole=DtoToBeanSupport.loadBySysRole((SysRoleDto)pageDto.getPageFilterDto().getFilterEntity());
				pageFilter.setFilterEntity(sysRole);
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
				SysRoleDto sysRoleDto=BeanToDtoSupport.loadBySysRoleDto((SysRole)page.getPageFilter().getFilterEntity());
				pageFilterDto.setFilterEntity(sysRoleDto);
			}
			pageFilterDto.setProperty(page.getPageFilter().getProperty());
			pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
			pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
			pageDto.setPageFilterDto(pageFilterDto);
		}
		
		List<SysRole> listSysRole=page.getResultsContent();
		List<SysRoleDto> listSysRoleDto=new ArrayList();
		for(SysRole sysRole:listSysRole){
			listSysRoleDto.add(BeanToDtoSupport.loadBySysRoleDto(sysRole));
		}
		pageDto.setResultsContent(listSysRoleDto);
		
		return pageDto;
	}

	@Override
	public Boolean updateSysRole(ClientAccessType management,
			ClientVersion version10, SysRoleDto sysRoleDto) {
		SysRole sysRole = DtoToBeanSupport.loadBySysRole(sysRoleDto);
		return sysRoleService.updateSysRoleById(sysRole);
	}

}
