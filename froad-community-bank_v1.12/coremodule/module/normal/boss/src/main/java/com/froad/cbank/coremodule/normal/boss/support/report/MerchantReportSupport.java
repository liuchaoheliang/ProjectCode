package com.froad.cbank.coremodule.normal.boss.support.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.thrift.service.MerchantSaleService;
import com.data.thrift.vo.MerchantSaleSummaryRequestVo;
import com.data.thrift.vo.MerchantSaleSummaryResponseVo;
import com.data.thrift.vo.PageVo;
import com.froad.cbank.coremodule.framework.common.util.Arith;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.MerchantReportLogic;
import com.froad.cbank.coremodule.normal.boss.pojo.report.BaseReportReq;
import com.froad.cbank.coremodule.normal.boss.pojo.report.MerchantReportRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
/**
 * 商户销售统计报表
 * @author liaopeixin
 *	@date 2015年11月5日 上午10:35:55
 */
@Service
public class MerchantReportSupport {

	@Resource
	private MerchantReportLogic merchantReportLogic;
	@Resource
	private MerchantSaleService.Iface merchantSaleService;
	
	public Map<String,Object> list(BaseReportReq req) throws Exception{
		Map<String,Object> map =new HashMap<String,Object>();
		List<MerchantReportRes> list =new ArrayList<MerchantReportRes>();
		MerchantReportRes m=null;
		BaseReportEntity entity=new BaseReportEntity();
		entity.setBusinessType(req.getBusinessType());
		entity.setClientId(req.getClientId());
		entity.setStartTime(req.getStartTime());
		entity.setEndTime(req.getEndTime());
		entity.setOrgCode(req.getOrgCode());
		
		Page<OrderEntity> sqlpage=new Page<OrderEntity>();
		sqlpage.setPageNumber(req.getPageNumber());
		sqlpage.setPageSize(req.getPageSize());
		
		com.froad.cbank.coremodule.normal.boss.pojo.Page page =new com.froad.cbank.coremodule.normal.boss.pojo.Page();
			
		if(req.getDaily()==1){
			List<OrderEntity> mlist=merchantReportLogic.getListByPage(sqlpage, entity);
			for (OrderEntity m1 : mlist) {
				m=new MerchantReportRes();
				//商品销售总金额
				double productTotalPrice = Arith.div(Double.parseDouble(m1.getProductTotalPrice()), 1000d);
				//订单总金额
				double orderTotalPrice=Arith.div(Double.parseDouble(m1.getOrderTotalPrice()), 1000d);
				//平均订单金额=总出单商户名下订单金额/总出单商户名下订单数量
				double avgOrderPrice=0d;
				if(m1.getOrderCount()!=0){
					avgOrderPrice=Arith.div(orderTotalPrice, m1.getOrderCount(),3);
				}
				//平均商品金额=总出单商户名下出单商品金额/总出单商户名下出单商品金额
				double avgProductPrice=0d;
				if( m1.getProductCount()!=0){
					avgProductPrice=Arith.div(productTotalPrice, m1.getProductCount(), 3);	
				}			
				m.setAvgOrderPrice(avgOrderPrice);
				m.setCreateTime(DateUtil.formatDate(m1.getCreateTime(), false));
				m.setMerchantTotalCount(m1.getMerchantCount());
				m.setOrderTotalCount(m1.getOrderCount());
				m.setOrderTotalPrice(orderTotalPrice);
				m.setProductTotalCount(m1.getProductCount());
				m.setProductTotalPrice(productTotalPrice);
				m.setAvgProductPrice(avgProductPrice);
				//平均发货周期
				
				list.add(m);
			}
			BeanUtils.copyProperties(page, sqlpage);
		}else{
			m=new MerchantReportRes();
			MerchantSaleSummaryRequestVo reqVo=new MerchantSaleSummaryRequestVo();

			reqVo.setClientId(req.getClientId());
			reqVo.setOrgCode(req.getOrgCode());
			reqVo.setBizType(req.getBusinessType());
			
			PageVo pageVo=new PageVo();
			pageVo.setBegDate(PramasUtil.DateFormat(req.getStartTime()));
			pageVo.setEndDate(PramasUtil.DateFormatEnd(req.getEndTime()));
			pageVo.setPageNumber(1);
			pageVo.setPageSize(1);
			
			reqVo.setPageVo(pageVo);
			MerchantSaleSummaryResponseVo res=merchantSaleService.queryMerchantSaleSummary(reqVo);
			//平均订单金额
			m.setAvgOrderPrice(res.getAvgOrderAmount());
			//平均商品金额
			m.setAvgProductPrice(res.getAvgProductAmount());
			//时间
			m.setCreateTime(res.getDateTime());
			//总订单数量
			m.setOrderTotalCount(res.getTotalOrderCount());
			//总订单金额
			m.setOrderTotalPrice(res.getTotalOrderAmount());
			//总商品数量
			m.setProductTotalCount(res.getTotalProductCount());
			//总商品金额
			m.setProductTotalPrice(res.getTotalProductAmount());
			//总商户数
			m.setMerchantTotalCount(res.getTotalMerchantCount());
			
			list.add(m);
			
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
	public List<MerchantReportRes> export(BaseReportReq req) throws Exception{
		List<MerchantReportRes> list = new ArrayList<MerchantReportRes>();
		
		Map<String, Object> map = list(req);
		com.froad.cbank.coremodule.normal.boss.pojo.Page page = (com.froad.cbank.coremodule.normal.boss.pojo.Page)map.get("page");
		list = (List<MerchantReportRes>)map.get("list");
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
				list.addAll((List<MerchantReportRes>)map1.get("list"));
			}
		}
		
		return list;		
	}
}
