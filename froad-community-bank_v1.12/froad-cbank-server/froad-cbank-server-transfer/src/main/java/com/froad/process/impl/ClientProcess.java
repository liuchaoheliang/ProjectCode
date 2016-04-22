package com.froad.process.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.ahui.mappers.SysClientMapper;
import com.froad.db.ahui.mappers.SysMobileClientMapper;
import com.froad.db.chonggou.entity.Client;
import com.froad.db.chonggou.mappers.ClientMapper;
import com.froad.enums.BankType;
import com.froad.fft.persistent.entity.SysClient;
import com.froad.fft.persistent.entity.SysMobileClient;
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
	
	private SysClientMapper sysClientMapper;
	private SysMobileClientMapper sysMobileClientMapper;
	private ClientMapper clientMapper;
	
	private final String BANK_NAME = "安徽银行";
	
	public ClientProcess(String name,ProcessServiceConfig config) {
		super(name,config);
	}

	@Override
	public void process() {
		LogCvt.info("客户端表cb_client 数据迁移开始");
		sysClientMapper = ahSqlSession.getMapper(SysClientMapper.class);
		sysMobileClientMapper = ahSqlSession.getMapper(SysMobileClientMapper.class);
		clientMapper = sqlSession.getMapper(ClientMapper.class);
		
		List<SysClient> sysClients = sysClientMapper.selectAllSysClient();
		List<SysMobileClient> sysMobileClients = sysMobileClientMapper.selectByCondition(null);
		
		SysClient sysClient = null;
		if(Checker.isNotEmpty(sysClients) && sysClients.size()==1){
			sysClient = sysClients.get(0);
		}
		LogCvt.info("原安徽表fft_sys_client 记录有1条, 数据为: "+JSonUtil.toJSonString(sysClient));
		
		SysMobileClient sysMobileClient = null;
		if(Checker.isNotEmpty(sysMobileClients) && sysMobileClients.size()==1){
			sysMobileClient = sysMobileClients.get(0);
		}
		LogCvt.info("原安徽表fft_sys_mobile_client 记录有1条, 数据为: "+JSonUtil.toJSonString(sysMobileClient));
		
		Client client = new Client();
		
		client.setClientId(Constans.clientId);
		client.setUri("");
		client.setName(ObjectUtils.toString(sysClient.getName()));
		client.setPointPartnerNo(sysClient.getPartnerId());
		client.setOpenapiPartnerNo(sysClient.getPartnerId());
		client.setRemark(sysClient.getRemark());
		client.setReturnUrl(sysClient.getReturnUrl());
		client.setOrderDisplay(sysClient.getOrderDisplay());
		client.setAppkey(ObjectUtils.toString(sysMobileClient.getAppkey()));
		client.setAppsecret(sysMobileClient.getAppsecret());
		client.setBankName(BANK_NAME);
		client.setBankType(BankType.province_city.getType());
		client.setBankId("664");
		client.setSettlePayOrg("264");
		
		boolean result = addClient(client);
		ConsoleLogger.info("目标表cb_client 数据迁移"+(result?"成功":"失败"));
		LogCvt.info("客户端表cb_client 数据迁移结束");
	}
	
	
	private boolean addClient(Client client){
		Client filter = clientMapper.findByClientId(client.getClientId());
		if(Checker.isNotEmpty(filter)){
			LogCvt.info("客户端号[clientId:"+client.getClientId()+"] 已存在");
			return false;
		}
		
		/**********************操作MySQL数据库**********************/
		client.setCreateTime(new Date());//获得系统时间
		clientMapper.addClient(client); 
		
		/**********************操作Redis缓存**********************/
		//缓存客户端信息
		boolean result=addClientRedis(client);
		LogCvt.info("缓存key为cbbank:client:client_id 数据迁移"+(result?"成功":"失败"));
		if(result){
			sqlSession.commit(true); 
		}
		return result;
	}
	
	private boolean addClientRedis(Client client){
		String key = RedisKeyUtil.cbbank_client_client_id(client.getClientId()) ;

		Map<String, String> hash = new HashMap<String, String>();
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
		
		String result = redis.putMap(key, hash);
		return "OK".equals(result);
	}
	
}

