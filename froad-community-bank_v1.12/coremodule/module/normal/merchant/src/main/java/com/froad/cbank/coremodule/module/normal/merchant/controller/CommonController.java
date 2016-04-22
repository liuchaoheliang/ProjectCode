package com.froad.cbank.coremodule.module.normal.merchant.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;

import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.exception.MerchantException;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.PageRes;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Deliery_Corp_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_PC_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Face_Group_PC_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Product_Qrcode_Req;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Ticket_Detail_Res;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.Query_Ticket_List_Req;
import com.froad.cbank.coremodule.module.normal.merchant.service.Common_Service;
import com.froad.cbank.coremodule.module.normal.merchant.service.Order_Service;
import com.froad.cbank.coremodule.module.normal.merchant.service.Ticket_Service;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.EnumTypes;
import com.froad.cbank.coremodule.module.normal.merchant.utils.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.PramasUtil;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;

/**
 * 
 * 通用接口
 * 
 * @ClassName CommonController
 * @author args
 * @date 20015年4月08日 下午1:51:13
 */
@Controller
@RequestMapping(value = "/common")
public class CommonController {
    @Resource
    Order_Service order_Service;
    
    @Resource
    Common_Service common_service;
    
    @Resource
	Ticket_Service ticket_Service;
    
	/**
	 * 将提货码导出excel进行 压缩
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "downCode", method = RequestMethod.GET)
	public void downCode(HttpServletResponse response, HttpServletRequest request,Query_Ticket_List_Req  req) throws IOException, ParseException {
		long begin = System.currentTimeMillis();
		String name="提货码列表-"+PramasUtil.DateFormat(System.currentTimeMillis(), "yyyyMMddHHmmss");
		try {	
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			MerchantUser user = common_service.verifyUser(request,req.getToken(),req.getUid());
			req.setMerchantUser(user);
			//暂时循环拿数据，每次拿200条
			List<Query_Ticket_Detail_Res> listC=new ArrayList<Query_Ticket_Detail_Res>();
			req.setPageSize(200);
			Map<String,Object> ob=ticket_Service.query_code_list(req);
			PageRes pageRes =(PageRes)ob.get("page");
			listC=(List<Query_Ticket_Detail_Res>)ob.get("codeList");
			if(pageRes!=null&&pageRes.getTotalCount()>200){
				int fo=(pageRes.getTotalCount()-200)%200>0?(pageRes.getTotalCount()-200)/200+1:(pageRes.getTotalCount()-200)/200;
				int pagesize=1;
				for(int i=0;i<fo;i++){
					req.setPageSize(200);
					req.setPageNumber(++pagesize);
					req.setFirstRecordTime(pageRes.getFirstRecordTime());
					req.setLastPageNumber(pageRes.getLastPageNumber());
					req.setLastRecordTime(pageRes.getLastRecordTime());
					Map<String,Object> bo=ticket_Service.query_code_list(req);
					listC.addAll((List<Query_Ticket_Detail_Res>)bo.get("codeList"));
				    pageRes=(PageRes)bo.get("page");
				}
			}
			HashMap<String, String> status_s = new HashMap<String, String>();
			status_s.put("0", "未结算");
			status_s.put("1", "结算中");
			status_s.put("2", "已结算");
			status_s.put("3", "结算失败");
			//需要导出的集合
			List<List<String>> data = new ArrayList<List<String>>();
			if (listC != null && listC.size() > 0) {
				int number=0;
				for (Query_Ticket_Detail_Res vo : listC) {
					List<String> data_1=new ArrayList<String>();
					data_1.add(String.valueOf(++number));
					data_1.add(vo.getOrderId());
					data_1.add(vo.getConsumeTime()>0?PramasUtil.DateFormat(vo.getConsumeTime(),null):"");
					data_1.add(vo.getTicketId());
					data_1.add(vo.getPrice());
					data_1.add(vo.getProductName());
					data_1.add(vo.getOutletName());
					data_1.add(vo.getMemberName()+":"+vo.getMobile());
					data_1.add(status_s.get(vo.getSettleState()));
					data_1.add(vo.getMerchantUserName());
                    data.add(data_1);
				}
				
				Monitor.send(MonitorEnums.merchant_ticket_download, String.valueOf(System.currentTimeMillis()-begin));
				//导出的标题
				List<String> header = new ArrayList<String>();
				header.add("序号");
				header.add("订单编号");
				header.add("提货时间");
				header.add("提货码");
				header.add("金额");
				header.add("商品名称");
				header.add("消费门店");
				header.add("提货人信息");
				header.add("结算状态");
				header.add("操作人");
				
				//生成excel
				HSSFWorkbook book = ExcelUtil.generate(name,name,header,data, null);
				response.setContentType("application/ms-excel;charset=UTF-8");
				response.setHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("GBK"),"ISO-8859-1")+".xls");
				OutputStream os = response.getOutputStream();
				book.write(os);
				os.flush();
				os.close();
			}
		} catch (MerchantException e) {
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
	}

	
	
	/**
	 * 将面对面导出excel进行 压缩
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ParseException 
	 */

	@SuppressWarnings({ "unchecked"})
	@RequestMapping(value = "downQcode", method = RequestMethod.GET)
	public void downQcode(HttpServletRequest request,HttpServletResponse response,Query_Face_Group_PC_Req req) throws IOException, ParseException {
		long begin = System.currentTimeMillis();
		HashMap<String, String> type_s = new HashMap<String, String>();
		type_s.put("1", "团购");
		type_s.put("0", "面对面");
		type_s.put("3", "名优特惠");
		HashMap<String, String> status_s = new HashMap<String, String>();
		status_s.put("1", "等待付款");
		status_s.put("2", "已取消");
		status_s.put("3", "已关闭");
		status_s.put("4", "支付中");
		status_s.put("5", "订单支付失败");
		status_s.put("6", "交易完成");

		response.reset();
		String name=type_s.get(req.getType())+"订单列表查询-"+PramasUtil.DateFormat(System.currentTimeMillis(), "yyyyMMddHHmmss");
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			MerchantUser user = common_service.verifyUser(request,req.getToken(),req.getUid());
			req.setMerchantUser(user);
			//暂时循环拿数据，每次拿200条
			List<Query_Face_Group_PC_Res> listC=new ArrayList<Query_Face_Group_PC_Res>();
			int size=200;
			if(req.getType().equals("0")){
				size=20;
			}
			req.setPageSize(size);
			HashMap<String,Object> ob=order_Service.query_order_group_down(req);
			PageRes pageRes =(PageRes)ob.get("page");
			listC=(List<Query_Face_Group_PC_Res>)ob.get("orderList");
			if(pageRes!=null&&pageRes.getTotalCount()>size){
				int fo=(pageRes.getTotalCount()-size)/size;
				fo=(pageRes.getTotalCount()-size)%size>0?(fo+1):fo;
				int pagesize=1;
				for(int i=0;i<fo;i++){
					req.setPageSize(size);
					req.setPageNumber(++pagesize);
					req.setFirstRecordTime(pageRes.getFirstRecordTime());
					req.setLastPageNumber(pageRes.getLastPageNumber());
					req.setLastRecordTime(pageRes.getLastRecordTime());
					HashMap<String,Object> bo=order_Service.query_order_group_down(req);
					listC.addAll((List<Query_Face_Group_PC_Res>)bo.get("orderList"));
				    pageRes=(PageRes)bo.get("page");
				}
			}
			//需要导出的集合
			List<List<String>> data = new ArrayList<List<String>>();
			if (listC != null && listC.size() > 0) {
				String status="--",type_="--";
				int number=0;
				for (Query_Face_Group_PC_Res vo : listC) {
	                 status=status_s.get(vo.getOrderStatus());
	                 type_=type_s.get(vo.getType());
	                 List<String> data_1=new ArrayList<String>();
	                 data_1.add(String.valueOf(++number));
	                 data_1.add(StringUtil.isNotEmpty(vo.getOrderId())?vo.getOrderId():"--");
	                 data_1.add(PramasUtil.DateFormat(vo.getCreateTime(),null));
	                 data_1.add(String.valueOf(vo.getSubTotalMoney()));
	                 data_1.add(StringUtil.isNotEmpty(vo.getProductName())?vo.getProductName():"--");
	                 data_1.add(type_);
	                 data_1.add(status);
	                 data_1.add(StringUtil.isNotEmpty(vo.getSettleState())?vo.getSettleState():"--");
	                 String per=StringUtil.isNotEmpty(vo.getConsignee())?vo.getConsignee():"--";
	                 String phon=StringUtil.isNotEmpty(vo.getPhone())?vo.getPhone():"--";
	                 
	                 if("3,1".indexOf(req.getType())!=-1)data_1.add(per+":"+phon);
	                 else if(req.getType().equals("0")) data_1.add(vo.getUserName());
	                 if(req.getType().equals("3")){		
					     data_1.add(StringUtil.isNotEmpty(vo.getAddress())?vo.getAddress():"--");
	    			 }

	                 data.add(data_1);
				}
				
			}
			Monitor.send(MonitorEnums.merchant_order_download, String.valueOf(System.currentTimeMillis()-begin));
			//导出的标题
			List<String> header = new ArrayList<String>();
			header.add("序号");
			header.add("订单号");
			header.add("提交日期");
			header.add("总金额");
			header.add("商品名称");
			header.add("业务类型");
			header.add("订单状态");
			header.add("结算状态");
			if(req.getType().equals("1")){//团购
				header.add("提货人信息");
			}else if(req.getType().equals("3")){//名优特惠
				header.add("收货人信息");
				header.add("收货地址");
			}else if(req.getType().equals("0")){//面对面
				header.add("操作人");
			}
			//生成excel
			HSSFWorkbook book = ExcelUtil.generate(name,name,header,data, null);
			response.setContentType("application/ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="+new String(name.getBytes("GBK"),"ISO-8859-1")+".xls");
			OutputStream os = response.getOutputStream();
			book.write(os);
			os.flush();
			os.close();		
		} catch (MerchantException e) {
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
		}
	}

	
	/**
	 * 物流列表
	 * @tilte getDeliveryCorp
	 * @author zxl
	 * @date 20015年4月8日 下午4:55:51
	 * @param model
	 */
	
	@CheckPermission(keys={"merchant_product_famousproductlist_menu"})
	@RequestMapping(value = "delive", method = RequestMethod.POST)
	public void getDeliveryCorp(ModelMap model,HttpServletRequest request,@RequestBody Query_Deliery_Corp_Req req){
		try {
			req.setClientId(request.getAttribute(Constants.CLIENT_ID).toString());
			req.setMerchantUser((MerchantUser)request.getAttribute(Constants.MERCHANT_USER));
			model.putAll(common_service.getDeliveryCorp(req));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 银行列表级联
	 * @tilte getBankList
	 * @author zxl
	 * @date 20015年4月22日 下午3:58:33
	 * @param model
	 * @param request
	 * @param bankcode
	 */
	@CheckPermission(keys={"merchant_outlet_outletlist_menu"})
	@RequestMapping(value = "bank", method = RequestMethod.GET)
	public void getBankList(ModelMap model,HttpServletRequest request,String orgCode){
		try {
			model.putAll(common_service.getBankList(orgCode,request.getAttribute(Constants.CLIENT_ID)+""));
		} catch (MerchantException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}
	
	/**
	 * 
	 * producnt_qrcodeDown:(商品二维码图片下载).
	 *
	 * @author wm
	 * 2015年9月7日 下午2:31:28
	 * @param response
	 * @param request
	 * @param model
	 * @param req
	 * @throws ServletException
	 * @throws IOException
	 * @throws TException
	 * @throws MerchantException
	 * @throws ParseException
	 * @throws InterruptedException 
	 *
	 */

	@RequestMapping(value = "qrcodeDown", method = RequestMethod.GET)
	public void producnt_qrcodeDown(HttpServletResponse response, HttpServletRequest request,ModelMap model,
			 Query_Product_Qrcode_Req req) throws ServletException, IOException, TException, MerchantException, ParseException, InterruptedException {
		
		if(StringUtil.isBlank(req.getQrcodeUrl()) || "".equals(req.getQrcodeUrl())){
			throw new MerchantException(EnumTypes.fail.getCode(),"商品二维码图片路径不存在！");
		}
		SCPClient scpClient = null;
		Connection scpConnection = null;
		String dirImage = "";
		try {
			scpConnection = common_service.connectServer(Constants.SCPCONFIG);
			scpClient = scpConnection.createSCPClient();
			
			String qrCodeLocal = Constants.getQrocdImgPath();
			String qURL = URLDecoder.decode(req.getQrcodeUrl(), "UTF-8");
			
			LogCvt.info("qURL :"+qrCodeLocal + qURL);
			scpClient.get(qrCodeLocal + qURL,Constants.getImgLocalUrl());
			dirImage = qURL.substring(qURL.lastIndexOf("/") + 1);
			common_service.downFile(Constants.getImgLocalUrl()+"/"+dirImage, response);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error("Scp 异常： "+e.getMessage(), e);
		}finally{
			if(scpConnection!=null){
				scpConnection.close();  //关闭scp链接
				Thread.sleep(6000);
				common_service.deleteDirectory(Constants.getImgLocalUrl()+"/"+dirImage);
			}
		}
	}
	
}
