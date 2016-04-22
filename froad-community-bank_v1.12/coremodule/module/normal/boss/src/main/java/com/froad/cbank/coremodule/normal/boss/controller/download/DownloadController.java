package com.froad.cbank.coremodule.normal.boss.controller.download;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.csvreader.CsvWriter;
import com.froad.cbank.coremodule.framework.common.util.excel.JxlsExcelUtil;
import com.froad.cbank.coremodule.framework.common.util.type.ArrayUtil;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.annotation.ImpExp;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.enums.GivePointState;
import com.froad.cbank.coremodule.normal.boss.enums.OrderStatus;
import com.froad.cbank.coremodule.normal.boss.enums.PayMentMethodEnum;
import com.froad.cbank.coremodule.normal.boss.enums.RefundState;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliverGoodsReq;
import com.froad.cbank.coremodule.normal.boss.pojo.delivery.DeliverGoodsRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductDetailReq;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductDetailRes;
import com.froad.cbank.coremodule.normal.boss.pojo.product.ActivityProductPointsRes;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipListReq;
import com.froad.cbank.coremodule.normal.boss.pojo.vip.VipRes;
import com.froad.cbank.coremodule.normal.boss.support.LoginBossSupport;
import com.froad.cbank.coremodule.normal.boss.support.delivery.DeliverGoodsSupport;
import com.froad.cbank.coremodule.normal.boss.support.product.ActivityProductSupport;
import com.froad.cbank.coremodule.normal.boss.support.vip.VipImpSupport;
import com.froad.cbank.coremodule.normal.boss.support.vip.VipSupport;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.DateUtil;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.FileUtil;
import com.pay.user.dto.VipBatchDto;


/**
 * 下载
 * @ClassName DownloadController
 * @author zxl
 * @date 2015年6月15日 下午4:39:13
 */
@Controller
@RequestMapping("/download")
public class DownloadController {
	
	@Resource
	DeliverGoodsSupport deliverGoodsSupport;
	
	@Resource
	LoginBossSupport loginBossSupport;
	
	@Resource
	ActivityProductSupport activityProductSupport;
	
	@Resource
	private VipSupport vipSupport;
	
	@Resource
	private VipImpSupport vipImpSupport;
	
	/**
	 * 发货下载
	 * @tilte list
	 * @author zxl
	 * @date 2015年6月15日 下午3:53:21
	 * @param model
	 * @param request
	 * @param req
	 */
	@SuppressWarnings("unchecked")
	@Auth(keys={"boss_presell_de_export"})
	@ImpExp
	@RequestMapping(value="deliver",method = RequestMethod.GET)
	public void deliver(HttpServletRequest request,HttpServletResponse response, DeliverGoodsReq req) {
		OutputStream os = null;
		int pageSize = 200;
		try {
			os = response.getOutputStream();
			
			String name="发货信息-"+System.currentTimeMillis()+".xls";
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(name,"UTF-8"));
			response.setContentType("application/ms-excel");
			
			List<DeliverGoodsRes> list = new ArrayList<DeliverGoodsRes>();
			
			req.setPageSize(pageSize);
			req.setPageNumber(1);
			HashMap<String, Object> map = deliverGoodsSupport.list(req);
			Page page = (Page)map.get("page");
			list = (List<DeliverGoodsRes>)map.get("list");
			LogCvt.info("totalCount:"+page.getTotalCount());
			if(page.getTotalCount()>pageSize){
				int s=(page.getTotalCount())/pageSize; //需要再查询次数
				if(page.getTotalCount()%pageSize==0){ //200倍数减1
					s--;
				}
				int pagenum=1;
				for(int i=1;i<=s;i++){
					req.setPageSize(pageSize);
					req.setPageNumber(++pagenum);
					req.setFirstRecordTime(page.getFirstRecordTime());
					req.setLastPageNumber(page.getLastPageNumber());
					req.setLastRecordTime(page.getLastRecordTime());
					HashMap<String, Object> map1 = deliverGoodsSupport.list(req);
					list.addAll((List<DeliverGoodsRes>)map1.get("list"));
					page=(Page)map1.get("page");
				}
			}
			
			HashMap<String,Object> bean = new HashMap<String,Object>();
			bean.put("data", list);
			
			os.write(JxlsExcelUtil.export(new File(this.getClass().getResource("/excel/deliver-goods.xls").toURI()), bean).toByteArray());
			
		}catch (BossException e) {
			HashMap<String,Object> h = new HashMap<String, Object>();
			h.put("code", e.getCode());
			h.put("message", e.getMessage());
			try {
				os.write(JSON.toJSONString(h).getBytes());
			} catch (IOException e1) {
			}
			LogCvt.error(e.getMessage(), e);
		}catch (Exception e) {
			HashMap<String,Object> h = new HashMap<String, Object>();
			h.put("code", ErrorEnums.syserr.getCode());
			h.put("message", ErrorEnums.syserr.getMsg());
			try {
				os.write(JSON.toJSONString(h).getBytes());
			} catch (IOException e1) {
			}
			LogCvt.error(e.getMessage(), e);
		}finally{
			try {
				os.flush();
				os.close();
			} catch (Exception e) {
				LogCvt.error(e.getMessage(), e);
			}
		}
	}
	
	/**
	 * 商品送积分明细导出
	 * @tilte points
	 * @author zxl
	 * @date 2015年6月17日 上午9:39:07
	 * @param request
	 * @param response
	 * @param req
	 */
	@SuppressWarnings("unchecked")
	@Auth(keys={"boss_actitvity_p_dl_export"})
	@ImpExp
	@RequestMapping(value="points",method = RequestMethod.GET)
	public void points(HttpServletRequest request,HttpServletResponse response, ActivityProductDetailReq req) {
		OutputStream os = null;
		int pageSize = 200;
		try {
			os = response.getOutputStream();
			
			String name="商品送积分明细导出-"+com.froad.cbank.coremodule.framework.common.util.DateUtil.getCurDate()+".csv";
			response.setCharacterEncoding("UTF-8");
			response.setHeader("Content-Disposition", "attachment; filename="+URLEncoder.encode(name,"UTF-8"));
			response.setContentType("application/csv");
			
			List<ActivityProductDetailRes> list = new ArrayList<ActivityProductDetailRes>();
			
			req.setPageSize(pageSize);
			req.setPageNumber(1);
			HashMap<String, Object> map = activityProductSupport.detail(req);
			Page page = (Page)map.get("page");
			list = (List<ActivityProductDetailRes>)map.get("list");
			LogCvt.info("totalCount:"+page.getTotalCount());
			if(page.getTotalCount()>pageSize){
				int s=(page.getTotalCount())/pageSize; //需要再查询次数
				if(page.getTotalCount()%pageSize==0){ //200倍数减1
					s--;
				}
				int pagenum=1;
				for(int i=1;i<=s;i++){
					req.setPageSize(pageSize);
					req.setPageNumber(++pagenum);
					req.setFirstRecordTime(page.getFirstRecordTime());
					req.setLastPageNumber(page.getLastPageNumber());
					req.setLastRecordTime(page.getLastRecordTime());
					HashMap<String, Object> map1 = activityProductSupport.detail(req);
					list.addAll((List<ActivityProductDetailRes>)map1.get("list"));
					page=(Page)map1.get("page");
				}
			}
			CsvWriter csv = new CsvWriter(os,',',Charset.forName("GBK"));
			String[] head = new String[]{"订单编号","子订单","所属客户端","商品ID","商品名称","支付方式","订单状态","订单子状态"
					,"下单时间","订单金额","送积分值","送分状态","送分时间"};
			csv.writeRecord(head);
			for(ActivityProductDetailRes r : list){
				
				for(ActivityProductPointsRes p : r.getProduct()){
					String[] s = new String[]{
							r.getOrderId(),
							r.getSubOrderId(),
							r.getClientId(),
							p.getProductId(),
							p.getProductName(),
							PayMentMethodEnum.getDescriptionByCode(r.getPaymentMethod()),
							OrderStatus.getDescriptionByCode(r.getOrderStatus()),
							RefundState.getDescriptionByCode(r.getRefundState()),
							r.getCreateTime(),
							r.getTotalPrice()+"",
							p.getGivePoints()+"",
							GivePointState.getDescriptionByCode(p.getGiveState()),
							p.getPaymentTime()
					};
					csv.writeRecord(s);
				}
				
			}
			csv.flush();
			csv.close();
			
		}catch (BossException e) {
			HashMap<String,Object> h = new HashMap<String, Object>();
			h.put("code", e.getCode());
			h.put("message", e.getMessage());
			try {
				os.write(JSON.toJSONString(h).getBytes());
			} catch (IOException e1) {
			}
			LogCvt.error(e.getMessage(), e);
		}catch (Exception e) {
			HashMap<String,Object> h = new HashMap<String, Object>();
			h.put("code", ErrorEnums.syserr.getCode());
			h.put("message", ErrorEnums.syserr.getMsg());
			try {
				os.write(JSON.toJSONString(h).getBytes());
			} catch (IOException e1) {
			}
			LogCvt.error(e.getMessage(), e);
		}finally{
			try {
				os.flush();
				os.close();
			} catch (Exception e) {
				LogCvt.error(e.getMessage(), e);
			}
		}
	}
	

	/**
	 * 下载批次信息
	 * @path /download/vipimp
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月29日 下午8:01:59
	 * @param model
	 * @throws IOException 
	 */
	@Auth(keys={"boss_vipimp_export","boss_vipimp_audit_export"})
	@ImpExp
	@RequestMapping(value = "/vipimp", method = RequestMethod.GET)
	public void download(HttpServletRequest req, HttpServletResponse res, Long batchCode, Long userId, String token) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream os = null;
		try {
			LogCvt.info("下载批次文件");
			res.setCharacterEncoding("UTF-8");
			if(batchCode == null) {
				throw new BossException("批次ID为空");
			}
			VipBatchDto dto = vipImpSupport.download(batchCode);
			
			if(dto != null && !ArrayUtil.empty(dto.getMemberNames())) {
				os = res.getOutputStream();
				//设置返回类型
				res.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(dto.getFileName(), "UTF-8"));
				res.setContentType("application/csv");
				//生成CSV文件
				List<String> list = dto.getMemberNames();
				CsvWriter csv = new CsvWriter(os, ',', Charset.forName("GBK"));
				for(String tmp : list) {
					String[] userName = new String[]{tmp};
					csv.writeRecord(userName);
				}
				csv.flush();
				csv.close();
			}
		} catch (BossException e) {
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			LogCvt.error(e.getMessage(), e);
			res.setContentType("text/html");
			res.getWriter().write(JSONArray.toJSONString(map));
		} catch (Exception e) {
			map.put("code", ErrorEnums.thrift_err.getCode());
			map.put("message", ErrorEnums.thrift_err.getMsg());
			LogCvt.error(e.getMessage(), e);
			res.setContentType("text/html");
			res.getWriter().write(JSONArray.toJSONString(map));
		} finally {
			if(os != null) {
				os.flush();
				os.close();
			}
		}
	}

	/**
	 * 下载导入失败结果文件
	 * @path /download/vipimpfail
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月30日 下午5:09:38
	 * @param model
	 */
	@ImpExp
	@RequestMapping(value = "/vipimpfail", method = RequestMethod.GET)
	public void downloadFail(ModelMap model, HttpServletRequest req, HttpServletResponse res, String fileName, Long userId, String token) throws IOException {
		Map<String, Object> map = new HashMap<String, Object>();
		OutputStream os = null;
		try {
			LogCvt.info("下载导入失败结果文件");
			res.setCharacterEncoding("UTF-8");
			//获取操作用户
			if(StringUtil.isBlank(fileName)) {
				throw new BossException("请传入要下载的文件名");
			}
			
			//下载文件
			FileUtil.downloadFile(Constants.getScpFileUploadPath(), fileName, Constants.getImgLocalUrl(), Constants.SCPCONFIG);
			
			String filePath = Constants.getImgLocalUrl();
			File file = new File(filePath + File.separator + fileName);
			if(!file.exists()) {
				throw new BossException("文件不存在");
			}
			os = res.getOutputStream();
			//设置返回类型
			res.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF-8"));
			res.setContentType("application/csv");
			//读取CSV文件流并输出
			FileInputStream in = new FileInputStream(file);
			byte[] buf = new byte[20 * 1024];
			int bytes = 0;
			while((bytes = in.read(buf, 0, buf.length)) != -1) {
				if(bytes < buf.length) {
					for(int i = 0; i < bytes; i++) {
						os.write(buf[i]);
					}
				} else {
					os.write(buf);
				}
			}
			in.close();
			file.delete();
			
		} catch (BossException e) {
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			LogCvt.error(e.getMessage(), e);
			res.setContentType("text/html");
			res.getWriter().write(JSONArray.toJSONString(map));
		} catch (Exception e) {
			map.put("code", ErrorEnums.thrift_err.getCode());
			map.put("message", ErrorEnums.thrift_err.getMsg());
			LogCvt.error(e.getMessage(), e);
			res.setContentType("text/html");
			res.getWriter().write(JSONArray.toJSONString(map));
		} finally {
			if(os != null) {
				os.flush();
				os.close();
			}
		}
	}
	

	/**
	 * @desc 导出会员明细列表
	 * @path /download/vipdetail
	 * @author 陆万全 luwanquan@f-road.com.cn
	 * @date 创建时间：2015年6月17日 上午11:54:29
	 * @param 
	 * @return 
	 * @throws IOException 
	 */
	@SuppressWarnings("unchecked")
	@Auth(keys={"boss_vip_detail_export"})
	@ImpExp
	@RequestMapping(value = "/vipdetail", method = RequestMethod.GET)
	public void exportVipList(HttpServletRequest req, HttpServletResponse res, VipListReq pojo) {
		HashMap<String, Object> h = new HashMap<String, Object>();
		OutputStream os = null;
		int pageSize = 200;
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("VIP会员明细导出请求参数：" + JSON.toJSONString(pojo));
			}
			res.setCharacterEncoding("UTF-8");
			os = res.getOutputStream();
			
			String name = "VIP会员明细导出" + DateUtil.formatDate(new Date(), DateUtil.DATE_TIME_FORMAT_03) + ".csv";
			res.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(name, "UTF-8"));
			res.setContentType("application/csv");
			
			Map<String, Object> map = null;
			List<VipRes> list = new ArrayList<VipRes>();
			pojo.setPageSize(pageSize);
			int pageNumber = 1;
			for(int i = 0; i < pageNumber; i++) {
				pojo.setPageNumber(pageNumber);
				map = vipSupport.getVipList(pojo);
				List<VipRes> data = (List<VipRes>) map.get("vipList");
				Page page = (Page) map.get("page");
				if(page != null && page.getPageCount() > pageNumber) {
					pageNumber++;
				}
				if(!ArrayUtil.empty(data)) {
					list.addAll(data);
				}
			}
			
			CsvWriter csv = new CsvWriter(os, ',', Charset.forName("GBK"));
			String[] head = new String[]{"会员ID", "会员名", "绑定手机", "创建渠道", "会员状态", "注册时间", 
					"最近登录时间", "最近登录IP", "VIP会员开通日期", "VIP会员到期日期", "VIP会员标签", "VIP会员状态", 
					"VIP会员等级", "VIP创建方式", "VIP银行渠道"};
			csv.writeRecord(head);
			for(VipRes tmp : list) {
				String status = null;
				if(tmp.getStatus() != null) {
					status = tmp.getStatus() == 1 ? "已激活" : tmp.getStatus() == 2 ? "未激活" : "已冻结";
				}
				String[] str = new String[]{
						tmp.getMemberCode() + "",
						StringUtil.isNotBlank(tmp.getLoginId()) ? tmp.getLoginId() : "-",
						StringUtil.isNotBlank(tmp.getMobile()) ? tmp.getMobile() : "-",
						StringUtil.isNotBlank(tmp.getMemberCreateChannel()) ? tmp.getMemberCreateChannel() : "-",
						status,
						tmp.getRegisterTime() == null ? "-" : DateUtil.longToString(tmp.getRegisterTime()),
						tmp.getLastLoginTime() == null ? "-" : DateUtil.longToString(tmp.getLastLoginTime()),
						StringUtil.isNotBlank(tmp.getLastLoginIp()) ? tmp.getLastLoginIp() : "-",
						DateUtil.formatDate(DateUtil.longToDate(tmp.getVipActivationTime()), false),
						DateUtil.formatDate(DateUtil.longToDate(tmp.getVipExpirationTime()), false),
						StringUtil.isNotBlank(tmp.getBankLabelName()) ? tmp.getBankLabelName() : "-",
						tmp.getVipStatus(),
						tmp.getVipLevel(),
						StringUtil.isNotBlank(tmp.getClientChannel()) ? tmp.getClientChannel() : "-",
						StringUtil.isNotBlank(tmp.getBankOrgNo()) ? tmp.getBankOrgNo() : "-"
				};
				csv.writeRecord(str);
			}
			csv.flush();
			csv.close();
		} catch (BossException e) {
			h.put("code", e.getCode());
			h.put("message", e.getMessage());
			res.setContentType("text/html");
			try {
				os.write(JSONArray.toJSONString(h).getBytes());
			} catch (IOException e1) {
			}
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			h.put("code", ErrorEnums.syserr.getCode());
			h.put("message", ErrorEnums.syserr.getMsg());
			res.setContentType("text/html");
			try {
				os.write(JSONArray.toJSONString(h).getBytes());
			} catch (IOException e1) {
			}
			LogCvt.error(e.getMessage(), e);
		} finally{
			try {
				if(os != null) {
					os.flush();
					os.close();
				}
			} catch (IOException e) {
				LogCvt.error(e.getMessage(), e);
			}
		}
	}
}
