package com.froad.db.mysql.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.froad.db.mysql.bean.Page;
import com.froad.po.req.ReqPo;
import com.froad.po.resp.Resp;

public interface MerchantMapper {

	public List<Resp> findReportMerchantInfoByPage(Page page,
			@Param("reqPo") ReqPo reqPo);

	public Integer getMerchantCount(Page page, @Param("reqPo") ReqPo reqPo);
	
	public Resp sumMerchantCount(@Param("reqPo") ReqPo reqPo);
}
