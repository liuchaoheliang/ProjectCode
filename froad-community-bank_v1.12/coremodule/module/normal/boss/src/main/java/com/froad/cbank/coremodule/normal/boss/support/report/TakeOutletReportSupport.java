package com.froad.cbank.coremodule.normal.boss.support.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.thrift.service.OutletAnalysisService;
import com.data.thrift.vo.OutletAnalysisSummaryRequestVo;
import com.data.thrift.vo.OutletAnalysisSummaryResponseVo;
import com.data.thrift.vo.PageVo;
import com.froad.cbank.coremodule.framework.common.util.Arith;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.TakeOutletReportLogic;
import com.froad.cbank.coremodule.normal.boss.pojo.report.BaseReportReq;
import com.froad.cbank.coremodule.normal.boss.pojo.report.TakeOutletReportRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
/**
 * 提货网点分析报表 support
 * @author liaopeixin
 *	@date 2015年11月4日 下午5:09:58
 */
@Service
public class TakeOutletReportSupport {

	@Resource
	private TakeOutletReportLogic takeOutletReportLogic;
	@Resource
	private OutletAnalysisService.Iface outletAnalysisService;
	
	public Map<String,Object> list(BaseReportReq req) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
		List<TakeOutletReportRes> list =new ArrayList<TakeOutletReportRes>();
		TakeOutletReportRes res=null;
		BaseReportEntity entity=new BaseReportEntity();
		entity.setBusinessType(req.getBusinessType());
		entity.setClientId(req.getClientId());
		entity.setStartTime(req.getStartTime());
		entity.setEndTime(req.getEndTime());
		entity.setOrgCode(req.getOrgCode());
		
		Page<OrderEntity> sqlpage =new Page<OrderEntity>();
		sqlpage.setPageNumber(req.getPageNumber());
		sqlpage.setPageSize(req.getPageSize());
		
		com.froad.cbank.coremodule.normal.boss.pojo.Page page= new com.froad.cbank.coremodule.normal.boss.pojo.Page();
		
		if(req.getDaily()==1){
			List<OrderEntity> tlist =takeOutletReportLogic.getListByPage(sqlpage, entity);
			for (OrderEntity t : tlist) {
				res=new TakeOutletReportRes();
				
				//商品销售总金额
				double productTotalPrice = Arith.div(Double.parseDouble(t.getProductTotalPrice()), 1000d);
				//订单总金额
				double orderTotalPrice=Arith.div(Double.parseDouble(t.getOrderTotalPrice()), 1000d);
				//平均订单金额=总下单支付方式下成交订单金额/总下单支付方式下成交订单数量
				double avgOrderPrice=0d;
				if( t.getOrderCount()!=0){
					avgOrderPrice=Arith.div(orderTotalPrice, t.getOrderCount(),3);
				}		
				//客单价=总下单支付方式下成交订单金额/总下单支付方式下下单会员数量 
				double perPrice=0d;
				if(t.getBuyCount()!=0)
					perPrice=Arith.div(orderTotalPrice, t.getBuyCount(), 3);
				//提货网点数量
				res.setTakeOutletCount(String.valueOf(t.getOutletCount()));
				
				res.setAvgOrderPrice(avgOrderPrice);
				res.setBuyUserTotalCount(t.getBuyCount());
				res.setCreateTime(DateUtil.formatDate(t.getCreateTime(), false));
				res.setOrderTotalCount(t.getOrderCount());
				res.setOrderTotalPrice(orderTotalPrice);
				res.setPerPrice(perPrice);
				res.setProductTotalCount(t.getProductCount());
				res.setProductTotalPrice(productTotalPrice);
				
				
				list.add(res);
			}
			BeanUtils.copyProperties(page, sqlpage);
		}else{
			res=new TakeOutletReportRes();
			OutletAnalysisSummaryRequestVo reqVo =new OutletAnalysisSummaryRequestVo();
			
			reqVo.setClientId(req.getClientId());
			reqVo.setOrgCode(req.getOrgCode());
			//封装给后台的分页对象
			PageVo pageVo=new PageVo();
			pageVo.setBegDate(PramasUtil.DateFormat(req.getStartTime()));
			pageVo.setEndDate(PramasUtil.DateFormatEnd(req.getEndTime()));
			pageVo.setPageNumber(1);
			pageVo.setPageSize(1);
			reqVo.setPageVo(pageVo);
			
			OutletAnalysisSummaryResponseVo res1=outletAnalysisService.queryOutletAnalysisSummary(reqVo);
			//时间
			res.setCreateTime(res1.getDateTime());
			//总订单数量
			res.setOrderTotalCount(res1.getTotalOrderCount());
			//总订单金额
			res.setOrderTotalPrice(res1.getTotalOrderAmount());
			//平均订单金额
			res.setAvgOrderPrice(res1.getAvgOrderAmount());
			//总商品数量
			res.setProductTotalCount(res1.getTotalProductCount());
			//总商品金额
			res.setProductTotalPrice(res1.getTotalProductAmount());
			//提货网点个数
			res.setTakeOutletCount(String.valueOf(res1.getOutletCount()));
			//客单价
			res.setPerPrice(res1.getAmountPerMember());
			//购买会员总数
			res.setBuyUserTotalCount(res1.getBuyMemberCount());
			
			list.add(res);
			//boss分页对象
			page.setPageCount(1);
			page.setPageNumber(1);
			page.setTotalCount(1);
		}
		map.put("page", page);
		map.put("list", list);
		return map;
	}
	
	/**
	 * 数据导出
	 * @param req
	 * @return
	 * @throws Exception
	 * @author liaopeixin
	 *	@date 2015年11月6日 上午9:54:57
	 */
	@SuppressWarnings("unchecked")
	public List<TakeOutletReportRes> export(BaseReportReq req) throws Exception{
		List<TakeOutletReportRes> list = new ArrayList<TakeOutletReportRes>();
		
		Map<String, Object> map = list(req);
		com.froad.cbank.coremodule.normal.boss.pojo.Page page = (com.froad.cbank.coremodule.normal.boss.pojo.Page)map.get("page");
		list = (List<TakeOutletReportRes>)map.get("list");
		LogCvt.info("totalCount:"+page.getTotalCount());
		if(page.getTotalCount()>req.getPageSize()){
			int s=(page.getTotalCount())/req.getPageSize(); //需要再查询次数
			if(page.getTotalCount()%req.getPageSize()==0){ //倍数减1
				s--;
			}
			int pagenum=1;
			for(int i=1;i<=s;i++){
				req.setPageNumber(++pagenum);
				Map<String, Object> map1 = list(req);
				list.addAll((List<TakeOutletReportRes>)map1.get("list"));
			}
		}
		
		return list;		
	}
	
}
