package com.froad.cbank.coremodule.module.normal.bank.controller;



import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSON;
import com.froad.cbank.coremodule.framework.common.util.type.StringUtil;
import com.froad.cbank.coremodule.framework.common.valid.CheckPermission;
import com.froad.cbank.coremodule.framework.expand.log.logback.LogCvt;
import com.froad.cbank.coremodule.framework.web.basic.controller.BasicSpringController;
import com.froad.cbank.coremodule.module.normal.bank.service.BankOrgService;
import com.froad.cbank.coremodule.module.normal.bank.service.CommentService;
import com.froad.cbank.coremodule.module.normal.bank.service.LoginService;
import com.froad.cbank.coremodule.module.normal.bank.util.Constants;
import com.froad.cbank.coremodule.module.normal.bank.util.EnumTypes;
import com.froad.cbank.coremodule.module.normal.bank.util.QueryResult;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVo;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVoReq;
import com.froad.cbank.coremodule.module.normal.bank.vo.CommentVoRes;
import com.froad.cbank.coremodule.module.normal.bank.vo.MsgVo;

/**
 * 商户评价管理
 * 
 * @author yfy
 *
 */
@Controller
@RequestMapping(value = "/merchantComment")
public class MerchantCommentController extends BasicSpringController{
	
	@Resource
	private CommentService commentService; 
	@Resource 
	private BankOrgService bankOrgService;
	@Resource
	private LoginService loginService;
	
	/**
	 * @Title: 商户评价列表条件查询
	 * @author yfy
	 * @date 2015-3-23 下午13:20:32
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"merchant_comment_menu","merchant_comment_lt_bind"})
	@RequestMapping(value = "/lt",method = RequestMethod.POST)
    public void commentList(ModelMap model,HttpServletRequest req,@RequestBody CommentVoReq voReq) {

		try {
			voReq.setClientId(req.getAttribute(Constants.CLIENT_ID)+"");
			QueryResult<CommentVoRes> queryVo = commentService.findPageByMerchantConditions(voReq);
			LogCvt.info("MerchantCommentController.commentList商户评价列表条件返回:"
					+ JSON.toJSONString(queryVo));
			model.put("merchantCommentList", queryVo.getResult());
			model.put("totalCount", queryVo.getTotalCount());// 总记录数
			model.put("pageCount", queryVo.getPageCount());// 总页数
			model.put("pageNumber", queryVo.getPageNumber());// 当前页
			model.put("lastPageNumber", queryVo.getLastPageNumber());
			model.put("firstRecordTime", queryVo.getFirstRecordTime());
			model.put("lastRecordTime", queryVo.getLastRecordTime());
				
			model.put("message", "商户评价列表条件查询成功");
			
		} catch (Exception e) {
			LogCvt.info("商户评价列表条件查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
		
	}
	
	/**
	 * @Title: 商户评价详情
	 * @author yfy
	 * @date 2015-3-23 下午13:20:32
	 * @param model
	 * @param req
	 */
	
	@CheckPermission(keys={"merchant_comment_dl","merchant_comment_dl_bind"})
	@RequestMapping(value = "/dl",method = RequestMethod.GET)
    public void commentDetail(ModelMap model,HttpServletRequest req,String commentId) {

		try {
			String clientId = req.getAttribute(Constants.CLIENT_ID)+"";
			if(StringUtil.isNotEmpty(commentId)){
				CommentVo commentVo = commentService.merchantDetail(clientId,commentId);
				LogCvt.info("MerchantCommentController.commentDetail商户评价详情返回:"
						+ JSON.toJSONString(commentVo));
				model.put("commentVo", commentVo);
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "评价ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("商户评价详情查询" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
	/**
	 * @Title: 商户评价删除
	 * @author yfy
	 * @date 2015-3-23 下午15:10:30
	 * @param model
	 * @param req
	 */
	@CheckPermission(keys={"merchant_comment_de"})
	@RequestMapping(value = "/de",method = RequestMethod.DELETE)
    public void commentDetele(ModelMap model,HttpServletRequest req,String commentId) {
		
		try {
			if(StringUtil.isNotEmpty(commentId)){
				MsgVo msgVo = commentService.merchantCommentDelete(loginService.getOriginVo(req),commentId);
				LogCvt.info("MerchantCommentController.commentDetele商户评价删除返回:"
						+ JSON.toJSONString(msgVo));
				if(msgVo.getResult()){
					model.put("code", EnumTypes.success.getCode());
					model.put("message", "删除商户评价成功");
				}else{
					model.put("code", EnumTypes.fail.getCode());
					model.put("message", "删除商户评价失败");
				}
			}else{
				model.put("code", EnumTypes.empty.getCode());
				model.put("message", "评价ID不能为空");
			}
		} catch (Exception e) {
			LogCvt.info("商户评价删除" + e.getMessage(), e);
			model.clear();
			model.put("code", EnumTypes.thrift_err.getCode());
			model.put("message", EnumTypes.thrift_err.getMessage());
		}
	}
	
}
