package com.froad.fft.support.base;

import java.util.List;
import java.util.Map;

import com.froad.fft.enums.TransStatisticType;


public interface BillBoardSupport {

	/**
	*<p>购买榜</p>
	* @author larry
	* @datetime 2014-5-5上午11:42:47
	* @return List<Map<String,Object>>
	* @throws 
	*@param size 取集合数量
	 */
	public  List<Map<String, Object>> getBoard(int size,TransStatisticType transStatisticType);
	
}
