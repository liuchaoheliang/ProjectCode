package com.froad.handler.impl;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.alibaba.fastjson.JSON;
import com.froad.db.mysql.MyBatisManager;
import com.froad.db.mysql.bean.Page;
import com.froad.db.mysql.mapper.ActiveMainReportMapper;
import com.froad.enums.ActiveIdCode;
import com.froad.enums.ActiveType;
import com.froad.exceptions.FroadBusinessException;
import com.froad.handler.ActiveAttachReportHandler;
import com.froad.handler.ActiveMainReportHandler;
import com.froad.logback.LogCvt;
import com.froad.po.ActiveMainReport;
import com.froad.po.ActiveMainReportDetail;
import com.froad.util.DateUtil;
import com.froad.util.ExcelWriter;

public class ActiveMainReportHandlerImpl implements ActiveMainReportHandler {

	private ActiveAttachReportHandler activeAttachReportHandler = new ActiveAttachReportHandlerImpl();

	@Override
	public String getActiveMainReportURL(String activeId, String clientId,
			String type, String activeType) {

		List<ActiveMainReport> result = null;
		SqlSession sqlSession = null;
		ActiveMainReportMapper activeMainReportMapper = null;
		String url = null;
		try {
			sqlSession = MyBatisManager.getSqlSessionFactory().openSession();
			activeMainReportMapper = sqlSession
					.getMapper(ActiveMainReportMapper.class);
			Page<ActiveMainReport> mainPage = new Page<ActiveMainReport>();
			result = activeMainReportMapper.getActiveMainReport(activeId,
					clientId, type);
			mainPage.setResultsContent(result);
			ExcelWriter excelWriter = new ExcelWriter(mainPage.getPageSize());
			url = generateExcel(mainPage, excelWriter, type, activeId, clientId, activeType);

		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			try {
				throw e;
			} catch (Exception e1) {
				LogCvt.error(e.getMessage(), e);
			}
		} finally {
			if (null != sqlSession)
				sqlSession.close();
		}
		return url;
	}

	private String generateExcel(Page<ActiveMainReport> page,
			ExcelWriter excelWriter, String type, String activeId,
			String clientId, String activeType) throws IOException, FroadBusinessException {
		String sheetName = "满减活动主表"; // sheetName
		if (activeType.equals(ActiveIdCode.ZC.getCode())) {
			sheetName = "注册活动主表";
		}
		
		String[] header = null;

		header = new String[] { "序号", "订单编号", "订单金额", "支付时间", "返现内容", "用户编号",
				"电话号码", "所属客户端", "交易明细" };
		System.out.println(" head : " + header.length);
		List<ActiveMainReport> mainList = (List<ActiveMainReport>) page
				.getResultsContent();

		int count = null == mainList ? 0 : mainList.size();
		List<List<String>> data = null; // 表数据

		data = new ArrayList<List<String>>();
		// {"序号, 订单编号, 订单金额, 支付时间, 返现内容, 用户编号, 电话号码, 交易明细, 所属客户端\r\n"};
		// String test =
		// "[{\"product_name\":\"立白洗衣液\",\"money\":\"10\",\"vip_money\":\"20\",\"quantity\":\"10\",\"vip_quantity\":\"20\",\"vip_price\":\"50\",\"price\":\"50\",\"vip_quantity\":\"20\",\"quantity\":\"20\",\"product_id\":\"001\"},{\"product_name\":\"蓝月亮洗衣液\",\"money\":\"10\",\"vip_money\":\"20\",\"quantity\":\"10\",\"vip_quantity\":\"20\",\"vip_price\":\"50\",\"price\":\"50\",\"vip_quantity\":\"20\",\"quantity\":\"20\",\"product_id\":\"002\"}]";
		for (int i = 0; i < count; i++) {
			ActiveMainReport activeMainReport = mainList.get(i);
			if (null == activeMainReport)
				continue;
			String[] row = new String[header.length]; // 行数据
			row[0] = (i + 1) + "";
			row[1] = activeMainReport.getOrderId();
			row[2] = ((double)activeMainReport.getOrderMoney())/1000
					+ " 元";
			if (activeMainReport.getPayTime() != null) {
				row[3] = DateUtil.formatDateTime(DateUtil.DATE_TIME_FORMAT2,
						new Date(activeMainReport.getPayTime().getTime()));
			} else {
				row[3] = null;
			}
			row[4] = ActiveType.fullCut.getCode().equals(
					activeMainReport.getOrderMarketType()) ? "满减 "
					+ " 普通优惠: "+ ((double)activeMainReport.getPriceMarket())/1000 +" 元, VIP优惠: " +((double)activeMainReport.getVipPriceMarket())/1000 +" 元" : "非满减"
					+" 普通优惠: "+((double)activeMainReport.getPriceMarket())/1000 + " 元, VIP优惠: "+ ((double)activeMainReport.getVipPriceMarket())/1000 +" 元";
			row[5] = activeMainReport.getMemberCode();
			row[6] = activeMainReport.getPhone();
			// row[7] = activeMainReport.getClientId();
			row[7] = activeMainReport.getClientName();
			List<ActiveMainReportDetail> detailList = JSON.parseArray(
					activeMainReport.getDetail(), ActiveMainReportDetail.class);
			StringBuffer sb = new StringBuffer();
			for (int j = 0; j < detailList.size(); j++) {
				ActiveMainReportDetail detail = detailList.get(j);

				if(detail != null && detail.getActiveId() != null 
						&& detail.getActiveType().equals(ActiveType.fullCut.getCode())) {
					String name = detail.getProductName();
					Double money = detail.getGenDisMoney() == null ? 0 : detail
							.getGenDisMoney()/1000;// 优惠的总价
					Double price = detail.getGenPrice() == null ? 0 : detail
							.getGenPrice()/1000;// 单价
					Integer quantity = detail.getGenDisCount() == null ? 0 : detail
							.getGenDisCount();// 普通价格的数量
					Double vipMoney = detail.getVipDisMoney() == null ? 0 : detail
							.getVipDisMoney()/1000;// VIP优惠的总价
					Double vipPrice = detail.getVipPrice() == null ? 0 : detail
							.getVipPrice()/1000;// VIP单价
					Integer vipQuantity = detail.getVipDisCount() == null ? 0
							: detail.getVipDisCount();// VIP单价
					Double realAmount = price * quantity - money;
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
			row[8] = sb.toString();
			LogCvt.debug("ROW:" + ArrayUtils.toString(row));
			data.add(Arrays.asList(row));
		}
		// 客户端编号_ MJ主表_yyyy-MM-dd

		String date = DateUtil
				.formatDateTime(DateUtil.DATE_FORMAT1, new Date());
		
		String excelName = clientId + "_" + "MJ主表_" + date;
		excelWriter.writeCsv(Arrays.asList(header), data, sheetName);

		String dest = excelWriter.getLocalFileDir();
		String mainFile = dest + "/" + excelWriter.getCurrentExcelName();
		String zipName = clientId + "_" + activeType + "_"
				+ DateUtil.formatDateTime(DateUtil.DATE_FORMAT1, new Date());
		String zip = null;
		if (type.equals("1")) {// 满减需要生成附表
			String attachFile = generateAttachReport(activeId, clientId, dest, activeType);

			String[] files = { mainFile, attachFile };
			zip = writeZip(files, dest, zipName);
		} else {
			String[] files = { mainFile };
			zip = writeZip(files, dest, zipName);
		}
		String url = excelWriter != null ? excelWriter.getUrl(zip) : "";

		return url;
	}

	private String generateAttachReport(String activeId, String clientId,
			String dest, String activeType) {
		return activeAttachReportHandler.generateActiveAttachReport(activeId,
				clientId, dest, activeType);
	}

	private String writeZip(String[] files, String dest, String zipName)
			throws IOException {

		String zip = dest + "/" + zipName + ".zip";
		OutputStream os = new BufferedOutputStream(new FileOutputStream(zip));
		ZipOutputStream zos = new ZipOutputStream(os);
		zos.setEncoding("UTF-8");
		byte[] buf = new byte[8192];
		int len;
		for (String filename : files) {
			File file = new File(filename);
			if (!file.isFile())
				continue;
			ZipEntry ze = new ZipEntry(file.getName());
			zos.putNextEntry(ze);
			BufferedInputStream bis = new BufferedInputStream(
					new FileInputStream(file));
			while ((len = bis.read(buf)) > 0) {
				zos.write(buf, 0, len);
			}
			zos.closeEntry();
		}
		zos.close();
		return zip;
	}

}
