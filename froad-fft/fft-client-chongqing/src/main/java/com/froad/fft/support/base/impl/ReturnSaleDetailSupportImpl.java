
	 /**
  * 文件名：ReturnSaleDetailSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.froad.fft.api.service.ReturnSaleDetailExportService;
import com.froad.fft.dto.ReturnSaleDetailDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.ReturnSaleDetailSupport;
import com.froad.fft.support.base.ReturnSaleSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午10:10:02 
 */
@Service
public class ReturnSaleDetailSupportImpl implements ReturnSaleDetailSupport {

	@Resource(name="returnSaleDetailService")
	private ReturnSaleDetailExportService returnSaleDetailExportService;
	
	@Resource
	private ReturnSaleSupport returnSaleSupport;
	
	
	private final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Override
	public Long addReturnSaleDetai(ReturnSaleDetailDto returnSaleDetailDto) {
		return returnSaleDetailExportService.saveReturnSaleDetailDto(clientAccessType, ClientVersion.version_1_0, returnSaleDetailDto);
	}

	

	@Override
	public List<ReturnSaleDetailDto> getByConditions(
			ReturnSaleDetailDto returnSaleDetailDto) {
		List<ReturnSaleDetailDto> list=returnSaleDetailExportService.getByConditions(clientAccessType, ClientVersion.version_1_0, returnSaleDetailDto);
		for(ReturnSaleDetailDto temp:list){
			temp.setReturnSaleDto(returnSaleSupport.getById(temp.getReturnSaleId()));
		}
		return list;
	}

}
