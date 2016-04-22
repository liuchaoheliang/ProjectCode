package com.froad.fft.controller.merchant;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.froad.fft.bean.AjaxCallBackBean;
import com.froad.fft.common.SessionKey;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.support.base.SysUserSupport;

/**
 * 个人资料
 * @author FQ
 *
 */

@Controller
@RequestMapping("/merchant/profile")
public class ProfileController extends BaseController {
	
	
	@Resource
	private SysUserSupport sysUserSupport;
	
	/**
	 * 编辑
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public String edit(HttpServletRequest req,ModelMap model) {
		SysUserDto sysUser=(SysUserDto) sysUserSupport.getCurrentSysUser();
		model.put("user", sysUser);
		return "/merchant/profile/edit";
	}

	/**
	 * 更新
	 */
	@RequestMapping(value = "/update_password", method = RequestMethod.POST)
	public  @ResponseBody AjaxCallBackBean update(HttpServletRequest req,String old_password,String new_password,String require_password) {
		SysUserDto sysUser=(SysUserDto)sysUserSupport.getCurrentSysUser();
		if(!new_password.equals(require_password)){
			logger.info("二次输入的新密码不一致");
			return new AjaxCallBackBean(false,"密码修改失败，二次输入的新密码不一致");
		}else if(!DigestUtils.md5Hex(old_password).equals(sysUser.getPassword())){
			logger.info("用户原密码错误");
			return new AjaxCallBackBean(false,"密码修改失败，输入的原密码错误");
			
		}else if(new_password.equals(old_password)){
			logger.info("原密码与新密码不能够相同");
			return new AjaxCallBackBean(false,"密码修改失败，原密码与新密码不能够相同");
			
		}else{			
			sysUser.setPassword(DigestUtils.md5Hex(new_password));
			boolean flag=sysUserSupport.updateSysUserById(sysUser);
			if(flag){
				return new AjaxCallBackBean(true,"密码修改成功，请重新登录!");
			}
			return new AjaxCallBackBean(false,"密码修改失败!");
		}
	}
	
}
