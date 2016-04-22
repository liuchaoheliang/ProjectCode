package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.AdExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.AdDto;
import com.froad.fft.dto.SysResourceDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Ad;
import com.froad.fft.persistent.entity.SysResource;
import com.froad.fft.service.AdService;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

public class AdExportServiceImpl implements AdExportService
{

    @Resource(name = "adServiceImpl")
    private AdService adService;

    @Override
    public Long addAd(ClientAccessType clientAccessType, ClientVersion clientVersion, AdDto adDto) throws FroadException
    {
        Ad temp = DtoToBeanSupport.loadByAd(adDto);
        return adService.saveAd(temp);
    }

    public PageDto findAdByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(adService.findAdByPage(page));
        return returnPageDto;
    }

    public AdDto findAdById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id)
    {
        return BeanToDtoSupport.loadByAdDto(adService.selectAdById(id));
    }

    public Boolean updateAdById(ClientAccessType clientAccessType, ClientVersion clientVersion, AdDto dto) throws FroadException
    {
        Ad temp = DtoToBeanSupport.loadByAd(dto);
        return adService.updateAdById(temp);
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
                Ad ad = DtoToBeanSupport.loadByAd((AdDto) pageDto.getPageFilterDto().getFilterEntity());

                pageFilter.setFilterEntity(ad);
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
                AdDto categoryDto = BeanToDtoSupport.loadByAdDto((Ad) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(categoryDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<Ad> list = page.getResultsContent();
        List<AdDto> dlist = new ArrayList();
        for (Ad ad : list)
        {
            dlist.add(BeanToDtoSupport.loadByAdDto(ad));
        }
        pageDto.setResultsContent(dlist);

        return pageDto;
    }

	@Override
	public List<AdDto> findEnableAdByAdPositionId(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			Long adPositionId) throws FroadException {
		
		List<AdDto> adDtoList=new ArrayList<AdDto>();
		
		List<Ad> adList=adService.findEnableAdByAdPositionId(adPositionId);
		for(Ad ad:adList){
			adDtoList.add(BeanToDtoSupport.loadByAdDto(ad));
		}
		
		return adDtoList;
	}


}
