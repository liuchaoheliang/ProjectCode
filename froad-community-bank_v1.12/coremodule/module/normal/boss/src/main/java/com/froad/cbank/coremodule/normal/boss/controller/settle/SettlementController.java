package com.froad.cbank.coremodule.normal.boss.controller.settle;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.settle.SettlementVoReq;
import com.froad.cbank.coremodule.normal.boss.support.settle.SettlementSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2015 
 * @author: f-road
 * @time:  2015-5-1下午1:45:00
 */
@Controller
@RequestMapping(value="/settle")
public class SettlementController{
	
	@Resource
	private SettlementSupport settlementSupport;
	
	/**
	  * 方法描述：分页查询结算订单列表
	  * @param: 
	  * @return: 
	  * @author: froad
	  */
	@Auth(keys={"boss_settle_menu"})
	@RequestMapping(value = "/lt", method = RequestMethod.GET)
	public void settlementPage(ModelMap model, SettlementVoReq voReq){	
		LogCvt.info("分页查询结算条件："+JSON.toJSONString(voReq));
		try {
			model.clear();
			if(StringUtils.isBlank(voReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(voReq.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(settlementSupport.querySettlementByPage(voReq));	
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			LogCvt.error("结算订单列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "结算订单列表查询失败!!!");
		}	
	}
	
	/**
	 * 结算订单列表导出
	 * @path /settle/settleListExport
	 * @author luwanquan@f-road.com.cn
	 * @date 2015年9月2日 上午10:16:19
	 * @param model
	 * @param voReq
	 * @throws Exception
	 */
	@Auth(keys={"boss_settle_export"})
	@RequestMapping(value = "/settleListExport", method = RequestMethod.GET)
	public void settleListExport(ModelMap model, SettlementVoReq voReq) throws Exception {
		try {
			if(LogCvt.isDebugEnabled()){
				LogCvt.debug("结算订单列表导出请求参数：" + JSON.toJSONString(voReq));
			}
			model.clear();
			if(StringUtils.isBlank(voReq.getClientId())){
				throw new BossException("银行渠道不能为空!");
			}
			if(StringUtils.isBlank(voReq.getOrgCodes())){
				throw new BossException("所属组织不能为空!");
			}
			model.putAll(settlementSupport.settleListExport(voReq));
		} catch (BossException e) {
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error(e.getMessage(), e);
			new RespError(model);
		}
	}

	/**
	  * 方法描述：修改结算状态
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘超 liuchao@f-road.com.cn
	  * @time: 2015年4月27日 下午1:44:01
	  */
	@Auth(keys={"boss_settle_modify"})
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public void updateMerchantUserPwd(ModelMap model, @RequestBody SettlementVoReq voReq){
		LogCvt.info("修改结算条件：" + JSON.toJSONString(voReq));
		model.clear();
		try {
			if(voReq != null && StringUtil.isNotBlank(voReq.getId()) && StringUtil.isNotBlank(voReq.getSettleState())){
				model.putAll(settlementSupport.modifySettlementState(voReq.getId(), voReq.getSettleState(), voReq.getRemark()));
		
			}else{
				new RespError(model, "id或settleState不能为空!!!");			
			}
		} catch (TException e) {
			LogCvt.error("修改结算状态请求异常" + e.getMessage(), e);
			new RespError(model, "修改结算状态失败!!!");
		}
	}
	
}
