/**
 * Project Name:Froad Cbank Server Order
 * File Name:Test.java
 * Package Name:com.froad.test.ticket
 * Date:2015年9月1日下午3:25:53
 * Copyright (c) 2015, F-Road, Inc.
 *
*/
/**
 * Project Name:Froad Cbank Server Order
 * File Name:Test.java
 * Package Name:com.froad.test.ticket
 * Date:2015年9月1日 下午3:25:53
 * Copyright (c) 2015, F-Road, Inc.
 *
 */

package com.froad.test.ticket;

import com.froad.enums.TicketStatus;
import com.froad.util.Arith;

/**
 * ClassName:Test
 * Reason:	 TODO ADD REASON.
 * Date:     2015年9月1日 下午3:25:53
 * @author   vania
 * @version  
 * @see 	 
 */
/**
 * ClassName: Test
 * Function: TODO ADD FUNCTION
 * date: 2015年9月1日 下午3:25:53
 *
 * @author vania
 * @version 
 */
public class Test {

	/**
	 * main:(这里用一句话描述这个方法的作用).
	 *
	 * @author vania
	 * 2015年9月1日 下午3:25:53
	 * @param args
	 * 
	 */
	public static void main(String[] args) {
		int totalPage = (int) Math.ceil(44606 / 10.0);
		System.out.println("totalPage==>" + totalPage);
		
		System.out.println(Arith.getDoubleDecimalString(Arith.div(Double.parseDouble("1000"), 1000, 2)));
		
		// TODO Auto-generated method stub
		System.out.println("status====>" + TicketStatus.getDescription(null));
		int i = 0;
		do {
			System.out.println("do while:" + (i++));
		} while (i<0);

		i = 0;
		System.out.println("-------------------" );

		while (i<0) {
			System.out.println("while:" + (i++));

		}
	}

}
