package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.RefundsExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.dto.RefundsDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.RefundsSupport;

@Service
public class RefundsSupportImpl implements RefundsSupport {

	private static Logger logger = Logger.getLogger(RefundsSupportImpl.class);

	@Resource(name = "refundsExportService")
	private RefundsExportService refundsExportService;
	
	private final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Override
	public RefundsDto applyRefund(RefundsDto refundsDto) {
		logger.info("保存退款申请");
		Long id =  refundsExportService.saveRefunds(clientAccessType, ClientVersion.version_1_0, refundsDto);
		logger.info("保存结果:refundId"+id);
	    refundsDto = refundsExportService.findRefundsById(clientAccessType, ClientVersion.version_1_0, id);
		return refundsDto;
	}

	@Override
	public RefundsDto selectRefunds(Long TransId) {
		PageDto<RefundsDto> pageDto = new PageDto<RefundsDto>();
		PageFilterDto<RefundsDto> pageFilterDto = new PageFilterDto<RefundsDto>();
		RefundsDto filterEntity = new RefundsDto();
		filterEntity.setTransId(TransId);
		pageFilterDto.setFilterEntity(filterEntity);
		pageDto.setPageFilterDto(pageFilterDto);
		pageDto = refundsExportService.findRefundsByPage(clientAccessType, ClientVersion.version_1_0, pageDto);
		if(pageDto!=null&&pageDto.getResultsContent()!=null&&pageDto.getResultsContent().size()>0){
			//只会有一条退款申请记录
			return pageDto.getResultsContent().get(0);
		}
		return null;
	}
	
}
