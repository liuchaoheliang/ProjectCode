package com.froad.action.member;


import com.froad.action.support.trans.RepeatMsgActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.authTicket.Result;
/**
 * *******************************************************
 * 
 * @项目名称: communityBusiness_client
 * @类名: RepeatMsg.java
 * @功能描述: 重发验证码短息
 * @作者: 赵肖瑶
 * @日期: 2013-5-6 下午12:33:00
 ********************************************************* 
 */
public class RepeatMsgAction extends BaseActionSupport {

	private String authId;


	private String transId;
	private RepeatMsgActionSupport repeatMsgSupport;

	/**
	 * *******************************************************
	 * 
	 * @函数名: repateGroup
	 * @功能描述: 重发团购验证码
	 * @输入参数: @return <说明>
	 * @返回类型: String
	 * @作者: 赵肖瑶
	 * @日期: 2013-5-6 下午12:35:35
	 * @修改: Zxy
	 * @日期:
	 ********************************************************** 
	 */
	public String repateGroupMsg() {	
		try{
			Integer transid = Integer.parseInt(transId);
			Integer authid = Integer.parseInt(authId);
			log.info("***********申请重发验证短信的交易号为："+transid+"  验证券Id为："+authid+"****************");
			Result result = repeatMsgSupport.repeatMsg(transid,authid);		
			return ajaxJsonSuccessMessage(result.getMsg());
		}catch (Exception e) {
			log.info("***********申请重发验证短信的交易号为："+transId+"  验证券Id为："+authId+"**证券码或交易号错误**************");
			return ajaxJsonSuccessMessage("订单号错误！");
		}
				
	}
	public RepeatMsgActionSupport getRepeatMsgSupport() {
		return repeatMsgSupport;
	}

	public void setRepeatMsgSupport(RepeatMsgActionSupport repeatMsgSupport) {
		this.repeatMsgSupport = repeatMsgSupport;
	}
	public String getAuthId() {
		return authId;
	}
	public void setAuthId(String authId) {
		this.authId = authId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	
}
