package com.froad.thrift.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;

import com.froad.common.beans.ResultBean;
import com.froad.db.mongo.page.MongoPage;
import com.froad.enums.ResultCode;
import com.froad.enums.SettlementStatus;
import com.froad.ftp.ExcelWriter;
import com.froad.logback.LogCvt;
import com.froad.logic.SettlementLogic;
import com.froad.logic.impl.SettlementLogicImpl;
import com.froad.po.settlement.SettlementDto;
import com.froad.po.settlement.SettlementReq;
import com.froad.thrift.monitor.service.BizMonitorBaseService;
import com.froad.thrift.service.BossSettlementService;
import com.froad.thrift.vo.ExportResultRes;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.settlement.BossSettlementPage;
import com.froad.thrift.vo.settlement.BossSettlementVo;
import com.froad.util.BeanUtil;
import com.froad.util.Checker;
import com.froad.util.DateUtil;
import com.froad.util.JSonUtil;

/**
 * Boss结算服务
 * 
 * @author wangzhangxu
 * @date 2015年8月4日 下午6:11:24
 * @version v1.0
 */
public class BossSettlementServiceImpl extends BizMonitorBaseService implements BossSettlementService.Iface {

	private SettlementLogic logic = new SettlementLogicImpl();
	
	public BossSettlementServiceImpl() {}
	
	public BossSettlementServiceImpl(String name, String version) {
		super(name, version);
	}

	@Override
	public BossSettlementPage queryByPage(BossSettlementPage page) throws TException {
		LogCvt.info("分页查询结算记录, 请求参数:" + JSonUtil.toJSonString(page));
		PageVo pagevo = page.getPage();
		if (pagevo == null) {
			pagevo = new PageVo();
			pagevo.setPageNumber(1);
			pagevo.setPageCount(10);
		}
		MongoPage mongoPage = (MongoPage) BeanUtil.copyProperties(MongoPage.class, pagevo);
		
		SettlementReq req = new SettlementReq();
		req.setPage(mongoPage);
		req.setBegDate(pagevo.getBegDate());
		req.setEndDate(pagevo.getEndDate());
		req.setClientId(page.getClientId());
		if (StringUtils.isNotEmpty(page.getMerchantName())) {
			req.setMerchantName(page.getMerchantName());
		}
		if (StringUtils.isNotEmpty(page.getOutletName())) {
			req.setOutletName(page.getOutletName());
		}
		if (StringUtils.isNotEmpty(page.getProductName())) {
			req.setProductName(page.getProductName());
		}
		req.setType(SettlementStatus.getSettlementByCode(page.getSettleState()));
		req.setOrderId(page.getOrderId());
		req.setBillNo(page.getBillNo());
		// 分页查询
		mongoPage = logic.querySettlementByPage(req);
		// 组装响应信息
		pagevo.setTotalCount(mongoPage.getTotalCount());
		pagevo.setPageCount(mongoPage.getPageCount());
		pagevo.setFirstRecordTime(mongoPage.getFirstRecordTime());
		pagevo.setLastPageNumber(mongoPage.getPageNumber());
		pagevo.setLastRecordTime(mongoPage.getLastRecordTime());

		page.setPage(pagevo);
		page.setRespList(convertVo(mongoPage.getItems()));
		return page;
	}

	@SuppressWarnings("unchecked")
	private List<BossSettlementVo> convertVo(List<?> list) {
		List<BossSettlementVo> settlementList = new ArrayList<BossSettlementVo>();
		if (list == null) {
			return settlementList;
		}

		for (SettlementDto settle : (List<SettlementDto>) list) {
			BossSettlementVo vo = convertVo(settle);
			settlementList.add(vo);
		}

		return settlementList;
	}

	private BossSettlementVo convertVo(SettlementDto settle) {
		BossSettlementVo vo = new BossSettlementVo();
		vo.setClientId(settle.getClientId());
		vo.setCreateTime(settle.getCreateTime());
		vo.setId(settle.getId());
		vo.setSettlementId(settle.getSettlementId());
		vo.setOrderId(settle.getOrderId());
		vo.setSubOrderId(settle.getSubOrderId());
		vo.setMerchantId(settle.getMerchantId());
		vo.setOutletId(settle.getOutletId());
		vo.setType(Integer.parseInt(settle.getType()));
		vo.setPaymentId(settle.getPaymentId());
		vo.setSettleState(settle.getSettleState());
		vo.setMoney(settle.getMoneyd());
		vo.setRemark(settle.getRemark());
		vo.setPaymentId(settle.getProductId());
		vo.setProductName(settle.getProductName());
		vo.setProductCount(settle.getProductCount());
		vo.setTickets(settle.getTickets());
		vo.setMerchantName(settle.getMerchantName());
		vo.setOutletName(settle.getOutletName());
		vo.setBillNo(settle.getBillNo());
		return vo;
	}

	@Override
	public ResultVo updateSettleState(String id, String status, String remark) throws TException {
		ResultBean result = logic.updateSettlement(id, SettlementStatus.getSettlementByCode(status), remark);
		ResultVo rvo = new ResultVo();
		rvo.setResultCode(result.getCode());
		rvo.setResultDesc(result.getMsg());
		return rvo;
	}

	/**
	 * Boss结算导出
	 * 
	 * @author liuyanyun
	 * @date 2015年8月28日 下午2:19:24
	 * 
	 */
	@Override
	public ExportResultRes exportSettlementQueryByPage(BossSettlementPage page) throws TException {
		int pageNo = 0;
		int pageSize = 20000;
		PageVo pageVo = page.getPage() == null ? new PageVo() : page.getPage();
		pageVo.setPageSize(pageSize);
		
		//第一步：根据条件查询要导出的所有数据
		List<BossSettlementVo> list = new ArrayList<BossSettlementVo>();
		BossSettlementPage bossResult = new BossSettlementPage();
		
		//第二步：将excel列头放入List<String>   将数据放入List<List<String>> 
		ExportResultRes resultRes = new ExportResultRes();
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("订单编号");
		header.add("结算单号");
		header.add("商户名称");
		header.add("消费数量");
		header.add("结算金额");
		header.add("结算时间");
		header.add("结算门店");
		header.add("券码");
		header.add("状态");
		header.add("账单编号");
		
		List<List<String>> data = new ArrayList<List<String>>();
		ResultVo rb = new ResultVo();
		String url = null;
		try {
			ExcelWriter excelWriter = new ExcelWriter(pageSize);
			boolean isSuccess = true;
			do {
				pageNo++;
				pageVo.setPageNumber(pageNo);
				page.setRespList(null);
				page.setPage(pageVo);
				bossResult = queryByPage(page);
				if(Checker.isNotEmpty(bossResult)) {
					pageVo = bossResult.getPage();
					list = bossResult.getRespList();
					
					data = convertExcel(list, (pageNo - 1) * pageSize);
					
					try {
						excelWriter.write(header, data, "结算订单列表", "结算订单列表");
					} catch (Exception e) {
						LogCvt.error("导出结算订单列表失败", e);
						isSuccess = false;
						break;
					}
				} else {
					list = null;
				}
				
			} while(list != null && list.size() >= pageSize);
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
			LogCvt.error("导出订单列表失败", e);
			rb.setResultCode(ResultCode.failed.getCode());
			rb.setResultDesc(ResultCode.failed.getMsg());
		}
		resultRes.setResultVo(rb);
		resultRes.setUrl(url);
		return resultRes;
	}
	
	/**
	 *结算导出
	 */
	private List<List<String>> convertExcel(List<BossSettlementVo> list, int index){
		//将数据放入List<List<String>> 
		List<List<String>> data = new ArrayList<List<String>>();
				
		if (list == null || list.size() == 0) {
			return data;
		}
		
		String createTime = null;
		
		for (BossSettlementVo vo : list) {
			List<String> record = new ArrayList<String>();
			record.add(String.valueOf((++index)));//序号
			if (Checker.isNotEmpty(vo.getOrderId())) {
				record.add(vo.getOrderId());//订单编号
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getSettlementId())) {
				record.add(vo.getSettlementId());//结算单号
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getMerchantName())) {
				record.add(vo.getMerchantName());//商户名称
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getProductCount())) {
				record.add(String.valueOf(vo.getProductCount()));//消费数量
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getMoney())) {
				record.add(String.valueOf(vo.getMoney()));//结算金额
			}else {
				record.add("--");
			}
			if (vo.getCreateTime() != 0) {
				createTime = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2, new Date(vo.getCreateTime()));
				record.add(createTime);//结算时间
			}else {
				record.add("--");
			}
			if (StringUtils.isEmpty(vo.getOutletId())) {
				record.add("--");
			} else if ("0".equals(vo.getOutletId())) {
				record.add("超级管理员结算");
			} else {
				if (StringUtils.isEmpty(vo.getOutletName())) {
					record.add("--");
				} else {
					record.add(vo.getOutletName());//结算门店
				}
			} 
			
			if (Checker.isNotEmpty(vo.getTickets()) && vo.getTickets().size() > 0) {
				StringBuffer ticket = new StringBuffer();
				int size = vo.getTickets().size();
				for (int i = 0; i < size; i++) {
					ticket.append(vo.getTickets().get(i) + ",");//List转换为String类型 ，中间用逗号相隔，
				}
					
				if (ticket.toString().endsWith(",")) {
					ticket.deleteCharAt(ticket.length()-1);//去掉字符串最后一个逗号
				}
				record.add(String.valueOf(ticket));//券码
			}else {
				record.add("--");
			}
			//状态
			if (Checker.isNotEmpty(vo.getSettleState())) {
				switch(Integer.valueOf(vo.getSettleState())) {
					case 0:record.add("未结算");
						break;
					case 1:record.add("结算中");
						break;
					case 2:record.add("结算成功");
						break;
					case 3:record.add("结算失败");
						break;
					case 4:record.add("无效结算记录");
						break;
					default:record.add("--");
				}
			}else {
				record.add("--");
			}
			if (Checker.isNotEmpty(vo.getBillNo())) {
				record.add(vo.getBillNo());//账单编号
			}else {
				record.add("--");
			}
			data.add(record);
		}
			
		return data;
	}
}
