package com.froad.cbank.coremodule.module.normal.bank.auto;

import org.springframework.stereotype.Component;

import com.froad.cbank.coremodule.module.normal.bank.util.RoleConstant;

/**
 * 定时任务
 * @ClassName AutoBusinessService
 * @author zxl
 * @date 2015年4月29日 下午2:25:15
 */
@Component
public class AutoBusinessService {
	
	/**
	 * 自动更新权限
	 * @tilte autoReloadRole
	 * @author zxl
	 * @date 2015年4月29日 下午2:24:58
	 */
	// @Scheduled(cron="0 0 0/1 * * ?")
	public void autoReloadRole(){
		RoleConstant.reload();
	}
	
}
