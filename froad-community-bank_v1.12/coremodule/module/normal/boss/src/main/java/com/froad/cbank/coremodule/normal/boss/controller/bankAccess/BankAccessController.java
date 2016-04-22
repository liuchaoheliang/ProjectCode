package com.froad.cbank.coremodule.normal.boss.controller.bankAccess;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
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
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.BankAccessVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.FunctionVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.PaymentMethodVoReq;
import com.froad.cbank.coremodule.normal.boss.support.bankAccess.BankAccessSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 多银行接入配置
 * @author yfy
 * @date: 2015年9月16日 下午16:13:01
 */
@Controller
@RequestMapping(value="bankAccess")
public class BankAccessController {
	
	@Resource
	private BankAccessSupport bankAccessSupport;
	
	/**
	 * 多银行接入配置列表查询
	 * @author yfy
	 * @date: 2015年9月16日 下午15:21:01
	 * @param model
	 * @param request
	 * @param listReq
	 */
	@Auth(keys={"boss_mulbank_menu"})
	@RequestMapping(value = "lt", method = RequestMethod.GET)
	public void list(ModelMap model, HttpServletRequest request,String clientId,
			Integer pageNumber,Integer pageSize) {
		LogCvt.info("多银行接入配置列表查询条件:clientId:" + clientId);
		try {
			if(!StringUtil.isNotBlank(pageNumber) || pageNumber < 1){
				pageNumber = 1;
			}
			if(!StringUtil.isNotBlank(pageSize) || pageSize < 0){
				pageSize = 10;
			}
			model.clear();
			model.putAll(bankAccessSupport.list(clientId,pageNumber,pageSize));
		} catch (Exception e) {
			LogCvt.error("多银行接入配置列表查询请求异常"+e.getMessage(), e);
			new RespError(model, "多银行接入配置列表查询失败!!!");
		}
	}
	
	/**
	 * 多银行接入配置新增
	 * @author yfy
	 * @date: 2015年9月16日 下午16:48:15
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_mulbank_add"})
	@RequestMapping(value = "ad", method = RequestMethod.POST)
	public void add(ModelMap model, HttpServletRequest request,@RequestBody BankAccessVoReq voReq) {
		LogCvt.info("多银行接入配置新增参数:" + JSON.toJSONString(voReq));
		try {
			//校验信息
			checkEmpty(model,voReq);
			model.clear();
			model.putAll(bankAccessSupport.add(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("多银行接入配置新增请求异常"+e.getMessage(), e);
			new RespError(model, "多银行接入配置新增失败!!!");
		}
	}
	
	/**
	 * 多银行接入配置修改
	 * @author yfy
	 * @date: 2015年9月16日 下午17:07:55
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_mulbank_modify"})
	@RequestMapping(value = "ue", method = RequestMethod.POST)
	public void update(ModelMap model, HttpServletRequest request,@RequestBody BankAccessVoReq voReq) {
		LogCvt.info("多银行接入配置修改参数:" + JSON.toJSONString(voReq));
		try {
			//校验信息
			checkEmpty(model,voReq);
			if(!StringUtils.isNotBlank(voReq.getClientId())){
				throw new BossException("客户端ID不能为空!");
			}
			model.clear();
			model.putAll(bankAccessSupport.update(voReq));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("多银行接入配置修改请求异常"+e.getMessage(), e);
			new RespError(model, "多银行接入配置修改失败!!!");
		}
	}
	
	/**
	 * 多银行接入配置详情
	 * @author yfy
	 * @date: 2015年9月16日 下午17:29:34
	 * @param model
	 * @param request
	 * @param clientId
	 */
	/*@Auth(keys={"boss_mulbank_menu"})*/
	@RequestMapping(value = "dl", method = RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,String clientId) {
		LogCvt.info("多银行接入配置详情查询条件:" + JSON.toJSONString(clientId));
		try {
			if(!StringUtils.isNotBlank(clientId)){
				throw new BossException("客户端ID不能为空!");
			}
			model.clear();
			model.putAll(bankAccessSupport.detail(clientId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("多银行接入配置详情查询请求异常"+e.getMessage(), e);
			new RespError(model, "多银行接入配置详情查询失败!!!");
		}
	}
	
	/**
	 * 多银行接入配置删除
	 * @author yfy
	 * @date: 2015年9月16日 下午17:42:14
	 * @param model
	 * @param request
	 * @param clientId
	 */
	@Auth(keys={"boss_mulbank_delete"})
	@RequestMapping(value = "de", method = RequestMethod.DELETE)
	public void delete(ModelMap model, HttpServletRequest request,String clientId) {
		LogCvt.info("多银行接入配置删除条件:" + JSON.toJSONString(clientId));
		try {
			if(!StringUtils.isNotBlank(clientId)){
				throw new BossException("客户端ID不能为空!");
			}
			model.clear();
			model.putAll(bankAccessSupport.delete(clientId));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("多银行接入配置删除请求异常"+e.getMessage(), e);
			new RespError(model, "多银行接入配置删除失败!!!");
		}
	}
	
	/**
	 * 多银行接入配置客户端查询
	 * @author yfy
	 * @date: 2015年9月17日 下午17:23:09
	 * @param model
	 * @param request
	 * @param type 获取业务类型1:获取查询列表客户端下拉框集合信息;2：获取新增银行信息时客户端下拉框信息
	 */
	@RequestMapping(value = "qcl", method = RequestMethod.GET)
	public void queryClient(ModelMap model, HttpServletRequest request,String type) {
		LogCvt.info("多银行接入配置客户端查询条件:type:" + type);
		try {
			model.clear();
			model.putAll(bankAccessSupport.queryClient(type));
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			LogCvt.error("多银行接入配置客户端查询请求异常"+e.getMessage(), e);
			new RespError(model, "多银行接入配置客户端查询失败!!!");
		}
	}
	
	/**
	 * 校验信息
	 * @param model
	 * @param voReq
	 * @throws BossException 
	 */
	public void checkEmpty(ModelMap model,BankAccessVoReq voReq) throws BossException{
		if(voReq.getFunctionList() != null && voReq.getFunctionList().size() > 0){
			int count = 0;
			for(FunctionVoReq vo : voReq.getFunctionList()){
				if(vo.getType().equals("1") || vo.getType().equals("2")
						|| vo.getType().equals("4")){
					count++;
				}
				if(StringUtil.isBlank(vo.getAliasName())){
					throw new BossException("请输入功能模块名称");
				}
				if(vo.getAliasName().length() > 5){
					throw new BossException("名称最多不能超过5个字符");
				}
			}
			if(count < 3){
				throw new BossException("功能模块必须默认选中特惠商户、特惠商品、扫码支付!");
			}
		}else{
			throw new BossException("功能模块必须默认选中特惠商户、特惠商品、扫码支付!");
		}
		if(voReq.getPaymentList() != null && voReq.getPaymentList().size() > 0){
			String payMoth = "";// 支付方式类型：1:(手机)联盟积分;2:银行积分;20:贴膜卡积分（贴膜卡支付）;
			//41:银联无卡积分（银联无卡支付）;50:网银积分（网银支付）;51:快捷支付（银行卡支付）
			for(PaymentMethodVoReq vo : voReq.getPaymentList()){
				if(vo.getType().equals("1")){
					payMoth = "手机联盟积分";
				}else if(vo.getType().equals("2")){
					payMoth = "银行积分";
				}else if(vo.getType().equals("20")){
					payMoth = "贴膜卡支付";
				}else if(vo.getType().equals("41")){
					payMoth = "银联无卡支付";
				}else if(vo.getType().equals("50")){
					payMoth = "网银支付";
				}else if(vo.getType().equals("51")){
					payMoth = "银行卡支付";
				}
				if(StringUtil.isBlank(vo.getAliasName())){
					throw new BossException("请输入"+payMoth+"文案");
				}
				if(vo.getAliasName().length() > 10){
					throw new BossException("文案最多不能超过10个字符");
				}
				if(StringUtil.isBlank(vo.getIconUrl())){
					throw new BossException("请上传"+payMoth+"图标");
				}
			}
		}else{
			throw new BossException("请至少选中一种支付方式!");
		}
	}
	
}
