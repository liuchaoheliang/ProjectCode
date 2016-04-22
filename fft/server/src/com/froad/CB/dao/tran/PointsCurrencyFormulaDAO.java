package com.froad.CB.dao.tran;

import java.util.List;

import com.froad.CB.po.transaction.PointsCurrencyFormula;

public interface PointsCurrencyFormulaDAO {

	void insert(PointsCurrencyFormula record);

	int updateByPrimaryKeySelective(PointsCurrencyFormula record);

	PointsCurrencyFormula selectByPrimaryKey(Integer id);

	int deleteByPrimaryKey(Integer id);

	List<PointsCurrencyFormula> getPointsCurrencyFormulaList(
			PointsCurrencyFormula queryCon);
}