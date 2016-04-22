/**
 * 文件名称:ProductPresellExportServiceImpl.java
 * 文件描述: 预售实体
 * 产品标识: fft
 * 单元描述: fft-server
 * 编写人: houguoquan_Aides
 * 编写时间: 14-3-27
 * 历史修改:  
 */
package com.froad.fft.api.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.froad.fft.api.service.ProductPresellExportService;
import com.froad.fft.api.support.*;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.*;
import com.froad.fft.enums.*;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.PresellTrans;
import com.froad.fft.persistent.entity.ProductPresell;
import com.froad.fft.persistent.entity.Refunds;
import com.froad.fft.service.ProductPresellService;

import javax.annotation.Resource;

/**
 * @author houguoquan_Aides
 * @version 1.0
 */
public class ProductPresellExportServiceImpl implements ProductPresellExportService
{
    @Resource(name = "productPresellServiceImpl")
    private ProductPresellService productPresellService;

    public Long saveProductPresell(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductPresellDto dto) throws FroadException
    {
        ProductPresell temp = DtoToBeanSupport.loadByProductPresell(dto);
        return productPresellService.saveProductPresell(temp);
    }

    public Boolean updateProductPresellById(ClientAccessType clientAccessType, ClientVersion clientVersion, ProductPresellDto dto) throws FroadException
    {
        ProductPresell temp = DtoToBeanSupport.loadByProductPresell(dto);
        return productPresellService.updateProductPresellById(temp);
    }

    public ProductPresellDto findProductPresellById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        ProductPresell temp = productPresellService.selectProductPresellById(id);
        ProductPresellDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadByProductPresellDto(temp);
        }
        return result;
    }

    public PageDto<ProductPresellDto> findProductPresellByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<ProductPresellDto> pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(productPresellService.findProductPresellByPage(page));
        return returnPageDto;
    }

    public List<Map> findPresellTransByProductId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long productId)
    {
        return productPresellService.findPresellTransByProductId(productId);
    }

    /**
     * 根据商品Id查询预售交易列表统计
     *
     * @param productId 商品Id 而非预售Id
     * @return
     */
    public List<Map> findPresellTransStatisticByProductId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long productId)
    {
        List<PresellTrans> tempList = productPresellService.findPresellTransStatisticByProductId(productId);
        List<Map> result = new ArrayList<Map>();
        for (PresellTrans trans : tempList)
        {
            Map _temp = new HashMap<String, Object>();
            _temp.put("id", trans.getId().toString());
            _temp.put("name", trans.getName());
            _temp.put("quantity", trans.getQuantity().toString());
            result.add(_temp);
        }
        return result;
    }

    public ProductPresellDto findByProductId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {
        ProductPresell temp = productPresellService.selectByProductId(id);
        ProductPresellDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadByProductPresellDto(temp);
        }
        return result;
    }

    public List<ProductPresellDto> findAllUnBindProductPresell(ClientAccessType clientAccessType, ClientVersion clientVersion)
    {
        List<ProductPresellDto> result = new ArrayList<ProductPresellDto>();
        List<ProductPresell> presellList = productPresellService.findAllUnBindProductPresell();

        for (ProductPresell presell : presellList)
        {
            result.add(BeanToDtoSupport.loadByProductPresellDto(presell));
        }
        return result;
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
                ProductPresell productPresell = DtoToBeanSupport.loadByProductPresell((ProductPresellDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(productPresell);
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
                ProductPresellDto productPresellDto = BeanToDtoSupport.loadByProductPresellDto((ProductPresell) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(productPresellDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<ProductPresell> list = page.getResultsContent();
        List<ProductPresellDto> dlist = new ArrayList();
        for (ProductPresell productPresell : list)
        {
            dlist.add(BeanToDtoSupport.loadByProductPresellDto(productPresell));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

}
