package com.froad.server.tran.transferInfo;

import com.froad.client.trans.Trans;


/**
  * 类描述：交易流转量的公式的 参数生成器
  * @version: 1.0
  * @Copyright www.f-road.com.cn Corporation 2012 
  * @author: 刘丽 liuli@f-road.com.cn
  * @time: 2012-12-20 下午08:59:27
 */
public interface ParaFormulaGenerator {
	/**
	  * 方法描述：获取该交易，流转量的公式参数值
	  * @param: 
	  * @return: 
	  * @version: 1.0
	  * @author: 刘丽 liuli@f-road.com.cn
	 * @throws Exception 
	  * @time: 2012-12-20 下午09:00:26
	 */
	public String getParaFormula(Trans tran) throws Exception;
}
