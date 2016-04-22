package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.froad.fft.dto.ProductRestrictRuleDto;
import com.froad.fft.persistent.entity.ProductRestrictRule;
import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import com.froad.fft.api.service.ProductExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Product;
import com.froad.fft.service.ProductService;

public class ProductExportServiceImpl implements ProductExportService
{

    final static Logger logger = Logger.getLogger(ProductExportServiceImpl.class);

    @Resource(name = "productServiceImpl")
    private ProductService productService;

    @Override
    public Long addProduct(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductDto productDto) throws FroadException
    {
        Product product = new Product();
        product = DtoToBeanSupport.loadByProduct(productDto);
        return productService.saveProduct(product);
    }

    @Override
    public PageDto<ProductDto> findProductByPage(ClientAccessType management, ClientVersion version10, PageDto<ProductDto> pageDto) throws FroadException
    {
        if (pageDto == null)
        {
            logger.error("参数不能为空");
            return null;
        }
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(productService.findProductByPage(page));
        return returnPageDto;
    }

    private Page loadBy(PageDto pageDto)
    {

        Page page = new Page();

        page.setPageNumber(pageDto.getPageNumber());
        page.setPageSize(pageDto.getPageSize());

        //排序
        if (pageDto.getOrderDto() != null)
        {
            Order order = new Order();
            order.setProperty(pageDto.getOrderDto().getProperty());
            order.setDirection(pageDto.getOrderDto().getDirection() != null ? com.froad.fft.persistent.bean.page.Order.Direction.valueOf(pageDto.getOrderDto().getDirection().toString()) : null);
            page.setOrder(order);
        }

        //过滤条件
        if (pageDto.getPageFilterDto() != null)
        {
            PageFilter pageFilter = new PageFilter();
            if (pageDto.getPageFilterDto().getFilterEntity() != null)
            {
                Product product = DtoToBeanSupport.loadByProduct((ProductDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(product);
            }
            pageFilter.setProperty(pageDto.getPageFilterDto().getProperty());

            SimpleDateFormat sdf_1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat sdf_2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


            if (pageDto.getPageFilterDto().getStartTime() != null)
            {
                String start = sdf_1.format(pageDto.getPageFilterDto().getStartTime()) + " 00:00:00";
                try
                {
                    pageFilter.setStartTime(sdf_2.parse(start));
                }
                catch (ParseException e)
                {

                }
            }

            if (pageDto.getPageFilterDto().getEndTime() != null)
            {
                String end = sdf_1.format(pageDto.getPageFilterDto().getEndTime()) + " 23:59:59";
                try
                {
                    pageFilter.setEndTime(sdf_2.parse(end));
                }
                catch (ParseException e)
                {

                }
            }
            //			pageFilter.setStartTime(pageDto.getPageFilterDto().getStartTime());
            //			pageFilter.setEndTime(pageDto.getPageFilterDto().getEndTime());
            page.setPageFilter(pageFilter);
        }
        return page;
    }

    private PageDto loadBy(Page page)
    {

        PageDto pageDto = new PageDto();

        pageDto.setPageNumber(page.getPageNumber());
        pageDto.setPageSize(page.getPageSize());
        pageDto.setTotalCount(page.getTotalCount());
        pageDto.setPageCount(page.getPageCount());

        if (page.getOrder() != null)
        {
            OrderDto orderDto = new OrderDto();
            orderDto.setProperty(page.getOrder().getProperty());
            orderDto.setDirection(page.getOrder().getDirection() != null ? com.froad.fft.bean.page.OrderDto.Direction.valueOf(page.getOrder().getDirection().toString()) : null);
            pageDto.setOrderDto(orderDto);
        }

        if (page.getPageFilter() != null)
        {
            PageFilterDto pageFilterDto = new PageFilterDto();
            if (page.getPageFilter().getFilterEntity() != null)
            {
                ProductDto productDto = BeanToDtoSupport.loadByProductDto((Product) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(productDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<Product> listProduct = page.getResultsContent();
        List<ProductDto> listProductDto = new ArrayList();
        for (Product product : listProduct)
        {
            listProductDto.add(BeanToDtoSupport.loadByProductDto(product));
        }
        pageDto.setResultsContent(listProductDto);

        return pageDto;
    }

    @Override
    public ProductDto getProductById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        Product product = productService.selectProductById(id);
        ProductDto productDto = BeanToDtoSupport.loadByProductDto(product);
        return productDto;
    }

    @Override
    public Boolean updateProduct(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductDto productDto) throws FroadException
    {
        Product product = new Product();
        product = DtoToBeanSupport.loadByProduct(productDto);
        return productService.updateProductById(product);
    }

    @Override
    public List<ProductDto> findProductByCondition(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductDto productDto) throws FroadException
    {
        Product product = new Product();
        product = DtoToBeanSupport.loadByProduct(productDto);  // dto ==> entityproductService.selectProductByCondition(product)
        return BeanToDtoSupport.loadByProductDtos(productService.selectProductByCondition(product));
    }

    public List<ProductDto> findAllUnRestrictProduct(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductRestrictRuleDto dto) throws FroadException
    {
        ProductRestrictRule productRestrictRule = DtoToBeanSupport.loadByProductRestrictRule(dto);
        List<Product> list = productService.findAllUnRestrictProduct(productRestrictRule);
        List<ProductDto> result = new ArrayList<ProductDto>();
        for (Product product : list)
        {
            ProductDto _dto = BeanToDtoSupport.loadByProductDto(product);
            result.add(_dto);
        }
        return result;
    }

    public List<ProductDto> findAllRestrictProduct(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductRestrictRuleDto dto) throws FroadException
      {
          ProductRestrictRule productRestrictRule = DtoToBeanSupport.loadByProductRestrictRule(dto);
          List<Product> list = productService.findAllRestrictProduct(productRestrictRule);
          List<ProductDto> result = new ArrayList<ProductDto>();
          for (Product product : list)
          {
              ProductDto _dto = BeanToDtoSupport.loadByProductDto(product);
              result.add(_dto);
          }
          return result;
      }


    public List<ProductDto> findAllProductHaveStockPile(ClientAccessType clientAccessType, ClientVersion clientVersion) throws FroadException
       {
           List<Product> list = productService.findAllProductHaveStockPile();
           List<ProductDto> result = new ArrayList<ProductDto>();
           for (Product product : list)
           {
               ProductDto _dto = BeanToDtoSupport.loadByProductDto(product);
               result.add(_dto);
           }
           return result;
       }
}
