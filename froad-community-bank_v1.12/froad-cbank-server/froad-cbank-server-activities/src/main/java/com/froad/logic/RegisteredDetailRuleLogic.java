/**
 * @Title: RegisteredDetailRuleLogic.java
 * @Package com.froad.logic
 * @Description: TODO
 * Copyright:2015 F-Road All Rights Reserved   
 * Company:f-road.com.cn
 * 
 * @creater froad-Joker 2015年12月2日
 * @version V1.0
**/

package com.froad.logic;

import com.froad.po.RegistDetailRule;
import com.froad.thrift.vo.ResultVo;

 /**
 * @ClassName: RegisteredDetailRuleLogic
 * @Description: TODO
 * @author froad-Joker 2015年12月2日
 * @modify froad-Joker 2015年12月2日
 */

public interface RegisteredDetailRuleLogic {
	
	public ResultVo verification(ResultVo resultVo,RegistDetailRule registeredDetailRule);

}
