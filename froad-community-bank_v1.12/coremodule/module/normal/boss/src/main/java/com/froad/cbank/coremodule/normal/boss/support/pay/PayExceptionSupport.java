package com.froad.cbank.coremodule.normal.boss.support.pay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.Arith;
import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.cbank.coremodule.normal.boss.pojo.pay.PayExceptionDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.pay.PayExceptionProductRes;
import com.froad.cbank.coremodule.normal.boss.pojo.pay.PayExceptionReq;
import com.froad.cbank.coremodule.normal.boss.pojo.pay.PayExceptionRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.ExceptionOfPaymentService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossPaymentDetialVo;
import com.froad.thrift.vo.payment.BossPaymentPage;
import com.froad.thrift.vo.payment.BossPaymentVo;
import com.froad.thrift.vo.payment.BossQueryConditionVo;
import com.froad.thrift.vo.payment.BossSubOrder;

/**
 * 支付异常订单管理
 * @ClassName PayExceptionSupport
 * @author zxl
 * @date 2015年10月10日 上午11:08:51
 */
@Service
public class PayExceptionSupport {
	
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
	public HashMap<String,Object> list(PayExceptionReq req) throws Exception{
		
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
		
		//异常类型:1-支付异常-退积分失败;2-结算异常;3-退款异常;4-支付异常-退现金失败
		
		if("1".equals(req.getExceptionType())){
			vo.setExceptionType("1");
		}else if("2".equals(req.getExceptionType())){
			vo.setExceptionType("4");
		}else{
			throw new BossException("异常类型错误");
		}
		
		
		BossPaymentPage res = exceptionOfPaymentService.queryExceptionPaymentByPage(vo);
		
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			List<PayExceptionRes> list = new ArrayList<PayExceptionRes>();
			Page page = new Page();
			BeanUtils.copyProperties(page, res.getPageVo());
			if(res.getPayments()!=null&&res.getPayments().size()>0){
				List<ClientRes> client = clientSupport.getClient();
				for(BossPaymentVo v : res.getPayments()){
					PayExceptionRes r = new PayExceptionRes();
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
					r.setExceptionType("支付异常");
					if("1".equals(req.getExceptionType())){
						r.setExceptionDesc("退积分失败");
						r.setProposal("退还积分");
					}else if("2".equals(req.getExceptionType())){
						r.setExceptionDesc("退现金失败");
						r.setProposal("退还现金");
					}
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
	public HashMap<String,Object> detail(String id,String exceptionType) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();

		BossPaymentDetialVo res = null;
		
		if("1".equals(exceptionType)){
			res = exceptionOfPaymentService.queryOfPaymentRefundPointDetial(id);
		}else if("2".equals(exceptionType)){
			res = exceptionOfPaymentService.queryOfPaymentRefundCashDetial(id);
		}else{
			throw new BossException("异常类型错误");
		}
		LogCvt.debug("detail---"+JSON.toJSONString(res));
		if(res!=null){
			PayExceptionDetailRes vo = new PayExceptionDetailRes();
			
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
			
			if(res.getSubOrders()!=null){
				List<PayExceptionProductRes> list = new ArrayList<PayExceptionProductRes>();
				for(BossSubOrder order : res.getSubOrders()){
					PayExceptionProductRes l = new PayExceptionProductRes();
					l.setProductId(order.getProductId());
					l.setProductName(order.getProductName());
					l.setProductCount(order.getQuantity());
					double price = Arith.div(order.getMoney(), 1000d) ;
					l.setPrice(price);
					l.setMoney(Arith.mul(price, (double)order.getQuantity()));
					list.add(l);
				}
				vo.setProductList(list);
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
	 * 退积分&现金
	 * @tilte re
	 * @author zxl
	 * @date 2015年10月14日 上午9:56:54
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> re(String id,String exceptionType) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		ResultVo res = null;
		if("1".equals(exceptionType)){
			res = exceptionOfPaymentService.returnPoint(id);
		}else if("2".equals(exceptionType)){
			res = exceptionOfPaymentService.returnCash(id);
		}else{
			throw new BossException("异常类型错误");
		}
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
