package com.froad.cbank.coremodule.module.normal.bank.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorLogVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorLogVoRes;
import com.froad.thrift.service.BankOperateLogService;
import com.froad.thrift.vo.BankOperateLogPageVoRes;
import com.froad.thrift.vo.BankOperateLogVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.PlatType;

/**
 * 日志
 * @author ylchu
 *
 */
@Service
public class OperatorLogService {

	@Resource
	BankOperateLogService.Iface bankOperateLogService;
	
	/**
	 * 银行管理员操作日志条件分页查询
	 * @param ol
	 * @return
	 * @throws ParseException 
	 */
	public QueryResult<OperatorLogVoRes> findPageByConditions(OperatorLogVo ol) throws ParseException{
		
		PageVo page = new PageVo();
		page.setPageNumber(ol.getPageNumber());
		page.setPageSize(ol.getPageSize());
		page.setLastPageNumber(ol.getLastPageNumber());
		page.setFirstRecordTime(ol.getFirstRecordTime());
		page.setLastRecordTime(ol.getLastRecordTime());
		
		BankOperateLogVo bankOperatorLog = new BankOperateLogVo();
		bankOperatorLog.setClientId(ol.getClientId());
		bankOperatorLog.setPlatType(PlatType.bank.getValue());
		bankOperatorLog.setLogType(true);
		
		if(StringUtils.isNotEmpty(ol.getOrgCode())){
			bankOperatorLog.setOrgCode(ol.getOrgCode());
		}
		if(StringUtils.isNotEmpty(ol.getLoginName())){
			bankOperatorLog.setUsername(ol.getLoginName());
		}
		if(StringUtils.isNotEmpty(ol.getStartDate())){
			bankOperatorLog.setStartDate(DateUtil.DateFormat(ol.getStartDate()));
		}
		if(StringUtils.isNotEmpty(ol.getEndDate())){
			bankOperatorLog.setEndDate(DateUtil.DateFormatEnd(ol.getEndDate()));
		}
		
		QueryResult<OperatorLogVoRes> queryVo = new QueryResult<OperatorLogVoRes>();
		List<OperatorLogVoRes> operatorList = new ArrayList<OperatorLogVoRes>();
		BankOperateLogPageVoRes bankOperatorPage = null;
		try {
			bankOperatorPage = bankOperateLogService.getBankOperateLogByPage(page, bankOperatorLog);
			List<BankOperateLogVo> bankOperatorList = bankOperatorPage.getBankOperateLogVoList();
			if(bankOperatorList != null && bankOperatorList.size() > 0){
				for(BankOperateLogVo bankOperatorVo : bankOperatorList){
					OperatorLogVoRes operatorVo = new OperatorLogVoRes();
					operatorVo.setLoginName(bankOperatorVo.getUsername());//登陆名
					operatorVo.setOrgName(bankOperatorVo.getOrgName());//所属机构
					operatorVo.setRoleName(bankOperatorVo.getRoleName());
					operatorVo.setCreateTime(DateUtil.formatDate(
							new Date(bankOperatorVo.getCreateTime()), 
							DateUtil.DATE_TIME_FORMAT_01));
					operatorVo.setRemark(bankOperatorVo.getDescription());
					operatorList.add(operatorVo);
				}
			}
			if(bankOperatorPage.getPage() != null){
				queryVo.setPageCount(bankOperatorPage.getPage().getPageCount());
				queryVo.setTotalCount(bankOperatorPage.getPage().getTotalCount());
				queryVo.setPageNumber(bankOperatorPage.getPage().getPageNumber());
				queryVo.setLastPageNumber(bankOperatorPage.getPage().getLastPageNumber());
				queryVo.setFirstRecordTime(bankOperatorPage.getPage().getFirstRecordTime());
				queryVo.setLastRecordTime(bankOperatorPage.getPage().getLastRecordTime());
			}

		} catch (Exception e) {
			LogCvt.info("银行管理员操作日志列表条件查询"+e.getMessage(), e);
		}
		
		queryVo.setResult(operatorList);
		return queryVo;
		
	}
}
