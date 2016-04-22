package com.froad.cbank.coremodule.normal.boss.logic.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.MemberEntity;
import com.froad.cbank.coremodule.normal.boss.logic.MemberReportLogic;
import com.froad.cbank.coremodule.normal.boss.mapper.MemberMapper;
/**
 * 会员注册分析报表
 * @author liaopeixin
 *	@date 2015年11月5日 上午9:23:44
 */
@Repository
public class MemberReportLogicImpl implements MemberReportLogic {

	@Resource
	private MemberMapper memberMapper;
	@Override
	public List<MemberEntity> getListByPage(Page<MemberEntity> page,
			BaseReportEntity base) throws Exception {
		return memberMapper.getListByPage(page, base);
	}

}
