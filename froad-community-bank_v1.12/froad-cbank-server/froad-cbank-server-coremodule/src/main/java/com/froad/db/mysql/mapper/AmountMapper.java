package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.req.ReqPo;
import com.froad.po.resp.Resp;

public interface AmountMapper {

	public List<Resp> findReportPageByAmount(@Param("reqPo") ReqPo reqPo);

	public int findReportPageCountByAmount(@Param("reqPo") ReqPo reqPo);
	
	public List<Resp> findReportPageBy(@Param("reqPo") ReqPo reqPo);
	
	public Resp sumAmountOrOrder(@Param("reqPo") ReqPo reqPo);
	
	public Resp sumRefundAmountOrOrder(@Param("reqPo") ReqPo reqPo);

}
