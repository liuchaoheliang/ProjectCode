package com.froad.cbank.coremodule.module.normal.user.support;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.user.pojo.PagePojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.TicketDetailPojo;
import com.froad.cbank.coremodule.module.normal.user.pojo.TicketDetailPojo.TicketProduct;
import com.froad.cbank.coremodule.module.normal.user.utils.DateUtils;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ticket.TicketDetailRequestVo;
import com.froad.thrift.vo.ticket.TicketDetailVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketListResponseVo;
import com.froad.thrift.vo.ticket.TicketProductResponseVo;
import com.froad.thrift.vo.ticket.TicketProductVo;


	/**
	 * 类描述：券码相关接口支持
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2015 
	 * @author: 刘超 liuchao@f-road.com.cn
	 * @time: 2015年3月26日 下午1:48:21 
	 */
@Service
public class TicketSupport extends BaseSupport {

	@Resource
	private TicketService.Iface ticketService;
	
	/**
	  * 方法描述：查询用户所有券信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月26日 下午1:51:26
	  */
	public Map<String, Object> getMyTicket(String clientId,String memberCode,String type,String status,String timeType ,PagePojo pagePojo){
		Map<String, Object> resMap=new HashMap<String, Object>();
		
	
		
		TicketListRequestVo ticketListRequestVo=new TicketListRequestVo();
		
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		
		ticketListRequestVo.setPageVo(pageVo);
		
		//条件参数
		ticketListRequestVo.setMemberCode(memberCode);
		ticketListRequestVo.setResource("3");// 个人用户类型
		ticketListRequestVo.setSource("1");//根据用户memberCode
		ticketListRequestVo.setClientId(clientId);
		if( !StringUtil.isBlank(timeType) && "1".equals(timeType)){
			ticketListRequestVo.setStartDate(String.valueOf( DateUtils.addDays(new Date(), -90).getTime() ));
			ticketListRequestVo.setEndDate(String.valueOf( new Date().getTime() ));
		}
		if(type != null){
			ticketListRequestVo.setType(type);
		}
		if(status != null){
			ticketListRequestVo.setStatus(status);
		}
		TicketListResponseVo ticketListResponseVo = null;
		try {
			LogCvt.info("调用接口传入参数:"+JSON.toJSONString(ticketListRequestVo)  );
			ticketListResponseVo=ticketService.getTicketList(ticketListRequestVo);
			PagePojo pojo=new PagePojo();
			List<TicketDetailPojo> list=null ;
			List<TicketDetailPojo.TicketProduct> plist=null;
			TicketDetailPojo detail=null;
			try {
				BeanUtils.copyProperties(pojo,ticketListResponseVo.getPageVo());
				if( ticketListResponseVo.getTicketDetailList() !=null && ticketListResponseVo.getTicketDetailList().size() != 0  ){
					list=new ArrayList<TicketDetailPojo>();
					for(TicketDetailVo temp:ticketListResponseVo.getTicketDetailList() ){
						detail=new TicketDetailPojo();
						BeanUtils.copyProperties(detail,temp);
						
						if(temp.isSetProductId()){
							plist=new ArrayList<TicketDetailPojo.TicketProduct>();
							TicketProduct ticketProduct=new TicketDetailPojo().new TicketProduct();
							BeanUtils.copyProperties(ticketProduct, temp);
							plist.add(ticketProduct);
							detail.setProductList(plist);
						}
						
						list.add(detail);
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			resMap.put("ticketList", list);
			resMap.put("page",pojo );
		} catch (TException e) {
			LogCvt.error("查询用户所有券出错", e);
		} 
		return resMap;
	}
	
	
	
	/**
	  * 方法描述：获取券详情信息
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年3月26日 下午1:52:21
	  */
	public HashMap<String, Object> getTicketDetail(String clientId,String ticketId){
		HashMap<String, Object> resMap=new HashMap<String, Object>();
		TicketDetailRequestVo ticketDetailRequestVo=new TicketDetailRequestVo();
		ticketDetailRequestVo.setClientId(clientId);
		ticketDetailRequestVo.setTicketId(ticketId);
		ticketDetailRequestVo.setResource("3");//设置来源 个人客户端
		ticketDetailRequestVo.setOption("1"); //  根据券号查询券信息
		TicketProductResponseVo ticketDetailResponseVo = null; 
		TicketDetailPojo detail = null;
		List<TicketDetailPojo.TicketProduct> list=null;
		TicketDetailPojo.TicketProduct ticketProduct= null;
		try {
			ticketDetailResponseVo=ticketService.getTicketProductDetails(ticketDetailRequestVo);
			if(ticketDetailResponseVo!= null ){
				detail=new TicketDetailPojo();
				BeanUtils.copyProperties(detail,ticketDetailResponseVo);
				if( !ArrayUtil.empty(ticketDetailResponseVo.getTicketProduct() ) ){
						list=new ArrayList<TicketDetailPojo.TicketProduct>();
						for(TicketProductVo temp:ticketDetailResponseVo.getTicketProduct()){
								ticketProduct=new TicketDetailPojo().new TicketProduct();
								BeanUtils.copyProperties(ticketProduct, temp);
								list.add(ticketProduct);
						}
						detail.setProductList(list);
				}
			}
			
			resMap.put("resResult", detail);
		} catch (TException e) {
			LogCvt.error("获取券详情出错", e);
		}
		return resMap;
	}
	
	
	
	/**
	  * 方法描述：我的券号总条数查询
	  * @param: memberCode  会员标识
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月7日 上午9:34:57
	  */
	public int totalTickets(String clientId,Long memberCode){
		
		TicketListRequestVo ticketListRequestVo=new TicketListRequestVo();
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(10);
		
		ticketListRequestVo.setPageVo(pageVo);
		
		ticketListRequestVo.setMemberCode(String.valueOf(memberCode));
		ticketListRequestVo.setType("1");
		ticketListRequestVo.setResource("3");// 个人用户类型
		ticketListRequestVo.setSource("1");//根据用户memberCode
		ticketListRequestVo.setClientId(clientId);
		ticketListRequestVo.setStatus("1");//未使用
		TicketListResponseVo ticketListResponseVo = null;
		int presellCount=0;
		int groupCount=0;
		try {
			ticketListResponseVo = ticketService.getTicketList(ticketListRequestVo);
			groupCount = ticketListResponseVo.getPageVo().getTotalCount();
			ticketListRequestVo.setType("2");
			ticketListResponseVo = ticketService.getTicketList(ticketListRequestVo);
			presellCount = ticketListResponseVo.getPageVo().getTotalCount();

		} catch (Exception e) {
			LogCvt.error("获取券总条数出错", e);
		}

		
		return groupCount + presellCount;
	}
	
	
	
	/**
	  * 方法描述：根据自订单号查询对应的券列表
	  * @param: clientId 客户端ID，subOrderId　子订单号 ，用户ID
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月20日 上午9:28:59
	  */
	public Map<String, Object> getTransTicket(String clientId,String subOrderId,String memberCode,PagePojo pagePojo){
		Map<String, Object> resMap=new HashMap<String, Object>();
		TicketListRequestVo ticketListRequestVo=new TicketListRequestVo();
		
		//分页page对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pagePojo.getPageNumber());
		pageVo.setPageSize(pagePojo.getPageSize());
		pageVo.setLastPageNumber(pagePojo.getLastPageNumber());
		pageVo.setLastRecordTime(pagePojo.getLastRecordTime());
		pageVo.setFirstRecordTime(pagePojo.getFirstRecordTime());
		
		ticketListRequestVo.setPageVo(pageVo);
		
		//帅选条件
		ticketListRequestVo.setSubOrderId(subOrderId);//子订单号
		ticketListRequestVo.setMemberCode(memberCode);
		ticketListRequestVo.setResource("3");// 个人用户类型
		ticketListRequestVo.setSource("5");//根据交易自订单号查询券列表
		ticketListRequestVo.setClientId(clientId);
		TicketListResponseVo ticketListResponseVo = null;
		try {
			LogCvt.info("调用接口传入参数:"+JSON.toJSONString(ticketListRequestVo)  );
			ticketListResponseVo=ticketService.getTicketList(ticketListRequestVo);
			PagePojo pojo=new PagePojo();
			List<TicketDetailPojo> list=null ;
			List<TicketDetailPojo.TicketProduct> plist=new ArrayList<TicketDetailPojo.TicketProduct>();
			TicketDetailPojo detail=null;
			try { 
				BeanUtils.copyProperties(pojo,ticketListResponseVo.getPageVo());
				if( ticketListResponseVo.getTicketDetailList() !=null && ticketListResponseVo.getTicketDetailList().size() != 0  ){
					list=new ArrayList<TicketDetailPojo>();
					for(TicketDetailVo temp:ticketListResponseVo.getTicketDetailList() ){
						detail=new TicketDetailPojo();
						BeanUtils.copyProperties(detail,temp);
						
						if(temp.isSetProductId()){
							plist=new ArrayList<TicketDetailPojo.TicketProduct>();
							TicketProduct ticketProduct=new TicketDetailPojo().new TicketProduct();
							BeanUtils.copyProperties(ticketProduct, temp);
							plist.add(ticketProduct);
							detail.setProductList(plist);
						}
						
						list.add(detail);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			resMap.put("ticketList", list);
			resMap.put("page",pojo );
		} catch (TException e) {
			LogCvt.error("根据子订单号查询券出错", e);
		} 
		return resMap;
	}
	
	
	
}
