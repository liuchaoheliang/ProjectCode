/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: Support.java
 * @Package com.froad.support
 * @Description: TODO
 * @author vania
 * @date 2015年4月10日
 */

package com.froad.support;

import org.apache.thrift.TException;

import com.froad.enums.ResultCode;
import com.froad.logback.LogCvt;
import com.froad.po.ClientMerchantAudit;
import com.froad.po.Result;
import com.froad.thread.FroadThreadPool;
import com.froad.thrift.client.ThriftClientProxyFactory;
import com.froad.thrift.service.AreaService;
import com.froad.thrift.service.BankCardService;
import com.froad.thrift.service.ClientMerchantAuditService;
import com.froad.thrift.service.ProductService;
import com.froad.thrift.service.QrCodeService;
import com.froad.thrift.service.SmsMessageService;
import com.froad.thrift.vo.AreaVo;
import com.froad.thrift.vo.MerchantBankWhiteVo;
import com.froad.thrift.vo.OutletVo;
import com.froad.thrift.vo.QrCodeRequestVo;
import com.froad.thrift.vo.QrCodeResponseVo;
import com.froad.thrift.vo.ResultVo;
import com.froad.thrift.vo.SmsMessageResponseVo;
import com.froad.thrift.vo.SmsMessageVo;
import com.froad.util.BeanUtil;
import com.froad.util.JSonUtil;
import com.froad.util.ThriftConfig;


/**    
 * <p>Title: Support.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月10日 下午10:50:02   
 */

public class Support {
	
	
	/**
     * 获取市,区机构
     * @param clientId
     * @param productId
     * @param qrCodeType
     * @return String 市,区机构
     */
//    public String getSuperOrgCodeByType(String clientId, String orgCode)throws TException {
//        OrgService.Iface client = (OrgService.Iface) ThriftClientProxyFactory.createIface(OrgService.class, ThriftConfig.BANK_SERVICE_HOST, ThriftConfig.BANK_SERVICE_PORT);
//		
//        return client.getSuperOrgCodeByType(clientId, orgCode);
//    }
    
    /**
     * 根据条件查询机构
     * @Title: getOrgCode 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param clientId
     * @param orgCode
     * @return
     * @throws TException
     * @return Org    返回类型 
     * @throws
     */
//    public Org getOrgCode(String clientId, String orgCode)throws TException {
//        OrgService.Iface client = (OrgService.Iface) ThriftClientProxyFactory.createIface(OrgService.class, ThriftConfig.BANK_SERVICE_HOST, ThriftConfig.BANK_SERVICE_PORT);
//		
//        return (Org)BeanUtil.copyProperties(Org.class, client.getOrgById(clientId, orgCode));
//    }
    
    /**
     * 查询
     * @Title: getClientMerchantAudit 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param clientId
     * @param orgCode
     * @return
     * @throws TException
     * @return ClientMerchantAudit    返回类型 
     * @throws
     */
    public ClientMerchantAudit getClientMerchantAudit(String clientId, String orgCode)throws TException {
    	ClientMerchantAuditService.Iface client = (ClientMerchantAuditService.Iface) ThriftClientProxyFactory.createIface(ClientMerchantAuditService.class, ThriftConfig.BANK_SERVICE_HOST, ThriftConfig.BANK_SERVICE_PORT);
		
        return (ClientMerchantAudit)BeanUtil.copyProperties(ClientMerchantAudit.class, client.getClientMerchantAuditByOrgCode(clientId, orgCode,"1"));
    }
    
    /**
     * 根据机构码查询子机构码
     * @Title: getAllSubOrgCodes 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param clientId
     * @param orgCode
     * @return
     * @throws TException
     * @return List<String>    返回类型 
     * @throws
     */
//    public List<String> getAllSubOrgCodes(String clientId, String orgCode)throws TException {
//    	OrgService.Iface client = (OrgService.Iface) ThriftClientProxyFactory.createIface(OrgService.class, ThriftConfig.BANK_SERVICE_HOST, ThriftConfig.BANK_SERVICE_PORT);
////        List<Org> list = (List<Org>)BeanUtil.copyProperties(Org.class,client.getAllSubOrgs(clientId, orgCode));
//    	
//    	List<String> orgCodeList = client.getAllSubOrgCodes(clientId, orgCode);
//    	
//    	return orgCodeList;
//    }
    
    public AreaVo getAreaById(long id)throws TException {
    	AreaService.Iface client = (AreaService.Iface) ThriftClientProxyFactory.createIface(AreaService.class, ThriftConfig.SUPPORT_SERVICE_HOST, ThriftConfig.SUPPORT_SERVICE_PORT);
    	return  client.findAreaById(id); 
    }
    
    /**
     * 添加银行账号白名单
     * @Title: addMerchantBankWhiteList 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param merchantIdOrOutletId
     * @param merchantNameOrOutletName
     * @param accountNo
     * @param clientId
     * @param accountName
     * @return
     * @throws TException
     * @return boolean    返回类型 
     * @throws
     */
    public Result addMerchantBankWhiteList(MerchantBankWhiteVo merchantBankWhiteVo)throws TException{
    	merchantBankWhiteVo.setOptionType("0");
    	LogCvt.info("添加银行账号白名单");
    	return (Result) this.setMerchantBankWhiteList(merchantBankWhiteVo);
    }
    
    /**
     * 修改银行账号白名单
     * @Title: updateMerchantBankWhiteList 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param merchantIdOrOutletId
     * @param merchantNameOrOutletName
     * @param accountNo
     * @param clientId
     * @param accountName
     * @return
     * @throws TException
     * @return boolean    返回类型 
     * @throws
     */
    public Result updateMerchantBankWhiteList(MerchantBankWhiteVo merchantBankWhiteVo)throws TException {
    	LogCvt.info("修改银行账号白名单");
    	merchantBankWhiteVo.setOptionType("1");
    	return (Result) this.setMerchantBankWhiteList(merchantBankWhiteVo);
    }
    
    /**
     * 删除银行账号白名单
     * @Title: deleteMerchantBankWhiteList 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param merchantIdOrOutletId
     * @param merchantNameOrOutletName
     * @param accountNo
     * @param clientId
     * @param accountName
     * @return
     * @throws TException
     * @return boolean    返回类型 
     * @throws
     */
	public Result deleteMerchantBankWhiteList(MerchantBankWhiteVo merchantBankWhiteVo)throws TException {
    	LogCvt.info("删除银行账号白名单");
    	merchantBankWhiteVo.setOptionType("2");
    	return (Result) this.setMerchantBankWhiteList(merchantBankWhiteVo);
    }
    
    /**
     * 审核设置银行账号白名单.
     * @Title: setMerchantBankWhiteList 
     * @author vania
     * @version 1.0
     * @see: TODO
     * @param merchantBankWhiteVo     
     * @return
     * @throws TException
     * @return Result    返回类型 
     * @throws
     */
	public Result setMerchantBankWhiteList(final MerchantBankWhiteVo merchantBankWhiteVo) throws TException {
		LogCvt.info("同步设置银行账号白名单开始");
		BankCardService.Iface client = (BankCardService.Iface) ThriftClientProxyFactory.createIface(BankCardService.class, ThriftConfig.ORDER_SERVICE_HOST, ThriftConfig.ORDER_SERVICE_PORT);
		Result result = new Result();
		try {
			ResultVo resultVo = client.setMerchantBankWhiteList(merchantBankWhiteVo.getMerchantIdOrOutletId(),merchantBankWhiteVo.getMerchantNameOrOutletName(),merchantBankWhiteVo.getAccountNo(),	
					merchantBankWhiteVo.getMac(),merchantBankWhiteVo.getOptionType(),merchantBankWhiteVo.getClientId(),	merchantBankWhiteVo.getAccountName());
			result = (Result) BeanUtil.copyProperties(Result.class, resultVo);
			LogCvt.info("设置银行账号白名单返回结果: " + JSonUtil.toJSonString(result));
			if(result.getResultCode().equals(ResultCode.success.getCode())){
				result.setResultCode(ResultCode.success.getCode());
				result.setResultDesc("设置银行账户白名单成功");
			}else{
				LogCvt.error("设置银行账号白名单处理失败："+result.getResultDesc());
			}
		} catch (TException e) {
			LogCvt.error("设置银行账号白名单异常, 原因: " + e.getMessage(), e);
			result.setResultCode(ResultCode.failed.getCode());
			result.setResultDesc(e.getMessage());
		}
		LogCvt.info("同步设置银行账号白名单结束");
		 return result;
	}
	
	/**
	 * 发送短信接口.
	 * 
	 * @param smsMessageVo 短信消息Vo对象
	 * @throws TException
	 */
	public void sendSmsMessage(final SmsMessageVo smsMessageVo)throws TException{
		LogCvt.info("异步发送短信开始");
		FroadThreadPool.execute(new Runnable() {
			@Override
			public void run() {	
				SmsMessageService.Iface client = (SmsMessageService.Iface) ThriftClientProxyFactory.createIface(SmsMessageService.class, ThriftConfig.SUPPORT_SERVICE_HOST, ThriftConfig.SUPPORT_SERVICE_PORT);
				try {
						SmsMessageResponseVo resultVo = client.sendSMS(smsMessageVo);
						if(ResultCode.success.getCode().equals(resultVo.getResultCode())){
								LogCvt.info("同步白名单,异步发送短信,客户端Id："+smsMessageVo.getClientId()+",发送手机号:"+smsMessageVo.getMobile()+"发送短信成功");
						}
					} catch (TException e) {
					LogCvt.error("异步发送短信失败, 原因: " + e.getMessage(), e);
				}		
			}
		});
		LogCvt.info("异步发送短信结束");
	}
	
	/**
	 * 商品关联门店接口.
	 * @param clientId
	 * @param optionType
	 * @param outletVo
	 */
	public void updateGroupProductByOutlet(String clientId,String optionType,OutletVo outletVo,Result result)throws TException{
		try{
			ProductService.Iface client = (ProductService.Iface) ThriftClientProxyFactory.createIface(ProductService.class,ThriftConfig.GOODS_SERVICE_HOST,ThriftConfig.GOODS_SERVICE_PORT);
			ResultVo resultVo = client.updateGroupProductByOutlet(clientId,optionType, outletVo);
			result =(Result) BeanUtil.copyProperties(Result.class, resultVo);
			if (ResultCode.success.getCode().equals(resultVo.getResultCode())) {
				LogCvt.info("修改商品关联门店成功,客户端Id：" + clientId + ",outletId:"+ outletVo.getOutletId());
			}else{
				LogCvt.info("修改商品关联门店失败,客户端Id：" + clientId + ",outletId:"+ outletVo.getOutletId());
			}
		} catch (TException ex) {
			LogCvt.error("修改商品关联门店失败, 原因: " + ex.getMessage(), ex);
		}
	}
	
	/**
	 * 获取二维码
	 * @param qrCodeRequestVo
	 * @return
	 * @throws TException
	 */
	public QrCodeResponseVo getQRCode(QrCodeRequestVo qrCodeRequestVo)throws TException {
		QrCodeService.Iface client = (QrCodeService.Iface) ThriftClientProxyFactory.createIface(QrCodeService.class, ThriftConfig.QRCODE_SERVICE_HOST, ThriftConfig.QRCODE_SERVICE_PORT);
		return  client.retrieveQrCode(qrCodeRequestVo);
    }
}