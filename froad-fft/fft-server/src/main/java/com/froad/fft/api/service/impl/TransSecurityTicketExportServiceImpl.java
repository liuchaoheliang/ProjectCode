
	 /**
  * 文件名：TransSecurityTicketExportServiceImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.api.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;

import com.froad.fft.api.service.TransSecurityTicketExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.dto.TransSecurityTicketDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.persistent.entity.TransSecurityTicket;
import com.froad.fft.service.TransSecurityTicketService;



	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午1:46:05 
 */
public class TransSecurityTicketExportServiceImpl implements
		TransSecurityTicketExportService {
	final static Logger logger = Logger.getLogger(TransSecurityTicketExportServiceImpl.class);

		@Resource(name="transSecurityTicketServiceImpl")
		private TransSecurityTicketService transSecurityTicketService;
	
	@Override
	public Long addTransSecurityTicket(ClientAccessType clientAccessType,
			ClientVersion clientVersion,
			TransSecurityTicketDto transSecurityTicketDto)
			throws FroadException {
		return transSecurityTicketService.saveTransSecurityTicket(DtoToBeanSupport.loadByTransSecurityTicket(transSecurityTicketDto));
	}


	@Override
	public Boolean updateById(ClientAccessType clientAccessType,
			ClientVersion clientVersion,
			TransSecurityTicketDto transSecurityTicketDto)
			throws FroadException {
		return transSecurityTicketService.updateTransSecurityTicketById(DtoToBeanSupport.loadByTransSecurityTicket(transSecurityTicketDto));
	}


	@Override
	public TransSecurityTicketDto selectById(ClientAccessType clientAccessType,
			ClientVersion clientVersion, Long id) throws FroadException {
		TransSecurityTicket securityTicket=transSecurityTicketService.selectTransSecurityTicketById(id);
		return BeanToDtoSupport.loadByTransSecurityTicketDto(securityTicket);
	}


	

	@Override
	public TransSecurityTicketDto selectBySecurityNo(ClientAccessType clientAccessType, ClientVersion clientVersion,String SecurityNo) {
		TransSecurityTicket securityTicket=transSecurityTicketService.selectBySecurityNo(SecurityNo);		
		return BeanToDtoSupport.loadByTransSecurityTicketDto(securityTicket);
	}



	@Override
	public PageDto<TransSecurityTicketDto> findByPage(
			ClientAccessType clientAccessType, ClientVersion clientVersion,
			PageDto<TransSecurityTicketDto> pageDto) throws FroadException {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(transSecurityTicketService.findByPage(page));
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
                TransSecurityTicket transSecurityTicket = DtoToBeanSupport.loadByTransSecurityTicket((TransSecurityTicketDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(transSecurityTicket);
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
            	TransSecurityTicketDto securityTicketDto = BeanToDtoSupport.loadByTransSecurityTicketDto((TransSecurityTicket) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(securityTicketDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<TransSecurityTicket> list = page.getResultsContent();
        List<TransSecurityTicketDto> tList = new ArrayList();
        for (TransSecurityTicket temp : list)
        {
            tList.add(BeanToDtoSupport.loadByTransSecurityTicketDto(temp));
        }
        pageDto.setResultsContent(tList);

        return pageDto;
    }


	@Override
	public List<TransSecurityTicketDto> selectByCondition(ClientAccessType clientAccessType, ClientVersion clientVersion,TransSecurityTicketDto transSecurityTicketDto) throws FroadException  {
		//dto
		List<TransSecurityTicketDto> transSecurityTicketDtos = new ArrayList<TransSecurityTicketDto>();
		//参数转换
		TransSecurityTicket ticket = DtoToBeanSupport.loadByTransSecurityTicket(transSecurityTicketDto);
		//查询结果
		List<TransSecurityTicket> transSecurityTickets = transSecurityTicketService.selectByCondition(ticket);
		//结果集转换
		if(transSecurityTickets!=null && transSecurityTickets.size()>0){			
			for (TransSecurityTicket transSecurityTicket : transSecurityTickets) {
				transSecurityTicketDtos.add(BeanToDtoSupport.loadByTransSecurityTicketDto(transSecurityTicket));
			}
		}
		//返回结果
		return transSecurityTicketDtos;
	}
}
