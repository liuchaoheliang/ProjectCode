package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;

import com.froad.fft.api.service.SysUserExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.SysResource;
import com.froad.fft.persistent.entity.SysUser;
import com.froad.fft.service.SysUserService;

public class SysUserExportServiceImpl implements SysUserExportService{
	
	final static Logger logger = Logger.getLogger(SysUserExportServiceImpl.class);
	@Resource(name="sysUserServiceImpl")
	private SysUserService sysUserService;

	@Override
	public Long saveSysUser(ClientAccessType clientAccessType,
			ClientVersion clientVersion, SysUserDto sysUserDto)
			throws FroadException {
		//明文密码加密 zjz
		String hexPassword = DigestUtils.md5Hex(sysUserDto.getPassword());
		sysUserDto.setPassword(hexPassword);
		return sysUserService.saveSysUser(DtoToBeanSupport.loadBySysUser(sysUserDto));
	}

	@Override
	public Boolean updateSysUserById(ClientAccessType clientAccessType,
			ClientVersion clientVersion, SysUserDto sysUserDto)
			throws FroadException {
		return sysUserService.updateSysUserById(DtoToBeanSupport.loadBySysUser(sysUserDto));
	}

	@Override
	public SysUserDto findSysUserById(ClientAccessType clientAccessType,
			ClientVersion clientVersion, Long id) throws FroadException {
		return BeanToDtoSupport.loadBySysUserDto(sysUserService.findSysUserById(id));
	}

	@Override
	public SysUserDto findSysUserByUsername(ClientAccessType clientAccessType,
			ClientVersion clientVersion, String username) throws FroadException {
		return BeanToDtoSupport.loadBySysUserDto(sysUserService.findSysUserByUsername(username));
	}

	@Override
	public List<SysResourceDto> findSysResourceListByUserId(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long id) throws FroadException {
		List<SysResourceDto> sysResourceDtoList=new ArrayList<SysResourceDto>();
		
		List<SysResource> SysResourceList=sysUserService.findSysResourceListByUserId(id);
		for(SysResource sysResource:SysResourceList){
			sysResourceDtoList.add(BeanToDtoSupport.loadBySysResourceDto(sysResource));
		}
		
		return sysResourceDtoList;
	}

	@Override
	public PageDto<SysUserDto> findSysUserByPage(PageDto<SysUserDto> pageDto) {

		if(pageDto == null){
			logger.error("参数不能为空");
			return null;
		}
		Page page=loadBy(pageDto);
		pageDto = loadBy(sysUserService.findSysUserByPage(page));
		return pageDto;
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
				SysUser sysUser=DtoToBeanSupport.loadBySysUser((SysUserDto)pageDto.getPageFilterDto().getFilterEntity());
				pageFilter.setFilterEntity(sysUser);
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
				SysUserDto sysUserDto=BeanToDtoSupport.loadBySysUserDto((SysUser)page.getPageFilter().getFilterEntity());
				pageFilterDto.setFilterEntity(sysUserDto);
			}
			pageFilterDto.setProperty(page.getPageFilter().getProperty());
			pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
			pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
			pageDto.setPageFilterDto(pageFilterDto);
		}
		
		List<SysUser> listSysUser=page.getResultsContent();
		List<SysUserDto> listSysUserDto=new ArrayList();
		for(SysUser sysUser:listSysUser){
			listSysUserDto.add(BeanToDtoSupport.loadBySysUserDto(sysUser));
		}
		pageDto.setResultsContent(listSysUserDto);
		
		return pageDto;
	}
}
