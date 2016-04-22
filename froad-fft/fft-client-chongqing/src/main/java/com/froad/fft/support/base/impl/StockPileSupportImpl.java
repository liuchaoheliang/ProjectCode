
	 /**
  * 文件名：StockPileSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月4日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.ProductExportService;
import com.froad.fft.api.service.StockPileExportService;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.dto.TransDetailDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.MerchantOutletSupport;
import com.froad.fft.support.base.ProductPresellSupport;
import com.froad.fft.support.base.ProductSupport;
import com.froad.fft.support.base.StockPileSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月4日 下午12:37:35 
 */
@Service
public class StockPileSupportImpl implements StockPileSupport {

	private static Logger logger = Logger.getLogger(StockPileSupportImpl.class);
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Resource(name="stockPileService")
	private StockPileExportService stockPileExportService;
	
	
	@Resource
	private MerchantOutletSupport merchantOutletSupport;
	
	@Resource
	private ProductSupport productSupport; 
	
	@Override
	public PageDto getByPage(PageDto page) {
		
		
		
		page=stockPileExportService.findStockPilebyPage(clientAccessType, ClientVersion.version_1_0, page);
		
		if(page!=null && page.getResultsContent().size()>0){
			//关联查询交易详情
			for(Object temp:page.getResultsContent()){
				StockPileDto stockPileDto=(StockPileDto) temp;
				stockPileDto.setProductDto(productSupport.getById(stockPileDto.getProductId()));
				stockPileDto.setMerchantOutletDto(merchantOutletSupport.getById(stockPileDto.getMerchantOutletId()));		
			}
		}
		return page;
	
	
	}

	
	@Override
	public List<StockPileDto> getByCondtitons(StockPileDto stockPileDto) {
		return stockPileExportService.selectByConditions(clientAccessType, ClientVersion.version_1_0, stockPileDto);		
	}


	
	@Override
	public Boolean updateById(StockPileDto stockPileDto) {
		return stockPileExportService.updateStockPileById(clientAccessType, ClientVersion.version_1_0, stockPileDto);
	}
	
	

}
