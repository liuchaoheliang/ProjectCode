package com.froad.cbank.coremodule.module.normal.bank.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.enums.TakeStateEnum;
import com.froad.cbank.coremodule.module.normal.bank.util.DateUtil;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.DeliveryVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.DeliveryVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.thrift.service.OrgService;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OrgVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ticket.TicketDetailVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketListResponseVo;
import com.froad.thrift.vo.ticket.TicketRelatedRequestVo;
import com.froad.thrift.vo.ticket.TicketSummaryVo;
import com.froad.thrift.vo.ticket.TicketVerifyRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyResponseVo;

/**
 * 提货管理
 * @author yfy
 *
 */
@Service
public class TakeService {
	
	@Resource
	TicketService.Iface ticketService;
	@Resource
	OrgService.Iface orgService;
	
	/**
	 * 根据提货码获取提货信息
	 * @param takeCode
	 * @return
	 * @throws TException
	 */
	public List<DeliveryVo> findPageByConditions(String takeCode,String orgCode) throws TException{
		
		List<DeliveryVo> deliveryList = new ArrayList<DeliveryVo>();
		TicketRelatedRequestVo vo = new TicketRelatedRequestVo();
		vo.setResource("1");
		vo.setTicketId(takeCode);
		vo.setOrgCode(orgCode);
		TicketListResponseVo ticketListVo = ticketService.getRelatedTickets(vo);
		LogCvt.info("tokecode:"+takeCode+"     findPageByConditions-ticketService.getRelatedTickets根据提货码查询提货信息后端返回:"+JSON.toJSONString(ticketListVo));
		List<TicketDetailVo> ticketDetailList = ticketListVo.getTicketDetailList();
		if(ticketDetailList != null && ticketDetailList.size() > 0){
			for(TicketDetailVo ticketDetailVo : ticketDetailList){
				DeliveryVo deliveryVo = new DeliveryVo();
				deliveryVo.setProductId(ticketDetailVo.getProductId());//商品ID
				deliveryVo.setProductName(ticketDetailVo.getProductName());//商品名称
				deliveryVo.setNumber(ticketDetailVo.getQuantity());//数量
				deliveryVo.setTakeCode(ticketDetailVo.getTicketId());//提货码(券ID)
				deliveryVo.setVaildDate(DateUtil.formatDate(new Date(ticketDetailVo.getExpireTime()), DateUtil.DATE_FORMAT_02));//有效日期
				deliveryVo.setPhone(ticketDetailVo.getMobile());//手机号
				deliveryVo.setTakePerson(ticketDetailVo.getMemberName());//提货人
				deliveryVo.setPrice(ticketDetailVo.getPrice());//金额
				long expireTime = ticketDetailVo.getExpireTime();
				// 获取当前的终点时刻
				long endOfTodDay = System.currentTimeMillis();
				// 是否过期判断
				if (endOfTodDay > expireTime) {
					deliveryVo.setIsPass("0");
				} else {
					deliveryVo.setIsPass("1");
				}
				deliveryList.add(deliveryVo);
			}
		}
		return deliveryList;
	}
	
	/**
	 * 验证提货
	 * @param clientId
	 * @param operator
	 * @param ticketList
	 * @return
	 */
	public MsgVo takeDelivery(String clientId,String orgCode,String operatorId, List<DeliveryVo> ticketList){
		MsgVo msgVo = new MsgVo();
		TicketVerifyRequestVo ticketVerifyReqVo = new TicketVerifyRequestVo();
		List<TicketSummaryVo> ticketSummaryList = new ArrayList<TicketSummaryVo>();
		if(ticketList != null && ticketList.size() > 0){
			for(DeliveryVo vo : ticketList){
				TicketSummaryVo ticketSummaryVo = new TicketSummaryVo();
				ticketSummaryVo.setTicketId(vo.getTakeCode());//提货码
				ticketSummaryVo.setProductId(vo.getProductId());//商品ID
				ticketSummaryVo.setQuantity(vo.getNumber());//数量
				ticketSummaryList.add(ticketSummaryVo);
			}
		}
		ticketVerifyReqVo.setResource("1");
		ticketVerifyReqVo.setTicketList(ticketSummaryList);
		ticketVerifyReqVo.setClientId(clientId);
		ticketVerifyReqVo.setMerchantUserId(Long.valueOf(operatorId));
		ticketVerifyReqVo.setOrgCode(orgCode);
		TicketVerifyResponseVo ticketVerifyRespVo = null;
		try {
			ticketVerifyRespVo = ticketService.verifyTickets(ticketVerifyReqVo);
			if(ticketVerifyRespVo.getResultVo().getResultCode().equals(EnumTypes.success.getCode())){
				msgVo.setResult(true);
				msgVo.setMsg("提货成功");
			}else{
				msgVo.setResult(false);
				msgVo.setMsg(ticketVerifyRespVo.getResultVo().getResultDesc());
			}
		} catch (TException e) {
			LogCvt.info("确认提货"+e.getMessage(), e);
			msgVo.setResult(false);
			msgVo.setMsg("提货异常");
		}
		return msgVo;
	}

	/**
	 * 提货分页列表条件查询
	 * @param orgCode
	 * @param state
	 * @param startDate
	 * @param endDate
	 * @param pageNumber
	 * @param pageSize
	 * @return
	 * @throws TException
	 * @throws ParseException
	 */
	public QueryResult<DeliveryVo> findPageByConditions(DeliveryVoReq voReq) throws TException, ParseException{
		LogCvt.info("提货码列表查询请求参数"+JSON.toJSONString(voReq));
		TicketListRequestVo ticketVo = new TicketListRequestVo();

		requestArgument(voReq, ticketVo);
		
		PageVo page = new PageVo();
		page.setPageNumber(voReq.getPageNumber());
		page.setPageSize(voReq.getPageSize());
		page.setLastPageNumber(voReq.getLastPageNumber());
		page.setFirstRecordTime(voReq.getFirstRecordTime());
		page.setLastRecordTime(voReq.getLastRecordTime());
		ticketVo.setPageVo(page);
		
		QueryResult<DeliveryVo> queryVo = new QueryResult<DeliveryVo>();
		List<DeliveryVo> deliveryList = new ArrayList<DeliveryVo>();
		TicketListResponseVo ticketListVo = null;
		try {
			ticketListVo = ticketService.getTicketList(ticketVo);
			List<TicketDetailVo> ticketList = ticketListVo.getTicketDetailList();
			if(ticketList != null && ticketList.size() > 0){
				for(TicketDetailVo ticketDetail : ticketList){
					DeliveryVo deliveryVo = new DeliveryVo();
					deliveryVo.setOrderId(ticketDetail.getSubOrderId()); //子订单编号
					deliveryVo.setTakeCode(ticketDetail.getTicketId());//提货码
					deliveryVo.setProductName(ticketDetail.getProductName());//商品名称
					deliveryVo.setNumber(ticketDetail.getQuantity());//数量
					if(ticketDetail.getCreateTime() > 0){
						deliveryVo.setStartDate(DateUtil.formatDate(new Date(ticketDetail.getCreateTime()), DateUtil.DATE_TIME_FORMAT_01));//下单时间
					}
					deliveryVo.setTakePerson(ticketDetail.getMemberName());//提货人
					deliveryVo.setPhone(ticketDetail.getMobile());//提货人电话
					if(StringUtil.equals(ticketDetail.getStatus(), TakeStateEnum.sent.getCode())){
						deliveryVo.setTakeState(TakeStateEnum.sent.getDescription());//提货状态
					}else if(StringUtil.equals(ticketDetail.getStatus(), TakeStateEnum.consumed.getCode())){
						deliveryVo.setTakeState(TakeStateEnum.consumed.getDescription());//提货状态
					}else if(StringUtil.equals(ticketDetail.getStatus(), TakeStateEnum.expired.getCode())){
						deliveryVo.setTakeState(TakeStateEnum.expired.getDescription());//提货状态
					}else if(StringUtil.equals(ticketDetail.getStatus(), TakeStateEnum.refunded.getCode())){
						deliveryVo.setTakeState(TakeStateEnum.refunded.getDescription());//提货状态
					}else if(StringUtil.equals(ticketDetail.getStatus(), TakeStateEnum.refunding.getCode())){
						deliveryVo.setTakeState(TakeStateEnum.refunding.getDescription());//提货状态
					}else if(StringUtil.equals(ticketDetail.getStatus(), TakeStateEnum.refund_failed.getCode())){
						deliveryVo.setTakeState(TakeStateEnum.refund_failed.getDescription());//提货状态
					}else if(StringUtil.equals(ticketDetail.getStatus(), TakeStateEnum.expired_refunded.getCode())){
						deliveryVo.setTakeState(TakeStateEnum.expired_refunded.getDescription());//提货状态
					}else{
						deliveryVo.setTakeState("");
					}
					deliveryVo.setOrgName(ticketDetail.getOrgName()); //提货网点名称
					OrgVo orgVo = orgService.getOrgById(voReq.getClientId(),ticketDetail.getSorgCode());
					if(orgVo != null){
						deliveryVo.setSuperOrgName(orgVo.getOrgName()); //提货网点上级机构
					}
					deliveryList.add(deliveryVo);
				}
				
			}
			if(ticketListVo.getPageVo() != null){
				queryVo.setTotalCount(ticketListVo.getPageVo().getTotalCount());
				queryVo.setPageCount(ticketListVo.getPageVo().getPageCount());
				queryVo.setPageNumber(ticketListVo.getPageVo().getPageNumber());
				queryVo.setLastPageNumber(ticketListVo.getPageVo().getLastPageNumber());
				queryVo.setFirstRecordTime(ticketListVo.getPageVo().getFirstRecordTime());
				queryVo.setLastRecordTime(ticketListVo.getPageVo().getLastRecordTime());
			}
		} catch (Exception e) {
			LogCvt.info("提货列表条件"+e.getMessage(), e);
		}
		queryVo.setResult(deliveryList);
		
		return queryVo;
	}
	
	/**
	 * 
	 * getTakeExport: 提货码下载优化
	 *
	 * @author 明灿
	 * 2015年9月16日 下午2:31:31
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String, Object> getTakeExport(DeliveryVoReq voReq) throws TException, ParseException {
		LogCvt.info("====提货码下载请求参数====" + JSON.toJSONString(voReq));
		Map<String, Object> map = new HashMap<String, Object>();
		// 请求参数封装
		TicketListRequestVo ticketVo = new TicketListRequestVo();
		requestArgument(voReq, ticketVo);
		ExportResultRes resultRes = ticketService.exportTickets(ticketVo);
		LogCvt.info("====提货码下载返回数据====" + JSON.toJSONString(resultRes));
		if (resultRes!=null&&EnumTypes.success.getCode().equals(resultRes.getResultVo().getResultCode())) {
			map.put("url", resultRes.getUrl());
		}
		map.put("code", resultRes.getResultVo().getResultCode());
		map.put("message", resultRes.getResultVo().getResultDesc());
		return map;
	}
	
	/**
	 * 
	 * requestArgument:提货码下载优化请求参数封装
	 *
	 * @author 明灿
	 * 2015年9月16日 下午2:32:01
	 * @param voReq
	 * @param ticketVo
	 * @throws ParseException
	 *
	 */
	private void requestArgument(DeliveryVoReq voReq, TicketListRequestVo ticketVo) throws ParseException {
		ticketVo.setClientId(voReq.getClientId());
		ticketVo.setResource("1");
		ticketVo.setType("2");
		if (StringUtils.isNotEmpty(voReq.getOrgCode())) {
			ticketVo.setSource("4");
			ticketVo.setOrgCode(voReq.getOrgCode());
		}
		if (StringUtils.isNotEmpty(voReq.getTakeState())) {// 券码状态
			ticketVo.setStatus(voReq.getTakeState());
		}
		if (StringUtils.isNotEmpty(voReq.getStartDate())) {// 开始时间
			ticketVo.setStartDate(DateUtil.DateFormat(voReq.getStartDate()) + "");
		}
		if (StringUtil.isNotEmpty(voReq.getEndDate())) {// 结束时间
			ticketVo.setEndDate(DateUtil.DateFormat(voReq.getEndDate()) + "");
		}
		if (StringUtil.isNotEmpty(voReq.getOrderId())) {
			ticketVo.setSource("4");
			ticketVo.setSubOrderId(voReq.getOrderId()); // 子订单编号
		}
	}
}
