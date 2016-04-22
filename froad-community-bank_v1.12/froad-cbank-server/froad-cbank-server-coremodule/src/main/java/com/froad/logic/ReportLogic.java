package com.froad.logic;

import java.util.List;

import org.apache.thrift.TException;

import com.froad.db.mysql.bean.Page;
import com.froad.po.req.DefineTaskReq;
import com.froad.po.req.ReqPo;
import com.froad.po.resp.DefineTaskResp;
import com.froad.po.resp.Resp;

public interface ReportLogic {

	public Page<Resp> findReportByPage(Page<Resp> page, ReqPo req);

	public Page<Resp> getReportByPage(Page<Resp> page, ReqPo req);
	
	public Page<DefineTaskResp> getDefineTaskByPage(Page<DefineTaskResp> page,DefineTaskReq req);
	
	public List<List<String>> toExcel(Page<Resp> page, ReqPo req);
	
	public List<List<String>> toExcelByPage(Page<Resp> page, ReqPo req) throws TException;
	
	public List<String> toExcelHeader(ReqPo req);
	
	public Resp sumAmountOrOrder(ReqPo req);
	
	public Resp sumRefundAmountOrOrder(ReqPo req);
	
	public Resp sumMerchantCount(ReqPo reqPo);
	
	public Resp sumProductCount(ReqPo reqPo);
}
