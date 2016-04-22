package com.froad.cbank.coremodule.normal.boss.support.bankAccess;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.framework.common.util.type.BeanUtils;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.Page;
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.BankAccessListVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.BankAccessVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.BankAccessVoRes;
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.ClientVo;
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.FunctionVoReq;
import com.froad.cbank.coremodule.normal.boss.pojo.bankAccess.PaymentMethodVoReq;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.thrift.service.BankAccessService;
import com.froad.thrift.vo.PageVo;
import com.froad.thrift.vo.bankaccess.BankAccessAddReq;
import com.froad.thrift.vo.bankaccess.BankAccessAddRes;
import com.froad.thrift.vo.bankaccess.BankAccessClientListReq;
import com.froad.thrift.vo.bankaccess.BankAccessClientListRes;
import com.froad.thrift.vo.bankaccess.BankAccessClientVo;
import com.froad.thrift.vo.bankaccess.BankAccessDeleteReq;
import com.froad.thrift.vo.bankaccess.BankAccessDeleteRes;
import com.froad.thrift.vo.bankaccess.BankAccessDetailReq;
import com.froad.thrift.vo.bankaccess.BankAccessDetailRes;
import com.froad.thrift.vo.bankaccess.BankAccessEditReq;
import com.froad.thrift.vo.bankaccess.BankAccessEditRes;
import com.froad.thrift.vo.bankaccess.BankAccessListReq;
import com.froad.thrift.vo.bankaccess.BankAccessListRes;
import com.froad.thrift.vo.bankaccess.BankAccessVo;
import com.froad.thrift.vo.bankaccess.FunctionModuleVo;
import com.froad.thrift.vo.bankaccess.PaymentMethodVo;

/**
 * 多银行接入配置列表
 * @author yfy
 * @date: 2015年9月16日 下午16:30:10
 */
@Service
public class BankAccessSupport {
	
	@Resource
	private BankAccessService.Iface bankAccessService;

	/**
	 * 多银行接入配置列表查询
	 * @author yfy
	 * @date: 2015年9月16日 下午16:42:01
	 * @param clientId
	 * @return
	 * @throws TException
	 */
	public Map<String, Object> list(String clientId,Integer pageNumber,Integer pageSize) throws TException {
		Map<String, Object> map = new HashMap<String, Object>();
		// 封装查询条件
		PageVo pageVo = new PageVo();
		pageVo.setPageNumber(pageNumber);
		pageVo.setPageSize(pageSize);
		BankAccessListReq listReq = new BankAccessListReq();
		listReq.setClientId(clientId);
		listReq.setPageVo(pageVo);
		BankAccessListVoRes voRes = null;
		ArrayList<BankAccessListVoRes> listVoRes = null;
		//调用server端银行配置列表接口
		BankAccessListRes listRes = bankAccessService.getBankAccessList(listReq);
		List<BankAccessVo> queryRes = listRes.getBankAccessList();
		if(queryRes != null && queryRes.size() > 0){
			listVoRes = new ArrayList<BankAccessListVoRes>();
			for(BankAccessVo vo : queryRes){
				voRes = new BankAccessListVoRes();
				BeanUtils.copyProperties(voRes, vo);// 封装列表数据基本信息
				listVoRes.add(voRes);
			}
		}
		// 封装分页数据
		Page page = new Page();
		if(listRes.getPageVo() != null){
			BeanUtils.copyProperties(page, listRes.getPageVo());
		}
		map.put("page", page);
		map.put("list", listVoRes);
		return map;
	}
	
	/**
	 * 多银行接配置新增
	 * @author yfy
	 * @date: 2015年9月16日 下午16:56:10
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> add(BankAccessVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		BankAccessAddReq addReq = new BankAccessAddReq();
		//封装数据基本信息
		BeanUtils.copyProperties(addReq, voReq);
		//封装功能模块基本信息
		if(voReq.getFunctionList() != null && voReq.getFunctionList().size() > 0){
			FunctionModuleVo functionModuleVo = null;
			List<FunctionModuleVo> functionModuleVoList = new ArrayList<FunctionModuleVo>();
			for(FunctionVoReq vo : voReq.getFunctionList()){
				functionModuleVo = new FunctionModuleVo();
				BeanUtils.copyProperties(functionModuleVo, vo);
				functionModuleVoList.add(functionModuleVo);
			}
			addReq.setFunctionList(functionModuleVoList);
		}
		//封装支付方式基本信息
		if(voReq.getPaymentList() != null && voReq.getPaymentList().size() > 0){
			PaymentMethodVo paymentMethodVo = null;
			List<PaymentMethodVo> paymentMethodVoList = new ArrayList<PaymentMethodVo>();
			for(PaymentMethodVoReq vo : voReq.getPaymentList()){
				paymentMethodVo = new PaymentMethodVo();
				BeanUtils.copyProperties(paymentMethodVo, vo);
				paymentMethodVoList.add(paymentMethodVo);
			}
			addReq.setPaymentList(paymentMethodVoList);
		}
		//调用server端银行配置新增接口
		BankAccessAddRes addRes = bankAccessService.addBankAccess(addReq);
		if(Constants.RESULT_SUCCESS.equals(addRes.getResultVo().getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "新增银行配置信息成功");
		}else{
			throw new BossException(addRes.getResultVo().getResultCode(), addRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 多银行接入配置修改
	 * @author yfy
	 * @date: 2015年9月16日 下午17:18:25
	 * @param voReq
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> update(BankAccessVoReq voReq) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		BankAccessEditReq editReq = new BankAccessEditReq();
		//封装数据基本信息
		BeanUtils.copyProperties(editReq, voReq);
		//封装功能模块基本信息
		if(voReq.getFunctionList() != null && voReq.getFunctionList().size() > 0){
			FunctionModuleVo functionModuleVo = null;
			List<FunctionModuleVo> functionModuleVoList = new ArrayList<FunctionModuleVo>();
			for(FunctionVoReq vo : voReq.getFunctionList()){
				functionModuleVo = new FunctionModuleVo();
				BeanUtils.copyProperties(functionModuleVo, vo);
				functionModuleVoList.add(functionModuleVo);
			}
			editReq.setFunctionList(functionModuleVoList);
		}
		//封装支付方式基本信息
		if(voReq.getPaymentList() != null && voReq.getPaymentList().size() > 0){
			PaymentMethodVo paymentMethodVo = null;
			List<PaymentMethodVo> paymentMethodVoList = new ArrayList<PaymentMethodVo>();
			for(PaymentMethodVoReq vo : voReq.getPaymentList()){
				paymentMethodVo = new PaymentMethodVo();
				BeanUtils.copyProperties(paymentMethodVo, vo);
				paymentMethodVoList.add(paymentMethodVo);
			}
			editReq.setPaymentList(paymentMethodVoList);
		}
		//调用server端银行配置修改接口
		BankAccessEditRes addRes = bankAccessService.editBankAccess(editReq);
		if(Constants.RESULT_SUCCESS.equals(addRes.getResultVo().getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "修改银行配置信息成功");
		}else{
			throw new BossException(addRes.getResultVo().getResultCode(), addRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 多银行接入配置详情
	 * @author yfy
	 * @date: 2015年9月16日 下午16:36:01
	 * @param clientId
	 * @return
	 * @throws TException
	 * @throws BossException 
	 */
	public Map<String, Object> detail(String clientId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		BankAccessDetailReq detailReq = new BankAccessDetailReq();
		detailReq.setClientId(clientId);
		//调用server端银行配置详情接口
		BankAccessDetailRes detailRes = bankAccessService.getBankAccessDetail(detailReq);
		if(detailRes.getResultVo().getResultCode().equals(Constants.RESULT_SUCCESS)){
			BankAccessVoRes voRes = null;
			if(detailRes != null && detailRes.getCilentName() != null){
				voRes = new BankAccessVoRes();
				//封装数据基本信息
				voRes.setClientId(clientId);
				voRes.setClientName(detailRes.getCilentName());
				//封装功能模块基本信息
				if(detailRes.getFunctionList() != null && detailRes.getFunctionList().size() > 0){
					FunctionVoReq functionVoReq = null;
					List<FunctionVoReq> functionVoReqList = new ArrayList<FunctionVoReq>();
					for(FunctionModuleVo vo : detailRes.getFunctionList()){
						functionVoReq = new FunctionVoReq();
						BeanUtils.copyProperties(functionVoReq, vo);
						functionVoReqList.add(functionVoReq);
					}
					voRes.setFunctionList(functionVoReqList);
				}
				//封装支付方式基本信息
				if(detailRes.getPaymentList() != null && detailRes.getPaymentList().size() > 0){
					PaymentMethodVoReq paymentMethodVoReq = null;
					List<PaymentMethodVoReq> paymentMethodVoReqList = new ArrayList<PaymentMethodVoReq>();
					for(PaymentMethodVo vo : detailRes.getPaymentList()){
						paymentMethodVoReq = new PaymentMethodVoReq();
						BeanUtils.copyProperties(paymentMethodVoReq, vo);
						paymentMethodVoReqList.add(paymentMethodVoReq);
					}
					voRes.setPaymentList(paymentMethodVoReqList);
				}
				map.put("bankAccess", voRes);
			}
		}else{
			throw new BossException(detailRes.getResultVo().getResultCode(), detailRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 多银行接入配置删除
	 * @author yfy
	 * @date: 2015年9月16日 下午17:53:41
	 * @param clientId
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> delete(String clientId) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		BankAccessDeleteReq deleteReq = new BankAccessDeleteReq();
		deleteReq.setClientId(clientId);
		BankAccessDeleteRes deleteRes = bankAccessService.deleteBankAccess(deleteReq);
		if(Constants.RESULT_SUCCESS.equals(deleteRes.getResultVo().getResultCode())){
			map.put("code", Constants.RESULT_SUCCESS);
			map.put("message", "已删除该银行配置信息");
		}else{
			throw new BossException(deleteRes.getResultVo().getResultCode(), deleteRes.getResultVo().getResultDesc());
		}
		return map;
	}
	
	/**
	 * 多银行接入配置客户端查询
	 * @author yfy
	 * @date: 2015年9月17日 下午17:23:41
	 * @param type 获取业务类型1:获取查询列表客户端下拉框集合信息;2：获取新增银行信息时客户端下拉框信息
	 * @return
	 * @throws TException
	 * @throws BossException
	 */
	public Map<String, Object> queryClient(String type) throws TException, BossException {
		Map<String, Object> map = new HashMap<String, Object>();
		List<ClientVo> clientList = new ArrayList<ClientVo>();
		ClientVo vo = null;
		BankAccessClientListReq listReq = new BankAccessClientListReq();
		listReq.setType(type);
		BankAccessClientListRes listRes = bankAccessService.getClientList(listReq);
		List<BankAccessClientVo> clientVoList =  listRes.getClientList();
		if(clientVoList != null && clientVoList.size() > 0){
			for(BankAccessClientVo clientVo : clientVoList){
				vo = new ClientVo();
				BeanUtils.copyProperties(vo, clientVo);
				clientList.add(vo);
			}
		}
		map.put("clientList", clientList);
		return map;
	}
	
}
