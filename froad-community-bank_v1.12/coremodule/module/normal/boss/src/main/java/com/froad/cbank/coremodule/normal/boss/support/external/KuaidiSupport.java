package com.froad.cbank.coremodule.normal.boss.support.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.thrift.TException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.pojo.external.CallBackVo;
import com.froad.cbank.coremodule.normal.boss.pojo.external.ResultItem;
import com.froad.cbank.coremodule.normal.boss.utils.Constants;
import com.froad.cbank.coremodule.normal.boss.utils.HttpClientUtil;
import com.froad.thrift.service.DeliveryWayBillService;
import com.froad.thrift.vo.DeliveryWayBillVo;
import com.froad.thrift.vo.ResultVo;

/**
 * 快递100订阅请求
 * @author yfy
 * @date: 2016年1月11日 上午11:11:18
 */
@Service
public class KuaidiSupport {
	
	@Resource
	private DeliveryWayBillService.Iface deliveryWayBillService;
	/**
	 * 订阅请求
	 * @author yfy
	 * @date: 2016年1月11日 上午11:31:42
	 * @param voReq
	 * @return
	 */
	public void sendKuaidiSubscribe(String corpCode,String shippingId,String address){
		LogCvt.info("开始发送订阅请求");
		
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("company", corpCode);//公司编码
		jsonObj.put("number", shippingId);//快递单号
		jsonObj.put("from", "");//出发城市
		jsonObj.put("to", address);//目的地
		jsonObj.put("key", Constants.get("boss.kuaidi.key"));//授权密匙(Key):aHpmOsSO3530
		Map<String,String> data = new HashMap<String,String>();
		data.put("callbackurl", Constants.get("boss.kuaidi.url"));//回调地址
		data.put("salt", "");
		data.put("resultv2", "1");
		jsonObj.put("parameters", data);

		String content="schema=json&param="+jsonObj.toString();
		JSONObject resJson = HttpClientUtil.doPostForm(Constants.get("http.kuaidi.url"), content);
		
		if(resJson.getString("result").equals("true")){
			LogCvt.info("订阅成功");
		}else{
			LogCvt.info("订阅失败：returnCode："+resJson.getString("returnCode")
					+",message:"+resJson.getString("message"));
		}
		
	}
	
	/**
	 * 快递100回调接口
	 * @author yfy
	 * @date: 2016年1月11日 下午13:10:09
	 * @param callBack
	 * @return
	 * @throws TException
	 */
	public HashMap<String, Object> callback(CallBackVo callBack) throws TException{
		HashMap<String, Object> map = new HashMap<String, Object>();
		DeliveryWayBillVo billVo = new DeliveryWayBillVo();
		billVo.setShippingCorpCode(callBack.getLastResult().getCom());//物流公司编号
		billVo.setShippingId(callBack.getLastResult().getNu());//单号
		//签收状态0在途中、1已揽收、2疑难、3已签收、4退签、5同城派送中、6退回、7转单等7个状态
		billVo.setStatus(callBack.getLastResult().getState());
		String message = "";
		ArrayList<ResultItem> resultList = callBack.getLastResult().getData();
		if(resultList != null && resultList.size() > 0){
			for(ResultItem item : resultList){
				if(StringUtil.isNotBlank(message)){
					message += "##" + item.getFtime() +"@@"+ item.getContext();
				}else{
					message += item.getFtime() +"@@"+ item.getContext();
				}
			}
			billVo.setMessage(message);
		}
		ResultVo resultVo = deliveryWayBillService.updateDeliveryWayBill(billVo);
		if(resultVo.getResultCode().equals(Constants.RESULT_SUCCESS)){
			map.put("result",true);
			map.put("returnCode","200");
			map.put("message","成功");
		}else{
			map.put("result",false);
			map.put("returnCode",resultVo.getResultCode());
			map.put("message",resultVo.getResultDesc());
		}
		return map;
	}
}
