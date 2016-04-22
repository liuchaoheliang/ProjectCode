package com.froad.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.froad.enums.ActiveType;
import com.froad.po.ActiveMainReportDetail;
import com.froad.po.RegisteredExportDetail;
import com.froad.po.VouchersUseDetails;

public class ActiveDownUtils {

	/**
	 * @Title: downSpecialProductHeader
	 * @Description: 获取下载红包券码的header
	 * @author: shenshaocheng 2015年11月30日
	 * @modify: shenshaocheng 2015年11月30日
	 * @return
	 */
	public List<String> downVoucherUseDetaiHeader() {
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("红包券码");
		header.add("红包金额");
		header.add("使用金额");
		header.add("订单编号");
		header.add("订单金额");
		header.add("支付时间");
		header.add("用户编号");
		header.add("电话号码");
		header.add("交易明细");
		header.add("所属客户端");
		header.add("支付账单号");
		header.add("退款金额");
		header.add("退款编号");
		return header;
	}

	/**
	 * @Title: downSpecialProductData
	 * @Description: 获取下载红包券码的data
	 * @author: shenshaocheng 2015年11月30日
	 * @modify: shenshaocheng 2015年11月30日
	 * @return
	 */
	public List<List<String>> downVoucherUseDetaiData(
			List<VouchersUseDetails> vouchersUseDetailsList) {
		List<List<String>> data = new ArrayList<List<String>>();
		if (vouchersUseDetailsList == null
				|| vouchersUseDetailsList.size() == 0) {
			List<String> record = new ArrayList<String>();
			// 序号
			record.add("");
			// 红包券码
			record.add("");
			// 红包金额
			record.add("");
			// 使用金额
			record.add("");
			// 订单编号
			record.add("");
			// 订单金额
			record.add("");
			//支付时间
			record.add("");
			// 用户编号
			record.add("");
			// 电话号码
			record.add("");
			// 交易明细
			record.add("");
			// 所属客户端
			record.add("");
			// 支付账单号
			record.add("");
			// 退款金额
			record.add("");
			// 退款编号
			record.add("");
			data.add(record);
		} else {
			// 序号
			int count = 0;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (VouchersUseDetails vouchersUseDetails : vouchersUseDetailsList) {
				List<String> record = new ArrayList<String>();
				// 序号
				record.add((++count) + "");
				// 红包券码
				record.add(vouchersUseDetails.getVouchersId());
				// 红包金额
				double vouchersMoney = (double)vouchersUseDetails.getVouchersMoney()/1000;
				record.add(vouchersMoney + "");
				// 使用金额
				//double normalPriceMarket = vouchersUseDetails.getNormalPriceMarket()/1000;
				//double vipPriceMarket = vouchersUseDetails.getVipPriceMarket()/1000;
				record.add(vouchersMoney + "");
				// 订单编号
				record.add(vouchersUseDetails.getOrderId());
				// 订单金额
				double orderMoney = (double)vouchersUseDetails.getOrderMoney()/1000;
				record.add(orderMoney + "");
				// 支付时间
				if(null != vouchersUseDetails.getPayTime()) {
					record.add(formatter.format(vouchersUseDetails.getPayTime()));
				}else {
					record.add(null);
				}
				// 用户编号
				record.add(vouchersUseDetails.getMemberCode().toString());
				// 电话号码
				record.add(vouchersUseDetails.getPhone());
				List<ActiveMainReportDetail> detailList = JSON.parseArray(
						vouchersUseDetails.getDetail(),
						ActiveMainReportDetail.class);
				StringBuffer sb = new StringBuffer();
				for (int j = 0; j < detailList.size(); j++) {				
					ActiveMainReportDetail detail = detailList.get(j);
					if(detail != null && detail.getActiveId() != null 
							&& detail.getActiveType().equals(ActiveType.vouchers.getCode())) {
						String name = detail.getProductName();
						Double money = detail.getGenDisMoney() == null ? 0 : detail
								.getGenDisMoney() / 1000;// 优惠的总价
						Double price = detail.getGenPrice() == null ? 0 : detail
								.getGenPrice() / 1000;// 单价
						Integer quantity = detail.getGenDisCount() == null ? 0
								: detail.getGenDisCount();// 普通价格的数量
						Double vipMoney = detail.getVipDisMoney() == null ? 0 : detail
								.getVipDisMoney() / 1000;// VIP优惠的总价
						Double vipPrice = detail.getVipPrice() == null ? 0 : detail
								.getVipPrice() / 1000;// VIP单价
						Integer vipQuantity = detail.getVipDisCount() == null ? 0
								: detail.getVipDisCount();// VIP数量
						Double realAmount = price * quantity - money;
						BigDecimal b = new BigDecimal(realAmount);  
						realAmount = b.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();  
						Double realVipAmount = vipPrice * vipQuantity - vipMoney;
						sb.append(name + ":" + "(普通单价: " + price + " 元 普通数量 "
								+ detail.getGenDisCount() + " 个,实际成交总价: " + realAmount
								+ " 元 ; VIP单价: " + vipPrice + " 元 VIP数量 "
								+ detail.getVipDisCount() + "个,实际VIP成交总价: "
								+ realVipAmount + " 元)");
						if (j != detailList.size() - 1) {
							sb.append(", ");
						}
					}					
				}
				// 交易明细
				record.add(sb.toString());
				// 客户端
				record.add(vouchersUseDetails.getClientId());
				// 支付账单号
				record.add(vouchersUseDetails.getPayBillNO());
				// 退款金额
				record.add(vouchersUseDetails.getReturnMoney());
				// 退款编号
				record.add(vouchersUseDetails.getReturnBillNO());
				data.add(record);
			}
		}

		return data;
	}
	
	/**
	 * @Title: downSpecialProductHeader
	 * @Description: 获取下载注册活动（红包）的header
	 * @author: shenshaocheng 2015年11月30日
	 * @modify: shenshaocheng 2015年11月30日
	 * @return
	 */
	public List<String> downRegisteredDetaiHeader() {
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("用户编号");
		header.add("电话号码");
		header.add("注册时间");
		header.add("返现内容");
		header.add("所属客户端");
		return header;
	}
	
	/**
	 * @Title: downSpecialProductData
	 * @Description: 获取下载红包券码的data
	 * @author: shenshaocheng 2015年11月30日
	 * @modify: shenshaocheng 2015年11月30日
	 * @return
	 */
	public List<List<String>> downRegisteredDetaiData(
			List<RegisteredExportDetail> registeredExportDetailsList) {
		List<List<String>> data = new ArrayList<List<String>>();
		if (registeredExportDetailsList == null
				|| registeredExportDetailsList.size() == 0) {
			List<String> record = new ArrayList<String>();
			// 序号
			record.add("");
			// 用户编号
			record.add("");
			// 电话号码
			record.add("");
			// 注册时间
			record.add("");
			// 返现内容
			record.add("");
			// 所属客户端
			record.add("");
			data.add(record);
		} else {
			// 序号
			int count = 0;
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
			for (RegisteredExportDetail registeredExportDetail : registeredExportDetailsList) {
				List<String> record = new ArrayList<String>();
				// 序号
				record.add(++count + "");
				// 用户编号
				record.add(registeredExportDetail.getMenberCode().toString());
				// 电话号码
				record.add(registeredExportDetail.getPhone());
				// 注册时间
				record.add(formatter.format(registeredExportDetail.getRegisteredTime()));
				// 返现内容
				record.add(registeredExportDetail.getActiveName());
				// 所属客户端
				record.add(registeredExportDetail.getClientName());

				data.add(record);
			}
		}

		return data;
	}
}
