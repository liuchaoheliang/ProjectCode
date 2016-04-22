/**   
 * @author liuhuangle@f-road.com.cn  
 * @date 2015年10月16日 下午1:44:50 
 */
package com.froad.cbank.coremodule.module.normal.merchant.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.module.normal.merchant.enums.UserType;
import com.froad.cbank.coremodule.module.normal.merchant.pojo.MerchantUser;
import com.froad.cbank.coremodule.module.normal.merchant.service.ResourceService;
import com.froad.cbank.coremodule.module.normal.merchant.utils.Constants;
import com.froad.cbank.coremodule.module.normal.merchant.utils.RespError;

@Controller
@RequestMapping(value = "/resource")
public class ResourceController {
	
	@Resource
	private ResourceService resourceService;
	
	/**   
	 * 根据用户查询资源列表
	 * @description 
	 * @author liuhuangle@f-road.com.cn  
	 * @date 2015年10月16日 下午1:51:13  
	 * @param model
	 * @param request 
	 * @param origin : 请求渠道，list表示用户列表访问 
	 * @throws  
	 */
	
	@CheckPermission(keys={"merchant_user_users_menu"})
	@RequestMapping(value = "/byUserInfo", method = RequestMethod.GET)
	public void queryResourceByUserInfo(ModelMap model,HttpServletRequest request,Long userId,String origin){
		try {
 			MerchantUser currentUser=(MerchantUser)request.getAttribute(Constants.MERCHANT_USER);//当前用户
			if(userId==null){//如果未传入，则获取当前用户ID
				userId=currentUser.getId();
			} 
			String merchantRoleId=currentUser.getMerchantRoleId();
			if(UserType.admin.getCode().equals(merchantRoleId) && userId.longValue()!=currentUser.getId().longValue()){//
				merchantRoleId=UserType.normalUser.getCode();
			}
			if(origin!=null && "list".equalsIgnoreCase(origin)){
				model.putAll(resourceService.findResourceByUser(null, userId,merchantRoleId));
			}else{
				model.putAll(resourceService.findResourceByUser(null, currentUser.getId(),userId));
			} 
			model.put("userId", userId);
		 } catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(), e);
		}
	}

}
