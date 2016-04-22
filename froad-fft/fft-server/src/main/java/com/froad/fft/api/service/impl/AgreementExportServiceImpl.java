package com.froad.fft.api.service.impl;

import com.froad.fft.api.service.AgreementExportService;
import com.froad.fft.api.support.BeanToDtoSupport;
import com.froad.fft.api.support.DtoToBeanSupport;
import com.froad.fft.bean.FroadException;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.AgreementDto;
import com.froad.fft.dto.SysClientDto;
import com.froad.fft.enums.AgreementType;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.persistent.bean.page.Order;
import com.froad.fft.persistent.bean.page.Page;
import com.froad.fft.persistent.bean.page.PageFilter;
import com.froad.fft.persistent.entity.Agreement;
import com.froad.fft.service.AgreementService;
import com.froad.fft.service.SysClientService;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class AgreementExportServiceImpl implements AgreementExportService
{
    @Resource(name = "agreementServiceImpl")
    private AgreementService agreementService;
    
    @Resource(name= "sysClientServiceImpl")
    private SysClientService sysClientService;

    @Override
    public Long addAgreement(ClientAccessType clientAccessType, ClientVersion clientVersion, AgreementDto agreementDto) throws FroadException
    {

        Agreement temp = DtoToBeanSupport.loadByAgreement(agreementDto);
        return agreementService.saveAgreement(temp);
    }

    public Boolean updateAgreementById(ClientAccessType clientAccessType, ClientVersion clientVersion, AgreementDto dto) throws FroadException
    {
        Agreement temp = DtoToBeanSupport.loadByAgreement(dto);
        return agreementService.updateAgreementById(temp);
    }

    public AgreementDto findAgreementById(ClientAccessType clientAccessType, ClientVersion clientVersion, Long id) throws FroadException
    {

        Agreement temp = agreementService.selectAgreementById(id);
        AgreementDto result = null;
        if (null != temp)
        {
            result = BeanToDtoSupport.loadByAgreementDto(temp);
        }
        return result;

    }

    public PageDto<AgreementDto> findAgreementByPage(ClientAccessType clientAccessType, ClientVersion clientVersion, PageDto<AgreementDto> pageDto) throws FroadException
    {
        Page page = loadBy(pageDto);
        PageDto returnPageDto = loadBy(agreementService.selectAgreementByPage(page));
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
                      Agreement circle = DtoToBeanSupport.loadByAgreement((AgreementDto) pageDto.getPageFilterDto().getFilterEntity());
                      pageFilter.setFilterEntity(circle);
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
                      AgreementDto circleDto = BeanToDtoSupport.loadByAgreementDto((Agreement) page.getPageFilter().getFilterEntity());
                      pageFilterDto.setFilterEntity(circleDto);
                  }
                  pageFilterDto.setProperty(page.getPageFilter().getProperty());
                  pageFilterDto.setStartTime(page.getPageFilter().getStartTime());
                  pageFilterDto.setEndTime(page.getPageFilter().getEndTime());
                  pageDto.setPageFilterDto(pageFilterDto);
              }

              List<Agreement> list = page.getResultsContent();
              List<AgreementDto> dlist = new ArrayList();
              for (Agreement agreement : list)
              {
                  dlist.add(BeanToDtoSupport.loadByAgreementDto(agreement));
              }
              pageDto.setResultsContent(dlist);

              return pageDto;
          }

		@Override
		public AgreementDto findAgreementByClient(
				ClientAccessType clientAccessType, ClientVersion version10,
				AgreementType agreementType) {
			AgreementDto agreementDto = new AgreementDto();
			SysClientDto sysClientDto = BeanToDtoSupport.loadbySysClientDto(sysClientService.findSysClientByNumber(clientAccessType.getCode()));
			if(sysClientDto == null){
				return null;
			}else{
				agreementDto.setClientId(sysClientDto.getId());
				agreementDto.setType(agreementType);
				//开始查询协议
				PageDto<AgreementDto> pageDto  = new PageDto<AgreementDto>();
				PageFilterDto<AgreementDto> pageFilterDto = new PageFilterDto<AgreementDto>();
				pageFilterDto.setFilterEntity(agreementDto);
				pageDto.setPageFilterDto(pageFilterDto);
				pageDto = findAgreementByPage(clientAccessType, ClientVersion.version_1_0, pageDto);
				if(pageDto.getResultsContent()==null||pageDto.getResultsContent().size()==0){
					return null;
				}
				agreementDto = pageDto.getResultsContent().get(0);
			}
			return agreementDto;
		}

}
