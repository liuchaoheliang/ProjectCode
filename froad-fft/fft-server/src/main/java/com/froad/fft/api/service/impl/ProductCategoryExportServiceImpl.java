package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import com.froad.fft.api.service.ProductCategoryExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ProductCategoryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.ProductCategory;
import com.froad.fft.service.ProductCategoryService;

public class ProductCategoryExportServiceImpl implements
		ProductCategoryExportService {
	final static Logger logger = Logger.getLogger(ProductCategoryExportServiceImpl.class);
	@Resource(name = "productCategoryServiceImpl")
	ProductCategoryService productCategoryService;
	
	@Override
	public Long addProductCategory(ClientAccessType clientAccessType,
			ClientVersion clientVersion, ProductCategoryDto productCategoryDto)
			throws FroadException {
		ProductCategory productCategory = DtoToBeanSupport.loadByProductCategory(productCategoryDto);
		return productCategoryService.saveProductCategory(productCategory);
	}

	@Override
	public PageDto<ProductCategoryDto> findProductCategoryByPage(
			ClientAccessType management, ClientVersion version10,
			PageDto<ProductCategoryDto> pageDto) {
		if(pageDto == null){
			logger.error("参数不能为空");
			return null;
		}
		Page page=loadBy(pageDto);
		PageDto returnPageDto=loadBy(productCategoryService.findProductCategoryByPage(page));
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
				ProductCategory productCategory=DtoToBeanSupport.loadByProductCategory((ProductCategoryDto)pageDto.getPageFilterDto().getFilterEntity());
				pageFilter.setFilterEntity(productCategory);
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
				ProductCategoryDto logDto=BeanToDtoSupport.loadByProductCategoryDto((ProductCategory)page.getPageFilter().getFilterEntity());
				pageFilterDto.setFilterEntity(logDto);
			}
			pageFilterDto.setProperty(page.getPageFilter().getProperty());
			pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
			pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
			pageDto.setPageFilterDto(pageFilterDto);
		}
		
		List<ProductCategory> listProductCategory=page.getResultsContent();
		List<ProductCategoryDto> listProductCategoryDto=new ArrayList();
		for(ProductCategory productCategory:listProductCategory){
			listProductCategoryDto.add(BeanToDtoSupport.loadByProductCategoryDto(productCategory));
		}
		pageDto.setResultsContent(listProductCategoryDto);
		
		return pageDto;
	}

	@Override
	public ProductCategoryDto getProductCategoryById(ClientAccessType clientAccessType,ClientVersion clientVersion,Long id) {
//		ProductCategory productCategory = productCategoryService.getProductCategoryById(id);
		ProductCategory productCategory = productCategoryService.selectProductCategoryById(id);
		ProductCategoryDto productCategoryDto = BeanToDtoSupport.loadByProductCategoryDto(productCategory);
		return productCategoryDto;
	}

	@Override
	public Boolean updateProductCategoryById(ClientAccessType clientAccessType,ClientVersion clientVersion,
			ProductCategoryDto productCategoryDto) {
		ProductCategory productCategory =DtoToBeanSupport.loadByProductCategory(productCategoryDto);
		return productCategoryService.updateProductCategoryById(productCategory);
	}

}
