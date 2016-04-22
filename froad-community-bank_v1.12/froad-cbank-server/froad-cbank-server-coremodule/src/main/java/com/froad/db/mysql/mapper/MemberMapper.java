package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.po.req.ReqPo;
import com.froad.po.resp.Resp;

public interface MemberMapper {

	public List<Resp> findReportPageByMember(@Param("reqPo") ReqPo reqPo);

	public int findReportMemberByPageCount(@Param("reqPo") ReqPo reqPo);
}
