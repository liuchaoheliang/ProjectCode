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
 * @Title: BoolTest.java
 * @Package com.froad.thrift
 * @Description: TODO
 * @author vania
 * @date 2015年6月3日
 */

package com.froad.thrift;

import com.alibaba.fastjson.JSON;
import com.froad.thrift.po.BoolTestPo;
import com.froad.thrift.vo.BoolTestVo;
import com.froad.util.BeanUtil;


/**    
 * <p>Title: BoolTest.java</p>    
 * <p>Description: 描述 </p>   
 * @author vania      
 * @version 1.0    
 * @created 2015年6月3日 下午6:41:11   
 */

public class BoolTest {

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
//		BoolTestVo vo = new BoolTestVo();
//		vo.setIsLike(false);
		
		BoolTestPo po = new BoolTestPo();
//		po.setIsLike(true);
		BoolTestVo vo = (BoolTestVo) BeanUtil.copyProperties(BoolTestVo.class, po);
		
//		BoolTestPo po = (BoolTestPo) BeanUtil.copyProperties(BoolTestPo.class, vo);
//		System.out.println(JSON.toJSONString(po, true));
		
		System.out.println(JSON.toJSONString(vo, true));
		
	}

}
