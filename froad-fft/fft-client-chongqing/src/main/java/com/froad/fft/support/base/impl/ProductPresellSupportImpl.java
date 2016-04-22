package com.froad.fft.support.base.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.froad.fft.api.service.ProductPresellDeliveryExportService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.AreaExportService;
import com.froad.fft.api.service.BusinessCircleExportService;
import com.froad.fft.api.service.PresellDeliveryExportService;
import com.froad.fft.api.service.ProductExportService;
import com.froad.fft.api.service.ProductPresellExportService;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.common.Constants;
import com.froad.fft.dto.AreaDto;
import com.froad.fft.dto.BusinessCircleDto;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.ProductPresellDeliveryDto;
import com.froad.fft.dto.ProductPresellDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.ProductPresellSupport;
import com.froad.fft.util.NullValueCheckUtil;

@Service
public class ProductPresellSupportImpl implements ProductPresellSupport {

	private static Logger logger = Logger.getLogger(ProductPresellSupportImpl.class);
	
	@Resource(name = "productPresellService")
	private ProductPresellExportService productPresellService;
	
	@Resource(name = "productPresellDeliveryService")
	private ProductPresellDeliveryExportService productPresellDeliveryService;
	
	@Resource(name = "presellDeliveryService")
	private PresellDeliveryExportService presellDeliveryService;
	
	@Resource(name = "productService")
	private ProductExportService productService;
	
	@Resource(name = "businessCircleService")
	private BusinessCircleExportService businessCircleService;
	
	@Resource(name = "areaService")
	private AreaExportService areaService;
	
	private final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Override
	public ProductPresellDto findProductPresellById(Long id) {
		return productPresellService.findProductPresellById(clientAccessType, ClientVersion.version_1_0, id);
	}

	public List<PresellDeliveryDto> getProductPresellDeliveryByProductId(Long productId){
		//获取其下所有自提点中间表信息
		List<ProductPresellDeliveryDto> ProductPresellDeliveryDtos = productPresellDeliveryService.selectByProductPresellId(clientAccessType, ClientVersion.version_1_0, productId);
		if(NullValueCheckUtil.isListArrayEmpty(ProductPresellDeliveryDtos)){
			return new ArrayList<PresellDeliveryDto>();
		}
		List<PresellDeliveryDto> presellDeliveryDtos = new ArrayList<PresellDeliveryDto>();
		for (ProductPresellDeliveryDto productPresellDeliveryDto : ProductPresellDeliveryDtos) {
			presellDeliveryDtos.add(presellDeliveryService.findPresellDeliveryById(clientAccessType, ClientVersion.version_1_0, productPresellDeliveryDto.getPresellDeliveryId()));
		}
		return presellDeliveryDtos;
	}

	@Override
	public List<ProductDto> getProductByCondition(ProductDto productDto) {
		return productService.findProductByCondition(clientAccessType, ClientVersion.version_1_0, productDto);
	}

	@Override
	public BusinessCircleDto getBusinessCircleDtosById(Long id) {
		return businessCircleService.findBusinessCircleById(clientAccessType, ClientVersion.version_1_0, id);
	}
	
	@Override
	public PresellDeliveryDto getProductPresellDeliveryById(Long id){
		return presellDeliveryService.findPresellDeliveryById(clientAccessType, ClientVersion.version_1_0, id);
	}
	
	@Override
	public AreaDto getAreaById(Long id){
		return areaService.findAreaById(clientAccessType, ClientVersion.version_1_0, id);
	}

	@Override
	public ProductDto getProductDtoById(Long id) {
		return productService.getProductById(clientAccessType, ClientVersion.version_1_0, id);
	}

	@Override
	public List<ProductDto> getProductByPresell(ProductDto productDto) {
		PageDto<ProductDto> pageDto = new PageDto<ProductDto>();
		pageDto.setPageSize(Constants.INDEX_PRESELL_NUM);//首页显示数量
		OrderDto orderDto = new OrderDto();
		//预售商品语句ID倒序，显示
		orderDto.setDirection(OrderDto.Direction.desc);
		orderDto.setProperty("id");
		pageDto.setOrderDto(orderDto);
		//设置过滤条件
		PageFilterDto<ProductDto> pageFilterDto = new PageFilterDto<ProductDto>();
		pageFilterDto.setFilterEntity(productDto);
		pageDto.setPageFilterDto(pageFilterDto);
		//查询商品
		List<ProductDto> productDtos  = productService.findProductByPage(clientAccessType, ClientVersion.version_1_0, pageDto).getResultsContent();
		for (ProductDto product : productDtos) {
			//商品预售ID，查询商品预售
			ProductPresellDto productPresellDto =  productPresellService.findProductPresellById(clientAccessType, ClientVersion.version_1_0, product.getProductPresellId());
			product.setProductPresell(productPresellDto);
		}
		logger.info("预售商品查询结果数:"+(productDtos!=null?productDtos.size():0));
		return productDtos;
	}

	@Override
	public ProductPresellDto getPresellByProductId(Long productId) {
		ProductDto productDto = productService.getProductById(clientAccessType, ClientVersion.version_1_0,productId);
		return 	productPresellService.findProductPresellById(clientAccessType, ClientVersion.version_1_0, productDto.getProductPresellId());
	}
}
