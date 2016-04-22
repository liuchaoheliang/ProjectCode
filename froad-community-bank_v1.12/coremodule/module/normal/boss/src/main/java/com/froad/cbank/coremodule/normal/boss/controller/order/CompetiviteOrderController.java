package com.froad.cbank.coremodule.normal.boss.controller.order;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.annotation.ImpExp;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.order.BusinessOrderRefundReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.BusinessOrderVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.ProviderOrderQueryVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.order.ShippingInfoVoReq;
import com.froad.cbank.coremodule.normal.boss.support.order.CompetiviteOrderSupport;
import com.froad.cbank.coremodule.normal.boss.utils.ErrorEnums;
import com.froad.cbank.coremodule.normal.boss.utils.ExcelReaderUtils;
import com.froad.cbank.coremodule.normal.boss.utils.PramasUtil;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 
 * @ClassName: CompetiviteOrderController
 * @author chenzhangwei@f-road.com.cn
 * @createTime 2015年11月24日 下午2:00:57
 * @desc <p>
 *       精品商城订单管理
 *       </p>
 */
@Controller
@RequestMapping(value = "/competiviteOrder")
public class CompetiviteOrderController {

	@Resource
	private CompetiviteOrderSupport competiviteOrderSupport;

	
	
	/**
	 * 物流信息查询
	 * @param model
	 * @param clientId
	 */
	@RequestMapping(value = "/deliveryCorpList", method = RequestMethod.GET)
	public void deliveryCorpList(ModelMap model) {
		try {
			model.clear();
			model.putAll(competiviteOrderSupport.queryDeliveryCorpList());	
		} catch (Exception e) {
			LogCvt.error("物流信息查询异常"+e.getMessage(), e);
			new RespError(model, "物流信息查询失败!!!");
		}	
	}
	
	/**
	 * 查看物流信息
	 * @author yfy
	 * @date 2016年1月11日 下午17:35:15
	 * @param request
	 * @param model
	 * @param subOrderId
	 */
	@RequestMapping(value = "/logistics", method =  RequestMethod.GET)
	public void getLogistics(HttpServletRequest request, ModelMap model,String shippingId) {
		
		LogCvt.info("查看物流信息条件是:shippingId:" + shippingId);
		try {
			if(StringUtil.isBlank(shippingId)){			
				throw new BossException("物流单号不能为空!");
			}
			model.clear();
			model.putAll(competiviteOrderSupport.getLogistics(shippingId));
		}catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("查看物流信息异常"+e.getMessage(), e);
			new RespError(model, "查看物流信息失败!!!");
		}
	}
	
	/**
	 * 
	 * <p>
	 * Title:运营商订单列表查询
	 * </p>
	 * 
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 运营订单列表查询
	 * @createTime 2015年11月24日 下午2:04:37
	 * @param model
	 */
	@Auth(keys={"boss_vipmall_order_yy_menu"})
	@RequestMapping(value = "/businessList", method = RequestMethod.GET)
	public void businessList(ModelMap model,BusinessOrderVoReq req,HttpServletRequest request) {
		LogCvt.info("运营商订单列表查询条件是:" + JSON.toJSONString(req));
		try {
			checkBusinessOrder(req);
			model.clear();
			model.putAll(competiviteOrderSupport.queryBusinessOrderList(req));	
		}catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("运营商列表查询异常"+e.getMessage(), e);
			new RespError(model, "运营商列表查询失败!!!");
		}	
	}
	/**
	 * 
	 * <p>
	 * Title: 运营订单详情查询
	 * </p>
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 运营订单详情查询
	 * @createTime 2015年11月24日 下午2:06:47
	 */
	@RequestMapping(value = "/businessDetail", method = RequestMethod.GET)
	public void businessDetail(ModelMap model,String clientId, String subOrderId,HttpServletRequest request) {
		LogCvt.info("运营订单详情查询条件是  clientId:"+clientId+" subOrderId:"+subOrderId );
		try {
			if(!StringUtil.isNotBlank(clientId)){
				throw new BossException("客户端ID不能为空!");
			}
			if(!StringUtil.isNotBlank(subOrderId)){
				throw new BossException("订单编号不能为空!");
			}
			model.clear();
			model.putAll(competiviteOrderSupport.queryBusinessOrderdetail(clientId,subOrderId));	
		}catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("运营商列表查询异常"+e.getMessage(), e);
			new RespError(model, "运营商列表查询失败!!!");
		}	
		
	}
	
	
	/**
	 * Title:运营商订单导出
	 * @author songzichao
	 * @Description: 运营订单导出
	 * @createTime 2015年11月27日 上午10:34:47
	 */
	@Auth(keys={"boss_vipmall_order_yy_export"})
	@RequestMapping(value = "/businessExport", method = RequestMethod.GET)
	public void businessExport(ModelMap model,BusinessOrderVoReq req,HttpServletRequest request){
		LogCvt.info("运营商订单导出条件:" + JSON.toJSONString(req));
		try {
			checkBusinessOrder(req);
			model.clear();
			model.putAll(competiviteOrderSupport.exportBusinessOrder(req));	
		}catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("运营商列表查询异常"+e.getMessage(), e);
			new RespError(model, "运营商列表查询失败!!!");
		}	
		
		
	}

	/**
	 * 运营发起退款
	 * @param model
	 * @param subOrderId 子订单ID
	 * @param refundReason 退款原因
	 * @author liaopeixin
	 *	@date 2016年1月5日 上午11:14:44
	 */
	@Auth(keys={"boss_vipmall_order_yy_refund"})
	@RequestMapping(value = "/refundForBoss", method = RequestMethod.POST)
	public void businessExport(ModelMap model,@RequestBody BusinessOrderRefundReq req){
		try {
			if(!StringUtils.isNotBlank(req.getSubOrderId())){
				throw new BossException("子订单ID不能为空！");
			}
			if(!StringUtils.isNotBlank(req.getProductId())){
				throw new BossException("商品ID不能为空！");
			}
			if(req.getQuantity()<1){
				throw new BossException("退款商品数量为0！");
			}
			if(!StringUtils.isNotBlank(req.getClientId())){
				throw new BossException("客户端ID不能为空！");
			}
			LogCvt.info("运营发起退款请求条件:子订单ID:"+ req.getSubOrderId() 
					+"--退款原因："+req.getRefundReason()+
					"--商品ID："+req.getProductId()+
					"--退款数量："+req.getQuantity()+
					"--客户端ID："+req.getClientId());
			model.clear();
			model.putAll(competiviteOrderSupport.refundForBoss( req.getSubOrderId(),
					req.getRefundReason(),req.getProductId(),req.getQuantity(),req.getClientId()));	
		}catch(BossException e){
			new RespError(model, e);
		}catch (Exception e) {
			LogCvt.error("运营发起退款异常"+e.getMessage(), e);
			new RespError(model, "运营发起退款失败!!!");
		}	
		
		
	}
	
	/**
	 * 
	 * 供应商订单列表查询
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 供应商列表查询
	 * @createTime 2015年11月24日 下午2:22:43
	 * @param model
	 */
	@Auth(keys={"boss_vipmall_order_menu"})
	@RequestMapping(value = "/suppliersList", method = RequestMethod.GET)
	public void supplierList(ModelMap model, ProviderOrderQueryVoReq req) {
		LogCvt.info("供应商订单列表查询条件是:" + JSON.toJSONString(req));
		try {
			checkProviderOrder(req);
			model.clear();
			model.putAll(competiviteOrderSupport.queryProviderOrderInfoByPage(req));	
		}catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("供应商列表查询异常"+e.getMessage(), e);
			new RespError(model, "供应商列表查询失败!!!");
		}	
	}
	
	/**
	 * 供应商订单信息导出
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: 供应商列表查询
	 * @createTime 2015年11月24日 下午2:22:43
	 * @param model
	 */
	@Auth(keys={"boss_vipmall_order_export"})
	@RequestMapping(value = "/supplierExport", method = RequestMethod.GET)
	public void supplierExport(ModelMap model,  ProviderOrderQueryVoReq req) {
		LogCvt.info("供应商订单导出条件是:" + JSON.toJSONString(req));
		try {
			checkProviderOrder(req);
			model.clear();
			model.putAll(competiviteOrderSupport.exportProviderOrderInfo(req));	
		}catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("供应商订单导出异常"+e.getMessage(), e);
			new RespError(model, "供应商订单导出失败!!!");
		}
	}
	

	/**
	 * 导入供应商订单物流信息
	 * @author chenzhangwei@f-road.com.cn
	 * @Description: TODO
	 * @createTime 2015年11月25日 下午3:05:34
	 * @param file
	 * @param model
	 * @param req
	 * @param res
	 * @throws IOException 
	 * @throws Exception
	 */
	@Auth(keys={"boss_vipmall_order_import"})
	@RequestMapping(value = "/supplierInput", method = RequestMethod.POST)
	@ImpExp
	public void supplierInput(@RequestParam("file") MultipartFile file, ModelMap model,
			HttpServletRequest req, HttpServletResponse res) throws IOException{
		res.setCharacterEncoding("UTF-8");
		res.setContentType("text/html");
		LogCvt.info("上传物流信息");
		String json = "";
		HashMap<String, Object> map=new HashMap<String, Object>();
		try {
			List<List<String>> data = new ArrayList<List<String>>();
			//解析Excel文件
			if (file.getOriginalFilename().indexOf(".xls") == -1
					&& file.getOriginalFilename().indexOf(".xlsx") == -1) {
				throw new BossException("文件格式有误，请上传excel文件");
			}
			// excel读取
			ExcelReaderUtils.readExcel(file.getInputStream(),
					file.getOriginalFilename(), null, 14, data);
			LogCvt.info("供应商订单导入物流信息的数据是:" + JSON.toJSONString(data));
			// 上传数据检查
			if (data.size() == 0) {
				throw new BossException("上传文件内容为空!");
			}
			map = competiviteOrderSupport.inputShippingInfo(data);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("RESPONE: " + json);
		} catch (BossException e) {
			map.put("code", e.getCode());
			map.put("message", e.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("excelUpload-RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		} catch (Exception e) {
			map.put("code", ErrorEnums.fail.getCode());
			map.put("message", ErrorEnums.fail.getMsg());
			res.setStatus(608);
			json = JSON.toJSONString(map);
			res.getWriter().write(json);
			LogCvt.info("excelUpload-RESPONE：" + json);
			LogCvt.error(e.getMessage(), e);
		}
	}
	
	
	/**
	 * 供应商订单设置出库
	 * @param model
	 * @param orderId
	 */
	@Auth(keys={"boss_vipmall_order_sale"})
	@RequestMapping(value = "/suppliersOutbound", method = RequestMethod.GET)
	public void suppliersOutbound(ModelMap model,String orderId) {
		LogCvt.info("供应商订单设置出库ID:" + orderId);
		try {
			if(!StringUtil.isNotBlank(orderId)){
				throw new BossException("订单编号不能为空!");
			}
			model.clear();
			model.putAll(competiviteOrderSupport.shippingByOrderId(orderId));	
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("供应商订单设置出库异常"+e.getMessage(), e);
			new RespError(model, "供应商订单设置出库失败!!!");
		}	
		
	}
	
	/**
	 * 供应商订单物流信息更新
	 * @param model
	 * @param orderId
	 */
	@Auth(keys={"boss_vipmall_order_modify"})
	@RequestMapping(value = "/suppliersUpdate", method = RequestMethod.GET)
	public void suppliersUpdate(ModelMap model,ShippingInfoVoReq req) {
		LogCvt.info("供应商订单物流信息更新参数:" + JSON.toJSONString(req));
		try {
			checkShippingInfo(req);
			model.clear();
			model.putAll(competiviteOrderSupport.updateShippingInfo(req));	
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("供应商订单物流信息更新异常"+e.getMessage(), e);
			new RespError(model, "供应商订单物流信息更新失败!!!");
		}	
	}
	
	//校验上传的物流信息 
	private void checkShippingInfo(ShippingInfoVoReq req) throws BossException{
		if(!StringUtil.isNotBlank(req.getSubOrderId())){
			throw new BossException("子订单编号不能为空!");
		}
		if(!StringUtil.isNotBlank(req.getShippingId())){
			throw new BossException("物流单号不能为空!");
		}
	} 
	
	
	//校验供应商订单查询信息
	private void checkProviderOrder(ProviderOrderQueryVoReq req) throws ParseException, BossException{
		if(req.getOrderId()!=null&&!"".equals(req.getOrderId())&&req.getOrderId().length()>20){
			throw new BossException("订单编号最大长度为20!");
		}
		if(req.getMemberCode()!=null&&!"".equals(req.getMemberCode()) &&req.getMemberCode().toString().length()>30){
			throw new BossException("用户编号最大长度为30!");
		}
		if(req.getPhone()!=null&&!"".equals(req.getPhone())&&!req.getPhone().matches("[1][\\d]{10}")){
			throw new BossException("手机号格式有误!");
		}
		if(req.getBegTime()!=null&&req.getEndTime()!=null&&!"".equals(req.getBegTime())&&!"".equals(req.getEndTime())){
			if((PramasUtil.DateFormat(req.getBegTime())-PramasUtil.DateFormat(req.getEndTime()))>0){
				throw new BossException("开始时间不能大于结束时间!");
			}
			if((PramasUtil.DateFormat(req.getBegTime())+Long.valueOf((long)90 * 86400 * 1000))<PramasUtil.DateFormat(req.getEndTime())){
				throw new BossException("订单创建时间段最大跨度90天!");
			}
		}
	}
	
	//校验运营商订单查询信息
	private void checkBusinessOrder(BusinessOrderVoReq req) throws ParseException, BossException{
		if(req.getOrderId()!=null&&!"".equals(req.getOrderId())&&req.getOrderId().length()>20){
			throw new BossException("订单编号最大长度为20!");
		}
		if(req.getMemberCode()!=null&&!"".equals(req.getMemberCode())&&req.getMemberCode().toString().length()>30){
			throw new BossException("用户编号最大长度为30!");
		}
		if(!"".equals(req.getCreateStartTime())&&!"".equals(req.getCreateEndTime())){
			if((PramasUtil.DateFormat(req.getCreateStartTime())-PramasUtil.DateFormat(req.getCreateEndTime()))>0){
				throw new BossException("开始时间不能大于结束时间!");
			}
			if((PramasUtil.DateFormat(req.getCreateStartTime())+Long.valueOf((long)90 * 86400 * 1000))<PramasUtil.DateFormat(req.getCreateEndTime())){
				throw new BossException("订单创建时间段最大跨度90天!");
			}
		}
	}

}
