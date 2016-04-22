package com.froad.cbank.coremodule.normal.boss.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.MemberEntity;

/**
 * 会员注册分析
 * @author liaopeixin
 *	@date 2015年11月3日 上午11:02:49
 */
public interface MemberMapper {

	public List<MemberEntity> getListByPage(Page<MemberEntity> page,@Param("base")BaseReportEntity base);
}
