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
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberVoReq;
import com.froad.cbank.coremodule.normal.boss.support.member.MemberRepreSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 会员申述
 * @ClassName MemberRepreController
 * @author zxl
 * @date 2015年6月10日 上午10:59:39
 */
@Controller
@RequestMapping("/member/repre")
public class MemberRepreController {
	
	@Resource
	private MemberRepreSupport memberRepreSupport;
	
	/**
	 * 人工申述列表
	 * @author yfy
	 * @date 2015年6月10日 下午2:19:30
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_member_repre_menu"})
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,MemberVoReq voReq) {
		LogCvt.info("人工申述列表查询条件："+JSON.toJSONString(voReq));
		try {
			model.clear();
			model.putAll(memberRepreSupport.queryList(voReq));
		} catch (Exception e) {
			new RespError(model,"人工申述列表查询失败!!!");
			LogCvt.error("人工申述列表查询请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 方法描述：人工申述审核
	 * @author: yfy
	 * @date 2015年6月10日 下午3:38:22
	 * @param model
	 * @param request
	 * @param id
	 * @param state (0-未审核，1-通过，2-不通过)
	 * @param reason
	 */
	@Auth(keys={"boss_member_repre_audit"})
	@RequestMapping(value="rev", method=RequestMethod.GET)
	public void audit(ModelMap model, HttpServletRequest request,Long msgID,
			Integer state,String reason,String email,String memberCode){
		LogCvt.info("人工申诉审核提交请求参数：msgID: "+msgID
				+",state:"+state+",reason:"+reason
				+",email:"+email+",memberCode:"+memberCode);	
		try {
			model.clear();
			if(msgID != null && state != null && StringUtil.isNotBlank(reason) 
					&& StringUtil.isNotBlank(email) && StringUtil.isNotBlank(memberCode)){
				model.putAll(memberRepreSupport.audit(msgID,state,reason,email,memberCode));
			}else{
				new RespError(model,"人工申诉审核提交信息不全!!!");
			}
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model,"人工申诉审核提交失败!!!");
			LogCvt.error("人工申诉审核提交请求异常"+ e.getMessage(),e);
		}
	}
	
}
