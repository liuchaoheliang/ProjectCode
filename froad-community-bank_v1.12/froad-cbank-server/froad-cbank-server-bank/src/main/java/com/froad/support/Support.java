package com.froad.support;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.po.Result;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.MemberInformationService;
import com.froad.thrift.service.MerchantAccountService;
import com.froad.thrift.service.MerchantAuditService;
import com.froad.thrift.vo.ResultVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;
import com.froad.utils.ThriftConfig;


/**    
 * <p>Title: Support.java</p>    
 * <p>Description: 描述 </p>   跨模块调用支持类
 * @author ll      
 * @version 1.0    
 * @created 2015年6月30日 下午10:50:02   
 */

public class Support {
	
	
    
    /**
     * VIP会员标签同步
     * @Title: synchBankLabel 
     * @author ll 20150716 
     * @param bankLabelID 组织机构号
     * @param bankLabelName 机构名称
     * @param state null/0新增          1修改
     * @throws TException
     * @return Result   结果
     * @throws
     */
    public Result synchBankLabel(String bankLabelID, String bankLabelName, Integer state, String clientId)throws TException {
    	LogCvt.info("VIP会员标签同步");
    	
    	ResultVo resultVo = new ResultVo();
    	try {
	    	BankCardService.Iface bcService = 
	    			(BankCardService.Iface) ThriftClientProxyFactory.createIface(BankCardService.class.getName(),BankCardService.class.getSimpleName(), ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
	    	resultVo =  bcService.synchBankLabel(bankLabelID,bankLabelName,state,clientId);
	    	
	    	LogCvt.info("VIP会员标签同步返回结果: " + JSonUtil.toJSonString(resultVo));
	    	if (resultVo.getResultCode().equals(ResultCode.failed.getCode())) {
	    		throw new TException(resultVo.getResultDesc()==null?"系统异常":resultVo.getResultDesc());
			}
	    	
    	} catch (TException e) {
			LogCvt.error("VIP会员标签同步失败, 原因: " + e.getMessage(), e);
			throw e;
		}
    	return (Result) BeanUtil.copyProperties(Result.class, resultVo);
    }
    
    /**
     * 银行联合登录帐号校验
     * @param clientId 客户端Id
     * @param verifyOrg 校验机构即：银行组号
     * @param employeeCode 员工号
     * @param password 密码
     * @param clientNo 客户端类型( PC("100"),ANDROID("200"),IPHONE("300"),IPAD("400"))
     * @return
     */
    public Result employeeCodeVerify (String clientId, String verifyOrg, String employeeCode, String password,String clientNo) throws TException{
		LogCvt.info("银行联合登录帐号发往银行进行校验");
    	
    	ResultVo resultVo = new ResultVo();
    	try {
    		MemberInformationService.Iface mService = 
	    			(MemberInformationService.Iface) ThriftClientProxyFactory.createIface(MemberInformationService.class.getName(),MemberInformationService.class.getSimpleName(), ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
	    	resultVo =  mService.employeeCodeVerify(clientId, verifyOrg, employeeCode, password, clientNo);
	    			
	    	
	    	LogCvt.info("银行联合登录帐号发往银行进行校验返回结果: " + JSonUtil.toJSonString(resultVo));
	    	if (resultVo.getResultCode().equals(ResultCode.failed.getCode())) {
	    		throw new TException(resultVo.getResultDesc()==null?"系统异常":resultVo.getResultDesc());
			}
	    	
    	} catch (TException e) {
			LogCvt.error("银行联合登录帐号发往银行进行校验失败, 原因: " + e.getMessage(), e);
			throw e;
		}
    	return (Result) BeanUtil.copyProperties(Result.class, resultVo);
    }
    
    /**
     * 同步商户编辑后的数据到主表cb_merchant中
     * @Title: synchMerchantEdit 
     * @author ll 20150818  
     * @param bankLabelID 组织机构号
     * @param bankLabelName 机构名称
     * @param state null/0新增          1修改
     * @throws TException
     * @return Result   结果
     * @throws
     */
    public Result synchMerchantEdit(String auditId)throws TException {
    	LogCvt.info("商户编辑信息同步");
    	
    	ResultVo resultVo = new ResultVo();
    	try {
    		MerchantAuditService.Iface bcService = 
	    			(MerchantAuditService.Iface) ThriftClientProxyFactory.createIface(MerchantAuditService.class.getName(),MerchantAuditService.class.getSimpleName(), ThriftConfig.MERCHANT_SERVICE_HOST, ThriftConfig.MERCHANT_SERVICE_PORT);
	    	resultVo =  bcService.synchMerchantEdit(auditId).getResult();
	    	
	    	LogCvt.info("商户编辑信息同步返回结果: " + JSonUtil.toJSonString(resultVo));
    	} catch (TException e) {
    		resultVo.setResultCode(ResultCode.failed.getCode());
    		resultVo.setResultDesc("连接Merchant网络异常");
			LogCvt.error("商户编辑信息同步失败, 原因: " + e.getMessage(), e);
		}
    	return (Result) BeanUtil.copyProperties(Result.class, resultVo);
    }
    
    /**
     * 录入审核：发起白名单同步请求处理.
     * @param merchantIdOroutletId 商户或门店ID
     * @param clientId 客户端Id
     * @param sourceType 类型:0-商户,1-门店,2-其它
     * @param optionType 同步类型:0-录入审核同步,1-修改银行账号白名单同步,2-删除银行账号白名单同步
     * @return ResultVo
     * 
     */
    public Result synchMerchantBankWhiteList(String merchantIdOroutletId, String clientId, String sourceType,String optionType) throws TException{
    	LogCvt.info("商户白名单请求同步");
    	
    	ResultVo resultVo = new ResultVo();
//    	try {
//    		MerchantAccountService.Iface bcService = 
//	    			(MerchantAccountService.Iface) ThriftClientProxyFactory.createIface(MerchantAccountService.class.getName(),MerchantAccountService.class.getSimpleName(), ThriftConfig.MERCHANT_SERVICE_HOST, ThriftConfig.MERCHANT_SERVICE_PORT);
//	    	resultVo =  bcService.syncMerchantBankWhiteList(merchantIdOroutletId, clientId, sourceType, optionType);
//	    	
//	    	LogCvt.info("商户白名单请求同步返回结果: " + JSonUtil.toJSonString(resultVo));
//    	} catch (TException e) {
//    		resultVo.setResultCode(ResultCode.failed.getCode());
//    		resultVo.setResultDesc("连接Merchant网络异常");
//			LogCvt.error("商户白名单请求同步失败, 原因: " + e.getMessage(), e);
//		}
    	return (Result) BeanUtil.copyProperties(Result.class, resultVo);
    }

    
    
    
}
