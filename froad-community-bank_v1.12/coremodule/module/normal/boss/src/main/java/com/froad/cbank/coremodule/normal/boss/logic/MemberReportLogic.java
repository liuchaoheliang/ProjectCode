package com.froad.cbank.coremodule.normal.boss.logic;

import java.util.List;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.MemberEntity;

/**
 * 会员注册分析 logic
 * @author liaopeixin
 *	@date 2015年11月5日 上午11:02:49
 */
public interface MemberReportLogic {

	public List<MemberEntity> getListByPage(Page<MemberEntity> page,BaseReportEntity base) throws Exception;
}
