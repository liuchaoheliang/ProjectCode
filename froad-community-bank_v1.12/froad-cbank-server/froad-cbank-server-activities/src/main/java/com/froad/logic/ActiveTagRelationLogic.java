package com.froad.logic;

import com.froad.po.ActiveTagRelation;
import com.froad.thrift.vo.ResultVo;

public interface ActiveTagRelationLogic {
	public ResultVo verification(ResultVo resultVo,ActiveTagRelation activeTagRelation);
}
