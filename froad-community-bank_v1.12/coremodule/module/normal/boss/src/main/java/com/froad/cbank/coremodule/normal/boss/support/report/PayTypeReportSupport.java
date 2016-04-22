package com.froad.cbank.coremodule.normal.boss.support.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.thrift.service.OrderPayTypeService;
import com.data.thrift.vo.PageVo;
import com.data.thrift.vo.PayTypeSummaryRequestVo;
import com.data.thrift.vo.PayTypeSummaryResponseVo;
import com.froad.cbank.coremodule.framework.common.util.Arith;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.PayTypeReportLogic;
import com.froad.cbank.coremodule.normal.boss.pojo.report.BaseReportReq;
import com.froad.cbank.coremodule.normal.boss.pojo.report.PayTypeReportRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;

/**
 * 支付方式分析报表
 * @author liaopeixin
 *	@date 2015年11月4日 上午9:23:10
 */
@Service
public class PayTypeReportSupport {
	@Resource
	private PayTypeReportLogic payTypeReportLogic;
	@Resource
	private OrderPayTypeService.Iface orderPayTypeService;

	public Map<String,Object> list(BaseReportReq req) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		List<PayTypeReportRes> list =new ArrayList<PayTypeReportRes>();
		PayTypeReportRes res=null;
		//封装请求参数
		BaseReportEntity entity=new BaseReportEntity();
		entity.setBusinessType(req.getBusinessType());
		entity.setClientId(req.getClientId());
		entity.setStartTime(req.getStartTime());
		entity.setEndTime(req.getEndTime());
		entity.setOrgCode(req.getOrgCode());
		
		//封装数据库分页请求参数
		Page<OrderEntity> sqlpage=new Page<OrderEntity>();
		sqlpage.setPageNumber(req.getPageNumber());
		sqlpage.setPageSize(req.getPageSize());
		
		com.froad.cbank.coremodule.normal.boss.pojo.Page page= new com.froad.cbank.coremodule.normal.boss.pojo.Page();
		
		if(req.getDaily()==1){
			List<OrderEntity> plist =payTypeReportLogic.getListByPage(sqlpage, entity);

			for (OrderEntity p : plist) {
				res=new PayTypeReportRes();
				
				//商品销售总金额
				double productTotalPrice = Arith.div(Double.parseDouble(p.getProductTotalPrice()), 1000d);
				//订单总金额
				double orderTotalPrice=Arith.div(Double.parseDouble(p.getOrderTotalPrice()), 1000d);				
				//平均订单金额=总下单支付方式下成交订单金额/总下单支付方式下成交订单数量
				double avgOrderPrice=0d;
				if( p.getOrderCount()!=0){
					avgOrderPrice=Arith.div(orderTotalPrice, p.getOrderCount(),3);
				}		
				//客单价=总下单支付方式下成交订单金额/总下单支付方式下下单会员数量 
				double perPrice=0d;
				if(p.getBuyCount()!=0)
					perPrice=Arith.div(orderTotalPrice, p.getBuyCount(), 3);
				//支付方式个数
				res.setPayTypeTotalCount(p.getPayTypeCount());
				res.setAvgOrderPrice(avgOrderPrice);
				res.setBuyUserTotalCount( p.getBuyCount());
				res.setCreateTime(DateUtil.formatDate(p.getCreateTime(), false));
				res.setOrderTotalCount(p.getOrderCount());
				res.setOrderTotalPrice(orderTotalPrice);
				res.setPerPrice(perPrice);
				res.setProductTotalCount(p.getProductCount());
				res.setProductTotalPrice(productTotalPrice);
				
				list.add(res);
			}
			
			BeanUtils.copyProperties(page, sqlpage);
		}else{
			res=new PayTypeReportRes();
			PayTypeSummaryRequestVo reqVo=new PayTypeSummaryRequestVo();

			reqVo.setClientId(req.getClientId());
			reqVo.setBizType(req.getBusinessType());
			reqVo.setOrgCode(req.getOrgCode());
			
			PageVo pageVo=new PageVo();
			pageVo.setBegDate(PramasUtil.DateFormat(req.getStartTime()));
			pageVo.setEndDate(PramasUtil.DateFormatEnd(req.getEndTime()));
			pageVo.setPageNumber(1);
			pageVo.setPageSize(1);
			
			reqVo.setPageVo(pageVo);
			PayTypeSummaryResponseVo res1=orderPayTypeService.queryPayTypeSummary(reqVo);
		
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
			//支付方式个数
			res.setPayTypeTotalCount(res1.getPayTypeCount());
			//客单价
			res.setPerPrice(res1.getAmountPerMember());
			//购买会员总数
			res.setBuyUserTotalCount(res1.getBuyMemberCount());
			
			list.add(res);
			
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
	 *	@date 2015年11月6日 上午9:51:53
	 */
	@SuppressWarnings("unchecked")
	public List<PayTypeReportRes> export(BaseReportReq req) throws Exception{
		List<PayTypeReportRes> list = new ArrayList<PayTypeReportRes>();
		
		Map<String, Object> map = list(req);
		com.froad.cbank.coremodule.normal.boss.pojo.Page page = (com.froad.cbank.coremodule.normal.boss.pojo.Page)map.get("page");
		list = (List<PayTypeReportRes>)map.get("list");
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
				list.addAll((List<PayTypeReportRes>)map1.get("list"));
			}
		}
		
		return list;		
	}
}
