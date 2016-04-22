package com.froad.cbank.coremodule.normal.boss.support.report;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.thrift.service.CategorySaleService;
import com.data.thrift.vo.CategorySaleSummaryRequestVo;
import com.data.thrift.vo.CategorySaleSummaryResponseVo;
import com.data.thrift.vo.PageVo;
import com.froad.cbank.coremodule.framework.common.util.Arith;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.db.mysql.bean.Page;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.entity.BaseReportEntity;
import com.froad.cbank.coremodule.normal.boss.entity.OrderEntity;
import com.froad.cbank.coremodule.normal.boss.logic.ProductCategoryReportLogic;
import com.froad.cbank.coremodule.normal.boss.pojo.report.BaseReportReq;
import com.froad.cbank.coremodule.normal.boss.pojo.report.ProductCategoryReportRes;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;

/**
 * 类目销售报表
 * @author liaopeixin
 *	@date 2015年11月3日 下午3:32:07
 */
@Service
public class ProductCategoryReportSupport {

	@Resource
	private ProductCategoryReportLogic productCategoryReportLogic;
	@Resource
	private CategorySaleService.Iface categorySaleService;
	
	public Map<String,Object> list(BaseReportReq req) throws Exception{
		Map<String,Object> map=new HashMap<String,Object>();
		List<ProductCategoryReportRes> list=new ArrayList<ProductCategoryReportRes>();
		ProductCategoryReportRes ps=null;
		//封装数据库查询分页对象
		Page<OrderEntity> page = new Page<OrderEntity>();
		page.setPageNumber(req.getPageNumber());
		page.setPageSize(req.getPageSize());
		
		//封装请求对象
		BaseReportEntity entity = new BaseReportEntity();
		entity.setClientId(req.getClientId());
		entity.setStartTime(req.getStartTime());
		entity.setEndTime(req.getEndTime());
		entity.setBusinessType(req.getBusinessType());
		entity.setOrgCode(req.getOrgCode());
		
		com.froad.cbank.coremodule.normal.boss.pojo.Page p = new com.froad.cbank.coremodule.normal.boss.pojo.Page();
		
		//如果按日显示
		if(req.getDaily()==1){
			List<OrderEntity> clist=productCategoryReportLogic.getListByPage(page, entity);
			
			for (OrderEntity p1 : clist) {
				ps=new ProductCategoryReportRes();
				ps.setProductTotalCount(p1.getProductCount());
				ps.setCreateTime(DateUtil.formatDate(p1.getCreateTime(), false));
				//类目总销售额
				double productTotalPrice = Arith.div(Double.parseDouble(p1.getProductTotalPrice()), 1000d);
				ps.setProductTotalPrice(productTotalPrice);
				//平均类目价格=出单类目下出单商品销售金额/总类目数
				if(p1.getCategoryCount()==0){
					ps.setAvgCategoryPerPrice(0d);
				}else{
					ps.setAvgCategoryPerPrice(Arith.div(productTotalPrice, p1.getCategoryCount(),3));
				}
				//总类目数
				ps.setCategoryTotalCount(p1.getCategoryCount());
				list.add(ps);
			}
			BeanUtils.copyProperties(p, page);
		}else{
			
			CategorySaleSummaryRequestVo reqVo=new CategorySaleSummaryRequestVo();
			ps=new ProductCategoryReportRes();
			
			reqVo.setClientId(req.getClientId());
			reqVo.setBizType(req.getBusinessType());
			reqVo.setOrgCode(req.getOrgCode());
			//封装给后台的分页对象
			PageVo pageVo=new PageVo();
			pageVo.setBegDate(PramasUtil.DateFormat(req.getStartTime()));
			pageVo.setEndDate(PramasUtil.DateFormatEnd(req.getEndTime()));
			pageVo.setPageNumber(1);
			pageVo.setPageSize(1);
			reqVo.setPageVo(pageVo);
			CategorySaleSummaryResponseVo res=categorySaleService.queryCategorySaleSummary(reqVo);
			
			ps.setCreateTime(res.getDateTime());
			ps.setAvgCategoryPerPrice(res.getAvgCatagoryPrice());
			ps.setCategoryTotalCount(res.getTotalCatagoryCount());
			ps.setProductTotalCount(res.getTotalProductCount());
			ps.setProductTotalPrice(res.getTotalProductAmount());
			
			list.add(ps);
			//boss分页对象
			page.setPageCount(1);
			page.setPageNumber(1);
			page.setTotalCount(1);
		}
		map.put("list", list);
		map.put("page",p);
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
	public List<ProductCategoryReportRes> export(BaseReportReq req) throws Exception{
		List<ProductCategoryReportRes> list = new ArrayList<ProductCategoryReportRes>();
		
		Map<String, Object> map = list(req);
		com.froad.cbank.coremodule.normal.boss.pojo.Page page = (com.froad.cbank.coremodule.normal.boss.pojo.Page)map.get("page");
		list = (List<ProductCategoryReportRes>)map.get("list");
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
				list.addAll((List<ProductCategoryReportRes>)map1.get("list"));
			}
		}
		
		return list;		
	}
}
