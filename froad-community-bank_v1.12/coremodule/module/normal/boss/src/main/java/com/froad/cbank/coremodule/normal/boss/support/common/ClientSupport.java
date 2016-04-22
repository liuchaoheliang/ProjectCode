package com.froad.cbank.coremodule.normal.boss.support.common;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.froad.cbank.coremodule.normal.boss.pojo.common.ClientRes;
import com.froad.thrift.service.ClientService;
import com.froad.thrift.vo.ClientVo;

/**
 * 
 * <p>标题: —— 银行客户端</p>
 * <p>说明: —— 操作银行客户端类</p>
 * <p>创建时间：2015-4-30下午5:03:29</p>
 * <p>作者: 高峰</p>
 * <p>版本: 1.0</p>
 * <p>修订历史:（历次修订内容、修订人、修订时间等） </p>
 */
@Service
public class ClientSupport {

	@Resource
	ClientService.Iface clientService;
	
	/**
	 * 
	 * <p>功能简述：—— 获取所有银行客户端列表</p> 
	 * <p>使用说明：—— 方法功能、应用场景、使用注意事项等</p> 
	 * <p>创建时间：2015-4-30下午5:04:17</p>
	 * <p>作者: 高峰</p>
	 * @return
	 * @throws TException 
	 */
	public List<ClientRes> getClient() throws TException {
		ClientVo reqVo = new ClientVo();
		List<ClientRes> list = new ArrayList<ClientRes>();
		//调用server后台接口获取数据
		List<ClientVo> listVo = clientService.getClient(reqVo);
		if(listVo != null && listVo.size() > 0){
			for(ClientVo vo : listVo) {
				ClientRes resC = new ClientRes();
				//copy数据
				resC.setClientId(vo.getClientId());
				resC.setClientName(vo.getBankName());
				resC.setBankOrg(vo.getBankOrg());
				resC.setBankType(vo.getBankType());
				list.add(resC);
			}
		}
		return list;
	}
	
	/**
	 * 根据银行标识获取客户端ID
	 * @param bankOrg
	 * @return
	 * @throws TException
	 */
	public ClientRes getClientByBankOrg(String bankOrg) throws TException {
		ClientRes clientRes = null;
		ClientVo reqVo = new ClientVo();
		reqVo.setBankOrg(bankOrg);
		List<ClientVo> listVo = clientService.getClient(reqVo);//调用server后台接口获取数据
		if(listVo != null && listVo.size() > 0){
			ClientVo clientVo = listVo.get(0);
			clientRes = new ClientRes();
			clientRes.setClientId(clientVo.getClientId());
			clientRes.setClientName(clientVo.getOrderDisplay());
		}
		return clientRes;
	}
}
