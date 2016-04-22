package com.froad.fft.controller.shop.billboard;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.froad.fft.controller.BaseController;
import com.froad.fft.enums.TransStatisticType;
import com.froad.fft.support.base.BillBoardSupport;

@Controller("shop.billBoard")
@RequestMapping("/shop/billboard")
public class BillBoardController extends BaseController {

		@Resource
		private BillBoardSupport billBoardSupport;
		/**
		*<p>排行榜</p>
		* @author larry
		* @datetime 2014-5-4下午02:50:24
		* @return String
		* @throws 
		* @example<br/>
		*
		 */
		@RequestMapping("index")
		public void index(ModelMap modelMap){
			//金额 5位
			modelMap.put("moneyBoard", billBoardSupport.getBoard(5,TransStatisticType.TOTALPRICE));
			//时间 200位
			modelMap.put("timeBoard", billBoardSupport.getBoard(200,TransStatisticType.ORDERTIME));
		}
}
