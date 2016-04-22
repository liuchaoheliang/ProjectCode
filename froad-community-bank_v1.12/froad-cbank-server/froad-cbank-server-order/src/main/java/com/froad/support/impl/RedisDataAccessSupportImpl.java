package com.froad.support.impl;

import java.util.List;
import java.util.Map;

import com.froad.db.redis.RedisService;
import com.froad.db.redis.impl.RedisManager;
import com.froad.enums.PaymentChannel;
import com.froad.po.Client;
import com.froad.po.ClientPaymentChannel;
import com.froad.support.RedisDataAccessSupport;
import com.froad.util.RedisKeyUtil;
import com.froad.util.payment.BaseSubassembly;


public class RedisDataAccessSupportImpl implements RedisDataAccessSupport{
    
    @Override
    public String acquireOpenAPIPartnerNo(String clientId) {
        return BaseSubassembly.acquireOpenAPIPartnerNo(clientId);
    }

    // --获取对应的银行机构号
    @Override
    public String acquirePaymentOrgNoByType(String clientId,int type) {
    	List<ClientPaymentChannel> channels = getPaymentChannelSetFromRedis(clientId);
        if (channels == null) {
            return null;
        }
        for (ClientPaymentChannel channel : channels) {
            if(channel.getType()==null){
                continue;
            }
            if (type == Integer.parseInt(channel.getType())) {// 银行积分机构号
                return channel.getPaymentOrgNo();
            }
        }
        return null;
    }
    
    
    public ClientPaymentChannel acquireClientPaymentChannel(String clientId,int type){
        List<ClientPaymentChannel> channels = getPaymentChannelSetFromRedis(clientId);
        if (channels == null) {
            return null;
        }
        for (ClientPaymentChannel channel : channels) {
            if(channel.getType()==null){
                continue;
            }
            if (type == Integer.parseInt(channel.getType())) {// 银行积分机构号
                return channel;
            }
        }
        return null;
    }
    
    @Override
    public String acquirePointPartnerNo(String clientId){
        return BaseSubassembly.acquireOpenAPIPartnerNo(clientId);
    }

    private List<ClientPaymentChannel> getPaymentChannelSetFromRedis(String clientId) {
    	return BaseSubassembly.acquireClientPaymentChannelFromRedis(clientId);
    }

    private Client getClientFromRedis(String clientId) {
        return BaseSubassembly.acquireClientFromRedis(clientId);
    }

    private static RedisService getRedisService() {
        return new RedisManager();
    }

    @Override
    public String acquireClientName(String clientId) {
        return getClientFromRedis(clientId).getName();
    }

	@Override
	public String acquireBankName(String clientId) {
		 return getClientFromRedis(clientId).getBankName();
	}

	@Override
	public String acquireBankId(String clientId) {
		return getClientFromRedis(clientId).getBankId();
	}

	@Override
	public String acquirePointsRate(String clientId) {
		ClientPaymentChannel channel = acquireClientPaymentChannel(clientId, PaymentChannel.bank_point.getCode());
		if(channel==null){
			return null;
		}
		return channel.getPointRate();
	}
	
	@Override
	public boolean setPaymentChannel(ClientPaymentChannel channel){
		getRedisService().hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(channel.getClientId(), channel.getPaymentChannelId()), "point_rate", channel.getPointRate());
		getRedisService().hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(channel.getClientId(), channel.getPaymentChannelId()), "client", channel.getClientId());
		getRedisService().hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(channel.getClientId(), channel.getPaymentChannelId()), "type", channel.getType());
		getRedisService().hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(channel.getClientId(), channel.getPaymentChannelId()), "payment_org_no", channel.getPaymentOrgNo());
		getRedisService().hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(channel.getClientId(), channel.getPaymentChannelId()), "name", channel.getName());
		getRedisService().hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(channel.getClientId(), channel.getPaymentChannelId()), "full_name", channel.getFullName());
		getRedisService().hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(channel.getClientId(), channel.getPaymentChannelId()), "ico_url", channel.getIcoUrl());
		getRedisService().hset(RedisKeyUtil.cbbank_client_channel_client_id_payment_channel_id(channel.getClientId(), channel.getPaymentChannelId()), "is_froad_check_token", channel.getIsFroadCheckToken()?"1":"0");
		return true;
	}
}
