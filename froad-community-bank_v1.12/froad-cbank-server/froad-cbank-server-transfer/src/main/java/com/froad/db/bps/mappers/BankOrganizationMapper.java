package com.froad.db.bps.mappers;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.bps.entity.BankOrganization;

public interface BankOrganizationMapper {
	
	public List<BankOrganization> findAllOrganization();
	
	public BankOrganization findByOrgCode(@Param("orgCode")String orgCode);
}
