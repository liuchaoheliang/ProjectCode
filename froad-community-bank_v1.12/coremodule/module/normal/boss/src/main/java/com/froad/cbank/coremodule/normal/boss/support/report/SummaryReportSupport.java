package com.froad.cbank.coremodule.normal.boss.support.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.thrift.service.CBankSaleService;
import com.data.thrift.vo.CBankSaleSummaryRequestVo;
import com.data.thrift.vo.CBankSaleSummaryResponseVo;
import com.data.thrift.vo.PageVo;
import com.froad.cbank.coremodule.framework.common.util.Arith;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.SummaryReportLogic;
import com.froad.cbank.coremodule.normal.boss.pojo.report.BaseReportReq;
import com.froad.cbank.coremodule.normal.boss.pojo.report.SummaryReportRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;

/**
 * 总分析报表
 * @ClassName SummaryReportSupport
 * @author zxl
 * @date 2015年11月3日 上午10:30:08
 */
@Service
public class SummaryReportSupport {
	
	@Resource
	SummaryReportLogic summaryReportLogic;
	@Resource
	CBankSaleService.Iface cBankSaleService;
	/**
	 * 查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年11月3日 上午10:30:19
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> list(BaseReportReq req) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		Page<OrderEntity> page = new Page<OrderEntity>();
		page.setPageNumber(req.getPageNumber());
		page.setPageSize(req.getPageSize());
		
		BaseReportEntity entity = new BaseReportEntity();
		entity.setClientId(req.getClientId());
		entity.setStartTime(req.getStartTime());
		entity.setEndTime(req.getEndTime());
		entity.setOrgCode(req.getOrgCode());
		
		List<SummaryReportRes> list = new ArrayList<SummaryReportRes>();
		com.froad.cbank.coremodule.normal.boss.pojo.Page p = new com.froad.cbank.coremodule.normal.boss.pojo.Page();
		
		if(req.getDaily() == 1){
			List<OrderEntity> l = summaryReportLogic.getListByPage(page, entity);
			for(OrderEntity e : l){
				SummaryReportRes r = new SummaryReportRes();
				r.setProductCount(e.getProductCount());
				r.setOrderCount(e.getOrderCount());
				double orderTotalPrice = Arith.div(Double.parseDouble(e.getOrderTotalPrice()), 1000d);
				double productTotalPrice = Arith.div(Double.parseDouble(e.getProductTotalPrice()), 1000d);
				r.setOrderTotalPrice(orderTotalPrice);
				r.setProductTotalPrice(productTotalPrice);
				r.setCreateTime(DateUtil.formatDate(e.getCreateTime(), false));
				r.setOrderAvgPrice(e.getOrderCount()==0?0:Arith.div(orderTotalPrice, (double)e.getOrderCount(),3));
				r.setProductAvgPrice(e.getProductCount()==0?0:Arith.div(productTotalPrice, (double)e.getProductCount(),3));
				r.setBuyCount(e.getBuyCount());
				r.setBuyAvg(e.getBuyCount()==0?0:Arith.div(orderTotalPrice, (double)e.getBuyCount(),3));
				r.setTypeCount(e.getTypeCount());
				list.add(r);
			}
			BeanUtils.copyProperties(p, page);
		}else{
			SummaryReportRes r = new SummaryReportRes();
			
			CBankSaleSummaryRequestVo vo = new CBankSaleSummaryRequestVo();
			vo.setClientId(req.getClientId());
			vo.setOrgCode(req.getOrgCode());
			PageVo pageVo = new PageVo();
			pageVo.setBegDate(PramasUtil.DateFormat(req.getStartTime()));
			pageVo.setEndDate(PramasUtil.DateFormatEnd(req.getEndTime()));
			pageVo.setPageNumber(1);
			pageVo.setPageSize(1);
			vo.setPageVo(pageVo);
			
			CBankSaleSummaryResponseVo resp = cBankSaleService.queryCBankSaleSummary(vo);
			r.setCreateTime(resp.getDateTime());
			r.setTypeCount(resp.getBizTypeCount());
			r.setBuyCount(resp.getBuyMemberCount());
			r.setOrderCount(resp.getTotalOrderCount());
			r.setOrderTotalPrice(resp.getTotalOrderAmount());
			r.setProductCount(resp.getTotalProductCount());
			r.setProductTotalPrice(resp.getTotalProductAmount());
			r.setOrderAvgPrice(resp.getAvgOrderAmount());
			r.setBuyAvg(resp.getAmountPerMember());
			
			list.add(r);
			p.setPageCount(1);
			p.setPageNumber(1);
			p.setTotalCount(1);
			
		}
		
		map.put("list", list);
		map.put("page", p);
		return map;
	}
	
	/**
	 * 数据导出
	 * @tilte export
	 * @author zxl
	 * @date 2015年11月3日 下午5:54:00
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SummaryReportRes> export(BaseReportReq req) throws Exception{
		List<SummaryReportRes> list = new ArrayList<SummaryReportRes>();
		
		HashMap<String, Object> map = list(req);
		com.froad.cbank.coremodule.normal.boss.pojo.Page page = (com.froad.cbank.coremodule.normal.boss.pojo.Page)map.get("page");
		list = (List<SummaryReportRes>)map.get("list");
		LogCvt.info("totalCount:"+page.getTotalCount());
		if(page.getTotalCount()>req.getPageSize()){
			int s=(page.getTotalCount())/req.getPageSize(); //需要再查询次数
			if(page.getTotalCount()%req.getPageSize()==0){ //倍数减1
				s--;
			}
			int pagenum=1;
			for(int i=1;i<=s;i++){
				req.setPageNumber(++pagenum);
				HashMap<String, Object> map1 = list(req);
				list.addAll((List<SummaryReportRes>)map1.get("list"));
			}
		}
		
		return list;
		
	}
}
