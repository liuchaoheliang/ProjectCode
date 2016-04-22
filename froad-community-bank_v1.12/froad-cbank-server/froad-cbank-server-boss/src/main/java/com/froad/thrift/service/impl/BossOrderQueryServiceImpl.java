package com.froad.thrift.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.CreateSource;
import com.froad.enums.OrderStatus;
import com.froad.enums.PaymentMethod;
import com.froad.enums.ResultCode;
import com.froad.exceptions.FroadBusinessException;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.BossOrderQueryLogic;
import com.froad.logic.impl.BossOrderQueryLogicImpl;
import com.froad.po.BossPrefPayOrderDetailInfo;
import com.froad.po.BossPrefPayOrderListInfo;
import com.froad.po.BossPrefPayOrderPageReq;
import com.froad.po.OrderQueryByBossCondition;
import com.froad.po.TicketDto;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossOrderQueryService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.order.BossGroupOrderListReq;
import com.froad.thrift.vo.order.BossGroupOrderListRes;
import com.froad.thrift.vo.order.BossGroupOrderVo;
import com.froad.thrift.vo.order.BossPointOrderListReq;
import com.froad.thrift.vo.order.BossPointOrderListRes;
import com.froad.thrift.vo.order.BossPointOrderVo;
import com.froad.thrift.vo.order.BossPrefPayOrderDetailInfoVo;
import com.froad.thrift.vo.order.BossPrefPayOrderDetailRes;
import com.froad.thrift.vo.order.BossPrefPayOrderListInfoVo;
import com.froad.thrift.vo.order.BossPrefPayOrderListReq;
import com.froad.thrift.vo.order.BossPrefPayOrderListRes;
import com.froad.thrift.vo.order.BossPresellBillListReq;
import com.froad.thrift.vo.order.BossPresellBillListRes;
import com.froad.thrift.vo.order.BossPresellBillVo;
import com.froad.thrift.vo.order.BossPresellDetialRes;
import com.froad.thrift.vo.order.BossPresellOrderListReq;
import com.froad.thrift.vo.order.BossPresellOrderListRes;
import com.froad.thrift.vo.order.BossPresellOrderVo;
import com.froad.thrift.vo.order.BossQueryOrderDetailReq;
import com.froad.thrift.vo.order.BossQueryOrderDetailRes;
import com.froad.thrift.vo.order.BossQueryOrderListReq;
import com.froad.thrift.vo.order.BossQueryOrderListRes;
import com.froad.thrift.vo.order.BossQueryOrderVo;
import com.froad.thrift.vo.order.BossSubOrderVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.EmptyChecker;
import com.froad.util.JSonUtil;
import com.froad.util.Util;

/**
 *   交易订单查询相关接口
 * @author kevin
 *
 */
public class BossOrderQueryServiceImpl extends BizMonitorBaseService implements BossOrderQueryService.Iface{

	private BossOrderQueryLogic logic = new BossOrderQueryLogicImpl();
	
	public BossOrderQueryServiceImpl() {
		
	}

	public BossOrderQueryServiceImpl(String name, String version) {
		super(name, version);
	}
	 
	/**
	 * 团购交易查询（以券为单位）
	 */
	@SuppressWarnings("unchecked")
	public BossGroupOrderListRes queryGroupOrderList(BossGroupOrderListReq req)
			throws TException {
		BossGroupOrderListRes res = new BossGroupOrderListRes();
		try {
			ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			ResultBean result  = logic.getBossGroupOrderList(req);
			List<BossGroupOrderVo> voList = new ArrayList<BossGroupOrderVo>();
			PageVo pageVo = new PageVo();
			if(result.getData() != null) {
				MongoPage mongoPage = (MongoPage)result.getData();
				pageVo = convertPageParamToReq(mongoPage);
				List<TicketDto> poList = (List<TicketDto>)mongoPage.getItems();
				if(poList != null && poList.size() > 0) {
					for(TicketDto t : poList) {
						BossGroupOrderVo vo = new BossGroupOrderVo();
						vo.setConsigneeName(t.getDeliveryUserName());
						vo.setMemberName(t.getMemberName());
						vo.setOrderId(t.getOrderId());
						vo.setOutletName(t.getOutletName());
						vo.setPaymentTime(t.getPaymentTime() == null? 0 : t.getPaymentTime());
						vo.setProductName(t.getProductName());
						vo.setTicketId(t.getTicketId());
						vo.setTicketStatus(t.getStatus());
						vo.setConsumeTime(t.getConsumeTime() == null? 0 : t.getConsumeTime());
						vo.setRefundTime(t.getRefundTime() == null? 0 : t.getRefundTime());
						vo.setExpireTime(t.getExpireTime() == null? 0 : t.getExpireTime());
						vo.setPhone(t.getMobile());
						vo.setSendTime(t.getCreateTime());
						voList.add(vo);
					}
				}
			} 
			res.setPageVo(pageVo);
			res.setOrders(voList);
			res.setResultVo(resultVo);
		} catch (Exception e) {
			LogCvt.error("Boss平台查询团购交易分页列表失败", e);
			ResultVo resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			res.setResultVo(resultVo);
		}
		return res;
	}
	
	/**
	 * 预售交易查询（以券为单位）
	 */
	@SuppressWarnings("unchecked")
	public BossPresellOrderListRes queryPresellOrderList(
			BossPresellOrderListReq req) throws TException {
		BossPresellOrderListRes res = new BossPresellOrderListRes();
		try {
			ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			ResultBean result  = logic.getBossPresellOrderList(req);
			List<BossPresellOrderVo> voList = new ArrayList<BossPresellOrderVo>();
			PageVo pageVo = new PageVo();
			if(result.getData() != null) {
				MongoPage mongoPage = (MongoPage)result.getData();
				pageVo = convertPageParamToReq(mongoPage);
				List<TicketDto> poList = (List<TicketDto>)mongoPage.getItems();
				if(poList != null && poList.size() > 0) {
					for(TicketDto t : poList) {
						BossPresellOrderVo vo = new BossPresellOrderVo();
						vo.setConsigneeName(t.getDeliveryUserName());//收货人姓名
						vo.setConsigneePhone(t.getMobile());
						vo.setDeliveryEndTime(t.getDeliveryEndTime() == null? 0 : t.getDeliveryEndTime());
						vo.setDeliveryStartTime(t.getDeliveryStartTime() == null? 0 : t.getDeliveryStartTime());
						vo.setDeliveryType(t.getDeliveryType());
						vo.setMemberName(t.getMemberName());
						vo.setOrderId(t.getOrderId());
						vo.setPaymentTime(t.getPaymentTime() == null? 0 : t.getPaymentTime());
						vo.setProductName(t.getProductName());
						vo.setConsumeTime(t.getConsumeTime() == null? 0 : t.getConsumeTime());
						vo.setRefundTime(t.getRefundTime() == null? 0 : t.getRefundTime());
						vo.setExpireTime(t.getExpireTime() == null? 0 : t.getExpireTime());
						vo.setTicketId(t.getTicketId());
						vo.setTicketStatus(t.getStatus());
						vo.setClientId(t.getClientId());
						vo.setId(t.get_id().split("\"")[3]);
						voList.add(vo);
					}
				}
			}
			res.setPageVo(pageVo);
			res.setOrders(voList);
			res.setResultVo(resultVo);
		} catch (Exception e) {
			LogCvt.error("Boss平台查询预售交易分页列表失败", e);
			ResultVo resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			res.setResultVo(resultVo);
		}
		return res;
	}

	
	
	/**
	 * 分页参数转换
	 * convertPage:(这里用一句话描述这个方法的作用).
	 *
	 * @author zhouzhiwei
	 * 2015年8月7日 上午10:40:59
	 * @param page
	 * @param vo
	 *
	 */
	 private PageVo convertPageParamToReq(MongoPage page) {
		PageVo vo = new PageVo();
        vo.setPageCount(page.getPageCount());
        vo.setPageNumber(page.getPageNumber());
        vo.setPageSize(page.getPageSize());
        vo.setTotalCount(page.getTotalCount());
        vo.setLastPageNumber(page.getPageNumber());
        vo.setFirstRecordTime(page.getFirstRecordTime());
        vo.setLastRecordTime(page.getLastRecordTime());
        vo.setHasNext(page.getPageCount() > page.getPageNumber());
        return vo;
    }
	 
	 /**
	  * 分页查询线上积分兑换订单列表
	  * 
	  * @author liuyanyun
	  * 2015年8月7日 下午5:12:59
	  * @param req
	  * @return orderListRes
	  */
	public BossPointOrderListRes queryPointOrderList(BossPointOrderListReq req) throws TException {
		BossPointOrderListRes orderListRes = new BossPointOrderListRes();
		try {
			if(Checker.isEmpty(req)){
				return orderListRes;
			}
			orderListRes = logic.queryPointOrderList(req);
		}catch(Exception e) {
			LogCvt.error("Boss平台分页查询线上积分兑换订单列表失败", e);
		}
		
		return orderListRes;
	}

	/**
	 * 预售交易详情
	 * 
	 * @author liuyanyun
	 * 2015年8月13日 下午2:13:59
	 * @param clientId
	 * @param id
	 * @return orderListRes
	 */
	public BossPresellDetialRes getPresellDetial(String clientId, String id) throws TException {
		BossPresellDetialRes presellDetial = new BossPresellDetialRes();
		try {
			if(Checker.isEmpty(clientId) && Checker.isEmpty(id)){
				return presellDetial;
			}
			presellDetial = logic.getPresellDetial(clientId, id);
		}catch(Exception e) {
			LogCvt.error("Boss平台查询预售交易详情失败", e);
		}
		
		return presellDetial;
	}

	/**
	 * 预售交易账单交易查询
	 */
	@SuppressWarnings("unchecked")
	public BossPresellBillListRes getPresellBillList(BossPresellBillListReq req) throws TException {
		BossPresellBillListRes res = new BossPresellBillListRes();
		try {
			ResultVo resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			ResultBean result  = logic.getBossPresellBillList(req);
			List<BossPresellBillVo> billList = (List<BossPresellBillVo>) result.getData();
			res.setBillVo(billList);
			res.setResultVo(resultVo);
			PageVo page = new PageVo();
			page.setTotalCount(billList != null? billList.size() : 0);
			res.setPageVo(page);
		} catch (Exception e) {
			LogCvt.error("Boss平台查询预售交易账单列表失败", e);
			ResultVo resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			res.setResultVo(resultVo);
		}
		return res;
	}

	/**
	 * 预售交易账单excel生成
	 */
	public ExportResultRes getPresellBillExportUrl(BossPresellBillListReq req) throws TException {
		return logic.getBossPresellBillExport(req);
	}


	@SuppressWarnings("unchecked")
	@Override
	public BossQueryOrderListRes getOrderList(BossQueryOrderListReq req) throws TException {
		    LogCvt.info("Boss查询订单列表：" + JSonUtil.toJSonString(req));
	        long st = System.currentTimeMillis();
	        BossQueryOrderListRes res = new BossQueryOrderListRes();
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
	            ResultBean rb = logic.getOrderList(condition, pageVo, "0");
	            if(!rb.isSuccess()) {
	                throw new FroadBusinessException(rb.getCode(), rb.getMsg());
	            }
	            List<BossQueryOrderVo> list = (List<BossQueryOrderVo>) rb.getData();
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
	private PageVo convertFirstAndEndTime(PageVo pageVo, long startTime, long endTime) {
        pageVo.setFirstRecordTime(startTime);
        pageVo.setLastRecordTime(endTime);
        return pageVo;
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
    
	@Override
	public ExportResultRes exportQueryOrderList(BossQueryOrderListReq req) throws TException {
        if(req.getStartTime() <= 0) {
            req.setStartTime(getDefaultStartTime());
        }
        if(req.getEndTime() <= 0) {
            req.setEndTime(getDefaultEndTime());
        }
        OrderQueryByBossCondition condition = (OrderQueryByBossCondition) BeanUtil.copyProperties(OrderQueryByBossCondition.class, req);
        ExportResultRes res = logic.exportOrderList(condition);
        return res;
	}
	
	/**
     * 预售交易（预售券）导出
     * @author liuyanyun
	 * 2015年9月2日 下午2:13:59
	 * 
	 * @param req
	 * @return resultRes
     */
	public ExportResultRes exportPresellOrderTicket(BossPresellOrderListReq req) throws TException {
		int pageNo = 0;
		int pageSize = 20000;
		PageVo pageVo = req.getPageVo() == null ? new PageVo() : req.getPageVo();
		pageVo.setPageSize(pageSize);
		
		//第一步：根据条件查询要导出的所有数据
		List<BossPresellOrderVo> poList = new ArrayList<BossPresellOrderVo>();
		BossPresellOrderListRes presellRes = new BossPresellOrderListRes();
		
		//第二步：将excel列头放入List<String>   将数据放入List<List<String>> 
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("订单编号");
		header.add("券号");
		header.add("购买时间");
		header.add("提货方式");
		header.add("商品名称");
		header.add("用户名");
		header.add("提货期");
		header.add("提货人");
		header.add("提货电话");
		header.add("消费时间|过期时间|退款时间");
		header.add("券号状态");
		
		List<List<String>> data = new ArrayList<List<String>>();
		ResultVo rb = new ResultVo();
		String url = null;
		try {
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			boolean isSuccess = true;
			do {
				pageNo++;
				pageVo.setPageNumber(pageNo);
				presellRes.setOrders(null);
				req.setPageVo(pageVo);
				presellRes = queryPresellOrderList(req);
				
				if (Checker.isNotEmpty(presellRes)) {
					pageVo = presellRes.getPageVo();
					poList = presellRes.getOrders();
					
					data = presellConvertExcel(poList, (pageNo - 1) * pageSize);
					
					try {
						excelWriter.write(header, data, "预售订单列表", "预售订单列表");
					} catch (Exception e) {
						LogCvt.error("导出预售订单列表失败", e);
						isSuccess = false;
						break;
					}
					
				} else {
					poList = null;
				}
			} while(poList != null && poList.size() >= pageSize);
			if(isSuccess) {
				url = excelWriter.getUrl();
				if(StringUtils.isNotEmpty(url)) {
					rb.setResultCode(ResultCode.success.getCode());
					rb.setResultDesc(ResultCode.success.getMsg());
				} else {
					rb.setResultCode(ResultCode.failed.getCode());
					rb.setResultDesc(ResultCode.failed.getMsg());
				}
			} else {
				rb.setResultCode(ResultCode.failed.getCode());
				rb.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (Exception e) {
			LogCvt.error("导出预售订单列表失败", e);
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}
	
	/**
     * 团购交易（团购券）导出
     * @author liuyanyun
	 * 2015年9月2日 下午4:10:59
	 * 
	 * @param req
	 * @return resultRes
     */
	public ExportResultRes exportGroupOrderTicket(BossGroupOrderListReq req) throws TException {
		int pageNo = 0;
		int pageSize = 20000;
		PageVo pageVo = req.getPageVo() == null ? new PageVo() : req.getPageVo();
		pageVo.setPageSize(pageSize);
		
		//第一步：根据条件查询要导出的所有数据
		List<BossGroupOrderVo> gpList = new ArrayList<BossGroupOrderVo>();
		BossGroupOrderListRes groupRes = new BossGroupOrderListRes();
		
		//第二步：将excel列头放入List<String>   将数据放入List<List<String>> 
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("订单号");
		header.add("券号");
		header.add("购买时间");
		header.add("券号发送时间");
		header.add("门店名称");
		header.add("商品名称");
		header.add("用户名");
		header.add("提货人");
		header.add("提货人手机号");
		header.add("消费时间|过期时间|退款时间");
		header.add("券号状态");
		
		List<List<String>> data = new ArrayList<List<String>>();
		ResultVo rb = new ResultVo();
		String url = null;
		try {
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			boolean isSuccess = true;
			do {
				pageNo++;
				pageVo.setPageNumber(pageNo);
				groupRes.setOrders(null);
				req.setPageVo(pageVo);
				groupRes = queryGroupOrderList(req);
				
				if (Checker.isNotEmpty(groupRes)) {
					pageVo = groupRes.getPageVo();
					gpList = groupRes.getOrders();
					
					data = groupConverExcel(gpList, (pageNo - 1) * pageSize);
					
					try {
						excelWriter.write(header, data, "团购订单列表", "团购订单列表");
					} catch (Exception e) {
						LogCvt.error("导出团购订单列表失败", e);
						isSuccess = false;
						break;
					}
					
				} else {
					gpList = null;
				}
			} while(gpList != null && gpList.size() >= pageSize);
			if(isSuccess) {
				url = excelWriter.getUrl();
				if(StringUtils.isNotEmpty(url)) {
					rb.setResultCode(ResultCode.success.getCode());
					rb.setResultDesc(ResultCode.success.getMsg());
				} else {
					rb.setResultCode(ResultCode.failed.getCode());
					rb.setResultDesc(ResultCode.failed.getMsg());
				}
			} else {
				rb.setResultCode(ResultCode.failed.getCode());
				rb.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (Exception e) {
			LogCvt.error("导出团购订单列表失败", e);
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}
	
	/**
     * 线上积分兑换导出
     * @author liuyanyun
	 * 2015年8月2日 下午5:09:59
	 * 
	 * @param req
	 * @return resultRes
     */
	public ExportResultRes exportPointOrderTicket(BossPointOrderListReq req) throws TException {
		int pageNo = 0;
		int pageSize = 20000;
		PageVo pageVo = req.getPageVo() == null ? new PageVo() : req.getPageVo();
		pageVo.setPageSize(pageSize);
		
		//第一步，根据条件查询所有的数据
		List<BossPointOrderVo> poList = new ArrayList<BossPointOrderVo>();
		BossPointOrderListRes bossRes = new BossPointOrderListRes();
		
		//第二步：将excel列头放入List<String>   将数据放入List<List<String>> 
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("订单编号");
		header.add("下单时间");
		header.add("总价");
		header.add("订单状态");
		header.add("商品ID");
		header.add("商品名称");
		header.add("购买数量");
		header.add("提货人");
		header.add("城市");
		header.add("地址");
		header.add("手机号");
		
		List<List<String>> data = new ArrayList<List<String>>();
		ResultVo rb = new ResultVo();
		String url = null;
		try {
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			boolean isSuccess = true;
			do {
				pageNo++;
				pageVo.setPageNumber(pageNo);
				req.setPageVo(pageVo);
				bossRes.setOrders(null);
				bossRes = queryPointOrderList(req);
				
				if (Checker.isNotEmpty(bossRes)) {
					pageVo = bossRes.getPageVo();
					poList = bossRes.getOrders();
					
					data = pointConvertExcel(poList, (pageNo - 1) * pageSize);
					
					try {
						excelWriter.write(header, data, "线上积分兑换订单列表", "线上积分兑换订单列表");
					} catch (Exception e) {
						LogCvt.error("导出线上积分兑换订单列表失败", e);
						isSuccess = false;
						break;
					}
				} else {
					poList = null;
				}
			} while (poList != null && poList.size() >= pageSize);
			if(isSuccess) {
				url = excelWriter.getUrl();
				if(StringUtils.isNotEmpty(url)) {
					rb.setResultCode(ResultCode.success.getCode());
					rb.setResultDesc(ResultCode.success.getMsg());
				} else {
					rb.setResultCode(ResultCode.failed.getCode());
					rb.setResultDesc(ResultCode.failed.getMsg());
				}
			} else {
				rb.setResultCode(ResultCode.failed.getCode());
				rb.setResultDesc(ResultCode.failed.getMsg());
			}
		} catch (Exception e) {
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}

	@SuppressWarnings("unchecked")
	@Override
	public BossQueryOrderDetailRes getOrderDetail(BossQueryOrderDetailReq req) throws TException {
		LogCvt.info("Boss查看订单详细：" + JSonUtil.toJSonString(req));
        long st = System.currentTimeMillis();
        BossQueryOrderDetailRes res = new BossQueryOrderDetailRes();
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
            rb = logic.getSubOrderDetailOfBossByOrderId(req.getClientId(), req.getOrderId());
            if(!rb.isSuccess()) {
                throw new FroadBusinessException(rb.getCode(), rb.getMsg());
            }
            res.setSubOrders((List<BossSubOrderVo>) rb.getData());
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
	
	/**
	 * 预售交易（预售券）列表信息转换为excel
	 * @author liuyanyun
	 */
	private List<List<String>> presellConvertExcel(List<BossPresellOrderVo> poList, int index) {
		List<List<String>> data = new ArrayList<List<String>>();
		
		if (poList == null || poList.size() == 0) {
			return data;
		}
		
		//将数据放入List<List<String>> 
		List<String> record = null;
		String paymentTime = "";
		String startTime = "";
		String endTime = "";
		String payMentTime = "";
		for (BossPresellOrderVo vo : poList) {
			record = new ArrayList<String>();
			record.add(String.valueOf(++index));//序号
			record.add(vo.getOrderId());//订单编号
			record.add(StringUtils.overlay(vo.getTicketId(), "****", 4, 8));//券号
			//购买时间
			if (vo.getPaymentTime() != 0) {
				paymentTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getPaymentTime()));
				record.add(paymentTime);
			}else{
				record.add("--");
			}
			//提货方式
			if (Checker.isNotEmpty(vo.getDeliveryType())) {
				switch (Integer.valueOf(vo.getDeliveryType())){
					case 0:
						record.add("送货上门");
						break;
					case 1:
						record.add("网点自提");
						break;
					case 2:
						record.add("配送或自提");
						break;
					default:
						record.add("--");
				}
				
			}else{
				record.add("--");
			}
			record.add(vo.getProductName());//商品名称
			if (Checker.isNotEmpty(vo.getMemberName())) {
				record.add(vo.getMemberName());//用户名
			}else{
				record.add("--");
			}
			if (vo.getDeliveryStartTime() != 0 && vo.getDeliveryEndTime() != 0) {
				startTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getDeliveryStartTime()));//提货开始时间
				endTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getDeliveryEndTime()));//提货结束时间
				record.add(startTime + "--" + endTime);//提货期
			}else{
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getConsigneeName())) {
				record.add(vo.getConsigneeName());//提货人
			}else{
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getConsigneePhone())) {
				record.add(vo.getConsigneePhone());//提货电话
			}else{
				record.add("--");
			}
			//消费时间|过期时间|退款时间
			if (vo.getConsumeTime() != 0) {
				payMentTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getConsumeTime()));
				record.add(payMentTime);
			}else{
				record.add("--");
			}
			
		    //券号状态
		    switch(Integer.valueOf(vo.getTicketStatus())){
			    case 0: record.add("待发送");
			    	break;
			    case 1: record.add("已发送");
			    	break;
			    case 2: record.add("已消费");
		    		break;
			    case 3: record.add("已过期");
		    		break;
			    case 4: record.add("已退款");
		    		break;
			    case 5: record.add("退款中");
		    		break;
			    case 6: record.add("退款失败");
		    		break;
			    case 7: record.add("已过期退款");
		    		break;
			    case 8: record.add("未发码退款");
		    		break;
			    case 9: record.add("已过期退款中");
		    		break;
		    	default: record.add("--");
		    }
			
			data.add(record);
			
		}
		return data;
	}
	
	/**
	 * 团购交易（团购券）导出
	 * @author liuyanyun
	 */
	private List<List<String>> groupConverExcel(List<BossGroupOrderVo> gpList, int index) {
		List<List<String>> data = new ArrayList<List<String>>();
		
		if (gpList == null || gpList.size() == 0) {
			return null;
		}
		
		//将数据放入List<List<String>> 
		List<String> record = null;
		String payMentTime = "";
		String sendTime = "";
		String consumeTime = "";
		for (BossGroupOrderVo vo : gpList) {
			record = new ArrayList<String>();
			record.add(String.valueOf(++index));//序号
			if (Checker.isNotEmpty(vo.getOrderId())){
				record.add(vo.getOrderId());//订单号
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getTicketId())) {
				record.add(StringUtils.overlay(vo.getTicketId(), "****", 4, 8));//券号
			}else {
				record.add("--");
			}
			
			//购买时间
			if (vo.getPaymentTime() != 0) {
				payMentTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getPaymentTime()));
				record.add(payMentTime);
			} else {
				record.add("--");
			}
			//券号发送时间
			if (vo.getSendTime() != 0) {
				sendTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getSendTime()));
				record.add(sendTime);
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getOutletName())) {
				record.add(vo.getOutletName());//门店名称
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getProductName())) {
				record.add(vo.getProductName());//商品名称
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getMemberName())) {
				record.add(vo.getMemberName());//用户名
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getConsigneeName())) {
				record.add(vo.getConsigneeName());//提货人
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getPhone())) {
				record.add(vo.getPhone());//提货人手机号
			}else {
				record.add("--");
			}
			//消费时间|过期时间|退款时间
			if (vo.getConsumeTime() != 0) {
				consumeTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getConsumeTime()));
				record.add(consumeTime);
			}else{
				record.add("--");
			}
			if(Checker.isNotEmpty(vo.getTicketStatus())){
				 //券号状态
			    switch(Integer.valueOf(vo.getTicketStatus())){
				    case 0: record.add("待发送");
				    	break;
				    case 1: record.add("已发送");
				    	break;
				    case 2: record.add("已消费");
			    		break;
				    case 3: record.add("已过期");
			    		break;
				    case 4: record.add("已退款");
			    		break;
				    case 5: record.add("退款中");
			    		break;
				    case 6: record.add("退款失败");
			    		break;
				    case 7: record.add("已过期退款");
			    		break;
				    case 8: record.add("未发码退款");
			    		break;
				    case 9: record.add("已过期退款中");
			    		break;
			    	default: record.add("--");
			    }
			}else {
				record.add("--");
			}
			
			data.add(record);
		}
		return data;
	}
	
	/**
	 * 线上积分兑换导出
	 * @author liuyanyun
	 */
	private List<List<String>> pointConvertExcel(List<BossPointOrderVo> poList, int index) {
		List<List<String>> data = new ArrayList<List<String>>();
		
		if (poList == null || poList.size() == 0) {
			return data;
		}
		
		//将数据放入List<List<String>>
		List<String> record =null;
		String createTime = "";
		for (BossPointOrderVo vo : poList) {
			record = new ArrayList<String>();
			record.add(String.valueOf(++index));//序号
			if (Checker.isNotEmpty(vo.getOrderId())) {
				record.add(vo.getOrderId());//订单编号
			}else {
				record.add("--");
			}
			//下单时间
			if (vo.getCreateTime() != 0) {
				createTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getCreateTime()));
				record.add(createTime);
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getTotalMoney())) {
				record.add(String.valueOf(vo.getTotalMoney()));//总价
			}else {
				record.add("--");
			}
			//订单状态
			if (Checker.isNotEmpty(vo.getOrderStatus())) {
				switch(Integer.valueOf(vo.getOrderStatus())) {
					case 1: record.add("订单创建");
						break;
					case 2: record.add("订单用户关闭");
						break;
					case 3: record.add("订单系统关闭");
						break;
					case 4: record.add("订单支付中");
						break;
					case 5: record.add("订单支付失败");
						break;
					case 6: record.add("订单支付完成");
						break;
					default: record.add("--");
				}
			}else {
				record.add("--");
			}
			if(Checker.isNotEmpty(vo.getProductId())){
				record.add(vo.getProductId());//商品ID
			}else {
				record.add("--");
			}
			if(Checker.isNotEmpty(vo.getProductName())){
				record.add(vo.getProductName());//商品名称
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getBuyCount())) {
				record.add(String.valueOf(vo.getBuyCount()));//购买数量
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getReciver())) {
				record.add(vo.getReciver());//提货人
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getCity())) {
				record.add(vo.getCity());//城市
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getAddress())) {
				record.add(vo.getAddress());//地址
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getPhone())) {
				record.add(vo.getPhone());//手机号
			}else {
				record.add("--");
			}
			data.add(record);
		}
		return data;
	}

	@Override
	public BossPrefPayOrderListRes queryPrefPayOrderList(BossPrefPayOrderListReq req, PageVo pageVo) throws TException {
		LogCvt.info("[面对面惠付订单列表查询]-开始");
		BossPrefPayOrderListRes resVo = new BossPrefPayOrderListRes();
		ResultVo resultVo = null;
		try {
			MongoPage page = (MongoPage) BeanUtil.copyProperties(MongoPage.class, pageVo);
			BossPrefPayOrderPageReq reqInfo = (BossPrefPayOrderPageReq) BeanUtil.copyProperties(BossPrefPayOrderPageReq.class, req);
			
			page = logic.queryPrefPayOrderList(reqInfo, page);
			
			pageVo = (PageVo) BeanUtil.copyProperties(PageVo.class, page);
			List<BossPrefPayOrderListInfoVo> infoVos = (List<BossPrefPayOrderListInfoVo>) BeanUtil.copyProperties(BossPrefPayOrderListInfoVo.class, page.getItems());
			resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			
			resVo.setResultVo(resultVo);
			resVo.setPageVo(pageVo);
			resVo.setInfoVos(infoVos);
		} catch (Exception e) {
			LogCvt.error("[面对面惠付订单列表查询]-异常", e);
			resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			resVo.setResultVo(resultVo);
			resVo.setPageVo(pageVo);
			resVo.setInfoVos(new ArrayList<BossPrefPayOrderListInfoVo>());
		}
		LogCvt.info("[面对面惠付订单列表查询]-结束");
		return resVo;
	}

	@Override
	public ExportResultRes exportPrefPayOrderList(BossPrefPayOrderListReq req) throws TException {
		LogCvt.info("[面对面惠付订单列表导出]-开始");
		ExportResultRes resVo = new ExportResultRes();
		ResultVo resultVo = null;
		String url = "";
		try {
			List<String> header = new ArrayList<String>();
			header.add("订单编号");
			header.add("订单状态");
			header.add("用户会员名");
			header.add("用户手机号");
			header.add("支付类型");
			header.add("所属商户");
			header.add("所属门店");
			header.add("支付方式");
			header.add("总金额");
			header.add("现金");
			header.add("联盟积分");
			header.add("银行积分");
			header.add("银行积分兑换比例");
			header.add("创建时间");
			header.add("支付时间");
			header.add("所属客户端");
			header.add("账单号");
			header.add("订单来源");
			
			int pageNo = 0;
			int pageSize = 1000;
			MongoPage page = new MongoPage();
			page.setPageSize(pageSize);
			List<List<String>> data = new ArrayList<List<String>>();
			BossPrefPayOrderPageReq reqInfo = (BossPrefPayOrderPageReq) BeanUtil.copyProperties(BossPrefPayOrderPageReq.class, req);
			List<BossPrefPayOrderListInfo> infos = null;
			ExcelWriter excelWriter = new ExcelWriter(',');
			do{
				pageNo++;
				page.setPageNumber(pageNo);
				
				page = logic.queryPrefPayOrderList(reqInfo, page);
				infos = (List<BossPrefPayOrderListInfo>) page.getItems();
				data = prefPayOrderConvertExcel(infos);
				excelWriter.writeCsv(header, data, "面对面与惠付订单");
			}while(pageNo < page.getPageCount());
			
			url = excelWriter.getUrl();
			if(Checker.isNotEmpty(url)) {
				resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			}else{
				resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			}
			resVo.setResultVo(resultVo);
			resVo.setUrl(url);
		} catch (Exception e) {
			LogCvt.error("[面对面惠付订单列表导出]-异常", e);
			resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			resVo.setResultVo(resultVo);
			resVo.setUrl(url);
		}
		LogCvt.info("[面对面惠付订单列表导出]-结束");
		return resVo;
	}

	
	private List<List<String>> prefPayOrderConvertExcel(List<BossPrefPayOrderListInfo> infoVos){
		List<List<String>> perList = new ArrayList<List<String>>();
		if(Checker.isEmpty(infoVos)) {
			return perList;
		}
		
		List<String> rowList = null;
		BossPrefPayOrderListInfo info = null;
		for(int i = 0; i < infoVos.size(); i++){
			info = infoVos.get(i);
			rowList = new ArrayList<String>();
			
			//订单编号
			rowList.add(ObjectUtils.toString(info.getOrderId(), "-"));
			
			//订单状态
			if(Checker.isNotEmpty(OrderStatus.getType(info.getOrderStatus()))){
				rowList.add(OrderStatus.getType(info.getOrderStatus()).getBossDescribe());
			}else{
				rowList.add("-");
			}
			
			//用户会员名
			rowList.add(ObjectUtils.toString(info.getMemberName(), "-"));
			
			//用户手机号
			rowList.add(ObjectUtils.toString(info.getPhone(), "-"));
			
			//支付类型
			if(Checker.isNotEmpty(info.getOrderType())){
				if("1".equals(info.getOrderType())){
					rowList.add("面对面支付");
				}else if("2".equals(info.getOrderType())){
					rowList.add("惠付");
				}
			}else{
				rowList.add("-");
			}
			
			//所属商户
			rowList.add(ObjectUtils.toString(info.getMerchantName(), "-"));

			//所属门店
			rowList.add(ObjectUtils.toString(info.getOutletName(), "-"));
			
			//支付方式
			rowList.add(Checker.isNotEmpty(PaymentMethod.getByCode(info.getPaymentMethod())) ? PaymentMethod.getByCode(info.getPaymentMethod()).getBossDescribe() : "-");
			
			//总金额
			rowList.add(info.getTotalPrice() > 0 ? Util.formatDecimalNum(info.getTotalPrice(), Util.moneyFormat) : "-");
			
			//现金
			rowList.add(info.getCash() > 0 ? Util.formatDecimalNum(info.getCash(), Util.moneyFormat) : "-");
			
			//联盟积分
			rowList.add(info.getFftPoints() > 0 ? Util.formatDecimalNum(info.getFftPoints(), Util.moneyFormat) : "-");
			
			//银行积分
			rowList.add(info.getBankPoints() > 0 ? Util.formatDecimalNum(info.getBankPoints(), Util.moneyFormat) : "-");
			
			//银行积分兑换比例
			rowList.add(Checker.isNotEmpty(info.getPointRate()) && !"0".equals(info.getPointRate()) ? (info.getPointRate() + ":1") : "-");
			
			//创建时间
			rowList.add(info.getCreateTime() > 0 ? DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, info.getCreateTime()) : "-");
			
			//支付时间
			rowList.add(Checker.isNotEmpty(info.getPaymentTime()) && info.getPaymentTime() > 0 ? DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, info.getPaymentTime()) : "-");
			
			//所属客户端
			rowList.add(ObjectUtils.toString(info.getClientName(), "-"));
			
			//账单号
			rowList.add(ObjectUtils.toString(info.getBillNos(), "-"));
			
			//订单来源
			rowList.add(Checker.isNotEmpty(CreateSource.getByCode(info.getCreateSource())) ? CreateSource.getByCode(info.getCreateSource()).getBossDescribe() : "-");
			
			perList.add(rowList);
		}
		
		return perList;
	}
	
	@Override
	public BossPrefPayOrderDetailRes getPrefPayOrderDetail(String clientId, String orderId) throws TException {
		LogCvt.info("[面对面惠付订单查询详情]-开始");
		BossPrefPayOrderDetailRes resVo = new BossPrefPayOrderDetailRes();
		ResultVo resultVo = null;
		try {
			BossPrefPayOrderDetailInfo detail = logic.getPrefPayOrderDetail(clientId, orderId);
			BossPrefPayOrderDetailInfoVo infoVo = (BossPrefPayOrderDetailInfoVo) BeanUtil.copyProperties(BossPrefPayOrderDetailInfoVo.class, detail);
			
			resultVo = new ResultVo(ResultCode.success.getCode(), ResultCode.success.getMsg());
			resVo.setResultVo(resultVo);
			resVo.setInfoVo(infoVo);
		} catch (Exception e) {
			LogCvt.info("[面对面惠付订单查询详情]-异常");
			resultVo = new ResultVo(ResultCode.failed.getCode(), ResultCode.failed.getMsg());
			resVo.setResultVo(resultVo);
			resVo.setInfoVo(new BossPrefPayOrderDetailInfoVo());
		}
		LogCvt.info("[面对面惠付订单查询详情]-结束");
		return resVo;
	}
	
} 
