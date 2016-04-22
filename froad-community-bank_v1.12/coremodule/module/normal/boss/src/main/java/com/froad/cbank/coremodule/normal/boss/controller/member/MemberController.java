package com.froad.cbank.coremodule.normal.boss.controller.member;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.Regulars;
import com.froad.cbank.coremodule.normal.boss.annotation.Auth;
import com.froad.cbank.coremodule.normal.boss.common.logback.LogCvt;
import com.froad.cbank.coremodule.normal.boss.exception.BossException;
import com.froad.cbank.coremodule.normal.boss.pojo.member.MemberVo;
import com.froad.cbank.coremodule.normal.boss.support.member.MemberSupport;
import com.froad.cbank.coremodule.normal.boss.utils.RespError;

/**
 * 会员
 * @ClassName MemberController
 * @author zxl
 * @date 2015年6月9日 下午3:23:19
 */
@Controller
@RequestMapping("/member")
public class MemberController {
	
	@Resource
	private MemberSupport merberSupport;
	
	/**
	 * 用户信息列表
	 * @author yfy
	 * @date 2015年6月9日 下午4:13:15
	 * @param model
	 * @param request
	 * @param voReq
	 */
	@Auth(keys={"boss_user_support_menu"})
	@RequestMapping(value = "list", method = RequestMethod.GET)
	public void list(ModelMap model,HttpServletRequest request,String loginID,String mobile) {
		LogCvt.info("用户信息列表查询条件：loginID:"+loginID+",mobile:"+mobile);
		try {
			model.clear();
			if(StringUtil.isNotBlank(loginID) || StringUtil.isNotBlank(mobile)){
				model.putAll(merberSupport.queryList(loginID,mobile));
			}else{
				new RespError(model,"用户信息列表查询loginID或mobile不能为空!!!");
			}
			
		}catch (Exception e) {
			new RespError(model,"用户信息列表查询失败!!!");
			LogCvt.error("用户信息列表查询请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 方法描述：会员名模糊查询
	 * @author: yfy
	 * @date 2015年6月9日 下午4:51:19
	 * @param model
	 * @param request
	 * @param loginID
	 */
	@Auth(keys={"boss_user_support_menu"})
	@RequestMapping(value="sl", method=RequestMethod.GET)
	public void selectLoginID(ModelMap model, HttpServletRequest request,String loginID){
		LogCvt.info("会员名模糊查询条件：loginID: "+loginID);	
		try {
			model.clear();
			if(StringUtil.isNotBlank(loginID)){
				model.putAll(merberSupport.selectLoginID(loginID));
			}else{
				new RespError(model,"会员名模糊查询loginID不能为空!!!");
			}
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			new RespError(model,"会员名模糊查询失败!!!");
			LogCvt.error("会员名模糊查询请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 方法描述：用户信息修改提交
	 * @author: yfy
	 * @date 2015年6月9日 下午4:51:19
	 * @param model
	 * @param request
	 * @param memberCode
	 */
	@Auth(keys={"boss_user_support_modify"})
	@RequestMapping(value="ue", method=RequestMethod.PUT)
	public void update(ModelMap model, HttpServletRequest request,@RequestBody MemberVo memberVo){
		LogCvt.info("用户信息修改提交信息："+JSON.toJSONString(memberVo));	
		try {
			model.clear();
			if(StringUtil.isNotBlank(memberVo.getMemberCode())
					&& StringUtil.isNotBlank(memberVo.getCreater())
					&& StringUtil.isNotBlank(memberVo.getDemo())
					&& StringUtil.isNotBlank(memberVo.getAction())){
				
				Map<String,Object> filter = new HashMap<String, Object>();
				if(StringUtil.isNotBlank(memberVo.getMobile())){
					filter.put("mobile",memberVo.getMobile());
				}
				if(StringUtil.isNotBlank(memberVo.getUserState())){
					filter.put("status",memberVo.getUserState());
				}
				memberVo.setActionCode(JSON.toJSONString(filter).replace("\"", ""));
				
				model.putAll(merberSupport.update(memberVo));
			}else{
				new RespError(model,"用户信息修改提交信息的memberCode不能为空!!!");
			}
		}catch(BossException e){
			new RespError(model, e);
		} catch (Exception e) {
			new RespError(model,"用户信息修改提交失败!!!");
			LogCvt.error("用户信息修改提交请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 方法描述：用户信息详情
	 * @author: yfy
	 * @date 2015年6月9日 下午4:51:19
	 * @param model
	 * @param request
	 * @param memberCode
	 */
	@RequestMapping(value="dl", method=RequestMethod.GET)
	public void detail(ModelMap model, HttpServletRequest request,String memberCode){
		LogCvt.info("用户信息详情查询条件：memberCode: "+memberCode);	
		try {
			model.clear();
			if(StringUtil.isNotBlank(memberCode)){
				model.putAll(merberSupport.detail(Long.valueOf(memberCode)));
			}else{
				new RespError(model,"用户信息详情memberCode不能为空!!!");
			}
		} catch (Exception e) {
			new RespError(model,"用户信息详情查询失败!!!");
			LogCvt.error("用户信息详情查询请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 方法描述：历史绑定手机号查询
	 * @author: yfy
	 * @date 2015年6月9日 下午4:51:19
	 * @param model
	 * @param request
	 * @param memberCode
	 */
	@RequestMapping(value="sm", method=RequestMethod.GET)
	public void selectMobile(ModelMap model, HttpServletRequest request,String memberCode){
		LogCvt.info("历史绑定手机号查询条件：memberCode: "+memberCode);	
		try {
			model.clear();
			if(StringUtil.isNotBlank(memberCode)){
				model.putAll(merberSupport.selectMobile(Long.valueOf(memberCode)));
			}else{
				new RespError(model,"历史绑定手机号memberCode不能为空!!!");
			}
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			new RespError(model,"历史绑定手机号查询失败!!!");
			LogCvt.error("历史绑定手机号查询请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 方法描述：修改原因列表查询
	 * @author: yfy
	 * @date 2015年7月8日 下午5:31:08
	 * @param model
	 * @param request
	 * @param memberCode
	 */
	@RequestMapping(value="suelist", method=RequestMethod.GET)
	public void selectUpdateList(ModelMap model, HttpServletRequest request,String memberCode){
		LogCvt.info("修改原因列表查询条件：memberCode: "+memberCode);	
		try {
			model.clear();
			if(StringUtil.isNotBlank(memberCode)){
				model.putAll(merberSupport.selectUpdateList(Long.valueOf(memberCode)));
			}else{
				new RespError(model,"修改原因列表查询memberCode不能为空!!!");
			}
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			new RespError(model,"修改原因列表查询失败!!!");
			LogCvt.error("修改原因列表查询请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 方法描述：短信发送列表查询
	 * @author: yfy
	 * @date 2015年7月8日 下午5:31:08
	 * @param model
	 * @param request
	 * @param memberCode
	 */
	@RequestMapping(value="ssmslist", method=RequestMethod.GET)
	public void selectSmsList(ModelMap model, HttpServletRequest request,String memberCode){
		LogCvt.info("短信发送列表查询条件：memberCode: "+memberCode);	
		try {
			model.clear();
			if(StringUtil.isNotBlank(memberCode)){
				model.putAll(merberSupport.selectSmsList(Long.valueOf(memberCode)));
			}else{
				new RespError(model,"短信发送列表查询memberCode不能为空!!!");
			}
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			new RespError(model,"短信发送列表查询失败!!!");
			LogCvt.error("短信发送列表查询请求异常"+ e.getMessage(),e);
		}
	}
	
	/**
	 * 手机号验证
	 * @tilte vmobile
	 * @author zxl
	 * @date 2015年6月26日 上午9:59:41
	 * @param model
	 * @param request
	 * @param mobile
	 */
	@RequestMapping(value="vmobile", method=RequestMethod.GET)
	public void vmobile(ModelMap model, HttpServletRequest request,String mobile){
		try {
			model.clear();
			
			Pattern p = Pattern.compile(Regulars.MOBILE);
			Matcher m = p.matcher(mobile);
			if(!m.matches()){
				throw new BossException("手机号格式不正确");
			}
			
			model.putAll(merberSupport.vmobile(mobile));
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(),e);
		}
	}
	
	/**
	 * 邮箱验证
	 * @tilte vemail
	 * @author zxl
	 * @date 2015年6月26日 上午10:02:31
	 * @param model
	 * @param request
	 * @param mobile
	 */
	@RequestMapping(value="vemail", method=RequestMethod.GET)
	public void vemail(ModelMap model, HttpServletRequest request,String email){
		try {
			model.clear();
			
			Pattern p = Pattern.compile(Regulars.EMAIL);
			Matcher m = p.matcher(email);
			if(!m.matches()){
				throw new BossException("邮箱格式不正确");
			}
			
			model.putAll(merberSupport.vemail(email));
			
		} catch(BossException e){
			new RespError(model,e);
		} catch (Exception e) {
			new RespError(model);
			LogCvt.error(e.getMessage(),e);
		}
	}
	
}
