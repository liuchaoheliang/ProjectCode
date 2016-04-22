package com.froad.fft.controller.merchant;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.hsqldb.util.CSVWriter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.csvreader.CsvWriter;
import com.froad.fft.api.service.OutletPresellDeliveryExportService;
import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.bean.Message.Type;
import com.froad.fft.bean.SecurityResult;
import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.bean.page.OrderDto.Direction;
import com.froad.fft.common.SessionKey;
import com.froad.fft.common.UserRoleType;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.OutletPresellDeliveryDto;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.dto.ProductDto;
import com.froad.fft.dto.ProductPresellDeliveryDto;
import com.froad.fft.dto.StockPileDto;
import com.froad.fft.dto.StockPileLogDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.dto.TransDetailDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.dto.TransSecurityTicketDto;
import com.froad.fft.enums.trans.TransState;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.support.base.MerchantGroupUserSupport;
import com.froad.fft.support.base.MerchantOutletSupport;
import com.froad.fft.support.base.OutletPresellDeliverySupport;
import com.froad.fft.support.base.ProductPresellSupport;
import com.froad.fft.support.base.StockPileLogSupport;
import com.froad.fft.support.base.StockPileSupport;
import com.froad.fft.support.base.SysUserRoleSupport;
import com.froad.fft.support.base.SysUserSupport;
import com.froad.fft.support.base.TransSecurityTicketSupport;
import com.froad.fft.support.base.TransSupport;
import com.froad.fft.util.HSSFWorkbookUtil;

/**
 * 预售
 * @author FQ
 *
 */

@Controller("merchantPresellController")
@RequestMapping("/merchant/presell")
public class PresellController extends BaseController {
	
	@Resource
	private SysUserSupport sysUserSupport;
	
	@Resource
	private TransSupport transSupport; 
	
	@Resource
	private MerchantGroupUserSupport merchantGroupUserSupport;
	
	@Resource
	private TransSecurityTicketSupport transSecurityTicketSupport;
	
	@Resource
	private ProductPresellSupport productPresellSupport; 
	
	@Resource
	private StockPileSupport stockPileSupport;
	
	@Resource
	private StockPileLogSupport stockPileLogSupport;
	
	@Resource
	private OutletPresellDeliverySupport outletPresellDeliverySupport;
	
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
	public String list(HttpServletRequest req,PageDto page,TransQueryDto trans,Long merchantOutletId, ModelMap model) {
		page=this.selectByPage(page, trans, merchantOutletId);
		List<MerchantOutletDto> mlist=merchantOutletSupport.getOutletBySysUser(sysUserSupport.getCurrentSysUser());
		model.addAttribute("page",page);
		model.addAttribute("merchant_outlet_list",mlist);
		model.addAttribute("outletId",merchantOutletId);
		model.addAttribute("isAdmin", sysUserRoleSupport.isAdmin(sysUserSupport.getCurrentSysUser().getId()));
		return "/merchant/presell/list";
	}
	
	/**
	 * 认证页面 
	 */
	@RequestMapping(value = "/verify", method = RequestMethod.GET)
	public String auth() {
		return "/merchant/presell/verify";
	}
	
	/**
	 * 根据券号获取券信息（共用查询）提货认证和退换货认证
	 */
	@RequestMapping(value = "/securitiesNo_detail")
	public  @ResponseBody AjaxCallBackBean securitiesNoDetail(HttpServletRequest req,String  securitiesNo) {
		if(!"".equals(securitiesNo)){
			//获取当前用户信息
			SysUserDto sysUser=(SysUserDto)sysUserSupport.getCurrentSysUser();	
			MerchantGroupUserDto merchantGroupUserDto=merchantGroupUserSupport.getBySysUserId(sysUser.getId());	
			
			TransSecurityTicketDto ticket=new TransSecurityTicketDto();
			ticket.setSecuritiesNo(securitiesNo);
			ticket.setMerchantId(merchantGroupUserDto.getMerchantId());
			List<TransSecurityTicketDto> list=transSecurityTicketSupport.getByConditions(ticket);
			if(list!=null && list.size()>0){
				ticket=list.get(0);
				Long transId=ticket.getTransId();
				TransQueryDto transQueryDto= transSupport.getByTransId(transId);
				PresellDeliveryDto deliveryDto= productPresellSupport.getProductPresellDeliveryById(transQueryDto.getDeliveryId());
				//组装展示数据
				SecurityResult result=new SecurityResult();
				result.setUserName(transQueryDto.getDeliveryName());
				result.setPhone(transQueryDto.getPhone());
				result.setSecurityNo(ticket.getSecuritiesNo());
				result.setDeliveryName(deliveryDto.getName());
				result.setProductName(transQueryDto.getTransDetailDto().get(0).getProductName());
				result.setNumber(transQueryDto.getTransDetailDto().get(0).getQuantity());
				result.setCreateTime(sdf.format(transQueryDto.getCreateTime()));
				result.setIsConsume(ticket.getIsConsume());
				result.setPayMethodShow(transQueryDto.getPayMethod());
				return new AjaxCallBackBean(true, "查找到券信息",result);
			}
		}
		logger.info("认证查询，券号不存在");
		return new AjaxCallBackBean(false, "不存在该券号，请重新输入");
	}
	
	
	
	/**
	  * 方法描述：兑换券认证，认证成功-在对应门店的商品库存实时数量上减掉认证券交易所购买的数量
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月8日 上午10:30:03
	  */
	@RequestMapping(value="/securitiesNo_authentic")
	public  @ResponseBody AjaxCallBackBean authentic(HttpServletRequest req,String  securitiesNo){
		if(!"".equals(securitiesNo)){
			TransSecurityTicketDto ticket=transSecurityTicketSupport.getBySecurityNo(securitiesNo);
			//判断是否存在
			if(ticket!=null){
				//判断是否已经消费
				if(!ticket.getIsConsume()){
					Date now=new Date();
					//判断是否过期
					if(now.before(ticket.getExpireTime())){
						//获取当前登录的系统用户
						SysUserDto sysUser=(SysUserDto)sysUserSupport.getCurrentSysUser();
						ticket.setSysUserId(sysUser.getId());
						ticket.setIsConsume(true);
						ticket.setConsumeTime(now);
						boolean flag=transSecurityTicketSupport.updateById(ticket);
						if(flag){
							try {
								//根据券号查询交易和交易详情
								TransQueryDto transQueryDto= transSupport.getByTransId(ticket.getTransId());
								Long productId=transQueryDto.getTransDetailDto().get(0).getProductId();
								//查询门店关联
								logger.info("认证成功，根据提货点查询门店信息，提货点ID"+transQueryDto.getDeliveryId());
								OutletPresellDeliveryDto outletPresellDeliveryDto=new OutletPresellDeliveryDto();
								outletPresellDeliveryDto.setPresellDeliveryId(transQueryDto.getDeliveryId());	
								OutletPresellDeliveryDto PresellDeliveryDto=outletPresellDeliverySupport.getByConditions(outletPresellDeliveryDto).get(0);
								Long outletId=PresellDeliveryDto.getMerchantOutletId();
								//设置属性（提货点和门店）
								StockPileDto stockPileDto=new StockPileDto();
								stockPileDto.setProductId(productId);
								stockPileDto.setMerchantOutletId(outletId);
								//当前库存信息
								List<StockPileDto> slist=stockPileSupport.getByCondtitons(stockPileDto);
								if(slist==null || slist.size()==0){
									logger.info("当前商品库存记录不存在，不执行减库存操作，交易Id："+ticket.getTransId());
								}else{
									stockPileDto=slist.get(0);
									logger.info("当前库存实时数量为："+stockPileDto.getQuantity());
									int num=transQueryDto.getTransDetailDto().get(0).getQuantity();//交易购买的数量
									stockPileDto.setQuantity(stockPileDto.getQuantity()-num);
									stockPileDto.setLastOutcomeTime(new Date());
									//减库存
									flag=stockPileSupport.updateById(stockPileDto);
									logger.info("执行减库存操作："+flag+",库存记录ID："+stockPileDto.getId()+",库存应减数量为："+num);							
									if(flag){
										logger.info("减库存成功，正在添加库存日志");
										StockPileLogDto pileLogDto=new StockPileLogDto();
										pileLogDto.setProductId(productId);
										pileLogDto.setMerchantOutletId(outletId);
										pileLogDto.setType(com.froad.fft.dto.StockPileLogDto.Type.sale_outcome);
										pileLogDto.setQuantity(num);
										pileLogDto.setContent("销售提货，减库存！交易ID("+transQueryDto.getId()+")");
										pileLogDto.setOperator(sysUser.getUsername());
										Long id= stockPileLogSupport.addStockPileLog(pileLogDto);
										logger.info("添加库存日志成功，记录ID："+id);									
									}
								}
							} catch (Exception e) {
								logger.info("认证成功，查询门店或库存信息异常",e);		
							}
							return new AjaxCallBackBean(true, "认证成功!");
						}else{
							logger.info("更新券信息失败，认证券ID："+ticket.getId());
							return new AjaxCallBackBean(false, "认证失败，网络错误请稍候再试");
						}					
					}
					return new AjaxCallBackBean(false, "认证失败，该券已经过期");
				}
				return new AjaxCallBackBean(false, "认证失败，该券已经被使用");
			}
		}
		return new AjaxCallBackBean(false, "认证失败，券号不存在");
	}
	
	
	
	
	
	/**
	  * 方法描述：下载预售列表查询记录
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月23日 下午2:53:06
	  */
	@RequestMapping(value="/downLoad")
	public  void  downLoad(HttpServletResponse response,PageDto page,TransQueryDto trans,Long merchantOutletId){
		page=this.selectByPage(page, trans, merchantOutletId);
		List<TransQueryDto> list=new ArrayList<TransQueryDto>();
		for(int pageNumber=1;pageNumber<=page.getPageCount();pageNumber++){
			page.setPageNumber(pageNumber);
			page=this.selectByPage(page, trans, merchantOutletId);
			list.addAll(page.getResultsContent());
		}
		String[] title={"商品","订单号","购买时间","数量（份）","价格（元）","积分","现金（元）","支付方式","状态","操作员"};
		List<String[]> content=new ArrayList<String[]>();
		content.add(title);
		for(TransQueryDto temp:list){
			String[] tp=new String[]{
				temp.getTransDetailDto().get(0).getProductName(),
				temp.getSn(),
				sdf.format(temp.getCreateTime()),
				temp.getTransDetailDto().get(0).getQuantity().toString(),
				temp.getTotalPrice()!=null?temp.getTotalPrice():"-",
				temp.getFftPoints()!=null?temp.getFftPoints():"-",
				temp.getRealPrice()!=null?temp.getRealPrice():"-",
				temp.getPayMethod().getDescribe(),
				temp.getState().getDescribe(),
				temp.getSysUserDto().getUsername()
			};
			content.add(tp);
		}
			String fileName=sdf1.format(new Date())+".csv";
			this.downloadCsv(response, content, fileName);			

	}	
	

	
	/**
	  * 方法描述：提货列表公用分页条件查询
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2014年4月23日 下午10:45:30
	  */
	public PageDto selectByPage(PageDto page,TransQueryDto trans,Long merchantOutletId){
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
		//获取当前登录的系统用户
		SysUserDto sysUser=(SysUserDto)sysUserSupport.getCurrentSysUser();	
		MerchantGroupUserDto merchantGroupUserDto=merchantGroupUserSupport.getBySysUserId(sysUser.getId());		
		List<Long> sysUserIds=new ArrayList<Long>();		
		//若选择了门店，则查询门店下的操作员
		if(merchantOutletId != null){
			MerchantGroupUserDto GroupUserDto=new MerchantGroupUserDto();
			GroupUserDto.setMerchantOutletId(merchantOutletId);
			List<MerchantGroupUserDto> glist= merchantGroupUserSupport.getByConditions(GroupUserDto);
			if(glist!=null && glist.size()>0){
				//如果门店下存在操作员
				for(MerchantGroupUserDto temp:glist){
					sysUserIds.add(temp.getSysUserId());
				}				
			}else{
				//如果门店下面没有任何操作员
				//此时不可省略，表示次查询条件为真，只是查询条件的值为空
				sysUserIds.add(null);
			}
		}else{
			//获取当前用户角色下可以查询到的所有操作员
			List<SysUserDto> list=sysUserSupport.getOperatorsBySysUser(sysUser);
			if(list!=null && list.size()>0){
				//如果存在操作员
				for(SysUserDto temp:list){
					sysUserIds.add(temp.getId());
				}
			}else{
				//如果没有任何操作员
				//此时不可省略，表示次查询条件为真，只是查询条件的值为空
				sysUserIds.add(null);
			}	
		}
		trans.setSysUserIds(sysUserIds);
		trans.setType(TransType.presell);
		page.getPageFilterDto().setProperty("create_time");
		page.getPageFilterDto().setFilterEntity(trans);
		page.setOrderDto(orderDto);		
		page.setPageSize(10);	
		page=transSupport.getPresellTransByPager(page);
		return page;
	}
	
}
