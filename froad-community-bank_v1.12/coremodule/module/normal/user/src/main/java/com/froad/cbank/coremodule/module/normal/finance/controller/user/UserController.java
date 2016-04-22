package com.froad.cbank.coremodule.module.normal.finance.controller.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.user.annotation.Token;

/**   
 * 用户操作相关接口
 * @author liuhuangle@f-road.com.cn   
 * com.froad.cbank.coremodule.module.normal.finance.controller.user
 * @date 2015-6-15 下午2:27:01
 */
@Controller
@RequestMapping("/finance/user")
public class UserController extends BasicSpringController{

	/**   
	 * 授权绑定接口
	 * 备注：快捷绑定后授权直销银行卡签约
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015-6-15 下午2:27:51  
	 * @param model
	 * @param memberCode  
	 * @throws  
	 */
	@Token
	@RequestMapping(value="/authFinancialBankCard", method=RequestMethod.POST)
	public void authFinancialBankCard(ModelMap model, @RequestHeader Long memberCode){ 
		model.put("memberCode", memberCode);
	}
	
}
