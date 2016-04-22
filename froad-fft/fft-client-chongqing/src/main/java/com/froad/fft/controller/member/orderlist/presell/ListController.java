
	 /**
  * 文件名：PresellController.java
  * 版本信息：Version 1.0
  * 日期：2014年4月5日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.controller.member.orderlist.presell;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.bean.page.OrderDto;
import com.froad.fft.bean.page.OrderDto.Direction;
import com.froad.fft.bean.page.PageDto;
import com.froad.fft.bean.page.PageFilterDto;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.PresellDeliveryDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.dto.TransSecurityTicketDto;
import com.froad.fft.dto.UserEngineDto;
import com.froad.fft.enums.ClientVersion;
import com.froad.fft.enums.trans.TransPayState;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.support.base.PresellDeliverySupport;
import com.froad.fft.support.base.TransSupport;
import com.froad.fft.support.base.impl.TransSecurityTicketSupportImpl;
import com.froad.fft.util.NullValueCheckUtil;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月5日 上午11:19:09 
 */
@Controller("orderlist.presell")
@RequestMapping("/member/orderlist/presell")
public class ListController  extends BaseController
{

	@Resource
	private TransSupport transSupport; 
	
	@Resource
	private TransSecurityTicketSupportImpl transSecurityTicketSupportImpl;
	
	@Resource
	private PresellDeliverySupport presellDeliverySupport;
	
	/**
	 * 方法描述：用户查询预售商品交易
	 * @param: 
	 * @return: 
	 * @version: 1.0
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2014年4月5日 上午11:19:09
	 */
	@RequestMapping(value = "list")
	public String list(HttpServletRequest req,PageDto page, ModelMap model,String payState){
		if(page==null){
			page=new PageDto();
		}
		if(page.getPageFilterDto()==null){
			PageFilterDto pageFilterDto=new PageFilterDto();
			page.setPageFilterDto(pageFilterDto);
		}
		TransQueryDto trans = new TransQueryDto();
		if(StringUtils.isNotBlank(payState)){
			List<TransPayState> status = new ArrayList<TransPayState>();
			String [] pays = payState.split(",");
			for (String py : pays) {
				for (TransPayState transPayState : TransPayState.values()) {
					if(transPayState.getCode().equals(py)){
						status.add(transPayState);
						break;
					}
				}
			}
			trans.setPayStates(status);
		}
		UserEngineDto user=(UserEngineDto)getSessionValue(req, SessionKey.LOGIN_IDENTIFICATION);
		trans.setMemberCode(user.getMemberCode());
		trans.setType(TransType.presell);
		page.getPageFilterDto().setProperty("create_time");
		page.getPageFilterDto().setFilterEntity(trans);
		//排序
		OrderDto orderDto = new OrderDto();
		orderDto.setDirection(Direction.desc);
		orderDto.setProperty("create_time");
		page.setOrderDto(orderDto);
		
		page.setPageSize(10);		
		page=transSupport.getPresellTransByPager(page);

		model.addAttribute("page",page);
		model.addAttribute("payState", payState);
		return "/member/orderlist/presell/list";
		
	}


	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>查询交易详情</b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年4月9日 下午7:31:53 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "detail" , method = RequestMethod.POST)
	public String getTransDetails(String transId,ModelMap modelMap){
		logger.info("查询交易详情 transId" + transId);
		if(NullValueCheckUtil.isStrEmpty(transId)){
			return ILLEGALITY;
		}
		Long id = null;
		try {
			id = Long.parseLong(transId);
		} catch (Exception e) {
			//传入交易id无效
			return ILLEGALITY;
		}
		TransQueryDto trans = transSupport.getByTransId(id);
		if(trans == null){
			return ILLEGALITY; //交易不存在
		}
		trans.getTransDetailDto().get(0).setProductName(NullValueCheckUtil.strEllipsis(trans.getTransDetailDto().get(0).getProductName(), 12));
		//根据交易Id查询验证券
		TransSecurityTicketDto ticket = transSecurityTicketSupportImpl.getByTransId(trans.getId());
		//查询提货点
		PresellDeliveryDto delivery = presellDeliverySupport.getById(trans.getDeliveryId());
		//预售交易状态
		String presellState = transSupport.getPresellState(trans.getSn());
		modelMap.put("trans", trans);
		modelMap.put("ticket", ticket);
		modelMap.put("delivery", delivery);
		modelMap.put("presellState", presellState);
		return "/common/ajax/member/orderlist/presell_info";
	}
}
