package com.froad.cbank.coremodule.normal.boss.controller.member;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberAuditVo;
import com.froad.cbank.coremodule.normal.boss.support.member.MemberAuditSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 待审核
 * @ClassName MemberAuditController
 * @author yfy
 * @date 2015年6月10日 上午10:59:39
 */
@Controller
@RequestMapping("/member/audit")
public class MemberAuditController {
	
	@Resource
	private MemberAuditSupport memberAuditSupport;
	
	/**
	 * 待审核列表
	 * @author yfy
	 * @date 2015年6月11日 上午10:13:28
	 * @param model
	 * @param request
	 */
	@Auth(keys={"boss_member_audit_menu"})
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,Integer pageSize,Integer pageNumber) {
		LogCvt.info("待审核列表查询条件：pageSize:"+pageSize+",pageNumber:"+pageNumber);
		try {
			model.clear();
			model.putAll(memberAuditSupport.queryList(pageSize,pageNumber));
		} catch (Exception e) {
			new RespError(model,"待审核列表查询失败!!!");
			LogCvt.error("待审核列表查询请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 方法描述：待审核列表审核操作
	 * @author: yfy
	 * @date 2015年6月10日 下午3:38:22
	 * @param model
	 * @param request
	 * @param id
	 * @param state (0-未审核，1-通过，2-不通过)
	 * @param mobile
	 * @param bindMobile
	 * @param reason
	 */
	@Auth(keys={"boss_member_audit"})
	@RequestMapping(value="rev", method=RequestMethod.GET)
	public void audit(ModelMap model, HttpServletRequest request,MemberAuditVo memberAuditVo){
		LogCvt.info("待审核列表审核提交请求参数："+JSON.toJSONString(memberAuditVo));	
		try {
			model.clear();
			if(StringUtil.isNotBlank(memberAuditVo.getMsgID()) 
					&& StringUtil.isNotBlank(memberAuditVo.getState())
					&& StringUtil.isNotBlank(memberAuditVo.getAuditor())){
				model.putAll(memberAuditSupport.audit(memberAuditVo));
			}else{
				new RespError(model,"待审核列表审核提交id或state不能为空!!!");
			}
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model,"待审核列表审核提交失败!!!");
			LogCvt.error("待审核列表审核提交请求异常"+ e.getMessage(),e);
		}
	}
	
}
