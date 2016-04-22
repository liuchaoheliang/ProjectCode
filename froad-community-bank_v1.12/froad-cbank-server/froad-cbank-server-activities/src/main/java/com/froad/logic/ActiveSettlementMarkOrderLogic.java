package com.froad.logic;

import com.froad.thrift.vo.active.SettlementMarkOrderReq;
import com.froad.thrift.vo.active.SettlementMarkOrderRes;


/**
  * @ClassName: ActiveSettlementMarkOrderLogic
  * @Description: 
  * @author froad-zengfanting 2015年11月13日
  * @modify froad-zengfanting 2015年11月13日
 */
public interface ActiveSettlementMarkOrderLogic {
	 public SettlementMarkOrderRes settlementMarkOrder(SettlementMarkOrderReq settlementMarkOrderReq);
	    
}
