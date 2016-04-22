package com.froad.db.bps.mappers;

import java.util.List;

import com.froad.db.bps.entity.BankOperator;

public interface BankOperatorMapper {
	
	List<BankOperator> selectAllOperators();
}
