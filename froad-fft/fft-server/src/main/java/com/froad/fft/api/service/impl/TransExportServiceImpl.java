package com.froad.fft.api.service.impl;

import javax.annotation.Resource;

import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.PayDto;
import com.froad.fft.dto.TransDetailDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Pay;
import com.froad.fft.persistent.entity.TransDetails;
import com.froad.fft.service.PayService;
import com.froad.fft.service.TransDetailsService;
import com.froad.fft.service.TransService;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.TransExportService;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.Result;
import com.froad.fft.bean.trans.TransDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.entity.Trans;
import com.froad.fft.service.TransCoreService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class TransExportServiceImpl implements TransExportService
{

    @Resource
    private TransCoreService transCoreService;

    @Resource
    private TransService transService;

    @Resource
    private PayService payService;

    @Resource
    private TransDetailsService transDetailsService;

    @Override
    public Result createTrans(TransDto transDto) throws FroadException
    {
        Trans trans = DtoToBeanSupport.loadByTrans(transDto);
        return transCoreService.createTrans(trans);
    }

    @Override
    public Result doPay(ClientAccessType clientAccessType, ClientVersion clientVersion, String transSn) throws FroadException
    {
        return transCoreService.doPay(transSn);
    }

    @Override
    public PageDto<TransQueryDto> findTransByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<TransQueryDto> pageDto)
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(transService.findTransByPage(page));
        return returnPageDto;
    }

    public TransQueryDto findTransById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long transId)
    {
        return BeanToDtoSupport.loadByTransDto(transService.findTransById(transId));
    }

    public List<PayDto> findPaysByTransId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long transId)
    {
        List<Pay> payList = payService.findPayByTransId(transId);
        List<PayDto> result = new ArrayList<PayDto>();
        for (Pay pay : payList)
        {
            result.add(BeanToDtoSupport.loadByPayDto(pay));
        }
        return result;

    }

    public List<TransDetailDto> findTransDetailsByTransId(ClientAccessType clientAccessType, ClientVersion clientVersion, Long transId)
    {

        List<TransDetails> detailsList = transDetailsService.findTransDetailsByTransId(transId);
        List<TransDetailDto> result = new ArrayList<TransDetailDto>();
        for (TransDetails details : detailsList)
        {
            result.add(BeanToDtoSupport.loadByTransDetailDto(details));
        }
        return result;
    }

    public Result clusterByManager(ClientAccessType clientAccessType, ClientVersion clientVersion,Long productId)
    {
        return transService.clusterByManager(productId);
    }

    public Result clusterFailedByManager(ClientAccessType clientAccessType, ClientVersion clientVersion,Long productId)
    {
        return transService.clusterFailedByManager(productId);
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
                Trans merchant = DtoToBeanSupport.loadByTransFromTransQueryDto((TransQueryDto) pageDto.getPageFilterDto().getFilterEntity());
                pageFilter.setFilterEntity(merchant);
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
                TransQueryDto merchantDto = BeanToDtoSupport.loadByTransDto((Trans) page.getPageFilter().getFilterEntity());
                pageFilterDto.setFilterEntity(merchantDto);
            }
            pageFilterDto.setProperty(page.getPageFilter().getProperty());
            pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
            pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
            pageDto.setPageFilterDto(pageFilterDto);
        }

        List<Trans> list = page.getResultsContent();
        List<TransQueryDto> tList = new ArrayList();
        for (Trans trans : list)
        {
            tList.add(BeanToDtoSupport.loadByTransDto(trans));
        }
        pageDto.setResultsContent(tList);

        return pageDto;
    }

    @Override
    public TransQueryDto findTransDtoBySn(ClientAccessType clientAccessType, ClientVersion clientVersion, String tranSn)
    {
        Trans trans = transService.findTransBySn(tranSn);

        if (trans == null)
        {
            return null;
        }
        return BeanToDtoSupport.loadByTransDto(trans);
    }

	@Override
	public String queryPresellState(ClientAccessType clientAccessType,
			ClientVersion clientVersion, String sn) {
		return transService.queryPresellState(sn);
	}

	@Override
	public void cluster(ClientAccessType clientAccessType,
			ClientVersion clientVersion) {
	   transService.cluster();
	}

	@Override
	public List<TransQueryDto> selectGroupAndPresellByMemberCode(Long memberCode) {
		List<TransQueryDto> transQueryDtos = new ArrayList<TransQueryDto>(); 
		List<Trans> trans = transService.selectGroupAndPresellByMemberCode(memberCode);
		if(trans!=null&&trans.size()>0){
			for (Trans tran : trans) {
				List<TransDetailDto> transDetailDtos = new ArrayList<TransDetailDto>();
				TransQueryDto transQueryDto = BeanToDtoSupport.loadByTransDto(tran);
				if(tran.getDetailsList()!=null&&tran.getDetailsList().size()>0){
					for (TransDetails transDetail : tran.getDetailsList()) {
						transDetailDtos.add(BeanToDtoSupport.loadByTransDetailDto(transDetail));
					}
				}
				transQueryDto.setTransDetailDto(transDetailDtos);
				transQueryDtos.add(transQueryDto);
			}
		}
		return transQueryDtos;
	}

	@Override
	public void closeTimeoutTrans(ClientAccessType clientAccessType,
			ClientVersion clientVersion) {
		transService.closeTimeoutTrans();
	}


}
