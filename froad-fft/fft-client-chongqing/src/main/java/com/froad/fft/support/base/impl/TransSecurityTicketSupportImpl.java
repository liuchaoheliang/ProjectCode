
	 /**
  * 文件名：TransSecurityTicketSupportImpl.java
  * 版本信息：Version 1.0
  * 日期：2014年4月7日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.support.base.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.froad.fft.api.service.TransSecurityTicketExportService;
import com.froad.fft.dto.TransSecurityTicketDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.support.base.TransSecurityTicketSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月7日 下午2:21:10 
 */

@Service
public class TransSecurityTicketSupportImpl implements
		TransSecurityTicketSupport {
	
	private Logger logger=Logger.getLogger(TransSecurityTicketSupportImpl.class);
	
	@Resource(name="transSecurityTicketService")
	private TransSecurityTicketExportService transSecurityTicketExportService; 
	
	final ClientAccessType clientAccessType = ClientAccessType.chongqing;
	
	@Override
	public TransSecurityTicketDto getById(Long id) {
		if(id==null){
			logger.info("查询ID为空");
			return null;
		}
		return transSecurityTicketExportService.selectById(clientAccessType, ClientVersion.version_1_0, id);
	}

	@Override
	public TransSecurityTicketDto getBySecurityNo(String SecurityNo) {
		if("".equals(SecurityNo)){
			logger.info("查询券号为空");
			return null;
		}
		return transSecurityTicketExportService.selectBySecurityNo(clientAccessType, ClientVersion.version_1_0, SecurityNo);
	}

	@Override
	public Long addTransSecurityTicket(TransSecurityTicketDto ticketDto) {
		if(ticketDto==null){
			logger.info("插入对象为空");
			return  null;
		}
		return transSecurityTicketExportService.addTransSecurityTicket(clientAccessType, ClientVersion.version_1_0, ticketDto);
	}

	@Override
	public boolean updateById(TransSecurityTicketDto ticketDto) {
		if(ticketDto==null || ticketDto.getId()==null){
			logger.info("必要参数为空");
			return false;
		}
		return transSecurityTicketExportService.updateById(clientAccessType, ClientVersion.version_1_0, ticketDto);
	}

	public TransSecurityTicketDto getByTransId(Long transId) {
		TransSecurityTicketDto  transSecurityTicketDto = new TransSecurityTicketDto();
		if(transId==null){
			logger.info("必要参数为空");
			return  transSecurityTicketDto;
		}
		transSecurityTicketDto.setTransId(transId);
		List<TransSecurityTicketDto> transSecurityTicketDtos = transSecurityTicketExportService.selectByCondition(clientAccessType, ClientVersion.version_1_0,transSecurityTicketDto);
		if(transSecurityTicketDtos!=null&&transSecurityTicketDtos.size()>0){//暂时是一 一对应，如果有改， 直接返回List
			transSecurityTicketDto = transSecurityTicketDtos.get(0);
		}
		return transSecurityTicketDto;
	}

	@Override
	public List<TransSecurityTicketDto> getByConditions(
			TransSecurityTicketDto transSecurityTicketDto) {
		return transSecurityTicketExportService.selectByCondition(clientAccessType, ClientVersion.version_1_0,transSecurityTicketDto);
	}

}
