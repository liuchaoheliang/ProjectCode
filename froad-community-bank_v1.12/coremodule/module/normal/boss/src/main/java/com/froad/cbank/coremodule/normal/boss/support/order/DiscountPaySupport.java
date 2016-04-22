package com.froad.cbank.coremodule.normal.boss.support.order;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.order.DiscontPayListRes;
import com.froad.cbank.coremodule.normal.boss.pojo.order.DiscontPayListVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.DiscountPayDetailRes;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.thrift.service.BossOrderQueryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.BossPrefPayOrderDetailRes;
import com.froad.thrift.vo.order.BossPrefPayOrderListInfoVo;
import com.froad.thrift.vo.order.BossPrefPayOrderListReq;
import com.froad.thrift.vo.order.BossPrefPayOrderListRes;


/**
 * 
 * @ClassName: DiscountPaySupport
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年12月29日 上午9:52:37 
 * @desc <p>惠付订单support</p>
 */
@Service
public class DiscountPaySupport {
	
	@Resource
	BossOrderQueryService.Iface bossOrderQueryService;
	
	public HashMap<String,Object> list(DiscontPayListVoReq req) throws ParseException, TException, BossException{
		
		HashMap<String,Object> map=new HashMap<String,Object>();	
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(req.getPageNumber());
		pageVo.setPageSize(req.getPageSize());
		pageVo.setFirstRecordTime(req.getFirstRecordTime());
		pageVo.setLastPageNumber(req.getLastPageNumber());
		pageVo.setLastRecordTime(req.getLastRecordTime());
		//得到thrift请求vo
		BossPrefPayOrderListReq temp=new BossPrefPayOrderListReq();
		
		//判断请求条件，当条件不为空的时候就给thrift请求vo设置对应的值
		
		if(StringUtil.isNotEmpty(req.getClientId())){//客户端
			temp.setClientId(req.getClientId());
		}if(StringUtil.isNotEmpty(req.getBegTime())){//开始时间
			temp.setBegTime(PramasUtil.DateFormat(req.getBegTime()));
		}if(StringUtil.isNotEmpty(req.getEndTime())){//结束时间
			temp.setEndTime(PramasUtil.DateFormat(req.getEndTime()));
		}if(StringUtil.isNotEmpty(req.getMemberName())){//用户会员名
			temp.setMemberName(req.getMemberName());
		}if(StringUtil.isNotEmpty(req.getCreateSource())){//订单创建来源
			temp.setCreateSource(req.getCreateSource());
		}if(StringUtil.isNotEmpty(req.getOrderId())){//订单编号
			temp.setOrderId(req.getOrderId());
		}if(StringUtil.isNotEmpty(req.getOrderStatus())){//订单状态
			temp.setOrderStatus(req.getOrderStatus());
		}if(StringUtil.isNotEmpty(req.getOrderType())){//订单编号
			temp.setOrderType(req.getOrderType());
		}if(StringUtil.isNotEmpty(req.getPaymentMethod())){//支付方式
			temp.setPaymentMethod(req.getPaymentMethod());
		}
		//执行查询
		BossPrefPayOrderListRes bossPrefPayOrderListRes=bossOrderQueryService.queryPrefPayOrderList(temp, pageVo);
		//返回惠付订单查询列表
		if(Constants.RESULT_SUCCESS.equals(bossPrefPayOrderListRes.getResultVo().getResultCode())){
			List<DiscontPayListRes> list=new ArrayList<DiscontPayListRes>();
			if(bossPrefPayOrderListRes !=null &&bossPrefPayOrderListRes.getInfoVos().size()>0){
				for (BossPrefPayOrderListInfoVo vo : bossPrefPayOrderListRes.getInfoVos()) {
					DiscontPayListRes result=new DiscontPayListRes();
					BeanUtils.copyProperties(result, vo);
					if(vo.getCreateTime()==0){
						result.setCreateTime("-");
					}else{
						result.setCreateTime(DateUtil.longToString(vo.getCreateTime()));
					}
					if(vo.getPaymentTime()==0){
						result.setPaymentTime("-");
					}else{
						result.setPaymentTime(DateUtil.longToString(vo.getPaymentTime()));
					}
					list.add(result);
				}
			}
			Page page = new Page();
			BeanUtils.copyProperties(page, bossPrefPayOrderListRes.getPageVo());
			map.put("list", list);
			map.put("page", page);
		}else{
			throw new BossException(bossPrefPayOrderListRes.getResultVo().getResultCode(), bossPrefPayOrderListRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	
	/**
	 * 
	 * <p>Title:惠付订单列表导出 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月29日 下午3:27:35 
	 * @param req
	 * @return
	 * @throws ParseException 
	 * @throws TException 
	 * @throws BossException 
	 */
	public HashMap<String,Object> exportDiscountPayOrderList(DiscontPayListVoReq req) throws ParseException, TException, BossException{
		HashMap<String,Object> map=new HashMap<String,Object>();
		//得到thrift请求vo
				BossPrefPayOrderListReq temp=new BossPrefPayOrderListReq();
				//判断请求条件，当条件不为空的时候就给thrift请求vo设置对应的值
				if(StringUtil.isNotEmpty(req.getClientId())){//客户端
					temp.setClientId(req.getClientId());
				}if(StringUtil.isNotEmpty(req.getBegTime())){//开始时间
					temp.setBegTime(PramasUtil.DateFormat(req.getBegTime()));
				}if(StringUtil.isNotEmpty(req.getEndTime())){//结束时间
					temp.setEndTime(PramasUtil.DateFormat(req.getEndTime()));
				}if(StringUtil.isNotEmpty(req.getMemberName())){//用户会员名
					temp.setMemberName(req.getMemberName());
				}if(StringUtil.isNotEmpty(req.getCreateSource())){//订单创建来源
					temp.setCreateSource(req.getCreateSource());
				}if(StringUtil.isNotEmpty(req.getOrderId())){//订单编号
					temp.setOrderId(req.getOrderId());
				}if(StringUtil.isNotEmpty(req.getOrderStatus())){//订单状态
					temp.setOrderStatus(req.getOrderStatus());
				}if(StringUtil.isNotEmpty(req.getOrderType())){//订单编号
					temp.setOrderType(req.getOrderType());
				}if(StringUtil.isNotEmpty(req.getPaymentMethod())){//支付方式
					temp.setPaymentMethod(req.getPaymentMethod());
				}
				//执行导出查询
				ExportResultRes res=bossOrderQueryService.exportPrefPayOrderList(temp);
				ResultVo result = res.getResultVo();
				if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
					map.put("url", res.getUrl());
					map.put("code", result.getResultCode());
					map.put("message", result.getResultDesc());
				} else {
					throw new BossException(result.getResultCode(), result.getResultDesc());
				}
				
				return map;
		
	}
	
	/**
	 * 
	 * <p>Title:惠付订单详情查询 </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年12月29日 下午4:41:44 
	 * @param clientId
	 * @param orderId
	 * @return
	 * @throws TException 
	 * @throws BossException 
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public HashMap<String,Object> detail(String clientId, String orderId) throws TException, BossException{
		HashMap map= new HashMap<String,Object>();
		BossPrefPayOrderDetailRes res=bossOrderQueryService.getPrefPayOrderDetail(clientId, orderId);
		ResultVo result = res.getResultVo();
		if(Constants.RESULT_SUCCESS.equals(result.getResultCode())) {
			DiscountPayDetailRes detail=new DiscountPayDetailRes();
			if(res.getResultVo()!=null){
				BeanUtils.copyProperties(detail, res.getInfoVo());
				if(res.getInfoVo().getCreateTime()==0){
					detail.setCreateTime("-");
				}else{
					detail.setCreateTime(DateUtil.longToString(res.getInfoVo().getCreateTime()));
				}
				if(res.getInfoVo().getPaymentTime()==0){
					detail.setPaymentTime("-");
				}else{
					detail.setPaymentTime(DateUtil.longToString(res.getInfoVo().getPaymentTime()));
				}
				if(res.getInfoVo().getSettleTime()==0){
					detail.setSettleTime("-");
				}else{
					detail.setSettleTime(DateUtil.longToString(res.getInfoVo().getSettleTime()));
				}
			}
			map.put("detail", detail);
			map.put("code", result.getResultCode());
			map.put("message", result.getResultDesc());
		} else {
			throw new BossException(result.getResultCode(), result.getResultDesc());
		}
		return map;
	}
	
}
