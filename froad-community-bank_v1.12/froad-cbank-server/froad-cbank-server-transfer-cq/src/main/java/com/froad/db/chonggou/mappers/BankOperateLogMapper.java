package com.froad.db.chonggou.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.BankOperateLog;

/**
 * 
 * <p>@Title: BankOperateLogMapper.java</p>
 * <p>Description: 描述 </p> 银行操作日志记录
 * @author f-road-ll 
 * @version 1.0
 * @created 2015年4月24日
 */
public interface BankOperateLogMapper {


	public Boolean addBankOperateLogByBatch(@Param("bankOperateLogs")List<BankOperateLog> bankOperateLogs);

	/**
	 * delete records by client id
	 * 
	 * @param clientId
	 */
	public void deleteByClientId(String clientId);
}