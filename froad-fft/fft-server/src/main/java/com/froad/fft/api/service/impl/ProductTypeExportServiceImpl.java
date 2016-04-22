package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import com.froad.fft.api.service.ProductTypeExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ProductTypeDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.ProductType;
import com.froad.fft.service.ProductTypeService;

public class ProductTypeExportServiceImpl implements ProductTypeExportService {

	final static Logger logger = Logger.getLogger(ProductTypeExportServiceImpl.class);
	
	@Resource(name = "productTypeServiceImpl")
	ProductTypeService productTypeService;
	@Override
	public Long addProductType(ClientAccessType clientAccessType,
			ClientVersion clientVersion, ProductTypeDto productTypeDto)
			throws FroadException {
		ProductType productType = DtoToBeanSupport.loadByProductType(productTypeDto);
		return productTypeService.saveProductType(productType);
	}

	@Override
	public PageDto<ProductTypeDto> findProductTypeByPage(
			ClientAccessType management, ClientVersion version10,
			PageDto<ProductTypeDto> pageDto) {
		if(pageDto == null){
			logger.error("参数不能为空");
			return null;
		}
		Page page=loadBy(pageDto);
		PageDto returnPageDto=loadBy(productTypeService.findProductTypeByPage(page));
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
				ProductType productType=DtoToBeanSupport.loadByProductType((ProductTypeDto)pageDto.getPageFilterDto().getFilterEntity());
				pageFilter.setFilterEntity(productType);
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
				ProductTypeDto productTypeDto=BeanToDtoSupport.loadByProductTypeDto((ProductType)page.getPageFilter().getFilterEntity());
				pageFilterDto.setFilterEntity(productTypeDto);
			}
			pageFilterDto.setProperty(page.getPageFilter().getProperty());
			pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
			pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
			pageDto.setPageFilterDto(pageFilterDto);
		}
		
		List<ProductType> listProductType=page.getResultsContent();
		List<ProductTypeDto> listProductTypeDto=new ArrayList();
		for(ProductType productType:listProductType){
			listProductTypeDto.add(BeanToDtoSupport.loadByProductTypeDto(productType));
		}
		pageDto.setResultsContent(listProductTypeDto);
		
		return pageDto;
	}

	@Override
	public ProductTypeDto getProductTypeById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id) {
		ProductType productType = productTypeService.selectProductTypeById(id);
		ProductTypeDto productTypeDto = BeanToDtoSupport.loadByProductTypeDto(productType);
		return productTypeDto;
	}

	@Override
	public Boolean updateProductType(ClientAccessType clientAccessType,ClientVersion clientVersion,ProductTypeDto productTypeDto) {
		ProductType productType =DtoToBeanSupport.loadByProductType(productTypeDto);
		return productTypeService.updateProductTypeById(productType);
	}

}
