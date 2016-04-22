package com.froad.logic;

import com.froad.po.ActiveDetailRule;
import com.froad.thrift.vo.ResultVo;

public interface ActiveDetailRuleLogic {
	public ResultVo verification(ResultVo resultVo,ActiveDetailRule activeDetailRule);
}
