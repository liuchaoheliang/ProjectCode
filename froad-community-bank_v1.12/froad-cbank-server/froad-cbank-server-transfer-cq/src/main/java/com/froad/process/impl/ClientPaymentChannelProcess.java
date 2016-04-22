package com.froad.process.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.ObjectUtils;

import com.froad.config.ProcessServiceConfig;
import com.froad.db.chonggou.entity.ClientPaymentChannel;
import com.froad.db.chonggou.mappers.ClientPaymentChannelMapper;
import com.froad.enums.ModuleID;
import com.froad.logback.LogCvt;
import com.froad.process.AbstractProcess;
import com.froad.util.Constans;
import com.froad.util.RedisKeyUtil;
import com.froad.util.SimpleID;

/**
 * 
 * @ClassName: ClientPaymentChannelProcess 
 * @Description: 客户端支付渠道表数据迁移
 * @author: huangyihao
 * @date: 2015年5月1日
 */
public class ClientPaymentChannelProcess extends AbstractProcess {
	
	private ClientPaymentChannelMapper clientPaymentChannelMapper;
	
	private SimpleID simpleID = new SimpleID(ModuleID.paymentchannel);
	
	private final String ICO_URL = "/image/froad-cb/froad-ico/ah_logo.png";
	
	public ClientPaymentChannelProcess(String name, ProcessServiceConfig config) {
		super(name, config);
	}

	
	@Override
	public void before() {
		
		
	}
	
	
	
	@Override
	public void process() {
		LogCvt.info("客户端支付渠道表cb_client_payment_channel 数据迁移开始");
		clientPaymentChannelMapper = sqlSession.getMapper(ClientPaymentChannelMapper.class);
		
		ClientPaymentChannel client = new ClientPaymentChannel();
		client.setClientId(Constans.clientId);
		
		List<ClientPaymentChannel> clientPaymentList = clientPaymentChannelMapper.findClientPaymentChannel(client);
		for(ClientPaymentChannel clientPaymentChannel : clientPaymentList){
			addClientPaymentChannelRedis(clientPaymentChannel);
			addPaymentChannel(clientPaymentChannel);
		}
	}
	
	/**
	 * 设置所有fields的value值
	 * 
	 * @Description: 使用Redis的哈希结构存储HSET key field value
	 * key为cbbank:client_channel:client_id:payment_channel_id
	 * field包含name/full_name/type/ico_url/payment_org_no/is_froad_check_token/point_rate
	 * @author ll 20150320
	 * @param ClientPaymentChannel支付渠道对象 
	 * @return boolean 是否操作成功
	 * @throws
	 */
	private void addClientPaymentChannelRedis(ClientPaymentChannel clientPaymentChannel){
		String key = RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(clientPaymentChannel.getClientId(), clientPaymentChannel.getPaymentChannelId()) ;

		Map<String, String> hash = new HashMap<String, String>();
		hash.put("name", clientPaymentChannel.getName());//资金机构名称
		hash.put("full_name", clientPaymentChannel.getFullName());//资金机构全名
		hash.put("type", clientPaymentChannel.getType());//渠道类型
		hash.put("ico_url", ObjectUtils.toString(clientPaymentChannel.getIcoUrl(),""));//支付方式图标
		hash.put("payment_org_no", ObjectUtils.toString(clientPaymentChannel.getPaymentOrgNo(), ""));//资金机构代号（⽀支付系统）
		hash.put("is_froad_check_token", BooleanUtils.toString(clientPaymentChannel.getIsFroadCheckToken(), "1", "0",""));//是否方付通验码
		hash.put("point_rate", clientPaymentChannel.getPointRate());//积分兑换比例(实时获取)

		String result = redis.putMap(key, hash);
		boolean flag = "OK".equals(result);
		LogCvt.info("缓存key==>"+key+" 数据迁移"+(flag?"成功":"失败"));
	}
	
	/**
	 * 设置Set集合中的payment_channel_id
	 * 
	 * @Description:使用Redis的集合结构存储SADD key value
	 * key为cbbank:client_channels:client_id
	 * value为payment_channel_id
	 * @author ll 20150323
	 * @param clientPaymentChannels支付渠道对象
	 * @return boolean是否操作成功
	 * @throws
	 */
	private void addPaymentChannel(ClientPaymentChannel clientPaymentChannel){
		/* 缓存该客户端下支付渠道Set集合信息 */
		String key = RedisKeyUtil.cbbank_client_channels_client_id(clientPaymentChannel.getClientId()) ;
		
		Set<String> valueSet = new HashSet<String>();
		valueSet.add(String.valueOf(clientPaymentChannel.getPaymentChannelId()));
		redis.putSet(key, valueSet);
	}
	
	
	public static void main(String[] args) {
		
		String s1 = "方付通分分通积分";
		String s2 = "方付通";
		
		int i = s1.indexOf(s2);
		System.out.println(i);
	}

}