package com.froad.cbank.coremodule.normal.boss.support.refund;

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
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundExceptionDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundExceptionProductRes;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundExceptionReq;
import com.froad.cbank.coremodule.normal.boss.pojo.refund.RefundExceptionRes;
import com.froad.cbank.coremodule.normal.boss.support.common.ClientSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.ExceptionOfPaymentService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.payment.BossPaymentDetialVo;
import com.froad.thrift.vo.payment.BossPaymentPage;
import com.froad.thrift.vo.payment.BossPaymentVo;
import com.froad.thrift.vo.payment.BossQueryConditionVo;
import com.froad.thrift.vo.payment.BossRefundProductVo;

/**
 * 退款异常订单管理
 * @ClassName RefundExceptionSupport
 * @author zxl
 * @date 2015年10月10日 上午11:08:51
 */
@Service
public class RefundExceptionSupport {
	
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
	public HashMap<String,Object> list(RefundExceptionReq req) throws Exception{
		
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
		vo.setExceptionType("3");
		BossPaymentPage res = exceptionOfPaymentService.queryExceptionPaymentByPage(vo);
		
		if(Constants.RESULT_SUCCESS.equals(res.getResultVo().getResultCode())){
			List<RefundExceptionRes> list = new ArrayList<RefundExceptionRes>();
			Page page = new Page();
			BeanUtils.copyProperties(page, res.getPageVo());
			if(res.getPayments()!=null&&res.getPayments().size()>0){
				List<ClientRes> client = clientSupport.getClient();
				for(BossPaymentVo v : res.getPayments()){
					RefundExceptionRes r = new RefundExceptionRes();
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
					r.setExceptionType("退款异常");
					r.setExceptionDesc("退款失败");
					r.setProposal("再次退款");
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
		
		BossPaymentDetialVo res = exceptionOfPaymentService.queryOfRefundDetial(id);
		LogCvt.debug("detail---"+JSON.toJSONString(res));
		if(res!=null){
			RefundExceptionDetailRes vo = new RefundExceptionDetailRes();
			
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
			}
			
			if(res.getRefund()!=null){
				vo.setRefundId(res.getRefund().getRefundId());
				vo.setRefundState(res.getRefund().getRefundState());
				vo.setRefundTime(res.getRefund().getCreateTime());
				vo.setCash(res.getRefund().getCash());
				vo.setFftPoints(res.getRefund().getFftPoints());
				vo.setBankPoints(res.getRefund().getBankPoints());
				
				List<RefundExceptionProductRes> pList = new ArrayList<RefundExceptionProductRes>();
				
				for(BossRefundProductVo p : res.getRefund().getRefundShoppingVo().getRefundProductVo()){
					RefundExceptionProductRes product = new RefundExceptionProductRes();
					product.setProductId(p.getProductId());
					product.setProductName(p.getProductName());
					product.setMerchantName(res.getRefund().getRefundShoppingVo().getMerchantName());
					product.setProductCount(p.getQuantity()+p.getVipQuantity());
					double price1 = Arith.mul(Arith.div(p.getPrice(), 1000d), (double)p.getQuantity()) ;
					double price2 = Arith.mul(Arith.div(p.getVipPrice(), 1000d), (double)p.getVipQuantity()) ;
					product.setMoney(Arith.add(price1, price2));
					pList.add(product);
					
				}
				vo.setProductList(pList);
				
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
	 * 再次退款
	 * @tilte re
	 * @author zxl
	 * @date 2015年10月12日 上午10:18:32
	 * @param id
	 * @return
	 * @throws Exception
	 */
	public HashMap<String,Object> re(String id) throws Exception{
		HashMap<String,Object> map = new HashMap<String,Object>();
		ResultVo res = exceptionOfPaymentService.retryOfReturn(id);
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
