package com.froad.cbank.coremodule.module.normal.bank.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.monitor.Monitor;
import com.froad.cbank.coremodule.framework.common.monitor.MonitorEnums;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.TakeService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.DeliveryVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.DeliveryVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;
import com.froad.thrift.service.BankOperatorService;

/**
 * 提货管理
 * 
 * @author yfy
 * @date 2015-3-21 下午17:32:31
 */
@Controller
@RequestMapping(value = "/take")
public class TakeController extends BasicSpringController{
	
	@Resource
	private TakeService takeService;
	@Resource
	BankOperatorService.Iface bankOperatorService;
	
	/**
	 * @Title: 根据提货码查询提货信息
	 * @author yfy
	 * @date 2015-3-21 下午16:38:41
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"pick_bytakecode_qlt_bind"})
	@RequestMapping(value = "/qlt",method = RequestMethod.POST)
    public void takeQueryList(ModelMap model,HttpServletRequest req,@RequestBody DeliveryVoReq voReq) {
		
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if (StringUtil.isNotEmpty(voReq.getTakeCode())) {
				List<DeliveryVo> deliveryList = takeService
						.findPageByConditions(voReq.getTakeCode(), voReq.getOrgCode());
				LogCvt.info("根据提货码查询提货信息返回:"+JSON.toJSONString(deliveryList));
				model.put("deliveryList", deliveryList);
				if(deliveryList != null && deliveryList.size() > 0){
					model.put("takePerson", deliveryList.get(0).getTakePerson());//提货人
					model.put("phone", deliveryList.get(0).getPhone());//提货手机号
				}else{
					model.put("takePerson", "");//提货人
					model.put("phone", "");//提货手机号
				}
				model.put("message", "提货码查询提货信息成功");	
			} else {
				model.put("code", EnumTypes.fail.getCode());
				model.put("message", "提货码不能为空");
				return;
			}
		} catch (Exception e) {
			LogCvt.info("提货码查询提货信息"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", "提货码查询提货信息异常");
		}
		
	}
	
	/**
	 * @Title: 确认提货
	 * @author yfy
	 * @date 2015-3-21 下午17:53:38
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"pick_bytakecode_ue"})
	@RequestMapping(value = "/ue",method = RequestMethod.POST)
    public void takeDelivery(ModelMap model,HttpServletRequest req,@RequestBody DeliveryVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			if(StringUtil.isNotEmpty(voReq.getOrgCode()) 
					&& StringUtil.isNotEmpty(voReq.getOperatorId()) 
					&& voReq.getTicketList() != null && voReq.getTicketList().size() > 0){
				MsgVo msgVo = takeService.takeDelivery(voReq.getClientId(),
						voReq.getOrgCode(),voReq.getOperatorId(),voReq.getTicketList());
				LogCvt.info("确认提货返回:"+JSON.toJSONString(msgVo));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
				}else{
					model.put("code", EnumTypes.fail.getCode());
				}
				model.put("message", msgVo.getMsg());	
			}else{
				if(!StringUtil.isNotEmpty(voReq.getOrgCode())){
					model.put("message", "机构不能为空");	
				}else if(!StringUtil.isNotEmpty(voReq.getOperatorId())){
					model.put("message", "操作人不能为空");	
				}else{
					model.put("message", "提货信息不能为空");
				}
				model.put("code", EnumTypes.fail.getCode());
			}
		} catch (Exception e) {
			LogCvt.info("确认提货"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", "确认提货异常");
		}
		
	}
	
	/**
	 * @Title: 提货列表查询信息
	 * @author yfy
	 * @date 2015-3-21 下午18:34:41
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"boutiquepickquery_menu","boutiquepickquery_lt_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
    public void takeList(ModelMap model,HttpServletRequest req,@RequestBody DeliveryVoReq voReq) {
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			QueryResult<DeliveryVo> queryVo = takeService.findPageByConditions(voReq);
			model.put("deliveryList", queryVo.getResult());//提货列表
			model.put("totalCount", queryVo.getTotalCount());//总记录数
			model.put("pageCount", queryVo.getPageCount());//总页数
			model.put("pageNumber", queryVo.getPageNumber());//当前页数
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
			model.put("message", "提货列表查询信息成功");	
			
		} catch (Exception e) {
			LogCvt.info("提货列表查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	

	/**
	 * 
	 * exportOfOptimize:提货码导出
	 *
	 * @author 明灿 2015年10月10日 下午4:49:16
	 * @param model
	 * @param req
	 * @param voReq
	 * @throws Exception
	 *
	 */
	@CheckPermission(keys = { "boutiquepickquery_et" })
	@RequestMapping(value = "/et", method = RequestMethod.GET)
	public void exportOfOptimize(ModelMap model, HttpServletRequest req, DeliveryVoReq voReq) throws Exception {
		try {

			long begin = System.currentTimeMillis();
			// 业务处理
			String clientId = String.valueOf(req.getAttribute((Constants.CLIENT_ID)));
			voReq.setClientId(clientId);
			model.putAll(takeService.getTakeExport(voReq));
			Monitor.send(MonitorEnums.bank_take_et, String.valueOf(System.currentTimeMillis() - begin));
		} catch (Exception e) {
			LogCvt.info("提货信息下载" + e.getMessage(), e);
			model.put("code", EnumTypes.fail.getCode());
			model.put("message", "提货信息下载异常");
		}
	}


}
