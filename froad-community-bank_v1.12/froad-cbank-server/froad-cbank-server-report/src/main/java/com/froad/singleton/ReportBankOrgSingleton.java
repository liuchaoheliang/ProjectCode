package com.froad.singleton;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.rp_mappers.ReportBankOrgMapper;
import com.froad.po.ReportBankOrg;

public class ReportBankOrgSingleton {
	private static volatile Map<String, ReportBankOrg> instance = null;
	
	private ReportBankOrgSingleton(){
	}
	
	/**
	 * get org instance
	 * 
	 * @param session
	 * @return
	 */
	public static Map<String, ReportBankOrg> getInstance(SqlSession session){
		ReportBankOrgMapper mapper = null;
		List<ReportBankOrg> reportOrgList = null;
		ReportBankOrg reportOrg = null;
		Page<ReportBankOrg> page = null;
		int pageNumber = 1;
		int pageSize = Page.MAX_PAGE_SIZE;
		
		if (null == instance){
			synchronized (ReportBankOrgSingleton.class){
				if (null == instance){
					mapper = session.getMapper(ReportBankOrgMapper.class);
					page = new Page<ReportBankOrg>();
					page.setPageNumber(pageNumber);
					page.setPageSize(pageSize);
					reportOrgList = mapper.findInfoByPage(page);
					
					instance = new HashMap<String, ReportBankOrg>();
					while (null != reportOrgList && reportOrgList.size() > 0){
						for (int i = 0; i < reportOrgList.size(); i++){
							reportOrg = reportOrgList.get(i);
							instance.put(reportOrg.getBankCardId(), reportOrg);
						}
						if (reportOrgList.size() < pageSize){
							break;
						}
						pageNumber += 1;
						page.setPageNumber(pageNumber);
						
						session.commit(false);
						reportOrgList = mapper.findInfoByPage(page);
					}
				}
			}
		}
		
		return instance;
	}
	
	/**
	 * find by bank card id
	 * 
	 * @param session
	 * @param bankCardId
	 * @return
	 */
	public ReportBankOrg findByBankCardId(SqlSession session, String bankCardId){
		ReportBankOrg reportOrg = null;
		
		getInstance(session);
		reportOrg = instance.get(bankCardId);
		
		return reportOrg;
	}
	
	/**
	 * destroy instance
	 */
	public static void destroyInstance(){
		if (instance != null){
			instance.clear();
			instance = null;
		}
	}
}
