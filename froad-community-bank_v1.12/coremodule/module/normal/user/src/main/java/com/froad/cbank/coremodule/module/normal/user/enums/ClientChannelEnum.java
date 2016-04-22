package com.froad.cbank.coremodule.module.normal.user.enums;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.http.HttpHeaders;

import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.pay.user.helper.BankOrg;
import com.pay.user.helper.ClientChannel;

/**
 * 客户端渠道
 */
public enum ClientChannelEnum {

	anhui("anhui","m",ClientChannel.AH_H5,BankOrg.BANK_AH),
	chongqing("chongqing","m",ClientChannel.CQ_H5,BankOrg.BANK_CQ),
	taizhou("taizhou","m",ClientChannel.TZ_H5,BankOrg.BANK_TZ),
	jilin("jilin","m",ClientChannel.JL_H5,BankOrg.BANK_JLNX),
	xingtai("xingtai","m",ClientChannel.XT_H5,BankOrg.BANK_XTYH)
	;
	
	private String client;
	private String channel;
	private ClientChannel clientChannel;
	private BankOrg bankOrg;
	
	private ClientChannelEnum(String client, String channel, ClientChannel clientChannel, BankOrg bankOrg){
		this.client = client;
		this.channel = channel;
		this.clientChannel = clientChannel;
		this.bankOrg = bankOrg;
	}
	
	
	public String getClient() {
		return client;
	}
	public void setClient(String client) {
		this.client = client;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public ClientChannel getClientChannel() {
		return clientChannel;
	}
	public void setClientChannel(ClientChannel clientChannel) {
		this.clientChannel = clientChannel;
	}
	public BankOrg getBankOrg() {
		return bankOrg;
	}
	public void setBankOrg(BankOrg bankOrg) {
		this.bankOrg = bankOrg;
	}


	/**
	 * 根据clientId获取客户端所属银行机构<br>
	 * 此方法只返回单个实例，不考虑渠道（如手机，PC）<br>
	 * 只用于获取机构数据
	 * @param client
	 * @return {@link BankOrg}
	 */
	public static BankOrg getClientBankOrg(String client){
		
		for(ClientChannelEnum cEnum : ClientChannelEnum.values()){
			if(cEnum.getClient().equals(client)){
				return cEnum.getBankOrg();
			}
		}
		return null;
	}
	
	/**
	 * 根据clientId获取客户端
	 * 此方法只返回单个实例，不考虑渠道（如手机，PC）<br>
	 * 只用于获取客户端
	 * @param client
	 * @return List<ClientChannelName>
	 */
	public static ClientChannelEnum getClientChannelEnum(String client){
		
		List<ClientChannelEnum> list = getClientListByClient(client);
		if(list.size() > 0){
			return list.get(0);
		}else{
			return null;
		}
	}
	
	

	/**
	 * 根据clientId获取客户端列表
	 * @param client
	 * @return List<ClientChannelName>
	 */
	public static List<ClientChannelEnum> getClientListByClient(String client){
		
		List<ClientChannelEnum> list = new ArrayList<ClientChannelEnum>();
		for(ClientChannelEnum cEnum : ClientChannelEnum.values()){
			if(cEnum.getClient().equals(client)){
				list.add(cEnum);
			}
		}
		return list;
	}
	
	/**
	 * 获取客户端渠道名
	 * @param request
	 * @return
	 */
	public static ClientChannel getClientChannel(HttpServletRequest request) {
		String referer = request.getHeader(HttpHeaders.REFERER);
		//https://test.ubank365.com/anhui/m/vip...
		//https://test.ubank365.com/anhui/vip...
		if(referer ==null || "".equals(referer)) {
	    	LogCvt.info("Http referer is null");
		    return null;
	    }
    	
    	String[] client = referer.split("/");
    	
    	if(client.length < 4 && (client[3]==null || "".equals(client[3]))) {
			LogCvt.info("Http referer is wrong：" + referer);
			return null;
    	}
    	
    	List<ClientChannelEnum> clientList = getClientListByClient(client[3]);
    	if(clientList.size() == 0){
    		LogCvt.info("client is wrong：" + client[3]);
    		return null;
    	}else if(clientList.size() == 1){
    		return clientList.get(0).getClientChannel();
    	}else if(client[4] != null || !client[4].equals("")){
    		for(ClientChannelEnum clientEnum : clientList){
        		if(clientEnum.getChannel().equals(client[4])){
        			return clientEnum.getClientChannel();
        		}
        	}
    	}
    	
		return null;
	}
	
	
	
}
