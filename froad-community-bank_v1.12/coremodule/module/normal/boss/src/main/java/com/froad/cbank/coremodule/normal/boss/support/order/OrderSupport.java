package com.froad.cbank.coremodule.normal.boss.support.order;

import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.order.OrderDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.OrderListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.OrderVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.SubOrderProductRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.SubOrderRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.TicketInfoVo;
import com.froad.cbank.coremodule.normal.boss.pojo.order.TicketInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BossOrderQueryService;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.service.MerchantService;
import com.froad.thrift.service.MerchantUserService;
import com.froad.thrift.vo.ClientVo;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.MerchantPageVoRes;
import com.froad.thrift.vo.MerchantUserVo;
import com.froad.thrift.vo.MerchantVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.BossGroupOrderListReq;
import com.froad.thrift.vo.order.BossGroupOrderListRes;
import com.froad.thrift.vo.order.BossGroupOrderVo;
import com.froad.thrift.vo.order.BossPresellDetialRes;
import com.froad.thrift.vo.order.BossPresellOrderListReq;
import com.froad.thrift.vo.order.BossPresellOrderListRes;
import com.froad.thrift.vo.order.BossPresellOrderVo;
import com.froad.thrift.vo.order.BossQueryOrderDetailReq;
import com.froad.thrift.vo.order.BossQueryOrderDetailRes;
import com.froad.thrift.vo.order.BossQueryOrderListReq;
import com.froad.thrift.vo.order.BossQueryOrderListRes;
import com.froad.thrift.vo.order.BossQueryOrderVo;
import com.froad.thrift.vo.order.BossSubOrderProductVo;
import com.froad.thrift.vo.order.BossSubOrderVo;

/**
 * 类描述：相关业务类
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road.com.cn
 * @time: 2015-5-13下午7:34:08 
 */

@Service
public class OrderSupport {
	
	@Resource
	BossOrderQueryService.Iface bossOrderQueryService;
	
	@Resource
	MerchantService.Iface merchantService;
	
	@Resource
	MerchantUserService.Iface merchantUserService;
	
	@Resource
	ClientService.Iface clientService;
	
	/**
	  * 方法描述：查询团购列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	 * @throws TException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	  * @time: 2015年8月5日 下午4:43:27
	  */
	public HashMap<String, Object> queryGroupList(TicketInfoVoReq req) throws Exception{
		
		BossGroupOrderListReq bossGroupOrderListReq=new BossGroupOrderListReq();
		bossGroupOrderListReq.setClientId(req.getClientId());
		
		//订单号
		if(StringUtil.isNotBlank(req.getOrderCode())){
			bossGroupOrderListReq.setOrderId(req.getOrderCode());
		}		
		//券号状态
		if(StringUtil.isNotBlank(req.getTicketStatu())){
			String tktStu=req.getTicketStatu();
			if(tktStu.equals("1")){
				bossGroupOrderListReq.setTicketStatus("1");
			}else if(tktStu.equals("2")){
				bossGroupOrderListReq.setTicketStatus("2");
			}else if(tktStu.equals("3")){
				bossGroupOrderListReq.setTicketStatus("3");
			}else if(tktStu.equals("4")){
				bossGroupOrderListReq.setTicketStatus("4,7");
			}else if(tktStu.equals("5")){
				bossGroupOrderListReq.setTicketStatus("5,9");
			}else if(tktStu.equals("6")){
				bossGroupOrderListReq.setTicketStatus("6");
			}else{
				bossGroupOrderListReq.setTicketStatus("");
			}		
		}
		//券号
		if(StringUtil.isNotBlank(req.getTicketNo())){
			bossGroupOrderListReq.setTicketId(req.getTicketNo());			
		}
		//手机号
		if(StringUtil.isNotBlank(req.getPhone())){
			bossGroupOrderListReq.setPhone(req.getPhone());
		}
		//结算状态
		if(StringUtil.isNotBlank(req.getSettlementStatus())){
			bossGroupOrderListReq.setSettlementStatus(req.getSettlementStatus());
		}		
		//登录名
		if(StringUtil.isNotBlank(req.getUserName())){
			bossGroupOrderListReq.setMemberName(req.getUserName());
		}
		//商品名
		if(StringUtil.isNotBlank(req.getProductName())){
			bossGroupOrderListReq.setProductName(req.getProductName());
		}
		//开始时间
		if(StringUtil.isNotBlank(req.getBeginTime())){
			bossGroupOrderListReq.setStartTime(PramasUtil.DateFormat(req.getBeginTime()));
		}
		//结束时间
		if(StringUtil.isNotBlank(req.getEndTime())){
			bossGroupOrderListReq.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		}		
		
		HashMap<String, Object> resMap=new HashMap<String, Object>();				
		//分页对象转换
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber()==0?1:req.getPageNumber());
		pageVo.setPageSize(req.getPageSize()==0?10:req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		pageVo.setBegDate(req.getBegDate()==0?req.getStartDate():req.getBegDate());
		pageVo.setEndDate(req.getEndDate());		
		bossGroupOrderListReq.setPageVo(pageVo);
		
		List<String> orgs = new ArrayList<String>();
		for(String org:req.getOrgCodes().split(",")){
			orgs.add(org);
		}
		bossGroupOrderListReq.setOrgCodes(orgs);
		
		Page page = new Page();
		List<TicketInfoVo> ticketList = new ArrayList<TicketInfoVo>();	
		BossGroupOrderListRes res = null;
		TicketInfoVo rv= null;
		res=bossOrderQueryService.queryGroupOrderList(bossGroupOrderListReq);
		//分页实体转换
		BeanUtils.copyProperties(page, res.getPageVo());
		if(res.getOrders()!=null && res.getOrders().size() > 0){
			for(BossGroupOrderVo riv: res.getOrders()){
				 rv= new TicketInfoVo();
				 
				 //券号
				 if(StringUtil.isNotBlank(riv.getTicketId()) && riv.getTicketId().length()>8){
					 String t = "";
					 for(int i = 0;i<riv.getTicketId().length()-8;i++){
						 t += "*";
					 }
					 String s = riv.getTicketId().substring(0, 4) + t + riv.getTicketId().substring(riv.getTicketId().length()-4);
					 
					 rv.setTicketNo(s);
				 }else{
					 rv.setTicketNo(riv.getTicketId());
				 }
				//明文券号
				 if(StringUtil.isNotBlank(riv.getTicketId())){
					 rv.setTicketNo2(riv.getTicketId());
				 }
					
				 //订单号
				 if(StringUtil.isNotBlank(riv.getOrderId())){
					 rv.setOrderCode(riv.getOrderId());				 
				 }
				 //券号发送时间
				 if(StringUtil.isNotBlank(riv.getSendTime())){
					 rv.setTicketSendTime(String.valueOf(riv.getSendTime()));
				 }
				//购买时间
				 if(StringUtil.isNotBlank(riv.getPaymentTime())){
					 rv.setSaleTime(String.valueOf(riv.getPaymentTime()));
				 }
				 //商品名
				 if(StringUtil.isNotBlank(riv.getProductName())){
					 rv.setProductName(riv.getProductName());
				 }
				 //门店名称
				 if(StringUtil.isNotBlank(riv.getOutletName())){
					 rv.setMerchantName(riv.getOutletName());
				 }
				 //用户名
				 if(StringUtil.isNotBlank(riv.getMemberName())){			
					 rv.setUserName(riv.getMemberName());
				 }
				 //提货人名称
				 if(StringUtil.isNotBlank(riv.getConsigneeName())){
					 rv.setMemberName(riv.getConsigneeName());
				 }
				//收货人手机号
				 if(StringUtil.isNotBlank(riv.getPhone())){
					 rv.setMobile(riv.getPhone());
				 }
				 String stau ="";
				 //券号状态
				 if(StringUtil.isNotBlank(riv.getTicketStatus())){
					 rv.setTicketStatu(riv.getTicketStatus());	
					 stau=riv.getTicketStatus();
				 }
				 //券号状态:0:待发送;1:已发送;2:已消费;3:已过期;4:已退款;
				 //5:退款中;6:退款失败;7:已过期退款;8:未发码退款;9:已过期退款中
				 
				 if("2".equals(stau)){
					 rv.setOpTime(String.valueOf(riv.getConsumeTime()));//消费时间					 
				 }else if("3".equals(stau)){
					 rv.setOpTime(String.valueOf(riv.getExpireTime()));//有效期
				 }else if("4".equals(stau)){
					 rv.setOpTime(String.valueOf(riv.getRefundTime()));//退款时间
				 }else if("7".equals(stau)){
					 rv.setOpTime(String.valueOf(riv.getRefundTime()));//退款时间
				 }else{
					 rv.setOpTime("");
				 }
				 ticketList.add(rv);
			}
		}
		resMap.put("totalCountExport", page.getTotalCount());
		resMap.put("page", page);
		resMap.put("ticketList", ticketList);
		return  resMap;
	}
	/**
	  * 方法描述：查询预售券列表
	  * @param: 
	  * @return: 
	  * @version: 1.0
	 * @throws TException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	  * @time: 2015年8月5日 下午4:43:27
	  */
	public HashMap<String, Object> queryPresellList(TicketInfoVoReq req) throws Exception{
		BossPresellOrderListReq bossPresellOrderListReq=new BossPresellOrderListReq();
		//订单编号
		if(StringUtil.isNotBlank(req.getOrderCode())){
			bossPresellOrderListReq.setOrderId(req.getOrderCode());
		}
		/**
		 * 
		 * 未消费：已发送 1
		 * 已消费：已消费2
		 * 已过期：已过期3、
		 * 已退款：已退款4、已过期退款7
		 * 退款中：已过期退款中9，退款中5
		 * 退款失败：退款失败6
		 * 
	     * 券号状态:1:已发送;2:已消费;3:已过期;4:已退款;
	     * 5:退款中;6:退款失败;7:已过期退款;8:未发码退款;9:已过期退款中
		 */
		//状态
		if(StringUtil.isNotBlank(req.getTicketStatu())){
			String tktStu=req.getTicketStatu();
			if(tktStu.equals("1")){
				bossPresellOrderListReq.setTicketStatus("1");
			}else if(tktStu.equals("2")){
				bossPresellOrderListReq.setTicketStatus("2");
			}else if(tktStu.equals("3")){
				bossPresellOrderListReq.setTicketStatus("3");
			}else if(tktStu.equals("4")){
				bossPresellOrderListReq.setTicketStatus("4,7");
			}else if(tktStu.equals("5")){
				bossPresellOrderListReq.setTicketStatus("5,9");
			}else if(tktStu.equals("6")){
				bossPresellOrderListReq.setTicketStatus("6");
			}else{
				bossPresellOrderListReq.setTicketStatus("");
			}		
		}
	
		//提货码
		if(StringUtil.isNotBlank(req.getTicketNo())){
			bossPresellOrderListReq.setTicketId(req.getTicketNo());			
		}
		//手机号
		if(StringUtil.isNotBlank(req.getPhone())){
			bossPresellOrderListReq.setPhone(req.getPhone());
		}
		//结算状态
		if(StringUtil.isNotBlank(req.getSettlementStatus())){
			bossPresellOrderListReq.setSettlementStatus(req.getSettlementStatus());
		}		
		
		//提货人
		if(StringUtil.isNotBlank(req.getMerchantUserName())){
			bossPresellOrderListReq.setMemberName(req.getMerchantUserName());
		}
		//商品名
		if(StringUtil.isNotBlank(req.getProductName())){
			bossPresellOrderListReq.setProductName(req.getProductName());
		}
		if(StringUtil.isNotBlank(req.getBeginTime())){
			bossPresellOrderListReq.setStartTime(PramasUtil.DateFormat(req.getBeginTime()));
		}
		if(StringUtil.isNotBlank(req.getEndTime())){
			bossPresellOrderListReq.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		}		
		
		HashMap<String, Object> resMap=new HashMap<String, Object>();				
		//分页对象转换
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber()==0?1:req.getPageNumber());
		pageVo.setPageSize(req.getPageSize()==0?10:req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		pageVo.setBegDate(req.getBegDate()==0?req.getStartDate():req.getBegDate());
		pageVo.setEndDate(req.getEndDate());		
		bossPresellOrderListReq.setPageVo(pageVo);
		
		Page page = new Page();
		List<TicketInfoVo> ticketList = new ArrayList<TicketInfoVo>();	
		BossPresellOrderListRes res = null;
		TicketInfoVo rv= null;
		res=bossOrderQueryService.queryPresellOrderList(bossPresellOrderListReq);
		//分页实体转换
		BeanUtils.copyProperties(page, res.getPageVo());
		if(res.getOrders()!=null && res.getOrders().size() > 0){
			for(BossPresellOrderVo riv: res.getOrders()){
				 rv= new TicketInfoVo();
				 //clientId
				 if(StringUtil.isNotBlank(riv.getClientId())){
					 rv.setClientId(riv.getClientId());
				 }
				 //券号id
				 if(StringUtil.isNotBlank(riv.getId())){
					 rv.setId(riv.getId());
				 }
				 //券号
				 if(StringUtil.isNotBlank(riv.getTicketId()) && riv.getTicketId().length()>8){
					 String t = "";
					 for(int i = 0;i<riv.getTicketId().length()-8;i++){
						 t += "*";
					 }
					 String s = riv.getTicketId().substring(0, 4) + t + riv.getTicketId().substring(riv.getTicketId().length()-4);
					 rv.setTicketNo(s);
				 }else{
					 rv.setTicketNo(riv.getTicketId());
				 }
					//明文券号
				 if(StringUtil.isNotBlank(riv.getTicketId())){
					 rv.setTicketNo2(riv.getTicketId());
				 }
					
				 //订单号
				 if(StringUtil.isNotBlank(riv.getOrderId())){
					 rv.setOrderCode(riv.getOrderId());				 
				 }
				 //购买时间
				 if(StringUtil.isNotBlank(riv.getPaymentTime())){
					 rv.setSaleTime(String.valueOf(riv.getPaymentTime()));//购买时间					 
				 }
				 
				/* //券号发送时间？？？
				 if(StringUtil.isNotBlank(riv.getPaymentTime())){
					 rv.setTicketSendTime(String.valueOf(riv.getPaymentTime()));
				 }*/
				 //商品名
				 if(StringUtil.isNotBlank(riv.getProductName())){
					 rv.setProductName(riv.getProductName());
				 }
				 //用户名
				 if(StringUtil.isNotBlank(riv.getMemberName())){
					 rv.setUserName(riv.getMemberName());
				 }
				 //提货人
				 if(StringUtil.isNotBlank(riv.getConsigneeName())){
					 rv.setMemberName(riv.getConsigneeName());
				 }
				 //提货方式
				 if(StringUtil.isNotBlank(riv.getDeliveryType())){
					rv.setDeliveryType(riv.getDeliveryType());
				 }
				 //提货期
				 if(StringUtil.isNotBlank(riv.getDeliveryStartTime())  && riv.getDeliveryStartTime() != 0 &&
						 StringUtil.isNotBlank(riv.getDeliveryEndTime())  && riv.getDeliveryEndTime() != 0){
					SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
					String begin=sdf.format(new Date(riv.getDeliveryStartTime()));
					String end=sdf.format(new Date(riv.getDeliveryEndTime()));
					rv.setDeliveryPeriod(begin+"-"+end);
				 }
				 //收货人手机号
				 if(StringUtil.isNotBlank(riv.getConsigneePhone())){
					 rv.setMobile(riv.getConsigneePhone());
				 }
				 String stau ="";
				 //券号状态
				 if(StringUtil.isNotBlank(riv.getTicketStatus())){
					 rv.setTicketStatu(riv.getTicketStatus());	
					 stau=riv.getTicketStatus();
				 }
				 //1、6：未消费  	2：已消费	4、7：已退款	3：已过期	5：退款中
				 if("2".equals(stau)){
					 rv.setOpTime(String.valueOf(riv.getConsumeTime()));//消费时间					 
				 }else if("3".equals(stau)){
					 rv.setOpTime(String.valueOf(riv.getExpireTime()));//有效期
				 }else if("4".equals(stau)){
					 rv.setOpTime(String.valueOf(riv.getRefundTime()));//退款时间
				 }else if("7".equals(stau)){
					 rv.setOpTime(String.valueOf(riv.getRefundTime()));//退款时间
				 }else{
					 rv.setOpTime("");
				 }
				 ticketList.add(rv);
			}
		}
		resMap.put("totalCountExport", page.getTotalCount());
		resMap.put("page", page);
		resMap.put("ticketList", ticketList);
		return  resMap;
	}
	/**
	 * 预售券详情
	 * @throws TException 
	 */
	public TicketInfoVo queryPresellDetail(TicketInfoVoReq req) throws Exception{
		String ticketId="",clientId="";				
		//券号id
		
		if(StringUtil.isNotBlank(req.getId())){
			ticketId=req.getId();
		}
		//客户端id
		if(StringUtil.isNotBlank(req.getClientId())){
			clientId=req.getClientId();
		}
		BossPresellDetialRes res = null;
		TicketInfoVo rv= new TicketInfoVo();
		res=bossOrderQueryService.getPresellDetial(clientId, ticketId);
    	//订单号
		if(StringUtil.isNotBlank(res.getOrderId())){
			rv.setOrderCode(res.getOrderId());
		 }
		 //券号
		 if(StringUtil.isNotBlank(res.getTicketId()) && res.getTicketId().length()>8){
			 String t = "";
			 for(int i = 0;i<res.getTicketId().length()-8;i++){
				 t += "*";
			 }
			 String s = res.getTicketId().substring(0, 4) + t + res.getTicketId().substring(res.getTicketId().length()-4);
			 rv.setTicketNo(s);
		 }else{
			 rv.setTicketNo(res.getTicketId());
		 }
		 //购买时间
		 if(StringUtil.isNotBlank(res.getPaymentTime())){
			 rv.setSaleTime(String.valueOf(res.getPaymentTime()));//购买时间					 
		 }
		 
		 //商品名
		 if(StringUtil.isNotBlank(res.getProductName())){
			 rv.setProductName(res.getProductName());
		 }
		 //用户名
		 if(StringUtil.isNotBlank(res.getMemberName())){
			 rv.setUserName(res.getMemberName());
		 }
		 //提货人
		 if(StringUtil.isNotBlank(res.getConsigneeName())){
			 rv.setMemberName(res.getConsigneeName());
		 }
		 //提货方式
		 if(StringUtil.isNotBlank(res.getDeliveryType())){
			rv.setDeliveryType(res.getDeliveryType());
		 }
		 //提货期
		 if(StringUtil.isNotBlank(res.getDeliveryStartTime())  && res.getDeliveryStartTime() != 0 &&
				 StringUtil.isNotBlank(res.getDeliveryEndTime())  && res.getDeliveryEndTime() != 0){
			SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
			String begin=sdf.format(new Date(res.getDeliveryStartTime()));
			String end=sdf.format(new Date(res.getDeliveryEndTime()));
			rv.setDeliveryPeriod(begin+"-"+end);
		 }
		 //收货人手机号
		 if(StringUtil.isNotBlank(res.getConsigneePhone())){
			 rv.setMobile(res.getConsigneePhone());
		 }
		 //券号状态
		 if(StringUtil.isNotBlank(res.getTicketStatus())){
			 rv.setTicketStatu(res.getTicketStatus());	
		 }
		 //消费时间
		 if(StringUtil.isNotBlank(res.getConsumeTime())){
			 rv.setConsumeTime(String.valueOf(res.getConsumeTime()));
		 }
		 //提货网点
		 if(StringUtil.isNotBlank(res.getConsigneeAddress())){
			 rv.setOrgAddr(res.getConsigneeAddress());
		 }
		return rv;
	}
	/**
	 * 根据门店名查询IDs
	 * @param name
	 * @return
	 * @throws TException
	 */
	public List<String> getMerchantIds(String name) throws TException{
		List<String>  ids = new ArrayList<String>();
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(1);
		pageVo.setPageSize(200);
		pageVo.setFirstRecordTime(0);
		pageVo.setLastPageNumber(0);
		pageVo.setLastRecordTime(0);
		
		MerchantVo mVo = new MerchantVo();
		mVo.setMerchantName(name);
		mVo.setIsEnable(true);
		mVo.setDisableStatus("0");//正常商户
		MerchantPageVoRes  res = merchantService.getMerchantByPage(pageVo, mVo);
		List<MerchantVo> list = null;
		if(res != null && res.getMerchantVoList()!= null){
			list = res.getMerchantVoList();			
		}
		for(MerchantVo vo:list){
			ids.add(String.valueOf(vo.getMerchantId()));
		}
		return ids;
	}
	
	/**
	 * 获取订单详情
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月2日 下午1:51:39
	 * @param pojo
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getOrderDetail(OrderDetailReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		List<SubOrderRes> subOrderList = new ArrayList<SubOrderRes>();
		SubOrderRes temp = null;
		List<SubOrderProductRes> proList = null;
		SubOrderProductRes temp2 = null;
		//封装请求对象
		BossQueryOrderDetailReq req = new BossQueryOrderDetailReq();
		BeanUtils.copyProperties(req, pojo);
		//调用SERVER端接口
		BossQueryOrderDetailRes resp = bossOrderQueryService.getOrderDetail(req);
		//封装返回结果
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			//封装返回结果
			if(!ArrayUtil.empty(resp.getSubOrders())) {
				for(BossSubOrderVo tmp : resp.getSubOrders()) {
					temp = new SubOrderRes();
					BeanUtils.copyProperties(temp, tmp);
					//封装返回每个子订单的商品集合
					proList = new ArrayList<SubOrderProductRes>();
					if(!ArrayUtil.empty(tmp.getProducts())) {
						for(BossSubOrderProductVo tmp2 : tmp.getProducts()) {
							temp2 = new SubOrderProductRes();
							BeanUtils.copyProperties(temp2, tmp2);
							proList.add(temp2);
						}
					}
					temp.setProducts(proList);
					subOrderList.add(temp);
				}
			}
			map.put("subOrderList", subOrderList);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 获取订单列表
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月2日 下午5:01:00
	 * @param orderReq
	 * @return map
	 * @throws Exception
	 */
	public HashMap<String, Object> getOrderList(OrderListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Page page = new Page();
		List<OrderVoRes> orderList = new ArrayList<OrderVoRes>();
		OrderVoRes temp = null;
		//封装请求对象
		BossQueryOrderListReq req = new BossQueryOrderListReq();
		BeanUtils.copyProperties(req, pojo);
		req.setStartTime(StringUtil.isNotBlank(pojo.getStartTime()) ? PramasUtil.DateFormat(pojo.getStartTime()) : 0);
		req.setEndTime(StringUtil.isNotBlank(pojo.getEndTime()) ? PramasUtil.DateFormatEnd(pojo.getEndTime()) : 0);
		if(req.getStartTime() > req.getEndTime()) {
			throw new BossException("起始筛选时间不可大于结束筛选时间");
		}
		//封装分页对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pojo.getPageNumber());
		pageVo.setPageSize(pojo.getPageSize());
		pageVo.setLastPageNumber(pojo.getLastPageNumber());
		pageVo.setFirstRecordTime(pojo.getFirstRecordTime());
		pageVo.setLastRecordTime(pojo.getLastRecordTime());
		req.setPageVo(pageVo);
		
		List<String> orgs = new ArrayList<String>();
		for(String org:pojo.getOrgCodes().split(",")){
			orgs.add(org);
		}
		req.setOrgCodes(orgs);
		
		//调用SERVER端接口
		BossQueryOrderListRes resp = bossOrderQueryService.getOrderList(req);
		//封装返回结果
		ResultVo result = resp.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			//封装返回分页对象
			BeanUtils.copyProperties(page, resp.getPageVo());
			//封装返回订单列表
			if(!ArrayUtil.empty(resp.getOrders())) {
				List<ClientVo> client = clientService.getClient(new ClientVo());
				for(BossQueryOrderVo tmp : resp.getOrders()) {
					temp = new OrderVoRes();
					BeanUtils.copyProperties(temp, tmp);
					
					for (ClientVo clientVo : client) {
						if (tmp.getClientId().equals(clientVo.getClientId())) {
							temp.setClientName(clientVo.getBankName());
							break;
						}
					}
					
					if(tmp.getMerchantUserId() > 0){
						MerchantUserVo mer = merchantUserService.getMerchantUserById(tmp.getMerchantUserId());
						if(mer != null){
							temp.setMerchantUserId(mer.getUsername());
						}
					}else{
						temp.setMerchantUserId("");
					}
					orderList.add(temp);
				}
			}
			map.put("result", orderList);
			map.put("page", page);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 订单列表导出
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年8月28日 下午2:36:46
	 * @return
	 */
	public HashMap<String, Object> orderListExport(OrderListReq pojo) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		BossQueryOrderListReq req = new BossQueryOrderListReq();
		BeanUtils.copyProperties(req, pojo);
		req.setStartTime(StringUtil.isNotBlank(pojo.getStartTime()) ? PramasUtil.DateFormat(pojo.getStartTime()) : 0);
		req.setEndTime(StringUtil.isNotBlank(pojo.getEndTime()) ? PramasUtil.DateFormatEnd(pojo.getEndTime()) : 0);
		if(req.getStartTime() > req.getEndTime()) {
			throw new BossException("起始筛选时间不可大于结束筛选时间");
		}
		//封装分页对象
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pojo.getPageNumber());
		pageVo.setPageSize(pojo.getPageSize());
		pageVo.setLastPageNumber(pojo.getLastPageNumber());
		pageVo.setFirstRecordTime(pojo.getFirstRecordTime());
		pageVo.setLastRecordTime(pojo.getLastRecordTime());
		req.setPageVo(pageVo);
		
		List<String> orgs = new ArrayList<String>();
		for(String org:pojo.getOrgCodes().split(",")){
			orgs.add(org);
		}
		req.setOrgCodes(orgs);
		
		//调用SERVER端接口
		ExportResultRes resp = bossOrderQueryService.exportQueryOrderList(req);
		//封装返回结果
		ResultVo result = resp.getResultVo();
		LogCvt.info("订单列表导出返回结果：" + JSON.toJSONString(result));
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("url", resp.getUrl());
			map.put("result", obj);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 团购券订单列表导出
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月7日 下午3:01:16
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> groupListExport(TicketInfoVoReq req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		BossGroupOrderListReq bossGroupOrderListReq=new BossGroupOrderListReq();
		bossGroupOrderListReq.setClientId(req.getClientId());
		//订单号
		if(StringUtil.isNotBlank(req.getOrderCode())){
			bossGroupOrderListReq.setOrderId(req.getOrderCode());
		}
		//券号状态
		if(StringUtil.isNotBlank(req.getTicketStatu())){
			String tktStu=req.getTicketStatu();
			if(tktStu.equals("1")){
				bossGroupOrderListReq.setTicketStatus("1");
			}else if(tktStu.equals("2")){
				bossGroupOrderListReq.setTicketStatus("2");
			}else if(tktStu.equals("3")){
				bossGroupOrderListReq.setTicketStatus("3");
			}else if(tktStu.equals("4")){
				bossGroupOrderListReq.setTicketStatus("4,7");
			}else if(tktStu.equals("5")){
				bossGroupOrderListReq.setTicketStatus("5,9");
			}else if(tktStu.equals("6")){
				bossGroupOrderListReq.setTicketStatus("6");
			}else{
				bossGroupOrderListReq.setTicketStatus("");
			}
		}
		//券号
		if(StringUtil.isNotBlank(req.getTicketNo())){
			bossGroupOrderListReq.setTicketId(req.getTicketNo());			
		}
		//手机号
		if(StringUtil.isNotBlank(req.getPhone())){
			bossGroupOrderListReq.setPhone(req.getPhone());
		}
		//结算状态
		if(StringUtil.isNotBlank(req.getSettlementStatus())){
			bossGroupOrderListReq.setSettlementStatus(req.getSettlementStatus());
		}
		//登录名
		if(StringUtil.isNotBlank(req.getUserName())){
			bossGroupOrderListReq.setMemberName(req.getUserName());
		}
		//商品名
		if(StringUtil.isNotBlank(req.getProductName())){
			bossGroupOrderListReq.setProductName(req.getProductName());
		}
		//开始时间
		if(StringUtil.isNotBlank(req.getBeginTime())){
			bossGroupOrderListReq.setStartTime(PramasUtil.DateFormat(req.getBeginTime()));
		}
		//结束时间
		if(StringUtil.isNotBlank(req.getEndTime())){
			bossGroupOrderListReq.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		}
		
		List<String> orgs = new ArrayList<String>();
		for(String org:req.getOrgCodes().split(",")){
			orgs.add(org);
		}
		bossGroupOrderListReq.setOrgCodes(orgs);
		
		//调用SERVER端接口
		ExportResultRes resp = bossOrderQueryService.exportGroupOrderTicket(bossGroupOrderListReq);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		LogCvt.info("团购券订单列表导出返回结果：" + JSON.toJSONString(result));
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("url", resp.getUrl());
			map.put("result", obj);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
	/**
	 * 预售券订单列表导出
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月7日 下午3:01:16
	 * @return
	 * @throws Exception
	 */
	public HashMap<String, Object> presellListExport(TicketInfoVoReq req) throws Exception {
		HashMap<String, Object> map = new HashMap<String, Object>();
		//封装请求对象
		BossPresellOrderListReq bossPresellOrderListReq = new BossPresellOrderListReq();
		//订单编号
		if(StringUtil.isNotBlank(req.getOrderCode())){
			bossPresellOrderListReq.setOrderId(req.getOrderCode());
		}
		/**
		 * 未消费：已发送 1
		 * 已消费：已消费2
		 * 已过期：已过期3、
		 * 已退款：已退款4、已过期退款7
		 * 退款中：已过期退款中9，退款中5
		 * 退款失败：退款失败6
	     * 券号状态:1:已发送;2:已消费;3:已过期;4:已退款;
	     * 5:退款中;6:退款失败;7:已过期退款;8:未发码退款;9:已过期退款中
		 */
		//状态
		if(StringUtil.isNotBlank(req.getTicketStatu())){
			String tktStu=req.getTicketStatu();
			if(tktStu.equals("1")){
				bossPresellOrderListReq.setTicketStatus("1");
			}else if(tktStu.equals("2")){
				bossPresellOrderListReq.setTicketStatus("2");
			}else if(tktStu.equals("3")){
				bossPresellOrderListReq.setTicketStatus("3");
			}else if(tktStu.equals("4")){
				bossPresellOrderListReq.setTicketStatus("4,7");
			}else if(tktStu.equals("5")){
				bossPresellOrderListReq.setTicketStatus("5,9");
			}else if(tktStu.equals("6")){
				bossPresellOrderListReq.setTicketStatus("6");
			}else{
				bossPresellOrderListReq.setTicketStatus("");
			}
		}
		//提货码
		if(StringUtil.isNotBlank(req.getTicketNo())){
			bossPresellOrderListReq.setTicketId(req.getTicketNo());			
		}
		//手机号
		if(StringUtil.isNotBlank(req.getPhone())){
			bossPresellOrderListReq.setPhone(req.getPhone());
		}
		//结算状态
		if(StringUtil.isNotBlank(req.getSettlementStatus())){
			bossPresellOrderListReq.setSettlementStatus(req.getSettlementStatus());
		}		
		//提货人
		if(StringUtil.isNotBlank(req.getMerchantUserName())){
			bossPresellOrderListReq.setMemberName(req.getMerchantUserName());
		}
		//商品名
		if(StringUtil.isNotBlank(req.getProductName())){
			bossPresellOrderListReq.setProductName(req.getProductName());
		}
		if(StringUtil.isNotBlank(req.getBeginTime())){
			bossPresellOrderListReq.setStartTime(PramasUtil.DateFormat(req.getBeginTime()));
		}
		if(StringUtil.isNotBlank(req.getEndTime())){
			bossPresellOrderListReq.setEndTime(PramasUtil.DateFormatEnd(req.getEndTime()));
		}
		//调用SERVER端接口
		ExportResultRes resp = bossOrderQueryService.exportPresellOrderTicket(bossPresellOrderListReq);
		//封装返回结果对象
		ResultVo result = resp.getResultVo();
		LogCvt.info("预售券订单列表导出返回结果：" + JSON.toJSONString(result));
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			HashMap<String, String> obj = new HashMap<String, String>();
			obj.put("url", resp.getUrl());
			map.put("result", obj);
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
}
