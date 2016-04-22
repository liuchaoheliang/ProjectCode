
	 /**
  * 文件名：ReturnSaleSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月6日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.ReturnSaleDetailExportService;
import com.froad.fft.api.service.ReturnSaleExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.ReturnSaleDetailDto;
import com.froad.fft.dto.ReturnSaleDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.MerchantOutletSupport;
import com.froad.fft.support.base.ReturnSaleSupport;
import com.froad.fft.support.base.SysUserSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月6日 上午10:27:11 
 */
@Service
public class ReturnSaleSupportImpl implements ReturnSaleSupport {
	
	private static Logger logger = Logger.getLogger(StockPileSupportImpl.class);
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Resource(name="returnSaleService")
	private ReturnSaleExportService returnSaleExportService;
	
	@Resource(name="returnSaleDetailService")
	private ReturnSaleDetailExportService returnSaleDetailExportService;
	
	
	@Resource
	private MerchantOutletSupport merchantOutletSupport;
	
	@Resource
	private SysUserSupport sysUserSupport;

	@Override
	public PageDto getByPage(PageDto page) {
		page=returnSaleExportService.findReturnSaleByPage(clientAccessType, ClientVersion.version_1_0, page);
		if(page!=null && page.getResultsContent().size()>0){
			//关联查询交易详情
			for(Object temp:page.getResultsContent()){
				ReturnSaleDto returnSaleDto=(ReturnSaleDto) temp;
				SysUserDto sysUserDto=sysUserSupport.findSysUserById(returnSaleDto.getSysUserId());
				returnSaleDto.setSysUserDto(sysUserDto);
				MerchantOutletDto merchantOutletDto=merchantOutletSupport.getById(returnSaleDto.getMerchantOutletId());
				returnSaleDto.setMerchantOutletDto(merchantOutletDto);
				ReturnSaleDetailDto returnSaleDetailDto=returnSaleDetailExportService.getReturnSaleDetailByRsId(clientAccessType, ClientVersion.version_1_0, returnSaleDto.getId());
				returnSaleDto.setReturnSaleDetailDto(returnSaleDetailDto);
			}
		}
		return page;
	}

	

	@Override
	public List<ReturnSaleDto> getByConditions(ReturnSaleDto dto) {
		
		List<ReturnSaleDto> list=returnSaleExportService.getByConditions(clientAccessType, ClientVersion.version_1_0, dto);
		if(list!=null && list.size()>0){
			for(ReturnSaleDto temp:list){
				long rsId=temp.getId();
				ReturnSaleDetailDto saleDetailDto=returnSaleDetailExportService.getReturnSaleDetailByRsId(clientAccessType, ClientVersion.version_1_0, rsId);
				temp.setReturnSaleDetailDto(saleDetailDto);
			}
		}
		return list;
	}

	@Override
	public Long addReturnSale(ReturnSaleDto returnSaleDto) {
		return returnSaleExportService.saveReturnSaleDto(clientAccessType, ClientVersion.version_1_0, returnSaleDto);
	}



	@Override
	public ReturnSaleDto getById(Long id) {
		if(id==null){
			logger.info("查询Id为空");
			return null; 
		}
		return returnSaleExportService.getReturnSaleById(clientAccessType, ClientVersion.version_1_0, id);
	}

}
