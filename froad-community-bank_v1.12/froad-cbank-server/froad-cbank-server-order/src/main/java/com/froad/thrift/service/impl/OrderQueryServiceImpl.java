package com.froad.thrift.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.thrift.TException;
import org.eclipse.jetty.io.NetworkTrafficListener.Empty;

import com.alibaba.druid.util.StringUtils;
import com.alibaba.fastjson.JSON;
import com.froad.common.beans.ResultBean;
import com.froad.enums.DeliveryType;
import com.froad.enums.OperType;
import com.froad.enums.OrderRequestType;
import com.froad.enums.OrderStatus;
import com.froad.enums.OrderType;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.enums.SettlementType;
import com.froad.enums.ShippingStatus;
import com.froad.enums.SubOrderType;
import com.froad.enums.TicketStatus;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.OrderQueryLogic;
import com.froad.logic.impl.OrderQueryLogicImpl;
import com.froad.logic.impl.order.OrderLogger;
import com.froad.po.OrderQueryByBankCondition;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.po.OrderQueryByMerchantPhoneCondition;
import com.froad.po.OrderQueryMerchantManageCondition;
import com.froad.po.QueryBoutiqueOrderByBankDto;
import com.froad.po.order.OrderExportData;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVo;
import com.froad.support.OrderSupport;
import com.froad.support.SettlementSupport;
import com.froad.support.TicketSupport;
import com.froad.support.impl.OrderSupportImpl;
import com.froad.support.impl.SettlementSupportImpl;
import com.froad.support.impl.TicketSupportImpl;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.OrderQueryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.GetOrderDetailByBankManageVoReq;
import com.froad.thrift.vo.order.GetOrderDetailByBankManageVoRes;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantManageVoReq;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantManageVoRes;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantPhoneVoReq;
import com.froad.thrift.vo.order.GetOrderDetailByMerchantPhoneVoRes;
import com.froad.thrift.vo.order.OrderDetailRes;
import com.froad.thrift.vo.order.OrderListByBossReq;
import com.froad.thrift.vo.order.OrderListByBossRes;
import com.froad.thrift.vo.order.OrderVo;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryBoutiqueOrderByBankManageVoRes;
import com.froad.thrift.vo.order.ProductInfoVo;
import com.froad.thrift.vo.order.ProductVo;
import com.froad.thrift.vo.order.QueryGivePointsProductByBossReq;
import com.froad.thrift.vo.order.QueryGivePointsProductByBossRes;
import com.froad.thrift.vo.order.QueryMerchantSettlementReq;
import com.froad.thrift.vo.order.QueryMerchantSettlementRes;
import com.froad.thrift.vo.order.QueryOrderByBankManageVo;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByBankManageVoRes;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVo;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVoReq;
import com.froad.thrift.vo.order.QueryOrderByMerchantManageVoRes;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVo;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVoReq;
import com.froad.thrift.vo.order.QueryOrderByMerchantPhoneVoRes;
import com.froad.thrift.vo.order.QueryRecvInfoForProductByBossReq;
import com.froad.thrift.vo.order.QueryRecvInfoForProductByBossRes;
import com.froad.thrift.vo.order.SubOrderByBossReq;
import com.froad.thrift.vo.order.SubOrderByBossRes;
import com.froad.thrift.vo.order.SubOrderVo;
import com.froad.util.Arith;
import com.froad.util.BeanUtil;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.order.HelpUtil;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.QueryOperators;

/**
 * 订单查询相关接口
 * 
 * @author Arron
 * 
 */
public class OrderQueryServiceImpl extends BizMonitorBaseService implements OrderQueryService.Iface {
	
	public static int querySize = 20000;

	public OrderQueryServiceImpl() {}
	
	public OrderQueryServiceImpl(String name, String version) {
		super(name, version);
	}
    
    private OrderQueryLogic orderQueryLogic = new OrderQueryLogicImpl();
    
    /**
	 * 检查字段是否为空，为空时抛出异常信息
	 * @param field 字段
	 * @param errorMsg 错误信息
	 * @throws FroadBusinessException 自定义业务异常信息
	 */
	public void dataEmptyChecker(Object field, String errorMsg) throws FroadBusinessException {
		//1.请求数据校验
		if(EmptyChecker.isEmpty(field)){
			LogCvt.error(errorMsg);
			throw new FroadBusinessException(ResultCode.failed.getCode(), errorMsg);
		}
	}

    @SuppressWarnings("unchecked")
    @Override
    public QueryOrderByMerchantPhoneVoRes queryOrderByMerchantPhone(QueryOrderByMerchantPhoneVoReq req) throws TException {
        //商户H5对团购和面对面订单查询
        LogCvt.info("商户H5订单查询-团购or面对面, Req:" + JSonUtil.toJSonString(req));
        long ct = System.currentTimeMillis();
        QueryOrderByMerchantPhoneVoRes res = new QueryOrderByMerchantPhoneVoRes();
        ResultVo resultVo = new ResultVo();
        resultVo.setResultCode(ResultCode.success.getCode());
        resultVo.setResultDesc("查询结果成功");

        List<QueryOrderByMerchantPhoneVo> orderVo = new ArrayList<QueryOrderByMerchantPhoneVo>();

        ResultBean rb = new ResultBean(ResultCode.success);

        try {
            OrderQueryByMerchantPhoneCondition condition = new OrderQueryByMerchantPhoneCondition();
            // 根据参数查找面对面订单
            // 设置 clientId， merchantId, outletId查询条件
            condition.setClientId(req.getClientId());
            condition.setMerchantId(req.getMerchantId());
            if(EmptyChecker.isNotEmpty(req.getOutletId()) && !StringUtils.equals(req.getOutletId().trim(), "0")){
            	condition.setOutletId(req.getOutletId());
            }
            condition.setType(req.getType());
            condition.setDeliveryStatus(req.getDeliveryStatus());
            condition.setStatus(req.getStatus());

            // 判断交易类型， 团购or面对面
            if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                // 面对面交易
                rb = orderQueryLogic.queryOrderByFacetface(condition, req.getPage());
                
                //统计今日收入和订单数
                Date curDate = new Date();
    			long dayBegin = getDayBegin(curDate);
    			OrderSupport orderSupport = new OrderSupportImpl();
    			QueryMerchantSettlementReq request = new QueryMerchantSettlementReq();
    			request.setClientId(req.getClientId());
    			request.setMerchantId(req.getMerchantId());
    			if(EmptyChecker.isNotEmpty(req.getOutletId()) && !StringUtils.equals(req.getOutletId().trim(), "0")){
    				request.setOutletId(req.getOutletId());
    			}
    			// 今日面对面 - cb_order
    			Iterator<DBObject> iterator = getF2fOrderPineline(orderSupport, request, dayBegin, curDate.getTime());
    			OrderLogger.info("商户H5订单查询", "收银记录", "当日订单数和金额查询统计结果："+JSON.toJSONString(iterator), null);
    			if (iterator.hasNext()){
    				DBObject tmpObj = iterator.next();
    				res.setTotalIncome(0);
    				
    				double cutMoney = 0;
    				if (tmpObj.containsField("cut_money")){
    					cutMoney = Arith.div(Double.parseDouble(tmpObj.get("cut_money").toString()), 1000);
    					LogCvt.info(new StringBuffer("今日面对面和惠付总满减金额：").append(cutMoney).toString());
    				}
    				
    				if (tmpObj.containsField("total_price")){
    					res.setTotalIncome(Arith.add(cutMoney, Arith.div(Double.parseDouble(tmpObj.get("total_price").toString()), 1000)));
    					LogCvt.info(new StringBuffer("今日面对面和惠付总收入：").append(res.getTotalIncome()).toString());
    				}
    				res.setOrderCount(0);
    				if (tmpObj.containsField("total_count")){
    					res.setOrderCount(Integer.valueOf(tmpObj.get("total_count").toString()));
    					LogCvt.info(new StringBuffer("今日面对面和惠付订单总笔数：").append(res.getOrderCount()).toString());
    				}
    			}
                
            } else if (StringUtils.equals(OrderType.group_merchant.getCode(), req.getType())
                    || StringUtils.equals(OrderType.special_merchant.getCode(), req.getType())) {
                // 团购和名优特惠
                rb = orderQueryLogic.queryOrderByGroup(condition, req.getPage());
            } else {
                LogCvt.error("查询类型不明确，目前只支持:3.名优特惠 4.团购，5.面对面，传入类型为：" + req.getType());
                throw new FroadBusinessException(ResultCode.member_order_type_unknow.getCode(), ResultCode.member_order_type_unknow.getMsg());
            }
            orderVo = (List<QueryOrderByMerchantPhoneVo>) rb.getData();
        } catch (FroadBusinessException e) {
            LogCvt.error("错误信息：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMessage());
        } catch (Exception e) {
            LogCvt.error("未知异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        
        if(orderVo != null && !orderVo.isEmpty()) {
            long firstTime = orderVo.get(0).getCreateTime();
            long lastTime = orderVo.get(orderVo.size()-1).getCreateTime();
            convertFirstAndEndTime(req.getPage(), firstTime, lastTime);
        }
        
        res.setPage(req.getPage());
        res.setOrderVo(orderVo);
        res.setResultVo(resultVo);
        LogCvt.info("商户H5订单查询-团购or面对面-处理耗时（" + (System.currentTimeMillis() - ct) + "毫秒）结果： " + JSonUtil.toJSonString(res));
        return res;

    }

    
    @Override
    public GetOrderDetailByMerchantPhoneVoRes getOrderDetailByMerchantPhone(GetOrderDetailByMerchantPhoneVoReq req) throws TException {
        //商户H5对团购和面对面订单的详细内容查询
        LogCvt.info("商户H5订单详情-团购or面对面， 请求参数：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();

        GetOrderDetailByMerchantPhoneVoRes res = new GetOrderDetailByMerchantPhoneVoRes();
        ResultVo resultVo = new ResultVo();
        ResultBean rb = new ResultBean(ResultCode.success);
        
        try {
            if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                // 面对面订单详细
                rb = orderQueryLogic.getFacetfaceOrderDetailByOrderId(req.getClientId(), req.getSubOrderId());
            } else if (StringUtils.equals(OrderType.group_merchant.getCode(), req.getType())
                    || StringUtils.equals(OrderType.special_merchant.getCode(), req.getType())) {
                // 名优特惠和团购
                rb = orderQueryLogic.getSubOrderDetailByOrderIdAndType(req.getClientId(), req.getSubOrderId());
            } else {
                ResultCode rc = ResultCode.member_order_type_unknow;
                throw new FroadBusinessException(rc.getCode(), rc.getMsg());
            }
            resultVo.setResultCode(rb.getCode());
            resultVo.setResultDesc(rb.getMsg());
            if (EmptyChecker.isNotEmpty(rb.getData())) {
                res = (GetOrderDetailByMerchantPhoneVoRes) rb.getData();
            }
        } catch (FroadBusinessException e) {
            LogCvt.error("错误码：" + e.getCode() + "， 错误信息" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("未知异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }

        res.setResultVo(resultVo);
        LogCvt.info("订单详细处理结束，耗时（" + (System.currentTimeMillis() - st) + "）毫秒， 响应内容：" + JSonUtil.toJSonString(res));
        return res;
    }

    @Override
    public QueryOrderByMerchantManageVoRes queryOrderByMerchantManage(QueryOrderByMerchantManageVoReq req) throws TException {
        //商户管理端查询
        LogCvt.info("[商户管理平台]-订单列表查询-开始，请求参数：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();

        ResultVo resultVo = new ResultVo();
        QueryOrderByMerchantManageVoRes res = new QueryOrderByMerchantManageVoRes();

        if(req.getStartTime() <= 0) {
            req.setStartTime(getDefaultStartTime());
        }
        if(req.getEndTime() <= 0) {
            req.setEndTime(getDefaultEndTime());
        }
        
        OrderQueryMerchantManageCondition conditon = (OrderQueryMerchantManageCondition) BeanUtil.copyProperties(OrderQueryMerchantManageCondition.class, req);
        
        try {
            ResultBean rb = null;

            if(EmptyChecker.isEmpty(conditon.getMerchantId())) {
                throw new FroadBusinessException(ResultCode.notAllParameters.getCode(), "商户号不能为空");
            }
            
            if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                // 面对面交易
                rb = orderQueryLogic.queryFacetfaceOrderByMerchantManage(conditon, req.getPage());
            } else {
                // 其他类型---(名优特惠，团购)
                rb = orderQueryLogic.querySubOrderByMerchantManage(conditon, req.getPage());
            }

            if (EmptyChecker.isNotEmpty(rb)) {
                List<QueryOrderByMerchantManageVo> list = (List<QueryOrderByMerchantManageVo>) rb.getData();
                if(list != null && !list.isEmpty()) {
                    long firstTime = list.get(0).getCreateTime();
                    long lastTime = list.get(list.size()-1).getCreateTime();
                    convertFirstAndEndTime(req.getPage(), firstTime, lastTime);
                }
                res.setOrderVo(list);
            }
            
            resultVo.setResultCode(rb.getCode());
            resultVo.setResultDesc(rb.getMsg());
        } catch (FroadBusinessException e) {
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
            LogCvt.error("错误码：" + e.getCode() + ", 错误内容：" + e.getMsg());
        } catch (Exception e) {
            LogCvt.error("未知异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        if (StringUtils.equals(OperType.QUERY.getCode(), req.getOperType())) {
            res.setPageVo(req.getPage());
        }
        res.setResultVo(resultVo);

        LogCvt.info("[商户管理平台]-订单列表查询-结束！总耗时（" + (System.currentTimeMillis() - st) + "）毫秒， 响应内容：<操作结果：" + JSonUtil.toJSonString(res.getResultVo())+" | 查询的总条数：" + res.getOrderVoSize()+ " | 分页结果："+JSonUtil.toJSonString(res.getPageVo())+">");
        return res;
    }
    
    @Override
	public ExportResultRes exportOrderByMerchantManage(QueryOrderByMerchantManageVoReq req) throws TException {
    	//商户管理端查询
        LogCvt.info("[商户管理平台]-订单导出-开始，请求参数：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();

        ExportResultRes res = new ExportResultRes();
        ResultVo resultVo = new ResultVo();
        
        if(req.getStartTime() <= 0) {
            req.setStartTime(getDefaultStartTime());
        }
        if(req.getEndTime() <= 0) {
            req.setEndTime(getDefaultEndTime());
        }
        
        OrderQueryMerchantManageCondition conditon = (OrderQueryMerchantManageCondition) BeanUtil.copyProperties(OrderQueryMerchantManageCondition.class, req);
        
        //导出URL
        String url = "";
        
        try {
            ResultBean rb = null;
            
			PageVo pageVo = new PageVo();
			pageVo.setPageNumber(1);
			pageVo.setPageSize(querySize);
			pageVo.setLastPageNumber(0);
			pageVo.setFirstRecordTime(0);
			pageVo.setLastRecordTime(0);
			req.setPage(pageVo);
			
			dataEmptyChecker(conditon.getMerchantId(), "参数错误，商户号不能为空");

            if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                // 面对面交易
                rb = orderQueryLogic.queryQrOrderByMerchantForExport(conditon, pageVo);
            } else {
                // 其他类型---(名优特惠，团购)
                rb = orderQueryLogic.querySubOrderByMerchantForExport(conditon, pageVo);
            }
            
            int pageCount = pageVo.getPageCount();

            //导出数据
            if (EmptyChecker.isNotEmpty(rb)) {
                List<QueryOrderByMerchantManageVo> list = (List<QueryOrderByMerchantManageVo>) rb.getData();
				if (EmptyChecker.isNotEmpty(list)) {
					long firstTime = list.get(0).getCreateTime();
					long lastTime = list.get(list.size() - 1).getCreateTime();
					convertFirstAndEndTime(pageVo, firstTime, lastTime);
				}
                OrderExportData orderExportData = orderQueryLogic.exportOrderByMerchantManage(list,req);
           	 	ExcelWriter excelWriter = new ExcelWriter(querySize);
           	 	//导出第一页
     			excelWriter.write(orderExportData.getHeader(), orderExportData.getData(), orderExportData.getSheetName(), orderExportData.getExcelFileName());
				if (pageCount > 1) {
					for (int i = 2; i <= pageCount; i++) {
						// 设置页数
						pageVo.setPageNumber(i);
                        pageVo.setLastPageNumber(i-1);
						// 查询数据
						ResultBean resultTemp = null;
						if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
			                // 面对面交易
							resultTemp = orderQueryLogic.queryQrOrderByMerchantForExport(conditon, pageVo);
			            } else {
			                // 其他类型---(名优特惠，团购)
			            	resultTemp = orderQueryLogic.querySubOrderByMerchantForExport(conditon, pageVo);
			            }
						// 转换成报表数据
						List<QueryOrderByMerchantManageVo> listTemp = (List<QueryOrderByMerchantManageVo>) resultTemp.getData();
						if (EmptyChecker.isNotEmpty(listTemp)) {
							long firstTime = listTemp.get(0).getCreateTime();
							long lastTime = listTemp.get(listTemp.size() - 1).getCreateTime();
							convertFirstAndEndTime(pageVo, firstTime, lastTime);
						}
						OrderExportData orderExportDataTemp = orderQueryLogic.exportOrderByMerchantManage(listTemp, req);
						// 导出
						excelWriter.write(orderExportDataTemp.getHeader(),orderExportDataTemp.getData(),orderExportDataTemp.getSheetName(),orderExportDataTemp.getExcelFileName());
					}
				}
				url = excelWriter.getUrl();
			}
            
            resultVo.setResultCode(rb.getCode());
            resultVo.setResultDesc(rb.getMsg());
        } catch (FroadBusinessException e) {
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
            LogCvt.error("错误码：" + e.getCode() + ", 错误内容：" + e.getMsg(),e);
        } catch (Exception e) {
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
            LogCvt.error("系统异常", e);
        }
        res.setResultVo(resultVo);
        res.setUrl(url);
        LogCvt.info("[商户管理平台]-订单导出-结束！总耗时（" + (System.currentTimeMillis() - st) + "）ms， excel下载链接：" + url);
        return res;
	}
    
    @Override
    public GetOrderDetailByMerchantManageVoRes getOrderDetailByMerchantManage(GetOrderDetailByMerchantManageVoReq req) throws TException {
        //商户管理段详细---产品原型未出， 先不做
        LogCvt.info("[商户管理平台]-订单详情查询-开始，请求参数：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();
        
        GetOrderDetailByMerchantManageVoRes res = new GetOrderDetailByMerchantManageVoRes();

        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "查询订单成功");
        try {
        	dataEmptyChecker(req.getClientId(), "参数错误，clientId不能为空");
        	dataEmptyChecker(req.getSubOrderId(), "参数错误，subOrderId不能为空");
        	
            ResultBean rb = new ResultBean(ResultCode.success);
            if (OrderType.face2face.getCode().equals(req.getType())) {
                LogCvt.info("查询面对面订单， 订单号：" + req.getSubOrderId());
                rb = orderQueryLogic.getOrderDetailByMerchantManage(req.getClientId(), req.getSubOrderId());
            } else {
                LogCvt.info("查询（团购，名优特惠）订单详细类型：" + req.getType() + ", 子订单号：" + req.getSubOrderId());
                rb = orderQueryLogic.getSubOrderDetailByMerchantManage(req.getClientId(), req.getSubOrderId(), req.getType());
            }
            
            resultVo.setResultCode(rb.getCode());
            resultVo.setResultDesc(rb.getMsg());
            res = (GetOrderDetailByMerchantManageVoRes) rb.getData();
            
        } catch (FroadBusinessException e) {
            LogCvt.error("查询错误码：" + e.getCode() + "，错误信息：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("未知异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        res.setResultVo(resultVo);
        LogCvt.info("[商户管理平台]-订单详情查询-结束！耗时（" + (System.currentTimeMillis() - st) + "）毫秒， 响应内容：" + JSonUtil.toJSonString(res));
        return res;
    }
    
    @Override
    public GetOrderDetailByBankManageVoRes getOrderDetailByMerchantManageNew(GetOrderDetailByMerchantManageVoReq req) throws TException {
        //商户管理段详细---产品原型未出， 先不做
        LogCvt.info("[商户管理平台]-订单详情查询-开始，请求参数：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();

        GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();
        ResultVo resultVo = new ResultVo();
        ResultBean rb = new ResultBean(ResultCode.success);
        try {
            if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                rb = orderQueryLogic.getFacetfaceOrderDetailOfBankByOrderId(req.getClientId(), req.getSubOrderId());
            } else {
                rb = orderQueryLogic.getSubOrderOfBankByOrderIdAndType(req.getClientId(), req.getSubOrderId(), req.getType(),req.getDeliveryOption());
            }
            resultVo.setResultCode(rb.getCode());
            resultVo.setResultDesc(rb.getMsg());
            if (EmptyChecker.isNotEmpty(rb.getData())) {
                res = (GetOrderDetailByBankManageVoRes) rb.getData();
            }
        } catch (FroadBusinessException e) {
            LogCvt.error("错误码：" + e.getCode() + "， 错误信息：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("未知异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        res.setResultVo(resultVo);
        LogCvt.info("[商户管理平台]-订单详情查询-结束！耗时（" + (System.currentTimeMillis() - st) + "）毫秒， 响应内容：" + JSonUtil.toJSonString(res));
        return res;
    }
    
    
	@Override
	public ExportResultRes exportBoutiqueOrderByBankManage(QueryBoutiqueOrderByBankManageVoReq req) throws TException {
		LogCvt.info("[银行管理平台]-精品商城订单导出-开始...请求参数：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();
        ExportResultRes res = new ExportResultRes();
        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "导出成功");
        
        //请求数据处理
        if(req.getStartTime() <= 0) {
            req.setStartTime(getDefaultStartTime());
        }
        if(req.getEndTime() <= 0) {
            req.setEndTime(getDefaultEndTime());
        }
        
        QueryBoutiqueOrderByBankDto reqDto = (QueryBoutiqueOrderByBankDto) BeanUtil.copyProperties(QueryBoutiqueOrderByBankDto.class, req);
        PageVo pageVo = req.getPage();
        //导出链接
        String url = "";
        try {
            //设置分页
            if (EmptyChecker.isEmpty(req.getPage())) {
                pageVo = new PageVo();
                pageVo.setPageSize(querySize);
                pageVo.setPageNumber(1);
                pageVo.setLastPageNumber(0);
                pageVo.setFirstRecordTime(0);
                pageVo.setLastRecordTime(0);
                req.setPage(pageVo);
            }
            //查询数据
            ResultBean rb = orderQueryLogic.queryBoutiqueSubOrderByBankForExport(reqDto, pageVo);
            int pageCount = pageVo.getPageCount();
            //导出数据
            if (EmptyChecker.isNotEmpty(rb)) {
                List<QueryBoutiqueOrderByBankManageVo> list = (List<QueryBoutiqueOrderByBankManageVo>) rb.getData();
                if(EmptyChecker.isNotEmpty(list)){
                    long firstTime = list.get(0).getCreateTime();
                    long lastTime = list.get(list.size()-1).getCreateTime();
                    convertFirstAndEndTime(pageVo, firstTime, lastTime);
                }
                OrderExportData orderExportData = orderQueryLogic.exportBoutiqueOrderByBankManage(list,req);
                ExcelWriter excelWriter = new ExcelWriter(querySize);
                //导出第一页
                excelWriter.write(orderExportData.getHeader(), orderExportData.getData(), orderExportData.getSheetName(), orderExportData.getExcelFileName());
                if(pageCount > 1){
                    for(int i = 2; i<= pageCount;i++){
                        //设置页数
                        pageVo.setPageNumber(i);
                        pageVo.setLastPageNumber(i-1);
                        //查询数据
                        ResultBean resultTemp = orderQueryLogic.queryBoutiqueSubOrderByBankForExport(reqDto, pageVo);
                        //转换成报表数据
                        List<QueryBoutiqueOrderByBankManageVo> listTemp = (List<QueryBoutiqueOrderByBankManageVo>) resultTemp.getData();
                        if(EmptyChecker.isNotEmpty(listTemp)){
                            long firstTimeTemp = listTemp.get(0).getCreateTime();
                            long lastTimeTemp = listTemp.get(listTemp.size()-1).getCreateTime();
                            convertFirstAndEndTime(pageVo, firstTimeTemp, lastTimeTemp);
                        }
                        
                        OrderExportData orderExportDataTemp = orderQueryLogic.exportBoutiqueOrderByBankManage(listTemp,req);
                        //导出
                        excelWriter.write(orderExportDataTemp.getHeader(), orderExportDataTemp.getData(), orderExportDataTemp.getSheetName(), orderExportDataTemp.getExcelFileName());
                    }
                }
                url = excelWriter.getUrl();
            }
        } catch (FroadBusinessException e) {
            LogCvt.error("银行管理平台-精品商城订单导出，业务异常：",e);
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("银行管理平台-精品商城订单导出，系统异常：", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        res.setResultVo(resultVo);
        res.setUrl(url);
        LogCvt.info("[银行管理平台]-精品商城订单导出-结束，耗时" + (System.currentTimeMillis() - st) + "ms，excel下载链接："+url);
        return res;
        
	}

	@Override
	public QueryBoutiqueOrderByBankManageVoRes queryBoutiqueOrderByBankManage(QueryBoutiqueOrderByBankManageVoReq req)throws TException {
		LogCvt.info("[银行管理平台]-[订单管理]-精品商城列表查询接口-开始...请求参数：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();
        QueryBoutiqueOrderByBankManageVoRes res = new QueryBoutiqueOrderByBankManageVoRes();
        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "查询成功");
        if(req.getStartTime() <= 0) {
            req.setStartTime(getDefaultStartTime());
        }
        if(req.getEndTime() <= 0) {
            req.setEndTime(getDefaultEndTime());
        }
        QueryBoutiqueOrderByBankDto reqDto = (QueryBoutiqueOrderByBankDto) BeanUtil.copyProperties(QueryBoutiqueOrderByBankDto.class, req);
        PageVo pageVo = req.getPage();
        try {
            if (EmptyChecker.isEmpty(req.getPage())) {
                pageVo = new PageVo();
            }
            ResultBean rb = orderQueryLogic.queryBoutiqueSubOrderByBank(reqDto, pageVo);
            if (EmptyChecker.isNotEmpty(rb)) {
                List<QueryBoutiqueOrderByBankManageVo> list = (List<QueryBoutiqueOrderByBankManageVo>) rb.getData();
                if(list != null && !list.isEmpty()) {
                    long firstTime = list.get(0).getCreateTime();
                    long lastTime = list.get(list.size()-1).getCreateTime();
                    convertFirstAndEndTime(req.getPage(), firstTime, lastTime);
                }
                res.setOrdervo(list);
            }
        } catch (FroadBusinessException e) {
            LogCvt.error("银行管理查询，业务异常：" + e.getMsg(),e);
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("银行管理查询，系统异常：", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        res.setPageVo(pageVo);
        res.setResultVo(resultVo);
        LogCvt.info("[银行管理平台]-[订单管理]-精品商城列表查询接口-结束，耗时（" + (System.currentTimeMillis() - st) + "）毫秒， 响应内容：<操作结果：" + JSonUtil.toJSonString(res.getResultVo())+" | 查询的总条数：" + res.getOrdervoSize()+ " | 分页结果："+JSonUtil.toJSonString(res.getPageVo())+">");
        return res;
	}
    
    
    
    
    @Override
    public QueryOrderByBankManageVoRes queryOrderByBankManage(QueryOrderByBankManageVoReq req) throws TException {
        LogCvt.info("[银行管理平台]-[订单管理]-列表查询接口-开始...请求参数：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();
        QueryOrderByBankManageVoRes res = new QueryOrderByBankManageVoRes();
        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "查询成功");
        
        if(req.getStartTime() <= 0) {
            req.setStartTime(getDefaultStartTime());
        }
        if(req.getEndTime() <= 0) {
            req.setEndTime(getDefaultEndTime());
        }
        
        OrderQueryByBankCondition bankCondition = (OrderQueryByBankCondition) BeanUtil.copyProperties(OrderQueryByBankCondition.class, req);

        PageVo pageVo = req.getPage();
        try {

            if (EmptyChecker.isEmpty(req.getPage())) {
                pageVo = new PageVo();
            }

            ResultBean rb = null;
            if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                // 面对面
                rb = orderQueryLogic.queryOrderOfFacetfacByBank(bankCondition, pageVo);
            } else {
                // 预售，团购
                rb = orderQueryLogic.querySubOrderByBank(bankCondition, pageVo);
            }

            if (EmptyChecker.isNotEmpty(rb)) {
                List<QueryOrderByBankManageVo> list = (List<QueryOrderByBankManageVo>) rb.getData();
                
                if(list != null && !list.isEmpty()) {
                    long firstTime = list.get(0).getCreateTime();
                    long lastTime = list.get(list.size()-1).getCreateTime();
                    convertFirstAndEndTime(req.getPage(), firstTime, lastTime);
                }
                
                res.setOrdervo(list);
            }
        } catch (FroadBusinessException e) {
            LogCvt.error("银行管理查询，业务异常：" + e.getMsg(),e);
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("银行管理查询，系统异常：", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        res.setPageVo(pageVo);
        res.setResultVo(resultVo);
        LogCvt.info("[银行管理平台]-[订单管理]-列表查询接口-结束，耗时（" + (System.currentTimeMillis() - st) + "）毫秒， 响应内容：<操作结果：" + JSonUtil.toJSonString(res.getResultVo())+" | 查询的总条数：" + res.getOrdervoSize()+ " | 分页结果："+JSonUtil.toJSonString(res.getPageVo())+">");
        return res;
    }
    
    @Override
	public ExportResultRes exportOrderByBankManage(QueryOrderByBankManageVoReq req) throws TException {
    	 LogCvt.info("[银行管理平台]-订单导出-开始...请求参数：" + JSonUtil.toJSonString(req));
         long st = System.currentTimeMillis();
         ExportResultRes res = new ExportResultRes();
         ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "导出成功");
         
         //参数校验
         try {
			dataEmptyChecker(req.getType(), "参数错误，业务类型不能为空");
			
			if (req.getType().equals(OrderType.presell_org.getCode())) {// 预售
				dataEmptyChecker(req.getDeliveryOption(), "参数错误，配送方式不能为空");
	 			if (!StringUtils.equals(req.getDeliveryOption(),DeliveryType.home.getCode()) && !StringUtils.equals(req.getDeliveryOption(),DeliveryType.take.getCode())) {
	 				LogCvt.error("银行管理平台-预售订单导出，参数错误：配送方式deliveryOption="+req.getDeliveryOption());
	 				throw new FroadBusinessException(ResultCode.failed.getCode(),"参数错误：配送方式不能为空");
	 			}
	 		}
			
		 } catch (FroadBusinessException e) {
			LogCvt.error("请求参数错误：",e);
			resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
            res.setResultVo(resultVo);
            return res;
		 }
         
         //请求数据处理
         if(req.getStartTime() <= 0) {
             req.setStartTime(getDefaultStartTime());
         }
         if(req.getEndTime() <= 0) {
             req.setEndTime(getDefaultEndTime());
         }
         
         OrderQueryByBankCondition bankCondition = (OrderQueryByBankCondition) BeanUtil.copyProperties(OrderQueryByBankCondition.class, req);

         PageVo pageVo = req.getPage();
         
         //导出链接
         String url = "";
         
         try {
        	 //设置分页
             if (EmptyChecker.isEmpty(req.getPage())) {
                 pageVo = new PageVo();
                 pageVo.setPageSize(querySize);
                 pageVo.setPageNumber(1);
                 pageVo.setLastPageNumber(0);
                 pageVo.setFirstRecordTime(0);
                 pageVo.setLastRecordTime(0);
     			 req.setPage(pageVo);
             }
             
             //查询数据
             ResultBean rb = null;
             if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                 // 面对面
                 rb = orderQueryLogic.queryQrOrderByBankForExport(bankCondition, pageVo);
             } else {
                 // 预售，团购
                 rb = orderQueryLogic.querySubOrderByBankForExport(bankCondition, pageVo);
             }
             int pageCount = pageVo.getPageCount();
             //导出数据
             if (EmptyChecker.isNotEmpty(rb)) {
            	 List<QueryOrderByBankManageVo> list = (List<QueryOrderByBankManageVo>) rb.getData();
            	 if(EmptyChecker.isNotEmpty(list)){
            		 long firstTime = list.get(0).getCreateTime();
                     long lastTime = list.get(list.size()-1).getCreateTime();
                     convertFirstAndEndTime(pageVo, firstTime, lastTime);
            	 }
            	 OrderExportData orderExportData = orderQueryLogic.exportOrderByBankManage(list,req);
            	 ExcelWriter excelWriter = new ExcelWriter(querySize);
            	 //导出第一页
      			 excelWriter.write(orderExportData.getHeader(), orderExportData.getData(), orderExportData.getSheetName(), orderExportData.getExcelFileName());
            	 if(pageCount > 1){
            		 for(int i = 2; i<= pageCount;i++){
            			 //设置页数
                         pageVo.setPageNumber(i);
                         pageVo.setLastPageNumber(i-1);
                         //查询数据
                         ResultBean resultTemp = null;
                         if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                             // 面对面
                        	 resultTemp = orderQueryLogic.queryQrOrderByBankForExport(bankCondition, pageVo);
                         } else {
                             // 预售，团购
                        	 resultTemp = orderQueryLogic.querySubOrderByBankForExport(bankCondition, pageVo);
                         }
                         //转换成报表数据
                         List<QueryOrderByBankManageVo> listTemp = (List<QueryOrderByBankManageVo>) resultTemp.getData();
                         if(EmptyChecker.isNotEmpty(listTemp)){
	                         long firstTimeTemp = listTemp.get(0).getCreateTime();
	                         long lastTimeTemp = listTemp.get(listTemp.size()-1).getCreateTime();
	                         convertFirstAndEndTime(pageVo, firstTimeTemp, lastTimeTemp);
                         }
                         
                    	 OrderExportData orderExportDataTemp = orderQueryLogic.exportOrderByBankManage(listTemp,req);
                    	 //导出
             			 excelWriter.write(orderExportDataTemp.getHeader(), orderExportDataTemp.getData(), orderExportDataTemp.getSheetName(), orderExportDataTemp.getExcelFileName());
            		 }
            	 }
            	 url = excelWriter.getUrl();
             }
             
         } catch (FroadBusinessException e) {
             LogCvt.error("银行管理平台-订单导出，业务异常：",e);
             resultVo.setResultCode(e.getCode());
             resultVo.setResultDesc(e.getMsg());
         } catch (Exception e) {
             LogCvt.error("银行管理平台-订单导出，系统异常：", e);
             resultVo.setResultCode(ResultCode.failed.getCode());
             resultVo.setResultDesc(ResultCode.failed.getMsg());
         }
         res.setResultVo(resultVo);
         res.setUrl(url);
         LogCvt.info("[银行管理平台]-订单导出-结束，耗时" + (System.currentTimeMillis() - st) + "ms，excel下载链接："+url);
         return res;
	}

    @Override
    public GetOrderDetailByBankManageVoRes getOrderDetailByBankManage(GetOrderDetailByBankManageVoReq req) throws TException {
        //银行管理平台订单详情
        LogCvt.info("[银行管理平台]-[订单管理]-订单详情接口-开始" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();

        GetOrderDetailByBankManageVoRes res = new GetOrderDetailByBankManageVoRes();
        ResultVo resultVo = new ResultVo();
        ResultBean rb = new ResultBean(ResultCode.success);
        try {
            if (StringUtils.equals(OrderType.face2face.getCode(), req.getType())) {
                rb = orderQueryLogic.getFacetfaceOrderDetailOfBankByOrderId(req.getClientId(), req.getSubOrderId());
            } else {
                rb = orderQueryLogic.getSubOrderOfBankByOrderIdAndType(req.getClientId(), req.getSubOrderId(), req.getType(),req.getDeliveryOption());
            }
            resultVo.setResultCode(rb.getCode());
            resultVo.setResultDesc(rb.getMsg());
            if (EmptyChecker.isNotEmpty(rb.getData())) {
                res = (GetOrderDetailByBankManageVoRes) rb.getData();
            }
        } catch (FroadBusinessException e) {
            LogCvt.error("错误码：" + e.getCode() + "， 错误信息：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("未知异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        res.setResultVo(resultVo);
        LogCvt.info("银行管理订单详细查询接口，耗时（" + (System.currentTimeMillis() - st) + "）毫秒， 响应内容：" + JSonUtil.toJSonString(res));
        return res;
    }
    
    @Override
    public OrderListByBossRes queryOrderListByBoss(OrderListByBossReq req) throws TException {
        LogCvt.info("Boss查询订单列表：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();
        
        OrderListByBossRes res = new OrderListByBossRes();
        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
        try {
            if(req.getStartTime() <= 0) {
                req.setStartTime(getDefaultStartTime());
            }
            if(req.getEndTime() <= 0) {
                req.setEndTime(getDefaultEndTime());
            }
            OrderQueryByBossCondition condition = (OrderQueryByBossCondition) BeanUtil.copyProperties(OrderQueryByBossCondition.class, req);
            
            PageVo pageVo = req.getPageVo();
            if(EmptyChecker.isEmpty(pageVo)) {
                pageVo = new PageVo();
            }
            
            ResultBean rb = orderQueryLogic.queryOrderByBoss(condition, pageVo);
            
            if(!rb.isSuccess()) {
                throw new FroadBusinessException(rb.getCode(), rb.getMsg());
            }
            
            List<OrderVo> list = (List<OrderVo>) rb.getData();
            
            if(list != null && !list.isEmpty()) {
                long firstTime = list.get(0).getCreateTime();
                long lastTime = list.get(list.size()-1).getCreateTime();
                convertFirstAndEndTime(req.getPageVo(), firstTime, lastTime);
            }
            
            res.setOrders(list);
            res.setPageVo(pageVo);
        } catch (FroadBusinessException e) {
            LogCvt.error("订单查询列表出错, 错误码：" + e.getCode() + ", 详细内容：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
            res.setResultVo(resultVo);
        } catch (Exception e) {
            LogCvt.error("未知异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("查询结果失败");
            res.setResultVo(resultVo);
        }
        res.setResultVo(resultVo);
        LogCvt.info("Boss查询订单结束耗时：" + (System.currentTimeMillis()-st) + "毫秒， 响应内容：" + JSonUtil.toJSonString(res));
        return res;
    }
    
    @Override
    public SubOrderByBossRes getSubOrderByBoss(SubOrderByBossReq req) throws TException {
        LogCvt.info("Boss查看订单详细：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();
        SubOrderByBossRes res = new SubOrderByBossRes();
        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
        try {
            if(BooleanUtils.toBoolean(req.getIsQrcode())) {
                LogCvt.error("面对面订单，目前不支持");
                throw new FroadBusinessException(ResultCode.failed.getCode(), "目前不支持面对面查询订单详细");
            }
            
            if(EmptyChecker.isEmpty(req.getOrderId())) {
                throw new FroadBusinessException(ResultCode.notAllParameters.getCode(), "订单号为空");
            }
            ResultBean rb = new ResultBean(ResultCode.success);
            rb = orderQueryLogic.getSubOrderDetailOfBossByOrderId(req.getClientId(), req.getOrderId());
            if(!rb.isSuccess()) {
                throw new FroadBusinessException(rb.getCode(), rb.getMsg());
            }
            res.setSubOrders((List<SubOrderVo>) rb.getData());
        } catch (FroadBusinessException e) {
            LogCvt.error("Boss订单详细查询出错, 错误码：" + e.getCode() + ", 详细内容：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("详细未知异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc("详细查询失败");
        }
        res.setResultVo(resultVo);
        LogCvt.info("Boss查询订单详细结束， 耗时：" + (System.currentTimeMillis()-st) + "毫秒，响应结果：" + JSonUtil.toJSonString(res));
        return res;
    }

    @Override
    public OrderDetailRes getOrderDetailById(String orderId, String clientId) throws TException {
        LogCvt.info("[大订单详情]-查询大订单详细：" + orderId);
        long st = System.currentTimeMillis();
        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "查询成功");
        
        OrderDetailRes res = new OrderDetailRes();
        
        try {
            if (EmptyChecker.isEmpty(orderId)) {
                throw new FroadBusinessException(ResultCode.notAllParameters.getCode(), "订单号不能为空");
            }
            ResultBean rb = orderQueryLogic.getOrderDetailByOrderId(clientId, orderId);
            if(!rb.isSuccess()) {
                throw new FroadBusinessException(rb.getCode(), rb.getMsg());
            }
            res = (OrderDetailRes) rb.getData();
        } catch (FroadBusinessException e) {
            LogCvt.error("查询出错, 错误码：" + e.getCode() + ", 详细内容：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("系统异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        res.setResultVo(resultVo);
        
        LogCvt.info("查询大订单详细耗时（"+(System.currentTimeMillis()-st)+"）毫秒，结果：" + JSonUtil.toJSonString(res));
        return res;
    }
    
    @Override
	public QueryGivePointsProductByBossRes queryGivePointsProductByBoss(
			QueryGivePointsProductByBossReq req) throws TException {
		LogCvt.info("[boss查询]-商品送积分明细，请求参数" + JSON.toJSONString(req,true));
        long st = System.currentTimeMillis();
        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "查询成功");
        QueryGivePointsProductByBossRes res = new QueryGivePointsProductByBossRes();
        try {
            OrderQueryByBossCondition condition = new OrderQueryByBossCondition();
            condition.setClientId(req.getClientId());
            condition.setOrderId(req.getOrderId());
            condition.setOrderStatus(req.getOrderStatus());
            condition.setPaymentMethod(req.getPaymentMethod());
            condition.setStartTime(req.getStartTime());
            condition.setEndTime(req.getEndTime());
            ResultBean rb = orderQueryLogic.queryGivePointsProductByBoss(condition,req.getPageVo());
            if(!rb.isSuccess()) {
                throw new FroadBusinessException(rb.getCode(), rb.getMsg());
            }
            res = (QueryGivePointsProductByBossRes) rb.getData();
        } catch (FroadBusinessException e) {
            LogCvt.error("查询出错, 错误码：" + e.getCode() + ", 详细内容：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("系统异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        res.setResultVo(resultVo);
        
        LogCvt.info("查询大订单详细耗时（"+(System.currentTimeMillis()-st)+"）毫秒，结果：" + JSonUtil.toJSonString(res));
        return res;
	}
    
    @Override
	public QueryRecvInfoForProductByBossRes queryRecvInfoForProductByBoss(
			QueryRecvInfoForProductByBossReq req) throws TException {
    	LogCvt.info("[boss查询]-导出预售订单配送信息列表，请求参数" + JSON.toJSONString(req,true));
        long st = System.currentTimeMillis();
        
        ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), "查询成功");
        QueryRecvInfoForProductByBossRes res = new QueryRecvInfoForProductByBossRes();
        
        try {
            OrderQueryByBossCondition condition = new OrderQueryByBossCondition();
            condition.setClientId(req.getClientId());
            condition.setSellStartTime(req.getPresellStartTime());
            condition.setSellEndTime(req.getPresellEndTime());
            condition.setStartTime(req.getOrderStartTime());
            condition.setEndTime(req.getOrderEndTime());
            condition.setfOrgCode(req.getFOrgCode());
            condition.setsOrgCode(req.getSOrgCode());
            
            ResultBean rb = orderQueryLogic.queryRecvInfoForProductByBoss(condition,req.getPageVo());
            if(!rb.isSuccess()) {
                throw new FroadBusinessException(rb.getCode(), rb.getMsg());
            }
            
            res = (QueryRecvInfoForProductByBossRes) rb.getData();
            
        } catch (FroadBusinessException e) {
            LogCvt.error("查询出错, 错误码：" + e.getCode() + ", 详细内容：" + e.getMsg());
            resultVo.setResultCode(e.getCode());
            resultVo.setResultDesc(e.getMsg());
        } catch (Exception e) {
            LogCvt.error("系统异常", e);
            resultVo.setResultCode(ResultCode.failed.getCode());
            resultVo.setResultDesc(ResultCode.failed.getMsg());
        }
        
        res.setResultVo(resultVo);
        
        LogCvt.info("[boss查询]-导出预售订单配送信息列表-耗时（"+(System.currentTimeMillis()-st)+"）毫秒，结果：" + JSonUtil.toJSonString(res));
        return res;
	}
    
    /**
     *  设置时间-第一条和最后一条
     * @param pageVo
     * @param startTime
     * @param endTime
     * @return
     *<pre>
     *
     * @version V1.0 修改人：Arron 日期：2015年5月14日 下午5:56:18 
     *
     *</pre>
     */
    private PageVo convertFirstAndEndTime(PageVo pageVo, long startTime, long endTime) {
        pageVo.setFirstRecordTime(startTime);
        pageVo.setLastRecordTime(endTime);
        return pageVo;
    }
    
    private static long getDefaultStartTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String ds = df.format(new Date()) + " 00:00:00";
        DateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        
        try {
            return dd.parse(ds).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    private static long getDefaultEndTime() {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String ds = df.format(new Date()) + " 23:59:59";
        DateFormat dd = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
        try {
            return dd.parse(ds).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return 0;
        }
    }
    
    /**计算两个long时间相隔天数*/
    private boolean checkTimeSpan(long startTime, long endTime) {
        boolean isOk = false;
        long nd = 1000*24*60*60;//一天的毫秒数
        // 相差天
        long d = (endTime - startTime) / nd;
        if(d <= 3) {
            isOk = true;  
        }
        return isOk;
    }
    
    public static void main(String[] args) {
//        System.out.println(getDefaultStartTime());
//        System.out.println(getDefaultEndTime());
        /*PropertiesUtil.load();
        PageVo pageVo = new PageVo();
        OrderQueryByBossCondition condition = new OrderQueryByBossCondition();
        String str1="2015-06-15 08:00:00";
		Date strDate1 = null;
		try {
			strDate1 = DateUtil.getSimpleDateFormat(DateUtil.C_TIME_PATTON_DEFAULT).parse(str1);
			System.out.println("strDate1:"+strDate1.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		String str2="2015-06-15 12:00:00";
		Date strDate2 = null;
		try {
			strDate2 = DateUtil.getSimpleDateFormat(DateUtil.C_TIME_PATTON_DEFAULT).parse(str2);
			System.out.println("strDate2:"+strDate2.getTime());
		} catch (ParseException e) {
			e.printStackTrace();
		}
        condition.setStartTime(strDate1.getTime());
        condition.setEndTime(strDate2.getTime());
        condition.setfOrgCode("340000");
        condition.setsOrgCode("340101");
        OrderQueryLogic orderQueryLogic = new OrderQueryLogicImpl();
        ResultBean result = orderQueryLogic.queryRecvInfoForProductByBoss(condition,pageVo);
        System.out.println(JSON.toJSONString(result,true));*/
    }

	@Override
	public QueryMerchantSettlementRes queryMerchantSettlement(
			QueryMerchantSettlementReq req) throws TException {
		QueryMerchantSettlementRes res = null;
		ResultVo resultVo = null;
		long dayBegin = 0l;
		long dayBeginOfMonth = 0l;
		Date curDate = null;
		OrderSupport orderSupport = null;
		TicketSupport ticketSupport = null;
		SettlementSupport settleSupport = null;
		Iterator<DBObject> iterator = null;
		DBObject tmpObj = null;
		double todayConsumedMoney = 0d;
		long todayConsumedCount = 0l;
		double monthConsumedMoney = 0d;
		long monthConsumedCount = 0l;
		
		LogCvt.info(new StringBuffer("商户PC首页统计查询请求：").append(req.toString()).toString());
		
		res = new QueryMerchantSettlementRes();
		resultVo = new ResultVo();
		resultVo.setResultCode(ResultCode.success.getCode());
		
		try {
			if (req.getMerchantId() == null || req.getMerchantId().trim().equals("null")){
				LogCvt.error("merchantId不能为空");
				return res;
			}
			
			orderSupport = new OrderSupportImpl();
			ticketSupport = new TicketSupportImpl();
			settleSupport = new SettlementSupportImpl();
			
			curDate = new Date();
			dayBegin = getDayBegin(curDate);
			dayBeginOfMonth = getFirstDayOfMonth(curDate);
			
			// 今日面对面 - cb_order
			iterator = getF2fOrderPineline(orderSupport, req, dayBegin, curDate.getTime());
			if (iterator.hasNext()){
				tmpObj = iterator.next();
				
				double cutMoney = 0;
				if (tmpObj.containsField("cut_money")){
					cutMoney = Arith.div(Double.parseDouble(tmpObj.get("cut_money").toString()), 1000);
					LogCvt.info(new StringBuffer("今日面对面和惠付总满减金额：").append(cutMoney).toString());
				}
				if (tmpObj.containsField("total_price")){
					res.setF2fTodayTotalMoney(Arith.add(cutMoney, Arith.div(Double.parseDouble(tmpObj.get("total_price").toString()), 1000)));
					LogCvt.info(new StringBuffer("今日面对面和惠付总金额：").append(res.getF2fTodayTotalMoney()).toString());
				}
				if (tmpObj.containsField("total_count")){
					res.setF2fTodayTotalCount(Long.parseLong(tmpObj.get("total_count").toString()));
					LogCvt.info(new StringBuffer("今日面对面和惠付总笔数：").append(res.getF2fTodayTotalCount()).toString());
				}
			}
			
			// 本月面对面 - cb_order
			iterator = getF2fOrderPineline(orderSupport, req, dayBeginOfMonth, curDate.getTime());
			if (iterator.hasNext()){
				tmpObj = iterator.next();
				
				double cutMoney = 0;
				if (tmpObj.containsField("cut_money")){
					cutMoney = Arith.div(Double.parseDouble(tmpObj.get("cut_money").toString()), 1000);
					LogCvt.info(new StringBuffer("本月面对面和惠付总满减金额：").append(cutMoney).toString());
				}
				
				if (tmpObj.containsField("total_price")){
					res.setF2fMonthTotalMoney(Arith.add(cutMoney, Arith.div(Double.parseDouble(tmpObj.get("total_price").toString()), 1000)));
					LogCvt.info(new StringBuffer("本月面对面和惠付总金额：").append(res.getF2fMonthTotalMoney()).toString());
				}
				if (tmpObj.containsField("total_count")){
					res.setF2fMonthTotalCount(Long.parseLong(tmpObj.get("total_count").toString()));
					LogCvt.info(new StringBuffer("本月面对面和惠付总笔数：").append(res.getF2fMonthTotalCount()).toString());
				}
			}
			
			// 今日团购已提笔数、金额  - cb_ticket
			iterator = getTicketPineline(ticketSupport, req, TicketStatus.consumed.getCode(), dayBegin, curDate.getTime());
			if (iterator.hasNext()){
				tmpObj = iterator.next();
				if (tmpObj.containsField("total_money")){
					todayConsumedMoney = Arith.div(Double.parseDouble(tmpObj.get("total_money").toString()), 1000);
					LogCvt.info(new StringBuffer("今日团购已提总金额：").append(todayConsumedMoney).toString());
				}
				if (tmpObj.containsField("total_count")){
					todayConsumedCount = Long.parseLong(tmpObj.get("total_count").toString());
					res.setGroupTodayTakeCount(todayConsumedCount);
					LogCvt.info(new StringBuffer("今日团购已提笔数：").append(todayConsumedCount).toString());
				}
			}
			
			// 本月团购已提笔数、金额  - cb_ticket
			iterator = getTicketPineline(ticketSupport, req, TicketStatus.consumed.getCode(), dayBeginOfMonth, curDate.getTime());
			if (iterator.hasNext()){
				tmpObj = iterator.next();
				if (tmpObj.containsField("total_money")){
					monthConsumedMoney = Arith.div(Double.parseDouble(tmpObj.get("total_money").toString()), 1000);
					LogCvt.info(new StringBuffer("本月团购已提总金额：").append(monthConsumedMoney).toString());
				}
				if (tmpObj.containsField("total_count")){
					monthConsumedCount = Long.parseLong(tmpObj.get("total_count").toString());
					res.setGroupMonthTakeCount(monthConsumedCount);
					LogCvt.info(new StringBuffer("本月团购已提笔数：").append(monthConsumedCount).toString());
				}
			}
			
			// 今日团购笔数、金额（今日之前购买的在当天提货的券码不包括在内） - cb_suborder_product
			iterator = getGroupSubOrderPineline(orderSupport, req, dayBegin, curDate.getTime());
			if (iterator.hasNext()){
				tmpObj = iterator.next();
				if (tmpObj.containsField("total_money")){
					res.setGroupTodayTotalMoney(Arith.div(Double.parseDouble(tmpObj.get("total_money").toString()), 1000));
					LogCvt.info(new StringBuffer("今日团购总金额：").append(res.getGroupTodayTotalMoney()).toString());
				}
				if (tmpObj.containsField("total_count")){
					res.setGroupTodayTotalCount(Long.parseLong(tmpObj.get("total_count").toString()));
					LogCvt.info(new StringBuffer("今日团购总笔数：").append(res.getGroupTodayTotalCount()).toString());
				}
			}
			
			// 本月团购笔数、金额（本月之前购买的在本月提货的券码不包括在内） - cb_suborder_product
			iterator = getGroupSubOrderPineline(orderSupport, req, dayBeginOfMonth, curDate.getTime());
			if (iterator.hasNext()){
				tmpObj = iterator.next();
				if (tmpObj.containsField("total_money")){
					res.setGroupMonthTotalMoney(Arith.div(Double.parseDouble(tmpObj.get("total_money").toString()), 1000));
					LogCvt.info(new StringBuffer("本月团购总金额：").append(res.getGroupMonthTotalMoney()).toString());
				}
				if (tmpObj.containsField("total_count")){
					res.setGroupMonthTotalCount(Long.parseLong(tmpObj.get("total_count").toString()));
					LogCvt.info(new StringBuffer("本月团购笔数：").append(res.getGroupMonthTotalCount()).toString());
				}
			}
			
			// 今日团购已结算金额 - cb_settlement
			iterator = getSettlementPineline(settleSupport, req, SettlementType.group.getCode(), SettlementStatus.settlementsucc.getCode(), dayBegin, curDate.getTime());
			if (iterator.hasNext()){
				tmpObj = iterator.next();
				if (tmpObj.containsField("total_money")){
					res.setGroupTodaySettleMoney(Arith.div(Double.parseDouble(tmpObj.get("total_money").toString()), 1000));
					LogCvt.info(new StringBuffer("今日团购已结算金额：").append(res.getGroupTodaySettleMoney()).toString());
				}
				if (tmpObj.containsField("total_count")){
					LogCvt.info(new StringBuffer("今日团购已结算总笔数：").append(tmpObj.get("total_count")).toString());
				}
			}
			
			// 本月团购已结算金额 - cb_settlement
			iterator = getSettlementPineline(settleSupport, req, SettlementType.group.getCode(), SettlementStatus.settlementsucc.getCode(), dayBeginOfMonth, curDate.getTime());
			if (iterator.hasNext()){
				tmpObj = iterator.next();
				if (tmpObj.containsField("total_money")){
					res.setGroupMonthSettleMoney(Arith.div(Double.parseDouble(tmpObj.get("total_money").toString()), 1000));
					LogCvt.info(new StringBuffer("本月团购已结算金额：").append(res.getGroupMonthSettleMoney()).toString());
				}
				if (tmpObj.containsField("total_count")){
					LogCvt.info(new StringBuffer("本月团购已结算总笔数：").append(tmpObj.get("total_count")).toString());
				}
			}
			
			// 本月收入 = 商户本月团购已结算金额 +本月面对面支付已结算金额（包括已结算及结算失败等）
			res.setMonthTotalSettleMoney(Arith.add(res.getGroupMonthSettleMoney(), res.getF2fMonthTotalMoney()));
			LogCvt.info(new StringBuffer("本月收入金额：").append(res.getMonthTotalSettleMoney()).toString());
			
			res.setMonthTotalSettleCount(0);
			LogCvt.info(new StringBuffer("本月收入笔数：").append(res.getMonthTotalSettleCount()).toString());
		} catch (Exception e){
			LogCvt.error("商户PC首页统计查询", e);
			resultVo.setResultCode(ResultCode.failed.getCode());
			resultVo.setResultDesc("查询异常");
			res = new QueryMerchantSettlementRes();
		}
		
		res.setResultVo(resultVo);
		LogCvt.info(new StringBuffer("商户PC首页统计查询响应：").append(res.toString()).toString());
		
		return res;
	}
	
	/**
	 * get current day begin
	 * 
	 * @return
	 */
	private long getDayBegin(Date date){
		long dayBegin = 0l;
    	Calendar calendar = null;
    	
    	calendar = Calendar.getInstance();
    	calendar.setTime(DateUtil.parse(DateUtil.DATE_FORMAT2, DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, date)));
		calendar.set(Calendar.HOUR, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		dayBegin = calendar.getTimeInMillis();
		
		return dayBegin;
	}

	/**
	 * get first day of the month
	 * 
	 * @param date
	 * @return
	 */
	private long getFirstDayOfMonth(Date date){
		long dayOfMonth = 0l;
    	Calendar calendar = Calendar.getInstance();
    	
    	calendar.setTime(DateUtil.parse(DateUtil.DATE_FORMAT2, DateUtil.formatDateTime(DateUtil.DATE_FORMAT2, date)));
    	calendar.add(Calendar.MONTH, 0);
    	calendar.set(Calendar.DAY_OF_MONTH, 1);
    	
    	dayOfMonth = getDayBegin(calendar.getTime());
		
		return dayOfMonth;
	}
	
	/**
	 * 面对面大订单信息统计
	 * 
	 * @param orderSupport
	 * @param req
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Iterator<DBObject> getF2fOrderPineline(OrderSupport orderSupport, QueryMerchantSettlementReq req, long startTime, long endTime){
		DBObject queryObj = null;
		DBObject matchObj = null;
		DBObject groupFieldObj = null;
		DBObject groupObj = null;
		List<DBObject> pipeline = null;
		
		// $match
		queryObj = new BasicDBObject();
		queryObj.put("client_id", req.getClientId());
		queryObj.put("merchant_id", req.getMerchantId());
		if (req.getOutletId() != null && !req.getOutletId().trim().equals("") && !req.getOutletId().trim().equals("0")){
			queryObj.put("outlet_id", req.getOutletId());
		}
		if (req.getUserId() != null && !req.getUserId().trim().equals("") && !req.getUserId().trim().equals("0")){
			queryObj.put("merchant_user_id", req.getUserId());
		}
		queryObj.put("is_qrcode", 1);
		queryObj.put("order_status", OrderStatus.paysuccess.getCode());
		queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		matchObj = new BasicDBObject();
		matchObj.put("$match", queryObj);
		
		// $group
		groupFieldObj = new BasicDBObject();
		groupFieldObj.put("_id", null);
		
//		groupFieldObj.put("total_money", new BasicDBObject("$sum", "$total_price"));
		
		/*List<String> addToSet = new ArrayList<String>();
		addToSet.add("$total_price");
		addToSet.add("$cut_money");
		groupFieldObj.put("total_money", new BasicDBObject("$sum", new BasicDBObject("$add", addToSet)));*/
		
		groupFieldObj.put("total_price", new BasicDBObject("$sum", "$total_price"));
		groupFieldObj.put("cut_money", new BasicDBObject("$sum", "$cut_money"));
		
		groupFieldObj.put("total_count", new BasicDBObject("$sum", 1));
		groupObj = new BasicDBObject();
		groupObj.put("$group", groupFieldObj);
		
		pipeline = new ArrayList<DBObject>();
		pipeline.add(matchObj);
		pipeline.add(groupObj);
		
		LogCvt.info(new StringBuffer("面对面大订单信息统计查询：").append(pipeline.toString()).toString());
		
		return orderSupport.findOrderByPipeline(pipeline);
	}
	
	/**
	 * 团购子订单信息统计
	 * 
	 * @param orderSupport
	 * @param req
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Iterator<DBObject> getGroupSubOrderPineline(OrderSupport orderSupport, QueryMerchantSettlementReq req, long startTime, long endTime){
		DBObject queryObj = null;
		DBObject matchObj = null;
		DBObject unwindObj = null;
		List<String> quantityFieldList = null;
		DBObject quantityAddObj = null;
		DBObject quantitySumObj = null;
		List<String> moneyFieldList = null;
		DBObject moneyMulObj = null;
		List<String> vipMoneyFieldList = null;
		DBObject vipMoneyMulObj = null;
		List<DBObject> mulObjList = null;
		DBObject moneyAddObj = null;
		DBObject moneySumObj = null;
		DBObject groupFieldObj = null;
		DBObject groupObj = null;
		List<DBObject> pipeline = null;
		
		// $match
		queryObj = new BasicDBObject();
		queryObj.put("client_id", req.getClientId());
		queryObj.put("merchant_id", req.getMerchantId());
		queryObj.put("order_status", OrderStatus.paysuccess.getCode());
		queryObj.put("type", SubOrderType.group_merchant.getCode());
		queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		matchObj = new BasicDBObject();
		matchObj.put("$match", queryObj);
		
		// $unwind
		unwindObj = new BasicDBObject();
		unwindObj.put("$unwind", "$products");
		
		// $group
		groupFieldObj = new BasicDBObject();
		groupFieldObj.put("_id", null);
		// total_count
		quantityFieldList = new ArrayList<String>();
		quantityFieldList.add("$products.quantity");
		quantityFieldList.add("$products.vip_quantity");
		quantityAddObj = new BasicDBObject();
		quantityAddObj.put("$add", quantityFieldList);
		quantitySumObj = new BasicDBObject();
		quantitySumObj.put("$sum", quantityAddObj);
		groupFieldObj.put("total_count", quantitySumObj);
		// total_money
		moneyFieldList = new ArrayList<String>();
		moneyFieldList.add("$products.money");
		moneyFieldList.add("$products.quantity");
		moneyMulObj = new BasicDBObject();
		moneyMulObj.put("$multiply", moneyFieldList);
		vipMoneyFieldList = new ArrayList<String>();
		vipMoneyFieldList.add("$products.vip_money");
		vipMoneyFieldList.add("$products.vip_quantity");
		vipMoneyMulObj = new BasicDBObject();
		vipMoneyMulObj.put("$multiply", vipMoneyFieldList);
		mulObjList = new ArrayList<DBObject>();
		mulObjList.add(moneyMulObj);
		mulObjList.add(vipMoneyMulObj);
		moneyAddObj = new BasicDBObject();
		moneyAddObj.put("$add", mulObjList);
		moneySumObj = new BasicDBObject();
		moneySumObj.put("$sum", moneyAddObj);
		groupFieldObj.put("total_money", moneySumObj);
		groupObj = new BasicDBObject();
		groupObj.put("$group", groupFieldObj);
		
		pipeline = new ArrayList<DBObject>();
		pipeline.add(matchObj);
		pipeline.add(unwindObj);
		pipeline.add(groupObj);
		
		LogCvt.info(new StringBuffer("团购子订单信息统计查询：").append(pipeline.toString()).toString());
		
		return orderSupport.findSubOrderByPipeline(pipeline);
	}
	
	/**
	 * 团购券消费信息统计
	 * 
	 * @param ticketSupport
	 * @param req
	 * @param status
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Iterator<DBObject> getTicketPineline(TicketSupport ticketSupport, QueryMerchantSettlementReq req, String status, long startTime, long endTime){
		DBObject queryObj = null;
		DBObject matchObj = null;
		DBObject groupFieldObj = null;
		DBObject groupObj = null;
		List<DBObject> pipeline = null;
		List<String> mulList = null;
		DBObject mulObj = null;
		List<String> statusList = null;
		
		// $match
		queryObj = new BasicDBObject();
		queryObj.put("client_id", req.getClientId());
		queryObj.put("merchant_id", req.getMerchantId());
		if (req.getOutletId() != null && !req.getOutletId().trim().equals("") && !req.getOutletId().trim().equals("0")){
			queryObj.put("outlet_id", req.getOutletId());
		}
		if (req.getUserId() != null && !req.getUserId().trim().equals("") && !req.getUserId().trim().equals("0")){
			queryObj.put("merchant_user_id", req.getUserId());
		}
		statusList = new ArrayList<String>();
		if (null != status){
			statusList.add(status);
		} else {
			statusList.add(TicketStatus.sent.getCode());
			statusList.add(TicketStatus.consumed.getCode());
		}
		queryObj.put("status", new BasicDBObject("$in", statusList));
		queryObj.put("type", SubOrderType.group_merchant.getCode());
		if (null == status) {
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
//			queryObj.put("consume_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		} else if(status.equals(TicketStatus.sent.getCode())){
			queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		} else if (status.equals(TicketStatus.consumed.getCode())){
			queryObj.put("consume_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		}
		matchObj = new BasicDBObject();
		matchObj.put("$match", queryObj);
		
		// $group
		groupFieldObj = new BasicDBObject();
		groupFieldObj.put("_id", null);
		groupFieldObj.put("total_count", new BasicDBObject("$sum", "$quantity"));
		mulList = new ArrayList<String>();
		mulList.add("$price");
		mulList.add("$quantity");
		mulObj = new BasicDBObject();
		mulObj.put("$multiply", mulList);
		groupFieldObj.put("total_money", new BasicDBObject("$sum", mulObj));
		groupObj = new BasicDBObject();
		groupObj.put("$group", groupFieldObj);
		
		pipeline = new ArrayList<DBObject>();
		pipeline.add(matchObj);
		pipeline.add(groupObj);
		
		LogCvt.info(new StringBuffer("团购券消费信息统计查询：").append(pipeline.toString()).toString());
		
		return ticketSupport.findByPipeline(pipeline);
	}
	
	/**
	 * 结算信息统计
	 * 
	 * @param settleSupport
	 * @param req
	 * @param settleType
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	private Iterator<DBObject> getSettlementPineline(
			SettlementSupport settleSupport, QueryMerchantSettlementReq req,
			String settleType, String status, long startTime, long endTime) {
		DBObject queryObj = null;
		DBObject matchObj = null;
		List<String> mulList = null;
		DBObject mulObj = null;
		DBObject groupFieldObj = null;
		DBObject groupObj = null;
		List<DBObject> pipeline = null;
		List<String> typeList = null;
		
		// $match
		queryObj = new BasicDBObject();
		queryObj.put("client_id", req.getClientId());
		queryObj.put("merchant_id", req.getMerchantId());
		if (req.getOutletId() != null && !req.getOutletId().trim().equals("")  && !req.getOutletId().trim().equals("0")){
			queryObj.put("outlet_id", req.getOutletId());
		}
		if (req.getUserId() != null && !req.getUserId().trim().equals("") && !req.getUserId().trim().equals("0")){
			queryObj.put("merchant_user_id", req.getUserId());
		}
		if (null != status){
			queryObj.put("settle_state", status);
		}
		if (null != settleType && !settleType.trim().equals("")){
			queryObj.put("type", settleType);
		} else {
			typeList = new ArrayList<String>();
			typeList.add(SettlementType.group.getCode());
			typeList.add(SettlementType.face2face.getCode());
			queryObj.put("type", new BasicDBObject("$in", typeList));
		}
		queryObj.put("create_time", new BasicDBObject(QueryOperators.GTE, startTime).append(QueryOperators.LTE, endTime));
		matchObj = new BasicDBObject();
		matchObj.put("$match", queryObj);
		
		// $group
		groupFieldObj = new BasicDBObject();
		groupFieldObj.put("_id", null);
		mulList = new ArrayList<String>();
		mulList.add("$money");
		mulList.add("$product_count");
		mulObj = new BasicDBObject();
		mulObj.put("$multiply", mulList);
//		groupFieldObj.put("total_money", new BasicDBObject("$sum", mulObj));
		groupFieldObj.put("total_money", new BasicDBObject("$sum", "$money"));
		groupFieldObj.put("total_count", new BasicDBObject("$sum", "$product_count"));
		groupObj = new BasicDBObject();
		groupObj.put("$group", groupFieldObj);
		
		pipeline = new ArrayList<DBObject>();
		pipeline.add(matchObj);
		pipeline.add(groupObj);
		
		LogCvt.info(new StringBuffer("结算信息统计查询：").append(pipeline.toString()).toString());
		
		return settleSupport.findByPipeline(pipeline);
	}




}
