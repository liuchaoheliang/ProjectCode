package com.froad.CB.service.impl;

import javax.jws.WebService;
import org.apache.log4j.Logger;

import com.froad.CB.dao.DailyTaskRuleDao;
import com.froad.CB.po.ClientGoodsExchangeRackImages;
import com.froad.CB.po.DailyTaskRule;
import com.froad.CB.service.DailyTaskRuleService;

/**
 * @author TXL
 * @date 2013-2-4 上午09:47:29
 * @version 1.0
 */
@WebService(endpointInterface = "com.froad.CB.service.DailyTaskRuleService")
public class DailyTaskRuleServiceImpl implements DailyTaskRuleService {

	private DailyTaskRuleDao dailyTaskRuleDao;
	private static final Logger log = Logger.getLogger(AdvertServiceImpl.class);

	@Override
	public DailyTaskRule getDailyTaskRuleByPrimaryId(Integer id) {
		if (id == null)
			return null;
		dailyTaskRuleDao.getDailyTaskRuleByPrimaryId(id);

		DailyTaskRule dtr = null;
		try {
			dtr = (DailyTaskRule) dailyTaskRuleDao.getDailyTaskRuleByPrimaryId(id);
		} catch (Exception e) {
			log.error("查询对象时，数据库异常！", e);
		}
		return dtr;
		
	}

	
	
	
	public DailyTaskRuleDao getDailyTaskRuleDao() {
		return dailyTaskRuleDao;
	}

	public void setDailyTaskRuleDao(DailyTaskRuleDao dailyTaskRuleDao) {
		this.dailyTaskRuleDao = dailyTaskRuleDao;
	}
	

}
