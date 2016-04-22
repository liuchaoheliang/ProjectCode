package com.froad.db.mysql.rp_mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.ReportBankOrg;

public interface ReportBankOrgMapper {

	
	public List<ReportBankOrg> findInfoByPage(@Param("page")Page<ReportBankOrg> page);
	
	/**
	 * find all report bank org information
	 * 
	 * @return
	 */
	public List<ReportBankOrg> findAll();
	
	
	public Integer findAllCount();
}
