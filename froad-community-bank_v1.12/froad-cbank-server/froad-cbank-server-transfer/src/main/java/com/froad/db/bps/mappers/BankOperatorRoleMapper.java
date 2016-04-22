package com.froad.db.bps.mappers;

import java.util.List;

import com.froad.db.bps.entity.BankOperatorRole;


public interface BankOperatorRoleMapper {
	
	Long findRoleByOperatorId(Long operatorId);
	
	List<BankOperatorRole> findAll();
}
