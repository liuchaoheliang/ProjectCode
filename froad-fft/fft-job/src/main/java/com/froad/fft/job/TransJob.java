package com.froad.fft.job;


import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.froad.fft.api.service.SystemConfigExportService;
import com.froad.fft.api.service.TransExportService;
import com.froad.fft.dto.SystemConfigDto;
import com.froad.fft.enums.ClientAccessType;
import com.froad.fft.enums.ClientVersion;

/**
 * 交易关闭
 * @author FQ
 *
 */

@Component
@Lazy(false)
public class TransJob {
	
	final static Logger logger = Logger.getLogger(TransJob.class);
	
	
	@Resource(name="transService")
	private TransExportService transExportService;
	
	/**
	 * 自然成团处理
	 * **/
	@Scheduled(cron="${job.auto_cluster.cron}")
	public void autoCluster(){
		logger.info("=========开始执行自然成团任务========");
		transExportService.cluster(ClientAccessType.management, ClientVersion.version_1_0);
		logger.info("=========结束执行自然成团任务========");
	}
	
	@Scheduled(cron="${job.close_timeout_trans.cron}")
	public void closeTimeoutTrans(){
		logger.info("=========开始执行关闭超时交易的任务========");
		transExportService.closeTimeoutTrans(ClientAccessType.management,
				ClientVersion.version_1_0);
		logger.info("=========结束执行关闭超时交易的任务========");
	}
}
