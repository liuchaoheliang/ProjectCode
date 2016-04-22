package com.froad.fft.controller.merchant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.bean.page.OrderDto.Direction;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.ReturnSaleDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.support.base.MerchantOutletSupport;
import com.froad.fft.support.base.ProductSupport;
import com.froad.fft.support.base.StockPileSupport;
import com.froad.fft.support.base.SysUserRoleSupport;
import com.froad.fft.support.base.SysUserSupport;
import com.froad.fft.util.NullValueCheckUtil;

/**
 * 库存
 * @author FQ
 *
 */

@Controller
@RequestMapping("/merchant/stock_pile")
public class StockPileController extends BaseController {
	
	@Resource
	private SysUserSupport sysUserSupport;
	
	@Resource
	private StockPileSupport stockPileSupport;
	
	@Resource
	private MerchantOutletSupport merchantOutletSupport;
	
	@Resource
	private SysUserRoleSupport sysUserRoleSupport;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public String list(PageDto page,StockPileDto stockPileDto ,ModelMap model) {				
		page=this.selectByPage(page, stockPileDto);
		List<MerchantOutletDto> list=merchantOutletSupport.getOutletBySysUser(sysUserSupport.getCurrentSysUser());
		model.addAttribute("page",page);
		model.addAttribute("merchant_outlet_list",list);
		model.addAttribute("outletId",stockPileDto.getMerchantOutletId());
		model.addAttribute("isAdmin", sysUserRoleSupport.isAdmin(sysUserSupport.getCurrentSysUser().getId()));
		return "/merchant/stock_pile/list";
	}
	
	/**
	  * 方法描述：下载库存列表查询记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月23日 下午2:53:06
	  */
	@RequestMapping(value="/downLoad")
	public  void  downLoad(HttpServletResponse response,PageDto page,StockPileDto stockPileDto){
		page=this.selectByPage(page, stockPileDto);		
		List<StockPileDto> list=new ArrayList<StockPileDto>();
		for(int pageNumber=1;pageNumber<=page.getPageCount();pageNumber++){
			page.setPageNumber(pageNumber);
			page=this.selectByPage(page, stockPileDto);
			list.addAll(page.getResultsContent());
		}
		//第一行表头
		String[] title={"商品名称","门店名称","实时库存数量","总库存数量","最后入库时间","最后出库时间"};
		List<String[]> content=new ArrayList<String[]>();
		content.add(title);
		
		for(StockPileDto temp:list){
			String[] tp=new String[]{
				temp.getProductDto().getName(),
				temp.getMerchantOutletDto().getName(),
				temp.getQuantity().toString(),
				temp.getTotalQuantity().toString(),
				sdf.format(temp.getLastIncomeTime()),
				temp.getLastOutcomeTime()!=null?sdf.format(temp.getLastOutcomeTime()):"-"
			};
			content.add(tp);
		}
		String fileName=sdf1.format(new Date())+".csv";
		this.downloadCsv(response, content, fileName);			
	}	
	
	
	
	/**
	  * 方法描述：库存列表公用分页条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月24日 上午10:36:05
	  */
	public PageDto selectByPage(PageDto page,StockPileDto stockPileDto ){
		if(page==null){
			page=new PageDto();
		}
		//查询当前角色的门店信息
		List<MerchantOutletDto> list=merchantOutletSupport.getOutletBySysUser(sysUserSupport.getCurrentSysUser());
		List<Long> ids=new ArrayList<Long>();
		if(list!=null && list.size()>0){
			for(MerchantOutletDto temp:list){
				ids.add(temp.getId());
			}			
		}else{
			//此时不可省略，表示次查询条件为真，只是查询条件的值为空
			ids.add(null);
		}
		if(page.getPageFilterDto()==null){
			PageFilterDto pageFilterDto=new PageFilterDto();
			pageFilterDto.setStartTime(new Date());
			pageFilterDto.setEndTime(new Date());
			page.setPageFilterDto(pageFilterDto);
			stockPileDto.setMerchantOutletIds(ids);
		}
		//排序
		OrderDto orderDto=new OrderDto();
		orderDto.setProperty("create_time");
		orderDto.setDirection(Direction.desc);	
		//当门店为空则为全部门店
		if(stockPileDto.getMerchantOutletId()==null){
			stockPileDto.setMerchantOutletIds(ids);
		}
		page.getPageFilterDto().setProperty("last_income_time");		
		page.getPageFilterDto().setFilterEntity(stockPileDto);
		page.setOrderDto(orderDto);
		page.setPageSize(10);
		page=stockPileSupport.getByPage(page);
		return page;
	};
	
}
	
	
