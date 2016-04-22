
	 /**
  * 文件名：SaleRankController.java
  * 版本信息：Version 1.0
  * 日期：2014年4月29日
  * author: 刘超 liuchao@f-road.com.cn
  */
package com.froad.fft.controller.merchant;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.froad.fft.bean.page.PageDto;
import com.froad.fft.controller.BaseController;
import com.froad.fft.dto.MerchantGroupUserDto;
import com.froad.fft.dto.SysUserDto;
import com.froad.fft.dto.TransQueryDto;
import com.froad.fft.enums.trans.TransType;
import com.froad.fft.support.base.MerchantGroupUserSupport;
import com.froad.fft.support.base.MerchantOutletSupport;
import com.froad.fft.support.base.SysUserSupport;


	/**
 * 类描述：
 * @version: 1.0
 * @Copyright www.f-road.com.cn Corporation 2014 
 * @author: 刘超 liuchao@f-road.com.cn
 * @time: 2014年4月29日 下午5:23:15 
 */
@Controller
@RequestMapping("/merchant/sale_rank")
public class SaleRankController extends BaseController {
	
	 @Resource
	 private MerchantOutletSupport merchantOutletSupport; 
	 
	@Resource
	private MerchantGroupUserSupport merchantGroupUserSupport;
	
	@Resource
	private SysUserSupport sysUserSupport;
	
	@RequestMapping(value ="/list" )
	public String getSaleRank(HttpServletRequest req, ModelMap model){
		//获取当前用户信息
		SysUserDto sysUser=(SysUserDto)sysUserSupport.getCurrentSysUser();	
		MerchantGroupUserDto merchantGroupUserDto=merchantGroupUserSupport.getBySysUserId(sysUser.getId());	
		List<Map<String,String>> list=merchantOutletSupport.getSaleRank(merchantGroupUserDto.getMerchantId(), TransType.presell);
		model.addAttribute("rankList", list);
		model.addAttribute("outletId", merchantGroupUserDto.getMerchantOutletId());
		return "/merchant/sale_rank/list";
	}
}
