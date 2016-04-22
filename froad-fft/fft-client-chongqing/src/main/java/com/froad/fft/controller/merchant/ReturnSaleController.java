package com.froad.fft.controller.merchant;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.bean.page.OrderDto.Direction;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.common.UserRoleType;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.ReturnSaleDetailDto;
import com.froad.fft.dto.ReturnSaleDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.dto.TransSecurityTicketDto;
import com.froad.fft.dto.ReturnSaleDto.Type;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.support.base.MerchantGroupUserSupport;
import com.froad.fft.support.base.MerchantOutletSupport;
import com.froad.fft.support.base.ReturnSaleDetailSupport;
import com.froad.fft.support.base.ReturnSaleSupport;
import com.froad.fft.support.base.SysUserRoleSupport;
import com.froad.fft.support.base.SysUserSupport;
import com.froad.fft.support.base.TransSecurityTicketSupport;
import com.froad.fft.support.base.TransSupport;
import com.froad.fft.util.NullValueCheckUtil;

/**
 * 退换货
 * @author FQ
 *
 */

@Controller
@RequestMapping("/merchant/return_sale")
public class ReturnSaleController extends BaseController {
	
	@Resource
	private ReturnSaleSupport returnSaleSupport;
	
	@Resource
	private ReturnSaleDetailSupport returnSaleDetailSupport;
	
	@Resource
	private SysUserSupport sysUserSupport; 
	
	@Resource
	private TransSupport transSupport; 
	
	@Resource
	private TransSecurityTicketSupport transSecurityTicketSupport;
	
	
	@Resource
	private MerchantGroupUserSupport merchantGroupUserSupport;
	
	@Resource
	private SysUserRoleSupport sysUserRoleSupport;
	
	@Resource
	private MerchantOutletSupport merchantOutletSupport;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SimpleDateFormat sdf1=new SimpleDateFormat("yyyyMMddHHmmss");
	
	/**
	 * 列表
	 */
	@RequestMapping(value = "/list")
	public String list(PageDto page,ReturnSaleDto returnSaleDto, ModelMap model) {
		page=this.selectByPage(page, returnSaleDto);
		SysUserDto sysUserDto=sysUserSupport.getCurrentSysUser();
		List<MerchantOutletDto> mlist=merchantOutletSupport.getOutletBySysUser(sysUserDto);
		model.addAttribute("page",page);
		model.addAttribute("merchant_outlet_list",mlist);
		model.addAttribute("outletId",returnSaleDto.getMerchantOutletId());
		model.addAttribute("type",returnSaleDto.getType());
		model.addAttribute("isAdmin", sysUserRoleSupport.isAdmin(sysUserDto.getId()));
		return "/merchant/return_sale/list";
	}
	
	/**
	 * 认证页面
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String auth() {
		return "/merchant/return_sale/verify";
	}
	
	
	/**
	 * 退换货认证
	 */
	@RequestMapping(value = "/returnSale_Authentic")
	public  @ResponseBody AjaxCallBackBean returnSale(HttpServletRequest req,String securitiesNo,String type,Integer quantity ,String reason) {
		
		TransSecurityTicketDto ticket=transSecurityTicketSupport.getBySecurityNo(securitiesNo);
		if(ticket!=null&&!ticket.getIsConsume()){
			return new AjaxCallBackBean(false, "认证失败，该券还未被消费！");
		}
		//根据券号查询交易和交易详情
		TransQueryDto transQueryDto= transSupport.getByTransId(ticket.getTransId());
		String sn=transQueryDto.getSn();//交易编号		
		logger.info("执行退换货认证,交易编号sn:"+sn);
		Long productId=transQueryDto.getTransDetailDto().get(0).getProductId();
		Integer buyNum=transQueryDto.getTransDetailDto().get(0).getQuantity();//购买数量
		//根据兑换券查询是否存在退货记录
		ReturnSaleDetailDto returnSaleDetailDto=new ReturnSaleDetailDto();
		returnSaleDetailDto.setSecuritiesNo(securitiesNo);
		List<ReturnSaleDetailDto> list=returnSaleDetailSupport.getByConditions(returnSaleDetailDto);
		//查询是否有过退货记录，计算之前退货数量的总和
		int returnNum=0;
		if(list!=null && list.size()>0){
			for(ReturnSaleDetailDto temp:list){
				//如果这条记录属于退货记录，则叠加退货数量
				if(Type.sale_return.equals(temp.getReturnSaleDto().getType())){
					returnNum+=temp.getQuantity();					
				}
			}
		}		
		//最大可退货数量
		int canReturn=buyNum-returnNum;
		//当前登录用户信息
		SysUserDto sysUserDto=sysUserSupport.getCurrentSysUser();
		if("0".equals(type)){ 
			//认证退货			
			if(canReturn>=quantity){				
				logger.info("退货认证成功,退货数量为"+quantity);
				MerchantGroupUserDto groupUserDto=merchantGroupUserSupport.getBySysUserId(sysUserDto.getId());
				ReturnSaleDto returnSaleDto=new ReturnSaleDto();
				returnSaleDto.setType(Type.sale_return);
				returnSaleDto.setMerchantOutletId(groupUserDto.getMerchantOutletId());
				returnSaleDto.setSysUserId(sysUserDto.getId());
				returnSaleDto.setReason(reason);
				long returnSaleId=returnSaleSupport.addReturnSale(returnSaleDto);
				logger.info("成功添加退货记录信息，记录ID："+returnSaleId);
				ReturnSaleDetailDto detailDto=new ReturnSaleDetailDto();
				detailDto.setReturnSaleId(returnSaleId);
				detailDto.setQuantity(quantity);
				detailDto.setProductId(productId);
				detailDto.setSecuritiesNo(securitiesNo);
				long returnSaleDetailId=returnSaleDetailSupport.addReturnSaleDetai(detailDto);
				logger.info("成功添加退货详情记录信息，记录ID："+returnSaleDetailId);
				return new AjaxCallBackBean(true, "认证成功!");
			}else{
				logger.info("退货认证失败，超过最大可退货数量,购买数量："+buyNum+",已存在退货数量："+returnNum+",认证输入退货数量："+quantity);
				String msg="认证失败,最多可退数量："+canReturn+"份";
				if(returnNum>0){					
					msg="认证失败,已退货"+returnNum+"份,可退数量："+canReturn+"份";
				}
				return new AjaxCallBackBean(false, msg);				
			}	
		}else if("1".equals(type)){	
			//判断当前交易是否已经全部退货（canReturn>0 则是未全部退货）
			if(canReturn==0){
				logger.info("换货认证失败,该券已经全部退货不可换货");
				return new AjaxCallBackBean(false, "认证失败,该券已经全部退货不可换货");
			}else if(quantity>canReturn){
				logger.info("换货认证失败,当前最大换货数量为 :"+canReturn+"份");
				return new AjaxCallBackBean(false, "换货认证失败,当前最大换货数量为 :"+canReturn+"份");
			}
			//认证换货
			if(buyNum>=quantity){
				logger.info("换货认证成功,换货数量为"+quantity);
				MerchantGroupUserDto groupUserDto=merchantGroupUserSupport.getBySysUserId(sysUserDto.getId());
				ReturnSaleDto returnDto=new ReturnSaleDto();
				returnDto.setType(Type.sale_swap);
				returnDto.setMerchantOutletId(groupUserDto.getMerchantOutletId());
				returnDto.setSysUserId(sysUserDto.getId());
				returnDto.setReason(reason);
				long returnSaleId=returnSaleSupport.addReturnSale(returnDto);
				logger.info("成功添加换货记录信息，记录ID："+returnSaleId);
				ReturnSaleDetailDto detailDto=new ReturnSaleDetailDto();
				detailDto.setReturnSaleId(returnSaleId);
				detailDto.setQuantity(quantity);
				detailDto.setProductId(productId);
				detailDto.setSecuritiesNo(securitiesNo);
				long returnSaleDetailId=returnSaleDetailSupport.addReturnSaleDetai(detailDto);
				logger.info("成功添加换货详情记录信息，记录ID："+returnSaleDetailId);
				return new AjaxCallBackBean(true, "认证成功!");
			}else{		
				logger.info("换货认证失败，换货数量大于购买数量");
				return new AjaxCallBackBean(false, "认证失败，换货数量大于购买数量");
			}			
		}
		return new AjaxCallBackBean(false, "认证失败，退换货类型选择有误");
	}
	
	/**
	  * 方法描述：下载退换货列表查询记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月23日 下午2:53:06
	  */
	@RequestMapping(value="/downLoad")
	public  void  downLoad(HttpServletResponse response,PageDto page,ReturnSaleDto returnSaleDto){
		page=this.selectByPage(page, returnSaleDto);
		List<ReturnSaleDto> list=new ArrayList<ReturnSaleDto>();
		for(int pageNumber=1;pageNumber<=page.getPageCount();pageNumber++){
			page.setPageNumber(pageNumber);
			page=this.selectByPage(page, returnSaleDto);
			list.addAll(page.getResultsContent());
		}
		//第一行表头
		String[] title={"编号","类型","数量","门店","操作员","原因","创建时间"};
		List<String[]> content=new ArrayList<String[]>();
		content.add(title);
		
		for(ReturnSaleDto temp:list){
			String[] tp=new String[]{
				temp.getSn(),
				convertType(temp.getType()),
				temp.getReturnSaleDetailDto()!=null?temp.getReturnSaleDetailDto().getQuantity().toString():"-",
				temp.getMerchantOutletDto()!=null?temp.getMerchantOutletDto().getName():"-",
				temp.getSysUserDto().getUsername(),
				temp.getReason(),			
				sdf.format(temp.getCreateTime())
			};
			content.add(tp);
		}
		String fileName=sdf1.format(new Date())+".csv";
		this.downloadCsv(response, content, fileName);			
	}	
	
	
	/**
	  * 方法描述：
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月24日 上午11:38:26
	  */
	public String convertType(Type type){
		if(Type.sale_return.equals(type)){
			return "退货";
		}else if(Type.sale_swap.equals(type)){
			return "换货";
		}else{
			return "未知";
		}
	}
	
	
	/**
	  * 方法描述：库存列表公用分页条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月24日 上午10:36:05
	  */
	public PageDto selectByPage(PageDto page,ReturnSaleDto returnSaleDto){
		if(page==null){
			page=new PageDto();
		}
		if(page.getPageFilterDto()==null){
			PageFilterDto pageFilterDto=new PageFilterDto();
			pageFilterDto.setStartTime(new Date());
			pageFilterDto.setEndTime(new Date());
			page.setPageFilterDto(pageFilterDto);
		}
		//排序
		OrderDto orderDto=new OrderDto();
		orderDto.setProperty("create_time");
		orderDto.setDirection(Direction.desc);	
		
		SysUserDto sysUserDto=sysUserSupport.getCurrentSysUser();
		//查询当前角色的门店信息
		List<MerchantOutletDto> mlist=merchantOutletSupport.getOutletBySysUser(sysUserDto);
		//获取当前用户角色下可以查询到的所有操作员
		List<SysUserDto> list=sysUserSupport.getOperatorsBySysUser(sysUserDto);
		List<Long> sysUserIds=new ArrayList<Long>();
		if(list!=null && list.size()>0){
			for(SysUserDto temp:list){
				sysUserIds.add(temp.getId());
			}
		}else{
			//没有任何操作员信息
			//此时不可省略，表示次查询条件为真，只是查询条件的值为空
			sysUserIds.add(null);
		}
		returnSaleDto.setSysUserIds(sysUserIds);
		
		page.setPageSize(10);
		page.getPageFilterDto().setProperty("create_time");
		page.setOrderDto(orderDto);
		page.getPageFilterDto().setFilterEntity(returnSaleDto);
		page=returnSaleSupport.getByPage(page);
		return page;
	}
	
}
