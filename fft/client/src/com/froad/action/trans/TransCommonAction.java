package com.froad.action.trans;

import org.apache.log4j.Logger;

import com.froad.action.support.TransActionSupport;
import com.froad.baseAction.BaseActionSupport;
import com.froad.client.trans.Trans;


	/**
	 * 类描述：交易的公共action
 	 * @version: 1.0
	 * @Copyright www.f-road.com.cn Corporation 2013 
	 * @author: 李金魁 lijinkui@f-road.com.cn
	 * @time: Apr 18, 2013 2:03:54 PM 
	 */
public class TransCommonAction extends BaseActionSupport{

	
	/* serialVersionUID: serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	private static final Logger logger=Logger.getLogger(TransCommonAction.class);

	private TransActionSupport transActionSupport;
	
	private Trans trans;
	
	private Integer transId;
	
	/**
	  * 方法描述：异步查询交易结果
	  * @param: transId
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: Apr 18, 2013 2:01:38 PM
	  */
	public void queryTransResultByAjax(){
		logger.info("开始异步查询交易结果，交易号为："+transId);
		if(transId==null){
			sendMsg("notExist");
			return;
		}
		String result=transActionSupport.queryTransResult(transId);
		logger.info("结束异步查询交易结果，交易号为："+transId+"结果为："+result);
		sendMsg(result);
	}
	
	
	/**
	  * 方法描述：异步查询收款结果
	  * @param: transId
	  * @return: void
	  * @version: 1.0
	  * @author: 李金魁 lijinkui@f-road.com.cn
	  * @time: May 8, 2013 3:43:58 PM
	  */
	public void queryCollectResultByAjax(){
		logger.info("开始异步查询收款结果，交易号为："+transId);
		if(transId==null){
			sendMsg("notExist");
			return;
		}
		String result=transActionSupport.queryCollectResult(transId+"");
		logger.info("结束异步查询收款结果，交易号为："+transId+"结果为："+result);
		sendMsg(result);
	}

	public void setTransActionSupport(TransActionSupport transActionSupport) {
		this.transActionSupport = transActionSupport;
	}

	public Trans getTrans() {
		return trans;
	}

	public void setTrans(Trans trans) {
		this.trans = trans;
	}

	public Integer getTransId() {
		return transId;
	}

	public void setTransId(Integer transId) {
		this.transId = transId;
	}
}
