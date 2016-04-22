package com.froad.cbank.coremodule.module.normal.bank.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.OperatorLogService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorLogVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.OperatorLogVoRes;

/**
 * 日志
 * @author ylchu
 *
 */
@Controller
@RequestMapping(value = "/operatorLog")
public class OperatorLogController extends BasicSpringController {

	@Resource
	private OperatorLogService operatorLogService;
	/**
	 * @Title: 日志查询
	 * @author ylchu
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"operate_log_lt_menu","operate_log_select_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
	public void operatorLogDetail(ModelMap model,HttpServletRequest req,@RequestBody OperatorLogVo voReq){
		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			QueryResult<OperatorLogVoRes> queryVo = operatorLogService.findPageByConditions(voReq);
			
			model.put("operatorLogList", queryVo.getResult());
			model.put("totalCount", queryVo.getTotalCount());
			model.put("pageCount", queryVo.getPageCount());
			model.put("pageNumber", queryVo.getPageNumber());//总页
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
			
			model.put("message", "银行管理员操作日志列表条件查询成功");	
			
		} catch (Exception e) {
			LogCvt.info("银行管理员操作日志列表条件查询"+e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
}
