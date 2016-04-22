package com.froad.cbank.coremodule.normal.boss.support.settle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.settle.SettleExceptionDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.settle.SettleExceptionReq;
import com.froad.cbank.coremodule.normal.boss.pojo.settle.SettleExceptionRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.ExceptionOfPaymentService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossPaymentDetialVo;
import com.froad.thrift.vo.payment.BossPaymentPage;
import com.froad.thrift.vo.payment.BossPaymentVo;
import com.froad.thrift.vo.payment.BossQueryConditionVo;

/**
 * 结算异常订单管理
 * @ClassName SettlementExceptionSupport
 * @author zxl
 * @date 2015年9月18日 下午2:06:16
 */
@Service
public class SettlementExceptionSupport {
	
	@Resource
	ExceptionOfPaymentService.Iface exceptionOfPaymentService;
	
	@Resource
	ClientSupport clientSupport;
	/**
	 * 列表查询
	 * @tilte list
	 * @author zxl
	 * @date 2015年9月18日 下午2:33:08
	 * @param req
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> list(SettleExceptionReq req) throws Exception{
		
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		BossQueryConditionVo vo = new BossQueryConditionVo();
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		if(StringUtils.isNotBlank(req.getStartTime())){
			pageVo.setBegDate(Long.valueOf(req.getStartTime()));
		}
		if(StringUtils.isNotBlank(req.getEndTime())){
			pageVo.setEndDate(Long.valueOf(req.getEndTime()));
		}
		vo.setPageVo(pageVo);
		
		if(StringUtils.isNotBlank(req.getClientId())){
			vo.setClientId(req.getClientId());
		}
		if(StringUtils.isNotBlank(req.getOrderId())){
			vo.setOrderId(req.getOrderId());
		}
		
		//异常类型:1-支付异常;2-结算异常;3-退款异常
		vo.setExceptionType("2");
		BossPaymentPage res = exceptionOfPaymentService.queryExceptionPaymentByPage(vo);
		
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			List<SettleExceptionRes> list = new ArrayList<SettleExceptionRes>();
			Page page = new Page();
			BeanUtils.copyProperties(page, res.getPageVo());
			if(res.getPayments()!=null&&res.getPayments().size()>0){
				List<ClientRes> client = clientSupport.getClient();
				for(BossPaymentVo v : res.getPayments()){
					SettleExceptionRes r = new SettleExceptionRes();
					r.setOrderId(v.getOrderId());
					r.setBillNo(v.getBillNo());
					r.setPaymentType(v.getPaymentType());
					r.setCreateTime(v.getCreateTime());
					r.setPaymentId(v.getPaymentId());
					for(ClientRes c : client){
						if(c.getClientId().equals(v.getClientId())){
							r.setClientId(c.getClientName());
							break;
						}
					}
					r.setExceptionType("结算异常");
					r.setExceptionDesc("结算失败");
					r.setProposal("再次结算");
					list.add(r);
				}
			}
			map.put("list", list);
			map.put("page", page);
		}else{
			throw new BossException(res.getResultVo().getResultCode(), res.getResultVo().getResultDesc());
		}
		
		return map;
	}
	
	/**
	 * 异常订单详情
	 * @tilte detail
	 * @author zxl
	 * @date 2015年9月21日 上午10:33:18
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> detail(String id) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		
		BossPaymentDetialVo res = exceptionOfPaymentService.queryOfSettleDetial(id);
		LogCvt.debug("detail---"+JSON.toJSONString(res));
		if(res!=null){
			SettleExceptionDetailRes vo = new SettleExceptionDetailRes();
			
			if(res.getOrderVo()!=null){
				
				List<ClientRes> client = clientSupport.getClient();
				for(ClientRes c : client){
					if(c.getClientId().equals(res.getOrderVo().getClientId())){
						vo.setClientId(c.getClientName());
						break;
					}
				}
				vo.setOrderId(res.getOrderVo().getOrderId());
				vo.setOrderStatus(res.getOrderVo().getOrderStatus());
				vo.setCreateTime(res.getOrderVo().getCreateTime());
				vo.setTotalPrice(res.getOrderVo().getTotalPrice());
				vo.setMemberName(res.getOrderVo().getMemberName());
			}
			if(res.getPayment()!=null){
				vo.setPaymentTime(res.getPayment().getCreateTime());
				vo.setPaymentId(res.getPayment().getPaymentId());
				vo.setBillNo(res.getPayment().getBillNo());
				vo.setPaymentStatus(res.getPayment().getPaymentStatus());
				vo.setPointRate(res.getPayment().getPointRate());
				vo.setPaymentMethod(res.getPayment().getPaymentType()+"");
			}
			if(res.getSettlementVo()!=null){
				vo.setMerchantName(res.getSettlementVo().getMerchantName());
				vo.setOutletName(res.getSettlementVo().getOutletName());
				vo.setProductCount(res.getSettlementVo().getProductCount());
				vo.setMoney(res.getSettlementVo().getMoney());
				vo.setProductName(res.getSettlementVo().getProductName());
				if(res.getSettlementVo().getTickets()!=null&&res.getSettlementVo().getTickets().size()>0){
					String tickets = "";
					for(String s : res.getSettlementVo().getTickets()){
						tickets += (s+",");
					}
					vo.setTickets(tickets.substring(0,tickets.length()-1));
				}
			}
			vo.setExceptionType(res.getExceptionType());
			vo.setExceptionDesc(res.getExceptionDesc());
			vo.setProposal(res.getProposal());
			
			map.put("detail", vo);
		}else{
			throw new BossException("详情查询异常");
		}
		return map;
	}
	
	/**
	 * 再次发起结算
	 * @tilte re
	 * @author zxl
	 * @date 2015年9月21日 上午11:35:06
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> re(String id) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		ResultVo res = exceptionOfPaymentService.retryOfSettle(id);
		LogCvt.debug("res:"+JSON.toJSONString(res));
		if(!Constants.RESULT_SUCCESS.equals(res.getResultCode())){
			throw new BossException(res.getResultCode(), res.getResultDesc());
		}else{
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", res.getResultDesc());
		}
		return map;
	}
}
