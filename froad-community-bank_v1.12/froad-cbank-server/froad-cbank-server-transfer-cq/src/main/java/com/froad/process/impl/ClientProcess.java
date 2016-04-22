package com.froad.process.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.enums.BankType;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Checker;
import com.froad.util.ConsoleLogger;
import com.froad.util.Constans;
import com.froad.util.JSonUtil;
import com.froad.util.RedisKeyUtil;

/**
 * 
 * @ClassName: ClientProcess 
 * @Description: 客户端表数据迁移
 * @author: huangyihao
 * @date: 2015年5月1日
 */
public class ClientProcess extends AbstractProcess{
	
	private com.froad.db.chongqing.mappers.ClientMapper sourceClientMapper;
	private com.froad.db.chongqing.mappers.MobileClientMapper sourceMobileClientMapper;
	private com.froad.db.chonggou.mappers.ClientMapper targetClientMapper;
	
	private final String BANK_NAME = "重庆农商行";
	
	public ClientProcess(String name,ProcessServiceConfig config) {
		super(name,config);
	}

	@Override
	public void before() {
		targetClientMapper.deleteByClientId(Constans.clientId);
	}
	
	@Override
	public void begin() {
		super.begin();
		
		// source sql session
		sourceClientMapper = cqSqlSession.getMapper(com.froad.db.chongqing.mappers.ClientMapper.class);
		sourceMobileClientMapper = cqSqlSession.getMapper(com.froad.db.chongqing.mappers.MobileClientMapper.class);
		
		// target session
		targetClientMapper = sqlSession.getMapper(com.froad.db.chonggou.mappers.ClientMapper.class);
	}
	
	@Override
	public void process() {
		List<com.froad.cbank.persistent.entity.Client> sourceClients = null;
		List<com.froad.cbank.persistent.entity.MobileClient> sourceMobileClients = null;
		com.froad.cbank.persistent.entity.Client sourceClient = null;
		com.froad.cbank.persistent.entity.MobileClient sourceMobileClient = null;
		com.froad.db.chonggou.entity.Client targetclient = null;
		boolean result = false;
		
		LogCvt.info("客户端表cb_client 数据迁移开始");
		
		sourceClients = sourceClientMapper.selectByCondition(null);
		sourceMobileClients = sourceMobileClientMapper.selectByCondition(null);
		
		if(Checker.isNotEmpty(sourceClients) && sourceClients.size()==1){
			sourceClient = sourceClients.get(0);
		}
		LogCvt.info("原表cb_client 记录有1条, 数据为: "+JSonUtil.toJSonString(sourceClient));
		
		if(Checker.isNotEmpty(sourceMobileClients) && sourceMobileClients.size()==1){
			sourceMobileClient = sourceMobileClients.get(0);
		}
		LogCvt.info("原表cb_client 记录有1条, 数据为: "+JSonUtil.toJSonString(sourceMobileClient));
		
		targetclient = new com.froad.db.chonggou.entity.Client();
		targetclient.setClientId(Constans.clientId);
		targetclient.setUri("");
		targetclient.setName(ObjectUtils.toString(sourceClient.getName()));
		targetclient.setPointPartnerNo(sourceClient.getPointPartnerNo());
		targetclient.setOpenapiPartnerNo(sourceClient.getOpenapiPartnerNo());
		targetclient.setRemark(sourceClient.getRemark());
		targetclient.setReturnUrl(sourceClient.getReturnUrl());
		targetclient.setOrderDisplay(sourceClient.getOrderDisplay());
		targetclient.setAppkey(ObjectUtils.toString(sourceMobileClient.getAppKey()));
		targetclient.setAppsecret(sourceMobileClient.getAppSecret());
		targetclient.setBankName(BANK_NAME);
		targetclient.setBankType("0");
		targetclient.setBankId("664");//TODO
		targetclient.setSettlePayOrg("264");//TODO
		targetclient.setBankOrg("0");
		targetclient.setBankZH("243");
		
		result = addClient(targetclient);
		ConsoleLogger.info("目标表cb_client 数据迁移"+(result?"成功":"失败"));
		LogCvt.info("客户端表cb_client 数据迁移结束");
	}
	
	private boolean addClient(com.froad.db.chonggou.entity.Client client){
		com.froad.db.chonggou.entity.Client filter = null;
		boolean result = false;
		
		filter = targetClientMapper.findByClientId(client.getClientId());
		if(Checker.isNotEmpty(filter)){
			LogCvt.info("客户端号[clientId:"+client.getClientId()+"] 已存在");
			return false;
		}
		
		/**********************操作MySQL数据库**********************/
		client.setCreateTime(new Date());//获得系统时间
		targetClientMapper.addClient(client); 
		
		/**********************操作Redis缓存**********************/
		//缓存客户端信息
		result=addClientRedis(client);
		LogCvt.info("缓存key为cbbank:client:client_id 数据迁移"+(result?"成功":"失败"));
		if(result){
			sqlSession.commit(true); 
		}
		return result;
	}
	
	private boolean addClientRedis(com.froad.db.chonggou.entity.Client client){
		String key = null;
		Map<String, String> hash = null;
		String result = null;
		
		key = RedisKeyUtil.cbbank_client_client_id(client.getClientId()) ;

		hash = new HashMap<String, String>();
		hash.put("name", client.getName());//客户端Id
		hash.put("point_partner_no", client.getPointPartnerNo());//积分平台商户号
		hash.put("openapi_partner_no", client.getOpenapiPartnerNo());//支付平台商户号
		hash.put("appkey", ObjectUtils.toString(client.getAppkey(), ""));//手机客户端appkey
		hash.put("appsecret", ObjectUtils.toString(client.getAppsecret() , ""));//私钥
		hash.put("order_display", client.getOrderDisplay());//订单显示名
		hash.put("return_url", ObjectUtils.toString(client.getReturnUrl(),""));//支付结果通知地址
		hash.put("bank_name", ObjectUtils.toString(client.getBankName(),""));//银行名称
		hash.put("qr_logo", ObjectUtils.toString(client.getQrLogo(), ""));//银行logo图片地址
		hash.put("bank_type", ObjectUtils.toString(client.getBankType(),""));//银行类型
		hash.put("bank_id", ObjectUtils.toString(client.getBankId(),""));//积分平台银行代码
		hash.put("settle_pay_org", ObjectUtils.toString(client.getSettlePayOrg(),""));//积分平台银行代码
		hash.put("bank_org", ObjectUtils.toString(client.getBankOrg(),""));
		hash.put("bank_ZH", ObjectUtils.toString(client.getBankZH(),""));
		
		result = redis.putMap(key, hash);
		return "OK".equals(result);
	}

}

