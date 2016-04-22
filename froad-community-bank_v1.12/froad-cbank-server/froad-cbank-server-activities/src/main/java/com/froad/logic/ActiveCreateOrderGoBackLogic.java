package com.froad.logic;

import com.froad.po.FailureGoBackReq;
import com.froad.po.Result;

/**
  * @ClassName: ActiveCreateOrderGoBackLogic
  * @Description: 订单创建失败，无条件回退资格
  * @author froad-zengfanting 2015年11月10日
  * @modify froad-lf 2015年12月03日
 */
public interface ActiveCreateOrderGoBackLogic {
	
	public  Result createOrderfailGoBack (FailureGoBackReq failureGoBackReq);

}
