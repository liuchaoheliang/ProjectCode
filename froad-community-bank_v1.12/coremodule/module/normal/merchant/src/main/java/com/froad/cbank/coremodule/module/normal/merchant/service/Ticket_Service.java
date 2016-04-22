package com.froad.cbank.coremodule.module.normal.merchant.service;

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
import com.froad.cbank.coremodule.module.normal.merchant.enums.OutletDisableStatusEnum;
import com.froad.cbank.coremodule.module.normal.merchant.enums.TicketStatus;
import com.froad.cbank.coremodule.module.normal.merchant.enums.UserType;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Member_Product_VerifyInfo_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.PageRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Ticket_Detail_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Ticket_Detail_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Ticket_List_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Ticket_Code_Use_Banth_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Ticket_Code_Use_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Ticket_Code_Use_Res;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.PramasUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.TargetObjectFormat;
import com.froad.thrift.service.OutletService;
import com.froad.thrift.service.SettlementService;
import com.froad.thrift.service.TicketService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.OriginVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.settlement.SettlementVo;
import com.froad.thrift.vo.ticket.MemberProductVerifyInfoVo;
import com.froad.thrift.vo.ticket.TicketDetailRequestVo;
import com.froad.thrift.vo.ticket.TicketDetailResponseVo;
import com.froad.thrift.vo.ticket.TicketDetailVo;
import com.froad.thrift.vo.ticket.TicketListRequestVo;
import com.froad.thrift.vo.ticket.TicketListResponseVo;
import com.froad.thrift.vo.ticket.TicketSummaryVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyOfMergerResponseVo;
import com.froad.thrift.vo.ticket.TicketVerifyRequestVo;
import com.froad.thrift.vo.ticket.TicketVerifyResponseVo;
import com.froad.thrift.vo.ticket.VerifyTicketListByPageResponseVo;

/**
 * 券码
 * @ClassName Ticket_Service
 * @author zxl
 * @date 2015年4月17日 上午9:55:12
 */
@Service
public class Ticket_Service {
	
	@Resource
	TicketService.Iface ticketService;
	@Resource
	SettlementService.Iface settlementService;
	@Resource
	OutletService.Iface outletService;
	/**
	 * 提货码查询列表 query_code_list
	 * 
	 * @param model
	 * @param req
	 * @throws TException 
	 * @throws ParseException 
	 */
	public Map<String,Object> query_code_list(Query_Ticket_List_Req req) throws MerchantException, TException, ParseException {
		//固定查询不能大于三90天
		if(StringUtil.isEmpty(req.getStartDate())){
			//
			req.setStartDate(StringUtil.isEmpty(req.getEndDate())?
					PramasUtil.notEmpyBe(0, true):
					PramasUtil.notEmpyBe(PramasUtil.DateFormat(req.getEndDate()), false)
				   );
		}
		if(StringUtil.isEmpty(req.getEndDate())){
			req.setEndDate(StringUtil.isEmpty(req.getStartDate())?
					PramasUtil.notEmpyEn_E(0, true):
					PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartDate()), false)
				   );
		}
		if(PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartDate()),PramasUtil.DateFormat(req.getEndDate()))){
			throw new MerchantException("608","查询时间不能超过90天");
		}

		Map<String,Object> map=new HashMap<String,Object>();
		TicketListRequestVo  voreq=new TicketListRequestVo();
		voreq.setType(req.getType());
		voreq.setStatus(req.getStatus());
		voreq.setClientId(req.getClientId());
		voreq.setSource("3");
		voreq.setMerchantId(req.getMerchantUser().getMerchantId());
		if(StringUtils.isNotBlank(req.getSecuritiesNo())){
			voreq.setTicketId(req.getSecuritiesNo());
		}
		
		//已消费
		if(TicketStatus.consumed.getCode().equals(req.getStatus())){
			/**管理员只看自己所属门店  这里用“非”为了兼容数据*/
			if(!req.getMerchantUser().getMerchantRoleId().equals(UserType.admin.getCode())){
				voreq.setOutletId(req.getMerchantUser().getOutletId());
			}else{
				voreq.setOutletId(req.getOutletId());
			}
		}
		
		if(StringUtils.isNotBlank(req.getStartDate())){
			voreq.setStartDate(PramasUtil.DateFormat(req.getStartDate())+"");
		}
		if(StringUtils.isNotBlank(req.getEndDate())){
			voreq.setEndDate(PramasUtil.DateFormatEnd(req.getEndDate())+"");
		}
		if(StringUtils.isNotBlank(req.getUserName())){
			voreq.setMerchantUserName(req.getUserName());
		}
		voreq.setResource("2");  //商户端
		
		// 排序字段(不传默认为创建时间倒序,1-根据消费时间倒序)
		voreq.setSortField("1") ;
		
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		voreq.setPageVo(pageVo);
		LogCvt.info("提货码请求server端参数："+JSON.toJSONString(voreq));
		TicketListResponseVo resV=ticketService.getTicketList(voreq);
		LogCvt.info("提货码列表server端返回 ："+JSON.toJSONString(resV));
		if(resV.getTicketDetailListSize()>0){
			List<Query_Ticket_Detail_Res> listRes=new ArrayList<Query_Ticket_Detail_Res>();
			List<String> codelist=new ArrayList<String>();
			for(TicketDetailVo vo:resV.getTicketDetailList()){
				Query_Ticket_Detail_Res code=new Query_Ticket_Detail_Res();
				TargetObjectFormat.copyProperties(vo, code);
				if("0".equals(vo.getOutletId()) || StringUtils.isBlank(vo.getOutletName())){
//					code.setOutletName("总店");
					OutletVo deOutVo =  merchantIsDefalut(vo.getOutletId(), req.getClientId(), req.getMerchantUser().getMerchantId());
					if(null != deOutVo){
						code.setOutletName(deOutVo.getOutletName());
					}
				}
				/**2015-11-23兼容老数据*/
				if(vo.getOutletName().equals("总店")){
					OutletVo outletVo = new OutletVo();
					outletVo.setClientId(req.getClientId());
					outletVo.setMerchantId(req.getMerchantUser().getMerchantId());
					List<String> disableStatusList = new ArrayList<String>();
					disableStatusList.add(OutletDisableStatusEnum.normal.getCode());
					outletVo.setDisableStatusList(disableStatusList);
					List<OutletVo> listOutletPage= outletService.getOutlet(outletVo);
					if(listOutletPage!=null&&listOutletPage.size()>0){
						for(OutletVo outVo : listOutletPage){
							//如果是默认门店绑定门店
							if(outVo.isDefault){
								code.setOutletName(outVo.getOutletName());
							}
						}
					}else{
						throw new MerchantException(EnumTypes.fail.getCode(),"此商户无门店!");
					}
				}
				
				codelist.add(vo.getTicketId());
                listRes.add(code);
			}
			List<SettlementVo> listcode=settlementService.getTicketIdSettlementList(codelist);
			if(listcode!=null&&listcode.size()>0){
				int number=0;
				for(Query_Ticket_Detail_Res rs:listRes){
					 String settstate=listcode.get(number)!=null?listcode.get(number).getSettleState():"";
                     rs.setSettleState(settstate);
                     number++;
				}
			}
			map.put("codeList", listRes);
			map.put("count",resV.getPageVo().getTotalCount());
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(resV.getPageVo(), pageRes);
			map.put("page",pageRes);
		}else{
			throw new MerchantException(EnumTypes.noresult.getCode(),EnumTypes.noresult.getMsg());
		}
		return map;
	}
	
	/**
	 * 提货码查询列表 query_code_list
	 * 
	 * @param model
	 * @param req
	 * @throws TException 
	 * @throws ParseException 
	 */
	public Map<String,Object> query_code_count(Query_Ticket_List_Req req) throws MerchantException, TException, ParseException {
		//固定查询不能大于三90天
		if(StringUtil.isEmpty(req.getStartDate())){
			//
			req.setStartDate(StringUtil.isEmpty(req.getEndDate())?
					PramasUtil.notEmpyBe(0, true):
					PramasUtil.notEmpyBe(PramasUtil.DateFormat(req.getEndDate()), false)
				   );
		}
		if(StringUtil.isEmpty(req.getEndDate())){
			req.setEndDate(StringUtil.isEmpty(req.getStartDate())?
					PramasUtil.notEmpyEn_E(0, true):
					PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartDate()), false)
				   );
		}
		if(PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartDate()),PramasUtil.DateFormat(req.getEndDate()))){
			throw new MerchantException("608","查询时间不能超过90天");
		}

		Map<String,Object> map=new HashMap<String,Object>();
		TicketListRequestVo  voreq=new TicketListRequestVo();
		voreq.setClientId(req.getClientId());
		voreq.setSource("3");
		voreq.setType(req.getType());
		voreq.setStatus(req.getStatus());
		voreq.setMerchantId(req.getMerchantUser().getMerchantId());
		if(StringUtils.isNotBlank(req.getSecuritiesNo())){
			voreq.setTicketId(req.getSecuritiesNo());
		}
		voreq.setResource("2");
		TicketListResponseVo resV=ticketService.getTicketList(voreq);
		if(EnumTypes.success.getCode().equals(resV.getResultVo().getResultCode())){
			map.put("count",resV.getPageVo().getTotalCount());
		}else{
			map.put("count","0");
		}
		return map;
	}
	
	/**
	 * 提货码详情查询
	 * @tilte querySecuritiesDetail
	 * @author zxl
	 * @date 2015年3月23日 下午2:08:20
	 * @param map
	 * @return
	 * @throws MerchantException
	 * @throws TException 
	 */
	public HashMap<String,Object> querySecuritiesDetail(Query_Ticket_Detail_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		TicketDetailRequestVo requestVo = new TicketDetailRequestVo();
		requestVo.setOption("2");
		requestVo.setMerchantId(req.getMerchantUser().getMerchantId());
		requestVo.setTicketId(req.getSecuritiesNo());
		requestVo.setClientId(req.getClientId());
		requestVo.setResource("2");
		TicketDetailResponseVo vo = ticketService.getTicketDetails(requestVo);
		List<String> codelist=new ArrayList<String>();
		if(EnumTypes.success.getCode().equals(vo.getResultVo().getResultCode())){
			Query_Ticket_Detail_Res po = new Query_Ticket_Detail_Res();
			TargetObjectFormat.copyProperties(vo.getTicketDetail(), po);
			if("0".equals(vo.getTicketDetail().getOutletId()) || StringUtils.isBlank(vo.getTicketDetail().getOutletName())){
//				po.setOutletName("总店");
				OutletVo deOutVo =  merchantIsDefalut(vo.getTicketDetail().getOutletId(), req.getClientId(), req.getMerchantUser().getMerchantId());
				if(null != deOutVo){
					po.setOutletName(deOutVo.getOutletName());
				}
			}
			codelist.add(vo.getTicketDetail().getTicketId());
			List<SettlementVo> listcode=settlementService.getTicketIdSettlementList(codelist);
			if(listcode!=null&&listcode.size()>0){
				 String settstate=listcode.get(0).getSettleState();
				 po.setSettleState(settstate);
			}
			reMap.put("codeDetails", po);
		}else{
			throw new MerchantException(vo.getResultVo().getResultCode(),vo.getResultVo().getResultDesc());
		}
		return reMap;
	}
	
	

	/**
	 * 提货码使用
	 * @tilte securitiesUsed
	 * @author zxl
	 * @date 2015年3月23日 下午2:11:09
	 * @param map
	 * @return
	 * @throws MerchantException
	 * @throws TException 
	 */
	public HashMap<String,Object> securitiesUsed(Ticket_Code_Use_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		TicketVerifyRequestVo reqvo = new TicketVerifyRequestVo();
		reqvo.setMerchantId(req.getMerchantUser().getMerchantId());
		reqvo.setClientId(req.getClientId());
		reqvo.setResource("2");
		
		String outletId = req.getMerchantUser().getOutletId();
		/**2015-11-12 迭代1.7 
		 * 放开商户管理员员收银权限，将商户管理员关联在默认门店上其收银均记录在默认门店中。
		 * 
		 *管理员只看自己所属门店  这里用“非”为了兼容数据*/
		if(req.getMerchantUser().getMerchantRoleId().equals(UserType.admin.getCode())){
			OutletVo deOutVo =  merchantIsDefalut(outletId, req.getClientId(), req.getMerchantUser().getMerchantId());
			if(null != deOutVo){
				reqvo.setOutletId(deOutVo.getOutletId());
				reqvo.setOutletName(deOutVo.getOutletName());
			}
		}else{
			reqvo.setOutletId(req.getMerchantUser().getOutletId());
			reqvo.setOutletName(req.getMerchantUser().getOutletName());
		}
		reqvo.setMerchantUserId(req.getMerchantUser().getId());
		reqvo.setMerchantUserName(req.getMerchantUser().getUsername());
		List<TicketSummaryVo> ticketList = new ArrayList<TicketSummaryVo>();
		
		String[] number=req.getSecuritiesNo().split(",");
		if(number.length>1){
			String[] productId=req.getProductId().split(",");
			for(int i=0 ;i<number.length;i++){
				TicketSummaryVo t = new TicketSummaryVo();
				t.setQuantity(1);
				t.setTicketId(number[i]);
				t.setProductId(productId[i]);
				ticketList.add(t);
			}
		}else{
			TicketSummaryVo t = new TicketSummaryVo();
			t.setTicketId(req.getSecuritiesNo());
			t.setQuantity(1);
			t.setProductId(req.getProductId());
			ticketList.add(t);
		}
		
		reqvo.setTicketList(ticketList);
		TicketVerifyResponseVo resp = ticketService.verifyTickets(reqvo);
		
		if(resp!=null&&resp.getResultVo().getResultCode().equals("0000")){
			List<Ticket_Code_Use_Res> listRes=new ArrayList<Ticket_Code_Use_Res>();
			for(TicketSummaryVo vo:resp.getValidTickets()){
				Ticket_Code_Use_Res res=new Ticket_Code_Use_Res();
				TargetObjectFormat.copyProperties(vo, res);
				listRes.add(res);
			}
		    reMap.put("successTicketCodes", listRes);
		     
		   //失败
			List<Ticket_Code_Use_Res> listResf=new ArrayList<Ticket_Code_Use_Res>();
			for(TicketSummaryVo vo:resp.getInValidTickets()){
				Ticket_Code_Use_Res res=new Ticket_Code_Use_Res();
				TargetObjectFormat.copyProperties(vo, res);
				listResf.add(res);
			}
			reMap.put("failTicketCodes", listResf);
		}else {
			throw new MerchantException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		
		return reMap;
	}
	
	/**
	 * 获取券列表(相同商品合并成一个)query_code_list
	 * pc
	 * @param model
	 * @param req
	 * @throws TException 
	 * @throws ParseException 
	 */
	public Map<String,Object> query_code_list_all(Query_Ticket_List_Req req) throws MerchantException, TException, ParseException {
		Map<String,Object> map=new HashMap<String,Object>();
		TicketListRequestVo  voreq=new TicketListRequestVo();
		voreq.setType(req.getType());
		voreq.setStatus(req.getStatus());
		voreq.setClientId(req.getClientId());
		voreq.setSource("3");
		voreq.setMerchantId(req.getMerchantUser().getMerchantId());
		voreq.setTicketId(req.getTicketId());
		voreq.setSubOrderId(req.getSubOrderId());
		voreq.setMemberCode(req.getMemberCode());
		//已消费
		if(TicketStatus.consumed.getCode().equals(req.getStatus())){
			/**管理员只看自己所属门店  这里用“非”为了兼容数据*/
			if(!req.getMerchantUser().getMerchantRoleId().equals(UserType.admin.getCode())){
				voreq.setOutletId(req.getMerchantUser().getOutletId());
			}else{
				voreq.setOutletId(req.getOutletId());
			}
		}
		if(StringUtils.isNotBlank(req.getStartDate())){
			voreq.setStartDate(PramasUtil.DateFormat(req.getStartDate())+"");
		}
		if(StringUtils.isNotBlank(req.getEndDate())){
			voreq.setEndDate(PramasUtil.DateFormatEnd(req.getEndDate())+"");
		}
		if(StringUtils.isNotBlank(req.getUserName())){
			voreq.setMerchantUserName(req.getUserName());
		}
		if(StringUtils.isNotBlank(req.getTicketId())){
			voreq.setTicketId(req.getTicketId());
		}
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		voreq.setPageVo(pageVo);
		voreq.setResource("2");
		TicketListResponseVo   resV=ticketService.getTicketListOfMerger(voreq);
		if(EnumTypes.success.getCode().equals(resV.getResultVo().getResultCode())){
			List<Query_Ticket_Detail_Res> listRes=new ArrayList<Query_Ticket_Detail_Res>();
			List<String> codelist=new ArrayList<String>();
			for(TicketDetailVo vo:resV.getTicketDetailList()){
				Query_Ticket_Detail_Res code=new Query_Ticket_Detail_Res();
				TargetObjectFormat.copyProperties(vo, code);
				if(StringUtils.isNotBlank(vo.getConsumeTime())){
					code.setConsumeTime(Long.valueOf(vo.getConsumeTime()));
				}else{
					code.setConsumeTime(0);
				}
					
				if("0".equals(vo.getOutletId()) || StringUtils.isBlank(vo.getOutletName())){
//					code.setOutletName("总店");
					OutletVo deOutVo =  merchantIsDefalut(vo.getOutletId(), req.getClientId(), req.getMerchantUser().getMerchantId());
					if(null != deOutVo){
						code.setOutletName(deOutVo.getOutletName());
					}
				}
				codelist.add(vo.getTicketId());
                listRes.add(code);
			}
			List<SettlementVo> listcode=settlementService.getTicketIdSettlementList(codelist);
			if(listcode!=null&&listcode.size()>0){
				int number=0;
				for(Query_Ticket_Detail_Res rs:listRes){
					 String settstate=listcode.get(number)!=null?listcode.get(number).getSettleState():"";
                     rs.setSettleState(settstate);
                     number++;
				}
			}
			
			map.put("codeList", listRes);
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(resV.getPageVo(), pageRes);
			map.put("page",pageRes);
		}else{
			throw new MerchantException(resV.getResultVo().getResultCode(),resV.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 提货码使用
	 * @tilte securitiesUsed
	 * pc
	 * @date 2015年3月23日 下午2:11:09
	 * @param map
	 * @return
	 * @throws MerchantException
	 * @throws TException 
	 */
	public HashMap<String,Object> securitiesUsed_all(Ticket_Code_Use_Banth_Req req) throws MerchantException, TException{
		HashMap<String,Object> reMap = new HashMap<String, Object>();
		
		OriginVo ori=new OriginVo();
		ori.setOperatorId(req.getMerchantUser().getId());
		ori.setOperatorIp(req.getIp());
		ori.setPlatType(req.getPlatType());
		ori.setClientId(req.getClientId());

		TicketVerifyOfMergerRequestVo reqvo = new TicketVerifyOfMergerRequestVo();
		reqvo.setMerchantId(req.getMerchantUser().getMerchantId());
		
		/**管理员只看自己所属门店  这里用“非”为了兼容数据*/
		if(req.getMerchantUser().getMerchantRoleId().equals(UserType.admin.getCode())){
			OutletVo deOutVo =  merchantIsDefalut(req.getMerchantUser().getOutletId(), req.getClientId(), req.getMerchantUser().getMerchantId());
			if(null != deOutVo){
				reqvo.setOutletId(deOutVo.getOutletId());
				reqvo.setOutletName(deOutVo.getOutletName());
			}
		}else{
			reqvo.setOutletId(req.getMerchantUser().getOutletId());
			reqvo.setOutletName(req.getMerchantUser().getOutletName());
		}
		
		reqvo.setMerchantUserName(req.getMerchantUser().getUsername());
		reqvo.setMemberCode(req.getMemberCode());
		//传入子订单号
		reqvo.setSubOrderId(req.getSubOrderId());
		reqvo.setMemberCode(req.getMemberCode());
		if(StringUtils.isNotBlank(req.getMustValidTicketId())){
			reqvo.setMustValidTicketId(req.getMustValidTicketId());
		}
		List<TicketSummaryVo> ticketList = new ArrayList<TicketSummaryVo>();
        
		
		//批量提货（商品id/提货数量）
//		String[] number=req.getSecuritiesNo().split(",");
		String[] quantitys=req.getQuantitys().split(",");
		if(quantitys.length>1){
			String[] productId=req.getProductIds().split(",");
			for(int i=0 ;i<quantitys.length;i++){
				TicketSummaryVo t = new TicketSummaryVo();
				t.setQuantity(Integer.valueOf(quantitys[i]));
//				t.setTicketId(number[i]);
				t.setProductId(productId[i]);
				ticketList.add(t);
			}
		}else{
			TicketSummaryVo t = new TicketSummaryVo();
//			t.setTicketId(req.getSecuritiesNo());
			t.setQuantity(Integer.valueOf(req.getQuantitys()));
			t.setProductId(req.getProductIds());
			ticketList.add(t);
		}
		
		reqvo.setTicketList(ticketList);
		reqvo.setOriginVo(ori);
		LogCvt.info("提货码掩码==============server端返回 ================："+JSON.toJSONString(reqvo));
		TicketVerifyOfMergerResponseVo resp = ticketService.verifyGroupTicketsOfMerger(reqvo);
		
		if(resp!=null&&resp.getResultVo().getResultCode().equals("0000")){
			List<Ticket_Code_Use_Res> listRes=new ArrayList<Ticket_Code_Use_Res>();
			if(resp.getValidTickets()!=null&&resp.getValidTickets().size()>0){
				for(TicketSummaryVo vo:resp.getValidTickets()){
					Ticket_Code_Use_Res res=new Ticket_Code_Use_Res();
					TargetObjectFormat.copyProperties(vo, res);
					listRes.add(res);
				}
			}
		    reMap.put("successTicketCodes", listRes);
		     
		   //失败
			List<Ticket_Code_Use_Res> listResf=new ArrayList<Ticket_Code_Use_Res>();
			if(resp.getInValidTickets()!=null&&resp.getInValidTickets().size()>0){
				for(TicketSummaryVo vo:resp.getInValidTickets()){
					Ticket_Code_Use_Res res=new Ticket_Code_Use_Res();
					TargetObjectFormat.copyProperties(vo, res);
					listResf.add(res);
				}
			}
			reMap.put("failTicketCodes", listResf);
			
			List<Member_Product_VerifyInfo_Res> listMember=new ArrayList<Member_Product_VerifyInfo_Res>();
			if(resp.getMemberProductVerifyInfos()!=null&&resp.getMemberProductVerifyInfos().size()>0){
				for(MemberProductVerifyInfoVo vo:resp.getMemberProductVerifyInfos()){
					Member_Product_VerifyInfo_Res res=new Member_Product_VerifyInfo_Res();
					TargetObjectFormat.copyProperties(vo, res);
					listMember.add(res);
				}
			}
			reMap.put("verifyQuentitys", listMember);
			
			
		}else {
			throw new MerchantException(resp.getResultVo().getResultCode(), resp.getResultVo().getResultDesc());
		}
		
		return reMap;
	}
	
	
	/**
	 * 商户验证码记录query_code_list
	 * 时间统计
	 * @param model
	 * @param req
	 * @throws TException 
	 * @throws ParseException 
	 */
	public Map<String,Object> query_code_list_count(Query_Ticket_List_Req req) throws MerchantException, TException, ParseException {
		Map<String,Object> map=new HashMap<String,Object>();
		TicketListRequestVo  voreq=new TicketListRequestVo();
		voreq.setType(req.getType());
		voreq.setStatus(req.getStatus());
		voreq.setClientId(req.getClientId());
		voreq.setSource("3");
		voreq.setMerchantId(req.getMerchantUser().getMerchantId());
		if(StringUtils.isNotBlank(req.getSecuritiesNo())){
			voreq.setTicketId(req.getSecuritiesNo());
		}
		//已消费
		if(TicketStatus.consumed.getCode().equals(req.getStatus())){
			//非超级管理员，门店数据共享
			if(!req.getMerchantUser().getMerchantRoleId().equalsIgnoreCase(UserType.admin.getCode())){
				voreq.setOutletId(req.getMerchantUser().getOutletId());
			}
		}
		
		Date date=new Date();
		//查询当天
		if(req.getDateType()==1){
			voreq.setStartDate(PramasUtil.DateFormatToString(date)+"");
			voreq.setEndDate(PramasUtil.totay(date)+"");
		}
		//查询本周
		if(req.getDateType()==2){
			voreq.setStartDate(PramasUtil.week(date)+"");
			voreq.setEndDate(PramasUtil.totay(date)+"");
		}
		//查询本月
		if(req.getDateType()==3){
			voreq.setStartDate(PramasUtil.month(date)+"");
			voreq.setEndDate(PramasUtil.totay(date)+"");
		}
		//查询三个月
		if(req.getDateType()==4){
			voreq.setStartDate(PramasUtil.month_3(date)+"");
			voreq.setEndDate(PramasUtil.totay(date)+"");
		}
		PageVo pageVo=new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		voreq.setPageVo(pageVo);
		voreq.setResource("2");
		VerifyTicketListByPageResponseVo resV=ticketService.getVerifyTicketListByPage(voreq);
		if(EnumTypes.success.getCode().equals(resV.getResultVo().getResultCode())){
			List<Query_Ticket_Detail_Res> listRes=new ArrayList<Query_Ticket_Detail_Res>();
			List<String> codelist=new ArrayList<String>();
			for(TicketDetailVo vo:resV.getTicketDetailList()){
				Query_Ticket_Detail_Res code=new Query_Ticket_Detail_Res();
				TargetObjectFormat.copyProperties(vo, code);
				if(StringUtils.isNotBlank(vo.getConsumeTime())){
					code.setConsumeTime(Long.valueOf(vo.getConsumeTime()));
				}else{
					code.setConsumeTime(0);
				}
				if("0".equals(vo.getOutletId()) || StringUtils.isBlank(vo.getOutletName())){
//					code.setOutletName("总店");
					OutletVo deOutVo =  merchantIsDefalut(vo.getOutletId(), req.getClientId(), req.getMerchantUser().getMerchantId());
					if(null != deOutVo){
						code.setOutletName(deOutVo.getOutletName());
					}
				}
				codelist.add(vo.getTicketId());
                listRes.add(code);
			}
			List<SettlementVo> listcode=settlementService.getTicketIdSettlementList(codelist);
			if(listcode!=null&&listcode.size()>0){
				int number=0;
				for(Query_Ticket_Detail_Res rs:listRes){
					 String settstate=listcode.get(number)!=null?listcode.get(number).getSettleState():"";
                     rs.setSettleState(settstate);
                     number++;
				}
			}
			map.put("codeList", listRes);
			map.put("count",resV.getPageVo().getTotalCount());
			map.put("totalprice",resV.getTotalAmount());
			PageRes pageRes = new PageRes();
			TargetObjectFormat.copyProperties(resV.getPageVo(), pageRes);
			map.put("page",pageRes);
		}else{
			throw new MerchantException(resV.getResultVo().getResultCode(),resV.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 
	 * query_ticket_down:(订单下载).
	 *
	 * @author wm
	 * 2015年9月9日 下午2:30:58
	 * @param req
	 * @return
	 * @throws MerchantException
	 * @throws TException
	 * @throws ParseException
	 *
	 */
	public Map<String,Object> query_ticket_down(Query_Ticket_List_Req req) throws MerchantException, TException, ParseException {
		//固定查询不能大于三90天
		if(StringUtil.isEmpty(req.getStartDate())){
			//
			req.setStartDate(StringUtil.isEmpty(req.getEndDate())?
					PramasUtil.notEmpyBe(0, true):
					PramasUtil.notEmpyBe(PramasUtil.DateFormat(req.getEndDate()), false)
				   );
		}
		if(StringUtil.isEmpty(req.getEndDate())){
			req.setEndDate(StringUtil.isEmpty(req.getStartDate())?
					PramasUtil.notEmpyEn_E(0, true):
					PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartDate()), false)
				   );
		}
		if(PramasUtil.notEmpyEn_E(PramasUtil.DateFormat(req.getStartDate()),PramasUtil.DateFormat(req.getEndDate()))){
			throw new MerchantException("608","查询时间不能超过90天");
		}

		Map<String,Object> map=new HashMap<String,Object>();
		TicketListRequestVo  voreq=new TicketListRequestVo();
		voreq.setType(req.getType());
		voreq.setStatus(req.getStatus());
		voreq.setClientId(req.getClientId());
		voreq.setSource("3");
		voreq.setMerchantId(req.getMerchantUser().getMerchantId());
		if(StringUtils.isNotBlank(req.getSecuritiesNo())){
			voreq.setTicketId(req.getSecuritiesNo());
		}
		//已消费
		if(TicketStatus.consumed.getCode().equals(req.getStatus())){
			/**管理员只看自己所属门店  这里用“非”为了兼容数据*/
			if(!req.getMerchantUser().getMerchantRoleId().equals(UserType.admin.getCode())){
				voreq.setOutletId(req.getMerchantUser().getOutletId());
			}else{
				voreq.setOutletId(req.getOutletId());
			}
			
		}
		if(StringUtils.isNotBlank(req.getStartDate())){
			voreq.setStartDate(PramasUtil.DateFormat(req.getStartDate())+"");
		}
		if(StringUtils.isNotBlank(req.getEndDate())){
			voreq.setEndDate(PramasUtil.DateFormatEnd(req.getEndDate())+"");
		}
		if(StringUtils.isNotBlank(req.getUserName())){
			voreq.setMerchantUserName(req.getUserName());
		}
		voreq.setResource("2");  //商户端
		
		// 排序字段(不传默认为创建时间倒序,1-根据消费时间倒序)
		voreq.setSortField("1") ;
		
		ExportResultRes resV=ticketService.exportTickets(voreq);
		if(EnumTypes.success.getCode().equals(resV.getResultVo().getResultCode()) && StringUtil.isNotBlank(resV.getUrl())){
			LogCvt.info("提货码下载返回参数："+JSON.toJSONString(resV));
			map.put("ticketUrl", resV.getUrl());
		}else{
			throw new MerchantException(resV.getResultVo().getResultCode(),resV.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**2015-11-12 迭代1.7 
	 * 放开商户管理员员收银权限，将商户管理员关联在默认门店上其收银均记录在默认门店中。
	 * @throws TException 
	 * @throws MerchantException 
	 * */
	public 	OutletVo merchantIsDefalut(String outletId, String clientId, String merchantId) throws TException, MerchantException{
		OutletVo vo = new OutletVo();
		//如果是商户没有门店去查默认门店
		if(outletId.equals("0") || StringUtils.isBlank(outletId)){
			OutletVo outletVo = new OutletVo();
			outletVo.setClientId(clientId);
			outletVo.setMerchantId(merchantId);
			List<OutletVo> listOutletPage= outletService.getOutlet(outletVo);
			if(listOutletPage!=null&&listOutletPage.size()>0){
				for(OutletVo outVo : listOutletPage){
					//如果是默认门店绑定门店
					if(outVo.isDefault){
						TargetObjectFormat.copyProperties(outVo, vo);
					}
				}
			}else{
				throw new MerchantException(EnumTypes.fail.getCode(),"此商户无门店!");
			}
		}
		return vo;
	}
}
