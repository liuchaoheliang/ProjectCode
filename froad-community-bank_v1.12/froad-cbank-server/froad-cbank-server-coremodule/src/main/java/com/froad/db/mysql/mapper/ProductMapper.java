package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.req.ReqPo;
import com.froad.po.resp.Resp;

public interface ProductMapper {

	public List<Resp> findReportProductInfoBy(Page page,
			@Param("reqPo") ReqPo reqPo);

	public Integer getProductCount(Page page, @Param("reqPo") ReqPo reqPo);
	
	public Resp sumProductCount(@Param("reqPo") ReqPo reqPo);
}
