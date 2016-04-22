/*   
 * Copyright © 2008 F-Road All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */
  
/**  
 * @Title: BeanTest.java
 * @Package com.froad.test
 * @Description: TODO
 * @author vania
 * @date 2015年4月18日
 */

package com.froad.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.froad.thirdparty.bean.PointInfoDto;
import com.froad.thirdparty.dto.request.points.UserEnginePointsRecord;
import com.froad.thirdparty.enums.ProtocolType;
import com.froad.util.BeanUtil;


/**    
 * <p>Title: BeanTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年4月18日 下午5:38:17   
 */

public class BeanTest {

	/** 
	 * @Title: main 
	 * @author vania
	 * @version 1.0
	 * @see: TODO
	 * @param args
	 * @return void    返回类型 
	 * @throws 
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		List<PointInfoDto> list = new ArrayList<PointInfoDto>();
		System.out.println(list.getClass());
		PointInfoDto pd = new PointInfoDto();
		pd.setAccountNo("yamadie");
		Map<String, String> map = new HashMap<String, String>();
		map.put("abc", "yoxi");
		map.put("dev", "654654");
		pd.setExtension(map);
//		pd.setProtocolType(ProtocolType.cardDepositPoints);
		
		list.add(pd);
		List<UserEnginePointsRecord> dest = (List<UserEnginePointsRecord>) BeanUtil.copyProperties(UserEnginePointsRecord.class, list,new String[]{ "protocolType"});
		System.out.println(JSON.toJSONString(dest, true));
	}

}
