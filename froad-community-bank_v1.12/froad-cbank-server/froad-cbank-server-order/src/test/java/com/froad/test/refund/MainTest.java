package com.froad.test.refund;

import com.froad.logic.RefundLogic;
import com.froad.logic.impl.RefundLogicImpl;

public class MainTest {

	static{
		
		System.setProperty("CONFIG_PATH", "./config");
	}
	
	public static void main(String[] args) {
		
		RefundLogic refundLogic = new RefundLogicImpl();

	
		refundLogic.getRefundDetails("anhui", "02119B998000", "01E190108000");
	}
}
