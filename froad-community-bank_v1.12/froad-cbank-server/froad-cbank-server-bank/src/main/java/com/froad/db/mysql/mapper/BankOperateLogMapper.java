package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
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


    /**
     * 分页查询 BankOperateLog
     * @param page 
     * @param bankOperateLog
     * @return List<BankOperateLog>    返回分页查询结果集
     */
	public List<BankOperateLog> findByPage(Page page, @Param("bankOperateLog")BankOperateLog bankOperateLog);



}