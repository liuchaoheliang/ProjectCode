/**
 * 文件名称:ProductGroupExportServiceImpl.java
 * 文件描述: 团购对外接口实现
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-4-11
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.ProductGroupExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.ProductGroupDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.ProductGroup;
import com.froad.fft.service.ProductGroupService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class ProductGroupExportServiceImpl implements ProductGroupExportService
{
    @Resource(name = "productGroupServiceImpl")
    private ProductGroupService productGroupService;

    public Long saveProductGroup(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductGroupDto dto) throws FroadException
    {
        ProductGroup temp = DtoToBeanSupport.loadByProductGroup(dto);
        return productGroupService.saveProductGroup(temp);
    }

    public Boolean updateProductGroupById(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductGroupDto dto) throws FroadException
    {
        ProductGroup temp = DtoToBeanSupport.loadByProductGroup(dto);
        return productGroupService.updateProductGroupById(temp);
    }

    public ProductGroupDto findProductGroupById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        ProductGroup temp = productGroupService.selectProductGroupById(id);
        ProductGroupDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadByProductGroupDto(temp);
        }
        return result;
    }

    public PageDto<ProductGroupDto> findProductGroupByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<ProductGroupDto> pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(productGroupService.findProductGroupByPage(page));
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
                ProductGroup group = DtoToBeanSupport.loadByProductGroup((ProductGroupDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(group);
            }
            pageFilter.setProperty(pageDto.getPageFilterDto().getProperty());


            if (pageDto.getPageFilterDto().getStartTime() != null)
            {
                pageFilter.setStartTime(pageDto.getPageFilterDto().getStartTime());
            }

            if (pageDto.getPageFilterDto().getEndTime() != null)
            {
                pageFilter.setEndTime(pageDto.getPageFilterDto().getEndTime());
            }

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
                ProductGroupDto dto = BeanToDtoSupport.loadByProductGroupDto((ProductGroup) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(dto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<ProductGroup> list = page.getResultsContent();
        List<ProductGroupDto> dlist = new ArrayList();
        for (ProductGroup group : list)
        {
            dlist.add(BeanToDtoSupport.loadByProductGroupDto(group));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }


}
