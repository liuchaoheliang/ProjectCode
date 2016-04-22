
	 /**
  * 文件名：StockPileLogSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.StockPileLogExportService;
import com.froad.fft.dto.StockPileLogDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.StockPileLogSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午10:04:10 
 */
@Service
public class StockPileLogSupportImpl implements StockPileLogSupport {

	@Resource(name="stockPileLogService")
	private StockPileLogExportService stockPileLogExportService;

	private final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	
	@Override
	public Long addStockPileLog(StockPileLogDto stockPileLogDto) {
		return stockPileLogExportService.addStockPileLog(clientAccessType, ClientVersion.version_1_0, stockPileLogDto);		
	}

}
