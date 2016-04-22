package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.bank.service.GatherService;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.ExcelUtil;
import com.froad.cbank.coremodule.module.normal.bank.vo.PayGatherVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.TypeVo;

/**
 * 支付汇总
 * @author yfy
 *
 */
@Controller
@RequestMapping(value = "/payGather")
public class PayGatherController {
	
	@Resource
	private GatherService gatherService;
	
	/**
	 * @Title: 支付汇总条件查询
	 * @author yfy
	 * @date 2015-3-24 上午9:48:32
	 * @param model
	 * @param req
	 */
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
    public void payGatherList(ModelMap model,HttpServletRequest req) {
		//String productType,String merchantName,String pratenOrgCode,String orgCode,
		//String startDate,String endDate
		try {
			
			//List<PayGatherVo> payGatherList = gatherService.findByConditions(null, null, null, null, null, null);
			
			//商品类型
			List<TypeVo> productTypeList = gatherService.getProductType();
			model.put("productTypeList", productTypeList);
			
			List<PayGatherVo> payGatherList = new ArrayList<PayGatherVo>();
			for(int i = 0;i < 3;i++){
				PayGatherVo payGather = new PayGatherVo();
				if(i == 0){
					payGather.setPayType("积分");
				}else if(i == 1){
					payGather.setPayType("贴膜卡");
				}else{
					payGather.setPayType("快捷");
				}
				payGather.setTotalMoney("20");
				payGather.setOrderNumber("5");
				payGatherList.add(payGather);
			}
			PayGatherVo payGather = new PayGatherVo();
			payGather.setPayType("合计");
			payGather.setTotalMoney("60");
			payGather.setOrderNumber("15");
			payGatherList.add(payGather);
			model.put("payGatherList", payGatherList);
			
			model.put("message", "支付汇总列表条件查询成功");	
			
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 商户交易汇总列表条件查询
	 * @author yfy
	 * @date 2015-3-24 下午15:08:32
	 * @param model
	 * @param req
	 * @param resp
	 */
	@RequestMapping(value = "/et",method = RequestMethod.POST)
	public void export(ModelMap model,HttpServletRequest req,HttpServletResponse response) throws Exception {
		System.out.println("支付汇总查询");
		//String productType,String merchantName,String pratenOrgCode,String orgCode,
		//String startDate,String endDate
		
		//导出的数据
		List<List<String>> data = new ArrayList<List<String>>();
		
		try {
			//业务处理
			
		} catch (Exception e) {
			LogCvt.info("支付汇总列表条件查询"+e.getMessage(), e);
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
		//导出的标题
		List<String> header = new ArrayList<String>();
		header.add("序号");
		header.add("支付方式");
		header.add("总金额");
		header.add("订单总数量");
		
		//生成excel
		HSSFWorkbook book = ExcelUtil.generate(header, data, "支付汇总");
		
		try {
			// 下载excel
			 //下载excel
			response.setContentType("application/ms-excel;charset=UTF-8");
			String headerStr="支付汇总-"+ExcelUtil.getFileShortName();
			response.setHeader("Content-Disposition", "attachment;filename="+new String(headerStr.getBytes("GBK"),"ISO-8859-1")+".xls");
			OutputStream os = response.getOutputStream();
			book.write(os);
			os.flush();
			os.close();
		} catch (Exception e) {
			LogCvt.info("支付汇总下载"+e.getMessage(), e);
			model.put("code", EnumTypes.fail.getCode());
			model.put("message","支付汇总下载异常");
		}
    }

}
