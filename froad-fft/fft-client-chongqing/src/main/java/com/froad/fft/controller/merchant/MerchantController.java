package com.froad.fft.controller.merchant;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.common.SessionKey;
import com.froad.fft.common.UserRoleType;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.dto.MerchantOutletDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.dto.SysUserRoleDto;
import com.froad.fft.support.base.MerchantGroupUserSupport;
import com.froad.fft.support.base.MerchantOutletSupport;
import com.froad.fft.support.base.SysUserRoleSupport;
import com.froad.fft.support.base.SysUserSupport;

/**
 * 商户
 * @author FQ
 *
 */

@Controller
@RequestMapping("/merchant")
public class MerchantController extends BaseController {
	
	@Resource
	private SysUserSupport sysUserSupport;
	
	@Resource
	private MerchantGroupUserSupport merchantGroupUserSupport;
	
	@Resource
	private MerchantOutletSupport merchantOutletSupport;
	

	/**
	 * 主页
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String main(HttpServletRequest req) {

		return "/merchant/index";
	}
	
	
	

	
	
}
