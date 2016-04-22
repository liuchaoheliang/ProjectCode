package com.froad.db.mysql.mapper;

import com.froad.po.BankOperateLog;

/**
 * 
 * <p>@Title: BankOperateLogCommonMapper.java</p>
 * <p>Description: 描述 </p> 银行操作日志记录
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年5月8日
 */
public interface BankOperateLogCommonMapper {


    /**
     * 增加 BankOperateLog
     * @param bankOperateLog
     * @return Long    主键ID
     */
	public Long addBankOperateLog(BankOperateLog bankOperateLog);



}