package com.froad.fft.controller.member;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.froad.fft.controller.BaseController;

/**
 * 会员
 * @author FQ
 *
 */

@Controller("member")
@RequestMapping("/member")
public class MemberController extends BaseController {

	/**
	 * *******************************************************
	 *<p> 描述: *-- <b>进入个人中心/b> --* </p>
	 *<p> 作者: 赵肖瑶 zhaoxiaoyao@f-road.com.cn </p>
	 *<p> 时间: 2014年3月28日 上午11:48:33 </p>
	 *<p> 版本: 1.0.1 </p>
	 *********************************************************
	 */
	@RequestMapping(value = "index" , method = RequestMethod.GET)
	public void index(){}
	
}
