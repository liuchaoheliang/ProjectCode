package com.froad.logic;


import com.froad.db.mysql.bean.Page;
import com.froad.exceptions.FroadServerException;
import com.froad.po.BankOperateLog;

/**
 * 
 * <p>@Title: BankOperateLogLogic.java</p>
 * <p>Description: 描述 </p> 银行用户操作日志Logic接口类
 * @author f-road 
 * @version 1.0
 * @created 2015年4月2日
 */
public interface BankOperateLogLogic {


    /**
     * 增加 BankOperateLog用户操作日志
     * @param bankOperateLog
     * @return Long    主键ID
     */
	public Boolean addBankOperateLog(int operateFlag,BankOperateLog bankOperatorLog) throws FroadServerException, Exception;


    /**
     * 分页查询 BankOperateLog
     * @param page
     * @param bankOperateLog
     * @return Page    结果集合 
     */
	// public Page<BankOperateLog> findBankOperateLogByPage(Page<BankOperateLog> page, BankOperateLog bankOperatorLog);



}